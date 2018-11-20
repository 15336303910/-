Ext.namespace("com.increase.cen.poleline");
var limit = null;
com.increase.cen.poleline.ModifyPoleLS = Ext.extend(Ext.form.FormPanel, {
	bodyStyle : "padding:5px;",
	frame : true,
	bodyBorder : false,
	labelAlign : "right",
	buttonAlign : 'center',
	labelWidth : 120,
	width : 550,
	height : 300,
	initComponent : function(config) {
		// 获取一页的记录数
		limit = Ext.getDom("limit").value;
		// 启动悬停提示
		Ext.QuickTips.init();
		// 提示的方式
		Ext.form.Field.prototype.msgTarget = 'qtip';
		// 验证光缆信息是否存在
		var userExist = false;
		var idIsExist = true;
		// 光缆id
		var poleLSId = new Ext.form.TextField({
			id : "modifyPoleLineSegmentId",
			name : "polelineSegmentInfoBean.poleLineSegmentId",
			fieldLabel : "杆路段ID",
			width : 250,
			hidden : true
		});
		var oldPLSName=new Ext.form.TextField({
			id : 'modifyPLSName',
			name : "polelineSegmentInfoBean.poleLineSegmentName",
			fieldLabel : "杆路段名称",
			hidden : true
		});
		var poleLSName=new Ext.form.TextField({
			id : 'modifyPoleLineSegmentName',
			name : "polelineSegmentInfoBean.poleLineSegmentName",
			fieldLabel : "杆路段名称",
			allowBlank : false,
			blankText : '杆路段名称不能为空',
			emptyText : "请输入杆路段名称",
			readOnly:true,
			width : 250
		});
		var poleLineName = {
			layout : 'column', // 从左往右布局
			items : [{
				columnWidth : 1.0, // 该列有整行中所占百分比
				height : 30,
				layout : 'form', // 从上往下布局
				items : [{
					xtype : 'textfield',
					id : "modifyPoleLineId",
					name : "polelineSegmentInfoBean.poleLineId",
					width : 5,
					hidden : true
				}, {
					xtype : 'compositefield',
					items : [{
						id : 'modifyPoleLineName',
						name : 'polelineSegmentInfoBean.poleLineName',
						xtype : 'textfield',
						emptyText : "请选择所属杆路",
						fieldLabel : '所属杆路名称',
						allowBlank : false,
						blankText : '所属杆路名称不能为空',
						width : 250,
						readOnly : true
					}, {
						xtype : 'button',
						text : '选择插入',
						width : 50,
						handler : function() {
							showPoleLineGrid();
						}
					}, {
						xtype : 'button',
						text : '清空',
						width : 25,
						style : 'margin-left:5px',
						handler : function() {
							Ext.getCmp("modifyPoleLineId").setValue("");
							Ext.getCmp("modifyPoleLineName").setValue("");
						}
					}]
				}]
			}]
    	}
		var poleLSCode=new Ext.form.TextField({
			id : 'modifyPoleLineSegmentCode',
			name : "polelineSegmentInfoBean.poleLineSegmentCode",
			fieldLabel : "杆路段编码",
			emptyText : "请输入杆路段编码",
			width : 250
		});
    	this.statusStore = new Ext.data.SimpleStore({
    		fields : ['value', 'text'],
    		data : [['', ''],['1', '空闲'], ['2', '在用'], ['3', '出租']]
    	})
		var status=new Ext.form.ComboBox({
    		id : "modifyStatus",
			name : "polelineSegmentInfoBean.status",
			typeAhead: true,
        	triggerAction: 'all',
			fieldLabel : "状态",
			emptyText : "请选择状态",
			blankText : '状态不能为空',
			forceSelection: true,
         	mode: 'local',
        	width : 250,
         	store: this.statusStore,
         	valueField: 'value',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
       		displayField: 'text',//下拉框显示内容
       		hiddenName:'polelineSegmentInfoBean.status',//创建一个表单隐藏域来存储输入项的值
       		editable:false//设置为false以阻止用户直接向该输入项输入文本
    	});
    	var poleLSLength=new Ext.form.TextField({
			id : 'modifyPoleLineSegmentLength',
			name : "polelineSegmentInfoBean.poleLineSegmentLength",
			fieldLabel : "杆路段长度（米）",
			emptyText : "请输入杆路段长度且只能为数字",
			width : 250,
			invalidText : '请输入正确的杆路段长度！',
			validationEvent : 'keydown',
			validationDelay : 100,
			validator : function(thisText) {
				if (thisText == "") {
					return true;
				}
				var reg=/^[0-9]+\.{0,1}\d*$/;
				if(!reg.test(thisText)){
					Ext.getCmp('modifyPoleLineSegmentLength').markInvalid('填写格式不正确，请重新填写!');
					return false;
				}else{
					return true;
				}
			}
		});
		//起始设施名称
		var startDeviceName = {
			layout : 'column', // 从左往右布局
			items : [{
				columnWidth : 1.0, // 该列有整行中所占百分比
				height : 30,
				layout : 'form', // 从上往下布局
				items : [{
					xtype : 'textfield',
					id : "modifyStartDeviceId",
					name : "polelineSegmentInfoBean.startDeviceId",
					width : 5,
					hidden : true
				}, {
					xtype : 'compositefield',
					items : [{
						id : 'modifyStartDeviceName',
						name : 'polelineSegmentInfoBean.startDeviceName',
						xtype : 'textfield',
						emptyText : "请选择终止设施",
						fieldLabel : '起始设施名称',
						allowBlank : false,
						blankText : '起始设施名称不能为空',
						width : 250,
						readOnly : true
					}, {
						xtype : 'button',
						text : '选择插入',
						width : 50,
						handler : function() {
							showPoleGrid_aa();
						}
					}, {
						xtype : 'button',
						text : '清空',
						width : 25,
						style : 'margin-left:5px',
						handler : function() {
							//var sname=Ext.getCmp("modifyStartDeviceName").getValue();
							//var plsname=Ext.getCmp("modifyPoleLineSegmentName").getValue();
							//plsname=plsname.substring(sname.length+1,plsname.length);
							//Ext.getCmp("modifyPoleLineSegmentName").setValue(plsname);
							Ext.getCmp("modifyStartDeviceId").setValue("");
							Ext.getCmp("modifyStartDeviceName").setValue("");
						}
					}]
				}]
			}]
    	}
    	//终止设施名称
		var endDeviceName = {
			layout : 'column', // 从左往右布局
			items : [{
				columnWidth : 1.0, // 该列有整行中所占百分比
				height : 30,
				layout : 'form', // 从上往下布局
				items : [{
					xtype : 'textfield',
					id : "modifyEndDeviceId",
					name : "polelineSegmentInfoBean.endDeviceId",
					width : 5,
					hidden : true
				}, {
					xtype : 'compositefield',
					items : [{
						id : 'modifyEndDeviceName',
						name : 'polelineSegmentInfoBean.endDeviceName',
						xtype : 'textfield',
						emptyText : "请选择终止设施",
						fieldLabel : '终止设施名称',
						allowBlank : false,
						blankText : '终止设施名称不能为空',
						width : 250,
						readOnly : true
					}, {
						xtype : 'button',
						text : '选择插入',
						width : 50,
						handler : function() {
							showPoleGrid_zz();
						}
					}, {
						xtype : 'button',
						text : '清空',
						width : 25,
						style : 'margin-left:5px',
						handler : function() {
							//var ename=Ext.getCmp("modifyEndDeviceName").getValue();
							//var plsname=Ext.getCmp("modifyPoleLineSegmentName").getValue();
							//plsname=plsname.substring(0,plsname.length-ename.length);
							//Ext.getCmp("modifyPoleLineSegmentName").setValue(plsname);
							Ext.getCmp("modifyEndDeviceId").setValue("");
							Ext.getCmp("modifyEndDeviceName").setValue("");
						}
					}]
				}]
			}]
    	}
		this.conStore = new Ext.data.SimpleStore({
    		fields : ['value', 'text'],
    		data : [['', ''],['1', '共建'], ['2', '共享'],['3', '自建自用（不包括自建预留）'], ['4', '自建预留']]
    	})
		var constructionSharingEnumId=new Ext.form.ComboBox({
    		id : "modifyConstructionSharingEnumId",
			name : "polelineSegmentInfoBean.constructionSharingEnumId",
			typeAhead: true,
        	triggerAction: 'all',
			fieldLabel : "共建/共享类型",
			emptyText : "请选择共建/共享类型",
			blankText : '共建/共享类型不能为空',
			forceSelection: true,
         	mode: 'local',
        	width : 250,
         	store: this.conStore,
         	valueField: 'value',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
       		displayField: 'text',//下拉框显示内容
       		hiddenName:'polelineSegmentInfoBean.constructionSharingEnumId',//创建一个表单隐藏域来存储输入项的值
       		editable:false//设置为false以阻止用户直接向该输入项输入文本
    	});
    	var constructionSharingOrg=new Ext.form.TextField({
			id : 'modifyConstructionSharingOrg',
			name : "polelineSegmentInfoBean.constructionSharingOrg",
			fieldLabel : "共享方",
			emptyText : "请输入共享方",
			width : 250
		});
		this.sharingStore = new Ext.data.SimpleStore({
    		fields : ['value', 'text'],
    		data : [['', ''],['1', '我方共享他方'], ['2', '他方共享我方']]
    	})
		var sharingTypeEnumId=new Ext.form.ComboBox({
    		id : "modifySharingTypeEnumId",
			name : "polelineSegmentInfoBean.sharingTypeEnumId",
			typeAhead: true,
        	triggerAction: 'all',
			fieldLabel : "共享类型",
			emptyText : "请选择共享类型",
			blankText : '共享类型不能为空',
			forceSelection: true,
         	mode: 'local',
        	width : 250,
         	store: this.sharingStore,
         	valueField: 'value',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
       		displayField: 'text',//下拉框显示内容
       		hiddenName:'polelineSegmentInfoBean.sharingTypeEnumId',//创建一个表单隐藏域来存储输入项的值
       		editable:false//设置为false以阻止用户直接向该输入项输入文本
    	});
    	this.resourceLifePeriodEnumId = new Ext.data.SimpleStore({
    		fields : ['value', 'text'],
    		data : [['', ''],['1', '设计状态'], ['2', '工程建设期（入网带业务前）'], ['3', '工程可用期（已入网带业务）'], 
    		['4', '工程初验后试运行'], ['5', '工程终验后在网'], ['6', '退网状态']]
    	})
		var resourceLifePeriodEnumId=new Ext.form.ComboBox({
    		id : "modifyResourceLifePeriodEnumId",
			name : "polelineSegmentInfoBean.resourceLifePeriodEnumId",
			typeAhead: true,
        	triggerAction: 'all',
			fieldLabel : "资源生命周期",
			emptyText : "请选择资源生命周期",
			blankText : '资源生命周期不能为空',
			forceSelection: true,
         	mode: 'local',
        	width : 250,
         	store: this.resourceLifePeriodEnumId,
         	valueField: 'value',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
       		displayField: 'text',//下拉框显示内容
       		hiddenName:'polelineSegmentInfoBean.resourceLifePeriodEnumId',//创建一个表单隐藏域来存储输入项的值
       		editable:false//设置为false以阻止用户直接向该输入项输入文本
    	});
    	var areano={
    		layout:'form',
    		items:[{
    			id : 'modifyAreano',
				name : "polelineSegmentInfoBean.areano",
				xtype:'textfield',
				fieldLabel : "所属局站",
				emptyText : "请输入所属局站",
				hidden:true,
				width : 250
    		},{
    			id : 'modifyAreaname',
				name : "polelineSegmentInfoBean.areaname",
				fieldLabel : "所属局站",
				emptyText : "请输入所属局站",
				xtype:'textfield',
				readOnly:true,
				width : 250
    		}]
    	}
		this.items = [{
			xtype : 'fieldset',
			title : '杆路段基本信息',
			collapsible : true,
			items : [poleLSId,poleLSName,poleLineName,poleLSCode,status,poleLSLength,startDeviceName,endDeviceName,
			constructionSharingEnumId,constructionSharingOrg,sharingTypeEnumId,resourceLifePeriodEnumId,areano]
		}];
		// 按钮
		this.buttons = [{
			text : "保存",
			icon : "imgs/save_btn.png",
			cls : "x-btn-text-icon",
			tooltip : '修改杆路段信息',
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
		com.increase.cen.poleline.ModifyPoleLS.superclass.initComponent.call(this);
	},
	// 提交事件
	btnSubmit : function submitForm() {
		if (!this.getForm().isValid()) {
			return;
		}
		var form=this.getForm();
		//验证杆路段名称是否重复
		var oldPLSName=Ext.getCmp("modifyPLSName").getValue();
		var polelsname=Ext.getCmp("modifyPoleLineSegmentName").getValue();
		if(oldPLSName==polelsname){
			form.doAction("submit", {
						url : 'poleline!doeditpls.action',
						submitEmptyText : false,
						method : 'post',
						success : function(form, action) {
							Ext.Msg.show({
								title : '提示',
								width : 300,
								msg : '<img src="imgs/tip_success.png" align="center" hspace="30"/>'
										+ '修改杆路段信息成功!',
								buttons : {
									ok : "确定"
								}
							});
							// 窗口隐藏
							Ext.getCmp('modifyPoleLSWindow').close();
							Ext.getCmp("poleLSGrid").getStore().reload();
						},
						failure : function(form, action) {
							Ext.Msg.show({
								title : '提示',
								width : 300,
								msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
										+ '修改杆路段信息失败!',
								buttons : {
									ok : "确定"
								}
							});
						}
					});
		}else{
		Ext.Ajax.request({
			url : 'poleline!checkpolelinesegmentJSON.action',
			method : 'post',
			params : {
				'poleLineSegmentName' : polelsname
			},
			success : function(response) {
				var json = Ext.util.JSON.decode(response.responseText);
				if (json.success == false) {
					Ext.Msg.show({
						title : '提示',
						width : 300,
						msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'+'杆路段名称重复请重新输入!',
						buttons : {
							ok : "确定"
						}
					});
				}else{
					form.doAction("submit", {
						url : 'poleline!doeditpls.action',
						submitEmptyText : false,
						method : 'post',
						success : function(form, action) {
							Ext.Msg.show({
								title : '提示',
								width : 300,
								msg : '<img src="imgs/tip_success.png" align="center" hspace="30"/>'
										+ '修改杆路段信息成功!',
								buttons : {
									ok : "确定"
								}
							});
							// 窗口隐藏
							Ext.getCmp('modifyPoleLSWindow').close();
							Ext.getCmp("poleLSGrid").getStore().reload();
						},
						failure : function(form, action) {
							Ext.Msg.show({
								title : '提示',
								width : 300,
								msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
										+ '修改杆路段信息失败!',
								buttons : {
									ok : "确定"
								}
							});
						}
					});
				}
			}
		});
		}
	},
	// 关闭
	btnClose : function() {
		// 重置表单
		Ext.getCmp("modifyPoleLS").getForm().reset();
		// 窗口隐藏
		Ext.getCmp('modifyPoleLSWindow').close();
		// 刷新表格
		Ext.getCmp("poleLSGrid").getStore().reload();
	}
});
Ext.reg("modifyPoleLS", com.increase.cen.poleline.ModifyPoleLS);