var store;
var listGrid;
var thisPagesize = 15;
var autoHeight = false;


Ext.onReady(function() {
	Ext.data.Connection.prototype.timeout ='300000';
	
	var stoneNamequery = itemText(.33,80,'stoneNamequery','标石名称');
	var stoneAreaquery = itemText(.33,80,'stoneAreaquery','所属区域');
	var queryPanel = new Ext.form.FormPanel({
		bodyStyle : 'padding-top:5px',
	    layout : "column",
	    id:'queryPanel',
		border : false,
		width:'100%',
		frame:true,
		items:[stoneNamequery,stoneAreaquery
		]
	});
	
	
	new Ext.Panel({
		id:'basic',
		width:'100%',
    	frame:true,
    	modal:true,
		applyTo:'form',
		items:[queryPanel],
		listeners:{
			beforeRender:function(){
			}
		}
	});
});