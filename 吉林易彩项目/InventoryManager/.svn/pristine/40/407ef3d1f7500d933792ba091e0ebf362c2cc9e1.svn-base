Ext.namespace("com.increase.cen.document");
var pageSize = 12;
var limit = 12;

 com.increase.cen.document.DocPointGrid = Ext.extend(Ext.Panel,{
	
	initComponent:function(config){
		// 列模型
			var cm = new Ext.grid.ColumnModel([
				{header : 'did',dataIndex : 'did',hidden:true}, 
				{header : '端子ID',dataIndex : 'pid',width : 60}, 
				{header : '端子编码',dataIndex : 'pcode',width : 60}, 
				{header : '端子类型',dataIndex : 'ptype',width : 60,renderer : function(v) {return getPointTypeStr(v);}}, 
				{header : '端子状态',dataIndex : 'pstat',width : 60,renderer : function(v) {return getPstatStr(v);}}, 
				{header : '端子方向',dataIndex : 'direction',width : 70,renderer : function(v) {return getPdirectionStr(v);}}, 
				{header : '业务类型',dataIndex : 'servtype',width : 80},
				{header : '业务编号',dataIndex : 'servno',width : 80},
				{header : '承载业务',dataIndex : 'pserv',width : 80},
				{header : '光纤名称',dataIndex : 'fibname',width : 80},
				{header : '光路编码',dataIndex : 'ofpcode',width : 80},
				{header : '光路名称',dataIndex : 'ofpname',width : 80},
				{header : '对端设备编码',dataIndex : 'oppo_ecode',width : 80},
				{header : '对端设备名称',dataIndex : 'oppo_ename',width : 80},
				{header : '对端端子编码',dataIndex : 'oppo_pcode',width : 80}
			]);
			// Record 定义记录结果
			var point = Ext.data.Record.create([
				{name : 'did',type : 'string',mapping : 'did'},
				{name : 'pid',type : 'string',mapping : 'pid'},
				{name : 'pcode',type : 'string',mapping : 'pcode'}, 
				{name : 'ptype',type : 'string',mapping : 'ptype'}, 
				{name : 'pstat',type : 'string',mapping : 'pstat'}, 
				{name : 'direction',type : 'string',mapping : 'direction'}, 
				{name : 'servtype',type : 'string',mapping : 'servtype'}, 
				{name : 'servno',type : 'string',mapping : 'servno'}, 
				{name : 'pserv',type : 'string',mapping : 'pserv'}, 
				{name : 'fibname',type : 'string',mapping : 'fibname'}, 
				{name : 'ofpcode',type : 'string',mapping : 'ofpcode'},
				{name : 'ofpname',type : 'string',mapping : 'ofpname'},
				{name : 'oppo_ecode',type : 'string',mapping : 'oppo_ecode'},
				{name : 'oppo_ename',type : 'string',mapping : 'oppo_ename'},
				{name : 'oppo_pcode',type : 'string',mapping : 'oppo_pcode'}
			]);
			//数据源
		var proxy = new Ext.data.HttpProxy({
			url : "document!loadDocPointGrid.action"
		});
			// Reader以提供的Ext.data.Record 构造器中的映射为基础，从JSON响应创建一个Ext.data.Record 对象数组
		var reader = new Ext.data.JsonReader({
			totalProperty : "total",
			root : "docPoints"
		}, point);
		// 提供数据输入
		this.store = new Ext.data.Store({
			proxy : proxy,
			reader : reader,
			remoteSort : true
		});
		
		this.grid = new Ext.grid.GridPanel({
			id:'docPoint',
			width:1080,
			height:320,
			cm : cm,// 列定义的model(必需)
			store : this.store,
			viewConfig : {
				sortAscText : '升序',
				sortDescText : '降序',
				columnsText : '可选列',
				forceFit : false,// Ture表示自动扩展或缩小列的宽度
				scrollOffset : -1
			},
			tbar : [{
				//xtype:'label',
				//x:200,
				//text:'请输入关键词：'
			},{
				//xtype:'textfield',id:'KeyWord'
			}],
			bbar : new Ext.PagingToolbar({// 分页工具栏
				pageSize:pageSize,
				beforePageText : "当前第",
				store : this.store,
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
		
		})
		
		
		this.items = [this.grid];
		
		//重写initComponent superclass.initComponent.call(this);来调用父类的初始化方法.
		com.increase.cen.document.DocPointGrid.superclass.initComponent.call(this);  
	}
	
});
Ext.reg("docPointGrid",com.increase.cen.document.DocPointGrid );