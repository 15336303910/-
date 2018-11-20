package interfaces.irmsInterface.utils;

import java.io.ByteArrayInputStream;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultAttribute;
import org.dom4j.tree.DefaultElement;

import base.util.TextUtil;
import weblogic.utils.classfile.attribute_info;
import manage.equt.pojo.EqutInfoBean;
import manage.generator.pojo.GeneratorInfoBean;

public class StationUtil {

	/**
	 * 得到基础数据
	 * @param xml
	 * @return
	 */
	public static Map<String, String> getMapStr(String xml){
		Map<String, String> map  = new HashMap<String, String>();
		return map;
	}
	
	/**
	 * 得到机房列表
	 * @param str
	 * @return
	 */
	public static List<GeneratorInfoBean> getGenerator(String str){
		List<GeneratorInfoBean> list = new LinkedList<GeneratorInfoBean>();
		List<Map<String, String>> mapList = new LinkedList<Map<String,String>>();
		try{
			
			//解析文件过程
			SAXReader reader = new SAXReader();
			Document document = reader.read(new ByteArrayInputStream(str.getBytes("UTF-8")));
			Element root = document.getRootElement();
			Iterator it = root.elementIterator();
			while (it.hasNext()) {
				Element datasEle = (Element) it.next();
				Iterator datas = datasEle.elementIterator();
				while (datas.hasNext()) {
					Map<String, String> map = new HashMap<String, String>();
					Element objEle = (Element) datas.next();
					Iterator data = objEle.elementIterator();
					while(data.hasNext()){
						DefaultElement a = (DefaultElement) data.next();
						List<Attribute> attList = a.attributes();
						Attribute attKey = attList.get(0);
						Attribute attValue = attList.get(1);
						map.put(attKey.getValue(), attValue.getValue());
					}
					mapList.add(map);
				}
			}
			/**
			 * 遍历数据进行封装实体类
			 */
			for(Map<String, String> map : mapList){
				GeneratorInfoBean gen = new GeneratorInfoBean();
				gen.setGeneratorId(Integer.parseInt(map.get("int_id")));
				gen.setResNum(map.get("int_id"));
				gen.setGeneratorName(map.get("zh_label"));
				gen.setDeleteFlag("0");
				gen.setJflx(0);
				gen.setYwjb(0);
				gen.setSzlc("1");
				list.add(gen);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 解析得到机架的数据
	 * @param xml
	 * @return
	 */
	public static List<EqutInfoBean> getEqutInfo(String xml){
		List<EqutInfoBean> list = new LinkedList<EqutInfoBean>();
		List<Map<String, String>> mapList = new LinkedList<Map<String,String>>();
		try{
			//解析出相关数据
			SAXReader reader = new SAXReader();
			Document document = reader.read(new ByteArrayInputStream(xml.getBytes("UTF-8")));
			Element root = document.getRootElement();
			Iterator it = root.elementIterator();
			
			while (it.hasNext()) {
				Element racksEle = (Element) it.next();
				Iterator racksit = racksEle.elementIterator();
				while (racksit.hasNext()) {
					Element rackEle = (Element) racksit.next();
					Iterator rackit = rackEle.elementIterator();
					while(rackit.hasNext()){
						Element typeEle = (Element) rackit.next();
						if("attribute".equals(typeEle.getName())){
							Map<String, String> strMap = new LinkedHashMap<String, String>();
							List<Attribute> attList = typeEle.attributes();
							for(Attribute att : attList){
								strMap.put(att.getName(), att.getText());
							}
							mapList.add(strMap);
						}
					}
					
				}
			}
			//封装数据
			if(TextUtil.isNotNull(mapList)){
				for(Map<String, String> map : mapList){
					EqutInfoBean equt = new EqutInfoBean();
					equt.setId(Integer.parseInt(map.get("int_id")));
					equt.setEid("EQU_"+map.get("int_id"));
					equt.setEname(map.get("h_label"));
					equt.setEtype("1");
					equt.setEmodel("0");
					equt.setGid(map.get("equiproom_id"));
					equt.setPosX(StationUtil.getNonce());
					equt.setPosY(StationUtil.getNonce());
					equt.setMflag(1);
					equt.setHasallow(0);
					equt.setDel(0);
					equt.setJijialeixing(0);
					equt.setJijiahanghao(map.get("row_show"));
					equt.setJijialiehao(map.get("column_num"));
					list.add(equt);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	
	static String getNonce(){
		  double num = Math.random();
		  DecimalFormat  df=new DecimalFormat("#.####");
		  return df.format(num);
	  }
	
	
	public static void main(String[] args) {
		//解析机房数据
		/*String xml ="<resultxml success=\"true\" msg=\"true\">"
				+ " <datas total=\"2\">"
				+ " <data int_id=\"1028951898\" zh_label=\"长顺县9站一楼基站机房0#\">"
				+ "<attr key=\"int_id\" value=\"1028951898\"/>"
				+ "<attr key=\"zh_label\" value=\"长顺县9站一楼基站机房0#\"/>"
				+ "</data>"
				+ "<data int_id=\"1028951899\" zh_label=\"长顺县9站一楼基站机房1#\">"
				+ "<attr key=\"int_id\" value=\"1028951899\"/>"
				+ "<attr key=\"zh_label\" value=\"长顺县9站一楼基站机房1#\"/>"
				+ "</data>"
				+ "</datas>"
				+ "</resultxml>";
		StationUtil.getGenerator(xml);*/
		//解析机架数据
		String xml = "<resultxml success=\"true\" msg=\"true\">"
				+ "<rackpositionlist>"
				+ "<rackposition>"
				+ "<style width=\"\" height=\"\" x=\"\" y=\"\"/>"
				+ "<attribute city_id=\"854\" rack_num=\"A-01-01\" column_num=\"1\" int_id=\"1631226932\" equiproom_id_value=\"长顺县9站一楼基站机房0#\" h_label=\"长顺县9站一楼基站机房0#A01\" time_stamp=\"2016-06-20 17:49:53.0\" county_id=\"43\" col_show=\"1\" is_use=\"1\" line=\"A\" rack_type=\"设备机架\" row_show=\"A\" equiproom_id=\"1028951898\"/>"
				+ "</rackposition>"
				+ "<rackposition>"
				+ "<style width=\"\" height=\"\" x=\"\" y=\"\"/>"
				+ "<attribute city_id=\"854\" rack_num=\"A-01-02\" column_num=\"2\" int_id=\"1523813870\" equiproom_id_value=\"长顺县9站一楼基站机房0#\" zh_label=\"长顺县9站一楼基站机房0#A02\" time_stamp=\"2016-06-20 17:49:53.0\" county_id=\"43\" col_show=\"2\" is_use=\"0\" line=\"A\" rack_type=\"设备机架\" row_show=\"A\" equiproom_id=\"1028951898\"/>"
				+ "</rackposition>"
				+ "</rackpositionlist>"
				+ "</resultxml>"
				+ "";
		StationUtil.getEqutInfo(xml);
	}
}
