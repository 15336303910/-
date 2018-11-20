

/**
 * 查看详情
 * @param store
 */
function detailBuriedPart(store,obj){
	var buriedPartName = itemText(.5,80,'buriedPartName','直埋段名称',obj.buriedPartName,null);
	var buriedStr = itemText(.5,80,'buriedStr','所属直埋',obj.buriedStr,null);
	var buriedPartArea = itemText(.5,80,'buriedPartArea','维护区域',obj.buriedPartArea,null);
	var buriedPartLength = itemText(.5,80,'buriedPartLength','直埋段长度',obj.buriedPartLength,null);
	var buriedPartStart = itemText(.5,80,'buriedPartStart','开始设备',obj.buriedPartStart,null);
	var buriedPartEnd = itemText(.5,80,'buriedPartEnd','结束设备',obj.buriedPartEnd,null);
	var buriedPartOptical = itemText(.5,80,'buriedPartOptical','承载光缆段',obj.buriedPartOptical,null);
	var propertyRightStr = itemText(.5,80,'propertyRightStr','产权性质',obj.propertyRightStr,null);
	var propertyDeptStr = itemText(.5,80,'propertyDeptStr','产权单位',obj.propertyDeptStr,null);
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
		       buriedPartName,buriedStr,buriedPartArea,buriedPartLength,buriedPartStart,
		       buriedPartEnd,buriedPartOptical,propertyRightStr,propertyDeptStr,dataQualitier,maintainer
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