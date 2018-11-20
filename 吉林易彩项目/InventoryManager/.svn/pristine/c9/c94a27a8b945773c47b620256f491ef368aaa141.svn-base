Ext.namespace("com.increase.cen.document");
com.increase.cen.document.QueryDocument = Ext.extend(Ext.form.FormPanel, {
	bodyStyle : "padding:5px;",
	bodyBorder : false,
	frame : true,
	labelAlign : "right",
	buttonAlign : 'center',
	labelWidth : 65,
	width : 400,
	height : 200,
	initComponent : function(config) {
		// 启动悬停提示
		Ext.QuickTips.init();
		Ext.form.Field.prototype.msgTarget = 'qtip';// 提示的方式

		var typeStore = new Ext.data.SimpleStore({
			fields : ['value', 'text'],
			data : [['','全部类型'],['1',"光配线架(ODF)"], ['2',"综合性光配线架(MODF)"], ['3',"光交接箱"],
							['4',"光分路器(分光器)"], ['5',"光分纤盒"],['6',"光缆接头盒(终端盒)"], ['7',"ONU"]]
		});
		
		this.areano = new Ext.form.Hidden({
			id:"queryAreano",
			value:''
		});
		
		this.row1 = {
			layout : 'column', // 从左往右布局
			items : [{
				columnWidth : .5, // 该列有整行中所占百分比
				height : 40,
				layout : 'form', // 从上往下布局
				items : [{
					id : 'doc_ecode',
					xtype : 'textfield',
					fieldLabel : '设备编码',
					labelStyle:'width:80px',
					width : 130
				}]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [{
					id : 'doc_ename',
					xtype : 'textfield',
					fieldLabel : '设备名称',
					labelStyle:'width:80px',
					width : 130
				}]
			}]
		};
		this.row2 = {
			layout : 'column', // 从左往右布局
			items : [{
				columnWidth : .5,
				layout : 'form',
				items : [{
					fieldLabel : '设备类型',
					xtype : 'combo',
					labelStyle:'width:80px',
					id : 'doc_etype_id',
					store : typeStore,
					width : 130,
					value : '',
					displayField : 'text',
					valueField : 'value',
					mode : 'local',
					triggerAction : "all",// 下拉框获得了焦点或者被点击了
					editable : false,// 设置为false以阻止用户直接向该输入项输入文本
					hiddenName : 'doc_estat'
				}]
			},{
				columnWidth : .5, // 该列有整行中所占百分比
				height : 40,
				layout : 'form', // 从上往下布局
				items : [{
					id : 'doc_eaddr',
					xtype : 'textfield',
					labelStyle:'width:80px',
					fieldLabel : '设备地址',
					width : 130
				}]
			}]
		};
		
		this.items = [{
			xtype : 'fieldset',
			title : '查询条件',
			collapsible : true,
			items : [this.row1, this.row2]
		}];
		// 按钮
		this.buttons = [{
			text : "查询",
			icon : "imgs/queryBtn.png",
			cls : "x-btn-text-icon",
			tooltip : '查询档案',
			tooltipType : 'qtip',
			handler : this.btnSubmit.createDelegate(this)
		}, {
			xtype : 'tbspacer'
		}, {
			xtype : 'tbspacer'
		}, {
			xtype : 'tbspacer'
		}, {
			text : "返回",
			icon : "imgs/back.png",
			cls : "x-btn-text-icon",
			tooltip : '关闭此窗口',
			tooltipType : 'qtip',
			handler : this.btnClose.createDelegate(this)
		}];
		// 重写initComponent superclass.initComponent.call(this);来调用父类的初始化方法.
		com.increase.cen.document.QueryDocument.superclass.initComponent.call(this);
	},
	// 得到查询条件，查询结果
	btnSubmit : function submitForm() {
		// 获取一页的记录数 
		var limit = Ext.getDom("limit").value;
		Ext.getCmp('grid').getStore().load({
			params : {
				'start' : 0,
				'limit' : limit,
				'searchdequt.ecode':Ext.getCmp('doc_ecode').getValue(),
				'searchdequt.ename':Ext.getCmp('doc_ename').getValue(),
				'searchdequt.etype':Ext.getCmp('doc_etype_id').getValue(),
				'searchdequt.eaddr':Ext.getCmp('doc_eaddr').getValue(),
				'searchdequt.areano':Ext.getCmp('queryAreano').getValue()
			}
		});
		Ext.getCmp('grid').getStore().on('beforeload', function() {
			Ext.getCmp('grid').getStore().baseParams = {
				'start' : 0,
				'limit' : limit,
				'searchdequt.ecode':Ext.getCmp('doc_ecode').getValue(),
				'searchdequt.ename':Ext.getCmp('doc_ename').getValue(),
				'searchdequt.etype':Ext.getCmp('doc_etype_id').getValue(),
				'searchdequt.eaddr':Ext.getCmp('doc_eaddr').getValue(),
				'searchdequt.areano':Ext.getCmp('queryAreano').getValue()
			};
		});
		// 表格加载数据
		Ext.getCmp('grid').getView().refresh();// 表格刷新
		//Ext.getCmp("queryDoc").getForm().reset();// 重置表单
		Ext.getCmp("queryDocWindow").hide();
	},
	// 关闭窗口
	btnClose : function() {
		Ext.getCmp("queryDoc").getForm().reset();// 重置表单
		Ext.getCmp("queryDocWindow").hide();
	}
});
Ext.reg("queryDoc", com.increase.cen.document.QueryDocument);