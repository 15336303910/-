Ext.namespace("com.increase.cen.document");
var limit = null;
com.increase.cen.document.fiberpointModify = Ext.extend(Ext.form.FormPanel, {
	bodyStyle : "padding:5px;",
	frame : true,
	bodyBorder : false,
	labelAlign : "right",
	buttonAlign : 'center',
	autoScroll:true,
	labelWidth : 100,
	initComponent : function(config) {
		// 启动悬停提示
		Ext.QuickTips.init();
		// 提示的方式
		Ext.form.Field.prototype.msgTarget = 'qtip';
		// 验证光缆信息是否存在
		var userExist = false;
		var idIsExist = true;
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
					id : 'pcodemf',
					name : "pointInfoBean.pcode",
					xtype : 'textfield',
					fieldLabel : "端子编码",
					width : 150
				},{
					id : "eidmf",
					name : "pointInfoBean.eid",
					xtype : 'textfield',
					fieldLabel : "eid",
					hidden : true
				},{
					id : "pidmf",
					name : "pointInfoBean.pid",
					xtype : 'textfield',
					fieldLabel : "pid",
					hidden : true
				}]
			},{
				layout:'form',
				items:[{
					id : "pstatmf",
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
					id : "ptypemf",
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
					id : "directionmf",
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
					id : 'servnomf',
					name : "pointInfoBean.servno",
					xtype : 'textfield',
					fieldLabel : "业务编号",
					width : 150
				}]
			},{
				layout:'form',
				items:[{
					id : 'servtypemf',
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
					id : 'pservmf',
					name : "pointInfoBean.pserv",
					xtype : 'textfield',
					fieldLabel : "承载业务",
					width : 150
				}]
			},{
				layout:'form',
				items:[{
					id : 'fibnamemf',
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
					id : 'fiberstationnamemf',
					name : "pointInfoBean.fiberstationname",
					xtype : 'textfield',
					fieldLabel : "局向光纤名称",
					width : 150
				}]
			},{
				layout:'form',
				items:[{
					id : 'ofpnomf',
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
					id : 'ofpcodemf',
					name : "pointInfoBean.ofpcode",
					xtype : 'textfield',
					fieldLabel : "光路编码",
					width : 150
				}]
			},{
				layout:'form',
				items:[{
					id : 'ofpnamemf',
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
					id : 'routenamemf',
					name : "pointInfoBean.routename",
					xtype : 'textfield',
					fieldLabel : "光缆名称",
					width : 150
				}]
			},{
				layout:'form',
				items:[{
					id : 'cablenamemf',
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
					id : 'mflagmf',
					name : "pointInfoBean.mflag",
					xtype : 'textfield',
					fieldLabel : "同步状态",
					width : 150
				}]
			},{
				layout:'form',
				items:[{
					id : 'oppo_enamemf',
					name : "pointInfoBean.oppo_ename",
					xtype : 'textfield',
					fieldLabel : "对端设备名称",
					width : 150
				},{
					id : 'oppo_eidmf',
					name : "pointInfoBean.oppo_eidm",
					xtype : 'textfield',
					fieldLabel : "对端设备id",
					hidden:true
				},{
					id : 'oppo_pidmf',
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
					id : 'oppo_ecodemf',
					name : "pointInfoBean.oppo_ecode",
					xtype : 'textfield',
					fieldLabel : "对端设备编码",
					width : 150
				}]
			},{
				layout:'form',
				items:[{
					id : 'oppo_pcodemf',
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
					id : 'notemf',
					name : 'pointInfoBean.note',
					xtype : 'textarea',
					fieldLabel : '备注',
					width : 400
				}]
			}]
		}
		var row21={
			layout:'column',
			items:[{
				height:30,
				layout:'form',
				items:[{
					id : 'pcodemf2',
					name : "pointjumpBean.pcode",
					xtype : 'textfield',
					fieldLabel : "端子编码",
					width : 150
				},{
					id : "eidmf2",
					name : "pointjumpBean.eid",
					xtype : 'textfield',
					fieldLabel : "eid",
					hidden : true
				},{
					id : "pidmf2",
					name : "pointjumpBean.pid",
					xtype : 'textfield',
					fieldLabel : "pid",
					hidden : true
				}]
			},{
				layout:'form',
				items:[{
					id : "pstatmf2",
					name : "pointjumpBean.pstat",
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
		       		hiddenName:'pointjumpBean.pstat',//创建一个表单隐藏域来存储输入项的值
		       		editable:false//设置为false以阻止用户直接向该输入项输入文本
				}]
			}]
		}
    	var row22={
			layout:'column',
			items:[{
				height:30,
				layout:'form',
				items:[{
					id : "ptypemf2",
					name : "pointjumpBean.ptype",
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
		       		hiddenName:'pointjumpBean.ptype',//创建一个表单隐藏域来存储输入项的值
		       		editable:false//设置为false以阻止用户直接向该输入项输入文本
				}]
			},{
				layout:'form',
				items:[{
					id : "directionmf2",
					name : "pointjumpBean.direction",
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
		       		hiddenName:'pointjumpBean.direction',//创建一个表单隐藏域来存储输入项的值
		       		editable:false//设置为false以阻止用户直接向该输入项输入文本
				}]
			}]
		}
		var row23={
			layout:'column',
			items:[{
				height:30,
				layout:'form',
				items:[{
					id : 'servnomf2',
					name : "pointjumpBean.servno",
					xtype : 'textfield',
					fieldLabel : "业务编号",
					width : 150
				}]
			},{
				layout:'form',
				items:[{
					id : 'servtypemf2',
					name : "pointjumpBean.servtype",
					xtype : 'textfield',
					fieldLabel : "业务类型",
					width : 150
				}]
			}]
		}
		var row24={
			layout:'column',
			items:[{
				height:30,
				layout:'form',
				items:[{
					id : 'pservmf2',
					name : "pointjumpBean.pserv",
					xtype : 'textfield',
					fieldLabel : "承载业务",
					width : 150
				}]
			},{
				layout:'form',
				items:[{
					id : 'fibnamemf2',
					name : "pointjumpBean.fibname",
					xtype : 'textfield',
					fieldLabel : "光纤名称",
					width : 150
				}]
			}]
		}
		var row25={
			layout:'column',
			items:[{
				height:30,
				layout:'form',
				items:[{
					id : 'fiberstationnamemf2',
					name : "pointjumpBean.fiberstationname",
					xtype : 'textfield',
					fieldLabel : "局向光纤名称",
					width : 150
				}]
			},{
				layout:'form',
				items:[{
					id : 'ofpnomf2',
					name : "pointjumpBean.ofpno",
					xtype : 'textfield',
					fieldLabel : "光路代号",
					width : 150
				}]
			}]
		}
		var row26={
			layout:'column',
			items:[{
				height:30,
				layout:'form',
				items:[{
					id : 'ofpcodemf2',
					name : "pointjumpBean.ofpcode",
					xtype : 'textfield',
					fieldLabel : "光路编码",
					width : 150
				}]
			},{
				layout:'form',
				items:[{
					id : 'ofpnamemf2',
					name : "pointjumpBean.ofpname",
					xtype : 'textfield',
					fieldLabel : "光路名称",
					width : 150
				}]
			}]
		}
		var row27={
			layout:'column',
			items:[{
				height:30,
				layout:'form',
				items:[{
					id : 'routenamemf2',
					name : "pointjumpBean.routename",
					xtype : 'textfield',
					fieldLabel : "光缆名称",
					width : 150
				}]
			},{
				layout:'form',
				items:[{
					id : 'cablenamemf2',
					name : "pointjumpBean.cablename",
					xtype : 'textfield',
					fieldLabel : "光缆段名称",
					width : 150
				}]
			}]
		}
		var row28={
			layout:'column',
			items:[{
				height:30,
				layout:'form',
				items:[{
					id : 'mflagmf2',
					name : "pointjumpBean.mflag",
					xtype : 'textfield',
					fieldLabel : "同步状态",
					width : 150
				}]
			},{
				layout:'form',
				items:[{
					id : 'oppo_enamemf2',
					name : "pointjumpBean.oppo_ename",
					xtype : 'textfield',
					fieldLabel : "对端设备名称",
					width : 150
				},{
					id : 'oppo_eidmf2',
					name : "pointjumpBean.oppo_eid",
					xtype : 'textfield',
					fieldLabel : "对端设备id",
					hidden:true
				},{
					id : 'oppo_pidmf2',
					name : "pointjumpBean.oppo_pid",
					xtype : 'textfield',
					fieldLabel : "对端端子pid",
					hidden:true
				}]
			}]
		}
		var row29={
			layout:'column',
			items:[{
				height:30,
				layout:'form',
				items:[{
					id : 'oppo_ecodemf2',
					name : "pointjumpBean.oppo_ecode",
					xtype : 'textfield',
					fieldLabel : "对端设备编码",
					width : 150
				}]
			},{
				layout:'form',
				items:[{
					id : 'oppo_pcodemf2',
					name : "pointjumpBean.oppo_pcode",
					xtype : 'textfield',
					fieldLabel : "对端端子编码",
					width : 150
				}]
			}]
		}
		var row210={
			layout : 'column', // 从左往右布局
			items : [{
				columnWidth : .9, // 该列有整行中所占百分比
				height : 70,
				layout : 'form', // 从上往下布局
				items : [{
					id : 'notemf2',
					name : 'pointjumpBean.note',
					xtype : 'textarea',
					fieldLabel : '备注',
					width : 400
				}]
			}]
		}
		this.items = [{
			xtype : 'fieldset',
			id : 'fieldset1',
			title : '跳纤端子1',
			autoScroll:true,
			width:580,
			items : [row1,row2,row3,row4,row5,row6,row7,row8,row9,row10]
		},{
			xtype : 'fieldset',
			id : 'fieldset2',
			title : '跳纤端子2',
			autoScroll:true,
			width:580,
			items : [row21,row22,row23,row24,row25,row26,row27,row28,row29,row210]
		}];
		//this.items =[row1,row2,row3,row4,row5,row6,row7,row8,row9,row10];
		Ext.Ajax.request({
			url : 'point!getFiberPointInfo.action',
			method : 'Post',
			params : {
				"pointInfoBean.eid" : Ext.getDom("eid").value,
				"pointInfoBean.pid" : Ext.getDom("pid").value
			},
			success:function(response){
				var list = Ext.util.JSON.decode(response.responseText);
				if(list.length==0){
					Ext.getCmp("fieldset2").hide();
				}else if(list.length==1){
					var point=list[0];
					Ext.getCmp("eidmf").setValue(point.eid);
					Ext.getCmp("pidmf").setValue(point.pid);
					Ext.getCmp("pcodemf").setValue(point.pcode);
					Ext.getCmp("pstatmf").setValue(point.pstat);
					Ext.getCmp("ptypemf").setValue(point.ptype);
					Ext.getCmp("directionmf").setValue(point.direction);
					Ext.getCmp("servnomf").setValue(point.servno);
					Ext.getCmp("servtypemf").setValue(point.servtype);
					Ext.getCmp("pservmf").setValue(point.pserv);
					Ext.getCmp("fibnamemf").setValue(point.fibname);
					Ext.getCmp("fiberstationnamemf").setValue(point.fiberstationname);
					Ext.getCmp("ofpnomf").setValue(point.ofpno);
					Ext.getCmp("ofpcodemf").setValue(point.ofpcode);
					Ext.getCmp("ofpnamemf").setValue(point.ofpname);
					Ext.getCmp("routenamemf").setValue(point.routename);
					Ext.getCmp("cablenamemf").setValue(point.cablename);
					Ext.getCmp("mflagmf").setValue(point.mflag);
					Ext.getCmp("oppo_enamemf").setValue(point.oppo_ename);
					Ext.getCmp("oppo_ecodemf").setValue(point.oppo_ecode);
					Ext.getCmp("oppo_pcodemf").setValue(point.oppo_pcode);
					Ext.getCmp("notemf").setValue(point.note);
					Ext.getCmp("fieldset2").hide();
				}else if(list.length==2){
					for(var i=0;i<list.length;i++){
						var point=list[i];
						var d="";
						if(i!=0){
							d=(i+1)+"";
						}
						Ext.getCmp("eidmf"+d).setValue(point.eid);
						Ext.getCmp("pidmf"+d).setValue(point.pid);
						Ext.getCmp("pcodemf"+d).setValue(point.pcode);
						Ext.getCmp("pstatmf"+d).setValue(point.pstat);
						Ext.getCmp("ptypemf"+d).setValue(point.ptype);
						Ext.getCmp("directionmf"+d).setValue(point.direction);
						Ext.getCmp("servnomf"+d).setValue(point.servno);
						Ext.getCmp("servtypemf"+d).setValue(point.servtype);
						Ext.getCmp("pservmf"+d).setValue(point.pserv);
						Ext.getCmp("fibnamemf"+d).setValue(point.fibname);
						Ext.getCmp("fiberstationnamemf"+d).setValue(point.fiberstationname);
						Ext.getCmp("ofpnomf"+d).setValue(point.ofpno);
						Ext.getCmp("ofpcodemf"+d).setValue(point.ofpcode);
						Ext.getCmp("ofpnamemf"+d).setValue(point.ofpname);
						Ext.getCmp("routenamemf"+d).setValue(point.routename);
						Ext.getCmp("cablenamemf"+d).setValue(point.cablename);
						Ext.getCmp("mflagmf"+d).setValue(point.mflag);
						Ext.getCmp("oppo_enamemf"+d).setValue(point.oppo_ename);
						Ext.getCmp("oppo_ecodemf"+d).setValue(point.oppo_ecode);
						Ext.getCmp("oppo_pcodemf"+d).setValue(point.oppo_pcode);
						Ext.getCmp("notemf"+d).setValue(point.note);
					}
				}
				/*Ext.getCmp("eidmf").setValue(point.eid);
				Ext.getCmp("pidmf").setValue(point.pid);
				Ext.getCmp("pcodemf").setValue(point.pcode);
				Ext.getCmp("pstatmf").setValue(point.pstat);
				Ext.getCmp("ptypemf").setValue(point.ptype);
				Ext.getCmp("directionmf").setValue(point.direction);
				Ext.getCmp("servnomf").setValue(point.servno);
				Ext.getCmp("servtypemf").setValue(point.servtype);
				Ext.getCmp("pservmf").setValue(point.pserv);
				Ext.getCmp("fibnamemf").setValue(point.fibname);
				Ext.getCmp("fiberstationnamemf").setValue(point.fiberstationname);
				Ext.getCmp("ofpnomf").setValue(point.ofpno);
				Ext.getCmp("ofpcodemf").setValue(point.ofpcode);
				Ext.getCmp("ofpnamemf").setValue(point.ofpname);
				Ext.getCmp("routenamemf").setValue(point.routename);
				Ext.getCmp("cablenamemf").setValue(point.cablename);
				Ext.getCmp("mflagmf").setValue(point.mflag);
				Ext.getCmp("oppo_enamemf").setValue(point.oppo_ename);
				Ext.getCmp("oppo_ecodemf").setValue(point.oppo_ecode);
				Ext.getCmp("oppo_pcodemf").setValue(point.oppo_pcode);
				Ext.getCmp("notemf").setValue(point.note);*/
			}
		});
		// 按钮
		this.buttons = [{
			text : "保存",
			icon : "imgs/save_btn.png",
			cls : "x-btn-text-icon",
			tooltip : '修改端子信息',
			tooltipType : 'qtip',
			hidden:true,
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
		com.increase.cen.document.fiberpointModify.superclass.initComponent.call(this);
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
				var pid=Ext.getDom("pid").value;
				Ext.getCmp("fiberpointModify").getForm().reset();
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
		Ext.getCmp("fiberpointModify").getForm().reset();
		// 窗口隐藏
		Ext.getCmp('showPointModifyWindow').close();
		// 刷新表格
		//Ext.getCmp("poleLineGrid").getStore().reload();
	}
});
Ext.reg("fiberpointModify", com.increase.cen.document.fiberpointModify);
