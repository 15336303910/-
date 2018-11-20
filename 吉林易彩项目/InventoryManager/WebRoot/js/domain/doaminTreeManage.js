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
				dataUrl : 'domain!loadDomainTree.action?searchDomain.manage=userManage'
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
							url : 'domain!searchDomainTree.action?searchDomain.manage=userManage',
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
	var currentNode;
	// 定义右键菜单
	var rightClick = new Ext.menu.Menu({
		id : 'rightClickCont',
		width : 85,
		items : [{
			id : 'rMenu1',
			icon : 'js/ext/resources/images/yourtheme/dd/drop-add.gif',
			text : '添加地区',
			// 增加菜单点击事件
			handler : function(node) {
				var newNode =  new Ext.data.Node({
					text:"请输入地区名称",
					icon: "imgs/domain.gif",
					leaf:true
				});
				currentNode.appendChild(newNode)
				parent.addDomain(currentNode);
			}
		}, {
			id : 'rMenu3',
			icon : 'js/ext/resources/images/yourtheme/dd/edit.gif',
			text : ' 编辑地区',
			// 增加菜单点击事件
			handler : function(node) {
				parent.updateDomain(currentNode);
			}
		}, {
			id : 'rMenu2',
			icon : 'imgs/no.png',
			text : ' 删除地区',
			// 增加菜单点击事件
			handler : function(node) {
				parent.delDomain(currentNode);
			}
		}]

	});
	var dTree = Ext.getCmp("domainTree");
	dTree.on('contextmenu', function(node, event) {// 声明菜单类型
				currentNode = node;
				event.preventDefault();
				rightClick.showAt(event.getXY());// 取得鼠标点击坐标，展示菜单
	});


});