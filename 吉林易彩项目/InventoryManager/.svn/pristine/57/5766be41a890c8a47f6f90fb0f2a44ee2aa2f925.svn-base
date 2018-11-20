//Ext.require('Ext.chart.*');
Ext.require(['Ext.layout.container.Fit', 'Ext.window.MessageBox']);

Ext.onReady(function () {
	var report=Ext.getDom("report").value;
	
	report=report.replace(/\'/g,"\"");

	report = eval("(" + report + ")");
	var	dom=document.getElementById("report").value;
		window.store1 = Ext.create('Ext.data.JsonStore', {
		        fields: ['name', 'num'],
//		        data: Ext.getDom("report").value
		        data:report
		    });

    var donut = false,
        chart = Ext.create('Ext.chart.Chart', {
            xtype: 'chart',
            animate: true,
            store: store1,
            shadow: true,
            legend: {
                position: 'left'
            },
            insetPadding: 60,
//            theme: 'Base:gradients',
            series: [{
                type: 'pie',
                field: 'num',
                showInLegend: true,
                donut: donut,
                tips: {
                  trackMouse: true,
                  width: 140,
                  height: 28,
                  renderer: function(storeItem, item) {
                    //calculate percentage.
                    var total = 0;
                    store1.each(function(rec) {
                        total += rec.get('num');
                    });
                    this.setTitle(storeItem.get('name') + ': ' + Math.round(storeItem.get('num') / total * 100) + '%');
                  }
                },
                highlight: {
                  segment: {
                    margin: 20
                  }
                },
                label: {
                    field: 'name',
                    display: 'rotate',
                    contrast: true,
                    font: '18px Arial'
                }
            }]
        });


    var panel1 = Ext.create('widget.panel', {
        width: 800,
        height: 600,
        title: '查看报表',
        renderTo: Ext.getBody(),
        layout: 'fit',
        items: chart
    });
});
