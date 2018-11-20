/**
 * 增加直埋段
 * @param store
 */
function addBuriedPart(store){
	var buriedPartName = itemText(.5,80,'buriedPartName','直埋段名称');
	var buriedId = commCombo("/buriedAction!getBuriedList.action",.5,80,'buriedId','所属直埋','remote','id','name',null);
	var buriedPartArea = itemText(.5,80,'buriedPartArea','维护区域');
	var buriedPartLength = itemText(.5,80,'buriedPartLength','直埋段长度');
	var buriedPartStart = itemText(.5,80,'buriedPartStart','开始设备');
	var buriedPartEnd = itemText(.5,80,'buriedPartEnd','终止设备');
	var buriedPartOptical = itemText(.5,80,'buriedPartOptical','承载光缆段');
	var propertyRight = commCombo("/dictAction!getDicValues.action?type=propertyNature",.5,80,'propertyRight','产权性质','remote','id','name',null);
	var propertyDept = commCombo("/dictAction!getDicValues.action?type=propertyComp",.5,80,'propertyDept','产权单位','remote','id','name',null);
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
		    buriedPartName,buriedId,buriedPartArea,buriedPartLength,buriedPartStart,
		    buriedPartEnd,buriedPartOptical,propertyRight,propertyDept,dataQualitier,maintainer
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
      				url : context_path+'/buriedPartAction!saveBuriedPart.action',
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