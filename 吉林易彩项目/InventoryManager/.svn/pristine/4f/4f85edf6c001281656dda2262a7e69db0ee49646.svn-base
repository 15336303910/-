Ext.namespace("com.increase.cen.generator");
var limit = null;
com.increase.cen.generator.seeHFS= Ext.extend(Ext.form.FormPanel, {
	bodyStyle : "padding:5px;",
	frame : true,
	bodyBorder : false,
	labelAlign : "right",
	buttonAlign : 'center',
	height:350,
	labelWidth : 100,
	equt : null,
	windowId: null,
	initComponent : function(config) {
		// 启动悬停提示
		Ext.QuickTips.init();
		var row1={
			layout:'column',
			items:[{
				height:30,
				layout:'form',
				items:[{
					id : 'deviceName',
					xtype : 'textfield',
					fieldLabel : "设备名称",
					width : 150,
					readOnly :true
				}]
			},{
				layout:'form',
				items:[{
					id : 'fixedAssetsCode',
					xtype : 'textfield',
					fieldLabel : "资产标签号",
					width : 150,
					readOnly :true
				}]
			}]
		};
    	var row2={
			layout:'column',
			items:[{
				height:30,
				layout:'form',
				items:[{
					id : "generator",
					xtype : 'textfield',
					readOnly :true,
					fieldLabel : "所属机房",
		        	width : 150
				}]
			},{
				layout:'form',
				items:[{
					id : "ratedVoltage",
					xtype : 'textfield',
					fieldLabel : "额定电压",
		        	width : 150,
		        	readOnly :true
				}]
			}]
		};
		var row3={
			layout:'column',
			items:[{
				height:30,
				layout:'form',
				items:[{
					id : 'ratedElectricity',
					xtype : 'textfield',
					fieldLabel : "额定电流",
					width : 150,
					readOnly :true
				}]
			},{
				layout:'form',
				items:[{
					id : 'lifecycle',
					xtype : 'textfield',
					fieldLabel : "资源生命周期",
					readOnly :true,
					width : 150
				}]
			}]
		};
		var row4={
			layout:'column',
			items:[{
				height:30,
				layout:'form',
				items:[{
					id : 'maintainState',
					xtype : 'textfield',
					fieldLabel : "维护状态",
					readOnly :true,
					width : 150
				}]
			},{
				layout:'form',
				items:[{
					id : 'inNetTime',
					xtype : 'textfield',
					fieldLabel : "入网时间",
					readOnly :true,
					width : 150
				}]
			}]
		};
		var row5={
			layout:'column',
			items:[{
				height:30,
				layout:'form',
				items:[{
					id : 'deviceMaker',
					xtype : 'textfield',
					fieldLabel : '设备厂商',
					readOnly :true,
					width : 150
				}]
			},{
				layout:'form',
				items:[{
					id:'maintenanceMode', 
	           	 	fieldLabel : "维护方式",
	           		xtype :'textfield',
	           		readOnly :true,
	        		width : 150
				}]
			}]
		};
		var row6={
				layout:'column',
				items:[{
					height:30,
					layout:'form',
					items:[{
						id : 'maintenanceUnit',
						xtype : 'textfield',
						fieldLabel : '维护单位',
						readOnly :true,
						width : 150
					}]
				},{
					layout:'form',
					items:[{
						id:'note', 
		           	 	fieldLabel : "备注",
		           		xtype :'textfield',
		           		readOnly :true,
		        		width : 150
					}]
				}]
			};
		
		
		this.items =[{
			xtype : 'fieldset',
			id : 'fieldset',
			title : '电源信息',
			autoScroll:true,
			items : [row1,row2,row3,row4,row5,row6]
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
		com.increase.cen.generator.seeHFS.superclass.initComponent.call(this);
	},

	// 关闭
	btnClose : function() {
		// 窗口隐藏
		Ext.getCmp(this.windowId).close();
	}
});
Ext.reg("seeHFS", com.increase.cen.generator.seeHFS);
