

/**
 * 查看详情
 * @param store
 */
function updateStone(store,obj){
	var stoneName = itemText(.5,80,'stoneName','标石名称',obj.stoneName,null,'u');
	var stoneNum = itemText(.5,80,'stoneNum','标石序号',obj.stoneNum,null,'u');
	var stoneArea = itemText(.5,80,'stoneArea','所属区域',obj.stoneArea,null,'u');
	var longitude = itemText(.5,80,'longitude','经度',obj.longitude,null,'u');
	var latitude = itemText(.5,80,'latitude','纬度',obj.latitude,null,'u');
	var dataQualitier = itemText(.5,80,'dataQualitier','质量责任人',obj.dataQualitier,null,'u');
	var maintainer = itemText(.5,80,'maintainer','数据维护人',obj.maintainer,null,'u');
	var detailPanel =new Ext.form.FormPanel({
	    layout : "column",
	    id:'detailPanel',
		border : false,
		width:'100%',
		autoHeight:true,
		frame:true,
		fileUpload : true,
		items:[
		    {
		    	id:'stoneId',
				name : "stoneId",
				xtype : "hidden",
				value:obj.stoneId,
				anchor : '0%'
		    },stoneName,
		    {
				xtype : "panel",
				layout : "form",
				columnWidth :.5,
				labelWidth :80,
				border : false,
				items : [{
					xtype:'combo',
					id:"stonePositionStr",
					autoSelect :true,
					fieldLabel : "标石方位",
					autoLoad :false,
					emptyText:'请选择',
					value:obj.stonePositionStr,
					store:new Ext.data.Store({
						proxy: new Ext.data.HttpProxy({url:context_path+'/dictAction!getDicValues.action?type=stonePosition'}),
						reader: new Ext.data.JsonReader({},[
							{name:'name'} ,
							{name:'id'}
						])
					}),
					valueField:'id',
					displayField:'name',
					typeAhead: true,
					mode: 'remote',
					anchor : '85%',
					triggerAction: 'all',
					listeners:{
						change:function (t,newValue,oldValue)
						{
							Ext.getCmp('stonePosition').setValue(newValue);
						}
					}
				},{
					id:'stonePosition',
					name : "stonePosition",
					xtype : "hidden",
					value:obj.stonePosition,
					anchor : '0%'
				}]
		    }
		    ,stoneNum,stoneArea,longitude,latitude,
		    {
				xtype : "panel",
				layout : "form",
				columnWidth :.5,
				labelWidth :80,
				border : false,
				items : [{
					xtype:'combo',
					id:"propertyNatureStr",
					autoSelect :true,
					fieldLabel : "产权性质",
					autoLoad :false,
					emptyText:'请选择',
					value:obj.propertyNatureStr,
					store:new Ext.data.Store({
						proxy: new Ext.data.HttpProxy({url:context_path+'/dictAction!getDicValues.action?type=propertyNature'}),
						reader: new Ext.data.JsonReader({},[
							{name:'name'} ,
							{name:'id'}
						])
					}),
					valueField:'id',
					displayField:'name',
					typeAhead: true,
					mode: 'remote',
					anchor : '85%',
					triggerAction: 'all',
					listeners:{
						change:function (t,newValue,oldValue)
						{
							Ext.getCmp('propertyNature').setValue(newValue);
						}
					}
				},{
					id:'propertyNature',
					name : "propertyNature",
					xtype : "hidden",
					value:obj.propertyNature,
					anchor : '0%'
				}]
		    },{
				xtype : "panel",
				layout : "form",
				columnWidth :.5,
				labelWidth :80,
				border : false,
				items : [{
					xtype:'combo',
					id:"propertyCompStr",
					autoSelect :true,
					fieldLabel : "产权单位",
					autoLoad :false,
					emptyText:'请选择',
					value:obj.propertyCompStr,
					store:new Ext.data.Store({
						proxy: new Ext.data.HttpProxy({url:context_path+'/dictAction!getDicValues.action?type=propertyComp'}),
						reader: new Ext.data.JsonReader({},[
							{name:'name'} ,
							{name:'id'}
						])
					}),
					valueField:'id',
					displayField:'name',
					typeAhead: true,
					mode: 'remote',
					anchor : '85%',
					triggerAction: 'all',
					listeners:{
						change:function (t,newValue,oldValue)
						{
							Ext.getCmp('propertyComp').setValue(newValue);
						}
					}
				},{
					id:'propertyComp',
					name : "propertyComp",
					xtype : "hidden",
					value:obj.propertyComp,
					anchor : '0%'
				}]
		    },dataQualitier,maintainer
		]
	});
	var detailWin = new Ext.Window({
		title:'修改标石',
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
      				url : context_path+'/stoneAction!updateStone.action',
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
            			detailWin.destroy();
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
      			detailWin.destroy();
      		}
      	}]
	});
	detailWin.show();
}