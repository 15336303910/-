/*
 * 	type:HashMap
 * 	
 * 	key:timestamp	value:Object
 * 
 * */
var acheObject = null;
/*
 * 	双击已编辑的记录：时间戳
 * 
 * */
var editingRule = null;
/*
 * 	[单表]保存单表规则至：acheObject<TimeKey,Object>
 * 
 * */
function doSaveLocal(){
	if(acheObject==null){
		acheObject = new HashMap();
	}
	/*[校验]是否选择了核查字段*/
	var checkedColumn = getCheckedValue("columnSelect");
	if(checkedColumn==null){
		window.wxc.xcConfirm("请选择核查字段.",window.wxc.xcConfirm.typeEnum.info);
		return;
	}
	/*[校验]是否已编辑表达式*/
	var $value = $("#expressionInput").val();
	if($value==""){
		window.wxc.xcConfirm("请输入表达式.",window.wxc.xcConfirm.typeEnum.info);
		return;
	}	
	if(editingRule!=null){
		saveObject = acheObject.remove(editingRule);
		editingRule = null;
	}
	var checkboxes = document.getElementsByName("columnSelect");
	if(checkboxes!=null && checkboxes.length>0){
		var currentMillion = 1;
		for(var i=0;i<checkboxes.length;i++){
			var $checkbox = checkboxes[i];
			if($checkbox.checked){
				var checkedValue = $checkbox.value;
				var checkedObj = acheItems.get(checkedValue);
				var saveObject = new Object();
				//1.规则类型[基础规则核查、关联规则核查]
				saveObject["auditType"] = "基础规则核查";
				//2.待核查属性[编号、名称]
				saveObject["columnCode"] = checkedValue;
				saveObject["columnName"] = checkedObj["COLUMN_NAME"];
				//3.是否过滤空值
				var $isFilter = getCheckedValue("isFilterNull");
				if($isFilter!=null && $isFilter!=""){
					saveObject["isFilter"] = true;
				}else{
					saveObject["isFilter"] = false;
				}
				//4.表达式
				saveObject["expression"] = $value;
				//5.缓存结果.
				saveObject["expressKey"] = getCurrentTime(null)+currentMillion;
				acheObject.put(saveObject["expressKey"],saveObject);
				currentMillion++;
			}
		}	
	}
	if(ruleObj!=null){
		ruleObj["editedItems"] = acheObject.valueSet();
	}
	unCheckAllBoxes("columnSelect");
	initEditedTable();
	$("#expressionInput").val("");
}
/*
 * 	[连表]保存单表规则至：acheObject<TimeKey,Object>
 * 
 * */
function saveRelateCheck(){
	if(acheObject==null){
		acheObject = new HashMap();
	}
	var saveObject = null;	
	if(editingRule!=null){
		saveObject = acheObject.get(editingRule);	
	}else{
		saveObject = new Object();
	}
	//1.AuditType
	saveObject["auditType"] = "关联规则核查";
	//2.ColumnCode/ColumnName
	var beCheckedColumn = getCheckedValue("columnSelect");
	if(beCheckedColumn==null || beCheckedColumn==""){
		window.wxc.xcConfirm("请选择核查字段.",window.wxc.xcConfirm.typeEnum.info);
		return;
	}else{
		var checkedObj = acheItems.get(beCheckedColumn);
		saveObject["columnCode"] = beCheckedColumn;
		saveObject["columnName"] = checkedObj["COLUMN_NAME"];
	}
	//3.IsFilter
	var $isFilter = getCheckedValue("isFilterNull");
	if($isFilter==null || $isFilter==""){
		saveObject["isFilter"] = false;
	}else{
		saveObject["isFilter"] = true;
	}
	//4.Expression['核查枚举值','核查关联数据条数','多属性匹配']
	var checkType = getCheckedValue("isRelate");	
	saveObject["expression"] = checkType;
	if(checkType=="核查关联数据条数"){
		//4.1.MinCount
		saveObject["minNumber"] = document.getElementById("startNumber").innerHTML;
		//4.2.MaxCount
		saveObject["maxNumber"] = document.getElementById("limitNumber").innerHTML;
	}	
	//5.GlassTableId
	var glassTableId = $("#hiddenOfGlassTable").val();
	if(glassTableId=="" || glassTableId=="-1"){
		window.wxc.xcConfirm("请先选择参照数据表.",window.wxc.xcConfirm.typeEnum.info);
		return;
	}else{
		saveObject["tableCode"] = glassTableId;
	}
	//6.GlassColumnId
	if(saveObject["expression"]=="多属性匹配"){
		/*
		 * 	组装已选择的ValueColumn
		 * 
		 * */
		var checkedCodes = "";
		var firstChecked = "";
		var valueCheckboxes = document.getElementsByName("valueSelect");
		if(valueCheckboxes!=null && valueCheckboxes.length>0){
			for(var i=0;i<valueCheckboxes.length;i++){
				var $checkbox = valueCheckboxes[i];
				if($checkbox.checked){
					var checkedValue = $checkbox.value;
					if(checkedCodes==""){
						checkedCodes = checkedValue;
						firstChecked = checkedValue;
					}else{
						checkedCodes += ","+checkedValue;
					}
				}
			}	
		}
		if(checkedCodes==""){
			window.wxc.xcConfirm("请至少选择一个值属性.",window.wxc.xcConfirm.typeEnum.info);
			return;
		}else{
			saveObject["valueColumn"] = firstChecked;
			saveObject["matchType"] = getCheckedValue("isParalle");
			saveObject["dimension"] = getCheckedValue("matchDimension");
			if(checkedCodes.indexOf(",")!=-1){
				saveObject["checkedValues"] = checkedCodes;
			}else{
				saveObject["checkedValues"] = firstChecked;
			}
		}
	}else{
		var checkedValueColumn = getCheckedValue("valueSelect");
		if(checkedValueColumn==null || checkedValueColumn==""){
			window.wxc.xcConfirm("请选择一个值属性.",window.wxc.xcConfirm.typeEnum.info);
			return;
		}else{
			saveObject["valueColumn"] = checkedValueColumn;
		}
	}
	//5.ConnectedColumnIds
	saveObject["connectedColumns"] = connectedColumn;
	//6.缓存结果.
	if(editingRule==null){
		//6.1.如果当前状态是[新增]，则填充时间戳，缓存.
		saveObject["expressKey"] = getCurrentTime(null);
		acheObject.put(saveObject["expressKey"],saveObject);
	}else{
		//6.2.如果当前状态是[修改]，则提交更新.
		acheObject.put(editingRule,saveObject);
		editingRule = null;
	}
	if(ruleObj!=null){
		ruleObj["editedItems"] = acheObject.valueSet();
	}
	//====================执行一些初始化操作==================
	//7.01.最小数量
	document.getElementById("startNumber").innerHTML = "0";
	//7.02.最大数量
	document.getElementById("limitNumber").innerHTML = "0";
	//7.03.关联字段[清空]
	connectedColumn = new Array();
	//7.04.关联字段[渲染]
	flushConnect();
	//7.05.值属性[取消选择]	
	unCheckAllBoxes("valueSelect");
	//7.06.规则类型[取消选择]
	unCheckAllBoxes("isRelate");
	//7.07.刷新参照字段
	$("#checkedGlassTable").val("");
	$("#glassColumnKey").val("");
	$("#hiddenOfGlassTable").val("-1");
	searchGlassColumn();
	//7.08.解封表达式输入
	$("#expressionInput").attr("disabled",false);
	//7.09.回归展示
	$(".rightPanel").hide();
	$("#editedTable").show(500);
	//7.10.渲染[已编辑表格]
	initEditedTable();
}
/*[删除]删除已编辑的规则*/
function cleanEditedItem(){
	var checkedValue = getCheckedValue("editedItem");
	if(checkedValue!=null && checkedValue!="" && acheObject!=null){
		window.wxc.xcConfirm("是否确定删除此项规则？.","custom",{
			title:"警告",
			btn:parseInt("0011",2),
			onOk:function(){
				acheObject.remove(checkedValue);
				if(ruleObj==null){
					ruleObj = new Object();
				}
				ruleObj["editedItems"] = acheObject.valueSet();
				initEditedTable();
			}
		});	
	}else{
		window.wxc.xcConfirm("请先选择一条规则.",window.wxc.xcConfirm.typeEnum.info);
		return;
	}
}
/*
 * 	双击修改已编辑的规则：暂只支持单表规则修改.
 * 
 * */
function turnToUpdate(itemKey){
	editingRule = itemKey;
	//★ 双击 选中已编辑规则[CheckBox]
	checkAssignedValue("editedItem",itemKey);
	if(acheObject!=null){
		var checkedObj = acheObject.get(itemKey);	
		if(checkedObj!=null && checkedObj["auditType"]=="基础规则核查"){
			//1.01.[渲染]属性数据 =====================================================================================
			$("#columnAliasInput").val("");
			conditions = [{
				name:"BELONG_TABLE",
				value:$("#hiddenOfBelongTable").val()
			},{
				name:"ID",
				value:checkedObj["columnCode"]
			}];
			columnTable.fnDraw();		
			//1.02.[选中]Columns指定的值
			setTimeout("javascript:checkAssignedValue('columnSelect',"+checkedObj["columnCode"]+");",1000);		
			//1.03.[渲染]是否过滤空值
			if(checkedObj["isFilter"]){
				inCheckAllBoxes("isFilterNull");
			}else{
				unCheckAllBoxes("isFilterNull");
			}
			//1.04.[渲染]取消选择
			unCheckAllBoxes("isRelate");
			//1.05.渲染表达式
			$("#expressionInput").attr("disabled",false);
			$("#expressionInput").val(checkedObj["expression"]);
			//======================================================================================================
		}else if(checkedObj!=null && checkedObj["auditType"]=="关联规则核查"){
			//2.01.[缓存]缓存关联=======================================================1=============================
			connectedColumn = checkedObj["connectedColumns"];
			//2.02.[渲染]根据“核查字段”和“关联字段”渲染左侧数据
			$("#columnAliasInput").val("");
			var columnIds = checkedObj["columnCode"];
			if(connectedColumn.length>0){
				for(var i=0;i<connectedColumn.length;i++){
					var connObj = connectedColumn[i];
					columnIds+=","+connObj["keyOne"];
				}
			}		
			conditions = [{
				name:"BELONG_TABLE",
				value:$("#hiddenOfBelongTable").val()
			},{
				name:"ID",
				value:columnIds
			}];
			columnTable.fnDraw();
			setTimeout("javascript:checkAssignedValue('columnSelect',"+checkedObj["columnCode"]+");",1000);
			
			//2.03.[渲染]根据“参照字段”和“关联字段”渲染右侧数据=============================2=============================
			$("#checkedGlassTable").val(checkedObj["glassTableName"]);
			$("#glassColumnKey").val("");
			$("#hiddenOfGlassTable").val(checkedObj["tableCode"]);
			var valueIds = null;
			if(checkedObj["expression"]=="多属性匹配"){
				valueIds = checkedObj["checkedValues"];
			}else{
				valueIds = checkedObj["valueColumn"];
			}			
			if(connectedColumn!=null && connectedColumn.length>0){
				for(var i=0;i<connectedColumn.length;i++){
					var connObj = connectedColumn[i];
					valueIds+=","+connObj["keyTwo"];
				}
			}		
			glassParam = [{
				name:"BELONG_TABLE",
				value:$("#hiddenOfGlassTable").val()
			},{
				name:"ID",
				value:valueIds
			}];
			glassTable.fnDraw();
			if(checkedObj["expression"]=="多属性匹配"){
				setTimeout("javascript:checkAssignedValues('valueSelect','"+checkedObj["checkedValues"]+"');",1000);
			}else{
				setTimeout("javascript:checkAssignedValue('valueSelect',"+checkedObj["valueColumn"]+");",1000);
			}
			//2.04.[渲染]是否过滤空值====================================================3============================
			if(checkedObj["isFilter"]){
				inCheckAllBoxes("isFilterNull");
			}else{
				unCheckAllBoxes("isFilterNull");
			}
			//2.05.[渲染]选择规则类型
			checkAssignedValue("isRelate",checkedObj["expression"]);
			//7.[渲染]使失效
			$("#expressionInput").val("");
			$("#expressionInput").attr("disabled",true);
			//8.显示左侧关联表格界面
			$(".rightPanel").hide();
			$("#glassTable").show();			
			//9.判断是否展示数量范围						
			if(checkedObj["expression"]=="核查关联数据条数"){
				document.getElementById("startNumber").innerHTML = checkedObj["minNumber"];
				document.getElementById("limitNumber").innerHTML = checkedObj["maxNumber"];
				$("#numberRange").show();
			}else{
				document.getElementById("startNumber").innerHTML = "0";
				document.getElementById("limitNumber").innerHTML = "0";
				$("#numberRange").hide();
			}
			if(checkedObj["expression"]=="多属性匹配"){
				$("#matchTypees").show();
				checkAssignedValue("isParalle",checkedObj["matchType"]);
				checkAssignedValue("matchDimension",checkedObj["dimension"]);
			}else{
				checkAssignedValue("isParalle","OR");
				checkAssignedValue("matchDimension","T");
				$("#matchTypees").hide();
			}
		}
	}	
}
/*渲染已编辑的规则信息*/
function initEditedTable(){
	var innerHtml="<table class='table table-bordered table-hover' style='border:0px;'>";
	innerHtml+="<tbody><tr>";
		innerHtml+="<td class='tableHead' style='width:10%;border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;background:#F9FAFE;'>&nbsp;</td>";
		innerHtml+="<td class='tableHead' style='width:30%;border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;background:#F9FAFE;'>核查属性</td>";
		innerHtml+="<td class='tableHead' style='width:30%;border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;background:#F9FAFE;'>表达式/核查类型</td>";
		innerHtml+="<td class='tableHead' style='width:30%;border:0px;border-bottom:solid 1px #DFE8F1;background:#F9FAFE;'>是否忽略空值</td>";
	innerHtml+="</tr>";	
	if(acheObject!=null && acheObject.valueSet().length>0){
		var items = acheObject.valueSet();
		for(var j=0;j<items.length;j++){
			var $obj = items[j];
			innerHtml+="<tr ondblclick='javascript:turnToUpdate("+$obj["expressKey"]+");' style='cursor:pointer'>";
				innerHtml+="<td style='text-align:center;border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;'>";
					innerHtml+="<input type='checkbox' value='"+$obj["expressKey"]+"' name='editedItem'></input>";
				innerHtml+="</td>";
				innerHtml+="<td style='border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;'>"+$obj["columnName"]+"</td>";
				innerHtml+="<td style='border:0px;border-bottom:solid 1px #DFE8F1;border-right:solid 1px #DFE8F1;'>";
				if($obj["expression"]=="核查枚举值" || $obj["expression"]=="核查关联数据条数" || $obj["expression"]=="多属性匹配"){
					innerHtml+="<font color='red'>"+$obj["expression"]+"</font>";
				}else{
					innerHtml+=$obj["expression"];	
				}
				innerHtml+="</td>";
				innerHtml+="<td style='border:0px;border-bottom:solid 1px #DFE8F1;'>";
					if($obj["isFilter"]){
						innerHtml+="<font color='red'>是</font>";	
					}else{
						innerHtml+="<font color='green'>否</font>";
					}
				innerHtml+="</td>";
			innerHtml+="</tr>";		
		}
	}else{
		innerHtml+="<tr>";
			innerHtml+="<td colspan='4' style='text-align:center;'><font color='red'>尚未配置任何规则</font></td>";
		innerHtml+="</tr>";
	}
	innerHtml+="</tbody></table>";
	document.getElementById("editedItems").innerHTML = innerHtml;
}