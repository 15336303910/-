Ext.namespace("com.increase.cen.domain");
com.increase.cen.domain.DomainSearchForm = Ext.extend(Ext.form.FormPanel, {
	id : 'domainSearchForm',
	xtype : "form",
	labelWidth : 78,
	labelAlign : "left",
	layout : "form",
	border:false,
	frame : true,
	initComponent : function(config) {
		// 启动悬停提示
		Ext.QuickTips.init();
		this.items = [{
			xtype : "textfield",
			id:"searchDomain.domainName",
			name : "searchDomain.domainName",
			fieldLabel : "地区名称",
			anchor : "100%",
			allowBlank : false,
			blankText : '地区名称不能为空',
			emptyText : "请输入地区名称",
			maxLength : 15,
			maxLengthText : '温馨提示：最大长度只能为15个字符'
		}];
		
		this.buttons = [{
			id : 'btnSubmit',
			text : "搜索",
			hidden : false,
			// icon : "imgs/save_btn.png",
			//cls : "x-btn-text-icon",
			tooltip : '搜索地区',
			tooltipType : 'qtip',
			handler : this.btnSubmit.createDelegate(this)
		}, {
			id : 'btnClear',
			hidden : false,
			text : '清空',
			tooltip : '清空表单',
			tooltipType : 'qtip',
			// icon : "imgs/form_add.png",
			//cls : "x-btn-text-icon",
			handler : this.btnClear.createDelegate(this)
		}];
		com.increase.cen.domain.DomainSearchForm.superclass.initComponent.call(this);
	},
	btnSubmit : function() {
		if (!this.getForm().isValid()) {
			return;
		}
		this.getForm().doAction("submit", {
			url : 'domain!searchDomainTree.action',
			method : 'post',
			submitEmptyText : false,
			success : function(form, action) {
				var json = action.result.domianTrees;
				var rootTree = Ext.getCmp('domainSearchResultTree');
				var rootNode = rootTree.getRootNode();
				rootNode.setText("搜索结果");
				rootNode.removeAll();
				rootNode.appendChild(json);
				rootNode.expand();
			},
			failure:function(form, action) {
				
				var rootTree = Ext.getCmp('domainSearchResultTree');
				var rootNode = rootTree.getRootNode();
				rootNode.removeAll()
				var s = '未搜索到 " '+Ext.getCmp('searchDomain.domainName').getValue()+' " 相关地区!'
				rootNode.setText(s);
				
			}
		});
	},
	btnClear : function() {
		Ext.getCmp('domainSearchForm').form.reset();
	}

});
Ext.reg("domainSearchForm", com.increase.cen.domain.DomainSearchForm);