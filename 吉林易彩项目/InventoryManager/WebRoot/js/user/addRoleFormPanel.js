Ext.namespace("com.increase.cen.user");
// 一页显示多少条数据
var columns = 3;
/*var critems = [];
Ext.Ajax.request({
	url : 'user!searchPowerstr.action',
	sync : true,
	success : function(response, opts) {
		critems = [];
		var resr = Ext.util.JSON.decode(response.responseText);
		var resLen = res.length;
		columns = getPageAmount(resLen, '3');
		var i = 0;
		for (i = 0; i < resr.length; i++) {
			var dr = resr[i];
			var chkr = {
				boxLabel : dr.name,
				name : 'powerstr',
				inputValue : dr.code,
				checked : false
			};
			critems[i] = chkr;
		}
	}
});*/

var limit = 10;
com.increase.cen.user.AddRole = Ext.extend(Ext.form.FormPanel, {
	bodyStyle : "padding:5px;",
	frame : true,
	bodyBorder : false,
	labelAlign : "right",
	buttonAlign : 'center',
	labelWidth : 100,
	width : 500,
	height : 400,
	initComponent : function(config) {
		// 一页显示多少条数据
		//limit = Ext.getDom("limit").value;
		// 启动悬停提示
		Ext.QuickTips.init();
		// 提示的方式
		Ext.form.Field.prototype.msgTarget = 'qtip';
		// 用户信息是否存在
		var idIsExist = true;
		// 是否批量增加
		this.batchAdd = new Ext.form.Checkbox({
			id : 'batchAdd1',
			inputValue : 'yes',
			boxLabel : '批量录入',
			listeners : {
				'check' : function() {
					// 批量添加按钮
					var btnAdd = Ext.getCmp("btnAdd1");
					// 保存按钮
					var btnSubmit = Ext.getCmp("btnSubmit1");
					if (this.checked) {
						btnAdd.setVisible(true);
						btnSubmit.setVisible(false);
					} else {
						btnAdd.setVisible(false);
						btnSubmit.setVisible(true);
					}
				}
			}
		})
		
		this.areaname = new Ext.form.TextField({
			id : "addareaname",
			name : "newRole.areaname",
			fieldLabel : "所属地区",
			editable : false,
			allowBlank : false,
			blankText : '',
			mode : "remote",
			triggerAction : "all",
			emptyText : "",
			width : 200,
			readOnly : true
		});
		
		
		this.areano = new Ext.form.Hidden({
			id : "addareano",
			name : "newRole.areano"
		});
		// 用户名
		this.rolename = new Ext.form.TextField({
			id : "addRolename",
			name : "newRole.roleName",
			fieldLabel : "角色名",
			allowBlank : false,// 验证是否为空
			blankText : '角色名不能为空',
			width : 200,
			emptyText : "角色名可以为字母或数字",
			regex:/[a-zA-z0-9\u4E00-\u9FA5]*/,
			regexText : '请输入正确角色名',
			invalidText : '角色名已存在！',
			validationEvent : 'keydown',
			validationDelay : 100,
			validator : function(thisText) {
				// 没有输入，不验证
				if (thisText == "") {
					return true;
				}
				Ext.Ajax.request({
					url : 'user!verifyRole.action',
					method : 'post',
					// async:false,
					params : {
						'newRole.roleName' : thisText,
						'newRole.areano' :Ext.getCmp("addareano").getValue()
					},
					success : function(response) {
						var res = Ext.util.JSON.decode(response.responseText);
						var msg = res.success;
						if (msg == false) {
							Ext.getCmp('addRolename').markInvalid('角色名已存在!');
							idIsExist=false;
						}else{
							Ext.getCmp('addRolename').clearInvalid();
							idIsExist=true;
						}
					}
				});
				return idIsExist;
			}
		});

		
		// 用户类型
		this.roletype = new Ext.form.CheckboxGroup({
			id : 'type',
			name : "newRole.roletype",
			xtype : 'checkboxgroup',
			fieldLabel : "终端操作权限",
			width : 400,
			columns : 3,
			allowBlank : false,
			blankText : "请选择终端操作权限！",
			items : [{
				name : "newRole.roletype",
				inputValue : "2",
				boxLabel : "设备配置"
			}, {
				name : "newRole.roletype",
				inputValue : "3",
				boxLabel : "资源维护"
			}, {
				name : "newRole.roletype",
				inputValue : "4",
				boxLabel : "资源查看"
			}, {
				name : "newRole.roletype",
				inputValue : "5",
				boxLabel : "禁止访问无白名单"
			},{
				name : "newRole.roletype",
				inputValue : "6",
				boxLabel : "工单转派"
			},{
				name : "newRole.roletype",
				inputValue : "7",
				boxLabel : "节点变更"
			}]
		});
		this.roletype.on('change', function (cbgroup, checked) {
				var s='10';
                 for (var i = 0; i < checked.length; i++) {
                 	if(parseInt(checked[i].getRawValue())<parseInt(s)){
                 		s=checked[i].getRawValue();
                 	}
                 }
                 var atype= Ext.getCmp('type').items;
                 if(s=='2'){
                 	atype.each(function(item){
							if(item.inputValue=='2'||item.inputValue=='3'||item.inputValue=='4'){
								item.setValue(true);
							}else if(item.inputValue=='5'){
								item.setValue(false);
							}
				    });
				  }else if(s=='3'){
                 		atype.each(function(item){
							if(item.inputValue=='3'||item.inputValue=='4'){
								item.setValue(true);
							}
				        });
				  }else if(s=='5'){
                 		atype.each(function(item){
							if(item.inputValue=='5'||item.inputValue=='3'||item.inputValue=='4'){
								item.setValue(true);
							}
				        });
				   }
             });
		
		// 角色权限
		this.powerstr = new Ext.form.CheckboxGroup({
			id : "raddPower",
			xtype : 'checkboxgroup',
			allowBlank : false,
			blankText : "请选择角色权限",
			fieldLabel : '权限类别',
			name : 'newRole.powerstr',
			columns : 3,
			anchor : "95%",
			// msgTarget : "side",
			width : 400,
			items : citems
		});

		this.items = [{
			xtype : 'panel',
			bodyStyle : 'padding-left:78%;',
			items : [this.batchAdd]
		}, {
			xtype : 'fieldset',
			title : '角色信息',
			collapsible : true,
			items : [ this.areano, this.areaname,this.rolename]
		}, {
			xtype : 'fieldset',
			title : '权限信息',
			collapsible : true,
			height:240,
			autoScroll:true,
			items : [this.roletype, this.powerstr]
		}];

		// 按钮
		this.buttons = [{
			id : 'btnSubmit1',
			text : "保存",
			hidden : false,
			icon : "imgs/save_btn.png",
			cls : "x-btn-text-icon",
			tooltip : '保存角色信息',
			tooltipType : 'qtip',
			handler : this.btnSubmit.createDelegate(this)
		}, {
			id : 'btnAdd1',
			hidden : true,
			text : '继续添加',
			tooltip : '继续添加角色信息',
			tooltipType : 'qtip',
			icon : "imgs/form_add.png",
			cls : "x-btn-text-icon",
			handler : this.btnAdd.createDelegate(this)
		}, {
			xtype : 'tbspacer'
		}, {
			xtype : 'tbspacer'
		}, {
			text : "返回",
			icon : "imgs/back.png",
			cls : "x-btn-text-icon",
			tooltip : '关闭此窗口',
			tooltipType : 'qtip',
			handler : this.btnClose.createDelegate(this)
		}];

		// 重写initComponent superclass.initComponent.call(this);来调用父类的初始化方法.
		com.increase.cen.user.AddRole.superclass.initComponent.call(this);
	},
	// 保存按钮事件
	btnSubmit : function() {
		if (!this.getForm().isValid()) {
			return;
		}
		this.getForm().doAction("submit", {
			url : 'user!addRole.action',
			method : 'post',
			submitEmptyText : false,
			success : function(form, action) {
				Ext.Msg.show({
					title : '提示',
					width : 300,
					msg : '<img src="imgs/tip_success.png" align="center" hspace="30" />'
							+ '添加用户信息成功!',
					buttons : {
						ok : "确定"
					}
				});
				Ext.getCmp("addRole").getForm().reset();// 重置表单
				Ext.getCmp('addRoleWindow').hide();// 窗口隐藏
				Ext.getCmp('roleList').getStore().load({
					params : {
						start : 0,
						limit : limit,
						"roleBean.areano" : Ext.getDom("domainCode").value
					}
				});// 表格加载数据
				Ext.getCmp('roleList').getView().refresh();// 表格刷新
			},
			failure : function(form, action) {
				Ext.Msg.show({
					title : '提示',
					width : 300,
					msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
							+ '添加角色信息失败',
					buttons : {
						ok : "确定"
					}
				});
			}
		});
	},
	// 返回操作
	btnClose : function() {
		// 重置表单
		Ext.getCmp("addRole").getForm().reset();
		// 窗口隐藏
		Ext.getCmp('addRoleWindow').hide();
		Ext.getCmp("roleList").getStore().load({
			params : {
				start : 0,
				limit : limit,
				"roleBean.areano" : Ext.getDom("domainCode").value
			}
		});
	},
	// 批量新增
	btnAdd : function() {
		// 批量添加复选框无效
		Ext.getCmp("batchAdd1").disable();
		if (!this.getForm().isValid()) {
			return;
		}
		this.getForm().doAction("submit", {
			url : 'user!addRole.action',
			method : 'post',
			submitEmptyText : false,
			success : function(form, action) {
				Ext.Msg.show({
					title : '提示',
					width : 300,
					msg : '<img src="imgs/tip_success.png" align="center" hspace="30"/>'
							+ '添加用户信息成功!',
					buttons : {
						ok : "确定"
					}
				});
				// 重置表单
				Ext.getCmp("addRole").getForm().reset();
				// 加载下拉列表数据
				Ext.getCmp("addRole").store.reload();
				// 复选框选中
				Ext.getCmp("batchAdd").setValue(true);
			},
			failure : function(form, action) {
				Ext.Msg.show({
					title : '提示',
					width : 300,
					msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
							+ '添加角色信息失败',
					buttons : {
						ok : "确定"
					}
				});
			}
		});
	}
});
Ext.reg("addRole", com.increase.cen.user.AddRole);

