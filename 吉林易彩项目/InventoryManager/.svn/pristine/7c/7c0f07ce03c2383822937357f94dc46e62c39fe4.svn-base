Ext.namespace("com.increase.cen.document");
com.increase.cen.document.PointLogNewWindow = Ext.extend(Ext.Window,{
	width:1080,
	//autoHeight:true,
	closeAction:'hide',//当关闭按钮被点击时执行的操作
	closable:true,
	layout: 'fit',//布局
	bodyStyle:'padding:5px;',
    buttonAlign:'center',
	plain:true,//设置为true将把窗体的body区域用一个透明背景来渲染
	border:false,
	//resizable:false,
	modal:true,//将使窗口成为模态的
	frame:true,
	/*
	listeners:{   
       hide:function(w){  
          Ext.getCmp('pointLogNewWindow').close(); 
       }   
     }, */
	initComponent:function(config){
		//重写initComponent superclass.initComponent.call(this);来调用父类的初始化方法.
		com.increase.cen.document.PointLogNewWindow.superclass.initComponent.call(this);  
	}
	
});
Ext.reg("pointLogNewWindow",com.increase.cen.document.PointLogNewWindow );