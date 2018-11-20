Ext.namespace("com.increase.cen.fiber");
var pageSize = 10;
var limit = 10;

//列模型
var cm = new Ext.grid.ColumnModel([
	{header : "序号",width : 50,hideable : false,
		renderer : function(value, metadata, record, rowIndex, colIndex, store) {
			var start = store.lastOptions.params.start;
			if (isNaN(start)) {
				start = 0;
			}
			return start + rowIndex + 1;
		}
	}, 
	{header : '设备编码',dataIndex : 'ecode',width : 150}, 
	{header : '设备名称',dataIndex : 'ename',width : 120}, 
	{header : '设备类型',dataIndex : 'etype',width : 100,
		renderer:function(v){//列模型回调函数
			 return getEtypeStr(v);
		}
	}, 
	{header : '设备状态',dataIndex : 'estat',width : 70,
		renderer:function(v){//列模型回调函数
			return getEstatStr(v)
		}
	}, 
	{header : '设备型号',dataIndex : 'emodel',width : 100}, 
	{header : '地址信息',dataIndex : 'eaddr',width : 130},
	{header : '所属局站',dataIndex : 'station',width : 130},
	{header : '操作',dataIndex : 'id',width : 100,
		renderer : function(value, metadata, record, rowIndex, colIndex, store) {
			return '<input type="button" value="选择此设备" onclick="selectEqut_a('+rowIndex+')" />';				
		}
	}
]);

//数据源
var proxy = new Ext.data.HttpProxy({
	url : "workorder!loadEqutGrid.action"
});

//数据结构
var equt = Ext.data.Record.create([
	{name : 'ecode',type : 'string',mapping : 'ecode'}, 
	{name : 'ename',type : 'string',mapping : 'ename'}, 
	{name : 'etype',type : 'string',mapping : 'etype'}, 
	{name : 'estat',type : 'string',mapping : 'estat'}, 
	{name : 'emodel',type : 'string',mapping : 'emodel'}, 
	{name : 'eaddr',type : 'string',mapping : 'eaddr'},
	{name : 'station',type : 'string',mapping : 'station'}
]);


// Reader以提供的Ext.data.Record 构造器中的映射为基础，从JSON响应创建一个Ext.data.Record 对象数组
var reader = new Ext.data.JsonReader({
	totalProperty : "total",
	root : "equts"
}, equt);
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
com.increase.cen.fiber.EqutGrid_A = Ext.extend(Ext.grid.GridPanel, {
	id:'equtGrid_a',
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
		text : '设备编码：'
	}, {
		id : 'ecode_sel',
		xtype : 'textfield',
		cls :'sel-textfield-width-e'
	}, {
		xtype : 'tbseparator'
	}, {
		xtype : 'label',
		text : '设备名称：'
	}, {
		id : 'ename_sel',
		xtype : 'textfield',
		cls :'sel-textfield-width-e'
	},{
		xtype : 'tbseparator'
	}, {
		xtype : 'label',
		text : '设备类型：'
	}, {
		xtype : 'combo',
		id:'etype_sel',
		cls :'sel-textfield-width-e',
		store : etypeStore,
		displayField : 'text',
		valueField : 'value',
		mode : 'local',
		value:'',
		triggerAction : "all",// 下拉框获得了焦点或者被点击了
		editable : false,// 设置为false以阻止用户直接向该输入项输入文本
		hiddenName : 'etype_sel_name'
	}, {
		xtype : 'tbseparator'
	}, {
		xtype : 'label',
		text : '设备地址：'
	}, {
		id : 'eaddr_sel',
		xtype : 'textfield',
		cls :'sel-textfield-width-e'
	},{
		xtype : 'tbseparator'
	},{
		id : 'queryEqutBtn_a',
		text : '查询',
		icon : "imgs/queryBtn.png",
		cls : "x-btn-text-icon",
		handler:function(){
			var store = Ext.getCmp("equtGrid_a").getStore();
			store.load({
				params : {
					start : 0,
					limit : limit,
					'requestEqut.ecode':Ext.getCmp("ecode_sel").getValue(),
					'requestEqut.ename':Ext.getCmp("ename_sel").getValue(),
					'requestEqut.etype':Ext.getCmp("etype_sel").getValue(),
					'requestEqut.eaddr':Ext.getCmp("eaddr_sel").getValue()
				}
			});
			store.on('beforeload',function(){
   				store.baseParams = {
   					'requestEqut.ecode':Ext.getCmp("ecode_sel").getValue(),
					'requestEqut.ename':Ext.getCmp("ename_sel").getValue(),
					'requestEqut.etype':Ext.getCmp("etype_sel").getValue(),
					'requestEqut.eaddr':Ext.getCmp("eaddr_sel").getValue()
   				};
			}); 
			Ext.getCmp("equtGrid_a").getView().refresh();
		}
	}, {
		xtype : 'tbseparator'
	}, {
		id : 'refreshBtn_a',
		text : '全部刷新',
		icon : "imgs/all_refresh.png",
		cls : "x-btn-text-icon",
		handler:function(){
			Ext.getCmp("ecode_sel").setValue("");
			Ext.getCmp("ename_sel").setValue("");
			Ext.getCmp("etype_sel").setValue("");
			Ext.getCmp("eaddr_sel").setValue("");
			var store = Ext.getCmp("equtGrid_a").getStore();
			store.load({
				params : {
					start : 0,
					limit : limit
				}
			});
			Ext.getCmp("equtGrid_a").getView().refresh();
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
		com.increase.cen.fiber.EqutGrid_A.superclass.initComponent.call(this);
	}
});

Ext.reg("equtGrid_a", com.increase.cen.fiber.EqutGrid_A);
