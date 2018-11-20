var treeWindow = null;
var resourceGrid = null;
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
		header:'所属分公司',
		dataIndex:"belongCmp",
		width:120
	},{
		header:'工单总数',
		dataIndex:"totalTask",
		width:50
	},{
		header:'已派',
		dataIndex:'sendTask',
		width:50
	},{
		header:'处理中',
		dataIndex:'beingTask',
		width:50
	},{
		header:'待审核',
		dataIndex:'checkTask',
		width:50
	},{
		header:'归档',
		dataIndex:'endTask',
		width:50
	},{
		header:'驳回',
		dataIndex:'rejectTask',
		width:50
	},{
		header:'采集总长度(公里)',
		dataIndex:'totalLength',
		width:70
	},{
		header:'审核总长度(公里)',
		dataIndex:'checkLength',
		width:70,
		sortable:true
	},{
		header:'采集光交',
		dataIndex:'alEqut',
		width:70,
		sortable:true
	},{
		header:'审批光交',
		dataIndex:'passEqut',
		width:70,
		sortable:true
	}]);
	var store = new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({
			url:"approvalAction!getTaskReport.action"
		}),
		reader:new Ext.data.JsonReader({
			totalProperty:"total",
			root:"items"
		},Ext.data.Record.create([{
			name:'belongCmp',
			type:'string',
			mapping:'belongCmp'
		},{
			name:'totalTask',
			type:'string',
			mapping:'totalTask'
		},{
			name:'sendTask',
			type:'string',
			mapping:'sendTask'
		},{
			name:'beingTask',
			type:'string',
			mapping:'beingTask'
		},{
			name:'checkTask',
			type:'string',
			mapping:'checkTask'
		},{
			name:'endTask',
			type:'string',
			mapping:'endTask'
		},{
			name:'rejectTask',
			type:'string',
			mapping:'rejectTask'
		},{
			name:'totalLength',
			type:'string',
			mapping:'totalLength'
		},{
			name:'checkLength',
			type:'string',
			mapping:'checkLength'
		},{
			name:'groupName',
			type:'string',
			mapping:'groupName'
		},{
			name:'alEqut',
			type:'string',
			mapping:'alEqut'
		},{
			name:'passEqut',
			type:'string',
			mapping:'passEqut'
		}])),
		remoteSort:true
	});
	var pageSize = parseInt(Ext.getDom("pageSize").value);
	var limit = parseInt(Ext.getDom("limit").value);	
	grid = new Ext.grid.GridPanel({
		title:"当前位置：全量统计",
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
			xtype:'label',
			text:'任务主题：'
		},{
			xtype:'textfield',
			id:'checkDescribe',
			cls:'sel-textfield-width',
			width:200
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
			text:'导出报表',
			id:'delTask',
			icon:"imgs/fiber/link_add.png",
			cls:"x-btn-text-icon",
			handler:function(){
				var url = context_path+'/approvalAction!expTaskReport.action?';
				url = encodeURI(url);
				window.open(url,"_blank");
			}
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
			'taskTitle':Ext.getCmp("checkDescribe").getValue()
		}
	});
	
	new Ext.Viewport({
		layout:'border',
		items:[
			grid
		]
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