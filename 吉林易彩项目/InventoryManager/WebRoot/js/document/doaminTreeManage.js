Ext.onReady(function() {
	// new com.strong.bussinessTong.Tree();
	new Ext.Viewport({
		width : 150,
		layout : 'accordion',
		border : false,
		items : [{
			width : 150,
			title : "地区列表",
			autoScroll:true,
			border : false,
			items : [{
				xtype : "domainTree",
				dataUrl : 'domain!loadDomainTree.action?searchDomain.manage=documentManage'
			}]
			
		},{
			width : 150,
			title : "地区搜索",
			border : false,
			items : [{
				border:false,
				items:[{
					xtype : "domainSearchForm",
					btnSubmit : function() {
						if (!this.getForm().isValid()) {
							return;
						}
						this.getForm().doAction("submit", {
							url : 'domain!searchDomainTree.action?searchDomain.manage=documentManage',
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
					}
				}]
			},{
				xtype : "treepanel",
				id : "domainSearchResultTree",
				renderTo : 'domainResultTree',
				border:false,
				root : {
					id : 'resultTreeRoot',
					text : '搜索结果',
					leaf : false,
					expanded:true,
					children:[]
				},
				//rootVisible : false	,
				listeners : {
					'click' : function(node, e) {
						if(node.id !='resultTreeRoot'){
							e.stopEvent();
							var tab = parent.Ext.getCmp("pnCenter").getComponent(node.id);
							if (!tab) {
								tab = parent.Ext.getCmp("pnCenter").add({
								id : node.id,
								title : node.text,
								closable : true,
								html : '<iframe name="userList'
									+ node.id+""
									+'" scrolling="auto" frameborder="0" width="100%" height="100%" src="'
									+ node.attributes.href + '"></iframe>'
								});
							}
								parent.Ext.getCmp("pnCenter").setActiveTab(tab);
						}
					}
				}
			}]
		}]
	});

});