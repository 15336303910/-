Ext.namespace("com.increase.cen.user");
 com.increase.cen.user.userWindow = Ext.extend(Ext.Window,{
	width:600,
	autoHeight:true,
	closeAction:'hide',//当关闭按钮被点击时执行的操作
	closable:true,
	layout: 'fit',//布局
	bodyStyle:'padding:5px;',
    buttonAlign:'center',
	plain:true,//设置为true将把窗体的body区域用一个透明背景来渲染
	border:false,
	modal:true,//将使窗口成为模态的
	frame:true,
	listeners:{   
       hide:function(w){   
          if(w.items){    
             w.items.each(function(f){   
                if(f.getXType()=="form"){    
                    f.form.reset();   
                }   
             });   
          }
          var limit = parseInt(Ext.getDom("limit").value);
          Ext.getCmp('userList').getStore().load({
			params : {
				start : 0,
				limit : limit,
				"user.username" : "",
				"user.realname" : "",
				"user.areano" : Ext.getDom("domainCode").value
			}
		  });
		  Ext.getCmp('userList').getView().refresh();   
       }   
     }, 
	initComponent:function(config){
		//重写initComponent superclass.initComponent.call(this);来调用父类的初始化方法.
		com.increase.cen.user.userWindow.superclass.initComponent.call(this);  
	}
	
});
Ext.reg("userWindow",com.increase.cen.user.userWindow );