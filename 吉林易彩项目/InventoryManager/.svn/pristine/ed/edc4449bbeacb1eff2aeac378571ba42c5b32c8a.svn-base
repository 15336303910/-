Ext.namespace("com.increase.cen.document");
// 一页显示多少条数据

var limit = null;
	// 从后台读取工单级别
		var proxy = new Ext.data.HttpProxy({
			url : "workorder!loadWorkOrderEmodelCombo.action"
		});
		// Record 定义记录结果
		var emodel = Ext.data.Record.create([{
			name : 'modelId',
			type : 'string',
			mapping : 'modelId'
		}, {
			name : 'modelName',
			type : 'string',
			mapping : 'modelName'
		}]);
		// 读取json格式数据
		var reader = new Ext.data.JsonReader({}, emodel);
		var rankStore = new Ext.data.Store({
			proxy : proxy,
			reader : reader,
			listeners : {
				load : function(store, records, options) {
					var data = {
						"modelId" : "",
						"emodel" : "请选择设备型号"
					};
					var rs = [new Ext.data.Record(data)];
					store.insert(0, rs);
				}
			}
		});
		rankStore.load();		
com.increase.cen.document.AddDocmentWorkerorder = Ext.extend(Ext.form.FormPanel, {
	bodyStyle : "padding:5px;",
	frame : true,
	bodyBorder : false,
	labelAlign : "right",
	buttonAlign : 'center',
	labelWidth : 100,
	width : 500,
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
			html : '<img border="0" src="imgs/plwh/s.gif" width="28" height="21"> 第一步 <a target="_self" href="export.jsp?filename=docequttmpl">下载模板</a>并按模板格式整理设备，端子数据',
			anchor : '90%' // anchor width by percentage
		}
		this.two = {
			bodyStyle : 'padding: 10 0 10 50; border-width:0 0 0 0px',
			style : '',
			html : '<img border="0" src="imgs/plwh/s.gif" width="28" height="21"> 第二步 整理好的数据文件路径并选择设备配置型号，点击上传按钮进行配置工单生成。',
			anchor : '90%' // anchor width by percentage
		}
		
		var areaname = Ext.getDom("domainCode").value;
		this.areano = new Ext.form.TextField({
			xtype : 'textfield',
			id : 'uploadAreano',
			name : 'areano',
			hidden : 'true',
			value:areaname
		})
	

		this.file = {
			xtype : 'compositefield',
			items : [{
				xtype : 'combo',
				name : 'modelName',
				fieldLabel : '设备型号',
				// hideTrigger:true,
				editable : false,// 设置为false以阻止用户直接向该输入项输入文本
				store : rankStore,
				mode : "remote",// 加载本地数据 设置为'local' (默认值为 'remote'，它将从服务器加载数据)
				triggerAction : "all",// 下拉框获得了焦点或者被点击了
				displayField : "modelName",
				valueField : "modelId",
				//hiddenName : 'emodelId',// 创建一个表单隐藏域来存储输入项的值
				width : '150'

			},{
				xtype : 'textfield',
				fieldLabel : '',
				name : 'excle',
				inputType : 'file',
				allowBlank : false,
				width : '150'

			},{
				xtype : 'button',
				width : 25,
				tooltip : '添加档案导入框',
				tooltipType : 'qtip',
				icon : "js/ext/resources/images/default/dd/drop-add.gif",
				handler : this.addTextfield.createDelegate(this)
			}
			]
		}
		this.items = [{
			xtype : 'fieldset',
			id : 'fieldset2',
			title : '配置信息',
			autoScroll:true,
			width:580,
			items : [this.one, this.two, this.areano, this.file]
		}];

		// 按钮
		this.buttons = [{
			id : 'btnSubmit2',
			text : "上传",
			hidden : false,
			icon : "imgs/save_btn.png",
			cls : "x-btn-text-icon",
			tooltip : '保存信息',
			tooltipType : 'qtip',
			handler : this.btnSubmit2.createDelegate(this)
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
		com.increase.cen.document.AddDocmentWorkerorder.superclass.initComponent
				.call(this);
	},
	// 保存按钮事件
	btnSubmit2 : function() {
		//if (!this.getForm().isValid()) {
			//return;
		//}

		// 提交验证
		var excle = Ext.query("*[name=excle]");
		var ary = [];
		for (var i = 0; i < excle.length; i++) {
			if (excle[i].value != null && excle[i].value != '') {
				var filename = excle[i].value.substring(excle[i].value
						.lastIndexOf('\\')
						+ 1, excle[i].value.length);
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
			var filetype = ary[i].substring(ary[i].lastIndexOf('.') + 1,
					ary[i].length);
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
		var areaname = Ext.getDom("domainName").value;
		Ext.MessageBox.show({
			title : '确认',
			msg : '<div style="margin-top:5px;margin-left:10px;">您确定要将选择的档案全部上传到 "'+areaname+'" 吗?</div>',
			width : 300,
			multiline : false,// 设置为true，提示用户输入多行文本
			closable : false,
			buttons : {
				yes : '确定',
				no : '取消'
			},
			icon : Ext.MessageBox.QUESTION,
			fn : function(btn) {
				if (btn == 'yes') {// 如果选择确定，则进行修改
					parent.parent.Ext.Msg.wait("服务器正在处理档案……", '请稍候'); 
					Ext.getCmp("addDocmentWorkerorder").getForm().doAction("submit", {
						url : 'document!importWorkerorder.action',
						method : 'post',
						timeout: 1000*60*5,
						submitEmptyText : false,
						//waitMsg : '正在处理上传文件...请稍后...',
						success : function(form, action) {
							parent.parent.Ext.Msg.hide();
							Ext.getCmp('grid').getStore().load({
								params : {
									start : 0,
									limit : limit,
									"searchdequt.areano" : Ext.getDom("domainCode").value
								}
							});// 表格加载数据
							Ext.getCmp('grid').getView().refresh();// 表格刷新
							parent.Ext.Msg.show({
								title : '提示',
								width : 300,
								msg : '<img src="imgs/tip_success.png" align="center" hspace="30" />'
										+ '上传档案成功!',
								buttons : {
									ok : "确定"
								}
							});
							Ext.getCmp("addDocmentWorkerorder").getForm().reset();// 重置表单
							if(Ext.getCmp('addDocumentWindow2')!=null){
							
							Ext.getCmp('addDocumentWindow2').hide();// 窗口隐藏
							}

						},
						failure : function(form, action) {
							parent.parent.Ext.Msg.hide();
							var verifyMsg = action.result.verifyMsg;
							parent.Ext.Msg.show({
								title : '提示',
								width : 500,
								msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
										+ verifyMsg,
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
		Ext.getCmp("addDocmentWorkerorder").getForm().reset();// 重置表单
		if(Ext.getCmp('addDocumentWindow2')!=null){
			
				Ext.getCmp('addDocumentWindow2').hide();// 窗口隐藏
		}
		Ext.getCmp('grid').getStore().load({
			params : {
				start : 0,
				limit : limit,
				"searchdequt.areano" : Ext.getDom("domainCode").value
			}
		});// 表格加载数据
		Ext.getCmp('grid').getView().refresh();// 表格刷新
	},

	addTextfield : function() {
		var id = new Date().getTime().toString();
		var addFile = {
			xtype : 'compositefield',
			id : 'f' + id,
			bodyStyle : 'padding: 10 0 20 50; border-width:0 0 0 0px',
			items : [{
				xtype : 'combo',
				name : 'modelName',
				fieldLabel : '设备型号',
				// hideTrigger:true,
				editable : false,// 设置为false以阻止用户直接向该输入项输入文本
				store : rankStore,
				mode : "remote",// 加载本地数据 设置为'local' (默认值为 'remote'，它将从服务器加载数据)
				triggerAction : "all",// 下拉框获得了焦点或者被点击了
				displayField : "modelName",
				valueField : "modelId",
				//hiddenName : 'emodelId',// 创建一个表单隐藏域来存储输入项的值
				width : '150'
			},{
				xtype : 'textfield',
				fieldLabel : '',
				name : 'excle',
				inputType : 'file',
				width : '150'

			}, {
				xtype : 'button',
				width : 25,
				tooltip : '删除档案导入框',
				tooltipType : 'qtip',
				icon : "js/ext/resources/images/default/dd/drop-no.gif",
				handler : function(){
					var f = Ext.getCmp("f" + id);
					Ext.getCmp("fieldset2").remove(f);
					Ext.getCmp("fieldset2").doLayout();
				}
			}
			]
		}
		Ext.getCmp("fieldset2").add(addFile);
		Ext.getCmp("fieldset2").doLayout();
	}
});
Ext.reg("addDocmentWorkerorder", com.increase.cen.document.AddDocmentWorkerorder);
