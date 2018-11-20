Ext.namespace("com.increase.cen.log");


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
	{header : 'logID',dataIndex : 'generatorName',sortable : true,hidden : true}, 
	{header : '操作时间',dataIndex : 'logTime',
		renderer:function(v){
			v=v.replace("T"," ");
			v=grey(v);
			return v;
		}
	}, 
	{header : '操作人',dataIndex : 'logOperater',renderer:grey},
	{header : '操作类型',dataIndex : 'operationType',renderer:OperationType}, 
	{header : 'id',dataIndex : 'id',hidden:true},
	{
		header : '设备ID',
		dataIndex : 'eid',
		width : 100,
		hidden:true
	},{
		header : '设备名称',
		dataIndex : 'ename',
		width : 100,
		renderer:color1
	}, {
		header : '设备编码',
		dataIndex : 'ecode',
		width : 100,
		renderer:color1
	}, {
		header : '设备地址',
		dataIndex : 'eaddr',
		width : 120,
		renderer:color1
	}, {
		header : '设备类型',
		dataIndex : 'etype',
		width : 100,
		renderer : function(v) {
			return getEtypeStr(v);
		}
	}, {
		header : '设备状态',
		dataIndex : 'estat',
		width : 60,
		renderer : function(v) {
			return getEstatStr(v);
		}
	}, {
		header : '设备型号',
		dataIndex : 'emodel',
		width : 100,
		renderer:color1
	},	{
		header : '维护人',
		dataIndex : 'mp',
		width : 100,
		renderer:color1
	},{
		header : '维护时间',
		dataIndex : 'mtime',
		width : 100,
		renderer:Ext.util.Format.dateRenderer('Y-m-d H:i:s')
	},{
		header : '经度',
		dataIndex : 'lon',
		width : 100,
		renderer:color1
	},{
		header : '纬度',
		dataIndex : 'lat',
		width : 100,
		renderer:color1
	},{
		header : '负责人',
		dataIndex : 'prlusername',
		width : 100,
		renderer:color1
	}]);
	// var pageSize = Ext.getDom("domainCode").value;
	// 从远程读取数据
	var proxy = new Ext.data.HttpProxy({
		url : "log!getEqutLog.action"
	});
	// Record 定义记录结果
	var equt = Ext.data.Record.create([
    {name : 'logId',type : 'string',mapping : 'logId'
	},{name : 'logTime',mapping : 'logTime',type : 'string'
	},{name : 'logOperater',type : 'string',mapping : 'logOperater'
	},{name : 'operationType',type : 'string',mapping : 'operationType'
	},{name : 'id',type : 'string',mapping : 'id'
	},{
		name : 'eid',
		type : 'string',
		mapping : 'eid'
	},{
		name : 'ename',
		type : 'string',
		mapping : 'ename'
	}, {
		name : 'ecode',
		type : 'string',
		mapping : 'ecode'
	}, {
		name : 'eaddr',
		type : 'string',
		mapping : 'eaddr'
	}, {
		name : 'etype',
		type : 'string',
		mapping : 'etype'
	}, {
		name : 'estat',
		type : 'string',
		mapping : 'estat'
	}, {
		name : 'emodel',
		type : 'string',
		mapping : 'emodel'
	}, {
		name : 'mp',
		type : 'string',
		mapping : 'mp'
	}, {
		name : 'mtime',
		type : Ext.data.Types.DATE,
     	dateFormat : 'Y-m-d\\TH:i:s',
		mapping : 'mtime'
	}, {
		name : 'lon',
		type : 'string',
		mapping : 'lon'
	}, {
		name : 'lat',
		type : 'string',
		mapping : 'lat'
	}, {
		name : 'prlusername',
		type : 'string',
		mapping : 'prlusername'
	}]);
	
	var reader = new Ext.data.JsonReader({
		totalProperty : "total",
		root : "equts"
	}, equt);
	var store = new Ext.data.Store({// 提供数据输入
		proxy : proxy,
		reader : reader,
		remoteSort : true
	});
	
	com.increase.cen.log.aloneOccEqut = Ext.extend(Ext.grid.GridPanel, {
		region : 'center',
		id : "aloneOccEqut",
		border : false,
		cm : cm,// 列定义的model(必需)
		store : store,
		autoScroll : true,// 滚动条
		viewConfig : {
			sortAscText : '升序',
			sortDescText : '降序',
			columnsText : '可选列',
			forceFit : false,// Ture表示自动扩展或缩小列的宽度
			scrollOffset : -1
		},

		initComponent : function(config) {
			// 重写initComponent superclass.initComponent.call(this);来调用父类的初始化方法.
			com.increase.cen.log.aloneOccEqut.superclass.initComponent.call(this);
		}
	});
Ext.reg("aloneOccEqut", com.increase.cen.log.aloneOccEqut);