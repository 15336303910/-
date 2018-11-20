function addHangWall() {
	var hangWallName = itemText(.5,80,'hangWallName','挂墙段名称');
	var maintMode = commCombo("/dictAction!getDicValues.action?type=buriedModel",.5,80,'maintMode','维护方式','remote','id','name',null);
	var maintArea = itemText(.5,80,'maintArea','维护区县');
	var direction = itemText(.5,80,'direction','维护方向');
	var purpose = itemText(.5,80,'purpose','用途');
	var username = itemText(.5,80,'username','使用单位');
	var hangLength = itemText(.5,80,'hangLength','挂墙长度');
	var maintLength = itemText(.5,80,'maintLength','维护长度');
	var startDeviceId = itemText(.5,80,'startDeviceId','起始撑点id');
	var startDeviceName = itemText(.5,80,'startDeviceName','起始撑点名称');
	var endDeviceId = itemText(.5,80,'endDeviceId','终止撑点id');
	var endDeviceName = itemText(.5,80,'endDeviceName','终止撑点名称');
	var maintainer = itemText(.5,80,'maintainer','一线维护人');
	
	/**
	 * 流程表单
	 */
	var addPanel =new Ext.form.FormPanel({
	    layout : "column",
	    id:'addPanel',
		border : false,
		width:'100%',
		autoHeight:true,
		frame:true,
		fileUpload : true,
		items:[
		       hangWallName,maintMode,maintArea,direction,purpose,username,hangLength,
		       maintLength,startDeviceId,startDeviceName,endDeviceId,endDeviceName,maintainer
		]
	});
	
	var addWin = new Ext.Window({
		id:'addWin',
		title:'发起流程',
      	width:600,
      	closable:false,
      	height:250,
      	modal:true,
      	buttonAlign:"center",
      	items:addPanel,
      	buttons:[{
      		text:"提交",
      		handler:function(){
      			addPanel.form.submit({
      				url : context_path+'/leadupAction!saveHangWallPojo.action',
            		waitMsg : '正在提交数据，请稍等...',
            		success : function() {
            			Ext.Msg.show({
    						title : "提示",
    						msg : "保存成功！",
    						width :150,
    						buttons: Ext.MessageBox.OK,
    						icon: Ext.MessageBox.INFO
    					});
            			reload();
            			addWin.destroy();
            			
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
      	},{
      		text:"关闭",
      		handler:function(){
      			addWin.destroy();
      		}
      	}]
	});
	addWin.show();
}