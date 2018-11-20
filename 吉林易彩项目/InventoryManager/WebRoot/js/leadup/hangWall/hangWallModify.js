function modifyHangWall(obj) {
	var hangWallName = itemText(.5,80,'hangWallName','挂墙段名称',obj.hangWallName,null,'u');
	var maintArea = itemText(.5,80,'maintArea','维护区县',obj.maintArea,null,'u');
	var direction = itemText(.5,80,'direction','维护方向',obj.direction,null,'u');
	var purpose = itemText(.5,80,'purpose','用途',obj.purpose,null,'u');
	var username = itemText(.5,80,'username','使用单位',obj.username,null,'u');
	var hangLength = itemText(.5,80,'hangLength','挂墙长度',obj.hangLength,null,'u');
	var maintLength = itemText(.5,80,'maintLength','维护长度',obj.maintLength,null,'u');
	var startDeviceId = itemText(.5,80,'startDeviceId','起始撑点id',obj.startDeviceId,null,'u');
	var startDeviceName = itemText(.5,80,'startDeviceName','起始撑点名称',obj.startDeviceName,null,'u');
	var endDeviceId = itemText(.5,80,'endDeviceId','终止撑点id',obj.endDeviceId,null,'u');
	var endDeviceName = itemText(.5,80,'endDeviceName','终止撑点名称',obj.endDeviceName,null,'u');
	var maintainer = itemText(.5,80,'maintainer','一线维护人',obj.maintainer,null,'u');
	
	var detailPanel =new Ext.form.FormPanel({
	    layout : "column",
	    id:'detailPanel',
		border : false,
		width:'100%',
		autoHeight:true,
		frame:true,
		fileUpload : true,
		items:[
		      hangWallName,
		      {
					xtype : "panel",
					layout : "form",
					columnWidth :.5,
					labelWidth :80,
					border : false,
					items : [{
						xtype:'combo',
						id:"maintMode",
						autoSelect :true,
						fieldLabel : "维护方式",
						autoLoad :false,
						emptyText:'请选择',
						value:obj.maintMode,
						store:new Ext.data.Store({
							proxy: new Ext.data.HttpProxy({url:context_path+'/dictAction!getDicValues.action?type=buriedModel'}),
							reader: new Ext.data.JsonReader({},[
								{name:'name'} ,
								{name:'id'}
							])
						}),
						valueField:'id',
						displayField:'name',
						typeAhead: true,
						mode: 'remote',
						triggerAction: 'all',
						listeners:{
							change:function (t,newValue,oldValue)
							{
								Ext.getCmp('maintMode').setValue(newValue);
							}
						}
					}]
			    },
		maintArea, direction, purpose,  username,hangLength,maintLength,startDeviceId,startDeviceName,endDeviceId,
		endDeviceName,maintainer
		]
	});
	
	var detailWin = new Ext.Window({
		title:'修改撑点',
		closeAction : "destroy",
      	width:600,
      	height:250,
      	autoScroll:true,
      	modal:true,
      	buttonAlign:"center",
      	items:[detailPanel],
      	buttons:[{
      		text:"提交",
      		handler:function(){
      			detailPanel.form.submit({
      				url : context_path+'/leadupAction!updateHangWall.action?id='+obj.id,
            		waitMsg : '正在提交数据，请稍等...',
            		success : function() {
            			Ext.Msg.show({
    						title : "提示",
    						msg : "修改成功！",
    						width :150,
    						buttons: Ext.MessageBox.OK,
    						icon: Ext.MessageBox.INFO
    					});
            			reload();
            			detailWin.destroy();
            		},
            		failure : function() {
            			Ext.Msg.show({
    						title : "提示",
    						msg : "修改失败！",
    						width :150,
    						buttons: Ext.MessageBox.OK,
    						icon: Ext.MessageBox.INFO
    					});
            		}
      			});
      		}
      	},{
      		text:"关闭",
      		handler:function(){
      			detailWin.destroy();
      		}
      	}]
	});
	detailWin.show();
}