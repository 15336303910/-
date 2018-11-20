Ext.namespace("com.increase.cen.equt");
var limit = null;
com.increase.cen.equt.seeEqut = Ext.extend(Ext.form.FormPanel, {
	bodyStyle : "padding:5px;",
	frame : true,
	bodyBorder : false,
	labelAlign : "right",
	buttonAlign : 'center',
	height:300,
	labelWidth : 80,
	equt : null,
	windowId: null,
	initComponent : function(config) {
		// 启动悬停提示
		Ext.QuickTips.init();
		var ename=this.equt.ename;
		var ecode=this.equt.ecode;
		var eaddr=this.equt.eaddr;
		var	etype=this.equt.etype;
		etype=getEtypeStr(etype);
		var estat=this.equt.estat;
		estat=getEstatStr(estat);
		var mp=this.equt.mp;
		var mtime=this.equt.mtime;
		mtime=time(mtime);
		var lon=this.equt.lon;
		var lat=this.equt.lat;
		var prlusername=this.equt.prlusername;
		var row1={
			layout:'column',
			items:[{
				height:30,
				layout:'form',
				items:[{
					id : 'ecode',
					xtype : 'textfield',
					fieldLabel : "设备编码",
					width : 150,
					readOnly :true,
					value:ecode
				}]
			},{
				layout:'form',
				items:[{
					id : 'enameb',
					xtype : 'textfield',
					fieldLabel : "设备名称",
					width : 150,
					readOnly :true,
					value : ename
				}]
			}]
		};
    	var row2={
			layout:'column',
			items:[{
				height:30,
				layout:'form',
				items:[{
					id : "etypeb",
					xtype : 'textfield',
					readOnly :true,
					fieldLabel : "设备类型",
		        	width : 150,
		        	value : etype
				}]
			},{
				layout:'form',
				items:[{
					id : "estatb",
					name : "equtInfoBean.estat",
					xtype : 'textfield',
					fieldLabel : "设备状态",
		        	width : 150,
		        	readOnly :true,
		        	value:estat
				}]
			}]
		};
		var row3={
			layout:'column',
			items:[{
				height:30,
				layout:'form',
				items:[{
					id : 'eaddrb',
					xtype : 'textfield',
					fieldLabel : "地址信息",
					width : 150,
					readOnly :true,
					value:eaddr
				}]
			},{
				layout:'form',
				items:[{
					id : 'mpb',
					name : "equtInfoBean.mp",
					xtype : 'textfield',
					fieldLabel : "维护人",
					readOnly :true,
					width : 150,
					value:mp
				}]
			}]
		};
		var row4={
			layout:'column',
			items:[{
				height:30,
				layout:'form',
				items:[{
					id : 'lonb',
					name : "equtInfoBean.lon",
					xtype : 'textfield',
					fieldLabel : "经度",
					readOnly :true,
					width : 150,
					value:lon
				}]
			},{
				layout:'form',
				items:[{
					id : 'latb',
					name : "equtInfoBean.lat",
					xtype : 'textfield',
					fieldLabel : "纬度",
					readOnly :true,
					width : 150,
					value:lat
				}]
			}]
		};
		var row5={
			layout:'column',
			items:[{
				height:30,
				layout:'form',
				items:[{
					id : 'mtimeb',
					name : 'equtInfoBean.mtime',
					xtype : 'textfield',
					fieldLabel : '维护时间',
					readOnly :true,
					width : 150,
					value:mtime
				}]
			},{
				layout:'form',
				items:[{
					id:'prlrealnameb', 
					name : "equtInfoBean.prlrealname",
	           	 	fieldLabel : "负责人",
	           		xtype :'textfield',
	           		readOnly :true,
	        		width : 150,
	        		value:prlusername
				}]
			}]
		};
		
		this.items =[{
			xtype : 'fieldset',
			id : 'fieldset',
			title : '设备信息',
			autoScroll:true,
			items : [row1,row2,row3,row4,row5]
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
		com.increase.cen.equt.seeEqut.superclass.initComponent.call(this);
	},

	// 关闭
	btnClose : function() {
		// 窗口隐藏
		Ext.getCmp(this.windowId).close();
		// 刷新表格
		//Ext.getCmp("poleLineGrid").getStore().reload();
	}
});
Ext.reg("seeEqut", com.increase.cen.equt.seeEqut);
