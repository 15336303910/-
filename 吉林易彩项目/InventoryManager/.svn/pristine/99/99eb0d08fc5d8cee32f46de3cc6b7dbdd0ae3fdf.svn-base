	var bu2;
	//点击按钮
	function shuzi3(b){
		bu2=b.id;
		var num=Ext.getCmp(bu2).getText();
		if(!isNaN(num)){
			//变色
			//$(".faceButton_2").css("color","");
			//Ext.getCmp(bu2).setText("<font color='blue'>"+num+"</font>");
			if(Ext.getCmp("faceno").getValue()=="1"){
				Ext.getCmp("addStartFaceNo").setValue(num);
			}else if(Ext.getCmp("faceno").getValue()=="2"){
				Ext.getCmp("modifyStartFaceNo").setValue(num);
			}else if(Ext.getCmp("faceno").getValue()=="3"){
				Ext.getCmp("addEndFaceNo").setValue(num);
			}else if(Ext.getCmp("faceno").getValue()=="4"){
				Ext.getCmp("modifyEndFaceNo").setValue(num);
			}
		}
	}
	
	//保存按钮
	function showWellFace(){
		Ext.getCmp("ox_4").setText("东南");
		Ext.getCmp("ox_5").setText("南");
		Ext.getCmp("ox_6").setText("西南");
		Ext.getCmp("ox_7").setText("西");
		Ext.getCmp("ox_8").setText("西北");
		Ext.getCmp("ox_1").setText("北");
		Ext.getCmp("ox_2").setText("东北");
		Ext.getCmp("ox_3").setText("东");
		Ext.getCmp("faceno").setValue("");
		Ext.getCmp("faceidss").setValue("");
		for(var i=1;i<9;i++){
			Ext.getCmp("ox_"+i).setDisabled(false);
		}
		Ext.getCmp("wellFace_aWindow").close();
	}
	//长按按钮
	function ondown(wellmap){
		var intervalTimer = null;
		for(var j=1;j<9;j++){
			$("#ox_"+j).mousedown(function(e){
				clearTimeout(intervalTimer); //取消上次延时未执行的方法
		        intervalTimer = setTimeout(function() {
		            var pipeSegmentGrid = Ext.getCmp("pipeSegmentGrid");
					if (!pipeSegmentGrid.faceNumberWindow) {
						pipeSegmentGrid.faceNumberWindow = new com.increase.cen.poleline.PolelineWindow({
							id : "faceNumberWindow",
							title : '选择井面相应的数字',
							iconCls : 'i-script-window',
							items : [{
								id : "faceNumber",
								height : 330,
								xtype : 'faceNumber'// 窗口里的表单
							}]
						})
					}
					pipeSegmentGrid.faceNumberWindow.show();
					resetfacenumber();
					if(wellmap!='' && wellmap!=null){
						var strarr=wellmap.split('');
						for(var i=0;i<strarr.length;i++){
							if(strarr[i]!='0'){
								var fid2="f"+strarr[i];
								Ext.getCmp(fid2).setDisabled(true);
							}
						}
					}
					bu2=e.currentTarget.id;
		        }, 1000);
		    }).mouseup(function(){
		    	clearTimeout(intervalTimer);
		    });
		}
	}
	function resetfacenumber(){
		Ext.getCmp("f1").setDisabled(false);
		Ext.getCmp("f2").setDisabled(false);
		Ext.getCmp("f3").setDisabled(false);
		Ext.getCmp("f4").setDisabled(false);
		Ext.getCmp("f5").setDisabled(false);
		Ext.getCmp("f6").setDisabled(false);
		Ext.getCmp("f7").setDisabled(false);
		Ext.getCmp("f8").setDisabled(false);		
	}
    //清除按钮
	function qingchu(){
		var num=Ext.getCmp(bu2).getText();
		var fx=	bu2;
		if(!isNaN(num)){
			var fid="f"+num;
			Ext.getCmp(fid).setDisabled(false);
			if('ox_8'==fx){
				Ext.getCmp("ox_8").setText("西北");
			}
			if('ox_1'==fx){
				Ext.getCmp("ox_1").setText("北");
			}
			if('ox_2'==fx){
				Ext.getCmp("ox_2").setText("东北");
			}
			if('ox_7'==fx){
				Ext.getCmp("ox_7").setText("西");
			}
			if('ox_3'==fx){
				Ext.getCmp("ox_3").setText("东");
			}
			if('ox_6'==fx){
				Ext.getCmp("ox_6").setText("西南");
			}
			if('ox_5'==fx){
				Ext.getCmp("ox_5").setText("南");
			}
			if('ox_4'==fx){
				Ext.getCmp("ox_4").setText("东南");
			}
			var pipeSegmentGrid = Ext.getCmp("pipeSegmentGrid");
			pipeSegmentGrid.faceNumberWindow.hide();
		}	
	}
	//关闭按钮
	function hideFaceNumber(){
		var pipeSegmentGrid = Ext.getCmp("pipeSegmentGrid");
		pipeSegmentGrid.faceNumberWindow.close();
	}
	//关闭面信息确认窗口
	function hideFaceConfirm(){
		var pipeSegmentGrid = Ext.getCmp("pipeSegmentGrid");
		pipeSegmentGrid.faceConfirmWindow.close();
	}
	//已哪个面为基准面的操作
	function dosubmitOppo(numFlag){
		var form=Ext.getCmp("commitform").getValue();
		var operate=Ext.getCmp("operate").getValue();
		if(operate=="modify"){
			Ext.getCmp("modifyfacebasic").setValue(numFlag);
			formmit(form);
		}else{
			Ext.getCmp("facebasic").setValue(numFlag);
			formmits(form);
		}
		hideFaceConfirm();
	}
	//数字按钮
	function fuzhi(a){
		var num=Ext.getCmp(a.id).getText();
		var num2=Ext.getCmp(bu2).getText();
		Ext.getCmp(bu2).setText(num);
		if(isNaN(num2)){	
			//变色
			//$(".faceButton_2").css("color","");
			//$(bu2).css("color","blue");
			var faceidss=Ext.getCmp("faceidss").getValue();
			if(Ext.getCmp("faceno").getValue()=="1"){
				var wellIds = Ext.getCmp("addStartDeviceId").getValue();
			}else if(Ext.getCmp("faceno").getValue()=="2"){
				var wellIds = Ext.getCmp("modifyStartDeviceId").getValue();
			}else if(Ext.getCmp("faceno").getValue()=="3"){
				var wellIds = Ext.getCmp("addEndDeviceId").getValue();
			}else if(Ext.getCmp("faceno").getValue()=="4"){
				var wellIds = Ext.getCmp("modifyEndDeviceId").getValue();
			}
			
			var maparr=["0","0","0","0","0","0","0","0"];
	    
	    	for(var i=0;i<8;i++){
	    		var xnum=Ext.getCmp("ox_"+(i+1)).getText();
	    		if(!isNaN(xnum)){
	    			maparr[i]=xnum;
	    		}
	    	}
	    	var wellmap= maparr.join("");
	    	Ext.Ajax.request({
				url : 'pipe!updateWellMap.action',
				method : 'post',
				params : {
					'wellInfo.wellId' : wellIds,
					'wellInfo.wellmap' : wellmap,
					'wellInfo.faceids' : faceidss
				},
				success : function(response) {
					var json = Ext.util.JSON.decode(response.responseText);
					Ext.getCmp("faceidss").setValue(json.faceIds);
				}
			});
		}else{
			Ext.getCmp("f"+num2).setDisabled(false);
		}
		hideFaceNumber();
		Ext.getCmp(a.id).setDisabled(true);
	}
	
	function clearAll(){
		Ext.getCmp("ox_1").setText("北");
		Ext.getCmp("ox_2").setText("东北");
		Ext.getCmp("ox_3").setText("东");
		Ext.getCmp("ox_4").setText("东南");
		Ext.getCmp("ox_5").setText("南");
		Ext.getCmp("ox_6").setText("西南");
		Ext.getCmp("ox_7").setText("西");
		Ext.getCmp("ox_8").setText("西北");
		Ext.getCmp("f1").setDisabled(false);
		Ext.getCmp("f2").setDisabled(false);
		Ext.getCmp("f3").setDisabled(false);
		Ext.getCmp("f4").setDisabled(false);
		Ext.getCmp("f5").setDisabled(false);
		Ext.getCmp("f6").setDisabled(false);
		Ext.getCmp("f7").setDisabled(false);
		Ext.getCmp("f8").setDisabled(false);
	}