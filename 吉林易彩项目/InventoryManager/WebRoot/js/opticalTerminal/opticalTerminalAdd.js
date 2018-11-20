function addOpticalTerminal(store){
	var terminalName = itemText(.5,80,'terminalName','光终端盒名称');
	var attachType = commCombo("/dictAction!getDicValues.action?type=attachType",.5,80,'attachType','归属点类型','remote','id','name',null);
	var rowNum = itemText(.5,80,'rowNum','行数');
	var colNum = itemText(.5,80,'colNum','列数');
	var longitude = itemText(.5,80,'longitude','经度');
	var latitude = itemText(.5,80,'latitude','纬度');
	var terminalAddres = itemText(.5,80,'terminalAddres','设备地址');
	var dataQualitier = itemText(.5,80,'dataQualitier','质量责任人');
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
		    terminalName,attachType,rowNum,colNum,longitude,latitude,
		    terminalAddres,dataQualitier,maintainer
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
      				url : context_path+'/opticalTerminalAction!saveOpticalTerminal.action',
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