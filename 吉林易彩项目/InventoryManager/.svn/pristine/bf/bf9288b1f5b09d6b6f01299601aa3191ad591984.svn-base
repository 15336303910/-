

/**
 * 查看详情
 * @param store
 */
function detailStone(store,obj){
	var stoneName = itemText(.5,80,'stoneName','标石名称',obj.stoneName,null);
	var stonePositionStr = itemText(.5,80,'stonePositionStr','标石方位',obj.stonePositionStr,null);
	var stoneNum = itemText(.5,80,'stoneNum','标石序号',obj.stoneNum,null);
	var stoneArea = itemText(.5,80,'stoneArea','所属区域',obj.stoneArea,null);
	var longitude = itemText(.5,80,'longitude','经度',obj.longitude,null);
	var latitude = itemText(.5,80,'latitude','纬度',obj.latitude,null);
	var propertyNatureStr = itemText(.5,80,'propertyNatureStr','产权性质',obj.propertyNatureStr,null);
	var propertyCompStr = itemText(.5,80,'propertyCompStr','产权单位',obj.propertyCompStr,null);
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
		    stoneName,stonePositionStr,stoneNum,stoneArea,longitude,latitude,
		    propertyNatureStr,propertyCompStr,dataQualitier,maintainer
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