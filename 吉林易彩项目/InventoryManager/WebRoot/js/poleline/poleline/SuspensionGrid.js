Ext.namespace("com.increase.cen.poleline");
com.increase.cen.poleline.suspensionGrid = Ext.extend(Ext.grid.GridPanel,{
	region : 'center',
	id : "suspensionGrid",
	border : false,
	cm : null,// 列定义的model(必需)
	store : null,
	height:210,
	autoScroll : true,// 滚动条
	buttonAlign : "center",// 按钮居中
	polelineid:null,
	initComponent : function(config) {
		this.cm = new Ext.grid.ColumnModel([
			    {header : "序号",width : 50,hideable : false,
			    	renderer : function(value, metadata, record, rowIndex, colIndex, store) {
			          var start = store.lastOptions.params.start;
			            if (isNaN(start)) {
			            start = 0;
			            }
			            return start + rowIndex + 1;
			    	}
			    }, 
			    {header : '吊线ID',dataIndex : 'suspensionWireId',width :50,hidden : true},
			    {header : '吊线名称',dataIndex : 'suspensionWireName',width :150}, 
			    {header : '吊线编号',dataIndex : 'suspensionWireCode',width : 100}, 
			    {header : '所属维护区域',dataIndex : 'region',width : 100}, 
			    {header : '长度',dataIndex : 'length',width : 100}, 
			    {header : '资源生命周期',dataIndex : 'resourceLifePeriodEnumId',width : 100}, 
			    {header : '生命周期时间',dataIndex : 'lifecycleTime',width : 150},
			    {header : '是否授权管理',dataIndex : 'isAuthorizationManagement',width : 150},
			    {header : '授权管理单位',dataIndex : 'authorizationManagementUnit',width : 150},
			    {header : '设计用途',dataIndex : 'designPurposes',width : 150},
			    {header : '备注',dataIndex : 'note',width : 150}
			    ]);
			
			suspension_grid_proxy = new Ext.data.HttpProxy({
				url : "poleline!getSuspensionlist.action"
			});
			
			suspension_grid_reader = Ext.data.Record.create([
			       {
			        name : 'suspensionWireId',
			        type : 'string',
			        mapping : 'suspensionWireId'
			       },{
			        name : 'suspensionWireName',
			        type : 'string',
			        mapping : 'suspensionWireName'
			       },{
			        name : 'suspensionWireCode',
			        type : 'string',
			        mapping : 'suspensionWireCode'
			       },{
			        name : 'region',
			        type : 'string',
			        mapping : 'region'
			       },{
			        name : 'length',
			        type : 'string',
			        mapping : 'length'
			       },{
			        name : 'resourceLifePeriodEnumId',
			        type : 'string',
			        mapping : 'resourceLifePeriodEnumId'
			       },{
			        name : 'resourceLifePeriodEnumId',
			        type : 'string',
			        mapping : 'resourceLifePeriodEnumId'
			       },{
			        name : 'resourceLifePeriodEnumId',
			        type : 'string',
			        mapping : 'resourceLifePeriodEnumId'
			       },{
			        name : 'resourceLifePeriodEnumId',
			        type : 'string',
			        mapping : 'resourceLifePeriodEnumId'
			       }]);
			
			suspension_grid_josnReader = new Ext.data.JsonReader({
				totalProperty : "total",
				root : "suspensions"
			}, suspension_grid_reader);
			
			this.store = new Ext.data.Store({// 提供数据输入
				proxy : suspension_grid_proxy,
				reader : suspension_grid_josnReader,
				remoteSort : true
			});
			
			this.store.load({
				params : {
					"suspension.poleLineId": this.polelineid,
					limit:10,
					start:0
				}
			});
			
			
			//双击查看并修改信息
			this.on('rowdblclick', function(g, r, e) {
				e.stopEvent();
				var suspension=g.getStore().getAt(r).json;
				this.suspensionsegGridWindow=new com.increase.cen.poleline.PolelineWindow({
					id:"suspensionsegGridWindow",
					width : 750,
					items : [{
						xtype:"suspensionsegGrid",
						suspensionWireId:suspension.suspensionWireId
					}]
				});
				this.suspensionsegGridWindow.show();
			});
			
			com.increase.cen.poleline.suspensionGrid.superclass.initComponent.call(this);	
		}
	});

	
Ext.reg("suspensionGrid", com.increase.cen.poleline.suspensionGrid);