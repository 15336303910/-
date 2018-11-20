Ext.namespace("com.increase.cen.poleline");
var pageSize = 10;
var limit = 10;
var id="";

com.increase.cen.poleline.poleGrid = Ext.extend(Ext.Panel, {
	id:"poleGrid",
	bodyStyle : "padding:5px;",
	frame : true,
	bodyBorder : false,
	labelAlign : "right",
	buttonAlign : 'center',
	labelWidth : 100,
	height : 300,
	autoHeight:true,
	polelineId:null,
	initComponent : function(config) {
		id=this.polelineId;
		// 列模型
		var cm = new Ext.grid.ColumnModel([
		{header : '电杆ID',dataIndex : 'poleId',hidden : true}, 
		{header : '电杆名称',dataIndex : 'poleNameSub',width : 220,sortable : true}, 
		{header : '电杆编码',dataIndex : 'poleCode',width : 180,sortable : true}, 
		{header : '电杆类型',dataIndex : 'poleTypeEnumId',width : 100,sortable : true,
			renderer:function(v){
				if(v=='1'){
					return "普通杆";
				}else if(v=='2'){
					return "单接杆";
				}else if(v=='3'){
					return "双接杆";
				}else if(v=='4'){
					return "H型杆";
				}else if(v=='5'){
					return "A型杆";
				}else if(v=='6'){
					return "L型杆";
				}else if(v=='7'){
					return "三角杆";
				}else if(v=='8'){
					return "井型杆";
				}else if(v=='9'){
					return "引上杆";
				}else if(v=='10'){
					return "终端杆";
				}else if(v=='11'){
					return "角杆";
				}else if(v=='12'){
					return "分歧杆";
				}else if(v=='13'){
					return "T型杆";
				}else if(v=='14'){
					return "跨路杆";
				}
			}
		}, 
		{header : '电杆地址',dataIndex : 'poleAddress',width : 180,sortable : true}, 
		{header : '最后更新时间',dataIndex : 'lastUpdateDate',width : 180,sortable : true,
			renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')
		}]);
		// 从远程读取数据
		var proxy = new Ext.data.HttpProxy({
			url : "poleline!addPoleLS.action"

		});
		// Record 定义记录结果
		var pole = Ext.data.Record.create([
			{name : 'poleId',type : 'string',mapping : 'poleId',hidden : true
		}, {
			name : 'poleNameSub',
			type : 'string',
			mapping : 'poleNameSub'
		}, {
			name : 'poleCode',
			type : 'string',
			mapping : 'poleCode'
		}, {
			name : 'poleTypeEnumId',
			type : 'string',
			mapping : 'poleTypeEnumId'
		}, {
			name : 'poleAddress',
			type : 'string',
			mapping : 'poleAddress'
		}, {
			name : 'lastUpdateDate',
			mapping : 'lastUpdateDate',
			type : Ext.data.Types.DATE,
			dateFormat : 'Y-m-d\\TH:i:s'
		}]);
		// Reader以提供的Ext.data.Record 构造器中的映射为基础，从JSON响应创建一个Ext.data.Record 对象数组
		var reader = new Ext.data.JsonReader({
			totalProperty : "total",
			root : "poles"
		}, pole);
		this.store = new Ext.data.Store({// 提供数据输入
			proxy : proxy,
			reader : reader,
			remoteSort : true
		});
		this.poleGridPanel=new Ext.grid.GridPanel({
			id:"poleGridPanel",
			cm : cm,// 列定义的model(必需)
			store : this.store,
			height:300,
			width:950,
			autoDestroy:true,
			bbar : new Ext.PagingToolbar({// 分页工具栏
				store : this.store,
				pageSize : pageSize,
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
		
		//双击gridpanel的行，执行函数
		this.poleGridPanel.on('rowdblclick', function () {
			var isexits=false ;
    		var row=Ext.getCmp("poleGridPanel").getSelectionModel().getSelections();
        	if(row.length == 1){
        		pole = row[0].json;
        		var options = Ext.getDom("selectpole").childNodes;
        		if(options.length > 1){
        			for(var i=1;i<options.length;i++){
        				var option = options[i];
        				if(pole.poleId == option.value){
        					isexits=true ;
        					return false ;
        				}
        			}
        		}
        		if(!isexits){
        			var newNode = document.createElement("option"); 
        			newNode.innerHTML = pole.poleNameSub;
        			newNode.value = pole.poleId;
        			Ext.getDom("selectpole").appendChild(newNode);
        		}
        	}
		});
		
		
		this.items = [{
			layout : 'column', // 从左往右布局
			items : [{
				columnWidth : .8, // 该列有整行中所占百分比
				layout : 'form', // 从上往下布局
				labelWidth : 50,
				items : [this.poleGridPanel]
			}, {
				columnWidth : .2, // 该列有整行中所占百分比
				layout : 'form', // 从上往下布局
				labelWidth : 50,
				items : [{
					style : 'margin-left:10px;',
					xtype : 'fieldset',
					title : '已选择电杆',
					items:[{
						html:'<select name=selectpole width=100 size=14 id=selectpole multiple=multiple><option value="">---双击选择左侧电杆---</option></select>'
						
					},{
						html:'<input type="submit" name="deletepole" id="deletepole" value=" 移除选中电杆" onclick="deleteselect();">'
					}]
				}]
			}]
		}];
		// 按钮
		this.buttons = [{
			id : 'btnSubmit',
			text : "确定",
			hidden : false,
			icon : "imgs/save_btn.png",
			cls : "x-btn-text-icon",
			tooltip : '选择电杆',
			tooltipType : 'qtip',
			handler : this.btnSubmit.createDelegate(this)
		}, {
			xtype : 'tbspacer'
		}, {
			xtype : 'tbspacer'
		}, {
			text : "返回",
			icon : "imgs/back.png",
			cls : "x-btn-text-icon",
			tooltip : '关闭此窗口',
			tooltipType : 'qtip',
			handler : this.btnClose.createDelegate(this)
		}];
		
			// 重写initComponent superclass.initComponent.call(this);来调用父类的初始化方法.
			com.increase.cen.poleline.poleGrid.superclass.initComponent.call(this);
		},
	//保存按钮事件
	btnSubmit : function() {
		var select =  Ext.getDom("selectpole");
		var options = select.childNodes;
		var poleId = '';
		for(var i=1;i<options.length;i++){
			if(i==options.length-1){
				poleId += "'"+options[i].value+"'";
			}else{
				poleId +="'"+ options[i].value+"'"+",";
			}
		}
		var poleIdlist="("+poleId+")";
		Ext.Ajax.request({
			url : 'poleline!poleIdList.action',
			method : 'Post',
			params : {
				"poleInfoBean.poleLineId":id,
				"poleInfoBean.poleLineIdStr":poleIdlist
			},
			callback : function(options, success, response) {
				if (success) {
					Ext.Msg.show({
						title : '提示',
						width : 300,
						msg : '<img src="imgs/tip_success.png" align="center" hspace="30"/>'
								+ '生成杆路段成功!',
						buttons : {
							ok : "确定"
						}
					});
					Ext.getCmp("addPoleLSWindow").hide();
					Ext.getCmp("poleLineGrid").getStore().reload();
					var select =  Ext.getDom("selectpole");
					var options = select.childNodes;
					for(var i=1;i<options.length;i++){
				    		select.remove(i);
				    		i--;
				    	
				    };
				};
			}
		});

//		Ext.getCmp("receiver").setValue(username);
//		if(Ext.getCmp("receivername")!=null){
//			Ext.getCmp("receivername").setValue(realname);
//		}
//		Ext.getCmp("addPoleLSWindow").hide();
	},
	// 返回操作
	btnClose : function() {
		Ext.getCmp("addPoleLSWindow").hide();
		var select =  Ext.getDom("selectpole");
		var options = select.childNodes;
		for(var i=1;i<options.length;i++){
	    		select.remove(i);
	    		i--;
	    	
	    };
	}
});
	
Ext.reg("poleGrid", com.increase.cen.poleline.poleGrid);

function deleteselect(){
	var select =  Ext.getDom("selectpole");
	var options = select.childNodes;
	for(var i=1;i<options.length;i++){
    	var o = options[i];
    	if(o.selected==true && o.value !='' ){
    		select.remove(i);
    		i--;
    	}
    }
		
}



