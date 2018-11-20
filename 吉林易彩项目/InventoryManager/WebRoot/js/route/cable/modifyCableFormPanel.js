Ext.namespace("com.increase.cen.cable");
var limit = 10;
com.increase.cen.cable.ModifyCable = Ext.extend(Ext.form.FormPanel,{
	bodyStyle : "padding:5px;",
	frame : true,
	bodyBorder : false,
	labelAlign : "right",
	buttonAlign : 'center',
	labelWidth : 100,
	width : 500,
	height : 380,
	initComponent:function(config){
		// 一页显示多少条数据
		//limit = Ext.getDom("limit").value;
		// 启动悬停提示
		Ext.QuickTips.init();
		// 提示的方式
		Ext.form.Field.prototype.msgTarget = 'qtip';
		// 光缆段信息是否存在
		var idIsExist = true;
		
		//id
		this.cableid = new Ext.form.Hidden({
    		id : "updateCable.cableid",
			name : "updateCable.cableid",
			fieldLabel : "cableid",
			readOnly:true,
			width : 300
			
    	})
    	this.rowno = new Ext.form.TextField({
    		id : "rowno",
			name : "rowno",
			width : 5,
			hidden : true
    	})
		//所属光缆
		this.routename = new Ext.form.TextField({
    		id : "updateCable.routename",
			name : "updateCable.routename",
			fieldLabel : "所属光缆",
			readOnly:true,
			width : 300
    	})
    	this.routeid = new Ext.form.TextField({
    		id : "updateCable.routeid",
			name : "updateCable.routeid",
			fieldLabel : "所属光缆id",
			hidden:true,
			width : 300
    	})
		//光缆段名
		this.cablename = new Ext.form.Hidden({
    		id : "updateCable.cablename",
			name : "updateCable.cablename",
			fieldLabel : "cablename",
			readOnly:true,
			width : 300
			
    	})
		// 光缆段名
		this.newCablename = new Ext.form.TextField({
			id : "updateCable.newCablename",
			name : "updateCable.newCablename",
			fieldLabel : "光缆段名称",
			allowBlank : false,// 验证是否为空
			blankText : '光缆段名称不能为空',
			width : 300,
			emptyText : "请输入光缆段名称",
			regex:/[a-zA-z0-9\u4E00-\u9FA5]*/,
			regexText : '请输入正确光缆段名称',
			invalidText : '光缆段名称已存在！',
			validationEvent : 'keydown',
			validationDelay : 100,
			validator : function(thisText) {
				// 没有输入，不验证
				if (thisText == "") {
					return true;
				}
				Ext.Ajax.request({
					url : 'route!verifyCable.action',
					method : 'post',
					// async:false,
					params : {
						'newCable.cableid' : Ext.getCmp("updateCable.cableid").getValue(),
						'newCable.cablename' : thisText,
						'newCable.routename' : Ext.getCmp("updateCable.routename").getValue()
					},
					success : function(response) {
						var res = Ext.util.JSON.decode(response.responseText);
						var msg = res.success;
						if (msg == false) {
							Ext.getCmp('updateCable.newCablename').markInvalid('光缆段名称已存在!');
						}
					}
				});
				return idIsExist;
			}
		});
		//起始设施名称
		this.startDeviceName = {
			layout : 'column', // 从左往右布局
			items : [{
				columnWidth : 1.0, // 该列有整行中所占百分比
				height : 30,
				layout : 'form', // 从上往下布局
				items : [{
					xtype : 'textfield',
					id : "updateCable.starteid",
					name : "updateCable.starteid",
					width : 5,
					hidden : true
				}, {
					xtype : 'compositefield',
					items : [{
						id : 'updateCable.startDeviceName',
						name : 'updateCable.startDeviceName',
						xtype : 'textfield',
						emptyText : "请选择终止设施",
						fieldLabel : '起始设备名称',
						width : 300,
						readOnly : true
					}, {
						xtype : 'button',
						text : '...',
						width : 23,
						handler : function() {
							showEqutGrid_a();
						}
					}]
				}]
			}]
    	}
    	//终止设施名称
		this.endDeviceName = {
			layout : 'column', // 从左往右布局
			items : [{
				columnWidth : 1.0, // 该列有整行中所占百分比
				height : 30,
				layout : 'form', // 从上往下布局
				items : [{
					xtype : 'textfield',
					id : "updateCable.endeid",
					name : "updateCable.endeid",
					width : 5,
					hidden : true
				}, {
					xtype : 'compositefield',
					items : [{
						id : 'updateCable.endDeviceName',
						name : 'updateCable.endDeviceName',
						xtype : 'textfield',
						emptyText : "请选择终止设施",
						fieldLabel : '终止设备名称',
						width : 300,
						readOnly : true
					}, {
						xtype : 'button',
						text : '...',
						width : 23,
						handler : function() {
							showEqutGrid_z();
						}
					}]
				}]
			}]
    	}
		//缆线类型
		this.type = new Ext.form.TextField({
    		id : "updateCable.type",
			name : "updateCable.type",
			emptyText : "请输入缆线类型",
			fieldLabel : "缆线类型",
			blankText : '缆线类型不能为空',
			width : 300
    	})
    	//光缆段型号
		this.mode = new Ext.form.TextField({
    		id : "updateCable.mode",
			name : "updateCable.mode",
			emptyText : "请输入光缆段型号",
			fieldLabel : "光缆段型号",
			blankText : '光缆段型号不能为空',
			width : 300
    	})
		//光缆段长度
		this.length = new Ext.form.TextField({
    		id : "updateCable.length",
			name : "updateCable.length",
			emptyText : "请输入光缆段长度只能为大于零的正整数",
			fieldLabel : "光缆段长度",
			blankText : '光缆段长度不能为空',
			width : 300,
			invalidText : '请输入正确的光缆段长度！',
			validationEvent : 'keydown',
			validationDelay : 100,
			validator : function(thisText) {
				if (thisText == "") {
					return true;
				}
				var reg=/^[1-9]\d*$/;
				if(!reg.test(thisText)){
					Ext.getCmp('updateCable.length').markInvalid('填写格式不正确，请重新填写!');
					return false;
				}else{
					return true;
				}
			}
    	})
		
    	//铺设方式
		this.paveWay = new Ext.form.TextField({
    		id : "updateCable.paveWay",
			name : "updateCable.paveWay",
			emptyText : "请输入铺设方式",
			fieldLabel : "铺设方式",
			blankText : '铺设方式不能为空',
			width : 300
    	})
		//维护状态
    	this.stateStore = new Ext.data.SimpleStore({
    		fields : ['value', 'text'],
    		data : [['', ''],['1', '割接封锁'], ['2', '正常'], ['3', '不可用']]
    	})
    	this.state = new Ext.form.ComboBox({
    		id : "updateCable.state1",
    		name : "updateCable.state",
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
       		editable:false,//设置为false以阻止用户直接向该输入项输入文本
       		hiddenName:'updateCable.state'//创建一个表单隐藏域来存储输入项的值
    	})
    	
    	//维护状态
    	this.sharetypeStore = new Ext.data.SimpleStore({
    		fields : ['value', 'text'],
    		data : [['', ''],['1', '共建'], ['2', '共享(我方共享他方)'], ['3', '共享(他方共享我方)'], ['4', '自建自用'], ['5', '自建预留']]
    	})
    	this.sharetype = new Ext.form.ComboBox({
    		id : "updateCable.sharetype1",
    		name : "updateCable.sharetype",
			typeAhead: true,
        	triggerAction: 'all',
        	fieldLabel : "共建/共享类型",
        	blankText : '共建/共享类型不能为空',
        	emptyText : "请选择共建/共享类型",
        	forceSelection: true,
         	mode: 'local',
        	width : 300,
         	store: this.sharetypeStore,
         	valueField: 'value',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
       		displayField: 'text',//下拉框显示内容
       		hiddenName:'updateCable.sharetype',//创建一个表单隐藏域来存储输入项的值
       		editable:false//设置为false以阻止用户直接向该输入项输入文本
    	})
    	
    	//共建/共享方
		this.shareoperator = new Ext.form.TextField({
    		id : "updateCable.shareoperator",
			name : "updateCable.shareoperator",
			emptyText : "请输入共建/共享方",
			fieldLabel : "共建/共享方",
			blankText : '共建/共享方不能为空',
			width : 300
    	})
    	
    	this.items = [{
			xtype : 'panel',
			bodyStyle : 'padding-left:78%;'
		}, {
			xtype : 'fieldset',
			title : '光缆段信息',
			collapsible : true,
			items : [this.cableid,this.routeid,this.routename,this.cablename,this.newCablename,this.startDeviceName,this.endDeviceName,this.type,this.mode,this.length,this.paveWay,this.state,this.sharetype,this.shareoperator]
		}];
		// 按钮
		this.buttons = [{
			id : 'updateCableBtnSubmit',
			text : "保存",
			hidden : false,
			icon : "imgs/save_btn.png",
			cls : "x-btn-text-icon",
			tooltip : '保存光缆信息',
			tooltipType : 'qtip',
			handler : this.updateCableBtnSubmit.createDelegate(this)
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
		
		com.increase.cen.cable.ModifyCable.superclass.initComponent.call(this);
	},
	// 提交事件
	updateCableBtnSubmit : function submitForm() {
		if (!this.getForm().isValid()) {
			return;
		}
		var rowno=Ext.getCmp("rowno").getValue();
		this.getForm().doAction("submit", {
			url : 'route!updateCableBean.action',
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
				Ext.getCmp('modifyCableWindow').hide();
				Ext.getCmp("cableGrid"+rowno).getStore().reload();
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
		var rowno=Ext.getCmp("rowno").getValue();
		// 重置表单
		Ext.getCmp("modifyCable").getForm().reset();
		// 窗口隐藏
		Ext.getCmp('modifyCableWindow').hide();
		// 刷新表格
		Ext.getCmp("cableGrid"+rowno).getStore().reload();
	}
});
Ext.reg("modifyCable",com.increase.cen.cable.ModifyCable);