function addBuried(store){
	var buriedName =itemText(.5,80,'buriedName','直埋名称');
	var buriedArea =itemText(.5,80,'buriedArea','维护区域');
	var buriedModel =commCombo("/dictAction!getDicValues.action?type=buriedModel",.5,80,'buriedModel','维护方式','remote','id','name',null);
	var buriedDept =itemText(.5,80,'buriedDept','维护单位');
	var buriedLength =itemText(.5,80,'buriedLength','直埋长度');
	var dataQualitier =itemText(.5,80,'dataQualitier','质量责任人');
	var maintainer =itemText(.5,80,'maintainer','一线维护人');
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
	     buriedName,buriedArea,
	     buriedModel,
	     buriedDept,
	     buriedLength,dataQualitier,
	     maintainer
		]
	});
	var addWin = new Ext.Window({
		id:'addWin',
		title:'新建',
      	width:600,
      	closable:false,
      	height:250,
      	modal:true,
      	buttonAlign:"center",
      	items:addPanel,
      	buttons:[{
      		text:"提交",
      		handler:function(){
      			//alert('09');
      			addPanel.form.submit({
      				url : context_path+'/buriedAction!saveBuried.action',
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