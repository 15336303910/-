Ext.namespace("com.increase.cen.poleline");
var pageSize = 10;
var limit = 10;


// 列模型
	var cm = new Ext.grid.ColumnModel([ /*{
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
	}, */
	{header : '杆路段ID',dataIndex : 'poleLineSegmentId',hidden : true}, 
	{header : '杆路段名称',dataIndex : 'poleLineSegmentName',width : 350,sortable : true}, 
	{header : '杆路段编码',dataIndex : 'poleLineSegmentCode',width : 180,sortable : true}, 
	{header : '状态',dataIndex : 'status',width : 100,sortable : true,
		renderer:function(v){
			if(v=='1'){
				return "空闲";
			}else if(v=='2'){
				return "在用";
			}else if(v=='3'){
				return "出租";
			}
		}
	}, 
	{header : '杆路段长度（米）',dataIndex : 'poleLineSegmentLength',width : 180,sortable : true}, 
	{header : '起始设施名称',dataIndex : 'startDeviceName',width : 200,sortable : true},
	{header : '终止设施名称',dataIndex : 'endDeviceName',width : 200,sortable : true},
	{header : '最后更新时间',dataIndex : 'lastUpdateDate',width : 180,sortable : true,
		renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')
	}]);
	// 从远程读取数据
	var proxy = new Ext.data.HttpProxy({
		url : "poleline!polelslist.action"

	});
	// Record 定义记录结果
	var poleLS = Ext.data.Record.create([
		{name : 'poleLineSegmentId',type : 'string',mapping : 'poleLineSegmentId',hidden : true
	}, {
		name : 'poleLineSegmentName',
		type : 'string',
		mapping : 'poleLineSegmentName'
	}, {
		name : 'poleLineSegmentCode',
		type : 'string',
		mapping : 'poleLineSegmentCode'
	}, {
		name : 'status',
		type : 'string',
		mapping : 'status'
	}, {
		name : 'poleLineSegmentLength',
		type : 'string',
		mapping : 'poleLineSegmentLength'
	}, {
		name : 'startDeviceName',
		type : 'string',
		mapping : 'startDeviceName'
	},{
		name : 'endDeviceName',
		type : 'string',
		mapping : 'endDeviceName'
	},{
		name : 'lastUpdateDate',
		mapping : 'lastUpdateDate',
		type : Ext.data.Types.DATE,
		dateFormat : 'Y-m-d\\TH:i:s'
	}]);
	// Reader以提供的Ext.data.Record 构造器中的映射为基础，从JSON响应创建一个Ext.data.Record 对象数组
	var reader = new Ext.data.JsonReader({
		totalProperty : "total",
		root : "polelinesegments"
	}, poleLS);
	var store = new Ext.data.Store({// 提供数据输入
		proxy : proxy,
		reader : reader,
		remoteSort : true
		/*sortInfo: {  
        	field: 'jumpfiberId',  
        	direction: "DESC"  
    	}*/
	});
	this.statusStore=new Ext.data.SimpleStore({
    		fields : ['value', 'text'],
    		data : [['', ''],['1', '空闲'], ['2', '在用'], ['3', '占用']]
    });
	
	com.increase.cen.poleline.poleLSGrid = Ext.extend(Ext.grid.GridPanel, {
		id:'poleLSGrid',
		cm : cm,// 列定义的model(必需)
		store : store,
		autoDestroy:true,
		viewConfig : {
			sortAscText : '升序',
			sortDescText : '降序',
			columnsText : '可选列',
			forceFit : false,// Ture表示自动扩展或缩小列的宽度
			scrollOffset : -1
		},
//		tbar : [{
//			xtype : 'tbseparator'
//		}, {
//			id : 'refreshBtn_a',
//			text : '全部刷新',
//			icon : "imgs/all_refresh.png",
//			cls : "x-btn-text-icon",
//			handler:function(){
//				var store = Ext.getCmp("pipeSegmentGrid").getStore();
//				store.load({
//					params : {
//						start : 0,
//						limit : limit
//					}
//				});
//				Ext.getCmp("addrGrid").getView().refresh();
//			}
//		}],
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
//			onFirstLayout : function(){//增加这个配置
//                if(this.dsLoaded){
//                    this.onLoad.apply(this, this.dsLoaded);
//                }
//                if(this.rendered && this.refresh){
//                    this.refresh.hide();
//                }
//            }

		}),

		initComponent : function(config) {
			// 重写initComponent superclass.initComponent.call(this);来调用父类的初始化方法.
			com.increase.cen.poleline.poleLSGrid.superclass.initComponent.call(this);
		}
	});
	
Ext.reg("poleLSGrid", com.increase.cen.poleline.poleLSGrid);