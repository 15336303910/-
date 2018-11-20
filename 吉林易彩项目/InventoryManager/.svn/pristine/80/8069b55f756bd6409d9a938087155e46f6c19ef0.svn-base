var store;
var thisPagesize = 15;
var listGrid;
var autoHeight = false;

function reload(){
	var buriedPartNameQuery = Ext.getCmp('buriedPartNameQuery').getValue();
	var buriedPartAreaQuery = Ext.getCmp('buriedPartAreaQuery').getValue();
	listGrid.getStore().reload({params:{
		start:0,limit:15,buriedPartName:buriedPartNameQuery,buriedPartArea:buriedPartAreaQuery
	}});
}

function getBuriedPart(type){
	var row = listGrid.getSelectionModel().getSelections();
	if(row.length!=1){
		Ext.MessageBox.alert("","请选择一条数据！");
	}
	var r=row[0];
	Ext.Ajax.request({ 
		url:context_path+'/buriedPartAction!getBuriedPart.action',
		method:'post',
		params:{id:r.data['id']},
		success:function(fp,o){
			var obj = eval("("+fp.responseText+")");
			if(type == 'update'){
				updateBuriedPart(listGrid.getStore(),obj);
			}else{
				detailBuriedPart(listGrid.getStore(),obj);
			}
		}
 	});
}
Ext.onReady(function() {
	Ext.data.Connection.prototype.timeout ='300000';
	
	var buriedPartNameQuery = itemText(.33,80,'buriedPartNameQuery','直埋段名称');
	var buriedPartAreaQuery = itemText(.33,80,'buriedPartAreaQuery','维护区域');
	var queryPanel = new Ext.form.FormPanel({
		bodyStyle : 'padding-top:5px',
	    layout : "column",
	    id:'queryPanel',
		border : false,
		width:'100%',
		frame:true,
		items:[buriedPartNameQuery,buriedPartAreaQuery
		]
	});
	var column = "[{header:'id',dataIndex:'id',hidden:true,width:150,fun:null}," +
				 "{header:'直埋',dataIndex:'buriedStr',hidden:false,width:100,fun:null}," +
				 "{header:'直埋段名称',dataIndex:'buriedPartName',hidden:false,width:100,fun:null}," +
				 "{header:'维护区域',dataIndex:'buriedPartArea',hidden:false,width:150,fun:null}," +
				 "{header:'直埋段长度',dataIndex:'buriedPartLength',hidden:false,width:100,fun:null}," +
				 "{header:'开始实施',dataIndex:'buriedPartStart',hidden:false,width:100,fun:null}," +
				 "{header:'终止实施',dataIndex:'buriedPartEnd',hidden:false,width:100,fun:null}," +
				 "{header:'承载光缆段',dataIndex:'buriedPartOptical',hidden:false,width:100,fun:null}," +
				 "{header:'产权性质',dataIndex:'propertyRightStr',hidden:false,width:100,fun:null}," +
				 "{header:'产权单位',dataIndex:'propertyDeptStr',hidden:false,width:100,fun:null}," +
				 "{header:'质量责任人',dataIndex:'dataQualitier',hidden:false,width:100,fun:null}," +
				 "{header:'一线维护人',dataIndex:'maintainer',hidden:false,width:100,fun:null}," +
				 "{header:'录入时间',dataIndex:'createTime',hidden:false,width:150,fun:null}" +
				 "]";
	var result = basicStore(column,'buriedPartAction!getBuriedPartGrid.action','totalCount','root')
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
    		addBuriedPart(store);
    	}
    },'-',{
    	id:'updateBuriedPart',
    	text:'修改',
    	iconCls:'add',
    	handler:function(){
    		getBuriedPart('update');
    	}
    },'-',{
    	id:'detailBuriedPart',
    	text:'查看',
    	iconCls:'add',
    	handler:function(){
    		getBuriedPart('detail');
    	}
    },'-',{
    	id:'delBuriedPart',
    	text:"删除",
    	iconCls:'del',
    	handler:function(){
    		var rows = listGrid.getSelectionModel().getSelections();
    		delRows('buriedPartAction!delBuriedPart.action','id',rows,reload);
    	}
    },'-',{
    	id:'exp',
    	text:"导出数据",
    	iconCls:'exp',
    	handler:function(){
    		var buriedPartNameQuery = Ext.getCmp('buriedPartNameQuery').getValue();
    		var buriedPartAreaQuery = Ext.getCmp('buriedPartAreaQuery').getValue();
    		var data = "buriedPartName="+buriedPartNameQuery+"&buriedPartArea="+buriedPartAreaQuery;
			var url = context_path+'/buriedPartAction!expData.action?'+data;
			url = encodeURI(url);
			window.open(url,"_blank");
    	}
    },'-',{
    	id:'imp',
    	text:"导入数据",
    	iconCls:'imp',
    	handler:function(){
    		var tmpUrl = 'buriedPartAction!expTemp.action?';
    		var url = 'buriedPartAction!impData.action?';
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
	    tbar:new Ext.Toolbar({ //标题+按钮的工具栏
        	items:gridButtons
        }),
	    bbar: new Ext.PagingToolbar({
	        pageSize: thisPagesize,
	        store: store,
	        displayInfo: true,
	        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
	        emptyMsg: "没有记录"
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