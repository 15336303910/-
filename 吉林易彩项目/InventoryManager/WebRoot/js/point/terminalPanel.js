Ext.onReady(function() {
	var columns;
	var map;Ext.QuickTips.init(); 
	var panelplace;
	var eid = parent.Ext.getDom("eid").value;
	var panelNo = 0;Ext.Ajax.timeout = 50000
	parent.parent.parent.shuloading();
	Ext.Ajax.request({
		url : 'point!getEqutSetting.action',
		method : 'Post',
		params : {
			'equtInfoBean.eid' : eid
		},
		async : false,
		callback : function(options, success, response){
			if(success){
				var jsonResult = Ext.util.JSON.decode(response.responseText);
				columns = jsonResult.col;
				map = jsonResult.map;
				panelplace = jsonResult.panelplace;
				setTerminalPanel();
			}
		}
	});
	
	function setTerminalPanel() {
		Ext.Ajax.request({
			url : 'point!getPointFromEqut.action',
			params : {
				'pointInfoBean.eid' : eid,
				'panelplace':panelplace,
				'map':map,
				'col':columns
			},
			method : 'Post',
			callback : function(options, success, response) {
				if (success) {
					var points = Ext.util.JSON.decode(response.responseText);
					var pls = [];
					if(panelplace == 1){
						pls = createStandView(points,map);
					}else if(panelplace == 2||panelplace == '2'){
						pls = screateStandView(points,map);
					}else if(panelplace == 3||panelplace == '3'){
						pls = createView_OCC_R_AB_2_12_576(points,map);
					}else{
						pls = createView(points,map);
					}
					var panelContainer = {
						xtype : "container",
						layout : "table",
						autoEl : "div",
						autoHeight : true,
						autoWidth : true,
						x : "10%",
						y : "10%",
						layoutConfig : {
							columns : 1
						},
						hideBorders : true,
						items : pls
					};
					Ext.MyViewport = Ext.extend(Ext.Viewport, {
						xtype : "viewport",
						layout : "border",
						initComponent : function() {
							this.items = [{
								title : "告警信息",
								xtype : "panel",
								region : "south",
								id : "southPanel",
								autoScroll : true,
								collapsible : true,
								collapsed : true,
								margins : "1 0 0 0",
								split : true,
								height : 200,
								contentEl : "alarmPanel"
							}, {
								layoutConfig : {
									align : "top",
									pack : "center"
								},
								region : "center",
								autoScroll : true,
								layout : "absolute",
								items : [panelContainer]
							}]
							Ext.MyViewport.superclass.initComponent.call(this);
						}
					})
					var port = new Ext.MyViewport();
				}
				parent.parent.parent.closemask();
			}
		});
	}
	//createView_OCC_R_AB_2_12_576
	function createView_OCC_R_AB_2_12_576(points,map){
		var pointIndex = 0;
		var pls = [];
		var mod = "";
		if (map.indexOf("n") != -1) {
			mod = map.split("n");
		} else {
			mod = [map];
		}
		var modL = mod.length;
		for (x = 0; x < modL-1; x++) {
			pls.push(creatContainer(creatPl_AB("MODFRAMEUP")));
			var plstate = mod[x].split(',');
			var plsL = plstate.length;
			var frameStrPos = plsL%2==0 ? plsL/2 : plsL/2+1;
			for (j = 0; j < plsL; j++) {
				panelNo++;//盘显示的数字
				//var panelStr= plstate[j].substring(0,2) == "01"? "A面":"B面";//A/B面
				var pl = [];
				pl.push(modSide);
				if(j==frameStrPos){
					pl.push(createModNoArea("A面"));
				}else{
					pl.push(createModNoArea(''));
				}
				pl.push(createSideLeft(parseInt(plstate[j].substring(2,4))));
				var canOut = false;
				//var firstPoint = points[pointIndex];
				var firstPoint = points[plstate[j]+'01'];
				if(typeof(firstPoint) != "undefined" && firstPoint.pid.indexOf(plstate[j]) == 0){
					canOut = true;
					for (k = 0; k < columns; k++) {
						var pNoStr = k<9 ? '0'+(k+1) : k+1;
						pNoStr = plstate[j] + pNoStr;
						pl.push(creatTerminal(points[pNoStr]));
					}
				}else{
					pl.push(creatPl_AB("EMPTYPL"));
				}
				pl.push(modSide);
				
				pl.push(createImg("gap", 30, 29));
				//右半部分
				
				pl.push(modSide);
				if(j==frameStrPos){
					pl.push(createModNoArea("B面"));
				}else{
					pl.push(createModNoArea(''));
				}
				var pid = "02"+plstate[j].substring(2,4);
				pl.push(createSideLeft(parseInt(plstate[j].substring(2,4))));
				var canOut = false;
				//var firstPoint = points[pointIndex];
				var firstPoint = points[pid+'01'];
				if(typeof(firstPoint) != "undefined" && firstPoint.pid.indexOf(pid) == 0){
					canOut = true;
					for (k = columns; k >0; k--) {
						var pNoStr = k<10 ? '0'+k : k;
						pNoStr = pid + pNoStr;
						pl.push(creatTerminal(points[pNoStr]));
					}
				}else{
					pl.push(creatPl_AB("EMPTYPL"));
				}
				pl.push(modSide);
				
				
				//pl.push(creatPanelAlarmArea(x + 1, j + 1, canOut));
				pls.push(creatContainer(pl));

				if (j == plsL - 1)
					break;
				pls.push(creatContainer(creatPl_AB("PLGAP")));
			}
			pls.push(creatContainer(creatPl_AB("MODFRAME")));
//			if (x == modL - 1)
//				break;
//			pls.push(creatContainer(creatPl_AB("MODGAP")));
		}
		
		return pls;
	}
	//ODF_D2_10_12_720
	function screateStandView(points,map){
		var pointIndex = 0;
		var pls = [];
		var mod = "";
		if (map.indexOf("n") != -1) {
			mod = map.split("n");
		} else {
			mod = [map];
		}
		var modL = mod.length;//框数
		for (x = 0; x < modL-5; x++) {
			var plstate = mod[x].split(',');
			var plsL = plstate.length;//盘数
			var plstate1=mod[x+5].split(',');
			var plsL1 = plstate1.length;//盘数
			var allplsL=plsL+plsL1;
			if(x==0){
				pls.push(creatContainer(creatPl_s("MODFRAME_12",allplsL)));
			}
			pls.push(creatContainer(creatPl_s("MODGAP_12s",10-x)));//空白处
			pls.push(creatContainer(creatPl_s("MODFRAME_12t",allplsL)));//带有相应的盘数字
			for (k = columns; k > 0; k--) {
				var pl = [];
				//pl.push(modSide);
				pl.push(createModSideLeft_s(k));
				for (j = 0; j < allplsL; j++) {//列数
					var pNoStr = k<10 ? '0'+k : k;
					if(j<plsL){
						pNoStr = plstate[j] + pNoStr;
						var nowpid=points[pNoStr];
						if(typeof(nowpid) != "undefined" && nowpid.pid.indexOf(plstate[j]) == 0){
							pl.push(creatTerminal_s(nowpid));
						}else{
							pl.push(creatEmptyTerminal_s());
						}
						if(j==plsL-1){//左半部完成
							pl.push(modSide);
							pl.push(creatPl_s("MODGAP_12",1));//左框与友框空白处
							pl.push(modSide);
							continue;
						}
					}else{
						pNoStr = plstate1[j-6] + pNoStr;
						var nowpid=points[pNoStr];
						if(typeof(nowpid) != "undefined" && nowpid.pid.indexOf(plstate1[j-6]) == 0){
							pl.push(creatTerminal_s(nowpid));
						}else{
							pl.push(creatEmptyTerminal_s());
						}
					}
					if(j==(allplsL-1)){
						break;
					}
					pl.push(plGap_s);
				}
				pl.push(modSide);
				pls.push(creatContainer(pl));
			}
			pls.push(creatContainer(creatPl_s("MODFRAME_12",allplsL)));
			if (x == modL-5 - 1)
				break;
			//pls.push(creatContainer(creatPl_s("MODGAP_12s",allplsL)));//框与框空白处
		}
		
		return pls;
	}
	function createStandView(points,map){
		var pointIndex = 0;
		var pls = [];
		var mod = "";
		if (map.indexOf("n") != -1) {
			mod = map.split("n");
		} else {
			mod = [map];
		}
		var modL = mod.length;//框数
		for (x = 0; x < modL; x++) {
			var plstate = mod[x].split(',');
			var plsL = plstate.length;//盘数
			pls.push(creatContainer(creatPl_s("MODFRAME",plsL)));
			for (k = 0; k < columns; k++) {
				var pl = [];
				pl.push(createModSideLeft_s(k+1));
				for (j = 0; j < plsL; j++) {
					var pNoStr = k<9 ? '0'+(k+1) : k+1;
					pNoStr = plstate[j] + pNoStr;
			
					var nowpid=points[pNoStr];
					if(typeof(nowpid) != "undefined" && nowpid.pid.indexOf(plstate[j]) == 0){
						pl.push(creatTerminal_s(nowpid));
					}else{
						pl.push(creatEmptyTerminal_s());
					}
					
					if(j==plsL-1)
						break;
					pl.push(plGap_s);
				}
				pl.push(modSide);
				pls.push(creatContainer(pl));
			}
			pls.push(creatContainer(creatPl_s("MODFRAME",plsL)));
			if (x == modL - 1)
				break;
			pls.push(creatContainer(creatPl_s("MODGAP",plsL)));
		}
		
		return pls;
	}
	
	function createView(points,map){
		var pointIndex = 0;
		var pls = [];
		var mod = "";
		if (map.indexOf("n") != -1) {
			mod = map.split("n");
		} else {
			mod = [map];
		}
		var modL = mod.length;
		for (x = 0; x < modL; x++) {
			pls.push(creatContainer(creatPl("MODFRAMEUP")));
			var plstate = mod[x].split(',');
			var plsL = plstate.length;
			var frameStrPos = plsL/2+1;
			for (j = 0; j < plsL; j++) {
				panelNo++;//盘显示的数字
				var panelStr= plstate[j].substring(0,2) + "-" + plstate[j].substring(2,4);
				var pl = [];
				pl.push(modSide);
				if(j==frameStrPos){
					pl.push(createModNoArea(parseInt(plstate[j].substring(0,2))+"框"));
				}else{
					pl.push(createModNoArea(''));
				}
				pl.push(createSideLeft(parseInt(plstate[j].substring(2,4))));
				var canOut = false;
				//var firstPoint = points[pointIndex];
				var firstPoint = points[plstate[j]+'01'];
				if(typeof(firstPoint) != "undefined" && firstPoint.pid.indexOf(plstate[j]) == 0){
					canOut = true;
					for (k = 0; k < columns; k++) {
						var pNoStr = k<9 ? '0'+(k+1) : k+1;
						pNoStr = plstate[j] + pNoStr;
						pl.push(creatTerminal(points[pNoStr]));
					}
				}else{
					pl.push(creatPl("EMPTYPL"));
				}
				pl.push(modSide);
				pl.push(creatPanelAlarmArea(x + 1, j + 1, canOut));
				pls.push(creatContainer(pl));

				if (j == plsL - 1)
					break;
				pls.push(creatContainer(creatPl("PLGAP")));
			}
			pls.push(creatContainer(creatPl("MODFRAME")));
			if (x == modL - 1)
				break;
			pls.push(creatContainer(creatPl("MODGAP")));
		}
		
		return pls;
	}
	
	function createImg(name, width, height){
		var html = "<img width='"+width+"px' height='"+height+"px' src='imgs/point/terminalPanel/"+name+".png'>";
		var img = {
			html : html
		}
		return img;
	}
	var modFrame = {
		html:"<img width='30px' height='10px' src='imgs/point/terminalPanel/frame.png'>"
	}
	var modFrame_s = {
		html:"<img width='30px' height='10px' src='imgs/point/terminalPanel/frame.png'>"
	}
	var modCorner = {
		html:"<img width='10px' height='10px' src='imgs/point/terminalPanel/frame.png'>"
	}
	var modCorner_left = {
		html:"<img width='40px' height='10px' src='imgs/point/terminalPanel/frame.png'>"
	}
	var modSide = {
		//html:"<div style=backgroud-img('imgs/point/terminalPanel/head.png') >1</div>"
		//html:"<table><tr><td style=backgroud-img('imgs/point/terminalPanel/head.png')> 文字</td></tr></table>"
		html:"<img width='10px' height='29px' src='imgs/point/terminalPanel/frame.png'>"
	}

	var modSide_left = {
		//html:"<div style=backgroud-img('imgs/point/terminalPanel/head.png') >1</div>"
		//html:"<table><tr><td style=backgroud-img('imgs/point/terminalPanel/head.png')> 文字</td></tr></table>"
		//html:"<img width='10px' height='29px' src='imgs/point/terminalPanel/frame.png'>"
		html : "<div style='position: relative; width: 60px; height: 29px;'> "+
		"<img src='imgs/point/terminalPanel/frame.png' width='60' height='29' alt=''> "+
		"<span style='color: silver; font-size: larger; font-weight:bold; position: absolute; top: 0; left: 0;'>77</span>"+
		"</div> "
	}
	var plGap = {
		html:"<img width='30px' height='3px' src='imgs/point/terminalPanel/frame.png'>"
	}
	var plGap_s = {
		html:"<img width='3px' height='29px' src='imgs/point/terminalPanel/frame.png'>"
	}
	var plGapSide = {
		html:"<img width='10px' height='3px' src='imgs/point/terminalPanel/frame.png'>"
	}
	var plGapSide_s = {
		html:"<img width='3px' height='10px' src='imgs/point/terminalPanel/frame.png'>"
	}
	var plGapSide_left = {
		html:"<img width='10px' height='3px' src='imgs/point/terminalPanel/frame.png'>"
	}
	var plGapSide_left_Gap = {
		html:"<img width='20px' height='3px' src='imgs/point/terminalPanel/gap.png'>"
	}
	var modGap = {
		html:"<img width='30px' height='10px' src='imgs/point/terminalPanel/gap.png'>"
	}
	var modGap_s = {
		html:"<img width='30px' height='10px' src='imgs/point/terminalPanel/gap.png'>"
	}
	var modGap_s_12 = {
		html:"<img width='30px' height='20px' src='imgs/point/terminalPanel/gap.png'>"
	}
	var modFrame_s_12 = {
		html:"<img width='30px' height='10px' src='imgs/point/terminalPanel/gap.png'>"
	}
	var modGap_s_1 = {
		html:"<img width='3px' height='10px' src='imgs/point/terminalPanel/gap.png'>"
	}
	var modGap_s_1_12 = {
		html:"<img width='3px' height='20px' src='imgs/point/terminalPanel/gap.png'>"
	}
	var modGapSide_12 = {
		html:"<img width='10px' height='20px' src='imgs/point/terminalPanel/frame.png'>"
	}
	var modGapSide = {
		html:"<img width='10px' height='10px' src='imgs/point/terminalPanel/frame.png'>"
	}
	var modGapSide_left = {
		html:"<img width='40px' height='10px' src='imgs/point/terminalPanel/frame.png'>"
	}
	var EmptyTerminal = {
		html:"<img width='30px' height='29px' src='imgs/point/terminalPanel/duanzi_gray.png'>"
	}
	var EmptyTerminal_s = {
		html:"<img width='29px' height='30px' src='imgs/point/terminalPanel/s_duanzi_gray.png'>"
	}
	
	function creatPl_s(part,length){
		var pl=[];
		if(part=="MODFRAME"){
			pl.push(modCorner);
			for(i=0; i<length; i++){
				pl.push(modFrame_s);
				if(i == length-1)
					break;
				pl.push(plGapSide_s);
			}
			pl.push(modCorner);
		}
		if(part=="MODFRAME_12"||part=="MODFRAME_12t"){
			pl.push(modCorner);
			for(i=0; i<length; i++){
				//pl.push(modFrame_s);
				if(part=="MODFRAME_12"){//不带盘的数字
					pl.push(modFrame_s);
				}else{//带盘的
					if(i<6){
						pl.push(createFrame_s(i+1));
					}else{
						pl.push(createFrame_s(i-5));
					}
				}
				if(i==5){
					pl.push(modCorner);
					pl.push(modFrame_s_12);
					pl.push(modCorner);
					continue;
				}
				if(i == length-1)
					break;
				pl.push(plGapSide_s);
			}
			pl.push(modCorner);
		}
		if(part=="PLGAP"){
			pl.push(plGapSide_s);
			for(i=0; i<columns; i++){
				pl.push(plGap_s);
			}
			pl.push(plGapSide_s);
		}
		if(part=="MODGAP"){
			pl.push(modGapSide);
			for(i=0; i<length; i++){
				pl.push(modGap_s);
				if(i == length-1)
					break;
				pl.push(modGap_s_1);
			}
			pl.push(modGapSide);
		}
		if(part=="MODGAP_12s"){
			pl.push(modGapSide_12);
			for(i=0; i<12; i++){
				if(i==2){
					pl.push(createGap_s(length));
				}else if(i==8){
					pl.push(createGap_s(length-5));
				}else{
					pl.push(modGap_s_12);
				}
				if(i==5){
					pl.push(modGapSide_12);
					pl.push(modFrame_s_12);
					pl.push(modGapSide_12);
					continue;
				}
				if(i == 11)
					break;
				pl.push(modGap_s_1_12);
			}
			pl.push(modGapSide_12);
		}
		if(part=="MODGAP_12"){
			pl.push(modGap_s_12);
		}
		if(part=="EMPTYPL"){
			for(i=0; i<columns; i++){
				pl.push(EmptyTerminal_s);
			}
		}
		return pl;
	}
	
	function creatPl_AB(part){
		var pl=[];
		if(part=="MODFRAME"){
			pl.push(modCorner_left);
			for(i=0; i<columns; i++){
				pl.push(modFrame);
			}
			pl.push(modCorner);
			
			pl.push(createImg("gap", 30, 10));
			
			pl.push(modCorner_left);
			for(i=0; i<columns; i++){
				pl.push(modFrame);
			}
			pl.push(modCorner);
		}
		if(part=="MODFRAMEUP"){
			pl.push(modCorner_left);
			for(i=0; i<columns; i++){
				pl.push(createModFrameUp(i+1));
			}
			pl.push(modCorner);
			
			pl.push(createImg("gap", 30, 10));
			
			pl.push(modCorner_left);
			for(i=columns; i>0; i--){
				pl.push(createModFrameUp(i));
			}
			pl.push(modCorner);
		}
		if(part=="PLGAP"){
			pl.push(plGapSide_left);
			pl.push(plGapSide_left_Gap);
			pl.push(plGapSide_left);
			for(i=0; i<columns; i++){
				pl.push(plGap);
			}
			pl.push(plGapSide);
			
			pl.push(createImg("gap", 30, 3));
			
			pl.push(plGapSide_left);
			pl.push(plGapSide_left_Gap);
			pl.push(plGapSide_left);
			for(i=0; i<columns; i++){
				pl.push(plGap);
			}
			pl.push(plGapSide);
		}
		if(part=="MODGAP"){
			pl.push(modGapSide_left);
			for(i=0; i<columns; i++){
				pl.push(modGap);
			}
			pl.push(modGapSide);
			
			pl.push(createImg("gap", 30, 10));
			
			pl.push(modGapSide_left);
			for(i=0; i<columns; i++){
				pl.push(modGap);
			}
			pl.push(modGapSide);
		}
		if(part=="EMPTYPL"){
			for(i=0; i<columns; i++){
				pl.push(EmptyTerminal);
			}
		}
		return pl;
	}
	
	function creatPl(part){
		var pl=[];
		if(part=="MODFRAME"){
			pl.push(modCorner_left);
			for(i=0; i<columns; i++){
				pl.push(modFrame);
			}
			pl.push(modCorner);
		}
		if(part=="MODFRAMEUP"){
			pl.push(modCorner_left);
			for(i=0; i<columns; i++){
				pl.push(createModFrameUp(i+1));
			}
			pl.push(modCorner);
		}
		if(part=="PLGAP"){
			pl.push(plGapSide_left);
			pl.push(plGapSide_left_Gap);
			pl.push(plGapSide_left);
			for(i=0; i<columns; i++){
				pl.push(plGap);
			}
			pl.push(plGapSide);
		}
		if(part=="MODGAP"){
			pl.push(modGapSide_left);
			for(i=0; i<columns; i++){
				pl.push(modGap);
			}
			pl.push(modGapSide);
		}
		if(part=="EMPTYPL"){
			for(i=0; i<columns; i++){
				pl.push(EmptyTerminal);
			}
		}
		return pl;
	}
	function createModSideLeft_s(index){//ODF_D2_10_12_720 显示端子
		var modSide_s_left = {
		html:"<div style='position: relative; width: 10px; height: 29px;'> "+
		"<img src='imgs/point/terminalPanel/frame.png' width='10' height='29' alt=''> "+
		"<span style='color: silver; font-size: 10%; font-weight:bold; position: absolute; top: 8; left: 1;'>"+index+"</span>"+
		"</div> "
		}
		return modSide_s_left;
	}
	function createModNoArea(index){//正常设备显示框号
		var ModNoArea = {
			html:"<div style='position: relative; width: 20px; height: 29px;'> "+
			"<img width='30px' height='29px' src='imgs/point/terminalPanel/gap.png' alt=''> "+
			"<span style='color: #5599FF; font-size: 60%; font-weight:bold; position: absolute; top: 0; left: 5;'>"+index+"</span>"+
			"</div> "
		}
		return ModNoArea;
	}
	function createGap_s(index){//ODF_D2_10_12_720 显示框
		var modSide_s_left = {
		html:"<div style='position: relative; width: 30px; height: 20px;'> "+
		"<img width='30px' height='29px' src='imgs/point/terminalPanel/gap.png' alt=''> "+
		"<span style='color: #5599FF; font-size: 20%; font-weight:bold; position: absolute; top: 3; left: 0;'>"+index+"框"+"</span>"+
		"</div> "
		}
		return modSide_s_left;
	}
	function createFrame_s(index){//ODF_D2_10_12_720 显示盘
		var modSide_s_left = {
		html:"<div style='position: relative; width: 30px; height: 10px;'> "+
		"<img width='30px' height='10px' src='imgs/point/terminalPanel/frame.png' alt=''> "+
		"<span style='color: silver; font-size: 20%; font-weight:bold; position: absolute; top: 0; left: 8;'>"+index+"</span>"+
		"</div> "
		}
		return modSide_s_left;
	}
	function createSideLeft(index){
		var left = {
			html : "<div style='position: relative; width: 10px; height: 29px;'> "+
			"<img src='imgs/point/terminalPanel/frame.png' width='10' height='29' alt=''> "+
			"<span style='color: silver; font-size: 60%; font-weight:bold; position: absolute; top: 0; left: 0;'>"+index+"</span>"+
			"</div> "
		}
		return left;
	}
	
	function createModFrameUp(index){
		var up = {
			html:"<div style='position: relative; width: 30px; height: 10px;'> "+
				"<img src='imgs/point/terminalPanel/frame.png' width='30' height='10' alt=''> "+
				"<span style='color: silver; font-size: 60%; font-weight:bold; position: absolute; top: 0; left: 10;'>"+index+"</span>"+
				"</div> "
		}
		return up;
	}
	
	function createModSideLeft_s(index){
		var modSide_s_left = {
			html:"<div style='position: relative; width: 10px; height: 29px;'> "+
			"<img src='imgs/point/terminalPanel/frame.png' width='10' height='29' alt=''> "+
			"<span style='color: silver; font-size: 10%; font-weight:bold; position: absolute; top: 8; left: 1;'>"+index+"</span>"+
			"</div> "
		}
		return modSide_s_left;
	}
	
	function creatTerminal(point){
		var content="";
		if(point.pserv==null||point.pserv==''){
			content="端子编码："+point.pcode;
		}else{
			content="端子编码："+point.pcode+"</br>承载业务："+point.pserv;
		}//当鼠标移动到端子图片上时，显示该端子的端子编码与承载业务
		var terminal = {
			html : "<input type='hidden' id='s"+point.pid+"' value='"+point.pstat+"'>"+"<img id='t" + point.pid + "' width='30px' height='29px' src='imgs/point/terminalPanel/duanzi_" + point.pstat + ".png' onclick=showPointInfo('"+point.pid+"') ext:qtip='"+content+"'>"
			
		};
		return terminal;
	}
	
	function creatTerminal_s(point){
		var terminal = {
			html : "<input type='hidden' id='s"+point.pid+"' value='"+point.pstat+"'>"+"<img id='t" + point.pid + "' width='30px' height='29px' src='imgs/point/terminalPanel/s_duanzi_" + point.pstat + ".png' onclick=showPointInfo('"+point.pid+"')>"
			
		};
		return terminal;
	}
	//向盘子中添加空端子
	function creatEmptyTerminal_s(){
		var terminal = {
			html : "<input type='hidden' >"+"<img  width='30px' height='29px' src='imgs/point/terminalPanel/s_duanzi_gray.png' >"
		};
		return terminal;
	}
	function creatPanelAlarmArea(modNo, frameNo, canOut){
		var plNo = (modNo>9?modNo:"0"+modNo)+""+(frameNo>9?frameNo:"0"+frameNo)+"";
		var className = "";
		var img = "";
		if(canOut){
			className = "canOut";
			img = "on";
			
		}else{
			className = "canIn";
			img = "off";
		}
		var plAlarmArae = {
			html:"<input type='hidden' id='s"+plNo+"' value='0'>"
				//+"<input type='checkbox' height='16px' class='box "+className+"' style='vertical-align:7px;display:none' id='c"+plNo+"'/>"
				+"<img id='c" + plNo + "' width='69px' height='29px'  class='box "+className+"' src='imgs/point/terminalPanel/"+img+".jpg' style='display:none' onclick=changeImg('c"+plNo+"')>"
				+"<img id='t" + plNo + "' width='30px' height='29px' src='imgs/point/terminalPanel/plalarm_0.png'>"
		}
		return plAlarmArae;
	}
	
	function creatContainer(pl) {
		var container = {
			xtype : "container",
			layout : "table",
			autoEl : "div",
			autoHeight : true,
			autoWidth : true,
			layoutConfig : {
				columns : columns + 2 + 1 + 1//2为边框 1为告警区域
			},
			hideBorders : true,
			items : pl
		};
		return container;
	}
});

var focusPid="";

var panelAlarmDispose = function disposePanelAlarm(pid,state){
	
}

function changeImg(id){
	var or_src = Ext.getDom(id).src;
	if(or_src.indexOf("on.jpg") != -1){
		Ext.getDom(id).src = 'imgs/point/terminalPanel/off.jpg';
	}else if(or_src.indexOf("off.jpg") != -1){
		Ext.getDom(id).src = 'imgs/point/terminalPanel/on.jpg';
	}
}

function setFocus(pid){
	focusPid = pid;
	Ext.get("t"+pid).dom.src = "imgs/point/terminalPanel/duanzi_selected.png";
}

function focusCancel(pid){
	var state = Ext.getDom("s" + pid).value;
	Ext.getDom("t" + alarm.pid).src = "imgs/point/terminalPanel/duanzi_"+ state +".png";
}

function showEvent(list) {
	for (i = 0; i < list.length; i++) {
		var alarm = list[i];
		var oldState = 0;
		if(Ext.getDom("s" + alarm.pid) != null){
			oldState = Ext.getDom("s" + alarm.pid).value;
		}
		var lastId = Ext.getDom("lastId").value;
		if (parseInt(alarm.id) > parseInt(lastId)) {
			//保存最大id
			Ext.getDom("lastId").value = alarm.id;
			var index = Ext.getDom("alarmIndex").value;
			var alarmText = {
				html : index++ + ". " + alarm.alarmText + "<br/>"
			};
			Ext.getDom("alarmIndex").value = index;
			if(Ext.getDom("s" + alarm.pid) != null){
				Ext.getDom("s" + alarm.pid).value = alarm.state;
			}
			if(parseInt(alarm.type)==0){
//				var oldImgPath = Ext.getDom("t" + alarm.pid).src;
//				oldImgPath = oldImgPath.substring(oldImgPath.lastIndexOf('_')+1);
				//focusCancel(alarm.pid)
				//Ext.getDom("t" + alarm.pid).src = "imgs/point/terminalPanel/duanzi_"+ alarm.state +".png";
				//setFocus(alarm.pid)
			}else if(parseInt(alarm.type)==1){
				$("#t"+alarm.pid).unbind("click");
				Ext.getDom("t" + alarm.pid).src = "imgs/point/terminalPanel/plalarm_"+ alarm.state +".png";
				if(alarm.state != "0" && alarm.state != "1"){
					$("#c"+alarm.pid).attr("onclick", "");
					var onAndOffImg = Ext.getDom("c" + alarm.pid).src;
					var isOn = false;
					if(onAndOffImg.indexOf("on.jpg") != -1){
						Ext.getDom("c" + alarm.pid).src = "imgs/point/terminalPanel/on_disable.jpg";
					}
					if(onAndOffImg.indexOf("off.jpg") != -1){
						Ext.getDom("c" + alarm.pid).src = "imgs/point/terminalPanel/off_disable.jpg";
					}
					var equtstate = parent.Ext.getCmp("equtState").iconCls;
					if(equtstate == "equtState1"){
						var str = "";
						if(alarm.state=="2"){
							str = "拔出";
						}else if(alarm.state=="3"){
							str = "更换";
						}else if(alarm.state=="4"){
							str = "插入";
						}
						$("#t" + alarm.pid).bind("click", function() {
							Ext.MessageBox.show({
								title : '确认',
								msg : '<div style="margin-top:5px;margin-left:10px;">您要确认'+str+'该业务板/盘吗?</div>',
								width : 300,
								multiline : false,// 设置为true，提示用户输入多行文本
								closable : false,
								buttons : {
									yes : '确定',
									no : '取消'
								},
								icon : Ext.MessageBox.QUESTION,
								fn : function(btn) {
									if (btn == 'yes') {// 如果选择确定，则进行修改
										Ext.Ajax.request({//IE中以params方式传值,无效
											url : 'equt!equtPanelAlarmDispose.action?point.eid='+alarm.eid+"&point.pid="+alarm.pid+"&selectValue="+alarm.state,
											method : 'Post'
										});
									}
								}
							});
						});
					}
				}
			}
			
			Ext.DomHelper.insertFirst("alarmPanel", alarmText);
		}
	}
}

var oldPointImg = "";
var oldPointSelected = "";

function showPointInfo(pid){
	if(oldPointSelected!=""){
		pointDisSelect(oldPointSelected);
	}
	parent.showPointProps(pid);
	pointSelect(pid);
}

function changPointState(state){
	oldPointImg = "imgs/point/terminalPanel/duanzi_"+state+".png";
}

function pointSelect(pid){
	oldPointSelected = pid;
	oldPointImg =  Ext.get("t"+pid).dom.src;
	var picPath = oldPointImg.substring(0,oldPointImg.lastIndexOf('_'))+"_selected.png";
	//Ext.get("t"+pid).dom.src = "imgs/point/terminalPanel/duanzi_selected.png";
	Ext.get("t"+pid).dom.src = picPath;
}

function pointDisSelect(pid){
	Ext.get("t"+pid).dom.src = oldPointImg;
	oldPointImg = "";
	oldPointSelected = "";
}
