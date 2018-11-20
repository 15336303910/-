Ext.namespace("com.increase.cen.route");
// 一页显示多少条数据

var limit = null;
com.increase.cen.route.BetchRoute = Ext.extend(Ext.form.FormPanel, {
	bodyStyle : "padding:5px;",
	frame : true,
	bodyBorder : false,
	labelAlign : "right",
	buttonAlign : 'center',
	labelWidth : 100,
	width : 550,
	initComponent : function(config) {
		// 一页显示多少条数据
		limit = Ext.getDom("limit").value;
		// 启动悬停提示
		Ext.QuickTips.init();
		// 提示的方式
		Ext.form.Field.prototype.msgTarget = 'qtip';

		this.one = {
			bodyStyle : 'padding: 10 0 10 50; border-width:0 0 0 0px',
			style : '',
			html : '<img border="0" src="imgs/plwh/s.gif" width="28" height="21"> 第一步 <a target="_self" href="export.jsp?filename=routetmpl">下载模板</a>并按模板格式整理光缆数据。',
			anchor : '90%' // anchor width by percentage
		}
		this.two = {
			bodyStyle : 'padding: 10 0 10 50; border-width:0 0 0 0px',
			style : '',
			html : '<img border="0" src="imgs/plwh/s.gif" width="28" height="21"> 第二步 选择整理好的数据文件路径，点击上传按钮进行批量维护。',
			anchor : '90%' // anchor width by percentage
		}
		
		this.file = {
			xtype : 'compositefield',
			items : [{
				xtype : 'textfield',
				fieldLabel : '',
				name : 'excle',
				inputType : 'file',
				allowBlank : false,
				width : '300'
			}]
		}
		this.items = [{
			xtype : 'fieldset',
			id : 'fieldset',
			title : '光缆信息导入',
			autoScroll:true,
			width:540,
			items : [this.one, this.two, this.file]
		}];

		// 按钮
		this.buttons = [{
			id : 'btnSubmit',
			text : "上传",
			hidden : false,
			icon : "imgs/save_btn.png",
			cls : "x-btn-text-icon",
			tooltip : '保存信息',
			tooltipType : 'qtip',
			handler : this.btnSubmit.createDelegate(this)
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
		com.increase.cen.route.BetchRoute.superclass.initComponent.call(this);
	},
	// 保存按钮事件
	btnSubmit : function() {
		// 提交验证
		var excle = Ext.query("*[name=excle]");
		var ary = [];
		for (var i = 0; i < excle.length; i++) {
			if (excle[i].value != null && excle[i].value != '') {
				var filename = excle[i].value.substring(excle[i].value
						.lastIndexOf('\\')+ 1, excle[i].value.length);
				ary.push(filename);
			}else{
				parent.Ext.Msg.show({
					title : '提示',
					autowidth : true,
					width : 400,
					msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>请上传所有文件',
					buttons : {
						ok : "确定"
					}
				});
				return false;
			}
		}
		var s = ary.join(",") + ",";
		// 检查是否有重复文件
		for (var i = 0; i < ary.length; i++) {
			var filetype = ary[i].substring(ary[i].lastIndexOf('.') + 1,ary[i].length);
			var filename = ary[i];
			if (filetype != 'xls') {
				parent.Ext.Msg.show({
					title : '提示',
					autowidth : true,
					width : 400,
					msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>文件: "'
							+ filename + '" 格式不正确!请仔细核对后上传',
					buttons : {
						ok : "确定"
					}
				});
				return false;
			}
			if (s.replace(ary[i] + ",", "").indexOf(ary[i] + ",") > -1) {
				parent.Ext.Msg.show({
					title : '提示',
					autowidth : true,
					width : 400,
					msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>存在重复文件: "'
							+ filename + '" 请仔细核对后上传',
					buttons : {
						ok : "确定"
					}
				});
				return false;
			}
		}
		parent.parent.Ext.Msg.wait("服务器正在处理档案……", '请稍候'); 
		Ext.getCmp("betchRoute").getForm().doAction("submit", {
			url : 'route!betchUpdateRoute.action',
			method : 'post',
			//timeout: 1000*60*5,
			submitEmptyText : false,
			//waitMsg : '正在处理上传文件...请稍后...',
			success : function(form, action) {
				parent.parent.Ext.Msg.hide();
				parent.Ext.Msg.show({
					title : '提示',
					width : 300,
					msg : '<img src="imgs/tip_success.png" align="center" hspace="30" />'+ '上传档案成功!',
					buttons : {
						ok : "确定"
					}
				});
				Ext.getCmp("betchRoute").getForm().reset();// 重置表单
				if(Ext.getCmp('betchRouteWindow')!=null){
					Ext.getCmp('betchRouteWindow').hide();// 窗口隐藏
				}
				Ext.getCmp('routeGrid').getStore().load({
					params : {
						start : 0,
						limit : limit
					}
				});// 表格加载数据
				Ext.getCmp('routeGrid').getView().refresh();// 表格刷新
			},
			failure : function(form, action) {
				parent.parent.Ext.Msg.hide();
				var verifyMsg = action.result.verifyMsg;
				parent.Ext.Msg.show({
					title : '提示',
					width : 500,
					msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'+ "上传档案失败",
					buttons : {
						ok : "确定"
					}
				});
			}
		});
	},
	// 返回操作
	btnClose : function() {
		Ext.getCmp("betchRoute").getForm().reset();// 重置表单
		if(Ext.getCmp('betchRouteWindow')!=null){
			Ext.getCmp('betchRouteWindow').hide();// 窗口隐藏
		}
		Ext.getCmp('routeGrid').getStore().load({
			params : {
				start : 0,
				limit : limit
			}
		});// 表格加载数据
		Ext.getCmp('routeGrid').getView().refresh();// 表格刷新
	}
});
Ext.reg("betchRoute", com.increase.cen.route.BetchRoute);
