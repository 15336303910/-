function modifySupportPoint(obj) {
	
	var sportName = itemText(.5,80,'sportName','撑点名称',obj.sportName,null,'u');
	var purpose = itemText(.5,80,'purpose','用途',obj.purpose, null, 'u');
	var maintArea = itemText(.5,80,'maintArea','维护区县',obj.maintArea, null, 'u');
	var longitude = itemText(.5,80,'longitude','经度', obj.longitude, null, 'u');
	var latitude = itemText(.5,80,'latitude','纬度', obj.latitude, null, 'u');
	var maintainer = itemText(.5,80,'maintainer','一线维护人', obj.maintainer, null, 'u');
	var maintDept = itemText(.5,80,'maintDept','维护单位', obj.maintDept, null, 'u');
	
	var detailPanel =new Ext.form.FormPanel({
	    layout : "column",
	    id:'detailPanel',
		border : false,
		width:'100%',
		autoHeight:true,
		frame:true,
		fileUpload : true,
		items:[
		    sportName,purpose,maintArea,longitude,latitude,maintainer,maintDept,
		    {
				xtype : "panel",
				layout : "form",
				columnWidth :.5,
				labelWidth :80,
				border : false,
				items : [{
					xtype:'combo',
					id:"maintMode",
					autoSelect :true,
					fieldLabel : "维护方式",
					autoLoad :false,
					emptyText:'请选择',
					value:obj.maintMode,
					store:new Ext.data.Store({
						proxy: new Ext.data.HttpProxy({url:context_path+'/dictAction!getDicValues.action?type=buriedModel'}),
						reader: new Ext.data.JsonReader({},[
							{name:'name'} ,
							{name:'id'}
						])
					}),
					valueField:'id',
					displayField:'name',
					typeAhead: true,
					mode: 'remote',
					triggerAction: 'all',
					listeners:{
						change:function (t,newValue,oldValue)
						{
							Ext.getCmp('maintMode').setValue(newValue);
						}
					}
				}]
		    }
		    
		]
	});
	
	var detailWin = new Ext.Window({
		title:'修改撑点',
		closeAction : "destroy",
      	width:600,
      	height:250,
      	autoScroll:true,
      	modal:true,
      	buttonAlign:"center",
      	items:[detailPanel],
      	buttons:[{
      		text:"提交",
      		handler:function(){
      			detailPanel.form.submit({
      				url : context_path+'/leadupAction!updateSupportPoint.action?id='+obj.id,
            		waitMsg : '正在提交数据，请稍等...',
            		success : function() {
            			Ext.Msg.show({
    						title : "提示",
    						msg : "修改成功！",
    						width :150,
    						buttons: Ext.MessageBox.OK,
    						icon: Ext.MessageBox.INFO
    					});
            			reload();
            			detailWin.destroy();
            		},
            		failure : function() {
            			Ext.Msg.show({
    						title : "提示",
    						msg : "修改失败！",
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
      			detailWin.destroy();
      		}
      	}]
	});
	detailWin.show();
}