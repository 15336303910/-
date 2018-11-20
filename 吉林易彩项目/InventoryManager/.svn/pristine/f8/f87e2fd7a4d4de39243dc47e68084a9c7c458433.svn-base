Ext.namespace("com.increase.cen.cable");
var limit = 10;
com.increase.cen.cable.MergeCable = Ext.extend(Ext.form.FormPanel,{
	bodyStyle : "padding:5px;",
	frame : true,
	bodyBorder : false,
	labelAlign : "right",
	buttonAlign : 'center',
	labelWidth : 100,
	width : 500,
	height : 630,
	initComponent:function(config){
		// 一页显示多少条数据
		//limit = Ext.getDom("limit").value;
		// 启动悬停提示
		Ext.QuickTips.init();
		// 提示的方式
		Ext.form.Field.prototype.msgTarget = 'qtip';
		// 光缆段信息是否存在
		var idIsExist = true;
		
		//所属光缆
		this.routename = new Ext.form.TextField({
    		id : "mergeCable.newRoutename",
			name : "mergeCable.newRoutename",
			fieldLabel : "所属光缆",
			readOnly:true,
			width : 300
    	})
		// 光缆段名
		this.cablename = new Ext.form.TextField({
			id : "mergeCable.newCablename",
			name : "mergeCable.newCablename",
			fieldLabel : "合并后光缆段名称",
			allowBlank : false,// 验证是否为空
			blankText : '光缆段名称不能为空',
			width : 300,
			emptyText : "请输入光缆段名称",
			regex:/[a-zA-z0-9\u4E00-\u9FA5]*/,
			regexText : '请输入正确光缆段名称'
			
		});
		this.starteid = new Ext.form.TextField({
    		id : "mergeCable.starteid",
			name : "mergeCable.starteid",
			width : 5,
			hidden : true
    	})
		//起始设施名称
		this.startDeviceName = {
			layout : 'column', // 从左往右布局
			items : [{
				columnWidth : 1.0, // 该列有整行中所占百分比
				height : 30,
				layout : 'form', // 从上往下布局
				items : [{
					xtype : 'textfield',
					id : "mergeCable.starteid",
					name : "mergeCable.starteid",
					width : 5,
					hidden : true
				}, {
					xtype : 'compositefield',
					items : [{
						id : 'mergeCable.startDeviceName',
						name : 'mergeCable.startDeviceName',
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
					id : "mergeCable.endeid",
					name : "mergeCable.endeid",
					width : 5,
					hidden : true
				}, {
					xtype : 'compositefield',
					items : [{
						id : 'mergeCable.endDeviceName',
						name : 'mergeCable.endDeviceName',
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
    		id : "mergeCable.type",
			name : "mergeCable.type",
			emptyText : "请输入缆线类型",
			fieldLabel : "合并后缆线类型",
			blankText : '缆线类型不能为空',
			width : 300
    	})
    	this.rowno = new Ext.form.TextField({
    		id : "rowno",
			name : "rowno",
			width : 5,
			hidden : true
    	})
		//光缆段型号
		this.mode = new Ext.form.TextField({
    		id : "mergeCable.mode",
			name : "mergeCable.mode",
			emptyText : "请输入光缆段型号",
			fieldLabel : "合并后光缆段型号",
			blankText : '光缆段型号不能为空',
			width : 300
    	})
		//光缆段长度
		this.length = new Ext.form.TextField({
    		id : "mergeCable.length",
			name : "mergeCable.length",
			emptyText : "请输入光缆段长度只能为大于零的正整数",
			fieldLabel : "合并后光缆段长度",
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
					Ext.getCmp('mergeCable.length').markInvalid('填写格式不正确，请重新填写!');
					return false;
				}else{
					return true;
				}
			}
    	})
    	
    	//铺设方式
		this.paveWay = new Ext.form.TextField({
    		id : "mergeCable.paveWay",
			name : "mergeCable.paveWay",
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
    		id : "mergeCable.state",
			name : "mergeCable.state",
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
       		hiddenName:'mergeCable.state'//创建一个表单隐藏域来存储输入项的值
    	})
    	
    	
    	//共建/共享类型
    	this.sharetypeStore = new Ext.data.SimpleStore({
    		fields : ['value', 'text'],
    		data : [['', ''],['1', '共建'], ['2', '共享(我方共享他方)'], ['3', '共享(他方共享我方)'], ['4', '自建自用'], ['5', '自建预留']]
    	})
    	this.sharetype = new Ext.form.ComboBox({
    		id : "mergeCable.sharetype",
			name : "mergeCable.sharetype",
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
       		hiddenName:'mergeCable.sharetype',//创建一个表单隐藏域来存储输入项的值
       		editable:false//设置为false以阻止用户直接向该输入项输入文本
    	})
    	
    	
    	//铺设方式
		this.shareoperator = new Ext.form.TextField({
    		id : "mergeCable.shareoperator",
			name : "mergeCable.shareoperator",
			emptyText : "请输入共建/共享方",
			fieldLabel : "共建/共享方",
			blankText : '共建/共享方不能为空',
			width : 300
    	})
    	//远程数据源
    	var proxy = new Ext.data.HttpProxy({
			url : "route!searchCable.action"
			
		});
    	//数据结构
		var cable = Ext.data.Record.create([
			{name : 'cableid',type : 'string',mapping : 'cableid',hidden : true}, 
			{name : 'cablename',type : 'string',mapping : 'cablename'}, 
		]);
		
		var reader = new Ext.data.JsonReader({
				totalProperty : "total",
				root : "cables"
		}, cable);
		this.cStore = new Ext.data.Store({// 提供数据输入
			id:'cStore',
			proxy : proxy,
			reader : reader,
			remoteSort : true
		});
		
//		this.cStore.load({
//			params : {
//				'searchCable.routename' : Ext.getCmp("mergeCable.routename").getValue()
//			}
//		});
		
    	this.items = [{
			xtype : 'panel',
			bodyStyle : 'padding-left:78%;'
		}, {
			xtype : 'fieldset',
			title : '光缆段信息',
			collapsible : true,
			items:[{
				xtype:"itemselector",
	            id:'cableItemSelector',
	            name:"ids",
	            allowBlank : false,
	             hideLabel : true,
	           // fieldLabel:"ItemSelector",
	            dataFields:["cableid", "cablename"],
	            toData:[],
	            fromData:[],
	            msWidth:300,
	            msHeight:200,
	            valueField:"cableid",
	            displayField:"cablename",
	            imagePath:"imgs/itemselector",
	            toLegend:'<span style="font-size:14px;">已选择合并光缆段</span>',
	            fromLegend:'<span style="font-size:14px;">可选择合并光缆段</span>',
	            //toStore:,
	            fromStore:this.cStore,//角色信息数据源
	            toTBar : [{
					text:"清除所有",
					handler : function() {
						Ext.getCmp("cableItemSelector").reset();
					}
				}]
			}]
		}, {
			xtype : 'fieldset',
			title : '合并后光缆段信息',
			//collapsible : true,
			items : [this.routename,this.cablename,this.startDeviceName,this.endDeviceName,this.type,this.mode,this.length,this.paveWay,this.state,this.sharetype,this.shareoperator]
		}];
    	
		// 按钮
		this.buttons = [{
			id : 'mergeCableBtnSubmit',
			text : "保存",
			hidden : false,
			icon : "imgs/save_btn.png",
			cls : "x-btn-text-icon",
			tooltip : '保存光缆信息',
			tooltipType : 'qtip',
			handler : this.mergeCableBtnSubmit.createDelegate(this)
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
        		 var routename = node.innerHTML;
       			 Ext.getCmp("mergeCable.newCablename").setValue(routename);
        		this.fireEvent('change', this, this.getValue(), this.hiddenField.dom.value);
        		this.hiddenField.dom.value = this.getValue();
        		this.fireEvent('click', this, e);
        		this.validate();        
   		 	}	
		});
    	
		com.increase.cen.cable.MergeCable.superclass.initComponent.call(this);
	},
	
	// 保存按钮事件
	mergeCableBtnSubmit : function() {
		if (!this.getForm().isValid()) {
			return;
		}
		var rowno=Ext.getCmp("rowno").getValue();
		this.getForm().doAction("submit", {
			url : 'route!mergeCable.action',
			method : 'post',
			submitEmptyText : false,
			success : function(form, action) {
				Ext.Msg.show({
					title : '提示',
					width : 300,
					msg : '<img src="imgs/tip_success.png" align="center" hspace="30" />'
							+ '合并光缆段成功!',
					buttons : {
						ok : "确定"
					}
				});
				Ext.getCmp("mergeCable").getForm().reset();// 重置表单
				Ext.getCmp('mergeCableWindow').hide();// 窗口隐藏
				Ext.getCmp('cableGrid'+rowno).getStore().load({
					params : {
						start : 0,
						limit : limit
					}
				});// 表格加载数据
				Ext.getCmp('cableGrid'+rowno).getView().refresh();// 表格刷新
			},
			failure : function(form, action) {
				Ext.Msg.show({
					title : '提示',
					width : 300,
					msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
							+ '合并光缆段失败',
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
		var rowno=Ext.getCmp("rowno").getValue();
		Ext.getCmp("mergeCable").getForm().reset();// 重置表单
		Ext.getCmp('mergeCableWindow').hide();// 窗口隐藏
		Ext.getCmp('cableGrid'+rowno).getStore().load({
			params : {
				start : 0,
				limit : limit
			}
		});// 表格加载数据
	}
});
Ext.reg("mergeCable",com.increase.cen.cable.MergeCable);