function addStone(store){
	var enName = itemText(1,80,'enName','英文名称');
	var cnName = itemText(1,80,'cnName','中文名称');
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
		    enName,cnName
		]
	});
	var addWin = new Ext.Window({
		id:'addWin',
		title:'添加字典',
      	width:400,
      	closable:false,
      	height:125,
      	modal:true,
      	buttonAlign:"center",
      	items:addPanel,
      	buttons:[{
      		text:"提交",
      		handler:function(){
      			addPanel.form.submit({
      				url : context_path+'/dictAction!saveDictType.action',
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