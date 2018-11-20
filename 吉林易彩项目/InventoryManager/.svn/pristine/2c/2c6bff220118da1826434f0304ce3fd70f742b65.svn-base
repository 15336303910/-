/**
 * 增加字典项
 * @param store
 * @param id
 */
function addDicTable(store,id){
	var value = itemText(1,80,'value','属性值');
	var text = itemText(1,80,'text','显示值');
	var orderValue = itemText(1,80,'orderValue','排序');
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
		    text,value,orderValue
		]
	});
	var addWin = new Ext.Window({
		id:'addWin',
		title:'增加属性',
      	width:400,
      	closable:false,
      	height:150,
      	modal:true,
      	buttonAlign:"center",
      	items:addPanel,
      	buttons:[{
      		text:"提交",
      		handler:function(){
      			addPanel.form.submit({
      				url : context_path+'/dictAction!saveDictTable.action?dicId='+id,
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
      		text:"继续添加",
      		handler:function(){
      			addPanel.form.submit({
      				url : context_path+'/dictAction!saveDictTable.action?dicId='+id,
            		waitMsg : '正在提交数据，请稍等...',
            		success : function() {
            			Ext.Msg.show({
    						title : "提示",
    						msg : "保存成功！",
    						width :150,
    						buttons: Ext.MessageBox.OK,
    						icon: Ext.MessageBox.INFO
    					});
            			Ext.getCmp('value').reset();
            			Ext.getCmp('text').reset();
            			Ext.getCmp('orderValue').reset();
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