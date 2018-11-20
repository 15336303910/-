var treeWindow = null;
var resourceGrid = null;
var checkLoader = new Ext.tree.TreeLoader({
	dataUrl:"hcgdManageAction!getChecks.action",
	requestMethod:"post"
});
var editWindow = null;
var loadingMask = new Ext.LoadMask(Ext.getBody(),{
	msg:"正在执行..."
});
function closeWindow(){
	if(editWindow!=null){
		editWindow.close();
		editWindow = null;
	}
}
var grid = null;
Ext.onReady(function(){
	var sm = new Ext.grid.CheckboxSelectionModel({
		checkOnly:true
	});
	var cm = new Ext.grid.ColumnModel([sm,{
		header:'id',
		dataIndex:'id',
		hidden:true
	},{
		header:'工单编号',
		dataIndex:"gdCode",
		width:130
	},{
		header:'所属资源',
		dataIndex:'resourceType',
		width:150,
		sortable:true
	},{
		header:'任务状态',
		dataIndex:'taskState',
		width:100,
		sortable:false,
		renderer:function(v){
			if(v=='未接单'){
				return "<font color='red'>未接单<font>";
			}else if(v=='已接单'){
				return "<font color='blue'>已接单<font>";
			}else if(v=='待审核'){
				return "<font color='green'>待审核<font>";
			}else if(v=='已完成'){
				return "<font color='#5088FF'>已完成<font>";
			}
		}
	},{
		header:'创建时间',
		dataIndex:'createDate',
		sortable:false,
		width:160,
		renderer:Ext.util.Format.dateRenderer('Y-m-d H:i:s')
	},{
		header:'任务主题',
		dataIndex:'taskSubject',
		sortable:false,
		width:500
	}]);
	var store = new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({
			url:"hcgdManageAction!getTaskList.action"
		}),
		reader:new Ext.data.JsonReader({
			totalProperty:"total",
			root:"items"
		},Ext.data.Record.create([{
			name:'id',
			type:'integer',
			mapping:'id',
			hidden:true
		},{
			name:'gdCode',
			type:'string',
			mapping:'gdCode'
		},{
			name:'resourceType',
			type:'string',
			mapping:'resourceType'
		},{
			name:'taskSubject',
			type:'string',
			mapping:'taskSubject'
		},{
			name:'taskState',
			type:'string',
			mapping:'taskState'
		},{
			name:'createDate',
			type:'date',
			mapping:'createDate'
		},{
			name:'currentUserName',
			type:'string',
			mapping:'currentUserName'
		}])),
		remoteSort:true
	});
	var pageSize = parseInt(Ext.getDom("pageSize").value);
	var limit = parseInt(Ext.getDom("limit").value);	
	grid = new Ext.grid.GridPanel({
		title:"当前位置：工单管理",
		region:'center',
		id:"configDataGrid",
		border:false,
		cm:cm,
		sm:sm,
		store:store,
		autoScroll:true,
		viewConfig:{
			sortAscText:'升序',
			sortDescText:'降序',
			columnsText:'可选列',
			forceFit:true,
			scrollOffset:-1
		},
		buttonAlign:"center",
		tbar:[{
			id:"helpBtn",
			icon:"imgs/bangzhu.png",
			cls:"x-btn-text-icon",
			handler:function(){}
		},{
			xtype:'tbseparator'
		},{
			xtype:'tbseparator'
		},{
			text:'派发任务',
			id:'addItemsBtn',
			icon:"imgs/fiber/link_add.png",
			cls:"x-btn-text-icon"
		},{
			xtype:'tbseparator'
		},{
			xtype:'tbseparator'
		},{
			text:'任务详情',
			id:'gdDetailBtn',
			icon:"imgs/fiber/link_add.png",
			cls:"x-btn-text-icon"
		},{
			xtype:'tbseparator'
		},{
			xtype:'tbseparator'
		},{
			text:'任务审核',
			id:'gdAuditBtn',
			icon:"imgs/fiber/link_add.png",
			cls:"x-btn-text-icon"
		},{
			xtype:'tbseparator'
		},{
			xtype:'tbseparator'
		},{
			xtype:'label',
			text:'任务状态：'
		},{
			id:'taskStateKey',
			name:'taskStateKey',
			cls:'sel-textfield-width',
			xtype:'combo',
			store:new Ext.data.SimpleStore({
	    		fields:['value', 'text'],
	    		data:[
	    		     ['',''],
	    		     ['未接单','未接单'],
	    		     ['已接单','已接单'],
	    		     ['待审核','待审核'],
	    		     ['已完成','已完成']
	    		]
	    	}),
			value:'',
			displayField:'text',
			valueField:'value',
			mode:'local',
			triggerAction:"all",
			editable:false,	
			width:130
		},{
			xtype:'tbseparator'
		},{
			xtype:'tbseparator'
		},{
			xtype:'label',
			text:'任务主题：'
		},{
			xtype:'textfield',
			id:'checkDescribe',
			cls:'sel-textfield-width',
			width:130
		},{
			xtype:'tbseparator'
		},{
			xtype:'tbseparator'
		},{
			id:'hiddenOfCode',
			name:"hiddenOfCode",
			xtype:'hidden'
		},{
			id:'seekPoleLineBtn',
			text:'搜索',
			icon:"imgs/queryBtn.png",
			cls:"x-btn-text-icon"
		},{
			xtype:'tbseparator'
		},{
			xtype:'tbseparator'
		}],
		bbar:new Ext.PagingToolbar({
			store:store,
			pageSize:pageSize,
			beforePageText:"当前第",
			afterPageText:"页，共{0}页",
			lastText:"尾页",
			nextText:"下一页",
			prevText:"上一页",
			firstText:"首页",
			refreshText:"刷新页面",
			displayInfo:true,
			displayMsg:"显示第{0}-{1}条 ，共{2}条",
			emptyMsg:"没有记录"
		})
	});
	var loadMarsk = new Ext.LoadMask(document.body,{
		msg:'数据加载中，请稍候......',
		disabled:false,
		store:store
	});
	store.addListener('beforeload',function(){
		loadMarsk.show();
	});
	store.load({
		params:{
			start:0,
			limit:limit
		}
	});
	new Ext.Viewport({
		layout:'border',
		items:[
		    grid
		]
	});
	/*任务审核*/
	var gdAuditBtn = Ext.getCmp("gdAuditBtn");
	gdAuditBtn.on('click',function(){
		var selModel = grid.getSelectionModel();
		var records = selModel.getSelections();	
		if(records.length!=1){
			Ext.Msg.show({
				title:'提示',
				width:300,
				msg:'<img src="imgs/tip_warn.png" align="center" hspace="30"/>请您选择一条核查任务!',
				buttons:{
					ok:"确定"
				}
			});
		}else{
			var taskState = records[0].get("taskState");
			if(taskState!="待审核"){
				Ext.Msg.show({
					title:'提示',
					width:300,
					msg:'<img src="imgs/tip_warn.png" align="center" hspace="30"/>当前任务不可审核!',
					buttons:{
						ok:"确定"
					}
				});
			}else{
				var checkedId = records[0].get("id");
				Ext.getCmp("hiddenOfCode").setValue(checkedId);
				var checkStore = new Ext.data.JsonStore({
					autoDestroy:true,
					proxy:new Ext.data.HttpProxy({
						url:"hcgdManageAction!getFatalChecks.action"
					}),
					totalProperty:"total",
					root:"items",
					fields:[
						"res_dimension",
						"resource_name",
						"check_item",
						"is_checked",
						"is_ok",
						"new_value",
						"contents"
					]
				});
				var checksView = new Ext.grid.GridView({
					scrollOffset:0,
					sortAscText:"正序",
					sortDescText:"倒序",
					columnsText:"列显示/隐藏",
					groupTextTpl:"{text} ({[values.rs.length]} 条记录)",
					forceFit:true
				});
				var checksRender = new Ext.grid.ColumnModel([
					new Ext.grid.RowNumberer(),{
						header:"资源类型",
						dataIndex:"res_dimension"
					},{
						header:"资源名称",
						dataIndex:"resource_name",
						width:140
					},{
						header:"核查内容",
						dataIndex:"check_item"
					},{
						header:"是否已查",
						dataIndex:"is_checked",
						renderer:function(v){
							if(v=='Y'){
								return "<font color='blue'>已查<font>";
							}else{
								return "<font color='red'>未查<font>";
							}
						}
					},{
						header:"是否正常",
						dataIndex:"is_ok",
						renderer:function(v){
							if(v=='Y'){
								return "<font color='blue'>是<font>";
							}else{
								return "<font color='red'>否<font>";
							}
						}
					},{
						header:"修正值",
						dataIndex:"new_value"
					},{
						header:"备注",
						dataIndex:"contents"
					}]
				);
				var detailPanel = new Ext.FormPanel({
					layout:'column',
	            	labelAlign:"right",
	            	labelWidth:70,
	            	frame:true,
	            	bodyBorder:false,
	            	items:[{
	            		columnWidth:1,
	        			xtype:'fieldset',
	        			title:'基础信息 ',
	        			layout:'column',
	        			collapsible:true,
	        			items:[{
	    				    columnWidth:0.5,
	    				    layout:'form',
	    				    labelAlign:"right",
	    				    style:"margin-top:17px",
	    				    items:[{
	    						id:'gdCodeDetail',
	    						name:"gdCodeDetail",
	    						xtype:'textfield',
	    						disabled:true,
	    						fieldLabel:"工单编号",
	    						allowBlank:false,
	    						blankText:"",
	    						emptyText:"",
	    						width:310
	    					}]
	    				},{
	    				    columnWidth:0.5,
	    				    layout:'form',
	    				    labelAlign:"right",
	    				    style:"margin-top:17px",
	    				    items:[{
	    						id:'resourceTypeDetail',
	    						name:"resourceTypeDetail",
	    						xtype:'textfield',
	    						disabled:true,
	    						fieldLabel:"资源类型",
	    						allowBlank:false,
	    						blankText:"",
	    						emptyText:"",
	    						width:310
	    					}]
	    				},{
	    				    columnWidth:0.5,
	    				    layout:'form',
	    				    labelAlign:"right",
	    				    style:"margin-top:10px",
	    				    items:[{
	    						id:'operatorUserName',
	    						name:"operatorUserName",
	    						xtype:'textfield',
	    						disabled:true,
	    						fieldLabel:"执行人员",
	    						allowBlank:false,
	    						blankText:"",
	    						emptyText:"",
	    						width:310
	    					}]
	    				},{
	    				    columnWidth:0.5,
	    				    layout:'form',
	    				    labelAlign:"right",
	    				    style:"margin-top:10px",
	    				    items:[{
	    						id:'taskSubjectDetail',
	    						name:"taskSubjectDetail",
	    						xtype:'textfield',
	    						disabled:true,
	    						fieldLabel:"任务主题",
	    						allowBlank:false,
	    						blankText:"",
	    						emptyText:"",
	    						width:310
	    					}]
	    				},{
	    				    columnWidth:0.5,
	    				    layout:'form',
	    				    labelAlign:"right",
	    				    style:"margin-top:10px",
	    				    items:[{
	    						id:'taskStateDetail',
	    						name:"taskStateDetail",
	    						xtype:'textfield',
	    						disabled:true,
	    						fieldLabel:"当前状态",
	    						allowBlank:false,
	    						blankText:"",
	    						emptyText:"",
	    						width:310
	    					}]
	    				},{
	    				    columnWidth:0.5,
	    				    layout:'form',
	    				    labelAlign:"right",
	    				    style:"margin-top:10px",
	    				    items:[{
	    						id:'taskRegionDetail',
	    						name:"taskRegionDetail",
	    						xtype:'textfield',
	    						disabled:true,
	    						fieldLabel:"资源区域",
	    						allowBlank:false,
	    						blankText:"",
	    						emptyText:"",
	    						width:310
	    					}]
	    				},{
	    				    columnWidth:0.5,
	    				    layout:'form',
	    				    labelAlign:"right",
	    				    style:"margin-top:10px",
	    				    items:[{
	    						id:'createDatetime',
	    						name:"createDatetime",
	    						xtype:'textfield',
	    						disabled:true,
	    						fieldLabel:"派单日期",
	    						allowBlank:false,
	    						blankText:"",
	    						emptyText:"",
	    						width:310
	    					}]
	    				},{
	    				    columnWidth:0.5,
	    				    layout:'form',
	    				    labelAlign:"right",
	    				    style:"margin-top:10px;margin-bottom:15px",
	    				    items:[{
	    						id:'finishDatetime',
	    						name:"finishDatetime",
	    						xtype:'textfield',
	    						disabled:true,
	    						fieldLabel:"完成日期",
	    						allowBlank:false,
	    						blankText:"",
	    						emptyText:"",
	    						width:310
	    					}]
	    				}]
	        		},{
	            		columnWidth:1,
	        			xtype:'fieldset',
	        			title:'核查结果 ',
	        			style:"margin-top:5px",
	        			collapsible:true,
	        			layout:'column',
	        			items:[
	        			    new Ext.grid.GridPanel({
		        				id:'checksGrid',
		        				store:checkStore,
		        				cm:checksRender,   
		        				view:checksView,
		        				loadMask:true,  
		        				border:false,
		        				autoHeight:false,
		        				autoScroll:false,
		        				stripeRows:true,
		        				border:true,
		        				frame:true,
		        				height:245,
		        				bbar:[{
		        					xtype:'tbseparator'
		        				},{
		        					xtype:'tbseparator'
		        				},{
		        					xtype:'label',
		        					text:'审批结论：'
		        				},{
		        					id:'isPassKey',
		        					name:'isPassKey',
		        					cls:'sel-textfield-width',
		        					xtype:'combo',
		        					store:new Ext.data.SimpleStore({
		        			    		fields:['value', 'text'],
		        			    		data:[
		        			    		     ['通过','通过'],
		        			    		     ['驳回','驳回']
		        			    		]
		        			    	}),
		        					value:'',
		        					displayField:'text',
		        					valueField:'value',
		        					mode:'local',
		        					triggerAction:"all",
		        					editable:false,	
		        					width:70
		        				},{
		        					xtype:'tbseparator'
		        				},{
		        					xtype:'tbseparator'
		        				},{
		        					xtype:'label',
		        					text:'审批结论：'
		        				},{
		        					id:'auditDescribe',
		        					xtype:'textfield',
		        					cls:'sel-textfield-width',
		        					width:510			
		        				},{
		        					xtype:'tbseparator'
		        				},{
		        					xtype:'tbseparator'
		        				},{
		        					id:'submitAuditBtn',
		        					text:'提交',
		        					icon:"imgs/fiber/link_add.png",
		        					cls:"x-btn-text-icon"
		        				},{
		        					xtype:'tbseparator'
		        				},{
		        					xtype:'tbseparator'
		        				}],
		        				listeners:{
		        					afterrender:function(thisTable){
		        						thisTable.getStore().load({
		        							params:{
		        								itemCode:checkedId
											}
		        						});
		        					}
		        				}      
	        			    })
	        			]
	        		}]
				});
				editWindow = new Ext.Window({
			    	width:850,
			    	height:500,
			    	title:"核查任务审核",
			        layout:'fit',
			        items:[
			            detailPanel
			        ]
			    });
			    editWindow.show();			    
			    var submitAuditBtn = Ext.getCmp("submitAuditBtn");
			    submitAuditBtn.on('click',function(){
					var auditResult = Ext.getCmp("isPassKey").getValue();
					if(auditResult==""){
						Ext.Msg.show({
							title:'提示',
							width:300,
							msg:'<img src="imgs/tip_warn.png" align="center" hspace="30"/>请选择审批结论...',
							buttons:{
								ok:"确定"
							}
						});
					}else{
						var auditDescribe = Ext.getCmp("auditDescribe").getValue();
						if(auditResult=="驳回" && auditDescribe==""){
							Ext.Msg.show({
								title:'提示',
								width:300,
								msg:'<img src="imgs/tip_warn.png" align="center" hspace="30"/>请填写驳回理由...',
								buttons:{
									ok:"确定"
								}
							});
						}else{
							Ext.Ajax.request({
						    	url:"hcgdManageAction!saveCheckAudit.action",
						    	method:'post',
						    	params:{
						    		taskCode:Ext.getCmp("hiddenOfCode").getValue(),
						    		auditResult:auditResult,
						    		auditDesc:auditDescribe
						    	},
						    	success:function(resp,opts){
						    		loadingMask.hide();
						    		var resultObject = Ext.decode(resp.responseText);
						    		if(resultObject["success"]){
						    			Ext.Msg.show({
											title:'提示',
											width:300,
											msg:'<img src="imgs/tip_warn.png" align="center" hspace="30"/>任务审核完毕.',
											buttons:{
												ok:"确定"
											}
										});
						    			Ext.getCmp('seekPoleLineBtn').fireEvent('click');
						    			if(editWindow!=null){
						    				editWindow.close();
						    				editWindow = null;
						    			}
						    		}
						    	},
						    	failure:function(resp,opts){
						    		Ext.MessageBox.alert("提示","请检查网络");
						    	}
						    });
						}
					}
				});		    
			    /*渲染数据*/
			    loadingMask.show();
			    Ext.Ajax.request({
			    	url:"hcgdManageAction!findDetail.action",
			    	method:'post',
			    	params:{
			    		itemCode:checkedId
			    	},
			    	success:function(resp,opts){
			    		loadingMask.hide();
			    		var resultObject = Ext.decode(resp.responseText);
			    		if(resultObject["success"]){
			    			var detailObject = resultObject["gdDetail"];
			    			Ext.getCmp("gdCodeDetail").setValue(detailObject["gd_code"]);
			    			Ext.getCmp("resourceTypeDetail").setValue(detailObject["resource_type"]);
			    			Ext.getCmp("taskSubjectDetail").setValue(detailObject["task_subject"]);
			    			Ext.getCmp("taskStateDetail").setValue(detailObject["task_state"]);
			    			Ext.getCmp("taskRegionDetail").setValue(detailObject["receive_region_name"]);
			    			Ext.getCmp("operatorUserName").setValue(detailObject["current_action_username"]);
			    			var dateObject = detailObject["create_datetime"];
			    			if(dateObject!=null){
			    				var thisDate = new Date();
			    				thisDate.setTime(dateObject.time);
			    				Ext.getCmp("createDatetime").setValue(thisDate.toLocaleString());
			    			}
			    			var finishObject = detailObject["finish_datetime"];
			    			if(finishObject!=null){
			    				var thisDate = new Date();
			    				thisDate.setTime(finishObject.time);
			    				Ext.getCmp("finishDatetime").setValue(thisDate.toLocaleString());
			    			}
			    		}
			    	},
			    	failure:function(resp,opts){
			    		loadingMask.hide();
			    		Ext.MessageBox.alert("提示","请检查网络");
			    	}
			    });
			}
		}
	});	
	/*任务详情*/
	var gdDetailBtn = Ext.getCmp("gdDetailBtn");
	gdDetailBtn.on('click',function(){
		var selModel = grid.getSelectionModel();
		var records = selModel.getSelections();	
		if(records.length!=1){
			Ext.Msg.show({
				title:'提示',
				width:300,
				msg:'<img src="imgs/tip_warn.png" align="center" hspace="30"/>请您选择一条核查任务!',
				buttons:{
					ok:"确定"
				}
			});
		}else{
			var checkedId = records[0].get("id");
			var checkStore = new Ext.data.JsonStore({
				autoDestroy:true,
				proxy:new Ext.data.HttpProxy({
					url:"hcgdManageAction!getCheckResult.action"
				}),
				totalProperty:"total",
				root:"items",
				fields:[
					"res_dimension",
					"resource_name",
					"check_item",
					"is_checked",
					"is_ok",
					"new_value",
					"contents"
				]
			});
			var checksView = new Ext.grid.GridView({
				scrollOffset:0,
				sortAscText:"正序",
				sortDescText:"倒序",
				columnsText:"列显示/隐藏",
				groupTextTpl:"{text} ({[values.rs.length]} 条记录)",
				forceFit:true
			});
			var checksRender = new Ext.grid.ColumnModel([
				new Ext.grid.RowNumberer(),{
					header:"资源类型",
					dataIndex:"res_dimension"
				},{
					header:"资源名称",
					dataIndex:"resource_name",
					width:140
				},{
					header:"核查内容",
					dataIndex:"check_item"
				},{
					header:"是否已查",
					dataIndex:"is_checked",
					renderer:function(v){
						if(v=='Y'){
							return "<font color='blue'>已查<font>";
						}else{
							return "<font color='red'>未查<font>";
						}
					}
				},{
					header:"是否正常",
					dataIndex:"is_ok",
					renderer:function(v){
						if(v=='Y'){
							return "<font color='blue'>是<font>";
						}else{
							return "<font color='red'>否<font>";
						}
					}
				},{
					header:"修正值",
					dataIndex:"new_value"
				},{
					header:"备注",
					dataIndex:"contents"
				}]
			);
			var detailPanel = new Ext.FormPanel({
				layout:'column',
            	labelAlign:"right",
            	labelWidth:70,
            	frame:true,
            	bodyBorder:false,
            	items:[{
            		columnWidth:1,
        			xtype:'fieldset',
        			title:'基础信息 ',
        			layout:'column',
        			collapsible:true,
        			items:[{
    				    columnWidth:0.5,
    				    layout:'form',
    				    labelAlign:"right",
    				    style:"margin-top:17px",
    				    items:[{
    						id:'gdCodeDetail',
    						name:"gdCodeDetail",
    						xtype:'textfield',
    						disabled:true,
    						fieldLabel:"工单编号",
    						allowBlank:false,
    						blankText:"",
    						emptyText:"",
    						width:310
    					}]
    				},{
    				    columnWidth:0.5,
    				    layout:'form',
    				    labelAlign:"right",
    				    style:"margin-top:17px",
    				    items:[{
    						id:'resourceTypeDetail',
    						name:"resourceTypeDetail",
    						xtype:'textfield',
    						disabled:true,
    						fieldLabel:"资源类型",
    						allowBlank:false,
    						blankText:"",
    						emptyText:"",
    						width:310
    					}]
    				},{
    				    columnWidth:0.5,
    				    layout:'form',
    				    labelAlign:"right",
    				    style:"margin-top:10px",
    				    items:[{
    						id:'operatorUserName',
    						name:"operatorUserName",
    						xtype:'textfield',
    						disabled:true,
    						fieldLabel:"执行人员",
    						allowBlank:false,
    						blankText:"",
    						emptyText:"",
    						width:310
    					}]
    				},{
    				    columnWidth:0.5,
    				    layout:'form',
    				    labelAlign:"right",
    				    style:"margin-top:10px",
    				    items:[{
    						id:'taskSubjectDetail',
    						name:"taskSubjectDetail",
    						xtype:'textfield',
    						disabled:true,
    						fieldLabel:"任务主题",
    						allowBlank:false,
    						blankText:"",
    						emptyText:"",
    						width:310
    					}]
    				},{
    				    columnWidth:0.5,
    				    layout:'form',
    				    labelAlign:"right",
    				    style:"margin-top:10px",
    				    items:[{
    						id:'taskStateDetail',
    						name:"taskStateDetail",
    						xtype:'textfield',
    						disabled:true,
    						fieldLabel:"当前状态",
    						allowBlank:false,
    						blankText:"",
    						emptyText:"",
    						width:310
    					}]
    				},{
    				    columnWidth:0.5,
    				    layout:'form',
    				    labelAlign:"right",
    				    style:"margin-top:10px",
    				    items:[{
    						id:'taskRegionDetail',
    						name:"taskRegionDetail",
    						xtype:'textfield',
    						disabled:true,
    						fieldLabel:"资源区域",
    						allowBlank:false,
    						blankText:"",
    						emptyText:"",
    						width:310
    					}]
    				},{
    				    columnWidth:0.5,
    				    layout:'form',
    				    labelAlign:"right",
    				    style:"margin-top:10px",
    				    items:[{
    						id:'createDatetime',
    						name:"createDatetime",
    						xtype:'textfield',
    						disabled:true,
    						fieldLabel:"派单日期",
    						allowBlank:false,
    						blankText:"",
    						emptyText:"",
    						width:310
    					}]
    				},{
    				    columnWidth:0.5,
    				    layout:'form',
    				    labelAlign:"right",
    				    style:"margin-top:10px;margin-bottom:15px",
    				    items:[{
    						id:'finishDatetime',
    						name:"finishDatetime",
    						xtype:'textfield',
    						disabled:true,
    						fieldLabel:"完成日期",
    						allowBlank:false,
    						blankText:"",
    						emptyText:"",
    						width:310
    					}]
    				}]
        		},{
            		columnWidth:1,
        			xtype:'fieldset',
        			title:'核查结果 ',
        			style:"margin-top:5px",
        			collapsible:true,
        			layout:'column',
        			items:[
        			    new Ext.grid.GridPanel({
	        				id:'checksGrid',
	        				store:checkStore,
	        				cm:checksRender,   
	        				view:checksView,
	        				loadMask:true,  
	        				border:false,
	        				autoHeight:false,
	        				autoScroll:false,
	        				stripeRows:true,
	        				border:true,
	        				frame:true,
	        				height:245,
	        				listeners:{
	        					afterrender:function(thisTable){
	        						thisTable.getStore().load({
	        							params:{
	        								itemCode:checkedId
										}
	        						});
	        					}
	        				}      
        			    })
        			]
        		}]
			});
			editWindow = new Ext.Window({
		    	width:850,
		    	height:500,
		    	title:"核查任务详情",
		        layout:'fit',
		        items:[
		            detailPanel
		        ]
		    });
		    editWindow.show();	
		    /*渲染数据*/
		    loadingMask.show();
		    Ext.Ajax.request({
		    	url:"hcgdManageAction!findDetail.action",
		    	method:'post',
		    	params:{
		    		itemCode:checkedId
		    	},
		    	success:function(resp,opts){
		    		loadingMask.hide();
		    		var resultObject = Ext.decode(resp.responseText);
		    		if(resultObject["success"]){
		    			var detailObject = resultObject["gdDetail"];
		    			Ext.getCmp("gdCodeDetail").setValue(detailObject["gd_code"]);
		    			Ext.getCmp("resourceTypeDetail").setValue(detailObject["resource_type"]);
		    			Ext.getCmp("taskSubjectDetail").setValue(detailObject["task_subject"]);
		    			Ext.getCmp("taskStateDetail").setValue(detailObject["task_state"]);
		    			Ext.getCmp("taskRegionDetail").setValue(detailObject["receive_region_name"]);
		    			Ext.getCmp("operatorUserName").setValue(detailObject["current_action_username"]);
		    			var dateObject = detailObject["create_datetime"];
		    			if(dateObject!=null){
		    				var thisDate = new Date();
		    				thisDate.setTime(dateObject.time);
		    				Ext.getCmp("createDatetime").setValue(thisDate.toLocaleString());
		    			}
		    			var finishObject = detailObject["finish_datetime"];
		    			if(finishObject!=null){
		    				var thisDate = new Date();
		    				thisDate.setTime(finishObject.time);
		    				Ext.getCmp("finishDatetime").setValue(thisDate.toLocaleString());
		    			}
		    		}
		    	},
		    	failure:function(resp,opts){
		    		loadingMask.hide();
		    		Ext.MessageBox.alert("提示","请检查网络");
		    	}
		    });    
		}
	});
	/*添加检查项*/
	var addItemsBtn = Ext.getCmp("addItemsBtn");
	addItemsBtn.on('click',function(){		
		var navHandler = function(direction){		
	        var wizard = Ext.getCmp('wizard').layout;
	        var prev = Ext.getCmp('move-prev');
	        var next = Ext.getCmp('move-next');
	        var activeId = wizard.activeItem.id;		
	        if(activeId == 'card-0'){
	            if(direction==1){
	            	if(Ext.getCmp("card-0").getForm().isValid()){
	            		wizard.setActiveItem(1);
		                prev.setDisabled(false);
	        		}else{
	        			Ext.Msg.show({
	    					title:'提示',
	    					width:300,
	    					msg:'<img src="imgs/tip_success.png" align="center" hspace="30" />请仔细填写工单信息!',
	    					buttons:{
	    						ok:"确定"
	    					}
	    				});
	        		}
	            }
	        }else if(activeId=='card-1'){
	            if(direction==-1){
	                wizard.setActiveItem(0);
	                prev.setDisabled(true);
	            }else{
	            	/*验证数据并保存，如果保存成功则跳转到第三步*/
	            	var dataTree = Ext.getCmp("card-1");
	            	var checkedItems = dataTree.getChecked();
	            	if(checkedItems.length>0){
	            		var checkedNodes = "";
	            		for(var i=0;i<checkedItems.length;i++){
	            			if(checkedNodes==""){
	            				checkedNodes = parseInt(checkedItems[i].id);
	            			}else{
	            				checkedNodes+=","+parseInt(checkedItems[i].id);
	            			}
	            		}
	            		var requestParams = {
	            			receiveRegionCode:Ext.getCmp("gdTaskMain.receiveRegionCode").getValue(),
	            			receiveRegionName:Ext.getCmp("gdTaskMain.receiveRegionName").getValue(),
	            			resourceType:Ext.getCmp("gdTaskMain.resourceType").getValue(),
	            			resourceCode:Ext.getCmp("gdTaskMain.resourceCode").getValue(),
	            			resourceName:Ext.getCmp("gdTaskMain.resourceName").getValue(),
	            			taskSubject:Ext.getCmp("gdTaskMain.taskSubject").getValue(),
	            			finishDate:document.getElementById("gdTaskMain.finishDate").value,
	            			taskDescribe:Ext.getCmp("gdTaskMain.taskDescribe").getValue(),	            			
	            			items:checkedNodes
	            		};
	            		loadingMask.show();
	            		Ext.Ajax.request({
	            			url:"hcgdManageAction!saveAudits.action",
	            			method:'post',
	            			params:{
	            				thisObject:Ext.encode(requestParams)
	            			},
	            			success:function(resp,opts){
	            				var resultObject = Ext.decode(resp.responseText);
	            				loadingMask.hide();
	            				if(resultObject["success"]){
	            					wizard.setActiveItem(2);
	        	                	next.setDisabled(true); 
	        	                	Ext.getCmp('seekPoleLineBtn').fireEvent('click');
	        	                	setTimeout('javascript:closeWindow();',1200);
	            				}else{
	            					Ext.Msg.show({
	        	    					title:'提示',
	        	    					width:300,
	        	    					msg:'<img src="imgs/tip_success.png" align="center" hspace="30"/>任务发布失败!',
	        	    					buttons:{
	        	    						ok:"确定"
	        	    					}
	        	    				});
	            				}
	            			},
	            			failure:function(resp,opts){
	            				loadingMask.hide();
	            				Ext.MessageBox.alert("提示","请检查网络");
	            			}
	            		});        		          		
	            	}else{
	            		Ext.Msg.show({
	    					title:'提示',
	    					width:300,
	    					msg:'<img src="imgs/tip_success.png" align="center" hspace="30"/>请选择核查内容!',
	    					buttons:{
	    						ok:"确定"
	    					}
	    				});
	            	}
	            }
	        }else if(activeId == 'card-2'){
	            if(direction==-1){
	                wizard.setActiveItem(1);
	                next.setDisabled(false);
	            }
	        }
	    };		
	    var viewport = new Ext.Panel({
	        layout:'border',
	        items:[{
	            id:'wizard',
	            region:'north',
	            width:800,
	            height:315,
	            layout:'card',
	            activeItem: 0,
	            defaults:{
	                border:false
	            },
	            bbar:[{
	                id:'move-prev',
	                text:'上一步',
	                handler:function(){
						navHandler(-1);
					},
					disabled:true
	            },'->',{
	                id:'move-next',
	                text:'下一步',
	                handler: function() {
						navHandler(1);
					}
	            }],
	            items:[new Ext.FormPanel({
	                	id:"card-0",
	                	layout:'column',
	                	labelAlign:"right",
	                	buttonAlign:'center',
	                	labelWidth:120,
	                	frame:true,
	                	bodyBorder:false,
	                	items:[{
						    columnWidth:0.82,
						    layout:'form',
						    labelAlign:"right",
						    style:"margin-top:17px",
						    items:[{
								id:'gdTaskMain.receiveRegionCode',
								name:"gdTaskMain.receiveRegionCode",
								xtype:'hidden'
							},{
								id:'gdTaskMain.receiveRegionName',
								name:"gdTaskMain.receiveRegionName",
								xtype:'textfield',
								readOnly:true,
								fieldLabel:"选定接收组",
								allowBlank:false,
								blankText:"",
								emptyText:"",
								width:340
							}]
						},{
						    columnWidth:0.18,
						    layout:'form',
						    labelAlign:"right",
						    style:"margin-top:14px",
						    items:[
						        new Ext.Button({
									text:"选择",
									style:"margin-top:2px",
									allowDepress:true,
									enableToggle:true,
									width:58,
									height:23,
									handler:function(){										
										var treeloader = new Ext.tree.TreeLoader({
											dataUrl:"hcgdManageAction!getOrganizations.action",
											requestMethod:"post"
										});
										var treePanel = new Ext.tree.TreePanel({
										    autoScroll:true,
										    animate:true,
										    width:250,
										    bodyStyle:"background-color:transparent;padding:0 0 0 0;",
										    loader:treeloader,
										    rootVisible:false,		
											root:{
												nodeType:"async",
												text:"root",
												draggable:false,
												expanded:false,
												id:"source"
											},
											listeners:{
												click:function(node,e){
													if(node.isLeaf()){
														Ext.getCmp("gdTaskMain.receiveRegionCode").setValue(node.id);
														Ext.getCmp("gdTaskMain.receiveRegionName").setValue(node.text);														
														treeWindow.close();
														treeWindow = null;
									        		}
												}
											}
										});
										treeWindow = new Ext.Window({
											title:"选择核查任务接收组", 
									    	width:250,
									    	height:300,
									        layout:'fit',
									        items:[
									            treePanel
									        ]
									    });
										treeWindow.show();									
									}
								})
						    ]
						},{
						    columnWidth:1,
						    layout:'form',
						    labelAlign:"right",
						    style:"margin-top:7px",
						    items:[{
								xtype:'combo',
								id:"gdTaskMain.resourceType",
								name:"gdTaskMain.resourceType",					
								typeAhead:true,
					        	triggerAction:'all',
								fieldLabel:"资源类型",
								autoLoad:true,
								emptyText:"",
								blankText:"",
								forceSelection:true,
					         	mode:'remote',
					        	width:400,
					         	store:new Ext.data.JsonStore({
								    url:"hcgdManageAction!getTopResTypes.action",
									fields:["resourceCode","resourceName"]
								}),
					         	valueField:'resourceName', 
					       		displayField:'resourceName',
					       		editable:false,
					       		listeners:{ 
					       			select:function(combo,record,opts){  
					       				var checkedType = Ext.getCmp("gdTaskMain.resourceType").getValue();
					       				var aphyciniTree = Ext.getCmp('card-1');
					       				var loader = aphyciniTree.getLoader();
					       				loader.on('beforeload',function(loader,node){
					       					this.baseParams.resourceType = checkedType;
					       				},loader);
					       				loader.load(aphyciniTree.root);
					       			}  
					       		}   
							}]
						},{
						    columnWidth:0.82,
						    layout:'form',
						    labelAlign:"right",
						    style:"margin-top:7px",
						    items:[{
								id:'gdTaskMain.resourceCode',
								name:"gdTaskMain.resourceCode",
								xtype:'hidden'
							},{
								id:'gdTaskMain.resourceName',
								name:"gdTaskMain.resourceName",
								xtype:'textfield',
								fieldLabel:"任务资源",
								readOnly:true,
								allowBlank:false,
								blankText:"",
								emptyText:"",
								width:340
							}]
						},{
						    columnWidth:0.18,
						    layout:'form',
						    labelAlign:"right",
						    items:[
						        new Ext.Button({
									text:"选择",
									style:"margin-top:6px",
									allowDepress:true,
									enableToggle:true,
									width:58,
									height:23,
									handler:function(){
										var regionName = Ext.getCmp("gdTaskMain.receiveRegionName").getValue();
										if(regionName==null || regionName==""){
											Ext.Msg.show({
						    					title:'提示',
						    					width:300,
						    					msg:'<img src="imgs/tip_success.png" align="center" hspace="30" />请先选择接收组!',
						    					buttons:{
						    						ok:"确定"
						    					}
						    				});
										}else{
											var resourceType = Ext.getCmp("gdTaskMain.resourceType").getValue();
											if(resourceType==null || resourceType==""){
												Ext.Msg.show({
							    					title:'提示',
							    					width:300,
							    					msg:'<img src="imgs/tip_success.png" align="center" hspace="30" />请先选择资源类型!',
							    					buttons:{
							    						ok:"确定"
							    					}
							    				});
											}else{
												/*资源列表*/
												var gridButtons = ['->','-',{
													xtype:'tbseparator'
												},{
													xtype:'label',
													text:'资源名称：'
												},{
													xtype:'textfield',
													id:'resourceNameKey',
													cls:'sel-textfield-width',
													width:250
												},'-','-',{
													id:'seedSomeResource',
													text:'搜索',
													icon:"imgs/queryBtn.png",
													cls:"x-btn-text-icon"
												},'-','-',{
												    text:"确定",
												    icon:"imgs/fiber/link_add.png",
													cls:"x-btn-text-icon",
												    handler:function(){
												    	var selModel = resourceGrid.getSelectionModel();
														if(selModel.hasSelection()){
															var records = selModel.getSelections();
															var checkedCodes = "";
															var checkedNames = "";
															for(var w=0;w<records.length;w++){
																var checkedRecord = records[w];
																if(checkedCodes==""){
																	checkedCodes = records[w].get("resource_code");
																	checkedNames = records[w].get("resource_name");
																}else{
																	checkedCodes+= ","+records[w].get("resource_code");
																	checkedNames+= ","+records[w].get("resource_name");
																}
															}
															Ext.getCmp("gdTaskMain.resourceCode").setValue(checkedCodes);
															Ext.getCmp("gdTaskMain.resourceName").setValue(checkedNames);
															treeWindow.close();
															treeWindow = null;
														}else{
															Ext.Msg.show({
										    					title:'提示',
										    					width:300,
										    					msg:'<img src="imgs/tip_success.png" align="center" hspace="30" />请先选定资源...',
										    					buttons:{
										    						ok:"确定"
										    					}
										    				});
														}
												    }
												},'-','-'];
												var jsonStore = new Ext.data.JsonStore({
													autoDestroy:true,
													proxy:new Ext.data.HttpProxy({
														url:"hcgdManageAction!getResourceData.action"
													}),
													baseParams:{
														start:0,
														limit:15
													},
													paramNames:{
														start:"start",   
														limit:"length"       
													},
													totalProperty:"total",
													root:"items",
													fields:[
														"resource_code",
														"resource_name"
													]
												});
												var gridView = new Ext.grid.GridView({
													scrollOffset:0,
													sortAscText:"正序",
													sortDescText:"倒序",
													columnsText:"列显示/隐藏",
													groupTextTpl:"{text} ({[values.rs.length]} 条记录)",
													forceFit:true
												});
												var checkedModel = new Ext.grid.CheckboxSelectionModel({
									 				singleSelect:false
												});
												var callBackRender = new Ext.grid.ColumnModel(
													[new Ext.grid.RowNumberer(),checkedModel,{
		                               					header:"资源编号",dataIndex:"resource_code",hidden:true
		                               				},{
		                               					header:"资源名称",dataIndex:"resource_name"
		                               				}]
		                               			);
												var buttomBar = new Ext.PagingToolbar({
													pageSize:15,
													store:jsonStore,
													displayInfo:true,
													displayMsg:"显示第 {0} 条到 {1} 条记录，一共 {2} 条",
													emptyMsg:"没有记录",
													beforePageText:"第",
													afterPageText:"页，共 {0}页"
												});
												resourceGrid = new Ext.grid.GridPanel({
													id:'gridPanel',
													region:"center",
													store:jsonStore,
													cm:callBackRender,   
													sm:checkedModel,  
													view:gridView,
													loadMask:true,
													border:false,
													autoHeight:false,
													autoScroll:false,
													stripeRows:true,
													border:true,
													frame:true,
													height:350,
													tbar:gridButtons,
													listeners:{
														afterrender:function(thisTable){
															thisTable.getStore().load({
																params:{
																	start:0,
																	length:15,
																	"gdTaskMain.resourceType":resourceType,
																	"gdTaskMain.receiveRegionName":regionName
																}
															});
														}
													}      
												});
												treeWindow = new Ext.Window({
													title:"选择核查资源", 
											    	width:600,
											    	height:355,
											        layout:'fit',
											        items:[
											             resourceGrid
											        ]
											    });
												treeWindow.show();
												/*绑定查询事件*/
												var seedSomeResourceBtn = Ext.getCmp("seedSomeResource");
												seedSomeResourceBtn.on('click', function(){		
													resourceGrid.getStore().load({
														params:{
															"gdTaskMain.receiveRegionName":Ext.getCmp("gdTaskMain.receiveRegionName").getValue(),
															"gdTaskMain.resourceType":Ext.getCmp("gdTaskMain.resourceType").getValue(),
															"gdTaskMain.resourceName":Ext.getCmp("resourceNameKey").getValue()
														}
													});
													resourceGrid.getStore().on('beforeload',function(){
														resourceGrid.getStore().baseParams = {
															"gdTaskMain.receiveRegionName":Ext.getCmp("gdTaskMain.receiveRegionName").getValue(),
															"gdTaskMain.resourceType":Ext.getCmp("gdTaskMain.resourceType").getValue(),
															"gdTaskMain.resourceName":Ext.getCmp("resourceNameKey").getValue()
														};
													});
													resourceGrid.getView().refresh();
												});									
											}
										}
									}
								})
						    ]
						},{
						    columnWidth:1,
						    layout:'form',
						    labelAlign:"right",
						    style:"margin-top:7px",
						    items:[{
								id:'gdTaskMain.taskSubject',
								name:"gdTaskMain.taskSubject",
								xtype:'textfield',
								fieldLabel:"任务主题",
								allowBlank:false,
								blankText:"",
								emptyText:"",
								width:400
							}]
						},{
						    columnWidth:1,
						    layout:'form',
						    labelAlign:"right",
						    style:"margin-top:7px",
						    items:[{
								id:'gdTaskMain.finishDate',
								name:"gdTaskMain.finishDate",
								xtype:'datefield',
								fieldLabel:"最迟完成时间",
								allowBlank:false,
								format:"Y-m-d 23:59:59",
								width:400
							}]
						},{
						    columnWidth:1,
						    layout:'form',
						    labelAlign:"right",
						    style:"margin-top:7px",
						    items:[{
								id:'gdTaskMain.taskDescribe',
								name:"gdTaskMain.taskDescribe",
								xtype:'textarea',
								fieldLabel:"任务描述",
								allowBlank:false,
								blankText:"",
								emptyText:"",								
								width:400,
								height:50,
								allowBlank:false,
								minLength:1,
								maxLength:200
							}]
						}]
	                }),new Ext.tree.TreePanel({
	                	id:'card-1',
	                    autoScroll:true,
	                    animate:true,
	                    width:550,
	                    height:210,
	                    bodyStyle:"background-color:transparent;padding:0 0 0 0;margin-top:10px",
	                    loader:checkLoader,
	                    rootVisible:false,		
	                	root:{
	                		nodeType:"async",
	                		text:"root",
	                		draggable:false,
	                		expanded:false,
	                		id:"source"
	                	}
	                }),{
		                id:'card-2',
		                html:"<div style='width:99%;height:220px;'><center><div style='margin-top:90px'><font size='5'>任务发布成功...</font></div></center></div>"
		            }]
	        },{
	            region:'center',
	            split:true,
	            border:true
	        }]
	    });	
	    editWindow = new Ext.Window({
	    	width:600,
	    	height:350,
	    	title:"核查任务派发",
	        layout:'fit',
	        items:[
	            viewport
	        ]
	    });
	    editWindow.show();
	});
	/*执行查询*/
	var seekPoleLineBtn = Ext.getCmp("seekPoleLineBtn");
	seekPoleLineBtn.on('click', function(){		
		grid.getStore().load({
			params:{
				start:0,
				limit:limit,
				'gdTaskMain.taskSubject':Ext.getCmp("checkDescribe").getValue(),
				'gdTaskMain.taskState':Ext.getCmp("taskStateKey").getValue()
			}
		});
		grid.getStore().on('beforeload',function(){
			grid.getStore().baseParams = {
				'gdTaskMain.taskSubject':Ext.getCmp("checkDescribe").getValue(),
				'gdTaskMain.taskState':Ext.getCmp("taskStateKey").getValue()
			};
		});
		grid.getView().refresh();
	});
});