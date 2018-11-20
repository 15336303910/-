		var node = null;
		var setting = {
			async: {
				enable: true,
				url:"domain!loadDomainTree2.action",
				autoParam:["id","text","domainCode","level","pId=fatherId"],
				dataFilter: filter
			},
			view: {
				dblClickExpand: false,
				selectedMulti: false,
				showIcon: true
			},
			data: {
				simpleData: {
					enable: true,
					pIdKey: "fatherId"
				}
			},
			callback: {
				onClick: onClick
			}
		};
		
		function filter(treeId, parentNode, childNodes) {
			if (!childNodes) return null;
			for (var i=0, l=childNodes.length; i<l; i++) {
//				childNodes[i].name = childNodes[i].text.replace(/\.n/g, '.');
			}
			return childNodes;
		}
		
		function showMenu() {
			var typeObj = $("#stationSel");
			var typeOffset = $("#stationSel").offset();
			$("#stationTreeContent").css({left:typeOffset.left + "px", top:typeOffset.top + typeObj.outerHeight() + "px"}).slideDown("fast");

			$("body").bind("mousedown", onBodyDown);
		}
		function hideMenu() {
			$("#stationTreeContent").fadeOut("fast");
			$("body").unbind("mousedown", onBodyDown);
		}
		function onBodyDown(event) {
			if (!(event.target.id == "menuBtn" || event.target.id == "stationTreeContent" || $(event.target).parents("#stationTreeContent").length>0)) {
				hideMenu();
			}
		}
		
		function onClick(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("stationTree");
			var nodes = zTree.getSelectedNodes();
			var v = nodes[0].name;
			var code = nodes[0].id;
//			$("#stationSel").attr("value", v);
//			$("#stationAreanoSel").attr("value", code);
			Ext.getCmp("stationSel").setValue(v);
			Ext.getDom("domainId").value = code;
			hideMenu();
		}
		

		$(document).ready(function(){
			$.fn.zTree.init($("#stationTree"), setting, node);
		});