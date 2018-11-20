Ext.namespace("com.inspur.checkConfig");
com.inspur.checkConfig.EditCheckWindow = Ext.extend(Ext.Window,{
	autoHeight:true,
	closeAction:'close',
	closable:true,
	layout:'fit',
	bodyStyle:'padding:5px;',
    buttonAlign:'center',
	plain:true,
	border:false,
	modal:true,
	frame:true,
	initComponent:function(config){
		com.inspur.checkConfig.EditCheckWindow.superclass.initComponent.call(this);  
	}	
});
Ext.reg("editCheckWindow",com.inspur.checkConfig.EditCheckWindow);