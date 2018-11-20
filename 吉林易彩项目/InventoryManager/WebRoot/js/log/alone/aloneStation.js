Ext.namespace("com.increase.cen.log");


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
{header : '局站ID',dataIndex : 'stationBaseId',hidden : true}, 
{header : '局站名称',dataIndex : 'stationName',renderer:color1}, 
{header : '所属维护区域',dataIndex : 'region',width : 150,renderer:color1},
{header : '所属行政区域标识',dataIndex : 'regionalism',hidden : true},
{header : '站点经度',dataIndex : 'lon',renderer:color1},
{header : '站点纬度',dataIndex : 'lat',renderer:color1},
{header : '站点地址',dataIndex : 'stationAddr',width:150,renderer:color1},
{header : '站点类型',dataIndex : 'stationType',sortable : true,
	renderer:function(v){
		if(v=='0'){
			return "普通局站";
		}else if(v=='1'){
			return "基站站点";
		}else if(v=='2'){
			return "多机房ITMC";
		}else if(v=='3'){
			return "边境站";
		}
	}
},
{header : '站点网络类型',dataIndex : 'stationNetWorkType',sortable : true,
	renderer:function(v){
		if(v=='0'){
			return "GSM";
		}else if(v=='1'){
			return "WCDMA";
		}else if(v=='2'){
			return "GSM和WCDMA共用";
		}
	}	
},
{header : '局站等级',dataIndex : 'stationLevel',width : 100,sortable : true,
	renderer:function(v){
		if(v=='0'){
			return "A";
		}else if(v=='1'){
			return "B";
		}else if(v=='2'){
			return "C";
		}
	}		
},
{header : '站点范围',dataIndex : 'stationRange',sortable : true,
	renderer:function(v){
		if(v=='0'){
			return "城市";
		}else if(v=='1'){
			return "乡村";
		}
	}	
},
{header : '固移共站',dataIndex : 'sameStation',renderer:color1},
{header : '固网专用',dataIndex : 'fixedDedicated',renderer:color1},
{header : '工程项目编号',dataIndex : 'projectCode',renderer:color1},
{header : '工程项目名称',dataIndex : 'projectName',renderer:color1},
{header : '工程保修截止日期',dataIndex : 'projectGuaranteeOverTime',renderer:Ext.util.Format.dateRenderer('Y-m-d H:i:s')},
{header : '是否授权管理',dataIndex : 'isAuthorizationManagement',sortable : true,
	renderer:function(v){
		if(v=='0'){
			return "是";
		}else if(v=='1'){
			return "否";
		}
	}
},
{header : '授权管理单位',dataIndex : 'authorizationManagementUnit',renderer:color1},
{header : '创建时间',dataIndex : 'creationDate',width : 150,renderer:Ext.util.Format.dateRenderer('Y-m-d H:i:s')},
{header : '最后更新时间',dataIndex : 'lastUpdateDate',width : 150,renderer:Ext.util.Format.dateRenderer('Y-m-d H:i:s')},
{header : '备注',dataIndex : 'note',width : 150,renderer:color1}
]);
// 从远程读取数据
var proxy = new Ext.data.HttpProxy({
	url : "log!getStationLog.action"
});
// Record 定义记录结果
var station= Ext.data.Record.create([
{name : 'stationBaseId',type : 'string',mapping : 'stationBaseId'
},{name : 'logId',type : 'string',mapping : 'logId'
},{name : 'logTime',type : 'string',mapping : 'logTime'
},{name : 'logOperater',type : 'string',mapping : 'logOperater'
},{name : 'operationType',type : 'string',mapping : 'operationType'
},{
	name : 'stationName',
	type : 'string',
	mapping : 'stationName'
},{
	name : 'region',
	type : 'string',
	mapping : 'region'
},{
	name : 'regionalism',
	type : 'string',
	mapping : 'regionalism'
},{
	name : 'lon',
	type : 'string',
	mapping : 'lon'
},{
	name : 'lat',
	type : 'string',
	mapping : 'lat'
},{
	name : 'stationAddr',
	type : 'string',
	mapping : 'stationAddr'
},{
	name : 'stationType',
	type : 'string',
	mapping : 'stationType'
},{
	name : 'stationNetWorkType',
	type : 'string',
	mapping : 'stationNetWorkType'
},{
	name : 'stationLevel',
	type : 'string',
	mapping : 'stationLevel'
},{
	name : 'stationRange',
	type : 'string',
	mapping : 'stationRange'
},{
	name : 'sameStation',
	type : 'string',
	mapping : 'sameStation'
},{
	name : 'fixedDedicated',
	type : 'string',
	mapping : 'fixedDedicated'
},{
	name : 'projectCode',
	type : 'string',
	mapping : 'projectCode'
},{
	name : 'projectName',
	type : 'string',
	mapping : 'projectName'
},{
	name : 'projectGuaranteeOverTime',
	type : Ext.data.Types.DATE,
 	dateFormat : 'Y-m-d\\TH:i:s',
	mapping : 'projectGuaranteeOverTime'
},{
	name : 'isAuthorizationManagement',
	type : 'string',
	mapping : 'isAuthorizationManagement'
},{
	name : 'authorizationManagementUnit',
	type : 'string',
	mapping : 'authorizationManagementUnit'
},{
	name : 'creationDate',
	type : Ext.data.Types.DATE,
 	dateFormat : 'Y-m-d\\TH:i:s',
	mapping : 'creationDate'
},{
	name : 'lastUpdateDate',
	type : Ext.data.Types.DATE,
 	dateFormat : 'Y-m-d\\TH:i:s',
	mapping : 'lastUpdateDate'
	
},{
	name : 'note',
	type : 'string',
	mapping : 'note'
}]);
// Reader以提供的Ext.data.Record 构造器中的映射为基础，从JSON响应创建一个Ext.data.Record 对象数组
var reader = new Ext.data.JsonReader({
	totalProperty : "total",
	root : "stations"
}, station);
var store = new Ext.data.Store({// 提供数据输入
	proxy : proxy,
	reader : reader,
	remoteSort : true
});
	
	com.increase.cen.log.aloneStation = Ext.extend(Ext.grid.GridPanel, {
		region : 'center',
		id : "aloneStation",
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
			com.increase.cen.log.aloneStation.superclass.initComponent.call(this);
		}
	});
Ext.reg("aloneStation", com.increase.cen.log.aloneStation);