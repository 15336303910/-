/**
 * 增加班组
 * @param stone
 * @param domainCode
 */
function addGroup(stone,domainCode){
	
	var groupStr = itemText(1,80,'groupStr','班组名称');
	var compName = commCombo("/maintainGroupAction!getMtComp.action",1,80,'compName','所属公司','remote','id','name',null);
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
		       groupStr,compName
		]
	});
	
	
	var addWin = new Ext.Window({
		id:'addWin',
		title:'保存班组',
      	width:400,
      	closable:false,
      	height:180,
      	modal:true,
      	buttonAlign:"center",
      	items:addPanel,
      	buttons:[{
      		text:"提交",
      		handler:function(){
      			var groupName = Ext.getCmp('groupStr').getValue();
      			var compRes = Ext.getCmp('compName_id').getValue();
      			addPanel.form.submit({
      				url : context_path+'/maintainGroupAction!saveGroup.action',
      				params : {groupName:groupName,compRes:compRes,domainId:domainCode},
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