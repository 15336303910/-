Ext.namespace("com.increase.cen.equt");

com.increase.cen.equt.seePoint = Ext.extend(Ext.form.FormPanel, {
	bodyStyle : "padding:5px;",
	frame : true,
	bodyBorder : false,
	labelAlign : "right",
	buttonAlign : 'center',
	labelWidth : 80,
	height : 400,
	windowId : null,
	point : null,
	initComponent : function(config) {
		// 启动悬停提示
		Ext.QuickTips.init();
		
		this.pcode = new Ext.form.TextField({
			id : "pcode",
			fieldLabel : "端子编码",
			width : 200,
			readOnly:true,
			value:this.point.pcode
		});
		
		this.ptype = new Ext.form.TextField({
			id : "ptype",
			fieldLabel : "端子类型",
			width : 200,
			readOnly:true,
			value:getPointTypeStr(this.point.ptype)
		});
		
		this.pstat = new Ext.form.TextField({
			id : "pstat",
			fieldLabel : "端子状态",
			width : 200,
			readOnly:true,
			value:getPstatStr(this.point.pstat)
		});
		
		this.pserv = new Ext.form.TextField({
			id : "pserv",
			fieldLabel : "承载业务",
			width : 200,
			readOnly:true,
			value:this.point.pserv
		});
		
		this.fibname = new Ext.form.TextField({
			id : "proID",
			fieldLabel : "光纤名称",
			width : 200,
			readOnly:true,
			value:this.point.fibname
		});
		
		this.ofpcode = new Ext.form.TextField({
			id : "ofpcode",
			fieldLabel : "光路编码",
			width : 200,
			readOnly:true,
			value:this.point.ofpcode
		});
		
		this.items = [{
			xtype : 'fieldset',
			title : '基本信息',
			collapsible : true,
			items : [this.pcode,this.ptype,this.pstat,this.pserv,this.fibname,this.ofpcode]
		}];
		
		// 按钮
		this.buttons = [{
			text : "返回",
			icon : "imgs/back.png",
			cls : "x-btn-text-icon",
			tooltip : '关闭此窗口',
			tooltipType : 'qtip',
			handler : this.btnClose.createDelegate(this)
		}];
		// 重写initComponent superclass.initComponent.call(this);来调用父类的初始化方法.
		com.increase.cen.equt.seePoint.superclass.initComponent.call(this);
	},
	btnClose : function() {
		// 窗口隐藏
		Ext.getCmp(this.windowId).close();
		// 刷新表格
		//Ext.getCmp("poleLineGrid").getStore().reload();
	}
});
Ext.reg("seePoint", com.increase.cen.equt.seePoint);
