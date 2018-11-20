Ext.namespace("com.increase.cen.domain");
// 一页的记录数

com.increase.cen.domain.DeleteDomain = Ext.extend(Ext.form.FormPanel, {
	bodyStyle : "padding:5px;",
	frame : true,
	bodyBorder : false,
	labelAlign : "right",
	buttonAlign : 'center',
	labelWidth : 65,
	width : 300,
	height : 200,
	initComponent : function(config) {
		// 启动悬停提示
		Ext.QuickTips.init();
		// 提示的方式
		Ext.form.Field.prototype.msgTarget = 'qtip';
		// 验证用户信息是否存在
		var userExist = false;

		// 用户名
		this.username = new Ext.form.TextField({
			name : "confirmDelete",
			allowBlank : false,
			blankText : '请输入确认删除',
			emptyText : "请输入确认删除",
			width : 200

		});
		this.domainId = new Ext.form.Hidden({
			id : 'delDomainId',
			name : "delDomain.domainId"

		})
		this.domainCode = new Ext.form.Hidden({
			id : "delDomainCode",
			name : "delDomain.domainCode"
		})
		this.items = [{
			xtype : 'fieldset',
			title : '确认删除',
			collapsible : true,

			items : [{
				id : "confirmDel",
				html : "<font id='updatehtml' size='3px' color='#15428b'>&nbsp;&nbsp;&nbsp;&nbsp;您确定要删除该地区，删除后，"
						+ "该地区下的所有地区和用户将一并被删除，并且该操作不可恢复，请在下面输入框中输入 “确认删除”。</font>"
			}, this.username, this.domainId, this.domainCode]
		}];
		// 按钮
		this.buttons = [{
			text : "确认删除",
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
			text : "取消",
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
		com.increase.cen.domain.DeleteDomain.superclass.initComponent
				.call(this);
	},
	// 提交事件
	btnSubmit : function submitForm() {
		if (!this.getForm().isValid()) {
			return;
		}
		this.getForm().doAction("submit", {
			url : 'domain!deleteDomain.action',
			submitEmptyText : false,
			method : 'post',
			success : function(form, action) {
				var json = Ext.util.JSON.decode(action.response.responseText);
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
								+ '删除地区信息失败!',
						buttons : {
							ok : "确定"
						}
					});
				}
				Ext.getCmp("deleteDomain").getForm().reset();
				// 窗口隐藏
				Ext.getCmp('deleteDomainWindow').hide();

			},
			failure : function(form, action) {
				Ext.Msg.show({
					title : '提示',
					width : 300,
					msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
							+ '删除地区失败!',
					buttons : {
						ok : "确定"
					}
				});
			}
		});
	},
	// 关闭
	btnClose : function() {
		// 重置表单
		Ext.getCmp("deleteDomain").getForm().reset();
		// 窗口隐藏
		Ext.getCmp('deleteDomainWindow').hide();

	}
});
Ext.reg("deleteDomain", com.increase.cen.domain.DeleteDomain);

// 添加地区
function delDomain(node) {
	var pnCenter = Ext.getCmp("pnCenter");
	if (!pnCenter.deleteDomainWindow) {
		pnCenter.deleteDomainWindow = new com.increase.cen.domain.domainWindow({
			id : "deleteDomainWindow",
			title : '删除地区',
			iconCls : 'i-script-window',
			items : [{
				id : "deleteDomain",
				xtype : 'deleteDomain'// 窗口里的表单
			}],
			listeners : {
				"hide" : function() {
					Ext.getCmp("pnWest").body
							.update("<iframe id='domainTreeIframe' src='pages/domain/domainTree.jsp' name='domainTree' width='500' height='100%' frameborder='no' border='0' marginwidth='0' marginheight='0' align='center'></iframe>");
				}
			}
		});
	}

	// document.getElementById("updatehtml").html="确定要删除地区"+node.text;
	Ext.getCmp("delDomainId").setValue(node.id);
	var code = node.attributes.domainCode;
	Ext.getCmp("delDomainCode").setValue(code);
	pnCenter.deleteDomainWindow.show("");
}