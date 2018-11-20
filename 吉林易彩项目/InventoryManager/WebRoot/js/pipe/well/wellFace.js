Ext.namespace("com.increase.cen.pipe");
var facesm = new Ext.grid.CheckboxSelectionModel({
	singleSelect : true
});
var cm = new Ext.grid.ColumnModel([facesm,
	{header : '面Id',dataIndex : 'faceId',sortable : true,hidden : true}, 
	{header : '面方向',dataIndex : 'locationNo',sortable : true}, 
	{header : '管孔容量',dataIndex : 'cols',sortable : true}, 
	{header : '坍塌管孔数量',dataIndex : 'disableTubes',sortable : true}
	]);
	// var pageSize = Ext.getDom("domainCode").value;
	// 从远程读取数据
	var proxy = new Ext.data.HttpProxy({
		url : "pipe!getFace.action"
	});
	// Record 定义记录结果
	var face = Ext.data.Record.create([
    {name : 'faceId',type : 'string',mapping : 'faceId'
	},{name : 'locationNo',mapping : 'locationNo',type : 'string'
	},{name : 'cols',type : 'string',mapping : 'cols'
	},{name : 'disableTubes',type : 'string',mapping : 'disableTubes'
	}]);
	
	var reader = new Ext.data.JsonReader({
		totalProperty : "total",
		root : "faces"
	}, face);
	var store = new Ext.data.Store({// 提供数据输入
		proxy : proxy,
		reader : reader,
		remoteSort : true
	});
	
	com.increase.cen.pipe.seewellFace = Ext.extend(Ext.grid.GridPanel, {
		region : 'center',
		id : "seewellface",
		border : false,
		cm : cm,// 列定义的model(必需)
		sm : facesm,
		store : store,
		autoScroll : true,// 滚动条
		tbar:[{
			id : 'facaTubeBtn',
			text : '井面管孔',
			icon : "imgs/fiber/link_break.png",
			cls : "x-btn-text-icon",
			handler : function() {
				var selModel = Ext.getCmp("seewellface").getSelectionModel();
				var grid = Ext.getCmp("seewellface");
				if (selModel.hasSelection()) {
					var records = selModel.getSelections();// 返回所有选中的记录
					if(!grid.faceTubeWindow){
						grid.faceTubeWindow= new com.increase.cen.pipe.pipeWindow({
							id:"faceTubeWindow",
							iconCls : 'i-script-window',
							title:"管孔",
							width:600,
							items:[({
								height:400,
								xtype:'facetube'
							})]
						
						});
					}
					var facetube=Ext.getCmp("facetube");
					var store = facetube.getStore();
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
							'fatherTube.faceId' : records[0].json.locationNo,
							'fatherTube.wellId' : records[0].json.wellId,
							'fatherTube.isFather' :'1,3'
						}
					});
					grid.faceTubeWindow.show();
					
					facetube.on('rowdblclick',function(g, r, e){
						e.stopEvent();
						var selModel = facetube.getSelectionModel();
						if (selModel.hasSelection()) {
							var records = selModel.getSelections();// 返回所有选中的记录
							if(!facetube.TubesonWindow){
								facetube.TubesonWindow= new com.increase.cen.pipe.pipeWindow({
									id:"TubesonWindow",
									iconCls : 'i-script-window',
									title:"子孔",
									width:600,
									items:[({
										height:400,
										xtype:'tubeson'
									})]
								
								});
							}
							var store = Ext.getCmp("tubeson").getStore();
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
									'fatherTube.fatherPoreId' : records[0].json.tubeId,
									'fatherTube.faceId' : records[0].json.faceId,
									'fatherTube.wellId' : records[0].json.wellId,
									'fatherTube.isFather' :'2'
								}
							});
							if(records[0].json.subTubeAmount==null||records[0].json.subTubeAmount==""||records[0].json.subTubeAmount=='0'){
								Ext.Msg.show({
									title : '提示',
									width : 300,
									msg : '<img src="imgs/tip_warn.png" align="center" hspace="30"/>'
											+ '此孔为单孔，无子控!',
									buttons : {
										ok : "确定"
									}
								});
							}else{
								facetube.TubesonWindow.show();
							}
						}
					});
				}
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
			com.increase.cen.pipe.seewellFace.superclass.initComponent.call(this);
		}
	});
Ext.reg("seewellface", com.increase.cen.pipe.seewellFace);