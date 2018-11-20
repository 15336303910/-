Ext.namespace("com.increase.cen.domain");
// 一页的记录数
com.increase.cen.domain.ModifyDomain = Ext.extend(Ext.form.FormPanel, {
	bodyStyle : "padding:5px;",
	frame : true,
	bodyBorder : false,
	labelAlign : "right",
	buttonAlign : 'center',
	labelWidth : 65,
	width : 300,
	height : 300,
	initComponent : function(config) {
		// 启动悬停提示
		Ext.QuickTips.init();
		// 提示的方式
		Ext.form.Field.prototype.msgTarget = 'qtip';
		// 验证地区信息是否存在
		var userExist = false;
		var idIsExist = true;
		this.domainId = new Ext.form.Hidden({
			id : "domainId",
			name : "updateDomain.domainId",
			value : ""
		});

		this.nodeId = new Ext.form.Hidden({
			id : "nodeId",
			value : ""
		});
		// 所属地区
		this.parentName = new Ext.form.TextField({
			id : "parentName",
			name : "updateDomain.parentName",
			fieldLabel : "所属地区",
			// hideTrigger:true,
			editable : false,// 设置为false以阻止用户直接向该输入项输入文本
			allowBlank : false,
			mode : "remote",// 加载本地数据 设置为'local' (默认值为 'remote'，它将从服务器加载数据)
			triggerAction : "all",// 下拉框获得了焦点或者被点击了
			width : 300,

			disabled : true
		});

		// 地区名称
		this.domainName = new Ext.form.TextField({
			id : "domainName",
			name : "updateDomain.domainName",
			fieldLabel : "地区名称",
			allowBlank : false,// 验证是否为空
			blankText : '地区名称不能为空',
			width : 300,
			emptyText : "请输入添加的地区名称",
			// regex:/(^[0-9]{1,}$)/,
			regexText : '请输入添加的地区名称',
			invalidText : '地区已存在！',
			validationEvent : 'keydown',
			validationDelay : 100,
			mode : "remote",
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
						'newDomain.domainId' : Ext.getCmp("domainId").value
					},
					success : function(response) {
						var res = Ext.util.JSON.decode(response.responseText);
						var msg = res.success;
						if (msg == false) {
							Ext.getCmp('domainName').markInvalid('地区名称已存在!');
						}
					}
				});
				return idIsExist;
			}
		});
		var store = new Ext.data.SimpleStore({
			fields : ['responsibleid', 'responsiblename'],
			data:['','']
		})
		
		this.responsiblename = new Ext.form.ComboBox({
			id:'responsibleid',
			width : 300,
			fieldLabel : '负责人',
			emptyText : '请选择地区负责人',
			mode:"local",//加载本地数据 设置为'local' (默认值为 'remote'，它将从服务器加载数据) 
			triggerAction:"all",//下拉框获得了焦点或者被点击了
			store : store,
			valueField : 'responsibleid',
			displayField : 'responsiblename',
			hiddenName:'updateDomain.responsibleid',//创建一个表单隐藏域来存储输入项的值
			editable:false,//设置为false以阻止用户直接向该输入项输入文本	
			//forceSelection : true	
			listeners : {
				"select" : function() {
					Ext.Ajax.request({
						url:'domain!searchResponsibleUser.action',
						method:'post',
						async:false,
						params:{'loadDomain.responsibleid':Ext.getCmp("responsibleid").getValue()},
						success:function(response){
							var res = Ext.util.JSON.decode(response.responseText);
							Ext.getCmp("username").setValue(res.username);
							Ext.getCmp("userType").setValue(res.usertype);
							Ext.getCmp("tal").setValue(res.phoneNumber);
							Ext.getCmp("userinfo").show();
							
						}
					});
				}
			}
		});
	
		this.username = new Ext.form.TextField({
			id:"username",
			width : 300,
			readOnly :true,
			fieldLabel : "用户名"
		});
		this.tal = new Ext.form.TextField({
			id:"tal",
			width : 300,
			readOnly :true,
			fieldLabel : "电话"
		})
		this.userType = new Ext.form.TextField({
			id:"userType",
			width : 300,
			readOnly :true,
			fieldLabel : "角色名称"
		})
		this.items = [{
			xtype : 'fieldset',
			title : '基本信息',
			collapsible : true,
			items : [this.domainId, this.parentName, this.domainName,
					this.responsiblename]
		},{
			xtype : 'fieldset',
			id:"userinfo",
			title : '负责人信息',
			hidden:true,
			collapsible : true,
			items : [this.username,this.userType,this.tal]
		}];
		// 按钮
		this.buttons = [{
			text : "保存修改",
			icon : "imgs/save_btn.png",
			cls : "x-btn-text-icon",
			tooltip : '修改地区信息',
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
		com.increase.cen.domain.ModifyDomain.superclass.initComponent
				.call(this);
	},
	// 提交事件
	btnSubmit : function submitForm() {
		if (!this.getForm().isValid()) {
			return;
		}
		this.getForm().doAction("submit", {
			url : 'domain!updateDomainBean.action',
			submitEmptyText : false,
			method : 'post',
			success : function(form, action) {
				Ext.Msg.show({
					title : '提示',
					width : 300,
					msg : '<img src="imgs/tip_success.png" align="center" hspace="30"/>'
							+ '修改地区信息成功!',
					buttons : {
						ok : "确定"
					}
				});
				// 重置表单

				// 窗口隐藏
				Ext.getCmp('modifyDomainWindow').hide();

			},
			failure : function(form, action) {
				Ext.Msg.show({
					title : '提示',
					width : 300,
					msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
							+ '修改地区信息失败!',
					buttons : {
						ok : "确定"
					}
				});
			}
		});
	},
	// 关闭
	btnClose : function() {
		// 窗口隐藏
		Ext.getCmp('modifyDomainWindow').hide();
		// 刷新表格
	}
});
Ext.reg("modifyDomain", com.increase.cen.domain.ModifyDomain);

// 添加地区
function updateDomain(node) {
	var pnCenter = Ext.getCmp("pnCenter");
	if (!pnCenter.modifyDomainWindow) {
		pnCenter.modifyDomainWindow = new com.increase.cen.domain.domainWindow({
			id : "modifyDomainWindow",
			title : '编辑地区',
			iconCls : 'i-script-window',
			items : [{
				id : "modifyDomain",
				xtype : 'modifyDomain'// 窗口里的表单
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
	Ext.getCmp("modifyDomain").getForm().load({
		url : 'domain!loadDomain.action',
		params : {
			'loadDomain.domainId' : node.id
		},
		success : function(form, action) {
			var userData = Ext.util.JSON.decode(action.response.responseText).data
			var domains = userData.domains;
			 var sdata = [];
			 var resLen = domains.length;
			 for (var i = 0; i < resLen; i++) {
				var d = domains[i];
			 	var chk = [d.responsibleid,d.responsiblename]
				sdata.push(chk);
			 }
			 var OrgStore = new Ext.data.SimpleStore({
			 	fields : ['responsibleid', 'responsiblename'],
			 	data :sdata
			 });
			
			Ext.getCmp("responsibleid").getStore().removeAll();
			//Ext.getCmp("responsibleid").store = OrgStore;
			Ext.getCmp("responsibleid").getStore().loadData(sdata);
			if(userData.responsibleid == null || ''== userData.responsibleid){
				Ext.getCmp("userinfo").hide()
			}else{
				Ext.getCmp("responsibleid").setValue(userData.responsibleid);
				Ext.getCmp("username").setValue(userData.responsibleusername);
				//Ext.getCmp("userType").setValue(userData.responsibleUserType);
				Ext.getCmp("userType").setValue(userData.roleName);
				Ext.getCmp("tal").setValue(userData.responsiblephone);
				Ext.getCmp("userinfo").show();
			}
			
			
		}
	});
	pnCenter.modifyDomainWindow.show();
}