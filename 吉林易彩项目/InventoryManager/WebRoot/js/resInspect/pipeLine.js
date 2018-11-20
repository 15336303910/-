var store;
var listGrid;
var thisPagesize = 15;
var autoHeight = false;
function reload(){
	var lineNameQuery = Ext.getCmp('lineNameQuery').getValue();
	var startTypeQuery =  Ext.getCmp('lineTypeQuery').getValue();
	listGrid.getStore().reload({params:{
		start:0,limit:15,lineName:lineNameQuery,startType:startTypeQuery
	}});
  }

function getPipeLine(){
	var row= listGrid.getSelectionModel().getSelections();
	if(row.length!=1){
		Ext.MessageBox.alert("","请选择一条数据进行查看！");
	}
	var  r =row[0];
	Ext.Ajax.request({ 
		url:context_path+'/pipeLineAction!getPipeDetail.action',
		method:'post',
		params:{lineId:r.data['lineId'],startId:r.data['startId'],endId:r.data['endId'],type:r.data['startType']},
		success:function(fp,o){
			var obj = eval("("+fp.responseText+")");
			if(r.data['startType'] == 'well'){
				detailPipeLine(listGrid.getStore(),obj);
			}else if(r.data['startType'] == 'pole' || r.data['lineType'] =='pole'){
				detailPoleLine(listGrid.getStore(),obj);
			}else if(r.data['startType'] == 'stone' || r.data['lineType'] =='buried'){
				detailBuried(listGrid.getStore(),obj);
			}
			
		}
 	});
}
Ext.onReady(function() {
	Ext.data.Connection.prototype.timeout ='300000';
	
	var lineNameQuery = itemText(.33,80,'lineNameQuery','管线名称');
	var typeQuery =  [['well','管道'],['pole','杆路'],['buried','直埋']];
	var lineTypeQuery = basicCombo(typeQuery,.33,80,'lineTypeQuery','管线类型');
	var queryPanel = new Ext.form.FormPanel({
		bodyStyle : 'padding-top:5px',
	    layout : "column",
	    id:'queryPanel',
		border : false,
		width:'100%',
		frame:true,
		items:[lineNameQuery,lineTypeQuery
		]
	});
	var column = "[{header:'lineId',dataIndex:'lineId',hidden:true,width:150,fun:null}," +
	 			 "{header:'管线类型',dataIndex:'lineType',hidden:true,width:150,fun:null}," +
				 "{header:'管线名称',dataIndex:'lineName',hidden:false,width:250,fun:null}," +
				 "{header:'A端设备',dataIndex:'startName',hidden:false,width:250,fun:null}," +
				 "{header:'A端类型',dataIndex:'startType',hidden:true,width:250,fun:null}," +
				 "{header:'A端id',dataIndex:'startId',hidden:true,width:150,fun:null}," +
				 "{header:'A端经度',dataIndex:'startLon',hidden:false,width:150,fun:null}," +
				 "{header:'A端纬度',dataIndex:'startLat',hidden:false,width:150,fun:null}," +
				 "{header:'Z端设备',dataIndex:'endName',hidden:false,width:250,fun:null}," +
				 "{header:'Z端id',dataIndex:'endId',hidden:true,width:150,fun:null}," +
				 "{header:'Z端经度',dataIndex:'endLon',hidden:false,width:150,fun:null}," +
				 "{header:'Z端纬度',dataIndex:'endLat',hidden:false,width:150,fun:null}" +
				 "]";
	var result = basicStore(column,'pipeLineAction!getPipeGrid.action','totalCount','root')
	store =result.store;
	var cm = result.cm;
	var sm = result.sm;
	
	var gridButtons = ['-',
	  {
    	id:'detailBuried',
    	text:"查看",
    	iconCls:'add',
    	handler:function(){
    		getPipeLine();
    	}
    },'-',{
    	id:'exp',
    	text:"导出数据",
    	iconCls:'exp',
    	handler:function(){
    		var buriedNamequery = Ext.getCmp('buriedNamequery').getValue();
    		var buriedAreaquery = Ext.getCmp('buriedAreaquery').getValue();
    		var data = "buriedName="+buriedNamequery+"&buriedArea="+buriedAreaquery;
			var url = context_path+'/buriedAction!expData.action?'+data;
			url = encodeURI(url);
			window.open(url,"_blank");
    	}
    },'-',{
	      id:'doquery',
	      text:"查询",
	      iconCls:'query',
	      handler: function(){
	    	  reload();
	      }
	    },'-'];
	
	    listGrid = new Ext.grid.GridPanel({
		region : 'center',
		title : "当前位置："+"直埋",
		border : false,
		store: store,
		cm: cm,
		sm: sm,
		autoHeight: autoHeight,
		loadMask:{msg : '加载数据中，请稍候...'},
		height: document.body.clientHeight-238,
		anchor:'100% -30',
	    bodyStyle:'width:100%',
	    stripeRows: true,// 让grid相邻两行背景色不同
	    loadMask: true,
	    viewConfig : {
			sortAscText : '升序',
			sortDescText : '降序',
			columnsText : '可选列',
			forceFit : false,// Ture表示自动扩展或缩小列的宽度
			scrollOffset : -1
		},
		buttonAlign : "center",// 按钮居中
	    tbar:new Ext.Toolbar({ //标题+按钮的工具栏
        	items:gridButtons
        }),
        bbar : new Ext.PagingToolbar({// 分页工具栏
			store : store,
			pageSize : thisPagesize,
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
	
	new Ext.Panel({
		id:'basic',
		width:'100%',
    	frame:true,
    	modal:true,
		applyTo:'form',
		items:[queryPanel,listGrid],
		listeners:{
			beforeRender:function(){
			}
		}
	});
});