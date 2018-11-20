function showEqutGrid_a() {
	var bindingFiberForm = Ext.getCmp("routeGrid");
	if (!bindingFiberForm.equtGrid_aWindow) {
		bindingFiberForm.equtGrid_aWindow = new com.increase.cen.route.routeWindow({
			id : "equtGrid_aWindow",
			height : 400,
			width : 1000,
			title : '选择设备',
			iconCls : 'i-script-window',
			items : [{
				// id : "equtGrid_a",
				height : 350,
				xtype : 'equtGrid_a'// 窗口里的表单

			}]
		})
	}
	
	var store = Ext.getCmp("equtGrid_a").getStore();
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
			limit : limit
		}
	});
	bindingFiberForm.equtGrid_aWindow.show();
}

function showEqutGrid_z() {
	var bindingFiberForm = Ext.getCmp("routeGrid");
	if (!bindingFiberForm.equtGrid_zWindow) {
		bindingFiberForm.equtGrid_zWindow = new com.increase.cen.route.routeWindow({
			id : "equtGrid_zWindow",
			height : 400,
			width : 1000,
			title : '选择设备',
			iconCls : 'i-script-window',
			items : [{
				// id : "equtGrid_a",
				height : 350,
				xtype : 'equtGrid_z'// 窗口里的表单

			}]
		})
	}
	var store = Ext.getCmp("equtGrid_z").getStore();
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
			limit : limit
		}
	});
	bindingFiberForm.equtGrid_zWindow.show();
}

function selectEqut_a(rowIndex){
	var store = Ext.getCmp("equtGrid_a").getStore();
	var equt = store.data.items[rowIndex].json;
	
	var end_eid = "";
	if(Ext.getCmp("newCable.endeid")){
		end_eid= Ext.getCmp("newCable.endeid").getValue();
	}else if(Ext.getCmp("mergeCable.endeid")){
		end_eid= Ext.getCmp("mergeCable.endeid").getValue();
	}else{
		end_eid= Ext.getCmp("updateCable.endeid").getValue();
	}
	if (equt.eid == end_eid) {
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
	
	if(Ext.getCmp("newCable.starteid")){
		Ext.getCmp("newCable.starteid").setValue(equt.eid);
		Ext.getCmp("newCable.startDeviceName").setValue(equt.ename);
	}else if(Ext.getCmp("mergeCable.starteid")){
		Ext.getCmp("mergeCable.starteid").setValue(equt.eid);
		Ext.getCmp("mergeCable.startDeviceName").setValue(equt.ename);
	}else{
		Ext.getCmp("updateCable.starteid").setValue(equt.eid);
		Ext.getCmp("updateCable.startDeviceName").setValue(equt.ename);
	}
	Ext.getCmp("equtGrid_aWindow").hide();
	
}

function selectEqut_z(rowIndex){
	var store = Ext.getCmp("equtGrid_z").getStore();
	var equt = store.data.items[rowIndex].json;
	var start_eid = "";
	if(Ext.getCmp("newCable.starteid")){
		start_eid= Ext.getCmp("newCable.starteid").getValue();
	}else if(Ext.getCmp("mergeCable.starteid")){
		start_eid= Ext.getCmp("mergeCable.starteid").getValue();
	}else{
		start_eid= Ext.getCmp("updateCable.starteid").getValue();
	}
	if (equt.eid == start_eid) {
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
	if(Ext.getCmp("newCable.endeid")){
		Ext.getCmp("newCable.endeid").setValue(equt.eid);
		Ext.getCmp("newCable.endDeviceName").setValue(equt.ename);
	}else if(Ext.getCmp("mergeCable.endeid")){
		Ext.getCmp("mergeCable.endeid").setValue(equt.eid);
		Ext.getCmp("mergeCable.endDeviceName").setValue(equt.ename);
	}else{
		Ext.getCmp("updateCable.endeid").setValue(equt.eid);
		Ext.getCmp("updateCable.endDeviceName").setValue(equt.ename);
	}
	Ext.getCmp("equtGrid_zWindow").hide();
}