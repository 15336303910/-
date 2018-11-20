Ext.onReady(function() {
	// 获取每页显示的记录数
	var pageSize = parseInt(Ext.getDom("pageSize").value);
	var limit = parseInt(Ext.getDom("limit").value);
	var sm = new Ext.grid.CheckboxSelectionModel({
		singleSelect : true
	});
	// 列模型
	var cm = new Ext.grid.ColumnModel([sm,
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
	{header : '机房ID',dataIndex : 'generatorId',hidden : true}, 
	{header : '机房名称',dataIndex : 'generatorName',renderer:color1}, 
	{header : '所属维护区域',dataIndex : 'region',width : 100,renderer:color1}, 
	{header : '所属局站',dataIndex : 'station',width : 100,renderer:color1}, 
	{header : '地理位置经度',dataIndex : 'lon',renderer:color1}, 
	{header : '地理位置纬度',dataIndex : 'lat',renderer:color1}, 
	{header : '机房地址',dataIndex : 'generatorAddr',width : 150,renderer:color1},
	{header : '机房常用名称',dataIndex : 'generatorCommonName',renderer:color1}, 
	{header : '建筑面积',dataIndex : 'floorArea',renderer:color1},
	{header : '可用面积',dataIndex : 'canUseArea',renderer:color1},
	{header : '已用面积',dataIndex : 'usedArea',renderer:color1},
	{header : '长度',dataIndex : 'length',renderer:color1}, 
	{header : '宽度',dataIndex : 'width',renderer:color1}, 
	{header : '高度',dataIndex : 'height',renderer:color1}, 
	{header : '机房归属',dataIndex : 'affiliation',sortable : true,
		renderer:function(v){
			if(v=='0'){
				return "自建";
			}else if(v=='1'){
				return "合作";
			}else if(v=='2'){
				return "客户";
			}else if(v=='3'){
				return "租用";
			}
		}	
	},
	{header : '是否共享',dataIndex : 'isShare',sortable : true,
		renderer:function(v){
			if(v=='0'){
				return "是";
			}else if(v=='1'){
				return "否";
			}
		}
	},
	
	
	{header : '共享方',dataIndex : 'shareOperator',width : 100,renderer:color1},
	{header : '机房大类',dataIndex : 'broadHeading',
		renderer:function(v){
			if(v=='0'){
				return "A类";
			}else if(v=='1'){
				return "B类";
			}else if(v=="2"){
				return "C类";
			}
		}
		}, 
	{header : '机房小类',dataIndex : 'subclass',
			renderer:function(v){
				if(v=='0'){
					return "长途传输机房";
				}else if(v=='1'){
					return "长途交换机房";
				}else if(v=="2"){
					return "长途IP机房";
				}else if(v=="3"){
					return "长途数据机房";
				}else if(v=="4"){
					return "长途专业综合机房";
				}else if(v=="5"){
					return "移动核心机房";
				}else if(v=="6"){
					return "本地核心交换机房";
				}else if(v=="7"){
					return "本地核心数据机房";
				}else if(v=="8"){
					return "本地核心专业综合机房";
				}else if(v=="9"){
					return "城域IP网核心机房";
				}else if(v=="10"){
					return "电力室";
				}else if(v=="11"){
					return "高级、低级信令转接点机房";
				}else if(v=="12"){
					return "IDC机房";
				}else if(v=="13"){
					return "本地城域网传输核心、汇聚、边缘层机房";
				}else if(v=="14"){
					return "本地城域网IP网汇聚层机房";
				}else if(v=="15"){
					return "本地交换端局机房";
				}else if(v=="16"){
					return "本地数据端局机房";
				}else if(v=="17"){
					return "本地专业综合机房";
				}else if(v=="18"){
					return "中继机房";
				}else if(v=="19"){
					return "移动基站机房";
				}else if(v=="20"){
					return "小灵通基站机房";
				}else if(v=="21"){
					return "交换模块局机房";
				}else if(v=="22"){
					return "PSTN接入网机房";
				}else if(v=="23"){
					return "综合接入网机房";
				}else if(v=="24"){
					return "其他接入网机房";
				}else if(v=="25"){
					return "用户机房";
				}else if(v=="26"){
					return "海外POP点";
				}else if(v=="27"){
					return "卫星站";
				}else if(v=="28"){
					return "国际传输维护中心";
				}else if(v=="29"){
					return "国际交换维护中心";
				}
			}		
	}, 
	{header : '海外PoP点提供业务类型',dataIndex : 'overseasPopBusinessType',
		renderer:function(v){
			if(v=='0'){
				return "海事卫星电话";
			}else if(v=='1'){
				return "大洋通";
			}else if(v=="2"){
				return "国际基础设施";
			}else if(v=="3"){
				return "国际运营商设备托管";
			}else if(v=="4"){
				return "卫星电路";
			}
		}
	}, 
	{header : '是否基站站点',dataIndex : 'isBaseStation',sortable : true,
		renderer:function(v){
			if(v=='0'){
				return "是";
			}else if(v=='1'){
				return "否";
			}
		}
	},
	{header : '投产时间',dataIndex : 'commissioningDate',renderer:Ext.util.Format.dateRenderer('Y-m-d H:i:s')}, 
	{header : '机房承重',dataIndex : 'bearing',renderer:color1}, 
	{header : '走线方式',dataIndex : 'takeUpMode',sortable : true,
		renderer:function(v){
			if(v=='0'){
				return "上走线";
			}else if(v=='1'){
				return "下走线";
			}else if(v=='2'){
				return "混合走线";
			}
		}	
	},
	{header : '单双层走线架',dataIndex : 'singleLayerChamfer',
		renderer:function(v){
			if(v=='0'){
				return "单层走线";
			}else if(v=='1'){
				return "双层走线";
			}else if(v=='2'){
				return "多层走线";
			}
		}	
	}, 
	{header : '消防系统',dataIndex : 'fireFightingSystem',renderer:color1},
	{header : '有无地板',dataIndex : 'isHaveFloor',
		renderer:function(v){
			if(v=='0'){
				return "有";
			}else if(v=='1'){
				return "无";
			}
		}
	},
	{header : '有无监控',dataIndex : 'isHaveMonitor',
		renderer:function(v){
			if(v=='0'){
				return "有";
			}else if(v=='1'){
				return "无";
			}
		}
	},
	{header : '租用到期时间',dataIndex : 'overTime',renderer:Ext.util.Format.dateRenderer('Y-m-d H:i:s')},
	{header : '房屋类型',dataIndex : 'buildingType',
		renderer:function(v){
			if(v=='0'){
				return "砖混";
			}else if(v=='1'){
				return "彩钢板";
			}else if(v=='2'){
				return "其他";
			}
		}	
	},
	{header : '包机人',dataIndex : 'charteredMan',renderer:color1},
	{header : '维护方式',dataIndex : 'maIntegeraIntegerype',sortable : true,
		renderer:function(v){
			if(v=='0'){
				return "自维";
			}else if(v=='1'){
				return "代维";
			}
		}		
	},
	{header : '维护单位',dataIndex : 'maIntegerainUnit',width : 100,renderer:color1},
	{header : '第三方维护单位',dataIndex : 'otherMaIntegerainUnit',renderer:color1},
	{header : '续保截止日期',dataIndex : 'renewalOverTime',renderer:Ext.util.Format.dateRenderer('Y-m-d H:i:s')},
	{header : '维保类型',dataIndex : 'maIntegerenanceType',
		renderer:function(v){
			if(v=='0'){
				return "硬件维修";
			}else if(v=='1'){
				return "紧急备件支持";
			}else if(v=='2'){
				return "软件补丁版本支持";
			}else if(v=='3'){
				return "技术支持服务（电话咨询、电话支持、远程支持）";
			}else if(v=='4'){
				return "无";
			}
		}	
	},
	{header : '已购买维保累计年限',dataIndex : 'purchasedMaIntegerenanceTime',renderer:color1},
	{header : '工程项目编号',dataIndex : 'projectCode',renderer:color1},
	{header : '工程项目名称',dataIndex : 'projectName',renderer:color1},
	{header : '工程项目保修截止日期',dataIndex : 'projectGuaranteeOverTime',renderer:Ext.util.Format.dateRenderer('Y-m-d H:i:s')},
	{header : '是否授权管理',dataIndex : 'isAuthorizationManagement',
		renderer:function(v){
			if(v=='0'){
				return "是";
			}else if(v=='1'){
				return "否";
			}
		}
	},
	{header : '授权管理单位',dataIndex : 'authorizationManagementUnit',renderer:color1},
	{header : '备注',dataIndex : 'note',width : 100,renderer:color1}
	]);
	// 从远程读取数据
	var proxy = new Ext.data.HttpProxy({
		url : "log!getGeneratorLog.action"
	});
	// Record 定义记录结果
	var generator = Ext.data.Record.create([
	{name : 'generatorId',type : 'string',mapping : 'generatorId'
	},{name : 'logId',type : 'string',mapping : 'logId'
	},{name : 'logTime',type : 'string',mapping : 'logTime'
	},{name : 'logOperater',type : 'string',mapping : 'logOperater'
	},{name : 'operationType',type : 'string',mapping : 'operationType'
	},{
		name : 'generatorName',
		type : 'string',
		mapping : 'generatorName'
	},{
		name : 'region',
		type : 'string',
		mapping : 'region'
	},{
		name : 'station',
		type : 'string',
		mapping : 'station'
	},{
		name : 'lon',
		type : 'string',
		mapping : 'lon'
	},{
		name : 'lat',
		type : 'string',
		mapping : 'lat'
	},{
		name : 'generatorAddr',
		type : 'string',
		mapping : 'generatorAddr'
	},{
		name : 'generatorCommonName',
		type : 'string',
		mapping : 'generatorCommonName'
	},{
		name : 'floorArea',
		type : 'string',
		mapping : 'floorArea'
	},{
		name : 'canUseArea',
		type : 'string',
		mapping : 'canUseArea'
	},{
		name : 'usedArea',
		type : 'string',
		mapping : 'usedArea'
	},{
		name : 'length',
		type : 'string',
		mapping : 'length'
	},{
		name : 'width',
		type : 'string',
		mapping : 'width'
	},{
		name : 'height',
		type : 'string',
		mapping : 'height'
	},{
		name : 'affiliation',
		type : 'string',
		mapping : 'affiliation'
	},{
		name : 'isShare',
		type : 'string',
		mapping : 'isShare'
	},{
		name : 'shareOperator',
		type : 'string',
		mapping : 'shareOperator'
	},{
		name : 'broadHeading',
		type : 'string',
		mapping : 'broadHeading'
	},{
		name : 'subclass',
		type : 'string',
		mapping : 'subclass'
	},{
		name : 'overseasPopBusinessType',
		type : 'string',
		mapping : 'overseasPopBusinessType'
	},{
		name : 'isBaseStation',
		type : 'string',
		mapping : 'isBaseStation'
	},{
		name : 'commissioningDate',
		mapping : 'commissioningDate',
		type : Ext.data.Types.DATE,
		dateFormat : 'Y-m-d\\TH:i:s'
	},{
		name : 'bearing',
		type : 'string',
		mapping : 'bearing'
	},{
		name : 'takeUpMode',
		type : 'string',
		mapping : 'takeUpMode'
	},{
		name : 'singleLayerChamfer',
		type : 'string',
		mapping : 'singleLayerChamfer'
	},{
		name : 'fireFightingSystem',
		type : 'string',
		mapping : 'fireFightingSystem'
	},{
		name : 'isHaveFloor',
		type : 'string',
		mapping : 'isHaveFloor'
	},{
		name : 'isHaveMonitor',
		type : 'string',
		mapping : 'isHaveMonitor'
	},{
		name : 'overTime',
		mapping : 'overTime',
		type : Ext.data.Types.DATE,
		dateFormat : 'Y-m-d\\TH:i:s'
	},{
		name : 'buildingType',
		type : 'string',
		mapping : 'buildingType'
	},{
		name : 'charteredMan',
		type : 'string',
		mapping : 'charteredMan'
	},{
		name : 'maIntegeraIntegerype',
		type : 'string',
		mapping : 'maIntegeraIntegerype'
	},{
		name : 'maIntegerainUnit',
		type : 'string',
		mapping : 'maIntegerainUnit'
	},{
		name : 'otherMaIntegerainUnit',
		type : 'string',
		mapping : 'otherMaIntegerainUnit'
	},{
		name : 'renewalOverTime',
		mapping : 'renewalOverTime',
		type : Ext.data.Types.DATE,
		dateFormat : 'Y-m-d\\TH:i:s'
	},{
		name : 'maIntegerenanceType',
		type : 'string',
		mapping : 'maIntegerenanceType'
	},{
		name : 'purchasedMaIntegerenanceTime',
		type : 'string',
		mapping : 'purchasedMaIntegerenanceTime'
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
		mapping : 'projectGuaranteeOverTime',
		type : Ext.data.Types.DATE,
		dateFormat : 'Y-m-d\\TH:i:s'
	},{
		name : 'isAuthorizationManagement',
		type : 'string',
		mapping : 'isAuthorizationManagement'
	},{
		name : 'authorizationManagementUnit',
		type : 'string',
		mapping : 'authorizationManagementUnit'
	},{
		name : 'note',
		type : 'string',
		mapping : 'note'
	}]);
	// Reader以提供的Ext.data.Record 构造器中的映射为基础，从JSON响应创建一个Ext.data.Record 对象数组
	var reader = new Ext.data.JsonReader({
		totalProperty : "total",
		root : "generators"
	}, generator);
	var store = new Ext.data.Store({// 提供数据输入
		proxy : proxy,
		reader : reader,
		remoteSort : true
	});
	
	var grid = new Ext.grid.GridPanel({
		region : 'center',
		id : "generatorGrid",
		border : false,
		cm : cm,// 列定义的model(必需)
		sm :sm,
		store : store,
		autoScroll : true,// 滚动条
		viewConfig : {
			sortAscText : '升序',
			sortDescText : '降序',
			columnsText : '可选列',
			forceFit : false,// Ture表示自动扩展或缩小列的宽度
			scrollOffset : -1
		},
		buttonAlign : "center",// 按钮居中
		tbar : [{
			id:"helpBtn",
			icon:"imgs/bangzhu.png",
			cls:"x-btn-text-icon",
			handler : function() {
				help("pages/help/log.jsp");
			}
		},{
			xtype : 'tbseparator'
		},{
			id : 'seeGenerator',
			text : '查看资源日志',
			icon : "imgs/fiber/link_break.png",
			cls : "x-btn-text-icon"
		},{
			xtype : 'tbseparator'
		},{
			id : 'refreshBtn',
			text : '全部刷新',
			icon : "imgs/all_refresh.png",
			cls : "x-btn-text-icon"
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
		})
	});
	
	var loadMarsk = new Ext.LoadMask(document.body, {
		msg : '数据加载中，请稍候......',
		disabled : false,
		store : store
	});
	store.addListener('beforeload', function() {
		loadMarsk.show();
	});
	store.load({
		params : {
			start : 0,
			limit : limit
		}
	});

	// viewport 布局
	new Ext.Viewport({
		layout : 'border',
		items : [grid]
	});
	
	var refreshBtn = Ext.getCmp("refreshBtn");
	refreshBtn.on('click', function() {
		Ext.getCmp("generatorGrid").getStore().load({
			params : {
   					start : 0,
					limit : limit
   				}
		//Ext.getCmp("proGrid").getStore（）.reload();
		});
});
	
	var seeGenerator=Ext.getCmp("seeGenerator");
	seeGenerator.on('click',function(){
		var selModel = grid.getSelectionModel();// 返回grid的SelectionModel
		if (selModel.hasSelection()) {
			var records = selModel.getSelections();
			var ids = [];
			for (var i = 0; i < records.length; i++) {
				ids.push(records[i].get("generatorId"));// 获得选择的编号
			}
			if (ids.length > 1) {
				Ext.Msg.show({
					title : '提示',
					width : 300,
					msg : '<img src="imgs/tip_warn.png" align="center" hspace="30"/>'
							+ '请您选择一条数据进行操作!',
					buttons : {
						ok : "确定"
					}
				});
			} else if (ids.length == 1) {
				var generatorGrid=Ext.getCmp("generatorGrid");
				if(!generatorGrid.seeGeneratorWindow){
					generatorGrid.seeGeneratorWindow=new com.increase.cen.log.logWindow({
					id : "seeGeneratorWindow",
					height : 400,
					width : 1000,
					title : '查看资源日志',
					iconCls : 'i-script-window',
					items : [({
						height : 350,
						xtype : 'aloneGenerator'// 窗口里的表单
					})]
				});
				}
				var store = Ext.getCmp("aloneGenerator").getStore();
				var loadMarsk = new Ext.LoadMask(document.body, {
					msg : '数据加载中，请稍候......',
					disabled : false,
					store : store
				});
				store.addListener('beforeload', function() {
					loadMarsk.show();
				});

				store.load({
					params : {
						'generator.generatorId' : ids[0]
					}
				});
	
				generatorGrid.seeGeneratorWindow.show();
			 }
			}else{
			Ext.Msg.show({
				title : '提示',
				width : 300,
				msg : '<img src="imgs/tip_warn.png" align="center" hspace="30"/>'
						+ '您未选择数据，请选择!',
				buttons : {
					ok : "确定"
				}
			});
		}
	});
	
});