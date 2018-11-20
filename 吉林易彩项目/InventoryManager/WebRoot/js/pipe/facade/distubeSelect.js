Ext.namespace("com.increase.cen.poleline");
com.increase.cen.poleline.DistubeGrid = Ext.extend(Ext.form.FormPanel, {
	bodyStyle : "padding:5px;",
	frame : true,
	bodyBorder : false,
	labelAlign : "right",
	buttonAlign : 'center',
	labelWidth : 120,
	width : 950,
	height : 450,
	autoScroll : true,
	initComponent : function(config) {
		// 启动悬停提示
		Ext.QuickTips.init();
		// 提示的方式
		Ext.form.Field.prototype.msgTarget = 'qtip';
		// 用光缆信息是否存在
		var idIsExist = true;
		var faceno=new Ext.form.TextField({
			id : 'faceno',
			name : "faceno",
			fieldLabel : "面",
			hidden : true
		});
		var faceids=new Ext.form.TextField({
			id : 'faceids',
			name : "faceids",
			fieldLabel : "对端面ID",
			hidden : true
		});
		var form1 = {
			layout : 'column', // 从左往右布局
			style:'margin:0 0 0 25',
			items : [{
				html:'<table id="tubeselectlist" style="BACKGROUND-COLOR: white; BORDER-COLLAPSE: collapse; FONT-SIZE: 13px;  '+
				'" class="list" name="table" id="table" border="1" rules="all" cellSpacing="0" '+
				'cellPadding="0" width="100%" bordercolor="#CACACA">'+
				'</table>'
			}]
		};
		this.items = [{
			xtype : 'fieldset',
			title : '选择不可见管孔',
			collapsible : true,
			items : [form1]
		}];

		// 按钮
		this.buttons = [{
			id : 'saveDisSubmit',
			text : "保存",
			//tooltip : '保存井面信息',
			tooltipType : 'qtip',
			handler : this.saveDisSubmit.createDelegate(this)
		}];
		// 重写initComponent superclass.initComponent.call(this);来调用父类的初始化方法.
		com.increase.cen.poleline.DistubeGrid.superclass.initComponent.call(this);
	},
	// 保存按钮事件
	saveDisSubmit : function() {
		//submitDisTube();
		var wellId=Ext.getCmp("wellId").getValue();
    	var benfaceid=Ext.getCmp("benfaceId").getValue();
    	var startDeviceId=Ext.getCmp("startDeviceId").getValue();
    	var oppofaceface=Ext.getCmp("oppofaceface").getValue();
    	var faceface=Ext.getCmp("faceface").getValue();
    	var tubeno="";
    	var tubenos="";
   		 $("[name='chkItemDisTube']").each(function(){
    		if(this.checked==false){
				tubeno=$(this).val();
				tubenos+=(tubeno+",");
	 		} 
    	})
    	Ext.Ajax.request({
    		url:'pipe!updateFaceDisTube.action',
    		method:'post',
    		params:{
    			'faceInfo.wellId':wellId,
    			'faceInfo.locationNo':faceface,
    			'faceInfo.disableTubes':tubenos
    		},
    		success:function(response){
    			var json = Ext.util.JSON.decode(response.responseText);
    			if(json.success==true){
    				Ext.Ajax.request({
						url : 'pipe!getTubeNameListForFace.action',
						method : 'post',
						params : {
							'faceInfo.faceId' : benfaceid
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
					     	    var row = "<table border='1' style='font-size:8px;' cellspacing='0' cellpadding='0' ><tr>"
								var erow="</tr>";
								var td_start = "<td class='tubeClass' height='20' width='40' align='center' onclick=guankong('";
								var td_end = "'); onmouseover='bianse(this);' onmouseout='bianhui(this);'>"
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
							}
						}
					});
    			}
    		}
    	});
    	var distubeGrid = Ext.getCmp("123");
    	distubeGrid.distubeWindow.hide();
	}
});
Ext.reg("distubeGrid", com.increase.cen.poleline.DistubeGrid);
