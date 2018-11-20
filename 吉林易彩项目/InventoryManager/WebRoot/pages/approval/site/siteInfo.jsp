<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>站点拓扑</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/pages/approval/site/css/base.css">
  	<link href="${pageContext.request.contextPath}/js/jquery/jtopo/snippet/jquery.snippet.min.css" rel="stylesheet">
    <style type="text/css">
	#contextmenu {
		border: 1px solid #aaa;
		border-bottom: 0;
		background: #eee;
		position: absolute;
		list-style: none;
		margin: 0;
		padding: 0;
		display: none;
	}
																			   
	#contextmenu li a {
		display: block;
		padding: 10px;
		border-bottom: 1px solid #aaa;
		cursor: pointer;
	}
																			   
	#contextmenu li a:hover {
		background: #fff;
	} 
	div.panel {
  		color: white;
  		background: rgba(51, 122, 183, 0.5);
  		position: absolute;
	}
	</style>	
   
    <script type="text/javascript">
    	var id =  '<%=request.getParameter("id") %>';
		var type = '<%=request.getParameter("resType") %>';
		var grapType = '<%=request.getParameter("grapType") %>';
		var parentId = '<%=request.getParameter("parentId") %>';
		var parentType = '<%=request.getParameter("parentType") %>';
		var context_path = '${pageContext.request.contextPath}';
	</script>
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jtopo/snippet/jquery.snippet.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jtopo/jtopo-min.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jtopo/jtopo.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/approval/map.js" charset="utf-8"></script>
	<script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/pages/approval/site/js/toolbar.js"></script>
	<script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/pages/approval/site/js/siteInfo.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/zDialog/zDrag.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/zDialog/zDialog.js"></script>
	<script type="text/javascript">
		var canvas;
		var state;
		var scene;
		var winWidth ;
		var winHeight ;
		var temp = $(document);
		$(document).ready(function() {
			
			if (window.innerWidth){
				winWidth = window.innerWidth;
			}else if ((document.body) && (document.body.clientWidth)){
				winWidth = document.body.clientWidth;
			}
			if (window.innerHeight){
				winHeight = window.innerHeight;
			}else if ((document.body) && (document.body.clientHeight)){
				winHeight = document.body.clientHeight;
			}
			if (document.documentElement && document.documentElement.clientHeight && document.documentElement.clientWidth)
			{
				winHeight = document.documentElement.clientHeight;
				winWidth = document.documentElement.clientWidth;
			}
			
			var currentNode = null;
			var siteName,nodeId;
			var currentNode = null;
			canvas = document.getElementById('canvas'); 
			canvas.width = winWidth;
			canvas.height = winHeight;
            stage = new JTopo.Stage(canvas); // 创建一个舞台对象
            scene = new JTopo.Scene(stage); // 创建一个场景对象
            scene.background = context_path+'/pages/approval/site/img/bg.jpg';
            stage.add(scene);
            showJTopoToobar(stage);
            /* function handler(event){
                if(event.button == 2){
                  $("#contextmenu").css({
                    top: event.pageY,
                    left: event.pageX
                  }).show();  
                }
              }
            stage.click(function(event){
              if(event.button == 0){
                $("#contextmenu").hide();
              }
            }); */
            if(type =='optical'){
            	getOpticalTopo(scene,id,type);
            }
            //当站点字段
            if ( parentType =='null' && type !='optical') {
            	getSiteTopo(scene,id,type);
            }
            //如果是
            //得到站点下面的机房信息
            if(parentType =='station'){
            	getGeneratorTopo(parentId,parentType);
            }
            //得到机房下面的机架信息
            if(parentType =='generator'){
            	getEqutTopo(parentId,parentType);
            }
            //得到机架信息
            if(parentType =='rack'){
            	getRackTopo(parentId,parentType);
            }
            //得到ODF面板信息
            if(parentType =='odm'){
            	getOdmTopo(parentId,parentType);
            }
            //得到光交箱的数据
            if(parentType =='optical'){
            	getRackTopo(parentId,parentType);
            }
            //进行鼠标移动
            scene.mousemove(function(e){
            	mouseShow(e);
            });
		});
	</script>
  </head>
  	<body >
  		<div id="content">
          <canvas width="850" height="550" id="canvas"></canvas>  
          <div name="topo_tips" class="panel panel-default" id="topoTips">
    			<div class="panel-body"></div>
  		  </div>
        </div>
	</body>
</html>
