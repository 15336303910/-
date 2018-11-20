/**
 * 查看审批工单详情
 * @param stone
 * @param obj
 */
function approvalTask(stone,obj){
	var taskTitle = itemText(1,80,'taskTitle','工单标题',obj.taskTitle,null);
	var startTime = itemText(.5,80,'startTime','开始时间',obj.startTime,null);
	var endTime = itemText(.5,80,'endTime','结束时间',obj.endTime,null);
	var sender = itemText(.5,80,'sender','采集人',obj.sender,null);
	var approvaler = itemText(.5,80,'approvaler','审批人',obj.approvaler,null);
	var resType = itemText(1,80,'resType','涉及资源',obj.resType,null);
	var approvalAdvice = itemText(1,80,'approvalAdvice','审批意见',null,null);
	var taskPanel =new Ext.form.FormPanel({
	    layout : "column",
	    title:'流程信息 ',
	    id:'taskPanel',
		border : false,
		width:'100%',
		autoHeight:true,
		frame:true,
		collapsible:true,
		fileUpload : true,
		items:[
		    taskTitle,startTime,endTime,sender,approvaler,resType,approvalAdvice
		]
	});
	var tabPanel = this.getTabPanel(obj.resType,obj.id);
	var detailWin = new Ext.Window({
		title:'查看详情',
		closeAction : "destroy",
      	width:800,
      	height:515,
      	autoScroll:true,
      	modal:true,
      	buttonAlign:"center",
      	items:[taskPanel,tabPanel],
      	buttons:[/*{
      		text:"关闭",
      		handler:function(){
      			detailWin.destroy();
      		}
      	},*/{
      		text:"审批通过",
      		handler:function(){
      			if(obj.taskState =='归档' || obj.taskState =='驳回'){
      				alert("已处理 !请勿重复处理。");
      				return false;
      			}
      			taskPanel.form.submit({
      				url : context_path+'/approvalAction!subApprove.action?id='+obj.id,
            		waitMsg : '正在提交数据，请稍等...',
            		success : function() {
            			Ext.Msg.show({
    						title : "提示",
    						msg : "审批通过数据转入后台同步!",
    						width :150,
    						buttons: Ext.MessageBox.OK,
    						icon: Ext.MessageBox.INFO
    					});
            			detailWin.destroy();
            			stone.reload({params:{
            				start:0,limit:15
            			}});
            		},
            		failure : function() {
            			Ext.Msg.show({
    						title : "提示",
    						msg : "保存失败！",
    						width :150,
    						buttons: Ext.MessageBox.OK,
    						icon: Ext.MessageBox.INFO
    					});
            		}
      			});
      		}
      	},{
      		text:"审批驳回",
      		handler:function(){
      			if(obj.taskState =='归档' || obj.taskState =='驳回'){
      				alert("已处理 !请勿重复处理。");
      				return false;
      			}
      			taskPanel.form.submit({
      				url : context_path+'/approvalAction!rejectApprove.action?id='+obj.id,
            		waitMsg : '正在提交数据，请稍等...',
            		success : function() {
            			Ext.Msg.show({
    						title : "提示",
    						msg : "审批未通过已驳回!",
    						width :150,
    						buttons: Ext.MessageBox.OK,
    						icon: Ext.MessageBox.INFO
    					});
            			detailWin.destroy();
            			stone.reload({params:{
            				start:0,limit:15
            			}});
            		},
            		failure : function() {
            			Ext.Msg.show({
    						title : "提示",
    						msg : "保存失败！",
    						width :150,
    						buttons: Ext.MessageBox.OK,
    						icon: Ext.MessageBox.INFO
    					});
            		}
      			});
      		}
      	}]
	});
	detailWin.show();
}


/**
 * 得到分页的panel
 * @param resType
 * @returns {Ext.TabPanel}
 */
function getTabPanel(resType,id){
	var res = resType.split(",");
	var tabPanel = new Ext.TabPanel({
        width:'100%',
        height:300,
        activeTab : 0,//默认激活第一个tab页
        animScroll : true,//使用动画滚动效果
        enableTabScroll : true,//tab标签超宽时自动出现滚动按钮
        items: [ ]
    });
	for(var i= 0;i<res.length;i++){
		var grid = getPointPanel(res[i],id);
		tabPanel.add({
			id :'tabItem'+i,
			title : res[i],
			closable : true,
			layout : 'fit',
			items : grid
		});
	}
	return tabPanel;
}


/**
 * 返回一个gridpanel
 * @param resType
 * @param id
 */
function getPointPanel(resType,id){
	var thisPagesize = 15;
	var column = "[{header:'编号',dataIndex:'resCode',hidden:false,width:100,fun:null}," +
	 			"{header:'资源名称',dataIndex:'resName',hidden:false,width:150,fun:null}," +
	 			"{header:'经度',dataIndex:'longitude',hidden:false,width:150,fun:null}," +
	 			"{header:'纬度',dataIndex:'latitude',hidden:false,width:150,fun:null}," +
	 			"{header:'所属区域',dataIndex:'region',hidden:false,width:150,fun:null}," +
	 			"{header:'创建时间',dataIndex:'createTime',hidden:false,width:150,fun:null},"+
	 			"{header:'修改时间',dataIndex:'updateTime',hidden:false,width:150,fun:null}," +
	 			"{header:'状态',dataIndex:'resState',hidden:false,width:100,fun:null}" +
	 			"]";
	var result = basicStore(column,'approvalAction!getApprovalGrid.action?id='+id+'&resType='+encodeURIComponent(resType)+'','totalCount','root')
	var store =result.store;
	var cm = result.cm;
	var sm = result.sm;
	var listGrid = new Ext.grid.GridPanel({
		store: store,
		cm: cm,
		sm: sm,
		autoHeight: false,
		loadMask:{msg : '加载数据中，请稍候...'},
		height: document.body.clientHeight-50,
		anchor:'100% -30',
	    bodyStyle:'width:100%',
	    stripeRows: true,// 让grid相邻两行背景色不同
	    loadMask: false,
	    bbar: new Ext.PagingToolbar({
	        pageSize: thisPagesize,
	        store: store,
	        displayInfo: true,
	        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
	        emptyMsg: "没有记录"
	   })
	});
	return listGrid;
}