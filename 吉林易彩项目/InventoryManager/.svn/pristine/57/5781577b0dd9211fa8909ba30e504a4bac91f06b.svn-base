var bu;//选择相应的井面
var bu2;//选择相应井面对应数字
var flag=true;
var face="";
var oppoface="";
var ftn="";
var tubeNameforSpl="";
var addcableno;
var wellmap1="";//当前井的井面
var wellmap2="";//对端井的井面
	//长按按钮
	function ondown(){
		var intervalTimer = null;
		for(var j=1;j<9;j++){
			$("#x_"+j).mousedown(function(e){
				clearTimeout(intervalTimer); //取消上次延时未执行的方法
		        intervalTimer = setTimeout(function() {
		            var faceNumberGrid = Ext.getCmp("123");
//					if (!faceNumberGrid.faceNumberWindow) {
						faceNumberGrid.faceNumberWindow = new com.increase.cen.poleline.PolelineWindow({
							id : "faceNumberWindow",
							title : '选择井面相应的数字',
							iconCls : 'i-script-window',
							items : [{
								id : "faceNumber",
								height : 330,
								xtype : 'faceNumber'// 窗口里的表单
							}]
						});
//					}
					faceNumberGrid.faceNumberWindow.show();
					//将页面设置初始化
					resetfacenumber();
					//对已经形成的面对应的数字进行不可见设置
					wellmap1=Ext.getCmp("wellmapId").getValue();
					if(wellmap1!='' && wellmap1!=null){
						var strarr=wellmap1.split('');
						for(var i=0;i<strarr.length;i++){
							if(strarr[i]!='0'){
								if(strarr[i]!='0'){
									Ext.getCmp("f"+strarr[i]).setDisabled(true);
								}
							}
						}
					}
					flag=false;
					bu=e.currentTarget.id;
		        }, 1000);
		    }).mouseup(function(){
		    	clearTimeout(intervalTimer);
		    });
		}
	}
	function ondown2(){
		var intervalTimer = null;
		for(var j=1;j<9;j++){
			$("#ox_"+j).mousedown(function(e){
				clearTimeout(intervalTimer); //取消上次延时未执行的方法
		        intervalTimer = setTimeout(function() {
		            var face2NumberGrid = Ext.getCmp("123");

						face2NumberGrid.face2NumberWindow = new com.increase.cen.poleline.PolelineWindow({
							id : "face2NumberWindow",
							title : '选择井面相应的数字',
							iconCls : 'i-script-window',
							items : [{
								id : "face2Number",
								height : 330,
								xtype : 'face2Number'// 窗口里的表单
							}]
						});
					
					face2NumberGrid.face2NumberWindow.show();
					resetfacenumber2();
					//对已经形成的面对应的数字进行不可见设置
					if(wellmap2!='' && wellmap2!=null){
						var strarr=wellmap2.split('');
						for(var i=0;i<strarr.length;i++){
							if(strarr[i]!='0'){
								if(strarr[i]!='0'){
									Ext.getCmp("ff"+strarr[i]).setDisabled(true);
								}
							}
						}
					}
					bu2=e.currentTarget.id;
		        }, 1000);
		    }).mouseup(function(){
		    	clearTimeout(intervalTimer);
		    });
		}
	}
	//点击面按钮
	function shuzi(b){
		bu=b.id;
		var num2=Ext.getCmp(bu).getText();
 		var mapstr=bu.split('_')[1];
		if(!flag){
			if(isNaN(num2)){
				return;
			}
			Ext.Msg.show({
				title : '提示',
				width : 300,
				msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'+'请先保存立面信息!',
				buttons : {
					ok : "确定"
				}
			});
			return;
		}
		if(!isNaN(num2)){
			//变色
			//$(".faceButton_1").css("color","");
			//$(bu).css("color","blue");
			//Ext.getCmp(bu).getText().addClass('textcolor');
			Ext.getCmp("faceface").setValue(num2);
			face=Ext.getCmp("fids").getValue().split(',')[mapstr-1];
			Ext.getCmp("benfaceId").setValue(face);
			Ext.getCmp("oppoinformation").show();
			Ext.Ajax.request({
				url : 'pipe!getTubeNameListForFace.action',
				method : 'post',
				params : {
					'faceInfo.faceId' : face
				},
				success : function(response) {
					var json = Ext.util.JSON.decode(response.responseText);
					if(json.faceInfo!=null){
						var result=json.faceInfo;
						var startDeviceName="";
						if(result.oppoWellName!=null&&result.oppoWellName!=""){
			     	    	startDeviceName=result.oppoWellName+"第"+result.oppolocationNo+"面";
			     	    }else{
			     	    	startDeviceName=result.oppoWellName;
			     	    }
			     	    var startDeviceId=result.oppoWellId;
			     	    var h=result.rows;
			     	    var s=result.cols;
			     	    Ext.getCmp("rows").setValue(h);
			     	    Ext.getCmp("cols").setValue(s);
			     	    Ext.getCmp("startDeviceName").setValue(startDeviceName);
			     	    Ext.getCmp("startDeviceId").setValue(startDeviceId);
			     	    Ext.getCmp("pipeSegmentId").setValue(result.pipeSegmentId);
			     	    Ext.getCmp("oppofaceId").setValue(result.oppoFaceId);
			     	    Ext.getCmp("oppofaceface").setValue(result.oppolocationNo);
			 			tubeNameforSpl=result.tubeNames;
			     	    
			     	    //显示管孔
			     	    var row = "<table border='1' style='font-size:8px;' cellspacing='0' cellpadding='0' ><tr>";
						var erow="</tr>";
						var td_start = "<td class='tubeClass' height='20' width='40' align='center' onclick=guankong('";
						var td_end = "'); onmouseover='bianse(this);' onmouseout='bianhui(this);'>";
						var etd="</td>" ;
					 	for(var i=h;i>0;i--){
					 		for(var j=0;j<s;j++){
					 			row+=td_start;
					 			var tubeNumber = "";
					 			if((i)<10 && (j+1)<10){
					 				tubeNumber+="0"+i+"0"+(j+1);
					 			}
								 if((i)>=10 && (j+1)<10){
					 				tubeNumber+=i+"0"+(j+1);
					 			}
					 			if((i)<10 && (j+1)>=10){
					 				tubeNumber+="0"+i+(j+1);
					 			}
					 			if((i)>=10 && (j+1)>=10){
					 				tubeNumber+=""+i+(j+1);
					 			}
					 			row += (tubeNumber+td_end+tubeNumber);
					 			row+=etd;
					 		}
					 		row+=erow;
					 		row=row+"<tr>";
					 	}
					 	row=row+"</table>";
					 	Ext.getCmp("gk").body.update(row);
					 	var distubes=result.disableTubes;
					 	var arrtubes=[];
					 	if(distubes!=null&&distubes!=""){
					 		arrtubes=distubes.split(",");
					 	}
						var arr= result.tubeNames.split(",");
					 	for(var k=0;k<arr.length;k++){
					 	 	var arrdstr=arr[k];
					 		if(arrdstr!=""){
					 			$(".tubeClass:contains('"+arrdstr+"')").css("color","#0FF");
					 		}
					 	}
					 	for(var p=0;p<arrtubes.length;p++){
					 	 	var arrtube=arrtubes[p];
					 		if(arrtube!=""){
					 			$(".tubeClass:contains('"+arrtube+"')").css('background-color','white');
					 			$(".tubeClass:contains('"+arrtube+"')").css('color','white');
					 			$(".tubeClass:contains('"+arrtube+"')").removeAttr('onclick');
					 			$(".tubeClass:contains('"+arrtube+"')").removeAttr('onmouseover');
					 			$(".tubeClass:contains('"+arrtube+"')").removeAttr('onmouseout');
					 		}
					 	}
					}else{
						
					}
				}
			});
		}
	}
	//生成的管孔数字列表
	function tubes(h,s){
		var row = "<table border='1' style='font-size:8px;' cellspacing='0' cellpadding='0' ><tr>";
		var erow="</tr>";
		var td_start = "<td class='tubeClass' height='20' width='40' align='center' onclick=guankong('";
		var td_end = "'); onmouseover='bianse(this);' onmouseout='bianhui(this);'>";
		var etd="</td>" ;
		for(var i=h;i>0;i--){
			for(var j=0;j<s;j++){
				row+=td_start;
				var tubeNumber = "";
				if((i)<10 && (j+1)<10){
					 tubeNumber+="0"+i+"0"+(j+1);
				}
				if((i)>=10 && (j+1)<10){
					 tubeNumber+=i+"0"+(j+1);
				}
				if((i)<10 && (j+1)>=10){
					 tubeNumber+="0"+i+(j+1);
				}
				if((i)>=10 && (j+1)>=10){
					 tubeNumber+=""+i+(j+1);
				}
				row += (tubeNumber+td_end+tubeNumber);
				row+=etd;
			}
			row+=erow;
			row=row+"<tr>";
		}
		row=row+"</table>";
		return row;
	}
	function bianse(a){
		$(a).css('background-color','grey');
	}
	function bianhui(a){
		$(a).css('background-color','');
	}
	//添加或编辑管孔信息
 	function guankong(tubeNumber){
	 	Ext.getCmp("sonTable").body.update("");
	 	Ext.getCmp("sonNum").hide();
	 	Ext.getCmp("sonNum").setValue("0");
	 	$("#docable").html('');
 	 	$("#docable2").html('');
	 	Ext.getCmp("docabletr").hide();
	 	Ext.getCmp("tubeNumber").setValue(tubeNumber);
	 	Ext.getCmp("tubenumber").setValue(tubeNumber);//用于自动生成管孔名称
	 	Ext.Ajax.request({
			url : 'pipe!getTube.action',
			method : 'post',
			params : {
				"tubeInfoBean.faceId":face,
				"tubeInfoBean.tubeNumber":tubeNumber
			},
			success : function(response) {
				var json = Ext.util.JSON.decode(response.responseText);
				var tube = json.tubeInfoBean;
				Ext.getCmp("tube").show();
				if(tube.tubeId!=""&&tube.tubeId!=null){
					var subTubeAmount= tube.subTubeAmount;
					var isFather=tube.isFather;
					var tubeNames=tube.tubeNames;
					Ext.getCmp("tubeId").setValue(tube.tubeId);
					Ext.getCmp("tubeName").setValue(tube.tubeName);
					Ext.getCmp("isFather").setValue(tube.isFather);
					Ext.getCmp("subTubeArrangeMode").setValue(tube.subTubeArrangeMode);
					Ext.getCmp("tubeStatusEnumId").setValue(tube.tubeStatusEnumId);
					Ext.getCmp("tubeDiameter").setValue(tube.tubeDiameter);
					Ext.getCmp("tubeColor").setValue(tube.tubeColor);
					Ext.getCmp("tubeMaterial").setValue(tube.tubeMaterial);
					Ext.getCmp("tubeShape").setValue(tube.tubeShape);
					Ext.getCmp("rentFlag").setValue(tube.rentFlag);
					Ext.getCmp("rentOrg").setValue(tube.rentOrg);
					Ext.getCmp("resourceLifePeriodEnumId").setValue(tube.resourceLifePeriodEnumId);
					if(isFather=='1'){
						Ext.getCmp("sonNum").show();
						Ext.getCmp("sonNum").setValue(tube.subTubeAmount);
						Ext.getCmp("sonNum").setReadOnly(true);
			 			createt(subTubeAmount,tubeNumber);
			 		}else if(isFather=='3'){
			 			var row="";
			 			var row2="";
			 			Ext.getCmp("docabletr").show();
			 			var bearCableSegment=tube.bearCableSegment;
						var bearCableSegmentId=tube.bearCableSegmentId;
						var redunBearCableSegment=tube.redunBearCableSegment;
						var redunBearCableSegmentId=tube.redunBearCableSegmentId;
						if(bearCableSegment!='' && bearCableSegment!=null && bearCableSegmentId!='' && bearCableSegmentId!=null){
							var bstr=bearCableSegment.split(",");
					 		var bidstr=bearCableSegmentId.split(",");
			 				for(var i=0;i<bidstr.length-1;i++){
			 					row+="<tr id='trcablename"+bidstr[i]+"'><td id='cablename"+bidstr[i]+"'>"+bstr[i]+"</td><td id='cableids' style='display:none;'>"+bidstr[i]+"</td>"+"</td> <td><input id='del"+bidstr[i]+"' type='button' value='删除' onclick='clearCable(this,1);'></td></tr>";
			 				}
			 				$("#docable").append(row);
			 			}
			 			if(redunBearCableSegmentId!='' && redunBearCableSegmentId!=null && redunBearCableSegment!='' && redunBearCableSegment!=null){
							var bstr2=redunBearCableSegment.substring(0,redunBearCableSegment.lastIndexOf(',')).split(",");
			 				var bidstr2=redunBearCableSegmentId.substring(0,redunBearCableSegmentId.lastIndexOf(',')).split(",");
			 				for(var i=0;i<bidstr2.length;i++){
			 					row2+="<tr id='trcablename"+bidstr2[i]+"'><td id='cablename"+bidstr2[i]+"'>"+bstr2[i]+"</td><td id='cableids' style='display:none;'>"+bidstr2[i]+"</td>"+"</td> <td><input id='del2"+bidstr2[i]+"' type='button' value='删除' onclick='clearCable(this,2);'></td></tr>";
			 				}
			 				$("#docable2").append(row2);
			 			}
			 		}
				}else{
					Ext.getCmp("tubeId").setValue('');
					Ext.getCmp("tubeName").setValue('');
					Ext.getCmp("isFather").setValue('');
					Ext.getCmp("sonNum").setValue(1);
					Ext.getCmp("subTubeArrangeMode").setValue('');
					Ext.getCmp("tubeStatusEnumId").setValue('');
					Ext.getCmp("tubeDiameter").setValue('');
					Ext.getCmp("tubeColor").setValue('');
					Ext.getCmp("tubeMaterial").setValue('');
					Ext.getCmp("tubeShape").setValue('');
					Ext.getCmp("rentFlag").setValue('');
					Ext.getCmp("rentOrg").setValue('');
					Ext.getCmp("resourceLifePeriodEnumId").setValue('');
				}
			}
		});
		Ext.getCmp("tube1").hide();
	 }
	 //显示子孔的列表
	function createt(num,tubeNumber){
		Ext.getCmp("sonTable").body.update("");
		var arr= tubeNameforSpl.split(",");
		var row = "<table border='1' style='font-size:8px;' cellspacing='0' cellpadding='0' ><tr>";
		var td = "<td height='20' class='tubeClass' width='40' align='center' onclick=createSon('";
 		var erow="</tr>";
		var etd="</td>";
		var td_end = "'); onmouseover='bianse(this);' onmouseout='bianhui(this);'>";
		var tName=tubeNumber+"0";
		for(var i=0;i<num;i++){
			tName+=(i+1);
			row= row+td+tName+td_end+tName;
			row= row+etd;
			tName=tubeNumber+"0";
		}
		row=row+erow+"</table>";
		Ext.getCmp("sonTable").body.update(row);
		for(var k=0;k<arr.length;k++){
			var arrdstr=arr[k];
			if(arrdstr!=""){
				if(arrdstr.indexOf(tName)!=-1){
					$(".tubeClass:contains('"+arrdstr+"')").css("color","#0FF");
				}	 	
			}
		}
	}
	 //保存立面
	 function dosubmitface(){
		flag=true;
		var wellId = Ext.getCmp("wellId").getValue();
		var num = Ext.getCmp(bu).getText();
		var fx=	bu;
	 	var mapstr=fx.split('_')[1];
		var maparr=["0","0","0","0","0","0","0","0"];
	    maparr[mapstr-1]=num;
	    for(var i=0;i<8;i++){
	    	var xnum=Ext.getCmp("x_"+(i+1)).getText();
	    	if(!isNaN(xnum)){
	    		maparr[i]=xnum;
	    	}
	    }
	    var ids=Ext.getCmp("fids").getValue();
	    var wellmap= maparr.join("");
	    Ext.Ajax.request({
				url : 'pipe!updateWellMap.action',
				method : 'post',
				params : {
					'wellInfo.wellId' : wellId,
					'wellInfo.wellmap' : wellmap,
					'wellInfo.faceids' : ids
				},
				success : function(response) {
					var json = Ext.util.JSON.decode(response.responseText);
					Ext.getCmp("fids").setValue(json.faceIds);
				}
			});
	}
	//选择对端井面
	function checkcable(){
		var oppoId=Ext.getCmp("startDeviceId").getValue();
		var oppofaceId=Ext.getCmp("oppofaceId").getValue();
		var pipeSegmentId=Ext.getCmp("pipeSegmentId").getValue();
		if(oppoId!=null&&oppofaceId!=null&&pipeSegmentId!=null&&oppoId!=""&&oppofaceId!=""&&pipeSegmentId!=""){
								Ext.Ajax.request({
									url : 'pipe!checktubecable.action',
									method : 'post',
									params : {
										'pipeSegmentId' : pipeSegmentId,
										'faceId' : oppofaceId,
										'wellId' : oppoId
									},
									success : function(response) {
										var json = Ext.util.JSON.decode(response.responseText);
										if(json.success==true){
											showWellGrid();
										}else{
											Ext.Msg.show({
												title : '提示',
												width : 300,
												msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
															+ '该对端面已承载光缆，若要更换对端面，请先解除与光缆关系!',
												buttons : {
													ok : "确定"
												}
											});
										}
									}
								});
		}else{
			showWellGrid();
		}
	}
	//选择对端井时弹出选择井的列表
	function showWellGrid(){
		var wellFacadeGrid = Ext.getCmp("123");
		if (!wellFacadeGrid.wellGridWindow) {
			wellFacadeGrid.wellGridWindow = new com.increase.cen.poleline.PolelineWindow({
				id : "wellGridWindow",
				height : 400,
				width : 750,
				title : '选择井',
				iconCls : 'i-script-window',
				items : [{
					id : "wellGrid",
					height : 350,
					xtype : 'wellGrid'// 窗口里的表单
	
				}]
			});
		}
		var store = Ext.getCmp("wellGrid").getStore();
		var loadMarsk = new Ext.LoadMask(document.body, {
			msg : '数据加载中，请稍候......',
			disabled : false,
			store : store
		});
		store.addListener('beforeload', function() {
			loadMarsk.show();
		});
		store.load({
			params : {
				start : 0,
				limit : 10
			}
		});
		wellFacadeGrid.wellGridWindow.show();
	}
	//选择井
	function selectWell(rowIndex){
		var store = Ext.getCmp("wellGrid").getStore();
		var well = store.data.items[rowIndex].json;
		var wellFaceGrid = Ext.getCmp("123");
		
			wellFaceGrid.wellFaceWindow = new com.increase.cen.poleline.PolelineWindow({
				id : "wellFaceWindow",
				title : '选择井立面',
				iconCls : 'i-script-window',
				items : [{
					id : "wellFace",
					height : 410,
					xtype : 'wellFace'// 窗口里的表单
	
				}]
			});
		
		wellFaceGrid.wellFaceWindow.show();
		clearAll();
		//添加长按按钮事件
		ondown2();
		
		var wellmap = well.wellmap;	
		wellmap2=well.wellmap;
		Ext.getCmp("faceids").setValue(well.faceids);
		if(wellmap!='' && wellmap!=null){
			var strarr=wellmap.split('');
			for(var i=0;i<strarr.length;i++){
				if(strarr[i]!='0'){
					if(strarr[i]!='0'){
						var fid="ox_"+(i+1);
						Ext.getCmp(fid).setText(strarr[i]);
					}
				}
			}
		}
		Ext.Ajax.request({
			url:'pipe!facerelation.action',
			method:'post',
			params : {
				'wellInfoBean.wellId' : well.wellId
			},
			success : function(response) {
				var json = Ext.util.JSON.decode(response.responseText);
				var map1=json.wellInfoBean.hrwellmap;
				var map11=map1.split('');
				for(var i=0;i<map11.length;i++){
					if(map11[i]!='0'){
						var fids="ox_"+(i+1);
						Ext.getCmp(fids).setDisabled(true);
						//Ext.getCmp(fids).setText("<span ext:qtip='"+此立面已存在关系+"'>"+Ext.getCmp(fids).getText()+"</span>");
						//ToolTip.setToolTip(fids,'此立面已存在关系');
					}
				}
			}
		});
		/*var options = "";
		if(wellmap!=""){
			for(var i=0;i<8; i++){
				var thisMap = wellmap.charAt(i);
				if(thisMap!="0"){
					options+="<option value='"+thisMap+"'>"+thisMap+"</option>";
				}
			}
		}
		if(options.length == 0){
			options+="<option value='0'>此井无立面信息</option>";
		}else{
			options = "<option value='0'>请选择立面</option>" + options;
		}*/
		Ext.getCmp("startDeviceName").setValue(well.wellNameSub);
		Ext.getCmp("startDeviceId").setValue(well.wellId);
		Ext.getCmp("oppoids").setValue(well.faceids);
		Ext.getCmp("oppowellmap").setValue(wellmap);
		var wellFacadeGrid = Ext.getCmp("123");
		wellFacadeGrid.wellGridWindow.hide();
	}
	//清空按钮
	function washOppo(){
		Ext.getCmp("startDeviceName").setValue("");
		Ext.getCmp("startDeviceId").setValue("");
	}
	//保存对端井面
 function dosubmit(){
 	var oppoName= Ext.getCmp("startDeviceName").getValue();
 	if(oppoName==""){
 		alert("请先选择井对端面！");
 		return;
 	}
	var h=Ext.getCmp("rows").getValue();
 	var s=Ext.getCmp("cols").getValue();
	Ext.Ajax.request({
			url:'pipe!updateFace.action',
			method:'post',
			params : {
				'faceInfo.faceId' : face,
				'faceInfo.cols':s,
				'faceInfo.rows':h
			},
			success : function(response) {
				
			}
		});
	var row = tubes(h,s);
 	Ext.getCmp("gk").body.update(row);
 	//将承载缆段的管孔变色
 		var arr=tubeNameforSpl.split(",");
 		for(var k=0;k<arr.length;k++){
			var arrdstr=arr[k];
			if(arrdstr!=""){
				$(".tubeClass:contains('"+arrdstr+"')").css("color","#0FF");
			}else{
				$(".tubeClass:contains('"+arrdstr+"')").css("color","");
			}	
		}
 }
 //选择对端面时的点击操作
 	function shuzi2(b){
		bu2=b.id;
		var num2=Ext.getCmp(bu2).getText();
		if(!isNaN(num2)){
			//变色
			//$(".faceButton_2").css("color","");
			//$(bu2).css("color","blue");
 			var mapstr=bu2.split('_')[1];
 			oppoface=Ext.getCmp("oppoids").getValue().split(',')[mapstr-1];
 			Ext.getCmp("oppofaceface").setValue(num2);
 			Ext.getCmp("oppofaceId").setValue(oppoface);
 			Ext.getCmp("startDeviceName").setValue(Ext.getCmp("startDeviceName").getValue()+"第"+num2+"面");
		}
	}
	function showConfirmFace(){
		//判断管孔信息是否一致
			var startDeviceId=Ext.getCmp("wellId").getValue();
			var endDeviceId=Ext.getCmp("startDeviceId").getValue();
			var startFaceNo=Ext.getCmp("faceface").getValue();
			var endFaceNo=Ext.getCmp("oppofaceface").getValue();
			var benfaceId=Ext.getCmp("benfaceId").getValue();
			var oppofaceId=Ext.getCmp("oppofaceId").getValue();
			//验证井选择的对端面形成的管道段是否存在
			var wellName=Ext.getCmp("wellNamess").getValue();
			var startDeviceName=Ext.getCmp("startDeviceName").getValue();
			startDeviceName=startDeviceName.substring(0,startDeviceName.lastIndexOf("第"));
			var pipeSegmentName=wellName+"_"+startDeviceName;
			Ext.Ajax.request({
				url:'pipe!checkPipeSegmentNameJSON.action',
				method:'post',
				params : {
					'pipeSegmentName' : pipeSegmentName
				},
				success : function(response) {
					var json = Ext.util.JSON.decode(response.responseText);
					var wellFaceGrid = Ext.getCmp("123");
					if(json.success==true){
						if(startDeviceId!=""&&endDeviceId!=""&&startFaceNo!=""&&endFaceNo!=""){
							Ext.Ajax.request({
								url:'pipe!checkfacetube.action',
								method:'post',
								params : {
									'startDeviceId' : startDeviceId,
									'endDeviceId':endDeviceId,
									'startFaceNo':startFaceNo,
									'endFaceNo':endFaceNo
								},
								success : function(response) {
									var json = Ext.util.JSON.decode(response.responseText);
									if(json.success==true){
										$("#confirmFace").show();
										var faceConfirmGrid = Ext.getCmp("123");
										if (!faceConfirmGrid.faceConfirmWindow) {
											faceConfirmGrid.faceConfirmWindow = new com.increase.cen.poleline.PolelineWindow({
												id : "faceConfirmWindow",
												title : '选择基准面',
												iconCls : 'i-script-window',
												items : [{
													id : "faceConfirm",
													height : 410,
													xtype : 'faceConfirm'// 窗口里的表单
												}]
											});
										}
										faceConfirmGrid.faceConfirmWindow.show();
									}else{
										Ext.Ajax.request({
											url:'pipe!updateFaceRelation.action',
											method:'post',
											params : {
												'faceInfo.faceId' : benfaceId,
												'faceInfo.oppoFaceId':oppofaceId
											},
											success : function(response) {
												var json = Ext.util.JSON.decode(response.responseText);
												Ext.getCmp("pipeSegmentId").setValue(json.pipeSegmentId);
											}
										});
										wellFaceGrid.wellFaceWindow.hide();
									}
								}
							});
						}
					}else{
						Ext.getCmp("oppofaceface").setValue("");
						Ext.getCmp("oppofaceId").setValue("");
						Ext.getCmp("startDeviceName").setValue("");
 						wellFaceGrid.wellFaceWindow.hide();
						Ext.Msg.show({
							title : '提示',
							width : 300,
							msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
										+ '管道段已存在，请勿重复选择!',
							buttons : {
								ok : "确定"
							}
						});
					}
				}
			});
	}
	//已哪个面为基准面的操作
	function dosubmitOppo(numFlag){
		var benfaceId=$("#benfaceId").val();
		var oppofaceId=$("#oppofaceId").val();
		var ben="";
		var oppo="";
		if(numFlag=="1"){
			ben=benfaceId;
			oppo=oppofaceId;
		}else{
			ben=oppofaceId;
			oppo=benfaceId;
		}
		Ext.Ajax.request({
			url:"pipe!dosaveOppoface.action",
			method:'post',
			params : {
				'faceInfo.faceId' : ben,
				'faceInfo.oppoFaceId':oppo
			},
			success : function(response) {
				var json = Ext.util.JSON.decode(response.responseText);
				var result=json.faceInfo;
				Ext.getCmp("pipeSegmentId").setValue(result.pipeSegmentId);
				Ext.getCmp("rows").setValue(result.rows);
				Ext.getCmp("cols").setValue(result.cols);
				var h=result.rows;
	 			var s=result.cols;
	 			var row = tubes(h,s);
				Ext.getCmp("gk").body.update(row);
			}
		});
		hideFaceConfirm();
	}
	//关闭面信息确认窗口
	function hideFaceConfirm(){
		var faceConfirmGrid = Ext.getCmp("123");
		faceConfirmGrid.faceConfirmWindow.hide();
	}
	//将选择井面中的数据初始化
	function clearAll(){
		Ext.getCmp("ox_1").setText("北");
		Ext.getCmp("ox_2").setText("东北");
		Ext.getCmp("ox_3").setText("东");
		Ext.getCmp("ox_4").setText("东南");
		Ext.getCmp("ox_5").setText("南");
		Ext.getCmp("ox_6").setText("西南");
		Ext.getCmp("ox_7").setText("西");
		Ext.getCmp("ox_8").setText("西北");
		Ext.getCmp("ox_8").setDisabled(false);
		Ext.getCmp("ox_7").setDisabled(false);
		Ext.getCmp("ox_6").setDisabled(false);
		Ext.getCmp("ox_5").setDisabled(false);
		Ext.getCmp("ox_4").setDisabled(false);
		Ext.getCmp("ox_3").setDisabled(false);
		Ext.getCmp("ox_2").setDisabled(false);
		Ext.getCmp("ox_1").setDisabled(false);
	}
	function resetfacenumber2(){
		Ext.getCmp("ff1").setDisabled(false);
		Ext.getCmp("ff2").setDisabled(false);
		Ext.getCmp("ff3").setDisabled(false);
		Ext.getCmp("ff4").setDisabled(false);
		Ext.getCmp("ff5").setDisabled(false);
		Ext.getCmp("ff8").setDisabled(false);
		Ext.getCmp("ff6").setDisabled(false);
		Ext.getCmp("ff7").setDisabled(false);
	}
	function resetfacenumber(){
		Ext.getCmp("f1").setDisabled(false);
		Ext.getCmp("f2").setDisabled(false);
		Ext.getCmp("f3").setDisabled(false);
		Ext.getCmp("f4").setDisabled(false);
		Ext.getCmp("f5").setDisabled(false);
		Ext.getCmp("f8").setDisabled(false);
		Ext.getCmp("f6").setDisabled(false);
		Ext.getCmp("f7").setDisabled(false);
	}
	//选择管孔类型，显示不同的管孔添加页面
	function onchangeIsFather(){
 	 	var num="";
 	 	num=Ext.getCmp("isFather").getValue();
 	 	if(num=='3'){
 	 		Ext.getCmp("subTubeArrangeMode").setValue("");
 	 		Ext.getCmp("subTubeArrangeMode").setReadOnly(true);
 	 		Ext.getCmp("sonNum").hide();
 	 		Ext.getCmp("sonNum").setValue('1');
 	 		Ext.getCmp("docabletr").show();
 	 		Ext.getCmp("tubeName").setValue(Ext.getCmp("wellname1").getValue()+"-0"+
 	 			Ext.getCmp("faceface").getValue()+"面-"+Ext.getCmp("tubeNumber").getValue()+"孔");
 	 	}else if(num=='1'){
 	 		if(Ext.getCmp("tubeId").getValue()!=null&&Ext.getCmp("tubeId").getValue()!=""){
 	 			Ext.getCmp("subTubeArrangeMode").setReadOnly(false);
 	 		}
 	 		Ext.getCmp("sonNum").show();
 	 		Ext.getCmp("docabletr").hide();
 	 		$("#docable").html('');
 	 		$("#docable2").html('');
 	 		Ext.getCmp("tubeName").setValue(Ext.getCmp("wellname1").getValue()+"-0"+
 	 			Ext.getCmp("faceface").getValue()+"面-"+Ext.getCmp("tubeNumber").getValue()+"母孔");
 	 	}
 	 }
 	 //保存管孔信息
 	 function dosubmitTube(){
		var isFather=Ext.getCmp("isFather").getValue();
		if(isFather=='' || isFather==null || isFather==0){
			Ext.Msg.show({
				title : '提示',
				width : 300,
				msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'+ '请选择管孔类型!',
				buttons : {
					ok : "确定"
				}
			});
			return false;
		}
		var bearCableSegmentId="";
		var redunBearCableSegmentId="";
		var bearCableSegment="";
		var redunBearCableSegment="";
		$("#docable").find("tr").each(function(){
    		bearCableSegmentId+=$(this).children().eq(1).text()+",";
    		bearCableSegment+=$(this).children().eq(0).text()+",";	 	 
  	    });
  	  	$("#docable2").find("tr").each(function(){
    		redunBearCableSegmentId+=$(this).children().eq(1).text()+",";	 
    		redunBearCableSegment+=	 $(this).children().eq(0).text()+",";
  	    });
	 		var tubeId=Ext.getCmp("tubeId").getValue();
	 		if(tubeId==null || tubeId==''){
	 			tubeId=0;
	 		}
			var tubeName=Ext.getCmp("tubeName").getValue();
			var tubeNumber=Ext.getCmp("tubeNumber").getValue();
			var sonNum=Ext.getCmp("sonNum").getValue();
			var subTubeArrangeMode=Ext.getCmp("subTubeArrangeMode").getValue();
			var tubeStatusEnumId=Ext.getCmp("tubeStatusEnumId").getValue();
			if(tubeStatusEnumId==null || tubeStatusEnumId==''){
	 			tubeStatusEnumId=0;
	 		}
			var tubeDiameter=Ext.getCmp("tubeDiameter").getValue();
			
			var tubeColor=Ext.getCmp("tubeColor").getValue();
			if(tubeColor==null || tubeColor==''){
	 			tubeColor=0;
	 		}
			var tubeMaterial=Ext.getCmp("tubeMaterial").getValue();
			if(tubeMaterial==null || tubeMaterial==''){
	 			tubeMaterial=0;
	 		}
			var tubeShape=Ext.getCmp("tubeShape").getValue();
			if(tubeShape==null || tubeShape==''){
	 			tubeShape=0;
	 		}
			var rentFlag=Ext.getCmp("rentFlag").getValue();
			if(rentFlag==null || rentFlag==''){
	 			rentFlag=0;
	 		}
			var rentOrg=$("#rentOrg").val();
			var resourceLifePeriodEnumId=Ext.getCmp("resourceLifePeriodEnumId").getValue();
			if(resourceLifePeriodEnumId==null || resourceLifePeriodEnumId==''){
	 			resourceLifePeriodEnumId=0;
	 		}
	 		var pipeSegmentId=Ext.getCmp("pipeSegmentId").getValue();
	 		var wellId=Ext.getCmp("wellId").getValue();
	 		Ext.Ajax.request({
				url:"pipe!saveTube.action",
				method:'post',
				params : {
					'tubeInfoBean.tubeName' : tubeName,
					'tubeInfoBean.tubeNumber':tubeNumber,
					'tubeInfoBean.isFather' : isFather,
					'tubeInfoBean.faceId':face,
					'tubeInfoBean.subTubeAmount' : sonNum,
					'tubeInfoBean.subTubeArrangeMode':subTubeArrangeMode,
					'tubeInfoBean.tubeStatusEnumId' : tubeStatusEnumId,
					'tubeInfoBean.tubeDiameter':tubeDiameter,
					'tubeInfoBean.tubeColor' : tubeColor,
					'tubeInfoBean.tubeMaterial':tubeMaterial,
					'tubeInfoBean.tubeShape' : tubeShape,
					'tubeInfoBean.rentFlag':rentFlag,
					'tubeInfoBean.rentOrg' : rentOrg,
					'tubeInfoBean.resourceLifePeriodEnumId':resourceLifePeriodEnumId,
					'tubeInfoBean.tubeId' : tubeId,
					'tubeInfoBean.bearCableSegmentId':bearCableSegmentId,
					'tubeInfoBean.redunBearCableSegmentId' : redunBearCableSegmentId,
					'tubeInfoBean.bearCableSegment':bearCableSegment,
					'tubeInfoBean.redunBearCableSegment':redunBearCableSegment,
					'tubeInfoBean.pipeSegmentId' : pipeSegmentId,
					'tubeInfoBean.wellId':wellId
				},
				success : function(response) {
					var json = Ext.util.JSON.decode(response.responseText);
					if(bearCableSegmentId!=""){
						$(".tubeClass:contains('"+tubeNumber+"')").css("color","#0FF");	
					}else{
						$(".tubeClass:contains('"+tubeNumber+"')").css("color","");	
					}
					tubeNameforSpl+=tubeNumber+",";
				}
			});
			Canceldo();
	}
	//管孔中的取消按钮
	function Canceldo(){
 			Ext.getCmp("tubeName").setValue('');
			Ext.getCmp("tubeNumber").setValue('');
			Ext.getCmp("isFather").setValue('');
			Ext.getCmp("sonNum").setValue(1);
			Ext.getCmp("subTubeArrangeMode").setValue('');
			Ext.getCmp("tubeStatusEnumId").setValue('');
			Ext.getCmp("tubeDiameter").setValue('');
			Ext.getCmp("tubeColor").setValue('');
			Ext.getCmp("tubeMaterial").setValue('');
			Ext.getCmp("tubeShape").setValue('');
			Ext.getCmp("rentFlag").setValue('');
			Ext.getCmp("rentOrg").setValue('');
			Ext.getCmp("resourceLifePeriodEnumId").setValue('');
			$("#docable").html('');
 	 		$("#docable2").html(''); 
			Ext.getCmp("sonTable").body.update("");
 	 		Ext.getCmp("tube").hide();
 	}
 	//保存子孔信息
 	function dosubmitSonTube(){
		var bearCableSegmentId="";
		var redunBearCableSegmentId="";
		var bearCableSegment="";
		var redunBearCableSegment="";
		$("#doSoncable").find("tr").each(function(){
    		bearCableSegmentId+=$(this).children().eq(1).text()+",";	
    		bearCableSegment+=$(this).children().eq(0).text()+",";	 	 
  	  	});
  	  	$("#doSoncable2").find("tr").each(function(){
    		redunBearCableSegmentId+=$(this).children().eq(1).text()+",";	 
    		redunBearCableSegment+=	 $(this).children().eq(0).text()+",";			 
  	 	});
  	  		var tubeId=Ext.getCmp("tubeId2").getValue();
	 		if(tubeId==null || tubeId==''){
	 			tubeId=0;
	 		} 
			var tubeName=Ext.getCmp("tubeName2").getValue();
			var tubeNumber=Ext.getCmp("tubeNumber2").getValue();
			var tubeStatusEnumId=Ext.getCmp("tubeStatusEnumId2").getValue();
			if(tubeStatusEnumId==null || tubeStatusEnumId==''){
	 			tubeStatusEnumId=0;
	 		}
			var tubeDiameter=Ext.getCmp("tubeDiameter2").getValue();
			var tubeColor=Ext.getCmp("tubeColor2").getValue();
			if(tubeColor==null || tubeColor==''){
	 			tubeColor=0;
	 		}
			var tubeMaterial=Ext.getCmp("tubeMaterial2").getValue();
			if(tubeMaterial==null || tubeMaterial==''){
	 			tubeMaterial=0;
	 		}
			var tubeShape=Ext.getCmp("tubeShape2").getValue();
			if(tubeShape==null || tubeShape==''){
	 			tubeShape=0;
	 		}
			var rentFlag=Ext.getCmp("rentFlag2").getValue();
			if(rentFlag==null || rentFlag==''){
	 			rentFlag=0;
	 		}
			var rentOrg=Ext.getCmp("rentOrg2").getValue();
			var resourceLifePeriodEnumId=Ext.getCmp("resourceLifePeriodEnumId2").getValue();
			if(resourceLifePeriodEnumId==null || resourceLifePeriodEnumId==''){
	 			resourceLifePeriodEnumId=0;
	 		}
			var fatherPoreId=Ext.getCmp("fatherPoreId").getValue();
			var pipeSegmentId=Ext.getCmp("pipeSegmentId").getValue();
	 		var wellIdzi=Ext.getCmp("wellIdzi").getValue();
	 		Ext.Ajax.request({
				url:"pipe!saveTube.action",
				method:'post',
				params : {
					'tubeInfoBean.tubeName' : tubeName,
					'tubeInfoBean.tubeNumber':tubeNumber,
					'tubeInfoBean.isFather' : 2,
					'tubeInfoBean.faceId':face,
					'tubeInfoBean.tubeStatusEnumId' : tubeStatusEnumId,
					'tubeInfoBean.tubeDiameter':tubeDiameter,
					'tubeInfoBean.tubeColor' : tubeColor,
					'tubeInfoBean.tubeMaterial':tubeMaterial,
					'tubeInfoBean.tubeShape' : tubeShape,
					'tubeInfoBean.rentFlag':rentFlag,
					'tubeInfoBean.rentOrg' : rentOrg,
					'tubeInfoBean.resourceLifePeriodEnumId':resourceLifePeriodEnumId,
					'tubeInfoBean.tubeId' : tubeId,
					'tubeInfoBean.bearCableSegmentId':bearCableSegmentId,
					'tubeInfoBean.redunBearCableSegmentId' : redunBearCableSegmentId,
					'tubeInfoBean.bearCableSegment':bearCableSegment,
					'tubeInfoBean.redunBearCableSegment':redunBearCableSegment,
					'tubeInfoBean.pipeSegmentId' : pipeSegmentId,
					'tubeInfoBean.wellId':wellIdzi,
					'tubeInfoBean.fatherPoreId':fatherPoreId
				},
				success : function(response) {
					var json = Ext.util.JSON.decode(response.responseText);
					if(bearCableSegmentId!=""){
						$(".tubeClass:contains('"+tubeNumber+"')").css("color","#0FF");	
					}else{
						$(".tubeClass:contains('"+tubeNumber+"')").css("color","");	
					}
					tubeNameforSpl+=tubeNumber+",";
				}
			});
			Ext.getCmp("tubeName2").setValue('');
			Ext.getCmp("tubeNumber2").setValue('');
			Ext.getCmp("tubeStatusEnumId2").setValue('');
			Ext.getCmp("tubeDiameter2").setValue('');
			Ext.getCmp("tubeColor2").setValue('');
			Ext.getCmp("tubeMaterial2").setValue('');
			Ext.getCmp("tubeShape2").setValue('');
			Ext.getCmp("rentFlag2").setValue('');
			Ext.getCmp("rentOrg2").setValue('');
			Ext.getCmp("resourceLifePeriodEnumId2").setValue('');
			$("#doSoncable").html('');
 	 		$("#doSoncable2").html(''); 
			Ext.getCmp("tube1").hide();
			
	}
	function Canceldo2(){
			Ext.getCmp("tubeId2").setValue('');
			Ext.getCmp("tubeName2").setValue('');
			Ext.getCmp("tubeNumber2").setValue('');
			Ext.getCmp("tubeStatusEnumId2").setValue('');
			Ext.getCmp("tubeDiameter2").setValue('');
			Ext.getCmp("tubeColor2").setValue('');
			Ext.getCmp("tubeMaterial2").setValue('');
			Ext.getCmp("tubeShape2").setValue('');
			Ext.getCmp("rentFlag2").setValue('');
			Ext.getCmp("rentOrg2").setValue('');
			Ext.getCmp("resourceLifePeriodEnumId2").setValue('');
			$("#doSoncable").html('');
 	 		$("#doSoncable2").html('');
 	 		Ext.getCmp("sonTable").body.update("");
 	 		Ext.getCmp("tube1").hide();
	}
	//进入子孔添加页面
	function createSon(tubeNumber){
		var fatherPoreId=Ext.getCmp("tubeId").getValue();
		Ext.getCmp("tubeId2").setValue('');
		Ext.getCmp("tubeName2").setValue('');
		Ext.getCmp("tubeNumber2").setValue(tubeNumber);
		Ext.getCmp("tubeStatusEnumId2").setValue('');
		Ext.getCmp("tubeDiameter2").setValue('');
		Ext.getCmp("tubeColor2").setValue('');
		Ext.getCmp("tubeMaterial2").setValue('');
		Ext.getCmp("tubeShape2").setValue('');
		Ext.getCmp("rentFlag2").setValue('');
		Ext.getCmp("rentOrg2").setValue('');
		Ext.getCmp("resourceLifePeriodEnumId2").setValue('');
		$("#doSoncable").html('');
	 	$("#doSoncable2").html(''); 
	 	Ext.Ajax.request({
			url : 'pipe!getTube.action',
			method : 'post',
			params : {
				"tubeInfoBean.faceId":face,
				"tubeInfoBean.tubeNumber":tubeNumber
			},
			success : function(response) {
				var json = Ext.util.JSON.decode(response.responseText);
				var tube = json.tubeInfoBean;
				Ext.getCmp("tube").hide();
				Ext.getCmp("tube1").show();
				if(tube.tubeId!=""&&tube.tubeId!=null){
					Ext.getCmp("tubeId2").setValue(tube.tubeId);
					Ext.getCmp("tubeName2").setValue(tube.tubeName);
					Ext.getCmp("tubeStatusEnumId2").setValue(tube.tubeStatusEnumId);
					Ext.getCmp("tubeDiameter2").setValue(tube.tubeDiameter);
					Ext.getCmp("tubeColor2").setValue(tube.tubeColor);
					Ext.getCmp("tubeMaterial2").setValue(tube.tubeMaterial);
					Ext.getCmp("tubeShape2").setValue(tube.tubeShape);
					Ext.getCmp("rentFlag2").setValue(tube.rentFlag);
					Ext.getCmp("rentOrg2").setValue(tube.rentOrg);
					Ext.getCmp("resourceLifePeriodEnumId2").setValue(tube.resourceLifePeriodEnumId);
					Ext.getCmp("fatherPoreId").setValue(fatherPoreId);
					var row="";
			 		var row2="";
			 		var bearCableSegment=tube.bearCableSegment;
					var bearCableSegmentId=tube.bearCableSegmentId;
					var redunBearCableSegment=tube.redunBearCableSegment;
					var redunBearCableSegmentId=tube.redunBearCableSegmentId;
					if(bearCableSegment!='' && bearCableSegment!=null && bearCableSegmentId!='' && bearCableSegmentId!=null){
			    		var bstr=bearCableSegment.split(",");
			 			var bidstr=bearCableSegmentId.split(",");
				 		for(var i=0;i<bidstr.length-1;i++){
				 			row+="<tr id='trcablename"+bidstr[i]+"'><td id='cablename"+bidstr[i]+"'>"+bstr[i]+"</td><td id='cableids' style='display:none;'>"+bidstr[i]+"</td>"+"</td> <td><input id='del"+bidstr[i]+"' type='button' value='删除' onclick='clearCable(this,1);'></td></tr>";
				 		}
			 			$("#doSoncable").append(row);
			 		}
			 		if(redunBearCableSegmentId!='' && redunBearCableSegmentId!=null && redunBearCableSegment!='' && redunBearCableSegment!=null){
						var bstr2=redunBearCableSegment.substring(0,redunBearCableSegment.lastIndexOf(',')).split(",");
			 			var bidstr2=redunBearCableSegmentId.substring(0,redunBearCableSegmentId.lastIndexOf(',')).split(",");
			 			for(var i=0;i<bidstr2.length;i++){
			 				row2+="<tr id='trcablename"+bidstr2[i]+"'><td id='cablename"+bidstr2[i]+"'>"+bstr2[i]+"</td><td id='cableids' style='display:none;'>"+bidstr2[i]+"</td>"+"</td> <td><input id='del2"+bidstr2[i]+"' type='button' value='删除' onclick='clearCable(this,2);'></td></tr>";
			 			}
			 			$("#doSoncable2").append(row2);
			 		}
				}else{
					var sub=tubeNumber.substring(tubeNumber.length-1,tubeNumber);
					Ext.getCmp("tubeName2").setValue(Ext.getCmp("wellname1").getValue()+"-0"+
						Ext.getCmp("faceface").getValue()+"面-"+Ext.getCmp("tubenumber").getValue()+"母孔-0"+sub+"孔");
					Ext.getCmp("fatherPoreId").setValue(fatherPoreId);
				}
			}
		});	
	}
	function addCables(num){
		var cableGrid = Ext.getCmp("123");
		if (!cableGrid.cableWindow) {
			cableGrid.cableWindow = new com.increase.cen.poleline.PolelineWindow({
				id : "cableWindow",
				title : '选择光缆段',
				width : 670,
				iconCls : 'i-script-window',
				items : [{
					id : "cableGrid",
					height : 360,
					xtype : 'cableGrid'// 窗口里的表单
				}]
			});
		}
		cableGrid.cableWindow.show();
		setTimeout(function(){
			$("#docable").find("tr").each(function(){
	    		var bearCableSegmentIdFordis=$(this).children().eq(1).text();
	    		$("#selectCables"+bearCableSegmentIdFordis).attr('disabled','true');
	    		 	 
	  	 	 });
	    	 $("#docable2").find("tr").each(function(){
	    		var redunBearCableSegmentIdFordis=$(this).children().eq(1).text();	 
	    		$("#selectCables"+redunBearCableSegmentIdFordis).attr('disabled','true');
	  		  });
		},100);
		addcableno=num;
	}
	function addSonCables(num){
		var cableGrid = Ext.getCmp("123");
		if (!cableGrid.cable2Window) {
			cableGrid.cable2Window = new com.increase.cen.poleline.PolelineWindow({
				id : "cable2Window",
				title : '选择光缆段',
				width : 670,
				iconCls : 'i-script-window',
				items : [{
					id : "cable2Grid",
					height : 360,
					xtype : 'cable2Grid'// 窗口里的表单
				}]
			});
		}
		cableGrid.cable2Window.show();
		setTimeout(function(){
			$("#doSoncable").find("tr").each(function(){
	    		var bearCableSegmentIdFordis=$(this).children().eq(1).text();
	    		$("#select2Cables"+bearCableSegmentIdFordis).attr('disabled','true');
	  	 	});
	    	$("#doSoncable2").find("tr").each(function(){
	    		var redunBearCableSegmentIdFordis=$(this).children().eq(1).text();	 
	    		$("#select2Cables"+redunBearCableSegmentIdFordis).attr('disabled','true');
	  		});
		},100);
		addcableno=num;
	}
	function selectCable(rowIndex){
		var store = Ext.getCmp("cableGrid").getStore();
		var cable = store.data.items[rowIndex].json;
		var cablename=cable.cablename;
		var ids=cable.cableid;
		if(addcableno==1){
			var row="<tr id='trcablename"+ids+"'><td id='cablename"+ids+"'>"+cablename+"</td><td id='cableids' style='display:none;'>"+ids+"</td>"+"</td> <td><input id='del"+ids+"' type='button' value='删除' onclick='clearCable(this,1);'></td></tr>";
			$("#docable").append(row);
		}
		if(addcableno==2){
			var row="<tr id='trcablename2"+ids+"'><td id='cablename"+ids+"'>"+cablename+"</td><td id='cableids' style='display:none;'>"+ids+"</td>"+"</td> <td><input id='del2"+ids+"' type='button' value='删除' onclick='clearCable(this,2);'></td></tr>";
			$("#docable2").append(row);
		}
		var cableGrid = Ext.getCmp("123");
		cableGrid.cableWindow.hide();
	}
	function selectCable2(rowIndex){
		var store = Ext.getCmp("cable2Grid").getStore();
		var cable = store.data.items[rowIndex].json;
		var cablename=cable.cablename;
		var ids=cable.cableid;
		if(addcableno==1){
			var row="<tr id='trcablename"+ids+"'><td id='cablename"+ids+"'>"+cablename+"</td><td id='cableids' style='display:none;'>"+ids+"</td>"+"</td> <td><input id='del"+ids+"' type='button' value='删除' onclick='clearCable(this,1);'></td></tr>";
			$("#doSoncable").append(row);
		}
		if(addcableno==2){
			var row="<tr id='trcablename2"+ids+"'><td id='cablename"+ids+"'>"+cablename+"</td><td id='cableids' style='display:none;'>"+ids+"</td>"+"</td> <td><input id='del2"+ids+"' type='button' value='删除' onclick='clearCable(this,2);'></td></tr>";
			$("#doSoncable2").append(row);
		}
		var cableGrid = Ext.getCmp("123");
		cableGrid.cable2Window.hide();
	}
	function clearCable(b,num){
		var id="";
		var id1="";
		if(num==1){
			id=b.id.substring(3,b.id.length);
			id1="#trcablename"+id;
		}else{
			id=b.id.substring(4,b.id.length);
			id1="#trcablename2"+id;
		}
		$(id1).remove();
	}
	function showDisTube(){
	  	var oppoName= Ext.getCmp("startDeviceName").getValue();
	 	if(oppoName==""){
	 		alert("请先选择井对端面！");
	 		return;
	 	}
	 	var distubeGrid = Ext.getCmp("123");
		if (!distubeGrid.distubeWindow) {
			distubeGrid.distubeWindow = new com.increase.cen.poleline.PolelineWindow({
				id : "distubeWindow",
				title : '请选择不可见的管孔',
				iconCls : 'i-script-window',
				items : [{
					id : "distubeGrid",
					xtype : 'distubeGrid'// 窗口里的表单
				}]
			});
		}
		distubeGrid.distubeWindow.show();
	   	tubeSelect();
	  }
	function tubeSelect(){
	  	var wellId=Ext.getCmp("wellId").getValue();
		var faceface=Ext.getCmp("faceface").getValue();
		var h=0;
		var s=0;
		Ext.Ajax.request({
			url : 'pipe!getface.action',
			method : 'post',
			params : {
				"faceInfo.wellId":wellId,
				"faceInfo.locationNo":faceface
			},
			success : function(response) {
				var json = Ext.util.JSON.decode(response.responseText);
				var result=json.faceInfo;
				h=result.rows;
	     	   	s=result.cols;
	     	   	var row = "<tr>";
	     	   	var erow="</tr>";
				var button="<td colspan='"+s+"'>"+"<input type='button' value='保存' onclick='submitDisTube();'/>"+"</td>";
				var str="";
				for(var i=h;i>0;i--){
				 	for(var j=0;j<s;j++){
				 		str="";
				 		var tubeNumber = "";
				 		if((i)<10 && (j+1)<10){
				 			tubeNumber+="0"+i+"0"+(j+1);
				 		}
						if((i)>=10 && (j+1)<10){
				 			tubeNumber+=i+"0"+(j+1);
				 		}
				 		if((i)<10 && (j+1)>=10){
				 			tubeNumber+="0"+i+(j+1);
				 		}
				 		if((i)>=10 && (j+1)>=10){
				 			tubeNumber+=""+i+(j+1);
				 		}
				 		str+="<td align='center'>"+"<input type='checkbox' name='chkItemDisTube' value='"+tubeNumber+"' />"+tubeNumber+"</td>";
				 		row += str;
				 	}
				 	row+=erow;
				}
				$("#tubeselectlist").html(row); 
				$("[name='chkItemDisTube']").attr("checked",'true');
				var distubes=result.disableTubes;
				var arrtubes=[];
				if(distubes!=null&&distubes!=""){
					arrtubes=distubes.split(",");
				}
	     	    for(var p=0;p<arrtubes.length;p++){
				 	var arrtube=arrtubes[p];
				 	if(arrtube!=""){
				 		$("[name='chkItemDisTube']").each(function(){
							if($(this).val()==arrtube) {
								this.checked=false;
							}
    					});
				 	}
				}
			}
		});		
				 /*
				 	if(s>5){
				 		var width=48*s;
				 	}else{
				 		var width=48*5;
				 	}
				 	$("#tableim").attr('width',width);
	     	    });*/
	  }
	  //为当前井选择井面
	function fuzhi(a){
		var num=Ext.getCmp(a.id).getText();
		var num2=Ext.getCmp(bu).getText();
		Ext.getCmp(bu).setText(num);
		if(!isNaN(num2)){
			Ext.getCmp("f"+num2).setDisabled(false);
		}
		hideFaceNumber();
		Ext.getCmp(a.id).setDisabled(true);
	}
	function hideFaceNumber(){
		var faceNumberGrid = Ext.getCmp("123");
		faceNumberGrid.faceNumberWindow.hide();
	}
	function hideFaceNumber2(){
		var face2NumberGrid = Ext.getCmp("123");
		face2NumberGrid.face2NumberWindow.hide();
	}
	//清除按钮
	function qingchu(){
		var num=Ext.getCmp(bu).getText();
		var fx=	bu;
		if(!isNaN(num)){
			Ext.getCmp("f"+num).setDisabled(false);
			if('x_8'==fx){
				Ext.getCmp(bu).setText("西北");
			}
			if('x_1'==fx){
				Ext.getCmp(bu).setText("北");
			}
			if('x_2'==fx){
				Ext.getCmp(bu).setText("东北");
			}
			if('x_7'==fx){
				Ext.getCmp(bu).setText("西");
			}
			if('x_3'==fx){
				Ext.getCmp(bu).setText("东");
			}
			if('x_6'==fx){
				Ext.getCmp(bu).setText("西南");
			}
			if('x_5'==fx){
				Ext.getCmp(bu).setText("南");
			}
			if('x_4'==fx){
				Ext.getCmp(bu).setText("东南");
			}
			hideFaceNumber();
			Ext.getCmp("oppoinformation").hide();
			washOppo();
			Ext.getCmp("gk").body.update('');
		}	
	}
	function qingchu2(){
		var num=Ext.getCmp(bu2).getText();
		var fx=	bu2;
		if(!isNaN(num)){
			Ext.getCmp("ff"+num).setDisabled(false);
			if('ox_8'==fx){
				Ext.getCmp(bu2).setText("西北");
			}
			if('ox_1'==fx){
				Ext.getCmp(bu2).setText("北");
			}
			if('ox_2'==fx){
				Ext.getCmp(bu2).setText("东北");
			}
			if('ox_7'==fx){
				Ext.getCmp(bu2).setText("西");
			}
			if('ox_3'==fx){
				Ext.getCmp(bu2).setText("东");
			}
			if('ox_6'==fx){
				Ext.getCmp(bu2).setText("西南");
			}
			if('ox_5'==fx){
				Ext.getCmp(bu2).setText("南");
			}
			if('ox_4'==fx){
				Ext.getCmp(bu2).setText("东南");
			}
			hideFaceNumber2();
		}	
	}
	function washOppo(){
		Ext.getCmp("startDeviceName").setValue("");
		Ext.getCmp("startDeviceId").setValue("");
		Ext.getCmp("rows").setValue(1);
		Ext.getCmp("cols").setValue(1);
	}
	function fuzhi2(a){
		var num=Ext.getCmp(a.id).getText();
		var num2=Ext.getCmp(bu2).getText();
		Ext.getCmp(bu2).setText(num);
		if(isNaN(num2)){	
			//变色
			//$(".faceButton_2").css("color","");
			//$(bu2).css("color","blue");
			var faceids = Ext.getCmp("oppoids").getValue();
			var wellIds = Ext.getCmp("startDeviceId").getValue();
			var maparr=["0","0","0","0","0","0","0","0"];
	    	for(var i=0;i<8;i++){
	    		var xnum = Ext.getCmp("ox_"+(i+1)).getText();
	    		if(!isNaN(xnum)){
	    			maparr[i]=xnum;
	    		}
	    	}
	    	var wellmap= maparr.join("");
	    	Ext.Ajax.request({
				url : 'pipe!updateWellMap.action',
				method : 'post',
				params : {
					'wellInfo.wellId' : wellIds,
					'wellInfo.wellmap' : wellmap,
					'wellInfo.faceids' : faceids
				},
				success : function(response) {
					var json = Ext.util.JSON.decode(response.responseText);
					Ext.getCmp("oppoids").setValue(json.faceIds);
					var mapstr=bu2.split('_')[1];
					oppoface=Ext.getCmp("oppoids").getValue().split(',')[mapstr-1];
				}
			});
		}else{
			Ext.getCmp("ff"+num2).setDisabled(false);
		}
		hideFaceNumber2();
		Ext.getCmp(a.id).setDisabled(true);
	}