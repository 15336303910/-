var cadditems = [];
var citems = [];
var rolenums=0;
Ext.onReady(function() {
	Ext.Ajax.request({
		url : 'user!searchPowerstr.action',
		sync : true,
		success : function(response, opts) {
			citems = [];
			var res = Ext.util.JSON.decode(response.responseText);
			var i = 0;
			for (i = 0; i < res.length; i++) {
				var d = res[i];
				var chk = {
					boxLabel : d.name,
					name : 'powerstr',
					inputValue : d.code,
					checked : false
				};
				citems[i] = chk;
				// citems.push(chk);
			}
		}
	});
	Ext.Ajax.request({
				url : 'user!searchRolestr.action',
				params : {
					"roleBean.areano" : Ext.getDom("domainCode").value
				},
				sync : true,
				success : function(response, opts) {
					var ress = Ext.util.JSON.decode(response.responseText);
					for (var i = 0; i < ress.length; i++) {
						var dd = ress[i];
						var chkk = {
							boxLabel : dd.roleName,
							name : 'roleId',
							inputValue : dd.roleId,
							checked : false
						};
						cadditems[i] = chkk;
					}
					
				}
			});
	Ext.useShims=true;
	// 创建一个复选框
	var sm = new Ext.grid.CheckboxSelectionModel();
	// 列模型
	var cm = new Ext.grid.ColumnModel([sm, {
		header : "序号",
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
	{header : 'id',dataIndex : 'id',width : 100,hidden : true}, 
	{header : '用户名',dataIndex : 'username',width : 120, sortable : true}, 
	{header : '姓名',dataIndex : 'realname',width : 120,sortable : true}, 
	{header : '角色名称',dataIndex : 'rolename',width : 120,sortable : true}, 
	{header : '代维班组',dataIndex : 'groupName',width : 120,sortable : true}, 
	{header : '所属地区',dataIndex : 'areaName',width : 100,sortable : true}, 
	{header : '联系电话',dataIndex : 'phoneNumber',width : 130,sortable : true}, 
	{header : '最后登录时间',dataIndex : 'lasttime',width : 130,sortable : true,renderer: Ext.util.Format.dateRenderer('Y-m-d H:i')}, 
	{header : '地区负责人',dataIndex : 'isResponsible',width : 180}
	]);
	//var pageSize = Ext.getDom("domainCode").value;
	// 从远程读取数据
	var proxy = new Ext.data.HttpProxy({
		url : "user!searchUser.action"
		
	});
	// Record 定义记录结果
	var user = Ext.data.Record.create([
		{name : 'userId',type : 'string',mapping : 'userId',hidden : true}, 
		{name : 'username',type : 'string',mapping : 'username'}, 
		{name : 'realname',type : 'string',mapping : 'realname'}, 
		{name : 'rolename',type : 'string',mapping : 'rolename'}, 
		{name : 'groupName',type : 'string',mapping : 'groupName'}, 
		{name : 'areaName',type : 'string',mapping : 'areaName'}, 
		{name : 'phoneNumber',type : 'string',mapping : 'phoneNumber'}, 
		{name : 'lasttime',	type : Ext.data.Types.DATE,dateFormat : 'Y-m-d\\TH:i:s',mapping : 'lasttime'}, 
		{name : 'isResponsible',type : 'string',mapping : 'isResponsible',
			convert:function(v){
				if(v==true){
					return '<img src="imgs/yes.png"/>';
				}
			}
		}
	]);
	// Reader以提供的Ext.data.Record 构造器中的映射为基础，从JSON响应创建一个Ext.data.Record 对象数组
	var reader = new Ext.data.JsonReader({
		totalProperty : "total",
		root : "users"
	}, user);
	var store = new Ext.data.Store({// 提供数据输入
		proxy : proxy,
		reader : reader,
		remoteSort : true
	});
	// 获取每页显示的记录数
	var pageSize = parseInt(Ext.getDom("pageSize").value);
	var limit = parseInt(Ext.getDom("limit").value);
	var domainName = Ext.getDom("domainName").value;
	// 表格
	var grid = new Ext.grid.GridPanel({
		title : "当前位置："+domainName+"用户管理",
		region : 'center',
		id : "userList",
		border : false,
		cm : cm,// 列定义的model(必需)
		sm : sm,// 作为grid的选择模型(selection model)
		store : store,
		autoScroll : true,// 滚动条
		viewConfig : {
			sortAscText : '升序',
			sortDescText : '降序',
			columnsText : '可选列',
			forceFit : false,// Ture表示自动扩展或缩小列的宽度
			scrollOffset : -1
		},
		addUserWindow : null,// 添加用户窗口
		modifyUserWindow : null,// 修改用户窗口
		queryUserWindow : null,// 查询用户窗口
		buttonAlign : "center",// 按钮居中
		tbar : [{
			xtype : 'tbseparator'// 一个分隔栏
		}, {
			text : '角色管理',
			id : 'roleBtn',
			icon : "imgs/modifyBtn.png",
			cls : "x-btn-text-icon"
		},{
			text:'班组管理',
			id:'groupBtn',
			icon : "imgs/modifyBtn.png",
			cls : "x-btn-text-icon",
			handler:function(){
				var domainCode = Ext.getDom("domainCode").value;
				groupMagFun(domainCode);
			}
		},{
			xtype : 'tbseparator'// 一个分隔栏
		}, {
			text : '添加',
			id : 'addBtn',
			icon : "js/ext/resources/images/default/dd/drop-add.gif",
			cls : "x-btn-text-icon"
		}, {
			xtype : 'tbseparator'
		}, {
			id : 'modifyBtn',
			text : '修改',
			icon : "imgs/modifyBtn.png",
			cls : "x-btn-text-icon"
		}, {
			xtype : 'tbseparator'
		}, {
			id : 'deleteBtn',
			text : '删除',
			icon : "imgs/deleteBtn.png",
			cls : "x-btn-text-icon"
		}, {
			xtype : 'tbseparator'
		}, {
			id : 'userCkBtn',
			text : '设备权限设置',
			icon : "imgs/userCk.png",
			cls : "x-btn-text-icon"
		}, {
			xtype : 'tbseparator'
		}, {
			id : 'refreshBtn',
			text : '全部刷新',
			icon : "imgs/all_refresh.png",
			cls : "x-btn-text-icon",
			handler:function(){
				freshing();
			}
		}, {
			xtype : 'tbspacer',
			width:200
		},{
			xtype:'label',
			text:'用户名：'
		},{
			xtype:'textfield',
			cls :'order-sel-textfield-width',
			id:'queryusername'
		},{
			xtype : 'tbseparator'
		},{
			xtype:'label',
			text:'姓名：'
		},{
			xtype:'textfield',
			cls :'order-sel-textfield-width',
			id:'queryrealname'
		},{
			xtype : 'tbseparator'
		},{
			xtype:'label',
			text:'电话：'
		},{
			xtype:'textfield',
			cls :'order-sel-textfield-width',
			id:'queryphoneNumber'
		},{
			xtype : 'tbseparator'
		},{
			id : 'seekBtn',
			text : '搜索',
			icon : "imgs/queryBtn.png",
			cls : "x-btn-text-icon"
		}],
		bbar : new Ext.PagingToolbar({// 分页工具栏
			store : store,
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
		})
		
	});
		
	
	var loadMarsk = new Ext.LoadMask(document.body, {
		msg : '数据加载中，请稍候......',
		disabled : false,
		store : store
	});
	store.addListener('beforeload', function() {
		loadMarsk.show();
	});
	store.load({
		params : {
			start : 0,
			limit : limit,
			"user.username" : "",
			"user.realname" : "",
			"user.areano" : Ext.getDom("domainCode").value
		}
	});
	//解决分页点击下一页时不带参数问题
	store.on('beforeload',function(){
   		store.baseParams = {"user.areano":Ext.getDom("domainCode").value};
	}); 
	// viewport 布局
	new Ext.Viewport({
		layout : 'border',
		items : [grid]
	});
	//var view = Ext.getCmp("view")
	
	//获得角色表按钮
	var roleBtn = Ext.getCmp("roleBtn");
	roleBtn.on('click', function() {
		Ext.getCmp("queryusername").setValue("");
		Ext.getCmp("queryrealname").setValue("");
		Ext.getCmp("queryphoneNumber").setValue("");
		grid.getStore().on('beforeload',function(){
		   	grid.getStore().baseParams = {
		   		start : 0,
				limit : 20,
				"user.areano" : Ext.getDom("domainCode").value
		   	};
		});
		if(!grid.roleListWindow){
			grid.roleListWindow=new com.increase.cen.user.userWindow({
				id : "roleListWindow",
				title : '角色列表',
				iconCls : 'i-script-window',
				items : [{
					id : "roleList",
					xtype : 'roleList'// 窗口里的表单
				}]
			});
		}
		var store = Ext.getCmp("roleList").getStore();
		var loadMarsk = new Ext.LoadMask(document.body, {
			msg : '数据加载中，请稍候......',
			disabled : false,
			store : store
		});
		store.addListener('beforeload', function() {
			loadMarsk.show();
		});
	
		store.load({
			params : {
				start : 0,
				limit : 10,
				"roleBean.areano":Ext.getDom("domainCode").value
			}
		});
		store.on('beforeload',function(){
		   				store.baseParams = {
		   					start : 0,
							limit : 10,
							'roleBean.areano':Ext.getDom("domainCode").value
		   				};
					}); 
		grid.roleListWindow.show("roleBtn");
	});
	
	// 获得增加按钮,增加新员工
	var addBtn = Ext.getCmp("addBtn");
	// 增加按钮的click事件
	addBtn.on('click', function() {
		var num=0;
		Ext.getCmp("queryusername").setValue("");
		Ext.getCmp("queryrealname").setValue("");
		Ext.getCmp("queryphoneNumber").setValue("");
		grid.getStore().on('beforeload',function(){
		   	grid.getStore().baseParams = {
		   		start : 0,
				limit : 10,
				"user.areano" : Ext.getDom("domainCode").value
		   	};
		});
		Ext.Ajax.request({
			url : 'user!searchRolestragain.action',
				params : {
					"roleBean.areano" : Ext.getDom("domainCode").value
				},
				sync : true,
				method : 'post',
				success : function(response, opts) {
					var re = Ext.util.JSON.decode(response.responseText);
					num=re.length;
					if(num==0){
						Ext.Msg.show({
									title : '提示',
									width : 300,
									msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
											+ '请先添加该地区的角色信息!',
									buttons : {
										ok : "确定"
									}
								});
					}else{
						cadditems = [];
						for (var i = 0; i < re.length; i++) {
							var dd = re[i];
							var chkkk = {
								boxLabel : dd.roleName,
								name : 'roleId',
								inputValue : dd.roleId,
								checked : false
							};
							cadditems[i] = chkkk;
						}
						if (!grid.addUserWindow) {
							// 新建添加窗口
							grid.addUserWindow = new com.increase.cen.user.userWindow({
								id : "addUserWindow",
								title : '添加新用户',
								iconCls : 'i-script-window',
								items : [{
									id : "addUser",
									xtype : 'addUser'// 窗口里的表单
								}]
							});
						}
						Ext.getCmp("addAreano").setValue(Ext.getDom("domainCode").value);
						Ext.getCmp("addAreaname").setValue(Ext.getDom("domainName").value);
						grid.addUserWindow.show("addBtn");
						
						// 重置表单数据
						Ext.getCmp("addUser").getForm().reset();
						// 是否批量增加复选框有效状态
						Ext.getCmp("batchAdd").enable();
					}
				}
		});
		
		
	});
	var userCkBtn = Ext.getCmp("userCkBtn");
	userCkBtn.on('click', function() {
		var selModel = grid.getSelectionModel();// 返回grid的SelectionModel
		if (selModel.hasSelection()) {
			var records = selModel.getSelections();
			var ids = [];
			for (var i = 0; i < records.length; i++) {
				ids.push(records[i].get("userId"));// 获得选择的编号
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
			}else if (ids.length == 1) {
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
							Ext.getDom("userid").value=ids[0];
								// 新建修改窗口
								grid.userCkWindow = new com.increase.cen.userck.userCkWindow({
									id : "userCkWindow",
									title : '修改用户设备权限',
									iconCls : 'i-script-window',
									html:'<div id="equtTree" ></div>',
									width:300,
									items : [{
										id : "userCkForm",
										xtype : 'userCkForm'// 窗口里的修改表单
										
									}]
								});
													// 显示窗口
							Ext.getCmp("userId").setValue(ids[0]);
							grid.userCkWindow.show();
							// 表单加载数据
							
						}
					}
				});
			}
		}else{
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
	});
	
	// 获得修改按钮，修改员工信息
	var modifyBtn = Ext.getCmp("modifyBtn");
	modifyBtn.on('click', function() {
		Ext.getCmp("queryusername").setValue("");
		Ext.getCmp("queryrealname").setValue("");
		Ext.getCmp("queryphoneNumber").setValue("");
		grid.getStore().on('beforeload',function(){
		   	grid.getStore().baseParams = {
		   		start : 0,
				limit : 10,
				"user.areano" : Ext.getDom("domainCode").value
		   	};
		});
		Ext.Ajax.request({
			url : 'user!searchRolestragain.action',
				params : {
					"roleBean.areano" : Ext.getDom("domainCode").value
				},
				sync : true,
				method : 'post',
				success : function(response, opts) {
					var re = Ext.util.JSON.decode(response.responseText);
					cadditems = [];
					for (var i = 0; i < re.length; i++) {
							var dd = re[i];
							var chkkk = {
								boxLabel : dd.roleName,
								name : 'roleId',
								inputValue : dd.roleId,
								checked : false
							};
							cadditems[i] = chkkk;
						}
				}
		});
		var selModel = grid.getSelectionModel();// 返回grid的SelectionModel
		if (selModel.hasSelection()) {
			var records = selModel.getSelections();
			var ids = [];
			for (var i = 0; i < records.length; i++) {
				ids.push(records[i].get("userId"));// 获得选择的编号
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
							if (!grid.modifyUserWindow) {// 如果窗口不存在，则创建新窗口
								// 新建修改窗口
								grid.modifyUserWindow = new com.increase.cen.user.userWindow({
									id : "modifyUserWindow",
									title : '修改用户信息',
									iconCls : 'i-script-window',
									items : [{
										id : "modifyUser",
										xtype : 'modifyUser'// 窗口里的修改表单
									}]
								});
							}else{
								rolenums=0;
							}
							// 显示窗口
							grid.modifyUserWindow.show("modifyBtn");
							// 表单加载数据
							var modifyUserForm = Ext.getCmp("modifyUser");
							modifyUserForm.getForm().load({
								url : 'user!loadUser.action',
								params : {
									ids : ids
								},
								success : function(form, action) {
									var json = Ext.util.JSON.decode(action.response.responseText);
									var powerstrs = json.data.powerstrs
									Ext.getCmp("powerstrs").setValue(powerstrs);
									Ext.getCmp("roleid").setValue(json.data.roleId);
									Ext.getCmp("roleids").setValue(json.data.roleId);
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
	});
	// 获得删除按钮，删除员工信息
	var deleteBtn = Ext.getCmp("deleteBtn");
	// 按钮加click监听事件
	deleteBtn.on('click', function() {
		// 返回grid的SelectionModel
		Ext.getCmp("queryusername").setValue("");
		Ext.getCmp("queryrealname").setValue("");
		Ext.getCmp("queryphoneNumber").setValue("");
		grid.getStore().on('beforeload',function(){
		   	grid.getStore().baseParams = {
		   		start : 0,
				limit : 10,
				"user.areano" : Ext.getDom("domainCode").value
		   	};
		});
		var selModel = grid.getSelectionModel();
		if (selModel.hasSelection()) {
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
							ids.push(records[i].get("userId"));
						}
						Ext.Ajax.request({
							url : 'user!deleteUser.action',
							method : 'post',
							params : {
								ids : ids
							},
							success : function(response) {
								var json = Ext.util.JSON
										.decode(response.responseText);
								if (json.success == true) {
									Ext.Msg.show({
										title : '提示',
										width : 300,
										msg : '<img src="imgs/tip_success.png" align="center" hspace="30"/>'
												+ json.deleteMsg,
										buttons : {
											ok : "确定"
										}
									});
								} else {
									Ext.Msg.show({
										title : '提示',
										width : 300,
										msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
												+ '删除用户信息失败!',
										buttons : {
											ok : "确定"
										}
									});
								}
								grid.getStore().remove(records);
								grid.getStore().load({
									params : {
										start : 0,
										limit : limit,
										"user.username" : "",
										"user.realname" : "",
										"user.phoneNumber" : "",
										"user.areano" : Ext.getDom("domainCode").value
									}
								});
								grid.getView().refresh();
							},
							failure : function() {
								Ext.Msg.show({
									title : '提示',
									width : 300,
									msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
											+ '删除用户信息失败!',
									buttons : {
										ok : "确定"
									}
								});
							}
						});
					}
				}
			});
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
	});
	// 获得查询按钮，查询用户信息
	/*var queryBtn = Ext.getCmp("queryBtn");
	queryBtn.on('click', function() {
		// 创建查询窗口
		if (!grid.queryUserWindow) {
			grid.queryUserWindow = new com.increase.cen.user.userWindow({
				id : "queryUserWindow",
				title : '查询用户信息',
				iconCls : 'i-script-window',
				items : [{
					id : "queryUser",
					xtype : 'queryUser'
				}]
			});
		}
		
		
		Ext.getCmp("queryUser").getForm().reset();
		Ext.getCmp("queryAreano").setValue(Ext.getDom("domainCode").value);
		// 显示查询窗口
		grid.queryUserWindow.show("queryBtn");

	});*/
	function freshing(){
		Ext.getCmp("queryusername").setValue("");
		Ext.getCmp("queryrealname").setValue("");
		Ext.getCmp("queryphoneNumber").setValue("");
		grid.getStore().on('beforeload',function(){
		   	grid.getStore().baseParams = {
		   		start : 0,
				limit : 10,
				"user.areano" : Ext.getDom("domainCode").value
		   	};
		});
		grid.getStore().load({
			params : {
				start : 0,
				limit : limit,
				"user.username" : "",
				"user.realname" : "",
				"user.areano" : Ext.getDom("domainCode").value
			}
		});
		grid.getView().refresh();
	}
	// 获得刷新按钮，刷新全部用户信息
	/*var refreshBtn = Ext.getCmp("refreshBtn");
	refreshBtn.on('click', function() {
		grid.getStore().load({
			params : {
				start : 0,
				limit : limit,
				"user.username" : "",
				"user.realname" : "",
				"user.areano" : Ext.getDom("domainCode").value
			}
		});
		grid.getView().refresh();
	})*/
	
	var seekBtn = Ext.getCmp("seekBtn");
	seekBtn.on('click',function(){
		var username = Ext.getCmp("queryusername").getValue();
		var realname = Ext.getCmp("queryrealname").getValue();
		var phoneNumber = Ext.getCmp("queryphoneNumber").getValue();
		grid.getStore().load({
			params : {
				start : 0,
				limit : limit,
				"user.username" : username,
				"user.realname" : realname,
				"user.phoneNumber":phoneNumber,
				"user.areano" : Ext.getDom("domainCode").value
			}
		});
		grid.getStore().on('beforeload',function(){
		   	grid.getStore().baseParams = {
		   		start : 0,
				limit : 10,
				"user.username" : username,
				"user.realname" : realname,
				"user.phoneNumber":phoneNumber,
				"user.areano" : Ext.getDom("domainCode").value
		   	};
		}); 
		grid.getView().refresh();
		/*Ext.getCmp("username").setValue("");
		Ext.getCmp("realname").setValue("");
		Ext.getCmp("phoneNumber").setValue("");*/
		
	})
});
