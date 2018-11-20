function detailBuried(store,obj){
	var line = obj.buried;
	var start = obj.start;
	var end = obj.end;
	
	var buriedPartName =itemText(1,80,'buriedPartName','直埋段名称',line.buriedPartName,null);
	var buriedPartArea = itemText(.5,80,'buriedPartArea','所属维护区域',line.buriedPartArea,null);
	var buriedPartLength = itemText(.5,80,'buriedPartLength','直埋段长度',line.buriedPartLength,null);
	var  propertyRight =itemText(.5,80,'propertyRight','产权性质',getShareType(line.propertyRight),null);
    var propertyDept = itemText(.5,80,'propertyDept','产权单位',getShareOrg(line.propertyDept),null);
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
		      buriedPartName,buriedPartArea, 
		      buriedPartLength,propertyRight,propertyDept
		]
	});
    
	var AstoneName = itemText(1,80,'AstoneName','标石名称',start.stoneName,null);
	var Aposition = itemText(.5,80,'Aposition','标石方位',getPosition(start.stonePosition),null);
	var AstoneNum = itemText(.5,80,'AstoneNum','序号',start.stoneNum,null);
	var Achengzai = itemText(.5,80,'Achengzai','承载点类型',getBear(start.chengzaidian_type),null);
	var AstoneArea = itemText(.5,80,'AstoneArea','维护区域',start.stoneArea,null);
	var Alongitude = itemText(.5,80,'Alongitude','经度',start.longitude,null);
	var Alatitude = itemText(.5,80,'Alatitude','纬度',start.latitude,null);
	var ApropertyNature = itemText(.5,80,'ApropertyNature','产权性质',getShareType(start.propertyNature),null);
	var ApropertyComp = itemText(.5,80,'ApropertyComp','产权单位',getShareOrg(start.propertyComp),null);
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
		   AstoneName,Aposition,AstoneNum,Achengzai,AstoneArea,
		   Alongitude,Alatitude,ApropertyNature,ApropertyComp
		]
	});
    
    var ZstoneName = itemText(1,80,'ZstoneName','标石名称',end.stoneName,null);
	var Zposition = itemText(.5,80,'Zposition','标石方位',getPosition(end.stonePosition),null);
	var ZstoneNum = itemText(.5,80,'ZstoneNum','序号',end.stoneNum,null);
	var Zchengzai = itemText(.5,80,'Zchengzai','承载点类型',getBear(end.chengzaidian_type),null);
	var ZstoneArea = itemText(.5,80,'ZstoneArea','维护区域',end.stoneArea,null);
	var Zlongitude = itemText(.5,80,'Zlongitude','经度',end.longitude,null);
	var Zlatitude = itemText(.5,80,'Zlatitude','纬度',end.latitude,null);
	var ZpropertyNature = itemText(.5,80,'ZpropertyNature','产权性质',getShareType(end.propertyNature),null);
	var ZpropertyComp = itemText(.5,80,'ZpropertyComp','产权单位',getShareOrg(end.propertyComp),null);
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
		   ZstoneName,Zposition,ZstoneNum,Zchengzai,ZstoneArea,
		   Zlongitude,Zlatitude,ZpropertyNature,ZpropertyComp
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