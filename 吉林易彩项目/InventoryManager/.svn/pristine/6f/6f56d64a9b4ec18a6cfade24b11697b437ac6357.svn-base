Ext.namespace("com.increase.cen.domain");
var citems = [];
var columns = 3;
Ext.Ajax.request({
	url : 'user!searchPowerstr.action',
	sync : true,
	success : function(response, opts) {
		citems = [];
		var res = Ext.util.JSON.decode(response.responseText);
		var resLen = res.length;
		// alert(resLen);
		columns = getPageAmount(resLen, '3');
		var i = 0;
		for (i = 0; i < res.length; i++) {
			var d = res[i];
			var chk = {
				id : d.id,
				boxLabel : d.name,
				name : 'powerstr',
				inputValue : d.code,
				checked : true
			};
			citems[i] = chk;
			// citems.push(chk);
		}

	}

});
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
// 计算
function getPageAmount(exp1, exp2) {
	if (exp1 % exp2 == 0) {
		return Div(exp1, exp2);
	} else {
		return Div(exp1, exp2) + 1;
	}
}


com.increase.cen.domain.AddDomain = Ext.extend(Ext.form.FormPanel, {
	id : "addDomain",
	bodyStyle : "padding:5px;",
	frame : true,
	bodyBorder : false,
	labelAlign : "right",
	buttonAlign : 'center',
	//labelWidth : 100,
	width : 400,
	height : 400,
	initComponent : function(config) {
		// 一页显示多少条数据
		// 启动悬停提示
		Ext.QuickTips.init();
		// 提示的方式
		Ext.form.Field.prototype.msgTarget = 'qtip';
		// 地区信息是否存在
		var idIsExist = true;

		this.parentId = new Ext.form.Hidden({
			id : "newParentId",
			name : "newDomain.parentId",
			value : ""
		});
		// 所属地区
		this.parentName = new Ext.form.TextField({
			id : "newParentName",
			fieldLabel : "所属地区",
			// hideTrigger:true,
			editable : false,// 设置为false以阻止用户直接向该输入项输入文本
			allowBlank : false,
			blankText : '',
			mode : "remote",// 加载本地数据 设置为'local' (默认值为 'remote'，它将从服务器加载数据)
			triggerAction : "all",// 下拉框获得了焦点或者被点击了
			emptyText : "",
			width : 160,
			readOnly : true
		});

		// 区号
		this.domainCode = new Ext.form.TextField({
			id : "newDomainCode",
			name : "newDomain.domainCode",
			fieldLabel : "地区区号",
			allowBlank : false,// 验证是否为空
			blankText : '请输入地区区号!区号不足4位请补0 如北京 0010',
			width : 160,
			emptyText : "请输入地区区号!区号不足4位请补0 如北京 0010",
			regex : /(^[0-9]{4}$)/,
			regexText : '请输入地区区号!区号不足4位请补0 如北京 0010',
			invalidText : '地区区号已存在！',
			validationEvent : 'keydown',
			validationDelay : 100,
			hidden : true,
			validator : function(thisText) {
				// 没有输入，不验证
				if (thisText == "") {
					return true;
				}
				Ext.Ajax.request({
					url : 'domain!verifyDomain.action',
					method : 'post',
					// async:false,
					params : {
						'newDomain.domainCode' : thisText,
						'newDomain.parentId' : Ext.getCmp("newParentId").value
					},
					success : function(response) {
						var res = Ext.util.JSON.decode(response.responseText);
						var msg = res.success;
						if (msg == false) {
							Ext.getCmp('newDomainCode').markInvalid('地区区号已存在!');
						}
					}
				});
				return idIsExist;
			}
		});

		// 地区名称
		this.domainName = new Ext.form.TextField({
			id : "newDomainName",
			name : "newDomain.domainName",
			fieldLabel : "地区名称",
			allowBlank : false,// 验证是否为空
			blankText : '地区名称不能为空',
			width : 160,
			emptyText : "请输入添加的地区名称",
			// regex:/(^[0-9]{1,}$)/,
			regexText : '请输入添加的地区名称',
			invalidText : '地区已存在！',
			validationEvent : 'keydown',
			validationDelay : 100,
			validator : function(thisText) {
				// 没有输入，不验证
				if (thisText == "") {
					return true;
				}
				Ext.Ajax.request({
					url : 'domain!verifyDomain.action',
					method : 'post',
					// async:false,
					params : {
						'newDomain.domainName' : thisText,
						'newDomain.parentId' : Ext.getCmp("newParentId").value
					},
					success : function(response) {
						var res = Ext.util.JSON.decode(response.responseText);
						var msg = res.success;
						if (msg == false) {
							Ext.getCmp('newDomainName').markInvalid('地区名称已存在!');
						}
					}
				});
				return idIsExist;
			}
		});

		this.row1 = {
			layout : 'column', // 从左往右布局
			items : [{
				columnWidth : .5, // 该列有整行中所占百分比
				layout : 'form', // 从上往下布局
				items : [this.parentId, this.parentName]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [this.domainCode]
			}]
		};
		this.row2 = {
			layout : 'column', // 从左往右布局
			items : [{
				columnWidth : .5, // 该列有整行中所占百分比
				layout : 'form', // 从上往下布局
				items : [this.domainName]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [{}]
			}]
		};

		this.row3 = {
			layout : 'column', // 从左往右布局
			items : [{
				columnWidth : .5, // 该列有整行中所占百分比
				layout : 'form', // 从上往下布局
				items : [{
					id : "addUsername",
					xtype : 'textfield',
					name : "newUser.username",
					fieldLabel : "用户名",
					allowBlank : false,// 验证是否为空
					blankText : '用户名不能为空',
					width : 160,
					emptyText : "用户名可以为字母或数字",
					regex : /[a-zA-z0-9\u4E00-\u9FA5]*/,
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
							// async:false,
							params : {
								'newUser.username' : thisText
							},
							success : function(response) {
								var res = Ext.util.JSON
										.decode(response.responseText);
								var msg = res.success;
								if (msg == false) {
									Ext.getCmp('addUsername')
											.markInvalid('用户名已存在!');
								}
							}
						});
						return idIsExist;
					}

				}]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [{
					id : 'addRealname',
					xtype : 'textfield',
					name : "newUser.realname",
					fieldLabel : "姓名",
					allowBlank : false,
					blankText : '姓名不能为空',
					emptyText : "请输入用户姓名",
					maxLength : 15,
					maxLengthText : '温馨提示：最大长度只能为15个字符',
					width : 160
				}]
			}]
		};
		this.row4 = {
			layout : 'column', // 从左往右布局
			items : [{
				columnWidth : .5, // 该列有整行中所占百分比
				layout : 'form', // 从上往下布局
				items : [{
					id : 'adsPassword',
					xtype : 'textfield',
					name : 'newUser.password',
					fieldLabel : '密码',
					inputType : 'password',
					allowBlank : false,
					blankText : '密码不能为空',
					emptyText : "请输入用户密码",
					maxLength : 15,
					maxLengthText : '温馨提示：最大长度只能为15个字符',
					width : 160

				}]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [{
					xtype : 'textfield',
					name : "newUser.phoneNumber",
					fieldLabel : "联系电话",
					emptyText : "电话如18612345678",
					allowBlank : false,
					blankText : '请输入联系电话',
					width : 160,
					regex : /(^[0-9]{3,4}\-[0-9]{7,8}$)|(^[0-9]{7,8}$)|(^\([0-9]{3,4}\)[0-9]{3,8}$)|(^0{0,1}1[0-9]{10}$)/,
					regexText : '电话输入错误，请重新输入'
				}]
			}]
		};
		
		// 用户类型
		this.row5 = new Ext.form.RadioGroup({
			name : "newUser.usertype",
			fieldLabel : "用户类型",
			width : 400,
			allowBlank : false,
			blankText : "请选择用户类型！",
			items : [{
				name : "newUser.usertype",
				inputValue : "1",
				boxLabel : "管理员",
				checked : true,   
				width : 40
				
			}, {
				name : "newUser.usertype",
				inputValue : "2",
				boxLabel : "施工人员"
			}, {
				name : "newUser.usertype",
				inputValue : "3",
				boxLabel : "维护人员"
			}, {
				name : "newUser.usertype",
				inputValue : "4",
				boxLabel : "查看人员"
			}, {
				name : "newUser.usertype",
				inputValue : "5",
				boxLabel : "代维人员"
			}],
			listeners : {
				change : function(radiofield, oldvalue) {// 这事件是当radiogroup的值发生改变时进入
					var v = radiofield.getValue();
					var array = Ext.getCmp('addPower').items;
					if (v == '1') {
						array.each(function(item){
		        			item.setValue(true);
		        		});

					} else if (v == '2') {
						array.each(function(item){
							if(item.inputValue=='11'||item.inputValue=='12'||item.inputValue=='21'||item.inputValue=='14'||item.inputValue=='15'){
								item.setValue(true);
							}else{
								item.setValue(false);
							}
		        			
		        		});
					}else if (v == '3') {
						array.each(function(item){
							
							if(item.inputValue=='11'||item.inputValue=='12'||item.inputValue=='21'||item.inputValue=='14'){
								item.setValue(true);
							}else{
								item.setValue(false);
							}
		        			
		        		});
					}else if (v == '4') {
						array.each(function(item){
							
							if(item.inputValue=='11'){
								item.setValue(true);
							}else{
								item.setValue(false);
							}
		        			
		        		});
					}else if (v == '5') {
						array.each(function(item){
							
							if(item.inputValue=='11'||item.inputValue=='14'||item.inputValue=='21'){
								item.setValue(true);
							}else{
								item.setValue(false);
							}
		        			
		        		});
					}
				}
			}
		});
		
		// 用户权限
		this.row6 = new Ext.form.CheckboxGroup({
			id : "addPower",
			xtype : 'checkboxgroup',
			allowBlank : false,
			blankText : "请选择用户权限",
			fieldLabel : '权限类别',
			name : 'newUser.powerstr',
			columns : columns,
			anchor : "95%",
			// msgTarget : "side",
			width : 400,
			items : citems
		});
		
		
		
		
		
		this.items = [{
			xtype : 'fieldset',
			title : '地区信息',
			collapsible : true,
			items : [this.row1, this.row2]
		}/*, {
			xtype : 'fieldset',
			title : '负责人',
			collapsible : true,
			items : [this.row3, this.row4,this.row5,this.row6]
		}*/];

		// 按钮
		this.buttons = [{
			id : 'btnSubmit',
			text : "保存",
			hidden : false,
			icon : "imgs/save_btn.png",
			cls : "x-btn-text-icon",
			tooltip : '保存地区信息',
			tooltipType : 'qtip',
			handler : this.btnSubmit.createDelegate(this)
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

		// 重写initComponent superclass.initComponent.call(this);来调用父类的初始化方法.
		com.increase.cen.domain.AddDomain.superclass.initComponent.call(this);
	},
	// 保存按钮事件
	btnSubmit : function() {
		if (!this.getForm().isValid()) {
			return;
		}
		this.getForm().doAction("submit", {
			url : 'domain!addDomain.action',
			method : 'post',
			submitEmptyText : false,
			success : function(form, action) {
				Ext.Msg.show({
					title : '提示',
					width : 300,
					msg : '<img src="imgs/tip_success.png" align="center" hspace="30" />'
							+ '添加地区信息成功!',
					buttons : {
						ok : "确定"
					}
				});

				Ext.getCmp("addDomain").getForm().reset();// 重置表单
				Ext.getCmp('addDomainWindow').hide();// 窗口隐藏
				// 刷新地区树
				Ext.getCmp("pnWest").body
						.update("<iframe id='domainTreeIframe' src='pages/domain/domainTree.jsp' name='domainTree' width='500' height='100%' frameborder='no' border='0' marginwidth='0' marginheight='0' align='center'></iframe>");
			},
			failure : function(form, action) {
				Ext.Msg.show({
					title : '提示',
					width : 300,
					msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
							+ '添加地区信息失败',
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
		Ext.getCmp("addDomain").getForm().reset();
		// 窗口隐藏
		Ext.getCmp('addDomainWindow').hide();
		// 刷新地区树
		Ext.getCmp("pnWest").body
				.update("<iframe id='domainTreeIframe' src='pages/domain/domainTree.jsp' name='domainTree' width='500' height='100%' frameborder='no' border='0' marginwidth='0' marginheight='0' align='center'></iframe>");

	}
});
Ext.reg("addDomain", com.increase.cen.domain.AddDomain);

// 添加地区
function addDomain(node) {
	var pnCenter = Ext.getCmp("pnCenter");
	if (!pnCenter.addDomainWindow) {
		pnCenter.addDomainWindow = new com.increase.cen.domain.domainWindow({
			id : "addDomainWindow",
			title : '添加地区',
			iconCls : 'i-script-window',
			items : [{
				id : "addDomain",
				xtype : 'addDomain'// 窗口里的表单
			}],
			listeners : {
				"hide" : function() {
					// 刷新地区树
					Ext.getCmp("pnWest").body
							.update("<iframe id='domainTreeIframe' src='pages/domain/domainTree.jsp' name='domainTree' width='500' height='100%' frameborder='no' border='0' marginwidth='0' marginheight='0' align='center'></iframe>");
				}
			}
		});
	}
	Ext.getCmp("addDomain").getForm().reset();
	Ext.getCmp("newParentId").setValue(node.id);
	Ext.getCmp("newParentName").setValue(node.text);
	var code = node.attributes.domainCode;
	if (code == '0') {
		Ext.getCmp("newDomainCode").enable();
		Ext.getCmp("newDomainCode").show();
	} else {
		Ext.getCmp("newDomainCode").hide();
		Ext.getCmp("newDomainCode").disable();
	}
	pnCenter.addDomainWindow.show("");
}
