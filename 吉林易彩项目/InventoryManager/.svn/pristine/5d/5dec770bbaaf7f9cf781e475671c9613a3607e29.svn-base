function addSupportPoint() {
	var sportName = itemText(.5,80,'sportName','撑点名称');
	var purpose = itemText(.5,80,'purpose','用途');
	var maintArea = itemText(.5,80,'maintArea','维护区县');
	var longitude = itemText(.5,80,'longitude','经度');
	var latitude = itemText(.5,80,'latitude','纬度');
	var maintainer = itemText(.5,80,'maintainer','一线维护人');
	var maintDept = itemText(.5,80,'maintDept','维护单位');
	var maintMode = commCombo("/dictAction!getDicValues.action?type=buriedModel",.5,80,'maintMode','维护方式','remote','id','name',null);
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
		   sportName,purpose,maintArea,maintMode,longitude,latitude,maintainer,
		   maintDept
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
      				url : context_path+'/leadupAction!saveSupportPointPojo.action',
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

