Ext.namespace("com.increase.cen.poleline");
var limit = null;
com.increase.cen.poleline.WellFace = Ext.extend(Ext.form.FormPanel, {
	bodyStyle : "padding:5px;",
	frame : true,
	bodyBorder : false,
	labelAlign : "right",
	buttonAlign : 'center',
	labelWidth : 120,
	width : 400,
	height : 410,
	autoScroll : true,
	initComponent : function(config) {
		// 一页显示多少条数据
		limit = Ext.getDom("limit").value;
		// 启动悬停提示
		Ext.QuickTips.init();
		// 提示的方式
		Ext.form.Field.prototype.msgTarget = 'qtip';
		// 用光缆信息是否存在
		var idIsExist = true;
		var faceno=new Ext.form.TextField({
			id : 'faceno',
			name : "faceno",
			fieldLabel : "面",
			hidden : true
		});
		var faceids=new Ext.form.TextField({
			id : 'faceidss',
			name : "faceids",
			fieldLabel : "对端面ID",
			hidden : true
		});
		var form1 = {
			layout : 'column', // 从左往右布局
			style:'margin:0 0 0 25',
			items : [{
				id : 'ox_8',
				xtype : 'button',
				text : "西北",
				width:100,
				height:100,
				tooltip: '',
				iconCls:'',
				handler : function() {
					shuzi3(this);
				}
			},{
				xtype : 'tbseparator'
			},{
				id : 'ox_1',
				xtype : 'button',
				text : "北",
				width:100,
				height:100,
				tooltip: '',
				handler : function() {
					shuzi3(this);
				}
			},{
				xtype : 'tbseparator'
			},{
				id : 'ox_2',
				xtype : 'button',
				text : "东北",
				width:100,
				height:100,
				tooltip: '',
				handler : function() {
					shuzi3(this);
				}
			}]
		};
		var form2 = {
			layout : 'column', // 从左往右布局
			style:'margin:0 0 0 25',
			items : [{
				id : 'ox_7',
				xtype : 'button',
				text : "西",
				width:100,
				height:100,
				tooltip: '',
				handler : function() {
					shuzi3(this);
				}
			},{
				xtype : 'tbseparator'
			},{
				xtype : 'button',
				text : "",
				width:100,
				height:100,
				handler : function() {
					
				}
			},{
				xtype : 'tbseparator'
			},{
				id : 'ox_3',
				xtype : 'button',
				text : "东",
				width:100,
				height:100,
				tooltip: '',
				handler : function() {
					shuzi3(this);
				}
			}]
		};
		var form3 = {
			layout : 'column', // 从左往右布局
			style:'margin:0 0 0 25',
			items : [{
				id : 'ox_6',
				xtype : 'button',
				text : "西南",
				width:100,
				height:100,
				tooltip: '',
				handler : function() {
					shuzi3(this);
				}
			},{
				xtype : 'tbseparator'
			},{
				id : 'ox_5',
				xtype : 'button',
				text : "南",
				width:100,
				height:100,
				tooltip: '',
				handler : function() {
					shuzi3(this);
				}
			},{
				xtype : 'tbseparator'
			},{
				id : 'ox_4',
				xtype : 'button',
				text : "东南",
				width:100,
				height:100,
				tooltip: '',
				handler : function() {
					shuzi3(this);
				}
			}]
		};
		this.items = [{
			xtype : 'fieldset',
			title : '选择井立面',
			collapsible : true,
			items : [form1,form2,form3]
		}];

		// 按钮
		this.buttons = [{
			id : 'saveSubmit',
			text : "保存",
			hidden : false,
			tooltip : '保存井面信息',
			tooltipType : 'qtip',
			handler : this.saveSubmit.createDelegate(this)
		}];
		// 重写initComponent superclass.initComponent.call(this);来调用父类的初始化方法.
		com.increase.cen.poleline.WellFace.superclass.initComponent.call(this);
	},
	// 保存按钮事件
	saveSubmit : function() {
		showWellFace();
	}
});
Ext.reg("wellFace", com.increase.cen.poleline.WellFace);
