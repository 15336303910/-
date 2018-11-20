Ext.namespace("com.increase.cen.route");
var limit = null;
com.increase.cen.route.ModifyRoute = Ext.extend(Ext.form.FormPanel, {
	bodyStyle : "padding:5px;",
	frame : true,
	bodyBorder : false,
	labelAlign : "right",
	buttonAlign : 'center',
	labelWidth : 65,
	width : 300,
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
		this.routeid = new Ext.form.TextField({
			id : "routeid",
			name : "updateRoute.routeid",
			fieldLabel : "光缆id",
			width : 300,
			hidden : true
		});
		this.routename = new Ext.form.TextField({
			id:'routename',
			name : "updateRoute.routename",
			width : 300,
			hidden : true
		});

		// 光缆名
		this.newRoutename = new Ext.form.TextField({
			id : 'newRoutename',
			name : "updateRoute.newRoutename",
			fieldLabel : "光缆名",
			allowBlank : false,// 验证是否为空
			blankText : '光缆名不能为空',
			emptyText : "请输入光缆名",
			width : 300,
			validationEvent : 'keydown',
			invalidText : '地区已存在！',
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
						'newRoute.routeid' :Ext.getCmp("routeid").getValue(),
						'newRoute.routename' : thisText
					},
					success : function(response) {
						var res = Ext.util.JSON.decode(response.responseText);
						var msg = res.success;
						if (msg == false) {
							Ext.getCmp('routename').markInvalid('光缆名已存在!');
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
		//光缆级别
		this.routelevel = new Ext.form.ComboBox({
    		id : "routelevel2",
			name : "updateRoute.routelevel",
			typeAhead: true,
        	triggerAction: 'all',
			fieldLabel : "光缆级别",
			emptyText : "请选择光缆级别",
			blankText : '光缆级别不能为空',
			forceSelection: true,
         	mode: 'local',
        	width : 300,
         	store: this.levelStore,
         	valueField: 'value',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
       		displayField: 'text',//下拉框显示内容
       		hiddenName:'updateRoute.routelevel',//创建一个表单隐藏域来存储输入项的值
       		editable:false//设置为false以阻止用户直接向该输入项输入文本
    	})
		//起始局站
    	this.startSite = new Ext.form.TextField({
    		id : "startsite",
			name : "updateRoute.startsite",
			emptyText : "请输入光缆起始局站",
			fieldLabel : "起始局站",
			blankText : '起始局站不能为空',
			width : 300
    	})
		//终止局站
    	this.endSite = new Ext.form.TextField({
    		id : "endsite",
			name : "updateRoute.endsite",
			fieldLabel : "终止局站",
			emptyText : "请输入光缆终止局站",
			blankText : '终止局站不能为空',
			width : 300
    	})
    	
    	
    	this.stateStore = new Ext.data.SimpleStore({
    		fields : ['value', 'text'],
    		data : [['', ''],['1', '割接封锁'], ['2', '正常'], ['3', '不可用']]
    	})
    	this.state = new Ext.form.ComboBox({
    		id : "state2",
			name : "updateRoute.state",
			typeAhead: true,
        	triggerAction: 'all',
        	fieldLabel : "维护状态",
        	blankText : '维护状态不能为空',
        	emptyText : "请选择光缆维护状态",
        	forceSelection: true,
         	mode: 'local',
        	width : 300,
         	store: this.stateStore,
         	valueField: 'value',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
       		displayField: 'text',//下拉框显示内容
       		hiddenName:'updateRoute.state',//创建一个表单隐藏域来存储输入项的值
       		editable:false//设置为false以阻止用户直接向该输入项输入文本
    	})
    	//光缆铭牌
    	this.sign = new Ext.form.TextField({
    		id : "sign",
			name : "updateRoute.sign",
			fieldLabel : "光缆标牌",
			emptyText : "请输入光缆标牌",
			width : 300
    	})

		this.items = [{
			xtype : 'fieldset',
			title : '基本信息',
			collapsible : true,
			items : [this.routeid,this.routename,this.newRoutename,this.routelevel,this.startSite,this.endSite,this.state,this.sign]
		}];
		// 按钮
		this.buttons = [{
			text : "保存",
			icon : "imgs/save_btn.png",
			cls : "x-btn-text-icon",
			tooltip : '修改光缆信息',
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
		com.increase.cen.route.ModifyRoute.superclass.initComponent.call(this);
	},
	// 提交事件
	btnSubmit : function submitForm() {
		if (!this.getForm().isValid()) {
			return;
		}
		this.getForm().doAction("submit", {
			url : 'route!updateRouteBean.action',
			submitEmptyText : false,
			method : 'post',
			success : function(form, action) {
				Ext.Msg.show({
					title : '提示',
					width : 300,
					msg : '<img src="imgs/tip_success.png" align="center" hspace="30"/>'
							+ '修改光缆信息成功!',
					buttons : {
						ok : "确定"
					}
				});
				// 窗口隐藏
				Ext.getCmp('modifyRouteWindow').hide();
				Ext.getCmp("routeGrid").getStore().reload();
			},
			failure : function(form, action) {
				Ext.Msg.show({
					title : '提示',
					width : 300,
					msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
							+ '修改光缆信息失败!',
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
		Ext.getCmp("modifyRoute").getForm().reset();
		// 窗口隐藏
		Ext.getCmp('modifyRouteWindow').hide();
		// 刷新表格
		Ext.getCmp("routeGrid").getStore().reload();
	}
});
Ext.reg("modifyRoute", com.increase.cen.route.ModifyRoute);