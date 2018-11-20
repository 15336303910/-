Ext.namespace("com.increase.cen.pipe");
var tubesm = new Ext.grid.CheckboxSelectionModel({
	singleSelect : true
});

var cm = new Ext.grid.ColumnModel([tubesm,
	{header : '管孔Id',dataIndex : 'tubeId',sortable : true,hidden : true}, 
	{header : '管孔名称',dataIndex : 'tubeName',sortable : true}, 
	{header : '子孔数量',dataIndex : 'subTubeAmount',sortable : true}, 
	{header : '管孔状态',dataIndex : 'tubeStatusEnumId',sortable : true,
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
	{header : '管孔直径',dataIndex : 'tubeDiameter',sortable : true},
	{header : '管孔颜色',dataIndex : 'tubeColor',sortable : true,
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
	{header : '管孔材质',dataIndex : 'tubeMaterial',sortable : true,
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
	{header : '管孔形状',dataIndex : 'tubeShape',sortable : true,
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
	{header : '资源生命周期',dataIndex : 'resourceLifePeriodEnumId',sortable : true}
	]);
	// var pageSize = Ext.getDom("domainCode").value;
	// 从远程读取数据
	var proxy = new Ext.data.HttpProxy({
		url : "pipe!getFacetube.action"
	});
	// Record 定义记录结果
	var tube = Ext.data.Record.create([
    {name : 'tubeId',type : 'string',mapping : 'tubeId'},
    {name : 'tubeName',mapping : 'tubeName',type : 'string'},
    {name : 'subTubeAmount',type : 'string',mapping : 'subTubeAmount'},
    {name : 'tubeStatusEnumId',type : 'string',mapping : 'tubeStatusEnumId'},
    {name : 'tubeDiameter',type : 'string',mapping : 'tubeDiameter'},
    {name : 'tubeColor',type : 'string',mapping : 'tubeColor'},
    {name : 'tubeMaterial',type : 'string',mapping : 'tubeMaterial'},
    {name : 'tubeShape',type : 'string',mapping : 'tubeShape'},
    {name : 'rentFlag',type : 'string',mapping : 'rentFlag'},
    {name : 'rentOrg',type : 'string',mapping : 'rentOrg'},
    {name : 'resourceLifePeriodEnumId',type : 'string',mapping : 'resourceLifePeriodEnumId'},
    
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
	
	com.increase.cen.pipe.faceTube = Ext.extend(Ext.grid.GridPanel, {
		region : 'center',
		id : "facetube",
		border : false,
		cm : cm,// 列定义的model(必需)
		sm : tubesm,
		store : store,
		autoScroll : true,// 滚动条
		tbar:[{
			id : 'exportTubeBtn',
			text : '导出管孔',
			icon : "imgs/fiber/link_break.png",
			cls : "x-btn-text-icon",
			handler : function() {
				var selModel = Ext.getCmp("facetube").getSelectionModel();// 返回grid的SelectionModel
				if (selModel.hasSelection()) {
					var records = selModel.getSelections();
					var ids = [];
					for (var i = 0; i < records.length; i++) {
						ids.push(records[i].get("tubeId"));// 获得选择的编号
					}
					location.href="dataupdate!getTubelist.action?ids="+ids;
					
				}else{
					Ext.Msg.show({
						title : '提示',
						width : 300,
						msg : '<img src="imgs/tip_warn.png" align="center" hspace="30"/>'
								+ '请您至少选择一条数据进行操作!',
						buttons : {
							ok : "确定"
						}
					});
				}
			}
		},{
			xtype : 'tbseparator'// 一个分隔栏
		},{
			id : 'importTubeBtn',
			text : '导入管孔',
			icon : "imgs/fiber/link_break.png",
			cls : "x-btn-text-icon",
			handler : function() {
				var grid=Ext.getCmp("facetube");
				grid.importTubeWindow = new com.increase.cen.poleline.PolelineWindow({
					id : "importTubeWindow",
					height:500,
					title : '数据导入',
					iconCls : 'i-script-window',
					items : [{
						id:'importTube',
						fileUpload : true,
						width:600,
						height:300,
						xtype : 'importTube'// 窗口里的表单
					}]
				});
			
			grid.importTubeWindow.show();
			}
		}],
		viewConfig : {
			sortAscText : '升序',
			sortDescText : '降序',
			columnsText : '可选列',
			forceFit : false,// Ture表示自动扩展或缩小列的宽度
			scrollOffset : -1
		},

		initComponent : function(config) {
			// 重写initComponent superclass.initComponent.call(this);来调用父类的初始化方法.
			com.increase.cen.pipe.faceTube.superclass.initComponent.call(this);
		}
	});
Ext.reg("facetube", com.increase.cen.pipe.faceTube);