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
		header:'自建合计',
		dataIndex:"selfBuild",
		width:50
	},{
		header:'自建审批',
		dataIndex:"pselfBuild",
		width:50
	},{
		header:'合建合计',
		dataIndex:'togetherBuild',
		width:50
	},{
		header:'合建审批',
		dataIndex:'ptogetherBuild',
		width:50
	},{
		header:'共建合计',
		dataIndex:'commonBuild',
		width:50
	},{
		header:'共建审批',
		dataIndex:'pcommonBuild',
		width:50
	},{
		header:'租用合计',
		dataIndex:'leaseBuild',
		width:50
	},{
		header:'租用审批',
		dataIndex:'pleaseBuild',
		width:50
	},{
		header:'购买合计',
		dataIndex:'buyBuild',
		width:50
	},{
		header:'购买审批',
		dataIndex:'pbuyBuild',
		width:50
	},{
		header:'置换合计',
		dataIndex:'replaceBuild',
		width:50
	},{
		header:'置换审批',
		dataIndex:'preplaceBuild',
		width:50
	},{
		header:'其他合计',
		dataIndex:'otherBuild',
		width:70
	},{
		header:'其他审批',
		dataIndex:'potherBuild',
		width:70
	}]);
	var store = new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({
			url:"approvalAction!getPropertyReport.action"
		}),
		reader:new Ext.data.JsonReader({
			totalProperty:"total",
			root:"items"
		},Ext.data.Record.create([{
			name:'belongCmp',
			type:'string',
			mapping:'belongCmp'
		},{
			name:'selfBuild',
			type:'string',
			mapping:'selfBuild'
		},{
			name:'pselfBuild',
			type:'string',
			mapping:'pselfBuild'
		},{
			name:'togetherBuild',
			type:'string',
			mapping:'togetherBuild'
		},{
			name:'ptogetherBuild',
			type:'string',
			mapping:'ptogetherBuild'
		},{
			name:'commonBuild',
			type:'string',
			mapping:'commonBuild'
		},{
			name:'pcommonBuild',
			type:'string',
			mapping:'pcommonBuild'
		},{
			name:'leaseBuild',
			type:'string',
			mapping:'leaseBuild'
		},{
			name:'pleaseBuild',
			type:'string',
			mapping:'pleaseBuild'
		},{
			name:'buyBuild',
			type:'string',
			mapping:'buyBuild'
		},{
			name:'pbuyBuild',
			type:'string',
			mapping:'pbuyBuild'
		},{
			name:'replaceBuild',
			type:'string',
			mapping:'replaceBuild'
		},{
			name:'preplaceBuild',
			type:'string',
			mapping:'preplaceBuild'
		},{
			name:'otherBuild',
			type:'string',
			mapping:'otherBuild'
		},{
			name:'potherBuild',
			type:'string',
			mapping:'potherBuild'
		}])),
		remoteSort:true
	});
	var pageSize = parseInt(Ext.getDom("pageSize").value);
	var limit = parseInt(Ext.getDom("limit").value);	
	grid = new Ext.grid.GridPanel({
		title:"当前位置：产权性质统计",
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
				var url = context_path+'/approvalAction!expPropertyReport.action?';
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