Ext.namespace("com.increase.cen.pipe");

var cm = new Ext.grid.ColumnModel([
	{header : '子孔Id',dataIndex : 'tubeId',sortable : true,hidden : true}, 
	{header : '子孔名称',dataIndex : 'tubeName',sortable : true}, 
	{header : '子孔状态',dataIndex : 'tubeStatusEnumId',sortable : true,
		renderer:function(v){
			if(v=='0'){
				return "";
			}else if(v=='1'){
				return "占用";
			}else if(v=='2'){
				return "空闲";
			}else if(v=='3'){
				return "预留";
			}else if(v=='4'){
				return "暂拆";
			}else if(v=='5'){
				return "损坏";
			}else if(v=='6'){
				return "废弃";
			}else if(v=='7'){
				return "外租";
			}else if(v=='8'){
				return "出租";
			}
		}	
	}, 
	{header : '子孔直径',dataIndex : 'tubeDiameter',sortable : true},
	{header : '子孔颜色',dataIndex : 'tubeColor',sortable : true,
		renderer:function(v){
			if(v=='0'){
				return "";
			}else if(v=='1'){
				return "红";
			}else if(v=='2'){
				return "橙";
			}else if(v=='3'){
				return "黄";
			}else if(v=='4'){
				return "绿";
			}else if(v=='5'){
				return "青";
			}else if(v=='6'){
				return "蓝";
			}else if(v=='7'){
				return "紫";
			}else if(v=='8'){
				return "白";
			}else if(v=='9'){
				return "黑";
			}
		}	
	},
	{header : '子孔材质',dataIndex : 'tubeMaterial',sortable : true,
		renderer:function(v){
			if(v=='0'){
				return "";
			}else if(v=='1'){
				return "塑料管";
			}else if(v=='2'){
				return "陶瓷管";
			}else if(v=='3'){
				return "水泥管";
			}else if(v=='4'){
				return "钢管";
			}
		}	
	},
	{header : '子孔形状',dataIndex : 'tubeShape',sortable : true,
		renderer:function(v){
			if(v=='0'){
				return "";
			}else if(v=='1'){
				return "圆形";
			}else if(v=='2'){
				return "方形";
			}else if(v=='3'){
				return "梅花孔形";
			}
		}
	},
	{header : '是否租用',dataIndex : 'rentFlag',sortable : true,
		renderer:function(v){
			if(v=='0'){
				return "";
			}else if(v=='1'){
				return "是";
			}else if(v=='2'){
				return "否";
			}
		}
	},
	{header : '租用单位',dataIndex : 'rentOrg',sortable : true},
	{header : '资源生命周期',dataIndex : 'resourceLifePeriodEnumId',sortable : true,
		renderer:function(v){
			if(v=='0'){
				return ' ';
			}else if(v=='1'){
				return "设计状态";
			}else if(v=='2'){
				return "工程建设期（入网带业务前）";
			}else if(v=='3'){
				return "工程可用期（已入网带业务）";
			}else if(v=='4'){
				return "工程初验后试运行";
			}else if(v=='5'){
				return "工程终验后在网";
			}else if(v=='6'){
				return "退网状态";
			}
		}		
	}
	]);

	var proxy = new Ext.data.HttpProxy({
		url : "pipe!getFacetube.action"
	});
	// Record 定义记录结果
	var tube = Ext.data.Record.create([
	    {name : 'tubeId',type : 'string',mapping : 'tubeId'},
	    {name : 'tubeName',mapping : 'tubeName',type : 'string'},
	    {name : 'tubeStatusEnumId',type : 'string',mapping : 'tubeStatusEnumId'},
	    {name : 'tubeDiameter',type : 'string',mapping : 'tubeDiameter'},
	    {name : 'tubeColor',type : 'string',mapping : 'tubeColor'},
	    {name : 'tubeMaterial',type : 'string',mapping : 'tubeMaterial'},
	    {name : 'tubeShape',type : 'string',mapping : 'tubeShape'},
	    {name : 'rentFlag',type : 'string',mapping : 'rentFlag'},
	    {name : 'rentOrg',type : 'string',mapping : 'rentOrg'},
	    {name : 'resourceLifePeriodEnumId',type : 'string',mapping : 'resourceLifePeriodEnumId'}
    ]);
	
	var reader = new Ext.data.JsonReader({
		totalProperty : "total",
		root : "tubes"
	}, tube);
	var store = new Ext.data.Store({// 提供数据输入
		proxy : proxy,
		reader : reader,
		remoteSort : true
	});
	
	com.increase.cen.pipe.tubeSon = Ext.extend(Ext.grid.GridPanel, {
		region : 'center',
		id : "tubeson",
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
			com.increase.cen.pipe.tubeSon.superclass.initComponent.call(this);
		}
	});
Ext.reg("tubeson", com.increase.cen.pipe.tubeSon);