Ext.namespace("com.increase.cen.poleline");
var limit = null;
com.increase.cen.poleline.ModifyWell = Ext.extend(Ext.form.FormPanel, {
	bodyStyle : "padding:5px;",
	frame : true,
	bodyBorder : false,
	labelAlign : "right",
	buttonAlign : 'center',
	labelWidth : 140,
	width : 700,
	height : 300,
	autoScroll : true,
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
		this.wellId = new Ext.form.TextField({
			id : "modifyWellId",
			name : "wellInfoBean.wellId",
			fieldLabel : "井ID",
			width : 300,
			hidden : true
		});
		var oldname=new Ext.form.TextField({
			id : 'oldWellName',
			name : "cname",
			fieldLabel : "井名称",
			width : 300,
			hidden : true
		});
		var olddirection=new Ext.form.TextField({
			id : 'oldDirection',
			name : "cdirection",
			fieldLabel : "方位",
			width : 300,
			hidden : true
		});
		var oldno=new Ext.form.TextField({
			id : 'oldWellNo',
			name : "cwellNo",
			fieldLabel : "序号",
			width : 300,
			hidden : true
		});
		var oldsubno=new Ext.form.TextField({
			id : 'oldWellSubNo',
			name : "cwellSubNo",
			fieldLabel : "扩充后缀",
			width : 300,
			hidden : true
		});
		this.directionStore = new Ext.data.SimpleStore({
    		fields : ['value', 'text'],
    		data : [['', ''],['1', '东'], ['2', '南'], ['3', '西'], ['4', '北']]
    	})
		var row1={
			layout : 'column', // 从左往右布局
			items : [{ // 该列有整行中所占百分比
				height : 30,
				layout : 'form', // 从上往下布局
				items : [{
					id : 'modifyWellName',
					name : "wellInfoBean.wellName",
					xtype : 'textfield',
					fieldLabel : "人/手井名称",
					allowBlank : false,
					blankText : '人/手井名称不能为空',
					emptyText : "请输入人/手井名称",
					width : 150
				}]
			}, { // 该列有整行中所占百分比
				layout : 'form', // 从上往下布局
				items : [{
					id : "modifyDirection",
					name : "wellInfoBean.direction",
					typeAhead: true,
					xtype : 'combo',
		        	triggerAction: 'all',
					fieldLabel : "方位",
					emptyText : "请选择方位",
					blankText : '方位不能为空',
					forceSelection: true,
		         	mode: 'local',
		        	width : 150,
		         	store: this.directionStore,
		         	valueField: 'value',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
		       		displayField: 'text',//下拉框显示内容
		       		hiddenName:'wellInfoBean.direction',//创建一个表单隐藏域来存储输入项的值
		       		editable:false//设置为false以阻止用户直接向该输入项输入文本
				}]
			}]
		}
		var row2={
			layout : 'column', // 从左往右布局
			items : [{ // 该列有整行中所占百分比
				layout : 'form', // 从上往下布局
				items : [{
					id : 'modifyWellNo',
					name : "wellInfoBean.wellNo",
					xtype : 'textfield',
					fieldLabel : "序号",
					allowBlank : false,
					blankText : '序号不能为空',
					emptyText : "请输入序号为数字",
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
							Ext.getCmp('modifyWellNo').markInvalid('填写格式不正确，请重新填写!');
							return false;
						}else{
							return true;
						}
					}
				}]
			}, { // 该列有整行中所占百分比
				layout : 'form', // 从上往下布局
				items : [{
					id : 'modifyWellSubNo',
					name : "wellInfoBean.wellSubNo",
					xtype : 'textfield',
					fieldLabel : "扩充后缀",
					emptyText : "请输入扩充后缀",
					xtype : 'textfield',
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
							Ext.getCmp('modifyWellSubNo').markInvalid('填写格式不正确，请重新填写!');
							return false;
						}else{
							return true;
						}
					}
				}]
			}]
		}
		this.typeStore=new Ext.data.SimpleStore({
			fields : ['value', 'text'],
	    	data : [['', ''],['1', '人孔'], ['2', '手孔'], ['3', '双人孔'],['4', '三人孔'], ['5', '局前井'], ['6', '地下室']]
		})
		var row3={
			layout:'column',
			items:[{
				layout:'form',
				items:[{
					id : 'modifyAlias',
					name : "wellInfoBean.alias",
					xtype:'textfield',
					fieldLabel : "别名",
					emptyText : "请输入别名",
					width : 150
				}]
			},{
				layout:'form',
				items:[{
					id : "modifyManholeTypeEnumId",
					name : "wellInfoBean.manholeTypeEnumId",
					xtype:'combo',
					typeAhead: true,
		        	triggerAction: 'all',
					fieldLabel : "井类型",
					emptyText : "请选择井类型",
					blankText : '井类型不能为空',
					forceSelection: true,
		         	mode: 'local',
		        	width : 150,
		         	store: this.typeStore,
		         	valueField: 'value',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
		       		displayField: 'text',//下拉框显示内容
		       		hiddenName:'wellInfoBean.manholeTypeEnumId',//创建一个表单隐藏域来存储输入项的值
		       		editable:false//设置为false以阻止用户直接向该输入项输入文本
				}]
			}]
		}
		var row4={
			layout:'column',
			items:[{
				layout:'form',
				items:[{
					id : 'modifyLongitude',
					name : "wellInfoBean.longitude",
					xtype : 'textfield',
					fieldLabel : "人/手井经度",
					emptyText : "请输入人/手井经度",
					width : 150,
					invalidText : '请输入正确的人/手井经度！',
					validationEvent : 'keydown',
					validationDelay : 100,
					validator : function(thisText) {
						if (thisText == "") {
							return true;
						}
						var reg=/^[0-9]+\.{0,1}\d*$/;
						if(!reg.test(thisText)){
							Ext.getCmp('modifyLongitude').markInvalid('填写格式不正确，请重新填写!');
							return false;
						}else{
							return true;
						}
					}
				}]
			},{
				layout:'form',
				items:[{
					id : 'modifyLatitude',
					name : "wellInfoBean.latitude",
					xtype : 'textfield',
					fieldLabel : "人/手井纬度",
					emptyText : "请输入人/手井纬度",
					width : 150,
					invalidText : '请输入正确的人/手井纬度！',
					validationEvent : 'keydown',
					validationDelay : 100,
					validator : function(thisText) {
						if (thisText == "") {
							return true;
						}
						var reg=/^[0-9]+\.{0,1}\d*$/;
						if(!reg.test(thisText)){
							Ext.getCmp('modifyLatitude').markInvalid('填写格式不正确，请重新填写!');
							return false;
						}else{
							return true;
						}
					}
				}]
			}]
		}
		var row5={
			layout:'column',
			items:[{
				layout:'form',
				items:[{
					id : 'modifyCoverTopElevation',
					name : "wellInfoBean.coverTopElevation",
					xtype : 'textfield',
					fieldLabel : "盖顶高程(米)",
					emptyText : "请输入盖顶高程",
					width : 150,
					invalidText : '请输入正确的盖顶高程！',
					validationEvent : 'keydown',
					validationDelay : 100,
					validator : function(thisText) {
						if (thisText == "") {
							return true;
						}
						var reg=/^[0-9]+\.{0,1}\d*$/;
						if(!reg.test(thisText)){
							Ext.getCmp('modifyCoverTopElevation').markInvalid('填写格式不正确，请重新填写!');
							return false;
						}else{
							return true;
						}
					}
				}]
			},{
				layout:'form',
				items:[{
					id : 'modifyBottomAltitude',
					name : "wellInfoBean.bottomAltitude",
					xtype : 'textfield',
					fieldLabel : "井坑底高程(米)",
					emptyText : "请输入井坑底高程",
					width : 150,
					invalidText : '请输入正确的井坑底高程！',
					validationEvent : 'keydown',
					validationDelay : 100,
					validator : function(thisText) {
						if (thisText == "") {
							return true;
						}
						var reg=/^[0-9]+\.{0,1}\d*$/;
						if(!reg.test(thisText)){
							Ext.getCmp('modifyBottomAltitude').markInvalid('填写格式不正确，请重新填写!');
							return false;
						}else{
							return true;
						}
					}
				}]
			}]
		};
		var row6={
			layout:'column',
			items:[{
				layout:'form',
				items:[{
					id : 'modifyWellAddress',
					name : "wellInfoBean.wellAddress",
					xtype : 'textfield',
					fieldLabel : "人/手井地址",
					emptyText : "请输入人/手井地址",
					width : 150
				}]
			},{
				layout:'form',
				items:[{
					id : 'modifyManholeDepth',
					name : "wellInfoBean.manholeDepth",
					xtype : 'textfield',
					fieldLabel : "井深(米)",
					emptyText : "请输入井深",
					width : 150,
					invalidText : '请输入正确的井深！',
					validationEvent : 'keydown',
					validationDelay : 100,
					validator : function(thisText) {
						if (thisText == "") {
							return true;
						}
						var reg=/^[0-9]+\.{0,1}\d*$/;
						if(!reg.test(thisText)){
							Ext.getCmp('modifyManholeDepth').markInvalid('填写格式不正确，请重新填写!');
							return false;
						}else{
							return true;
						}
					}
				}]
			}]
		};
    	var row7={
    		layout:'column',
    		items:[{
    			layout:'form',
    			items:[{
    				id : 'modifyManholeLength',
					name : "wellInfoBean.manholeLength",
					xtype : 'textfield',
					fieldLabel : "井长(米)",
					emptyText : "请输入井长",
					width : 150,
					invalidText : '请输入正确的井长！',
					validationEvent : 'keydown',
					validationDelay : 100,
					validator : function(thisText) {
						if (thisText == "") {
							return true;
						}
						//var reg=/^[0-9].*$/;
						var reg=/^[0-9]+\.{0,1}\d*$/;
						if(!reg.test(thisText)){
							Ext.getCmp('modifyManholeLength').markInvalid('填写格式不正确，请重新填写!');
							return false;
						}else{
							return true;
						}
					}
    			}]
    		},{
    			layout:'form',
    			items:[{
    				id : 'modifyManholeWidth',
					name : "wellInfoBean.manholeWidth",
					xtype : 'textfield',
					fieldLabel : "井宽(米)",
					emptyText : "请输入井宽",
					width : 150,
					invalidText : '请输入正确的井宽！',
					validationEvent : 'keydown',
					validationDelay : 100,
					validator : function(thisText) {
						if (thisText == "") {
							return true;
						}
						var reg=/^[0-9]+\.{0,1}\d*$/;
						if(!reg.test(thisText)){
							Ext.getCmp('modifyManholeWidth').markInvalid('填写格式不正确，请重新填写!');
							return false;
						}else{
							return true;
						}
					}
    			}]
    		}]
    	};
    	
    	this.shapeStore=new Ext.data.SimpleStore({
			fields : ['value', 'text'],
	    	data : [['', ''],['1', '直通型'], ['2', '丁字型'], ['3', '十字型'],['4', '15度斜通型'], ['5', '30度斜通型'], 
	    	['6', '45度斜通型'],['7', '60度斜通型'], ['8', '75度斜通型'], ['9', '不规则型'],['10', '扇型'],
	    	['11', '拐弯型(左)'], ['12', '拐弯型(右)'], ['13', '手井'],['14', '方井'],['15', '穿线孔']]
		})
		this.specificationStore=new Ext.data.SimpleStore({
			fields : ['value', 'text'],
	    	data : [['', ''],['1', '特大号'], ['2', '大号'], ['3', '中号'],['4', '小号'], ['5', '手孔']]
		})
    	var row8={
    		layout : 'column', // 从左往右布局
			items : [{
				layout:'form',
				items:[{
					id : "modifyManholeShape",
					name : "wellInfoBean.manholeShape",
					xtype : 'combo',
					typeAhead: true,
		        	triggerAction: 'all',
					fieldLabel : "井形状",
					emptyText : "请选择井形状",
					forceSelection: true,
		         	mode: 'local',
		        	width : 150,
		         	store: this.shapeStore,
		         	valueField: 'value',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
		       		displayField: 'text',//下拉框显示内容
		       		hiddenName:'wellInfoBean.manholeShape',//创建一个表单隐藏域来存储输入项的值
		       		editable:false//设置为false以阻止用户直接向该输入项输入文本
				}]
			},{
				layout:'form',
				items:[{
					id : "modifyManholeSpecification",
					name : "wellInfoBean.manholeSpecification",
					xtype : 'combo',
					typeAhead: true,
		        	triggerAction: 'all',
					fieldLabel : "井规格",
					emptyText : "请选择井规格",
					forceSelection: true,
		         	mode: 'local',
		        	width : 150,
		         	store: this.specificationStore,
		         	valueField: 'value',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
		       		displayField: 'text',//下拉框显示内容
		       		hiddenName:'wellInfoBean.manholeSpecification',//创建一个表单隐藏域来存储输入项的值
		       		editable:false//设置为false以阻止用户直接向该输入项输入文本
				}]
			}]
    	};
    	
    	this.structureStore=new Ext.data.SimpleStore({
			fields : ['value', 'text'],
	    	data : [['', ''],['1', '砖砌体'], ['2', '钢筋混凝土体'], ['3', '装配式']]
		})
		this.roadTypeStore=new Ext.data.SimpleStore({
			fields : ['value', 'text'],
	    	data : [['', ''],['1', '车行道'], ['2', '人行道']]
		})
    	var row9={
    		layout : 'column', // 从左往右布局
			items : [{
				layout:'form',
				items:[{
					id : "modifyManholeStructure",
					name : "wellInfoBean.manholeStructure",
					xtype : 'combo',
					typeAhead: true,
		        	triggerAction: 'all',
					fieldLabel : "井结构",
					emptyText : "请选择井结构",
					forceSelection: true,
		         	mode: 'local',
		        	width : 150,
		         	store: this.structureStore,
		         	valueField: 'value',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
		       		displayField: 'text',//下拉框显示内容
		       		hiddenName:'wellInfoBean.manholeStructure',//创建一个表单隐藏域来存储输入项的值
		       		editable:false//设置为false以阻止用户直接向该输入项输入文本
				}]
			},{
				layout:'form',
				items:[{
					id : "modifyRoadTypeEnumId",
					name : "wellInfoBean.roadTypeEnumId",
					xtype : 'combo',
					typeAhead: true,
		        	triggerAction: 'all',
					fieldLabel : "道路类型",
					emptyText : "请选择道路类型",
					forceSelection: true,
		         	mode: 'local',
		        	width : 150,
		         	store: this.roadTypeStore,
		         	valueField: 'value',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
		       		displayField: 'text',//下拉框显示内容
		       		hiddenName:'wellInfoBean.roadTypeEnumId',//创建一个表单隐藏域来存储输入项的值
		       		editable:false//设置为false以阻止用户直接向该输入项输入文本
				}]
			}]
    	};
    	this.roadSurfaceStore=new Ext.data.SimpleStore({
			fields : ['value', 'text'],
	    	data : [['', ''],['1', '柏油马路'], ['2', '沙石路面'], ['3', '混凝土砌块路面'],
	    	['4', '水泥砖铺路面'], ['5', '条石路面'], ['6', '水泥路面'],['7', '砼路面']]
		})
    	var row10={
    		layout : 'column', // 从左往右布局
			items : [{
				layout:'form',
				items:[{
					id : "modifyRoadSurfaceTypeEnumId",
					name : "wellInfoBean.roadSurfaceTypeEnumId",
					xtype : 'combo',
					typeAhead: true,
		        	triggerAction: 'all',
					fieldLabel : "路面类型",
					emptyText : "请选择路面类型",
					forceSelection: true,
		         	mode: 'local',
		        	width : 150,
		         	store: this.roadSurfaceStore,
		         	valueField: 'value',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
		       		displayField: 'text',//下拉框显示内容
		       		hiddenName:'wellInfoBean.roadSurfaceTypeEnumId',//创建一个表单隐藏域来存储输入项的值
		       		editable:false//设置为false以阻止用户直接向该输入项输入文本
				}]
			},{
				layout:'form',
				items:[{
					id : 'modifyCoverMaterialEnumId',
					name : 'wellInfoBean.coverMaterialEnumId',
					xtype : 'textfield',
					fieldLabel : '井盖材质',
					emptyText : "请输入井盖材质",
					width : 150
				}]
			}]
    	};
    	var row11={
    		layout : 'column', // 从左往右布局
			items : [{
				layout:'form',
				items:[{
					id : 'modifyCoverShapeEnumId',
					name : 'wellInfoBean.coverShapeEnumId',
					xtype : 'textfield',
					fieldLabel : '井盖形状',
					emptyText : "请输入井盖形状",
					width : 150
				}]
			},{
				layout:'form',
				items:[{
					id : 'modifyCoverQuantity',
					name : 'wellInfoBean.coverQuantity',
					xtype : 'textfield',
					fieldLabel : '井盖数量',
					emptyText : "请输入井盖数量",
					width : 150,
					invalidText : '请输入正确的井盖数量！',
					validationEvent : 'keydown',
					validationDelay : 100,
					validator : function(thisText) {
						if (thisText == "") {
							return true;
						}
						var reg=/^[1-9]\d*$/;
						if(!reg.test(thisText)){
							Ext.getCmp('modifyCoverQuantity').markInvalid('填写格式不正确，请重新填写!');
							return false;
						}else{
							return true;
						}
					}
				}]
			}]
    	};
    	var row12={
    		layout:'column',
    		items:[{
    			layout:'form',
    			items:[{
    				id : 'modifyWallThickness',
					name : "wellInfoBean.wallThickness",
					xtype : 'textfield',
					fieldLabel : "井壁厚度(米)",
					emptyText : "请输入井壁厚度",
					width : 150,
					invalidText : '请输入正确的井壁厚度！',
					validationEvent : 'keydown',
					validationDelay : 100,
					validator : function(thisText) {
						if (thisText == "") {
							return true;
						}
						var reg=/^[0-9]+\.{0,1}\d*$/;
						if(!reg.test(thisText)){
							Ext.getCmp('modifyWallThickness').markInvalid('填写格式不正确，请重新填写!');
							return false;
						}else{
							return true;
						}
					}
    			}]
    		},{
    			layout:'form',
    			items:[{
    				id : 'modifySubbaseThickness',
					name : "wellInfoBean.subbaseThickness",
					xtype : 'textfield',
					fieldLabel : "底基厚度(米)",
					emptyText : "请输入底基厚度",
					width : 150,
					invalidText : '请输入正确的底基厚度！',
					validationEvent : 'keydown',
					validationDelay : 100,
					validator : function(thisText) {
						if (thisText == "") {
							return true;
						}
						var reg=/^[0-9]+\.{0,1}\d*$/;
						if(!reg.test(thisText)){
							Ext.getCmp('modifySubbaseThickness').markInvalid('填写格式不正确，请重新填写!');
							return false;
						}else{
							return true;
						}
					}
    			}]
    		}]
    	};
    	this.maintenanceStore = new Ext.data.SimpleStore({
    		fields : ['value', 'text'],
    		data : [['', ''],['1', '自维'], ['2', '代维']]
    	})
    	var row13={
    		layout : 'column', // 从左往右布局
			items : [{
				layout:'form',
				items:[{
					id : "modifyMaintenanceModeEnumId",
					name : "wellInfoBean.maintenanceModeEnumId",
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
		       		hiddenName:'wellInfoBean.maintenanceModeEnumId',//创建一个表单隐藏域来存储输入项的值
		       		editable:false//设置为false以阻止用户直接向该输入项输入文本
				}]
			},{
				layout:'form',
				items:[{
					id : 'modifyMaintenanceOrgId',
					name : "wellInfoBean.maintenanceOrgId",
					xtype : 'textfield',
					fieldLabel : "维护单位",
					emptyText : "请输入维护单位",
					width : 150
				}]
			}]
    	};
    	this.maintenanceTypeStore = new Ext.data.SimpleStore({
    		fields : ['value', 'text'],
    		data : [['', ''],['1', '硬件维修'], ['2', '紧急备件支持'], ['3', '软件补丁版本支持'], ['4', '技术支持服务（电话咨询、电话支持、远程支持、现场支持、网络预警、重要通信保障）'], ['5', '无']]
    	})
    	var row14={
    		layout:'column',
    		items:[{
    			layout:'form',
    			items:[{
    				id : 'modifyThirdPartyMaintenanceOrg',
					name : "wellInfoBean.thirdPartyMaintenanceOrg",
					xtype : 'textfield',
					fieldLabel : "第三方维护单位",
					emptyText : "请输入第三方维护单位",
					width : 150
    			}]
    		},{
    			layout:'form',
    			items:[{
    				id : "modifyMaintenanceTypeEnumId",
					name : "wellInfoBean.maintenanceTypeEnumId",
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
		       		hiddenName:'wellInfoBean.maintenanceTypeEnumId',//创建一个表单隐藏域来存储输入项的值
		       		editable:false//设置为false以阻止用户直接向该输入项输入文本
    			}]
    		}]
    	};
    	this.resourceLifePeriodEnumId = new Ext.data.SimpleStore({
    		fields : ['value', 'text'],
    		data : [['', ''],['1', '设计状态'], ['2', '工程建设期（入网带业务前）'], ['3', '工程可用期（已入网带业务）'], 
    		['4', '工程初验后试运行'], ['5', '工程终验后在网'], ['6', '退网状态']]
    	})
    	var row15={
    		layout:'column',
    		items:[{
    			layout:'form',
    			items:[{
    				id : 'modifyPurchasedMaintenanceTime',
					name : "wellInfoBean.purchasedMaintenanceTime",
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
							Ext.getCmp('modifyPurchasedMaintenanceTime').markInvalid('填写格式不正确，请重新填写!');
							return false;
						}else{
							return true;
						}
					}
    			}]
    		},{
    			layout:'form',
    			items:[{
    				id : "modifyResourceLifePeriodEnumId",
					name : "wellInfoBean.resourceLifePeriodEnumId",
					xtype : 'combo',
					typeAhead: true,
		        	triggerAction: 'all',
					fieldLabel : "资源生命周期",
					emptyText : "请选择资源生命周期",
					blankText : '资源生命周期不能为空',
					forceSelection: true,
		         	mode: 'local',
		        	width : 150,
		         	store: this.resourceLifePeriodEnumId,
		         	valueField: 'value',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
		       		displayField: 'text',//下拉框显示内容
		       		hiddenName:'wellInfoBean.resourceLifePeriodEnumId',//创建一个表单隐藏域来存储输入项的值
		       		editable:false//设置为false以阻止用户直接向该输入项输入文本
    			}]
    		}]
    	};
		var row16={
			layout : 'column', // 从左往右布局
			items : [{ // 该列有整行中所占百分比
				height : 30,
				layout : 'form', // 从上往下布局
				items : [{
					id : 'modifyProjectNumber',
					name : 'wellInfoBean.projectNumber',
					xtype : 'textfield',
					fieldLabel : '项目编号',
					emptyText : "请输入项目编号",
					width : 150
				}]
			}, { // 该列有整行中所占百分比
				layout : 'form', // 从上往下布局
				items : [{
					id : 'modifyProjectName',
					name : 'wellInfoBean.projectName',
					xtype : 'textfield',
					fieldLabel : '项目名称',
					emptyText : "请输入项目名称",
					width : 150
				}]
			}]
		}
		var row17={
			layout : 'column', // 从左往右布局
			items : [{ // 该列有整行中所占百分比
				height : 30,
				layout : 'form', // 从上往下布局
				items : [{
					id : 'modifyRenewalExpirationDate',
					name : 'wellInfoBean.renewalExpirationDate',
					xtype : "datefield",
					format : 'Y-m-d',// 日期格式
					fieldLabel : '续保截止日期',
					emptyText : "请选择保修截止日期",
					width : 150
				}]
			}, { // 该列有整行中所占百分比
				layout : 'form', // 从上往下布局
				items : [{
					id : 'modifyProjectWarrantyExpireDate',
					name : 'wellInfoBean.projectWarrantyExpireDate',
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
			name : 'wellInfoBean.areano',
			id : 'areano',
			xtype : 'textfield',
			fieldLabel : '所属局站',
			hidden : true
		};
		
		var areaname = {
			name : 'wellInfoBean.areaname',
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
		var row18={
			layout : 'column', // 从左往右布局
			items : [this.area, { // 该列有整行中所占百分比
				layout : 'form', // 从上往下布局
				items : [{
					id : 'modifyResponsiblePersonId',
					name : 'wellInfoBean.responsiblePersonId',
					xtype : 'textfield',
					fieldLabel : '责任人',
					emptyText : "请输入责任人",
					width : 150
				}]
			}]
		}
		this.items = [{
			xtype : 'fieldset',
			title : '井基本信息',
			collapsible : true,
			items : [this.wellId,olddirection,oldname,oldno,oldsubno,row1,row2,row3,row4,row5,row6,row7,row8,row9,row10,row11,
			row12,row13,row14,row15,row16,row17,row18]
		}];
		// 按钮
		this.buttons = [{
			text : "保存",
			icon : "imgs/save_btn.png",
			cls : "x-btn-text-icon",
			tooltip : '修改井信息',
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
		com.increase.cen.poleline.ModifyWell.superclass.initComponent.call(this);
	},
	// 提交事件
	btnSubmit : function submitForm() {
		if (!this.getForm().isValid()) {
			return;
		}
		var form=this.getForm();
		//验证电杆名称是否重复
		var wellname=Ext.getCmp("modifyWellName").getValue();
		var wellno=Ext.getCmp("modifyWellNo").getValue();
		var wellsubno=Ext.getCmp("modifyWellSubNo").getValue();
		var direction=Ext.getCmp("modifyDirection").getValue();
		var oldwellname=Ext.getCmp("oldWellName").getValue();
		var oldwellno=Ext.getCmp("oldWellNo").getValue();
		var oldwellsubno=Ext.getCmp("oldWellSubNo").getValue();
		var olddirection=Ext.getCmp("oldDirection").getValue();
		if(wellname==oldwellname&&wellno==oldwellno&&wellsubno==oldwellsubno&&direction==olddirection){
			form.doAction("submit", {
						url : 'pipe!dosaveWell.action',
						submitEmptyText : false,
						method : 'post',
						success : function(form, action) {
							Ext.Msg.show({
								title : '提示',
								width : 300,
								msg : '<img src="imgs/tip_success.png" align="center" hspace="30"/>'
										+ '修井信息成功!',
								buttons : {
									ok : "确定"
								}
							});
							// 窗口隐藏
							Ext.getCmp('modifyWellWindow').close();
							Ext.getCmp("wellGrid").getStore().reload();
						},
						failure : function(form, action) {
							Ext.Msg.show({
								title : '提示',
								width : 300,
								msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
										+ '修改井信息失败!',
								buttons : {
									ok : "确定"
								}
							});
						}
					});
		}else{
		Ext.Ajax.request({
			url : 'pipe!checkWellNameJSON.action',
			method : 'post',
			params : {
				'wellName' : wellname,
				'wellNo' : wellno,
				'direction' : direction,
				'wellSubNo' : wellsubno
			},
			success : function(response) {
				var json = Ext.util.JSON.decode(response.responseText);
				if (json.success == false) {
					Ext.Msg.show({
						title : '提示',
						width : 300,
						msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'+'井名称重复请重新输入!',
						buttons : {
							ok : "确定"
						}
					});
				}else{
					form.doAction("submit", {
						url : 'pipe!dosaveWell.action',
						submitEmptyText : false,
						method : 'post',
						success : function(form, action) {
							Ext.Msg.show({
								title : '提示',
								width : 300,
								msg : '<img src="imgs/tip_success.png" align="center" hspace="30"/>'
										+ '修改井信息成功!',
								buttons : {
									ok : "确定"
								}
							});
							// 窗口隐藏
							Ext.getCmp('modifyWellWindow').close();
							Ext.getCmp("wellGrid").getStore().reload();
						},
						failure : function(form, action) {
							Ext.Msg.show({
								title : '提示',
								width : 300,
								msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
										+ '修改井信息失败!',
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
		Ext.getCmp("modifyWell").getForm().reset();
		// 窗口隐藏
		Ext.getCmp('modifyWellWindow').close();
		// 刷新表格
		Ext.getCmp("wellGrid").getStore().reload();
	}
});
Ext.reg("modifyWell", com.increase.cen.poleline.ModifyWell);