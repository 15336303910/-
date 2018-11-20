Ext.namespace("com.increase.cen.poleline");
var limit = null;
com.increase.cen.poleline.FaceNumber = Ext.extend(Ext.form.FormPanel, {
	bodyStyle : "padding:5px;",
	frame : true,
	bodyBorder : false,
	labelAlign : "right",
	buttonAlign : 'center',
	width : 400,
	autoScroll : true,
	initComponent : function(config) {
		// 一页显示多少条数据
		limit = Ext.getDom("limit").value;
		// 启动悬停提示
		Ext.QuickTips.init();
		// 提示的方式
		Ext.form.Field.prototype.msgTarget = 'qtip';
		// 用光缆信息是否存在
		var idIsExist = true;
		var form1 = {
			layout : 'column', // 从左往右布局
			style:'margin:0 0 0 80',
			items : [{
				id : 'f1',
				xtype : 'button',
				text : "1",
				width:50,
				height:50,
				handler : function() {
					fuzhi(this);
				}
			},{
				id : 'f2',
				xtype : 'button',
				text : "2",
				width:50,
				height:50,
				style : 'margin-left:100px',
				handler : function() {
					fuzhi(this);
				}
			}]
		};
		var form2 = {
			layout : 'column', // 从左往右布局
			style:'margin:0 0 0 80',
			items : [{
				id : 'f3',
				xtype : 'button',
				text : "3",
				width:50,
				height:50,
				handler : function() {
					fuzhi(this);
				}
			},{
				id : 'f4',
				xtype : 'button',
				text : "4",
				width:50,
				height:50,
				style : 'margin-left:100px',
				handler : function() {
					fuzhi(this);
				}
			}]
		};
		var form3 = {
			layout : 'column', // 从左往右布局
			style:'margin:0 0 0 80',
			items : [{
				id : 'f5',
				xtype : 'button',
				text : "5",
				width:50,
				height:50,
				handler : function() {
					fuzhi(this);
				}
			},{
				id : 'f6',
				xtype : 'button',
				text : "6",
				width:50,
				height:50,
				style : 'margin-left:100px',
				handler : function() {
					fuzhi(this);
				}
			}]
		};
		var form4 = {
			layout : 'column', // 从左往右布局
			style:'margin:0 0 0 80',
			items : [{
				id : 'f7',
				xtype : 'button',
				text : "7",
				width:50,
				height:50,
				handler : function() {
					fuzhi(this);
				}
			},{
				id : 'f8',
				xtype : 'button',
				text : "8",
				width:50,
				height:50,
				style : 'margin-left:100px',
				handler : function() {
					fuzhi(this);
				}
			}]
		};
		var form5 = {
			layout : 'column', // 从左往右布局
			style:'margin:0 0 0 80',
			items : [{
				xtype : 'button',
				text : "清除",
				width:50,
				height:50,
				handler : function() {
					qingchu();
				}
			},{
				xtype : 'button',
				text : "关闭",
				style : 'margin-left:100px',
				width:50,
				height:50,
				handler : function() {
					hideFaceNumber();
				}
			}]
		};
		/*var form1 = {
			layout : 'column', // 从左往右布局
			items : [{
				html:'<table  border="1"  width="350" height="50">'+
				'<tr><td align="center" ><input type="button" id="f1" value="1" style="width:50px; height:50px" onclick="fuzhi(this);"/></td>'+
				'<td align="center" ><input type="button" id="f2" value="2" style="width:50px; height:50px" onclick="fuzhi(this);"/></td></tr>'+
				'<tr><td align="center" ><input type="button" id="f3" value="3" style="width:50px; height:50px" onclick="fuzhi(this);"/></td>'+
				'<td align="center" ><input type="button" id="f4" value="4" style="width:50px; height:50px" onclick="fuzhi(this);"/></td></tr>'+
				'<tr><td align="center" ><input type="button" id="f5" value="5" style="width:50px; height:50px" onclick="fuzhi(this);"/></td>'+
				'<td align="center" ><input type="button" id="f6" value="6" style="width:50px; height:50px" onclick="fuzhi(this);"/></td></tr>'+
				'<tr><td align="center" ><input type="button" id="f7" value="7" style="width:50px; height:50px" onclick="fuzhi(this);"/></td>'+
				'<td align="center" ><input type="button" id="f8" value="8" style="width:50px; height:50px" onclick="fuzhi(this);"/></td></tr>'+
				'<tr><td align="center" ><input type="button" value="清除" style="width:50px; height:50px" onclick="qingchu();"/></td>'+
				'<td align="center" ><input type="button" value="关闭" style="width:50px; height:50px" onclick="hideFaceNumber();"/></td></tr></table>'
			}]
		};*/
		this.items = [{
			xtype : 'fieldset',
			title : '选择井面对应的数字',
			collapsible : true,
			items : [form1,form2,form3,form4,form5]
		}];

		// 重写initComponent superclass.initComponent.call(this);来调用父类的初始化方法.
		com.increase.cen.poleline.FaceNumber.superclass.initComponent.call(this);
	}
});
Ext.reg("faceNumber", com.increase.cen.poleline.FaceNumber);
