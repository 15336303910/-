Ext.onReady(function() {
	var wellid=Ext.getDom("wellId").value;
	
	Ext.Ajax.request({
		url : 'pipe!duili.action',
		method : 'post',
		params : {
			wellId : wellid
		},
		success : function(response) {
			var json = Ext.util.JSON.decode(response.responseText);
			Ext.getCmp("wellNamess").setValue(json.wellInfoBean.wellNameSub);
			Ext.getCmp("manholeTypeEnumId").setValue(json.wellInfoBean.manholeTypeEnumId);
			Ext.getCmp("wellAddress").setValue(json.wellInfoBean.wellAddress);
			Ext.getCmp("longitude").setValue(json.wellInfoBean.longitude);
			Ext.getCmp("latitude").setValue(json.wellInfoBean.latitude);
			Ext.getCmp("wellmapId").setValue(json.wellInfoBean.wellmap);
			Ext.getCmp("fids").setValue(json.wellInfoBean.faceids);
			Ext.getCmp("wellid").setValue(json.wellInfoBean.wellId);
			//管孔
			Ext.getCmp("wellname1").setValue(json.wellInfoBean.wellNameSub);
			Ext.getCmp("wellId").setValue(json.wellInfoBean.wellId);
			Ext.getCmp("wellIdzi").setValue(json.wellInfoBean.wellId);
			var wellmap = json.wellInfoBean.wellmap;
			if(wellmap!='' && wellmap!=null){
				var strarr=wellmap.split('');
				for(var i=0;i<strarr.length;i++){
					if(strarr[i]!='0'){
						var fid="x_"+(i+1);
						Ext.getCmp(fid).setText(strarr[i]);
					}
				}
			}
		}
	});
	//井基本信息
	this.manholeType=new Ext.data.SimpleStore({
    		fields : ['value', 'text'],
    		data : [['', ''],['1', '人孔'], ['2', '手孔'], ['3', '双人孔'], ['4', '三人孔'], ['5', '局前井'], ['6', '地下室']]
    	})
	var row1 = {
		layout : 'column', // 从左往右布局
		items : [{
			layout : 'form', // 从上往下布局
			items : [{
				id : 'wellid',
				name : 'wellInfoBean.wellId',
				xtype : 'textfield',
				fieldLabel : '人/手井ID',
				hidden : true
				}]
			},{
			columnWidth : .3, // 该列有整行中所占百分比
			layout : 'form', // 从上往下布局
			items : [{
				id : 'wellNamess',
				name : 'wellInfoBean.wellNameSub',
				xtype : 'textfield',
				fieldLabel : '人/手井名称',
				width : 180,
				readOnly : true
				}]
			},{
			columnWidth : .3, // 该列有整行中所占百分比
			layout : 'form', // 从上往下布局
			items : [{
				xtype : 'combo',
				id : 'manholeTypeEnumId',
				name : "wellInfoBean.manholeTypeEnumId",
				forceSelection: true,
				fieldLabel : '井类型',
				editable : false,// 设置为false以阻止用户直接向该输入项输入文本
				store : this.manholeType,
				mode : "local",// 加载本地数据 设置为'local' (默认值为 'remote'，它将从服务器加载数据)
				triggerAction : "all",// 下拉框获得了焦点或者被点击了
				displayField : "text",
				valueField : "value",
				hiddenName : 'wellInfoBean.manholeTypeEnumId',// 创建一个表单隐藏域来存储输入项的值
				width : 180,
				readOnly : true
			}]
		},{
				columnWidth : .3, // 该列有整行中所占百分比
				layout : 'form', // 从上往下布局
				items : [{
					id : 'wellAddress',
					name : 'wellInfoBean.wellAddress',
					xtype : 'textfield',
					fieldLabel : '人/手井地址',
					width : 180,
					readOnly : true
				}]
			}]
	};

	var row2 = {
		layout : 'column', // 从左往右布局
		items : [{
			columnWidth : .3, // 该列有整行中所占百分比
			layout : 'form', // 从上往下布局
			items : [{
				id : 'longitude',
				name : 'wellInfoBean.longitude',
				xtype : 'textfield',
				fieldLabel : '人/手井经度',
				width : 180,
				readOnly : true
			}]
		}, {
			columnWidth : .3,
			layout : 'form',
			items : [{
				id : 'latitude',
				name : 'wellInfoBean.latitude',
				xtype : 'textfield',
				fieldLabel : '人/手井纬度',
				readOnly : true,
				width : 180
			}]
		}]
	};

	var basic = new Ext.form.FieldSet({
		title : '井信息',
		labelAlign : "right",
		collapsible : true,
		items : [row1, row2]
	})
	var form1 = {
		layout : 'column', // 从左往右布局
		items : [{
			id : 'wellmapId',
			name : 'wellmapId',
			xtype : 'textfield',
			fieldLabel : '井面',
			hidden : true
		},{
			id : 'faceface',
			name : 'faceface',
			xtype : 'textfield',
			fieldLabel : '',
			hidden : true
		},{
			id : 'tubenumber',
			name : 'tubenumber',
			xtype : 'textfield',
			fieldLabel : '',
			hidden : true
		},{
			id : 'fids',
			name : 'fids',
			xtype : 'textfield',
			fieldLabel : '',
			hidden : true
		},{
			id : 'pipeSegmentId',
			name : 'pipeSegmentId',
			xtype : 'textfield',
			fieldLabel : '',
			hidden : true
		},{
			id : 'benfaceId',
			name : 'benfaceId',
			xtype : 'textfield',
			fieldLabel : '',
			hidden : true
		},{
			id : 'x_8',
			xtype : 'button',
			text : "西北",
			width:100,
			height:100,
			handler : function() {
				shuzi(this);
			}
		},{
			id : 'x_1',
			xtype : 'button',
			text : "北",
			width:100,
			height:100,
			handler : function() {
				shuzi(this);
			}
		},{
			id : 'x_2',
			xtype : 'button',
			text : "东北",
			width:100,
			height:100,
			handler : function() {
				shuzi(this);
			}
		}]
	};
	this.rowStore=new Ext.data.SimpleStore({
		fields : ['value', 'text'],
    	data : [['', ''],['1', '1'], ['2', '2'],['3', '3'], ['4', '4'],['5', '5'], ['6', '6'],['7', '7'], ['8', '8'],
    	['9', '9'], ['10', '10'],['11', '11'], ['12', '12'],['13', '13'], ['14', '14'],['15', '15'], ['16', '16'],
    	['17', '17'], ['18', '18'],['19', '19'], ['20', '20']]
	})
	this.colStore=new Ext.data.SimpleStore({
		fields : ['value', 'text'],
    	data : [['', ''],['1', '1'], ['2', '2'],['3', '3'], ['4', '4'],['5', '5'], ['6', '6'],['7', '7'], ['8', '8'],
    	['9', '9'], ['10', '10'],['11', '11'], ['12', '12'],['13', '13'], ['14', '14'],['15', '15'], ['16', '16'],
    	['17', '17'], ['18', '18'],['19', '19'], ['20', '20']]
	})
	var form2 = {
		layout : 'column', // 从左往右布局
		items : [{
			id : 'x_7',
			xtype : 'button',
			text : "西",
			width:100,
			height:100,
			cls:'',
			handler : function() {
				shuzi(this);
			}
		},{
			xtype : 'button',
			text : "",
			width:100,
			height:100,
			handler : function() {
				
			}
		},{
			id : 'x_3',
			xtype : 'button',
			text : "东",
			width:100,
			height:100,
			handler : function() {
				shuzi(this);
			}
		}]
	};
	var form3 = {
		layout : 'column', // 从左往右布局
		items : [{
			id : 'x_6',
			xtype : 'button',
			text : "西南",
			width:100,
			height:100,
			handler : function() {
				shuzi(this);
			}
		},{
			layout:'form',
			items:[{
				id : 'x_5',
				xtype : 'button',
				text : "南",
				width:100,
				height:100,
				handler : function() {
					shuzi(this);
				}
			},{
				xtype:'button',
				text : "保存立面",
				width:80,
				style : 'margin-top:5px',
				handler : function() {
					dosubmitface();
				}
			}]
		},{
			id : 'x_4',
			xtype : 'button',
			text : "东南",
			width:100,
			height:100,
			handler : function() {
				shuzi(this);
			}
		}]
	};
	var duiduan={
		id : 'oppoinformation',
		layout : 'form',
		lableWidth: 40,
		hidden : true,
		items:[{
				html : '<span style="font-size:10px; color: #15428b; margin:0 0 0 120; font-weight:bold"> 对端井面信息</span>'
			},{
				layout:'form',
				items:[{
					id:'startDeviceId',
					name:'pipeSegmentInfoBean.startDeviceId',
					xtype:'textfield',
					hidden:true
				},{
					id:'oppoids',
					name:'oppoids',
					xtype:'textfield',
					hidden:true
				},{
					id:'oppowellmap',
					name:'oppowellmap',
					xtype:'textfield',
					hidden:true
				},{
					id:'oppofaceId',
					name:'oppofaceId',
					xtype:'textfield',
					hidden:true
				},{
					id:'oppofaceface',
					name:'oppofaceface',
					xtype:'textfield',
					hidden:true
				},{
					id:'startDeviceName',
					name : 'pipeInfoBean.startDeviceName',
					style:'margin-top:5px',
					xtype : 'textarea',
					fieldLabel : '',
					height:60,
					width : 250
				}]	
			},{
				layout : 'form', // 从上往下布局
				items : [{
					layout:'column',
					style:'margin:0 0 0 120',
					items:[{
						xtype : 'button',
						text : "选择对端井面",
						handler : function() {
							checkcable();
						}
					},{
						xtype : 'button',
						text : "清空",
						style:'margin-left:5px',
						handler : function() {
							washOppo();
						}
					}]
				},{
					layout:'column',
					style:'margin-top:5px',
					items:[{   
						layout:'form',
						items:[{
							xtype : 'combo',
							id : 'rows',
							name : "tuberows",
							fieldLabel : '横',
							editable : false,// 设置为false以阻止用户直接向该输入项输入文本
							store : this.rowStore,
							mode : "local",// 加载本地数据 设置为'local' (默认值为 'remote'，它将从服务器加载数据)
							triggerAction : "all",// 下拉框获得了焦点或者被点击了
							displayField : "text",
							valueField : "value",
							hiddenName : 'tuberows',// 创建一个表单隐藏域来存储输入项的值
							width :100
						}]
					},{
						layout:'form',
						labelWidth : 55,
						items:[{
							xtype : 'combo',
							id : 'cols',
							name : "tubecols",
							fieldLabel : '竖',
							editable : false,// 设置为false以阻止用户直接向该输入项输入文本
							store : this.colStore,
							mode : "local",// 加载本地数据 设置为'local' (默认值为 'remote'，它将从服务器加载数据)
							triggerAction : "all",// 下拉框获得了焦点或者被点击了
							displayField : "text",
							valueField : "value",
							hiddenName : 'tubecols',// 创建一个表单隐藏域来存储输入项的值
							width : 100
						}]
					},{
						xtype : 'button',
						text : "保存",
						style:'margin-left:5px',
						handler : function() {
							dosubmit();
						}
					},{
						xtype : 'button',
						text : "筛选",
						style:'margin-left:5px',
						handler : function() {
							showDisTube();
						}
					}]
				}]
			},{
			layout : 'form', // 从上往下布局
			style:'margin-top:15px',
			items : [{
				html : '<span style="font-size:10px; color: #15428b; margin:0 0 0 120; font-weight:bold"> 管孔信息</span>'
			},{
				id : 'gk',
				html : '<table border="1" cellspacing="0" cellpadding="0" >'+
						'<tr><td onclick="guankong(0101);" onmouseover="bianse(this);" onmouseout="bianhui(this);">'+
						"0101"+'</td></tr></table>'
			}]
		}]
	}
	var forms = new Ext.form.FieldSet({
		id : "forms",
		title : '井立面信息',
		collapsible : true,
		autoScroll : true,
		layout : 'column',
		style : {
			marginRight : 'auto',
			marginLeft : 'auto'
		},
		items:[{
			layout:'form',
			width:350,
			items:[{
				style : 'margin-bottom:5px',
				html : '<span style="font-size:10px; color: #15428b; margin:0 0 0 80; font-weight:bold">长按立面按钮选择立面编号</span>'
			},form1,form2,form3]
		},{
			layout:'form',
			width:650,
			items:[
				duiduan
			]
		}]
	})
	var sontube={
		layout : 'form',
		items:[{
			id : 'sonTable',
			html :''
		}]
	}
	var sonform = new Ext.form.FieldSet({
		title : '子孔信息',
		collapsible : true,
		autoScroll : true,
		layout : 'column',
		style : {
			marginRight : 'auto',
			marginLeft : 'auto'
		},
		items:[ sontube ]
	})
	//管孔信息
	this.isFatherStore = new Ext.data.SimpleStore({
    		fields : ['value', 'text'],
    		data : [['', ''],['1', '母孔'], ['3', '单孔']]
    	})
    this.subTubeAmountStore = new Ext.data.SimpleStore({
    		fields : ['value', 'text'],
    		data : [['', ''],['1', '1'], ['2', '2'],['3', '3'], ['4', '4'],['5', '5'], ['6', '6'],['7', '7'], ['8', '8']]
    	})
	var tuberow1 = {
		layout : 'column', // 从左往右布局
		items : [{
			layout : 'form', // 从上往下布局
			items : [{
				id : 'tubeId',
				name : 'tubeInfoBean.tubeId',
				xtype : 'textfield',
				fieldLabel : '管孔ID',
				hidden:true
				}]
			},{
			layout : 'form', // 从上往下布局
			items : [{
				id : 'wellId',
				name : 'tubeInfoBean.wellId',
				xtype : 'textfield',
				fieldLabel : '所属井ID',
				hidden:true
				}]
			},{
			layout : 'form', // 从上往下布局
			items : [{
				id : 'wellname1',
				name : '',
				xtype : 'textfield',
				hidden:true
				}]
			},{
			columnWidth : .3, // 该列有整行中所占百分比
			height : 40,
			layout : 'form', // 从上往下布局
			items : [{
				id : 'tubeName',
				name : 'tubeInfoBean.tubeName',
				xtype : 'textfield',
				fieldLabel : '母孔/单孔名称',
				width : 140
				}]
			},{
			columnWidth : .3, // 该列有整行中所占百分比
			height : 40,
			layout : 'form', // 从上往下布局
			items : [{
				id : 'tubeNumber',
				name : 'tubeInfoBean.tubeNumber',
				xtype : 'textfield',
				fieldLabel : '母孔/单孔编号',
				width : 140
				}]
			},{
			columnWidth : .4, // 该列有整行中所占百分比
			layout : 'form', // 从上往下布局
			items : [{
				layout:'column',
				fieldLabel : '管孔类型',
				items:[{
					xtype : 'combo',
					id : 'isFather',
					name : "tubeInfoBean.isFather",
					// hideTrigger:true,
					editable : false,// 设置为false以阻止用户直接向该输入项输入文本
					store : this.isFatherStore,
					mode : "local",// 加载本地数据 设置为'local' (默认值为 'remote'，它将从服务器加载数据)
					triggerAction : "all",// 下拉框获得了焦点或者被点击了
					displayField : "text",
					valueField : "value",
					hiddenName : 'tubeInfoBean.isFather',// 创建一个表单隐藏域来存储输入项的值
					width : 80,
					listeners:{
    					"select":function(v){
    						onchangeIsFather(); 
    					}
					}
				},{
					xtype : 'combo',
					id : 'sonNum',
					name : "ttubeInfoBean.subTubeAmount",
					style:'margin-left:5px',
					// hideTrigger:true,
					editable : false,// 设置为false以阻止用户直接向该输入项输入文本
					store : this.subTubeAmountStore,
					mode : "local",// 加载本地数据 设置为'local' (默认值为 'remote'，它将从服务器加载数据)
					triggerAction : "all",// 下拉框获得了焦点或者被点击了
					displayField : "text",
					valueField : "value",
					hiddenName : 'tubeInfoBean.subTubeAmount',// 创建一个表单隐藏域来存储输入项的值
					width : 40,
					hidden:true
				}]
			}]
		}]
	};
	var statusStore= new Ext.data.SimpleStore({
		fields : ['value', 'text'],
    	data : [['', ''],['1', '占用'], ['2', '空闲'],['3', '预占'], ['4', '暂拆'],['5', '损坏'], 
    	['6', '废弃'],['7', '外租'], ['8', '出租']]
	})
	var tuberow2 = {
		layout : 'column', // 从左往右布局
		items : [{
			columnWidth : .3, // 该列有整行中所占百分比
			height : 40,
			layout : 'form', // 从上往下布局
			items : [{
				id : 'subTubeArrangeMode',
				name : 'tubeInfoBean.subTubeArrangeMode',
				xtype : 'textfield',
				fieldLabel : '子管孔排列方式',
				width : 140
			}]
		},{
			columnWidth : .3, // 该列有整行中所占百分比
			height : 40,
			layout : 'form', // 从上往下布局
			items : [{
				xtype : 'combo',
				id : 'tubeStatusEnumId',
				name : "tubeInfoBean.tubeStatusEnumId",
				fieldLabel : '管孔状态',
				// hideTrigger:true,
				editable : false,// 设置为false以阻止用户直接向该输入项输入文本
				store : statusStore,
				mode : "local",// 加载本地数据 设置为'local' (默认值为 'remote'，它将从服务器加载数据)
				triggerAction : "all",// 下拉框获得了焦点或者被点击了
				displayField : "text",
				valueField : "value",
				hiddenName : 'tubeInfoBean.tubeStatusEnumId',// 创建一个表单隐藏域来存储输入项的值
				width : 140
			}]
		}, {
			columnWidth : .3,
			layout : 'form',
			items : [{
				id : 'tubeDiameter',
				name : 'tubeInfoBean.tubeDiameter',
				xtype : 'textfield',
				fieldLabel : '管孔直径（厘米）',
				width : 140,
				invalidText : '请输入正确的管孔直径！',
				validationEvent : 'keydown',
				validationDelay : 100,
				validator : function(thisText) {
					if (thisText == "") {
						return true;
					}
					var reg=/^[0-9].*$/;
					if(!reg.test(thisText)){
						Ext.getCmp('tubeDiameter').markInvalid('填写格式不正确，请重新填写!');
						return false;
					}else{
						return true;
					}
				}
			}]
		}]
	};
	this.colorStore=new Ext.data.SimpleStore({
		fields : ['value', 'text'],
    	data : [['', ''],['1', '红'], ['2', '橙'],['3', '黄'], ['4', '绿'],['5', '青'], 
    	['6', '蓝'],['7', '紫'], ['8', '白'], ['9', '黑']]
	})
	this.materialStore=new Ext.data.SimpleStore({
		fields : ['value', 'text'],
    	data : [['', ''],['1', '塑料管'], ['2', '陶瓷管'],['3', '水泥管'], ['4', '钢管']]
	})
	this.shapeStore=new Ext.data.SimpleStore({
		fields : ['value', 'text'],
    	data : [['', ''],['1', '圆形'], ['2', '方形'],['3', '梅花管形']]
	})
	var tuberow3 = {
		layout : 'column', // 从左往右布局
		items : [{
			columnWidth : .3, // 该列有整行中所占百分比
			height : 40,
			layout : 'form', // 从上往下布局
			items : [{
				xtype : 'combo',
				id : 'tubeColor',
				name : "tubeInfoBean.tubeColor",
				fieldLabel : '管孔颜色',
				editable : false,// 设置为false以阻止用户直接向该输入项输入文本
				store : this.colorStore,
				mode : "local",// 加载本地数据 设置为'local' (默认值为 'remote'，它将从服务器加载数据)
				triggerAction : "all",// 下拉框获得了焦点或者被点击了
				displayField : "text",
				valueField : "value",
				hiddenName : 'tubeInfoBean.tubeColor',// 创建一个表单隐藏域来存储输入项的值
				width : 140
			}]
		},{
			columnWidth : .3, // 该列有整行中所占百分比
			height : 40,
			layout : 'form', // 从上往下布局
			items : [{
				xtype : 'combo',
				id : 'tubeMaterial',
				name : "tubeInfoBean.tubeMaterial",
				fieldLabel : '管孔材质',
				// hideTrigger:true,
				editable : false,// 设置为false以阻止用户直接向该输入项输入文本
				store : this.materialStore,
				mode : "local",// 加载本地数据 设置为'local' (默认值为 'remote'，它将从服务器加载数据)
				triggerAction : "all",// 下拉框获得了焦点或者被点击了
				displayField : "text",
				valueField : "value",
				hiddenName : 'tubeInfoBean.tubeMaterial',// 创建一个表单隐藏域来存储输入项的值
				width : 140
			}]
		}, {
			columnWidth : .3, // 该列有整行中所占百分比
			height : 40,
			layout : 'form', // 从上往下布局
			items : [{
				xtype : 'combo',
				id : 'tubeShape',
				name : "tubeInfoBean.tubeShape",
				fieldLabel : '管孔形状',
				// hideTrigger:true,
				editable : false,// 设置为false以阻止用户直接向该输入项输入文本
				store : this.shapeStore,
				mode : "local",// 加载本地数据 设置为'local' (默认值为 'remote'，它将从服务器加载数据)
				triggerAction : "all",// 下拉框获得了焦点或者被点击了
				displayField : "text",
				valueField : "value",
				hiddenName : 'tubeInfoBean.tubeShape',// 创建一个表单隐藏域来存储输入项的值
				width : 140
			}]
		}]
	};
	this.rentFlagStore=new Ext.data.SimpleStore({
		fields : ['value', 'text'],
    	data : [['', ''],['1', '是'], ['2', '否']]
	})
	this.resourceStore=new Ext.data.SimpleStore({
		fields : ['value', 'text'],
    	data : [['', ''],['1', '设计状态'], ['2', '工程建设期'], ['3', '工程可用期'], 
    		['4', '工程初验后试运行'], ['5', '工程终验后在网'], ['6', '退网状态']]
	})
	var tuberow4 = {
		layout : 'column', // 从左往右布局
		items : [{
			columnWidth : .3, // 该列有整行中所占百分比
			height : 40,
			layout : 'form', // 从上往下布局
			items : [{
				xtype : 'combo',
				id : 'rentFlag',
				name : "tubeInfoBean.rentFlag",
				fieldLabel : '是否租用',
				editable : false,// 设置为false以阻止用户直接向该输入项输入文本
				store : this.rentFlagStore,
				mode : "local",// 加载本地数据 设置为'local' (默认值为 'remote'，它将从服务器加载数据)
				triggerAction : "all",// 下拉框获得了焦点或者被点击了
				displayField : "text",
				valueField : "value",
				hiddenName : 'tubeInfoBean.rentFlag',// 创建一个表单隐藏域来存储输入项的值
				width : 140
			}]
		},{
			columnWidth : .3,
			layout : 'form',
			items : [{
				id : 'rentOrg',
				name : 'tubeInfoBean.rentOrg',
				xtype : 'textfield',
				fieldLabel : '租用单位',
				width : 140
			}]
		}, {
			columnWidth : .3, // 该列有整行中所占百分比
			height : 40,
			layout : 'form', // 从上往下布局
			items : [{
				xtype : 'combo',
				id : 'resourceLifePeriodEnumId',
				name : "tubeInfoBean.resourceLifePeriodEnumId",
				fieldLabel : '资源生命周期',
				// hideTrigger:true,
				editable : false,// 设置为false以阻止用户直接向该输入项输入文本
				store : this.resourceStore,
				mode : "local",// 加载本地数据 设置为'local' (默认值为 'remote'，它将从服务器加载数据)
				triggerAction : "all",// 下拉框获得了焦点或者被点击了
				displayField : "text",
				valueField : "value",
				hiddenName : 'tubeInfoBean.resourceLifePeriodEnumId',// 创建一个表单隐藏域来存储输入项的值
				width : 140
			}]
		}]
	};
	var tuberow5={
		id:'docabletr',
		hidden:true,
		layout : 'column', // 从左往右布局
		items : [{
			columnWidth : .5,
			layout:'form',
			items:[{
				html:'<span style="font-size:10px;  margin:0 0 0 120;"> 承载的缆段</span>'
			},{
				/*id:'docable',
				name : 'pipeInfoBean.startDeviceName',
				style:'margin-top:5px',
				xtype : 'textarea',
				height:200,
				width : 300*/
				//id:'docable',
				html:'<table id="docable" border="1" cellspacing="0" cellpadding="0" width="400px" height="200px">'+
						'</table>'
			},{
				xtype:'button',
				text:'添加',
				style:'margin:0 0 0 120',
				handler:function(){
					addCables(1);
				}
			}]
		},{
			columnWidth : .5,
			layout:'form',
			items:[{
				html:'<span style="font-size:10px;  margin:0 0 0 120;">承载的缆段(备用)</span>'
			},{
				/*id:'docable2',
				name : 'pipeInfoBean.startDeviceName',
				style:'margin-top:5px',
				xtype : 'textarea',
				height:200,
				width : 300*/
				html:'<table id="docable2" border="1" cellspacing="0" cellpadding="0" width="400px" height="200px">'+
						'</table>'
			},{
				xtype:'button',
				text:'添加',
				style:'margin:0 0 0 120',
				handler:function(){
					addCables(2);
				}
			}]
		}]
	}
	
	var tuberow11 = {
		layout : 'column', // 从左往右布局
		items : [{
			layout : 'form', // 从上往下布局
			items : [{
				id : 'tubeId2',
				name : 'tubeInfoBean.tubeId',
				xtype : 'textfield',
				fieldLabel : '管孔ID',
				hidden:true
				}]
			},{
			layout : 'form', // 从上往下布局
			items : [{
				id : 'fatherPoreId',
				name : 'tubeInfoBean.fatherPoreId',
				xtype : 'textfield',
				fieldLabel : '母孔ID',
				hidden:true
				}]
			},{
			layout : 'form', // 从上往下布局
			items : [{
				id : 'wellIdzi',
				name : '',
				xtype : 'textfield',
				hidden:true
				}]
			},{
			columnWidth : .3, // 该列有整行中所占百分比
			height : 40,
			layout : 'form', // 从上往下布局
			items : [{
				id : 'tubeName2',
				name : 'tubeInfoBean.tubeName',
				xtype : 'textfield',
				fieldLabel : '子孔名称',
				width : 140
				}]
		},{
			columnWidth : .3, // 该列有整行中所占百分比
			height : 40,
			layout : 'form', // 从上往下布局
			items : [{
				id : 'tubeNumber2',
				name : 'tubeInfoBean.tubeNumber',
				xtype : 'textfield',
				fieldLabel : '子孔编号',
				width : 140
			}]
		},{
			columnWidth : .3, // 该列有整行中所占百分比
			height : 40,
			layout : 'form', // 从上往下布局
			items : [{
				xtype : 'combo',
				id : 'tubeStatusEnumId2',
				name : "tubeInfoBean.tubeStatusEnumId",
				fieldLabel : '管孔状态',
				// hideTrigger:true,
				editable : false,// 设置为false以阻止用户直接向该输入项输入文本
				store : statusStore,
				mode : "local",// 加载本地数据 设置为'local' (默认值为 'remote'，它将从服务器加载数据)
				triggerAction : "all",// 下拉框获得了焦点或者被点击了
				displayField : "text",
				valueField : "value",
				hiddenName : 'tubeInfoBean.tubeStatusEnumId',// 创建一个表单隐藏域来存储输入项的值
				width : 140
			}]
		}]
	};
	var tuberow22 = {
		layout : 'column', // 从左往右布局
		items : [{
			columnWidth : .3,
			layout : 'form',
			items : [{
				id : 'tubeDiameter2',
				name : 'tubeInfoBean.tubeDiameter',
				xtype : 'textfield',
				fieldLabel : '管孔直径（厘米）',
				width : 140,
				invalidText : '请输入正确的管孔直径！',
				validationEvent : 'keydown',
				validationDelay : 100,
				validator : function(thisText) {
					if (thisText == "") {
						return true;
					}
					var reg=/^[0-9].*$/;
					if(!reg.test(thisText)){
						Ext.getCmp('tubeDiameter').markInvalid('填写格式不正确，请重新填写!');
						return false;
					}else{
						return true;
					}
				}
			}]
		},{
			columnWidth : .3, // 该列有整行中所占百分比
			height : 40,
			layout : 'form', // 从上往下布局
			items : [{
				xtype : 'combo',
				id : 'tubeColor2',
				name : "tubeInfoBean.tubeColor",
				fieldLabel : '管孔颜色',
				editable : false,// 设置为false以阻止用户直接向该输入项输入文本
				store : this.colorStore,
				mode : "local",// 加载本地数据 设置为'local' (默认值为 'remote'，它将从服务器加载数据)
				triggerAction : "all",// 下拉框获得了焦点或者被点击了
				displayField : "text",
				valueField : "value",
				hiddenName : 'tubeInfoBean.tubeColor',// 创建一个表单隐藏域来存储输入项的值
				width : 140
			}]
		},{
			columnWidth : .3, // 该列有整行中所占百分比
			height : 40,
			layout : 'form', // 从上往下布局
			items : [{
				xtype : 'combo',
				id : 'tubeMaterial2',
				name : "tubeInfoBean.tubeMaterial",
				fieldLabel : '管孔材质',
				// hideTrigger:true,
				editable : false,// 设置为false以阻止用户直接向该输入项输入文本
				store : this.materialStore,
				mode : "local",// 加载本地数据 设置为'local' (默认值为 'remote'，它将从服务器加载数据)
				triggerAction : "all",// 下拉框获得了焦点或者被点击了
				displayField : "text",
				valueField : "value",
				hiddenName : 'tubeInfoBean.tubeMaterial',// 创建一个表单隐藏域来存储输入项的值
				width : 140
			}]
		}]
	};
	var tuberow33 = {
		layout : 'column', // 从左往右布局
		items : [{
			columnWidth : .3, // 该列有整行中所占百分比
			height : 40,
			layout : 'form', // 从上往下布局
			items : [{
				xtype : 'combo',
				id : 'tubeShape2',
				name : "tubeInfoBean.tubeShape",
				fieldLabel : '管孔形状',
				// hideTrigger:true,
				editable : false,// 设置为false以阻止用户直接向该输入项输入文本
				store : this.shapeStore,
				mode : "local",// 加载本地数据 设置为'local' (默认值为 'remote'，它将从服务器加载数据)
				triggerAction : "all",// 下拉框获得了焦点或者被点击了
				displayField : "text",
				valueField : "value",
				hiddenName : 'tubeInfoBean.tubeShape',// 创建一个表单隐藏域来存储输入项的值
				width : 140
			}]
		},{
			columnWidth : .3, // 该列有整行中所占百分比
			height : 40,
			layout : 'form', // 从上往下布局
			items : [{
				xtype : 'combo',
				id : 'rentFlag2',
				name : "tubeInfoBean.rentFlag",
				fieldLabel : '是否租用',
				editable : false,// 设置为false以阻止用户直接向该输入项输入文本
				store : this.rentFlagStore,
				mode : "local",// 加载本地数据 设置为'local' (默认值为 'remote'，它将从服务器加载数据)
				triggerAction : "all",// 下拉框获得了焦点或者被点击了
				displayField : "text",
				valueField : "value",
				hiddenName : 'tubeInfoBean.rentFlag',// 创建一个表单隐藏域来存储输入项的值
				width : 140
			}]
		},{
			columnWidth : .3,
			layout : 'form',
			items : [{
				id : 'rentOrg2',
				name : 'tubeInfoBean.rentOrg',
				xtype : 'textfield',
				fieldLabel : '租用单位',
				width : 140
			}]
		}]
	};
	var tuberow44 = {
		layout : 'column', // 从左往右布局
		items : [{
			columnWidth : .3, // 该列有整行中所占百分比
			height : 40,
			layout : 'form', // 从上往下布局
			items : [{
				xtype : 'combo',
				id : 'resourceLifePeriodEnumId2',
				name : "tubeInfoBean.resourceLifePeriodEnumId",
				fieldLabel : '资源生命周期',
				// hideTrigger:true,
				editable : false,// 设置为false以阻止用户直接向该输入项输入文本
				store : this.resourceStore,
				mode : "local",// 加载本地数据 设置为'local' (默认值为 'remote'，它将从服务器加载数据)
				triggerAction : "all",// 下拉框获得了焦点或者被点击了
				displayField : "text",
				valueField : "value",
				hiddenName : 'tubeInfoBean.resourceLifePeriodEnumId',// 创建一个表单隐藏域来存储输入项的值
				width : 140
			}]
		}]
	};
	var tuberow55={
		id:'docabletr2',
		layout : 'column', // 从左往右布局
		items : [{
			columnWidth : .5,
			layout:'form',
			items:[{
				html:'<span style="font-size:10px;  margin:0 0 0 120;"> 承载的缆段</span>'
			},{
				/*id:'doSoncable',
				name : 'pipeInfoBean.startDeviceName',
				style:'margin-top:5px',
				xtype : 'textarea',
				height:200,
				width : 300*/
				html:'<table id="doSoncable" border="1" cellspacing="0" cellpadding="0" width="400px" height="200px">'+
						'</table>'
			},{
				xtype:'button',
				text:'添加',
				style:'margin:0 0 0 120',
				handler:function(){
					addSonCables(1);
				}
			}]
		},{
			columnWidth : .5,
			layout:'form',
			items:[{
				html:'<span style="font-size:10px;  margin:0 0 0 120;">承载的缆段(备用)</span>'
			},{
				/*id:'doSoncable2',
				name : 'pipeInfoBean.startDeviceName',
				style:'margin-top:5px',
				xtype : 'textarea',
				height:200,
				width : 300*/
				html:'<table id="doSoncable2" border="1" cellspacing="0" cellpadding="0" width="400px" height="200px">'+
						'</table>'
			},{
				xtype:'button',
				text:'添加',
				style:'margin:0 0 0 120',
				handler:function(){
					addSonCables(2);
				}
			}]
		}]
	}
	var tube = new Ext.form.FieldSet({
		id : 'tube',
		hidden:true,
		title : '管孔基本信息',
		collapsible : true,
		items : [tuberow1,tuberow2,tuberow3,tuberow4,tuberow5],
		buttonAlign : 'center',
		buttons : [{
			text:'保存',
			tooltip : '保存管孔信息',
			tooltipType : 'qtip',
			handler : function(){
				dosubmitTube();
			}
		},{
			text:'取消',
			tooltip : '取消',
			tooltipType : 'qtip',
			handler : function(){
				Canceldo();
			}
		}]
	})
	var tube1=new Ext.form.FieldSet({
		id : 'tube1',
		hidden:true,
		title : '管孔基本信息',
		collapsible : true,
		items : [tuberow11,tuberow22,tuberow33,tuberow44,tuberow55],
		buttonAlign : 'center',
		buttons : [{
			text:'保存',
			tooltip : '保存管孔信息',
			tooltipType : 'qtip',
			handler : function(){
				dosubmitSonTube();
			}
		},{
			text:'取消',
			tooltip : '取消',
			tooltipType : 'qtip',
			handler : function(){
				Canceldo2();
			}
		}]
	})
	var workOrderForm = new Ext.form.FormPanel({
		id : 'workOrderForm',
		frame : true,
		width : '1125',
		bodyStyle : 'padding:10px 30px 50px 20px ; border-width:0 0 0 0',
		layout : 'form',
		labelAlign : 'right',
		autoScroll : false,
		style : {
			marginRight : 'auto',
			marginLeft : 'auto'
		},
		items : [{
			xtype : 'panel',
			autoScroll : false,
			style : 'text-align:center',
			items : [{
				html : '<span style="font-size:20px; color: #15428b; font-weight:bold">井立面管理</span>'
			}]
		}, basic,forms,sonform,tube,tube1]
	});
	var main = new Ext.Panel({
		id : '123',
		bodyStyle : 'padding:0px 20px 20px 40px ; border-width:0 0 0 0px',
		frame : true,
		applyTo : Ext.get("centerDiv"),// 渲染到哪个对象上
		style : 'height:100%;whdth:100%;',
		// autoScroll : true,
		border : false,
		items : [workOrderForm]
	})
	ondown();
});
