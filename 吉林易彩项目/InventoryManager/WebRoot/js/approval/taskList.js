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
		header:'id',
		dataIndex:'id',
		hidden:true
	},{
		header:'工单标题',
		dataIndex:"taskTitle",
		width:120
	},{
		header:'区县',
		dataIndex:"county",
		width:70
	},{
		header:'二级',
		dataIndex:'belongCmp',
		width:70
	},{
		header:'创建时间',
		dataIndex:'createStr',
		width:70
	},{
		header:'派发人',
		dataIndex:'sender',
		width:70
	},{
		header:'采集人',
		dataIndex:'approvaler',
		width:70
	},{
		header:'处理时间',
		dataIndex:'dealStr',
		width:70
	},{
		header:'审核人',
		dataIndex:'auditer',
		width:70
	},{
		header:'审核时间',
		dataIndex:'auditStr',
		width:70
	},{
		header:'工单状态',
		dataIndex:'taskState',
		width:70
	},{
		header:'班组名称',
		dataIndex:'groupName',
		width:70
	},{
		header:'采集长度',
		dataIndex:'totalLength',
		width:70
	},{
		header:'审核长度',
		dataIndex:'auditLength',
		width:70
	}]);
	var store = new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({
			url:"approvalAction!getAppAuditList.action"
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
			name:'county',
			type:'string',
			mapping:'county'
		},{
			name:'belongCmp',
			type:'string',
			mapping:'belongCmp'
		},{
			name:'createStr',
			type:'string',
			mapping:'createStr'
		},{
			name:'sender',
			type:'string',
			mapping:'sender'
		},{
			name:'approvaler',
			type:'string',
			mapping:'approvaler'
		},{
			name:'dealStr',
			type:'string',
			mapping:'dealStr'
		},{
			name:'auditer',
			type:'string',
			mapping:'auditer'
		},{
			name:'auditStr',
			type:'string',
			mapping:'auditStr'
		},{
			name:'taskState',
			type:'string',
			mapping:'taskState'
		},{
			name:'groupName',
			type:'string',
			mapping:'groupName'
		},{
			name:'totalLength',
			type:'string',
			mapping:'totalLength'
		},{
			name:'auditLength',
			type:'string',
			mapping:'auditLength'
		}])),
		remoteSort:true
	});
	var pageSize = parseInt(Ext.getDom("pageSize").value);
	var limit = parseInt(Ext.getDom("limit").value);	
	var comp_store =new Ext.data.SimpleStore({
		fields : ['value','text'],
		data : [['城一分公司', '城一分公司'],['城二分公司','城二分公司'],
		  ['城三分公司','城三分公司'],['顺义区','顺义区'],['通州区','通州区'],
		  ['大兴区','大兴区'],['房山区','房山区'],['昌平区','昌平区'],
		  ['平谷区','平谷区'],['密云区','密云区'],['怀柔区','怀柔区'],
		  ['延庆区','延庆区'],['网运中心','网运中心']
		]
	});
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
			xtype:'label',
			text:'派单人：'
		},{
			xtype:'textfield',
			id:'senderText',
			cls:'sel-textfield-width',
			width:100
		},{
			xtype:'tbseparator'
		},{
			xtype:'label',
			text:'处理人：'
		},{
			xtype:'textfield',
			id:'approvalerText',
			cls:'sel-textfield-width',
			width:100
		},{
			xtype:'tbseparator'
		},{
			xtype:'label',
			text:'二级公司：'
		},{
			xtype : 'combo',  
		      name : 'blcomp',
		      id : 'blcomp_id',
		      mode : 'local',
		      emptyText:'请选择',
		      readOnly : false,
		      anchor : '85%',
		      triggerAction : 'all',
		      fieldLabel : '二级单位',
		      store : comp_store,
		      hiddenName : 'blcomp',
		      valueField : 'value',
		      displayField : 'text'
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
				var taskTitle = Ext.getCmp("checkDescribe").getValue();
				var sender = Ext.getCmp("senderText").getValue();
				var approvaler = Ext.getCmp("approvalerText").getValue();
				var belongCmp = Ext.getCmp("blcomp_id").getValue();
				var url = context_path+"/approvalAction!extTaskAuditTask.action?" +
						"taskTitle="+taskTitle+"&sender="+sender+"" +
						"&approvaler="+approvaler+"&belongCmp="+belongCmp+"";
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
				'belongCmp':Ext.getCmp("blcomp_id").getValue()
			}
		});
		grid.getStore().on('beforeload',function(){
			grid.getStore().baseParams = {
				'taskTitle':Ext.getCmp("checkDescribe").getValue(),
				'sender':Ext.getCmp("senderText").getValue(),
				'approvaler':Ext.getCmp("approvalerText").getValue(),
				'belongCmp':Ext.getCmp("blcomp_id").getValue()
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
			limit:limit,
			'taskTitle':Ext.getCmp("checkDescribe").getValue(),
			'sender':Ext.getCmp("senderText").getValue(),
			'approvaler':Ext.getCmp("approvalerText").getValue(),
			'belongCmp':Ext.getCmp("blcomp_id").getValue()
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