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
		header:'机架名称',
		dataIndex:"eName",
		width:120
	},{
		header:'机架类型',
		dataIndex:"rackType",
		width:70
	},{
		header:'编号',
		dataIndex:'resNum',
		width:70,
		hidden:true
	},{
		header:'序号',
		dataIndex:'eid',
		width:70,
		hidden:true
	},{
		header:'创建时间',
		dataIndex:"createTime",
		width:70
	},{
		header:'厂家',
		dataIndex:'changjia',
		width:70
	},{
		header:'机架列号',
		dataIndex:'jijialiehao',
		width:70
	},{
		header:'机架行号',
		dataIndex:'jijiahanghao',
		width:70
	},{
		header:'机架长',
		dataIndex:'equtLength',
		width:50 
	},{
		header:'机架高',
		dataIndex:'equtTall',
		width:50
	},{
		header:'机架宽',
		dataIndex:'equtWide',
		width:50
	},{
		header:'删除',
		dataIndex:'del',
		hidden:true
	},{
		header:'状态',
		dataIndex:'equType',
		width:50
	}]);
	var store = new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({
			url:"approvalAction!getRackStr.action"
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
			name:'resNum',
			type:'string',
			mapping:'resNum'
		},{
			name:'eid',
			type:'string',
			mapping:'eid'
		},{
			name:'eName',
			type:'string',
			mapping:'eName'
		},{
			name:'rackType',
			type:'string',
			mapping:'rackType'
		},{
			name:'createTime',
			type:'string',
			mapping:'createTime'
		},{
			name:'changjia',
			type:'string',
			mapping:'changjia'
		},{
			name:'jijialiehao',
			type:'string',
			mapping:'jijialiehao'
		},{
			name:'jijiahanghao',
			type:'string',
			mapping:'jijiahanghao'
		},{
			name:'equtLength',
			type:'string',
			mapping:'equtLength'
		},{
			name:'equtTall',
			type:'string',
			mapping:'equtTall'
		},{
			name:'equtWide',
			type:'string',
			mapping:'equtWide'
		},{
			name:'del',
			type:'string',
			mapping:'del',
			hidden:true
		}])),
		remoteSort:true
	});
	var pageSize = parseInt(Ext.getDom("pageSize").value);
	var limit = parseInt(Ext.getDom("limit").value);	
	grid = new Ext.grid.GridPanel({
		title:"当前位置：机房采集",
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
	    	id:'takeOdm',
	    	text:"模块信息",
	    	iconCls:'add',
	    	handler:function(){
	    		var rows = grid.getSelectionModel().getSelections();
  				if(rows.length != 1){
  					Ext.Msg.alert('信息提示','请选择一个机架！');
  					return ;
  				}
  				var odmsm = new Ext.grid.CheckboxSelectionModel({
  					checkOnly:true
  				});
  				var odmcm = new Ext.grid.ColumnModel([sm,{
  					header:'id',
  					dataIndex:'id',
  					hidden:true
  				},{
  					header:'模块名称',
  					dataIndex:"odmName",
  					width:0
  				},{
  					header:'端口位置',
  					dataIndex:"pos",
  					width:100
  				},{
  					header:'创建时间',
  					dataIndex:"creationDate",
  					width:100,
  					hidden:true
  				},{
  					header:'采集时间',
  					dataIndex:"lastUpdateDate",
  					width:100
  				},{
  					header:'承载纤芯',
  					dataIndex:"fiberName",
  					width:170
  				},{
  					header:'纤芯对端',
  					dataIndex:"oppsite",
  					width:170
  				},{
  					header:'跳纤对端',
  					dataIndex:"jumpOptical",
  					width:170
  				}]);
  				var odmstore = new Ext.data.GroupingStore({
  					proxy:new Ext.data.HttpProxy({
  						url:"approvalAction!odmResList.action?eid="+rows[0].get("eid")
  					}),
  					reader:new Ext.data.JsonReader({
  						root:"items"
  					},Ext.data.Record.create([{
  						name:'id',
  						type:'integer',
  						mapping:'id',
  						hidden:true
  					},{
  						name:'odmName',
  						type:'string',
  						mapping:'odmName'
  					},{
  						name:'pos',
  						type:'string',
  						mapping:'pos'
  					},{
  						name:'creationDate',
  						type:'string',
  						mapping:'creationDate'
  					},{
  						name:'lastUpdateDate',
  						type:'string',
  						mapping:'lastUpdateDate'
  					},{
  						name:'fiberName',
  						type:'string',
  						mapping:'fiberName'
  					},{
  						name:'oppsite',
  						type:'string',
  						mapping:'oppsite'
  					},{
  						name:'jumpOptical',
  						type:'string',
  						mapping:'jumpOptical'
  					}])),
  					groupField:'odmName',
  					remoteSort:true
  				});
  				odmstore.load();
  				var odmGrid = new Ext.grid.GridPanel({
  					title:"当前位置：核查统计",
  					region:'center',
  					id:"odmGrid",
  					border:false,
  					cm:odmcm,
  					sm:odmsm,
  					store:odmstore,
  					height:300,
  					autoScroll:true,
  					buttonAlign:"center",
  					view: new Ext.grid.GroupingView({
  						hideGroupedColumn:true,
  						startCollapsed:true
  					}),
  					tbar:[{
  						id:"helpBtn",
  						icon:"imgs/bangzhu.png",
  						cls:"x-btn-text-icon",
  						handler:function(){}
  					}]
  				});
  				
  				var odmWin = new Ext.Window({
  					id:'odmWin',
  					title:'ODM窗口',
  			      	width:800,
  			      	closable:false,
  			      	height:400,
  			      	modal:true,
  			      	buttonAlign:"center",
  			      	items:[odmGrid],
  			      	buttons:[{
  			      		text:"关闭",
  			      		handler:function(){
  			      			odmWin.destroy();
  			      		}
  			      	}]
  				});
  				odmWin.show();
	    	}
	    },{
	    	xtype:'tbseparator'
	    },{
	    	id:'checkPhoto',
	    	text:"查看拍照",
	    	iconCls:'add',
	    	handler:function(){
	    		var rows = grid.getSelectionModel().getSelections();
  				if(rows.length != 1){
  					Ext.Msg.alert('信息提示','请选择一个机架！');
  					return ;
  				}
  				var eid = rows[0].get("eid");
  				var eids = eid.split("_");
	    		var diag = new Dialog();
	    		diag.Modal = true;
				diag.Title = "图片";
				diag.Width = 500;
				diag.Height = 400;
				diag.URL = context_path+"/pages/approval/resImageInfo.jsp?id="+eid+"&resType=EQU";
				diag.show();
	    	}
	    },{
	    	xtype:'tbseparator'
	    },{
	    	id:'syncRes',
	    	text:"同步资源",
	    	iconCls:'add',
	    	handler:function(){
	    		var rows = grid.getSelectionModel().getSelections();
  				if(rows.length != 1){
  					Ext.Msg.alert('信息提示','请选择一个机架！');
  					return ;
  				}
  				var eid = rows[0].get("eid");
	    		Ext.Ajax.request({ 
	    			url:context_path+'/approvalAction!sendResObj.action',
	    			method:'post',
	    			params:{resId:eid, type:'rack'},
	    			success:function(fp,o){
	    				var data = fp.responseText;
	    				var re = /^[0-9]+.?[0-9]*$/;
		            	if(re.test(data)){
		            		alert("数据同步完成!");
							$('#res'+(tbLength-1)).attr("value",data);
		            	}else{
		            		if(data == ""){
			            		alert("数据未更新!");
			            	}else if(data.indexOf("error") >=0){
			            		if(data.indexOf("_") >=0){
			            			alert(data.substring(6));
			            		}else{
			            			alert("数据同步失败，请联系管理员!");
			            		}
							}else{
								alert("数据同步完成!");
								$('#res'+(tbLength-1)).attr("value",data);
							}
		            	}
	    			}
	    	 	});
	    	}
	    },{
	    	xtype:'tbseparator'
	    },{
	    	id:'rejectRes',
	    	text:'驳回机架',
	    	iconCls:'add',
	    	handler:function(){
	    		var rows = grid.getSelectionModel().getSelections();
  				if(rows.length != 1){
  					Ext.Msg.alert('信息提示','请选择一个机架！');
  					return ;
  				}
  				var resId = rows[0].data["eid"];
  				var resType ="rack";
  				var resName = rows[0].data["eName"];
  				var parties = rows[0].data["parties"];
  				var taskId =data.id;
  				var rejectStr = "";
  				Ext.MessageBox.prompt("驳回资源","驳回信息描述",
  					 function (btn, text) {
  						rejectStr =text;
  						Ext.Ajax.request({ 
  			    			url:context_path+'/approvalAction!rejectRes.action',
  			    			method:'post',
  			    			params: {resId:resId, resType:resType,taskId:taskId,resName:resName,rejectStr:rejectStr,parties:parties},
  			    			success:function(fp,o){
  			    				var result = fp.responseText;
  			    				if(data =="success"){
  				            		alert("标记成功");
  				            	}else{
  				            		alert("标记失败!");
  				            	}
  			    			}
  			    	 	});
  					 },
  					 this,
  					 true,
  					 ""
  				); 
	    	}
	    },{
	    	xtype:'tbseparator'
	    },{
	    	id:'auditOver',
	    	text:'审批完成',
	    	iconCls:'add',
	    	handler:function(){
	    		var taskId =data.id;
	    		var taskState = data.taskState;
	    		var str = "请确认工单内资源已审批完成!";
	    		if(taskState == '驳回'){
	    			str ="该工单为二次审批!请确认资源已完全采集完成!"
	    		}
	    		Ext.MessageBox.confirm("审批完成", str, function (btn) {
	    			Ext.Ajax.request({ 
			    		url:context_path+'/approvalAction!examApprove.action',
			    		method:'post',
			    		params: {taskId:taskId},
			    		success:function(fp,o){
			    			var result = fp.responseText;
			    			if(data =="success"){
				           		alert("审批完成");
				           	}else{
				           		alert("审批结束!");
				           	}
			    		}
			    	 });
	    		});
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
		}),
		viewConfig:{
			getRowClass:function(record,rowIndex,rowParams,store){
				if(record.data.del == '1'){ 
					record.data.equType='删除'
					return 'x-grid-record-red';
				}else{
					if(record.data.resNum ==0){
						record.data.equType='新增'
						return 'x-grid-record-green';
					}else{
						record.data.equType='修改'
						return 'x-grid-record-yellow';
					}
				}
			}
		}
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
			'gid':data.resId,
			'approval':data.approvaler
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