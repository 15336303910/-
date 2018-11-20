var pointList = new Array();
var resList = new Array();
var siteList = new Array();
var longitude=117.1301469455;
var latitude =36.6651862225;
var thisPagesize = 100;
var siteStore;
var siteGrid;
/**
 * 派发工单并刷新列表
 * @param stone
 */
function sendApproval(store){
	var taskTitle = itemText(.5,80,'taskTitle','工单标题');
	var startTime = basicDate(.5,80,'startTime','开始时间');
	var endTime = basicDate(.5,80,'endTime','结束时间');
	
	var res_store = new Ext.data.SimpleStore({
		fields : ['value','text'],
		data : [['well,pole,stone,optical', '外线资源'],['station','站点'],
		  ['optiTermainal','光终端盒']]
	});
	
	var grab_store = new Ext.data.SimpleStore({
		fields : ['value','text'],
		data : [['简易清查', '简易清查'],['应急普查','应急普查'],
		  ['普查项目','普查项目']]
	});
	
	var comp_store =new Ext.data.SimpleStore({
		fields : ['value','text'],
		data : [['城一分公司', '城一分公司'],['城二分公司','城二分公司'],
		  ['城三分公司','城三分公司'],['顺义区','顺义区'],['通州区','通州区'],
		  ['大兴区','大兴区'],['房山区','房山区'],['昌平区','昌平区'],
		  ['平谷区','平谷区'],['密云区','密云区'],['怀柔区','怀柔区'],
		  ['延庆区','延庆区'],['网运中心','网运中心']
		]
	});
	var addPanel =new Ext.form.FormPanel({
	    layout : "column",
	    id:'addPanel',
		border : false,
		width:'100%',
		autoHeight:true,
		frame:true,
		fileUpload : true,
		items:[
		    taskTitle,{
				  xtype:"panel",
				  layout : "form",
				  columnWidth : .5,
				  border : false,
				  labelWidth:80,
				  items:[{
					  xtype : 'combo',  
				      name : 'grabType',
				      id : 'grabType_id',
				      mode : 'local',
				      emptyText:'请选择',
				      readOnly : false,
				      anchor : '85%',
				      triggerAction : 'all',
				      fieldLabel : '采集类型',
				      store : grab_store,
				      hiddenName : 'grabType',
				      valueField : 'value',
				      allowBlank:true,
				      blankText:"请选择采集类型",
				      displayField : 'text'
				  }]
			  },startTime,endTime,{
		    	xtype:"panel",
		    	layout : "form",
				columnWidth : .5,
				border : false,
				labelWidth:80,
				items : [{
					id:'county',
					name : "county",
					fieldLabel : "区县",
					xtype : "combo",
					editable : false,
					emptyText:'请选择',
	    			triggerAction: 'all',
	    			mode: 'remote',
	    			lazyRender:true,
	    			autoSelect :true,
	    			store:new Ext.data.Store({
	    			   autoLoad:true,
				       proxy: new Ext.data.HttpProxy({url:context_path+'/dictAction!getImCounty.action?cityId=1'}),
				       reader: new Ext.data.JsonReader({
				        fields:[
				           {name:'id'},
				           {name:'name'}
				       	]
				       })
					}),
					listeners:{
						change:function (t,newValue,oldValue)
						{
							Ext.getCmp('group_id').setValue("");
							Ext.getCmp('group_id').getStore().reload({params:{domainCode:newValue}});
						}
					},
	   				displayField:'name',
					valueField:'id',
					anchor : '85%',
					allowBlank:true,
					blankText:"请选择区县",
			    	msgTarget:"under"
		    }]
		  },{
		    	xtype:"panel",
		    	layout : "form",
				columnWidth : .5,
				border : false,
				labelWidth:80,
				items : [{
					id:'group_id',
					name : "group",
					fieldLabel : "班组",
					xtype : "combo",
					editable : false,
					emptyText:'请选择',
	    			triggerAction: 'all',
	    			mode: 'local',
	    			lazyRender:true,
	    			store:new Ext.data.Store({
	    			   autoLoad:false,
				       proxy: new Ext.data.HttpProxy({url:context_path+'/maintainGroupAction!getMtGoup.action'}),
				       reader: new Ext.data.JsonReader({
				        fields:[
				           {name:'id'},
				           {name:'name'}
				       	]
				       })
					}),
	   				displayField:'name',
					valueField:'id',
					anchor : '85%',
					allowBlank:true,
					blankText:"请选择班组",
			    	msgTarget:"under"
		    }]
		  },{
			  xtype:"panel",
			  layout : "form",
			  columnWidth : .5,
			  border : false,
			  labelWidth:80,
			  items:[{
				  xtype : 'combo',  
			      name : 'blcomp',
			      id : 'blcomp_id',
			      mode : 'local',
			      emptyText:'请选择',
			      readOnly : false,
			      anchor : '85%',
			      triggerAction : 'all',
			      fieldLabel : '二级单位',
			      store : comp_store,
			      hiddenName : 'blcomp',
			      valueField : 'value',
			      allowBlank:true,
			      blankText:"请选择二级单位",
			      displayField : 'text'
			  }]
		  },{
			  xtype:"panel",
			  layout : "form",
			  columnWidth : .5,
			  border : false,
			  labelWidth:80,
			  items:[{
				  xtype : 'combo',  
			      name : 'resType',
			      id : 'resType_id',
			      mode : 'local',
			      emptyText:'请选择',
			      readOnly : false,
			      anchor : '85%',
			      triggerAction : 'all',
			      fieldLabel : '资源类型',
			      store : res_store,
			      hiddenName : 'resType',
			      valueField : 'value',
			      allowBlank:true,
			      blankText:"请选择资源类型",
			      displayField : 'text',
			      listeners:{
					select:function(combo,record,opts) {
						var resType = Ext.getCmp('resType_id').getValue();
						if(resType =='station'){
							Ext.getCmp('addWin').remove(mapPanel,false);
							Ext.getCmp('addWin').add(siteGrid)
							mapPanel.hide();
							siteGrid.show();
						}else{
							Ext.getCmp('addWin').remove(siteGrid,false);
							Ext.getCmp('addWin').add(mapPanel);
							siteGrid.hide();
							mapPanel.show();
						}
						Ext.getCmp('addWin').doLayout();
					}
			      }
			  }]
		  }
		]
	});
	
	
	var mapButton=['-',{
		id:'loadRes',
		text:'加载资源',
		iconCls:'add',
		handler:function(){
		    loadRes();
		}
	},'-',{
    	id:'drawPoint',
    	text:"画点",
    	iconCls:'add',
    	handler:function(){
    		tb.activate(esri.toolbars.Draw.POINT);
    		map.hideZoomSlider();
    	}
    },'-',{
    	id:'clearPoint',
    	text:"清屏",
    	iconCls:'add',
    	handler:function(){
    		pointList.splice(0,pointList.length);
    		map.graphics.clear();
    	}
    },'-',{
    	id:'panMap',
    	text:"拖动地图",
    	iconCls:'add',
    	handler:function(){
    		tb.deactivate();
    	}
    },'-',{
    	id:'completeMap',
    	text:"完成",
    	iconCls:'add',
    	handler:function(){
    		tb.deactivate();
    		completeMap();
    	}
    },'-',{
    	id:'loadHis',
    	text:"加载历史",
    	iconCls:'add',
    	handler:function(){
    		loadHis();
    	}
    },'-',{
    	id:'locatArea',
    	xtype:'textfield',
    	name:'locatArea',
    	emptyText:'输入关键字' 
    },'-',{
    	id:'locatMap',
    	text:'加载位置',
    	handler:function(){
    		locatMapFun();
    	}
    },'-',{
    	id:'delFlag',
    	xtype:'checkbox',
    	boxLabel: "派发清查",
    	checked:true
    },'-'];
	//封裝 站點數據
	var siteButton=['-',{
		id:'siteArea',
    	xtype:'textfield',
    	name:'siteArea',
    	emptyText:'输入关键字' 
	},'-',{
		id:'locatSite',
		text:'站点查询',
		handler:function(){
			var  siteSeach =Ext.getCmp('siteArea').getValue();
			Ext.apply(siteGrid.getStore().baseParams, {siteName:siteSeach});
			siteGrid.getStore().reload({params:{
				start:0,limit:15
			}});
		}
	},'-'];
	var siteBbr  = new Ext.PagingToolbar({
        pageSize: thisPagesize,
        store: siteStore,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
	});
	var column = "[{header:'id',dataIndex:'id',hidden:true,width:150,fun:null}," +
				"{header:'站点ID',dataIndex:'stationBaseId',hidden:true,width:150,fun:null}," +
				"{header:'站点综资',dataIndex:'siteNum',hidden:true,width:150,fun:null}," +
				"{header:'机房ID',dataIndex:'generatorId',hidden:true,width:150,fun:null}," +
				"{header:'机房综资',dataIndex:'generNum',hidden:true,width:150,fun:null}," +
				"{header:'站点名称',dataIndex:'stationName',hidden:false,width:150,fun:null}," +
				"{header:'机房名称',dataIndex:'generatorName',hidden:false,width:150,fun:null}," +
				"{header:'楼层',dataIndex:'szlc',hidden:false,width:50,fun:null}," +
				"{header:'经度',dataIndex:'lon',hidden:false,width:150,fun:null}," +
				"{header:'纬度',dataIndex:'lat',hidden:false,width:150,fun:null}," +
				"{header:'维护人',dataIndex:'parties',hidden:false,width:100,fun:null}" +
				"]";
	var siteResult = basicStore(column,'approvalAction!getSeachSite.action','totalCount','root')
	siteStore =siteResult.store;
	var siteCm = siteResult.cm;
	var siteSm = siteResult.sm;
	siteGrid = new Ext.grid.GridPanel({
		store: siteStore,
		cm: siteCm,
		sm: siteSm,
		loadMask:{msg : '加载数据中，请稍候...'},
		height: 400,
		anchor:'100% -30',
	    bodyStyle:'width:100%',
	    stripeRows: true,// 让grid相邻两行背景色不同
	    loadMask: false,
	    tbar:new Ext.Toolbar({ //标题+按钮的工具栏
        	items:siteButton
        }),
	    bbar: siteBbr
	});
	
		
	//waixian ditu  
	var mapPanel = new Ext.Panel({
    	id: 'mapPanel',
    	region:'center',
    	border: false,
    	title: '划定区域',
    	tbar:new Ext.Toolbar({ //标题+按钮的工具栏
        	items:mapButton
        }),
    	html: '<div id="mapDiv" style="width:100%;height:400px;"></div>'
    });
	
	
	var addWin = new Ext.Window({
		id:'addWin',
		title:'派发任务',
      	width:800,
      	closable:false,
      	height:585,
      	modal:true,
      	buttonAlign:"center",
      	items:[addPanel,mapPanel],
      	buttons:[{
      		text:"提交",
      		handler:function(){
      			var resType = Ext.getCmp('resType_id').getValue();
      			var groupId = Ext.getCmp('group_id').getValue();
      			var blcomp_id = Ext.getCmp('blcomp_id').getValue();
      			var delFlag = Ext.getCmp('delFlag').getValue();
      			var title = Ext.getCmp('taskTitle').getValue();
      			if(delFlag == true){
      				delFlag = 'Y';
      			}else{
      				delFlag = 'N';
      			}
      			if(blcomp_id == null || blcomp_id ==""){
      				alert("请选择所属公司!");
      				return;
      			}
      			if(title == null || title ==""){
      				alert("请填写工单标题!");
      				return;
      			}
      			if(resType == null || resType ==""){
      				alert("请选择资源类型!");
      				return;
      			}
      			if("well,pole,stone"==resType){
      				if(pointList == null || pointList.length < 3){
          				Ext.Msg.show({
    						title : "提示",
    						msg : "请划定核查区域！",
    						width :150,
    						buttons: Ext.MessageBox.OK,
    						icon: Ext.MessageBox.INFO
    					});
          				return ;
          			}
      			}else if("station" == resType){
      				var rows = siteGrid.getSelectionModel().getSelections();
      				if(rows.length != 1){
      					Ext.Msg.alert('信息提示','请选择一个机房！');
      					return ;
      				}
      				var flag = true;
      				for(var i=0;i<rows.length;i++ ){
      					var gid = rows[i].data["generatorId"];
      					if(gid == null || gid == ""){
      						flag = false;
      					}
      					siteList.push({id:rows[i].data["stationBaseId"],
      									resName:rows[i].data["stationName"],
      									resNum:rows[i].data["siteNum"],
      									generId:rows[i].data["generatorId"],
      									generName:rows[i].data["generatorName"],
      									generNum:rows[i].data["generNum"],
      									resType:resType,
      									lon:rows[i].data["lon"],
      									lat:rows[i].data["lat"]});
      				}
      				if(!flag){
      					alert("选中站点下有空机房!");
      					return false;
      				}
      				
      			}
      			var pointStr = JSON.stringify(pointList);
      			var resStr = JSON.stringify(resList);
      			var siteStr = JSON.stringify(siteList);
      			//先看下有没有重复工单
      			var myMask = new Ext.LoadMask(Ext.getBody(), { 
                    msg: '正在计算区域信息,请稍后......'
                }); 
                myMask.show();
      			Ext.Ajax.request({ 
      				url:context_path+'/approvalSendAction!getPointNum.action',
      				method:'post',
      				params:{pointList:pointStr,title:title},
      				success:function(fp,o){
      					myMask.hide();
      					result = fp.responseText;
      					if(result == '工单标题'){
      						alert("标题重复,请更改标题内容!");
      						return;
      					}
      					if(result >800){
      						alert("选择区域过大包含资源数过多,请重新选择派单区域!");
      						return;
      					}
      					addPanel.form.submit({
      	      				url : context_path+'/approvalSendAction!payoutWork.action',
      	            		waitMsg : '正在派发工单，请稍等...',
      	            		params : {pointList:pointStr,resList:resStr,siteList:siteStr,groupId:groupId,belongCmp:blcomp_id,delFlag:delFlag},
      	            		success:function() {
      	            			Ext.Msg.show({
      	    						title : "提示",
      	    						msg : "保存成功！",
      	    						width :150,
      	    						buttons: Ext.MessageBox.OK,
      	    						icon: Ext.MessageBox.INFO
      	    					});
      	            		},
      	            		failure : function() {
      	            			Ext.Msg.show({
      	    						title : "提示",
      	    						msg : "保存失败！",
      	    						width :150,
      	    						buttons: Ext.MessageBox.OK,
      	    						icon: Ext.MessageBox.INFO
      	    					});
      	            		}
      	      			});
      				}
      		 	});
      			return ;
      			
      		}
      	},{
      		text:"关闭",
      		handler:function(){
      			pointList = new Array();
      			resList = new Array();
      			siteList = new Array();
      			addWin.destroy();
      		}
      	}]
	});
	addWin.show();
}

/**
 * 完成的时候
 * 调用的方法
 */
function completeMap(){
	if(pointList != null && pointList.length >2){
		var num = pointList.length;
		var first = pointList[0];
		var end = pointList[num-1];
		//绘制一条线形成闭合
		var polyline = new esri.geometry.Polyline
		([[first.longitude,first.latitude],
		  [end.longitude,end.latitude]]);
		var color = new dojo.Color([245,0,68]);
		var symbol = new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, color, 2);
		var lineGraph = new esri.Graphic(polyline, symbol);
		map.graphics.add(lineGraph);
		var areaNum = parseFloat(polygonArea());
		
		if(areaNum >=100000){
			alert("所选区域过大，请重新绘制!");
			return ;
		}
	}else{
		alert("请绘制多边形区域!");
		return ;
	}
}


/**
 * 计算闭合区
 * 域内面积
 */
function polygonArea(){
	var str = "";
	var result;
	for(var i=0;i<pointList.length;i++){
		var point = pointList[i];
		str += point.longitude+","+point.latitude+";";
	}
	Ext.Ajax.request({ 
		url:context_path+'/approvalSendAction!polygonArea.action',
		method:'post',
		params:{str:str},
		success:function(fp,o){
			result = fp.responseText;
		}
 	});
	return result;
}

/**
 * 加载历史
 */
function loadHis(){
	var resType = Ext.getCmp('resType_id').getValue();
	var city = "北京";
	var county = Ext.getCmp('county').getRawValue();
	if(city == null || city ==""){
		alert("请选择地市!");
		return ;
	}
	if(county == null || county ==""){
		alert("请选择区县!");
		return ;
	}
	Ext.Ajax.request({ 
		url:context_path+'/approvalSendAction!loadHisMap.action',
		method:'post',
		params:{resType:resType,city:city,county:county},
		success:function(fp,o){
			var result = fp.responseText;
			var infoObj= eval("("+result+")");
			var ptlayer = esri.layers.GraphicsLayer({id:"ptlayer"});
			for(var i=0;i<infoObj.length;i++){
				var points = infoObj[i].mapPoint;
				var pts = points.split(",");
				for(var j=0;j<pts.length;j++){
					var img = context_path+"/images/map/point.png";
					var ptobj = pts[j].split("_");
					var pt = new esri.geometry.Point({
				    	type:'point',
				    	"x":ptobj[2],
				    	"y":ptobj[1]
				    });
					
					var template = new esri.InfoTemplate();
				    template.setTitle(infoObj[i].taskTitle);
				    template.setContent(infoObj[i].taskTitle+'第'+ptobj[0]+'节点');
					
					var pictureMarkerSymbol = new esri.symbol.PictureMarkerSymbol(img, 20, 20);
				    var g = new esri.Graphic(pt, pictureMarkerSymbol); 
				    g.setInfoTemplate(template);
				    if(j==0){
				    	ptlayer.add(g);
				    }
				    //划线
				    if(j>0){
				    	//得到上一个节点
				    	var last = pts[j-1].split("_");
				    	var polyline = new esri.geometry.Polyline
		        		([[ptobj[2],ptobj[1]],
		        		  [last[2],last[1]]]);
				    	var color = new dojo.Color([0,0,255]);
				    	var symbol = new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, color, 2);
				    	var lineGraph = new esri.Graphic(polyline, symbol);
				    	ptlayer.add(lineGraph);
				    }
				    //将最后一个点和第一个点连接起来
				    if(j == pts.length -1){
				    	var first = pts[0].split("_");
				    	var polyline = new esri.geometry.Polyline
		        		([[ptobj[2],ptobj[1]],
		        		  [first[2],first[1]]]);
				    	var color = new dojo.Color([0,0,255]);
				    	var symbol = new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, color, 2);
				    	var lineGraph = new esri.Graphic(polyline, symbol);
				    	ptlayer.add(lineGraph);
				    }
				}
			}
			map.addLayer(ptlayer);
		}
 	});
}


/**
 * 添加到地图上
 * @param geometry
 */
function addToMap(geometry) {
    switch (geometry.type) {
       case "point":
    	 //先画出点来
    	 var img = context_path+"/images/map/point.png";
    	 var symbol = new esri.symbol.PictureMarkerSymbol(img, 20, 20);
    	 var graphic = new esri.Graphic(geometry, symbol);
         map.graphics.add(graphic);
         //判断是不是已经存在原有点
         if(pointList!=null && pointList.length >0){
        	 var num = pointList.length;
        	 var polyline = new esri.geometry.Polyline
        		([[pointList[num-1].longitude,pointList[num-1].latitude],
        		  [geometry.x,geometry.y]]);
        	 var color = new dojo.Color([245,0,68]);
        	 var symbol = new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, color, 2);
        	 var lineGraph = new esri.Graphic(polyline, symbol);
        	 map.graphics.add(lineGraph);
    	 }
         pointList.push({num:pointList.length+1,latitude:geometry.y,longitude:geometry.x});
    	 break;
       case "polyline":
         var symbol = new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_DASH, new dojo.Color([255,0,0]), 1);
         var graphic = new esri.Graphic(geometry, symbol);
         map.graphics.add(graphic);
         break;
       case "polygon":
         var symbol = new esri.symbol.SimpleFillSymbol(esri.symbol.SimpleFillSymbol.STYLE_NONE, new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_DASHDOT, new dojo.Color([255,0,0]), 2), new dojo.Color([255,255,0,0.25]));
         var graphic = new esri.Graphic(geometry, symbol);
         map.graphics.add(graphic);
         break;
       case "extent":
         var symbol = new esri.symbol.SimpleFillSymbol(esri.symbol.SimpleFillSymbol.STYLE_NONE, new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_DASHDOT, new dojo.Color([255,0,0]), 2), new dojo.Color([255,255,0,0.25]));
         var graphic = new esri.Graphic(geometry, symbol);
         map.graphics.add(graphic);
         break;
       case "multipoint":
         var symbol = new esri.symbol.SimpleMarkerSymbol(esri.symbol.SimpleMarkerSymbol.STYLE_DIAMOND, 20, new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new dojo.Color([0,0,0]), 1), new dojo.Color([255,255,0,0.5]));
         var graphic = new esri.Graphic(geometry, symbol);
         map.graphics.add(graphic);
         break;
     }
}

/**
 * 加载地图
 */
function locatMapFun(){
	var locatArea = Ext.getCmp('locatArea').getValue();
	var resType = Ext.getCmp('resType_id').getValue(); 
	map.removeAllLayers();
	var layer =new esri.layers.ArcGISDynamicMapServiceLayer("http://10.224.202.65:6080/arcgis/rest/services/bjMapService1106/MapServer");
	//var layer = new esri.layers.ArcGISTiledMapServiceLayer("http://server.arcgisonline.com/ArcGIS/rest/services/ESRI_Imagery_World_2D/MapServer");
	map.addLayer(layer);
	Ext.Ajax.request({ 
		url:context_path+'/approvalSendAction!locatMap.action',
		method:'post',
		params:{locatArea:locatArea,resType:resType},
		success:function(fp,o){
			var result = fp.responseText;
			if(null == result || result == ''){
				alert("查无数据,请放大查询范围.");
				return ;
			}else{
				var infoObj= eval("("+result+")");
				if(resType =='station'){
					var drawLayer =  drawRes(result);
					var info = infoObj.info;
					var pt = new esri.geometry.Point([info[0].start.lon,infoObj.info[0].start.lat]);
					map.centerAndZoom(pt,.5);
					map.addLayer(drawLayer);
					dojo.connect(drawLayer,"onClick",drawClick);
				}else{
					var pt = new esri.geometry.Point([infoObj[0].longitude,infoObj[0].latitude]);
					map.centerAndZoom(pt,.5);
				}
			}
		}
 	});
}

/**
 * 加载资源
 */
function loadRes(){
	var lat,lon;
	if(pointList!=null && pointList.length >2){
		lat = pointList[pointList.length-1].latitude;
		lon = pointList[pointList.length-1].longitude;
	}else{
		lat = latitude;
		lon = longitude;
	}
	var resType = Ext.getCmp('resType_id').getValue();
	if(null == resType || '' == resType){
		alert("请选择资源类型!");
		return ;
	}
	Ext.Ajax.request({ 
		url:context_path+'/approvalSendAction!loadRes.action',
		method:'post',
		params:{lat:lat,lon:lon,resType:resType},
		success:function(fp,o){
			result = fp.responseText;
			var drawLayer =  drawRes(result);
			var pt = new esri.geometry.Point([lon,lat]);
			map.centerAndZoom(pt,5);
			map.addLayer(drawLayer);
			dojo.connect(drawLayer,"onClick",drawClick);
		}
 	});
}

function getResColumn(grapType,type){
	var arr ;
	if(grapType == 'line'){
		arr = ["资源名称","系统名称","维护区县","计算长度","维护长度","A端设备",
		       "Z端设备","创建时间","修改时间","数据质量责任人","一线维护人","综资标识"];
	}else{
		if(type == 'optical'){
			arr = ["资源名称","经度","纬度","所属区县","光交箱面数","安装地址",
			       "安装容量","使用容量","设计容量","空闲容量",
			       "创建时间","修改时间","数据质量责任人","一线维护人","综资标识"];
		}else if(type == 'OpticalTerminal'){
			arr = ["资源名称","经度","纬度","所属区县","所属设备",
			       "创建时间","修改时间","数据质量责任人","一线维护人","综资标识"];
		}else if(type =='station'){
			arr = ["资源名称","经度","纬度","所属区县","资源地址","站点类型","业务级别",
			       "创建时间","修改时间","数据质量责任人","一线维护人","综资标识"];
			
		}else if(type == 'generator'){
			arr = ["资源名称","经度","纬度","所属区县","资源地址","机房类型","业务级别","所在楼层",
			       "创建时间","修改时间","数据质量责任人","一线维护人","综资标识"];
		}else if(type == 'rack'){
			arr = ["资源名称","厂家","机架类型","机架行号","机架列号",
			       "创建时间","修改时间","数据质量责任人","一线维护人","综资标识"];
		}else if(type == 'odm'){
			arr = ["模块名称","模块编号","行数","列数",
			       "创建时间","修改时间","综资标识"];
			$("#sendRes").attr({ disabled: "disabled" });
		}else if(type == 'ne'){
			arr = ["设备名称","设备型号","设备厂家",
			       "创建时间","修改时间","综资标识"];
			$("#sendRes").attr({ disabled: "disabled" });
		}else if(type == 'neCard'){
			arr = ["板卡名称","软件版本","板卡型号","板卡类型",
			       "创建时间","修改时间","综资标识"];
			$("#sendRes").attr({ disabled: "disabled" });
		}else if(type == 'odmPoint'){
			arr = ["资源名称","列号","行号","上架纤芯","状态",
			       "创建时间","修改时间","综资标识"];
			$("#sendRes").attr({ disabled: "disabled" });
		}else if(type == 'nePoint'){
			arr = ["资源名称","对端","状态",
			       "创建时间","修改时间","综资标识"];
			$("#sendRes").attr({ disabled: "disabled" });
		}else{
			arr = ["资源名称","编号","经度","纬度","所属区县",
			       "创建时间","修改时间","数据质量责任人","一线维护人","综资标识"];
		}
	}
	return arr;
}

/**
 * 点击事件
 * @param event
 */
function drawClick(event){
	 var graphic=event.graphic;
	 var resType = graphic.attributes.resType;
	 var id = graphic.attributes.id;
	 var lon = graphic.geometry.x;
	 var lat = graphic.geometry.y;
	 var symbol = graphic.symbol;
	 var grapType = graphic.attributes.grapType;
	 var feildlabelIndex=1;
	 var resForm = new Ext.form.FormPanel({
		 layout : "column",
		 id:'resForm',
		 border : false,
	     width:'100%',
		 autoHeight:true,
	     frame:true,
		 fileUpload : true,
		 items:[]
	 });
	 var resCol = getResColumn(grapType,resType);
	 var resName = "";
	 //加载资源信息
	 Ext.Ajax.request({ 
		url:context_path+'/approvalAction!getResObj.action?',
		method:'post',
		params:{resId:graphic.attributes.id, type:graphic.attributes.resType},
		success:function(fp,o){
			result = fp.responseText;
			var dataObj=eval("("+result+")");
			var resStr = dataObj.resStr;
			var formPanel = Ext.getCmp("resForm");
			var arrs =resCol.toString().split(",");
			for(var i=0;i<arrs.length;i++){
				var name = arrs[i];
				if(name =='资源名称'){
					resName = resStr[name];
				}
				var tval = resStr[name];
				if(tval == null || tval == "null"){
					tval ="";
				}
		    	formPanel.add(itemText(.5,80,'taskTitle'+feildlabelIndex,name,tval));
		        feildlabelIndex++;
			}
     		 formPanel.doLayout();
		}
	 });
	 var resWin = new Ext.Window({
		id:'resWin',
		title:'查看资源',
	    width:400,
	    closable:false,
	    height:243,
	    modal:true,
	    buttonAlign:"center",
	    items:[resForm],
	    buttons:[{
	      text:"数据准确",
	      handler:function(){
	    	var resPoint = {id:id,resType:resType,grapType:grapType,resNum:graphic.attributes.resNum,lon:lon,lat:lat};
	      	if(resList !=null && resList.length >0){
	      		if(!contains(resList,resPoint)){
	      			resList.push(resPoint);
	      		}
	      	}else{
	      		resList.push(resPoint);	
	      	}
	      	alert("已确认!");
	      	var img = context_path+"/images/map/"+resType+"del.png";
	      	graphic.setSymbol(new esri.symbol.PictureMarkerSymbol(img, 20, 20));
	      }
	    },{
	    	text:"现场稽核",
	    	handler:function(){
	    		if(siteList.length>=1){
	    			alert("你已选中一个站，暂不支持批量核查!");
	    			return;
	    		}else{
	    			siteList.push({id:id,resName:resName,resType:resType,resNum:graphic.attributes.resNum,lon:lon,lat:lat});
		    		var img = context_path+"/images/map/"+resType+"del.png";
			      	graphic.setSymbol(new esri.symbol.PictureMarkerSymbol(img, 20, 20));
			      	resWin.destroy();
	    		}
	    		
	    	}
	    },{
	      text:"关闭",
	      handler:function(){
	      	resWin.destroy();
	      }
	    },{
	    	text:"查看站内",
	    	handler:function(){
	    		var url = context_path+"/pages/approval/site/stationInfo.jsp?" +
	    				"resId="+id+"&resType="+resType+"&resName="+encodeURI(resName)+"";
	    		window.open(url); 
	    	}
	    }]
	}); 
	 resWin.show();
}


/**
 * 得到点击中心点
 * @param event
 */
function setMapCenter(evt){
    longitude = (map.extent.xmax+map.extent.xmin)/2;
    latitude = (map.extent.ymax+map.extent.ymin)/2;
}

/**
 * 判断是否
 * @param arr
 * @param obj
 * @returns {Boolean}
 */
function contains(arr, obj) {  
    var i = arr.length;  
    while (i--) {  
        if (arr[i].id=== obj.id && arr[i].resType === obj.resType) {  
            return true;  
        }  
    }  
    return false;  
} 
/**
 * 初始化地图
 */
function initMap(){
	dojo.require("esri.tasks.geometry");
	dojo.require("esri.toolbars.draw");
	dojo.require("esri.toolbars.navigation");
	dojo.require("esri.layers.ArcGISDynamicMapServiceLayer");
	dojo.require("dijit.form.Button");
    dojo.require("dijit.Toolbar");
    
    
	map = new esri.Map("mapDiv",{
			zoom:7});
	map.centerAndZoom(new esri.geometry.Point({  
        "x":longitude,  
        "y":latitude  
    }),7);
	
	map.on("extent-change", setMapCenter);
	
	//点击结束时调用
	tb = new esri.toolbars.Draw(map);
	dojo.connect(tb, "onDrawEnd", addToMap);
	//var layer =new esri.layers.ArcGISDynamicMapServiceLayer("http://10.224.202.65:6080/arcgis/rest/services/bjMapService1106/MapServer");
	var layer = new esri.layers.ArcGISTiledMapServiceLayer("http://server.arcgisonline.com/ArcGIS/rest/services/ESRI_Imagery_World_2D/MapServer");
    map.addLayer(layer);
}