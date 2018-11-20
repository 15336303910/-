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
{header : '井ID',dataIndex : 'wellId',hidden : true}, 
{header : '人/手井名称',dataIndex : 'wellNameSub',renderer:color1}, 
{header : '别名',dataIndex : 'alias',renderer:color1}, 
{header : '方位',dataIndex : 'direction',renderer:color1}, 
{header : '序号',dataIndex : 'wellNo',renderer:color1}, 
{header : '扩充后缀',dataIndex : 'wellSubNo',renderer:color1}, 
{header : '井类型',dataIndex : 'manholeTypeEnumId',sortable : true,
	renderer:function(v){
		if(v=='1'){
			return "人孔";
		}else if(v=='2'){
			return "手孔";
		}else if(v=='3'){
			return "双人孔";
		}else if(v=='4'){
			return "三人孔";
		}else if(v=='5'){
			return "局前井";
		}else if(v=='6'){
			return "地下室";
		}
	}
}, 

{header : '人/手井经度',dataIndex : 'longitude',renderer:color1}, 
{header : '人/手井纬度',dataIndex : 'latitude',renderer:color1},
{header : '盖井高程',dataIndex : 'coverTopElevation',renderer:color1}, 
{header : '井坑底高程',dataIndex : 'latitude',renderer:color1},
{header : '人/手井地址',dataIndex : 'wellAddress',width : 200,renderer:color1}, 
{header : '井深（米）',dataIndex : 'manholeDepth',renderer:color1},
{header : '井长（米）',dataIndex : 'manholeLength',renderer:color1},
{header : '井宽（米）',dataIndex : 'manholeWidth',renderer:color1},
{header : '井形状',dataIndex : 'manholeShape',renderer:color1},
{header : '井规格',dataIndex : 'manholeSpecification',renderer:color1},
{header : '井结构',dataIndex : 'manholeStructure',renderer:color1},
{header : '道路类型',dataIndex : 'roadTypeEnumId',renderer:color1},
{header : '路面类型',dataIndex : 'roadSurfaceTypeEnumId',renderer:color1},
{header : '井盖材质',dataIndex : 'coverMaterialEnumId',renderer:color1},
{header : '井盖形状',dataIndex : 'coverShapeEnumId',renderer:color1},
{header : '井盖数量',dataIndex : 'coverQuantity',renderer:color1},
{header : '井壁厚度',dataIndex : 'wallThickness',renderer:color1},
{header : '底基厚度',dataIndex : 'subbaseThickness',renderer:color1},
{header : '维护方式',dataIndex : 'maintenanceModeEnumId',renderer:maintenanceMode},
{header : '维护单位',dataIndex : 'maintenanceOrgId',renderer:color1},
{header : '第三方维护单位',dataIndex : 'thirdPartyMaintenanceOrg',renderer:color1},
{header : '维护类型',dataIndex : 'maintenanceTypeEnumId',renderer:maintenanceType},
{header : '购买年限',dataIndex : 'purchasedMaintenanceTime',renderer:Ext.util.Format.dateRenderer('Y-m-d H:i:s')},
{header : '资源生命周期',dataIndex : 'resourceLifePeriodEnumId',renderer:color1},
{header : '项目编号',dataIndex : 'projectNumber',renderer:color1},
{header : '项目名称',dataIndex : 'projectName',renderer:color1},
{header : '续保截止日期',dataIndex : 'renewalExpirationDate',renderer:Ext.util.Format.dateRenderer('Y-m-d H:i:s')},
{header : '工程保修截止日期',dataIndex : 'projectWarrantyExpireDate',renderer:Ext.util.Format.dateRenderer('Y-m-d H:i:s')},
{header : '最后更新时间',dataIndex : 'lastUpdateDate',width : 180,sortable : true,
	renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')
}]);
// 从远程读取数据
var proxy = new Ext.data.HttpProxy({
	url : "log!getWellLog.action"

});
// Record 定义记录结果
var fiber = Ext.data.Record.create([
	{name : 'wellId',type : 'string',mapping : 'wellId',hidden : true
},{name : 'logId',type : 'string',mapping : 'logId'
},{name : 'logTime',type : 'string',mapping : 'logTime'
},{name : 'logOperater',type : 'string',mapping : 'logOperater'
},{name : 'operationType',type : 'string',mapping : 'operationType'
}, {
	name : 'wellNameSub',
	type : 'string',
	mapping : 'wellNameSub'
}, {
	name : 'alias',
	type : 'string',
	mapping : 'alias'
},{
	name : 'direction',
	type : 'string',
	mapping : 'direction'
},{
	name : 'wellNo',
	type : 'string',
	mapping : 'wellNo'
},{
	name : 'wellSubNo',
	type : 'string',
	mapping : 'wellSubNo'
},{
	name : 'manholeTypeEnumId',
	type : 'string',
	mapping : 'manholeTypeEnumId'
}, {
	name : 'longitude',
	type : 'string',
	mapping : 'longitude'
}, {
	name : 'latitude',
	type : 'string',
	mapping : 'latitude'
}, {
	name : 'coverTopElevation',
	type : 'string',
	mapping : 'coverTopElevation'
}, {
	name : 'latitude',
	type : 'string',
	mapping : 'latitude'
}, {
	name : 'wellAddress',
	type : 'string',
	mapping : 'wellAddress'
}, {
	name : 'manholeDepth',
	type : 'string',
	mapping : 'manholeDepth'
}, {
	name : 'manholeLength',
	type : 'string',
	mapping : 'manholeLength'
}, {
	name : 'manholeWidth',
	type : 'string',
	mapping : 'manholeWidth'
}, {
	name : 'manholeShape',
	type : 'string',
	mapping : 'manholeShape'
}, {
	name : 'manholeSpecification',
	type : 'string',
	mapping : 'manholeSpecification'
}, {
	name : 'manholeStructure',
	type : 'string',
	mapping : 'manholeStructure'
}, {
	name : 'roadTypeEnumId',
	type : 'string',
	mapping : 'roadTypeEnumId'
}, {
	name : 'roadSurfaceTypeEnumId',
	type : 'string',
	mapping : 'roadSurfaceTypeEnumId'
}, {
	name : 'coverMaterialEnumId',
	type : 'string',
	mapping : 'coverMaterialEnumId'
}, {
	name : 'coverShapeEnumId',
	type : 'string',
	mapping : 'coverShapeEnumId'
}, {
	name : 'coverQuantity',
	type : 'string',
	mapping : 'coverQuantity'
}, {
	name : 'wallThickness',
	type : 'string',
	mapping : 'wallThickness'
}, {
	name : 'subbaseThickness',
	type : 'string',
	mapping : 'subbaseThickness'
}, {
	name : 'maintenanceModeEnumId',
	type : 'string',
	mapping : 'maintenanceModeEnumId'
}, {
	name : 'maintenanceOrgId',
	type : 'string',
	mapping : 'maintenanceOrgId'
}, {
	name : 'thirdPartyMaintenanceOrg',
	type : 'string',
	mapping : 'thirdPartyMaintenanceOrg'
}, {
	name : 'maintenanceTypeEnumId',
	type : 'string',
	mapping : 'maintenanceTypeEnumId'
}, {
	name : 'purchasedMaintenanceTime',
	mapping : 'purchasedMaintenanceTime',
	type : Ext.data.Types.DATE,
	dateFormat : 'Y-m-d\\TH:i:s'
}, {
	name : 'resourceLifePeriodEnumId',
	type : 'string',
	mapping : 'resourceLifePeriodEnumId'
}, {
	name : 'projectNumber',
	type : 'string',
	mapping : 'projectNumber'
}, {
	name : 'projectName',
	type : 'string',
	mapping : 'projectName'
}, {
	name : 'renewalExpirationDate',
	mapping : 'renewalExpirationDate',
	type : Ext.data.Types.DATE,
	dateFormat : 'Y-m-d\\TH:i:s'
}, {
	name : 'projectWarrantyExpireDate',
	mapping : 'projectWarrantyExpireDate',
	type : Ext.data.Types.DATE,
	dateFormat : 'Y-m-d\\TH:i:s'
}, {
	name : 'lastUpdateDate',
	mapping : 'lastUpdateDate',
	type : Ext.data.Types.DATE,
	dateFormat : 'Y-m-d\\TH:i:s'
}]);
// Reader以提供的Ext.data.Record 构造器中的映射为基础，从JSON响应创建一个Ext.data.Record 对象数组
var reader = new Ext.data.JsonReader({
	totalProperty : "total",
	root : "wellLogs"
}, fiber);
var store = new Ext.data.Store({// 提供数据输入
	proxy : proxy,
	reader : reader,
	remoteSort : true
});
	
	com.increase.cen.log.aloneWell = Ext.extend(Ext.grid.GridPanel, {
		region : 'center',
		id : "aloneWell",
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
			com.increase.cen.log.aloneWell.superclass.initComponent.call(this);
		}
	});
Ext.reg("aloneWell", com.increase.cen.log.aloneWell);