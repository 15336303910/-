/***
 * 查看字典内容
 * @param id
 */
function checkDicTable(id){
	var stone;
	var listGrid;
	var column = "[{header:'序号',dataIndex:'id',hidden:false,width:50,fun:null}," +
	 			"{header:'隐藏值',dataIndex:'value',hidden:false,width:150,fun:null}," +
	 			"{header:'展现值',dataIndex:'text',hidden:false,width:150,fun:null}," +
	 			"{header:'序号',dataIndex:'orderValue',hidden:false,width:50,fun:null}," +
	 			"{header:'录入时间',dataIndex:'createTime',hidden:false,width:150,fun:null}" +
	 			"]";
	var result = basicStore(column,'dictAction!getDictTabGird.action?dicId='+id,'totalCount','root')
	stone =result.store;
	var cm = result.cm;
	var sm = result.sm;

	stone.load({params:{
		start:0,limit:15
	}});
	
	var gridButtons 
		=['-',
	      {
			id:'delDicTable',
			text:"删除",
	    	iconCls:'del',
	    	handler:function(){
	    		var rows = listGrid.getSelectionModel().getSelections();
	    		if(rows.length < 1){
	    			Ext.Msg.alert('信息提示','请选择一条记录！');
	    			return ;
	    		}
	    		var ids = "";
	    		for(var i=0;i<rows.length;i++ ){
	    			ids = ids+rows[i].data["id"]+",";
	    		}
	    		ids = ids.substr(0,ids.length-1);
	    		Ext.Msg.confirm('信息','确认要删除么?',function(btn){
	    			if(btn =='yes'){
	    			 	Ext.Ajax.request({ 
	    					url:context_path+"/dictAction!delDicTable.action?ids='"+ids+"'",
	    					method:'post',
	    					params:{parms:ids},
	    					success:function(fp,o){
	    						var resultText =  Ext.decode(fp.responseText);
	    						if (resultText.success ==true) {
	    							Ext.Msg.alert('信息提示','删除成功');
	    							listGrid.getStore().reload({params:{
    									start:0,limit:15
    								}});
	    						}
	    					}
	    			 	});
	    			}
	    		});
	    	}
	       }
	     ];
	listGrid = new Ext.grid.GridPanel({
		store: stone,
		cm: cm,
		sm: sm,
		autoHeight: false,
		loadMask:{msg : '加载数据中，请稍候...'},
		height: 200,
		anchor:'100% -30',
	    bodyStyle:'width:100%',
	    stripeRows: true,// 让grid相邻两行背景色不同
	    loadMask: true,
	    tbar:new Ext.Toolbar({ //标题+按钮的工具栏
        	items:gridButtons
        }),
	    bbar: new Ext.PagingToolbar({
	        pageSize: 15,
	        store: stone,
	        displayInfo: true,
	        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
	        emptyMsg: "没有记录"
	   })
	});
	var checkWin = new Ext.Window({
		id:'checkWin',
		title:'查看属性',
      	width:700,
      	closable:false,
      	height:290,
      	modal:true,
      	buttonAlign:"center",
    	items:listGrid,
      	buttons:[{
      		text:"关闭",
      		handler:function(){
      			checkWin.destroy();
      		}
      	}]
	});
	checkWin.show();
}