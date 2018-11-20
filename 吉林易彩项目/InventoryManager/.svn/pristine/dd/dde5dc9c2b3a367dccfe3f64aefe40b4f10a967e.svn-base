

/**
 * 查看详情
 * @param store
 */
function updateOpt(store,obj){
	var terminalName = itemText(.5,80,'terminalName','光终端盒名称',obj.terminalName,null,'u');
	var longitude = itemText(.5,80,'longitude','经度',obj.longitude,null,'u');
	var latitude = itemText(.5,80,'latitude','纬度',obj.latitude,null,'u');
	var rowNum = itemText(.5,80,'rowNum','行数',obj.rowNum,null,'u');
	var colNum = itemText(.5,80,'colNum','列数',obj.colNum,null,'u');
	var attachName = itemText(.5,80,'attachName','归属点名称',obj.attachName,null,'u');
	var terminalAddres = itemText(.5,80,'terminalAddres','设备地址',obj.terminalAddres,null,'u');
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
		    	   name:'id',
		    	   xtype : "hidden",
				   value:obj.id,
				   anchor : '0%'
		       },terminalName,{
					xtype : "panel",
					layout : "form",
					columnWidth :.5,
					labelWidth :80,
					border : false,
					items : [{
						xtype:'combo',
						id:"attachTypeStr",
						autoSelect :true,
						fieldLabel : "归属点类型",
						autoLoad :false,
						emptyText:'请选择',
						value:obj.attachTypeStr,
						store:new Ext.data.Store({
							proxy: new Ext.data.HttpProxy({url:context_path+'/dictAction!getDicValues.action?type=attachType'}),
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
								Ext.getCmp('attachType').setValue(newValue);
							}
						}
					},{
						id:'attachType',
						name : "attachType",
						xtype : "hidden",
						value:obj.attachType,
						anchor : '0%'
					}]
			    },rowNum,colNum,longitude,latitude,
		       terminalAddres,dataQualitier,maintainer
		]
	});
	var detailWin = new Ext.Window({
		title:'修改光终端盒',
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
      				url : context_path+'/opticalTerminalAction!updateOpticalTerminal.action',
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