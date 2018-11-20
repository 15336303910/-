var store;
var listGrid;
var thisPagesize = 15;
var autoHeight = false;

/**
 * 重新刷新数据
 */
function reload(){
	listGrid.getStore().reload();
}
/**
 * 组管理方法
 * @param domainCode
 */
function groupMagFun(domainCode){
	Ext.data.Connection.prototype.timeout ='300000';
	
	var column = "[{header:'id',dataIndex:'id',hidden:true,width:150,fun:null}," +
	 			"{header:'班组名称',dataIndex:'groupName',hidden:false,width:150,fun:null}," +
	 			"{header:'所属区域',dataIndex:'domainName',hidden:false,width:150,fun:null}," +
	 			"{header:'所属分公司',dataIndex:'compName',hidden:false,width:150,fun:null}" +
	 			"]";
	var result = basicStore(column,'maintainGroupAction!getMtGroupGrid.action?dCode='+domainCode,'totalCount','root');
	store =result.store;
	var cm = result.cm;
	var sm = result.sm;
	
	store.load({params:{
 		start:0,limit:15
	}});
	
	var gridButtons = ['-',
	         {
	           id:'addTemple',
	           text:"新建",
	           icon : "js/ext/resources/images/default/dd/drop-add.gif",
	           handler:function(){
	        	   addGroup(store,domainCode);
	           }
	         },'-',
	         {
	        	 id:'delGroup',
	        	 text:"删除",
	        	 icon:"js/ext/resources/images/default/dd/drop-no.gif",
	        	 handler:function(){
	        		 var rows = listGrid.getSelectionModel().getSelections();
	        		 delRows('maintainGroupAction!delGroup.action','id',rows,reload);
	        	 }
	         },'-'];
	
	listGrid = new Ext.grid.GridPanel({
		store: store,
		cm: cm,
		sm: sm,
		autoHeight: autoHeight,
		loadMask:{msg : '加载数据中，请稍候...'},
		height: 330,
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
	
	var magWin = new Ext.Window({
		id:'magWin',
		title:'班组管理',
      	width:600,
      	closable:false,
      	height:400,
      	modal:true,
      	buttonAlign:"center",
      	items:[listGrid],
      	buttons:[{
      		text:"提交",
      		handler:function(){
      			
      		}
      	},{
      		text:"关闭",
      		handler:function(){
      			magWin.destroy();
      		}
      	}]
	});
	magWin.show();
}