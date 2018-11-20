Ext.onReady(function() {
	var pstatStore = new Ext.data.SimpleStore({
		fields : ['value', 'text'],
		data : [['', '全部状态'],['1', '空闲'], ['2', '故障'], ['3', '可用'], ['4', '占用'], ['5', '待核查']]
	})
	var cm = new Ext.grid.ColumnModel([/*{
		header : "序号",
		width : 60,
		hideable : false,
		renderer : function(value, metadata, record, rowIndex, colIndex, store) {
			var start = store.lastOptions.params.start;
			if (isNaN(start)) {
				start = 0;
			}
			return start + rowIndex + 1;
		}
	}, */
	{header : 'id',dataIndex : 'id',width : 100,hidden : true},
	{header : '设备名称',dataIndex : 'ename',width : 130,sortable : true}, 
	{header : '端子编码',dataIndex : 'pcode',width : 130, sortable : true}, 
	{header : '端子状态',dataIndex : 'pstat',width : 70,sortable : true,
		renderer : function(v) {
			return getPstatStr(v);
		}
	}, 
	{header : '承载业务',dataIndex : 'pserv',width : 130,sortable : true},
	{header : '光纤名称',dataIndex : 'fibname',width : 130,sortable : true}, 
	{header : '光路编码',dataIndex : 'ofpcode',width : 130,sortable : true},
	{header : '光路名称',dataIndex : 'ofpname',width : 130,sortable : true },
	{header : '对端端子/端口编码',dataIndex : 'oppo_pcode',width : 130,sortable : true},
	{header : '维护人',dataIndex : 'mp',width : 80,sortable : true},
	{header : '维护时间',dataIndex : 'mtime',width : 130,sortable : true,renderer: Ext.util.Format.dateRenderer('Y-m-d H:i:s')},
	/*{header : '相关工单',dataIndex : 'workorderno',width : 130,sortable : true},*/
	{header : '操作',dataIndex : 'alarmText',width : 100,sortable : true,
		renderer : function(value, metadata, record, rowIndex, colIndex, store) {
			var pointlog = store.data.items[rowIndex].json
			var eid = pointlog.eid;
			var pid = pointlog.pid;
			var id=pointlog.id;
			return '<img style="CURSOR: pointer" onclick=showPointLogNew("'+eid+'","'+pid+'","'+id+'") ' +
				'title="查看端子日志" src="imgs/workorder/logBtn.png" onmouseover=MM_swapImage("imgs/workorder/logBtn1.png",this) onmouseout=MM_swapImgRestore("imgs/workorder/logBtn.png",this)>';	
		}
	}
	]);
	// 从远程读取数据
	var proxy = new Ext.data.HttpProxy({
		url : "point!getPointLog.action"
	});
	// Record 定义记录结果
	var alarm = Ext.data.Record.create([
		{name : 'id',type : 'string',mapping : 'id',hidden : true}, 
		{name : 'ename',type : 'string',mapping : 'ename'}, 
		{name : 'pcode',type : 'string',mapping : 'pcode'}, 
		{name : 'pstat',type : 'string',mapping : 'pstat'}, 
		{name : 'pserv',type : 'string',mapping : 'pserv'}, 
		{name : 'fibname',type : 'string',mapping : 'fibname'}, 
		{name : 'ofpcode',type : 'string',mapping : 'ofpcode'}, 
		{name : 'ofpname',type : 'string',mapping : 'ofpname'}, 
		{name : 'oppo_pcode',type : 'string',mapping : 'oppo_pcode'}, 
		{name : 'mp',type : 'string',mapping : 'mp'}, 
		{name : 'mtime',type : Ext.data.Types.DATE,dateFormat : 'Y-m-d\\TH:i:s',mapping : 'mtime'}/*, 
		{name : 'workorderno',type : 'string',mapping : 'workorderno'}*/
	]);
	// Reader以提供的Ext.data.Record 构造器中的映射为基础，从JSON响应创建一个Ext.data.Record 对象数组
	var reader = new Ext.data.JsonReader({
		totalProperty : "total",
		root : "points"
	}, alarm);
	var store = new Ext.data.Store({// 提供数据输入
		proxy : proxy,
		reader : reader,
		remoteSort : true,
		sortInfo: {  
        	field: 'id',  
        	direction: "DESC"  
    	}
	});
	// 获取每页显示的记录数
	var pageSize = parseInt(Ext.getDom("pageSize").value);
	var limit = parseInt(Ext.getDom("limit").value);
	
	// 表格
	var grid = new Ext.grid.GridPanel({
		title : "当前位置：端子日志",
		region : 'center',
		id : "pointLogGrid",
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
		addUserWindow : null,// 添加用户窗口
		modifyUserWindow : null,// 修改用户窗口
		queryUserWindow : null,// 查询用户窗口
		buttonAlign : "center",// 按钮居中
		tbar : [{
				xtype:'tbspacer',
				width:10
			},{
				text : '刷新',
				id : 'refreshBtn',
				icon : "imgs/all_refresh.png",
				cls : "x-btn-text-icon"
			},{
				xtype : 'tbspacer',
				width:200
			},  '-', "地区/设备:", {
				xtype : "treecombo",
				id : "ename_sel",
				//width:125,
				//listWidth:200,
				anchor : "100%",
				hiddenName : "eid_hidden", 
				url : "workorder!loadEqutInspecTree.action",
				setHiddenValue : function(node, dispText) {
					if(node.attributes.beanType=="equt"){
						this.setValue(node.id);
						Ext.form.ComboBox.superclass.setValue.call(this, node.text);
						Ext.getDom("eid").value = node.id;
					}else if(node.attributes.beanType=="domain"){
						this.setValue(node.attributes.domainCode);
						Ext.form.ComboBox.superclass.setValue.call(this, node.text);
						Ext.getDom("areano").value = node.attributes.domainCode;
					}
					Ext.getDom("beanType").value = node.attributes.beanType;
				}
			}, {
						xtype : 'tbseparator'
					},{
						xtype:'label',
						text:'端子编码：'
					},{
						xtype:'textfield',
						//cls :'order-sel-textfield-width',
						id:'log_pcode'
					}, {
						xtype : 'tbseparator'
					},{
						xtype:'label',
						text:'端子状态：'
					},{
						xtype : 'combo',
						id:'log_pstat',
						//cls :'order-sel-textfield-width',
						store : pstatStore,
						value:'',
						displayField : 'text',
						valueField : 'value',
						mode : 'local',
						triggerAction : "all",// 下拉框获得了焦点或者被点击了
						editable : false,// 设置为false以阻止用户直接向该输入项输入文本
						hiddenName : 'otype'
						
					}, '-', "维护起始时间:", {
				xtype : "datefield",
				id : "btime_sel",
				editable : false,
				format : 'Y-m-d',// 日期格式
				fieldLabel : "维护起始时间",
				anchor : "100%",
				listeners:{
					'select':function(v){
						var end = Ext.getCmp("etime_sel").getValue();
						if(end != null && end != ""){
							if(new Date(end) < new Date(v.getValue())){
								v.setValue("");
								alert("起始时间不能大于截止时间!");
							}
						}
					}
				}
			}, '-', "维护截止时间:", {
				xtype : "datefield",
				id : "etime_sel",
				editable : false,
				format : 'Y-m-d',// 日期格式
				fieldLabel : "维护截止时间",
				anchor : "100%",
				listeners:{
				'select' : function(v) {
					var begin = Ext.getCmp("btime_sel").getValue();
					if(begin!=null && begin!=""){
						if(new Date(begin) > new Date(v.getValue())){
							v.setValue("");
							alert("截止时间不能小于起始时间!");
						}
					}
				}
			}
			}, '-', {
				id : 'btnSubmit',
				text : "查询",
				hidden : false,
				tooltip : '查询端子日志',
				tooltipType : 'qtip',
				handler : function() {
					
				}
			}],
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
	//解决分页点击下一页时不带参数问题
	store.on('beforeload',function(){
   		store.baseParams = {
   			start : 0,
			limit : limit,
   			"pointInfoBean.pcode" : Ext.getCmp("log_pcode").getValue(),
	   		"pointInfoBean.pstat" : Ext.getCmp("log_pstat").getValue(),
			"pointInfoBean.areano" : Ext.getDom("areano").value,
			"pointInfoBean.eid" : Ext.getDom("eid").value,
			"pointInfoBean.mbtime" : Ext.getCmp("btime_sel").getValue(),
			"pointInfoBean.metime" : Ext.getCmp("etime_sel").getValue()
   		};
	}); 
	
	var viewport = new Ext.Viewport({
		layout : 'border',
		items : [grid]
	});
	
	//刷新当前页面
	var refreshBtn = Ext.getCmp("refreshBtn");
	refreshBtn.on('click', function() {
		Ext.getDom("areano").value="";
		Ext.getDom("eid").value="";
		Ext.getCmp("log_pstat").setValue("");
		Ext.getCmp("log_pcode").setValue("");
		Ext.getCmp("ename_sel").setValue("");
		Ext.getCmp("btime_sel").setValue("");
		Ext.getCmp("etime_sel").setValue("");
		store.load({
			params : {
				start : 0,
				limit : limit
			}
		});
	});
	//按条件进行查询
	var btnSubmit = Ext.getCmp("btnSubmit")
	btnSubmit.on('click',function(){
		store.load({
			params : {
				start : 0,
				limit : limit,
				"pointInfoBean.pcode" : Ext.getCmp("log_pcode").getValue(),
	   			"pointInfoBean.pstat" : Ext.getCmp("log_pstat").getValue(),
				"pointInfoBean.areano" : Ext.getDom("areano").value,
				"pointInfoBean.eid" : Ext.getDom("eid").value,
				"pointInfoBean.mbtime" : Ext.getCmp("btime_sel").getValue(),
				"pointInfoBean.metime" : Ext.getCmp("etime_sel").getValue()
			}
		});
		store.on('beforeload',function(){
	   		store.baseParams = {
	   			"pointInfoBean.pcode" : Ext.getCmp("log_pcode").getValue(),
	   			"pointInfoBean.pstat" : Ext.getCmp("log_pstat").getValue(),
				"pointInfoBean.areano" : Ext.getDom("areano").value,
				"pointInfoBean.eid" : Ext.getDom("eid").value,
				"pointInfoBean.mbtime" : Ext.getCmp("btime_sel").getValue(),
				"pointInfoBean.metime" : Ext.getCmp("etime_sel").getValue()
	   		};
		});
	})
});

function showPointLogNew(eid,pid,id){
	var grid = Ext.getCmp("pointLogGrid");
	if (!grid.showPLogGridWindow) {
		// 新建查看日志窗口
		grid.showPLogGridWindow = new com.increase.cen.document.DocumentWindow({
			id : "showPLogGridWindow",
			width : 600,
			height : 330,
			title : '端子信息',
			iconCls : 'i-script-window',
			items : [{
				id : 'pointLogNewGrid',
				width : 600,
				height : 300,
				xtype : 'pointLogNewGrid'
			}]
		});
	}
	var title = "设备ID: " + eid + " &nbsp;&nbsp;&nbsp;端子PID: "+ pid
	Ext.getCmp("pointLogNewGrid").setTitle(title);
	grid.showPLogGridWindow.show();
	var modifyRoleForm = Ext.getCmp("pointLogNewGrid");
							modifyRoleForm.getForm().load({
								url : 'point!getPointLogs.action',
								params : {
									//'pointInfoBean.id':id,
									'sort':'id',
									'dir':'desc',
									'pointInfoBean.eid':eid,
									'pointInfoBean.pid':pid
								},
								success : function(form, action) {
									var json = Ext.util.JSON.decode(action.response.responseText);
									var point=json.pointInfoBean.points[0];
									Ext.getCmp("show_ename").setValue(point.ename);
									Ext.getCmp("show_pcode").setValue(point.pcode);
									Ext.getCmp("show_pstat").setValue(point.pstat);
									Ext.getCmp("show_pserv").setValue(point.pserv);
								},
								failure:function(form, action){
									var json = Ext.util.JSON.decode(action.response.responseText);
									var pointinfo=json.pointInfoBean;
									var point=null;
									var point1=null;
									var state="";
									for(var i=0;i<pointinfo.points.length;i++){
										if(pointinfo.points[i].id==id){
											point=pointinfo.points[i];
											if(i!=pointinfo.points.length-1){
												point1=pointinfo.points[i+1];
											}
										}
									}
									//将原有样式去掉
									Ext.get("show_ename").removeClass('error');
									Ext.get("show_pcode").removeClass('error');
									Ext.get("show_pstat").removeClass('error');
									Ext.get("show_pserv").removeClass('error');
									Ext.get("show_fibname").removeClass('error');
									Ext.get("show_ofpcode").removeClass('error');
									Ext.get("show_ofpname").removeClass('error');
									Ext.get("show_oppo_pcode").removeClass('error');
									Ext.get("show_mp").removeClass('error');
									Ext.get("show_mtime").removeClass('error');
									//Ext.get("show_workorderno").removeClass('error');
									//为各文本框赋值
									Ext.getCmp("show_ename").setValue(point.ename);
									Ext.getCmp("show_pcode").setValue(point.pcode);
									if(point.pstat=='1'){
										state="空闲";
									}else if(point.pstat=='2'){
										state="故障";
									}else if(point.pstat=='3'){
										state="可用";
									}
									Ext.getCmp("show_pstat").setValue(state);
									Ext.getCmp("show_pserv").setValue(point.pserv);
									Ext.getCmp("show_fibname").setValue(point.fibname);
									Ext.getCmp("show_ofpcode").setValue(point.ofpcode);
									Ext.getCmp("show_ofpname").setValue(point.ofpname);
									Ext.getCmp("show_oppo_pcode").setValue(point.oppo_pcode);
									Ext.getCmp("show_mp").setValue(point.mp);
									Ext.getCmp("show_mtime").setValue(point.mtime);
									//Ext.getCmp("show_workorderno").setValue(point.workorderno);
									//判断当前端子信息与前一个信息字段不一样的加样式
									if(point1!=null){
										if(point.ename!=point1.ename){
											Ext.get("show_ename").addClass('error');
										}
										if(point.pcode!=point1.pcode){
											Ext.get("show_pcode").addClass('error');
										}
										if(point.pstat!=point1.pstat){
											Ext.get("show_pstat").addClass('error');
										}
										if(point.pserv!=point1.pserv){
											Ext.get("show_pserv").addClass('error');
										}
										if(point.fibname!=point1.fibname){
											Ext.get("show_fibname").addClass('error');
										}
										if(point.ofpcode!=point1.ofpcode){
											Ext.get("show_ofpcode").addClass('error');
										}
										if(point.ofpname!=point1.ofpname){
											Ext.get("show_ofpname").addClass('error');
										}
										if(point.oppo_pcode!=point1.oppo_pcode){
											Ext.get("show_oppo_pcode").addClass('error');
										}
										if(point.mp!=point1.mp){
											Ext.get("show_mp").addClass('error');
										}
										if(point.mtime!=point1.mtime){
											Ext.get("show_mtime").addClass('error');
										}
										/*if(point.workorderno!=point1.workorderno){
											Ext.get("show_workorderno").addClass('error');
										}*/
									}
								}
							});

	
}
