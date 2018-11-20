Ext.namespace("com.increase.cen.user");
// 一页的记录数
var limit = null;
//var n=0;
com.increase.cen.user.ModifyUser = Ext.extend(Ext.form.FormPanel, {
	bodyStyle : "padding:5px;",
	frame : true,
	bodyBorder : false,
	labelAlign : "right",
	buttonAlign : 'center',
	labelWidth : 65,
	width : 600,
	height : 600,
	initComponent : function(config) {
		// 获取一页的记录数
		limit = Ext.getDom("limit").value;
		// 启动悬停提示
		Ext.QuickTips.init();
		// 提示的方式
		Ext.form.Field.prototype.msgTarget = 'qtip';
		// 验证用户信息是否存在
		var userExist = false;
		// 用户id
		this.userid = new Ext.form.TextField({
			id : "userId",
			name : "updateUser.userId",
			fieldLabel : "用户id",
			width : 200,
			hidden : true
		});

		this.areano = new Ext.form.Hidden({
			id : 'areano',
			name : "updateUser.areaname"
		});
		// 所属地区
		this.areaname = new Ext.form.TextField({
			id : 'areaName',
			name : "updateUser.areaname",
			fieldLabel : "所属地区",
			allowBlank : false,
			blankText : '所属地区不能为空',
			emptyText : "请输入所属地区",
			readOnly :true,
			width : 200

		});

		// 用户名
		this.username = new Ext.form.TextField({
			id : 'username',
			name : "updateUser.username",
			fieldLabel : "用户名",
			allowBlank : false,
			blankText : '用户名不能为空',
			emptyText : "请输入用户姓名",
			readOnly :true,
			width : 200

		});
		// 姓名
		this.realname = new Ext.form.TextField({
			id : 'realname',
			name : "updateUser.realname",
			fieldLabel : "姓名",
			allowBlank : false,
			blankText : '用户名不能为空',
			emptyText : "请输入用户姓名",
			maxLength : 15,
			maxLengthText : '温馨提示：最大长度只能为15个字符',
			width : 200
		});

		// 密码
		this.password = new Ext.form.TextField({
			id : "psword",
			name : 'updateUser.password',
			fieldLabel : '密码',
			inputType : 'password',
			allowBlank : false,
			blankText : '密码不能为空',
			maxLength : 15,
			maxLengthText : '温馨提示：最大长度只能为15个字符',
			width : 200,
			disabled : true,
			emptyText : "请输入用户姓名",
			items : [this.psup]
		});
		this.psCheckbox = new Ext.form.Checkbox({
			id : 'updateps',
			inputValue : 'yes',
			boxLabel : '激活修改',
			hideLabel :true,
			listeners : {
				'check' : function() {
					if (this.checked) {
						Ext.getCmp("psword").enable();
						Ext.getCmp("psword").reset() ;
					} else {
						Ext.getCmp("psword").disable();
					}
				}
			}
		})

		this.pAndC = new Ext.Container({
			layout : 'column',
			items : [{
				columnWidth : .52,
				layout : 'form',
				items : [this.password]
			}, {
				columnWidth : .48,
				style:'padding:0px;',
				hiddenLabel:true,
				layout : 'form',
				items : [this.psCheckbox]
			}]
		});
		// 电话
		this.tel = new Ext.form.TextField({
			id : "phoneNumber",
			name : "updateUser.phoneNumber",
			fieldLabel : "绑定号码",
			allowBlank : false,
			width : 200,
			readOnly : true,
		});
		
		this.roleids = new Ext.form.Hidden({
			id : 'roleids',
			name : ""
		});
		// 用户类型
		this.roleId = new Ext.form.RadioGroup({
			id : "roleid",
			name : "updateUser.roleId",
			columns : 4,
			fieldLabel : "角色",
			width : 400,
			allowBlank : false,
			blankText : "请选择角色！",
			/*items : [{
				name : "updateUser.usertype",
				inputValue : "1",
				boxLabel : "管理员",
				width : 40
			}, {
				name : "updateUser.usertype",
				inputValue : "2",
				boxLabel : "施工人员"
			}, {
				name : "updateUser.usertype",
				inputValue : "3",
				boxLabel : "维护人员"
			}, {
				name : "updateUser.usertype",
				inputValue : "4",
				boxLabel : "查看人员"
			}, {
				name : "updateUser.usertype",
				inputValue : "5",
				boxLabel : "代维人员"
			}]*/
			items:cadditems,
			listeners : {
				change : function(radiofield, oldvalue) {// 这事件是当radiogroup的值发生改变时进入
					var v = radiofield.getValue();
					var s=Ext.getCmp("roleids").getValue();
					if(v==s){
						rolenums=rolenums+1;
					}
					if(v!=s||(v==s&&rolenums!=1)){
						var array = Ext.getCmp('powerstrs').items;
						Ext.Ajax.request({
							url : 'user!searchRolePowerstr.action',
							params:{
								"roleBean.roleId" : v
							},
							sync : true,
							success : function(response, opts) {
								var re = Ext.util.JSON.decode(response.responseText);
								array.each(function(item){
									item.setValue(false);
					        	});
								for (var i = 0; i < re.length; i++) {
									array.each(function(item){
										if(item.inputValue==re[i].code){
											item.setValue(true);
										}
					        		});
								}
							}
						});
					}	
				}
			}
		});

		// 用户权限
		this.powerstr = new Ext.form.CheckboxGroup({
			id : "powerstrs",
			xtype : 'checkboxgroup',
			allowBlank : false,
			blankText : "请选择用户权限",
			fieldLabel : '权限类别',
			name : 'updateUser.powerstr',
			columns : 3,
			anchor : "95%",
			msgTarget : "side",
			width : 400,
			items : citems
		});

		this.items =[{
			xtype : 'fieldset',
			title : '基本信息',
			collapsible : true,
			items : [this.userid, this.areano, this.areaname, this.username,
					this.realname, this.pAndC, this.tel]
		}, {
			xtype : 'fieldset',
			title : '权限信息',
			collapsible : true,
			height:300,
			autoScroll:true,
			items : [this.roleId, this.powerstr]

		}];
		// 按钮
		this.buttons = [{
			text : "保存",
			icon : "imgs/save_btn.png",
			cls : "x-btn-text-icon",
			tooltip : '修改用户信息',
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
		// RadioGroup重写的getValue和setValue
		Ext.override(Ext.form.RadioGroup, {
			getValue : function() {
				var v;
				if (this.rendered) {
					this.items.each(function(item) {
						if (!item.getValue())
							return true;
						v = item.getRawValue();
						return false;
					});
				} else {
					for (var k in this.items) {
						if (this.items[k].checked) {
							v = this.items[k].inputValue;
							break;
						}
					}
				}
				return v;
			},
			setValue : function(v,changeMenuPower) {
				if (this.rendered)
					this.items.each(function(item) {
						item.setValue(item.getRawValue() == v);
					});
				else {
					for (var k in this.items) {
						this.items[k].checked = this.items[k].inputValue == v;
					}
				}
			}
		});
		Ext.override(Ext.form.CheckboxGroup, {
			
			setValueForItem : function(val) {
				val = String(val).split(',');

				this.items.each(function(item) {
					item.setValue(false); // 初始化，現將所有的checkbox設為未選定。
				});
				// this.items.each()<====>this.eachItem();

				this.items.each(function(item) {
					if (val.indexOf(item.inputValue) > -1) { // 從數據庫中提取數據，與頁面上checkbox的inputValue對比來判斷是否選中。
						item.setValue(true); // 可理解為以下：
					}
						/*
						 * 等價于： for(var i=0;i<val.length;i++){
						 * if(var[i]==item.inputValue){ item.setValue(true); } }
						 */
					});
			},
			setValue : function(val) {
				val = String(val).split(',');

				this.items.each(function(item) {
					item.setValue(false); // 初始化，現將所有的checkbox設為未選定。
				});
				// this.items.each()<====>this.eachItem();

				this.items.each(function(item) {
					if (val.indexOf(item.inputValue) > -1) { // 從數據庫中提取數據，與頁面上checkbox的inputValue對比來判斷是否選中。
						item.setValue(true); // 可理解為以下：
					}
						/*
						 * 等價于： for(var i=0;i<val.length;i++){
						 * if(var[i]==item.inputValue){ item.setValue(true); } }
						 */
					});
			}
		});

		// 重写initComponent superclass.initComponent.call(this);来调用父类的初始化方法.
		com.increase.cen.user.ModifyUser.superclass.initComponent.call(this);
	},
	// 提交事件
	btnSubmit : function submitForm() {
		if (!this.getForm().isValid()) {
			return;
		}
		// var value = this.getForm().findField("usercode").getValue();
		var areano = this.getForm().findField("areano").getValue();
		// this.getForm().findField("hideusercode").setValue(value);
		this.getForm().doAction("submit", {
			url : 'user!updateUser.action',
			submitEmptyText : false,
			method : 'post',
			success : function(form, action) {
				Ext.Msg.show({
					title : '提示',
					width : 300,
					msg : '<img src="imgs/tip_success.png" align="center" hspace="30"/>'
							+ '修改用户信息成功!',
					buttons : {
						ok : "确定"
					}
				});
				rolenums=0;
				Ext.getCmp("modifyUser").getForm().reset();// 重置表单
				Ext.getCmp('modifyUserWindow').hide();// 窗口隐藏
				Ext.getCmp('userList').getStore().load({
					params : {
						start : 0,
						limit : limit,
						"user.username" : "",
						"user.realname" : "",
						"user.areano" : areano
					}
				});// 表格加载数据
				Ext.getCmp('userList').getView().refresh();// 表格刷新
			},
			failure : function(form, action) {
				Ext.Msg.show({
					title : '提示',
					width : 300,
					msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
							+ '修改用户信息失败!',
					buttons : {
						ok : "确定"
					}
				});
			}
		});
	},
	// 关闭
	btnClose : function() {
		var areano = this.getForm().findField("areano").getValue();
		// 重置表单
		Ext.getCmp("modifyUser").getForm().reset();
		rolenums=0;
		// 窗口隐藏
		Ext.getCmp('modifyUserWindow').hide();
		
		// 刷新表格
		Ext.getCmp("userList").getStore({
			params : {
				start : 0,
				limit : limit,
				"user.username" : "",
				"user.realname" : "",
				"user.areano" : areano
			}
		});
	}
});
Ext.reg("modifyUser", com.increase.cen.user.ModifyUser);