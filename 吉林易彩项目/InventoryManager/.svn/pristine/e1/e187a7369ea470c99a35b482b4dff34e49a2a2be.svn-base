/**
 * 得到站点信息
 * @param scene
 * @param id
 * @param type
 */
function getSiteTopo(scene,id,type){
	$.ajax({
		type:'POST',
		url:context_path+'/approvalAction!getSiteTopo.action?siteId='+id+'&&type='+type,
		dataType:'json',
		async:true,
		success:function(result) {
			var node = newNode(winWidth/2,winHeight/2, 32, 32, result.center, context_path+'/images/canvas/station.png');    // 创建一个节点
            node.id = result.centerId;
			node.resType = "station";
			node.addEventListener("dbclick", function() {
            	scene.clear();
            	window.location.href = context_path + "/pages/approval/site/siteInfo.jsp?parentId="+node.id+"&&parentType=station";
            });
			
            scene.add(node); 
            var lineObj = eval("("+result.line+")");
            //设置环形布局
			var stard = 0;
	  			var radius = 200;
			var avd = 360/lineObj.length;
			var ahd = avd*Math.PI/180;
			
            for(var i=0;i<lineObj.length;i++){
            	var vmNode = newNode(Math.sin((ahd*i))*radius+(winWidth/2),Math.cos((ahd*i))*radius+(winHeight/2),
            			32, 32, lineObj[i].sideName, context_path+'/images/canvas/station.png');
            	vmNode.id = lineObj[i].sideId;
            	vmNode.resType = lineObj[i].sideType;
            	scene.add(vmNode);
            	var link = newLink(node,vmNode,lineObj[i].cablename);
            	link.id= lineObj[i].cableid;
            	scene.add(link);
            	vmNode.addEventListener("click",function(){
                	document.getElementById("resName").value=lineObj[i].sideName;
                	document.getElementById("resId").value=lineObj[i].sideId;
                	document.getElementById("resType").value='station';
                });
            	vmNode.addEventListener("dbclick", function() {
	            	scene.clear();
	            	window.location.href = context_path + "/pages/approval/site/siteInfo.jsp?parentId="+lineObj[i].sideId+"&&parentType="+vmNode.resType;
	            });
            }
            JTopo.layout.layoutNode(scene, node, true);
		}
	});
}


/**
 * 根据站点ID得到机房信息
 * @param parentId
 * @param type
 */
function getGeneratorTopo(parentId,type){
	$.ajax({
		type:'POST',
		url:context_path+'/approvalAction!getGeneratorTopo.action?siteId='+parentId,
		async:true,
		success:function(result) {
			var obj = eval("("+result+")");
			for(var i=0;i<obj.length;i++){
				var generatName = obj[i].text;
				if(generatName.length > 7){
					generatName = generatName.substring(0,7)+"&-40&38@"+generatName.substring(7)+"&-45&65";
				}
				
				addNode((winWidth/2)+((i-1)*50),(winHeight/2)+((i-1)*50),generatName,obj[i].id,obj[i].resType);
			}
		}
	});
}

/**
 * 得到机房下面的
 * 机架信息
 * @param parentId
 * @param type
 */
function getEqutTopo(parentId,type){
	$.ajax({
		type:'POST',
		url:context_path+'/approvalAction!getEqutTopo.action?generatorId='+parentId,
		async:true,
		success:function(result) {
			var obj = eval("("+result+")");
			for(var i=0;i<obj.length;i++){
				var equtName = obj[i].text;
				if(equtName.length > 7){
					equtName = equtName.substring(0,7)+"&-40&38@"+equtName.substring(7)+"&-45&65";
				}
				var row =1;
				var col = 1;
				var model = 0;
				if(obj[i].model != 'null'){
					model = obj[i].model;
				}else{
					model =1;
				}
				if(obj[i].row != 'null'){
					row= obj[i].row ;
				}else{
					row =1;
				}
				if(obj[i].col == 'null'){
					col=1;
				}else{
					col= obj[i].col ;
				}
				addNode((100)+((col-1)*90)+100,(row*90)+100,equtName,obj[i].id.substring(4),obj[i].resType);
			}
		}
	});
}

/**
 * 得到机架信息
 * @param parentId
 * @param type
 */
function getRackTopo(parentId,type){
	$.ajax({
		type:'POST',
		url:context_path+'/approvalAction!getRackTopo.action?rackId='+parentId+'&type='+type,
		async:true,
		success:function(result) {
			var obj = eval("("+result+")");
			for(var i=0;i<obj.length;i++){
				var texts = obj[i].text.split("-");
				var num = parseInt(texts[texts.length-1]);
				var posX,posY;
				if(isNaN(num)){
					posX=((winWidth/2)-200);
					posY=100;
				}else{
					posX=((winWidth/2)-100) + (Math.ceil(num /6)*50);
					posY=((winHeight/2-300))+ num*70;
				}
				addNode(posX,posY,obj[i].text,obj[i].id,obj[i].resType);
			}
		}
	});
}

/**
 * 得到端子面板图
 * @param parentId
 * @param type
 */
function getOdmTopo(parentId,type){
	$.ajax({
		type:'POST',
		url:context_path+'/approvalAction!getPointTopo.action?id='+parentId+'&type='+type,
		async:true,
		success:function(result) {
			var obj = eval("("+result+")");
			for(var i= 0;i<obj.length;i++){
				var posX ,posY,resType;
				posX = ((winWidth/2)-200)+((obj[i].col-1)*70);
				posY = (winHeight/2-200)+((obj[i].row-1)*70);
				resType ='point'+obj[i].state;
				addNode(posX,posY,obj[i].text,obj[i].id,resType);
			}
		}
	});
}

/**
 * 增加点资源
 * @param xPosition
 * @param yPosition
 * @param name
 * @param id
 * @param resType
 */
function addNode(xPosition, yPosition, name, id, resType){
	var node = newNode(xPosition, yPosition, 50, 50, name, context_path+'/images/canvas/'+resType+'.png');    // 创建一个节点
	node.id = id;
	node.resType = resType;
    scene.add(node); // 放入到场景中
    node.addEventListener("click",function(){
    	document.getElementById("resName").value=name;
    	document.getElementById("resId").value=id;
    	document.getElementById("resType").value=resType;
    	resClickFun(xPosition, yPosition, name, id, resType);
    });
	node.addEventListener("dbclick", function() {
    	scene.clear();
    	window.location.href =context_path + "/pages/approval/site/siteInfo.jsp?parentId="+id+"&&parentType="+resType;
    }); 
}


/**
 * 单击资源显示对应的数据
 * @param xPosition
 * @param yPosition
 * @param name
 * @param id
 * @param resType
 */
function resClickFun(xPosition, yPosition, name, id, resType){
	$.ajax({
		type:'POST',
		url:context_path+'/approvalAction!getResContent.action?resId='+id+'&resType='+resType,
		async:true,
		success:function(result) {
			var proters = result.split(",");
			var map = new Map();
			for(var i=0;i<proters.length;i++){
				var pro = proters[i].split(":");
				map.put(pro[0], pro[1]);
			}
			var keys = map.keys();
			var content = "";
			for(var i=0;i<keys.length;i++){
				var key = keys[i].replace(/\"/g,"");
				var value = map.get(keys[i]).replace(/\"/g,"");
				content +='<h5>&nbsp;&nbsp;' + key + ':' + value + '</h5>';
			}
			
			showToolip({
				x: xPosition + 30,
				y: yPosition + 30,
				width: 0,
				content: content
				});
		}
	});
}

/**
 * 得到光交箱的资源拓扑
 * @param scene
 * @param id
 * @param type
 * @returns
 */
function getOpticalTopo(scene,id,type){
	$.ajax({
		type:'POST',
		url:context_path+'/approvalAction!getOpticalTopo.action?eid='+id+'&&type='+type,
		dataType:'json',
		async:true,
		success:function(result) {
			var node = newNode(winWidth/2,winHeight/2, 32, 32, result.center, context_path+'/images/canvas/station.png');    // 创建一个节点
            node.id = result.centerId;
			node.resType = "optical";
			node.addEventListener("click",function(){
            	document.getElementById("resId").value=id;
            	document.getElementById("resType").value='optical';
            });
			node.addEventListener("dbclick", function() {
            	scene.clear();
            	window.location.href = context_path + "/pages/approval/site/siteInfo.jsp?parentId="+node.id+"&&parentType=optical";
            });
            scene.add(node); 
            JTopo.layout.layoutNode(scene, node, true);
		}
	});
}

/**
 * 
 * @param data
 */
function pockData(data){
	var proters = data.split(",");
	var map = new Map();
	for(var i=0;i<proters.length;i++){
		var pro = proters[i].split(":");
		map.put(pro[0], pro[1]);
	}
	return map;
}

/**
 * 移动鼠标隐藏提示框
 * @param e
 */
function mouseShow(e){
	var element = e.target;
	if (element && element.elementType == 'node') {
		
	}else{
		showToolip('hide');
	}
}

/**
 * 弹出气泡
 * @param options
 */
function showToolip(options) {
    var tipsWin = temp.find('div[name=topo_tips]').hide();
    var tips_body = tipsWin.find('.panel-body');
    var op = options || {};
    var x = op.x;
    var y = op.y;
    var width = op.width || 200;
    var content = op.content || '';
    if (content) {
      tips_body.show();
      tips_body.html(content);
      tipsWin.css({
        "left": x,
        "top": y,
        "width": width
      }).show();
    } else {
      tips_body.hide();
      tipsWin.hide();
    }
  }

/**
 * 查看现场图片 
 */
function transImgFun(){
	var resName = document.getElementById("resName").value;
	var resId = document.getElementById("resId").value;
	var resType = document.getElementById("resType").value;
	if(resId ==null || resId ==''){
		alert("请选中资源");
	}else{
		var url;
		url = context_path+"/pages/approval/resImageInfo.jsp?id="+resId+"&resType="+resType;
    	var pointDialog = new Dialog();
		pointDialog.Width = 300;
		pointDialog.Height = 300;
		pointDialog.Title = "资源图片页面";
		pointDialog.URL = url;
		pointDialog.show();
	}
}

/**
 * 数据同步调用
 */
function transResFun(){
	var resName = document.getElementById("resName").value;
	var resId = document.getElementById("resId").value;
	var resType = document.getElementById("resType").value;
	if(resType != 'rack' && resType !='optical'){
		alert("不支持此类资源数据同步!");
	}else{
		if(resType == 'optical'){
			resId =resId.replace('EIU_','');
		}
		resName = resName.replace('&-40&38@','');
		resName = resName.replace('&-45&65','');
		$.ajax({
			type:'POST',
			url:context_path+"/approvalAction!sendResObj.action?",
			async:true,
			data: {resId:resId, type:resType},
			success:function(result) {
				if(result =='success'){
					alert("同步完成");
				}
			}
		});
	}
}

