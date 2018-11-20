var zTreeObject = null;
var isTreeShow = false;
function assignedTeam(){
	if(isTreeShow){
		$("#treeDiv").hide(500);
		isTreeShow = false;
	}else{
		$("#treeDiv").show(500);
		isTreeShow = true;
	}
}
var AsyncSetting = {
	async:{
		enable:true,
		url:"ruleEditAction/findTableData.ilf"
	},
	data:{
		simpleData:{
		   enable:true
		}
	},
    check:{
		enable:false
    },
    callback:{
		onClick:function(){
			var TreeObject = $.fn.zTree.getZTreeObj("tableTree");
			var SelectedObjects = TreeObject.getSelectedNodes();
			if(SelectedObjects.length>0){
				var nodeObject = SelectedObjects[0];
				if(nodeObject.leaf){
					//1.[编号]Table信息渲染
					$("#hiddenOfGlassTable").val(nodeObject.id);
					//2.[名称]Table信息渲染
					$("#checkedGlassTable").val(nodeObject.name);
					//3.[显隐]隐藏资源树
					isTreeShow = false;
					$("#treeDiv").hide(500);
					//4.[查询]查询参照表所包含的字段信息
					searchGlassColumn();
				}
			}
		}
    }
};
function initResTree(){
	$.post("ruleEditAction/findTableData.ilf",{},function(result){
		zTreeObject = $.fn.zTree.init($("#tableTree"),AsyncSetting,result);
    });
}