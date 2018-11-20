var store;
var listGrid ;
var thisPagesize = 15;
var autoHeight = false;
function reload(){
	var terminalNameQuery = Ext.getCmp('terminalNameQuery').getValue();
	var terminalAddresQuery = Ext.getCmp('terminalAddresQuery').getValue();
	listGrid.getStore().reload({params:{
		start:0,limit:15,terminalName:terminalNameQuery,terminalAddres:terminalAddresQuery
	}});
}
function getDetailOpt(type){
	var row = listGrid.getSelectionModel().getSelections();
	if(row.length!=1){
		Ext.MessageBox.alert("","请选择一条数据进行查看！");
	}
	var r=row[0];
	Ext.Ajax.request({ 
		url:context_path+'/opticalTerminalAction!getDetailOpt.action',
		method:'post',
		params:{id:r.data['id']},
		success:function(fp,o){
			var obj = eval("("+fp.responseText+")");
			if(type == 'update'){
				updateOpt(listGrid.getStore(),obj);
			}else{
				detailOpt(listGrid.getStore(),obj);
			}
		}
 	});
}
Ext.onReady(function() {
	Ext.data.Connection.prototype.timeout ='300000';
	
	var terminalNameQuery = itemText(.33,80,'terminalNameQuery','光终端盒名称');
	var terminalAddresQuery = itemText(.33,80,'terminalAddresQuery','设备地址');
	var queryPanel = new Ext.form.FormPanel({
		bodyStyle : 'padding-top:5px',
	    layout : "column",
	    id:'queryPanel',
		border : false,
		width:'100%',
		frame:true,
		items:[terminalNameQuery,terminalAddresQuery
		]
	});
	var column = "[{header:'id',dataIndex:'id',hidden:true,width:50,fun:null}," +
				 "{header:'终端盒名称',dataIndex:'terminalName',hidden:false,width:150,fun:null}," +
				 "{header:'经度',dataIndex:'longitude',hidden:false,width:100,fun:null}," +
				 "{header:'纬度',dataIndex:'latitude',hidden:false,width:100,fun:null}," +
				 "{header:'行数',dataIndex:'rowNum',hidden:false,width:50,fun:null}," +
				 "{header:'列数',dataIndex:'colNum',hidden:false,width:50,fun:null}," +
				 "{header:'归属点类型',dataIndex:'attachTypeStr',hidden:false,width:150,fun:null}," +
				 "{header:'设备地址',dataIndex:'terminalAddres',hidden:false,width:150,fun:null}," +
				 "{header:'质量责任人',dataIndex:'dataQualitier',hidden:false,width:100,fun:null}," +
				 "{header:'一线维护人',dataIndex:'maintainer',hidden:false,width:100,fun:null}," +
				 "{header:'录入时间',dataIndex:'createTime',hidden:false,width:150,fun:null}" +
				 "]";
	var result = basicStore(column,'opticalTerminalAction!getOpticalTerGrid.action','totalCount','root')
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
    	iconCls:'add',
    	handler:function(){
    		addOpticalTerminal(store);
    	}
    },'-',{
    	id:'detailOpt',
    	text:"查看",
    	iconCls:'add',
    	handler:function(){
    		getDetailOpt('detail');
    	}
    },'-',{
    	id:'updateOpt',
    	text:"修改",
    	iconCls:'add',
    	handler:function(){
    		getDetailOpt('update');
    	}
    },'-',{
    	id:'delTemple',
    	text:"删除",
    	iconCls:'del',
    	handler:function(){
    		var rows = listGrid.getSelectionModel().getSelections();
    		delRows('opticalTerminalAction!delOpticalTerminal.action','id',rows,reload);
    	}
    },'-',{
    	id:'exp',
    	text:"导出数据",
    	iconCls:'exp',
    	handler:function(){
    		var terminalNameQuery = Ext.getCmp('terminalNameQuery').getValue();
    		var terminalAddresQuery = Ext.getCmp('terminalAddresQuery').getValue();
    		var data = "terminalName="+terminalNameQuery+"&terminalAddres="+terminalAddresQuery;
			var url = context_path+'/opticalTerminalAction!expData.action?'+data;
			url = encodeURI(url);
			window.open(url,"_blank");
    	}
    },'-',{
    	id:'imp',
    	text:"导入数据",
    	iconCls:'imp',
    	handler:function(){
    		var tmpUrl = 'opticalTerminalAction!expTemp.action?';
    		var url ='opticalTerminalAction!impData.action?';
    		impData(tmpUrl,url);
    		
    	}
    },'-',{
	      id:'doquery',
	      text:"查询",
	      iconCls:'query',
	      handler: function(){
	    	  reload();
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