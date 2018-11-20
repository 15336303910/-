var store;
var listGrid;
var thisPagesize = 15;
var autoHeight = false;
var siteText;
Ext.onReady(function() {
	Ext.data.Connection.prototype.timeout ='300000';
	var root = new Ext.tree.AsyncTreeNode({
		 id:"root",
		 text : '资源站点',
		 nodeType: 'async',
		 draggable : false,
		 expanded : false
	});
	var tree = new Ext.tree.TreePanel({
        border : false,
        autoScroll:true,
        height:500,
        root : root,
        listeners:{
        	'beforeload':function(node){
        		var nodeType = node.attributes.resType;
            	var resId ,resType;
            	if(nodeType == null || nodeType == ""){
            		resId = id;
            		resType ='station';
            	}else{
            		resId = node.id;
            		resType = node.attributes.resType;
            	}
        		node.loader = new Ext.tree.TreeLoader({url:context_path+'/approvalAction!getResTree.action?',
        			baseParams:{parentId: resId, parentType:resType} })
        	},
        	click:function(node,e){
        		var nodeType = node.attributes.resType;
        		var url;
        		if(nodeType == undefined){
        			url = context_path+"/pages/approval/resPoint.jsp?id="+id+"&resType=station&grapType=point";
        		}else{
        			url = context_path+"/pages/approval/resPoint.jsp?id="+node.id+"&resType="+nodeType+"&grapType=point";
        		}
        		Ext.get('mainPanel').dom.src = url;
        	}
        }
	});
	var viewport = new Ext.Viewport({   
        layout:'border',
        height:500,
        items:[{   
                title : '功能菜单',
                region:'west',   
                contentEl: 'leftDiv',  
                items : tree, 
                split:true,   
                width: 200,  
                height:500,
                autoScroll:true,
                titlebar: false,   
                collapsible: true   
            },
            {   
                region:'center',   
                contentEl: 'rightDiv', 
                html:'<iframe scrolling="auto" id="mainPanel" width="100%" height="100%" frameBorder=0></iframe>',
                border:false, 
                split:true,   
                margins:'0 0 0 0',   
                collapsible : true,   
                titlebar: false,   
                animate: true   
         }],
         listeners:{
        	beforeRender:function(){
        		Ext.Ajax.request({
        			url: context_path+ "/approvalAction!getSiteTree.action?",
        			params: {
        				siteId:id,type:'station'
        			},
        			success: function(resp) {
        				siteText = Ext.util.JSON.decode(resp.responseText);
        				root.setText(siteText.stationName);
        			},
        			failure: function(resp) {
        				Ext.Msg.alert("系统提示", "加载信息超时，请刷新页面后重试....");
                	}
        		});
        	}
         }
	}); 
});