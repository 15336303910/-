Ext.require('Ext.chart.*');
Ext.require(['Ext.layout.container.Fit', 'Ext.window.MessageBox']);

Ext.onReady(function () {
	var str=Ext.getDom("str").value;
	var str1=Ext.getDom("str1").value;
	
	 var tabs = Ext.widget('tabpanel', {
	    	renderTo: 'tab',
	        width: 820,
	        items: [{
	            title: '柱状图',
	            html:"<iframe id='report1' height='620' width='820' src='pages/resource/report1.jsp?str="+str+"' frameborder='no' border='0' marginwidth='0' marginheight='0' align='center'/>"
	        },{
	            title: '饼状图',
	            html:"<iframe id='report1' height='620' width='820' src='pages/resource/report2.jsp?str="+str1+"' frameborder='no' border='0' marginwidth='0' marginheight='0' align='center'/>"
	        }]
	    });
});
