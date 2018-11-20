Ext.require([
    'Ext.form.*',
    'Ext.data.*',
    'Ext.chart.*',
    'Ext.grid.Panel',
    'Ext.layout.container.Column'
]);


function perc(v) {
	return v + '%';
}
var domainId;
var times;
var timee;
Ext.onReady(function(){
	domainId = Ext.getDom("domainId").value;
	times = Ext.getDom("timestart").value;
	timee = Ext.getDom("timeend").value;
	Ext.Ajax.request({
		url : 'statistics!statisticsReasourceByUser.action',
		method : 'post',
		params : {
			'domain.domainId':domainId,
			'times':times,
			'timee':timee
		},
		success : function(response) {
			var str=response.responseText;//取response
			str=str.substring(2,str.length-2);//去掉 "[ 和 ]"
			var dataArrOut = str.split("], [");//将字符串 1,2,3],[4,5,6 分为["[1,2,3]"],["[4,5,6]"]数组
			var resultArr=new Array();//结果集 传入store的必须是array不能是string
			for(var i=0; i<dataArrOut.length; i++){
				var inStr = dataArrOut[i];//取出"[1,2,3]"字符串 
				
				
				inStr = inStr.replace("[","");//去掉[和]
				inStr = inStr.replace("]","");//去掉[和]
				resultArr[i] = inStr.split(",");//再次分解为1 2 3三个元素的数组[1,2,3]
			}
			
			//最后的结果为[[1,2,3],[4,5,6]] 数组 而不是字符串
			var typeStore = new Ext.data.SimpleStore({
				fields : ['value', 'text'],
				data : [['1', '地区'], ['2', '人员']]
			});
		  
		    var form = false,
		        selectedRec = false,
		        //performs the highlight of an item in the bar series
		        highlightCompanyPriceBar = function(storeItem) {
		            var name = storeItem.get('domainName'),
		                series = barChart.series.get(0),
		                i, items, l;

		            series.highlight = true;
		            series.unHighlightItem();
		            series.cleanHighlights();
		            for (i = 0, items = series.items, l = items.length; i < l; i++) {
		                if (name == items[i].storeItem.get('domainName')) {
		                    series.highlightItem(items[i]);
		                    //break;
		                }
		            }
		            series.highlight = false;
		        },
		        // Loads fresh records into the radar store based upon the passed company record
		        updateRadarChart = function(rec) {
		            radarStore.loadData([{
		                'Name': '局站已核查',
		                'Data': rec.get('stationPer')
		            }, {
		                'Name': '机房已核查',
		                'Data':rec.get('generatorPer')
		            }, {
		                'Name': '井已核查',
		                'Data': rec.get('wellPer')
		            }, {
		                'Name': '电杆已核查',
		                'Data': rec.get('polePer')
		            }]);
		        };
		        
		  
		    
//		    //create data store to be shared among the grid and bar series.
		    var ds = Ext.create('Ext.data.ArrayStore', {
		        fields: [
		            {name: 'domainName'},
		            {name: 'total'},
		            {name: 'totalCheck'},
		            {name: 'totalUnCheck'},
		            {name: 'stationNum'},
		            {name: 'stationNumCheck'},
		            {name: 'stationNumUnCheck'},
		            {name: 'stationPer'},
		            {name: 'generatorNum'},
		            {name: 'generatorNumCheck'},
		            {name: 'generatorNumUnCheck'},
		            {name: 'generatorPer'},
		            {name: 'wellNum'},
		            {name: 'wellNumCheck'},
		            {name: 'wellNumUnCheck'},
		            {name: 'wellPer'},
		            {name: 'poleNum'},
		            {name: 'poleNumCheck'},
		            {name: 'poleNumUnCheck'},
		            {name: 'polePer'}
		        ],
		        data: resultArr,
		        listeners: {
		            beforesort: function() {
		                if (barChart) {
		                    var a = barChart.animate;
		                    barChart.animate = false;
		                    barChart.series.get(0).unHighlightItem();
		                    barChart.animate = a;
		                }
		            },
		            //add listener to (re)select bar item after sorting or refreshing the dataset.
		            refresh: {
		                fn: function() {
		                    if (selectedRec) {
		                        highlightCompanyPriceBar(selectedRec);
		                    }
		                },
		                // Jump over the chart's refresh listener
		                delay: 1
		            }
		        }
		    });

		    //create radar store.
		    var radarStore = Ext.create('Ext.data.JsonStore', {
		        fields: ['Name', 'Data'],
		        data: [
		        {
		            'Name': '局站已核查',
		            'Data': 100
		        }, {
		            'Name': '机房已核查',
		            'Data': 100
		        }, {
		            'Name': '井已核查',
		            'Data': 100
		        }, {
		            'Name': '电杆已核查',
		            'Data': 100
		        }]
		    });
		    
		    //Radar chart will render information for a selected company in the
		    //list. Selection can also be done via clicking on the bars in the series.
		    var radarChart = Ext.create('Ext.chart.Chart', {
		        margin: '0 0 0 0',
		        insetPadding: 20,
		        flex: 1.2,
		        animate: true,
		        store: radarStore,
		        theme: 'Blue',
		        axes: [{
		            steps: 5,
		            type: 'Radial',
		            position: 'radial',
		            maximum: 100
		        }],
		        series: [{
		            type: 'radar',
		            xField: 'Name',
		            yField: 'Data',
		            showInLegend: false,
		            showMarkers: true,
		            markerConfig: {
		                radius: 4,
		                size: 4,
		                fill: 'rgb(69,109,159)'
		            },
		            style: {
		                fill: 'rgb(194,214,240)',
		                opacity: 0.5,
		                'stroke-width': 0.5
		            }
		        }]
		    });
		    
		    //create a grid that will list the dataset items.
		    var gridPanel = Ext.create('Ext.grid.Panel', {
		        flex: 7,
		        store: ds,
		        title:'统计列表',

		        columns: [
		            {
		                id       :'domainName',
		                text   : '地区',
		                width    : 90,
		                sortable : true,
		                dataIndex: 'domainName'
		            },
		            {
		                id       :'total',
		                text   : '总数量',
		                width    : 70,
		                sortable : true,
		                dataIndex: 'total'
		            },
		            {
		                text   : '局站',
		                width    : 65,
		                sortable : true,
		                dataIndex: 'stationNum'
		               
		            },
		            {
		                text   : '局站占比',
		                width : 75,
		                sortable : true,
		                dataIndex: 'stationPer',
		                renderer: perc
		            },
		            {
		                text   : '机房',
		                width    : 65,
		                sortable : true,
		                dataIndex: 'generatorNum'
		            },
		            {
		                text   : '机房占比',
		                width    : 75,
		                sortable : true,
		                dataIndex: 'generatorPer',
		                renderer: perc
		            },
		            {
		                text   : '井',
		                width    : 65,
		                sortable : true,
		                dataIndex: 'wellNum'
		            },
		            {
		                text   : '井占比',
		                width    : 75,
		                sortable : true,
		                dataIndex: 'wellPer',
		                renderer: perc
		            },
		            {
		                text   : '电杆',
		                width    : 65,
		                sortable : true,
		                dataIndex: 'poleNum'
		            },
		            {
		                text   : '电杆占比',
		                width    : 75,
		                sortable : true,
		                dataIndex: 'polePer',
		                renderer: perc	
		            }
		           
		        ],

		        listeners: {
		            selectionchange: function(model, records) {
		                var fields;
		                if (records[0]) {
		                    selectedRec = records[0];
		                    if (!form) {
		                        form = this.up('panel').down('form').getForm();
//		                        fields = form.getFields();
//		                        fields.each(function(field){
//		                        	field.setReadOnly(true);
//		                        });
		                    } else {
		                        fields = form.getFields();
		                    }
		                    
		                    // prevent change events from firing
		                    form.suspendEvents();
		                    form.loadRecord(selectedRec);
		                    form.resumeEvents();
		                    highlightCompanyPriceBar(selectedRec);
		                }
		            }
		        }
		    });
			var colors = ['#FF9900','#94AE0A'];  
		    Ext.define('Ext.chart.theme.MyFancy', {  
		        extend: 'Ext.chart.theme.Base',         
		        constructor: function(config) {  
		            this.callParent([Ext.apply({  
		                colors: colors  
		            }, config)]);  
		        }  
		    });
		    //create a bar series to be at the top of the panel.
		    var barChart = Ext.create('Ext.chart.Chart', {
    			theme: 'MyFancy', 
		        height: 200,
		        margin: '0 0 3 0',
		        cls: 'x-panel-body-default',
		        shadow: true,
		        animate: true,
		        store: ds,
		        axes: [{
		            type: 'Numeric',
		            position: 'left',
		            fields: ['totalUnCheck','totalCheck'],
		            minimum: 0,
		            hidden: true
		        }, {
		            type: 'Category',
		            position: 'bottom',
		            fields: ['domainName'],
		            label: {
		                renderer: function(v) {
		                    return Ext.String.ellipsis(v, 15, false);
		                },
		                font: '9px Arial',
		                rotate: {
		                    degrees: 0
		                }
		            }
		        }],
		        series: [{
		            type: 'column',
		            axis: 'left',
//                	stacked: true,
		            style: {
		                fill: '#456d9f'
		            },
		            highlightCfg: {
		                fill: '#a2b5ca'
		            },
		            label: {
		                contrast: true,
		                display: 'insideEnd',
		                field: ['totalUnCheck','totalCheck'],
		                color: '#000',
		                'text-anchor': 'middle'
		            },
		            listeners: {
		                itemmouseup: function(item) {
		                     var series = barChart.series.get(0);
		                     var index = Ext.Array.indexOf(series.items, item);
		                     index = index/2==0?index:index-1;
		                     gridPanel.getSelectionModel().select(index);
		                }
		            },
		            xField: 'name',
		            yField: ['totalUnCheck','totalCheck']
		        }]
		    });
		    
		    /*
		     * Here is where we create the main Panel
		     */
		    Ext.create('Ext.panel.Panel', {
		        title: '当前位置：各人员任务统计',
		        frame: true,
		        bodyPadding: 5,
		        height: 635,
		        theme:'classic',
		        tbar :[{
					xtype:'label',
					text:'统计维度：'
				},{
					xtype : 'combo',
					hideTrigger : true,
					id:'type',
					cls :'order-sel-textfield-width',
					store : typeStore,
					value:'2',
					displayField : 'text',
					valueField : 'value',
					mode : 'local',
					triggerAction : "all",// 下拉框获得了焦点或者被点击了
					editable : false,// 设置为false以阻止用户直接向该输入项输入文本
					hiddenName : 'otype',
					listeners:{
		    			'select': function(){
			           		if(Ext.getCmp('type').getValue() == '1') {
			           			location.href="pages/statistics/statisticReport.jsp";
			            	} else {
			                	location.href="pages/statistics/statisticReportByUser.jsp";
			                }
			       		}
		   		    }
				},'->',{
					xtype:'label',
					text:'地区：'
				},{
					xtype : "textfield",
					id : "stationSel",
					width:150
				},{
					xtype : "textfield",
					id : "domainCode",
					hidden: true
				},'-',{
					xtype:'label',
					text:'开始日期：'
				},{
					xtype : "datefield",
					id:'times',
					hideTrigger : true,
					format : 'Y-m-d',// 日期格式
					editable : false// 设置为false以阻止用户直接向该输入项输入文本
				},{
					xtype:'label',
					text:'结束日期：'
				},{
					xtype : "datefield",
					id:'timee',
					hideTrigger : true,
					format : 'Y-m-d',// 日期格式
					editable : false// 设置为false以阻止用户直接向该输入项输入文本
				},{
					id : 'seekBtn',
					text : '搜索',
					icon : "imgs/queryBtn.png",
					cls : "x-btn-text-icon"
				},{
					id:'resetBtn',
					text : '重置',
					icon : "imgs/queryBtn.png",
					cls : "x-btn-text-icon"
				}],
		        fieldDefaults: {
		            labelAlign: 'left',
		            msgTarget: 'side'
		        },
		    
		        layout: {
		            type: 'hbox',
		            align: 'stretch'
		        },
		        
		        items: [{
		            xtype: 'container',
		            layout: {type: 'vbox', align: 'stretch'},
		            flex: 2,
		            items: [barChart,gridPanel]
		        },{
	                xtype: 'form',
	                flex: 1,
	                width:200,
	                layout: {
	                	width:200,
	                    type: 'vbox',
	                    align:'stretch'
	                },
	                margin: '0 0 0 5',
	                title: '查看',
	                labelwidth:200,
	                items: [{
		                    margin: '5',
		                    xtype: 'fieldset',
		                    flex: 1,
		                    title:'Company details',
		                    defaults: {
		                        width: 240,
		                        labelWidth: 90,
		                        readOnly: true,
		                        bubbleEvents: ['change']
		                    },
		                    defaultType: 'numberfield',
		                    autoScroll:true,
		                    items: [{
		                        fieldLabel: '用户',
		                        name: 'domainName',
		                        xtype: 'textfield'
		                    }, {
		                        fieldLabel: '局站数量',
		                        xtype: 'textfield',
		                        name: 'stationNum'
		                    }, {
		                        fieldLabel: '机房数量',
		                        xtype: 'textfield',
		                        name: 'generatorNum'
		                    }, {
		                        fieldLabel: '井数量',
		                        xtype: 'textfield',
		                        name: 'wellNum'
		                    }, {
		                        fieldLabel: '电杆数量',
		                        xtype: 'textfield',
		                        name: 'poleNum'
		                    }]
		                },radarChart],
	                listeners: {
	                    // buffer so we don't refire while the user is still typing
	                    buffer: 200,
	                    change: function(field, newValue, oldValue, listener) {
	                        if (selectedRec && form) {
	                            if (newValue > field.maxValue) {
	                            	field.setValue(field.maxValue);
	                            } else {
	                                if (form.isValid()) {
	                                    form.updateRecord(selectedRec);
	                                    updateRadarChart(selectedRec);
	                                }
	                            }
	                        }
	                    }
	                }
	            }],
		        renderTo: Ext.getBody()
		    });
		    
		    
		    var seekBtn = Ext.getCmp("seekBtn");
			seekBtn.on('click', function() {
				var area = Ext.getDom("domainId").value;
				var times = Ext.getCmp("times").getValue();
				var timee = Ext.getCmp("timee").getValue();
				location.href="pages/statistics/statisticReportByUser.jsp?domainId="+area+"&times="+times+"&timee="+timee;
				/*Ext.getCmp("state").setValue("");
				Ext.getCmp("ename_sel").setValue("");
				Ext.getCmp("ename_sel").hiddenValue="";*/
			});
			
			var resetBtn=Ext.getCmp("resetBtn");
			resetBtn.on('click', function() {
				Ext.getDom("domainId").value="";
				Ext.getCmp("stationSel").setValue("");
				Ext.getCmp("times").setValue("");
				Ext.getCmp("timee").setValue("");
			});
			
			var selectAreaField = Ext.getCmp("stationSel");
			selectAreaField.on('focus', function(){
				showMenu();
			});
			
		}
	});
		
	
	
	
});