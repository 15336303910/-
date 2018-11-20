var glassTable = null;
var glassParam = [];
var glassAches = null;
function initGlassTable(){
	glassTable = $("#glassTableColumn").dataTable({
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
		"sScrollY":"210px",
		"sScrollX":"548px",
		"aoColumns":[{
			"mData":"ID",
			"sWidth":"143px"
		},{
			"mData":"COLUMN_ALIAS",
			"sWidth":"155px"
		},{
			"mData":"COLUMN_NAME",
			"sWidth":"155px"
		},{
			"mData":"ID",
			"sWidth":"83px"
		}],
		"aoColumnDefs":[],
		"iDisplayLength":8,
		"sDom":"<'top'>frt<'bottom'ilp><'clear'>",
		"aaSorting":[[0,"desc"]],
		"fnRowCallback":function(nRow,aData,iDisplayIndex){
			if(glassAches==null){
				glassAches = new HashMap();
			}
			glassAches.put(aData["ID"],aData);
			//1.渲染第一列选择框
			if(getCheckedValue("isRelate")!=null && getCheckedValue("isRelate")=="多属性匹配"){
				$("td:eq(0)",nRow).html("<center><div id='CDIV"+aData["ID"]+"'><input type='checkbox' value='"+aData["ID"]+"' name='valueSelect'></input></div></center>");
			}else{
				$("td:eq(0)",nRow).html("<center><div id='CDIV"+aData["ID"]+"'><input type='checkbox' value='"+aData["ID"]+"' name='valueSelect' onchange='javascript:uniqueCheckbox(this.checked,this.name,this.value);'></input></div></center>");
			}
			//2.渲染第四列关联键
			if(connectedColumn!=null && connectedColumn.length>0){
				var isConnected = false;
				var backColor = "";
				for(var i=0;i<connectedColumn.length;i++){
					var thisObj = connectedColumn[i];
					if(thisObj["keyTwo"]==aData["ID"]){
						isConnected = true;
						backColor = thisObj["backColor"];
						break;
					}
				}
				if(isConnected){
					$("td:eq(3)",nRow).html("<center><div id='BDIV"+aData["ID"]+"'><div onclick='javascript:canelConnect(2,"+aData["ID"]+");' style='background:"+backColor+";width:13px;height:13px;cursor:pointer;border-radius:50%;'></div></div></center>");
				}else{
					$("td:eq(3)",nRow).html("<center><div id='BDIV"+aData["ID"]+"'><input type='checkbox' value='"+aData["ID"]+"' name='connectColu' onchange='javascript:referConnected(this.checked,this.value);'></input></div></center>");	
				}
			}else{
				$("td:eq(3)",nRow).html("<center><div id='BDIV"+aData["ID"]+"'><input type='checkbox' value='"+aData["ID"]+"' name='connectColu' onchange='javascript:referConnected(this.checked,this.value);'></input></div></center>");	
			}					
		},
		"fnServerData":getGlassData,
		"sAjaxSource":"basicColumnAction/findItems.ilf"
	});
	$("#glassTableColumn tbody").click(function(event){
		$(columnTable.fnSettings().aoData).each(function(){
			$(this.nTr).removeClass("row_selected");
		});
		if($(event.target.parentNode).attr("class")=="even" || $(event.target.parentNode).attr("class")=="odd"){
			$(event.target.parentNode).addClass("row_selected");
		}
	});
}
function getGlassData(sSource,aoData,fnCallback){
	$.ajax({
		url:sSource,
		type:"POST",
		data:"tableparam="+JSON.stringify(aoData)+"&conditions="+JSON.stringify(glassParam),
		dataType:"json",		
		success:function(data){
			fnCallback(data);
		}
	});
}
function searchGlassColumn(){
	glassParam = [{
		name:"BELONG_TABLE",
		value:$("#hiddenOfGlassTable").val()
	}];
	var columnAlias = $("#glassColumnKey").val();
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
		glassParam.push({
			name:"COLUMN_ALIAS",
			value:columnKeys
		});
	}
	glassTable.fnDraw();
}