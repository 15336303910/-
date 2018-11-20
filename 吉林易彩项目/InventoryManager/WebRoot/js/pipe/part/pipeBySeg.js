Ext.namespace("com.increase.cen.poleline");
var pageSize = 10;
var limit = 10;
	var cm = new Ext.grid.ColumnModel([{
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
	}, 
	{header : '管道ID',dataIndex : 'pipeId',hidden : true}, 
	{header : '管道名称',dataIndex : 'pipeName',width : 150,sortable : true}, 
	{header : '管道别名',dataIndex : 'alias',width : 90,sortable : true}, 
	{header : '管道级别',dataIndex : 'pipeLevel',width : 60,sortable : true,
		renderer:function(v){
			if(v=='1'){
				return "一干";
			}else if(v=='2'){
				return "二干";
			}else if(v=='3'){
				return "本地";
			}
		}
	},
	{header : '起始设施名称',dataIndex : 'startDeviceName',width : 130,sortable : true},
	{header : '终止设施名称',dataIndex : 'endDeviceName',width : 130,sortable : true},
	{header : '最后更新时间',dataIndex : 'lastUpdateDate',width : 130,sortable : true,
		renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')
	},
	{header : '操作',dataIndex : 'pipeId',width : 100,
		renderer : function(value, metadata, record, rowIndex, colIndex, store) {
			return '<input type="button" value="选择此管道" onclick="selectPipeBySeg('+rowIndex+')" />';				
		}
	}]);
	// 从远程读取数据
	var proxy = new Ext.data.HttpProxy({
		url : "pipe!list.action"

	});
	// Record 定义记录结果
	var pipe = Ext.data.Record.create([
		{name : 'pipeId',type : 'string',mapping : 'pipeId',hidden : true
	}, {
		name : 'pipeName',
		type : 'string',
		mapping : 'pipeName'
	},{
		name : 'alias',
		type : 'string',
		mapping : 'alias'
	},{
		name : 'pipeLevel',
		type : 'string',
		mapping : 'pipeLevel'
	},{
		name : 'startDeviceName',
		type : 'string',
		mapping : 'startDeviceName'
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
	root : "pipes"
}, pipe);
// 提供数据输入
var store = new Ext.data.Store({
	proxy : proxy,
	reader : reader,
	remoteSort : true
});
this.levelStore=new Ext.data.SimpleStore({
    		fields : ['value', 'text'],
    		data : [['', ''],['1', '一干'], ['2', '二干'], ['3', '本地']]
    	})
com.increase.cen.poleline.PipeBySeg = Ext.extend(Ext.grid.GridPanel, {
	id:'pipeBySeg',
	cm : cm,// 列定义的model(必需)
	store : store,
	viewConfig : {
		sortAscText : '升序',
		sortDescText : '降序',
		columnsText : '可选列',
		forceFit : false,// Ture表示自动扩展或缩小列的宽度
		scrollOffset : -1
	},
	tbar : [{
			xtype : 'label',
			text : '管道名称：'
		},{
			xtype : 'textfield',
			id:'mergepipeName',
			cls:'sel-textfield-width'
			
		},{
			xtype : 'tbseparator'
		}, {
			xtype : 'label',
			text : '管道别名：'
		},{
			xtype : 'textfield',
			id:'mergealias',
			cls:'sel-textfield-width'
			
		},{
			xtype : 'tbseparator'
		},{
			xtype : 'label',
			text : '管道级别：'
		},{
			id:'mergepipeLevel',
			cls:'sel-textfield-width',
			xtype : 'combo',
			store : this.levelStore,
			value:'',
			displayField : 'text',
			valueField : 'value',
			mode : 'local',
			triggerAction : "all",// 下拉框获得了焦点或者被点击了
			editable : false,// 设置为false以阻止用户直接向该输入项输入文本
			hiddenName : 'pipeLevel'
			
		},{
		xtype : 'tbseparator'
	},{
		text : '查询',
		icon : "imgs/queryBtn.png",
		cls : "x-btn-text-icon",
		handler:function(){
			var store = Ext.getCmp("pipeBySeg").getStore();
			store.load({
				params : {
					start : 0,
					limit : limit,
					'pipeInfoBean.pipeName':Ext.getCmp("mergepipeName").getValue(),
					'pipeInfoBean.alias':Ext.getCmp("mergealias").getValue(),
					'pipeInfoBean.pipeLevel':Ext.getCmp("mergepipeLevel").getValue()
				}
			});
			store.on('beforeload',function(){
   				store.baseParams = {
   					'pipeInfoBean.pipeName':Ext.getCmp("mergepipeName").getValue(),
					'pipeInfoBean.alias':Ext.getCmp("mergealias").getValue(),
					'pipeInfoBean.pipeLevel':Ext.getCmp("mergepipeLevel").getValue()
   				};
			}); 
			Ext.getCmp("pipeBySeg").getView().refresh();
		}
	}, {
		xtype : 'tbseparator'
	}, {
		text : '全部刷新',
		icon : "imgs/all_refresh.png",
		cls : "x-btn-text-icon",
		handler:function(){
			Ext.getCmp("mergepipeName").setValue("");
			Ext.getCmp("mergealias").setValue("");
			Ext.getCmp("mergepipeLevel").setValue("");
			var store = Ext.getCmp("pipeBySeg").getStore();
			store.load({
				params : {
					start : 0,
					limit : limit
				}
			});
			Ext.getCmp("pipeBySeg").getView().refresh();
		}
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
	}),

	initComponent : function(config) {
		// 重写initComponent superclass.initComponent.call(this);来调用父类的初始化方法.
		com.increase.cen.poleline.PipeBySeg.superclass.initComponent.call(this);
	}
});

Ext.reg("pipeBySeg", com.increase.cen.poleline.PipeBySeg);

function selectPipeBySeg(rowIndex){
	var store = Ext.getCmp("pipeBySeg").getStore();
	var pipe = store.data.items[rowIndex].json;
	Ext.getCmp("mergePipeId").setValue(pipe.pipeId);
	Ext.getCmp("mergePipeName").setValue(pipe.pipeName);
	Ext.getCmp("mergeAlias").setValue(pipe.alias);
	Ext.getCmp("mergePipeLevel").setValue(pipe.pipeLevel);
	Ext.getCmp("mergeLength").setValue(pipe.length);
	Ext.getCmp("mergeStartDeviceId").setValue(pipe.startDeviceId);
	Ext.getCmp("mergeStartDeviceType").setValue(pipe.startDeviceType);
	Ext.getCmp("mergeStartDeviceName").setValue(pipe.startDeviceName);
	Ext.getCmp("mergeEndDeviceId").setValue(pipe.endDeviceId);
	Ext.getCmp("mergeEndDeviceType").setValue(pipe.endDeviceType);
	Ext.getCmp("mergeEndDeviceName").setValue(pipe.endDeviceName);
	Ext.getCmp("mergePurchasedMaintenanceTime").setValue(pipe.purchasedMaintenanceTime);
	Ext.getCmp("mergeMaintenanceModeEnumId").setValue(pipe.maintenanceModeEnumId);
	Ext.getCmp("mergeMaintenanceOrgId").setValue(pipe.maintenanceOrgId);
	Ext.getCmp("mergeThirdPartyMaintenanceOrg").setValue(pipe.thirdPartyMaintenanceOrg);
	Ext.getCmp("mergeMaintenanceTypeEnumId").setValue(pipe.maintenanceTypeEnumId);
	Ext.getCmp("mergeResourceLifePeriodEnumId").setValue(pipe.resourceLifePeriodEnumId);
	Ext.getCmp("mergeProjectName").setValue(pipe.projectName);
	Ext.getCmp("mergeProjectCode").setValue(pipe.projectCode);
	if(pipe.renewalExpirationDate!=null||pipe.renewalExpirationDate!=""){
		var date =new Date(pipe.renewalExpirationDate);
		Ext.getCmp("mergeRenewalExpirationDate").setValue(date);
	}
	if(pipe.projectWarrantyExpireDate!=null||pipe.projectWarrantyExpireDate!=""){
		var date =new Date(pipe.projectWarrantyExpireDate);
		Ext.getCmp("mergeProjectWarrantyExpireDate").setValue(date);
	}
	Ext.getCmp("mergecomboxWithTree").setValue(pipe.areaname);
	Ext.getCmp("pipeBySegWindow").hide();
}