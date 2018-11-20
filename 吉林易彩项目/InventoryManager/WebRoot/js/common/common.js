/**
 * var stationText = itemText(.33,60,'stationName','站点名称');
 * 一般用于formpanel中的
 * 查询textfield
 * @param columnw 占的比例
 * @param labelw 备注宽度
 * @param id 标识
 * @param fieldLabel 备注内容
 * @param values 是给赋值用的
 * @param method 单击时调用
 */
function itemText(columnw,labelw,id,fieldLabel,values,method,type){
	var val = "";
	var flag = false;
	if(values == null){
		flag = false;
		val ="";
	}else{
		flag = true;
		val = values;
	}
	if(type == 'u'){
		flag = false;
		val = values;
	}
	var panel = new Ext.Panel({
		columnWidth:columnw,
		border : false,
		layout : "form",
		labelAlign : 'center',
		labelWidth :labelw,
		items:[{
			id:id,
			name:id,
			xtype : "textfield",
			fieldLabel:fieldLabel,
			emptyText:fieldLabel,
			readOnly:flag,
			value:val,
			anchor : '85%',
			listeners: {// select监听函数
				focus:function(){
					if(method != null){
						method();
					}
				}
			}
		}]
	});
	return panel;
}

/**
 * var stationText = basicArea(.33,60,'stationName','站点名称');
 * 一般用于formpanel中的
 * 查询textfield
 * @param columnw 占的比例
 * @param labelw 备注宽度
 * @param id 标识
 * @param fieldLabel 备注内容
 * @param values 是给赋值用的
 * @param method 单击时调用
 */
function basicArea(columnw,labelw,id,fieldLabel,values,method){
	var val = "";
	var flag = false;
	if(values == null){
		flag = false;
		val ="";
	}else{
		flag = true;
		val = values;
	}
	var panel = new Ext.Panel({
		columnWidth:columnw,
		border : false,
		layout : "form",
		labelAlign : 'center',
		labelWidth :labelw,
		items:[{
			id:id,
			name:id,
			xtype : "textarea",
			fieldLabel:fieldLabel,
			emptyText:fieldLabel,
			readOnly:flag,
			value:val,
			anchor : '85%',
			listeners: {// select监听函数
				focus:function(){
					if(method != null){
						method();
					}
				}
			}
		}]
	});
	return panel;
}
/**
 * var stationText = labelText(.33,60,'stationName','站点名称');
 * 一般用于formpanel中的
 * 查询textfield
 * @param columnw 占的比例
 * @param labelw 备注宽度
 * @param id 标识
 * @param fieldLabel 备注内容
 * @param values 是给赋值用的
 * @param method 单击时调用
 */
function labelText(columnw,labelw,fieldLabel,values){
	var val = "";
	var flag = false;
	if(values == null){
		flag = false;
		val ="";
	}else{
		flag = true;
		val = values;
	}
	var panel = new Ext.Panel({
		columnWidth:columnw,
		border : false,
		layout : "form",
		labelAlign : 'center',
		labelWidth :labelw,
		items:[{
			xtype : "label",
			fieldLabel:fieldLabel,
			emptyText:fieldLabel,
			readOnly:flag,
			text:val,
			anchor : '85%'
		}]
	});
	return panel;
}

/**
 * 下拉列表
 * var quenData = [{"value":"immed","name":"立即巡检"},{"value":"cycle","name":"周期巡检"}];
 * var quenData =  [['well','管道'],['pole','杆路'],['buried','直埋']];
	var quenCombo = basicCombo(quenData,.3,60,'quenExe','执行频度');
 * @param dataArr
 * @param columnw
 * @param labelw
 * @param id
 * @param fieldLabel
 */
function basicCombo(dataArr,columnw,labelw,id,fieldLabel){
	var combostore = new Ext.data.ArrayStore({
		fields : [ 'id', 'name' ],
		data : dataArr
	});
	var panel =  new Ext.Panel({
		layout:"form",
		labelWidth:labelw,
		columnWidth:columnw,
		border:false,
		items:[{
			xtype:'combo',
			id:id,
			name:id,
			fieldLabel : fieldLabel,
			store : combostore,
			displayField : 'name',
			valueField : 'id',
			triggerAction : 'all',
			emptyText : '请选择...',
			allowBlank : false,
			blankText : '请选择政治面貌',
			editable : false,
			mode : 'local',
			anchor:'95%'
		}]
	});
	return panel;
}

/**
 * 这是一个后台刷新的
 * commCombo('/common/city',.33,40,'city','地市',loadCounty);
 * @param url
 * @param columnw
 * @param labelw
 * @param id
 * @param fieldLabel
 * @param 模式
 * @param value 值
 * @param 展示值
 * @param method
 */
function commCombo(url,columnw,labelw,id,fieldLabel,mode,value,disValue,method){
	var panel  = new Ext.Panel({
		columnWidth:columnw,
		border : false,
		layout : "form",
		labelAlign : 'center',
		labelWidth :labelw,
		items:[{
			id:id+"_id",
			hiddenName:id,
			xtype : "combo",
			autoSelect :true,
			fieldLabel:fieldLabel,
			emptyText:fieldLabel,
			store:new Ext.data.Store({
				proxy: new Ext.data.HttpProxy({url:context_path+"/"+url}),
				reader: new Ext.data.JsonReader({},[
					{name:value} ,
					{name:disValue}
				])
			}),
			valueField:value,
			displayField:disValue,
			typeAhead: true,
			mode: mode,
			anchor : '85%',
			triggerAction: 'all',
			listeners: {// select监听函数
				select : function(combo, record, index){
					if(method != null){
						method();
					}
				}
			}
		}]
	});
	return panel;
}

/**
 * 统一的日期格式
 * @param columnW
 * @param labelw
 * @param id
 * @param fieldLabel
 */
function basicDate(columnW,labelw,id,fieldLabel,values){
	var val = "";
	var flag = false;
	if(values == null){
		flag = false;
		val ="";
	}else{
		flag = true;
		val = values;
	}
	var panel  = new Ext.Panel({
		columnWidth : columnW,
		border : false,
		layout : "form",
		labelWidth:labelw,
		items : [{
			id:id,
			name :id,
			xtype : "datefield",
			format:'Y-m-d',
			fieldLabel : fieldLabel,
			emptyText:'请选择'+fieldLabel,
			editable:flag,
			value:val,
			anchor : '85%'
		}]
	});
	return panel;
}
/**
 * 得到一个gridstore;
 * var column = "[{header:'计划id',dataIndex:'ID',hidden:true,width:'150',fun:null}," +
				 "{header:'计划名称',dataIndex:'PLAN_NAME',hidden:false,width:'150',fun:null}," +
				 "{header:'地市',dataIndex:'CITY_NAME',hidden:false,width:'150',fun:null}," +
				 "{header:'站点',dataIndex:'RES_NAME',hidden:false,width:'150',fun:null}," +
				 "{header:'站点类型',dataIndex:'SITE_TYPE',hidden:false,width:'150',fun:null}," +
				 "{header:'经度',dataIndex:'LONGITUDE',hidden:false,width:'150',fun:null}" +
				 "]";
 * var result = basicStore(column,'newStationCheckAction!queryPlanResult.ilf','totalCount','root')
 * @param column
 * @param url
 * @param total
 * @param root
 */
function basicStore(column,url,total,root){
	var result = new Object();
	var datas = eval('(' + column + ')');
	
	var sm = new Ext.grid.CheckboxSelectionModel();
	result.sm = sm;
	var colMArray = new Array();
	colMArray[0] = sm;
	var readArray = new Array();
	for(var i=0;i<datas.length;i++){
		colMArray[i+1] ={header:datas[i].header,width:datas[i].width,dataIndex:datas[i].dataIndex,hidden:datas[i].hidden,renderer:datas[i].fun,align:'center'};
		readArray[i]={name:datas[i].dataIndex};
	}
	var cm = new Ext.grid.ColumnModel(colMArray);
	result.cm = cm;
	var proxy = new Ext.data.HttpProxy({
		url : context_path+'/'+url
	});
	
	var reader =  new Ext.data.JsonReader({
		totalProperty:total,
		root :root
	},readArray);
	var store = new Ext.data.Store({
		proxy:proxy,
		pageSize: 10,
		autoLoad: true,
		reader:reader
	});
	result.store = store;
	return result;
}

/**
 * 导入数据
 * @param url
 */
function impData(temurl,url){
	var upwin = new Ext.Window({
		title : '导入数据',
		id:'upwin',
		closeAction : "destroy",
      	width:400,
      	height:160,
      	autoScroll:true,
      	modal:true,
      	buttonAlign:"center",
		items:[new Ext.form.FormPanel({
			height :115,
			id:'uploadform',
			fileUpload : true,
			layout: "column",
			items :[{
				xtype:'panel',
				columnWidth:1,
				border : false,
				layout : "form",
				labelAlign : 'center',
				labelWidth :80,
				items:[{
					xtype : "button",
					text:"数据模板",
					fieldLabel:'下载数据模板',
					readOnly:true,
					anchor : '80%',
					handler:function(){
						 window.open(context_path+'/'+temurl,"_blank");
					}
				}]
			},{
				xtype:'panel',
				columnWidth:1,
				border : false,
				layout : "form",
				labelAlign : 'center',
				items:[{
					xtype: "fileuploadfield", 
					buttonText: "浏览", 
					id:"file", 
					name:"file" ,
					fieldLabel:'上传数据模板',
					allowblank:false,
					anchor : '80%'
				}]
			}],
			buttonAlign : 'center',
			buttons : [{
				text : "上传",
				scope:this,
				handler:function(){
				var filename=Ext.getCmp('file').getValue();
				if(String(filename).lastIndexOf(".xls")==-1){
                  Ext.Msg.alert('提示','上传校验非法');
                  return;
				}
				Ext.getBody().mask("正在导入数据,请稍后......");
				Ext.getCmp('uploadform').getForm().submit({
					    clientValidation: true,
					    params:{'file':Ext.getCmp('file').getValue()},
					    url:url,
						success: function(form, action) {
							Ext.getBody().unmask();
							Ext.MessageBox.alert( "信息提示",action.result.msg);
						},
						failure: function(form, action) {
							Ext.getBody().unmask();
							Ext.MessageBox.alert( "保存异常", action.result.msg );
						}
				});	
				}},
				{
					text : "返回",
					scope:this,
					handler:function(){
                       Ext.getCmp('upwin').close();
                  }}
				]
			})]
		}
	);
	upwin.show();
}


/**
 * 删除数据
 * @param url  调用后台URL
 * @param id   删除依据字段
 * @param grid 数据
 * @param fun  回调
 */
function delRows(url,id,rows,fun){
	if(rows.length < 1){
		Ext.Msg.alert('信息提示','请选择一条记录！');
		return ;
	}
	var ids = "";
	for(var i=0;i<rows.length;i++ ){
		ids = ids+rows[i].data[id]+",";
	}
	ids = ids.substr(0,ids.length-1);
	Ext.Msg.confirm('信息','确认要删除么?',function(btn){
		if(btn =='yes'){
		 	Ext.Ajax.request({ 
				url:context_path+"/"+url,
				method:'post',
				params:{parms:ids},
				success:function(fp,o){
					var resultText =  Ext.decode(fp.responseText);
					if (resultText.success ==true) {
						Ext.Msg.alert('信息提示','删除成功');
						if(fun !=null){
							fun();
						}
					}
				}
		 	});
		}
	});
}

/**
 * 得到产权性质
 * @param typeId
 */
function getShareType(typeId){
	var sharingTypeEnumStr="";
	if(typeId =='0'){
		sharingTypeEnumStr='自建';
	}else if(typeId =='1'){
		sharingTypeEnumStr ='合建';
	}else if(typeId =='2'){
		sharingTypeEnumStr ='共建';
	}else if(typeId =='3'){
		sharingTypeEnumStr ='租用';
	}else if(typeId =='4'){
		sharingTypeEnumStr ='购买';
	}else if(typeId =='5'){
		sharingTypeEnumStr ='置换';
	}else if(typeId =='6'){
		sharingTypeEnumStr ='其他';
	}
	return sharingTypeEnumStr;
}

/**
 * 得到井型号
 * @param shapeId
 */
function getHoleShape(shapeId){
	var holeShape = "";
	if(shapeId == '0'){
		holeShape ='居前';
	}else if(shapeId =='1'){
		holeShape ='直通';
	}else if(shapeId =='2'){
		holeShape ='三通';
	}else if(shapeId =='3'){
		holeShape ='四通';
	}else if(shapeId =='4'){
		holeShape ='其他';
	}
	return holeShape;
}

/**
 * 产权单位
 * @param orgId
 */
function getShareOrg(orgId){
	var org = "";
	if(orgId =='0'){
		org = '中国移动';
	}else if(orgId =='1'){
		org = '中国联通';
	}else if(orgId =='2'){
		org = '中国电信';
	}else if(orgId =='3'){
		org = '中国铁通';
	}else if(orgId =='4'){
		org = '广电';
	}else if(orgId =='5'){
		org = '电力';
	}else if(orgId =='6'){
		org = '业主';
	}else if(orgId =='7'){
		org = '其他';
	}
	return org;
}


/**
 * 得到承载点
 * @param bearId
 */
function getBear(bearId){
	var bear = "";
	if(bearId =='0'){
		bear ='直通';
	}else if(bearId == '1'){
		bear ='分歧';
	}
	return bear;
}

/**
 * 得到杆类型
 * @param poleTypeId
 * @returns {String}
 */
function getPoleType(poleTypeId){
	var poleType = "";
	if(poleTypeId =='1'){
		poleType ='七米杆';
	}else if(poleTypeId =='2'){
		poleType ='八米杆';
	}else if(poleTypeId =='3'){
		poleType ='九米杆';
	}
	return poleType;
}

/**
 * 得到方位
 * @param positionId
 * @returns {String}
 */
function getPosition(positionId){
	var position ="";
	if(positionId =='1'){
		position ='东';
	}else if(positionId =='2'){
		position ='南';
	}else if(positionId =='3'){
		position ='西';
	}else if(positionId =='4'){
		position ='北';
	}
	return position;
}
