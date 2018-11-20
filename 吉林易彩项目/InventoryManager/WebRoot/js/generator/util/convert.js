function deviceMaker(v){
	if(v=='0'){
		return ' ';
	}else if(v=='1'){
		return "IBM";
	}else if(v=='2'){
		return "JUNIPER";
	}else if(v=='3'){
		return "ORACLE(SUN)";
	}else if(v=='4'){
		return "阿朗";
	}else if(v=='5'){
		return "爱立信";
	}else if(v=='6'){
		return "上海贝尔";
	}else if(v=='7'){
		return "烽火";
	}else if(v=='8'){
		return "华为";
	}else if(v=='9'){
		return "惠普";
	}else if(v=='10'){
		return "诺西";
	}else if(v=='11'){
		return "思科";
	}else if(v=='12'){
		return "中兴";
	}else if(v=='13'){
		return "3COM";
	}else if(v=='14'){
		return "APC";
	}else if(v=='15'){
		return "DB2";
	}else if(v=='16'){
		return "DELL";
	}else if(v=='17'){
		return "D-LINK";
	}else if(v=='18'){
		return "EMC";
	}else if(v=='19'){
		return "GNB";
	}else if(v=='20'){
		return "Microsoft";
	}else if(v=='21'){
		return "NETAPP";
	}else if(v=='22'){
		return "Oracle";
	}else if(v=='23'){
		return "PCCW";
	}else if(v=='24'){
		return "Quantum(昆腾)";
	}else if(v=='25'){
		return "Symantec";
	}else if(v=='26'){
		return "TP-LINK";
	}else if(v=='27'){
		return "艾克赛";
	}else if(v=='28'){
		return "艾默生";
	}else if(v=='29'){
		return "东方国信";
	}else if(v=='30'){
		return "东软";
	}else if(v=='31'){
		return "华日";
	}else if(v=='32'){
		return "佳力图";
	}else if(v=='33'){
		return "力博特";
	}else if(v=='34'){
		return "联想";
	}else if(v=='35'){
		return "梅兰日兰";
	}else if(v=='36'){
		return "神州数码";
	}else if(v=='37'){
		return "双登";
	}else if(v=='38'){
		return "泰岳";
	}else if(v=='39'){
		return "拓维";
	}else if(v=='40'){
		return "西门子";
	}else if(v=='41'){
		return "亚联";
	}else if(v=='42'){
		return "Jeep";
	}else if(v=='43'){
		return "LG";
	}else if(v=='44'){
		return "TCL";
	}else if(v=='45'){
		return "爱普生";
	}else if(v=='46'){
		return "奥林巴斯";
	}else if(v=='47'){
		return "宝马";
	}else if(v=='48'){
		return "奔驰";
	}else if(v=='49'){
		return "本田";
	}else if(v=='50'){
		return "标志";
	}else if(v=='51'){
		return "博世";
	}else if(v=='52'){
		return "大众";
	}else if(v=='53'){
		return "东南";
	}else if(v=='54'){
		return "东芝";
	}else if(v=='55'){
		return "丰田";
	}else if(v=='56'){
		return "福特";
	}else if(v=='57'){
		return "海尔";
	}else if(v=='58'){
		return "海信";
	}else if(v=='59'){
		return "佳能";
	}else if(v=='60'){
		return "金龙";
	}else if(v=='61'){
		return "康佳";
	}else if(v=='62'){
		return "理光";
	}else if(v=='63'){
		return "铃木";
	}else if(v=='64'){
		return "明基";
	}else if(v=='65'){
		return "尼康";
	}else if(v=='66'){
		return "日产";
	}else if(v=='67'){
		return "日立";
	}else if(v=='68'){
		return "三星";
	}else if(v=='69'){
		return "索尼";
	}else if(v=='70'){
		return "通用";
	}else if(v=='71'){
		return "同方";
	}else if(v=='72'){
		return "沃尔沃";
	}else if(v=='73'){
		return "五菱";
	}else if(v=='74'){
		return "夏普";
	}else if(v=='75'){
		return "现代";
	}else if(v=='76'){
		return "长安";
	}else if(v=='77'){
		return "长城";
	}else if(v=='78'){
		return "长虹";
	}else if(v=='79'){
		return "紫光";
	}else if(v=='80'){
		return "小生产厂商";
	}
}

function YorN(v){
	if(v=='0'){
		return ' ';
	}else if(v=='1'){
		return "是";
	}else if(v=='2'){
		return "否";
	}
}

function stationRange(v){
	if(v=='0'){
		return ' ';
	}else if(v=='1'){
		return "城市";
	}else if(v=='2'){
		return "乡村";
	}
}

function stationLevel(v){
	if(v=='0'){
		return ' ';
	}else if(v=='1'){
		return "A";
	}else if(v=='2'){
		return "B";
	}else if(v=='3'){
		return "C";
	}
}

function stationNetWorkType(v){
	if(v=='0'){
		return ' ';
	}else if(v=='1'){
		return "GSM";
	}else if(v=='2'){
		return "WCDMA";
	}else if(v=='3'){
		return "GSM和WCDMA共用";
	}
}

function stationType(v){
	if(v=='0'){
		return ' ';
	}else if(v=='1'){
		return "普通局站";
	}else if(v=='2'){
		return "基站站点";
	}else if(v=='3'){
		return "多机房ITMC";
	}else if(v=='4'){
		return "边境站";
	}
}

function maintenanceMode(v){
	if(v=='0'){
		return ' ';
	}else if(v=='1'){
		return "自维";
	}else if(v=='2'){
		return "代维";
	}
}

function maintenanceType(v){
	if(v=='0'){
		return ' ';
	}else if(v=='1'){
		return "硬件维修";
	}else if(v=='2'){
		return "紧急备件支持";
	}else if(v=='3'){
		return "软件补丁版本支持";
	}else if(v=='4'){
		return "技术支持服务（电话咨询、电话支持、远程支持）";
	}else if(v=='5'){
		return "无";
	}
}	


function lifecycle(v){
	if(v=='0'){
		return ' ';
	}else if(v=='1'){
		return "设计状态";
	}else if(v=='2'){
		return "工程建设期（入网带业务前）";
	}else if(v=='3'){
		return "工程可用期（已入网带业务）";
	}else if(v=='4'){
		return "工程初验后试运行";
	}else if(v=='5'){
		return "工程终验后在网";
	}else if(v=='6'){
		return "退网状态";
	}
}

function maintainState(v){
	if(v=='0'){
		return ' ';
	}else if(v=='1'){
		return "正常";
	}else if(v=='2'){
		return "故障";
	}else if(v=='3'){
		return "维护";
	}
}

function buildingType(v){
	if(v=='0'){
		return ' ';
	}else if(v=='1'){
		return "砖混";
	}else if(v=='2'){
		return "彩钢板";
	}else if(v=='3'){
		return "其他";
	}
}

function haveORno(v){
	if(v=='0'){
		return ' ';
	}else if(v=='1'){
		return "有";
	}else if(v=='2'){
		return "无";
	}
}

function singleLayerChamfer(v){
	if(v=='0'){
		return ' ';
	}else if(v=='1'){
		return "单层走线";
	}else if(v=='2'){
		return "双层走线";
	}else if(v=='3'){
		return "多层走线";
	}
}

function takeUpMode(v){
	if(v=='0'){
		return " ";
	}else if(v=='1'){
		return "上走线";
	}else if(v=='2'){
		return "下走线";
	}else if(v=='3'){
		return "混合走线";
	}
}

function overseasPopBusinessType(v){
	if(v=='0'){
		return " ";
	}else if(v=='1'){
		return "海事卫星电话";
	}else if(v=='2'){
		return "大洋通";
	}else if(v=="3"){
		return "国际基础设施";
	}else if(v=="4"){
		return "国际运营商设备托管";
	}else if(v=="5"){
		return "卫星电路";
	}
}

function subclass(v){
	if(v=='0'){
		return " ";
	}else if(v=='1'){
		return "长途传输机房";
	}else if(v=='2'){
		return "长途交换机房";
	}else if(v=="3"){
		return "长途IP机房";
	}else if(v=="4"){
		return "长途数据机房";
	}else if(v=="5"){
		return "长途专业综合机房";
	}else if(v=="6"){
		return "移动核心机房";
	}else if(v=="7"){
		return "本地核心交换机房";
	}else if(v=="8"){
		return "本地核心数据机房";
	}else if(v=="9"){
		return "本地核心专业综合机房";
	}else if(v=="10"){
		return "城域IP网核心机房";
	}else if(v=="11"){
		return "电力室";
	}else if(v=="12"){
		return "高级、低级信令转接点机房";
	}else if(v=="13"){
		return "IDC机房";
	}else if(v=="14"){
		return "本地城域网传输核心、汇聚、边缘层机房";
	}else if(v=="15"){
		return "本地城域网IP网汇聚层机房";
	}else if(v=="16"){
		return "本地交换端局机房";
	}else if(v=="17"){
		return "本地数据端局机房";
	}else if(v=="18"){
		return "本地专业综合机房";
	}else if(v=="19"){
		return "中继机房";
	}else if(v=="20"){
		return "移动基站机房";
	}else if(v=="21"){
		return "小灵通基站机房";
	}else if(v=="22"){
		return "交换模块局机房";
	}else if(v=="23"){
		return "PSTN接入网机房";
	}else if(v=="24"){
		return "综合接入网机房";
	}else if(v=="25"){
		return "其他接入网机房";
	}else if(v=="26"){
		return "用户机房";
	}else if(v=="27"){
		return "海外POP点";
	}else if(v=="28"){
		return "卫星站";
	}else if(v=="29"){
		return "国际传输维护中心";
	}else if(v=="30"){
		return "国际交换维护中心";
	}
}

function broadHeading(v){
	if(v=='0'){
		return " ";
	}else if(v=='1'){
		return "A类";
	}else if(v=='2'){
		return "B类";
	}else if(v=='3'){
		return "C类";
	}
}

function affiliation(v){
	if(v=='0'){
		return " ";
	}else if(v=='1'){
		return "自建";
	}else if(v=='2'){
		return "合作";
	}else if(v=='3'){
		return "客户";
	}else if(v=='4'){
		return "租用";
	}
}

function time(v){
	v=v.replace("T"," ");
	return v;
}