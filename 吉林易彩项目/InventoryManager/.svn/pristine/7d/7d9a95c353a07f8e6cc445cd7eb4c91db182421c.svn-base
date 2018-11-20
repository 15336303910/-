Ext.namespace("com.increase.cen.poleline");
var limit = null;
com.increase.cen.poleline.FaceConfirm = Ext.extend(Ext.form.FormPanel, {
	bodyStyle : "padding:5px;",
	frame : true,
	bodyBorder : false,
	labelAlign : "right",
	buttonAlign : 'center',
	labelWidth : 120,
	width : 400,
	height : 200,
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
		var commitform=new Ext.form.TextField({
			id : 'commitform',
			name : "commitform",
			fieldLabel : "",
			hidden : true
		});
		var operate=new Ext.form.TextField({
			id : 'operate',
			name : "operate",
			fieldLabel : "",
			hidden : true
		});
		var form1 = {
			layout : 'column', // 从左往右布局
			style:'margin:0 0 0 50',
			items : [{
				html:'两个面管孔信息不一致，请选择基准面'
			}]
		};
		this.items = [{
			xtype : 'fieldset',
			title : '面信息确认',
			collapsible : true,
			items : [form1]
		}];

		// 按钮
		this.buttons = [{
			text : "本端面",
			hidden : false,
			tooltip : '以本端面为基准面',
			tooltipType : 'qtip',
			handler : function(){
				dosubmitOppo('1');
			}
		},{
			text : "对端面",
			hidden : false,
			tooltip : '以对端面为基准面',
			tooltipType : 'qtip',
			handler : function(){
				dosubmitOppo('2');
			}
		},{
			text : "取消",
			hidden : false,
			handler : function(){
				hideFaceConfirm();
			}
		}];
		// 重写initComponent superclass.initComponent.call(this);来调用父类的初始化方法.
		com.increase.cen.poleline.FaceConfirm.superclass.initComponent.call(this);
	}
});
Ext.reg("faceConfirm", com.increase.cen.poleline.FaceConfirm);
