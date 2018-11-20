function redirectToTerminalPanel(eid){
	 parent.parent.Ext.getCmp("pnCenterframe").body
							.update("<iframe id='domainTreeIframe' src='pages/equt/equtManage.jsp?eid="+eid+"&fromOther=1' scrolling='yes' width='100%' height='100%' frameborder='no' border='0' marginwidth='0' marginheight='0' align='center'></iframe>");
}

function redirectToOrder(idstr){
	parent.parent.Ext.getCmp("pnCenterframe").body
							.update("<iframe id='orderCenter' src='pages/workorder/orderManage.jsp?idstr="+idstr+"&fromOther=1' scrolling='yes' width='100%' height='100%' frameborder='no' border='0' marginwidth='0' marginheight='0' align='center'></iframe>");
}

function redirectToNewOrder(idstr){
	parent.parent.Ext.getCmp("pnCenterframe").body
							.update("<iframe id='orderCenter' src='pages/workorder/orderManage.jsp?idstr="+idstr+"&fromOther=2' scrolling='yes' width='100%' height='100%' frameborder='no' border='0' marginwidth='0' marginheight='0' align='center'></iframe>");
}
function redirectToWellFacade(wellId){
	  parent.parent.Ext.getCmp("pnCenterframe").body
							.update("<iframe id='centerDiv' src='pages/pipe/well/wellFacadeManage.jsp?wellId="+wellId+"&fromOther=1' scrolling='yes' width='100%' height='100%' frameborder='no' border='0' marginwidth='0' marginheight='0' align='center'></iframe>");
}