var columnTable = null;
var acheItems = null;
var conditions = [];
function initColumnsOfTable(){
	columnTable = $("#columnTable").dataTable({
		"bSortClasses":false,
		"aLengthMenu":[10,20,30],
		"bAutoWidth":true,
		"bSort":true,
		"bProcessing":true,
		"bServerSide":true,
		"bFilter":false,
		"bLengthChange":false,
		"sPaginationType":"full_numbers",
		"bStateSave":false,
		"bScrollCollapse":true,
		"sScrollY":"158px",
		"sScrollX":"560px",
		"aoColumns":[{
			"mData":"ID",
			"sWidth":"70px"
		},{
			"mData":"COLUMN_ALIAS",
			"sWidth":"195px"
		},{
			"mData":"COLUMN_NAME",
			"sWidth":"195px"
		},{
			"mData":"DATA_TYPE",
			"sWidth":"120px",
			"bVisible":false
		},{
			"mData":"IS_EXPORT",
			"sWidth":"90px"
		}],
		"aoColumnDefs":[],
		"iDisplayLength":6,
		"sDom":"<'top'>frt<'bottom'ilp><'clear'>",
		"aaSorting":[[0,"desc"]],
		"fnRowCallback":function(nRow,aData,iDisplayIndex){
			//1.根据ID缓存界面数据
			if(acheItems==null){
				acheItems = new HashMap();
			}
			acheItems.put(aData["ID"],aData);
			//2.渲染第一列选择框
			$("td:eq(0)",nRow).html("<center><div><input type='checkbox' value='"+aData["ID"]+"' name='columnSelect'></input></div></center>");
			//3.渲染第四列关联键
			if(connectedColumn!=null && connectedColumn.length>0){
				var isConnected = false;
				var backColor = "";
				for(var i=0;i<connectedColumn.length;i++){
					var thisObj = connectedColumn[i];
					if(thisObj["keyOne"]==aData["ID"]){
						isConnected = true;
						backColor = thisObj["backColor"];
						break;
					}
				}
				if(isConnected){
					$("td:eq(3)",nRow).html("<center><div id='ADIV"+aData["ID"]+"'><div onclick='javascript:canelConnect(1,"+aData["ID"]+");' style='background:"+backColor+";width:13px;height:13px;cursor:pointer;border-radius:50%;'></div></div></center>");
				}else{
					$("td:eq(3)",nRow).html("<center><div id='ADIV"+aData["ID"]+"'><input type='checkbox' value='"+aData["ID"]+"' name='relateColum' onchange='javascript:changeRelate(this.checked,this.value);'></input></div></center>");	
				}
			}else{
				$("td:eq(3)",nRow).html("<center><div id='ADIV"+aData["ID"]+"'><input type='checkbox' value='"+aData["ID"]+"' name='relateColum' onchange='javascript:changeRelate(this.checked,this.value);'></input></div></center>");	
			}
		},
		"fnServerData":getColumnData,
		"sAjaxSource":"basicColumnAction/findItems.ilf"
	});
	$("#columnTable tbody").click(function(event){
		$(columnTable.fnSettings().aoData).each(function(){
			$(this.nTr).removeClass("row_selected");
		});
		if($(event.target.parentNode).attr("class")=="even" || $(event.target.parentNode).attr("class")=="odd"){
			$(event.target.parentNode).addClass("row_selected");
		}
	});
}
function getColumnData(sSource,aoData,fnCallback){
	$.ajax({
		url:sSource,
		type:"POST",
		data:"tableparam="+JSON.stringify(aoData)+"&conditions="+JSON.stringify(conditions),
		dataType:"json",		
		success:function(data){
			fnCallback(data);
		}
	});
};
function searchColumnData(){
	conditions = [{
		name:"BELONG_TABLE",
		value:$("#hiddenOfBelongTable").val()
	}];
	var columnAlias = $("#columnAliasInput").val();
	if(columnAlias!=""){
		var columnKeys = "";
		var columnNames = columnAlias.split(" ");
		if(columnNames.length>0){
			for(var i=0;i<columnNames.length;i++){
				var keyStr = columnNames[i];
				if(i==0){
					columnKeys =keyStr;
				}else{
					columnKeys+=";"+keyStr;
				}
			}
		}
		conditions.push({
			name:"COLUMN_ALIAS",
			value:columnKeys
		});
	}
	columnTable.fnDraw();
}