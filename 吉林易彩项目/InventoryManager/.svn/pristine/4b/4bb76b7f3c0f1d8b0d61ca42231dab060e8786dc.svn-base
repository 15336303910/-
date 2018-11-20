Ext.namespace("com.increase.cen");
var index = 0;
com.increase.cen.Tree = Ext.extend(Ext.tree.TreePanel, {
	id:"domainTree",
	border : false,
	autoScroll : true,
	animate : true, // 动画效果
	renderTo : 'tree',
	root : {
		id : 'rootNode',
		text : '部门',
		expanded : false,// true将开始展开节点
		leaf : false
	},
	rootVisible : false,// false将隐藏root节点
	expanded : false,// true将开始展开节点
	listeners : {
		"load" : function() {
			/*
			 *  this.collapseAll();
			 */
			var node = this.getRootNode().item(0);
			var tab = parent.Ext.getCmp("pnCenter").getComponent(node.id);
			if (!tab && index==0) {
				
				index++;
					tab = parent.Ext.getCmp("pnCenter").add({
						id : node.id,
						title : node.text,
						closable : true,
						html : '<iframe name="userList'
								+ node.id+""
								+'" scrolling="auto" frameborder="0" width="100%" height="100%" src="'
								+ node.attributes.href + '"></iframe>'
					});
					parent.Ext.getCmp("pnCenter").setActiveTab(tab);
				}
				
		},
		'click' : function(node, e) {
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
				//parent.document.frames(node.text).location.reload()

		}
	},
	initComponent : function(config) {
		this.sortTree = new Ext.tree.TreeSorter(this, {
			folderSort : true,
			property : "id"
		});

		var contextmenu = new Ext.menu.Menu({
			id : 'Menu',
			items : [{
				text : '修改',
				handler : function(node, e) {
					alert(currentnode.id);
				}
			}]
		});
		
		
		com.increase.cen.Tree.superclass.initComponent.call(this);
	}
});
Ext.reg("domainTree", com.increase.cen.Tree);