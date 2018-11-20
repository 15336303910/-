/**
 * 查看详情
 * @param store
 */
function detailBuried(store,obj){
	var buriedName =itemText(.5,80,'buriedName','直埋名称',obj.buriedName,null,'u');
	var buriedArea =itemText(.5,80,'buriedArea','维护区域',obj.buriedArea,null,'u');
	var buriedModelStr =itemText(.5,80,'buriedModelStr','维护方式',obj.buriedModelStr,null,'u');
	var buriedDept =itemText(.5,80,'buriedDept','维护单位',obj.buriedDept,null,'u');
	var buriedLength =itemText(.5,80,'buriedLength','直埋长度',obj.buriedLength,null,'u');
	var dataQualitier =itemText(.5,80,'dataQualitier','质量责任人',obj.dataQualitier,null,'u');
	var maintainer =itemText(.5,80,'maintainer','一线维护人',obj.maintainer,null,'u');
		//工单基本信息
		var detailPanel = new Ext.form.FormPanel({
		    layout : "column",
		    id:'dealPanel',
			border : false,
			width:'100%',
			autoHeight:true,
			frame:true,
			fileUpload : true,
			items:[
			buriedName,buriedArea,
			buriedModelStr,buriedDept,
			buriedLength,dataQualitier,
			maintainer
			]
		});
		var detailWin = new Ext.Window({
			title:'查看详情',
			closeAction : "destroy",
	      	width:600,
	      	height:250,
	      	autoScroll:true,
	      	modal:true,
	      	buttonAlign:"center",
	      	items:[detailPanel],
	      	buttons:[{
	      		text:"关闭",
	      		handler:function(){
	      			detailWin.destroy();
	      		}
	      	}]
		});
		detailWin.show();
	}