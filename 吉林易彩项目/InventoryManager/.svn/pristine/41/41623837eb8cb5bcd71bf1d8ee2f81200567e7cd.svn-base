Ext.namespace("com.increase.cen.property");
var limit = null;
com.increase.cen.property.seeProperty = Ext.extend(Ext.form.FormPanel, {
	bodyStyle : "padding:5px;",
	frame : true,
	bodyBorder : false,
	labelAlign : "right",
	buttonAlign : 'center',
	height:350,
	labelWidth : 150,
	autoScroll: true,
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
					id : 'proper_zczb',
					xtype : 'textfield',
					fieldLabel : "资产帐簿",
					width : 150,
					readOnly :true
				}]
			},{
				layout:'form',
				items:[{
					id : 'proper_zcbh',
					xtype : 'textfield',
					fieldLabel : "资产编号",
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
					id : "proper_zcbqh",
					xtype : 'textfield',
					readOnly :true,
					fieldLabel : "资产标签号",
		        	width : 150
				}]
			},{
				layout:'form',
				items:[{
					id : "proper_pdjg",
					xtype : 'textfield',
					fieldLabel : "盘点结果",
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
					id : 'proper_zcmc',
					xtype : 'textfield',
					fieldLabel : "资产名称",
					width : 150,
					readOnly :true
				}]
			},{
				layout:'form',
				items:[{
					id : 'proper_sfcf',
					xtype : 'textfield',
					fieldLabel : "是否拆分",
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
					id : 'proper_zclb',
					xtype : 'textfield',
					fieldLabel : "资产类别",
					readOnly :true,
					width : 150
				}]
			},{
				layout:'form',
				items:[{
					id : 'proper_zclbms',
					xtype : 'textfield',
					fieldLabel : "资产类别描述",
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
					id : 'proper_zzs',
					xtype : 'textfield',
					fieldLabel : '制造商',
					readOnly :true,
					width : 150
				}]
			},{
				layout:'form',
				items:[{
					id:'proper_zcxh', 
	           	 	fieldLabel : "资产型号",
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
						id : 'proper_zcsl',
						xtype : 'textfield',
						fieldLabel : '资产数量',
						readOnly :true,
						width : 150
					}]
				},{
					layout:'form',
					items:[{
						id:'proper_jldw', 
		           	 	fieldLabel : "计量单位",
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
						id : 'proper_zcgjz',
						xtype : 'textfield',
						fieldLabel : '资产关键字',
						readOnly :true,
						width : 150
					}]
				},{
					layout:'form',
					items:[{
						id:'proper_zcgjzms', 
		           	 	fieldLabel : "资产关键字描述",
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
						id : 'proper_zccjrq',
						xtype : 'textfield',
						fieldLabel : '资产创建日期',
						readOnly :true,
						width : 150
					}]
				},{
					layout:'form',
					items:[{
						id:'proper_zcqyrq', 
		           	 	fieldLabel : "资产启用日期",
		           		xtype :'textfield',
		           		readOnly :true,
		        		width : 150
					}]
				}]
			};
		var row9={
				layout:'column',
				items:[{
					height:30,
					layout:'form',
					items:[{
						id : 'proper_ablftrq',
						xtype : 'textfield',
						fieldLabel : "按比例分摊日期",
						width : 150,
						readOnly :true
					}]
				},{
					layout:'form',
					items:[{
						id : 'proper_synx',
						xtype : 'textfield',
						fieldLabel : "使用年限",
						width : 150,
						readOnly :true
					}]
				}]
			};
		var row10={
				layout:'column',
				items:[{
					height:30,
					layout:'form',
					items:[{
						id : 'proper_syys',
						xtype : 'textfield',
						fieldLabel : "剩余月数",
						width : 150,
						readOnly :true
					}]
				},{
					layout:'form',
					items:[{
						id : 'proper_gdzcyzzh',
						xtype : 'textfield',
						fieldLabel : "固定资产原值帐户",
						width : 150,
						readOnly :true
					}]
				}]
			};
		var row11={
				layout:'column',
				items:[{
					height:30,
					layout:'form',
					items:[{
						id : 'proper_gdzcyzzhms',
						xtype : 'textfield',
						fieldLabel : "固定资产原值帐户描述",
						width : 150,
						readOnly :true
					}]
				},{
					layout:'form',
					items:[{
						id : 'proper_gdzcljzjzh',
						xtype : 'textfield',
						fieldLabel : "固定资产累计折旧帐户",
						width : 150,
						readOnly :true
					}]
				}]
			};
		var row12={
				layout:'column',
				items:[{
					height:30,
					layout:'form',
					items:[{
						id : 'proper_gdzcljzjzhms',
						xtype : 'textfield',
						fieldLabel : "固定资产累计折旧帐户描述",
						width : 150,
						readOnly :true
					}]
				},{
					layout:'form',
					items:[{
						id : 'proper_zjfyzh',
						xtype : 'textfield',
						fieldLabel : "折旧费用帐户",
						width : 150,
						readOnly :true
					}]
				}]
			};
		var row13={
				layout:'column',
				items:[{
					height:30,
					layout:'form',
					items:[{
						id : 'proper_zjfyzhms',
						xtype : 'textfield',
						fieldLabel : "折旧费用帐户描述",
						width : 150,
						readOnly :true
					}]
				},{
					layout:'form',
					items:[{
						id : 'proper_zccb',
						xtype : 'textfield',
						fieldLabel : "资产成本",
						width : 150,
						readOnly :true
					}]
				}]
			};
		var row14={
				layout:'column',
				items:[{
					height:30,
					layout:'form',
					items:[{
						id : 'proper_zcjz',
						xtype : 'textfield',
						fieldLabel : "资产净值",
						width : 150,
						readOnly :true
					}]
				},{
					layout:'form',
					items:[{
						id : 'proper_zccz',
						xtype : 'textfield',
						fieldLabel : "资产残值",
						width : 150,
						readOnly :true
					}]
				}]
			};
		var row15={
				layout:'column',
				items:[{
					height:30,
					layout:'form',
					items:[{
						id : 'proper_zcbqzj',
						xtype : 'textfield',
						fieldLabel : "资产本期折旧",
						width : 150,
						readOnly :true
					}]
				},{
					layout:'form',
					items:[{
						id : 'proper_zcbnzj',
						xtype : 'textfield',
						fieldLabel : "资产本年折旧",
						width : 150,
						readOnly :true
					}]
				}]
			};
		var row16={
				layout:'column',
				items:[{
					height:30,
					layout:'form',
					items:[{
						id : 'proper_zcljzj',
						xtype : 'textfield',
						fieldLabel : "资产累计折旧",
						width : 150,
						readOnly :true
					}]
				},{
					layout:'form',
					items:[{
						id : 'proper_zcjzzb',
						xtype : 'textfield',
						fieldLabel : "资产减值准备",
						width : 150,
						readOnly :true
					}]
				}]
			};
		var row17={
				layout:'column',
				items:[{
					height:30,
					layout:'form',
					items:[{
						id : 'proper_ygbh',
						xtype : 'textfield',
						fieldLabel : "员工编号",
						width : 150,
						readOnly :true
					}]
				},{
					layout:'form',
					items:[{
						id : 'proper_ygxm',
						xtype : 'textfield',
						fieldLabel : "员工姓名",
						width : 150,
						readOnly :true
					}]
				}]
			};
		var row18={
				layout:'column',
				items:[{
					height:30,
					layout:'form',
					items:[{
						id : 'proper_ssqy',
						xtype : 'textfield',
						fieldLabel : "所属区域",
						width : 150,
						readOnly :true
					}]
				},{
					layout:'form',
					items:[{
						id : 'proper_ssqyms',
						xtype : 'textfield',
						fieldLabel : "所属区域描述",
						width : 150,
						readOnly :true
					}]
				}]
			};
		var row19={
				layout:'column',
				items:[{
					height:30,
					layout:'form',
					items:[{
						id : 'proper_szdd',
						xtype : 'textfield',
						fieldLabel : "所在地点",
						width : 150,
						readOnly :true
					}]
				},{
					layout:'form',
					items:[{
						id : 'proper_szddms',
						xtype : 'textfield',
						fieldLabel : "所在地点描述",
						width : 150,
						readOnly :true
					}]
				}]
			};
		var row20={
				layout:'column',
				items:[{
					height:30,
					layout:'form',
					items:[{
						id : 'proper_syzt',
						xtype : 'textfield',
						fieldLabel : "使用状态",
						width : 150,
						readOnly :true
					}]
				},{
					layout:'form',
					items:[{
						id : 'proper_syztms',
						xtype : 'textfield',
						fieldLabel : "使用状态描述",
						width : 150,
						readOnly :true
					}]
				}]
			};
		var row21={
				layout:'column',
				items:[{
					height:30,
					layout:'form',
					items:[{
						id : 'proper_qczckpbh',
						xtype : 'textfield',
						fieldLabel : "期初资产卡片编号",
						width : 150,
						readOnly :true
					}]
				},{
					layout:'form',
					items:[{
						id : 'proper_cqpzh',
						xtype : 'textfield',
						fieldLabel : "产权凭证号",
						width : 150,
						readOnly :true
					}]
				}]
			};
		var row22={
				layout:'column',
				items:[{
					height:30,
					layout:'form',
					items:[{
						id : 'proper_sbbh',
						xtype : 'textfield',
						fieldLabel : "设备编号",
						width : 150,
						readOnly :true
					}]
				},{
					layout:'form',
					items:[{
						id : 'proper_zysx',
						xtype : 'textfield',
						fieldLabel : "专业属性",
						width : 150,
						readOnly :true
					}]
				}]
			};
		var row23={
				layout:'column',
				items:[{
					height:30,
					layout:'form',
					items:[{
						id : 'proper_zylx',
						xtype : 'textfield',
						fieldLabel : "专业类型",
						width : 150,
						readOnly :true
					}]
				},{
					layout:'form',
					items:[{
						id : 'proper_zcly',
						xtype : 'textfield',
						fieldLabel : "资产来源",
						width : 150,
						readOnly :true
					}]
				}]
			};
		var row24={
				layout:'column',
				items:[{
					height:30,
					layout:'form',
					items:[{
						id : 'proper_zcgs',
						xtype : 'textfield',
						fieldLabel : "资产归属",
						width : 150,
						readOnly :true
					}]
				},{
					layout:'form',
					items:[{
						id : 'proper_krgzxm',
						xtype : 'textfield',
						fieldLabel : "扩容改造项目",
						width : 150,
						readOnly :true
					}]
				}]
			};
		var row25={
				layout:'column',
				items:[{
					height:30,
					layout:'form',
					items:[{
						id : 'proper_krgzxmms',
						xtype : 'textfield',
						fieldLabel : "扩容改造项目描述",
						width : 150,
						readOnly :true
					}]
				},{
					layout:'form',
					items:[{
						id : 'proper_zjzt',
						xtype : 'textfield',
						fieldLabel : "折旧状态",
						width : 150,
						readOnly :true
					}]
				}]
			};
		var row26={
				layout:'column',
				items:[{
					height:30,
					layout:'form',
					items:[{
						id : 'proper_pgjz',
						xtype : 'textfield',
						fieldLabel : "评估净值",
						width : 150,
						readOnly :true
					}]
				},{
					layout:'form',
					items:[{
						id : 'proper_pghsksyyf',
						xtype : 'textfield',
						fieldLabel : "评估后尚可使用月份",
						width : 150,
						readOnly :true
					}]
				}]
			};
		var row27={
				layout:'column',
				items:[{
					height:30,
					layout:'form',
					items:[{
						id : 'proper_fjxx1',
						xtype : 'textfield',
						fieldLabel : "附加信息一",
						width : 150,
						readOnly :true
					}]
				},{
					layout:'form',
					items:[{
						id : 'proper_fjxx2',
						xtype : 'textfield',
						fieldLabel : "附加信息二",
						width : 150,
						readOnly :true
					}]
				}]
			};
		var row28={
				layout:'column',
				items:[{
					height:30,
					layout:'form',
					items:[{
						id : 'proper_fjxx3',
						xtype : 'textfield',
						fieldLabel : "附加信息三",
						width : 150,
						readOnly :true
					}]
				},{
					layout:'form',
					items:[{
						id : 'proper_cscylx',
						xtype : 'textfield',
						fieldLabel : "财税差异类型",
						width : 150,
						readOnly :true
					}]
				}]
			};
		var row29={
				layout:'column',
				items:[{
					height:30,
					layout:'form',
					items:[{
						id : 'proper_fssbjfj',
						xtype : 'textfield',
						fieldLabel : "附属设备及附件",
						width : 150,
						readOnly :true
					}]
				},{
					layout:'form',
					items:[{
						id : 'proper_fzsl',
						xtype : 'textfield',
						fieldLabel : "辅助数量",
						width : 150,
						readOnly :true
					}]
				}]
			};
		var row30={
				layout:'column',
				items:[{
					height:30,
					layout:'form',
					items:[{
						id : 'proper_fzjldw',
						xtype : 'textfield',
						fieldLabel : "辅助计量单位",
						width : 150,
						readOnly :true
					}]
				},{
					layout:'form',
					items:[{
						id : 'proper_xmbh',
						xtype : 'textfield',
						fieldLabel : "项目编号",
						width : 150,
						readOnly :true
					}]
				}]
			};
		var row31={
				layout:'column',
				items:[{
					height:30,
					layout:'form',
					items:[{
						id : 'proper_xmbhms',
						xtype : 'textfield',
						fieldLabel : "项目编号描述",
						width : 150,
						readOnly :true
					}]
				},{
					layout:'form',
					items:[{
						id : 'proper_qcgcxmbh',
						xtype : 'textfield',
						fieldLabel : "期初工程项目编号",
						width : 150,
						readOnly :true
					}]
				}]
			};
		var row32={
				layout:'column',
				items:[{
					height:30,
					layout:'form',
					items:[{
						id : 'proper_zczyglbm',
						xtype : 'textfield',
						fieldLabel : "资产专业管理部门",
						width : 150,
						readOnly :true
					}]
				},{
					layout:'form',
					items:[{
						id : 'creationDate',
						xtype : 'textfield',
						fieldLabel : "创建时间",
						width : 150,
						readOnly :true
					}]
				}]
			};
		var row33={
				layout:'column',
				items:[{
					height:30,
					layout:'form',
					items:[{
						id : 'lastUpdateDate',
						xtype : 'textfield',
						fieldLabel : "最后更新时间",
						width : 150,
						readOnly :true
					}]
				}]
			};
		
		this.items =[{
			xtype : 'fieldset',
			id : 'fieldset',
			title : '资产信息',
			autoScroll:true,
			items : [row1,row2,row3,row4,row5,row6,row7,row8,row9,row10,row11,row12,row13,row14,row15,
			         row16,row17,row18,row19,row20,row21,row22,row23,row24,row25,row26,row27,row28,row29,row30,row31,row32,row33]
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
		com.increase.cen.property.seeProperty.superclass.initComponent.call(this);
	},

	// 关闭
	btnClose : function() {
		// 窗口隐藏
		Ext.getCmp(this.windowId).close();
	}
});
Ext.reg("seeProperty", com.increase.cen.property.seeProperty);
