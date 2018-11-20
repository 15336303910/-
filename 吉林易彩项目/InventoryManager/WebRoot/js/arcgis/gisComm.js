
/**
 * 显示地图点.
 * 	json = "{\"info\":\"[{'id':'28', 'name':'咯么站点_测试光交想光缆段','type':'cable',
 * start:{'id':'16','name':'咯么站点','lat':'39.8934563305','lon':'116.3808325728','type':'site'},
 * end:{'id':'EIU_1479174081270','name':'测试光交想','lat':'39.893449187','lon':'116.3801137732','type':'optical'}},
 * {start:{'id':11,'name':'北京接头盒测试','lat':'39.8951257315','lon':'116.3807338652','type':'joint'}}]\"}";
 * @param json
 */
function drawRes(json){
	var infoObj ;
	var strType = typeof json;
	if(strType == 'object'){
		infoObj= json;
	}else{
		infoObj= eval("("+json+")");
	}
	var lineObj ;
	if(typeof(infoObj.info) =='string'){
		lineObj= eval("("+infoObj.info+")");
	}else{
		lineObj = infoObj.info;
	}
	var layer = esri.layers.GraphicsLayer({id:"layer"+(500*Math.random())});
	for(var i=0;i<lineObj.length;i++){
		 layer.add(getPoint(lineObj[i].start))
	     if(lineObj[i].id != null){
	    	 layer.add(getPoint(lineObj[i].end));
	    	 layer.add(getLine(lineObj[i]));
	     }
	 }
	return layer;
}


/**
 * 得到一个点并设置成中心点
 * @param json
 */
function getCentPoint(json){
	var pt = new esri.geometry.Point([116.3808325728,39.8934563305]);
	var infoObj= eval("("+json+")");
	var lineObj ;
	if(typeof(infoObj.info) =='string'){
		lineObj= eval("("+infoObj.info+")");
	}else{
		lineObj = infoObj.info;
	}
	if(lineObj.length >0){
		pt.setX(Number(lineObj[0].start.lon));
		pt.setY(Number(lineObj[0].start.lat));
	}
	return pt;
}

/**
 * 得到点线
 *  start:{'id':'16','name':'咯么站点','lat':'39.8934563305','lon':'116.3808325728','type':'site'},
 * end:{'id':'EIU_1479174081270','name':'测试光交想','lat':'39.893449187','lon':'116.3801137732','type':'optical'}},
 * {start:{'id':11,'name':'北京接头盒测试','lat':'39.8951257315','lon':'116.3807338652','type':'joint'}}]\"}";
 * @param lineObj
 */
function getLine(lineObj){
	var polyline = new esri.geometry.Polyline
	([[lineObj.start.lon,lineObj.start.lat],
	  [lineObj.end.lon,lineObj.end.lat]]);
	var lineType = lineObj.type;
	var color = new dojo.Color([245,0,68]);
	if(lineType =='buried'){
		color = new dojo.Color([14,14,14]);
	}else if(lineType == 'cable'){
		color = new dojo.Color([235,0,68]);
	}else if(lineType == 'poleLine'){
		color = new dojo.Color([0,0,238]);
	}else if(lineType =='pipe'){
		color = new dojo.Color([0,163,0]);
	}
    var symbol = new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, color, 2);
    var graphic = new esri.Graphic(polyline, symbol);
    var attributes = {"id":lineObj.id,"resType":lineObj.type,"grapType":"line","resName":lineObj.name};
    graphic.setAttributes(attributes);
    /*var template = new esri.InfoTemplate();
    template.setTitle(lineObj.name);
    template.setContent('起始设备:'+lineObj.start.name+";<br>终止设备:"+lineObj.end.name+";");
    graphic.setInfoTemplate(template);*/
    return graphic;
	
}

/**
 * 得到点的数据
 * @param point
 */
function getPoint(point){
		var x = point.lon;
	    var y = point.lat;
	    var png = "station";
	    if(point.type == 'station'){
	    	png = "station";
	    }else if(point.type =='optical' || point.type =='equtinfo'){
	    	png = "equtinfo";
	    }else if(point.type == 'pole'){
	    	png = "pole";
	    }else if(point.type == 'stone'){
	    	png = "stone";
	    }else if(point.type == 'well'){
	    	png = "well";
	    }else if(point.type =='joint'){
	    	png = "joint";
	    }else if(point.type == 'OpticalTerminal'){
	    	png = "OpticalTerminal";
	    }
	    var img = context_path+"/images/map/"+png+".png";
	    if(png != "station" && point.state != '' && point.state != null && point.state != 'null'){
	    	img = context_path+"/images/map/"+png+point.state+".png";
	    }
	    var pt = new esri.geometry.Point({
	    	type:'point',
	    	"x":x,
	    	"y":y
	    });
	    var pictureMarkerSymbol = new esri.symbol.PictureMarkerSymbol(img, 20, 20);
	    var g = new esri.Graphic(pt, pictureMarkerSymbol); 
	    var attributes = {"id":point.id,"resType":point.type,"grapType":"point","resName":point.name,"resNum":point.resNum};
	    g.setAttributes(attributes);
	   /* var template = new esri.InfoTemplate();
	    template.setTitle(point.name);
	    template.setContent('经度:'+point.lon+";" +
	    					"<br>纬度:"+point.lat+";");
	    g.setInfoTemplate(template);*/
	    return g;
}

/**
 * 渲染驳回资源
 * @param json
 * @returns
 */
function drawReject(json,resLayer){
	for(var i=0;i<resLayer.graphics.length ;i++){
		for(var j=0;j<json.length;j++){
			if(resLayer.graphics[i].attributes.id == json[j].resId && resLayer.graphics[i].attributes.resType == json[j].resType){
				var template = new esri.InfoTemplate();
			    template.setTitle(json[j].resName);
			    if(json[j].rejectStr != null && json[j].rejectStr != ''){
			    	 template.setContent(json[j].rejectStr);
			    }else{
			    	template.setContent("");
			    }
			    resLayer.graphics[i].setInfoTemplate(template);
				
				var png = "station";
			    if(resLayer.graphics[i].attributes.resType == 'station'){
			    	png = "station";
			    }else if(resLayer.graphics[i].attributes.resType =='optical'){
			    	png = "equtinfo";
			    }else if(resLayer.graphics[i].attributes.resType == 'pole'){
			    	png = "pole";
			    }else if(resLayer.graphics[i].attributes.resType == 'stone'){
			    	png = "stone";
			    }else if(resLayer.graphics[i].attributes.resType == 'well'){
			    	png = "well";
			    }else if(resLayer.graphics[i].attributes.resType =='joint'){
			    	png = "joint";
			    }else if(resLayer.graphics[i].attributes.resType == 'OpticalTerminal'){
			    	png = "OpticalTerminal";
			    }
			    var img = context_path+"/images/map/"+png+"Reject.png";
			    var rejectSymbol = new esri.symbol.PictureMarkerSymbol(img, 20, 20);
			    resLayer.graphics[i].setSymbol(rejectSymbol);
			}
		}
	}
}

