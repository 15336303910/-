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
		header:'工单主题',
		dataIndex:"taskTitle",
		width:250
	},{
		header:'工单编号',
		dataIndex:"flowId",
		width:120
	},{
		header:'开始日期',
		dataIndex:'startTime',
		width:70
	},{
		header:'截止日期',
		dataIndex:'endTime',
		width:70
	},{
		header:'采集类型',
		dataIndex:'grabType',
		width:100,
		sortable:true
	},{
		header:'资源类型',
		dataIndex:'resType',
		width:100,
		sortable:true
	},{
		header:'地市',
		dataIndex:'city',
		width:150,
		hidden:true,
		sortable:true
	},{
		header:'所属公司',
		dataIndex:'belongCmp',
		width:150,
		sortable:true
	},{
		header:'派发人',
		dataIndex:'sender',
		width:100,
		sortable:true
	},{
		header:'执行班组',
		dataIndex:'groupName',
		width:100,
		sortable:true
	},{
		header:'执行人',
		dataIndex:'approvaler',
		width:100,
		sortable:true
	},{
		header:'领单时间',
		dataIndex:'dealStr',
		width:100,
		sortable:true
	},{
		header:'完成时间',
		dataIndex:'finishStr',
		width:100,
		sortable:true
	},{
		header:'资源数',
		dataIndex:'pointNum',
		width:50,
		sortable:true
	},{
		header:'任务状态',
		dataIndex:'taskState',
		width:100,
		sortable:true,
		renderer: function(value) {
			if(value=="已派单" || value=="已派发"){
				value ="已派";
			}else if(value=="已领单" || value =="处理中"){
				value ="处理中";
			}else if(value =="处理完成" || value=="已完成"){
				value = "采集完成";
			}else if(value =="归档"){
				value = "审核通过";
			}else if(value =="驳回"){
				value= "驳回";
			}
			return value;
		}
	}]);
	var store = new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({
			url:"approvalAction!getTaskList.action"
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
			name:'taskTitle',
			type:'string',
			mapping:'taskTitle'
		},{
			name:'flowId',
			type:'string',
			mapping:'flowId'
		},{
			name:'startTime',
			type:'string',
			mapping:'startTime'
		},{
			name:'endTime',
			type:'string',
			mapping:'endTime'
		},{
			name:'grabType',
			type:'string',
			mapping:'grabType'
		},{
			name:'resType',
			type:'string',
			mapping:'resType'
		},{
			name:'city',
			type:'string',
			mapping:'city',
			hidden:true
		},{
			name:'belongCmp',
			type:'string',
			mapping:'belongCmp'
		},{
			name:'sender',
			type:'string',
			mapping:'sender'
		},{
			name:'sendId',
			type:'string',
			mapping:'sendId',
			hidden:true
		},{
			name:'groupName',
			type:'string',
			mapping:'groupName'
		},{
			name:'approvaler',
			type:'string',
			mapping:'approvaler'
		},{
			name:'dealStr',
			type:'string',
			mapping:'dealStr'
		},{
			name:'finishStr',
			type:'string',
			mapping:'finishStr'
		},{
			name:'pointNum',
			type:'string',
			mapping:'pointNum'
		},{
			name:'taskState',
			type:'string',
			mapping:'taskState'
		}])),
		remoteSort:true
	});
	var pageSize = parseInt(Ext.getDom("pageSize").value);
	var limit = parseInt(Ext.getDom("limit").value);	
	grid = new Ext.grid.GridPanel({
		title:"当前位置：核查统计",
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
			text:'任务资源详情',
			id:'gdDetailBtn',
			icon:"imgs/fiber/link_add.png",
			cls:"x-btn-text-icon"
		},{
			xtype:'tbseparator'
		},{
			text:'派发任务',
			id:'sendTask',
			icon:"imgs/fiber/link_add.png",
			cls:"x-btn-text-icon",
			handler:function(){
				sendApproval(store);
				initMap();
			}
		},{
			xtype:'tbseparator'
		},{
			text:'刪除任务',
			id:'delTask',
			icon:"imgs/fiber/link_add.png",
			cls:"x-btn-text-icon",
			handler:function(){
				var records = grid.getSelectionModel().getSelections();
	    		if(records.length!=1){
					Ext.MessageBox.alert("","请选择一条数据進行刪除!");
					return ;
				}
	    		var r=records[0];
	    		
	    		Ext.Msg.confirm('信息','确认要删除么?',function(btn){
	    			if(btn =='yes'){
	    			 	Ext.Ajax.request({ 
	    					url:context_path+'/approvalAction!delTask.action?id='+r.get('id'),
	    					method:'post',
	    					success:function(fp,o){
	    						var resultText =  Ext.decode(fp.responseText);
	    						if (resultText.success ==true) {
	    							Ext.Msg.alert('信息提示','删除成功');
	    							grid.getStore().load({
	    								params:{
	    									start:0,length: 15
	    								}
	    							});
	    						}else{
	    							Ext.Msg.alert('信息提示',resultText.errors);
	    						}
	    					}
	    			 	});
	    			}
	    		}); 
			}
		},{
			xtype:'tbseparator'
		},{
			text:'水文数据',
			id:'hydrology',
			icon:"imgs/fiber/link_add.png",
			cls:"x-btn-text-icon",
			handler:function(){
				var url = context_path+'/pages/approval/hydrology.jsp';
				openWin(url,'now',900,600);
			}
		},{
			xtype:'tbseparator'
		},{
			text:'查看采集数据',
			id:'originalBtn',
			icon:"imgs/fiber/link_add.png",
			cls:"x-btn-text-icon",
			handler:function(){
				var selModel = grid.getSelectionModel();
				var records = selModel.getSelections();	
				if(records.length!=1){
					Ext.Msg.show({
						title:'提示',
						width:300,
						msg:'<img src="imgs/tip_warn.png" align="center" hspace="30"/>请您选择一条工单!',
						buttons:{
							ok:"确定"
						}
					});
				}else{
					var resType = records[0].get("resType");
					var taskState = records[0].get("taskState");
					if(taskState == '已派发' || taskState =='已派'){
						alert("工单未领取");
						return;
					}
					var id = records[0].get("id");
					if(resType =='基站'){
						var url = context_path+'/approvalAction!getSiteApproval.action?id='+id+'&type=now';
						openWin(url,'now',900,600);
					}else if(resType == '外线'){
						var url = context_path+'/pages/approval/sendGis.jsp?id='+id+'&type=now';
						openWin(url,'now',900,600);
					}else{
						var url = context_path+'/pages/approval/resGis.jsp?id='+id+'&type=now';
						openWin(url,'now',900,600);
					}
				}
			}
		},{
			xtype:'tbseparator'
		},{
			text:'查看基站拓扑',
			id:'siteTopo',
			icon:"imgs/fiber/link_add.png",
			cls:"x-btn-text-icon",
			handler:function(){
				var selModel = grid.getSelectionModel();
				var records = selModel.getSelections();	
				if(records.length!=1){
					Ext.Msg.show({
						title:'提示',
						width:300,
						msg:'<img src="imgs/tip_warn.png" align="center" hspace="30"/>请您选择一条工单!',
						buttons:{
							ok:"确定"
						}
					});
					return;
				}
				var resType = records[0].get("resType");
				var id = records[0].get("id");
				if(resType =='基站'){
					Ext.Ajax.request({ 
						url:context_path+'/approvalAction!getTaskSite.action',
						method:'post',
						params:{taskId:id},
						success:function(fp,o){
							var result = eval("("+fp.responseText+")");
							var url = context_path+"/pages/approval/site/stationInfo.jsp?resId="+result.resId+"&resType="+result.resType+"&resName="+result.resName;
							//openWin(url,'now',900,600);
							window.open(url); 
						}
				 	});
				}else{
					Ext.Msg.show({
						title:'提示',
						width:300,
						msg:'<img src="imgs/tip_warn.png" align="center" hspace="30"/>请选择基站类普查工单!',
						buttons:{
							ok:"确定"
						}
					});
				}
			}
		},{
			xtype:'tbseparator'
		},{
			xtype:'label',
			text:'任务主题：'
		},{
			xtype:'textfield',
			id:'checkDescribe',
			cls:'sel-textfield-width',
			width:200
		},{
			xtype:'label',
			text:'工单状态：'
		},{
			id:'taskState',
			name:'taskState',
			cls:'sel-textfield-width',
			xtype:'combo',
			store:new Ext.data.SimpleStore({
	    		fields:['value', 'text'],
	    		data:[
	    		     ['',''],
	    		     ['处理完成','处理完成'],
	    		     ['已派','已派'],
	    		     ['归档','归档'],
	    		     ['驳回','驳回']
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
			limit:limit,
			'taskTitle':Ext.getCmp("checkDescribe").getValue(),
			'taskState':Ext.getCmp("taskState").getValue()
		}
	});
	new Ext.Viewport({
		layout:'border',
		items:[
		    grid
		]
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
				msg:'<img src="imgs/tip_warn.png" align="center" hspace="30"/>请您选择一项任务!',
				buttons:{
					ok:"确定"
				}
			});
		}else{
			var thisRecord = records[0];
			var checkedId = records[0].get("id");
			
			Ext.Ajax.request({ 
				url:context_path+'/approvalAction!getApprovalTaskObj.action',
				method:'post',
				params:{id:checkedId},
				success:function(fp,o){
					var result = eval("("+fp.responseText+")");
					approvalTask(grid.getStore(),result);
				}
		 	});
		}
	});
	/*执行查询*/
	var seekPoleLineBtn = Ext.getCmp("seekPoleLineBtn");
	seekPoleLineBtn.on('click', function(){		
		grid.getStore().load({
			params:{
				start:0,
				limit:limit,
				'taskTitle':Ext.getCmp("checkDescribe").getValue(),
				'taskState':Ext.getCmp("taskState").getValue()
			}
		});
		grid.getStore().on('beforeload',function(){
			grid.getStore().baseParams = {
				'taskTitle':Ext.getCmp("checkDescribe").getValue(),
				'taskState':Ext.getCmp("taskState").getValue()
			};
		});
		grid.getView().refresh();
	});
});


/**
 * 打开窗体
 * @param url
 * @param name
 * @param iWidth
 * @param iHeight
 */
function openWin(url,name,iWidth,iHeight) { 
    var iTop = (window.screen.availHeight - 30 - iHeight) / 2; 
    var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; 
    window.open(url, name, 'height=' + iHeight + ',innerHeight=' + iHeight + ',width=' + iWidth + ',innerWidth=' + iWidth + ',top=' + iTop + ',left=' + iLeft + ',status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no'); 
}