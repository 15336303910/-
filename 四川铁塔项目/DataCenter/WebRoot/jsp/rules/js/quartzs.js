var isExecShow = false;
function doCircleConfig(){
	if(!isExecShow){
		$("#exConfigButton").html("保存");
		$(".circleConfig").show(300);
		isExecShow = true;
	}else{
		var circleType = getCheckedValue("execCircle");
		if(circleType!=null){
			var isValid = true;
			if(circleType=="无"){
				$("#conExpressionInput").val("-");	
			}else{							
				//1.执行数据验证
				var hourOfDay = $("#hourOfDay").val();
				if(hourOfDay=="-" || hourOfDay==""){
					window.wxc.xcConfirm("请配置小时粒度.",window.wxc.xcConfirm.typeEnum.info);
					return;
				}
				var minuteOfHour = $("#minuteOfHour").val();
				if(minuteOfHour=="-" || minuteOfHour==""){
					window.wxc.xcConfirm("请配置分钟粒度.",window.wxc.xcConfirm.typeEnum.info);
					return;
				}
				var secondOfMinute = $("#secondOfMinute").val();
				if(secondOfMinute=="-" || secondOfMinute==""){
					window.wxc.xcConfirm("请配置秒粒度.",window.wxc.xcConfirm.typeEnum.info);
					return;
				}
				//2.拼装表达式
				if(circleType=="日"){
					//2.1.每天定点执行
					var $expression = secondOfMinute+" "+minuteOfHour+" "+hourOfDay+" * * ?";
					$("#conExpressionInput").val($expression);
				}else if(circleType=="周"){
					//2.2.每周定点执行
					var dayOfWeek = $("#chooseDayOfWeek").val();
					if(dayOfWeek=="-" || dayOfWeek==""){
						window.wxc.xcConfirm("请配置周粒度.",window.wxc.xcConfirm.typeEnum.info);
						return;
					}
					var $expression = secondOfMinute+" "+minuteOfHour+" "+hourOfDay+" ? * "+dayOfWeek;
					$("#conExpressionInput").val($expression);
				}else if(circleType=="月"){
					//2.3.每月定点执行
					var dayOfMonth = $("#chooseDayOfMonth").val();
					if(dayOfMonth=="-" || dayOfMonth==""){
						window.wxc.xcConfirm("请配置天粒度.",window.wxc.xcConfirm.typeEnum.info);
						return;
					}
					var $expression = secondOfMinute+" "+minuteOfHour+" "+hourOfDay+" "+dayOfMonth+" * ?";
					$("#conExpressionInput").val($expression);
				}
			}
			if(isValid){
				$("#exConfigButton").html("配置");
				$(".circleConfig").hide(300);
				isExecShow = false;		
			}
		}else{
			$("#exConfigButton").html("配置");
			$("#conExpressionInput").val("");
			$(".circleConfig").hide(300);
			isExecShow = false;	
		}
	}
}
function execCircleSetting(isChecked,checkboxName,checkedValue){
	uniqueCheckbox(isChecked,checkboxName,checkedValue);
	if(checkedValue=="无"){
		$("#chooseDayOfWeek").hide(300);
		$("#chooseDayOfMonth").hide(300);
	}else if(checkedValue=="周"){
		$("#conExpressionInput").val("");
		$("#chooseDayOfMonth").hide();
		$("#chooseDayOfWeek").show(300);				
	}else if(checkedValue=="月"){
		$("#chooseDayOfWeek").hide();
		$("#chooseDayOfMonth").show(300);
	}else if(checkedValue=="日"){
		$("#chooseDayOfWeek").hide(300);
		$("#chooseDayOfMonth").hide(300);
	}
}