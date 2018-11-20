var store;
var listGrid;
var thisPagesize = 15;
var autoHeight = false;
var start = 0;
function reload(){
	listGrid.getStore().reload({params:{
		start:start,limit:15
	}});
}
Ext.onReady(function() {
	Ext.data.Connection.prototype.timeout ='300000';
	
	var cnTitle = itemText(.33,80,'cnTitle','中文名称');
	var queryPanel = new Ext.form.FormPanel({
		bodyStyle : 'padding-top:5px',
	    layout : "column",
	    id:'queryPanel',
		border : false,
		width:'100%',
		frame:true,
		items:[cnTitle
		]
	});
	var column = "[{header:'编号',dataIndex:'id',width:50,fun:null}," +
				 "{header:'英文名称',dataIndex:'enName',hidden:false,width:150,fun:null}," +
				 "{header:'中文名称',dataIndex:'cnName',hidden:false,width:150,fun:null}," +
				 "{header:'创建时间',dataIndex:'createTime',hidden:false,width:150,fun:null}" +
				 "]";
	var result = basicStore(column,'dictAction!getDictGrid.action','totalCount','root')
	store =result.store;
	var cm = result.cm;
	var sm = result.sm;
	
	store.load({params:{
 		limit:15
	}});
	
	var gridButtons = ['-',
	  {
    	id:'addTemple',
    	text:"新建",
    	iconCls:'add',
    	handler:function(){
    		addStone(store);
    	}
    },'-',{
    	id:'addTable',
    	text:"添加属性",
    	iconCls:'add',
    	handler:function(){
    		var row = listGrid.getSelectionModel().getSelections();
    		if(row.length!=1){
    			Ext.MessageBox.alert("","请选择一条数据进行查看！");
    		}
    		var r=row[0];
    		var id = r.data['id'];
    		addDicTable(store,id);
    	}
    },'-'];
	
	listGrid = new Ext.grid.GridPanel({
		store: store,
		cm: cm,
		sm: sm,
		autoHeight: autoHeight,
		loadMask:{msg : '加载数据中，请稍候...'},
		height: document.body.clientHeight-238,
		anchor:'100% -30',
		region:'center',
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
	   }),
	   listeners:{
		   rowdblclick:function(grid,row){
			   var record = grid.getStore().getAt(row);
			   checkDicTable(record.data['id']);
		   }
	   }
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