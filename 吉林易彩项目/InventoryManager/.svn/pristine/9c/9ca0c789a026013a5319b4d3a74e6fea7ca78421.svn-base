var limit=10;
function showPoleGrid_a() {
	var pipeGrid = Ext.getCmp("pipeGrid");
	//if (!pipeGrid.poleGrid_aWindow) {
		pipeGrid.poleGrid_aWindow = new com.increase.cen.poleline.PolelineWindow({
			id : "poleGrid_aWindow",
			width : 750,
			title : '选择电杆',
			iconCls : 'i-script-window',
			items : [{
				id : "poleGrid_a",
				height : 350,
				xtype : 'poleGrid_a'// 窗口里的表单

			}]
		})
	//}
	var store = Ext.getCmp("poleGrid_a").getStore();
	var loadMarsk = new Ext.LoadMask(document.body, {
		msg : '数据加载中，请稍候......',
		disabled : false,
		store : store
	});
	store.addListener('beforeload', function() {
		loadMarsk.show();
	});
	store.load({
		params : {
			start : 0,
			limit : 10
		}
	});
	pipeGrid.poleGrid_aWindow.show();
}
function showPoleGrid_z() {
	var pipeGrid = Ext.getCmp("pipeGrid");
	//if (!pipeGrid.poleGrid_zWindow) {
		pipeGrid.poleGrid_zWindow = new com.increase.cen.poleline.PolelineWindow({
			id : "poleGrid_zWindow",
			width : 750,
			title : '选择电杆',
			iconCls : 'i-script-window',
			items : [{
				id : "poleGrid_z",
				height : 350,
				xtype : 'poleGrid_z'// 窗口里的表单

			}]
		})
	//}
	var store = Ext.getCmp("poleGrid_z").getStore();
	var loadMarsk = new Ext.LoadMask(document.body, {
		msg : '数据加载中，请稍候......',
		disabled : false,
		store : store
	});
	store.addListener('beforeload', function() {
		loadMarsk.show();
	});
	store.load({
		params : {
			start : 0,
			limit : 10
		}
	});
	pipeGrid.poleGrid_zWindow.show();
}
function selectPole_a(rowIndex){
	var store = Ext.getCmp("poleGrid_a").getStore();
	var pole = store.data.items[rowIndex].json;
	
	var end_poleid = "";
	if(Ext.getCmp("addEndDeviceId")){
		end_poleid= Ext.getCmp("addEndDeviceId").getValue();
	}else if(Ext.getCmp("modifyEndDeviceId")){
		end_poleid= Ext.getCmp("modifyEndDeviceId").getValue();
	}
	if (pole.poleId == end_poleid) {
		Ext.Msg.show({
			title : '提示',
			width : 300,
			msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
					+ '起始设施与终止设施不能相同!',
			buttons : {
				ok : "确定"
			}
		})
		return false;
	}
	if(Ext.getCmp("addStartDeviceId")){
		Ext.getCmp("addStartDeviceId").setValue(pole.poleId);
		Ext.getCmp("addStartDeviceName").setValue(pole.poleNameSub);
		Ext.getCmp("addStartDeviceType").setValue("2");
	}else if(Ext.getCmp("modifyStartDeviceId")){
		Ext.getCmp("modifyStartDeviceId").setValue(pole.poleId);
		Ext.getCmp("modifyStartDeviceName").setValue(pole.poleNameSub);
		Ext.getCmp("modifyStartDeviceType").setValue("2");
	}
	Ext.getCmp("poleGrid_aWindow").hide();
}
function selectPole_z(rowIndex){
	var store = Ext.getCmp("poleGrid_z").getStore();
	var pole = store.data.items[rowIndex].json;
	
	var start_poleid = "";
	if(Ext.getCmp("addStartDeviceId")){
		start_poleid= Ext.getCmp("addStartDeviceId").getValue();
	}else if(Ext.getCmp("modifyStartDeviceId")){
		start_poleid= Ext.getCmp("modifyStartDeviceId").getValue();
	}
	if (pole.poleId == start_poleid) {
		Ext.Msg.show({
			title : '提示',
			width : 300,
			msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
					+ '起始设施与终止设施不能相同!',
			buttons : {
				ok : "确定"
			}
		})
		return false;
	}
	if(Ext.getCmp("addEndDeviceId")){
		Ext.getCmp("addEndDeviceId").setValue(pole.poleId);
		Ext.getCmp("addEndDeviceName").setValue(pole.poleNameSub);
		Ext.getCmp("addEndDeviceType").setValue("2");
	}else if(Ext.getCmp("modifyEndDeviceId")){
		Ext.getCmp("modifyEndDeviceId").setValue(pole.poleId);
		Ext.getCmp("modifyEndDeviceName").setValue(pole.poleNameSub);
		Ext.getCmp("modifyEndDeviceType").setValue("2");
	}
	Ext.getCmp("poleGrid_zWindow").hide();
}
function showWellGrid_a() {
	var pipeGrid = Ext.getCmp("pipeGrid");
	//if (!pipeGrid.wellGrid_aWindow) {
		pipeGrid.wellGrid_aWindow = new com.increase.cen.poleline.PolelineWindow({
			id : "wellGrid_aWindow",
			width : 750,
			title : '选择井',
			iconCls : 'i-script-window',
			items : [{
				id : "wellGrid_a",
				height : 350,
				xtype : 'wellGrid_a'// 窗口里的表单

			}]
		})
	//}
	var store = Ext.getCmp("wellGrid_a").getStore();
	var loadMarsk = new Ext.LoadMask(document.body, {
		msg : '数据加载中，请稍候......',
		disabled : false,
		store : store
	});
	store.addListener('beforeload', function() {
		loadMarsk.show();
	});
	store.load({
		params : {
			start : 0,
			limit : 10
		}
	});
	pipeGrid.wellGrid_aWindow.show();
}
function showWellGrid_z() {
	var pipeGrid = Ext.getCmp("pipeGrid");
	//if (!pipeGrid.wellGrid_zWindow) {
		pipeGrid.wellGrid_zWindow = new com.increase.cen.poleline.PolelineWindow({
			id : "wellGrid_zWindow",
			width : 750,
			title : '选择井',
			iconCls : 'i-script-window',
			items : [{
				id : "wellGrid_z",
				height : 350,
				xtype : 'wellGrid_z'// 窗口里的表单

			}]
		})
	//}
	var store = Ext.getCmp("wellGrid_z").getStore();
	var loadMarsk = new Ext.LoadMask(document.body, {
		msg : '数据加载中，请稍候......',
		disabled : false,
		store : store
	});
	store.addListener('beforeload', function() {
		loadMarsk.show();
	});
	store.load({
		params : {
			start : 0,
			limit : 10
		}
	});
	pipeGrid.wellGrid_zWindow.show();
}
function selectWell_a(rowIndex){
	var store = Ext.getCmp("wellGrid_a").getStore();
	var well = store.data.items[rowIndex].json;
	
	var end_poleid = "";
	if(Ext.getCmp("addEndDeviceId")){
		end_poleid= Ext.getCmp("addEndDeviceId").getValue();
	}else if(Ext.getCmp("modifyEndDeviceId")){
		end_poleid= Ext.getCmp("modifyEndDeviceId").getValue();
	}
	if (well.wellId == end_poleid) {
		Ext.Msg.show({
			title : '提示',
			width : 300,
			msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
					+ '起始设施与终止设施不能相同!',
			buttons : {
				ok : "确定"
			}
		})
		return false;
	}
	if(Ext.getCmp("addStartDeviceId")){
		Ext.getCmp("addStartDeviceId").setValue(well.wellId);
		Ext.getCmp("addStartDeviceName").setValue(well.wellNameSub);
		Ext.getCmp("addStartDeviceType").setValue("1");
	}else if(Ext.getCmp("modifyStartDeviceId")){
		Ext.getCmp("modifyStartDeviceId").setValue(well.wellId);
		Ext.getCmp("modifyStartDeviceName").setValue(well.wellNameSub);
		Ext.getCmp("modifyStartDeviceType").setValue("1");
	}
	Ext.getCmp("wellGrid_aWindow").hide();
}
function selectWell_z(rowIndex){
	var store = Ext.getCmp("wellGrid_z").getStore();
	var well = store.data.items[rowIndex].json;
	
	var start_poleid = "";
	if(Ext.getCmp("addStartDeviceId")){
		start_poleid= Ext.getCmp("addStartDeviceId").getValue();
	}else if(Ext.getCmp("modifyStartDeviceId")){
		start_poleid= Ext.getCmp("modifyStartDeviceId").getValue();
	}
	if (well.wellId == start_poleid) {
		Ext.Msg.show({
			title : '提示',
			width : 300,
			msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
					+ '起始设施与终止设施不能相同!',
			buttons : {
				ok : "确定"
			}
		})
		return false;
	}
	if(Ext.getCmp("addEndDeviceId")){
		Ext.getCmp("addEndDeviceId").setValue(well.wellId);
		Ext.getCmp("addEndDeviceName").setValue(well.wellNameSub);
		Ext.getCmp("addEndDeviceType").setValue("1");
	}else if(Ext.getCmp("modifyEndDeviceId")){
		Ext.getCmp("modifyEndDeviceId").setValue(well.wellId);
		Ext.getCmp("modifyEndDeviceName").setValue(well.wellNameSub);
		Ext.getCmp("modifyEndDeviceType").setValue("1");
	}
	Ext.getCmp("wellGrid_zWindow").hide();
}
//管道段
function showPoleGrid_aa() {
	var pipeSegmentGrid = Ext.getCmp("pipeSegmentGrid");
	//if (!pipeSegmentGrid.poleGrid_aaWindow) {
		pipeSegmentGrid.poleGrid_aaWindow = new com.increase.cen.poleline.PolelineWindow({
			id : "poleGrid_aaWindow",
			width : 750,
			title : '选择电杆',
			iconCls : 'i-script-window',
			items : [{
				id : "poleGrid_aa",
				height : 350,
				xtype : 'poleGrid_aa'// 窗口里的表单

			}]
		})
	//}
	var store = Ext.getCmp("poleGrid_aa").getStore();
	var loadMarsk = new Ext.LoadMask(document.body, {
		msg : '数据加载中，请稍候......',
		disabled : false,
		store : store
	});
	store.addListener('beforeload', function() {
		loadMarsk.show();
	});
	store.load({
		params : {
			start : 0,
			limit : 10
		}
	});
	pipeSegmentGrid.poleGrid_aaWindow.show();
}
function showPoleGrid_zz() {
	var pipeSegmentGrid = Ext.getCmp("pipeSegmentGrid");
	//if (!pipeSegmentGrid.poleGrid_zzWindow) {
		pipeSegmentGrid.poleGrid_zzWindow = new com.increase.cen.poleline.PolelineWindow({
			id : "poleGrid_zzWindow",
			width : 750,
			title : '选择电杆',
			iconCls : 'i-script-window',
			items : [{
				id : "poleGrid_zz",
				height : 350,
				xtype : 'poleGrid_zz'// 窗口里的表单

			}]
		})
	//}
	var store = Ext.getCmp("poleGrid_zz").getStore();
	var loadMarsk = new Ext.LoadMask(document.body, {
		msg : '数据加载中，请稍候......',
		disabled : false,
		store : store
	});
	store.addListener('beforeload', function() {
		loadMarsk.show();
	});
	store.load({
		params : {
			start : 0,
			limit : 10
		}
	});
	pipeSegmentGrid.poleGrid_zzWindow.show();
}
function selectPole_aa(rowIndex){
	var store = Ext.getCmp("poleGrid_aa").getStore();
	var pole = store.data.items[rowIndex].json;
	
	var end_poleid = "";
	var type = "";
	if(Ext.getCmp("addEndDeviceId")){
		end_poleid= Ext.getCmp("addEndDeviceId").getValue();
		type = Ext.getCmp("addPipeSegmentType").getValue();
	}else if(Ext.getCmp("modifyEndDeviceId")){
		end_poleid= Ext.getCmp("modifyEndDeviceId").getValue();
		type = Ext.getCmp("modifyPipeSegmentType").getValue();
	}
	if (pole.poleId == end_poleid) {
		Ext.Msg.show({
			title : '提示',
			width : 300,
			msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
					+ '起始设施与终止设施不能相同!',
			buttons : {
				ok : "确定"
			}
		})
		return false;
	}
	var pipename="";
	if(Ext.getCmp("addPipeName")){
		pipename= Ext.getCmp("addPipeName").getValue();
	}else if(Ext.getCmp("modifyPipeName")){
		pipename= Ext.getCmp("modifyPipeName").getValue();
	}
	if(Ext.getCmp("addStartDeviceId")){
		Ext.getCmp("addStartDeviceId").setValue(pole.poleId);
		Ext.getCmp("addStartDeviceName").setValue(pole.poleNameSub);
		if(type=="3"){
			Ext.getCmp("addPoleId").setValue(pole.poleId);
			Ext.getCmp("addPoleName").setValue(pole.poleNameSub);
		}
		var endname=Ext.getCmp("addEndDeviceName").getValue();
		if(pipename==""){
			Ext.getCmp("addPipeSegmentName").setValue(pole.poleNameSub+"_"+endname);
		}else{
			Ext.getCmp("addPipeSegmentName").setValue(pipename+"("+pole.poleNameSub+"_"+endname+")");
		}
	}else if(Ext.getCmp("modifyStartDeviceId")){
		Ext.getCmp("modifyStartDeviceId").setValue(pole.poleId);
		Ext.getCmp("modifyStartDeviceName").setValue(pole.poleNameSub);
		if(type=="3"){
			Ext.getCmp("modifyPoleId").setValue(pole.poleId);
			Ext.getCmp("modifyPoleName").setValue(pole.poleNameSub);
		}
		var endname=Ext.getCmp("modifyEndDeviceName").getValue();
		if(pipename==""){
			Ext.getCmp("modifyPipeSegmentName").setValue(pole.poleNameSub+"_"+endname);
		}else{
			Ext.getCmp("modifyPipeSegmentName").setValue(pipename+"("+pole.poleNameSub+"_"+endname+")");
		}
	}
	Ext.getCmp("poleGrid_aaWindow").hide();
}
function selectPole_zz(rowIndex){
	var store = Ext.getCmp("poleGrid_zz").getStore();
	var pole = store.data.items[rowIndex].json;
	
	var start_poleid = "";
	var type = "";
	if(Ext.getCmp("addStartDeviceId")){
		start_poleid= Ext.getCmp("addStartDeviceId").getValue();
		type = Ext.getCmp("addPipeSegmentType").getValue();
	}else if(Ext.getCmp("modifyStartDeviceId")){
		start_poleid= Ext.getCmp("modifyStartDeviceId").getValue();
		type = Ext.getCmp("modifyPipeSegmentType").getValue();
	}
	if (pole.poleId == start_poleid) {
		Ext.Msg.show({
			title : '提示',
			width : 300,
			msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
					+ '起始设施与终止设施不能相同!',
			buttons : {
				ok : "确定"
			}
		})
		return false;
	}
	var pipename="";
	if(Ext.getCmp("addPipeName")){
		pipename= Ext.getCmp("addPipeName").getValue();
	}else if(Ext.getCmp("modifyPipeName")){
		pipename= Ext.getCmp("modifyPipeName").getValue();
	}
	if(Ext.getCmp("addEndDeviceId")){
		Ext.getCmp("addEndDeviceId").setValue(pole.poleId);
		Ext.getCmp("addEndDeviceName").setValue(pole.poleNameSub);
		if(type=="2"){
			Ext.getCmp("addPoleId").setValue(pole.poleId);
			Ext.getCmp("addPoleName").setValue(pole.poleNameSub);
		}
		var startname=Ext.getCmp("addStartDeviceName").getValue();
		if(pipename==""){
			Ext.getCmp("addPipeSegmentName").setValue(startname+"_"+pole.poleNameSub);
		}else{
			Ext.getCmp("addPipeSegmentName").setValue(pipename+"("+startname+"_"+pole.poleNameSub+")");
		}
	}else if(Ext.getCmp("modifyEndDeviceId")){
		Ext.getCmp("modifyEndDeviceId").setValue(pole.poleId);
		Ext.getCmp("modifyEndDeviceName").setValue(pole.poleNameSub);
		if(type=="2"){
			Ext.getCmp("modifyPoleId").setValue(pole.poleId);
			Ext.getCmp("modifyPoleName").setValue(pole.poleNameSub);
		}
		var startname=Ext.getCmp("modifyStartDeviceName").getValue();
		if(pipename==""){
			Ext.getCmp("modifyPipeSegmentName").setValue(startname+"_"+pole.poleNameSub);
		}else{
			Ext.getCmp("modifyPipeSegmentName").setValue(pipename+"("+startname+"_"+pole.poleNameSub+")");
		}
	}
	Ext.getCmp("poleGrid_zzWindow").hide();
}
function showWellGrid_aa() {
	var pipeSegmentGrid = Ext.getCmp("pipeSegmentGrid");
	//if (!pipeSegmentGrid.wellGrid_aaWindow) {
		pipeSegmentGrid.wellGrid_aaWindow = new com.increase.cen.poleline.PolelineWindow({
			id : "wellGrid_aaWindow",
			width : 750,
			title : '选择井',
			iconCls : 'i-script-window',
			items : [{
				id : "wellGrid_aa",
				width : 750,
				height : 350,
				xtype : 'wellGrid_aa'// 窗口里的表单

			}]
		})
	//}
	var store = Ext.getCmp("wellGrid_aa").getStore();
	var loadMarsk = new Ext.LoadMask(document.body, {
		msg : '数据加载中，请稍候......',
		disabled : false,
		store : store
	});
	store.addListener('beforeload', function() {
		loadMarsk.show();
	});
	store.load({
		params : {
			start : 0,
			limit : 10
		}
	});
	pipeSegmentGrid.wellGrid_aaWindow.show();
}
function showWellGrid_zz() {
	var pipeSegmentGrid = Ext.getCmp("pipeSegmentGrid");
	//if (!pipeSegmentGrid.wellGrid_zzWindow) {
		pipeSegmentGrid.wellGrid_zzWindow = new com.increase.cen.poleline.PolelineWindow({
			id : "wellGrid_zzWindow",
			width : 750,
			title : '选择井',
			iconCls : 'i-script-window',
			items : [{
				id : "wellGrid_zz",
				height : 350,
				xtype : 'wellGrid_zz'// 窗口里的表单

			}]
		})
	//}
	var store = Ext.getCmp("wellGrid_zz").getStore();
	var loadMarsk = new Ext.LoadMask(document.body, {
		msg : '数据加载中，请稍候......',
		disabled : false,
		store : store
	});
	store.addListener('beforeload', function() {
		loadMarsk.show();
	});
	store.load({
		params : {
			start : 0,
			limit : 10
		}
	});
	pipeSegmentGrid.wellGrid_zzWindow.show();
}
function selectWell_aa(rowIndex){
	var store = Ext.getCmp("wellGrid_aa").getStore();
	var well = store.data.items[rowIndex].json;
	
	var end_poleid = "";
	var type = "";
	if(Ext.getCmp("addEndDeviceId")){
		end_poleid= Ext.getCmp("addEndDeviceId").getValue();
		type = Ext.getCmp("addPipeSegmentType").getValue();
	}else if(Ext.getCmp("modifyEndDeviceId")){
		end_poleid= Ext.getCmp("modifyEndDeviceId").getValue();
		type = Ext.getCmp("modifyPipeSegmentType").getValue();
	}
	if (well.wellId == end_poleid && end_type==1) {
		Ext.Msg.show({
			title : '提示',
			width : 300,
			msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
					+ '起始设施与终止设施不能相同!',
			buttons : {
				ok : "确定"
			}
		})
		return false;
	}
	var pipename="";
	if(Ext.getCmp("addPipeName")){
		pipename= Ext.getCmp("addPipeName").getValue();
	}else if(Ext.getCmp("modifyPipeName")){
		pipename= Ext.getCmp("modifyPipeName").getValue();
	}
	//用于判断选择面时是给起始还是终止赋值 1为添加起始  2为修改起始  3为添加终止  4为修改终止
	var addAndModify='';
	if(Ext.getCmp("addStartDeviceId")){
		Ext.getCmp("addStartDeviceId").setValue(well.wellId);
		Ext.getCmp("addStartDeviceName").setValue(well.wellNameSub);
		addAndModify='1';
		if(type=="2"){
			Ext.getCmp("addWellId").setValue(well.wellId);
			Ext.getCmp("addWellName").setValue(well.wellNameSub);
		}
		var endname=Ext.getCmp("addEndDeviceName").getValue();
		if(pipename==""){
			Ext.getCmp("addPipeSegmentName").setValue(well.wellNameSub+"_"+endname);
		}else{
			Ext.getCmp("addPipeSegmentName").setValue(pipename+"("+well.wellNameSub+"_"+endname+")");
		}
	}else if(Ext.getCmp("modifyStartDeviceId")){
		Ext.getCmp("modifyStartDeviceId").setValue(well.wellId);
		Ext.getCmp("modifyStartDeviceName").setValue(well.wellNameSub);
		addAndModify='2';
		if(type=="2"){
			Ext.getCmp("modifyWellId").setValue(well.wellId);
			Ext.getCmp("modifyWellName").setValue(well.wellNameSub);
		}
		var endname=Ext.getCmp("modifyEndDeviceName").getValue();
		if(pipename==""){
			Ext.getCmp("modifyPipeSegmentName").setValue(well.wellNameSub+"_"+endname);
		}else{
			Ext.getCmp("modifyPipeSegmentName").setValue(pipename+"("+well.wellNameSub+"_"+endname+")");
		}
	}
	Ext.getCmp("wellGrid_aaWindow").hide();
	if(type=="1"){
		selectWellFace(well,addAndModify);
	}
}

function selectWellFace(well,addAndModify){
	var pipeSegmentGrid = Ext.getCmp("pipeSegmentGrid");
	//if (!pipeSegmentGrid.wellFace_aWindow) {
		pipeSegmentGrid.wellFace_aWindow = new com.increase.cen.poleline.PolelineWindow({
			id : "wellFace_aWindow",
			title : '选择井立面',
			iconCls : 'i-script-window',
			items : [{
				id : "wellFace",
				height : 410,
				xtype : 'wellFace'// 窗口里的表单

			}]
		})
	//}
	pipeSegmentGrid.wellFace_aWindow.show();
	//添加长按事件
	ondown(well.wellmap);
	Ext.getCmp("faceno").setValue(addAndModify);
	Ext.getCmp("faceidss").setValue(well.faceids);
	var wellmap = well.wellmap;
	if(wellmap!='' && wellmap!=null){
		var strarr=wellmap.split('');
		for(var i=0;i<strarr.length;i++){
			if(strarr[i]!='0'){
				var fid="ox_"+(i+1);
				Ext.getCmp(fid).setText(strarr[i]);
			}
		}
	}
	Ext.Ajax.request({
		url:'pipe!facerelation.action',
		method:'post',
		params : {
			'wellInfoBean.wellId' : well.wellId
		},
		success : function(response) {
			var json = Ext.util.JSON.decode(response.responseText);
			var map1=json.wellInfoBean.hrwellmap;
			var map11=map1.split('');
			for(var i=0;i<map11.length;i++){
				if(map11[i]!='0'){
					var fids="ox_"+(i+1);
					Ext.getCmp(fids).setDisabled(true);
					//Ext.getCmp(fids).setText("<span ext:qtip='"+此立面已存在关系+"'>"+Ext.getCmp(fids).getText()+"</span>");
					//ToolTip.setToolTip(fids,'此立面已存在关系');
				}
			}
		}
	})
}
function selectWell_zz(rowIndex){
	var store = Ext.getCmp("wellGrid_zz").getStore();
	var well = store.data.items[rowIndex].json;
	
	var start_poleid = "";
	var type = "";
	if(Ext.getCmp("addStartDeviceId")){
		start_poleid= Ext.getCmp("addStartDeviceId").getValue();
		type = Ext.getCmp("addPipeSegmentType").getValue();
	}else if(Ext.getCmp("modifyStartDeviceId")){
		start_poleid= Ext.getCmp("modifyStartDeviceId").getValue();
		type = Ext.getCmp("modifyPipeSegmentType").getValue();
	}
	if (well.wellId == start_poleid) {
		Ext.Msg.show({
			title : '提示',
			width : 300,
			msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
					+ '起始设施与终止设施不能相同!',
			buttons : {
				ok : "确定"
			}
		})
		return false;
	}
	var pipename="";
	if(Ext.getCmp("addPipeName")){
		pipename= Ext.getCmp("addPipeName").getValue();
	}else if(Ext.getCmp("modifyPipeName")){
		pipename= Ext.getCmp("modifyPipeName").getValue();
	}
	var addAndModify='';
	if(Ext.getCmp("addEndDeviceId")){
		Ext.getCmp("addEndDeviceId").setValue(well.wellId);
		Ext.getCmp("addEndDeviceName").setValue(well.wellNameSub);
		addAndModify='3';
		if(type=="3"){
			Ext.getCmp("addWellId").setValue(well.wellId);
			Ext.getCmp("addWellName").setValue(well.wellNameSub);
		}
		var startname=Ext.getCmp("addStartDeviceName").getValue();
		if(pipename==""){
			Ext.getCmp("addPipeSegmentName").setValue(startname+"_"+well.wellNameSub);
		}else{
			Ext.getCmp("addPipeSegmentName").setValue(pipename+"("+startname+"_"+well.wellNameSub+")");
		}
	}else if(Ext.getCmp("modifyEndDeviceId")){
		Ext.getCmp("modifyEndDeviceId").setValue(well.wellId);
		Ext.getCmp("modifyEndDeviceName").setValue(well.wellNameSub);
		addAndModify='4';
		if(type=="3"){
			Ext.getCmp("modifyWellId").setValue(well.wellId);
			Ext.getCmp("modifyWellName").setValue(well.wellNameSub);
		}
		var startname=Ext.getCmp("modifyStartDeviceName").getValue();
		if(pipename==""){
			Ext.getCmp("modifyPipeSegmentName").setValue(startname+"_"+well.wellNameSub);
		}else{
			Ext.getCmp("modifyPipeSegmentName").setValue(pipename+"("+startname+"_"+well.wellNameSub+")");
		}
	}
	Ext.getCmp("wellGrid_zzWindow").hide();
	if(type=="1"){
		selectWellFace(well,addAndModify);
	}
}
function showPipeGrid(){
	var pipeSegmentGrid = Ext.getCmp("pipeSegmentGrid");
	//if (!pipeSegmentGrid.pipGridWindow) {
		pipeSegmentGrid.pipGridWindow = new com.increase.cen.poleline.PolelineWindow({
			id : "pipGridWindow",
			width : 880,
			title : '选择杆路',
			iconCls : 'i-script-window',
			items : [{
				id : "pipGrid",
				height : 350,
				xtype : 'pipGrid'// 窗口里的表单

			}]
		})
	//}
	var store = Ext.getCmp("pipGrid").getStore();
	var loadMarsk = new Ext.LoadMask(document.body, {
		msg : '数据加载中，请稍候......',
		disabled : false,
		store : store
	});
	store.addListener('beforeload', function() {
		loadMarsk.show();
	});
	store.load({
		params : {
			start : 0,
			limit : 10
		}
	});
	pipeSegmentGrid.pipGridWindow.show();
}
function selectPipe(rowIndex){
	var store = Ext.getCmp("pipGrid").getStore();
	var pipe = store.data.items[rowIndex].json;
	
	var start_polename = "";
	var end_polename = "";
	if(Ext.getCmp("addStartDeviceName")){
		start_polename = Ext.getCmp("addStartDeviceName").getValue();
	}else if(Ext.getCmp("modifyStartDeviceName")){
		start_polename = Ext.getCmp("modifyStartDeviceName").getValue();
	}
	if(Ext.getCmp("addEndDeviceName")){
		end_polename= Ext.getCmp("addEndDeviceName").getValue();
	}else if(Ext.getCmp("modifyEndDeviceName")){
		end_polename= Ext.getCmp("modifyEndDeviceName").getValue();
	}
	if(Ext.getCmp("addPipeId")){
		Ext.getCmp("addPipeId").setValue(pipe.pipeId);
		Ext.getCmp("addPipeName").setValue(pipe.pipeName);
		if(start_polename==""&&end_polename==""){
			Ext.getCmp("addPipeSegmentName").setValue(pipe.pipeName);
		}else{
			Ext.getCmp("addPipeSegmentName").setValue(pipe.pipeName+"("+start_polename+"_"+end_polename+")");
		}
		Ext.getCmp("addAreano").setValue(pipe.areano);
		Ext.getCmp("addAreaname").setValue(pipe.areaname);
	}else if(Ext.getCmp("modifyPipeId")){
		Ext.getCmp("modifyPipeId").setValue(pipe.pipeId);
		Ext.getCmp("modifyPipeName").setValue(pipe.pipeName);
		if(start_polename==""&&end_polename==""){
			Ext.getCmp("modifyPipeSegmentName").setValue(pipe.pipeName);
		}else{
			Ext.getCmp("modifyPipeSegmentName").setValue(pipe.pipeName+"("+start_polename+"_"+end_polename+")");
		}
		Ext.getCmp("modifyAreano").setValue(pipe.areano);
		Ext.getCmp("modifyAreaname").setValue(pipe.areaname);
	}
	Ext.getCmp("pipGridWindow").hide();
}
//生成管道
function showPoleGrid_as() {
	var pipeGrid = Ext.getCmp("pipeSegmentGrid");
	//if (!pipeGrid.poleGrid_aWindow) {
		pipeGrid.poleGrid_asWindow = new com.increase.cen.poleline.PolelineWindow({
			id : "poleGrid_asWindow",
			width : 750,
			title : '选择电杆',
			iconCls : 'i-script-window',
			items : [{
				id : "poleGrid_as",
				height : 350,
				xtype : 'poleGrid_as'// 窗口里的表单

			}]
		})
	//}
	var store = Ext.getCmp("poleGrid_as").getStore();
	var loadMarsk = new Ext.LoadMask(document.body, {
		msg : '数据加载中，请稍候......',
		disabled : false,
		store : store
	});
	store.addListener('beforeload', function() {
		loadMarsk.show();
	});
	store.load({
		params : {
			start : 0,
			limit : 10
		}
	});
	pipeGrid.poleGrid_asWindow.show();
}
function showPoleGrid_zs() {
	var pipeGrid = Ext.getCmp("pipeSegmentGrid");
	//if (!pipeGrid.poleGrid_zWindow) {
		pipeGrid.poleGrid_zsWindow = new com.increase.cen.poleline.PolelineWindow({
			id : "poleGrid_zsWindow",
			width : 750,
			title : '选择电杆',
			iconCls : 'i-script-window',
			items : [{
				id : "poleGrid_zs",
				height : 350,
				xtype : 'poleGrid_zs'// 窗口里的表单

			}]
		})
	//}
	var store = Ext.getCmp("poleGrid_zs").getStore();
	var loadMarsk = new Ext.LoadMask(document.body, {
		msg : '数据加载中，请稍候......',
		disabled : false,
		store : store
	});
	store.addListener('beforeload', function() {
		loadMarsk.show();
	});
	store.load({
		params : {
			start : 0,
			limit : 10
		}
	});
	pipeGrid.poleGrid_zsWindow.show();
}
function selectPole_as(rowIndex){
	var store = Ext.getCmp("poleGrid_as").getStore();
	var pole = store.data.items[rowIndex].json;
	
	var end_poleid = Ext.getCmp("mergeEndDeviceId").getValue();
	if (pole.poleId == end_poleid) {
		Ext.Msg.show({
			title : '提示',
			width : 300,
			msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
					+ '起始设施与终止设施不能相同!',
			buttons : {
				ok : "确定"
			}
		})
		return false;
	}
	Ext.getCmp("mergeStartDeviceId").setValue(pole.poleId);
	Ext.getCmp("mergeStartDeviceName").setValue(pole.poleNameSub);
	Ext.getCmp("mergeStartDeviceType").setValue("2");
	Ext.getCmp("poleGrid_asWindow").hide();
}
function selectPole_zs(rowIndex){
	var store = Ext.getCmp("poleGrid_zs").getStore();
	var pole = store.data.items[rowIndex].json;
	
	var start_poleid = Ext.getCmp("mergeStartDeviceId").getValue();
	if (pole.poleId == start_poleid) {
		Ext.Msg.show({
			title : '提示',
			width : 300,
			msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
					+ '起始设施与终止设施不能相同!',
			buttons : {
				ok : "确定"
			}
		})
		return false;
	}
	Ext.getCmp("mergeEndDeviceId").setValue(pole.poleId);
	Ext.getCmp("mergeEndDeviceName").setValue(pole.poleNameSub);
	Ext.getCmp("mergeEndDeviceType").setValue("2");
	Ext.getCmp("poleGrid_zsWindow").hide();
}
function showWellGrid_as() {
	var pipeGrid = Ext.getCmp("pipeSegmentGrid");
	//if (!pipeGrid.wellGrid_aWindow) {
		pipeGrid.wellGrid_asWindow = new com.increase.cen.poleline.PolelineWindow({
			id : "wellGrid_asWindow",
			width : 750,
			title : '选择井',
			iconCls : 'i-script-window',
			items : [{
				id : "wellGrid_as",
				height : 350,
				xtype : 'wellGrid_as'// 窗口里的表单

			}]
		})
	//}
	var store = Ext.getCmp("wellGrid_as").getStore();
	var loadMarsk = new Ext.LoadMask(document.body, {
		msg : '数据加载中，请稍候......',
		disabled : false,
		store : store
	});
	store.addListener('beforeload', function() {
		loadMarsk.show();
	});
	store.load({
		params : {
			start : 0,
			limit : 10
		}
	});
	pipeGrid.wellGrid_asWindow.show();
}
function showWellGrid_zs() {
	var pipeGrid = Ext.getCmp("pipeSegmentGrid");
	//if (!pipeGrid.wellGrid_zWindow) {
		pipeGrid.wellGrid_zsWindow = new com.increase.cen.poleline.PolelineWindow({
			id : "wellGrid_zsWindow",
			width : 750,
			title : '选择井',
			iconCls : 'i-script-window',
			items : [{
				id : "wellGrid_zs",
				height : 350,
				xtype : 'wellGrid_zs'// 窗口里的表单

			}]
		})
	//}
	var store = Ext.getCmp("wellGrid_zs").getStore();
	var loadMarsk = new Ext.LoadMask(document.body, {
		msg : '数据加载中，请稍候......',
		disabled : false,
		store : store
	});
	store.addListener('beforeload', function() {
		loadMarsk.show();
	});
	store.load({
		params : {
			start : 0,
			limit : 10
		}
	});
	pipeGrid.wellGrid_zsWindow.show();
}
function selectWell_as(rowIndex){
	var store = Ext.getCmp("wellGrid_as").getStore();
	var well = store.data.items[rowIndex].json;
	
	var end_poleid = Ext.getCmp("mergeEndDeviceId").getValue();
	if (well.wellId == end_poleid) {
		Ext.Msg.show({
			title : '提示',
			width : 300,
			msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
					+ '起始设施与终止设施不能相同!',
			buttons : {
				ok : "确定"
			}
		})
		return false;
	}
	Ext.getCmp("mergeStartDeviceId").setValue(well.wellId);
	Ext.getCmp("mergeStartDeviceName").setValue(well.wellNameSub);
	Ext.getCmp("mergeStartDeviceType").setValue("1");
	Ext.getCmp("wellGrid_asWindow").hide();
}
function selectWell_zs(rowIndex){
	var store = Ext.getCmp("wellGrid_zs").getStore();
	var well = store.data.items[rowIndex].json;
	
	var start_poleid = Ext.getCmp("mergeStartDeviceId").getValue();
	if (well.wellId == start_poleid) {
		Ext.Msg.show({
			title : '提示',
			width : 300,
			msg : '<img src="imgs/tip_error.png" align="center" hspace="30"/>'
					+ '起始设施与终止设施不能相同!',
			buttons : {
				ok : "确定"
			}
		})
		return false;
	}
	Ext.getCmp("mergeEndDeviceId").setValue(well.wellId);
	Ext.getCmp("mergeEndDeviceName").setValue(well.wellNameSub);
	Ext.getCmp("mergeEndDeviceType").setValue("1");
	Ext.getCmp("wellGrid_zsWindow").hide();
}