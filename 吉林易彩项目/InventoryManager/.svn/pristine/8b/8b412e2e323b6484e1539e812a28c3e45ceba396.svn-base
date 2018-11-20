function showPoleGrid_a() {
	var poleLineGrid = Ext.getCmp("poleLineGrid");
	//if (!poleLineGrid.poleGrid_aWindow) {
		poleLineGrid.poleGrid_aWindow = new com.increase.cen.poleline.PolelineWindow({
			id : "poleGrid_aWindow",
			width : 800,
			title : '选择电杆',
			iconCls : 'i-script-window',
			items : [{
				id : "poleGrid_a",
				height : 370,
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
	poleLineGrid.poleGrid_aWindow.show();
}
function showPoleGrid_z() {
	var poleLineGrid = Ext.getCmp("poleLineGrid");
	//if (!poleLineGrid.poleGrid_zWindow) {
		poleLineGrid.poleGrid_zWindow = new com.increase.cen.poleline.PolelineWindow({
			id : "poleGrid_zWindow",
			width : 800,
			title : '选择电杆',
			iconCls : 'i-script-window',
			items : [{
				id : "poleGrid_z",
				height : 370,
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
	poleLineGrid.poleGrid_zWindow.show();
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
	}else if(Ext.getCmp("modifyStartDeviceId")){
		Ext.getCmp("modifyStartDeviceId").setValue(pole.poleId);
		Ext.getCmp("modifyStartDeviceName").setValue(pole.poleNameSub);
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
	}else if(Ext.getCmp("modifyEndDeviceId")){
		Ext.getCmp("modifyEndDeviceId").setValue(pole.poleId);
		Ext.getCmp("modifyEndDeviceName").setValue(pole.poleNameSub);
	}
	Ext.getCmp("poleGrid_zWindow").hide();
}
//杆路段时选择电杆的方法
function showPoleGrid_aa() {
	var poleLSGrid = Ext.getCmp("poleLSGrid");
	//if (!poleLSGrid.poleGrid_aaWindow) {
		poleLSGrid.poleGrid_aaWindow = new com.increase.cen.poleline.PolelineWindow({
			id : "poleGrid_aaWindow",
			width : 800,
			title : '选择电杆',
			iconCls : 'i-script-window',
			items : [{
				id : "poleGrid_aa",
				height : 370,
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
	poleLSGrid.poleGrid_aaWindow.show();
}
function showPoleGrid_zz() {
	var poleLSGrid = Ext.getCmp("poleLSGrid");
	//if (!poleLSGrid.poleGrid_zzWindow) {
		poleLSGrid.poleGrid_zzWindow = new com.increase.cen.poleline.PolelineWindow({
			id : "poleGrid_zzWindow",
			width : 800,
			title : '选择电杆',
			iconCls : 'i-script-window',
			items : [{
				id : "poleGrid_zz",
				height : 370,
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
	poleLSGrid.poleGrid_zzWindow.show();
}
function selectPole_aa(rowIndex){
	var store = Ext.getCmp("poleGrid_aa").getStore();
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
	var polelinename="";
	if(Ext.getCmp("addPoleLineName")){
		polelinename= Ext.getCmp("addPoleLineName").getValue();
	}else if(Ext.getCmp("modifyPoleLineName")){
		polelinename= Ext.getCmp("modifyPoleLineName").getValue();
	}
	if(Ext.getCmp("addStartDeviceId")){
		Ext.getCmp("addStartDeviceId").setValue(pole.poleId);
		Ext.getCmp("addStartDeviceName").setValue(pole.poleNameSub);
		var endname=Ext.getCmp("addEndDeviceName").getValue();
		if(polelinename==""){
			Ext.getCmp("addPoleLineSegmentName").setValue(pole.poleNameSub+"_"+endname);
		}else{
			Ext.getCmp("addPoleLineSegmentName").setValue(polelinename+"("+pole.poleNameSub+"_"+endname+")");
		}
	}else if(Ext.getCmp("modifyStartDeviceId")){
		Ext.getCmp("modifyStartDeviceId").setValue(pole.poleId);
		Ext.getCmp("modifyStartDeviceName").setValue(pole.poleNameSub);
		var endname=Ext.getCmp("modifyEndDeviceName").getValue();
		if(polelinename==""){
			Ext.getCmp("modifyPoleLineSegmentName").setValue(pole.poleNameSub+"_"+endname);
		}else{
			Ext.getCmp("modifyPoleLineSegmentName").setValue(polelinename+"("+pole.poleNameSub+"_"+endname+")");
		}
	}
	Ext.getCmp("poleGrid_aaWindow").hide();
}
function selectPole_zz(rowIndex){
	var store = Ext.getCmp("poleGrid_zz").getStore();
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
	var polelinename="";
	if(Ext.getCmp("addPoleLineName")){
		polelinename= Ext.getCmp("addPoleLineName").getValue();
	}else if(Ext.getCmp("modifyPoleLineName")){
		polelinename= Ext.getCmp("modifyPoleLineName").getValue();
	}
	if(Ext.getCmp("addEndDeviceId")){
		Ext.getCmp("addEndDeviceId").setValue(pole.poleId);
		Ext.getCmp("addEndDeviceName").setValue(pole.poleNameSub);
		var startname=Ext.getCmp("addStartDeviceName").getValue();
		if(polelinename==""){
			Ext.getCmp("addPoleLineSegmentName").setValue(startname+"_"+pole.poleNameSub);
		}else{
			Ext.getCmp("addPoleLineSegmentName").setValue(polelinename+"("+startname+"_"+pole.poleNameSub+")");
		}
	}else if(Ext.getCmp("modifyEndDeviceId")){
		Ext.getCmp("modifyEndDeviceId").setValue(pole.poleId);
		Ext.getCmp("modifyEndDeviceName").setValue(pole.poleNameSub);
		var startname=Ext.getCmp("modifyStartDeviceName").getValue();
		if(polelinename==""){
			Ext.getCmp("modifyPoleLineSegmentName").setValue(startname+"_"+pole.poleNameSub);
		}else{
			Ext.getCmp("modifyPoleLineSegmentName").setValue(polelinename+"("+startname+"_"+pole.poleNameSub+")");
		}
	}
	Ext.getCmp("poleGrid_zzWindow").hide();
}
function showPoleLineGrid(){
	var poleLSGrid = Ext.getCmp("poleLSGrid");
	//if (!poleLSGrid.polelineGridWindow) {
		poleLSGrid.polelineGridWindow = new com.increase.cen.poleline.PolelineWindow({
			id : "polelineGridWindow",
			width : 800,
			title : '选择杆路',
			iconCls : 'i-script-window',
			items : [{
				id : "polelineGrid",
				height : 370,
				xtype : 'polelineGrid'// 窗口里的表单

			}]
		})
	//}
	var store = Ext.getCmp("polelineGrid").getStore();
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
	poleLSGrid.polelineGridWindow.show();
}
function selectPoleline(rowIndex){
	var store = Ext.getCmp("polelineGrid").getStore();
	var poleline = store.data.items[rowIndex].json;
	
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
	if(Ext.getCmp("addPoleLineId")){
		Ext.getCmp("addPoleLineId").setValue(poleline.poleLineId);
		Ext.getCmp("addPoleLineName").setValue(poleline.poleLineName);
		if(start_polename==""&&end_polename==""){
			Ext.getCmp("addPoleLineSegmentName").setValue(poleline.poleLineName);
		}else{
			Ext.getCmp("addPoleLineSegmentName").setValue(poleline.poleLineName+"("+start_polename+"_"+end_polename+")");
		}
		Ext.getCmp("addAreano").setValue(poleline.areano);
		Ext.getCmp("addAreaname").setValue(poleline.areaname);
	}else if(Ext.getCmp("modifyPoleLineId")){
		Ext.getCmp("modifyPoleLineId").setValue(poleline.poleLineId);
		Ext.getCmp("modifyPoleLineName").setValue(poleline.poleLineName);
		if(start_polename==""&&end_polename==""){
			Ext.getCmp("modifyPoleLineSegmentName").setValue(poleline.poleLineName);
		}else{
			Ext.getCmp("modifyPoleLineSegmentName").setValue(poleline.poleLineName+"("+start_polename+"_"+end_polename+")");
		}
		Ext.getCmp("modifyAreano").setValue(poleline.areano);
		Ext.getCmp("modifyAreaname").setValue(poleline.areaname);
	}
	Ext.getCmp("polelineGridWindow").hide();
}
