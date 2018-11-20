Ext.namespace("com.increase.cen.route");
var limit = null;
com.increase.cen.route.AddRoute = Ext.extend(Ext.form.FormPanel, {
	bodyStyle : "padding:5px;",
	frame : true,
	bodyBorder : false,
	labelAlign : "right",
	buttonAlign : 'center',
	labelWidth : 100,
	width : 500,
	height : 300,
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
		this.routeBatchAdd = new Ext.form.Checkbox({
			id : 'routeBatchAdd',
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
		
		// 光缆名
		this.routename = new Ext.form.TextField({
			id : "addRouteName",
			name : "newRoute.routename",
			fieldLabel : "光缆名称",
			allowBlank : false,// 验证是否为空
			blankText : '光缆名称不能为空',
			width : 200,
			emptyText : "请输入光缆名称",
			regex:/[a-zA-z0-9\u4E00-\u9FA5]*/,
			regexText : '请输入正确光缆名称',
			invalidText : '光缆名称已存在！',
			validationEvent : 'keydown',
			validationDelay : 100,
			validator : function(thisText) {
				// 没有输入，不验证
				if (thisText == "") {
					return true;
				}
				Ext.Ajax.request({
					url : 'route!verifyRoute.action',
					method : 'post',
					// async:false,
					params : {
						'newRoute.routename' : thisText
					},
					success : function(response) {
						var res = Ext.util.JSON.decode(response.responseText);
						var msg = res.success;
						if (msg == false) {
							Ext.getCmp('addRouteName').markInvalid('光缆名称已存在!');
						}
					}
				});
				return idIsExist;
			}
		});
		
    	this.levelStore = new Ext.data.SimpleStore({
    		fields : ['value', 'text'],
    		data : [['', ''],['1', '一级干线'], ['2', '二级干线'], ['3', '本地中继'], ['4', '本地核心'], ['5', '本地汇聚'], ['6', '本地接入']]
    	})
    	this.routeLevel = new Ext.form.ComboBox({
    		id : "addRouteLevel",
			name : "newRoute.routelevel",
			typeAhead: true,
        	triggerAction: 'all',
			fieldLabel : "光缆级别",
			emptyText : "请选择光缆级别",
			blankText : '光缆级别不能为空',
			forceSelection: true,
         	mode: 'local',
        	width : 200,
         	store: this.levelStore,
         	valueField: 'value',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
       		displayField: 'text',//下拉框显示内容
       		hiddenName:'newRoute.routelevel',//创建一个表单隐藏域来存储输入项的值
       		editable:false//设置为false以阻止用户直接向该输入项输入文本
    	})
    	this.startSite = new Ext.form.TextField({
    		id : "addStartsite",
			name : "newRoute.startsite",
			emptyText : "请输入光缆起始局站",
			fieldLabel : "起始局站",
			blankText : '起始局站不能为空',
			width : 200
    	})
    	
    	this.endSite = new Ext.form.TextField({
    		id : "addEndSite",
			name : "newRoute.endsite",
			fieldLabel : "终止局站",
			emptyText : "请输入光缆终止局站",
			blankText : '终止局站不能为空',
			width : 200
    	})
    	
    	this.stateStore = new Ext.data.SimpleStore({
    		fields : ['value', 'text'],
    		data : [['', ''],['1', '割接封锁'], ['2', '正常'], ['3', '不可用']]
    	})
    	this.state = new Ext.form.ComboBox({
    		id : "addState",
			name : "newRoute.state",
			typeAhead: true,
        	triggerAction: 'all',
        	fieldLabel : "维护状态",
        	blankText : '维护状态不能为空',
        	emptyText : "请选择光缆维护状态",
        	forceSelection: true,
         	mode: 'local',
        	width : 200,
         	store: this.stateStore,
         	valueField: 'value',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
       		displayField: 'text',//下拉框显示内容
       		hiddenName:'newRoute.state',//创建一个表单隐藏域来存储输入项的值
       		editable:false//设置为false以阻止用户直接向该输入项输入文本
    	})
    	
    	this.sign = new Ext.form.TextField({
    		id : "addSign",
			name : "newRoute.sign",
			fieldLabel : "光缆标牌",
			emptyText : "请输入光缆标牌",
			width : 200
    	})
		

		this.items = [{
			xtype : 'panel',
			bodyStyle : 'padding-left:78%;',
			items : [this.routeBatchAdd]
		}, {
			xtype : 'fieldset',
			title : '光缆信息',
			collapsible : true,
			items : [this.routename,this.routeLevel,this.startSite,this.endSite,this.state,this.sign]
		}];

		// 按钮
		this.buttons = [{
			id : 'addBtnSubmit',
			text : "保存",
			hidden : false,
			icon : "imgs/save_btn.png",
			cls : "x-btn-text-icon",
			tooltip : '保存光缆信息',
			tooltipType : 'qtip',
			handler : this.addBtnSubmit.createDelegate(this)
		}, {
			id : 'addBtnAdd',
			hidden : true,
			text : '继续添加',
			tooltip : '继续添加光缆信息',
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
		com.increase.cen.route.AddRoute.superclass.initComponent.call(this);
	},
	// 保存按钮事件
	addBtnSubmit : function() {
		if (!this.getForm().isValid()) {
			return;
		}
		var state = Ext.getCmp("addState").getValue();
		var routelevel=Ext.getCmp("addRouteLevel").getValue();
		this.getForm().doAction("submit", {
			url : 'route!addRoute.action',
			method : 'post',
			submitEmptyText : false,
			success : function(form, action) {
				Ext.Msg.show({
					title : '提示',
					width : 300,
					msg : '<img src="imgs/tip_success.png" align="center" hspace="30" />'
							+ '添加光缆信息成功!',
					buttons : {
						ok : "确定"
					}
				});
				Ext.getCmp("addRoute").getForm().reset();// 重置表单
				Ext.getCmp('addRouteWindow').hide();// 窗口隐藏
				Ext.getCmp('routeGrid').getStore().load({
					params : {
						start : 0,
						limit : limit
					}
				});// 表格加载数据
				Ext.getCmp('routeGrid').getView().refresh();// 表格刷新
			},
			failure : function(form, action) {
				Ext.Msg.show({
					title : '提示',
					width : 300,
					msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
							+ '添加光缆信息失败',
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
		Ext.getCmp("addRoute").getForm().reset();// 重置表单
		Ext.getCmp('addRouteWindow').hide();// 窗口隐藏
		Ext.getCmp('routeGrid').getStore().load({
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
		this.getForm().doAction("submit", {
			url : 'route!addRoute.action',
			method : 'post',
			submitEmptyText : false,
			success : function(form, action) {
				Ext.Msg.show({
					title : '提示',
					width : 300,
					msg : '<img src="imgs/tip_success.png" align="center" hspace="30"/>'
							+ '添加光缆信息成功!',
					buttons : {
						ok : "确定"
					}
				});
				// 重置表单
				Ext.getCmp("addRoute").getForm().reset();
				// 复选框选中
				Ext.getCmp("routeBatchAdd").setValue(true);
			},
			failure : function(form, action) {
				Ext.Msg.show({
					title : '提示',
					width : 300,
					msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
							+ '添加光缆信息失败',
					buttons : {
						ok : "确定"
					}
				});
			}
		});
	}
});
Ext.reg("addRoute", com.increase.cen.route.AddRoute);