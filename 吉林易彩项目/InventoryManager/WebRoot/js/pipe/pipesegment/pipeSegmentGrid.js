Ext.namespace("com.increase.cen.pipe");
var pageSize = 10;
var limit = 10;


	// 列模型
	var cm = new Ext.grid.ColumnModel([
	{header : '管道段ID',dataIndex : 'pipeSegmentId',hidden : true}, 
	{header : '管道段名称',dataIndex : 'pipeSegmentName',width : 400,sortable : true}, 
	{header : '管道段编号',dataIndex : 'pipeSegmentCode',width : 180,sortable : true}, 
	{header : '管道类型',dataIndex : 'pipeSegmentType',width : 70,sortable : true,
		renderer:function(v){
			if(v=='1'){
				return "人井管段";
			}else if(v=='2'){
				return "引上类型";
			}else if(v=='3'){
				return "引入类型";
			}
		}
	}, 
	{header : '起始设施类型',dataIndex : 'startDeviceType',width : 90,sortable : true,
		renderer:function(v){
			if(v=='1'){
				return "井";
			}else if(v=='2'){
				return "杆";
			}
		}
	}, 
	{header : '起始设施名称',dataIndex : 'startDeviceName',width : 200,sortable : true},
	{header : '终止设施类型',dataIndex : 'endDeviceType',width : 90,sortable : true,
		renderer:function(v){
			if(v=='1'){
				return "井";
			}else if(v=='2'){
				return "杆";
			}
		}
	},
	{header : '终止设施名称',dataIndex : 'endDeviceName',width : 200,sortable : true},
	{header : '最后更新时间',dataIndex : 'lastUpdateDate',width : 150,sortable : true,
		renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')
	}]);
	// 从远程读取数据
	var proxy = new Ext.data.HttpProxy({
		url : "pipe!searchPipeSegmentlist.action"
	});
	// Record 定义记录结果
	var fiber = Ext.data.Record.create([
		{name : 'pipeSegmentId',type : 'string',mapping : 'pipeSegmentId',hidden : true
	}, {
		name : 'pipeSegmentName',
		type : 'string',
		mapping : 'pipeSegmentName'
	},{
		name : 'pipeSegmentCode',
		type : 'string',
		mapping : 'pipeSegmentCode'
	},{
		name : 'pipeSegmentType',
		type : 'string',
		mapping : 'pipeSegmentType'
	}, {
		name : 'startDeviceType',
		type : 'string',
		mapping : 'startDeviceType'
	}, {
		name : 'startDeviceName',
		type : 'string',
		mapping : 'startDeviceName'
	},{
		name : 'endDeviceType',
		type : 'string',
		mapping : 'endDeviceType'
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
		root : "pipesegments"
	}, fiber);
	var store = new Ext.data.Store({// 提供数据输入
		proxy : proxy,
		reader : reader,
		remoteSort : true
	});
	
	
	com.increase.cen.pipe.pipeSegmentGrid = Ext.extend(Ext.grid.GridPanel, {
		id:'pipeSegmentGrid',
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
			com.increase.cen.pipe.pipeSegmentGrid.superclass.initComponent.call(this);
		}
	});
	
Ext.reg("pipeSegmentGrid", com.increase.cen.pipe.pipeSegmentGrid);