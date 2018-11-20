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
	Ext.data.Connection.prototype.timeout ='300000';
	var sm = new Ext.grid.CheckboxSelectionModel({
		checkOnly:true
	});
	var cm = new Ext.grid.ColumnModel([sm,{
		header:'id',
		dataIndex:'id',
		hidden:true
	},{
		header:'二级公司',
		dataIndex:"belongCmp",
		width:70
	},{
		header:'工单总数',
		dataIndex:"totalTask",
		width:70
	},{
		header:'已派工单',
		dataIndex:'sendTask',
		width:70
	},{
		header:'处理中工单',
		dataIndex:'beingTask',
		width:70
	},{
		header:'待审核工单',
		dataIndex:'checkTask',
		width:70
	},{
		header:'归档工单',
		dataIndex:'endTask',
		width:70
	},{
		header:'驳回工单',
		dataIndex:'rejectTask',
		width:70
	},{
		header:'管线总长度',
		dataIndex:'allTotalLength',
		width:70
	},{
		header:'自有管线长度',
		dataIndex:'selfTotalLength',
		width:70
	},{
		header:'租用管线长度',
		dataIndex:'rentTotalLength',
		width:70
	},{
		header:'采集管线长度',
		dataIndex:'collectLength',
		width:70
	},{
		header:'审核通过管线',
		dataIndex:'allPassLength',
		width:70
	},{
		header:'审核通过自有',
		dataIndex:'selfPassLength',
		width:70
	},{
		header:'审核通过租用',
		dataIndex:'rentPassLength',
		width:70
	},{
		header:'审核通过完成率',
		dataIndex:'passAllRate',
		width:70
	},{
		header:'光交箱总数',
		dataIndex:'allEqutNum',
		width:70
	},{
		header:'已交维光交箱',
		dataIndex:'assertEqutNum',
		width:70
	},{
		header:'未交维光交箱',
		dataIndex:'buildEqutNum',
		width:70
	},{
		header:'采集光交箱',
		dataIndex:'collectEqutNum',
		width:70
	},{
		header:'审核通过光交',
		dataIndex:'passEqutNum',
		width:70
	}]);
	var store = new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({
			url:"approvalAction!getApprovalReport.action"
		}),
		reader:new Ext.data.JsonReader({
			totalProperty:"total",
			idProperty:"belongCmp",
			root:"items"
		},Ext.data.Record.create([{
			name:'id',
			type:'integer',
			mapping:'id',
			hidden:true
		},{
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
			name:'allTotalLength',
			type:'string',
			mapping:'allTotalLength'
		},{
			name:'selfTotalLength',
			type:'string',
			mapping:'selfTotalLength'
		},{
			name:'rentTotalLength',
			type:'string',
			mapping:'rentTotalLength'
		},{
			name:'collectLength',
			type:'string',
			mapping:'collectLength'
		},{
			name:'allPassLength',
			type:'string',
			mapping:'allPassLength'
		},{
			name:'selfPassLength',
			type:'string',
			mapping:'selfPassLength'
		},{
			name:'rentPassLength',
			type:'string',
			mapping:'rentPassLength'
		},{
			name:'passAllRate',
			type:'string',
			mapping:'passAllRate'
		},{
			name:'allEqutNum',
			type:'string',
			mapping:'allEqutNum'
		},{
			name:'assertEqutNum',
			type:'string',
			mapping:'assertEqutNum'
		},{
			name:'buildEqutNum',
			type:'string',
			mapping:'buildEqutNum'
		},{
			name:'collectEqutNum',
			type:'string',
			mapping:'collectEqutNum'
		},{
			name:'passEqutNum',
			type:'string',
			mapping:'passEqutNum'
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
				var url = context_path+"/approvalAction!extApprovalReport.action?";
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
	
	/*执行查询*/
	var seekPoleLineBtn = Ext.getCmp("seekPoleLineBtn");
	seekPoleLineBtn.on('click', function(){		
		grid.getStore().load({
			params:{
				start:0,
				limit:limit,
				'taskTitle':Ext.getCmp("checkDescribe").getValue(),
				'sender':Ext.getCmp("senderText").getValue(),
				'approvaler':Ext.getCmp("approvalerText").getValue(),
				'belongCmp':Ext.getCmp("belongCmpText").getValue()
			}
		});
		grid.getStore().on('beforeload',function(){
			grid.getStore().baseParams = {
				'taskTitle':Ext.getCmp("checkDescribe").getValue(),
				'sender':Ext.getCmp("senderText").getValue(),
				'approvaler':Ext.getCmp("approvalerText").getValue(),
				'belongCmp':Ext.getCmp("belongCmpText").getValue()
			};
		});
		grid.getView().refresh();
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