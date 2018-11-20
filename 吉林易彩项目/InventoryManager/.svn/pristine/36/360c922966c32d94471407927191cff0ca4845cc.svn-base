package base.util;

import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SqlCreator
{
  public static void main(String[] args)
  {
    String beanType = "resourcecheck";
    File root = new File("D:\\WorkSpace\\AssetManager\\src\\manage\\" + beanType + "\\pojo");
    String[] list = root.list();
    for (String name : list) {
      name = name.substring(0, name.indexOf("."));
      System.out.println(genCreateTableSql("manage." + beanType + ".pojo." + name));
      System.out.println(genSelectAllSql("manage." + beanType + ".pojo." + name));
      System.out.println(genUpdateTableSql("manage." + beanType + ".pojo." + name));
      System.out.println(genInsertSql("manage." + beanType + ".pojo." + name));
      name = name.substring(0, name.length() - 8);
      String constStr1 = "private final static String GET_" + name.toUpperCase() + " = \"" + beanType + ".get" + name + "\";";
      String constStr2 = "private final static String UPDATE_" + name.toUpperCase() + " = \"" + beanType + ".update" + name + "\";";
      String constStr3 = "private final static String INSERT_" + name.toUpperCase() + " = \"" + beanType + ".insert" + name + "\";";
      System.out.println(constStr1);
      System.out.println(constStr2);
      System.out.println(constStr3);
    }
  }

  public static String getBeanName(String bean) {
    try {
      Class clz = Class.forName(bean);
      String clzStr = clz.toString();

      String beanName = clzStr.substring(clzStr.lastIndexOf(".") + 1).toLowerCase();
      return beanName;
    } catch (ClassNotFoundException e) {
      e.printStackTrace(); }
    return "";
  }

  public static String getBeanNameNoLower(String bean)
  {
    try {
      Class clz = Class.forName(bean);
      String clzStr = clz.toString();

      String beanName = clzStr.substring(clzStr.lastIndexOf(".") + 1);
      return beanName;
    } catch (ClassNotFoundException e) {
      e.printStackTrace(); }
    return "";
  }

  public static List<String> getBeanPropertyList(String bean)
  {
    try {
      Class clz = Class.forName(bean);
      Field[] strs = clz.getDeclaredFields();
      List propertyList = new ArrayList();
      for (int i = 0; i < strs.length; ++i) {
        String protype = strs[i].getType().toString();
        propertyList.add(protype.substring(protype.lastIndexOf(".") + 1) + "`" + strs[i].getName());
      }
      return propertyList;
    } catch (ClassNotFoundException e) {
      e.printStackTrace(); }
    return null;
  }

  public static String getBeanFilesList(String bean)
  {
    try {
      Class clz = Class.forName(bean);
      Field[] strs = clz.getDeclaredFields();
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < strs.length; ++i) {
        String protype = strs[i].getType().toString();
        if ((!(strs[i].getName().equals("tableName"))) && (!(strs[i].getType().equals("List")))) {
          sb.append(strs[i].getName() + ",");
        }
      }
      sb.deleteCharAt(sb.toString().lastIndexOf(","));
      return sb.toString();
    } catch (ClassNotFoundException e) {
      e.printStackTrace(); }
    return null;
  }

  public static String genCreateTableSql(String bean)
  {
    List <String>beanPropertyList = getBeanPropertyList(bean);
    String beanBaseName = getBeanName(bean).substring(0, getBeanName(bean).length() - 8);
    StringBuffer sb = new StringBuffer("create table job_" + beanBaseName + "(\n");
    for (String string : beanPropertyList) {
      String[] propertys = string.split("`");
      if ((!(propertys[1].equals("tableName"))) && (!(propertys[1].equals("param"))) && (!(propertys[0].equals("List")))) {
        if (propertys[1].toLowerCase().equals(beanBaseName + "id")) {
          sb.append("   " + propertys[1] + " int primary key auto_increment,\n");
        }
        else if ((propertys[0].equals("int")) || (propertys[0].equals("Integer")))
          sb.append("   " + propertys[1] + " int default null comment '',\n");
        else if (propertys[0].equals("String"))
          sb.append("   " + propertys[1] + " varchar(255) default null comment '',\n");
        else if (propertys[0].equals("double"))
          sb.append("   " + propertys[1] + " varchar(50) default null comment '',\n");
        else if (propertys[0].equals("Date")) {
          sb.append("   " + propertys[1] + " datetime comment '',\n");
        }
      }
    }

    sb.append(")");
    sb.deleteCharAt(sb.lastIndexOf(","));
    return sb.toString();
  }

  public static String genUpdateTableSql(String bean)
  {
    List<String> beanPropertyList = getBeanPropertyList(bean);
    String beanBaseName = getBeanNameNoLower(bean).substring(0, getBeanName(bean).length() - 8);
    String idStr = "";
    String sql = "<update id=\"update" + beanBaseName + "\" parameterClass=\"" + bean + "\">\n";
    sql = sql + "\tUPDATE job_" + beanBaseName.toLowerCase() + " SET lastUpdateDate=now() \n";
    sql = sql + "\t<dynamic prepend=\" , \">\n";
    for (String string : beanPropertyList) {
      String[] propertys = string.split("`");
      if ((!(propertys[1].equals("tableName"))) && (!(propertys[1].equals("param"))) && (!(propertys[0].equals("List"))) && (!(propertys[1].equals("updateDate")))) {
        if (propertys[1].toLowerCase().equals(beanBaseName.toLowerCase() + "id")) {
          idStr = propertys[1];
        }
        else
          sql = sql + "\t\t<isNotNull prepend=\" , \" property=\"" + propertys[1] + "\"> " + propertys[1] + "=#" + propertys[1] + "# </isNotNull>" + "\n";
      }
    }
    sql = sql + "\t</dynamic>\n";
    sql = sql + "\tWHERE " + idStr + "=#" + idStr + "#\n";
    sql = sql + "</update>\n";
    return sql;
  }

  public static String genSelectAllSql(String bean)
  {
    List<String> beanPropertyList = getBeanPropertyList(bean);
    String beanBaseName = getBeanNameNoLower(bean).substring(0, getBeanName(bean).length() - 8);
    String sql = "<select id=\"get" + beanBaseName + "\" parameterClass=\"" + bean + "\" resultClass=\"" + bean + "\">\n";
    sql = sql + "\tSELECT * FROM job_" + beanBaseName.toLowerCase() + " WHERE deleteFlag='0'\n";
    sql = sql + "\t<dynamic prepend=\" AND \">\n";
    for (String string : beanPropertyList) {
      String[] propertys = string.split("`");
      if ((!(propertys[1].equals("tableName"))) && (!(propertys[1].equals("param"))) && (!(propertys[0].equals("List"))) && (!(propertys[1].equals("deleteFlag")))) {
        sql = sql + "\t\t<isNotEmpty prepend=\" AND \" property=\"" + propertys[1] + "\"> " + propertys[1] + "=#" + propertys[1] + "# </isNotEmpty>" + "\n";
      }
    }
    sql = sql + "\t</dynamic>\n";
    sql = sql + "</select>\n";
    return sql;
  }

  public static String genInsertSql(String bean)
  {
    List beanPropertyList = getBeanPropertyList(bean);
    String beanBaseName = getBeanNameNoLower(bean).substring(0, getBeanName(bean).length() - 8);
    String idStr = "";
    String sql = "<insert id=\"insert" + beanBaseName + "\" parameterClass=\"" + bean + "\">\n";
    sql = sql + "\tINSERT INTO job_" + beanBaseName.toLowerCase() + " ( \n";
    String nameSql = "";
    String valueSql = "";
    for (int i = 0; i < beanPropertyList.size(); ++i) {
      String string = (String)beanPropertyList.get(i);
      String[] propertys = string.split("`");
      if ((propertys[1].equals("tableName")) || 
        (propertys[1].equals("param")) || 
        (propertys[0].equals("List")) || 
        (propertys[1].equals("creationDate")) || 
        (propertys[1].equals("deleteFlag"))) continue;
      if (propertys[1].toLowerCase().equals(beanBaseName.toLowerCase() + "id")) {
        idStr = propertys[1];
      }
      else {
        nameSql = nameSql + " " + propertys[1] + ",";
        valueSql = valueSql + " #" + propertys[1] + "#" + ",";
        if (i == beanPropertyList.size() - 1) {
          nameSql = nameSql + " creationDate, deleteFlag";
          valueSql = valueSql + " now(), '0'";
        }
        if (i % 8 == 0) {
          nameSql = nameSql + "\n\t\t";
          valueSql = valueSql + "\n\t\t";
        }
      }
    }
    sql = sql + "\t\t" + nameSql + "\n";
    sql = sql + "\t) VALUES (\n";
    sql = sql + "\t\t" + valueSql + "\n";
    sql = sql + "\t)\n";
    sql = sql + "\t<selectKey resultClass=\"int\" keyProperty=\"" + idStr + "\">\n";
    sql = sql + "\t\tSELECT LAST_INSERT_ID() AS " + idStr + "\n";
    sql = sql + "\t</selectKey>\n";
    sql = sql + "</insert>\n";
    return sql;
  }
}
