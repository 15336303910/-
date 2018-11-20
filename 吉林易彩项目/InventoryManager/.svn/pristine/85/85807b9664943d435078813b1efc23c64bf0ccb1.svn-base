Ext.namespace("com.increase.cen.user");
//一页的记录数
var limit = null
com.increase.cen.user.QueryUser = Ext.extend(Ext.form.FormPanel,{
	bodyStyle:"padding:5px;",
	bodyBorder:false,
	frame:true,
	labelAlign:"right",
	buttonAlign:'center',
	labelWidth:65,
	width:400,
	height:200,
	initComponent:function(config){
		//获取一页的记录数
		limit = Ext.getDom("limit").value;
		//启动悬停提示
		Ext.QuickTips.init();
		Ext.form.Field.prototype.msgTarget='qtip';//提示的方式
		this.areano = new Ext.form.Hidden({
			id:"queryAreano",
			name : "user.areano",
			value:''
		});
		//用户名
		this.username = new Ext.form.TextField({
			name:"user.username",
			fieldLabel:"用户名",
			width:200,
			emptyText:"请输入用户编号"
		});
		//真实信姓名
		this.realname = new Ext.form.TextField({
			name:"user.realname",
			fieldLabel:"姓名",
			emptyText:"请输入用户姓名",
			width:200
		});
		// 电话
		this.tel = new Ext.form.TextField({
			name : "user.phoneNumber",
			fieldLabel : "联系电话",
			emptyText : "电话如18612345678",
			width : 200
			
		});
		
		this.items=[
			{
				xtype:'fieldset',
				title:'查询条件',
				collapsible: true,
				items:[
					this.areano,this.username,this.realname,this.tel 
				]
			}
		];
		//按钮
		this.buttons=[
			{
				text:"查询",
				icon:"imgs/queryBtn.png",
				cls:"x-btn-text-icon",
				tooltip:'查询用户',
				tooltipType:'qtip',
				handler:this.btnSubmit.createDelegate(this)
			},{
				xtype:'tbspacer' 
			},{
				xtype:'tbspacer'
			},{
				xtype:'tbspacer'
			},{
				text:"返回",
				icon:"imgs/back.png",
				cls:"x-btn-text-icon",
				tooltip:'关闭此窗口',
				tooltipType:'qtip',
				handler:this.btnClose.createDelegate(this)
			}
		];
		//重写initComponent superclass.initComponent.call(this);来调用父类的初始化方法.
		com.increase.cen.user.QueryUser.superclass.initComponent.call(this);
	},
	//得到查询条件，查询结果
	btnSubmit:function submitForm(){
		var areano = this.getForm().findField("user.areano").getValue();
		//查询的用户名
		var username = this.getForm().findField("user.username").getValue();
		//查询的姓名
		var realname = this.getForm().findField("user.realname").getValue();
		//查询的机构id
		var phoneNumber = this.getForm().findField("user.phoneNumber").getValue();
		//用户表格
		var grid = Ext.getCmp('userList');
		//用户表格数据
		var store = grid.getStore();
		Ext.Ajax.request({
			url:'user!findUserCount.action',
			method:'post',
			success:function(response){
				var result = Ext.util.JSON.decode(response.responseText);
				if(result > 0){
					//加载数据
					store.load({params:{
							start:0,
							limit:limit,
							"user.username":username,
							"user.realname":realname,
							"user.phoneNumber":phoneNumber,
							"user.areano":areano	
						},callback:function(r,options,success){
							if(r == null || r == ""){
								Ext.MessageBox.show({//提示框
									title:'提示',
									msg:'&nbsp;&nbsp;对不起,没有您所查询的用户信息!',
									width:300,
									multiline:false,//设置为true，提示用户输入多行文本
									closable:false,
									buttons:{
										yes: '确定' 
									},
									icon:Ext.MessageBox.ERROR,
									//刷新全部用户信息
									fn:function(btn){
										if(btn == "yes"){
											store.reload({params:{
												start:0,
												limit:limit,
												"user.username":"",
												"user.realname":"",
												"user.areano":areano	
											}});
										}
									}
								});
							}
						}
					});
				}else{
					Ext.MessageBox.show({
						title:'提示',
						msg:'&nbsp;&nbsp;对不起,没有您所查询的用户信息!',
						width:300,
						multiline:false,//设置为true，提示用户输入多行文本
						closable:false,
						buttons:{
							yes: '确定' 
						},
						icon:Ext.MessageBox.ERROR
					})
				}
			},
			failure:function(){
				Ext.MessageBox.show({
					title:'提示',
					msg:'&nbsp;&nbsp;对不起,没有您所查询的用户信息!',
					width:300,
					multiline:false,//设置为true，提示用户输入多行文本
					closable:false,
					buttons:{
						yes: '确定' 
					},
					icon:Ext.MessageBox.ERROR
				})
			}
		});
		//查询表单重置
		Ext.getCmp("queryUser").getForm().reset();
		//查询窗口隐藏
		Ext.getCmp('queryUserWindow').hide();
	},
	//关闭窗口
	btnClose:function(){
		//重置查询表单
		Ext.getCmp("queryUser").getForm().reset();
		//隐藏查询窗口
		Ext.getCmp('queryUserWindow').hide();
		//刷新表格
		Ext.getCmp("userList").getStore({params:{
								start:0,
								limit:limit,
								"user.username":"",
								"user.realname":"",
								"user.areano":""		
							}});
	}
});
Ext.reg("queryUser",com.increase.cen.user.QueryUser);