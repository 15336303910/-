var site = {
    init:function(){
    	if(document.addEventListener){
    		document.addEventListener("DOMMouseScroll",site.siteEvent.scrollFunc,false);
    	}
    	window.onmousewheel = document.onmousewheel = site.siteEvent.scrollFunc;
    	site.animation();//初始化加载中的动画
    	map = new BMap.Map("allmap");//实例化地图
    	site.auxiliary.initCity();//初始化 加载地市（省）
        $('#sc_normal').prop('disabled',true);//禁用选择正常站点
    },
    siteEvent:{
        scrollFunc:function(e){
            if($("#isViewed").val()=="N"){
                var oldZoom = $("#mapInfo").val();
                e = e || window.event;
                var t1 = document.getElementById("mapInfo");
                var currentZoom = map.getZoom();
                t1.value = currentZoom;
                var regExp = new RegExp("^[0-9]*[1-9][0-9]*$");
                if(regExp.test(currentZoom)){
                    if(currentZoom<oldZoom){
                    	
                    }else{
                        if(currentZoom==zoomValue){
                           site.showOrHideMarker.showMarker();
                        }
                    }
                    if(parseInt($("#mapInfo").val())>=zoomValue){
                        $("#tokenLight").attr({
                            "src":contextPath+"/jsp/firstlink/g_light.png"
                        });
                        var centerPoint = map.getCenter();
                        site.auxiliary.getSites(centerPoint);
                    }else{
                        $("#tokenLight").attr({
                            "src": contextPath+"/jsp/firstlink/r_light.png"
                        });
                    }
                }else{
                    var newZoom = parseInt(currentZoom.split("-")[0])-1;
                    t1.value = newZoom;
                }
            }
        },
        addClickHandler:function(marker) {
            marker.addEventListener("click",function(e){
                var id = marker.id;
                if(acheMap!=null){
                    var ache = acheMap.get(id);
                    if(ache!=null){
                        $("#isViewed").val("Y");
                        $("#TD_REGION_NAME").html(ache["区县"]==null?"-":ache["区县"]);
                        $("#TD_SITE_CODE").html(ache["SITE_CODE"]==null?"-":ache["SITE_CODE"]);
                        $("#TD_SITE_NAME").html(ache["SITE_NAME"]==null?"-":ache["SITE_NAME"]);
                        $("#TD_LONGGITUDE_LATITUDE").html(ache["经纬度"]==null?"-":ache["经纬度"]);
                        $("#TD_SITE_TYPE").html(ache["SITE_TYPE"]==null?"-":ache["SITE_TYPE"]);
                        $("#TD_PROP_CHAR").html(ache["原产权单位"]==null?"-":ache["原产权单位"]);
                        $("#TD_BUSINESS_SCENE").html(ache["业务场景"]==null?"-":ache["业务场景"]);
                        $("#TD_SHARE_UNIT").html(ache["共享单位"]==null?"-":ache["共享单位"]);
                        $("#TD_TOWER").html(ache["铁塔数量"]==null?"-":ache["铁塔数量"]);
                        $("#TD_ROOM").html(ache["机房数量"]==null?"-":ache["机房数量"]);
                        $("#TD_SWITCH").html(ache["开关电源数量"]==null?"-":ache["开关电源数量"]);
                        $("#TD_STORAGE").html(ache["蓄电池组数量"]==null?"-":ache["蓄电池组数量"]);
                        $("#TD_AIR").html(ache["空调数量"]==null?"-":ache["空调数量"]);
                        $("#TD_ENVIR").html(ache["动环数量"]==null?"-":ache["动环数量"]);
                        if(ache["运行状态"]==null){
                            $("#TD_STATE").html("-");
                        }else if(ache["运行状态"]=="正常"){
                            $("#TD_STATE").html("正常");
                        }else if(ache["运行状态"]=="已停电"){
                            $("#TD_STATE").html("<font color='yellow'>已停电</font>");
                        }else if(ache["运行状态"]=="已退服"){
                            $("#TD_STATE").html("<font color='red'>已退服</font>");
                        }
                        $("#TD_RUN_STATE").html(ache["站址运营状态"]==null?"-":ache["站址运营状态"]);
                        $("#TD_TZ_ICB_TOTAL").html(ache["投资合计"]==null?"-":ache["投资合计"]);
                        $("#TD_TOI_CUMULATIVE_TOTAL").html(ache["经营收入"]==null?"-":ache["经营收入"]);
                        $("#TD_TOI_TOWER_RENT_Y").html(ache["塔租收入"]==null?"-":ache["塔租收入"]);
                        $("#TD_TOI_MAINTAIN_FEE_Y").html(ache["维护费收入"]==null?"-":ache["维护费收入"]);
                        $("#TD_TOI_RENTAL_COSTS_Y").html(ache["场租收入"]==null?"-":ache["场租收入"]);
                        $("#TD_TOI_POWER_INTRODUCE_Y").html(ache["电力引入收入"]==null?"-":ache["电力引入收入"]);
                        $("#TD_TOI_OIL_SERVICE_Y").html(ache["油机发电收入"]==null?"-":ache["油机发电收入"]);
                        $("#TD_TOI_NEW_BUSINESS_Y").html(ache["新业务收入"]==null?"-":ache["新业务收入"]);
                        $("#TD_TOC_CUMULATIVE_TOTAL").html(ache["运营成本"]==null?"-":ache["运营成本"]);
                        $("#TD_TOC_MAINTAIN_Y").html(ache["维护成本"]==null?"-":ache["维护成本"]);
                        $("#TD_TOC_SITE_LEASING_Y").html(ache["场租成本"]==null?"-":ache["场租成本"]);
                        $("#TD_TOC_POWER_COST_Y").html(ache["电力成本"]==null?"-":ache["电力成本"]);
                        $("#TD_TOC_OIL_COST_Y").html(ache["油机发电成本"]==null?"-":ache["油机发电成本"]);
                        $("#TD_ZJ_DAD_CUMULATIVE_TOTAL").html(ache["折旧摊销"]==null?"-":ache["折旧摊销"]);
                        $("#TD_GL_ME_CUMULATIVE_TOTAL").html(ache["管理费用"]==null?"-":ache["管理费用"]);
                        $("#TD_FINANCE_COST_Y").html(ache["财务费用"]==null?"-":ache["财务费用"]);
                        $("#TD_SS_GROSS_PROFIT").html(ache["单站毛利"]==null?"-":ache["单站毛利"]);
                        $("#EDIT_PANEL").show(300);
                    }
                }
            });
        },
        addClickHandlerTwo:function(marker){
            marker.addEventListener("click",function(e){
                var id = marker.id;
                if(acheServiceMap!=null){
                    var ache = acheServiceMap.get(id);
                    if(ache!=null){
                        $("#isViewed").val("Y");
                        $("#TD_REGION_NAME").html(ache["区县"]==null?"-":ache["区县"]);
                        $("#TD_SITE_CODE").html(ache["SITE_CODE"]==null?"-":ache["SITE_CODE"]);
                        $("#TD_SITE_NAME").html(ache["SITE_NAME"]==null?"-":ache["SITE_NAME"]);
                        $("#TD_LONGGITUDE_LATITUDE").html(ache["经纬度"]==null?"-":ache["经纬度"]);
                        $("#TD_SITE_TYPE").html(ache["SITE_TYPE"]==null?"-":ache["SITE_TYPE"]);
                        $("#TD_PROP_CHAR").html(ache["原产权单位"]==null?"-":ache["原产权单位"]);
                        $("#TD_BUSINESS_SCENE").html(ache["业务场景"]==null?"-":ache["业务场景"]);
                        $("#TD_SHARE_UNIT").html(ache["共享单位"]==null?"-":ache["共享单位"]);
                        $("#TD_TOWER").html(ache["铁塔数量"]==null?"-":ache["铁塔数量"]);
                        $("#TD_ROOM").html(ache["机房数量"]==null?"-":ache["机房数量"]);
                        $("#TD_SWITCH").html(ache["开关电源数量"]==null?"-":ache["开关电源数量"]);
                        $("#TD_STORAGE").html(ache["蓄电池组数量"]==null?"-":ache["蓄电池组数量"]);
                        $("#TD_AIR").html(ache["空调数量"]==null?"-":ache["空调数量"]);
                        $("#TD_ENVIR").html(ache["动环数量"]==null?"-":ache["动环数量"]);
                        if(ache["运行状态"]==null){
                            $("#TD_STATE").html("-");
                        }else if(ache["运行状态"]=="正常"){
                            $("#TD_STATE").html("正常");
                        }else if(ache["运行状态"]=="已停电"){
                            $("#TD_STATE").html("<font color='yellow'>已停电</font>");
                        }else if(ache["运行状态"]=="已退服"){
                            $("#TD_STATE").html("<font color='red'>已退服</font>");
                        }
                        $("#TD_RUN_STATE").html(ache["站址运营状态"]==null?"-":ache["站址运营状态"]);
                        $("#TD_TZ_ICB_TOTAL").html(ache["投资合计"]==null?"-":ache["投资合计"]);
                        $("#TD_TOI_CUMULATIVE_TOTAL").html(ache["经营收入"]==null?"-":ache["经营收入"]);
                        $("#TD_TOI_TOWER_RENT_Y").html(ache["塔租收入"]==null?"-":ache["塔租收入"]);
                        $("#TD_TOI_MAINTAIN_FEE_Y").html(ache["维护费收入"]==null?"-":ache["维护费收入"]);
                        $("#TD_TOI_RENTAL_COSTS_Y").html(ache["场租收入"]==null?"-":ache["场租收入"]);
                        $("#TD_TOI_POWER_INTRODUCE_Y").html(ache["电力引入收入"]==null?"-":ache["电力引入收入"]);
                        $("#TD_TOI_OIL_SERVICE_Y").html(ache["油机发电收入"]==null?"-":ache["油机发电收入"]);
                        $("#TD_TOI_NEW_BUSINESS_Y").html(ache["新业务收入"]==null?"-":ache["新业务收入"]);
                        $("#TD_TOC_CUMULATIVE_TOTAL").html(ache["运营成本"]==null?"-":ache["运营成本"]);
                        $("#TD_TOC_MAINTAIN_Y").html(ache["维护成本"]==null?"-":ache["维护成本"]);
                        $("#TD_TOC_SITE_LEASING_Y").html(ache["场租成本"]==null?"-":ache["场租成本"]);
                        $("#TD_TOC_POWER_COST_Y").html(ache["电力成本"]==null?"-":ache["电力成本"]);
                        $("#TD_TOC_OIL_COST_Y").html(ache["油机发电成本"]==null?"-":ache["油机发电成本"]);
                        $("#TD_ZJ_DAD_CUMULATIVE_TOTAL").html(ache["折旧摊销"]==null?"-":ache["折旧摊销"]);
                        $("#TD_GL_ME_CUMULATIVE_TOTAL").html(ache["管理费用"]==null?"-":ache["管理费用"]);
                        $("#TD_FINANCE_COST_Y").html(ache["财务费用"]==null?"-":ache["财务费用"]);
                        $("#TD_SS_GROSS_PROFIT").html(ache["单站毛利"]==null?"-":ache["单站毛利"]);
                        $("#EDIT_PANEL").show(300);
                    }
                }
            });
        },
        flushNodesInMap:function(){
            isSearch = true;
            $("#EDIT_PANEL").hide(300);
            $("#isViewed").val("N");
            var zoomCountry = $("#REGIONS_OPTION").val();
            if(zoomCountry!="" && zoomCountry!="-"){
                //如果选择了[区县]，那么就将缩放比例调到14级.
                map.centerAndZoom(zoomCountry,14);
            }else{
                var zoomCity = $("#CITYS_OPTION").val();
                if(zoomCity!="" && zoomCity!="-"){
                    //如果选择了[地市]，那么就将缩放比例调到13级.
                    map.centerAndZoom(zoomCity,13);
                }
            }
            $("#shclNsInMap").show();
            var parameter = {
                cityName:$("#CITYS_OPTION").val(),
                regionName:$("#REGIONS_OPTION").val(),
                runState:$("#RUN_STATE_OPTION").val(),
                siteCode:$("#SITE_CODE_INPUT").val(),
                siteName:$("#SITE_NAME_INPUT").val(),
                siteType:$("#SITE_TYPE_OPTION").val()
            };
            $.ajax({
                type:"POST",
                url:contextPath+"/sitevisibleAction/getSitesByName.ilf",
                dataType:"JSON",
                data:parameter,
                async:true,
                success:function(resultInfo){
                    if(resultInfo["SUCCESS"]){
                        site.emptyArray.emptyAll();
                        if(resultInfo["COUNT"] > 1000){
                            window.wxc.xcConfirm("站点数量最多仅显示1000个,是否显示?",window.wxc.xcConfirm.typeEnum.confirm,{
                                onOk: function(){
                                    site.auxiliary.whereResultAlarm(parameter);
                                },
                                onCancel:function(){
                                    map.centerAndZoom(zoomCity,11);
                                    $("#shclNsInMap").hide();
                                }
                            });
                        }else {
                            site.auxiliary.whereResult(resultInfo);
                        }
                    }
                },
                error:function(){
                    $("#shclNsInMap").hide();
                    window.wxc.xcConfirm("加载失败，请联系管理员!","info");
                }
            });
        },
        ShowStopElecSite:function(){
            if(document.getElementById("sc_stopElsSite").checked==true){
                site.showOrHideMarker.showMarker();
            }else{
                site.showOrHideMarker.hideMarker();
            }
        },
        showStopServiceSite:function(){
            if(document.getElementById("sc_stopServiceSite").checked==true){
                site.showOrHideMarker.showTakeback();
            }else{
                site.showOrHideMarker.hideTakeback();
            }
        },
        showNormal:function(){
            if(document.getElementById("sc_normal").checked==true){
                site.showOrHideMarker.showNormal();
            }else{
                site.showOrHideMarker.hideNormal();
            }
        },
        showWhereNormal:function(){
            if($('#hid_normal').val()=='N'){
                $("#shclNsInMap").show();
                $('#hid_normal').val('Y');
                var parameter = {
                    cityName:$("#CITYS_OPTION").val(),
                    regionName:$("#REGIONS_OPTION").val(),
                    runState:$("#RUN_STATE_OPTION").val(),
                    siteCode:$("#SITE_CODE_INPUT").val(),
                    siteName:$("#SITE_NAME_INPUT").val(),
                    siteType:$("#SITE_TYPE_OPTION").val()
                };
                $.ajax({
                    type:"POST",
                    url:contextPath+"/sitevisibleAction/getSitesWhereAll.ilf",
                    dataType:"JSON",
                    data:parameter,
                    async:true,
                    success:function(resultInfo){
                        if(resultInfo['SUCCESS']){
                            if(parseInt(resultInfo['COUNT']) < 4000){
                            	var datas = resultInfo["SITES"];
                            	if(datas.length<=1000){
                            		if(datas.length>0){
                            			$('#sc_where_normal').prop('checked',true);
                            			var  normalSite = [];//正常站点
                            			for(var i=0;i<datas.length;i++){
                            				var siteInfo = datas[i];
                            				if(acheMap==null){
                            					acheMap = new HashMap();
                            				}
                            				if(!acheMap.containsKey(siteInfo["SITE_CODE"])){
                            					acheMap.put(siteInfo["SITE_CODE"],siteInfo);
                            					var sitePoint = new BMap.Point(
                            						parseFloat(siteInfo["LONGITUDE"]),
                            						parseFloat(siteInfo["LATITUDE"])
                            					);
                            					normalSite.push(sitePoint);
                            				}
                            			}
                            			if(normalSite.length>0){
                            				for(var w=0;w<normalSite.length;w++){
                            					var marker = new BMap.Marker(normalSite[w]);
                            					marker.id = datas[w].SITE_CODE;
                            					normalArray.push(marker);
                            					map.addOverlay(marker);
                            					site.siteEvent.addClickHandler(marker);
                            				}
                            			}
                            		}
                            	}
                            }else{
                                window.wxc.xcConfirm("站点大于4000个,无法打点显示", window.wxc.xcConfirm.typeEnum.info);
                            }
                            $("#shclNsInMap").hide();
                        }else{
                            window.wxc.xcConfirm("查询失败,请联系管理员", window.wxc.xcConfirm.typeEnum.error);
                            $("#shclNsInMap").hide();
                        }
                    }
                });
            }else{
                if(document.getElementById("sc_where_normal").checked==true){
                    site.showOrHideMarker.showNormal();
                }else{
                    site.showOrHideMarker.hideNormal();
                }
            }
        },
        hideMyPanel:function(){
            $("#EDIT_PANEL").hide(300);
            $("#isViewed").val("N");
        },
        showDoingJob:function(){
        	$("#ALARM_DETAIL_PANEL_TITLE").html("<div style='margin-left:5px;margin-top:2px;'>停电站点详情</div>");
        	showEditWindow();
        },
        hideDoingJob : function () {
            $("#DEVELOPINT_TIP").hide(1000);
        }
    },
    auxiliary : {
        initCity : function () {//获取登陆人所在地市（省）信息
            $("#shclNsInMap").show();
             $.ajax({
                type:"POST",
                url:contextPath+"/sitevisibleAction/getUserInfo.ilf",
                dataType:"JSON",
                async:false,
                success:function(data){
                    $("#STOP_ELEC_DIV").html("停电："+data["STOP_ELEC_COUNT"]+"个");
                    $("#QUIT_SERVICE_DIV").html("退服："+data["QUIT_SERVICE_COUNT"]+"个");
                    /*渲染[地市]Select*/
                    var cities = data["CITY"];
                    var innerHtml = "";
                    innerHtml+="<select id='CITYS_OPTION' style='text-indent:7px;border-top-left-radius:5px;border-bottom-left-radius:5px;cursor:pointer;width:130px;height:27px;font-size:12px;border:solid 1px #DFE8F1;margin-right:3px;' onchange='javascript:site.auxiliary.flushRegionOfCity();'>";
                    innerHtml+="	<option value='-'>- 全省  -</option>";
                    for(var i=0;i<cities.length;i++){
                        var cityName = cities[i];
                        innerHtml+="<option value='"+cityName["CITY"]+"'>"+cityName["CITY"]+"</option>";
                    }
                    innerHtml+="</select>";
                    document.getElementById("CITY_CONTAINER").innerHTML = innerHtml;
                    /*渲染[站点类型]Select*/
                    var siteTypes = data["SITE_TYPE"];
                    innerHtml = "";
                    innerHtml+="<select id='SITE_TYPE_OPTION' style='text-indent:7px;cursor:pointer;width:150px;height:27px;font-size:12px;border:solid 1px #DFE8F1;margin-right:3px;'>";
                    innerHtml+="	<option value='-'>请选择站址类型</option>";
                    for(var i=0;i<siteTypes.length;i++){
                        var siteType = siteTypes[i];
                        innerHtml+="<option value='"+siteType["SITE_TYPE"]+"'>"+siteType["SITE_TYPE"]+"</option>";
                    }
                    innerHtml+="</select>";
                    document.getElementById("SITE_TYPE_SELECT").innerHTML = innerHtml;
                    /*[获取地市]并[定位]当前地市：13.*/
                    var cityName = data.area;
                    if(cityName.indexOf("川")!=-1 || cityName.indexOf("省")!=-1){
                        //	cityName = "成都";
                    }else{
                        $("#CITY_CONTAINER").hide(200);
                    }
                    var citySelectObj = document.getElementById("CITYS_OPTION");
                    if(citySelectObj!=null && citySelectObj.options!=null && citySelectObj.options.length>0){
                        for(var i=0;i<citySelectObj.options.length;i++){
                            var optionValue = citySelectObj.options[i].value;
                            if(optionValue.indexOf(cityName)!=-1){
                                citySelectObj.options[i].selected = "selected";
                                break;
                            }
                        }
                        site.auxiliary.flushRegionOfCity();//联动区县
                    }
                    if (cityName.indexOf("川") != -1 || cityName.indexOf("省") != -1) {
                        map.setCurrentCity("康定");
                        map.centerAndZoom("康定",8);

                        /* map.centerAndZoom(new BMap.Point(116.403765,39.914850),9);
                         map.enableScrollWheelZoom();
                         getBoundary();*/

                    } else {

                        map.setCurrentCity(cityName);//设置地图城市
                        map.centerAndZoom(cityName, 13);//设初始化地图
                    }
                    map.enableScrollWheelZoom(true);//开启鼠标滚轮缩放功能。仅对PC上有效
                    map.addControl(new BMap.MapTypeControl({//添加控件
                        mapTypes:[
                            BMAP_NORMAL_MAP,
                            BMAP_HYBRID_MAP
                        ]
                    }));
                    /*添加拖拽事件:Begin.*/
                    map.addEventListener("dragend",function showInfo(){
                        var zoomLevel = $("#mapInfo").val();
                        if(zoomLevel>=zoomValue){
                            var centerPoint = map.getCenter();
                            site.auxiliary.getSites(centerPoint);
                        }
                    });


                     setTimeout("javascript:site.auxiliary.initStopElecSite();",3000);



                },
                error:function(){
                    window.wxc.xcConfirm("加载失败，请联系管理员!", "info");
                }
            });
            
        },
        flushRegionOfCity : function () {//联动区县
            var CITY_NAME = $("#CITYS_OPTION").val();
            if(CITY_NAME=="-"){
                var innerHtml = "";
                innerHtml+="<select id='REGIONS_OPTION' style='text-indent:7px;cursor:pointer;width:130px;height:27px;font-size:12px;border:solid 1px #DFE8F1;margin-right:3px;'>";
                innerHtml+="	<option value='-'>全地市</option>";
                innerHtml+="</select>";
                document.getElementById("REGION_CONTAINER").innerHTML = innerHtml;
            }else{
                $("#shclNsInMap").show();
                $.ajax({
                    type:"POST",
                    url:contextPath+"/sitevisibleAction/flushRegionOfCity.ilf",
                    dataType:"JSON",
                    data:"cityName="+$("#CITYS_OPTION").val(),
                    async:false,
                    success:function(resultObj){
                        $("#shclNsInMap").hide();
                        if(resultObj["SUCCESS"]){
                            var regions = resultObj["REGION"];
                            var innerHtml = "";
                            innerHtml+="<select id='REGIONS_OPTION' style='text-indent:7px;cursor:pointer;width:130px;height:27px;font-size:12px;border:solid 1px #DFE8F1;margin-right:3px;'>";
                            innerHtml+="	<option value='-'>全地市</option>";
                            for(var i=0;i<regions.length;i++){
                                var regionName = regions[i];
                                innerHtml+="<option value='"+regionName["REGION_ID"]+"'>"+regionName["REGION_ID"]+"</option>";
                            }
                            innerHtml+="</select>";
                            document.getElementById("REGION_CONTAINER").innerHTML = innerHtml;
                        }
                    }
                });
            }
        },
        initStopElecSite : function () {//初始化停电站点
            $.ajax({
                type:"POST",
                url:contextPath+"/sitevisibleAction/getStopElecSites.ilf",
                dataType:"JSON",
                async:false,
                success:function(resultInfo){
                    if(resultInfo["SUCCESS"]){
                        var datas = resultInfo["SITES"];
                        if(datas.length>0){
                            var sites = [];
                            for(var i=0;i<datas.length;i++){
                                var siteInfo = datas[i];
                                if(acheMap==null){
                                    acheMap = new HashMap();
                                    acheMap.put(siteInfo["SITE_CODE"],siteInfo);
                                    var sitePoint = new BMap.Point(
                                        parseFloat(siteInfo["LONGITUDE"]),
                                        parseFloat(siteInfo["LATITUDE"])
                                    );
                                    sites.push(sitePoint);
                                }else{
                                    if(!acheMap.containsKey(siteInfo["SITE_CODE"])){
                                        acheMap.put(siteInfo["SITE_CODE"],siteInfo);
                                        var sitePoint = new BMap.Point(
                                            parseFloat(siteInfo["LONGITUDE"]),
                                            parseFloat(siteInfo["LATITUDE"])
                                        );
                                        sites.push(sitePoint);
                                    }
                                }
                            }
                            if(sites.length>0){
                                for(var w=0;w<sites.length;w++){
                                    var myIcon = new BMap.Icon(
                                        "/DataCenter/jsp/firstlink/eleclost_25X25.png",
                                        new BMap.Size(20,20)
                                    );
                                    var marker = new BMap.Marker(sites[w],{icon:myIcon});
                                    marker.id = datas[w].SITE_CODE;

                                    if(acheMap!=null && acheMap.containsKey(datas[w].SITE_CODE)){
                                        var siteInfo = acheMap.get(datas[w].SITE_CODE);
                                        marker.setLabel(new BMap.Label(siteInfo.SITE_NAME,{
                                            offset:new BMap.Size(20,0)
                                        }));
                                    }
                                    map.addOverlay(marker);
                                    markerArray.push(marker);
                                    site.siteEvent.addClickHandler(marker);
                                }
                            }
                        }
                        site.auxiliary.initStopServiceSite();
                    }
                }
            });

        },
        initStopServiceSite : function () { //初始化退服站点
            $.ajax({
                type:"POST",
                url:contextPath+"/sitevisibleAction/getStopServiceSites.ilf",
                dataType:"JSON",
                async:false,
                success:function(resultInfo){
                    $("#shclNsInMap").hide();
                    if(resultInfo["SUCCESS"]){
                        //清空map
                       /* acheMap.length = 0;
                        acheMap = new HashMap();*/
                        var datas = resultInfo["SITES"];
                        if(datas.length>0){
                            var sites = [];
                            for(var i=0;i<datas.length;i++){
                                var siteInfo = datas[i];
                                if(acheServiceMap==null){
                                    acheServiceMap = new HashMap();
                                    acheServiceMap.put(siteInfo["SITE_CODE"],siteInfo);
                                    var sitePoint = new BMap.Point(
                                        parseFloat(siteInfo["LONGITUDE"]),
                                        parseFloat(siteInfo["LATITUDE"])
                                    );
                                    sites.push(sitePoint);
                                }else{
                                    if(!acheServiceMap.containsKey(siteInfo["SITE_CODE"])){
                                        acheServiceMap.put(siteInfo["SITE_CODE"],siteInfo);
                                        var sitePoint = new BMap.Point(
                                            parseFloat(siteInfo["LONGITUDE"]),
                                            parseFloat(siteInfo["LATITUDE"])
                                        );
                                        sites.push(sitePoint);
                                    }
                                }
                            }
                            if(sites.length>0){
                                for(var w=0;w<sites.length;w++){
                                    var myIcon = new BMap.Icon(
                                        "/DataCenter/jsp/firstlink/quitservice_25X25.png",
                                        new BMap.Size(25,25)
                                    );
                                    var marker = new BMap.Marker(sites[w],{icon:myIcon});
                                    marker.id = datas[w].SITE_CODE;
                                    map.addOverlay(marker);
                                    takebackArray.push(marker);
                                    site.siteEvent.addClickHandlerTwo(marker);
                                }
                        }
                        }
                    };
                    //初始化 隐藏报警站点
                    $('#sc_stopElsSite').prop('checked',false);
                    site.showOrHideMarker.hideMarker();
                    $('#sc_stopServiceSite').prop('checked',false);
                    site.showOrHideMarker.hideTakeback();
                }
            });
            
        },
        getSites : function (centerPoint) {
            $("#shclNsInMap").show();
            $.ajax({
                type: "POST",
                url: contextPath+"/sitevisibleAction/getSites.ilf",
                dataType: "JSON",
                data : {zoomLevel:$("#mapInfo").val(),latitude:centerPoint.lat,longitude:centerPoint.lng,siteType:$("#SITE_TYPE_OPTION").val()},
              //  data: "zoomLevel=" + $("#mapInfo").val() + "&latitude=" + centerPoint.lat + "&longitude=" + centerPoint.lng + "&siteType=" + $("#SITE_TYPE_OPTION").val(),
                async: true,
                success: function (resultInfo) {
                    if (resultInfo["SUCCESS"]) {
                        var datas = resultInfo["SITES"];
                        if (datas.length > 0) {
                            //清空所有数组
                            site.emptyArray.emptyAll();
                            $('#sc_stopElsSite').prop('checked',true);
                            $('#sc_stopServiceSite').prop('checked',true);
                            $('#sc_normal').prop('disabled',false);//放开正常站点
                            $('#sc_normal').prop('checked',true);//选中正常站点
                            /*从站点信息提取经纬度并组成：BMap.Point.*/
                            var stopElesites = [];//断电站点
                            var stopServiceSite = [];//退服站点
                            var  normalSite = [];//正常站点
                            for (var i = 0; i < datas.length; i++) {
                                var siteInfo = datas[i];
                                if (acheMap == null) {
                                    acheMap = new HashMap();
                                    acheMap.put(siteInfo["SITE_CODE"], siteInfo);
                                    var sitePoint = new BMap.Point(
                                        parseFloat(siteInfo["LONGITUDE"]),
                                        parseFloat(siteInfo["LATITUDE"])
                                    );
                                    if(siteInfo['运行状态'] == '已停电'){
                                        stopElesites.push(sitePoint);
                                    };
                                    if(siteInfo['运行状态'] == '已退服'){
                                        stopServiceSite.push(sitePoint);
                                    };
                                    if(siteInfo['运行状态'] == '正常'){
                                        normalSite.push(sitePoint);
                                    };

                                } else {
                                    if (!acheMap.containsKey(siteInfo["SITE_CODE"])) {
                                        acheMap.put(siteInfo["SITE_CODE"], siteInfo);
                                        var sitePoint = new BMap.Point(
                                            parseFloat(siteInfo["LONGITUDE"]),
                                            parseFloat(siteInfo["LATITUDE"])
                                        );
                                        if(siteInfo['运行状态'] == '已停电'){
                                            stopElesites.push(sitePoint);
                                        };
                                        if(siteInfo['运行状态'] == '已退服'){
                                            stopServiceSite.push(sitePoint);
                                        };
                                        if(siteInfo['运行状态'] == '正常'){
                                            normalSite.push(sitePoint);
                                        };
                                    }
                                }
                            };
                         //断电

                            if(stopElesites.length>0){
                                for(var w=0;w<stopElesites.length;w++){
                                    var myIcon = new BMap.Icon(
                                        "/DataCenter/jsp/firstlink/eleclost_25X25.png",
                                        new BMap.Size(20,20)
                                    );
                                    var marker = new BMap.Marker(stopElesites[w],{icon:myIcon});
                                    marker.id = datas[w].SITE_CODE;

                                    if(acheMap!=null && acheMap.containsKey(datas[w].SITE_CODE)){
                                        var siteInfo = acheMap.get(datas[w].SITE_CODE);
                                        marker.setLabel(new BMap.Label(siteInfo.SITE_NAME,{
                                            offset:new BMap.Size(20,0)
                                        }));
                                    }
                                    map.addOverlay(marker);
                                    markerArray.push(marker);
                                    site.siteEvent.addClickHandler(marker);
                                }
                            }
                          //退服
                            if(stopServiceSite.length>0){
                                for(var w=0;w<stopServiceSite.length;w++){
                                    var myIcon = new BMap.Icon(
                                        "/DataCenter/jsp/firstlink/quitservice_25X25.png",
                                        new BMap.Size(25,25)
                                    );
                                    var marker = new BMap.Marker(stopServiceSite[w],{icon:myIcon});
                                    marker.id = datas[w].SITE_CODE;
                                    map.addOverlay(marker);
                                    takebackArray.push(marker);
                                    site.siteEvent.addClickHandler(marker);
                                }
                            }


                           //正常
                            if (normalSite.length > 0) {
                                $('#sc_normal').prop('disabled',false);//放开选择正常站点
                                // $('#sc_normal').prop('checked',true);
                                //选中
                                for (var w = 0; w < normalSite.length; w++) {
                                    var marker = new BMap.Marker(normalSite[w]);
                                    marker.id = datas[w].SITE_CODE;
                                   /* marker.setLabel(new BMap.Label(datas[w].SITE_NAME, {
                                        offset: new BMap.Size(15, 0)
                                    }));*/
                                    normalArray.push(marker);
                                    map.addOverlay(marker);
                                    site.siteEvent.addClickHandler(marker);
                                };
                                //site.showOrHideMarker.hideNormal();//隐藏正常站点
                            };
                        }
                    }
                    $("#shclNsInMap").hide();
                },
                error: function () {
                    window.wxc.xcConfirm("加载失败，请联系管理员!", "info");
                }
            });

        },
        whereResult : function (resultInfo) {
        	/*停电站址总数*/
            $("#STOP_ELEC_DIV").html("停电："+resultInfo["STOP_ELECTRIC"]+"个");
            /*退服站址总数*/
            $("#QUIT_SERVICE_DIV").html("退服："+resultInfo["STOP_SERVICES"]+"个");
            var datas = resultInfo["SITES"];
            if(datas.length>0){
                $('#sc_stopElsSite').prop('checked',true);
                $('#sc_stopServiceSite').prop('checked',true);
                var stopElesites = [];//断电站点
                var stopElesitesCode = [];//断电站点(存放code)
                var stopServiceSite = [];//退服站点
                var stopServiceSiteCode = [];//退服站点(存放code)
                var  normalSite = [];//正常站点
                var  normalSiteCode = [];//正常站点(存放code)
                for(var i=0;i<datas.length;i++){
                    var siteInfo = datas[i];
                    if(acheMap==null){
                        acheMap = new HashMap();
                    }
                    if(!acheMap.containsKey(siteInfo["SITE_CODE"])){
                        acheMap.put(siteInfo["SITE_CODE"],siteInfo);
                        var sitePoint = new BMap.Point(
                            parseFloat(siteInfo["LONGITUDE"]),
                            parseFloat(siteInfo["LATITUDE"])
                        );
                        if(siteInfo['运行状态'] == '已停电'){
                            stopElesites.push(sitePoint);
                            stopElesitesCode.unshift(siteInfo["SITE_CODE"]);
                        };
                        if(siteInfo['运行状态'] == '已退服'){
                            stopServiceSite.push(sitePoint);
                            stopServiceSiteCode.unshift(siteInfo["SITE_CODE"]);
                        };
                        if(siteInfo['运行状态'] == '正常'){
                            normalSite.push(sitePoint);
                            normalSiteCode.unshift(siteInfo["SITE_CODE"]);
                        };
                        if(i==0 && datas.length==1){
                        	/*聚焦*/
                        	map.clearOverlays();
                        	var singleMarker = null;
                        	if(siteInfo['运行状态'] == '已停电'){
                                var myIcon = new BMap.Icon(
                                    "/DataCenter/jsp/firstlink/eleclost_25X25.png",
                                    new BMap.Size(25,25)
                                );
                            	singleMarker = new BMap.Marker(sitePoint,{icon:myIcon});
                            }else if(siteInfo['运行状态'] == '已退服'){
                            	var myIcon = new BMap.Icon(
                                    "/DataCenter/jsp/firstlink/quitservice_25X25.png",
                                    new BMap.Size(25,25)
                                );
                            	singleMarker = new BMap.Marker(sitePoint,{icon:myIcon});
                            }else if(siteInfo['运行状态'] == '正常'){
                            	singleMarker = new BMap.Marker(sitePoint);
                            }
                        	singleMarker.id = siteInfo["SITE_CODE"];
                        	singleMarker.setLabel(new BMap.Label(siteInfo.SITE_NAME,{
                                offset:new BMap.Size(20,0)
                            }));
                			map.addOverlay(singleMarker);
                			initSiteInfoForSingle(singleMarker);
                        	/*其他隐藏参数*/
                			$("#mapInfo").val(16);
                			map.centerAndZoom(sitePoint,16);
                        	$("#tokenLight").attr({
                                "src":contextPath+"/jsp/firstlink/g_light.png"
                            });
                        }else{
                        	if(($("#CITYS_OPTION").val()=="-" && $("#REGIONS_OPTION").val()=="-") && ((i==0 && datas.length==1) || (i==1 && datas.length==2))){
                                $("#mapInfo").val(18);
                                map.centerAndZoom(sitePoint,18);
                                $("#tokenLight").attr({
                                    "src":contextPath+"/jsp/firstlink/g_light.png"
                                });
                            }else{
                                if($("#CITYS_OPTION").val()=="-" && $("#REGIONS_OPTION").val()=="-"){
                                    var checkedIndex = parseInt(datas.length/2);
                                    if(i==checkedIndex){
                                        $("#mapInfo").val(13);
                                        map.centerAndZoom(sitePoint,13);
                                    }
                                    $("#tokenLight").attr({
                                        "src":contextPath+"/jsp/firstlink/r_light.png"
                                    });
                                }
                            }
                        }
                    }
                }
                if(stopElesites.length>0){
                    for(var w=0;w<stopElesites.length;w++){
                        var myIcon = new BMap.Icon(
                            "/DataCenter/jsp/firstlink/eleclost_25X25.png",
                            new BMap.Size(20,20)
                        );
                        var marker = new BMap.Marker(stopElesites[w],{icon:myIcon});
                        marker.id = stopElesitesCode[w];

                        if(acheMap!=null && acheMap.containsKey(stopElesitesCode[w])){
                            var siteInfo = acheMap.get(stopElesitesCode[w]);
                            marker.setLabel(new BMap.Label(siteInfo.SITE_NAME,{
                                offset:new BMap.Size(20,0)
                            }));
                        }
                        map.addOverlay(marker);
                        markerArray.push(marker);
                        site.siteEvent.addClickHandler(marker);
                    }
                }
                if(stopServiceSite.length>0){
                    for(var w=0;w<stopServiceSite.length;w++){
                        var myIcon = new BMap.Icon(
                            "/DataCenter/jsp/firstlink/quitservice_25X25.png",
                            new BMap.Size(25,25)
                        );
                        var marker = new BMap.Marker(stopServiceSite[w],{icon:myIcon});
                        marker.id = stopServiceSiteCode[w];
                        map.addOverlay(marker);
                        takebackArray.push(marker);
                        site.siteEvent.addClickHandler(marker);
                    }
                }
                if(normalSite.length>0){
                    $('#sc_normal').prop('disabled',false);//放开选择正常站点
                    $('#sc_normal').prop('checked',false);//选中
                    for (var w = 0; w < normalSite.length; w++) {
                        var marker = new BMap.Marker(normalSite[w]);
                        marker.id = normalSiteCode[w];
                        normalArray.push(marker);
                        map.addOverlay(marker);
                        site.siteEvent.addClickHandler(marker);
                    }
                    site.showOrHideMarker.hideNormal();
                };
                $("#shclNsInMap").hide();
                var totalCount = resultInfo["COUNT"];
                if(totalCount>50){
                    $("#tipDiv").html("根据查询条件共找到"+totalCount+"个站点.");
                }else{
                    $("#tipDiv").html("根据查询条件共找到"+totalCount+"个站点.");
                }
                $("#SUCCESS_TIP").fadeIn(500);
                setTimeout("$('#SUCCESS_TIP').fadeOut(1000);",5000);
            }else{
                $("#shclNsInMap").hide();
                window.wxc.xcConfirm("根据查询条件未找到相应的站址.",window.wxc.xcConfirm.typeEnum.info);
            }
        },
        whereResultAlarm : function (parameter) {//添加查询大与1000时查询告警站点
          $.ajax({
              type:"POST",
              url:contextPath+"/sitevisibleAction/getSitesAlarm.ilf",
              dataType:"JSON",
              data:parameter,
              async:true,
              success : function (resultInfo) {
                  if (resultInfo['SUCCESS']) {
                      $('#show_normal').hide();
                      $('#show_where_normal').show();

                      $("#STOP_ELEC_DIV").html("停电：" + resultInfo["STOP_ELECTRIC"] + "个");
                      $("#QUIT_SERVICE_DIV").html("退服：" + resultInfo["STOP_SERVICES"] + "个");
                      var datas = resultInfo["SITES"];
                      if (datas.length > 0) {
                          $('#sc_stopElsSite').prop('checked', true);
                          $('#sc_stopServiceSite').prop('checked', true);
                          /*隐藏现有Marker.*/
                          //hideMarker();
                          var stopElesites = [];//断电站点
                          var stopElesitesCode = [];//断电站点(存放code)
                          var stopServiceSite = [];//退服站点
                          var stopServiceSiteCode = [];//退服站点(存放code)

                          for (var i = 0; i < datas.length; i++) {
                              var siteInfo = datas[i];
                              if (acheMap == null) {
                                  acheMap = new HashMap();
                              }
                              if (!acheMap.containsKey(siteInfo["SITE_CODE"])) {
                                  acheMap.put(siteInfo["SITE_CODE"], siteInfo);
                                  var sitePoint = new BMap.Point(
                                      parseFloat(siteInfo["LONGITUDE"]),
                                      parseFloat(siteInfo["LATITUDE"])
                                  );
                                  if (siteInfo['运行状态'] == '已停电') {
                                      stopElesites.push(sitePoint);
                                      stopElesitesCode.unshift(siteInfo["SITE_CODE"]);
                                  }
                                  ;
                                  if (siteInfo['运行状态'] == '已退服') {
                                      stopServiceSite.push(sitePoint);
                                      stopServiceSiteCode.unshift(siteInfo["SITE_CODE"]);
                                  }
                                  ;


                              }
                          }

                          //断电

                          if (stopElesites.length > 0) {
                              for (var w = 0; w < stopElesites.length; w++) {
                                  var myIcon = new BMap.Icon(
                                      "/DataCenter/jsp/firstlink/eleclost_25X25.png",
                                      new BMap.Size(20, 20)
                                  );
                                  var marker = new BMap.Marker(stopElesites[w], {icon: myIcon});
                                  marker.id = stopElesitesCode[w];

                                  if (acheMap != null && acheMap.containsKey(stopElesitesCode[w])) {
                                      var siteInfo = acheMap.get(stopElesitesCode[w]);
                                      marker.setLabel(new BMap.Label(siteInfo.SITE_NAME, {
                                          offset: new BMap.Size(20, 0)
                                      }));
                                  }
                                  map.addOverlay(marker);
                                  markerArray.push(marker);
                                  site.siteEvent.addClickHandler(marker);
                              }
                          }
                          //退服
                          if (stopServiceSite.length > 0) {
                              for (var w = 0; w < stopServiceSite.length; w++) {
                                  var myIcon = new BMap.Icon(
                                      "/DataCenter/jsp/firstlink/quitservice_25X25.png",
                                      new BMap.Size(25, 25)
                                  );
                                  var marker = new BMap.Marker(stopServiceSite[w], {icon: myIcon});
                                  marker.id = stopServiceSiteCode[w];
                                  map.addOverlay(marker);
                                  takebackArray.push(marker);
                                  site.siteEvent.addClickHandler(marker);
                              }
                          }

                          $("#shclNsInMap").hide();

                          /*处理数量信息*/
                          var totalCount = resultInfo["COUNT"];
                          if (totalCount > 50) {
                              $("#tipDiv").html("根据查询条件共找到" + totalCount + "个站点.");
                          } else {
                              $("#tipDiv").html("根据查询条件共找到" + totalCount + "个站点.");
                          }
                          $("#SUCCESS_TIP").fadeIn(500);
                          setTimeout("$('#SUCCESS_TIP').fadeOut(1000);", 5000);
                      } else {
                          $("#shclNsInMap").hide();

                          window.wxc.xcConfirm("根据查询条件未找到相应的站址.", window.wxc.xcConfirm.typeEnum.info);
                      }

                  }else{
                      window.wxc.xcConfirm("查询失败,请联系管理员", window.wxc.xcConfirm.typeEnum.error);
                      $("#shclNsInMap").hide();

                  }
              }
              })

        }

    },
    showOrHideMarker : {
        showMarker : function () {//显示断电站点
            if(markerArray.length>0){
                for(var i=0;i<markerArray.length;i++){
                    var $marker = markerArray[i];
                    $marker.show();
                }
            }
        },
        hideMarker : function () {//隐藏断电站点
            if(markerArray.length>0){
                for(var i=0;i<markerArray.length;i++){
                    var $marker = markerArray[i];
                    $marker.hide();
                }
            }
        },
        showTakeback : function () {//显示退服站点
            if(takebackArray.length>0){
                for(var i=0;i<takebackArray.length;i++){
                    var $marker = takebackArray[i];
                    $marker.show();
                }
            }
        },
        hideTakeback : function () {//隐藏退服站点
            if(takebackArray.length>0){
                for(var i=0;i<takebackArray.length;i++){
                    var $marker = takebackArray[i];
                    $marker.hide();
                }
            }
        },
        showNormal : function () {//显示正常站点
            if(normalArray.length>0){
                for(var i=0;i<normalArray.length;i++){
                    var $marker = normalArray[i];
                    $marker.show();
                }
            }
            
        },
        hideNormal : function () {//隐藏正常站点
            if(normalArray.length>0){
                for(var i=0;i<normalArray.length;i++){
                    var $marker = normalArray[i];
                    $marker.hide();
                }
            }
            
        }
    },
    emptyArray :  {//清空数组

        emptyAll : function () {
            map.clearOverlays();//清楚所有覆盖物
            if(acheMap!=null){
            	acheMap.length = 0;//清空map
            }
            acheMap = new HashMap();
            if(acheServiceMap!=null){
            	acheServiceMap.length = 0;//清空map
            }
            acheServiceMap = new HashMap();
            markerArray.splice(0,markerArray.length);
            takebackArray.splice(0,takebackArray.length);
            normalArray.splice(0,normalArray.length)
            $('#show_normal').show();
            $('#show_where_normal').hide();
            $('#show_where_normal').prop('checked',false);
            $('#hid_normal').val('N');

        }
    },
    animation : function () {//加载动画
        $("#shclNsInMap").shCircleLoader({//加载中的动画效果
            namespace:"myns",
            color:"#6BC2EB",
            dotsRadius:15
        })
        
    }
    

}