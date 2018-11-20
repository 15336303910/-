var connectedColumn = new Array();
/*=============================================================================================*/
var colorsPool = [
	"#4B8EFD","#FBB14A","#DB4A38",
	"#35A946","#58BFDD","#CCCCCC"
];
var colorMap = new HashMap();
/*
 * 	[插入]：从表[1]的角度来新增关联
 * 
 * */
function changeRelate(isChecked,checkedValue){
	if(isChecked){
		uniqueCheckbox(isChecked,"relateColum",checkedValue);					
		var spouseValues = getCheckedValue("connectColu");
		if(spouseValues!=null && spouseValues!=""){
			if(connectedColumn==null){
				connectedColumn = new Array();
			}
			var curColor = "";
			for(var b=0;b<colorsPool.length;b++){
				var colorString = colorsPool[b];
				if(colorMap.containsKey(colorString)){
					continue;
				}else{
					colorMap.put(colorString,colorString);
					curColor = colorString;
					break;
				}
			}								
			var connectObject = {
				keyOne:checkedValue,
				keyTwo:spouseValues,
				backColor:curColor
			};
			connectedColumn.push(connectObject);
			flushConnect();
		}
	}
}
/*
 * 	[插入]：从表[2]的角度来新增关联
 * 
 * */
function referConnected(isChecked,checkedValue){
	if(isChecked){
		uniqueCheckbox(isChecked,"connectColu",checkedValue);
		var spouseValues = getCheckedValue("relateColum");
		if(spouseValues!=null && spouseValues!=""){
			if(connectedColumn==null){
				connectedColumn = new Array();
			}
			var curColor = "";
			for(var b=0;b<colorsPool.length;b++){
				var colorString = colorsPool[b];
				if(colorMap.containsKey(colorString)){
					continue;
				}else{
					colorMap.put(colorString,colorString);
					curColor = colorString;
					break;
				}
			}
			var connectObject = {
				keyOne:spouseValues,
				keyTwo:checkedValue,
				backColor:curColor
			};
			connectedColumn.push(connectObject);							
			flushConnect(); 
		}
	}
}
/*[查询]：根据已配置的关联来刷新界面*/
function flushConnect(){
	if(connectedColumn!=null && connectedColumn.length>0){
		for(var i=0;i<connectedColumn.length;i++){
			var thisObj = connectedColumn[i];
			var checkedValue = thisObj["keyOne"];
			var spouseValues = thisObj["keyTwo"];
			var backColor = thisObj["backColor"];
			var aDiv = $("#ADIV"+checkedValue);
			if(aDiv!=null){
				$("#ADIV"+checkedValue).html("<div onclick='javascript:canelConnect(1,"+checkedValue+");' style='background:"+backColor+";width:13px;height:13px;cursor:pointer;border-radius:50%;'></div>");	
			}
			var bDiv = $("#BDIV"+spouseValues);
			if(bDiv!=null){
				$("#BDIV"+spouseValues).html("<div onclick='javascript:canelConnect(2,"+spouseValues+");' style='background:"+backColor+";width:13px;height:13px;cursor:pointer;border-radius:50%;'></div>");	
			}
		}
	}else{
		//1.如果关联属性为空，则初始化Column表格.
		if(acheItems!=null && acheItems.valueSet().length>0){
			var columns = acheItems.valueSet();
			for(var i=0;i<columns.length;i++){
				var $Column = columns[i];
				if($("#ADIV"+$Column["ID"])!=null){
					$("#ADIV"+$Column["ID"]).html("<center><div id='ADIV"+$Column["ID"]+"'><input type='checkbox' value='"+$Column["ID"]+"' name='relateColum' onchange='javascript:changeRelate(this.checked,this.value);'></input></div></center>");
				}
			}
		}
		//2.初始化Glass表格
		if(glassAches!=null && glassAches.valueSet().length>0){
			var columns = glassAches.valueSet();
			for(var i=0;i<columns.length;i++){
				var $Column = columns[i];
				if($("#BDIV"+$Column["ID"])!=null){
					$("#BDIV"+$Column["ID"]).html("<center><div id='BDIV"+$Column["ID"]+"'><input type='checkbox' value='"+$Column["ID"]+"' name='connectColu' onchange='javascript:referConnected(this.checked,this.value);'></input></div></center>");
				}
			}
		}
	}
}
/*[删除]：取消关联*/
function canelConnect(location,relateColumn){
	window.wxc.xcConfirm("是否确认要取消这一对关联？.","custom",{
		title:"提示",
		btn:parseInt("0011",2),
		onOk:function(){
			var acheArray = new Array();
			if(connectedColumn!=null && connectedColumn.length>0){
				for(var i=0;i<connectedColumn.length;i++){
					var thisObj = connectedColumn[i];
					if(location==1){
						if(thisObj["keyOne"]!=relateColumn){
							acheArray.push(thisObj);
						}else{
							var glasssColumn = thisObj["keyTwo"];
							if($("#ADIV"+relateColumn)!=null){
								$("#ADIV"+relateColumn).html("<input type='checkbox' value='"+relateColumn+"' name='relateColum' onchange='javascript:changeRelate(this.checked,this.value);'></input>");
							}
							if($("#BDIV"+glasssColumn)!=null){
								$("#BDIV"+glasssColumn).html("<input type='checkbox' value='"+glasssColumn+"' name='connectColu' onchange='javascript:referConnected(this.checked,this.value);'></input>");
							}
							colorMap.remove(thisObj["backColor"]);
						}
					}else if(location==2){
						if(thisObj["keyTwo"]!=relateColumn){
							acheArray.push(thisObj);
						}else{
							var glasssColumn = thisObj["keyOne"];
							if($("#ADIV"+glasssColumn)!=null){
								$("#ADIV"+glasssColumn).html("<input type='checkbox' value='"+glasssColumn+"' name='relateColum' onchange='javascript:changeRelate(this.checked,this.value);'></input>");
							}
							if($("#BDIV"+relateColumn)!=null){
								$("#BDIV"+relateColumn).html("<input type='checkbox' value='"+relateColumn+"' name='connectColu' onchange='javascript:referConnected(this.checked,this.value);'></input>");
							}
							colorMap.remove(thisObj["backColor"]);
						}
					}
				}
			}
			connectedColumn = acheArray;
			flushConnect();
		}
	});	
}