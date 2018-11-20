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
	{header : '管道ID',dataIndex : 'pipeId',hidden : true}, 
	{header : '管道名称',dataIndex : 'pipeName',width : 150,sortable : true}, 
	{header : '管道别名',dataIndex : 'alias',width : 90,sortable : true}, 
	{header : '管道级别',dataIndex : 'pipeLevel',width : 60,sortable : true,
		renderer:function(v){
			if(v=='1'){
				return "一干";
			}else if(v=='2'){
				return "二干";
			}else if(v=='3'){
				return "本地";
			}
		}
	}, 
	//{header : '管道长度',dataIndex : 'length',width : 60,sortable : true},
	{header : '起始设施名称',dataIndex : 'startDeviceName',width : 130,sortable : true},
	{header : '终止设施名称',dataIndex : 'endDeviceName',width : 130,sortable : true},
	{header : '最后更新时间',dataIndex : 'lastUpdateDate',width : 130,sortable : true,
		renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')
	},
	{header : '操作',dataIndex : 'poleLineId',width : 100,
		renderer : function(value, metadata, record, rowIndex, colIndex, store) {
			return '<input type="button" value="选择此管道" onclick="selectPipe('+rowIndex+')" />';				
		}
	}]);
	// 从远程读取数据
	var proxy = new Ext.data.HttpProxy({
		url : "pipe!list.action"

	});
	// Record 定义记录结果
	var pipe = Ext.data.Record.create([
		{name : 'pipeId',type : 'string',mapping : 'pipeId',hidden : true
	}, {
		name : 'pipeName',
		type : 'string',
		mapping : 'pipeName'
	},{
		name : 'alias',
		type : 'string',
		mapping : 'alias'
	},{
		name : 'pipeLevel',
		type : 'string',
		mapping : 'pipeLevel'
	}, /*{
		name : 'length',
		type : 'string',
		mapping : 'length'
	}, */{
		name : 'startDeviceName',
		type : 'string',
		mapping : 'startDeviceName'
	},{
		name : 'endDeviceName',
		type : 'string',
		mapping : 'endDeviceName'
	}, {
		name : 'lastUpdateDate',
		mapping : 'lastUpdateDate',
		type : Ext.data.Types.DATE,
		dateFormat : 'Y-m-d\\TH:i:s'
	}]);

// Reader以提供的Ext.data.Record 构造器中的映射为基础，从JSON响应创建一个Ext.data.Record 对象数组
var reader = new Ext.data.JsonReader({
	totalProperty : "total",
	root : "pipes"
}, pipe);
// 提供数据输入
var store = new Ext.data.Store({
	proxy : proxy,
	reader : reader,
	remoteSort : true
});
this.levelStore=new Ext.data.SimpleStore({
    		fields : ['value', 'text'],
    		data : [['', ''],['1', '一干'], ['2', '二干'], ['3', '本地']]
    	})
com.increase.cen.poleline.PipGrid = Ext.extend(Ext.grid.GridPanel, {
	id:'pipGrid',
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
			xtype : 'label',
			text : '管道名称：'
		},{
			xtype : 'textfield',
			id:'pipeName',
			cls:'sel-textfield-width'
			
		},{
			xtype : 'tbseparator'
		}, {
			xtype : 'label',
			text : '管道别名：'
		},{
			xtype : 'textfield',
			id:'alias',
			cls:'sel-textfield-width'
			
		},{
			xtype : 'tbseparator'
		},{
			xtype : 'label',
			text : '管道级别：'
		},{
			id:'pipeLevel',
			cls:'sel-textfield-width',
			xtype : 'combo',
			store : this.levelStore,
			value:'',
			displayField : 'text',
			valueField : 'value',
			mode : 'local',
			triggerAction : "all",// 下拉框获得了焦点或者被点击了
			editable : false,// 设置为false以阻止用户直接向该输入项输入文本
			hiddenName : 'pipeLevel'
			
		},{
		xtype : 'tbseparator'
	},{
		id : 'queryEqutBtn',
		text : '查询',
		icon : "imgs/queryBtn.png",
		cls : "x-btn-text-icon",
		handler:function(){
			var store = Ext.getCmp("pipGrid").getStore();
			store.load({
				params : {
					start : 0,
					limit : limit,
					'pipeInfoBean.pipeName':Ext.getCmp("pipeName").getValue(),
					'pipeInfoBean.alias':Ext.getCmp("alias").getValue(),
					'pipeInfoBean.pipeLevel':Ext.getCmp("pipeLevel").getValue()
				}
			});
			store.on('beforeload',function(){
   				store.baseParams = {
   					'pipeInfoBean.pipeName':Ext.getCmp("pipeName").getValue(),
					'pipeInfoBean.alias':Ext.getCmp("alias").getValue(),
					'pipeInfoBean.pipeLevel':Ext.getCmp("pipeLevel").getValue()
   				};
			}); 
			Ext.getCmp("pipGrid").getView().refresh();
		}
	}, {
		xtype : 'tbseparator'
	}, {
		id : 'refreshBtn',
		text : '全部刷新',
		icon : "imgs/all_refresh.png",
		cls : "x-btn-text-icon",
		handler:function(){
			Ext.getCmp("pipeName").setValue("");
			Ext.getCmp("alias").setValue("");
			Ext.getCmp("pipeLevel").setValue("");
			var store = Ext.getCmp("pipGrid").getStore();
			store.load({
				params : {
					start : 0,
					limit : limit
				}
			});
			Ext.getCmp("pipGrid").getView().refresh();
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
		com.increase.cen.poleline.PipGrid.superclass.initComponent.call(this);
	}
});

Ext.reg("pipGrid", com.increase.cen.poleline.PipGrid);
