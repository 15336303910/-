var flowNodes = [{
	name:"区域经理发起外验",
	code:"1001",
	order:1,
	isRejectAble:false
},{
	name:"区域运营商审核",
	code:"1002",
	order:2,
	isRejectAble:false,
	rejectToOrder:1
},{
	name:"市运营商审核",
	code:"1003",
	order:3,
	isRejectAble:true,
	rejectToOrder:1
},{
	name:"市运发客户经理审核",
	code:"1004",
	order:4,
	isRejectAble:true,
	rejectToOrder:1
},{
	name:"市建设部项目经理审核",
	code:"1005",
	order:5,
	isRejectAble:true,
	rejectToOrder:1
}];
function getNodeOfFlowByOrder(orderNumber){
	var resultObj = null;
	for(var i=0;i<flowNodes.length;i++){
		var flowNode = flowNodes[i];
		if(flowNode["order"]==orderNumber){
			resultObj = flowNode;
			break;
		}
	}
	return resultObj;
}