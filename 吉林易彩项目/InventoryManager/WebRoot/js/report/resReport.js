
/**
 * 动态加载
 */
function ajaxLoad(url,callback){
	request = (window.XMLHttpRequest) ? new XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHTTP");  
    request.onreadystatechange = callback;  
    request.open("POST", context_path+url,true);  
    request.send(null);
    
}
function loadDemo(){
	if (request.readyState ==4 && request.status==200){
		var json_data = eval('(' + request.responseText + ')');
		var data =  eval('(' +json_data.message+ ')');
		var mapConfig = {
				title:{
					text:data["title"]["text"],
					subtext:data["title"]["subtext"]
				},
				tooltip:{
					trigger:'item'
				},
				legend:{
					orient:'vertical',
					x:'right',
					data:[data["legend"]["data"]]
				},
				dataRange:{
					min:0,
					max:450,
					selectedMode:false,
					x:'left',
					y:'bottom',
					text:['高','低'],
					calculable:true
				},
				series:[{
					name:data["series"]["name"],
					type:'map',
					mapType:data["series"]["mapType"],
					selectedMode:'single',
					itemStyle:{
						normal:{
							label:{
								show:true
							}
						},
						emphasis:{
							label:{
								show:true
							}
						}
					},
					data:data["series"]["data"]
				}]
			};
			require.config({
				paths:{
					'echarts':context_path+'/js/echarts/build/echarts-map',          
					'echarts/chart/map':context_path+'/js/echarts/build/echarts-map'
				}
			});
			var myChart;
			require(['echarts','echarts/chart/map'],function(ec){				
				myChart = ec.init(document.getElementById('main'));
				myChart.setOption(mapConfig);	
				/*绑定选择事件*/
				var ecConfig = require('echarts/config');
				myChart.on(ecConfig.EVENT.MAP_SELECTED,function(param){
					var selected=param.selected;  
		            var mapSeries=mapConfig.series[0];  
		            var data= [];  
		            var legendData= [];  
		            var name;  
		            var city ;
		            for (var p=0,len=mapSeries.data.length; p<len; p++) {  
		               name=mapSeries.data[p].name;  
		               mapSeries.data[p].selected=selected[name];  
		               if (selected[name]) {  
		            	   city = name;
		               }  
		            }
		            var url = context_path+'/pages/report/resDetail.jsp?city='+encodeURIComponent(city)+'';
		            //var url = context_path+'/pages/approval/resGis.jsp';
					openWin(url,'查看',900,600);
				}); 
			});
	}
}

/**
 * 打开窗体
 * @param url
 * @param name
 * @param iWidth
 * @param iHeight
 */
function openWin(url,name,iWidth,iHeight) { 
    var iTop = (window.screen.availHeight - 30 - iHeight) / 2; 
    var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; 
    window.open(url, name, 'height=' + iHeight + ',innerHeight=' + iHeight + ',width=' + iWidth + ',innerWidth=' + iWidth + ',top=' + iTop + ',left=' + iLeft + ',status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no'); 
}