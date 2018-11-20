Ext.namespace("com.increase.cen.poleline");
var limit = null;
com.increase.cen.poleline.AddPipe = Ext.extend(Ext.form.FormPanel, {
	bodyStyle : "padding:5px;",
	frame : true,
	bodyBorder : false,
	labelAlign : "right",
	buttonAlign : 'center',
	labelWidth : 120,
	width : 600,
	height : 350,
	autoScroll : true,
	initComponent : function(config) {
		// 一页显示多少条数据
		limit = Ext.getDom("limit").value;
		// 启动悬停提示
		Ext.QuickTips.init();
		// 提示的方式
		Ext.form.Field.prototype.msgTarget = 'qtip';
		// 用光缆信息是否存在
		var idIsExist = true;
		// 是否批量增加
		this.pipeBatchAdd = new Ext.form.Checkbox({
			id : 'pipeBatchAdd',
			inputValue : 'yes',
			boxLabel : '批量录入',
			listeners : {
				'check' : function() {
					// 批量添加按钮
					var addBtnAdd = Ext.getCmp("addBtnAdd");
					// 保存按钮
					var addBtnSubmit = Ext.getCmp("addBtnSubmit");
					if (this.checked) {
						addBtnAdd.setVisible(true);
						addBtnSubmit.setVisible(false);
					} else {
						addBtnAdd.setVisible(false);
						addBtnSubmit.setVisible(true);
					}
				}
			}
		})
		this.directionStore = new Ext.data.SimpleStore({
    		fields : ['value', 'text'],
    		data : [['', ''],['1', '东'], ['2', '南'], ['3', '西'], ['4', '北']]
    	})
    	var pipeName=new Ext.form.TextField({
			id : 'addPipeName',
			name : "pipeInfoBean.pipeName",
			fieldLabel : "管道名称",
			allowBlank : false,
			blankText : '管道名称不能为空',
			emptyText : "请输入管道名称",
			width : 250
		});
		var alias=new Ext.form.TextField({
			id : 'addAlias',
			name : "pipeInfoBean.alias",
			fieldLabel : "管道别名",
			emptyText : "请输入管道别名",
			width : 250
		});
		this.levelStore=new Ext.data.SimpleStore({
			fields : ['value', 'text'],
	    	data : [['', ''],['1', '一干'], ['2', '二干'], ['3', '本地']]
		})
		var pipeLevel=new Ext.form.ComboBox({
    		id : "addPipeLevel",
			name : "pipeInfoBean.pipeLevel",
			typeAhead: true,
        	triggerAction: 'all',
			fieldLabel : "管道级别",
			emptyText : "请选择管道级别",
			blankText : '管道级别不能为空',
			forceSelection: true,
         	mode: 'local',
        	width : 250,
         	store: this.levelStore,
         	valueField: 'value',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
       		displayField: 'text',//下拉框显示内容
       		hiddenName:'pipeInfoBean.pipeLevel',//创建一个表单隐藏域来存储输入项的值
       		editable:false//设置为false以阻止用户直接向该输入项输入文本
    	});
    	var length=new Ext.form.TextField({
			id : 'addLength',
			name : "pipeInfoBean.length",
			fieldLabel : "管道长度（米）",
			emptyText : "请输入管道长度且只能为数字",
			width : 250,
			invalidText : '请输入正确的管道长度！',
			validationEvent : 'keydown',
			validationDelay : 100,
			validator : function(thisText) {
				if (thisText == "") {
					return true;
				}
				var reg=/^[0-9]+\.{0,1}\d*$/;
				if(!reg.test(thisText)){
					Ext.getCmp('addLength').markInvalid('填写格式不正确，请重新填写!');
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
					id : "addStartDeviceId",
					name : "pipeInfoBean.startDeviceId",
					width : 5,
					hidden : true
				}, {
					xtype : 'textfield',
					id : "addStartDeviceType",
					name : "pipeInfoBean.startDeviceType",
					width : 5,
					hidden : true
				},{
					xtype : 'compositefield',
					items : [{
						id : 'addStartDeviceName',
						name : 'pipeInfoBean.startDeviceName',
						xtype : 'textfield',
						emptyText : "请选择终止设施",
						fieldLabel : '起始设施名称',
						width : 250,
						readOnly : true
					}, {
						xtype : 'button',
						text : '选择井',
						handler : function() {
							showWellGrid_a();
						}
					},{
						xtype : 'button',
						text : '选择杆',
						handler : function() {
							showPoleGrid_a();
						}
					},{
						xtype : 'button',
						text : '清空',
						handler : function() {
							Ext.getCmp("addStartDeviceId").setValue("");
							Ext.getCmp("addStartDeviceName").setValue("");
							Ext.getCmp("addStartDeviceType").setValue("");
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
					id : "addEndDeviceId",
					name : "pipeInfoBean.endDeviceId",
					width : 5,
					hidden : true
				},{
					xtype : 'textfield',
					id : "addEndDeviceType",
					name : "pipeInfoBean.endDeviceType",
					width : 5,
					hidden : true
				}, {
					xtype : 'compositefield',
					items : [{
						id : 'addEndDeviceName',
						name : 'pipeInfoBean.endDeviceName',
						xtype : 'textfield',
						emptyText : "请选择终止设施",
						fieldLabel : '终止设施名称',
						width : 250,
						readOnly : true
					}, {
						xtype : 'button',
						text : '选择井',
						handler : function() {
							showWellGrid_z();
						}
					},{
						xtype : 'button',
						text : '选择杆',
						handler : function() {
							showPoleGrid_z();
						}
					},{
						xtype : 'button',
						text : '清空',
						handler : function() {
							Ext.getCmp("addEndDeviceId").setValue("");
							Ext.getCmp("addEndDeviceName").setValue("");
							Ext.getCmp("addEndDeviceType").setValue("");
						}
					}]
				}]
			}]
    	}
		var purchasedMaintenanceTime=new Ext.form.TextField({
			id : 'addPurchasedMaintenanceTime',
			name : "pipeInfoBean.purchasedMaintenanceTime",
			fieldLabel : "购买年限",
			emptyText : "请输入购买年限且只能为正整数",
			width : 250,
			invalidText : '请输入正确的购买年限！',
			validationEvent : 'keydown',
			validationDelay : 100,
			validator : function(thisText) {
				if (thisText == "") {
					return true;
				}
				var reg=/^[1-9]\d*$/;
				if(!reg.test(thisText)){
					Ext.getCmp('addPurchasedMaintenanceTime').markInvalid('填写格式不正确，请重新填写!');
					return false;
				}else{
					return true;
				}
			}
		});
		this.maintenanceStore = new Ext.data.SimpleStore({
    		fields : ['value', 'text'],
    		data : [['', ''],['1', '自维'], ['2', '代维']]
    	})
		var maintenanceModeEnumId=new Ext.form.ComboBox({
    		id : "addMaintenanceModeEnumId",
			name : "pipeInfoBean.maintenanceModeEnumId",
			typeAhead: true,
        	triggerAction: 'all',
			fieldLabel : "维护方式",
			emptyText : "请选择维护方式",
			blankText : '维护方式不能为空',
			forceSelection: true,
         	mode: 'local',
        	width : 250,
         	store: this.maintenanceStore,
         	valueField: 'value',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
       		displayField: 'text',//下拉框显示内容
       		hiddenName:'pipeInfoBean.maintenanceModeEnumId',//创建一个表单隐藏域来存储输入项的值
       		editable:false//设置为false以阻止用户直接向该输入项输入文本
    	});
    	var maintenanceOrgId=new Ext.form.TextField({
			id : 'addMaintenanceOrgId',
			name : "pipeInfoBean.maintenanceOrgId",
			fieldLabel : "维护单位",
			emptyText : "请输入维护单位",
			width : 250
		});
		var thirdPartyMaintenanceOrg=new Ext.form.TextField({
			id : 'addThirdPartyMaintenanceOrg',
			name : "pipeInfoBean.thirdPartyMaintenanceOrg",
			fieldLabel : "第三方维护单位",
			emptyText : "请输入第三方维护单位",
			width : 250
		});
		this.maintenanceTypeStore = new Ext.data.SimpleStore({
    		fields : ['value', 'text'],
    		data : [['', ''],['1', '硬件维修'], ['2', '紧急备件支持'], ['3', '软件补丁版本支持'], ['4', '技术支持服务（电话咨询、电话支持、远程支持、现场支持、网络预警、重要通信保障）'], ['5', '无']]
    	})
		var maintenanceTypeEnumId=new Ext.form.ComboBox({
    		id : "addMaintenanceTypeEnumId",
			name : "pipeInfoBean.maintenanceTypeEnumId",
			typeAhead: true,
        	triggerAction: 'all',
			fieldLabel : "维保类型",
			emptyText : "请选择维保类型",
			blankText : '维保类型不能为空',
			forceSelection: true,
         	mode: 'local',
        	width : 250,
         	store: this.maintenanceTypeStore,
         	valueField: 'value',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
       		displayField: 'text',//下拉框显示内容
       		hiddenName:'pipeInfoBean.maintenanceTypeEnumId',//创建一个表单隐藏域来存储输入项的值
       		editable:false//设置为false以阻止用户直接向该输入项输入文本
    	});
    	var projectName=new Ext.form.TextField({
			id : 'addProjectName',
			name : "pipeInfoBean.projectName",
			fieldLabel : "项目名称",
			emptyText : "请输入项目名称",
			width : 250
		});
		var projectCode=new Ext.form.TextField({
			id : 'addProjectCode',
			name : "pipeInfoBean.projectCode",
			fieldLabel : "项目编号",
			emptyText : "请输入项目编号",
			width : 250
		});
    	this.resourceLifePeriodEnumId = new Ext.data.SimpleStore({
    		fields : ['value', 'text'],
    		data : [['', ''],['1', '设计状态'], ['2', '工程建设期（入网带业务前）'], ['3', '工程可用期（已入网带业务）'], 
    		['4', '工程初验后试运行'], ['5', '工程终验后在网'], ['6', '退网状态']]
    	})
		var resourceLifePeriodEnumId=new Ext.form.ComboBox({
    		id : "addResourceLifePeriodEnumId",
			name : "pipeInfoBean.resourceLifePeriodEnumId",
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
       		hiddenName:'pipeInfoBean.resourceLifePeriodEnumId',//创建一个表单隐藏域来存储输入项的值
       		editable:false//设置为false以阻止用户直接向该输入项输入文本
    	});
    	var renewalExpirationDate={
			layout : 'column', // 从左往右布局
			items : [{ // 该列有整行中所占百分比
				height : 30,
				layout : 'form', // 从上往下布局
				items : [{
					id : 'addRenewalExpirationDate',
					name : 'pipeInfoBean.renewalExpirationDate',
					xtype : "datefield",
					format : 'Y-m-d',// 日期格式
					fieldLabel : '续保截止日期',
					emptyText : "请选择保修截止日期",
					width : 250
				}]
			}]
		}
		var projectWarrantyExpireDate={
			layout : 'column', // 从左往右布局
			items : [{ // 该列有整行中所占百分比
				height : 30,
				layout : 'form', // 从上往下布局
				items : [{
					id : 'addProjectWarrantyExpireDate',
					name : 'pipeInfoBean.projectWarrantyExpireDate',
					xtype : "datefield",
					format : 'Y-m-d',// 日期格式
					fieldLabel : '工程保修截止日期',
					emptyText : "请选择保修截止日期",
					width : 250
				}]
			}]
		}
    	var comboxWithTree = new Ext.form.ComboBox({
			store : new Ext.data.SimpleStore({
				fields : [],
				data : [[]]
			}),
			id : 'addcomboxWithTree',
			fieldLabel : '所属局站',
			emptyText : "请选择所属局站",
			editable : false,
			mode : 'local',
			triggerAction : 'all',
			width : 250,
			autoListWidth : true,
			autoListHeight : true,
			listWidth : 150,
			tpl : "<div id='tree'></div>",
			selectedClass : '',
			value : '',
			onSelect : Ext.emptyFn,
			onViewClick : function(doFocus) {
				var index = this.view.getSelectedIndexes()[0], 
				s = this.store, 
				r = s.getAt(index);
				if (r) {
					this.onSelect(r, index);
				} else if (s.getCount() === 0) {
					// this.collapse();
				}
				if (doFocus !== false) {
					// this.el.focus();
				}
			}
		});
		var opRoot = new Ext.tree.AsyncTreeNode({
			id : "rootNode",
			text : 'root',
			leaf : false
		});
		// 树容器
		var opTree = new Ext.tree.TreePanel({
			anchor : '95%',
			frame : false,
			width : 250,
			height : 200,
			animate : false,
			rootVisible : true,
			autoScroll : true,
			dataUrl : 'workorder!getDomianTree.action',
			root : opRoot,
			rootVisible : false
		});
		opTree.on('click', function(node, event) {
			// event.stopEvent();
			comboxWithTree.setValue(node.attributes.text);
			Ext.getCmp("addareano").setValue(node.attributes.domainCode);
			Ext.getCmp("addareaname").setValue(node.attributes.text);
			//Ext.getCmp("did").setValue('');
			comboxWithTree.collapse();
		});
	
		comboxWithTree.on('expand', function() {
			opTree.render('tree');
		});
	
		var areano = {
			name : 'pipeInfoBean.areano',
			id : 'addareano',
			xtype : 'textfield',
			fieldLabel : '所属局站',
			hidden : true
		};
		
		var areaname = {
			name : 'pipeInfoBean.areaname',
			id : 'addareaname',
			xtype : 'textfield',
			fieldLabel : '所属局站',
			hidden : true
		};
    	this.area = {
			layout : 'form', // 从左往右布局
			items : [
					areano,areaname, comboxWithTree
				]
		};
		var row9={
			layout : 'column', // 从左往右布局
			items : [this.area]
		}
		this.items = [{
			xtype : 'panel',
			bodyStyle : 'padding-left:78%;',
			items : [this.pipeBatchAdd]
		}, {
			xtype : 'fieldset',
			title : '管道信息',
			collapsible : true,
			items : [pipeName,alias,pipeLevel,length,startDeviceName,endDeviceName,purchasedMaintenanceTime,
			maintenanceModeEnumId,maintenanceOrgId,thirdPartyMaintenanceOrg,
			maintenanceTypeEnumId,projectName,projectCode,resourceLifePeriodEnumId,
			renewalExpirationDate,projectWarrantyExpireDate,row9]
		}];

		// 按钮
		this.buttons = [{
			id : 'addBtnSubmit',
			text : "保存",
			hidden : false,
			icon : "imgs/save_btn.png",
			cls : "x-btn-text-icon",
			tooltip : '保存管道信息',
			tooltipType : 'qtip',
			handler : this.addBtnSubmit.createDelegate(this)
		}, {
			id : 'addBtnAdd',
			hidden : true,
			text : '继续添加',
			tooltip : '继续添加管道信息',
			tooltipType : 'qtip',
			icon : "imgs/form_add.png",
			cls : "x-btn-text-icon",
			handler : this.addBtnAdd.createDelegate(this)
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
		com.increase.cen.poleline.AddPipe.superclass.initComponent.call(this);
	},
	// 保存按钮事件
	addBtnSubmit : function() {
		if (!this.getForm().isValid()) {
			return;
		}
		var form=this.getForm();
		var type1=Ext.getCmp("addStartDeviceType").getValue();
		var type2=Ext.getCmp("addEndDeviceType").getValue();
		if(type1==type2&&type1=="2"){
			Ext.Msg.show({
				title : '提示',
				width : 300,
				msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'+'起始和终止设施不能同时选择电杆!',
				buttons : {
					ok : "确定"
				}
			});
			return;
		}
		//验证电杆名称是否重复
		var pipename=Ext.getCmp("addPipeName").getValue();
		Ext.Ajax.request({
			url : 'pipe!checkPipeNameJSON.action',
			method : 'post',
			params : {
				'pipeName' : pipename
			},
			success : function(response) {
				var json = Ext.util.JSON.decode(response.responseText);
				if (json.success == false) {
					Ext.Msg.show({
						title : '提示',
						width : 300,
						msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'+'管道名称重复请重新输入!',
						buttons : {
							ok : "确定"
						}
					});
				}else{
					form.doAction("submit", {
						url : 'pipe!doadd.action',
						method : 'post',
						submitEmptyText : false,
						success : function(form, action) {
							Ext.Msg.show({
								title : '提示',
								width : 300,
								msg : '<img src="imgs/tip_success.png" align="center" hspace="30" />'
										+ '添加管道信息成功!',
								buttons : {
									ok : "确定"
								}
							});
							Ext.getCmp("addPipe").getForm().reset();// 重置表单
							Ext.getCmp('addPipeWindow').close();// 窗口隐藏
							Ext.getCmp('pipeGrid').getStore().load({
								params : {
									start : 0,
									limit : limit
								}
							});// 表格加载数据
							Ext.getCmp('pipeGrid').getView().refresh();// 表格刷新
						},
						failure : function(form, action) {
							Ext.Msg.show({
								title : '提示',
								width : 300,
								msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
										+ '添加管道信息失败',
								buttons : {
									ok : "确定"
								}
							});
						}
					});
				}
			}				
		});
	},
	// 返回操作
	btnClose : function() {
		// 重置表单
		Ext.getCmp("addPipe").getForm().reset();// 重置表单
		Ext.getCmp('addPipeWindow').close();// 窗口隐藏
		Ext.getCmp('pipeGrid').getStore().load({
			params : {
				start : 0,
				limit : limit
			}
		});// 表格加载数据
	},
	// 批量新增
	addBtnAdd : function() {
		// 批量添加复选框无效
		//Ext.getCmp("routeBatchAdd").disable();
		if (!this.getForm().isValid()) {
			return;
		}
		var form=this.getForm();
		var type1=Ext.getCmp("addStartDeviceType").getValue();
		var type2=Ext.getCmp("addEndDeviceType").getValue();
		if(type1==type2&&type1=="2"){
			Ext.Msg.show({
				title : '提示',
				width : 300,
				msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'+'起始和终止设施不能同时选择电杆!',
				buttons : {
					ok : "确定"
				}
			});
			return;
		}
		//验证井名称是否重复
		var pipename=Ext.getCmp("addPipeName").getValue();
		Ext.Ajax.request({
			url : 'pipe!checkPipeNameJSON.action',
			method : 'post',
			params : {
				'pipeName' : pipename
			},
			success : function(response) {
				var json = Ext.util.JSON.decode(response.responseText);
				if (json.success == false) {
					Ext.Msg.show({
						title : '提示',
						width : 300,
						msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'+'管道名称重复请重新输入!',
						buttons : {
							ok : "确定"
						}
					});
				}else{
					form.doAction("submit", {
						url : 'pipe!doadd.action',
						method : 'post',
						submitEmptyText : false,
						success : function(form, action) {
							Ext.Msg.show({
								title : '提示',
								width : 300,
								msg : '<img src="imgs/tip_success.png" align="center" hspace="30"/>'
										+ '添加管道信息成功!',
								buttons : {
									ok : "确定"
								}
							});
							// 重置表单
							Ext.getCmp("addPipe").getForm().reset();
							// 复选框选中
							Ext.getCmp("pipeBatchAdd").setValue(true);
						},
						failure : function(form, action) {
							Ext.Msg.show({
								title : '提示',
								width : 300,
								msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
										+ '添加管道信息失败',
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
});
Ext.reg("addPipe", com.increase.cen.poleline.AddPipe);
