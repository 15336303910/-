Ext.namespace("com.increase.cen.generator");
var limit = null;
com.increase.cen.generator.seeGenerator = Ext.extend(Ext.form.FormPanel, {
	bodyStyle : "padding:5px;",
	frame : true,
	bodyBorder : false,
	labelAlign : "right",
	buttonAlign : 'center',
	height:350,
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
					id : 'generatorName',
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
					id : "station",
					xtype : 'textfield',
					readOnly :true,
					fieldLabel : "所属局站",
		        	width : 150
				}]
			},{
				layout:'form',
				items:[{
					id : "generatorAddr",
					xtype : 'textfield',
					fieldLabel : "机房地址",
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
					id : 'canUseArea',
					xtype : 'textfield',
					fieldLabel : "可用面积",
					width : 150,
					readOnly :true
				}]
			},{
				layout:'form',
				items:[{
					id : 'usedArea',
					xtype : 'textfield',
					fieldLabel : "已用面积",
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
					id : 'affiliation',
					xtype : 'textfield',
					fieldLabel : "机房归属",
					readOnly :true,
					width : 150
				}]
			},{
				layout:'form',
				items:[{
					id : 'isShare',
					xtype : 'textfield',
					fieldLabel : "是否共享",
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
					id : 'shareOperator',
					xtype : 'textfield',
					fieldLabel : '共享方',
					readOnly :true,
					width : 150
				}]
			},{
				layout:'form',
				items:[{
					id:'isBaseStation', 
	           	 	fieldLabel : "是否基站站点",
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
						id : 'takeUpMode',
						xtype : 'textfield',
						fieldLabel : '走线方式',
						readOnly :true,
						width : 150
					}]
				},{
					layout:'form',
					items:[{
						id:'overTime', 
		           	 	fieldLabel : "租用到期时间",
		           		xtype :'textfield',
		           		readOnly :true,
		        		width : 150
					}]
				}]
			};
		var row7={
				layout:'column',
				items:[{
					height:30,
					layout:'form',
					items:[{
						id : 'charteredMan',
						xtype : 'textfield',
						fieldLabel : '包机人',
						readOnly :true,
						width : 150
					}]
				},{
					layout:'form',
					items:[{
						id:'maIntegeraIntegerype', 
		           	 	fieldLabel : "维护方式",
		           		xtype :'textfield',
		           		readOnly :true,
		        		width : 150
					}]
				}]
			};
		var row8={
				layout:'column',
				items:[{
					height:30,
					layout:'form',
					items:[{
						id : 'maIntegerainUnit',
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
			title : '机房信息',
			autoScroll:true,
			items : [row1,row2,row3,row4,row5,row6,row7,row8]
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
		com.increase.cen.generator.seeGenerator.superclass.initComponent.call(this);
	},

	// 关闭
	btnClose : function() {
		// 窗口隐藏
		Ext.getCmp(this.windowId).close();
	}
});
Ext.reg("seeGenerator", com.increase.cen.generator.seeGenerator);
