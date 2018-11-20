Ext.onReady(function(){
	var sm = new Ext.grid.CheckboxSelectionModel({
		checkOnly:true
	});
	var cm = new Ext.grid.ColumnModel([sm,{
		header:'id',
		dataIndex:'id',
		hidden:true
	},{
		header:'核查信息编码',
		dataIndex:'id',
		width:100,
		hidden:true,
		sortable:true
	},{
		header:'所属资源类型',
		dataIndex:"dimensionName",
		width:100,
		sortable:false
	},{
		header:'核查内容',
		dataIndex:'itemDesc',
		width:100,
		sortable:true
	},{
		header:'核查 / 展示',
		dataIndex:'isUsing',
		width:60,
		sortable:true,
		renderer:function(v){
			if(v=='Y'){
				return "<font color='blue' size='1'>核查<font>";
			}else{
				return "<font color='red' size='1'>展示<font>";
			}
		}
	},{
		header:'编辑类型',
		dataIndex:'editType',
		width:100,
		sortable:true
	},{
		header:'资源字段（工单）',
		dataIndex:'considerColumn',
		sortable:false
	},{
		header:'资源字段（综资）',
		dataIndex:'resConsiderColumn',
		sortable:false
	}]);
	var store = new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({
			url:"checkConfigAction!getChecksList.action"
		}),
		reader:new Ext.data.JsonReader({
			totalProperty:"total",
			root:"checkItems"
		},Ext.data.Record.create([{
			name:'id',
			type:'integer',
			mapping:'id',
			hidden:true
		},{
			name:"editType",
			type:"string",
			mapping:"editType"
		},{
			name:'isUsing',
			type:'string',
			mapping:'isUsing'
		},{
			name:'dimensionName',
			type:'string',
			mapping:'dimensionName'
		}, { 
			name:'itemDesc',
			type:'string',
			mapping:'itemDesc'
		},{
			name:'lastModifyDate',
			type:'date',
			mapping:'lastModifyDate'
		},{
			name:'isDenyAll',
			type:'string',
			mapping:'isDenyAll'
		},{
			name:'isConsider',
			type:'string',
			mapping:'isConsider'
		},{
			name:'considerColumn',
			type:'string',
			mapping:'considerColumn'
		},{
			name:'resConsiderColumn',
			type:'string',
			mapping:'resConsiderColumn'
		}])),
		remoteSort:true
	});
	var pageSize = parseInt(Ext.getDom("pageSize").value);
	var limit = parseInt(Ext.getDom("limit").value);	
	var grid = new Ext.grid.GridPanel({
		title:"当前位置：核查配置",
		region:'center',
		id:"configDataGrid",
		border:false,
		cm:cm,
		sm:sm,
		store:store,
		autoScroll:true,
		viewConfig:{
			sortAscText:'升序',
			sortDescText:'降序',
			columnsText:'可选列',
			forceFit:true,
			scrollOffset:-1
		},
		buttonAlign:"center",
		tbar:[{
			id:"helpBtn",
			icon:"imgs/bangzhu.png",
			cls:"x-btn-text-icon",
			handler:function(){}
		},{
			xtype:'tbseparator'
		},{
			text:'添加',
			id:'addItemsBtn',
			icon:"imgs/fiber/link_add.png",
			cls:"x-btn-text-icon"
		},{
			xtype:'tbseparator'
		},{
			id:'modifyPoleLineBtn',
			text:'修改',
			icon:"imgs/fiber/link_break.png",
			cls:"x-btn-text-icon"
		},{
			xtype:'tbseparator'
		},{
			id:'deletePoleLineBtn',
			text:'删除',
			icon:"imgs/fiber/link_break.png",
			cls:"x-btn-text-icon"
		},{
			xtype:'tbseparator'
		},{
			xtype:'tbseparator'
		},{
			xtype:'tbseparator'
		},{
			xtype:'label',
			text:'所属资源类型：'
		},{
			xtype:'textfield',
			id:'checkDescribe',
			cls:'sel-textfield-width',
			width:200
		},{
			id:'seekPoleLineBtn',
			text:'搜索',
			icon:"imgs/queryBtn.png",
			cls:"x-btn-text-icon"
		}],
		bbar:new Ext.PagingToolbar({
			store:store,
			pageSize:pageSize,
			beforePageText:"当前第",
			afterPageText:"页，共{0}页",
			lastText:"尾页",
			nextText:"下一页",
			prevText:"上一页",
			firstText:"首页",
			refreshText:"刷新页面",
			displayInfo:true,
			displayMsg:"显示第{0}-{1}条 ，共{2}条",
			emptyMsg:"没有记录"
		})
	});
	var loadMarsk = new Ext.LoadMask(document.body,{
		msg:'数据加载中，请稍候......',
		disabled:false,
		store:store
	});
	store.addListener('beforeload',function(){
		loadMarsk.show();
	});
	store.load({
		params:{
			start:0,
			limit:limit
		}
	});
	new Ext.Viewport({
		layout:'border',
		items:[grid]
	});
	/*添加检查项*/
	var addItemsBtn = Ext.getCmp("addItemsBtn");
	addItemsBtn.on('click',function(){
		grid.editConfigWindow = new com.inspur.checkConfig.EditCheckWindow({
			id:"editFormWindow",
			title:'新增核查内容',
			iconCls:'i-script-window',
			width:600,
			height:700,
			items:[{
				id:"thisEditForm",
				height:280,
				width:600,
				xtype:'editConfigType'
			}]
		});
		grid.editConfigWindow.show("addItemsBtn");
	});
	/*修改检查项*/
	var modifyPoleLineBtn = Ext.getCmp("modifyPoleLineBtn");
	modifyPoleLineBtn.on('click',function(){
		var selModel = grid.getSelectionModel();
		if(selModel.hasSelection()){
			var records = selModel.getSelections();
			if(records.length!=1){
				Ext.Msg.show({
					title:'提示',
					width:300,
					msg:'<img src="imgs/tip_warn.png" align="center" hspace="30"/>'+'请您选择一条数据进行修改!',
					buttons:{
						ok:"确定"
					}
				});
			}else{
				Ext.MessageBox.show({
					title:'确认',
					msg:'<div style="margin-top:5px;margin-left:10px;">您确定要修改选择的记录吗?</div>',
					width:300,
					multiline:false,
					closable:false,
					buttons:{
						yes:'确定',
						no:'取消'
					},
					icon:Ext.MessageBox.QUESTION,
					fn:function(btn) {
						if(btn == 'yes'){
							/*初始化窗口*/
							grid.modifyConfigWindow = new com.inspur.checkConfig.EditCheckWindow({
								id:"editFormWindow",
								title:'编辑核查内容',
								iconCls:'i-script-window',
								width:600,
								items:[{
									id:"thisEditForm",
									height:280,
									width:600,
									xtype:'editConfigType'
								}]
							});
							grid.modifyConfigWindow.show("addItemsBtn");
							var modifyPoleLineForm = Ext.getCmp("thisEditForm");
							modifyPoleLineForm.getForm().load({
								url:'checkConfigAction!getCheckObject.action',
								params:{
									id:records[0].get("id")
								},
								success:function(form,action){
									var json = Ext.util.JSON.decode(action.response.responseText);
									if(json!=null && json.checkObject!=null){
										Ext.getCmp("checkItem.id").setValue(json.checkObject.id);
										Ext.getCmp("checkItem.itemDesc").setValue(json.checkObject.itemDesc);
										Ext.getCmp("checkItem.isUsing").setValue(json.checkObject.isUsing);
										Ext.getCmp("checkItem.isDenyAll").setValue(json.checkObject.isDenyAll);
										Ext.getCmp("checkItem.isConsider").setValue(json.checkObject.isConsider);
										Ext.getCmp("checkItem.considerColumn").setValue(json.checkObject.considerColumn);
										Ext.getCmp("checkItem.resConsiderColumn").setValue(json.checkObject.resConsiderColumn);
									}else{
										Ext.Msg.show({
											title:'提示',
											width:300,
											msg:'<img src="imgs/tip_warn.png" align="center" hspace="30"/>数据初始化异常!',
											buttons:{
												ok:"确定"
											}
										});
									}
								},
								failure:function(form,action){
									var json = Ext.util.JSON.decode(action.response.responseText);
									if(json!=null && json.checkObject!=null){
										Ext.getCmp("checkItem.id").setValue(json.checkObject.id);
										Ext.getCmp("checkItem.itemDesc").setValue(json.checkObject.itemDesc);
										Ext.getCmp("checkItem.isUsing").setValue(json.checkObject.isUsing);
										Ext.getCmp("checkItem.isDenyAll").setValue(json.checkObject.isDenyAll);
										Ext.getCmp("checkItem.isConsider").setValue(json.checkObject.isConsider);
										Ext.getCmp("checkItem.considerColumn").setValue(json.checkObject.considerColumn);
										Ext.getCmp("checkItem.resConsiderColumn").setValue(json.checkObject.resConsiderColumn);
									}else{
										Ext.Msg.show({
											title:'提示',
											width:300,
											msg:'<img src="imgs/tip_warn.png" align="center" hspace="30"/>数据初始化异常!',
											buttons:{
												ok:"确定"
											}
										});
									}
								}
							});
						}
					}
				});
			}
		}else{
			Ext.Msg.show({
				title:'提示',
				width:300,
				msg:'<img src="imgs/tip_warn.png" align="center" hspace="30"/>请选择您想修改的数据!',
				buttons:{
					ok:"确定"
				}
			});
		}
	});
	/*删除检查项*/
	var deletePoleLineBtn = Ext.getCmp("deletePoleLineBtn");
	deletePoleLineBtn.on('click',function(){
		var selModel = grid.getSelectionModel();
		var records = selModel.getSelections();	
		if(records.length!=1){
			Ext.Msg.show({
				title:'提示',
				width:300,
				msg:'<img src="imgs/tip_warn.png" align="center" hspace="30"/>请您选择一条数据进行删除!',
				buttons:{
					ok:"确定"
				}
			});
		}else{
			Ext.MessageBox.show({
				title:'确认',
				msg:'您确定要删除选择的记录吗?',
				width:300,
				multiline:false,
				closable:false,
				buttons:{
					yes:'确定',
					no:'取消'
				},
				icon:Ext.MessageBox.QUESTION,
				fn:function(btn){
					if(btn=="yes"){
						Ext.Ajax.request({
							url:'checkConfigAction!deleteCheckObject.action',
							method:'post',
							params:{
								id:records[0].get("id")
							},
							success:function(response){
								var json = Ext.util.JSON.decode(response.responseText);
								if(json.success == true){
									Ext.Msg.show({
										title:'提示',
										width:300,
										msg:'<img src="imgs/tip_success.png" align="center" hspace="30"/>'+json.deleteMsg,
										buttons:{
											ok:"确定"
										}
									});
								}else{
									Ext.Msg.show({
										title:'提示',
										width:300,
										msg:'<img src="imgs/tip_error.png" align="center" hspace="30"/>删除信息失败!',
										buttons:{
											ok:"确定"
										}
									});
								}
								grid.getStore().remove(records);
								grid.getStore().load({
									params:{
										start:0,
										limit:limit
									}
								});
								grid.getView().refresh();
							},
							failure:function(){
								Ext.Msg.show({
									title:'提示',
									width:300,
									msg:'<img src="imgs/tip_error.png" align="center" hspace="30"/>'+'删除信息失败!',
									buttons:{
										ok:"确定"
									}
								});
							}
						});
					}
				}
			});
		}
	});	
	/*执行查询*/
	var seekPoleLineBtn = Ext.getCmp("seekPoleLineBtn");
	seekPoleLineBtn.on('click', function() {
		var checkDescribe = Ext.getCmp("checkDescribe").getValue();
		grid.getStore().load({
			params:{
				start:0,
				limit:limit,
				'checkItem.belongDimension':checkDescribe
			}
		});
		grid.getStore().on('beforeload',function(){
			grid.getStore().baseParams = {
				'checkItem.belongDimension':checkDescribe
			};
		});
		grid.getView().refresh();
	});
});