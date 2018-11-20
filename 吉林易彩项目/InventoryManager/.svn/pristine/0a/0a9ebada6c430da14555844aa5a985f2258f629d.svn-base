var rowI=0;
Ext.onReady(function() {
	Ext.useShims = false;
	var typeStore = new Ext.data.SimpleStore({
		fields : ['value', 'text'],
		data : [['0', '设备'], ['1', '告警级别'],['2', '告警状态'],['3', '告警类型']]
	});
	// 列模型
	var cm = new Ext.grid.ColumnModel([ {
		header : "序号",
		width : 60,
		hideable : false,
		hidden : true,
		renderer : function(value, metadata, record, rowIndex, colIndex, store) {
			var start = store.lastOptions.params.start;
			if (isNaN(start)) {
				start = 0;
			}
			rowI=start + rowIndex + 1;
			return start + rowIndex + 1;
		}
	},{
		header : '告警级别',
		dataIndex : 'level',                                    
		width : 180,
		renderer:function(v){
						if(v == '0'){
							return '一杆告警';
						}else if(v == '1'){
							return '二杆告警';					
						}else if(v == '2'){
							return '本地告警';	
						}
					}
	}, {
		header : '发生告警数',
		dataIndex : 'happenAlarm',
		width : 130
	}, {
		header : '盘告警条数',
		dataIndex : 'panAlarm',
		width : 130
	},  {
		header : '端子告警条数',
		dataIndex : 'pointAlarm',
		width : 130
	},{
		header : '拔出告警条数',
		dataIndex : 'pullAlarm',
		width : 130
	}, {
		header : '不匹配告警条数',
		dataIndex : 'inmatchAlarm',
		width : 130
	}]);
	// 从远程读取数据
	var proxy = new Ext.data.HttpProxy({
		url : "statistics!alarmLevelStat.action"
	});
	// Record 定义记录结果
	var equt = Ext.data.Record.create([{
		name : 'level',
		type : 'string',
		mapping : 'level'
	}, {
		name : 'happenAlarm',
		type : 'Integer',
		mapping : 'happenAlarm'
	}, {
		name : 'panAlarm',
		type : 'Integer',
		mapping : 'panAlarm'
	}, {
		name : 'pointAlarm',
		type : 'Integer',
		mapping : 'pointAlarm'
	},{
		name : 'pullAlarm',
		type : 'Integer',
		mapping : 'pullAlarm'
	},  {
		name : 'inmatchAlarm',
		type : 'Integer',
		mapping : 'inmatchAlarm'
	}]);
	// Reader以提供的Ext.data.Record 构造器中的映射为基础，从JSON响应创建一个Ext.data.Record 对象数组
	var reader = new Ext.data.JsonReader({
		totalProperty : "total",
		root : "events"
	}, equt);
	var store = new Ext.data.Store({// 提供数据输入
		proxy : proxy,
		reader : reader,
		remoteSort : true
	});
	// 获取每页显示的记录数
	var pageSize = parseInt(Ext.getDom("pageSize").value);
	var limit = parseInt(Ext.getDom("limit").value);
	
	// 表格
	var grid = new Ext.grid.GridPanel({
		title : "当前位置：告警信息统计",
		region : 'center',
		id : "grid",
		border : false,
		cm : cm,// 列定义的model(必需)
		
		store : store,
		autoScroll : true,// 滚动条
		viewConfig : {
			sortAscText : '升序',
			sortDescText : '降序',
			columnsText : '可选列',
			forceFit : false,// Ture表示自动扩展或缩小列的宽度
			scrollOffset : -1
		},
		tbar :[ {
						xtype:'label',
						text:'统计维度：'
					},{
						xtype : 'combo',
						id:'type',
						cls :'order-sel-textfield-width',
						store : typeStore,
						value:'1',
						displayField : 'text',
						valueField : 'value',
						mode : 'local',
						triggerAction : "all",// 下拉框获得了焦点或者被点击了
						editable : false,// 设置为false以阻止用户直接向该输入项输入文本
						hiddenName : 'otype',
						listeners:{
                			'select': function(){
                       		 if(Ext.getCmp('type').getValue() == '0'){
                            	parent.Ext.getCmp("orderCenter").body.update("<iframe id='domainTreeIframe' scrolling='yes' src='pages/statistics/statisticsAlarm.jsp'  width='100%' height='100%' frameborder='no' border='0' marginwidth='0' marginheight='0' align='center'></iframe>");
                        	}else if(Ext.getCmp('type').getValue() == '1'){
	                            parent.Ext.getCmp("orderCenter").body.update("<iframe id='domainTreeIframe' scrolling='yes' src='pages/statistics/alarmOfLevel.jsp'  width='100%' height='100%' frameborder='no' border='0' marginwidth='0' marginheight='0' align='center'></iframe>");
	                        }else if(Ext.getCmp('type').getValue() == '2'){
	                        	parent.Ext.getCmp("orderCenter").body.update("<iframe id='domainTreeIframe' scrolling='yes' src='pages/statistics/alarmOfState.jsp'  width='100%' height='100%' frameborder='no' border='0' marginwidth='0' marginheight='0' align='center'></iframe>");
	                        }else{
	                        	parent.Ext.getCmp("orderCenter").body.update("<iframe id='domainTreeIframe' scrolling='yes' src='pages/statistics/alarmOfType.jsp'  width='100%' height='100%' frameborder='no' border='0' marginwidth='0' marginheight='0' align='center'></iframe>");
	                        }
                   		 }
               		  }	
					},'->',{
						xtype:'label',
						text:'设备名称：'
					},{
						xtype : "treecombo",
						id : "ename_sel",
						width:125,
						listWidth:200,
						anchor : "100%",
						hiddenName : "eid_hidden", 
						url : "workorder!loadEqutInspecTree.action",
						setHiddenValue : function(node, dispText) {
							if(node.attributes.beanType=="equt"){
								this.setValue(node.id);
								Ext.form.ComboBox.superclass.setValue.call(this, node.text);
								Ext.getDom("eid").value = node.id;
							}else if(node.attributes.beanType=="domain"){
							
							}
							Ext.getDom("beanType").value = node.attributes.beanType;
						}
				   },{
						xtype:'label',
						text:'地区：'
					},{
						xtype : "treecombo",
						id : "ename_sel2",
						width:125,
						listWidth:200,
						anchor : "100%",
						hiddenName : "eid", 
						url : "statistics!loadEqutInspecTree.action",
						setHiddenValue : function(node, dispText) {	
								this.setValue(node.attributes.domainCode);
								Ext.form.ComboBox.superclass.setValue.call(this, node.text);
								this.hiddenValue = node.attributes.domainCode;
						}
					},{
						xtype:'label',
						text:'告警起始时间：'
					},{
						id:'createtimebegin',
						xtype : "datefield",
						// format : 'yyyy-MM-dd hh:mm:ss',//日期格式
						format : 'Y-m-d',// 日期格式
						editable : false,// 设置为false以阻止用户直接向该输入项输入文本
						cls :'order-sel-textfield-width',
						listeners:{
							'select':function(v){
								var end = Ext.getCmp("createtimeend").getValue();
								if(end != null && end != ""){
									if(new Date(end) < new Date(v.getValue())){
										v.setValue("");
									}
								}
							}
						}
					},{
						xtype:'label',
						text:'告警结束时间：'
					},{
						id:'createtimeend',
						xtype : "datefield",
						// format : 'yyyy-MM-dd hh:mm:ss',//日期格式
						format : 'Y-m-d',// 日期格式
						cls :'order-sel-textfield-width',
						invalidText : '结束时间必须大于开始时间！',
						editable : false,// 设置为false以阻止用户直接向该输入项输入文本
						listeners:{
							'select' : function(v) {
								var begin = Ext.getCmp("createtimebegin").getValue();
								if(begin!=null && begin!=""){
									if(new Date(begin) > new Date(v.getValue())){
										v.setValue("");
									}
								}
							}
						}
					},{
						id : 'seekBtn',
						text : '搜索',
						icon : "imgs/queryBtn.png",
						cls : "x-btn-text-icon"
					},{
						id:'resetBtn',
						text : '重置',
						icon : "imgs/queryBtn.png",
						cls : "x-btn-text-icon"
					},{
						id : 'refreshBtn',
						text : '导出',
						icon : "imgs/all_refresh.png",
						cls : "x-btn-text-icon"
					}
				],
		
		bbar : new Ext.PagingToolbar({// 分页工具栏
			store : store,
			pageSize : pageSize,
			beforePageText : "当前第",
			afterPageText : "页，共{0}页",
			lastText : "尾页",
			nextText : "下一页",
			prevText : "上一页",
			firstText : "首页",
			refreshText : "刷新页面",
			displayInfo : true,// 是否显示displayMsg
			displayMsg : "显示第{0}-{1}条 ，共{2}条",
			emptyMsg : "没有记录"
		})

	});

	var loadMarsk = new Ext.LoadMask(document.body, {
		msg : '数据加载中，请稍候......',
		disabled : false,
		store : store
	});
	store.addListener('beforeload', function() {
		loadMarsk.show();
	});
	store.load({
		params : {
			start : 0,
			limit : limit	
		}
	});
	// 解决分页点击下一页时不带参数问题
	store.on('beforeload', function() {
		store.baseParams = {
			
		};
	});
	// viewport 布局
	new Ext.Viewport({
		layout : 'border',
		items : [grid]
	});
	// var view = Ext.getCmp("view")

	var seekBtn = Ext.getCmp("seekBtn");
	seekBtn.on('click', function() {
		var eid = Ext.getDom("eid").value;
		var code = Ext.getCmp("ename_sel2").getValue();
		var alarmTimeb = Ext.getCmp("createtimebegin").getValue();
		var alarmTimee = Ext.getCmp("createtimeend").getValue();
		grid.getStore().load({
			params : {
				start : 0,
				limit : limit,
				'eid' : eid,
				'code':code,
				'pointEventInfoBean.alarmTimeb' : alarmTimeb,
				'pointEventInfoBean.alarmTimee' : alarmTimee
			}
		});
		grid.getView().refresh();
	});
	
	var refreshBtn = Ext.getCmp("refreshBtn");
	refreshBtn.on('click', function() {
		var eid = Ext.getDom("eid").value;
		var code = Ext.getCmp("ename_sel2").getValue();
		var alarmTimeb = Ext.getCmp("createtimebegin").getValue();
		var alarmTimee = Ext.getCmp("createtimeend").getValue();
		alarmTimeb=encodeURI(encodeURI(alarmTimeb));
		alarmTimee=encodeURI(encodeURI(alarmTimee));
		location.href="statistics!exportalarmsbylevel.action?eid="+eid+"&code="+code+"&begintime="+alarmTimeb+"&endtime="+alarmTimee;
		grid.getView().refresh();
	});
	var resetBtn=Ext.getCmp("resetBtn");
	resetBtn.on('click', function() {
		Ext.getCmp("ename_sel").setValue("");
		Ext.getDom("eid").value="";
		Ext.getCmp("ename_sel2").setValue("");
		Ext.getCmp("ename_sel2").hiddenValue="";
		Ext.getCmp("createtimebegin").setValue("");
		Ext.getCmp("createtimeend").setValue("");
	});
});
