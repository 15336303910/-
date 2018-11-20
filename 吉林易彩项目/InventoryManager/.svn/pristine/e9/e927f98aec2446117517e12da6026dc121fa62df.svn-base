var store;
var listGrid;
var thisPagesize = 15;
var autoHeight = false;
function reload(){
	var stoneNamequery = Ext.getCmp('stoneNamequery').getValue();
	var stoneAreaquery = Ext.getCmp('stoneAreaquery').getValue();
	listGrid.getStore().reload({params:{
		start:0,limit:15,stoneName:stoneNamequery,stoneArea:stoneAreaquery
	}});
}

function getDetailStone(type){
	var row = listGrid.getSelectionModel().getSelections();
	if(row.length!=1){
		Ext.MessageBox.alert("","请选择一条数据进行查看！");
	}
	var r=row[0];
	Ext.Ajax.request({ 
		url:context_path+'/stoneAction!getDetailStone.action',
		method:'post',
		params:{id:r.data['stoneId']},
		success:function(fp,o){
			var obj = eval("("+fp.responseText+")");
			if(type == 'update'){
				updateStone(listGrid.getStore(),obj);
			}else{
				detailStone(listGrid.getStore(),obj);
			}
		}
 	});
}

Ext.onReady(function() {
	Ext.data.Connection.prototype.timeout ='300000';
	
	var stoneNamequery = itemText(.33,80,'stoneNamequery','标石名称');
	var stoneAreaquery = itemText(.33,80,'stoneAreaquery','所属区域');
	var queryPanel = new Ext.form.FormPanel({
		bodyStyle : 'padding-top:5px',
	    layout : "column",
	    id:'queryPanel',
		border : false,
		width:'100%',
		frame:true,
		items:[stoneNamequery,stoneAreaquery
		]
	});
	var column = "[{header:'stoneId',dataIndex:'stoneId',hidden:true,width:150,fun:null}," +
				 "{header:'标石名称',dataIndex:'stoneName',hidden:false,width:150,fun:null}," +
				 "{header:'标石方位',dataIndex:'stonePositionStr',hidden:false,width:150,fun:null}," +
				 "{header:'标石序号',dataIndex:'stoneNum',hidden:false,width:150,fun:null}," +
				 "{header:'所属区域',dataIndex:'stoneArea',hidden:false,width:150,fun:null}," +
				 "{header:'接头盒名称',dataIndex:'jointName',hidden:false,width:150,fun:null},"+
				 "{header:'经度',dataIndex:'longitude',hidden:false,width:150,fun:null}," +
				 "{header:'纬度',dataIndex:'latitude',hidden:false,width:150,fun:null}," +
				 "{header:'产权性质',dataIndex:'propertyNatureStr',hidden:false,width:150,fun:null}," +
				 "{header:'产权单位',dataIndex:'propertyCompStr',hidden:false,width:150,fun:null}," +
				/* "{header:'质量责任人',dataIndex:'dataQualitier',hidden:false,width:150,fun:null}," +*/
				 "{header:'一线维护人',dataIndex:'maintainer',hidden:false,width:150,fun:null}," +
				 "{header:'录入时间',dataIndex:'createTime',hidden:false,width:150,fun:null}" +
				 "]";
	var result = basicStore(column,'stoneAction!getStoneGrid.action','totalCount','root')
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
    		addStone(store);
    	}
    },'-',{
    	id:'detailStone',
    	text:"查看",
    	iconCls:'add',
    	handler:function(){
    		getDetailStone('detail');
    	}
    },'-',{
    	id:'updateStone',
    	text:"修改",
    	iconCls:'add',
    	handler:function(){
    		getDetailStone('update');
    	}
    },'-',{
    	id:'delStone',
    	text:"删除",
    	iconCls:'del',
    	handler:function(){
    		var rows = listGrid.getSelectionModel().getSelections();
    		delRows('stoneAction!delStone.action','stoneId',rows,reload);
    	}
    },'-',{
    	id:'exp',
    	text:"导出数据",
    	iconCls:'exp',
    	handler:function(){
    		var stoneNamequery = Ext.getCmp('stoneNamequery').getValue();
    		var stoneAreaquery = Ext.getCmp('stoneAreaquery').getValue();
    		var data = "stoneName="+stoneNamequery+"&stoneArea"+stoneAreaquery;
			var url = context_path+'/stoneAction!expData.action?'+data;
			url = encodeURI(url);
			window.open(url,"_blank");
    	}
    },'-',{
    	id:'imp',
    	text:"导入数据",
    	iconCls:'imp',
    	handler:function(){
    		var tmpUrl = 'stoneAction!expTemp.action?';
    		var url = 'stoneAction!impData.action?';
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
		height: document.body.clientHeight-50,
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