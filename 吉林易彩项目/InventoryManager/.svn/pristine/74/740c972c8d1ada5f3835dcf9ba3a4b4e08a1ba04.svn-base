Ext.onReady(function() {
	Ext.useShims = false;
	
	var typeStore = new Ext.data.SimpleStore({
		fields : ['value', 'text'],
		data : [['1', '地区'], ['2', '人员']]
	});
	 var combo = new Ext.form.ComboBox({ 
        store:typeStore //声明一个数组，提供数据 
        }); 
	// 列模型
	var cm = new Ext.grid.ColumnModel([ 
	{
		header : '区域',
		dataIndex : 'areaname',
		width : 90
	},{
		header : '施工工单',
		dataIndex : 'type1',
		width : 90
	}, {
		header : '设备配置工单',
		dataIndex : 'type2',
		width : 100
	}, {
		header : '设备巡检工单',
		dataIndex : 'type3',
		width : 100
	}, {
		header : '设备升级工单',
		dataIndex : 'type4',
		width : 100
	}, {
		header : '端子检查单',
		dataIndex : 'type5',
		width : 100
	}, {
		header : '端口操作工单',
		dataIndex : 'type6',
		width : 100
	}, {
		header : '分光器施工工单',
		dataIndex : 'type7',
		width : 100
	}, {
		header : '在途',
		dataIndex : 's12',
		width : 70
	}, {
		header : '回单',
		dataIndex : 's4',
		width : 70
	}, {
		header : '退单',
		dataIndex : 's3',
		width : 70
	},{
		header : '已竣工',
		dataIndex : 's5',
		width : 70
	},{
		header : '销单',
		dataIndex : 's6',
		width : 70
	},{
		header : '超时',
		dataIndex : 'overtime',
		width : 70
	}]);
	// var pageSize = Ext.getDom("domainCode").value;
	// 从远程读取数据
	var proxy = new Ext.data.HttpProxy({
		url : "statistics!statisticsReasource.action"
	});
	// Record 定义记录结果
	var equt = Ext.data.Record.create([{
		name : 'areaname',
		type : 'string',
		mapping : 'areaname'
	}, {
		name : 'type1',
		type : 'Integer',
		mapping : 'type1'
	}, {
		name : 'type2',
		type : 'Integer',
		mapping : 'type2'
	}, {
		name : 'type3',
		type : 'Integer',
		mapping : 'type3'
	}, {
		name : 'type4',
		type : 'Integer',
		mapping : 'type4'
	}, {
		name : 'type5',
		type : 'Integer',
		mapping : 'type5'
	}, {
		name : 'type6',
		type : 'Integer',
		mapping : 'type6'
	}, {
		name : 'type7',
		type : 'Integer',
		mapping : 'type7'
	}, {
		name : 's12',
		type : 'Integer',
		mapping : 's12'
	}, {
		name : 's4',
		type : 'Integer',
		mapping : 's4'
	}, {
		name : 's3',
		type : 'Integer',
		mapping : 's3'
	},{
		name : 's5',
		type : 'Integer',
		mapping : 's5'
	}, {
		name : 's6',
		type : 'Integer',
		mapping : 's6'
	},  {
		name : 'overtime',
		type : 'Integer',
		mapping : 'overtime'
	}]);
	// Reader以提供的Ext.data.Record 构造器中的映射为基础，从JSON响应创建一个Ext.data.Record 对象数组
	var reader = new Ext.data.JsonReader({
		totalProperty : "total",
		root : "orderlist"
	}, equt);
	var store = new Ext.data.Store({// 提供数据输入
		proxy : proxy,
		reader : reader,
		remoteSort : true
	});
	// 获取每页显示的记录数
	var pageSize = parseInt(Ext.getDom("pageSize").value);
	var limit = parseInt(Ext.getDom("limit").value);
	
	// 表格
	var grid = new Ext.grid.GridPanel({
		title : "当前位置：" + " " + "工单统计",
		region : 'center',
		id : "grid",
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
		tbar :[  	{
						xtype:'label',
						text:'统计维度：'
					},{
						xtype : 'combo',
						id:'type',
						cls :'order-sel-textfield-width',
						store : typeStore,
						value:'1',
						displayField : 'text',
						valueField : 'value',
						mode : 'local',
						triggerAction : "all",// 下拉框获得了焦点或者被点击了
						editable : false,// 设置为false以阻止用户直接向该输入项输入文本
						hiddenName : 'otype',
						listeners:{
                			'select': function(){
                       		 if(Ext.getCmp('type').getValue() == '1')
                        	{
                       			location.href="pages/statistics/orders.jsp";
//                            	parent.Ext.getDom("orderCenter").update("<iframe id='domainTreeIframe' scrolling='yes' src='pages/statistics/orders.jsp'  width='100%' height='100%' frameborder='no' border='0' marginwidth='0' marginheight='0' align='center'></iframe>");
   						
                        	}
	                        else 
	                        {
	                        	location.href="pages/statistics/ordersbyuser.jsp";
//	                            parent.Ext.getDom("orderCenter").update("<iframe id='domainTreeIframe' scrolling='yes' src='pages/statistics/ordersbyuser.jsp'  width='100%' height='100%' frameborder='no' border='0' marginwidth='0' marginheight='0' align='center'></iframe>");
	                        }
                   		 }
               		  }	
					},'->',	
					{
						xtype:'label',
						text:'地区：'
					},{
						xtype : "treecombo",
						id : "ename_sel",
						width:125,
						listWidth:200,
						anchor : "100%",
						hiddenName : "eid", 
						url : "statistics!loadEqutInspecTree.action",
						setHiddenValue : function(node, dispText) {	
								this.setValue(node.attributes.domainCode);
								Ext.form.ComboBox.superclass.setValue.call(this, node.text);
								this.hiddenValue = node.attributes.domainCode;
						}
					},{
						id : 'seekBtn',
						text : '搜索',
						icon : "imgs/queryBtn.png",
						cls : "x-btn-text-icon"
					},{
						id:'resetBtn',
						text : '重置',
						icon : "imgs/queryBtn.png",
						cls : "x-btn-text-icon"
					}
					
				],
		
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
	// var view = Ext.getCmp("view")
	var seekBtn = Ext.getCmp("seekBtn");
	seekBtn.on('click', function() {
		var state = Ext.getCmp("state").getValue();
		var code = Ext.getCmp("ename_sel").getValue();
		grid.getStore().load({
			params : {
				start : 0,
				limit : limit,
				'code':code,
				'time':state
			}
		});
		grid.getView().refresh();		
		/*Ext.getCmp("state").setValue("");
		Ext.getCmp("ename_sel").setValue("");
		Ext.getCmp("ename_sel").hiddenValue="";*/
	});
	
	var resetBtn=Ext.getCmp("resetBtn");
	resetBtn.on('click', function() {
		Ext.getCmp("state").setValue("");
		Ext.getCmp("ename_sel").setValue("");
		Ext.getCmp("ename_sel").hiddenValue="";
	});
});
