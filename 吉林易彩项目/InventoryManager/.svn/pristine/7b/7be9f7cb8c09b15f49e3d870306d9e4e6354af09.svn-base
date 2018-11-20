

/**
 * 查看详情
 * @param store
 */
function updateBuried(store,obj){
	var buriedName =itemText(.5,80,'buriedName','直埋名称',obj.buriedName,null,'u');
	var buriedArea =itemText(.5,80,'buriedArea','维护区域',obj.buriedArea,null,'u');
	//var buriedModel =itemText(.5,80,'buriedModel','维护方式',obj.buriedModel,null,'u');
	var buriedDept =itemText(.5,80,'buriedDept','维护单位',obj.buriedDept,null,'u');
	var buriedLength =itemText(.5,80,'buriedLength','直埋长度',obj.buriedLength,null,'u');
	var dataQualitier =itemText(.5,80,'dataQualitier','质量责任人',obj.dataQualitier,null,'u');
	var maintainer =itemText(.5,80,'maintainer','一线维护人',obj.maintainer,null,'u');
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
		    	id:'buriedId',
				name : "buriedId",
				xtype : "hidden",
				value:obj.buriedId,
				anchor : '0%'
		    },
		      buriedName,buriedArea,
		      {
					xtype : "panel",
					layout : "form",
					columnWidth :.5,
					labelWidth :80,
					border : false,
					items : [{
						xtype:'combo',
						id:"buriedModelStr",
						autoSelect :true,
						fieldLabel : "维护方式",
						autoLoad :false,
						emptyText:'请选择',
						value:obj.buriedModelStr,
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
						anchor : '85%',
						triggerAction: 'all',
						listeners:{
							change:function (t,newValue,oldValue)
							{
								Ext.getCmp('buriedModel').setValue(newValue);
							}
						}
					},{
						id:'buriedModel',
						name : "buriedModel",
						xtype : "hidden",
						value:obj.buriedModel,
						anchor : '0%'
					}]
			    },buriedDept,buriedLength,
		    dataQualitier,maintainer
		
		       ]
	});
	var detailWin = new Ext.Window({
		title:'修改直埋',
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
      				url : context_path+'/buriedAction!updateBuried.action',
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