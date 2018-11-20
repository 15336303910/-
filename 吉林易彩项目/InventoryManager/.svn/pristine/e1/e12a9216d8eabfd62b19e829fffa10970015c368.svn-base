Ext.namespace("com.increase.cen.generator");
var limit = null;
com.increase.cen.generator.seeStation = Ext.extend(Ext.form.FormPanel, {
	bodyStyle : "padding:5px;",
	frame : true,
	bodyBorder : false,
	labelAlign : "right",
	buttonAlign : 'center',
	height:300,
	labelWidth : 100,
	equt : null,
	windowId: null,
	autoScroll : true,
	initComponent : function(config) {
		// 启动悬停提示
		Ext.QuickTips.init();
		var row1={
			layout:'column',
			items:[{
				height:30,
				layout:'form',
				items:[{
					id : 'stationName',
					xtype : 'textfield',
					fieldLabel : "机房名称",
					width : 150,
					readOnly :true
					
				}]
			},{
				layout:'form',
				items:[{
					id : 'region',
					xtype : 'textfield',
					fieldLabel : "所属维护区域",
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
					id : "lon",
					xtype : 'textfield',
					readOnly :true,
					fieldLabel : "站点经度",
		        	width : 150
		        	
		         
				}]
			},{
				layout:'form',
				items:[{
					id : "lat",
					xtype : 'textfield',
					fieldLabel : "站点纬度",
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
					id : 'stationAddr',
					xtype : 'textfield',
					fieldLabel : "站点地址",
					width : 150,
					readOnly :true
					
				}]
			},{
				layout:'form',
				items:[{
					id : 'stationType',
					xtype : 'textfield',
					fieldLabel : "站点类型",
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
					id : 'stationNetWorkType',
					xtype : 'textfield',
					fieldLabel : "站点网络类型",
					readOnly :true,
					width : 150
					
				}]
			},{
				layout:'form',
				items:[{
					id : 'stationLevel',
					xtype : 'textfield',
					fieldLabel : "局站等级",
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
					id : 'stationRange',
					xtype : 'textfield',
					fieldLabel : '站点范围',
					readOnly :true,
					width : 150
					
					
				}]
			},{
				layout:'form',
				items:[{
					id:'isAuthorizationManagement', 
	           	 	fieldLabel : "是否授权管理",
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
						id : 'authorizationManagementUnit',
						xtype : 'textfield',
						fieldLabel : '授权管理单位',
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
			title : '局站信息',
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
		com.increase.cen.generator.seeStation.superclass.initComponent.call(this);
	},

	// 关闭
	btnClose : function() {
		// 窗口隐藏
		Ext.getCmp(this.windowId).close();
	}
});
Ext.reg("seeStation", com.increase.cen.generator.seeStation);
