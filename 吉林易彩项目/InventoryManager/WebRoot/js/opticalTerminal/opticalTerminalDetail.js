

/**
 * 查看详情
 * @param store
 */
function detailOpt(store,obj){
	var terminalName = itemText(.5,80,'terminalName','光终端盒名称',obj.terminalName,null);
	var longitude = itemText(.5,80,'longitude','经度',obj.longitude,null);
	var latitude = itemText(.5,80,'latitude','纬度',obj.latitude,null);
	var rowNum = itemText(.5,80,'rowNum','行数',obj.rowNum,null);
	var colNum = itemText(.5,80,'colNum','列数',obj.colNum,null);
	var attachName = itemText(.5,80,'attachName','归属点名称',obj.attachName,null);
	var attachTypeStr = itemText(.5,80,'attachTypeStr','归属点类型',obj.attachTypeStr,null);
	var terminalAddres = itemText(.5,80,'terminalAddres','设备地址',obj.terminalAddres,null);
	var dataQualitier = itemText(.5,80,'dataQualitier','质量责任人',obj.dataQualitier,null);
	var maintainer = itemText(.5,80,'maintainer','数据维护人',obj.maintainer,null);
	var detailPanel =new Ext.form.FormPanel({
	    layout : "column",
	    id:'detailPanel',
		border : false,
		width:'100%',
		autoHeight:true,
		frame:true,
		fileUpload : true,
		items:[
		       terminalName,attachTypeStr,rowNum,colNum,longitude,latitude,
		       terminalAddres,dataQualitier,maintainer
		]
	});
	var detailWin = new Ext.Window({
		title:'查看详情',
		closeAction : "destroy",
      	width:600,
      	height:250,
      	autoScroll:true,
      	modal:true,
      	buttonAlign:"center",
      	items:[detailPanel],
      	buttons:[{
      		text:"关闭",
      		handler:function(){
      			detailWin.destroy();
      		}
      	}]
	});
	detailWin.show();
}