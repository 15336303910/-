Ext.namespace("com.increase.cen.poleline");
var limit = null;
com.increase.cen.poleline.ModifyPipeSegment = Ext.extend(Ext.form.FormPanel, {
	bodyStyle : "padding:5px;",
	frame : true,
	bodyBorder : false,
	labelAlign : "right",
	buttonAlign : 'center',
	labelWidth : 130,
	width : 850,
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
		this.pipeSegmentId = new Ext.form.TextField({
			id : "modifyPipeSegmentId",
			name : "pipeSegmentInfoBean.pipeSegmentId",
			fieldLabel : "管道段ID",
			width : 300,
			hidden : true
		});
		var oldpsname=new Ext.form.TextField({
			id : 'oldPipeSegmentName',
			name : "pipeSegmentName",
			fieldLabel : "管道段名称",
			width : 300,
			hidden : true
		});
		var modifyfacebasic=new Ext.form.TextField({
			id : 'modifyfacebasic',
			name : "facebasic",
			fieldLabel : "基准面",
			hidden : true
		});
		var row1={
			layout : 'column', // 从左往右布局
			items : [{ // 该列有整行中所占百分比
				height : 30,
				layout : 'form', // 从上往下布局
				items : [{
					id : 'modifyPipeSegmentName',
					name : "pipeSegmentInfoBean.pipeSegmentName",
					xtype : 'textfield',
					fieldLabel : "管道段名称",
					allowBlank : false,
					blankText : '管道段名称不能为空',
					emptyText : "请输入管道段名称",
					readOnly:true,
					width : 150
				}]
			}, { // 该列有整行中所占百分比
				layout : 'form', // 从上往下布局
				items : [{
					xtype : 'textfield',
					id : 'modifyPipeId',
					name : "pipeSegmentInfoBean.pipeId",
					hidden : true
				},{
					//xtype : 'compositefield',
					layout : 'column',
					fieldLabel : '管道名称',
					items : [{
						id : 'modifyPipeName',
						name : 'pipeSegmentInfoBean.pipeName',
						xtype : 'textfield',
						emptyText : "请选择所属管道",
						width : 150,
						readOnly : true
					}, {
						xtype : 'button',
						text : '选择管道',
						width : 50,
						style : 'margin-left:5px',
						handler : function() {
							showPipeGrid();
						}
					}, {
						xtype : 'button',
						text : '清空',
						width : 25,
						style : 'margin-left:5px',
						handler : function() {
							Ext.getCmp("modifyPipeId").setValue("");
							Ext.getCmp("modifyPipeName").setValue("");
							Ext.getCmp("modifyAreano").setValue("");
							Ext.getCmp("modifyAreaname").setValue("");
						}
					}]
				}]
			}]
		}
		this.typeStore=new Ext.data.SimpleStore({
			fields : ['value', 'text'],
	    	data : [['', ''],['1', '人井管段'], ['2', '引上类型'], ['3', '引入类型']]
		})
		var row2={
			layout : 'column', // 从左往右布局
			items : [{ // 该列有整行中所占百分比
				layout : 'form', // 从上往下布局
				items : [{
					id : 'modifyPipeSegmentCode',
					name : "pipeSegmentInfoBean.pipeSegmentCode",
					xtype : 'textfield',
					fieldLabel : "管道段编号",
					emptyText : "请输入管道段编号",
					width : 150
				}]
			}, { // 该列有整行中所占百分比
				layout : 'form', // 从上往下布局
				items : [{
					id : "modifyPipeSegmentType",
					name : "pipeSegmentInfoBean.pipeSegmentType",
					typeAhead: true,
					xtype : 'combo',
		        	triggerAction: 'all',
					fieldLabel : "管道类型",
					emptyText : "请选择管道类型",
					blankText : '管道类型不能为空',
					forceSelection: true,
		         	mode: 'local',
		        	width : 150,
		         	store: this.typeStore,
		         	valueField: 'value',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
		       		displayField: 'text',//下拉框显示内容
		       		hiddenName:'pipeSegmentInfoBean.pipeSegmentType',//创建一个表单隐藏域来存储输入项的值
		       		editable:false,//设置为false以阻止用户直接向该输入项输入文本
		       		listeners:{
	                    select: function(combo ,record,value) {
	                        if(value=="1"){
								Ext.getCmp("modifyStartDeviceType").setValue("1");
								Ext.getCmp("modifystartdevicename").setValue("井");
								Ext.getCmp("modifyEndDeviceType").setValue("1");
								Ext.getCmp("modifyenddevicename").setValue("井");
								Ext.getCmp("modifywellmap").show();
								Ext.getCmp("modifyLedup").hide();
							}else if(value=="2"){
								Ext.getCmp("modifyStartDeviceType").setValue("1");
								Ext.getCmp("modifystartdevicename").setValue("井");
								Ext.getCmp("modifyEndDeviceType").setValue("2");
								Ext.getCmp("modifyenddevicename").setValue("电杆");
								Ext.getCmp("modifywellmap").hide();
								Ext.getCmp("modifyLedup").show();
							}else{
								Ext.getCmp("modifyStartDeviceType").setValue("2");
								Ext.getCmp("modifystartdevicename").setValue("电杆");
								Ext.getCmp("modifyEndDeviceType").setValue("1");
								Ext.getCmp("modifyenddevicename").setValue("井");
								Ext.getCmp("modifywellmap").hide();
								Ext.getCmp("modifyLedup").show();
							}
		                }
	                }
				}]
			}]
		}
		var row3={
			layout : 'column', // 从左往右布局
			items : [{ // 该列有整行中所占百分比
				height : 30,
				layout : 'form', // 从上往下布局
				items : [{
					id : 'modifyRoadName',
					name : "pipeSegmentInfoBean.roadName",
					xtype : 'textfield',
					fieldLabel : "道路名称",
					emptyText : "请输入道路名称",
					width : 150
				}]
			}, { // 该列有整行中所占百分比
				layout : 'form', // 从上往下布局
				items : [{
					id : 'modifyPipeSegmentLength',
					name : "pipeSegmentInfoBean.pipeSegmentLength",
					xtype : 'textfield',
					fieldLabel : "管道段长度(管程公里)",
					emptyText : "请输入管道段长度且只能为数字",
					width : 150,
					invalidText : '请输入正确的管道段长度！',
					validationEvent : 'keydown',
					validationDelay : 100,
					validator : function(thisText) {
						if (thisText == "") {
							return true;
						}
						var reg=/^[0-9]+\.{0,1}\d*$/;
						if(!reg.test(thisText)){
							Ext.getCmp('modifyPipeSegmentLength').markInvalid('填写格式不正确，请重新填写!');
							return false;
						}else{
							return true;
						}
					}
				}]
			}]
		}
		var row4={
			layout : 'column', // 从左往右布局
			items : [{ // 该列有整行中所占百分比
				height : 30,
				layout : 'form', // 从上往下布局
				items : [{
					id : 'modifyStartNestTopElevation',
					name : "pipeSegmentInfoBean.startNestTopElevation",
					xtype : 'textfield',
					fieldLabel : "起始管群顶高程(米)",
					emptyText : "请输入起始管群顶高程且只能为数字",
					width : 150,
					invalidText : '请输入正确的起始管群顶高程！',
					validationEvent : 'keydown',
					validationDelay : 100,
					validator : function(thisText) {
						if (thisText == "") {
							return true;
						}
						var reg=/^[0-9]+\.{0,1}\d*$/;
						if(!reg.test(thisText)){
							Ext.getCmp('addStartNestTopElevation').markInvalid('填写格式不正确，请重新填写!');
							return false;
						}else{
							return true;
						}
					}
				}]
			}, { // 该列有整行中所占百分比
				layout : 'form', // 从上往下布局
				items : [{
					id : 'modifyEndNestTopElevation',
					name : "pipeSegmentInfoBean.endNestTopElevation",
					xtype : 'textfield',
					fieldLabel : "终止管群顶高程(米)",
					emptyText : "请输入终止管群顶高程且只能为数字",
					width : 150,
					invalidText : '请输入正确的终止管群顶高程！',
					validationEvent : 'keydown',
					validationDelay : 130,
					validator : function(thisText) {
						if (thisText == "") {
							return true;
						}
						var reg=/^[0-9]+\.{0,1}\d*$/;
						if(!reg.test(thisText)){
							Ext.getCmp('modifyEndNestTopElevation').markInvalid('填写格式不正确，请重新填写!');
							return false;
						}else{
							return true;
						}
					}
				}]
			}]
		}
		var row5={
			layout : 'column', // 从左往右布局
			items : [{ // 该列有整行中所占百分比
				height : 30,
				layout : 'form', // 从上往下布局
				items : [{
					id : 'modifyStartNestBottomElevation',
					name : "pipeSegmentInfoBean.startNestBottomElevation",
					xtype : 'textfield',
					fieldLabel : "起始管道沟底高程(米)",
					emptyText : "请输入起始管道沟底高程且只能为数字",
					width : 150,
					invalidText : '请输入正确的起始管道沟底高程！',
					validationEvent : 'keydown',
					validationDelay : 100,
					validator : function(thisText) {
						if (thisText == "") {
							return true;
						}
						var reg=/^[0-9]+\.{0,1}\d*$/;
						if(!reg.test(thisText)){
							Ext.getCmp('modifyStartNestBottomElevation').markInvalid('填写格式不正确，请重新填写!');
							return false;
						}else{
							return true;
						}
					}
				}]
			}, { // 该列有整行中所占百分比
				layout : 'form', // 从上往下布局
				items : [{
					id : 'modifyEndNestBottomElevation',
					name : "pipeSegmentInfoBean.endNestBottomElevation",
					xtype : 'textfield',
					fieldLabel : "终止管道沟底高程(米)",
					emptyText : "请输入终止管道沟底高程且只能为数字",
					width : 150,
					invalidText : '请输入正确的终止管道沟底高程！',
					validationEvent : 'keydown',
					validationDelay : 100,
					validator : function(thisText) {
						if (thisText == "") {
							return true;
						}
						var reg=/^[0-9]+\.{0,1}\d*$/;
						if(!reg.test(thisText)){
							Ext.getCmp('modifyEndNestBottomElevation').markInvalid('填写格式不正确，请重新填写!');
							return false;
						}else{
							return true;
						}
					}
				}]
			}]
		}
		var row6={
			layout : 'column', // 从左往右布局
			items : [{ // 该列有整行中所占百分比
				height : 30,
				layout : 'form', // 从上往下布局
				items : [{
					id : 'modifyStartDeviceType',
					name : "pipeSegmentInfoBean.startDeviceType",
					xtype : 'textfield',
					fieldLabel : "起始设施类型",
					emptyText : "请输入起始设施类型",
					hidden:true
				},{
					id : 'modifystartdevicename',
					name : "startdevicename",
					xtype : 'textfield',
					fieldLabel : "起始设施类型",
					emptyText : "请输入起始设施类型",
					readOnly : true,
					width : 150
				}]
			}, { // 该列有整行中所占百分比
				layout : 'form', // 从上往下布局
				items : [{
					id : 'modifyEndDeviceType',
					name : "pipeSegmentInfoBean.endDeviceType",
					xtype : 'textfield',
					fieldLabel : "终止设施类型",
					emptyText : "请输入终止设施类型",
					hidden:true
				},{
					id : 'modifyenddevicename',
					name : "enddevicename",
					xtype : 'textfield',
					fieldLabel : "起始设施类型",
					emptyText : "请输入起始设施类型",
					readOnly : true,
					width : 150
				}]
			}]
		}
		var row7={
			layout : 'column', // 从左往右布局
			items : [{ // 该列有整行中所占百分比
				layout : 'form', // 从上往下布局
				items : [{
					xtype : 'textfield',
					id : "modifyStartDeviceId",
					name : "pipeSegmentInfoBean.startDeviceId",
					hidden : true
				},{
					layout : 'column',
					fieldLabel : '起始设施名称',
					items : [{
						id : 'modifyStartDeviceName',
						name : 'pipeSegmentInfoBean.startDeviceName',
						xtype : 'textfield',
						emptyText : "请选择起始设施名称",
						width : 150,
						readOnly : true
					}, {
						xtype : 'button',
						text : '选择插入',
						width : 50,
						style : 'margin-left:5px',
						handler : function() {
							var type1=Ext.getCmp("modifyStartDeviceType").getValue();
							if(type1==null||type1==""){
								Ext.Msg.show({
									title : '提示',
									width : 300,
									msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
											+ '请先选择管道类型！',
									buttons : {
										ok : "确定"
									}
								});
							}else if(type1=="1"){
								var ids=[];
								ids.push(Ext.getCmp("modifyPipeSegmentId").getValue());
								Ext.Ajax.request({
									url : 'pipe!searchPipeSegmentToCable.action',
									method : 'post',
									params : {
										'ids' : ids
									},
									success : function(response) {
										var json = Ext.util.JSON.decode(response.responseText);
										if(json.success==true){
											showWellGrid_zz();
										}else{
											Ext.Msg.show({
												title : '提示',
												width : 300,
												msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
															+ '该管道段已承载光缆，若要更换该设施，请先解除与光缆的关系!',
												buttons : {
													ok : "确定"
												}
											});
										}
									}
								})
								//showWellGrid_aa();
							}else{
								showPoleGrid_aa();
							}
						}
					}, {
						xtype : 'button',
						text : '清空',
						width : 25,
						style : 'margin-left:5px',
						handler : function() {
							Ext.getCmp("modifyStartDeviceId").setValue("");
							Ext.getCmp("modifyStartDeviceName").setValue("");
							Ext.getCmp("modifyStartFaceNo").setValue("");
						}
					}]
				}]
			}, { // 该列有整行中所占百分比
				layout : 'form', // 从上往下布局
				items : [{
					xtype : 'textfield',
					id : "modifyEndDeviceId",
					name : "pipeSegmentInfoBean.endDeviceId",
					hidden : true
				},{
					layout : 'column',
					fieldLabel : '终止设施名称',
					items : [{
						id : 'modifyEndDeviceName',
						name : 'pipeSegmentInfoBean.endDeviceName',
						xtype : 'textfield',
						emptyText : "请选择终止设施名称",
						width : 150,
						readOnly : true
					}, {
						xtype : 'button',
						text : '选择插入',
						width : 50,
						style : 'margin-left:5px',
						handler : function() {
							var type2=Ext.getCmp("modifyEndDeviceType").getValue();
							if(type2==null||type2==""){
								Ext.Msg.show({
									title : '提示',
									width : 300,
									msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
											+ '请先选择管道类型！',
									buttons : {
										ok : "确定"
									}
								});
							}else if(type2=="1"){
								var ids=[];
								ids.push(Ext.getCmp("modifyPipeSegmentId").getValue());
								Ext.Ajax.request({
									url : 'pipe!searchPipeSegmentToCable.action',
									method : 'post',
									params : {
										'ids' :ids 
									},
									success : function(response) {
										var json = Ext.util.JSON.decode(response.responseText);
										if(json.success==true){
											showWellGrid_zz();
										}else{
											Ext.Msg.show({
												title : '提示',
												width : 300,
												msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
															+ '该管道段已承载光缆，若要更换该设施，请先解除与光缆的关系!',
												buttons : {
													ok : "确定"
												}
											});
										}
									}
								})
							}else{
								showPoleGrid_zz();
							}
						}
					}, {
						xtype : 'button',
						text : '清空',
						width : 25,
						style : 'margin-left:5px',
						handler : function() {
							Ext.getCmp("modifyEndDeviceId").setValue("");
							Ext.getCmp("modifyEndDeviceName").setValue("");
							Ext.getCmp("modifyEndFaceNo").setValue("");
						}
					}]
				}]
			}]
		}
    	var row8={
    		id : 'modifywellmap',
			layout : 'column', // 从左往右布局
			hidden : true,
			items : [{ // 该列有整行中所占百分比
				height : 30,
				layout : 'form', // 从上往下布局
				items : [{
					id : 'modifyStartFaceNo',
					name : "pipeSegmentInfoBean.startFaceNo",
					xtype : 'textfield',
					fieldLabel : "起始井立面",
					emptyText : "请选择起始井立面",
					width : 150
				}]
			}, { // 该列有整行中所占百分比
				layout : 'form', // 从上往下布局
				items : [{
					id : 'modifyEndFaceNo',
					name : "pipeSegmentInfoBean.endFaceNo",
					xtype : 'textfield',
					fieldLabel : "终止井立面",
					emptyText : "请选择终止井立面",
					width : 150
				}]
			}]
		}
		this.layingStore=new Ext.data.SimpleStore({
			fields : ['value', 'text'],
	    	data : [['', ''],['1', '一字坡'], ['2', '人字坡']]
		})
		var row9={
			layout : 'column', // 从左往右布局
			items : [{ // 该列有整行中所占百分比
				layout : 'form', // 从上往下布局
				items : [{
					id : 'modifyBuriedDepth',
					name : "pipeSegmentInfoBean.buriedDepth",
					xtype : 'textfield',
					fieldLabel : "埋深(米)",
					emptyText : "请输入埋深且只能为数字",
					width : 150,
					invalidText : '请输入正确的埋深！',
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
			}, { // 该列有整行中所占百分比
				layout : 'form', // 从上往下布局
				items : [{
					id : "modifyPipeLineLayingEnumId",
					name : "pipeSegmentInfoBean.pipeLineLayingEnumId",
					typeAhead: true,
					xtype : 'combo',
		        	triggerAction: 'all',
					fieldLabel : "管道铺设工艺",
					emptyText : "请选择管道铺设工艺",
					blankText : '管道铺设工艺不能为空',
					forceSelection: true,
		         	mode: 'local',
		        	width : 150,
		         	store: this.layingStore,
		         	valueField: 'value',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
		       		displayField: 'text',//下拉框显示内容
		       		hiddenName:'pipeSegmentInfoBean.pipeLineLayingEnumId',//创建一个表单隐藏域来存储输入项的值
		       		editable:false//设置为false以阻止用户直接向该输入项输入文本
				}]
			}]
		}
		this.conStore = new Ext.data.SimpleStore({
    		fields : ['value', 'text'],
    		data : [['', ''],['1', '共建'], ['2', '共享'],['3', '自建自用（不包括自建预留）'], ['4', '自建预留']]
    	})
		var row10={
			layout : 'column', // 从左往右布局
			items : [{ // 该列有整行中所占百分比
				layout : 'form', // 从上往下布局
				items : [{
					id : "modifyConstructionSharingEnumId",
					name : "pipeSegmentInfoBean.constructionSharingEnumId",
					xtype : 'combo',
					typeAhead: true,
		        	triggerAction: 'all',
					fieldLabel : "共建/共享类型",
					emptyText : "请选择共建/共享类型",
					blankText : '共建/共享类型不能为空',
					forceSelection: true,
		         	mode: 'local',
		        	width : 150,
		         	store: this.conStore,
		         	valueField: 'value',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
		       		displayField: 'text',//下拉框显示内容
		       		hiddenName:'pipeSegmentInfoBean.constructionSharingEnumId',//创建一个表单隐藏域来存储输入项的值
		       		editable:false//设置为false以阻止用户直接向该输入项输入文本
				}]
			}, { // 该列有整行中所占百分比
				layout : 'form', // 从上往下布局
				items : [{
					id : 'modifyConstructionSharingOrg',
					name : "pipeSegmentInfoBean.constructionSharingOrg",
					xtype : 'textfield',
					fieldLabel : "共建/共享方",
					emptyText : "请输入共建/共享方",
					width : 150
				}]
			}]
		}
		this.sharingStore = new Ext.data.SimpleStore({
    		fields : ['value', 'text'],
    		data : [['', ''],['1', '我方共享他方'], ['2', '他方共享我方']]
    	})
		var row11={
			layout : 'column', // 从左往右布局
			items : [{ // 该列有整行中所占百分比
				layout : 'form', // 从上往下布局
				items : [{
					id : "modifySharingTypeEnumId",
					name : "pipeSegmentInfoBean.sharingTypeEnumId",
					xtype : 'combo',
					typeAhead: true,
		        	triggerAction: 'all',
					fieldLabel : "共享类型",
					emptyText : "请选择共享类型",
					blankText : '共享类型不能为空',
					forceSelection: true,
		         	mode: 'local',
		        	width : 150,
		         	store: this.sharingStore,
		         	valueField: 'value',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
		       		displayField: 'text',//下拉框显示内容
		       		hiddenName:'pipeSegmentInfoBean.sharingTypeEnumId',//创建一个表单隐藏域来存储输入项的值
		       		editable:false//设置为false以阻止用户直接向该输入项输入文本
				}]
			}, { // 该列有整行中所占百分比
				layout : 'form', // 从上往下布局
				items : [{
					id : 'modifyHoleQuantity',
					name : 'pipeSegmentInfoBean.holeQuantity',
					xtype : 'textfield',
					fieldLabel : '管孔数目',
					emptyText : "请输入管孔数目",
					width : 150,
					invalidText : '请输入正确的管孔数目！',
					validationEvent : 'keydown',
					validationDelay : 100,
					validator : function(thisText) {
						if (thisText == "") {
							return true;
						}
						var reg=/^[1-9]\d*$/;
						if(!reg.test(thisText)){
							Ext.getCmp('modifyHoleQuantity').markInvalid('填写格式不正确，请重新填写!');
							return false;
						}else{
							return true;
						}
					}
				}]
			}]
		}
		var row12={
			layout : 'column', // 从左往右布局
			items : [{ // 该列有整行中所占百分比
				layout : 'form', // 从上往下布局
				items : [{
					id : 'modifyBuildAndShareNum',
					name : 'pipeSegmentInfoBean.buildAndShareNum',
					xtype : 'textfield',
					fieldLabel : '共建/共享管孔数量',
					emptyText : "请输入共建/共享管孔数量",
					width : 150,
					invalidText : '请输入正确的共建/共享管孔数量！',
					validationEvent : 'keydown',
					validationDelay : 100,
					validator : function(thisText) {
						if (thisText == "") {
							return true;
						}
						var reg=/^[1-9]\d*$/;
						if(!reg.test(thisText)){
							Ext.getCmp('modifyBuildAndShareNum').markInvalid('填写格式不正确，请重新填写!');
							return false;
						}else{
							return true;
						}
					}
				}]
			}, { // 该列有整行中所占百分比
				layout : 'form', // 从上往下布局
				items : [{
					id : 'modifyOccupiedSharingHoleQuantity',
					name : 'pipeSegmentInfoBean.occupiedSharingHoleQuantity',
					xtype : 'textfield',
					fieldLabel : '共建/共享占用管孔数量',
					emptyText : "请输入共建/共享占用管孔数量",
					width : 150,
					invalidText : '请输入正确的共建/共享占用管孔数量！',
					validationEvent : 'keydown',
					validationDelay : 100,
					validator : function(thisText) {
						if (thisText == "") {
							return true;
						}
						var reg=/^[1-9]\d*$/;
						if(!reg.test(thisText)){
							Ext.getCmp('modifyOccupiedSharingHoleQuantity').markInvalid('填写格式不正确，请重新填写!');
							return false;
						}else{
							return true;
						}
					}
				}]
			}]
		}
		this.rentStore = new Ext.data.SimpleStore({
    		fields : ['value', 'text'],
    		data : [['', ''],['1', '是'], ['2', '否']]
    	})
		var row13={
			layout : 'column', // 从左往右布局
			items : [{ // 该列有整行中所占百分比
				layout : 'form', // 从上往下布局
				items : [{
					id : "modifyRentFlag",
					name : "pipeSegmentInfoBean.rentFlag",
					xtype : 'combo',
					typeAhead: true,
		        	triggerAction: 'all',
					fieldLabel : "是否租用",
					emptyText : "请选择是否租用",
					blankText : '是否租用不能为空',
					forceSelection: true,
		         	mode: 'local',
		        	width : 150,
		         	store: this.rentStore,
		         	valueField: 'value',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
		       		displayField: 'text',//下拉框显示内容
		       		hiddenName:'pipeSegmentInfoBean.rentFlag',//创建一个表单隐藏域来存储输入项的值
		       		editable:false//设置为false以阻止用户直接向该输入项输入文本
				}]
			}, { // 该列有整行中所占百分比
				layout : 'form', // 从上往下布局
				items : [{
					id : 'modifyRentOrg',
					name : "pipeSegmentInfoBean.rentOrg",
					xtype : 'textfield',
					fieldLabel : "租用单位",
					emptyText : "请输入租用单位",
					width : 150
				}]
			}]
		}
		this.resourceLifePeriodEnumId = new Ext.data.SimpleStore({
    		fields : ['value', 'text'],
    		data : [['', ''],['1', '设计状态'], ['2', '工程建设期（入网带业务前）'], ['3', '工程可用期（已入网带业务）'], 
    		['4', '工程初验后试运行'], ['5', '工程终验后在网'], ['6', '退网状态']]
    	})
		var row14={
			layout : 'column', // 从左往右布局
			items : [{ // 该列有整行中所占百分比
				layout : 'form', // 从上往下布局
				items : [{
					id : "modifyResourceLifePeriodEnumId",
					name : "pipeSegmentInfoBean.resourceLifePeriodEnumId",
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
		       		hiddenName:'pipeSegmentInfoBean.resourceLifePeriodEnumId',//创建一个表单隐藏域来存储输入项的值
		       		editable:false//设置为false以阻止用户直接向该输入项输入文本
				}]
			}, { // 该列有整行中所占百分比
				layout : 'form', // 从上往下布局
				items : [{
					id : 'modifyRentChargingCode',
					name : "pipeSegmentInfoBean.rentChargingCode",
					xtype : 'textfield',
					fieldLabel : "租用计费编号",
					emptyText : "请输入租用计费编号",
					width : 150
				}]
			}]
		}
		var row15={
			layout : 'column', // 从左往右布局
			items : [{ // 该列有整行中所占百分比
				height : 30,
				layout : 'form', // 从上往下布局
				items : [{
					id : 'modifyRentStartDate',
					name : 'pipeSegmentInfoBean.rentStartDate',
					xtype : "datefield",
					format : 'Y-m-d',// 日期格式
					fieldLabel : '租用起始时间',
					emptyText : "请选择租用起始时间",
					width : 150
				}]
			}, { // 该列有整行中所占百分比
				layout : 'form', // 从上往下布局
				items : [{
					id : 'modifyRentEndDate',
					name : 'pipeSegmentInfoBean.rentEndDate',
					xtype : "datefield",
					format : 'Y-m-d',// 日期格式
					fieldLabel : '租用到期时间',
					emptyText : "请选择租用到期时间",
					width : 150
				}]
			}]
		}
		var row16={
    		layout:'form',
    		items:[{
    			id : 'modifyAreano',
				name : "pipeSegmentInfoBean.areano",
				xtype:'textfield',
				fieldLabel : "所属局站",
				emptyText : "请输入所属局站",
				hidden:true,
				width : 150
    		},{
    			id : 'modifyAreaname',
				name : "pipeSegmentInfoBean.areaname",
				fieldLabel : "所属局站",
				emptyText : "请输入所属局站",
				xtype:'textfield',
				readOnly:true,
				width : 150
    		}]
    	}
		this.ledupPointId={
			id : 'modifyLedupPointId',
			name : "ledupInfoBean.ledupPointId",
			xtype : 'textfield',
			fieldLabel : "引上/引入ID",
			hidden : true
		}
		var yin1={
			layout:'column',
			items:[{
				layout : 'form',
				items:[{
					id : 'modifyLedupPointName',
					name : "ledupInfoBean.ledupPointName",
					xtype : 'textfield',
					fieldLabel : "引上/引入名称",
					emptyText : "请输入引上/引入名称",
					width : 150
				}]
			},{
				layout : 'form',
				items:[{
					id : 'modifyLedupPointCode',
					name : "ledupInfoBean.ledupPointCode",
					xtype : 'textfield',
					fieldLabel : "引上/引入点编号",
					emptyText : "请输入引上/引入点编号",
					width : 150
				}]
			}]
		}
		var yin2={
			layout:'column',
			items:[{
				layout : 'form',
				items:[{
					id : 'modifyWellName',
					name : "ledupInfoBean.wellName",
					xtype : 'textfield',
					fieldLabel : "井名称",
					emptyText : "请输入井名称",
					width : 150
				},{
					id : 'modifyWellId',
					name : "ledupInfoBean.wellId",
					xtype : 'textfield',
					fieldLabel : "井ID",
					emptyText : "请输入井ID",
					hidden : true
				}]
			},{
				layout : 'form',
				items:[{
					id : 'modifyPoleName',
					name : "ledupInfoBean.poleName",
					xtype : 'textfield',
					fieldLabel : "电杆名称",
					emptyText : "请输入电杆名称",
					width : 150
				},{
					id : 'modifyPoleId',
					name : "ledupInfoBean.poleId",
					xtype : 'textfield',
					fieldLabel : "电杆ID",
					hidden : true
				}]
			}]
		}
		var yin3={
			layout:'column',
			items:[{
				layout : 'form', // 从上往下布局
				items : [{
					id : 'modifyTubeQuantity',
					name : 'ledupInfoBean.tubeQuantity',
					xtype : 'textfield',
					fieldLabel : '引上/引入管数目',
					emptyText : "请输入引上/引入管数目",
					width : 150,
					invalidText : '请输入正确的引上/引入管数目！',
					validationEvent : 'keydown',
					validationDelay : 100,
					validator : function(thisText) {
						if (thisText == "") {
							return true;
						}
						var reg=/^[1-9]\d*$/;
						if(!reg.test(thisText)){
							Ext.getCmp('modifyTubeQuantity').markInvalid('填写格式不正确，请重新填写!');
							return false;
						}else{
							return true;
						}
					}
				}]
			},{
				layout : 'form', // 从上往下布局
				items : [{
					id : 'modifyOccupiedTubeQuantity',
					name : 'ledupInfoBean.occupiedTubeQuantity',
					xtype : 'textfield',
					fieldLabel : '已用引上/引入管数目',
					emptyText : "请输入已用引上/引入管数目",
					width : 150,
					invalidText : '请输入正确的已用引上/引入管数目！',
					validationEvent : 'keydown',
					validationDelay : 100,
					validator : function(thisText) {
						if (thisText == "") {
							return true;
						}
						var reg=/^[1-9]\d*$/;
						if(!reg.test(thisText)){
							Ext.getCmp('modifyOccupiedTubeQuantity').markInvalid('填写格式不正确，请重新填写!');
							return false;
						}else{
							return true;
						}
					}
				}]
			}]
		}
		var yin4={
			layout:'column',
			items:[{
				layout : 'form', // 从上往下布局
				items : [{
					id : 'modifyReservedTubeQuantity',
					name : 'ledupInfoBean.reservedTubeQuantity',
					xtype : 'textfield',
					fieldLabel : '预留引上/引入管数目',
					emptyText : "请输入预留引上/引入管数目",
					width : 150,
					invalidText : '请输入正确的预留引上/引入管数目！',
					validationEvent : 'keydown',
					validationDelay : 100,
					validator : function(thisText) {
						if (thisText == "") {
							return true;
						}
						var reg=/^[1-9]\d*$/;
						if(!reg.test(thisText)){
							Ext.getCmp('modifyReservedTubeQuantity').markInvalid('填写格式不正确，请重新填写!');
							return false;
						}else{
							return true;
						}
					}
				}]
			}]
		}
		this.items = [{
			xtype : 'fieldset',
			title : '管道段基本信息',
			collapsible : true,
			items : [this.pipeSegmentId,modifyfacebasic,row1,row2,row3,row4,row5,row6,row7,row8,
			row9,row10,row11,row12,row13,row14,row15,row16]
		},{
			id:'modifyLedup',
			xtype : 'fieldset',
			title : '引上/引入信息',
			collapsible : true,
			hidden : true,
			items : [this.ledupPointId,yin1,yin2,yin3,yin4]
		}];
		// 按钮
		this.buttons = [{
			text : "保存",
			icon : "imgs/save_btn.png",
			cls : "x-btn-text-icon",
			tooltip : '修改管道段信息',
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
		com.increase.cen.poleline.ModifyPipeSegment.superclass.initComponent.call(this);
	},
	// 提交事件
	btnSubmit : function submitForm() {
		if (!this.getForm().isValid()) {
			return;
		}
		var form=this.getForm();
		var type1=Ext.getCmp("modifyStartDeviceType").getValue();
		var type2=Ext.getCmp("modifyEndDeviceType").getValue();
		if(type1==type2&&type1=="2"){
			Ext.Msg.show({
				title : '提示',
				width : 300,
				msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'+'起始和终止设施不能同时选择电杆!',
				buttons : {
					ok : "确定"
				}
			});
			return;
		}
		//判断两个面的信息是否一致，并以哪个为基准面
		var startid=Ext.getCmp("modifyStartDeviceId").getValue();
		var startfaceno=Ext.getCmp("modifyStartFaceNo").getValue();
		var endid=Ext.getCmp("modifyEndDeviceId").getValue();
		var endfaceno=Ext.getCmp("modifyEndFaceNo").getValue();
		var type=Ext.getCmp("modifyPipeSegmentType").getValue();
		//验证电杆名称是否重复
		var pipename=Ext.getCmp("modifyPipeSegmentName").getValue();
		var oldpipename=Ext.getCmp("oldPipeSegmentName").getValue();
		if(pipename==oldpipename){
			formmit(form);
		}else{
			Ext.Ajax.request({
				url : 'pipe!checkPipeSegmentNameJSON.action',
				method : 'post',
				params : {
					'pipeSegmentName' : pipename
				},
				success : function(response) {
					var json = Ext.util.JSON.decode(response.responseText);
					if (json.success == false) {
						Ext.Msg.show({
							title : '提示',
							width : 300,
							msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'+'管道段名称重复请重新输入!',
							buttons : {
								ok : "确定"
							}
						});
					}else{
						if(type=="1"){
							if((startid==""||startfaceno=="")||(endid==""||endfaceno=="")){
								Ext.Msg.show({
									title : '提示',
									width : 300,
									msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'+'请选择井立面相关信息!',
									buttons : {
										ok : "确定"
									}
								});
								return ;
							}else{
								Ext.Ajax.request({
									url : 'pipe!checkfacetube.action',
									method : 'post',
									params : {
										'startDeviceId' : startid,
										'endDeviceId' : endid,
										'startFaceNo' : startfaceno,
										'endFaceNo' : endfaceno
									},
									success : function(response) {
										var json = Ext.util.JSON.decode(response.responseText);
										if (json.success == true) {
											//显示已哪个面为基准的窗口
											var pipeSegmentGrid = Ext.getCmp("pipeSegmentGrid");
											if (!pipeSegmentGrid.faceConfirmWindow) {
												pipeSegmentGrid.faceConfirmWindow = new com.increase.cen.poleline.PolelineWindow({
													id : "faceConfirmWindow",
													title : '选择井立面',
													iconCls : 'i-script-window',
													items : [{
														id : "faceConfirm",
														//height : 410,
														xtype : 'faceConfirm'// 窗口里的表单
										
													}]
												})
											}
											pipeSegmentGrid.faceConfirmWindow.show();
											Ext.getCmp("operate").setValue("modify");
											Ext.getCmp("commitform").setValue(form);
										}else{
											//提交
											Ext.getCmp("modifyfacebasic").setValue("0");
											formmit(form);
										}
									}
								})
							}
						}else{
							formmit(form);
						}
					}
				}
			});
		}
	},
	// 关闭
	btnClose : function() {
		// 重置表单
		Ext.getCmp("modifyPipeSegment").getForm().reset();
		// 窗口隐藏
		Ext.getCmp('modifyPipeSegmentWindow').close();
		// 刷新表格
		Ext.getCmp("pipeSegmentGrid").getStore().reload();
	}
});
Ext.reg("modifyPipeSegment", com.increase.cen.poleline.ModifyPipeSegment);
function formmit(form){
	form.doAction("submit", {
		url : 'pipe!dosavePipeSegment.action',
		submitEmptyText : false,				
		method : 'post',									
		success : function(form, action) {				
			Ext.Msg.show({
				title : '提示',
				width : 300,
				msg : '<img src="imgs/tip_success.png" align="center" hspace="30"/>'+ '修改管道段信息成功!',
				buttons : {
					ok : "确定"
				}
			});
			// 窗口隐藏
			Ext.getCmp('modifyPipeSegmentWindow').close();
			Ext.getCmp("pipeSegmentGrid").getStore().reload();
		},
		failure : function(form, action) {
			Ext.Msg.show({
				title : '提示',
				width : 300,
				msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'+ '修改管道段信息失败!',
				buttons : {
					ok : "确定"
				}
			});
		}
	});
}