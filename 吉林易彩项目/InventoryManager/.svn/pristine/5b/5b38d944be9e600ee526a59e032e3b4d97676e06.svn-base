function addStone(store){
	var stoneName = itemText(.5,80,'stoneName','标石名称');
	var stonePosition = commCombo("/dictAction!getDicValues.action?type=stonePosition",.5,80,'stonePosition','标石方位','remote','id','name',null);
	var stoneNum = itemText(.5,80,'stoneNum','标石序号');
	var stoneArea = itemText(.5,80,'stoneArea','所属区域');
	var longitude = itemText(.5,80,'longitude','经度');
	var latitude = itemText(.5,80,'latitude','纬度');
	var propertyNature = commCombo("/dictAction!getDicValues.action?type=propertyNature",.5,80,'propertyNature','产权性质','remote','id','name',null);
	var propertyComp = commCombo("/dictAction!getDicValues.action?type=propertyComp",.5,80,'propertyComp','产权单位','remote','id','name',null);
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
		    stoneName,stonePosition,stoneNum,stoneArea,
		    longitude,latitude,propertyNature,propertyComp,
		    dataQualitier,maintainer
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
      				url : context_path+'/stoneAction!saveStone.action',
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