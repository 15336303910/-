/*
	1.根据name获取checkbox已经选定的value.未选择则返回null.
*/
function getCheckedValue(checkboxName){
	var checkedValue = null;
	var checkboxes = document.getElementsByName(checkboxName);
	if(checkboxes!=null && checkboxes.length>0){
		for(var i=0;i<checkboxes.length;i++){
			var $checkbox = checkboxes[i];
			if($checkbox.checked){
				checkedValue = $checkbox.value;
				break;
			}
		}	
	}
	return checkedValue;
}
/*
	2.根据name获取checkbox已经选定的value数量.
*/
function getCheckedCount(checkboxName){
	var checkedCount = 0;
	var checkboxes = document.getElementsByName(checkboxName);
	if(checkboxes!=null && checkboxes.length>0){
		for(var i=0;i<checkboxes.length;i++){
			var $checkbox = checkboxes[i];
			if($checkbox.checked){
				checkedCount++;
			}
		}	
	}
	return checkedCount;
}
/*
	3.确保此name的checkbox只能有一项被选择
*/
function uniqueCheckbox(isChecked,checkboxName,checkedValue){
	if(isChecked){
		var checkboxes = document.getElementsByName(checkboxName);
		if(checkboxes!=null && checkboxes.length>0){
			for(var i=0;i<checkboxes.length;i++){
				if(checkboxes[i].value==checkedValue){
					checkboxes[i].checked = true;
				}else{
					checkboxes[i].checked = false;
				}
			}
		}
	}
}
/*
	4.使得CheckBox全部失效
*/
function unCheckAllBoxes(checkboxName){
	var checkboxes = document.getElementsByName(checkboxName);
	if(checkboxes!=null && checkboxes.length>0){
		for(var i=0;i<checkboxes.length;i++){
			checkboxes[i].checked = false;
		}
	}
}
/*
	5.使得CheckBox全部有效
*/
function inCheckAllBoxes(checkboxName){
	var checkboxes = document.getElementsByName(checkboxName);
	if(checkboxes!=null && checkboxes.length>0){
		for(var i=0;i<checkboxes.length;i++){
			checkboxes[i].checked = true;
		}
	}
}
/*
	6.1.选定符合某值的Checkbox
*/
function checkAssignedValue(checkboxName,checkedValue){
	var checkboxes = document.getElementsByName(checkboxName);
	if(checkboxes!=null && checkboxes.length>0){
		for(var i=0;i<checkboxes.length;i++){
			if(checkboxes[i].value == checkedValue){
				checkboxes[i].checked = true;
			}else{
				checkboxes[i].checked = false;
			}
		}
	}
}
/*
	6.2.选定符合某值的Checkbox
*/
function checkMultiValue(checkboxName,checkedValue){
	var checkboxes = document.getElementsByName(checkboxName);
	if(checkboxes!=null && checkboxes.length>0){
		for(var i=0;i<checkboxes.length;i++){
			if(checkboxes[i].value == checkedValue){
				checkboxes[i].checked = true;
			}
		}
	}
}
/*
	6.3.选定符合某值的Checkbox
*/
function unCheckMultiValue(checkboxName,checkedValue){
	var checkboxes = document.getElementsByName(checkboxName);
	if(checkboxes!=null && checkboxes.length>0){
		for(var i=0;i<checkboxes.length;i++){
			if(checkboxes[i].value == checkedValue){
				checkboxes[i].checked = false;
			}
		}
	}
}
/*
	7.选定符合某值的Checkboxes
*/
function checkAssignedValues(checkboxName,checkedValues){
	var values = checkedValues.split(",");
	var checkboxes = document.getElementsByName(checkboxName);
	if(checkboxes!=null && checkboxes.length>0){
		for(var i=0;i<checkboxes.length;i++){
			checkboxes[i].checked = false;
		}
		for(var i=0;i<checkboxes.length;i++){
			var $value = checkboxes[i].value;
			for(var j=0;j<values.length;j++){
				if(values[j]==$value){
					checkboxes[i].checked = true;
					break;
				}
			}
		}
	}
}