var fp;
var url="";
var pnNorth;
var reflashTime = new Date().getTime();
function reflash(){
	reflashTime = new Date().getTime();
}

/**
 * 得到消息推送
 * @returns
 */
function getMsgNum(){
	var title = Ext.getCmp("pnNorth").title;
	$.ajax({
   		url:context_path+"/approvalAction!getWarnMsg.action?",
           type: "POST",
           dataType: "text",
           success: function (data) {
        	   var infoObj ;
        	   var strType = typeof data;
        	   if(strType == 'object'){
        			infoObj= data;
        		}else{
        			infoObj= eval("("+data+")");
        		}
        	   
        	   var tils = title.split(">>");
        	   title = tils[0];
        	   if(data !="[]" && infoObj.length >0 && typeof(infoObj[0].taskTitle)!="undefined"){
        		   title +=">>" +
       	   				"<span style='color:#F00'><a href='javascript:void(0);' " +
       	   				" style='text-decoration:none;' " +
       	   				" onclick='openUrl("+infoObj[0].taskId+")'>"+infoObj[0].taskTitle+"</a>已提交!"+"" +
       	   				"</span>";
        	   }
        	   if(data !="[]" && infoObj.length >0 && typeof(infoObj[0].rejRes)!="undefined"){
        		   title +=">>" +
       	   				"<span style='color:#F00'>驳回资源<a href='javascript:void(0);' " +
       	   				" style='text-decoration:none;' " +
       	   				" onclick='openUrl("+infoObj[0].rejTaskId+")'>"+infoObj[0].rejRes+"</a>已修改提交!"+"" +
       	   				"</span>";
        	   }
        	   Ext.getCmp("pnNorth").setTitle(title);
           },
           error: function (data) {
           	alert("获取数据失败!");
           }
        });
}

/**
 * 打开工单处理页面
 * @param taskId
 * @returns
 */
function openUrl(taskId){
	var url = context_path+'/pages/approval/sendGis.jsp?id='+taskId+'&type=now';
	openWin(url,'now',900,600);
}

/**
 * 打开窗体
 * @param url
 * @param name
 * @param iWidth
 * @param iHeight
 */
function openWin(url,name,iWidth,iHeight) { 
    var iTop = (window.screen.availHeight - 30 - iHeight) / 2; 
    var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; 
    window.open(url, name, 'height=' + iHeight + ',innerHeight=' + iHeight + ',width=' + iWidth + ',innerWidth=' + iWidth + ',top=' + iTop + ',left=' + iLeft + ',status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no'); 
}
Ext.onReady(function() {
	
	
	var userArea = Ext.getDom("userArea").value;
    var realname = Ext.getDom("userRealName").value;
    var username = Ext.getDom("userName").value;
    var projectName=Ext.getDom("projectName").value;
    var copyRight=Ext.getDom("copyRight").value;
    var PageWidth=document.body.clientWidth-35;
    url = Ext.getDom("url").value;
	Ext.QuickTips.init();
	// 提示的方式
	Ext.form.Field.prototype.msgTarget = 'qtip';
	function checkOK(form, action) {
		isShow = false;
		w.hide();
		reflash();
	}

	function checkError(form, action) {
		Ext.Msg.show({
			title : '错误',
			width:200,
			msg : action.result.result,
			buttons : Ext.Msg.OK,
			icon : Ext.MessageBox.ERROR
		});
	}
	var message = {
		xtype: 'displayfield',
		value: '您当前登录的用户为'+realname+',请输入您的登录密码进行解锁。'
	}
	var usrename = new Ext.form.Hidden({
		value: username,
		name:'user.username'
	});
	var password = {
		xtype : 'textfield',
		cls : 'password',
		emptyText : "请输入密码",
		width : 200,
		id : 'password',
		labelStyle : 'margin-Top:10px;',
		style : 'margin-Top:10px;margin-bottom:10px; padding-left:20px; ',
		name : 'user.password',
		inputType : 'password',
		allowBlank : false,
		blankText : '请输入密码'
	}
	var loginUser = function() {
		var f = fp.getForm();
		if (f.isValid()) {
			f.submit({
				url : 'login!unlock.action',
				method : 'POST',
				waitMsg : '用户信息验证中...请稍候...',
				success : checkOK,
				failure : checkError,
				scope : this
			});
		} else {
			if (Ext.getCmp("password").getValue() == null
					|| Ext.getCmp("password").getValue() == '') {
				Ext.Msg.show({
					title : '错误',
					width : 150,
					msg : '请输入密码',
					buttons : Ext.Msg.OK,
					icon : Ext.MessageBox.ERROR
				});
			}
		}
	};
	fp = new Ext.form.FormPanel({
		bodyStyle : 'padding: 15px 0px 15px 0px; background-color: #efefff;',
		labelAlign : 'left',
		labelWidth : 10,
		items : [usrename,message, password],
		keys : [{
			key : Ext.EventObject.ENTER,
			scope : this,
			fn : loginUser
		}]
	});
	
    		var menu = new com.increase.cen.MenuToolbar();
    		// 初始化一些变量
 			var aWeek = ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'];
	 		var currentTime = new Ext.Toolbar.TextItem('当前时间：'
   							+ new Date().format('Y-m-d H:i:s') + '&nbsp;&nbsp;'
   							+ aWeek[new Date().getDay()]);
    		pnNorth=new Ext.Panel({
				id:"pnNorth",
				region:"north",
				title:projectName+"&nbsp;&nbsp;&nbsp;&nbsp;-导航&gt;&gt;工单列表",
				layout:'column',
				iconCls:'mainIcon',
				contentEl:'pnNorthBody',
				bbar: [
					menu,
					'->','-',
					{
						text:"导出采集",
						iconCls:'imgs/main/exit.png',
						handler:function(){
							var url = context_path+'/pages/report/expResExl.jsp';
							openWin(url,'查看',500,300);
						}
					},"-",
					'欢迎您，'+userArea+'用户：'+realname,
					"-",
					currentTime,
					"-",
					{	
						text : "退出",
    					icon : "imgs/main/exit.png",
    					handler : function() {
    						Ext.MessageBox.show({
								title : '确认',
								msg : '<div style="margin-top:5px;margin-left:10px;">您确定要退出系统吗?</div>',
								width : 300,
								multiline : false,// 设置为true，提示用户输入多行文本
								closable : false,
								buttons : {
									yes : '确定',
									no : '取消'
								},
								icon : Ext.MessageBox.QUESTION,
								fn : function(btn) {
									if (btn == 'yes') {// 如果选择确定，则进行修改
										window.location.href="logout.jsp";
									}
								}
							});
    					}
   					},
   					"-"]
			});
			
			var pnCenterframe=new Ext.Panel({
				id:"pnCenterframe",
				region:"center",
				layout:'fit',
				bodyStyle:'border-width: 0 0 0 1px',
				html:"<iframe src='"+context_path+url+"' height='100%' width='100%' frameborder='no' border='0' marginwidth='0' marginheight='0' align='center'></iframe>"
			});
			
			var pnSouth=new Ext.Panel({
				id:"pnSouth",
				region:"south",
				border:false,
				width:'100%',
				height:20,
				bodyStyle:'border-width:1px 1px 1px 1px',
				html:'<div align="center"><font size="2em">'+copyRight+'</font><div style="position:absolute;left:'+PageWidth+'px;top:-1px;height:21px;width:30px;"><img src="imgs/CopyRightIcon.png" width="30" height="21"></div></div>'
			});
			new Ext.Viewport({
				id:'view',
				layout:"border",
				items:[pnNorth,pnCenterframe,pnSouth]
			}); 
			
});
			var loadM = null;/*new Ext.LoadMask(document.body, {
				});*/
			/**
			 * 打开窗体
			 * @param url
			 * @param name
			 * @param iWidth
			 * @param iHeight
			 */
			function openWin(url,name,iWidth,iHeight) { 
			    var iTop = (window.screen.availHeight - 30 - iHeight) / 2; 
			    var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; 
			    window.open(url, name, 'height=' + iHeight + ',innerHeight=' + iHeight + ',width=' + iWidth + ',innerWidth=' + iWidth + ',top=' + iTop + ',left=' + iLeft + ',status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no'); 
			}
			function shuloading(){
				reflash();
				loadM = new Ext.LoadMask("pnCenterframe", {
					msg : '数据加载中，请稍候......',
					removeMask : true 
				});
				loadM.show();
			}
			function closemask(){
				loadM.hide();
			}