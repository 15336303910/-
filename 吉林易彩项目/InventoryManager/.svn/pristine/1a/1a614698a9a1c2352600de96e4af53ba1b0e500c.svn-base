/**
 * 杆路段信息
 * @param store
 * @param obj
 */
function detailPoleLine(store,obj){
	var line = obj.pole;
	var start = obj.start;
	var end = obj.end;
	var poleLineSegmentName = itemText(1,80,'poleLineSegmentName','杆路段名称',line.poleLineSegmentName,null);
	var  sharingType =itemText(.5,80,'sharingType','产权性质',getShareType(line.constructionSharingEnumId),null);
	 var constructionSharingOrg = itemText(.5,80,'constructionSharingOrg','产权单位',getShareOrg(line.constructionSharingOrg),null);
    var lineFilter = new Ext.form.FormPanel({
	    layout : "column",
	    id:'lineFilter',
	    title:'杆路段',  
		border : true,
		width:'97%',
		autoHeight:true,
		frame:true,
		fileUpload : true,
		items:[
		       poleLineSegmentName,sharingType,constructionSharingOrg
		]
	});
    
    var ApoleName = itemText(.5,80,'ApoleName','电杆名称',start.poleNameSub,null);
    var Adirection = itemText(.5,80,'Adirection','方位',start.direction,null);
    var ApoleNo = itemText(.5,80,'ApoleNo','序号',start.poleNo,null);
    var Achengzai = itemText(.5,80,'Achengzai','承载点',getBear(start.chengzaidian_type),null);
    var Aregion = itemText(.5,80,'Aregion','所属维护区域',start.region,null);
    var ApoleType = itemText(.5,80,'ApoleType','电杆类型',getPoleType(start.poleTypeEnumId),null);
    var AshareType =itemText(.5,80,'AshareType','产权性质',getShareType(start.constructionSharingEnumId),null);
    var Alon = itemText(.5,80,'Alon','经度',start.poleLongitude,null);
    var Alat = itemText(.5,80,'Alat','纬度',start.poleLatitude,null);
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
		     ApoleName,Adirection,ApoleNo,Achengzai,
		     Aregion,ApoleType,Alon,Alat
		]
	});
    
    var ZpoleName = itemText(.5,80,'ZpoleName','电杆名称',end.poleNameSub,null);
    var Zdirection = itemText(.5,80,'Zdirection','方位',end.direction,null);
    var ZpoleNo = itemText(.5,80,'ZpoleNo','序号',end.poleNo,null);
    var Zchengzai = itemText(.5,80,'Zchengzai','承载点',getBear(end.chengzaidian_type),null);
    var Zregion = itemText(.5,80,'Zregion','所属维护区域',end.region,null);
    var ZpoleType = itemText(.5,80,'ZpoleType','电杆类型',getPoleType(end.poleTypeEnumId),null);
    var ZshareType =itemText(.5,80,'ZshareType','产权性质',getShareType(end.constructionSharingEnumId),null);
    var Zlon = itemText(.5,80,'Zlon','经度',end.poleLongitude,null);
    var Zlat = itemText(.5,80,'Zlat','纬度',end.poleLatitude,null);
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
		       ZpoleName,Zdirection,ZpoleNo,Zchengzai,
		       Zregion,ZpoleType,Zlon,Zlat
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