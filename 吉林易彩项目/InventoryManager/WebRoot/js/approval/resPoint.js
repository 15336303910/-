var store;
var listGrid;
var thisPagesize = 15;
var autoHeight = false;

Ext.onReady(function() {
	Ext.data.Connection.prototype.timeout ='300000';
	
	
	new Ext.Panel({
		id:'basic',
		width:'100%',
    	frame:true,
    	modal:true,
		applyTo:'form',
		listeners:{
			beforeRender:function(){
			}
		}
	});
});