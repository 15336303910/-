Ext.namespace("com.increase.cen.document");
com.increase.cen.document.PointLogNewGrid = Ext.extend(Ext.form.FormPanel, {
	bodyStyle : "padding:5px;",
	frame : true,
	bodyBorder : false,
	labelAlign : "right",
	buttonAlign : 'center',
	labelWidth : 65,
	width : 300,
	height : 300,
	initComponent : function(config) {
		// 启动悬停提示
		Ext.QuickTips.init();
		// 提示的方式
		Ext.form.Field.prototype.msgTarget = 'qtip';
		
		var row1 = {
			layout : 'column', // 从左往右布局
			items : [{
				columnWidth : .5, // 该列有整行中所占百分比
				height : 40,
				layout : 'form', // 从上往下布局
				items : [{
					id : 'show_ename',
					name : 'pointInfoBean.ename',
					xtype : 'textfield',
					css:'color:red;',
					fieldLabel : '设备名称',
					width : 140
				}]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [{
					id : 'show_pcode',
					name : 'pointInfoBean.pcode',
					xtype : 'textfield',
					fieldLabel : '端子编码',
					width : 140
				}]
			}]
		};
		var row2 = {
			layout : 'column', // 从左往右布局
			items : [{
				columnWidth : .5, // 该列有整行中所占百分比
				height : 40,
				layout : 'form', // 从上往下布局
				items : [{
					id : 'show_pstat',
					name : 'pointInfoBean.pstat',
					xtype : 'textfield',
					fieldLabel : '端子状态',
					width : 140
				}]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [{
					id : 'show_pserv',
					name : 'pointInfoBean.pserv',
					xtype : 'textfield',
					fieldLabel : '承载业务',
					width : 140
				}]
			}]
		};
		var row3 = {
			layout : 'column', // 从左往右布局
			items : [{
				columnWidth : .5, // 该列有整行中所占百分比
				height : 40,
				layout : 'form', // 从上往下布局
				items : [{
					id : 'show_fibname',
					name : 'pointInfoBean.fibname',
					xtype : 'textfield',
					fieldLabel : '光纤名称',
					width : 140
				}]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [{
					id : 'show_ofpcode',
					name : 'pointInfoBean.ofpcode',
					xtype : 'textfield',
					fieldLabel : '光路编码',
					width : 140
				}]
			}]
		};
		var row4 = {
			layout : 'column', // 从左往右布局
			items : [{
				columnWidth : .5, // 该列有整行中所占百分比
				height : 40,
				layout : 'form', // 从上往下布局
				items : [{
					id : 'show_ofpname',
					name : 'pointInfoBean.ofpname',
					xtype : 'textfield',
					fieldLabel : '光路名称',
					width : 140
				}]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [{
					id : 'show_oppo_pcode',
					name : 'pointInfoBean.oppo_pcode',
					xtype : 'textfield',
					fieldLabel : '对端端子/端口编码',
					width : 140
				}]
			}]
		};
		var row5 = {
			layout : 'column', // 从左往右布局
			items : [{
				columnWidth : .5, // 该列有整行中所占百分比
				height : 40,
				layout : 'form', // 从上往下布局
				items : [{
					id : 'show_mp',
					name : 'pointInfoBean.mp',
					xtype : 'textfield',
					fieldLabel : '维护人',
					width : 140
				}]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [{
					id : 'show_mtime',
					name : 'pointInfoBean.mtime',
					xtype : 'textfield',
					fieldLabel : '维护时间',
					width : 140
				}]
			}]
		};
		var row6 = {
			layout : 'column', // 从左往右布局
			items : [{
				columnWidth : .5, // 该列有整行中所占百分比
				height : 40,
				layout : 'form', // 从上往下布局
				items : [{
					id : 'show_workorderno',
					name : 'pointInfoBean.workorderno',
					xtype : 'textfield',
					fieldLabel : '相关工单',
					width : 140
				}]
			}]
		};
		this.items = [{
			xtype : 'fieldset',
			title : '基本信息',
			collapsible : true,
			items : [row1,row2,row3,row4,row5,row6]
		}];
		// 重写initComponent superclass.initComponent.call(this);来调用父类的初始化方法.
		com.increase.cen.document.PointLogNewGrid.superclass.initComponent.call(this);
	}
});
Ext.reg("pointLogNewGrid", com.increase.cen.document.PointLogNewGrid);
