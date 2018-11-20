Ext.namespace("com.increase.cen.user");
var pageSize = 10;
var limit= 10;

	var sm = new Ext.grid.CheckboxSelectionModel();
		var ccm = new Ext.grid.ColumnModel([sm,
				{header : "序号",
					width : 60,
					hideable : false,
					renderer : function(value, metadata, record, rowIndex, colIndex, store) {
						var start = store.lastOptions.params.start;
							if (isNaN(start)) {
							start = 0;
							}
							return start + rowIndex + 1;
						}
					}, 
				{header : 'roleId',dataIndex : 'roleId',hidden : true}, 
				{header : '角色名',dataIndex : 'roleName',width : 120, sortable : true}, 
				{header : '修改时间',dataIndex : 'updateDate',width : 150,sortable : true,renderer: Ext.util.Format.dateRenderer('Y-m-d H:i:s')}
		]);
	
		// 从远程读取数据
		var cproxy = new Ext.data.HttpProxy({
			url : "user!roleList.action"
		});
	
		// Record 定义记录结果
		var role = Ext.data.Record.create([
			{name : 'roleId',type : 'string',mapping : 'roleId',hidden : true}, 
			{name : 'roleName',type : 'string',mapping : 'roleName'},
			{name : 'updateDate',type : Ext.data.Types.DATE,dateFormat : 'Y-m-d\\TH:i:s',mapping : 'updateDate'}
		]);
		var creader = new Ext.data.JsonReader({
			totalProperty : "total",
			root : "roles"
		}, role);
		var cstore = new Ext.data.Store({// 提供数据输入
			proxy : cproxy,
			reader : creader,
			remoteSort : true
		});
		com.increase.cen.user.RoleList = Ext.extend(Ext.grid.GridPanel, {
			id:"roleListGridPanel",
			cm : ccm,
			sm:sm,// 列定义的model(必需)
			store : cstore,
			height:300,
			width:800,
			//autoHeight:true,
			viewConfig : {
				sortAscText : '升序',
				sortDescText : '降序',
				columnsText : '可选列',
				forceFit : false,// Ture表示自动扩展或缩小列的宽度
				scrollOffset : -1
			},
			tbar : [{
				xtype : 'tbseparator'// 一个分隔栏
			}, {
				text : '添加',
				id : 'addBtn1',
				icon : "js/ext/resources/images/default/dd/drop-add.gif",
				cls : "x-btn-text-icon",
				handler:function(){
					addRoleing();
				}
			}, {
				xtype : 'tbseparator'
			}, {
				id : 'modifyBtn1',
				text : '修改',
				icon : "imgs/modifyBtn.png",
				cls : "x-btn-text-icon",
				handler:function(){
					eidtRoleing();
				}
			}, {
				xtype : 'tbseparator'
			}, {
				id : 'deleteBtn1',
				text : '删除',
				icon : "imgs/deleteBtn.png",
				cls : "x-btn-text-icon",
				handler:function(){
					deleteRoleing();
				}
			}, {
				xtype : 'tbspacer',
				width:50
			},{
				xtype : 'tbseparator'
			}, {
				xtype : 'label',
				text : '角色名：'
			}, {
				xtype : 'textfield',
				id : 'roleName_sel',
				cls :'sel-textfield-width-e'
			}, {
				xtype : 'tbseparator'
			},{
				id : 'queryBtn1',
				text : '查询',
				icon : "imgs/queryBtn.png",
				cls : "x-btn-text-icon",
				handler:function(){
					var store = Ext.getCmp("roleList").getStore();
					store.load({
						params : {
							start : 0,
							limit : 10,
							'roleBean.roleName':Ext.getCmp("roleName_sel").getValue(),
							'roleBean.areano':Ext.getDom("domainCode").value
						}
					});
					store.on('beforeload',function(){
		   				store.baseParams = {
		   					start : 0,
							limit : 10,
							'roleBean.areano':Ext.getDom("domainCode").value,
		   					'roleBean.roleName':Ext.getCmp("roleName_sel").getValue()
		   				};
					}); 
					//Ext.getCmp("roleName_sel").setValue("");
					Ext.getCmp("roleList").getView().refresh();
					
				}
			}],	
				bbar : new Ext.PagingToolbar({// 分页工具栏
					store : cstore,
					pageSize : pageSize,
					beforePageText : "当前第",
					afterPageText : "页，共{0}页",
					lastText : "尾页",
					nextText : "下一页",
					prevText : "上一页",
					firstText : "首页",
					refreshText : "刷新页面",
					displayInfo : true,// 是否显示displayMsg
					displayMsg : "显示第{0}-{1}条 ，共{2}条",
					emptyMsg : "没有记录"
				}),
				initComponent : function(config) {
				// 重写initComponent superclass.initComponent.call(this);来调用父类的初始化方法.
					com.increase.cen.user.RoleList.superclass.initComponent.call(this);
				}
		});
Ext.reg("roleList", com.increase.cen.user.RoleList);

	
	// 获得增加按钮,增加新员工
	function addRoleing() {
		var grid=Ext.getCmp("roleList");
		Ext.getCmp("roleName_sel").setValue("");
		grid.getStore().on('beforeload',function(){
		   				grid.getStore().baseParams = {
		   					start : 0,
							limit : 10,
							'roleBean.areano':Ext.getDom("domainCode").value
		   				};
					});
		if (!grid.addRoleWindow) {
			// 新建添加窗口
			grid.addRoleWindow = new com.increase.cen.user.userWindow({
				id : "addRoleWindow",
				title : '添加新角色',
				iconCls : 'i-script-window',
				items : [{
					id : "addRole",
					xtype : 'addRole'// 窗口里的表单
				}]
			});
		}
		Ext.getCmp("addareano").setValue(Ext.getDom("domainCode").value);
		Ext.getCmp("addareaname").setValue(Ext.getDom("domainName").value);
		grid.addRoleWindow.show("addBtn1");
		// 重置表单数据
		Ext.getCmp("addRole").getForm().reset();
	}
	
	function eidtRoleing(){
		var grid=Ext.getCmp("roleList");
		Ext.getCmp("roleName_sel").setValue("");
		grid.getStore().on('beforeload',function(){
		   				grid.getStore().baseParams = {
		   					start : 0,
							limit : 10,
							'roleBean.areano':Ext.getDom("domainCode").value
		   				};
					});
		var selModel = grid.getSelectionModel();// 返回grid的SelectionModel
		if (selModel.hasSelection()) {
			var records = selModel.getSelections();
			var ids = [];
			for (var i = 0; i < records.length; i++) {
				ids.push(records[i].get("roleId"));// 获得选择的编号
			}
			if (ids.length > 1) {
				Ext.Msg.show({
					title : '提示',
					width : 300,
					msg : '<img src="imgs/tip_warn.png" align="center" hspace="30"/>'
							+ '请您选择一条数据进行修改!',
					buttons : {
						ok : "确定"
					}
				});
			} else if (ids.length == 1) {
				Ext.MessageBox.show({
					title : '确认',
					msg : '<div style="margin-top:5px;margin-left:10px;">您确定要修改选择的记录吗?</div>',
					width : 300,
					multiline : false,// 设置为true，提示用户输入多行文本
					closable : false,
					buttons : {
						yes : '确定',
						no : '取消'
					},
					icon : Ext.MessageBox.QUESTION,
					fn : function(btn) {
						if (btn == 'yes') {// 如果选择确定，则进行修改
							if (!grid.modifyRoleWindow) {// 如果窗口不存在，则创建新窗口
								// 新建修改窗口
								grid.modifyRoleWindow = new com.increase.cen.user.userWindow({
									id : "modifyRoleWindow",
									title : '修改角色信息',
									iconCls : 'i-script-window',
									items : [{
										id : "modifyRole",
										xtype : 'modifyRole'// 窗口里的修改表单
									}]
								});
							}
							// 显示窗口
							grid.modifyRoleWindow.show("modifyBtn1");
							// 表单加载数据
							var modifyRoleForm = Ext.getCmp("modifyRole");
							modifyRoleForm.getForm().load({
								url : 'user!loadRole.action',
								params : {
									ids : ids
								},
								success : function(form, action) {
									var json = Ext.util.JSON.decode(action.response.responseText);
									var roletypes=json.roleBean.roletypes;
									var powerstrs = json.data.powerstrs;
									Ext.getCmp("update.roletypes").setValue(roletypes);
									Ext.getCmp("update.roleName").setValue(json.roleBean.roleName);
									Ext.getCmp("update.roleId").setValue(json.roleBean.roleId);
									Ext.getCmp("update.areano").setValue(json.roleBean.areano);
									Ext.getCmp("update.areaname").setValue(json.roleBean.areaname);
									Ext.getCmp("update.powers").setValue(powerstrs);
									Ext.getCmp("update.powerstrs").setValue(powerstrs);
									Ext.getCmp("oldrolename").setValue(json.roleBean.roleName);
								},
								failure : function(form, action) {
									var json = Ext.util.JSON.decode(action.response.responseText);
									var roletypes=json.roleBean.roletypes;
									var powerstrs = json.roleBean.powerstrs;
									Ext.getCmp("update.roletypes").setValue(roletypes);
									Ext.getCmp("update.roleName").setValue(json.roleBean.roleName);
									Ext.getCmp("update.roleId").setValue(json.roleBean.roleId);
									Ext.getCmp("update.areano").setValue(json.roleBean.areano);
									Ext.getCmp("update.areaname").setValue(json.roleBean.areaname);
									Ext.getCmp("update.powers").setValue(powerstrs);
									Ext.getCmp("update.powerstrs").setValue(powerstrs);
									Ext.getCmp("oldrolename").setValue(json.roleBean.roleName);
								}
							});
						}
					}
				});
			}
		} else {
			Ext.Msg.show({
				title : '提示',
				width : 300,
				msg : '<img src="imgs/tip_warn.png" align="center" hspace="30"/>'
						+ '请选择您想修改的数据!',
				buttons : {
					ok : "确定"
				}
			});
		}
	}

// 获得删除按钮，删除角色信息
	function deleteRoleing() {
		var grid=Ext.getCmp("roleList");
		Ext.getCmp("roleName_sel").setValue("");
		grid.getStore().on('beforeload',function(){
		   				grid.getStore().baseParams = {
		   					start : 0,
							limit : 10,
							'roleBean.areano':Ext.getDom("domainCode").value
		   				};
					});
		// 返回grid的SelectionModel
		var selModel = grid.getSelectionModel();
		if (selModel.hasSelection()) {
			var records = selModel.getSelections();
			var ids = [];
			for (var i = 0; i < records.length; i++) {
				ids.push(records[i].get("roleId"));// 获得选择的编号
			}
			if (ids.length > 1) {
				Ext.Msg.show({
					title : '提示',
					width : 300,
					msg : '<img src="imgs/tip_warn.png" align="center" hspace="30"/>'
							+ '请您选择一条数据进行删除!',
					buttons : {
						ok : "确定"
					}
				});
			} else if (ids.length == 1) {
			Ext.MessageBox.show({// 提示框
				title : '确认',
				msg : '您确定要删除选择的记录吗?',
				width : 300,
				multiline : false,// 设置为true，提示用户输入多行文本
				closable : false,
				buttons : {
					yes : '确定',
					no : '取消'
				},
				icon : Ext.MessageBox.QUESTION,
				fn : function(btn) {
					if (btn == "yes") {
						var records = selModel.getSelections();// 返回所有选中的记录
						var ids = [];
						for (var i = 0; i < records.length; i++) {
							ids.push(records[i].get("roleId"));
						}
						Ext.Ajax.request({
							url : 'user!deleteRole.action',
							method : 'post',
							params : {
								ids : ids
							},
							success : function(response) {
								var json = Ext.util.JSON.decode(response.responseText);
								if (json.success == true) {
									Ext.Msg.show({
										title : '提示',
										width : 300,
										msg : '<img src="imgs/tip_success.png" align="center" hspace="30"/>'+ json.deleteMsg,
										buttons : {
											ok : "确定"
										}
									});
									grid.getStore().remove(records);
								grid.getStore().load({
									params : {
										start : 0,
										limit : limit,
										"roleBean.areano" : Ext.getDom("domainCode").value
									}
								});
								grid.getView().refresh();
								} else {
									Ext.Msg.show({
										title : '提示',
										width : 300,
										msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
												+ json.deleteMsg,
										buttons : {
											ok : "确定"
										}
									});
								}
							},
							failure : function() {
								Ext.Msg.show({
									title : '提示',
									width : 300,
									msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
											+ '删除角色信息失败!',
									buttons : {
										ok : "确定"
									}
								});
							}
						});
					}
				}
			});
			}
		} else {
			Ext.Msg.show({
				title : '提示',
				width : 300,
				msg : '<img src="imgs/tip_warn.png" align="center" hspace="30"/>'
						+ '请选择您想删除的数据!',
				buttons : {
					ok : "确定"
				}

			});
		}
	}
