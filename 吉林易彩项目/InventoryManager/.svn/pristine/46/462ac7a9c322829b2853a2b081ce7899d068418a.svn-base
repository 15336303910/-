Ext.namespace("com.increase.cen.poleline");
var limit = null;
com.increase.cen.poleline.Face2Number = Ext.extend(Ext.form.FormPanel, {
	bodyStyle : "padding:5px;",
	frame : true,
	bodyBorder : false,
	labelAlign : "right",
	buttonAlign : 'center',
	width : 400,
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
		var form1 = {
			layout : 'column', // 从左往右布局
			style:'margin:0 0 0 80',
			items : [{
				id : 'ff1',
				xtype : 'button',
				text : "1",
				width:50,
				height:50,
				handler : function() {
					fuzhi2(this);
				}
			},{
				id : 'ff2',
				xtype : 'button',
				text : "2",
				width:50,
				height:50,
				style : 'margin-left:100px',
				handler : function() {
					fuzhi2(this);
				}
			}]
		};
		var form2 = {
			layout : 'column', // 从左往右布局
			style:'margin:0 0 0 80',
			items : [{
				id : 'ff3',
				xtype : 'button',
				text : "3",
				width:50,
				height:50,
				handler : function() {
					fuzhi2(this);
				}
			},{
				id : 'ff4',
				xtype : 'button',
				text : "4",
				width:50,
				height:50,
				style : 'margin-left:100px',
				handler : function() {
					fuzhi2(this);
				}
			}]
		};
		var form3 = {
			layout : 'column', // 从左往右布局
			style:'margin:0 0 0 80',
			items : [{
				id : 'ff5',
				xtype : 'button',
				text : "5",
				width:50,
				height:50,
				handler : function() {
					fuzhi2(this);
				}
			},{
				id : 'ff6',
				xtype : 'button',
				text : "6",
				width:50,
				height:50,
				style : 'margin-left:100px',
				handler : function() {
					fuzhi2(this);
				}
			}]
		};
		var form4 = {
			layout : 'column', // 从左往右布局
			style:'margin:0 0 0 80',
			items : [{
				id : 'ff7',
				xtype : 'button',
				text : "7",
				width:50,
				height:50,
				handler : function() {
					fuzhi2(this);
				}
			},{
				id : 'ff8',
				xtype : 'button',
				text : "8",
				width:50,
				height:50,
				style : 'margin-left:100px',
				handler : function() {
					fuzhi2(this);
				}
			}]
		};
		var form5 = {
			layout : 'column', // 从左往右布局
			style:'margin:0 0 0 80',
			items : [{
				xtype : 'button',
				text : "清除",
				width:50,
				height:50,
				handler : function() {
					qingchu2();
				}
			},{
				xtype : 'button',
				text : "关闭",
				style : 'margin-left:100px',
				width:50,
				height:50,
				handler : function() {
					hideFaceNumber2();
				}
			}]
		};
		this.items = [{
			xtype : 'fieldset',
			title : '选择井面对应的数字',
			collapsible : true,
			items : [form1,form2,form3,form4,form5]
		}];

		// 重写initComponent superclass.initComponent.call(this);来调用父类的初始化方法.
		com.increase.cen.poleline.Face2Number.superclass.initComponent.call(this);
	}
});
Ext.reg("face2Number", com.increase.cen.poleline.Face2Number);
