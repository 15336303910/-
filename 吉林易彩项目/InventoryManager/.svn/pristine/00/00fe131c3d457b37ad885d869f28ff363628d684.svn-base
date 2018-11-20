Ext.namespace("com.increase.cen.user");
// 一页的记录数
var limit = 10;
com.increase.cen.user.ModifyRole = Ext.extend(Ext.form.FormPanel, {
	bodyStyle : "padding:5px;",
	frame : true,
	bodyBorder : false,
	labelAlign : "right",
	buttonAlign : 'center',
	labelWidth : 100,
	width : 500,
	height : 400,
	initComponent : function(config) {
		// 获取一页的记录数
		//limit = Ext.getDom("limit").value;
		// 启动悬停提示
		Ext.QuickTips.init();
		var idIsExist = true;
		// 提示的方式
		Ext.form.Field.prototype.msgTarget = 'qtip';
		// 验证用户信息是否存在
		var userExist = false;
		this.roleId = new Ext.form.TextField({
			id : "update.roleId",
			name : "updateRole.roleId",
			fieldLabel : "角色id",
			width : 200,
			hidden : true
		});
		this.areano = new Ext.form.Hidden({
			id : 'update.areano',
			name : "updateRole.areano"
		});
		// 所属地区
		this.areaname = new Ext.form.TextField({
			id : 'update.areaname',
			name : "updateRole.areaname",
			fieldLabel : "所属地区",
			allowBlank : false,
			blankText : '所属地区不能为空',
			emptyText : "请输入所属地区",
			readOnly :true,
			width : 200

		});
		var oldrolename=new Ext.form.Hidden({
			id : 'oldrolename',
			name : ""
		});
		this.roleName = new Ext.form.TextField({
			id : 'update.roleName',
			name : "updateRole.roleName",
			fieldLabel : "角色名",
			allowBlank : false,
			blankText : '角色名不能为空',
			emptyText : "请输入角色名",
			maxLength : 15,
			maxLengthText : '温馨提示：最大长度只能为15个字符',
			width : 200,
			regex:/[a-zA-z0-9\u4E00-\u9FA5]*/,
			regexText : '请输入正确角色名',
			invalidText : '角色名已存在！',
			validationEvent : 'keydown',
			validationDelay : 100,
			validator : function(thisText) {
				if (thisText == "") {
					return true;
				}
				if(thisText==Ext.getCmp("oldrolename").getValue()){
					return true;
				}
				Ext.Ajax.request({
					url : 'user!verifyRole.action',
					method : 'post',
					params : {
						'newRole.roleName' : thisText,
						'newRole.areano' :Ext.getCmp("update.areano").getValue()
					},
					success : function(response) {
						var res = Ext.util.JSON.decode(response.responseText);
						var msg = res.success;
						if (msg == false) {
							Ext.getCmp('update.roleName').markInvalid('角色名已存在!');
							idIsExist=false;
						}
					}
				});
				return idIsExist;
			}
		});
		
		// 用户类型
		this.roletype = new Ext.form.CheckboxGroup({
			id : "update.roletypes",
			xtype : 'checkboxgroup',
			columns: 3,
			name : "updateRole.roletype",
			fieldLabel : "终端操作权限",
			width : 420,
			allowBlank : false,
			blankText : "请选择终端操作权限！",
			items : [{
				name : "updateRole.roletype",
				inputValue : "2",
				boxLabel : "设备配置"
			}, {
				name : "updateRole.roletype",
				inputValue : "3",
				boxLabel : "资源维护"
			}, {
				name : "updateRole.roletype",
				inputValue : "4",
				boxLabel : "资源查看"
			}, {
				name : "updateRole.roletype",
				inputValue : "5",
				boxLabel : "禁止访问无白名单"
			}, {
				name : "updateRole.roletype",
				inputValue : "6",
				boxLabel : "工单转派"
			}, {
				name : "updateRole.roletype",
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
                 var atype= Ext.getCmp('update.roletypes').items;
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
		this.power = new Ext.form.Hidden({
			id : 'update.powers',
			name : "updateRole.strs"
		});

		// 角色权限
		this.powerstr = new Ext.form.CheckboxGroup({
			id : "update.powerstrs",
			xtype : 'checkboxgroup',
			allowBlank : false,
			blankText : "请选择角色权限",
			fieldLabel : '权限类别',
			name : 'updateRole.powerstr',
			columns : 3,
			anchor : "95%",
			msgTarget : "side",
			width : 420,
			items : citems
		});

		this.items = [{
			xtype : 'fieldset',
			title : '基本信息',
			collapsible : true,
			items : [this.areano,this.areaname,this.roleId, this.roleName]
		}, {
			xtype : 'fieldset',
			title : '终端权限信息',
			collapsible : true,
			autoScroll:true,
			height:240,
			items : [this.roletype, this.power,this.powerstr]

		}];
		// 按钮
		this.buttons = [{
			text : "保存",
			icon : "imgs/save_btn.png",
			cls : "x-btn-text-icon",
			tooltip : '修改角色信息',
			tooltipType : 'qtip',
			handler : this.btnSubmit.createDelegate(this)
		}, {
			xtype : 'tbspacer'
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
		
		Ext.override(Ext.form.CheckboxGroup, {
			
			setValueForItem : function(val) {
				val = String(val).split(',');

				this.items.each(function(item) {
					item.setValue(false); 
				});
				this.items.each(function(item) {
					if (val.indexOf(item.inputValue) > -1) { 
						item.setValue(true); 
					}
					});
			},
			setValue : function(val) {
				val = String(val).split(',');

				this.items.each(function(item) {
					item.setValue(false); 
				});

				this.items.each(function(item) {
					if (val.indexOf(item.inputValue) > -1) {
						item.setValue(true); 
					}
				});
			}
		});

		// 重写initComponent superclass.initComponent.call(this);来调用父类的初始化方法.
		com.increase.cen.user.ModifyRole.superclass.initComponent.call(this);
	},
	// 提交事件
	btnSubmit : function submitForm() {
		if (!this.getForm().isValid()) {
			return;
		}
		this.getForm().doAction("submit", {
			url : 'user!updateRole.action',
			submitEmptyText : false,
			method : 'post',
			success : function(form, action) {
				Ext.Msg.show({
					title : '提示',
					width : 300,
					msg : '<img src="imgs/tip_success.png" align="center" hspace="30"/>'
							+ '修改角色信息成功!',
					buttons : {
						ok : "确定"
					}
				});
				Ext.getCmp("modifyRole").getForm().reset();// 重置表单
				Ext.getCmp('modifyRoleWindow').hide();// 窗口隐藏
				Ext.getCmp('roleList').getStore().load({
					params : {
						start : 0,
						limit : limit,
						"roleBean.areano":Ext.getDom("domainCode").value
					}
				});// 表格加载数据
				Ext.getCmp('roleList').getView().refresh();// 表格刷新
			},
			failure : function(form, action) {
				Ext.Msg.show({
					title : '提示',
					width : 300,
					msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
							+ '修改角色信息失败!',
					buttons : {
						ok : "确定"
					}
				});
			}
		});
		//freshing();
		Ext.getCmp('userList').getStore().load({
					params : {
						start : 0,
						limit : limit,
						"user.username" : "",
						"user.realname" : "",
						"user.areano":Ext.getDom("domainCode").value
					}
				});// 表格加载数据
				Ext.getCmp('userList').getView().refresh();
	},
	// 关闭
	btnClose : function() {
		// 重置表单
		Ext.getCmp("modifyRole").getForm().reset();
		// 窗口隐藏
		Ext.getCmp('modifyRoleWindow').hide();
		// 刷新表格
		Ext.getCmp("roleList").getStore({
			params : {
				start : 0,
				limit : limit,
				"roleBean.areano":Ext.getDom("domainCode").value
			}
		});
	}
});
Ext.reg("modifyRole", com.increase.cen.user.ModifyRole);