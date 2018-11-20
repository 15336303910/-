Ext.require('Ext.chart.*');
Ext.require(['Ext.layout.container.Fit', 'Ext.window.MessageBox']);

Ext.onReady(function () {
	var str=Ext.getDom("str").value;
	str=str.replace(/\'/g,"\"");
	str = eval("(" + str + ")");
	
    var store1 = Ext.create('Ext.data.JsonStore', {
        fields: ['type', '总数'],
        data: str
    });
    
    var colors = ['#94AE0A','#FF9900'];  
    Ext.define('Ext.chart.theme.MyFancy', {  
        extend: 'Ext.chart.theme.Base',         
        constructor: function(config) {  
            this.callParent([Ext.apply({  
                colors: colors  
            }, config)]);  
        }  
    });  


    var chart1 = Ext.create('Ext.chart.Chart',{
//		theme: 'MyFancy', 
        animate: true,
//        shadow: true,
        store: store1,
        legend: {
            position: 'left'
        },
        axes:[{
            type: 'Numeric',
            position: 'left',
            fields: ['总数'],
            title: false
//            grid: true,
          
        }, {
            type: 'Category',
            position: 'bottom',
            fields: ['type'],
            title: false
        }], 
        series: [{
            type: 'column',
            gutter: 80,
            xField: 'type',
            yField: ['总数'],
            stacked: true,
            label:{
            	display: 'insideEnd',
                field: '总数',
                contrast: true
            },
            tips: {
                trackMouse: true,
                width: 65,
                height: 28,
                renderer: function(storeItem, item) {
                    this.setTitle(item.value[1]);
                }
            }
        }]
    });
 
    var panel2 = Ext.create('widget.panel', {
        width: 800,
        height: 400,
//        style:{
//        	marginTop:'170px',
//        },
        renderTo: Ext.getBody(),
        layout: 'fit',
        items: chart1
    });

	
});