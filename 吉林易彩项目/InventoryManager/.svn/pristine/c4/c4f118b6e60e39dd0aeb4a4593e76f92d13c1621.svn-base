Ext.namespace("com.increase.cen.poleline");
var pageSize = 10;
var limit = 10;
	var cm = new Ext.grid.ColumnModel([{
		header : "序号",
		width : 40,
		hideable : false,
		renderer : function(value, metadata, record, rowIndex, colIndex, store) {
			var start = store.lastOptions.params.start;
			if (isNaN(start)) {
				start = 0;
			}
			return start + rowIndex + 1;
		}
	}, 
	{header : '电杆ID',dataIndex : 'poleId',hidden : true}, 
	{header : '电杆名称',dataIndex : 'poleNameSub',width : 180,sortable : true}, 
	{header : '电杆编码',dataIndex : 'poleCode',width : 90,sortable : true}, 
	{header : '电杆类型',dataIndex : 'poleTypeEnumId',width : 60,sortable : true,
		renderer:function(v){
			if(v=='1'){
				return "普通杆";
			}else if(v=='2'){
				return "单接杆";
			}else if(v=='3'){
				return "双接杆";
			}else if(v=='4'){
				return "H型杆";
			}else if(v=='5'){
				return "A型杆";
			}else if(v=='6'){
				return "L型杆";
			}else if(v=='7'){
				return "三角杆";
			}else if(v=='8'){
				return "井型杆";
			}else if(v=='9'){
				return "引上杆";
			}else if(v=='10'){
				return "终端杆";
			}else if(v=='11'){
				return "角杆";
			}else if(v=='12'){
				return "分歧杆";
			}else if(v=='13'){
				return "T型杆";
			}else if(v=='14'){
				return "跨路杆";
			}
		}
	}, 
	{header : '电杆地址',dataIndex : 'poleAddress',width : 100,sortable : true}, 
	{header : '最后更新时间',dataIndex : 'lastUpdateDate',width : 130,sortable : true,
		renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')
	},
	{header : '操作',dataIndex : 'poleId',width : 100,
		renderer : function(value, metadata, record, rowIndex, colIndex, store) {
			return '<input type="button" value="选择此电杆" onclick="selectPole_aa('+rowIndex+')" />';				
		}
	}]);
	// 从远程读取数据
	var proxy = new Ext.data.HttpProxy({
		url : "poleline!polelist.action"

	});
	// Record 定义记录结果
	var pole = Ext.data.Record.create([
		{name : 'poleId',type : 'string',mapping : 'poleId',hidden : true
	}, {
		name : 'poleNameSub',
		type : 'string',
		mapping : 'poleNameSub'
	}, {
		name : 'poleCode',
		type : 'string',
		mapping : 'poleCode'
	}, {
		name : 'poleTypeEnumId',
		type : 'string',
		mapping : 'poleTypeEnumId'
	}, {
		name : 'poleAddress',
		type : 'string',
		mapping : 'poleAddress'
	}, {
		name : 'lastUpdateDate',
		mapping : 'lastUpdateDate',
		type : Ext.data.Types.DATE,
		dateFormat : 'Y-m-d\\TH:i:s'
	}]);

// Reader以提供的Ext.data.Record 构造器中的映射为基础，从JSON响应创建一个Ext.data.Record 对象数组
var reader = new Ext.data.JsonReader({
	totalProperty : "total",
	root : "poles"
}, pole);
// 提供数据输入
var store = new Ext.data.Store({
	proxy : proxy,
	reader : reader,
	remoteSort : true
});
var etypeStore = new Ext.data.SimpleStore({
	fields : ['value', 'text'],
	data : [['', '全部类型'],['1', '光配线架(ODF)'], ['2', '合性光配线架(MODF)'], ['3', '光交接箱'], ['4', '光分路器'], ['5', '光分纤盒'], ['6', '光缆接头盒(终端盒)'], ['7', '分光器(ODB)']]
})
com.increase.cen.poleline.PoleGrid_AA = Ext.extend(Ext.grid.GridPanel, {
	id:'poleGrid_aa',
	cm : cm,// 列定义的model(必需)
	store : store,
	viewConfig : {
		sortAscText : '升序',
		sortDescText : '降序',
		columnsText : '可选列',
		forceFit : false,// Ture表示自动扩展或缩小列的宽度
		scrollOffset : -1
	},
	tbar : [{
		xtype : 'tbseparator'
	}, {
			xtype : 'label',
			text : '电杆名称：'
		},{
			xtype : 'textfield',
			id:'poleNameSub_aa',
			cls:'sel-textfield-width'
			
		},{
			xtype : 'tbseparator'
		}, {
			xtype : 'label',
			text : '电杆编码：'
		},{
			xtype : 'textfield',
			id:'poleCode_aa',
			cls:'sel-textfield-width'
			
		},{
			xtype : 'tbseparator'
		}, {
			xtype : 'label',
			text : '电杆地址：'
		},{
			xtype : 'textfield',
			id:'poleAddress_aa',
			cls:'sel-textfield-width'
			
		},{
		xtype : 'tbseparator'
	},{
		id : 'queryEqutBtn_aa',
		text : '查询',
		icon : "imgs/queryBtn.png",
		cls : "x-btn-text-icon",
		handler:function(){
			var store = Ext.getCmp("poleGrid_aa").getStore();
			store.load({
				params : {
					start : 0,
					limit : limit,
					'poleInfoBean.poleNameSub':Ext.getCmp("poleNameSub_aa").getValue(),
					'poleInfoBean.poleCode':Ext.getCmp("poleCode_aa").getValue(),
					'poleInfoBean.poleAddress':Ext.getCmp("poleAddress_aa").getValue()
				}
			});
			store.on('beforeload',function(){
   				store.baseParams = {
   					'poleInfoBean.poleNameSub':Ext.getCmp("poleNameSub_aa").getValue(),
					'poleInfoBean.poleCode':Ext.getCmp("poleCode_aa").getValue(),
					'poleInfoBean.poleAddress':Ext.getCmp("poleAddress_aa").getValue()
   				};
			}); 
			Ext.getCmp("poleGrid_aa").getView().refresh();
		}
	}, {
		xtype : 'tbseparator'
	}, {
		id : 'refreshBtn_aa',
		text : '全部刷新',
		icon : "imgs/all_refresh.png",
		cls : "x-btn-text-icon",
		handler:function(){
			Ext.getCmp("poleNameSub_aa").setValue("");
			Ext.getCmp("poleCode_aa").setValue("");
			Ext.getCmp("poleAddress_aa").setValue("");
			var store = Ext.getCmp("poleGrid_aa").getStore();
			store.load({
				params : {
					start : 0,
					limit : limit
				}
			});
			Ext.getCmp("poleGrid_aa").getView().refresh();
		}
	}],
	bbar : new Ext.PagingToolbar({// 分页工具栏
		store : store,
		pageSize : pageSize,
		beforePageText : "当前第",
		afterPageText : "页，共{0}页",
		lastText : "尾页",
		nextText : "下一页",
		prevText : "上一页",
		firstText : "首页",
		refreshText : "刷新页面",
		displayInfo : true,// 是否显示displayMsg
		displayMsg : "显示第{0}-{1}条 ，共{2}条",
		emptyMsg : "没有记录"
	}),

	initComponent : function(config) {
		// 重写initComponent superclass.initComponent.call(this);来调用父类的初始化方法.
		com.increase.cen.poleline.PoleGrid_AA.superclass.initComponent.call(this);
	}
});

Ext.reg("poleGrid_aa", com.increase.cen.poleline.PoleGrid_AA);
