var store;
var listGrid;
var thisPagesize = 15;
var autoHeight = false;
function reload(){
	var buriedNamequery = Ext.getCmp('buriedNamequery').getValue();
	var buriedAreaquery = Ext.getCmp('buriedAreaquery').getValue();
	listGrid.getStore().reload({params:{
		start:0,limit:15,buriedName:buriedNamequery,buriedArea:buriedAreaquery
	}});
  }

function getDetailBuried(type){
	var row= listGrid.getSelectionModel().getSelections();
	if(row.length!=1){
		Ext.MessageBox.alert("","请选择一条数据进行查看！");
	}
	var  r =row[0];
	Ext.Ajax.request({ 
		url:context_path+'/buriedAction!getDetailBuried.action',
		method:'post',
		params:{id:r.data['buriedId']},
		success:function(fp,o){
			var obj = eval("("+fp.responseText+")");
			if(type == 'update'){
				updateBuried(listGrid.getStore(),obj);
			}else{
				detailBuried(listGrid.getStore(),obj);
			}
		}
 	});
}
Ext.onReady(function() {
	Ext.data.Connection.prototype.timeout ='300000';
	
	var buriedNamequery = itemText(.33,80,'buriedNamequery','直埋名称');
	var buriedAreaquery = itemText(.33,80,'buriedAreaquery','维护区域');
	var queryPanel = new Ext.form.FormPanel({
		bodyStyle : 'padding-top:5px',
	    layout : "column",
	    id:'queryPanel',
		border : false,
		width:'100%',
		frame:true,
		items:[buriedNamequery,buriedAreaquery
		]
	});
	var column = "[{header:'buriedId',dataIndex:'buriedId',hidden:true,width:150,fun:null}," +
				 "{header:'直埋名称',dataIndex:'buriedName',hidden:false,width:150,fun:null}," +
				 "{header:'维护区域',dataIndex:'buriedArea',hidden:false,width:150,fun:null}," +
				 "{header:'维护方式',dataIndex:'buriedModelStr',hidden:false,width:100,fun:null}," +
				 "{header:'维护单位',dataIndex:'buriedDept',hidden:false,width:100,fun:null}," +
				 "{header:'直埋长度',dataIndex:'buriedLength',hidden:false,width:100,fun:null}," +
				 "{header:'质量责任人',dataIndex:'dataQualitier',hidden:false,width:100,fun:null}," +
				 "{header:'一线维护人',dataIndex:'maintainer',hidden:false,width:100,fun:null}," +
				 "{header:'录入时间',dataIndex:'createTime',hidden:false,width:100,fun:null}" +
				 "]";
	var result = basicStore(column,'buriedAction!getBuriedGrid.action','totalCount','root')
	store =result.store;
	var cm = result.cm;
	var sm = result.sm;
	
	store.load({params:{
 		start:0,limit:15
	}});
	
	var gridButtons = ['-',
	  {
    	id:'addTemple',
    	text:"新建",
    	iconCls:'add',
    	handler:function(){
    		addBuried(store);
    	}
    },'-',{
    	id:'detailBuried',
    	text:"查看",
    	iconCls:'add',
    	handler:function(){
    		getDetailBuried('detail');
    	}
    },'-',{
    	id:'updateBuried',
    	text:"修改",
    	iconCls : 'add',
    	handler:function(){
    		getDetailBuried('update');
    	}
    },'-',{
    	id:'delBuried',
    	text:"删除",
    	iconCls:'del',
    	handler:function(){
    		var rows = listGrid.getSelectionModel().getSelections();
    		delRows('buriedAction!delBuried.action','buriedId',rows,reload);
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
    	id:'imp',
    	text:"导入数据",
    	iconCls:'imp',
    	handler:function(){
    		var tmpUrl = 'buriedAction!expTemp.action?';
    		var url = 'buriedAction!impData.action?';
    		impData(tmpUrl,url);
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