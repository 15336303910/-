Ext.namespace("com.increase.cen.poleline");
var limit = null;
com.increase.cen.poleline.ModifyPole = Ext.extend(Ext.form.FormPanel, {
	bodyStyle : "padding:5px;",
	frame : true,
	bodyBorder : false,
	labelAlign : "right",
	buttonAlign : 'center',
	labelWidth : 120,
	autoScroll:true,
	width : 650,
	//height : 300,
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
		
		this.poleId = new Ext.form.TextField({
			id : "modifyPoleId",
			name : "poleInfoBean.poleId",
			fieldLabel : "电杆ID",
			width : 300,
			hidden : true
		});
		var oldname=new Ext.form.TextField({
			id : 'oldPoleName',
			name : "poleName",
			fieldLabel : "电杆名称",
			width : 300,
			hidden : true
		});
		var oldno=new Ext.form.TextField({
			id : 'oldPoleNo',
			name : "poleNo",
			fieldLabel : "电杆序号",
			width : 300,
			hidden : true
		});
		var oldsubno=new Ext.form.TextField({
			id : 'oldPoleSubNo',
			name : "poleSubNo",
			fieldLabel : "扩充后缀",
			width : 300,
			hidden : true
		});
		var row1={
			layout:'column',
			items:[{
				height:30,
				layout:'form',
				items:[{
					id : 'modifyPoleName',
					name : "poleInfoBean.poleName",
					xtype : 'textfield',
					fieldLabel : "电杆名称",
					allowBlank : false,
					blankText : '电杆名称不能为空',
					emptyText : "请输入电杆名称",
					width : 150
				}]
			},{
				layout:'form',
				items:[{
					id : 'modifyPoleNo',
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
							Ext.getCmp('modifyPoleNo').markInvalid('填写格式不正确，请重新填写!');
							return false;
						}else{
							return true;
						}
					}
				}]
			}]
		}
		/*var poleName=new Ext.form.TextField({
			id : 'modifyPoleName',
			name : "poleInfoBean.poleName",
			fieldLabel : "电杆名称",
			allowBlank : false,
			blankText : '电杆名称不能为空',
			emptyText : "请输入电杆名称",
			width : 300
		});
		var poleNo=new Ext.form.TextField({
			id : 'modifyPoleNo',
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
					Ext.getCmp('modifyPoleNo').markInvalid('填写格式不正确，请重新填写!');
					return false;
				}else{
					return true;
				}
			}
		});*/
		var row2={
			layout:'column',
			items:[{
				height:30,
				layout:'form',
				items:[{
					id : 'modifyPoleSubNo',
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
							Ext.getCmp('modifyPoleSubNo').markInvalid('填写格式不正确，请重新填写!');
							return false;
						}else{
							return true;
						}
					}
				}]
			},{
				layout:'form',
				items:[{
					id : 'modifyPoleCode',
					name : "poleInfoBean.poleCode",
					xtype : 'textfield',
					fieldLabel : "电杆编码",
					emptyText : "请输入电杆编码",
					width : 150
				}]
			}]
		}
		/*var poleSubNo=new Ext.form.TextField({
			id : 'modifyPoleSubNo',
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
					Ext.getCmp('modifyPoleSubNo').markInvalid('填写格式不正确，请重新填写!');
					return false;
				}else{
					return true;
				}
			}
		});
		var poleCode=new Ext.form.TextField({
			id : 'modifyPoleCode',
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
				height:30,
				layout:'form',
				items:[{
					id : "modifyPoleTypeEnumId",
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
				layout:'form',
				items:[{
					id : "modifyPoleMaterial",
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
    		id : "modifyPoleTypeEnumId",
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
    		id : "modifyPoleMaterial",
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
					id : 'modifyPoleLength',
					name : "poleInfoBean.poleLength",
					xtype:'textfield',
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
							Ext.getCmp('modifyPoleLength').markInvalid('填写格式不正确，请重新填写!');
							return false;
						}else{
							return true;
						}
					}
				}]
			},{
				layout:'form',
				items:[{
					id : 'modifyBuriedDepth',
					name : "poleInfoBean.buriedDepth",
					xtype:'textfield',
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
							Ext.getCmp('modifyBuriedDepth').markInvalid('填写格式不正确，请重新填写!');
							return false;
						}else{
							return true;
						}
					}
				}]
			}]
		}
    	/*var poleLength=new Ext.form.TextField({
			id : 'modifyPoleLength',
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
					Ext.getCmp('modifyPoleLength').markInvalid('填写格式不正确，请重新填写!');
					return false;
				}else{
					return true;
				}
			}
		});
		var buriedDepth=new Ext.form.TextField({
			id : 'modifyBuriedDepth',
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
					Ext.getCmp('modifyBuriedDepth').markInvalid('填写格式不正确，请重新填写!');
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
					id : 'modifyPoleRadius',
					name : "poleInfoBean.poleRadius",
					xtype:'textfield',
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
							Ext.getCmp('modifyPoleRadius').markInvalid('填写格式不正确，请重新填写!');
							return false;
						}else{
							return true;
						}
					}
				}]
			},{
				layout:'form',
				items:[{
					id : 'modifyPoleAddress',
					name : "poleInfoBean.poleAddress",
					xtype : 'textfield',
					fieldLabel : "电杆地址",
					emptyText : "请输入电杆地址",
					width : 150
				}]
			}]
		}
		/*var poleRadius=new Ext.form.TextField({
			id : 'modifyPoleRadius',
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
					Ext.getCmp('modifyPoleRadius').markInvalid('填写格式不正确，请重新填写!');
					return false;
				}else{
					return true;
				}
			}
		});
		var poleAddress=new Ext.form.TextField({
			id : 'modifyPoleAddress',
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
					id : 'modifyPoleLongitude',
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
						//var reg=/^[0-9].*$/;
						var reg=/^[0-9]+\.{0,1}\d*$/;
						if(!reg.test(thisText)){
							Ext.getCmp('modifyPoleLongitude').markInvalid('填写格式不正确，请重新填写!');
							return false;
						}else{
							return true;
						}
					}
				}]
			},{
				layout:'form',
				items:[{
					id : 'modifyPoleLatitude',
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
			id : 'modifyPoleLongitude',
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
					Ext.getCmp('modifyPoleLongitude').markInvalid('填写格式不正确，请重新填写!');
					return false;
				}else{
					return true;
				}
			}
		});
		var poleLatitude=new Ext.form.TextField({
			id : 'modifyPoleLatitude',
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
					id : 'modifyPurchasedMaintenanceTime',
					name : "poleInfoBean.purchasedMaintenanceTime",
					xtype : 'textfield',
					fieldLabel : "购买年限",
					emptyText : "请输入购买年限",
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
					id : "modifyMaintenanceModeEnumId",
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
			id : 'modifyPurchasedMaintenanceTime',
			name : "poleInfoBean.purchasedMaintenanceTime",
			fieldLabel : "购买年限",
			emptyText : "请输入购买年限",
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
    		id : "modifyMaintenanceModeEnumId",
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
					id : 'modifyMaintenanceOrgId',
					name : "poleInfoBean.maintenanceOrgId",
					xtype : 'textfield',
					fieldLabel : "维护单位",
					emptyText : "请输入维护单位",
					width : 150
				}]
			},{
				layout:'form',
				items:[{
					id : 'modifyThirdPartyMaintenanceOrg',
					name : "poleInfoBean.thirdPartyMaintenanceOrg",
					xtype : 'textfield',
					fieldLabel : "第三方维护单位",
					emptyText : "请输入第三方维护单位",
					width : 150
				}]
			}]
		}
    	/*var maintenanceOrgId=new Ext.form.TextField({
			id : 'modifyMaintenanceOrgId',
			name : "poleInfoBean.maintenanceOrgId",
			fieldLabel : "维护单位",
			emptyText : "请输入维护单位",
			width : 300
		});
		var thirdPartyMaintenanceOrg=new Ext.form.TextField({
			id : 'modifyThirdPartyMaintenanceOrg',
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
			id : 'comboxWithTree',
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
			Ext.getCmp("areano").setValue(node.attributes.domainCode);
			Ext.getCmp("areaname").setValue(node.attributes.text);
			//Ext.getCmp("did").setValue('');
			comboxWithTree.collapse();
		});
	
		comboxWithTree.on('expand', function() {
			opTree.render('tree');
		});
	
		var areano = {
			name : 'poleInfoBean.areano',
			id : 'areano',
			xtype : 'textfield',
			fieldLabel : '所属局站',
			hidden : true
		};
		
		var areaname = {
			name : 'poleInfoBean.areaname',
			id : 'areaname',
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
					id : "modifyMaintenanceTypeEnumId",
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
    		id : "modifyMaintenanceTypeEnumId",
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
			xtype : 'fieldset',
			title : '电杆基本信息',
			collapsible : true,
			items : [this.poleId,oldname,oldno,oldsubno,row1,row2,row3,row4,row5,row6,row7,row8,row9/*this.poleId,oldname,oldno,oldsubno,poleName,poleNo,poleSubNo,poleCode,poleTypeEnumId,poleMaterial,poleLength,buriedDepth,poleRadius,
			poleAddress,poleLongitude,poleLatitude,purchasedMaintenanceTime,maintenanceModeEnumId,maintenanceOrgId,
			thirdPartyMaintenanceOrg,maintenanceTypeEnumId*/]
		}];
		// 按钮
		this.buttons = [{
			text : "保存",
			icon : "imgs/save_btn.png",
			cls : "x-btn-text-icon",
			tooltip : '修改电杆信息',
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
		com.increase.cen.poleline.ModifyPole.superclass.initComponent.call(this);
	},
	// 提交事件
	btnSubmit : function submitForm() {
		if (!this.getForm().isValid()) {
			return;
		}
		var form=this.getForm();
		//验证电杆名称是否重复
		var polename=Ext.getCmp("modifyPoleName").getValue();
		var poleno=Ext.getCmp("modifyPoleNo").getValue();
		var polesubno=Ext.getCmp("modifyPoleSubNo").getValue();
		var oldpolename=Ext.getCmp("oldPoleName").getValue();
		var oldpoleno=Ext.getCmp("oldPoleNo").getValue();
		var oldpolesubno=Ext.getCmp("oldPoleSubNo").getValue();
		if(polename==oldpolename&&poleno==oldpoleno&&polesubno==oldpolesubno){
			form.doAction("submit", {
						url : 'poleline!doeditpole.action',
						submitEmptyText : false,
						method : 'post',
						success : function(form, action) {
							Ext.Msg.show({
								title : '提示',
								width : 300,
								msg : '<img src="imgs/tip_success.png" align="center" hspace="30"/>'
										+ '修改电杆信息成功!',
								buttons : {
									ok : "确定"
								}
							});
							// 窗口隐藏
							Ext.getCmp('modifyPoleWindow').close();
							Ext.getCmp("poleGrid").getStore().reload();
						},
						failure : function(form, action) {
							Ext.Msg.show({
								title : '提示',
								width : 300,
								msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
										+ '修改电杆信息失败!',
								buttons : {
									ok : "确定"
								}
							});
						}
					});
		}else{
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
						url : 'poleline!doeditpole.action',
						submitEmptyText : false,
						method : 'post',
						success : function(form, action) {
							Ext.Msg.show({
								title : '提示',
								width : 300,
								msg : '<img src="imgs/tip_success.png" align="center" hspace="30"/>'
										+ '修改电杆信息成功!',
								buttons : {
									ok : "确定"
								}
							});
							// 窗口隐藏
							Ext.getCmp('modifyPoleWindow').close();
							Ext.getCmp("poleGrid").getStore().reload();
						},
						failure : function(form, action) {
							Ext.Msg.show({
								title : '提示',
								width : 300,
								msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
										+ '修改电杆信息失败!',
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
	},
	// 关闭
	btnClose : function() {
		// 重置表单
		Ext.getCmp("modifyPole").getForm().reset();
		// 窗口隐藏
		Ext.getCmp('modifyPoleWindow').close();
		// 刷新表格
		//Ext.getCmp("poleGrid").getStore().reload();
		Ext.getCmp('poleGrid').getStore().load({
			params : {
				start : 0,
				limit : limit
			}
		});// 表格加载数据
	}
});
Ext.reg("modifyPole", com.increase.cen.poleline.ModifyPole);