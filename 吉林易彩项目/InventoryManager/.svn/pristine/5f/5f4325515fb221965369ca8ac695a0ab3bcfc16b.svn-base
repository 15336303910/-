var fp;
Ext.onReady(function() {
	Ext.QuickTips.init();
	var projectName = Ext.getDom("projectName").value;
	Ext.form.Field.prototype.msgTarget = 'qtip';
	function checkOK(form,action){
		Ext.util.Cookies.clear('imCook');
		window.location.href = action.result.result;
	}
	function checkError(form, action) {
		Ext.Msg.show({
			title:'错误',
			msg:action.result.result,
			buttons:Ext.Msg.OK,
			icon:Ext.MessageBox.ERROR
		});
		var userName = Ext.getCmp("username").getValue();
		var userPwd = Ext.getCmp("password").getValue();
		var imCook = Ext.util.Cookies.get("imCook");
		var nowDate = new Date();
		var imNum = 0;
		if(null!=imCook && imCook !=""){
			imNum = imCook.split(",")[0];
		}
		Ext.util.Cookies.set("imCook", (Number(imNum)+0+1)+","+userName+"====="+userPwd+","+nowDate.getTime());
	}
	var username = {
		fieldLabel:'用户名',
		cls:'username',
		xtype:'textfield',
		width:200,
		emptyText:"请输入用户名",
		id:'username',
		labelStyle:'margin-Top:10px;',
		style:'margin-Top:10px;padding-left:20px;',
		name:'user.username',
		allowBlank:false,
		blankText:'请输入用户名'
	}
	var password = {
		fieldLabel:'密码',
		xtype:'textfield',
		cls:'password',
		emptyText:"请输入密码",
		width:200,
		id:'password',
		labelStyle:'margin-Top:10px;',
		style:'margin-Top:10px;margin-bottom:10px; padding-left:20px; ',
		name:'user.password',
		inputType:'password',
		allowBlank:false,
		blankText:'请输入密码'
	}
	var vcode = {
		xtype:'compositefield',
		items:[{
			fieldLabel:'验证码',
			cls:'vcode',
			emptyText:"请输右侧图片中的內容",
			xtype:'textfield',
			width:150,
			id:'vcode',
			style:' padding-left:20px; ',
			name:'vcode',
			allowBlank:false,
			blankText:'请输右侧图片中的內容'
		},{
			html:'<img style="CURSOR: pointer" id="img" onclick="getNewVcode(this)" title="点击刷新"  border="0" src="servlet/AuthCode" width="45" height="23">'
		}]
	}
	var loginUser = function(){
		var f = fp.getForm();
		var imCook = Ext.util.Cookies.get("imCook");
		if(f.isValid()){
			if(null!=imCook && imCook!=""){
				var imNum = Number(imCook.split(",")[0]);
				var beginTimes = Number(imCook.split(",")[2]);
				var nowDate = new Date();
				var endTime = nowDate.getTime();
				var dates = Math.abs((endTime-beginTimes))/(1000*60);     
				if(imNum>3){
					if(dates>10){
						Ext.util.Cookies.clear("imCook");
					}else{
						alert("你已经多次未成功登陆账号被锁定,请等待10分钟");
						return ;
					}
				}
			}
			f.submit({
				url:'login!login.action',
				method:'POST',
				waitMsg:'用户信息验证中...请稍后...',
				success:checkOK,
				failure:checkError,
				scope:this
			});
		}else{
			if(Ext.getCmp("username").getValue()==null || Ext.getCmp("username").getValue()==''){
				Ext.Msg.show({
					title:'错误',
					msg:'请输入用户名',
					buttons:Ext.Msg.OK,
					icon:Ext.MessageBox.ERROR
				});
			}else if(Ext.getCmp("password").getValue()==null || Ext.getCmp("password").getValue()==''){
				Ext.Msg.show({
					title:'错误',
					msg:'请输入密码',
					buttons:Ext.Msg.OK,
					icon:Ext.MessageBox.ERROR
				});
			}else if(Ext.getCmp("vcode").getValue() == null	|| Ext.getCmp("vcode").getValue() == ''){
				Ext.Msg.show({
					title : '错误',
					msg : '请输入验证码',
					buttons : Ext.Msg.OK,
					icon : Ext.MessageBox.ERROR
				});
			}
		}
	};
	fp = new Ext.form.FormPanel({
		bodyStyle:'padding: 15px 40px 15px 40px; background-color: #efefff;',
		labelAlign:'right',
		labelWidth:80,
		items:[username, password,vcode],
		keys:[{
			key:Ext.EventObject.ENTER,
			scope:this,
			fn:loginUser
		}]
	});
	var w = new Ext.Window({
		title:'&nbsp;'+projectName,
		iconCls:'i-script',
		closable:false,
		resizable:false,
		modal:true,
		border:false,
		shadow:true,
		layout:'fit',
		width:400,
		height:200,
		plain:true,
		items:[fp],
		buttonAlign:'center',
		buttons:[{
			text:'登录',
			type:'submit',
			icon:"imgs/yes.png",
			handler:loginUser
		},{
			text:'退出',
			icon:"imgs/no.png",
			handler:function(){
				Ext.MessageBox.show({
					title:'确认',
					msg:'<div style="margin-top:5px;margin-left:10px;">您确定退出吗!</div>',
					width:300,
					multiline:false,
					closable:false,
					buttons:{
						yes:'确定',
						no:'取消'
					},
					icon:Ext.MessageBox.QUESTION,
					fn:function(btn){
						if(btn=='yes'){
							window.opener = null;
							window.open('','_self');
							window.close();
						}
					}
				});
			}
		}]
	});
	w.show();
});
function getNewVcode(img){
	img.src = "servlet/AuthCode?"+new Date().getTime();
}