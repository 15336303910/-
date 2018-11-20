Ext.namespace("com.increase.cen.route");
var limit = null;
com.increase.cen.route.mergeRoute = Ext.extend(Ext.form.FormPanel, {
	bodyStyle : "padding:5px;",
	frame : true,
	bodyBorder : false,
	labelAlign : "right",
	buttonAlign : 'center',
	labelWidth : 100,
	width : 800,
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
		// 从远程读取数据
		var proxy = new Ext.data.HttpProxy({
			url : "route!searchRoute.action"
			
		});
		//数据结构
		var route = Ext.data.Record.create([
			{name : 'routeid',type : 'string',mapping : 'routeid',hidden : true}, 
			{name : 'routename',type : 'string',mapping : 'routename'}, 
//			{name : 'routelevel',type : 'string',mapping : 'routelevel'}, 
//			{name : 'state',type : 'string',mapping : 'state'}, 
//			{name : 'startsite',type : 'startsite',mapping : 'startsite'}, 
//			{name : 'endsite',type : 'string',mapping : 'endsite'},
//			{name : 'sign',type : 'string',mapping : 'sign'}
		]);
		// Reader以提供的Ext.data.Record 构造器中的映射为基础，从JSON响应创建一个Ext.data.Record 对象数组
		var reader = new Ext.data.JsonReader({
			totalProperty : "total",
			root : "routes"
		}, route);
		
		 this.store = new Ext.data.Store({// 提供数据输入
			proxy : proxy,
			reader : reader,
			remoteSort : true
		});
    	this.store.load({
			
		});
    	this.routeName = new Ext.form.TextField({
    		id : "mergeRouteName",
			name : "mergeRoute.routename",
			fieldLabel : "合并后光缆名",
			allowBlank : false,
			blankText : '光缆名不能为空',
			width : 300
			
    	})
    	
    	this.levelStore = new Ext.data.SimpleStore({
    		fields : ['value', 'text'],
    		data : [['', ''],['1', '一级干线'], ['2', '二级干线'], ['3', '本地中继'], ['4', '本地核心'], ['5', '本地汇聚'], ['6', '本地接入']]
    	})
    	this.routeLevel = new Ext.form.ComboBox({
    		id : "mergeRouteLevel",
			name : "mergeRoute.routelevel",
			fieldLabel : "合并后光缆级别",
			typeAhead: true,
        	triggerAction: 'all',
			blankText : '光缆级别不能为空',
			forceSelection: true,
         	mode: 'local',
        	width : 300,
         	store: this.levelStore,
         	valueField: 'value',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
         	editable:false,//设置为false以阻止用户直接向该输入项输入文本
       		displayField: 'text',//下拉框显示内容
       		hiddenName:'mergeRoute.routelevel'//创建一个表单隐藏域来存储输入项的值
    	})
    	this.startSite = new Ext.form.TextField({
    		id : "mergeStartsite",
			name : "mergeRoute.startsite",
			fieldLabel : "合并后起始局站",
			allowBlank : false,
			blankText : '起始局站不能为空',
			width : 300
    	})
    	
    	this.endSite = new Ext.form.TextField({
    		id : "mergeEndSite",
			name : "mergeRoute.endsite",
			fieldLabel : "合并后终止局站",
			allowBlank : false,
			blankText : '终止局站不能为空',
			width : 300
    	})
    	
    	this.stateStore = new Ext.data.SimpleStore({
    		fields : ['value', 'text'],
    		data : [['', ''],['1', '割接封锁'], ['2', '正常'], ['3', '不可用']]
    	})
    	this.state = new Ext.form.ComboBox({
    		id : "mergeState",
			name : "mergeRoute.state",
			typeAhead: true,
        	triggerAction: 'all',
        	fieldLabel : "合并后维护状态",
        	blankText : '维护状态不能为空',
        	forceSelection: true,
         	mode: 'local',
        	width : 300,
         	store: this.stateStore,
         	valueField: 'value',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
         	editable:false,//设置为false以阻止用户直接向该输入项输入文本
       		displayField: 'text',//下拉框显示内容
       		hiddenName:'mergeRoute.state'//创建一个表单隐藏域来存储输入项的值
       		
    	})
    	
    	this.sign = new Ext.form.TextField({
    		id : "mergeSign",
			name : "mergeRoute.sign",
			fieldLabel : "合并后光缆标牌",
			width : 300
    	})
    	
		this.items = [{
			xtype : 'panel',
			bodyStyle : 'padding-left:78%;'
		}, {
			xtype : 'fieldset',
			title : '光缆信息',
			collapsible : true,
			items:[{
				xtype:"itemselector",
	            id:'roleItemSelector',
	            name:"ids",
	            allowBlank : false,
	             hideLabel : true,
	           // fieldLabel:"ItemSelector",
	            dataFields:["routeid", "routename"],
	            toData:[],
	            fromData:[],
	            msWidth:300,
	            msHeight:200,
	            valueField:"routeid",
	            displayField:"routename",
	            imagePath:"imgs/itemselector",
	            toLegend:'<span style="font-size:14px;">已选择合并光缆</span>',
	            fromLegend:'<span style="font-size:14px;">可选择合并光缆</span>',
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
			title : '新光缆信息',
			//collapsible : true,
			items : [this.routeName,this.routeLevel,this.startSite,this.endSite,this.state,this.sign]
		}];
	
		
		// 按钮
		this.buttons = [{
			id : 'mergeBtnSubmit',
			text : "合并",
			hidden : false,
			icon : "imgs/save_btn.png",
			cls : "x-btn-text-icon",
			tooltip : '合并光缆信息',
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
        		 var routename = node.innerHTML;
       			 Ext.getCmp("mergeRouteName").setValue(routename);
        		this.fireEvent('change', this, this.getValue(), this.hiddenField.dom.value);
        		this.hiddenField.dom.value = this.getValue();
        		this.fireEvent('click', this, e);
        		this.validate();        
   		 	}	
		});

		// 重写initComponent superclass.initComponent.call(this);来调用父类的初始化方法.
		com.increase.cen.route.mergeRoute.superclass.initComponent.call(this);
	},
	// 保存按钮事件
	mergeBtnSubmit : function() {
		if (!this.getForm().isValid()) {
			return;
		}
		this.getForm().doAction("submit", {
			url : 'route!mergeRoute.action',
			method : 'post',
			submitEmptyText : false,
			success : function(form, action) {
				Ext.Msg.show({
					title : '提示',
					width : 300,
					msg : '<img src="imgs/tip_success.png" align="center" hspace="30" />'
							+ '合并光缆成功!',
					buttons : {
						ok : "确定"
					}
				});
				Ext.getCmp("mergeRouteGrid").getForm().reset();// 重置表单
				Ext.getCmp('mergeRouteWindow').hide();// 窗口隐藏
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
							+ '合并光缆失败',
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
		Ext.getCmp("mergeRouteGrid").getForm().reset();// 重置表单
		Ext.getCmp('mergeRouteWindow').hide();// 窗口隐藏
		Ext.getCmp('routeGrid').getStore().load({
			params : {
				start : 0,
				limit : limit
			}
		});// 表格加载数据
	}
	
});
Ext.reg("mergeRouteGrid", com.increase.cen.route.mergeRoute);
