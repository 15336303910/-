Ext.namespace("com.increase.cen.poleline");
var pageSize = 10;
var limit = 10;
//创建 复选框
var sm = new Ext.grid.CheckboxSelectionModel({
	checkOnly : true
//	singleSelect : true
});
com.increase.cen.poleline.suspensionsegGrid = Ext.extend(Ext.grid.GridPanel,{
	region : 'center',
	id : "suspensionsegGrid",
	border : false,
	cm : null,// 列定义的model(必需)
	store : null,
	height:300,
	autoScroll : true,// 滚动条
	buttonAlign : "center",// 按钮居中
	suspensionWireId:null,
	initComponent : function(config) {
		this.cm = new Ext.grid.ColumnModel([sm,
			    {header : "序号",width : 50,hideable : false,
			    	renderer : function(value, metadata, record, rowIndex, colIndex, store) {
			          var start = store.lastOptions.params.start;
			            if (isNaN(start)) {
			            start = 0;
			            }
			            return start + rowIndex + 1;
			    	}
			    }, 
			    {header : '吊线段ID',dataIndex : 'suspensionWireSegId',width :50,hidden : true},
			    {header : '吊线段名称',dataIndex : 'suspensionWireSegName',width :150}, 
			    {header : '吊线段编号',dataIndex : 'suspensionWireSegCode',width : 100}, 
			    {header : '所属维护区域',dataIndex : 'region',width : 100}, 
			    {header : '所属吊线名称',dataIndex : 'suspensionWireName',width : 100}, 
			    {header : '起始名称',dataIndex : 'startName',width : 100}, 
			    {header : '终止名称',dataIndex : 'endName',width : 100}, 
			    {header : '材质',dataIndex : 'caizhi',width : 100}, 
			    {header : '规格',dataIndex : 'guige',width : 100}, 
			    {header : '条数',dataIndex : 'tiaoshu',width : 100}, 
			    {header : '长度',dataIndex : 'length',width : 100},
			    {header : '资源生命周期',dataIndex : 'resourceLifePeriodEnumId',width : 100}, 
			    {header : '生命周期时间',dataIndex : 'lifecycleTime',width : 150},
			    {header : '是否授权管理',dataIndex : 'isAuthorizationManagement',width : 150},
			    {header : '授权管理单位',dataIndex : 'authorizationManagementUnit',width : 150},
			    {header : '设计用途',dataIndex : 'designPurposes',width : 150},
			    {header : '备注',dataIndex : 'note',width : 150}
			    ]);
			
			suspensionseg_grid_proxy = new Ext.data.HttpProxy({
				url : "poleline!getSuspensionseglist.action"
			});
			
			suspensionseg_grid_reader = Ext.data.Record.create([
			       {
			        name : 'suspensionWireSegId',
			        type : 'string',
			        mapping : 'suspensionWireSegId'
			       },{
			        name : 'suspensionWireSegName',
			        type : 'string',
			        mapping : 'suspensionWireSegName'
			       },{
			        name : 'suspensionWireSegCode',
			        type : 'string',
			        mapping : 'suspensionWireSegCode'
			       },{
			        name : 'region',
			        type : 'string',
			        mapping : 'region'
			       },{
			        name : 'suspensionWireName',
			        type : 'string',
			        mapping : 'suspensionWireName'
			       },{
			        name : 'startName',
			        type : 'string',
			        mapping : 'startName'
			       },{
			        name : 'endName',
			        type : 'string',
			        mapping : 'endName'
			       },{
			        name : 'caizhi',
			        type : 'string',
			        mapping : 'caizhi'
			       },{
			        name : 'guige',
			        type : 'string',
			        mapping : 'guige'
			       },{
			        name : 'tiaoshu',
			        type : 'string',
			        mapping : 'tiaoshu'
			       },{
			        name : 'length',
			        type : 'string',
			        mapping : 'length'
			       },{
			        name : 'resourceLifePeriodEnumId',
			        type : 'string',
			        mapping : 'resourceLifePeriodEnumId'
			       },{
			    	name : 'lifecycleTime',
			    	type : Ext.data.Types.DATE,
			    	dateFormat : 'Y-m-d\\TH:i:s',
			    	mapping : 'lifecycleTime'
			       },{
			    	name : 'isAuthorizationManagement',
			    	type : 'string',
			    	mapping : 'isAuthorizationManagement'
			       },{
			    	name : 'authorizationManagementUnit',
			    	type : 'string',
			    	mapping : 'authorizationManagementUnit'
			       },{
			    	name : 'designPurposes',
			    	type : 'string',
			    	mapping : 'designPurposes'
			       },{
			    	name : 'note',
			    	type : 'string',
			    	mapping : 'note'
			       }		
			       ]);
			
			suspensionseg_grid_josnReader = new Ext.data.JsonReader({
				totalProperty : "total",
				root : "suspensionwiresegs"
			}, suspensionseg_grid_reader);
			
			this.store = new Ext.data.Store({// 提供数据输入
				proxy : suspensionseg_grid_proxy,
				reader : suspensionseg_grid_josnReader,
				remoteSort : true
			});
			
			this.store.load({
				params : {
					"suspensionseg.suspensionWireId": this.suspensionWireId,
					limit:10,
					start:0
				}
			});
			
				
			
			tbar : [{
						xtype : 'tbseparator'
					},{
						id : 'refreshBtn',
						text : '全部刷新',
						icon : "imgs/all_refresh.png",
						cls : "x-btn-text-icon"
					},{
						xtype : 'tbseparator'
					},{
						id : 'importBtn',
						text : '上传',
						icon : "imgs/fiber/link_break.png",
						cls : "x-btn-text-icon"
					},{
						xtype : 'tbseparator'
					},{		
						id : 'exportBtn',
						text : '下载',
						icon : "imgs/fiber/link_break.png",
						cls : "x-btn-text-icon"
					},{
						xtype : 'tbseparator'				
					}]
			
			bbar : new Ext.PagingToolbar({// 分页工具栏
				store : this.store,
				pageSize : 10,
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
			});
			//双击查看并修改信息
			this.on('rowdblclick', function(g, r, e) {
				e.stopEvent();
				
			});
			//获得上传按钮
			var importBtn = Ext.getCmp("importBtn");
			importBtn.on('click', function(){
				grid.importSuspensionsegWindow = new com.increase.cen.poleline.PolelineWindow({
					id : "importSuspensionsegWindow",
					height:500,
					title : '上传',
					iconCls : 'i-script-window',
					items : [{
						id:'importsuspensionseg',
						fileUpload : true,
						width:600,
						height:300,
						xtype : 'importsuspensionseg'// 窗口里的表单
					}]
					
				});
				grid.importSuspensionsegWindow.show("importBtn");
			});
			//获得下载按钮
			var exportBtn=Ext.getCmp("exportBtn");
			exportBtn.on('click',function(){
				var selModel = grid.getSelectionModel();// 返回grid的SelectionModel
				if (selModel.hasSelection()) {
					var records = selModel.getSelections();
					var ids = [];
					for (var i = 0; i < records.length; i++) {
						ids.push(records[i].get("suspensionWireSegId"));// 获得选择的编号
					}
					location.href="dataupdate!getsuspensionseglist.action?ids="+ids;
					
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
			});
			//
			
			
			com.increase.cen.poleline.suspensionsegGrid.superclass.initComponent.call(this);	
		}
	
	});

	
Ext.reg("suspensionsegGrid", com.increase.cen.poleline.suspensionsegGrid);