Ext.namespace("com.increase.cen.user");
// 一页显示多少条数据

var columns = 3;
// 整除函数
function Div(exp1, exp2) {
	var n1 = Math.round(exp1); // 四舍五入
	var n2 = Math.round(exp2); // 四舍五入
	var rslt = n1 / n2; // 除
	if (rslt >= 0) {
		rslt = Math.floor(rslt); // 返回值为小于等于其数值参数的最大整数值。
	} else {
		rslt = Math.ceil(rslt); // 返回值为大于等于其数字参数的最小整数。
	}
	return rslt;
}
// 计算分页
function getPageAmount(exp1, exp2) {
	if (exp1 % exp2 == 0) {
		return Div(exp1, exp2);
	} else {
		return Div(exp1, exp2) + 1;
	}
}

var limit = null;
com.increase.cen.user.AddUser = Ext.extend(Ext.form.FormPanel, {
	bodyStyle : "padding:5px;",
	frame : true,
	bodyBorder : false,
	labelAlign : "right",
	buttonAlign : 'center',
	labelWidth : 100,
	width : 600,
	height : 300,
	initComponent : function(config) {
		// 一页显示多少条数据
		limit = Ext.getDom("limit").value;
		// 启动悬停提示
		Ext.QuickTips.init();
		// 提示的方式
		Ext.form.Field.prototype.msgTarget = 'qtip';
		// 用户信息是否存在
		var idIsExist = true;
		// 是否批量增加
		this.batchAdd = new Ext.form.Checkbox({
			id : 'batchAdd',
			inputValue : 'yes',
			boxLabel : '批量录入',
			listeners : {
				'check' : function() {
					// 批量添加按钮
					var btnAdd = Ext.getCmp("btnAdd");
					// 保存按钮
					var btnSubmit = Ext.getCmp("btnSubmit");
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

		// 用户名
		this.username = new Ext.form.TextField({
			id : "addUsername",
			name : "newUser.username",
			fieldLabel : "用户名",
			allowBlank : false,// 验证是否为空
			blankText : '用户名不能为空',
			width : 200,
			emptyText : "用户名可以为字母或数字",
			regex:/[a-zA-z0-9\u4E00-\u9FA5]*/,
			regexText : '请输入正确用户名',
			invalidText : '用户名已存在！',
			validationEvent : 'keydown',
			validationDelay : 100,
			validator : function(thisText) {
				// 没有输入，不验证
				if (thisText == "") {
					return true;
				}
				Ext.Ajax.request({
					url : 'user!verifyUser.action',
					method : 'post',
					async:false,
					params : {
						'newUser.username' : thisText
					},
					success : function(response) {
						var res = Ext.util.JSON.decode(response.responseText);
						var msg = res.success;
						if (msg == false) {
							Ext.getCmp('addUsername').markInvalid('用户名已存在!');
							idIsExist=false;
						}else{
							Ext.getCmp('addUsername').clearInvalid();
							idIsExist=true;
						}
					}
				});
				return idIsExist;
			}
		});

		// 所属地区
		this.areaname = new Ext.form.TextField({
			id : "addAreaname",
			name : "newUser.areaname",
			fieldLabel : "所属地区",
			// hideTrigger:true,
			editable : false,// 设置为false以阻止用户直接向该输入项输入文本
			allowBlank : false,
			blankText : '',
			mode : "remote",// 加载本地数据 设置为'local' (默认值为 'remote'，它将从服务器加载数据)
			triggerAction : "all",// 下拉框获得了焦点或者被点击了
			emptyText : "",
			width : 200,
			readOnly : true
		});
		
		
		this.areano = new Ext.form.Hidden({
			id : "addAreano",
			name : "newUser.areano"
		});
		
		// 姓名
		this.realname = new Ext.form.TextField({
			id : 'addRealname',
			name : "newUser.realname",
			fieldLabel : "姓名",
			allowBlank : false,
			blankText : '姓名不能为空',
			emptyText : "请输入用户姓名",
			maxLength : 15,
			maxLengthText : '温馨提示：最大长度只能为15个字符',
			width : 200
		});
		// 密码
		this.password = new Ext.form.TextField({
			id : 'adsPassword',
			name : 'newUser.password',
			fieldLabel : '密码',
			inputType : 'password',
			allowBlank : false,
			blankText : '密码不能为空',
			emptyText : "请输入用户密码",
			maxLength : 15,
			maxLengthText : '温馨提示：最大长度只能为15个字符',
			width : 200
		});
		// 电话
		this.tel = new Ext.form.TextField({
			name : "newUser.phoneNumber",
			fieldLabel : "绑定号码",
			emptyText : "电话如18612345678",
			allowBlank : false,
			blankText : '请输入联系电话',
			width : 200,
			regex : /(^[0-9]{3,4}\-[0-9]{7,8}$)|(^[0-9]{7,8}$)|(^\([0-9]{3,4}\)[0-9]{3,8}$)|(^0{0,1}1[0-9]{10}$)/,
			regexText : '电话输入错误，请重新输入'
		});
		this.groupStr = commCombo("/maintainGroupAction!getMtGoup.action?domainCode="+domainCode,.33,100,'groupStr','所属班组','remote','id','name',null); 
		
		this.items = [{
			xtype : 'panel',
			bodyStyle : 'padding-left:78%;',
			items : [this.batchAdd]
		}, {
			xtype : 'fieldset',
			title : '用户信息',
			collapsible : true,
			items : [this.areano, this.areaname, this.username, this.realname,
					this.password, this.tel,this.groupStr]
		}];

		// 按钮
		this.buttons = [{
			id : 'btnSubmit',
			text : "保存",
			hidden : false,
			icon : "imgs/save_btn.png",
			cls : "x-btn-text-icon",
			tooltip : '保存用户信息',
			tooltipType : 'qtip',
			handler : this.btnSubmit.createDelegate(this)
		}, {
			id : 'btnAdd',
			hidden : true,
			text : '继续添加',
			tooltip : '继续添加用户信息',
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
			setValue : function(v) {
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
		com.increase.cen.user.AddUser.superclass.initComponent.call(this);
	},
	// 保存按钮事件
	btnSubmit : function() {
		if (!this.getForm().isValid()) {
			return;
		}
		var groupId = Ext.getCmp('groupStr_id').getValue();
		var groupName = Ext.getCmp('groupStr_id').getRawValue();
		if(groupId == null || groupId ==''){
			alert("请选择相应班组!");
			return ;
		}
		this.getForm().doAction("submit", {
			url : 'user!addUser.action',
			method : 'post',
			params : {groupName:groupName,groupId:groupId},
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
				Ext.getCmp("addUser").getForm().reset();// 重置表单
				Ext.getCmp('addUserWindow').hide();// 窗口隐藏
				Ext.getCmp('userList').getStore().load({
					params : {
						start : 0,
						limit : limit,
						"user.username" : "",
						"user.realname" : "",
						"user.areano" : Ext.getDom("domainCode").value
					}
				});// 表格加载数据
				Ext.getCmp('userList').getView().refresh();// 表格刷新
			},
			failure : function(form, action) {
				Ext.Msg.show({
					title : '提示',
					width : 300,
					msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
							+ '添加用户信息失败',
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
		Ext.getCmp("addUser").getForm().reset();
		// 窗口隐藏
		Ext.getCmp('addUserWindow').hide();
		Ext.getCmp("userList").getStore().load({
			params : {
				start : 0,
				limit : limit,
				"user.username" : "",
				"user.realname" : "",
				"user.areano" : Ext.getDom("domainCode").value
			}
		});
	},
	// 批量新增
	btnAdd : function() {
		// 批量添加复选框无效
		Ext.getCmp("batchAdd").disable();
		if (!this.getForm().isValid()) {
			return;
		}
		this.getForm().doAction("submit", {
			url : 'user!addUser.action',
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
				Ext.getCmp("addUser").getForm().reset();
				// 加载下拉列表数据
				Ext.getCmp("addUser").store.reload();
				// 复选框选中
				Ext.getCmp("batchAdd").setValue(true);
			},
			failure : function(form, action) {
				Ext.Msg.show({
					title : '提示',
					width : 300,
					msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
							+ '添加用户信息失败',
					buttons : {
						ok : "确定"
					}
				});
			}
		});
	}
});
Ext.reg("addUser", com.increase.cen.user.AddUser);
