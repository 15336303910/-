Ext.namespace("com.increase.cen.poleline");
var pageSize = 10;
var limit = 10;
	// 列模型
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
	{header : '光缆段ID',dataIndex : 'cableid',hidden : true}, 
	{header : '光缆段名称',dataIndex : 'cablename',width : 200,sortable : true}, 
	{header : '维护状态',dataIndex : 'state',width : 60,sortable : true,
		renderer:function(v){
			if(v=='1'){
				return "割接封锁";
			}else if(v=='2'){
				return "正常";
			}else if(v=='3'){
				return "不可用";
			}
		}
	}, 
	{header : '光缆段标牌',dataIndex : 'sign',width : 200,sortable : true}, 
	{header : '操作',dataIndex : 'cableid',width : 120,
		renderer : function(value, metadata, record, rowIndex, colIndex, store) {
			return '<input type="button" id="select2Cables'+record.data["cableid"]+'" value="选择此光缆段" onclick="selectCable2('+rowIndex+')" />';				
		}
	}]);
	// 从远程读取数据
	var proxy = new Ext.data.HttpProxy({
		url : "route!searchCable.action"

	});
	// Record 定义记录结果
	var fiber = Ext.data.Record.create([
		{name : 'cableid',type : 'string',mapping : 'cableid',hidden : true
	}, {
		name : 'cablename',
		type : 'string',
		mapping : 'cablename'
	}, {
		name : 'state',
		type : 'string',
		mapping : 'state'
	}, {
		name : 'sign',
		type : 'string',
		mapping : 'sign'
	}]);
	// Reader以提供的Ext.data.Record 构造器中的映射为基础，从JSON响应创建一个Ext.data.Record 对象数组
	var reader = new Ext.data.JsonReader({
		totalProperty : "total",
		root : "cables"
	}, fiber);
	var store = new Ext.data.Store({// 提供数据输入
		proxy : proxy,
		reader : reader,
		remoteSort : true
	});
	store.load({
		params : {
			start : 0,
			limit : limit
		}
	});
this.state2Store=new Ext.data.SimpleStore({
		fields : ['value', 'text'],
    	data : [['', ''],['1', '割接封锁'], ['2', '正常'], ['3', '不可用']]
	})
com.increase.cen.poleline.Cable2Grid = Ext.extend(Ext.grid.GridPanel, {
	id:'cable2Grid',
	cm : cm,// 列定义的model(必需)
	store : store,
	width:600,
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
			text : '光缆段名称：'
		},{
			xtype : 'textfield',
			id:'cablename2',
			cls:'sel-textfield-width'
			
		},{
			xtype : 'tbseparator'
		}, {
			xtype : 'label',
			text : '光缆段标牌：'
		},{
			xtype : 'textfield',
			id:'sign2',
			cls:'sel-textfield-width'
			
		},{
			xtype : 'tbseparator'
		},{
			xtype : 'label',
			text : '维护状态：'
		},{
			id:'state2',
			cls:'sel-textfield-width',
			xtype : 'combo',
			store : this.state2Store,
			value:'',
			displayField : 'text',
			valueField : 'value',
			mode : 'local',
			triggerAction : "all",// 下拉框获得了焦点或者被点击了
			editable : false,// 设置为false以阻止用户直接向该输入项输入文本
			hiddenName : 'state'
			
		},{
		xtype : 'tbseparator'
	},{
		id : 'queryEqutBtn2',
		text : '查询',
		icon : "imgs/queryBtn.png",
		cls : "x-btn-text-icon",
		handler:function(){
			var store = Ext.getCmp("cable2Grid").getStore();
			store.load({
				params : {
					start : 0,
					limit : limit,
					'searchCable.cablename' : Ext.getCmp("cablename2").getValue(),
					'searchCable.sign' : Ext.getCmp("sign2").getValue(),
					'searchCable.state' : Ext.getCmp("state2").getValue()
				}
			});
			store.on('beforeload',function(){
   				store.baseParams = {
   					'searchCable.cablename' : Ext.getCmp("cablename2").getValue(),
					'searchCable.sign' : Ext.getCmp("sign2").getValue(),
					'searchCable.state' : Ext.getCmp("state2").getValue()
   				};
			}); 
			Ext.getCmp("cable2Grid").getView().refresh();
		}
	}, {
		xtype : 'tbseparator'
	}, {
		id : 'refreshBtn2',
		text : '全部刷新',
		icon : "imgs/all_refresh.png",
		cls : "x-btn-text-icon",
		handler:function(){
			Ext.getCmp("cablename2").setValue("");
			Ext.getCmp("sign2").setValue("");
			Ext.getCmp("state2").setValue("");
			var store = Ext.getCmp("cable2Grid").getStore();
			store.load({
				params : {
					start : 0,
					limit : limit
				}
			});
			Ext.getCmp("cable2Grid").getView().refresh();
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
		com.increase.cen.poleline.Cable2Grid.superclass.initComponent.call(this);
	}
});

Ext.reg("cable2Grid", com.increase.cen.poleline.Cable2Grid);
