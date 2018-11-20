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
{header : 'logID',dataIndex : 'generatorName',hidden : true}, 
{header : '操作时间',dataIndex : 'logTime',
	renderer:function(v){
		v=v.replace("T"," ");
		v=grey(v);
		return v;
	}	
}, 
{header : '操作人',dataIndex : 'logOperater',renderer:grey},
{header : '操作类型',dataIndex : 'operationType',renderer:OperationType}, 
{header : '电杆ID',dataIndex : 'poleId',hidden : true}, 
{header : '电杆名称',dataIndex : 'poleNameSub',renderer:color1}, 

{header : '电杆序号',dataIndex : 'poleNo',renderer:color1}, 
{header : '扩充后缀',dataIndex : 'poleSubNo',renderer:color1}, 
{header : '电杆编码',dataIndex : 'poleCode',renderer:color1}, 
{header : '电杆类型',dataIndex : 'poleTypeEnumId',
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
{header : '电杆材质',dataIndex : 'poleMaterial',renderer:color1}, 
{header : '电杆程式',dataIndex : 'poleLength',renderer:color1}, 
{header : '电杆埋深',dataIndex : 'buriedDepth',renderer:color1}, 
{header : '电杆半径',dataIndex : 'poleRadius',renderer:color1}, 
{header : '电杆地址',dataIndex : 'poleAddress',renderer:color1}, 

{header : '电杆经度',dataIndex : 'poleLongitude',renderer:color1}, 
{header : '电杆纬度',dataIndex : 'poleLatitude',renderer:color1}, 
{header : '购买年限',dataIndex : 'purchasedMaintenanceTime',renderer:Ext.util.Format.dateRenderer('Y-m-d H:i:s')}, 
{header : '维护方式',dataIndex : 'maintenanceModeEnumId',renderer:maintenanceMode}, 
{header : '维护单位',dataIndex : 'maintenanceOrgId',renderer:color1}, 
{header : '第三方维护 单位',dataIndex : 'thirdPartyMaintenanceOrg',renderer:color1}, 
{header : '维护类型',dataIndex : 'maintenanceTypeEnumId',renderer:maintenanceType}, 

{header : '最后更新时间',dataIndex : 'lastUpdateDate',sortable : true,
	renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')
}]);
// 从远程读取数据
var proxy = new Ext.data.HttpProxy({
	url : "log!getPoleLog.action"

});
// Record 定义记录结果
var fiber = Ext.data.Record.create([
	{name : 'poleId',type : 'string',mapping : 'poleId',hidden : true
}, {name : 'logId',type : 'string',mapping : 'logId'
},{name : 'logTime',type : 'string',mapping : 'logTime'
},{name : 'logOperater',type : 'string',mapping : 'logOperater'
},{name : 'operationType',type : 'string',mapping : 'operationType'
},{
	name : 'poleNameSub',
	type : 'string',
	mapping : 'poleNameSub'
}, {
	name : 'poleNo',
	type : 'string',
	mapping : 'poleNo'
}, {
	name : 'poleSubNo',
	type : 'string',
	mapping : 'poleSubNo'
}, {
	name : 'poleCode',
	type : 'string',
	mapping : 'poleCode'
}, {
	name : 'poleTypeEnumId',
	type : 'string',
	mapping : 'poleTypeEnumId'
}, {
	name : 'poleMaterial',
	type : 'string',
	mapping : 'poleMaterial'
}, {
	name : 'poleLength',
	type : 'string',
	mapping : 'poleLength'
}, {
	name : 'buriedDepth',
	type : 'string',
	mapping : 'buriedDepth'
}, {
	name : 'poleRadius',
	type : 'string',
	mapping : 'poleRadius'
}, {
	name : 'poleAddress',
	type : 'string',
	mapping : 'poleAddress'
}, {
	name : 'poleLongitude',
	type : 'string',
	mapping : 'poleLongitude'
}, {
	name : 'poleLatitude',
	type : 'string',
	mapping : 'poleLatitude'
}, {
	name : 'purchasedMaintenanceTime',
	type : Ext.data.Types.DATE,
 	dateFormat : 'Y-m-d\\TH:i:s',
	mapping : 'purchasedMaintenanceTime'
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
	name : 'lastUpdateDate',
	mapping : 'lastUpdateDate',
	type : Ext.data.Types.DATE,
	dateFormat : 'Y-m-d\\TH:i:s'
}]);
// Reader以提供的Ext.data.Record 构造器中的映射为基础，从JSON响应创建一个Ext.data.Record 对象数组
var reader = new Ext.data.JsonReader({
	totalProperty : "total",
	root : "poles"
}, fiber);
var store = new Ext.data.Store({// 提供数据输入
	proxy : proxy,
	reader : reader,
	remoteSort : true
	/*sortInfo: {  
    	field: 'jumpfiberId',  
    	direction: "DESC"  
	}*/
});
	com.increase.cen.log.alonePole = Ext.extend(Ext.grid.GridPanel, {
		region : 'center',
		id : "alonePole",
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
			com.increase.cen.log.alonePole.superclass.initComponent.call(this);
		}
	});
Ext.reg("alonePole", com.increase.cen.log.alonePole);