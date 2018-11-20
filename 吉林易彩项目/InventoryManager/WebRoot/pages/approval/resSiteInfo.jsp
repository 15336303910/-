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
    
    <title>站点管理</title>
    
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
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jtopo/jtopo-0.4.8-min.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jtopo/jtopo.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/zDialog/zDrag.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/zDialog/zDialog.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			var currentNode = null;
			var siteName,nodeId;
			var canvas = document.getElementById('canvas'); 
			canvas.width = 500;
			canvas.height = 500;
            var stage = new JTopo.Stage(canvas); // 创建一个舞台对象
            var scene = new JTopo.Scene(stage); // 创建一个场景对象
            //scene.background = context_path+'/images/canvas/bg.jpg';
            
            if (type == "station") {
            	$.ajax({
    				type:'get',
    				url:context_path+'/approvalAction!getSiteTree.action?siteId='+id+'&&type='+type,
    				dataType:'json',
    				success:function(result) {
    					siteName = result.stationName;
    					nodeId = result.stationBaseId;
    					
    					var node = newNode(130,90, 50, 50, siteName, context_path+'/images/canvas/'+type+'.png');    // 创建一个节点
    		            node.id = nodeId;
    					node.resType = result.resType;
    		            scene.add(node); // 放入到场景中
    		            
    		            node.addEventListener('mouseup', function(event){
    		                currentNode = this;
    		                handler(event);
    		            });
    		            

    		            node.addEventListener("dbclick", function() {
    		            	scene.clear();
    		            	window.location.href = context_path + "/pages/approval/resSiteInfo.jsp?parentId="+node.id+"&&parentType="+type;
    		            });
    				}
    			});
            } else {
            	$.ajax({
            		type:'get',
            		url:context_path+'/approvalAction!getResTree.action?parentId='+parentId+'&&parentType='+parentType,
					dataType:'json',
					success:function(result) {
						var obj = eval("("+result+")");
						var xPosition = 130;
						var yPosition = 30;
						var sequence1 = 1;
						var sequence2 = 1;
						for (var i=0; i<obj.length; i++) {
							if (i == 0) {
								xPosition = 130;
								yPosition = 30;	
							} else if (i == 1) {
								xPosition = 400;
								yPosition = 60;
							} else if (i%2 == 0) {
								sequence1++;
								xPosition = 130;
								yPosition = 90*sequence1;
							} else if (i%2 != 0) {
								sequence2++;
								xPosition = 400;
								yPosition = 100*sequence2;
							}
							addNode(i,xPosition,yPosition, obj[i].text, obj[i].id, obj[i].resType);
						}
					}
            	});
            }
			
			
			stage.click(function(event){
                if(event.button == 0){// 右键
                    // 关闭弹出菜单（div）
                    $("#contextmenu").hide();
                }
            });
			
			$("#contextmenu a").click(function(){
                var text = $(this).text();
                
                if(text == '查看详情'){
                	var nodeType = currentNode.resType;
	        		var url;
	        		if(nodeType == undefined){
	        			url = context_path+"/pages/approval/resPoint.jsp?id="+currentNode.id+"&resType=station&grapType=point";
	        		}else{
	        			url = context_path+"/pages/approval/resPoint.jsp?id="+currentNode.id+"&resType="+currentNode.resType+"&grapType=point";
	        		}
	        		var pointDialog = new Dialog();
	        		pointDialog.Width = 300;
	        		pointDialog.Height = 300;
	        		pointDialog.Title = "资源详情页面";
	        		pointDialog.URL = url;
	        		pointDialog.show();

	        		//window.open(url, '查看详情', 'height=' + '300' + ',innerHeight=' + '300' + ',width=' + '400' + ',innerWidth=' + '400' + ',top=' + '200' + ',left=' + '600' + ',status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=4,titlebar=no');
                    currentNode = null;
                } else if (text == "查看图片") {
                	var nodeType = currentNode.resType;
                	var url;
                	if(nodeType == undefined){
	        			url = context_path+"/pages/approval/resImageInfo.jsp?id="+currentNode.id+"&resType=station";
	        		}else{
	        			url = context_path+"/pages/approval/resImageInfo.jsp?id="+currentNode.id+"&resType="+currentNode.resType;
	        		}
                	var pointDialog = new Dialog();
	        		pointDialog.Width = 300;
	        		pointDialog.Height = 300;
	        		pointDialog.Title = "资源图片页面";
	        		pointDialog.URL = url;
	        		pointDialog.show();
                } else if (text == "返回上一层") {
                	window.history.back();
                	window.location.reload();
                }
                $("#contextmenu").hide();
            });
			
			//想容器中添加node
			function addContainerNode(i, id,name, type) {
				//截取字符串获得端子号
				if (type == "nePoint") {
					var x = name.indexOf("_")+1;
					var y = name.indexOf("_号");
					name = name.substring(x,y)+"号";
				} else if (type == "odmPoint") {
					name = name.substring(name.length-4,name.length);
				} 
				
				var node1 = new JTopo.Node(name);
				node1.textPosition = "Middle_Center";
				node1.id = id;
				node1.resType = type;
				node1.setImage(context_path+'/images/canvas/point.png');
				
				node1.addEventListener('mouseup', function(event){
	                currentNode = this;
	                handler(event);
	            });
				
				return node1;
			}
            
			function addNode(index,xPosition, yPosition, name, id, resType) {
	            if (resType != "odm" && resType != "neCard") {
	            	var node = newNode(xPosition, yPosition, 50, 50, name, context_path+'/images/canvas/'+resType+'.png');    // 创建一个节点
	            	node.id = id;
	            	node.resType = resType;
		            scene.add(node); // 放入到场景中
		            
		            node.addEventListener('mouseup', function(event){
		                currentNode = this;
		                handler(event);
		            });
		            
	            	node.addEventListener("dbclick", function() {
		            	scene.clear();
		            	window.location.href = context_path + "/pages/approval/resSiteInfo.jsp?parentId="+id+"&&parentType="+resType;
		            }); 
	            } else if (resType == "odm" || resType == "neCard") {
	            	// 流式布局（水平、垂直间隔均为4,12)
	                var flowLayout = JTopo.layout.FlowLayout(4, 12);
					//创建容器
	                var container = new JTopo.Container(name);
	                container.layout = flowLayout;
	                container.fillColor = '100,10,100';
	                container.font = '18pt 微软雅黑';
	                container.setBound(30, 30+index*220, 440, 180); 
	                container.id = id;
	                container.resType = resType;
	                
	                container.addEventListener('mouseup', function(event){
		                currentNode = this;
		                handler(event);
	                });
	                scene.add(container);
	                
	                $.ajax({
	                	type:'get',
	                	url:context_path+'/approvalAction!getResTree.action?parentId='+id+'&&parentType='+resType,
	           			dataType:'json',
	           			success:function(result) {
	           				var obj1 = eval("("+result+")");
	           				for (var i=0; i<obj1.length; i++) {
	           					var node1 = addContainerNode(i, obj1[i].id, obj1[i].text,obj1[i].resType);
	           					scene.add(node1);
	           					container.add(node1);
	           				} 
	           			}
	                });
	            }
			} 
			
			function handler(event){
	            if(event.button == 2){// 右键
	                // 当前位置弹出菜单（div）
	                $("#contextmenu").css({
	                    top: event.pageY,
	                    left: event.pageX
	                }).show();
	            
	                var nodeType = currentNode.resType;
	            	if (nodeType == undefined) {
	            		$("#contextmenu #previousPage").hide();
	            	}
	            }
	        }
		});
	</script>
  </head>
  	<body >
  		<ul id="contextmenu" style="display:none;">    
            <li><a>查看详情</a></li>
            <li><a>查看图片</a></li>
            <li id="previousPage"><a>返回上一页</a></li>
        </ul>
		<canvas id="canvas"></canvas>	 
	</body>
</html>
