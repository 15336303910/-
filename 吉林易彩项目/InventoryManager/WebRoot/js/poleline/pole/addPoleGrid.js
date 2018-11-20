Ext.namespace("com.increase.cen.poleline");
var limit = null;
com.increase.cen.poleline.AddPole = Ext.extend(Ext.form.FormPanel, {
	bodyStyle : "padding:5px;",
	frame : true,
	bodyBorder : false,
	labelAlign : "right",
	buttonAlign : 'center',
	autoScroll:true,
	labelWidth : 120,
	width : 650,
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
		this.poleBatchAdd = new Ext.form.Checkbox({
			id : 'poleBatchAdd',
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
		var row1={
			layout : 'column',
			items:[{
				height : 30,
				layout : 'form', // 从上往下布局
				items : [{
					id : 'addPoleName',
					name : "poleInfoBean.poleName",
					xtype : 'textfield',
					fieldLabel : "电杆名称",
					allowBlank : false,
					blankText : '电杆名称不能为空',
					emptyText : "请输入电杆名称",
					width : 150
				}]
			},{
				layout : 'form',
				items : [{
					id : 'addPoleNo',
					name : "poleInfoBean.poleNo",
					xtype : 'textfield',
					fieldLabel : "序号",
					allowBlank : false,
					blankText : '序号不能为空',
					emptyText : "请输入序号且只能为数字",
					width : 150,
					invalidText : '请输入正确的序号！',
					validationEvent : 'keydown',
					validationDelay : 100,
					validator : function(thisText) {
						if (thisText == "") {
							return true;
						}
						var reg=/^[0-9]\d*$/;
						if(!reg.test(thisText)){
							Ext.getCmp('addPoleNo').markInvalid('填写格式不正确，请重新填写!');
							return false;
						}else{
							return true;
						}
					}
				}]
			}]
		}
		/*var poleName=new Ext.form.TextField({
			id : 'addPoleName',
			name : "poleInfoBean.poleName",
			fieldLabel : "电杆名称",
			allowBlank : false,
			blankText : '电杆名称不能为空',
			emptyText : "请输入电杆名称",
			width : 300
		});
		var poleNo=new Ext.form.TextField({
			id : 'addPoleNo',
			name : "poleInfoBean.poleNo",
			fieldLabel : "序号",
			allowBlank : false,
			blankText : '序号不能为空',
			emptyText : "请输入序号且只能为数字",
			width : 300,
			invalidText : '请输入正确的序号！',
			validationEvent : 'keydown',
			validationDelay : 100,
			validator : function(thisText) {
				if (thisText == "") {
					return true;
				}
				var reg=/^[0-9]\d*$/;
				if(!reg.test(thisText)){
					Ext.getCmp('addPoleNo').markInvalid('填写格式不正确，请重新填写!');
					return false;
				}else{
					return true;
				}
			}
		});*/
		var row2={
			layout : 'column',
			items:[{
				height : 30,
				layout : 'form', // 从上往下布局
				items : [{
					id : 'addPoleSubNo',
					name : "poleInfoBean.poleSubNo",
					xtype : 'textfield',
					fieldLabel : "扩充后缀",
					emptyText : "请输入扩充后缀且只能为数字",
					width : 150,
					invalidText : '请输入正确的扩充后缀！',
					validationEvent : 'keydown',
					validationDelay : 100,
					validator : function(thisText) {
						if (thisText == "") {
							return true;
						}
						var reg=/^[0-9]\d*$/;
						if(!reg.test(thisText)){
							Ext.getCmp('addPoleSubNo').markInvalid('填写格式不正确，请重新填写!');
							return false;
						}else{
							return true;
						}
					}
				}]
			},{
				layout : 'form', // 从上往下布局
				items : [{
					id : 'addPoleCode',
					name : "poleInfoBean.poleCode",
					xtype : 'textfield',
					fieldLabel : "电杆编码",
					emptyText : "请输入电杆编码",
					width : 150
				}]
			}]
		}
		/*var poleSubNo=new Ext.form.TextField({
			id : 'addPoleSubNo',
			name : "poleInfoBean.poleSubNo",
			fieldLabel : "扩充后缀",
			emptyText : "请输入扩充后缀且只能为数字",
			width : 300,
			invalidText : '请输入正确的扩充后缀！',
			validationEvent : 'keydown',
			validationDelay : 100,
			validator : function(thisText) {
				if (thisText == "") {
					return true;
				}
				var reg=/^[0-9]\d*$/;
				if(!reg.test(thisText)){
					Ext.getCmp('addPoleSubNo').markInvalid('填写格式不正确，请重新填写!');
					return false;
				}else{
					return true;
				}
			}
		});
		var poleCode=new Ext.form.TextField({
			id : 'addPoleCode',
			name : "poleInfoBean.poleCode",
			fieldLabel : "电杆编码",
			emptyText : "请输入电杆编码",
			width : 300
		});*/
		this.typeStore = new Ext.data.SimpleStore({
    		fields : ['value', 'text'],
    		data : [['', ''],['1', '普通杆'], ['2', '单接杆'], ['3', '双接杆'], ['4', 'H型杆'], ['5', 'A型杆'], ['6', 'L型杆'],['7','三角杆'],
    		['8', '井型杆'], ['9', '引上杆'], ['10', '终端杆'], ['11', '角杆杆'], ['12', '分歧杆'], ['13', 'T型杆'],['14','跨路杆']]
    	})
    	this.materialStore = new Ext.data.SimpleStore({
    		fields : ['value', 'text'],
    		data : [['', ''],['1', '钢筋混凝土电杆'], ['2', '木质电杆'], ['3', '铁质电杆']]
    	})
    	var row3={
    		layout:'column',
    		items:[{
    			height : 30,
				layout : 'form', // 从上往下布局
				items : [{
					id : "addPoleTypeEnumId",
					name : "poleInfoBean.poleTypeEnumId",
					xtype:'combo',
					typeAhead: true,
		        	triggerAction: 'all',
					fieldLabel : "电杆类型",
					emptyText : "请选择电杆类型",
					blankText : '电杆类型不能为空',
					forceSelection: true,
		         	mode: 'local',
		        	width : 150,
		         	store: this.typeStore,
		         	valueField: 'value',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
		       		displayField: 'text',//下拉框显示内容
		       		hiddenName:'poleInfoBean.poleTypeEnumId',//创建一个表单隐藏域来存储输入项的值
		       		editable:false//设置为false以阻止用户直接向该输入项输入文本
				}]
    		},{
				layout : 'form', // 从上往下布局
				items : [{
					id : "addPoleMaterial",
					name : "poleInfoBean.poleMaterial",
					xtype:'combo',
					typeAhead: true,
		        	triggerAction: 'all',
					fieldLabel : "电杆材质",
					emptyText : "请选择电杆材质",
					blankText : '电杆材质不能为空',
					forceSelection: true,
		         	mode: 'local',
		        	width : 150,
		         	store: this.materialStore,
		         	valueField: 'value',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
		       		displayField: 'text',//下拉框显示内容
		       		hiddenName:'poleInfoBean.poleMaterial',//创建一个表单隐藏域来存储输入项的值
		       		editable:false//设置为false以阻止用户直接向该输入项输入文本
				}]
    		}]
    	}
		/*var poleTypeEnumId=new Ext.form.ComboBox({
    		id : "addPoleTypeEnumId",
			name : "poleInfoBean.poleTypeEnumId",
			typeAhead: true,
        	triggerAction: 'all',
			fieldLabel : "电杆类型",
			emptyText : "请选择电杆类型",
			blankText : '电杆类型不能为空',
			forceSelection: true,
         	mode: 'local',
        	width : 300,
         	store: this.typeStore,
         	valueField: 'value',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
       		displayField: 'text',//下拉框显示内容
       		hiddenName:'poleInfoBean.poleTypeEnumId',//创建一个表单隐藏域来存储输入项的值
       		editable:false//设置为false以阻止用户直接向该输入项输入文本
    	});
    	var poleMaterial=new Ext.form.ComboBox({
    		id : "addPoleMaterial",
			name : "poleInfoBean.poleMaterial",
			typeAhead: true,
        	triggerAction: 'all',
			fieldLabel : "电杆材质",
			emptyText : "请选择电杆材质",
			blankText : '电杆材质不能为空',
			forceSelection: true,
         	mode: 'local',
        	width : 300,
         	store: this.materialStore,
         	valueField: 'value',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
       		displayField: 'text',//下拉框显示内容
       		hiddenName:'poleInfoBean.poleMaterial',//创建一个表单隐藏域来存储输入项的值
       		editable:false//设置为false以阻止用户直接向该输入项输入文本
    	});*/
    	var row4={
    		layout:'column',
    		items:[{
    			height:30,
    			layout:'form',
    			items:[{
    				id : 'addPoleLength',
					name : "poleInfoBean.poleLength",
					xtype : 'textfield',
					fieldLabel : "电杆程式（米）",
					emptyText : "请输入电杆程式",
					width : 150,
					invalidText : '请输入正确的电杆程式！',
					validationEvent : 'keydown',
					validationDelay : 100,
					validator : function(thisText) {
						if (thisText == "") {
							return true;
						}
						var reg=/^[0-9]+\.{0,1}\d*$/;
						if(!reg.test(thisText)){
							Ext.getCmp('addPoleLength').markInvalid('填写格式不正确，请重新填写!');
							return false;
						}else{
							return true;
						}
					}
    			}]
    		},{
    			layout:'form',
    			items:[{
    				id : 'addBuriedDepth',
					name : "poleInfoBean.buriedDepth",
					xtype : 'textfield',
					fieldLabel : "电杆埋深（米）",
					emptyText : "请输入电杆埋深",
					width : 150,
					invalidText : '请输入正确的电杆埋深！',
					validationEvent : 'keydown',
					validationDelay : 100,
					validator : function(thisText) {
						if (thisText == "") {
							return true;
						}
						var reg=/^[0-9]+\.{0,1}\d*$/;
						if(!reg.test(thisText)){
							Ext.getCmp('addBuriedDepth').markInvalid('填写格式不正确，请重新填写!');
							return false;
						}else{
							return true;
						}
					}
    			}]
    		}]
    	}
    	/*var poleLength=new Ext.form.TextField({
			id : 'addPoleLength',
			name : "poleInfoBean.poleLength",
			fieldLabel : "电杆程式（米）",
			emptyText : "请输入电杆程式",
			width : 300,
			invalidText : '请输入正确的电杆程式！',
			validationEvent : 'keydown',
			validationDelay : 100,
			validator : function(thisText) {
				if (thisText == "") {
					return true;
				}
				var reg=/^[0-9].*$/;
				if(!reg.test(thisText)){
					Ext.getCmp('addPoleLength').markInvalid('填写格式不正确，请重新填写!');
					return false;
				}else{
					return true;
				}
			}
		});
		var buriedDepth=new Ext.form.TextField({
			id : 'addBuriedDepth',
			name : "poleInfoBean.buriedDepth",
			fieldLabel : "电杆埋深（米）",
			emptyText : "请输入电杆埋深",
			width : 300,
			invalidText : '请输入正确的电杆埋深！',
			validationEvent : 'keydown',
			validationDelay : 100,
			validator : function(thisText) {
				if (thisText == "") {
					return true;
				}
				var reg=/^[0-9].*$/;
				if(!reg.test(thisText)){
					Ext.getCmp('addBuriedDepth').markInvalid('填写格式不正确，请重新填写!');
					return false;
				}else{
					return true;
				}
			}
		});*/
		var row5={
    		layout:'column',
    		items:[{
    			height:30,
    			layout:'form',
    			items:[{
    				id : 'addPoleRadius',
					name : "poleInfoBean.poleRadius",
					xtype : 'textfield',
					fieldLabel : "电杆半径（厘米）",
					emptyText : "请输入电杆半径",
					width : 150,
					invalidText : '请输入正确的电杆半径！',
					validationEvent : 'keydown',
					validationDelay : 100,
					validator : function(thisText) {
						if (thisText == "") {
							return true;
						}
						var reg=/^[0-9]+\.{0,1}\d*$/;
						if(!reg.test(thisText)){
							Ext.getCmp('addPoleRadius').markInvalid('填写格式不正确，请重新填写!');
							return false;
						}else{
							return true;
						}
					}
    			}]
    		},{
    			layout:'form',
    			items:[{
    				id : 'addPoleAddress',
					name : "poleInfoBean.poleAddress",
					xtype : 'textfield',
					fieldLabel : "电杆地址",
					emptyText : "请输入电杆地址",
					width : 150
    			}]
    		}]
    	}
		/*var poleRadius=new Ext.form.TextField({
			id : 'addPoleRadius',
			name : "poleInfoBean.poleRadius",
			fieldLabel : "电杆半径（厘米）",
			emptyText : "请输入电杆半径",
			width : 300,
			invalidText : '请输入正确的电杆半径！',
			validationEvent : 'keydown',
			validationDelay : 100,
			validator : function(thisText) {
				if (thisText == "") {
					return true;
				}
				var reg=/^[0-9].*$/;
				if(!reg.test(thisText)){
					Ext.getCmp('addPoleRadius').markInvalid('填写格式不正确，请重新填写!');
					return false;
				}else{
					return true;
				}
			}
		});
		var poleAddress=new Ext.form.TextField({
			id : 'addPoleAddress',
			name : "poleInfoBean.poleAddress",
			fieldLabel : "电杆地址",
			emptyText : "请输入电杆地址",
			width : 300
		});*/
		var row6={
    		layout:'column',
    		items:[{
    			height:30,
    			layout:'form',
    			items:[{
    				id : 'addPoleLongitude',
					name : "poleInfoBean.poleLongitude",
					xtype : 'textfield',
					fieldLabel : "电杆经度",
					emptyText : "请输入电杆经度",
					width : 150,
					invalidText : '请输入正确的电杆经度！',
					validationEvent : 'keydown',
					validationDelay : 100,
					validator : function(thisText) {
						if (thisText == "") {
							return true;
						}
						var reg=/^[0-9]+\.{0,1}\d*$/;
						if(!reg.test(thisText)){
							Ext.getCmp('addPoleLongitude').markInvalid('填写格式不正确，请重新填写!');
							return false;
						}else{
							return true;
						}
					}
    			}]
    		},{
    			layout:'form',
    			items:[{
    				id : 'addPoleLatitude',
					name : "poleInfoBean.poleLatitude",
					xtype : 'textfield',
					fieldLabel : "电杆纬度",
					emptyText : "请输入电杆纬度",
					width : 150,
					invalidText : '请输入正确的电杆纬度！',
					validationEvent : 'keydown',
					validationDelay : 100,
					validator : function(thisText) {
						if (thisText == "") {
							return true;
						}
						var reg=/^[0-9]+\.{0,1}\d*$/;
						if(!reg.test(thisText)){
							Ext.getCmp('addPoleLatitude').markInvalid('填写格式不正确，请重新填写!');
							return false;
						}else{
							return true;
						}
					}
    			}]
    		}]
    	}
		/*var poleLongitude=new Ext.form.TextField({
			id : 'addPoleLongitude',
			name : "poleInfoBean.poleLongitude",
			fieldLabel : "电杆经度",
			emptyText : "请输入电杆经度",
			width : 300,
			invalidText : '请输入正确的电杆经度！',
			validationEvent : 'keydown',
			validationDelay : 100,
			validator : function(thisText) {
				if (thisText == "") {
					return true;
				}
				var reg=/^[0-9].*$/;
				if(!reg.test(thisText)){
					Ext.getCmp('addPoleLongitude').markInvalid('填写格式不正确，请重新填写!');
					return false;
				}else{
					return true;
				}
			}
		});
		var poleLatitude=new Ext.form.TextField({
			id : 'addPoleLatitude',
			name : "poleInfoBean.poleLatitude",
			fieldLabel : "电杆纬度",
			emptyText : "请输入电杆纬度",
			width : 300,
			invalidText : '请输入正确的电杆纬度！',
			validationEvent : 'keydown',
			validationDelay : 100,
			validator : function(thisText) {
				if (thisText == "") {
					return true;
				}
				var reg=/^[0-9].*$/;
				if(!reg.test(thisText)){
					Ext.getCmp('addPoleLatitude').markInvalid('填写格式不正确，请重新填写!');
					return false;
				}else{
					return true;
				}
			}
		});*/
		this.maintenanceStore = new Ext.data.SimpleStore({
    		fields : ['value', 'text'],
    		data : [['', ''],['1', '自维'], ['2', '代维']]
    	})
		var row7={
    		layout:'column',
    		items:[{
    			height:30,
    			layout:'form',
    			items:[{
    				id : 'addPurchasedMaintenanceTime',
					name : "poleInfoBean.purchasedMaintenanceTime",
					xtype : 'textfield',
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
							Ext.getCmp('addPurchasedMaintenanceTime').markInvalid('填写格式不正确，请重新填写!');
							return false;
						}else{
							return true;
						}
					}
    			}]
    		},{
    			layout:'form',
    			items:[{
    				id : "addMaintenanceModeEnumId",
					name : "poleInfoBean.maintenanceModeEnumId",
					xtype : 'combo',
					typeAhead: true,
		        	triggerAction: 'all',
					fieldLabel : "维护方式",
					emptyText : "请选择维护方式",
					blankText : '维护方式不能为空',
					forceSelection: true,
		         	mode: 'local',
		        	width : 150,
		         	store: this.maintenanceStore,
		         	valueField: 'value',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
		       		displayField: 'text',//下拉框显示内容
		       		hiddenName:'poleInfoBean.maintenanceModeEnumId',//创建一个表单隐藏域来存储输入项的值
		       		editable:false//设置为false以阻止用户直接向该输入项输入文本
    			}]
    		}]
    	}
		/*var purchasedMaintenanceTime=new Ext.form.TextField({
			id : 'addPurchasedMaintenanceTime',
			name : "poleInfoBean.purchasedMaintenanceTime",
			fieldLabel : "购买年限",
			emptyText : "请输入购买年限且只能为正整数",
			width : 300,
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
		
		var maintenanceModeEnumId=new Ext.form.ComboBox({
    		id : "addMaintenanceModeEnumId",
			name : "poleInfoBean.maintenanceModeEnumId",
			typeAhead: true,
        	triggerAction: 'all',
			fieldLabel : "维护方式",
			emptyText : "请选择维护方式",
			blankText : '维护方式不能为空',
			forceSelection: true,
         	mode: 'local',
        	width : 300,
         	store: this.maintenanceStore,
         	valueField: 'value',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
       		displayField: 'text',//下拉框显示内容
       		hiddenName:'poleInfoBean.maintenanceModeEnumId',//创建一个表单隐藏域来存储输入项的值
       		editable:false//设置为false以阻止用户直接向该输入项输入文本
    	});*/
    	var row8={
    		layout:'column',
    		items:[{
    			height:30,
    			layout:'form',
    			items:[{
    				id : 'addMaintenanceOrgId',
					name : "poleInfoBean.maintenanceOrgId",
					xtype : 'textfield',
					fieldLabel : "维护单位",
					emptyText : "请输入维护单位",
					width : 150
    			}]
    		},{
    			layout:'form',
    			items:[{
    				id : 'addThirdPartyMaintenanceOrg',
					name : "poleInfoBean.thirdPartyMaintenanceOrg",
					xtype : 'textfield',
					fieldLabel : "第三方维护单位",
					emptyText : "请输入第三方维护单位",
					width : 150
    			}]
    		}]
    	}
    	/*var maintenanceOrgId=new Ext.form.TextField({
			id : 'addMaintenanceOrgId',
			name : "poleInfoBean.maintenanceOrgId",
			fieldLabel : "维护单位",
			emptyText : "请输入维护单位",
			width : 300
		});
		var thirdPartyMaintenanceOrg=new Ext.form.TextField({
			id : 'addThirdPartyMaintenanceOrg',
			name : "poleInfoBean.thirdPartyMaintenanceOrg",
			fieldLabel : "第三方维护单位",
			emptyText : "请输入第三方维护单位",
			width : 300
		});*/
		this.maintenanceTypeStore = new Ext.data.SimpleStore({
    		fields : ['value', 'text'],
    		data : [['', ''],['1', '硬件维修'], ['2', '紧急备件支持'], ['3', '软件补丁版本支持'], ['4', '技术支持服务（电话咨询、电话支持、远程支持、现场支持、网络预警、重要通信保障）'], ['5', '无']]
    	})
    	
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
			width : 150,
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
			name : 'poleInfoBean.areano',
			id : 'addareano',
			xtype : 'textfield',
			fieldLabel : '所属局站',
			hidden : true
		};
		
		var areaname = {
			name : 'poleInfoBean.areaname',
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
    		layout:'column',
    		items:[this.area,{
    			height:30,
    			layout:'form',
    			items:[{
    				id : "addMaintenanceTypeEnumId",
					name : "poleInfoBean.maintenanceTypeEnumId",
					xtype : 'combo',
					typeAhead: true,
		        	triggerAction: 'all',
					fieldLabel : "维护类型",
					emptyText : "请选择维护类型",
					blankText : '维护类型不能为空',
					forceSelection: true,
		         	mode: 'local',
		        	width : 150,
		         	store: this.maintenanceTypeStore,
		         	valueField: 'value',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
		       		displayField: 'text',//下拉框显示内容
		       		hiddenName:'poleInfoBean.maintenanceTypeEnumId',//创建一个表单隐藏域来存储输入项的值
		       		editable:false//设置为false以阻止用户直接向该输入项输入文本
    			}]
    		}]
    	}
		
		/*var maintenanceTypeEnumId=new Ext.form.ComboBox({
    		id : "addMaintenanceTypeEnumId",
			name : "poleInfoBean.maintenanceTypeEnumId",
			typeAhead: true,
        	triggerAction: 'all',
			fieldLabel : "维护类型",
			emptyText : "请选择维护类型",
			blankText : '维护类型不能为空',
			forceSelection: true,
         	mode: 'local',
        	width : 300,
         	store: this.maintenanceTypeStore,
         	valueField: 'value',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
       		displayField: 'text',//下拉框显示内容
       		hiddenName:'poleInfoBean.maintenanceTypeEnumId',//创建一个表单隐藏域来存储输入项的值
       		editable:false//设置为false以阻止用户直接向该输入项输入文本
    	});*/
		this.items = [{
			xtype : 'panel',
			bodyStyle : 'padding-left:78%;',
			items : [this.poleBatchAdd]
		}, {
			xtype : 'fieldset',
			title : '电杆信息',
			collapsible : true,
			items : [row1,row2,row3,row4,row5,row6,row7,row8,row9/*poleName,poleNo,poleSubNo,poleCode,poleTypeEnumId,poleMaterial,poleLength,buriedDepth,poleRadius,poleAddress,
			poleLongitude,poleLatitude,purchasedMaintenanceTime,maintenanceModeEnumId,maintenanceOrgId,thirdPartyMaintenanceOrg,
			maintenanceTypeEnumId*/]
		}];

		// 按钮
		this.buttons = [{
			id : 'addBtnSubmit',
			text : "保存",
			hidden : false,
			icon : "imgs/save_btn.png",
			cls : "x-btn-text-icon",
			tooltip : '保存电杆信息',
			tooltipType : 'qtip',
			handler : this.addBtnSubmit.createDelegate(this)
		}, {
			id : 'addBtnAdd',
			hidden : true,
			text : '继续添加',
			tooltip : '继续添加电杆信息',
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
		com.increase.cen.poleline.AddPole.superclass.initComponent.call(this);
	},
	// 保存按钮事件
	addBtnSubmit : function() {
		if (!this.getForm().isValid()) {
			return;
		}
		var form=this.getForm();
		//验证电杆名称是否重复
		var polename=Ext.getCmp("addPoleName").getValue();
		var poleno=Ext.getCmp("addPoleNo").getValue();
		var polesubno=Ext.getCmp("addPoleSubNo").getValue();
		Ext.Ajax.request({
			url : 'poleline!checkJSON.action',
			method : 'post',
			params : {
				'poleName' : polename,
				'poleNo' : poleno,
				'poleSubNo' : polesubno
			},
			success : function(response) {
				var json = Ext.util.JSON.decode(response.responseText);
				if (json.success == false) {
					Ext.Msg.show({
						title : '提示',
						width : 300,
						msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'+'电杆名称重复请重新输入!',
						buttons : {
							ok : "确定"
						}
					});
				}else{
					form.doAction("submit", {
						url : 'poleline!doaddpole.action',
						method : 'post',
						submitEmptyText : false,
						success : function(form, action) {
							Ext.Msg.show({
								title : '提示',
								width : 300,
								msg : '<img src="imgs/tip_success.png" align="center" hspace="30" />'
										+ '添加电杆信息成功!',
								buttons : {
									ok : "确定"
								}
							});
							Ext.getCmp("addPole").getForm().reset();// 重置表单
							Ext.getCmp('addPoleWindow').close();// 窗口隐藏
							Ext.getCmp('poleGrid').getStore().load({
								params : {
									start : 0,
									limit : limit
								}
							});// 表格加载数据
							Ext.getCmp('poleGrid').getView().refresh();// 表格刷新
						},
						failure : function(form, action) {
							Ext.Msg.show({
								title : '提示',
								width : 300,
								msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
										+ '添加电杆信息失败',
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
		Ext.getCmp("addPole").getForm().reset();// 重置表单
		Ext.getCmp('addPoleWindow').close();// 窗口隐藏
		Ext.getCmp('poleGrid').getStore().load({
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
		//验证电杆名称是否重复
		var polename=Ext.getCmp("addPoleName").getValue();
		var poleno=Ext.getCmp("addPoleNo").getValue();
		var polesubno=Ext.getCmp("addPoleSubNo").getValue();
		Ext.Ajax.request({
			url : 'poleline!checkJSON.action',
			method : 'post',
			params : {
				'poleName' : polename,
				'poleNo' : poleno,
				'poleSubNo' : polesubno
			},
			success : function(response) {
				var json = Ext.util.JSON.decode(response.responseText);
				if (json.success == false) {
					Ext.Msg.show({
						title : '提示',
						width : 300,
						msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'+'电杆名称重复请重新输入!',
						buttons : {
							ok : "确定"
						}
					});
				}else{
					form.doAction("submit", {
						url : 'poleline!doaddpole.action',
						method : 'post',
						submitEmptyText : false,
						success : function(form, action) {
							Ext.Msg.show({
								title : '提示',
								width : 300,
								msg : '<img src="imgs/tip_success.png" align="center" hspace="30"/>'
										+ '添加电杆信息成功!',
								buttons : {
									ok : "确定"
								}
							});
							// 重置表单
							Ext.getCmp("addPole").getForm().reset();
							// 复选框选中
							Ext.getCmp("poleBatchAdd").setValue(true);
						},
						failure : function(form, action) {
							Ext.Msg.show({
								title : '提示',
								width : 300,
								msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
										+ '添加电杆信息失败',
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
Ext.reg("addPole", com.increase.cen.poleline.AddPole);
