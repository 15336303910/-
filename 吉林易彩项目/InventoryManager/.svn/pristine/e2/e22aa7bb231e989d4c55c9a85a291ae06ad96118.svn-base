Ext.onReady(function() {
	// 获取每页显示的记录数
	var pageSize = parseInt(Ext.getDom("pageSize").value);
	var limit = parseInt(Ext.getDom("limit").value);
	
	// 列模型
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
	{header : 'ID',dataIndex : 'propertyId',hidden : true}, 
	{header : '资产帐簿 ',dataIndex : 'proper_zczb',sortable : true}, 
	{header : '资产编号',dataIndex : 'proper_zcbh',sortable : true}, 
	{header : '资产标签号',dataIndex : 'proper_zcbqh',sortable : true},
	{header : '盘点结果',dataIndex : 'proper_pdjg',sortable : true},
	{header : '资产名称',dataIndex : 'proper_zcmc',sortable : true},
	{header : '是否拆分',dataIndex : 'proper_sfcf',sortable : true},
	{header : '资产类别',dataIndex : 'proper_zclb',sortable : true},
	{header : '资产类别描述',dataIndex : 'proper_zclbms',sortable : true},
	{header : '制造商',dataIndex : 'proper_zzs',sortable : true},
	{header : '资产型号',dataIndex : 'proper_zcxh',sortable : true},
	{header : '资产数量',dataIndex : 'proper_zcsl',sortable : true},
	{header : '计量单位',dataIndex : 'proper_jldw',sortable : true},
	{header : '资产关键字',dataIndex : 'proper_zcgjz',sortable : true},
	{header : '资产关键字描述',dataIndex : 'proper_zcgjzms',sortable : true},
	{header : '资产创建日期',dataIndex : 'proper_zccjrq',sortable : true},
	{header : '资产启用日期',dataIndex : 'proper_zcqyrq',sortable : true},
	{header : '按比例分摊日期',dataIndex : 'proper_ablftrq',sortable : true},
	{header : '使用年限',dataIndex : 'proper_synx',sortable : true},
	{header : '剩余月数',dataIndex : 'proper_syys',sortable : true},
	{header : '固定资产原值帐户',dataIndex : 'proper_gdzcyzzh',sortable : true},
	{header : '固定资产原值帐户描述',dataIndex : 'proper_gdzcyzzhms',sortable : true},
	{header : '固定资产累计折旧帐户',dataIndex : 'proper_gdzcljzjzh',sortable : true},
	{header : '固定资产累计折旧帐户描述',dataIndex : 'proper_gdzcljzjzhms',sortable : true},
	{header : '折旧费用帐户',dataIndex : 'proper_zjfyzh',sortable : true},
	{header : '折旧费用帐户描述',dataIndex : 'proper_zjfyzhms',sortable : true},
	{header : '资产成本',dataIndex : 'proper_zccb',sortable : true},
	{header : '资产净值',dataIndex : 'proper_zcjz',sortable : true},
	{header : '资产残值',dataIndex : 'proper_zccz',sortable : true},
	{header : '资产本期折旧',dataIndex : 'proper_zcbqzj',sortable : true},
	{header : '资产本年折旧',dataIndex : 'proper_zcbnzj',sortable : true},
	{header : '资产累计折旧',dataIndex : 'proper_zcljzj',sortable : true},
	{header : '资产减值准备',dataIndex : 'proper_zcjzzb',sortable : true},
	{header : '员工编号',dataIndex : 'proper_ygbh',sortable : true},
	{header : '员工姓名',dataIndex : 'proper_ygxm',sortable : true},
	{header : '所属区域',dataIndex : 'proper_ssqy',sortable : true},
	{header : '所属区域描述',dataIndex : 'proper_ssqyms',sortable : true},
	{header : '所在地点',dataIndex : 'proper_szdd',sortable : true},
	{header : '所在地点描述',dataIndex : 'proper_szddms',sortable : true},
	{header : '使用状态',dataIndex : 'proper_syzt',sortable : true},
	{header : '使用状态描述',dataIndex : 'proper_syztms',sortable : true},
	{header : '期初资产卡片编号',dataIndex : 'proper_qczckpbh',sortable : true},
	{header : '产权凭证号',dataIndex : 'proper_cqpzh',sortable : true},
	{header : '设备编号',dataIndex : 'proper_sbbh',sortable : true},
	{header : '专业属性',dataIndex : 'proper_zysx',sortable : true},
	{header : '专业类型',dataIndex : 'proper_zylx',sortable : true},
	{header : '资产来源',dataIndex : 'proper_zcly',sortable : true},
	{header : '资产归属',dataIndex : 'proper_zcgs',sortable : true},
	{header : '扩容改造项目',dataIndex : 'proper_krgzxm',sortable : true},
	{header : '扩容改造项目描述',dataIndex : 'proper_krgzxmms',sortable : true},
	{header : '折旧状态',dataIndex : 'proper_zjzt',sortable : true},
	{header : '评估净值',dataIndex : 'proper_pgjz',sortable : true},
	{header : '评估后尚可使用月份',dataIndex : 'proper_pghsksyyf',sortable : true},
	{header : '附加信息一',dataIndex : 'proper_fjxx1',sortable : true},
	{header : '附加信息二',dataIndex : 'proper_fjxx2',sortable : true},
	{header : '附加信息三',dataIndex : 'proper_fjxx3',sortable : true},
	{header : '财税差异类型',dataIndex : 'proper_cscylx',sortable : true},
	{header : '附属设备及附件',dataIndex : 'proper_fssbjfj',sortable : true},
	{header : '辅助数量',dataIndex : 'proper_fzsl',sortable : true},
	{header : '辅助计量单位',dataIndex : 'proper_fzjldw',sortable : true},
	{header : '项目编号',dataIndex : 'proper_xmbh',sortable : true},
	{header : '项目编号描述',dataIndex : 'proper_xmbhms',sortable : true},
	{header : '期初工程项目编号',dataIndex : 'proper_qcgcxmbh',sortable : true},
	{header : '资产专业管理部门',dataIndex : 'proper_zczyglbm',sortable : true},
	{header : '创建时间',dataIndex : 'creationDate',sortable : true,renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')},
	{header : '最后更新时间',dataIndex : 'lastUpdateDate',sortable : true,renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')}
	]);
	// 从远程读取数据
	var proxy = new Ext.data.HttpProxy({
		url : "property!getPropertyInfo.action"
	});
	// Record 定义记录结果
	var property= Ext.data.Record.create([
	{name : 'propertyId',type : 'string',mapping : 'propertyId'
	},{
		name : 'proper_zczb',
		type : 'string',
		mapping : 'proper_zczb'
	},{
		name : 'proper_zcbh',
		type : 'string',
		mapping : 'proper_zcbh'
	},{
		name : 'proper_zcbqh',
		type : 'string',
		mapping : 'proper_zcbqh'
	},{
		name : 'proper_pdjg',
		type : 'string',
		mapping : 'proper_pdjg'
	},{
		name : 'proper_zcmc',
		type : 'string',
		mapping : 'proper_zcmc'
	},{
		name : 'proper_sfcf',
		type : 'string',
		mapping : 'proper_sfcf'
	},{
		name : 'proper_zclb',
		type : 'string',
		mapping : 'proper_zclb'
	},{
		name : 'proper_zclbms',
		type : 'string',
		mapping : 'proper_zclbms'
	},{
		name : 'proper_zzs',
		type : 'string',
		mapping : 'proper_zzs'
	},{
		name : 'proper_zcxh',
		type : 'string',
		mapping : 'proper_zcxh'
	},{
		name : 'proper_zcsl',
		type : 'string',
		mapping : 'proper_zcsl'
	},{
		name : 'proper_jldw',
		type : 'string',
		mapping : 'proper_jldw'
	},{
		name : 'proper_zcgjz',
		type : 'string',
		mapping : 'proper_zcgjz'
	},{
		name : 'proper_zcgjzms',
		type : 'string',
		mapping : 'proper_zcgjzms'
	},{
		name : 'proper_zccjrq',
		type : 'string',
		mapping : 'proper_zccjrq'
	},{
		name : 'proper_zcqyrq',
		type : 'string',
		mapping : 'proper_zcqyrq'
	},{
		name : 'proper_ablftrq',
		type : 'string',
		mapping : 'proper_ablftrq'
	},{
		name : 'proper_synx',
		type : 'string',
		mapping : 'proper_synx'
	},{
		name : 'proper_syys',
		type : 'string',
		mapping : 'proper_syys'
	},{
		name : 'proper_gdzcyzzh',
		type : 'string',
		mapping : 'proper_gdzcyzzh'
	},{
		name : 'proper_gdzcyzzhms',
		type : 'string',
		mapping : 'proper_gdzcyzzhms'
	},{
		name : 'proper_gdzcljzjzh',
		type : 'string',
		mapping : 'proper_gdzcljzjzh'
	},{
		name : 'proper_gdzcljzjzhms',
		type : 'string',
		mapping : 'proper_gdzcljzjzhms'
	},{
		name : 'proper_zjfyzh',
		type : 'string',
		mapping : 'proper_zjfyzh'
	},{
		name : 'proper_zjfyzhms',
		type : 'string',
		mapping : 'proper_zjfyzhms'
	},{
		name : 'proper_zccb',
		type : 'string',
		mapping : 'proper_zccb'
	},{
		name : 'proper_zcjz',
		type : 'string',
		mapping : 'proper_zcjz'
	},{
		name : 'proper_zccz',
		type : 'string',
		mapping : 'proper_zccz'
	},{
		name : 'proper_zcbqzj',
		type : 'string',
		mapping : 'proper_zcbqzj'
	},{
		name : 'proper_zcbnzj',
		type : 'string',
		mapping : 'proper_zcbnzj'
	},{
		name : 'proper_zcljzj',
		type : 'string',
		mapping : 'proper_zcljzj'
	},{
		name : 'proper_zcjzzb',
		type : 'string',
		mapping : 'proper_zcjzzb'
	},{
		name : 'proper_ygbh',
		type : 'string',
		mapping : 'proper_ygbh'
	},{
		name : 'proper_ygxm',
		type : 'string',
		mapping : 'proper_ygxm'
	},{
		name : 'proper_ssqy',
		type : 'string',
		mapping : 'proper_ssqy'
	},{
		name : 'proper_ssqyms',
		type : 'string',
		mapping : 'proper_ssqyms'
	},{
		name : 'proper_szdd',
		type : 'string',
		mapping : 'proper_szdd'
	},{
		name : 'proper_szddms',
		type : 'string',
		mapping : 'proper_szddms'
	},{
		name : 'proper_syzt',
		type : 'string',
		mapping : 'proper_syzt'
	},{
		name : 'proper_syztms',
		type : 'string',
		mapping : 'proper_syztms'
	},{
		name : 'proper_qczckpbh',
		type : 'string',
		mapping : 'proper_qczckpbh'
	},{
		name : 'proper_cqpzh',
		type : 'string',
		mapping : 'proper_cqpzh'
	},{
		name : 'proper_sbbh',
		type : 'string',
		mapping : 'proper_sbbh'
	},{
		name : 'proper_zysx',
		type : 'string',
		mapping : 'proper_zysx'
	},{
		name : 'proper_zylx',
		type : 'string',
		mapping : 'proper_zylx'
	},{
		name : 'proper_zcly',
		type : 'string',
		mapping : 'proper_zcly'
	},{
		name : 'proper_zcgs',
		type : 'string',
		mapping : 'proper_zcgs'
	},{
		name : 'proper_krgzxm',
		type : 'string',
		mapping : 'proper_krgzxm'
	},{
		name : 'proper_krgzxmms',
		type : 'string',
		mapping : 'proper_krgzxmms'
	},{
		name : 'proper_zjzt',
		type : 'string',
		mapping : 'proper_zjzt'
	},{
		name : 'proper_pgjz',
		type : 'string',
		mapping : 'proper_pgjz'
	},{
		name : 'proper_pghsksyyf',
		type : 'string',
		mapping : 'proper_pghsksyyf'
	},{
		name : 'proper_fjxx1',
		type : 'string',
		mapping : 'proper_fjxx1'
	},{
		name : 'proper_fjxx2',
		type : 'string',
		mapping : 'proper_fjxx2'
	},{
		name : 'proper_fjxx3',
		type : 'string',
		mapping : 'proper_fjxx3'
	},{
		name : 'proper_cscylx',
		type : 'string',
		mapping : 'proper_cscylx'
	},{
		name : 'proper_fssbjfj',
		type : 'string',
		mapping : 'proper_fssbjfj'
	},{
		name : 'proper_fzsl',
		type : 'string',
		mapping : 'proper_fzsl'
	},{
		name : 'proper_fzjldw',
		type : 'string',
		mapping : 'proper_fzjldw'
	},{
		name : 'proper_xmbh',
		type : 'string',
		mapping : 'proper_xmbh'
	},{
		name : 'proper_xmbhms',
		type : 'string',
		mapping : 'proper_xmbhms'
	},{
		name : 'proper_qcgcxmbh',
		type : 'string',
		mapping : 'proper_qcgcxmbh'
	},{
		name : 'proper_zczyglbm',
		type : 'string',
		mapping : 'proper_zczyglbm'
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
	}]);
	// Reader以提供的Ext.data.Record 构造器中的映射为基础，从JSON响应创建一个Ext.data.Record 对象数组
	var reader = new Ext.data.JsonReader({
		totalProperty : "total",
		root : "propertys"
	}, property);
	var store = new Ext.data.Store({// 提供数据输入
		proxy : proxy,
		reader : reader,
		remoteSort : true
	});
	
	var propertyGrid = new Ext.grid.GridPanel({
		title : "当前位置：资产管理",
		region : 'center',
		id : "propertyGrid",
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
		buttonAlign : "center",// 按钮居中
		tbar : [{
			id:"helpBtn",
			icon:"imgs/bangzhu.png",
			cls:"x-btn-text-icon",
			handler : function() {
				help("pages/help/property.jsp");
			}
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
		items : [propertyGrid]
	});
	
	var refreshBtn = Ext.getCmp("refreshBtn");
	refreshBtn.on('click', function() {
		Ext.getCmp("propertyGrid").getStore().load({
			params : {
   					start : 0,
					limit : limit
   				}
		});
});
	
	propertyGrid.on('rowdblclick', function(g, r, e) {
	e.stopEvent();
	var propertyGrid=Ext.getCmp("propertyGrid");
	var selModel = propertyGrid.getSelectionModel();
	if (selModel.hasSelection()) {
		var records = selModel.getSelections();
		propertyGrid.seepropertyWindow = new com.increase.cen.property.PropertyWindow({
				id : "seePropertyWindow",
				title : '查看资产信息',
				iconCls : 'i-script-window',
				items : [new com.increase.cen.property.seeProperty({
					id : "seeProperty",
					width:700,
					windowId: "seePropertyWindow"
				})]
			});
		propertyGrid.seepropertyWindow.show();
		Ext.getCmp("proper_zczb").setValue(records[0].json.proper_zczb);
		Ext.getCmp("proper_zcbh").setValue(records[0].json.proper_zcbh);
		Ext.getCmp("proper_zcbqh").setValue(records[0].json.proper_zcbqh);
		Ext.getCmp("proper_pdjg").setValue(records[0].json.proper_pdjg);
		Ext.getCmp("proper_zcmc").setValue(records[0].json.proper_zcmc);
		Ext.getCmp("proper_sfcf").setValue(records[0].json.proper_sfcf);
		Ext.getCmp("proper_zclb").setValue(records[0].json.proper_zclb);
		Ext.getCmp("proper_zclbms").setValue(records[0].json.proper_zclbms);
		Ext.getCmp("proper_zzs").setValue(records[0].json.proper_zzs);
		Ext.getCmp("proper_zcxh").setValue(records[0].json.proper_zcxh);
		Ext.getCmp("proper_zcsl").setValue(records[0].json.proper_zcsl);
		Ext.getCmp("proper_jldw").setValue(records[0].json.proper_jldw);
		Ext.getCmp("proper_zcgjz").setValue(records[0].json.proper_zcgjz);
		Ext.getCmp("proper_zcgjzms").setValue(records[0].json.proper_zcgjzms);
		Ext.getCmp("proper_zccjrq").setValue(records[0].json.proper_zccjrq);
		Ext.getCmp("proper_zcqyrq").setValue(records[0].json.proper_zcqyrq);
		Ext.getCmp("proper_ablftrq").setValue(records[0].json.proper_ablftrq);
		Ext.getCmp("proper_synx").setValue(records[0].json.proper_synx);
		Ext.getCmp("proper_syys").setValue(records[0].json.proper_syys);
		Ext.getCmp("proper_gdzcyzzh").setValue(records[0].json.proper_gdzcyzzh);
		Ext.getCmp("proper_gdzcyzzhms").setValue(records[0].json.proper_gdzcyzzhms);
		Ext.getCmp("proper_gdzcljzjzh").setValue(records[0].json.proper_gdzcljzjzh);
		Ext.getCmp("proper_gdzcljzjzhms").setValue(records[0].json.proper_gdzcljzjzhms);
		Ext.getCmp("proper_zjfyzh").setValue(records[0].json.proper_zjfyzh);
		Ext.getCmp("proper_zjfyzhms").setValue(records[0].json.proper_zjfyzhms);
		Ext.getCmp("proper_zccb").setValue(records[0].json.proper_zccb);
		Ext.getCmp("proper_zcjz").setValue(records[0].json.proper_zcjz);
		Ext.getCmp("proper_zccz").setValue(records[0].json.proper_zccz);
		Ext.getCmp("proper_zcbqzj").setValue(records[0].json.proper_zcbqzj);
		Ext.getCmp("proper_zcbnzj").setValue(records[0].json.proper_zcbnzj);
		Ext.getCmp("proper_zcljzj").setValue(records[0].json.proper_zcljzj);
		Ext.getCmp("proper_zcjzzb").setValue(records[0].json.proper_zcjzzb);
		Ext.getCmp("proper_ygbh").setValue(records[0].json.proper_ygbh);
		Ext.getCmp("proper_ygxm").setValue(records[0].json.proper_ygxm);
		Ext.getCmp("proper_ssqy").setValue(records[0].json.proper_ssqy);
		Ext.getCmp("proper_ssqyms").setValue(records[0].json.proper_ssqyms);
		Ext.getCmp("proper_szdd").setValue(records[0].json.proper_szdd);
		Ext.getCmp("proper_szddms").setValue(records[0].json.proper_szddms);
		Ext.getCmp("proper_syzt").setValue(records[0].json.proper_syzt);
		Ext.getCmp("proper_syztms").setValue(records[0].json.proper_syztms);
		Ext.getCmp("proper_qczckpbh").setValue(records[0].json.proper_qczckpbh);
		Ext.getCmp("proper_cqpzh").setValue(records[0].json.proper_cqpzh);
		Ext.getCmp("proper_sbbh").setValue(records[0].json.proper_sbbh);
		Ext.getCmp("proper_zysx").setValue(records[0].json.proper_zysx);
		Ext.getCmp("proper_zylx").setValue(records[0].json.proper_zylx);
		Ext.getCmp("proper_zcly").setValue(records[0].json.proper_zcly);
		Ext.getCmp("proper_zcgs").setValue(records[0].json.proper_zcgs);
		Ext.getCmp("proper_krgzxm").setValue(records[0].json.proper_krgzxm);
		Ext.getCmp("proper_krgzxmms").setValue(records[0].json.proper_krgzxmms);
		Ext.getCmp("proper_zjzt").setValue(records[0].json.proper_zjzt);
		Ext.getCmp("proper_pgjz").setValue(records[0].json.proper_pgjz);
		Ext.getCmp("proper_pghsksyyf").setValue(records[0].json.proper_pghsksyyf);
		Ext.getCmp("proper_fjxx1").setValue(records[0].json.proper_fjxx1);
		Ext.getCmp("proper_fjxx2").setValue(records[0].json.proper_fjxx2);
		Ext.getCmp("proper_fjxx3").setValue(records[0].json.proper_fjxx3);
		Ext.getCmp("proper_cscylx").setValue(records[0].json.proper_cscylx);
		Ext.getCmp("proper_fssbjfj").setValue(records[0].json.proper_fssbjfj);
		Ext.getCmp("proper_fzsl").setValue(records[0].json.proper_fzsl);
		Ext.getCmp("proper_fzjldw").setValue(records[0].json.proper_fzjldw);
		Ext.getCmp("proper_xmbh").setValue(records[0].json.proper_xmbh);
		Ext.getCmp("proper_xmbhms").setValue(records[0].json.proper_xmbhms);
		Ext.getCmp("proper_qcgcxmbh").setValue(records[0].json.proper_qcgcxmbh);
		Ext.getCmp("proper_zczyglbm").setValue(records[0].json.proper_zczyglbm);
		var a=time(records[0].json.creationDate);
		Ext.getCmp("creationDate").setValue(a);
		var b=time(records[0].json.lastUpdateDate);
		Ext.getCmp("lastUpdateDate").setValue(b);
	}
});
});

function time(v){
	v=v.replace("T"," ");
	return v;
}