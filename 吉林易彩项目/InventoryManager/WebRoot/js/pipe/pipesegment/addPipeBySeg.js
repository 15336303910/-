Ext.namespace("com.increase.cen.poleline");
var limit = null;
//var psgrid = new Ext.grid.GridPanel({});
//document.write("<script src='js/pipe/pipesegment/pipeseg.js' type='text/javascript'></script>");
//var pageSize = parseInt(Ext.getDom("pageSize").value);
com.increase.cen.poleline.addPipeBySeg = Ext.extend(Ext.form.FormPanel, {
	bodyStyle : "padding:5px;",
	frame : true,
	bodyBorder : false,
	labelAlign : "right",
	buttonAlign : 'center',
	labelWidth : 130,
	autoScroll : true,
	width : 900,
	height : 500,
	initComponent : function(config) {
		// 一页显示多少条数据
		limit = Ext.getDom("limit").value;
		// 启动悬停提示
		Ext.QuickTips.init();
		// 提示的方式
		Ext.form.Field.prototype.msgTarget = 'qtip';
		// 用户信息是否存在
		var idIsExist = true;
		var proxy = new Ext.data.HttpProxy({
			url : "pipe!searchPipeSegmentlist.action"
			
		});
		//数据结构
		var route = Ext.data.Record.create([
			{name : 'pipeSegmentId',type : 'string',mapping : 'pipeSegmentId',hidden : true}, 
			{name : 'pipeSegmentName',type : 'string',mapping : 'pipeSegmentName'}
		]);
		// Reader以提供的Ext.data.Record 构造器中的映射为基础，从JSON响应创建一个Ext.data.Record 对象数组
		var reader = new Ext.data.JsonReader({
			totalProperty : "total",
			root : "pipesegments"
		}, route);
		
		 this.store = new Ext.data.Store({// 提供数据输入
			proxy : proxy,
			reader : reader,
			remoteSort : true
		});
    	this.store.load({
			
		});
    	var pipeId={
    		layout : 'column',
    		items:[{
    			layout : 'form',
    			items:[{
    				id : 'mergePipeId',
    				name : 'pipeInfoBean.pipeId',
    				xtype : 'textfield',
    				hidden : true
    			}]
    		}]
    	}
		var row1={
			layout : 'column',
			items : [{
				layout : 'form',
				items:[{
					layout : 'column',
					fieldLabel : "管道名称",
					items :[{
						id : 'mergePipeName',
						xtype : 'textfield',
						name : "pipeInfoBean.pipeName",
						allowBlank : false,
						blankText : '管道名称不能为空',
						emptyText : "请输入管道名称",
						width : 150,
						invalidText : '管道名称已存在！',
						validationEvent : 'keydown',
						validationDelay : 100,
						validator : function(thisText) {
							// 没有输入，不验证
							if (thisText == "") {
								return true;
							}
							if(Ext.getCmp("mergePipeId").getValue()!=null||Ext.getCmp("mergePipeId").getValue()!=""){
								return true;
							}
							Ext.Ajax.request({
								url : 'pipe!checkPipeNameJSON.action',
								method : 'post',
								async:false,
								params : {
									'pipeName' : thisText
								},
								success : function(response) {
									var res = Ext.util.JSON.decode(response.responseText);
									var msg = res.success;
									if (msg == false) {
										Ext.getCmp('mergePipeName').markInvalid('管道名称已存在!');
										idIsExist=false;
									}else{
										Ext.getCmp('mergePipeName').clearInvalid();
										idIsExist=true;
									}
								}
							});
							return idIsExist;
						}
					},{
						xtype : 'button',
						text : '选择插入',
						style : 'margin-left:5px',
						width : 35,
						handler : function() {
							showPipeBySeg();
						}
					},{
						xtype : 'button',
						text : '清空',
						style : 'margin-left:5px',
						width : 25,
						handler : function() {
							Ext.getCmp("mergePipeId").setValue("");
							Ext.getCmp("mergePipeName").setValue("");
							Ext.getCmp("mergeAlias").setValue("");
							Ext.getCmp("mergePipeLevel").setValue("");
							Ext.getCmp("mergeLength").setValue("");
							Ext.getCmp("mergeStartDeviceId").setValue("");
							Ext.getCmp("mergeStartDeviceType").setValue("");
							Ext.getCmp("mergeStartDeviceName").setValue("");
							Ext.getCmp("mergeEndDeviceId").setValue("");
							Ext.getCmp("mergeEndDeviceType").setValue("");
							Ext.getCmp("mergeEndDeviceName").setValue("");
							Ext.getCmp("mergePurchasedMaintenanceTime").setValue("");
							Ext.getCmp("mergeMaintenanceModeEnumId").setValue("");
							Ext.getCmp("mergeMaintenanceOrgId").setValue("");
							Ext.getCmp("mergeThirdPartyMaintenanceOrg").setValue("");
							Ext.getCmp("mergeMaintenanceTypeEnumId").setValue("");
							Ext.getCmp("mergeResourceLifePeriodEnumId").setValue("");
							Ext.getCmp("mergeProjectName").setValue("");
							Ext.getCmp("mergeProjectCode").setValue("");
							Ext.getCmp("mergeRenewalExpirationDate").setValue("");
							Ext.getCmp("mergeProjectWarrantyExpireDate").setValue("");
							Ext.getCmp("mergecomboxWithTree").setValue("");
						}
					}]
				}]
			},{
				layout : 'form',
				labelWidth : 165,
				items :[{
					id : 'mergeAlias',
					xtype : 'textfield',
					name : "pipeInfoBean.alias",
					fieldLabel : "管道别名",
					emptyText : "请输入管道别名",
					width : 150
				}]
			}]
		}
		this.mergelevelStore=new Ext.data.SimpleStore({
			fields : ['value', 'text'],
	    	data : [['', ''],['1', '一干'], ['2', '二干'], ['3', '本地']]
		})
		var row2={
			layout : 'column',
			items : [{
				layout : 'form',
				items :[{
					id : "mergePipeLevel",
					xtype : 'combo',
					name : "pipeInfoBean.pipeLevel",
					typeAhead: true,
		        	triggerAction: 'all',
					fieldLabel : "管道级别",
					emptyText : "请选择管道级别",
					blankText : '管道级别不能为空',
					forceSelection: true,
		         	mode: 'local',
		        	width : 150,
		         	store: this.mergelevelStore,
		         	valueField: 'value',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
		       		displayField: 'text',//下拉框显示内容
		       		hiddenName:'pipeInfoBean.pipeLevel',//创建一个表单隐藏域来存储输入项的值
		       		editable:false//设置为false以阻止用户直接向该输入项输入文本
				}]
			},{
				layout : 'form',
				labelWidth : 260,
				items :[{
					id : 'mergeLength',
					xtype : 'textfield',
					name : "pipeInfoBean.length",
					fieldLabel : "管道长度（米）",
					emptyText : "请输入管道长度且只能为数字",
					width : 150,
					invalidText : '请输入正确的管道长度！',
					validationEvent : 'keydown',
					validationDelay : 100,
					validator : function(thisText) {
						if (thisText == "") {
							return true;
						}
						var reg=/^[0-9]+\.{0,1}\d*$/;
						if(!reg.test(thisText)){
							Ext.getCmp('mergeLength').markInvalid('填写格式不正确，请重新填写!');
							return false;
						}else{
							return true;
						}
					}
				}]
			}]
		}
		var row3={
			layout : 'column',
			items : [{
				layout : 'form',
				items :[{
					xtype : 'textfield',
					id : "mergeStartDeviceId",
					name : "pipeInfoBean.startDeviceId",
					width : 5,
					hidden : true
				}, {
					xtype : 'textfield',
					id : "mergeStartDeviceType",
					name : "pipeInfoBean.startDeviceType",
					width : 5,
					hidden : true
				},{
					layout : 'column',
					fieldLabel : '起始设施名称',
					items : [{
						id : 'mergeStartDeviceName',
						name : 'pipeInfoBean.startDeviceName',
						xtype : 'textfield',
						emptyText : "请选择起始设施",
						width : 150,
						readOnly : true
					}, {
						xtype : 'button',
						text : '选择井',
						style : 'margin-left:5px',
						width : 30,
						handler : function() {
							showWellGrid_as();
						}
					},{
						xtype : 'button',
						text : '选择杆',
						style : 'margin-left:5px',
						width : 30,
						handler : function() {
							showPoleGrid_as();
						}
					},{
						xtype : 'button',
						text : '清空',
						style : 'margin-left:5px',
						width : 25,
						handler : function() {
							Ext.getCmp("mergeStartDeviceId").setValue("");
							Ext.getCmp("mergeStartDeviceName").setValue("");
							Ext.getCmp("mergeStartDeviceType").setValue("");
						}
					}]
				}]
			},{
				layout : 'form',
				items :[{
					xtype : 'textfield',
					id : "mergeEndDeviceId",
					name : "pipeInfoBean.endDeviceId",
					width : 5,
					hidden : true
				},{
					xtype : 'textfield',
					id : "mergeEndDeviceType",
					name : "pipeInfoBean.endDeviceType",
					width : 5,
					hidden : true
				}, {
					layout : 'column',
					fieldLabel : '终止设施名称',
					items : [{
						id : 'mergeEndDeviceName',
						name : 'pipeInfoBean.endDeviceName',
						xtype : 'textfield',
						emptyText : "请选择终止设施",
						fieldLabel : '终止设施名称',
						width : 150,
						readOnly : true
					}, {
						xtype : 'button',
						text : '选择井',
						style : 'margin-left:5px',
						width : 30,
						handler : function() {
							showWellGrid_zs();
						}
					},{
						xtype : 'button',
						text : '选择杆',
						style : 'margin-left:5px',
						width : 30,
						handler : function() {
							showPoleGrid_zs();
						}
					},{
						xtype : 'button',
						text : '清空',
						style : 'margin-left:5px',
						width : 25,
						handler : function() {
							Ext.getCmp("mergeEndDeviceId").setValue("");
							Ext.getCmp("mergeEndDeviceName").setValue("");
							Ext.getCmp("mergeEndDeviceType").setValue("");
						}
					}]
				}]
			}]
		}
		this.mergemaintenanceStore = new Ext.data.SimpleStore({
    		fields : ['value', 'text'],
    		data : [['', ''],['1', '自维'], ['2', '代维']]
    	})
		var row4={
			layout : 'column',
			items : [{
				layout : 'form',
				items :[{
					id : 'mergePurchasedMaintenanceTime',
					xtype : 'textfield',
					name : "pipeInfoBean.purchasedMaintenanceTime",
					fieldLabel : "购买年限",
					emptyText : "请输入购买年限且只能为正整数",
					width : 150,
					invalidText : '请输入正确的购买年限！',
					validationEvent : 'keydown',
					validationDelay : 100,
					validator : function(thisText) {
						if (thisText == "") {
							return true;
						}
						var reg=/^[1-9]\d*$/;
						if(!reg.test(thisText)){
							Ext.getCmp('mergePurchasedMaintenanceTime').markInvalid('填写格式不正确，请重新填写!');
							return false;
						}else{
							return true;
						}
					}
				}]
			},{
				layout : 'form',
				labelWidth : 260,
				items :[{
					id : "mergeMaintenanceModeEnumId",
					xtype : 'combo',
					name : "pipeInfoBean.maintenanceModeEnumId",
					typeAhead: true,
		        	triggerAction: 'all',
					fieldLabel : "维护方式",
					emptyText : "请选择维护方式",
					blankText : '维护方式不能为空',
					forceSelection: true,
		         	mode: 'local',
		        	width : 150,
		         	store: this.mergemaintenanceStore,
		         	valueField: 'value',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
		       		displayField: 'text',//下拉框显示内容
		       		hiddenName:'pipeInfoBean.maintenanceModeEnumId',//创建一个表单隐藏域来存储输入项的值
		       		editable:false//设置为false以阻止用户直接向该输入项输入文本
				}]
			}]
		}
		var row5={
			layout : 'column',
			items : [{
				layout : 'form',
				items :[{
					id : 'mergeMaintenanceOrgId',
					xtype : 'textfield',
					name : "pipeInfoBean.maintenanceOrgId",
					fieldLabel : "维护单位",
					emptyText : "请输入维护单位",
					width : 150
				}]
			},{
				layout : 'form',
				labelWidth : 260,
				items :[{
					id : 'mergeThirdPartyMaintenanceOrg',
					xtype : 'textfield',
					name : "pipeInfoBean.thirdPartyMaintenanceOrg",
					fieldLabel : "第三方维护单位",
					emptyText : "请输入第三方维护单位",
					width : 150
				}]
			}]
		}
		this.mergemaintenanceTypeStore = new Ext.data.SimpleStore({
    		fields : ['value', 'text'],
    		data : [['', ''],['1', '硬件维修'], ['2', '紧急备件支持'], ['3', '软件补丁版本支持'], ['4', '技术支持服务（电话咨询、电话支持、远程支持、现场支持、网络预警、重要通信保障）'], ['5', '无']]
    	})
    	this.mergeresourceLifePeriodEnumId = new Ext.data.SimpleStore({
    		fields : ['value', 'text'],
    		data : [['', ''],['1', '设计状态'], ['2', '工程建设期（入网带业务前）'], ['3', '工程可用期（已入网带业务）'], 
    		['4', '工程初验后试运行'], ['5', '工程终验后在网'], ['6', '退网状态']]
    	})
		var row6={
			layout : 'column',
			items : [{
				layout : 'form',
				items :[{
					id : "mergeMaintenanceTypeEnumId",
					xtype : 'combo',
					name : "pipeInfoBean.maintenanceTypeEnumId",
					typeAhead: true,
		        	triggerAction: 'all',
					fieldLabel : "维保类型",
					emptyText : "请选择维保类型",
					blankText : '维保类型不能为空',
					forceSelection: true,
		         	mode: 'local',
		        	width : 150,
		         	store: this.mergemaintenanceTypeStore,
		         	valueField: 'value',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
		       		displayField: 'text',//下拉框显示内容
		       		hiddenName:'pipeInfoBean.maintenanceTypeEnumId',//创建一个表单隐藏域来存储输入项的值
		       		editable:false//设置为false以阻止用户直接向该输入项输入文本
				}]
			},{
				layout : 'form',
				labelWidth : 260,
				items :[{
					id : "mergeResourceLifePeriodEnumId",
					xtype : 'combo',
					name : "pipeInfoBean.resourceLifePeriodEnumId",
					typeAhead: true,
		        	triggerAction: 'all',
					fieldLabel : "资源生命周期",
					emptyText : "请选择资源生命周期",
					blankText : '资源生命周期不能为空',
					forceSelection: true,
		         	mode: 'local',
		        	width : 150,
		         	store: this.mergeresourceLifePeriodEnumId,
		         	valueField: 'value',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
		       		displayField: 'text',//下拉框显示内容
		       		hiddenName:'pipeInfoBean.resourceLifePeriodEnumId',//创建一个表单隐藏域来存储输入项的值
		       		editable:false//设置为false以阻止用户直接向该输入项输入文本
				}]
			}]
		}
		var row7={
			layout : 'column',
			items : [{
				layout : 'form',
				items :[{
					id : 'mergeProjectName',
					xtype : 'textfield',
					name : "pipeInfoBean.projectName",
					fieldLabel : "项目名称",
					emptyText : "请输入项目名称",
					width : 150
				}]
			},{
				layout : 'form',
				labelWidth : 260,
				items :[{
					id : 'mergeProjectCode',
					xtype : 'textfield',
					name : "pipeInfoBean.projectCode",
					fieldLabel : "项目编号",
					emptyText : "请输入项目编号",
					width : 150
				}]
			}]
		}
		var row8={
			layout : 'column',
			items : [{
				layout : 'form',
				items :[{
					id : 'mergeRenewalExpirationDate',
					name : 'pipeInfoBean.renewalExpirationDate',
					xtype : "datefield",
					format : 'Y-m-d',// 日期格式
					fieldLabel : '续保截止日期',
					emptyText : "请选择保修截止日期",
					width : 150
				}]
			},{
				layout : 'form',
				labelWidth : 260,
				items :[{
					id : 'mergeProjectWarrantyExpireDate',
					name : 'pipeInfoBean.projectWarrantyExpireDate',
					xtype : "datefield",
					format : 'Y-m-d',// 日期格式
					fieldLabel : '工程保修截止日期',
					emptyText : "请选择保修截止日期",
					width : 150
				}]
			}]
		}
		var comboxWithTree = new Ext.form.ComboBox({
			store : new Ext.data.SimpleStore({
				fields : [],
				data : [[]]
			}),
			id : 'mergecomboxWithTree',
			fieldLabel : '所属局站',
			emptyText : "请选择所属局站",
			editable : false,
			mode : 'local',
			triggerAction : 'all',
			width : 150,
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
			comboxWithTree.setValue(node.attributes.text);
			Ext.getCmp("mergeareano").setValue(node.attributes.domainCode);
			Ext.getCmp("mergeareaname").setValue(node.attributes.text);
			comboxWithTree.collapse();
		});
	
		comboxWithTree.on('expand', function() {
			opTree.render('tree');
		});
	
		var areano = {
			name : 'pipeInfoBean.areano',
			id : 'mergeareano',
			xtype : 'textfield',
			fieldLabel : '所属局站',
			hidden : true
		};
		
		var areaname = {
			name : 'pipeInfoBean.areaname',
			id : 'mergeareaname',
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
			bodyStyle : 'padding-left:78%;'
		}, {
			xtype : 'fieldset',
			title : '管道段信息',
			collapsible : true,
			items:[/*{
				layout : 'column',
				items : [psgrid]
			}*/{
				xtype:"itemselector",
	            id:'roleItemSelector',
	            name:"c_id",
	            allowBlank : false,
	            hideLabel : true,
	           	//fieldLabel:"ItemSelector",
	            dataFields:["pipeSegmentId", "pipeSegmentName"],
	            toData:[],
	            fromData:[],
	            msWidth:410,
	            msHeight:230,
	            valueField:"pipeSegmentId",
	            displayField:"pipeSegmentName",
	            imagePath:"imgs/itemselector",
	            toLegend:'<span style="font-size:14px;">已选择管道段</span>',
	            fromLegend:'<span style="font-size:14px;">可选择管道段</span>',
	            //toStore:,
	            fromStore:this.store,//角色信息数据源
	            toTBar : [{
					text:"清除所有",
					handler : function() {
						Ext.getCmp("roleItemSelector").reset();
					}
				}]
			}]
		}, {
			xtype : 'fieldset',
			title : '新管道基本信息',
			collapsible : true,
			items : [pipeId,row1,row2,row3,row4,row5,row6,row7,row8,row9]
		}];
		// 按钮
		this.buttons = [{
			id : 'mergeBtnSubmit',
			text : "提交保存",
			hidden : false,
			icon : "imgs/save_btn.png",
			cls : "x-btn-text-icon",
			tooltip : '保存',
			tooltipType : 'qtip',
			handler : this.mergeBtnSubmit.createDelegate(this)
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
		//重写左键点击方法
		Ext.override(Ext.ux.Multiselect, {
			onViewClick: function(vw, index, node, e) {
       			var arrayIndex = this.preClickSelections.indexOf(index);
        		if (arrayIndex  != -1){
           			this.preClickSelections.splice(arrayIndex, 1);
            		this.view.clearSelections(true);
            		this.view.select(this.preClickSelections);
        		}
        		//var routename = node.innerHTML;
       			//Ext.getCmp("mergeRouteName").setValue(routename);
        		this.fireEvent('change', this, this.getValue(), this.hiddenField.dom.value);
        		this.hiddenField.dom.value = this.getValue();
        		this.fireEvent('click', this, e);
        		this.validate();        
   		 	}	
		});

		// 重写initComponent superclass.initComponent.call(this);来调用父类的初始化方法.
		com.increase.cen.poleline.addPipeBySeg.superclass.initComponent.call(this);
	},
	// 保存按钮事件
	mergeBtnSubmit : function() {
		if (!this.getForm().isValid()) {
			return;
		}
		this.getForm().doAction("submit", {
			url : 'pipe!addPipeAndSegment.action',
			method : 'post',
			submitEmptyText : false,
			success : function(form, action) {
				Ext.Msg.show({
					title : '提示',
					width : 300,
					msg : '<img src="imgs/tip_success.png" align="center" hspace="30" />'
							+ '生成管道成功!',
					buttons : {
						ok : "确定"
					}
				});
				Ext.getCmp("addPipeBySeg").getForm().reset();// 重置表单
				Ext.getCmp('addPipeBySegWindow').close();// 窗口隐藏
				Ext.getCmp('pipeSegmentGrid').getStore().load({
					params : {
						start : 0,
						limit : limit
					}
				});// 表格加载数据
				Ext.getCmp('pipeSegmentGrid').getView().refresh();// 表格刷新
			},
			failure : function(form, action) {
				Ext.Msg.show({
					title : '提示',
					width : 300,
					msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
							+ '生成管道失败',
					buttons : {
						ok : "确定"
					}
				});
			}
		});
	},
	// 返回操作
	btnClose : function() {
		// 重置表单
		Ext.getCmp("addPipeBySeg").getForm().reset();// 重置表单
		Ext.getCmp('addPipeBySegWindow').close();// 窗口隐藏
		Ext.getCmp('pipeSegmentGrid').getStore().load({
			params : {
				start : 0,
				limit : limit
			}
		});// 表格加载数据
	}
});
Ext.reg("addPipeBySeg", com.increase.cen.poleline.addPipeBySeg);
function showPipeBySeg(){
	var grid = Ext.getCmp("pipeSegmentGrid");
	grid.pipeBySegWindow=new com.increase.cen.poleline.PolelineWindow({
		id : "pipeBySegWindow",
		title : '选择管道',
		iconCls : 'i-script-window',
		items : [{
			id : "pipeBySeg",
			height : 350,
			width : 850,
			xtype : 'pipeBySeg'// 窗口里的表单
		}]
	});
	var store = Ext.getCmp("pipeBySeg").getStore();
	var loadMarsk = new Ext.LoadMask(document.body, {
		msg : '数据加载中，请稍候......',
		disabled : false,
		store : store
	});
	store.addListener('beforeload', function() {
		loadMarsk.show();
	});
	store.load({
		params : {
			start : 0,
			limit : 10
		}
	});
	// 显示窗口
	grid.pipeBySegWindow.show();
}
