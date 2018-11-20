

/**
 * 查看详情
 * @param store
 */
function updateBuriedPart(store,obj){
	var buriedPartName = itemText(.5,80,'buriedPartName','直埋段名称',obj.buriedPartName,null,'u');
	var buriedPartArea = itemText(.5,80,'buriedPartArea','维护区域',obj.buriedPartArea,null,'u');
	var buriedPartLength = itemText(.5,80,'buriedPartLength','直埋段长度',obj.buriedPartLength,null,'u');
	var buriedPartStart = itemText(.5,80,'buriedPartStart','开始设备',obj.buriedPartStart,null,'u');
	var buriedPartEnd = itemText(.5,80,'buriedPartEnd','结束设备',obj.buriedPartEnd,null,'u');
	var buriedPartOptical = itemText(.5,80,'buriedPartOptical','承载光缆段',obj.buriedPartOptical,null,'u');
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
		    	id:'id',
				name : "id",
				xtype : "hidden",
				value:obj.id,
				anchor : '0%'
		    },buriedPartName,{
					xtype : "panel",
					layout : "form",
					columnWidth :.5,
					labelWidth :80,
					border : false,
					items : [{
						xtype:'combo',
						id:"buriedStr",
						autoSelect :true,
						fieldLabel : "所属直埋",
						autoLoad :false,
						emptyText:'请选择',
						value:obj.buriedStr,
						store:new Ext.data.Store({
							proxy: new Ext.data.HttpProxy({url:context_path+'/buriedAction!getBuriedList.action'}),
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
								Ext.getCmp('buriedId').setValue(newValue);
							}
						}
					},{
						id:'buriedId',
						name : "buriedId",
						xtype : "hidden",
						value:obj.buriedId,
						anchor : '0%'
					}]
			    },buriedPartArea,buriedPartLength,buriedPartStart,
		       buriedPartEnd,buriedPartOptical,
		       {
					xtype : "panel",
					layout : "form",
					columnWidth :.5,
					labelWidth :80,
					border : false,
					items : [{
						xtype:'combo',
						id:"propertyRightStr",
						autoSelect :true,
						fieldLabel : "产权性质",
						autoLoad :false,
						emptyText:'请选择',
						value:obj.propertyRightStr,
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
								Ext.getCmp('propertyRight').setValue(newValue);
							}
						}
					},{
						id:'propertyRight',
						name : "propertyRight",
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
						id:"propertyDeptStr",
						autoSelect :true,
						fieldLabel : "产权单位",
						autoLoad :false,
						emptyText:'请选择',
						value:obj.propertyDeptStr,
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
								Ext.getCmp('propertyDept').setValue(newValue);
							}
						}
					},{
						id:'propertyDept',
						name : "propertyDept",
						xtype : "hidden",
						value:obj.propertyDept,
						anchor : '0%'
					}]
			    }
		       ,dataQualitier,maintainer
		]
	});
	var detailWin = new Ext.Window({
		title:'修改直埋段',
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
      				url : context_path+'/buriedPartAction!updateBuriedPart.action',
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