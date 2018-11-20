var store;
var listGrid;
var thisPagesize = 15;
var autoHeight = false;

Ext.onReady(function() {
	Ext.data.Connection.prototype.timeout ='300000';
	
	var resTime = basicDate(.5,80,'resTime','采集时间');
	var queryPanel = new Ext.form.FormPanel({
		bodyStyle : 'padding-top:5px',
	    layout : "column",
	    id:'queryPanel',
		border : false,
		width:'100%',
		frame:true,
		buttonAlign:"center",
		items:[resTime, 
		      {columnWidth:1,
				layout: 'table', 
				xtype: 'fieldset', 
				title: '选择导出', 
				frame:true, 
				autoHeight: true, 
				defaults: {
			        bodyStyle:'padding:20px'
			    },
				layoutConfig: {   
			        columns: 5  
			    },
				items: [{
					text:"站点",
					xtype:'splitbutton',
					width:'80',
		      		handler:function(){
		      			expDate("station");
		      		}
				},{
					text:"机房",
					xtype:'splitbutton',
					width:'80',
					handler:function(){
						expDate("gener");
					}
				},{
					text:"光交",
					xtype:'splitbutton',
					width:'80',
					handler:function(){
						expDate("optical");
		      		}
				},{
					text:"电杆",
					xtype:'splitbutton',
					width:'80',
					handler:function(){
						expDate("pole");
					}
				},{
					text:"人手井",
					xtype:'splitbutton',
					width:'80',
					handler:function(){
						expDate("well");
					}
				},{
					text:"标石",
					xtype:'splitbutton',
					width:'80',
					handler:function(){
						expDate("stone");
					}
				},{
					text:"杆路",
					xtype:'splitbutton',
					width:'80',
					handler:function(){
						expDate("poleLine");
					}
				},{
					text:"管道",
					xtype:'splitbutton',
					width:'80',
					handler:function(){
						expDate("pipe");
					}
				},{
					text:"直埋",
					xtype:'splitbutton',
					width:'80',
					handler:function(){
						expDate("buried");
					}
				},{
					text:"撑点",
					xtype:'splitbutton',
					width:'80',
					handler:function(){
						expDate("supportPoint");
					}
				},{
					text:"挂墙",
					xtype:'splitbutton',
					width:'80',
					handler:function(){
						expDate("hangwall");
					}
				},{
					text:"全部",
					xtype:'splitbutton',
					width:'80',
					handler:function(){
						expDate("all");
					}
				},{
					text:"坐标",
					xtype:'splitbutton',
					width:'80',
					handler:function(){
						expDate("coord");
					}
				}]
		      }
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
	
	function expDate(type){
		var resTime = Ext.getCmp('resTime').getValue();
		if(resTime != null && resTime != ""){
			resTime = resTime.format("Y-m-d");
		}
		var data = "resType="+type+"&resTime="+resTime;
		var url = context_path+'/reportAction!expData.action?'+data;
		url = encodeURI(url);
		window.open(url,"_blank");
	}
	
	function dateFormat(value){ 
	    if(null != value){ 
	        return Ext.Date.format(new Date(value),'Y-m-d'); 
	    }else{ 
	        return null; 
	    } 
	} 
});