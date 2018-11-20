Ext.onReady(function() {
	Ext.data.Connection.prototype.timeout ='300000';
	
	var root = new Ext.tree.AsyncTreeNode({
		 id:"root",
		 text :resName,
		 nodeType: 'async',
		 draggable : false,
		 expanded : false
	});
	
	
	var tree = new Ext.tree.TreePanel({
       border : false,
       autoScroll:true,
       height:'100%',
       root : root,
       listeners:{
       	'beforeload':function(node){
       		nodeType = node.attributes.resType;
           	if(nodeType == null || nodeType == ""){
           		if(resType == 'optical'){
           			resType ='optical';
           		}else{
           			resType ='station';
           		}
           	}else{
           		resId = node.id;
           		resType = node.attributes.resType;
           	}
           	//如果是站点
       		var url; 
       		if(resType =='station'){
       			url= context_path+"/pages/approval/site/siteInfo.jsp?id="+resId+"&resType="+resType;
       			Ext.get('mainPanel').dom.src = url;
       		}
       		if(resType =='optical'){
       			url= context_path+"/pages/approval/site/siteInfo.jsp?id="+resId+"&resType="+resType;
       			Ext.get('mainPanel').dom.src = url;
       		}
       		node.loader = new Ext.tree.TreeLoader({url:context_path+'/approvalAction!getResTree.action?',
       			baseParams:{parentId: resId, parentType:resType} });
       	},
       	click:function(node,e){
       		var nodeType = node.attributes.resType;
       		var url;
       		if(nodeType == undefined){
       			if(resType == 'optical'){
       				url= context_path+"/pages/approval/site/siteInfo.jsp?parentId="+resId+"&parentType=optical";
           			Ext.get('mainPanel').dom.src = url;
       			}else{
       				url= context_path+"/pages/approval/site/siteInfo.jsp?parentId="+resId+"&parentType=station";
           			Ext.get('mainPanel').dom.src = url;
       			}
       		}
       		if(nodeType == 'generator'){
       			url= context_path+"/pages/approval/site/siteInfo.jsp?parentId="+node.id+"&parentType=generator";
       			Ext.get('mainPanel').dom.src = url;
       		}
       		if(nodeType == 'rack'){
       			url= context_path+"/pages/approval/site/siteInfo.jsp?parentId="+node.id+"&parentType=rack";
       			Ext.get('mainPanel').dom.src = url;
       		}
       		
       		
       	}
       }
	});
	
	new Ext.Viewport({ 
		id:'siteView',
		layout:'border',
		items:[
		    {
		    	title : '站内资源',
                region:'west',   
                contentEl: 'leftDiv',  
                split:true,   
                width: '15%',  
                height:'100%',
                items : tree, 
                titlebar: false,   
                collapsed : true,
                collapsible: true 
		    }, {   
                region:'center',
                title:'资源拓扑',
                contentEl: 'centerDiv',   
                html:'<iframe scrolling="auto" id="mainPanel" width="100%" height="100%" frameBorder=0 src='+context_path+"/pages/approval/site/siteInfo.jsp?id="+resId+"&resType="+resType+'></iframe>',
                border:false, 
                split:true,   
                margins:'0 0 0 0',   
                collapsible : true,   
                titlebar: false,   
                animate: true   
            }
		]
	});
	
});