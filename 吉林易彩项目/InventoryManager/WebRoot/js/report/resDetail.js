var store;
var listGrid;
var thisPagesize = 15;
var autoHeight = false;


function getColumn(resType){
	var col ;
	if(resType == null || resType == 'point'){
		col = "[{header:'id',dataIndex:'id',hidden:true,width:150,fun:null}," +
			"{header:'地市区县',dataIndex:'region',hidden:false,width:150,fun:null}," +
			"{header:'资源名称',dataIndex:'name',hidden:false,width:150,fun:null}," +
			"{header:'经度',dataIndex:'lon',hidden:false,width:150,fun:null}," +
			"{header:'纬度',dataIndex:'lat',hidden:false,width:150,fun:null}," +
			"{header:'修改时间',dataIndex:'dealtime',hidden:false,width:150,fun:null},"+
			"{header:'处理人',dataIndex:'dealer',hidden:false,width:50,fun:null}," +
			"{header:'资源标识',dataIndex:'resNum',hidden:false,width:100,fun:null}" +
			"]";
	}else{
		col = "[{header:'id',dataIndex:'id',hidden:true,width:10,fun:null}," +
			"{header:'地市区县',dataIndex:'region',hidden:false,width:100,fun:null}," +
			"{header:'名称',dataIndex:'name',hidden:false,width:150,fun:null}," +
			"{header:'起始资源',dataIndex:'startRes',hidden:false,width:150,fun:null}," +
			"{header:'起始经度',dataIndex:'startLon',hidden:false,width:100,fun:null}," +
			"{header:'起始纬度',dataIndex:'startLat',hidden:false,width:100,fun:null}," +
			"{header:'终止资源',dataIndex:'endRes',hidden:false,width:150,fun:null}," +
			"{header:'终止经度',dataIndex:'endLon',hidden:false,width:100,fun:null}," +
			"{header:'终止纬度',dataIndex:'endLat',hidden:false,width:100,fun:null}," +
			"{header:'计算长度',dataIndex:'segLength',hidden:false,width:70,fun:null}," +
			"{header:'维护长度',dataIndex:'maintainLength',hidden:false,width:70,fun:null},"+
			"{header:'处理人',dataIndex:'dealer',hidden:false,width:50,fun:null}," +
			"{header:'处理时间',dataIndex:'dealtime',hidden:false,width:70,fun:null}," +
			"{header:'资源标识',dataIndex:'resNum',hidden:false,width:70,fun:null}" +
			"]";
	}
	return col;
}


Ext.onReady(function() {
	Ext.data.Connection.prototype.timeout ='300000';
	var result;
	var column = getColumn(null);
	var bbr  = new Ext.PagingToolbar({
        pageSize: thisPagesize,
        store: store,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
	});
	
	var resNamequery = itemText(.33,40,'resName','名称');
	var resStartTime = basicDate(.33,40,'resTime','时间');
	var resType = [['station','站点'],['gener','机房'],['optical','光交'],['pipe','管道'],
	               ['well','人手井'],['poleLine','杆路'],['pole','电杆'],['buried','直埋'],
	               ['stone','标石']];
	var resTypeCombo = basicCombo(resType,.33,40,'resType','类型');
	var queryButton =  new Ext.Button({ 
		text: '查询',
		iconCls:'query',
		handler: function(){
			var rtQuery = Ext.getCmp('resType').getValue();
			var resName = Ext.getCmp('resName').getValue();
			var resTime = Ext.getCmp('resTime').getValue();
			if(rtQuery == null || rtQuery == ""){
				alert("请选择资源类型查询!");
				return;
			}
			if(rtQuery == 'pipe' || rtQuery == 'poleLine' || rtQuery == 'buried'){
				column = getColumn('line');
			}else{
				column = getColumn('point');
			}
			result = basicStore(column,'reportAction!getResGrid.action','totalCount','root')
			store =result.store;
			
			listGrid.reconfigure(result.store , result.cm);
			bbr.bind(result.store);
			Ext.apply(listGrid.getStore().baseParams, {resType:rtQuery,city:city,resName:resName,resTime:resTime});
			listGrid.getStore().reload({params:{
				start:0,limit:15
			}});
		}
	});
	
	var queryPanel = new Ext.form.FormPanel({
		bodyStyle : 'padding-top:5px',
	    layout : "column",
	    id:'queryPanel',
		border : false,
		width:'100%',
		frame:true,
		buttonAlign:'center',
		items:[resNamequery,resStartTime,resTypeCombo
		],
		buttons:[queryButton]
	});
	
	result = basicStore(column,'reportAction!getResGrid.action','totalCount','root')
	store =result.store;
	var cm = result.cm;
	var sm = result.sm;
	
	listGrid = new Ext.grid.GridPanel({
		store: store,
		cm: cm,
		sm: sm,
		autoHeight: autoHeight,
		loadMask:{msg : '加载数据中，请稍候...'},
		height: 300,
		anchor:'100% -30',
	    bodyStyle:'width:100%',
	    stripeRows: true,// 让grid相邻两行背景色不同
	    loadMask: false,
	    bbar: bbr
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