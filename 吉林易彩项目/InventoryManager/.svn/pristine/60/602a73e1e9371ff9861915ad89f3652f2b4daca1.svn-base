var store;
var thisPagesize = 15;
var autoHeight = false;
Ext.onReady(function() {
	Ext.data.Connection.prototype.timeout ='300000';
	
	var title = itemText(.33,80,'title','标题');
	var stationName = itemText(.33,80,'stationName','站点名称');
	var queryPanel = new Ext.form.FormPanel({
		bodyStyle : 'padding-top:5px',
	    layout : "column",
	    id:'queryPanel',
		border : false,
		width:'100%',
		frame:true,
		items:[title,stationName
		]
	});
	var column = "[{header:'id',dataIndex:'id',hidden:true,width:'150',fun:null}," +
				 "{header:'标题',dataIndex:'title',hidden:false,width:'150',fun:null}," +
				 "{header:'派单人',dataIndex:'sender',hidden:false,width:'150',fun:null}," +
				 "{header:'所属站点',dataIndex:'stationName',hidden:false,width:'150',fun:null}," +
				 "{header:'站点类型',dataIndex:'stationType',hidden:false,width:'150',fun:null}," +
				 "{header:'服务区域',dataIndex:'serviceArea',hidden:false,width:'150',fun:null}," +
				 "{header:'处理人',dataIndex:'dealer',hidden:false,width:'150',fun:null}," +
				 "{header:'纬度',dataIndex:'latitudef',hidden:false,width:'150',fun:null}" +
				 "]";
	var result = basicStore(column,'authorityAction!getAuthUser.action','totalCount','root')
	store =result.store;
	var cm = result.cm;
	var sm = result.sm;
	
	store.load({params:{
 		start:0,limit:15,type:type,id:id
	}});
	
	var gridButtons = ['->','-',
	  {
    	id:'addTemple',
    	text:"新建",
    	iconCls:'add',
    	handler:function(){
    		var panel=Ext.getCmp('panelRight');
			try{
				panel.removeAll();
				panel.load({ 
					url: context_path+"/jsp/special/powerGener/powerGenerAdd.jsp",
					discardUrl: false, 
					nocache: false, 
					scripts: true 
				}); 
			}catch(e){
				window.location.href=context_path+"/jsp/special/powerGener/powerGener.jsp";
			}
    	}
    },'-',{
    	id:'dealTask',
    	text:"处理",
    	iconCls:'update',
    	handler:function(){
    		var records = listGrid.getSelectionModel().getSelections();
    		if(records.length!=1){
				Ext.MessageBox.alert("","请选择一条数据进行修改！");
				return ;
			}
    		var r=records[0];
    		var panel = Ext.getCmp('panelRight')
    		try{
				panel.removeAll();
				panel.load({
					url: context_path+"/powerGenerAction!dealPowerGener.ilf?id="+r.get('id'),
					discardUrl: false, 
					nocache: false, 
					scripts: true 
				}); 
			}catch(e){
				window.location.href=context_path+"/jsp/special/powerGener/powerGener.jsp";
			}
    	}
    },'-',{
	      id:'doquery',
	      text:"查询",
	      iconCls:'query',
	      handler: function(){
	    	  var title = Ext.getCmp('title').getValue();
	    	  var stationName = Ext.getCmp('stationName').getValue();
	          store.reload({params:{
	       		start:0,limit:15,title:title,stationName:stationName
	      	}});
	      }
	    },'-',{
	    	id:'dealResult',
	    	text:"查看结果",
	    	iconCls:'fresh',
	    	handler:function(){
	    		var records = listGrid.getSelectionModel().getSelections();
	    		if(records.length!=1){
					Ext.MessageBox.alert("","请选择一条数据进行查看！");
				}
	    		var r=records[0];
	    		var panel=Ext.getCmp('panelRight');
				try{
					panel.removeAll();
					panel.load({ 
						url: context_path+"/powerGenerAction!resultPowerGener.ilf?id="+r.get('id'),
						discardUrl: false, 
						nocache: false, 
						scripts: true 
					}); 
				}catch(e){
					window.location.href=context_path+"/jsp/special/powerGener/powerGener.jsp";
				}
	    	}
	    },'-'];
	
	var listGrid = new Ext.grid.GridPanel({
		store: store,
		cm: cm,
		sm: sm,
		autoHeight: autoHeight,
		loadMask:{msg : '加载数据中，请稍候...'},
		height: document.body.clientHeight-238,
		anchor:'100% -30',
	    bodyStyle:'width:100%',
	    stripeRows: true,// 让grid相邻两行背景色不同
	    loadMask: true,
	    tbar:new Ext.Toolbar({ //标题+按钮的工具栏
        	items:gridButtons
        }),
	    bbar: new Ext.PagingToolbar({
	        pageSize: thisPagesize,
	        store: store,
	        displayInfo: true,
	        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
	        emptyMsg: "没有记录"
	   })
	});
	
	new Ext.Panel({
		id:'basic',
		width:'100%',
    	frame:true,
    	modal:true,
		applyTo:'form',
		items:[queryPanel,listGrid],
		listeners:{
			beforeRender:function(){
			}
		}
	});
});