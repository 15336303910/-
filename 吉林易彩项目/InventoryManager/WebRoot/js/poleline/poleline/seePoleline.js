Ext.namespace("com.increase.cen.poleline");
var limit = null;
com.increase.cen.poleline.seePoleline = Ext.extend(Ext.Panel, {
	bodyStyle : "padding:5px;",
	frame : true,
	bodyBorder : false,
	layout:"border",	//定义页面样式
	labelAlign : "right",
	buttonAlign : 'center',
	labelWidth : 100,
	
	height : 300,
	poleline:null,
	windowId:null,
	
	initComponent : function(config) {
		var formpanel;
		var panel;
			
		// 启动悬停提示
		Ext.QuickTips.init();
		Ext.form.Field.prototype.msgTarget = 'qtip';
		this.poleLineName = new Ext.form.TextField({
			id : "poleLineName",
			fieldLabel : "杆路名称",
			width : 200,
			readOnly:true,
			value:this.poleline.poleLineName
		});
		this.poleLineCode = new Ext.form.TextField({
			id : "poleLineCode",
			fieldLabel : "杆路编码",
			width : 200,
			readOnly:true,
			value:this.poleline.poleLineCode
		});
		this.poleLineLevel = new Ext.form.TextField({
			id : "poleLineLevel",
			fieldLabel : "杆路级别",
			width : 200,
			readOnly:true,
			value:this.poleline.poleLineLevel
		});
		this.poleLineLength = new Ext.form.TextField({
			id : "poleLineLength",
			fieldLabel : "杆路长度",
			width : 200,
			readOnly:true,
			value:this.poleline.poleLineLength
		});
		//信息预留位
		this.startDeviceName = new Ext.form.TextField({
			id : "startDeviceName",
			fieldLabel : "起始设施名称",
			width : 200,
			readOnly:true,
			value:this.poleline.startDeviceName
		});
		this.endDeviceName = new Ext.form.TextField({
			id : "endDeviceName",
			fieldLabel : "终止设施名称",
			width : 200,
			readOnly:true,
			value:this.poleline.endDeviceName
		});
		this.lastUpdateDate = new Ext.form.TextField({
			id : "lastUpdateDate",
			fieldLabel : "最后更新时间",
			width : 200,
			readOnly:true,
			value: this.poleline.lastUpdateDate
		});
		
		//左上items
		this.formpanel= {
			title : '基本信息',
			region : "center",
			width:350,
			items:[{
				xtype : 'fieldset',
				height:230,
				items : [this.poleLineName,this.poleLineCode,this.poleLineLevel,this.poleLineLength,
				         this.startDeviceName,this.endDeviceName,this.lastUpdateDate]
			}]
		};
		//左下items
		this.panel={
			title:"杆路内吊线",
			width:350,
			region : "east",
			items : [{
				polelineid : this.poleline.poleLineId,
				xtype:"suspensionGrid"
			}]
		};
		this.items=[
		            this.panel,this.formpanel
		];
		this.buttons = [{
			text : "返回",
			icon : "imgs/back.png",
			cls : "x-btn-text-icon",
			tooltip : '关闭此窗口',
			tooltipType : 'qtip',
			handler : this.btnClose.createDelegate(this)
		}];
		com.increase.cen.poleline.seePoleline.superclass.initComponent.call(this);
	},
	btnClose : function() {
		// 窗口关闭
//		this.close();
		if(this.windowId != null)
			Ext.getCmp(this.windowId).close();
		
	}
});
Ext.reg("seePoleline", com.increase.cen.poleline.seePoleline);