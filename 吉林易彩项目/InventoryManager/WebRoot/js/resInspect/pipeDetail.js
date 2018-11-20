function detailPipeLine(store,obj){
	var line = obj.pipe;
	var start = obj.start;
	var end = obj.end;
	var pipeSegmentName =itemText(1,80,'pipeSegmentName','管道段名称',line.pipeSegmentName,null);
	var holeQuantity = itemText(.5,80,'holeQuantity','管孔数目',line.holeQuantity,null);
    var  sharingType =itemText(.5,80,'sharingType','产权性质',getShareType(line.sharingTypeEnumId),null);
    var constructionSharingOrg = itemText(.5,80,'constructionSharingOrg','产权单位',line.constructionSharingOrg,null);
	var dataQualityPrincipal = itemText(.5,80,'dataQualityPrincipal','质量责任人',line.dataQualityPrincipal,null);
    var lineFilter = new Ext.form.FormPanel({
	    layout : "column",
	    id:'lineFilter',
	    title:'管道段',  
		border : true,
		width:'97%',
		autoHeight:true,
		frame:true,
		fileUpload : true,
		items:[
		      pipeSegmentName,holeQuantity,
		      sharingType,constructionSharingOrg,dataQualityPrincipal
		]
	});
    
    var AwellNameSub = itemText(1,80,'AwellNameSub','井名称',start.wellNameSub,null);
    var Adirection = itemText(.5,80,'Adirection','井方位',start.direction,null);
    var AwellNo = itemText(.5,80,'AwellNo','井序号',start.wellNo,null);
    var Aregion = itemText(.5,80,'Aregion','维护区域',start.region,null);
    var AmanholeShape = itemText(.5,80,'AmanholeShape','井型号',getHoleShape(start.manholeShape),null);
    var Alon = itemText(.5,80,'Alon','经度',start.longitude,null);
    var Alat = itemText(.5,80,'Alat','纬度',start.latitude,null);
    var Ashare =itemText(.5,80,'Ashare','产权性质',getShareType(start.constructionSharingEnumId),null); 
    var Aorg  = itemText(.5,80,'Aorg','产权单位',getShareOrg(start.constructionSharingOrg),null);
    var startForm = new Ext.form.FormPanel({
	    layout : "column",
	    id:'startForm',
	    title:'A端设备',  
		border : true,
		width:'97%',
		autoHeight:true,
		frame:true,
		fileUpload : true,
		items:[
		       AwellNameSub,Adirection,AwellNo,
		       Aregion,AmanholeShape,Alon,Alat,
		       Ashare,Aorg
		]
	});
    
    var ZwellNameSub = itemText(1,80,'ZwellNameSub','井名称',end.wellNameSub,null);
    var Zdirection = itemText(.5,80,'Zdirection','井方位',end.direction,null);
    var ZwellNo = itemText(.5,80,'ZwellNo','井序号',end.wellNo,null);
    var Zregion = itemText(.5,80,'Zregion','维护区域',end.region,null);
    var ZmanholeShape = itemText(.5,80,'ZmanholeShape','井型号',getHoleShape(end.manholeShape),null);
    var Zlon = itemText(.5,80,'Zlon','经度',end.longitude,null);
    var Zlat = itemText(.5,80,'Zlat','纬度',end.latitude,null);
    var Zshare =itemText(.5,80,'Zshare','产权性质',getShareType(end.constructionSharingEnumId),null); 
    var Zorg  = itemText(.5,80,'Zorg','产权单位',getShareOrg(end.constructionSharingOrg),null);
    var endForm = new Ext.form.FormPanel({
	    layout : "column",
	    id:'endForm',
	    title:'Z端设备',  
		border : true,
		width:'97%',
		autoHeight:true,
		frame:true,
		fileUpload : true,
		items:[
		       ZwellNameSub,Zdirection,ZwellNo,
		       Zregion,ZmanholeShape,Zlon,Zlat,
		       Zshare,Zorg
		]
	});
	
	var detailWin = new Ext.Window({
		title:'查看详情',
		closeAction : "destroy",
      	width:650,
      	height:450,
      	autoScroll:true,
      	modal:true,
      	buttonAlign:"center",
      	items:[lineFilter,startForm,endForm],
      	buttons:[{
      		text:"关闭",
      		handler:function(){
      			detailWin.destroy();
      		}
      	}]
	});
	detailWin.show();
}