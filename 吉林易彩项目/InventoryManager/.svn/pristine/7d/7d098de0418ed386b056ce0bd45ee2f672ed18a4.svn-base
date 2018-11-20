Ext.namespace("com.increase.cen.document");
var limit = null;
var urll="";
com.increase.cen.document.pointModify = Ext.extend(Ext.form.FormPanel, {
	bodyStyle : "padding:5px;",
	frame : true,
	bodyBorder : false,
	labelAlign : "right",
	buttonAlign : 'center',
	labelWidth : 100,
	initComponent : function(config) {
		// 启动悬停提示
		Ext.QuickTips.init();
		// 提示的方式
		Ext.form.Field.prototype.msgTarget = 'qtip';
		// 验证光缆信息是否存在
		var userExist = false;
		var idIsExist = true;
		//var urlflag=Ext.getDom("urlflag").value;
		// 光缆id
		this.stateStore = new Ext.data.SimpleStore({
    		fields : ['value', 'text'],
    		data : [['', ''],['1', '空闲'], ['2', '故障'], ['3', '可用'], ['4', '占用'], ['5', '待核查']]
    	})
		var row1={
			layout:'column',
			items:[{
				height:30,
				layout:'form',
				items:[{
					id : 'pcodem',
					name : "pointInfoBean.pcode",
					xtype : 'textfield',
					fieldLabel : "端子编码",
					width : 150
				},{
					id : "eidm",
					name : "pointInfoBean.eid",
					xtype : 'textfield',
					fieldLabel : "eid",
					hidden : true
				},{
					id : "pidm",
					name : "pointInfoBean.pid",
					xtype : 'textfield',
					fieldLabel : "pid",
					hidden : true
				}]
			},{
				layout:'form',
				items:[{
					id : "pstatm",
					name : "pointInfoBean.pstat",
					xtype : 'combo',
					typeAhead: true,
		        	triggerAction: 'all',
					fieldLabel : "端子状态",
					forceSelection: true,
		         	mode: 'local',
		        	width : 150,
		         	store: this.stateStore,
		         	valueField: 'value',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
		       		displayField: 'text',//下拉框显示内容
		       		hiddenName:'pointInfoBean.pstat',//创建一个表单隐藏域来存储输入项的值
		       		editable:false//设置为false以阻止用户直接向该输入项输入文本
				}]
			}]
		}
		this.typeStore = new Ext.data.SimpleStore({
    		fields : ['value', 'text'],
    		data : [['', ''],['1', '普通端子'], ['2', '分光器端子'], ['3', 'λ端子'], ['4', 'ONU上连端子'], ['5', 'ONU网口']]
    	})
    	this.directionStore = new Ext.data.SimpleStore({
    		fields : ['value', 'text'],
    		data : [['', ''],['1', '向局端'], ['2', '向用户端']]
    	})
    	var row2={
			layout:'column',
			items:[{
				height:30,
				layout:'form',
				items:[{
					id : "ptypem",
					name : "pointInfoBean.ptype",
					xtype : 'combo',
					typeAhead: true,
		        	triggerAction: 'all',
					fieldLabel : "端子类型",
					forceSelection: true,
		         	mode: 'local',
		        	width : 150,
		         	store: this.typeStore,
		         	valueField: 'value',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
		       		displayField: 'text',//下拉框显示内容
		       		hiddenName:'pointInfoBean.ptype',//创建一个表单隐藏域来存储输入项的值
		       		editable:false//设置为false以阻止用户直接向该输入项输入文本
				}]
			},{
				layout:'form',
				items:[{
					id : "directionm",
					name : "pointInfoBean.direction",
					xtype : 'combo',
					typeAhead: true,
		        	triggerAction: 'all',
					fieldLabel : "端子方向",
					forceSelection: true,
		         	mode: 'local',
		        	width : 150,
		         	store: this.directionStore,
		         	valueField: 'value',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
		       		displayField: 'text',//下拉框显示内容
		       		hiddenName:'pointInfoBean.direction',//创建一个表单隐藏域来存储输入项的值
		       		editable:false//设置为false以阻止用户直接向该输入项输入文本
				}]
			}]
		}
		var row3={
			layout:'column',
			items:[{
				height:30,
				layout:'form',
				items:[{
					id : 'servnom',
					name : "pointInfoBean.servno",
					xtype : 'textfield',
					fieldLabel : "业务编号",
					width : 150
				}]
			},{
				layout:'form',
				items:[{
					id : 'servtypem',
					name : "pointInfoBean.servtype",
					xtype : 'textfield',
					fieldLabel : "业务类型",
					width : 150
				}]
			}]
		}
		var row4={
			layout:'column',
			items:[{
				height:30,
				layout:'form',
				items:[{
					id : 'pservm',
					name : "pointInfoBean.pserv",
					xtype : 'textfield',
					fieldLabel : "承载业务",
					width : 150
				}]
			},{
				layout:'form',
				items:[{
					id : 'fibnamem',
					name : "pointInfoBean.fibname",
					xtype : 'textfield',
					fieldLabel : "光纤名称",
					width : 150
				}]
			}]
		}
		var row5={
			layout:'column',
			items:[{
				height:30,
				layout:'form',
				items:[{
					id : 'fiberstationnamem',
					name : "pointInfoBean.fiberstationname",
					xtype : 'textfield',
					fieldLabel : "局向光纤名称",
					width : 150
				}]
			},{
				layout:'form',
				items:[{
					id : 'ofpnom',
					name : "pointInfoBean.ofpno",
					xtype : 'textfield',
					fieldLabel : "光路代号",
					width : 150
				}]
			}]
		}
		var row6={
			layout:'column',
			items:[{
				height:30,
				layout:'form',
				items:[{
					id : 'ofpcodem',
					name : "pointInfoBean.ofpcode",
					xtype : 'textfield',
					fieldLabel : "光路编码",
					width : 150
				}]
			},{
				layout:'form',
				items:[{
					id : 'ofpnamem',
					name : "pointInfoBean.ofpname",
					xtype : 'textfield',
					fieldLabel : "光路名称",
					width : 150
				}]
			}]
		}
		var row7={
			layout:'column',
			items:[{
				height:30,
				layout:'form',
				items:[{
					id : 'routenamem',
					name : "pointInfoBean.routename",
					xtype : 'textfield',
					fieldLabel : "光缆名称",
					width : 150
				}]
			},{
				layout:'form',
				items:[{
					id : 'cablenamem',
					name : "pointInfoBean.cablename",
					xtype : 'textfield',
					fieldLabel : "光缆段名称",
					width : 150
				}]
			}]
		}
		var row8={
			layout:'column',
			items:[{
				height:30,
				layout:'form',
				items:[{
					id : 'mflagm',
					name : "pointInfoBean.mflag",
					xtype : 'textfield',
					fieldLabel : "同步状态",
					width : 150
				}]
			},{
				layout:'form',
				items:[{
					id : 'oppo_enamem',
					name : "pointInfoBean.oppo_ename",
					xtype : 'textfield',
					fieldLabel : "对端设备名称",
					width : 150
				},{
					id : 'oppo_eidm',
					name : "pointInfoBean.oppo_eidm",
					xtype : 'textfield',
					fieldLabel : "对端设备id",
					hidden:true
				},{
					id : 'oppo_pidm',
					name : "pointInfoBean.oppo_pidm",
					xtype : 'textfield',
					fieldLabel : "对端端子pid",
					hidden:true
				}]
			}]
		}
		var row9={
			layout:'column',
			items:[{
				height:30,
				layout:'form',
				items:[{
					id : 'oppo_ecodem',
					name : "pointInfoBean.oppo_ecode",
					xtype : 'textfield',
					fieldLabel : "对端设备编码",
					width : 150
				}]
			},{
				layout:'form',
				items:[{
					id : 'oppo_pcodem',
					name : "pointInfoBean.oppo_pcode",
					xtype : 'textfield',
					fieldLabel : "对端端子编码",
					width : 150
				}]
			}]
		}
		var row10={
			layout : 'column', // 从左往右布局
			items : [{
				columnWidth : .9, // 该列有整行中所占百分比
				height : 70,
				layout : 'form', // 从上往下布局
				items : [{
					id : 'notem',
					name : 'pointInfoBean.note',
					xtype : 'textarea',
					fieldLabel : '备注',
					width : 400
				}]
			}]
		}
		this.items =[row1,row2,row3,row4,row5,row6,row7,row8,row9,row10];
		/*if(urlflag=='1'){
			urll='point!getPointInfo.action';
		}else if(urlflag=='2'){
			urll='point!getOppoPointInfo.action';
		}else if(urlflag=='3'){
			urll='point!getFiberPointInfo.action';
		}*/
		Ext.Ajax.request({
			url : 'point!getPointInfo.action',
			method : 'Post',
			params : {
				"pointInfoBean.eid" : Ext.getDom("eid").value,
				"pointInfoBean.pid" : Ext.getDom("pid").value
			},
			success:function(response){
				var point = Ext.util.JSON.decode(response.responseText);
				Ext.getCmp("eidm").setValue(point.eid);
				Ext.getCmp("pidm").setValue(point.pid);
				Ext.getCmp("pcodem").setValue(point.pcode);
				Ext.getCmp("pstatm").setValue(point.pstat);
				Ext.getCmp("ptypem").setValue(point.ptype);
				Ext.getCmp("directionm").setValue(point.direction);
				Ext.getCmp("servnom").setValue(point.servno);
				Ext.getCmp("servtypem").setValue(point.servtype);
				Ext.getCmp("pservm").setValue(point.pserv);
				Ext.getCmp("fibnamem").setValue(point.fibname);
				Ext.getCmp("fiberstationnamem").setValue(point.fiberstationname);
				Ext.getCmp("ofpnom").setValue(point.ofpno);
				Ext.getCmp("ofpcodem").setValue(point.ofpcode);
				Ext.getCmp("ofpnamem").setValue(point.ofpname);
				Ext.getCmp("routenamem").setValue(point.routename);
				Ext.getCmp("cablenamem").setValue(point.cablename);
				Ext.getCmp("mflagm").setValue(point.mflag);
				Ext.getCmp("oppo_enamem").setValue(point.oppo_ename);
				Ext.getCmp("oppo_ecodem").setValue(point.oppo_ecode);
				Ext.getCmp("oppo_pcodem").setValue(point.oppo_pcode);
				Ext.getCmp("notem").setValue(point.note);
			}
		});
		// 按钮
		this.buttons = [{
			text : "保存",
			icon : "imgs/save_btn.png",
			cls : "x-btn-text-icon",
			tooltip : '修改杆路信息',
			tooltipType : 'qtip',
			handler : this.btnSubmit.createDelegate(this)
		}, {
			xtype : 'tbspacer'
		}, {
			xtype : 'tbspacer'
		}, {
			xtype : 'tbspacer'
		}, {
			text : "返回",
			icon : "imgs/back.png",
			cls : "x-btn-text-icon",
			tooltip : '关闭此窗口',
			tooltipType : 'qtip',
			handler : this.btnClose.createDelegate(this)
		}];
		// 重写initComponent superclass.initComponent.call(this);来调用父类的初始化方法.
		com.increase.cen.document.pointModify.superclass.initComponent.call(this);
	},
	// 提交事件
	btnSubmit : function submitForm() {
		if (!this.getForm().isValid()) {
			return;
		}
		var form=this.getForm();
		form.doAction("submit", {
			url : 'point!updatePointInfo.action',
			submitEmptyText : false,
			method : 'post',
			success : function(form, action) {
				Ext.Msg.show({
					title : '提示',
					width : 300,
					msg : '<img src="imgs/tip_success.png" align="center" hspace="30"/>'
							+ '修改端子信息成功!',
					buttons : {
						ok : "确定"
					}
				});
				// 窗口隐藏
				var pid=Ext.getCmp("pidm").getValue();
				Ext.getCmp("pointModify").getForm().reset();
				Ext.getCmp('showPointModifyWindow').close();
				showPointProps(pid);
				//Ext.getCmp("poleLineGrid").getStore().reload();
			},
			failure : function(form, action) {
				Ext.Msg.show({
					title : '提示',
					width : 300,
					msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
							+ '修改端子信息失败!',
					buttons : {
						ok : "确定"
					}
				});
			}
		});
	},
	// 关闭
	btnClose : function() {
		// 重置表单
		Ext.getCmp("pointModify").getForm().reset();
		// 窗口隐藏
		Ext.getCmp('showPointModifyWindow').close();
		// 刷新表格
		//Ext.getCmp("poleLineGrid").getStore().reload();
	}
});
Ext.reg("pointModify", com.increase.cen.document.pointModify);
