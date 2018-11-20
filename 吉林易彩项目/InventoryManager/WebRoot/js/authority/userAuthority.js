Ext.onReady(function(){
	var treeLoader = new Ext.tree.TreeLoader({dataUrl: context_path+"/authorityAction!getCompJson.action?"});
	var root=new Ext.tree.AsyncTreeNode({id:"root",text:"地区管理"});
	var tree = new Ext.tree.TreePanel({  
		root:root,
		loader: treeLoader,
		width:200,
		listeners:{
			click:function(node,event){
				if(node.id =='root'){
					return;
				}
				if(node.Url =='comp'){
					event.stopEvent();
    				Ext.get('mainPanel').dom.src = context_path+"/authorityAction!searchUser.action?type=comp&company="+node.id;
            		return;  
				}else{
					event.stopEvent();
    				Ext.get('mainPanel').dom.src = context_path+"/authorityAction!searchUser.action?type=dept&dept="+node.id;
            		return;
				}
			},
			contextmenu:function(node,event){
				event.preventDefault();
			}
		}
	});  
	tree.on('beforeload',function(node){
		tree.loader.dataUrl= context_path+'/authorityAction!getCompJson.action?&id='+ node.id;
	});
	
	var viewport = new Ext.Panel({   
		renderTo:Ext.getBody(),
		height:document.body.clientHeight,
        layout:'border',
        items:[ 
			{   
                title : '选择部门',
                region:'west',   
                items : tree, 
                split:true,   
                width: 200,  
                autoScroll: true,
                collapsible: true   
            },
            {   
                region:'center',   
                html:'<iframe scrolling="auto" id="mainPanel" width="100%" height="100%" frameBorder=0></iframe>',
                border:false, 
                split:true,   
                margins:'0 0 0 0',   
                collapsible : true,   
                titlebar: false,   
                animate: true   
            }]   
 	}); 
	
});