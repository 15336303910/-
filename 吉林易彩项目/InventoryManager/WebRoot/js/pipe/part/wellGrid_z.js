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
	{header : '井ID',dataIndex : 'wellId',hidden : true}, 
	{header : '人/手井名称',dataIndex : 'wellNameSub',width : 220,sortable : true}, 
	{header : '井类型',dataIndex : 'manholeTypeEnumId',width : 50,sortable : true,
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
	{header : '人/手井地址',dataIndex : 'wellAddress',width : 150,sortable : true}, 
	{header : '最后更新时间',dataIndex : 'lastUpdateDate',width : 130,sortable : true,
		renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')
	},
	{header : '操作',dataIndex : 'wellId',width : 100,
		renderer : function(value, metadata, record, rowIndex, colIndex, store) {
			return '<input type="button" value="选择此井" onclick="selectWell_z('+rowIndex+')" />';				
		}
	}]);
	// 从远程读取数据
	var proxy = new Ext.data.HttpProxy({
		url : "pipe!welllist.action"

	});
	// Record 定义记录结果
	var fiber = Ext.data.Record.create([
		{name : 'wellId',type : 'string',mapping : 'wellId',hidden : true
	}, {
		name : 'wellNameSub',
		type : 'string',
		mapping : 'wellNameSub'
	}, {
		name : 'manholeTypeEnumId',
		type : 'string',
		mapping : 'manholeTypeEnumId'
	}, {
		name : 'wellAddress',
		type : 'string',
		mapping : 'wellAddress'
	}, {
		name : 'lastUpdateDate',
		mapping : 'lastUpdateDate',
		type : Ext.data.Types.DATE,
		dateFormat : 'Y-m-d\\TH:i:s'
	}]);
	// Reader以提供的Ext.data.Record 构造器中的映射为基础，从JSON响应创建一个Ext.data.Record 对象数组
	var reader = new Ext.data.JsonReader({
		totalProperty : "total",
		root : "wells"
	}, fiber);
	var store = new Ext.data.Store({// 提供数据输入
		proxy : proxy,
		reader : reader,
		remoteSort : true
	});
this.typeStore=new Ext.data.SimpleStore({
		fields : ['value', 'text'],
    	data : [['', ''],['1', '人孔'], ['2', '手孔'], ['3', '双人孔'],['4', '三人孔'], ['5', '局前井'], ['6', '地下室']]
	})
com.increase.cen.poleline.WellGrid_Z = Ext.extend(Ext.grid.GridPanel, {
	id:'wellGrid_z',
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
		xtype : 'tbseparator'
	}, {
			xtype : 'label',
			text : '人/手井名称：'
		},{
			xtype : 'textfield',
			id:'wellNameSub_z',
			cls:'sel-textfield-width'
			
		},{
			xtype : 'tbseparator'
		}, {
			xtype : 'label',
			text : '人/手井地址：'
		},{
			xtype : 'textfield',
			id:'wellAddress_z',
			cls:'sel-textfield-width'
			
		},{
			xtype : 'tbseparator'
		},{
			xtype : 'label',
			text : '井类型：'
		},{
			id:'manholeTypeEnumId_z',
			cls:'sel-textfield-width',
			xtype : 'combo',
			store : this.typeStore,
			value:'',
			displayField : 'text',
			valueField : 'value',
			mode : 'local',
			triggerAction : "all",// 下拉框获得了焦点或者被点击了
			editable : false,// 设置为false以阻止用户直接向该输入项输入文本
			hiddenName : 'manholeTypeEnumId'
			
		},{
		xtype : 'tbseparator'
	},{
		id : 'queryEqutBtn_z',
		text : '查询',
		icon : "imgs/queryBtn.png",
		cls : "x-btn-text-icon",
		handler:function(){
			var store = Ext.getCmp("wellGrid_z").getStore();
			store.load({
				params : {
					start : 0,
					limit : limit,
					'wellInfoBean.wellNameSub' : Ext.getCmp("wellNameSub_z").getValue(),
					'wellInfoBean.manholeTypeEnumId' : Ext.getCmp("manholeTypeEnumId_z").getValue(),
					'wellInfoBean.wellAddress' : Ext.getCmp("wellAddress_z").getValue()
				}
			});
			store.on('beforeload',function(){
   				store.baseParams = {
   					'wellInfoBean.wellNameSub' : Ext.getCmp("wellNameSub_z").getValue(),
					'wellInfoBean.manholeTypeEnumId' : Ext.getCmp("manholeTypeEnumId_z").getValue(),
					'wellInfoBean.wellAddress' : Ext.getCmp("wellAddress_z").getValue()
   				};
			}); 
			Ext.getCmp("wellGrid_z").getView().refresh();
		}
	}, {
		xtype : 'tbseparator'
	}, {
		id : 'refreshBtn_z',
		text : '全部刷新',
		icon : "imgs/all_refresh.png",
		cls : "x-btn-text-icon",
		handler:function(){
			Ext.getCmp("wellNameSub_z").setValue("");
			Ext.getCmp("manholeTypeEnumId_z").setValue("");
			Ext.getCmp("wellAddress_z").setValue("");
			var store = Ext.getCmp("wellGrid_z").getStore();
			store.load({
				params : {
					start : 0,
					limit : limit
				}
			});
			Ext.getCmp("wellGrid_z").getView().refresh();
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
		com.increase.cen.poleline.WellGrid_Z.superclass.initComponent.call(this);
	}
});

Ext.reg("wellGrid_z", com.increase.cen.poleline.WellGrid_Z);
