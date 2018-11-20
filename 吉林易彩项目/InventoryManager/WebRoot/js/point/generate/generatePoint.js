//point单元为最小单元。横竖区分为名称与按钮方向；且名称+按钮宽或高为50

Ext.onReady(function() {
	var eid= Ext.getDom("eid").value;
	Ext.Ajax.request({
		url : 'point!pointModel.action',
		params : {
			eid:eid
		},
		method : 'Post',
		callback : function(options, success, response) {
			if (success) {
				var json = Ext.util.JSON.decode(response.responseText);
				var p=[];
				for(var i=0;i<json.length;i++){
					alert(json[i].boxorder);
					var boxorder=json[i].boxorder.split(",");
					var inboxorder=json[i].inboxorder.split(",");
					var rownum=parseInt(json[i].rownum);
					var vernum=parseInt(json[i].vernum);
					var inrownum=parseInt(json[i].inrownum);
					var invernum=parseInt(json[i].invernum);
					var boxWH=parseInt(Ext.getDom("boxWH").value);
					var inboxWH=parseInt(Ext.getDom("inboxWH").value);
					var wordWH=parseInt(Ext.getDom("wordWH").value);
					var alltable={
						html:OutTop(rownum,vernum,inrownum,invernum,json[i].inpointtype,json[i].intype,json[i].type,boxWH,inboxWH,wordWH)+
						OutButween(boxorder,inboxorder,inrownum,invernum,json[i].inpointtype,json[i].intype,json[i].type,json[i].boxn,rownum,vernum,boxWH,inboxWH,wordWH)+
						OutBottom(rownum,json[i].type,boxWH)
					};
					p.push(alltable);
				}
				var panelContainer = {
						xtype : "container",
						layout : "table",
						autoEl : "div",
						autoHeight : true,
						autoWidth : true,
						x : "10%",
						y : "10%",
						layoutConfig : {
							columns : 1
						},
						hideBorders : true,
						items : p
					};
				Ext.MyViewport = Ext.extend(Ext.Viewport, {
					xtype : "viewport",
					layout : "border",
					initComponent : function() {
						this.items = [{
//							title : "告警信息",
//							xtype : "panel",
//							region : "south",
//							id : "southPanel",
//							autoScroll : true,
//							collapsible : true,
//							collapsed : true,
//							margins : "1 0 0 0",
//							split : true,
//							height : 200,
//						}, {
							layoutConfig : {
								align : "top",
								pack : "center"
							},
							region : "center",
							autoScroll : true,
							layout : "absolute",
							items : [panelContainer]
						}];
						Ext.MyViewport.superclass.initComponent.call(this);
					}
				});
				var port = new Ext.MyViewport();
				Ext.Ajax.request({
					url : 'point!seePoint.action',
					params : {
						eid:eid
					},
					method : 'Post',
					callback : function(options, success, response) {
						if (success) {
							var json = Ext.util.JSON.decode(response.responseText);
							var jsons=json[0].equts;
							for(var e=0;e<jsons.length;e++){
								if(jsons[e].pstat=='0'||jsons[e].pstat=='1'||jsons[e].pstat=='3'||jsons[e].pstat=='5'){
									var id=jsons[e].pid;
									var s=$("td#"+id+"");
									s.empty();
									var src="<input type='hidden' id='s"+id+"' value='"+jsons[e].pstat+"'/><img <img src='imgs/point/terminalPanel/grey.png' width='30' height='30' id='t"+id+"' onclick='showPointInfo("+id+")'/>";
									s.append(src);
								}else if(jsons[e].pstat=='2'){
									var id=jsons[e].pid;
									var s=$("td#"+id+"");
									s.empty();
									var src="<input type='hidden' id='s"+id+"' value='"+jsons[e].pstat+"'/><img <img src='imgs/point/terminalPanel/red.png' width='30' height='30' id='t"+id+"' onclick='showPointInfo("+id+")'/>";
									s.append(src);
								}else if(jsons[e].pstat=='4'){
									var id=jsons[e].pid;
									var s=$("td#"+id+"");
									s.empty();
									var src="<input type='hidden' id='s"+id+"' value='"+jsons[e].pstat+"'/><img <img src='imgs/point/terminalPanel/green.png' width='30' height='30' id='t"+id+"' onclick='showPointInfo("+id+")'/>";
									s.append(src);
								}
							}
						}
					}
				});
			}
		}
	});
});
function OutTop(rownum,vernum,inrownum,invernum,inpointtype,intype,type,boxWH,inboxWH,wordWH){//大框架头
	var rowtotal=rownum;
	var vertotal=2+vernum;
	var frame="";
	if(inpointtype=='0'){
		if(intype=='0'){
			if(type=='0'){
				frame="width='"+((inrownum*50+inboxWH*2+wordWH)*rownum+boxWH*2+wordWH)+"'";
			}else if(type=='1'){
				frame="width='"+((inrownum*50+inboxWH*2+wordWH)*rownum+boxWH*2)+"'";
			}
		}else if(intype=='1'){
			if(type=='0'){
				frame="width='"+((inrownum*50+inboxWH*2)*rownum+boxWH*2+wordWH)+"'";
			}else if(type=='1'){
				frame="width='"+((inrownum*50+inboxWH*2)*rownum+boxWH*2)+"'";
			}
		}
	}else if(inpointtype=='1'){
		if(intype=='0'){
			if(type=='0'){
				frame="height='"+((invernum*50+inboxWH*2)*vernum+boxWH*2)+"'";
			}else if(type=='1'){
				frame="height='"+((invernum*50+inboxWH*2)*vernum+boxWH*2+wordWH)+"'";
			}
		}else if(intype=='1'){
			if(type=='0'){
				frame="height='"+((invernum*50+inboxWH*2+wordWH)*vernum+boxWH*2)+"'";
			}else if(type=='1'){
				frame="height='"+((invernum*50+inboxWH*2+wordWH)*vernum+boxWH*2+wordWH)+"'";
			}
		}
	}
	if(type=='1'){
		vertotal+=1;
	}else if(type=='0'){
		rowtotal+=1;
	}
	var out_top="<table "+frame+" border='0' cellpadding='0' cellspacing='0'>"+
		  "<tr>"+
		  	"<td width='"+boxWH+"' rowspan='"+vertotal+"' bgcolor='#194A3D'></td>"+
		    "<td colspan='"+rowtotal+"' height='"+boxWH+"' bgcolor='#194A3D'></td>"+
		    "<td width='"+boxWH+"' rowspan='"+vertotal+"' bgcolor='#194A3D'></td>"+
		  "</tr>";
	return out_top;
}

function OutBottom(rownum,type,boxWH){//大框架尾
	var rowtotal=rownum;
	if(type=='0'){
		rowtotal+=1;
	}
	var out_bottom="<tr>"+
			"<td colspan='"+rowtotal+"' height='"+boxWH+"' bgcolor='#194A3D'></td>"+
			"</tr>"+
			"</table>";
	return out_bottom;
}
//（row td） （ver tr）
function OutButween(boxorder,inboxorder,inrownum,invernum,inpointtype,intype,type,boxn,rownum,vernum,boxWH,inboxWH,wordWH){
	var num=boxn.indexOf("-");
	var boxname=boxn.substring(num+1);
	var boxnum=boxn.substring(0,num);
	var boxtr="";
	var table=Inbox(boxorder,inboxorder,inrownum,invernum,inpointtype,intype,boxnum,inboxWH,wordWH);
	var tabtop="";
	if(type=='0'){
		tabtop=vermeter(vernum,boxname,1,wordWH,"");
	}
	for(var i=1;i<=vernum;i++){
		var boxtd="";
		for(var j=1;j<=rownum;j++){
			boxtd+="<td>"+table[(j-1)+(i-1)*rownum]+"</td>";
		}
		if(i==1){
		boxtr+="<tr>"+tabtop+boxtd+"</tr>";
		}else{
		boxtr+="<tr>"+boxtd+"</tr>";
		}
	}
	if(type=='1'){
		boxtr=rowmeter(rownum,boxname,1,wordWH,"")+boxtr;
	}
	
	return boxtr;
}

function Inbox(boxorder,inboxorder,inrownum,invernum,inpointtype,intype,boxnum,inboxWH,wordWH){//单个table
	var frame="";
	var tab="";
	var tables=[];
	var inrow=0;
	var inver=0;
	if(inpointtype=='0'){
		if(intype=='0'){
			frame="width='"+(inboxWH*2+wordWH+inrownum*50)+"'";
			inrow=3+inrownum*2;
			inver=2+invernum;
		}else if(intype=='1'){
			frame="width='"+(inboxWH*2+inrownum*50)+"'";
			inrow=2+inrownum*2;
			inver=3+invernum;
		}
	}else if(inpointtype=='1'){
		if(intype=='0'){
			frame="height='"+(inboxWH*2+invernum*50)+"'";
			inrow=3+inrownum;
			inver=2+invernum*2;
		}else if(intype=='1'){
			frame="height='"+(inboxWH*2+wordWH+invernum*50)+"'";
			inrow=2+inrownum;
			inver=3+invernum*2;
		}
	}
	for(var i=0;i<boxorder.length;i++){
	tab="<table "+frame+" border='0' cellpadding='0' cellspacing='0'>"+
		 "<tr>"+
	  	"<td width='"+inboxWH+"' rowspan='"+inver+"' bgcolor='#194A3D'></td>"+
	  	"<td colspan='"+inrow+"' height='"+inboxWH+"' bgcolor='#194A3D'></td>"+
	    "<td width='"+inboxWH+"' rowspan='"+inver+"' bgcolor='#194A3D'></td>"+
	    "</tr>";
	if(intype=='0'){
		tab=tab+point(inboxorder,inrownum,invernum,boxnum+boxorder[i],inpointtype,intype,boxorder[i],wordWH)+
		"<tr>"+
		"<td colspan='"+inrow+"' height='"+inboxWH+"' bgcolor='#194A3D'></td>"+
		"</tr>"+
		"</table>";
	}else if(intype=='1'){
		tab=tab+rowmeter(inrow-2,boxorder[i],0,wordWH,boxnum+boxorder[i])+point(inboxorder,inrownum,invernum,boxnum+boxorder[i],inpointtype,intype,"","")+
		"<tr>"+
		"<td colspan='"+inrow+"' height='"+inboxWH+"' bgcolor='#194A3D'></td>"+
		"</tr>"+
		"</table>";
	};
	tables.push(tab);
	};
	return tables;
};

function point(inboxorder,inrownum,invernum,thepoint,inpointtype,intype,inboxname,wordWH){//point选择
	if(inpointtype=='0'){
		return Inpointvertical(inboxorder,inrownum,invernum,thepoint,intype,inboxname,wordWH);
	}else if(inpointtype=='1'){
		return Inpointcross(inboxorder,inrownum,invernum,thepoint,intype,inboxname,wordWH);
	}
}

function Inpointvertical(inboxorder,inrownum,invernum,thepoint,intype,inboxname){//横point单元
	var points="";
	var point=[];
	var onetd="";
	if(intype=='0'){
		onetd=vermeter(invernum,inboxname,0,wordWH,thepoint);
	}
	for(var i=0;i<inboxorder.length;i++){
		var id=thepoint+inboxorder[i];
		var a="<td width='20' align='center' bgcolor='#F1F5FB'>"+inboxorder[i]+"</td>"+
            "<td width='30' id='"+id+"'><img src='imgs/point/terminalPanel/greyall.png' width='30' height='30' /></td>";
		point.push(a);
	}
	for(var j=1;j<=invernum;j++){
		var line="";
		for(var k=1;k<=inrownum;k++){
			line+=point[(k-1)+(j-1)*inrownum];//生成二维矩阵算法 x*y+（y-1）*x最大值
		}
		if(j==1){
		points+="<tr>"+onetd+line+"</tr>";
		}else{
		points+="<tr>"+line+"</tr>";
		}
	}
	return points;
};

function Inpointcross(inboxorder,inrownum,invernum,thepoint,intype,inboxname){//竖point单元
	var ver="";
	var onetd="";
	if(intype=='0'){
		onetd=vermeter(invernum*2,inboxname,0,wordWH,thepoint);
	}
	for(var i=1;i<=invernum;i++){
		var row1="";
		var row2="";
		for(var v=1;v<=inrownum;v++){
			row1+="<td height='20' align='center' bgcolor='#F1F5FB'>"+inboxorder[(v-1)+(i-1)*inrownum]+"</td>";
			row2+="<td height='30' id='"+thepoint+inboxorder[(v-1)+(i-1)*inrownum]+"'><img src='imgs/point/terminalPanel/greyall.png' width='30' height='30' /></td>";
		}
		if(i==1){
		ver+="<tr>"+onetd+row1+"</tr><tr>"+row2+"</tr>";
		}else{
		ver+="<tr>"+row1+"</tr><tr>"+row2+"</tr>";
		}
	}
	
	return ver;
};

function rowmeter(row,boxname,boxtype,wordWH,thepoint){//横排表头
	var pro1="";
	var pro2="";
	var id="";
	if(boxtype=='0'){
		pro1="bgcolor='#194A3D'";
		pro2="color='#FFFFFF'";
		id="id='"+thepoint+"'";
	}
	var general_meter="<tr>"+
            "<td "+id+" colspan='"+row+"' height='"+wordWH+"' "+pro1+" align='center'><font "+pro2+">"+boxname+"</font></td>"+
          "</tr>";
	
	return general_meter;
};

function vermeter(ver,boxname,boxtype,wordWH,thepoint){//竖排表头
	var pro1="";
	var pro2="";
	var boxname1="";
	var id="";
	if(boxtype=='0'){
		pro1="bgcolor='#194A3D'";
		pro2="color='#FFFFFF'";
		boxname1=boxname;
		id="id='"+thepoint+"'";
	}else if(boxtype=='1'){
		for(var i=0;i<boxname.length;i++){
			boxname1+=(boxname.substring(i,i+1)+"<br/>");
		}
	}
	var yy="<td "+id+" rowspan='"+ver+"' width='"+wordWH+"' "+pro1+" align='center'><font "+pro2+">"+boxname1+"</font></td>";
	return yy;
};

function ceshi(id){
	var s=$("td#"+id+"");
	s.empty();
	var str="<font color='#FFFF00' onclick='ceshi1()'>"+id.substring(2)+"！</font>";
	s.append(str);
}
function ceshi1(){
	alert("嘿！你的益达");
}

