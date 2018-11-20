/*[初始化]：初始化右侧界面*/
function initRightPanel(){
	editingRule = null;
	//1.清除关联键缓存
	connectedColumn = new Array();
	//2.刷新参照模型的数据
	$("#checkedGlassTable").val("");
	$("#glassColumnKey").val("");
	$("#hiddenOfGlassTable").val("-1");
	searchGlassColumn();
	//3.刷新属性列表的关联状态
	if(acheItems!=null){
		var itemObjs = acheItems.valueSet();
		if(itemObjs.length>0){
			for(var i=0;i<itemObjs.length;i++){
				var $obj = itemObjs[i];
				if(document.getElementById("ADIV"+$obj["ID"])!=null){
					$("#ADIV"+$obj["ID"]).html("<input type='checkbox' value='"+$obj["ID"]+"' name='relateColum' onchange='javascript:changeRelate(this.checked,this.value);'></input>");
				}
			}
		}
	}
	//4.解封表达式输入框
	$("#expressionInput").attr("disabled",false);
	//5.展示已编辑列表
	$(".rightPanel").hide();
	$("#editedTable").show();
}
/*[查询]：查询当前模型信息并进行渲染*/
function initTbInfo(){
	var tableId = $("#hiddenOfBelongTable").val();
	$.ajax({
		url:"ruleEditAction/findOneTable.ilf",
		async:false,
		type:"POST",
		data:"tableId="+tableId,
		dataType:"json",
		timeout:10000, 
		success:function(textStatus){
			if(textStatus["SUCCESS"]){				
				$("#tableNameTd").html(textStatus["DB_NAME"]+" >> "+textStatus["TABLE_ALIAS"]);
				//$("#tableNameFont").html(textStatus["DB_NAME"]+" >> "+textStatus["TABLE_ALIAS"]);
				$("#tableNameFont").html(">> "+textStatus["TABLE_ALIAS"]);
			}
		},
		error:function(){}
	});
}
/*[查询]：如果是修改，则渲染界面信息*/
function initIfUpdate(){
	var ruleId = $("#hiddenOfRuleCode").val();
	if(parseInt(ruleId)!=-1){
		$.ajax({
			url:"ruleEditAction/findOneDetail.ilf",
			async:false,
			type:"POST",
			data:"ruleId="+ruleId,
			dataType:"json",
			timeout:10000, 
			success:function(obj){
				ruleObj = obj;				
				//1.渲染基本信息[规则名称、规则分类、核查目的、解决建议]
				$("#ruleNameInput").val(ruleObj["ruleName"]);
				$("#classTypeSelect").val(ruleObj["className"]);
				$("#ruleDescInput").val(ruleObj["ruleDesc"]);
				$("#howToRecover").val(ruleObj["recovery"]);
				$("#ruleVersionInput").val(ruleObj["ruleVersion"]);
				//2.渲染调度及是否启用
				$("#isUsingSelection").val(ruleObj["isUsing"]);
				$("#gradeSelection").val(ruleObj["ruleGrade"]);
				//3.渲染调度详情
				var quartzObj = ruleObj["quartzDetail"];
				execCircleSetting(true,"execCircle",quartzObj["CIRCLE_TYPE"]);
				if(quartzObj["CIRCLE_TYPE"]!="无"){
					$("#hourOfDay").val(quartzObj["HOUR_VAR"]);
					$("#minuteOfHour").val(quartzObj["SECOND_VAR"]);
					$("#secondOfMinute").val(quartzObj["MINUTE_VAR"]);
					if(quartzObj["CIRCLE_TYPE"]=="周"){
						$("#chooseDayOfWeek").val(quartzObj["DAY_OF_WEEK"]);
					}else if(quartzObj["CIRCLE_TYPE"]=="月"){
						$("#chooseDayOfMonth").val(quartzObj["DAY_OF_MONTH"]);
					}			
				}	
				document.getElementById("configConButton").click();
				document.getElementById("configConButton").click();
				//4.渲染规则详情
				var $items = ruleObj["editedItems"];
				if($items.length>0){
					if(acheObject==null){
						acheObject = new HashMap();
					}
					for(var i=0;i<$items.length;i++){
						var $item = $items[i];
						acheObject.put($item["expressKey"],$item);
					}
				}
				initEditedTable();
			},
			error:function(){
				window.wxc.xcConfirm("测试失败.",window.wxc.xcConfirm.typeEnum.error);
			}
		});	
	}
}