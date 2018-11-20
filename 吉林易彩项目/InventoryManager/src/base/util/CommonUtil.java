package base.util;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import manage.document.pojo.DocPointInfoBean;
import manage.equt.pojo.EqutInfoBean;
import manage.main.pojo.MenuInfoBean;
import manage.pipe.pojo.TubeInfoBean;
import manage.point.pojo.PointInfoBean;

import org.dom4j.Element;
import org.omg.CORBA.portable.ApplicationException;

public class CommonUtil
{
  public static final int DATE_OLD_FORMAT = 1;
  public static final int DATE_NEW_FORMAT = 0;

  public static String setNullForString(String str)
  {
    if ((str == null) || (str.equals(""))) {
      return null;
    }
    return str;
  }

  public static Date getTimeBucket(Integer time, Date btime)
  {
    Calendar c = Calendar.getInstance();
    if (time.intValue() == 1) {
      btime = null;
    }
    if (time.intValue() == 2) {
      int weekday = c.get(7) - 1;
      c.add(5, -weekday);
      btime = c.getTime();
    }
    if (time.intValue() == 3) {
      c.set(5, 1);
      btime = c.getTime();
    }
    if (time.intValue() == 4) {
      c.set(5, 1);
      int month = 2 + 1;
      if ((month >= 1) && (month <= 3)) {
        c.set(2, 0);
      }
      if ((month >= 4) && (month <= 6)) {
        c.set(2, 3);
      }
      if ((month >= 7) && (month <= 9)) {
        c.set(2, 6);
      }
      if ((month >= 10) && (month <= 12)) {
        c.set(2, 9);
      }
      btime = c.getTime();
    }
    if (time.intValue() == 5) {
      c.set(1, 0, 1);
      btime = c.getTime();
    }
    return btime;
  }

  public static String randomString(int length)
  {
    String rand = "";
    String chars = "abcdefghijklmnopqrstuvwxyz0123456789";
    for (int i = 0; i < length; ++i) {
      rand = rand + chars.charAt((int)(Math.random() * 36.0D));
    }
    return rand;
  }

  public static String getUuid()
  {
    SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    return sdFormat.format(new Date());
  }

  public static Date getTime(String UUID)
  {
    SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    try {
      return sdFormat.parse(UUID);
    } catch (ParseException e) {
      e.printStackTrace(); }
    return new Date();
  }

  public static void copyValueToBean(Object obj, Object toObj)
    throws ApplicationException
  {
    if ((obj == null) || (toObj == null))
      return;
    Class classz = obj.getClass();
    Class classTo = toObj.getClass();

    Field[] fields = classz.getDeclaredFields();
    for (Field field : fields)
      try
      {
        String name = field.getName();
        Type type = field.getGenericType();

        Field[] fieldsTo = classTo.getDeclaredFields();
        for (Field fieldTo : fieldsTo) {
          String nameTo = fieldTo.getName();
          Type typeTo = fieldTo.getGenericType();

          if ((!(name.equals(nameTo))) || (!(type.toString().equals(typeTo.toString())))) {
            continue;
          }
          Method m = classz.getMethod("get" + change(name), new Class[0]);
          Object value = m.invoke(obj, new Object[0]);
          Method mtd;
          if (type.toString().equals("class java.util.Date"))
          {
            mtd = classTo.getMethod("set" + change(nameTo), new Class[] { Date.class });
            mtd.invoke(obj, new Object[] { value });
          } else if (type.toString().equals("class java.lang.Integer"))
          {
            mtd = classTo.getMethod("set" + change(nameTo), new Class[] { Integer.class });
            mtd.invoke(obj, new Object[] { value });
          } else {
            mtd = classTo.getMethod("set" + change(nameTo), new Class[] { String.class });
            mtd.invoke(obj, new Object[] { value });
          }

        }

      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
  }

  public static String getAttributesToJSON(Object o, List<String> attributeStrList)
  {
    String result = "";
    if ((o != null) && 
      (attributeStrList != null) && 
      (attributeStrList.size() != 0)) {
      Class classz = o.getClass();

      Field[] fields = classz.getDeclaredFields();
      for (Field field : fields) {
        String name = field.getName();
        Type type = field.getGenericType();
        if (!(attributeStrList.contains(name)))
          continue;
        try
        {
          Method m;
          Object value;
          if (type.toString().equals("boolean")) {
            m = classz.getMethod("is" + change(name), new Class[0]);
            value = m.invoke(o, new Object[0]);
          } else {
            m = classz.getMethod("get" + change(name), new Class[0]);
            value = m.invoke(o, new Object[0]);
          }
          Gson g = new Gson();
          String json = g.toJson(value);
          result = result + name + "=" + json + "\n";
        } catch (Exception e) {
          e.printStackTrace();
        }
      }

      if (attributeStrList.contains("jsonResponse")) {
        try {
          Method m = classz.getSuperclass().getMethod("getJsonResponse", new Class[0]);
          Object value = m.invoke(o, new Object[0]);
          if (value != null) {
            Gson g = new Gson();
            String json = g.toJson(value);
            result = result + "jsonResponse=" + json + "\n";
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    return result;
  }

  public static Object compareValue(Object rawObj, Object newObj) throws ApplicationException
  {
    if ((rawObj != null) && (newObj != null))
    {
      Class classz = rawObj.getClass();
      Class classTo = newObj.getClass();

      if (classz.equals(classTo))
      {
        Field[] fields = classz.getDeclaredFields();
        Field[] arrayOfField1;
        int j = (arrayOfField1 = fields).length; int i = 0; 
        while (true) { Field field = arrayOfField1[i];
          try {
            String name = field.getName();
            Type type = field.getGenericType();
            if (type.toString().equals("class java.lang.String")) {
              Method m1 = classz.getMethod("get" + change(name), new Class[0]);
              Object valueOld = m1.invoke(rawObj, new Object[0]);

              Method m2 = classTo.getMethod("get" + change(name), new Class[0]);
              Object valueNew = m2.invoke(newObj, new Object[0]);
              Method set;
              String value;
              if (valueOld == null) {
                if (valueNew != null) {
                  set = classTo.getMethod("set" + change(name), new Class[] { String.class });
                  value = "||" + valueNew;
                  set.invoke(newObj, new Object[] { value });
                }
              }
              else if (!(valueOld.equals(valueNew))) {
                set = classTo.getMethod("set" + change(name), new Class[] { String.class });
                value = "||" + valueNew;
                set.invoke(newObj, new Object[] { value });
              }
            }
          }
          catch (Exception e) {
            e.printStackTrace();
          }
          ++i; if (i >= j)
          {
            label342: return newObj; } }
      }
    }
    return null;
  }

  public static void nullConverNullString(Object obj) throws ApplicationException
  {
    if (obj == null)
      return;
    Class classz = obj.getClass();

    Field[] fields = classz.getDeclaredFields();

    for (Field field : fields)
      try {
        Type t = field.getGenericType();
        if (t.toString().equals("class java.lang.String")) {
          Method m = classz.getMethod("get" + change(field.getName()), new Class[0]);
          Object name = m.invoke(obj, new Object[0]);
          Method mtd;
          if ((name == null) || (name.equals("null"))) {
            mtd = classz.getMethod("set" + change(field.getName()), new Class[] { String.class });
            mtd.invoke(obj, new Object[] { "" });
          } else if (t.toString().equals("class java.lang.String")) {
            mtd = classz.getMethod("set" + change(field.getName()), new Class[] { String.class });
            name = name.toString().replace(" ", "");
            mtd.invoke(obj, new Object[] { name });
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
  }

  public static String getTimeDiffString(Date begin, Date end)
  {
    String timeDiff = "";
    long between = (end.getTime() - begin.getTime()) / 1000L;
    long day = between / 86400L;
    long hour = between % 86400L / 3600L;
    long minute = between % 3600L / 60L;
    long second = between % 60L;
    if (day > 0L) {
      timeDiff = timeDiff + day + "天";
    }
    if (hour > 0L) {
      timeDiff = timeDiff + hour + "小时";
    }
    if (minute > 0L) {
      timeDiff = timeDiff + minute + "分";
    }
    if (second > 0L) {
      timeDiff = timeDiff + second + "秒";
    }
    return timeDiff;
  }

  public static boolean hasAreano(List<String> alist, String code)
  {
    boolean noHas = true;
    if ((alist == null) || (alist.isEmpty())) {
      return true;
    }
    for (String s : alist) {
      if (s.equals(code)) {
        noHas = false;
        break;
      }
    }
    return noHas;
  }

  public static List<String> getFatherAreanoList(List<String> fatherAreano, String areano)
  {
    if (areano.length() > 4) {
      areano = areano.substring(0, areano.length() - 2);
      fatherAreano.add(areano);
      getFatherAreanoList(fatherAreano, areano);
    } else if (areano.length() == 4) {
      fatherAreano.add(areano.substring(0, areano.length() - 3));
      return fatherAreano;
    }
    fatherAreano.add(areano);
    return fatherAreano;
  }

  public static void xmlToBean(Element workInfoContentElement, Object obj) throws ApplicationException
  {
    if ((obj == null) || (workInfoContentElement == null))
      return;
    Class classz = obj.getClass();

    Field[] fields = classz.getDeclaredFields();

    label466: for (Field field : fields)
      try {
        Type t = field.getGenericType();
        if (t.toString().equals("class java.util.Date"))
          if (workInfoContentElement.elementTextTrim(field.getName().toUpperCase()) != null) {
            String strDate = workInfoContentElement.elementTextTrim(field.getName().toUpperCase());
            String regx1 = "\\d{8}T\\d{6}";
            String regx2 = "20\\d{2}-\\d{2}-\\d{2}\\s{1}\\d{2}:{1}\\d{2}:{1}\\d{2}";

            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            if (strDate.matches(regx1))
              date = sdf1.parse(strDate);
            else if (strDate.matches(regx2)) {
              date = sdf2.parse(strDate);
            }

            Method mtd = classz.getMethod("set" + change(field.getName()), new Class[] { Date.class });
            mtd.invoke(obj, new Object[] { date });
          }
        String workInfoConten;
        Method mtd;
        if (t.toString().equals("class java.lang.Integer"))
          if (workInfoContentElement.elementTextTrim(field.getName().toUpperCase()) != null) {
            workInfoConten = workInfoContentElement.elementTextTrim(field.getName().toUpperCase());
            mtd = classz.getMethod("set" + change(field.getName()), new Class[] { Integer.class });
            if (workInfoConten.equals("")) {
              workInfoConten = "0";
            }
            mtd.invoke(obj, new Object[] { Integer.valueOf(Integer.parseInt(workInfoConten)) });
          }
        if ((t.toString().equals("class java.lang.String")) && 
          (workInfoContentElement.elementTextTrim(field.getName().toUpperCase()) != null)) {
          workInfoConten = workInfoContentElement.elementTextTrim(field.getName().toUpperCase());
          mtd = classz.getMethod("set" + change(field.getName()), new Class[] { String.class });
          mtd.invoke(obj, new Object[] { workInfoConten });
        }
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
  }

  public static String createNote(Object obj)
    throws ApplicationException
  {
    return createNote(obj, 0);
  }

  public static String createNote(Object obj, int dateFormat)
    throws ApplicationException
  {
    DateFormat df = null;
    if (dateFormat == 0)
      df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    else if (dateFormat == 1) {
      df = new SimpleDateFormat("yyyyMMdd'T'hhmmss");
    }
    StringBuffer strResult = new StringBuffer("");
    if (obj != null) {
      Class classz = obj.getClass();

      Field[] fields = classz.getDeclaredFields();

      for (Field field : fields) {
        try {
          Type t = field.getGenericType();
          Method m = classz.getMethod("get" + change(field.getName()), new Class[0]);
          Object value = m.invoke(obj, new Object[0]);
          if (value != null)
          {
            if (t.toString().equals("class java.util.Date")) {
              strResult.append("<");
              strResult.append(field.getName().toUpperCase());
              strResult.append(">");
              strResult.append(df.format(value));
              strResult.append("</");
              strResult.append(field.getName().toUpperCase());
              strResult.append(">");
            } else if (t.toString().equals("class java.lang.Integer")) {
              strResult.append("<");
              strResult.append(field.getName().toUpperCase());
              strResult.append(">");
              strResult.append(value);
              strResult.append("</");
              strResult.append(field.getName().toUpperCase());
              strResult.append(">");
            } else if ((t.toString().equals("class java.lang.String")) && 
              (!(value.equals("")))) {
              strResult.append("<");
              strResult.append(field.getName().toUpperCase());
              strResult.append(">");
              strResult.append(value);
              strResult.append("</");
              strResult.append(field.getName().toUpperCase());
              strResult.append(">");
            }
          }
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
    }
    return strResult.toString();
  }

  public static List<PointInfoBean> sort(List<PointInfoBean> list)
    throws Exception
  {
    List list1 = new ArrayList();
    List list2 = new ArrayList();
    String upeid = "";
    String uppid = "";
    String downeid = "";
    String downpid = "";
    String ptype = "";
    boolean reset = true;
    int count = 0;
    int i;

    List unOrderList = new ArrayList();
    for (PointInfoBean e : list) {
      if (!(list1.contains(e))) {
        unOrderList.add(e);
      }
    }
    if ((unOrderList.size() != 0) && (unOrderList.size() != list.size())) {
      unOrderList = sort(unOrderList);
    }
    list1.addAll(unOrderList);

    if (list1.size() != list.size())
    {
      return null;
    }
    return list1;
  }

  public static String getIpAddr(HttpServletRequest request)
  {
    String ip = request.getHeader("x-forwarded-for");
    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
      ip = request.getRemoteAddr();
    }
    return ip;
  }

  public static String change(String src)
  {
    if (src != null) {
      StringBuffer sb = new StringBuffer(src);
      sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
      return sb.toString();
    }
    return null;
  }

  public static String getMflagst(Integer mflag)
  {
    String s = "未知";
    if (mflag != null) {
      if (mflag.intValue() == 1)
        s = "已同步";
      else if (mflag.intValue() == 2) {
        s = "未同步";
      }
    }
    else {
      s = "未知";
    }

    return s;
  }

  public static String getPstatInt(String pstat) {
    String str = "";
    pstat = pstat.trim();
    if ("空闲".equals(pstat))
      str = "1";
    else if ("故障".equals(pstat))
      str = "2";
    else if ("可用".equals(pstat))
      str = "3";
    else if ("占用".equals(pstat))
      str = "4";
    else if ("待核查".equals(pstat)) {
      str = "5";
    }
    return str;
  }

  public static String getDirectionInt(String direction) {
    String str = "";
    direction = direction.trim();
    if ("向局端".equals(direction))
      str = "1";
    else if ("向用户端".equals(direction)) {
      str = "2";
    }
    return str;
  }

  public static String getPtypeInt(String ptype) {
    ptype = ptype.trim();
    String str = "";
    if ("普通端子".equals(ptype))
      str = "1";
    else if ("分光器端子".equals(ptype))
      str = "2";
    else if ("λ端子".equals(ptype))
      str = "3";
    else if ("ONU上连端子".equals(ptype))
      str = "4";
    else if ("ONU网口".equals(ptype)) {
      str = "5";
    }
    return str;
  }

  public static String getEststInt(String estat) {
    estat = estat.trim();
    String str = "";
    if ("空闲".equals(estat))
      str = "1";
    else if ("故障".equals(estat))
      str = "2";
    else if ("可用".equals(estat)) {
      str = "3";
    }
    return str;
  }

  public static String getEtypeInt(String etype) {
    etype = etype.trim();
    String str = "";
    if (("光配线架".equals(etype)) || ("光配线架（ODF）".equals(etype)) || ("光配线架(ODF)".equals(etype)))
      str = "1";
    else if (("综合性光配线架（MODF）".equals(etype)) || ("综合性光配线架".equals(etype)) || ("综合性光配线架(MODF)".equals(etype)))
      str = "2";
    else if ("光交接箱".equals(etype))
      str = "3";
    else if (("光分路器".equals(etype)) || ("光分路器(分光器)".equals(etype)) || ("光分路器（分光器）".equals(etype)))
      str = "4";
    else if ("光分纤盒".equals(etype))
      str = "5";
    else if (("光缆接头盒".equals(etype)) || ("光缆接头盒（终端盒）".equals(etype)) || ("光缆接头盒(终端盒)".equals(etype)))
      str = "6";
    else if (("ONU".equals(etype)) || ("onu".equals(etype)))
      str = "7";
    else if ("未知设备".equals(etype)) {
      str = "9";
    }

    return str;
  }

  public static String getRlevelStr(String level) {
    String stateStr = "未知";
    if ((level == null) || (level.equals("")))
      stateStr = "";
    else if (level.equals("1"))
      stateStr = "一级干线";
    else if (level.equals("2"))
      stateStr = "二级干线";
    else if (level.equals("3"))
      stateStr = "本地中继";
    else if (level.equals("4"))
      stateStr = "本地核心";
    else if (level.equals("5"))
      stateStr = "本地汇聚";
    else if (level.equals("6")) {
      stateStr = "本地接入";
    }
    return stateStr; }

  public static String getRlevelInt(String level) {
    String stateStr = "";
    if (level.equals("一级干线"))
      stateStr = "1";
    else if (level.equals("二级干线"))
      stateStr = "2";
    else if (level.equals("本地中继"))
      stateStr = "3";
    else if (level.equals("本地核心"))
      stateStr = "4";
    else if (level.equals("本地汇聚"))
      stateStr = "5";
    else if (level.equals("本地接入")) {
      stateStr = "6";
    }
    return stateStr;
  }

  public static String getRstateStr(Integer state) {
    String stateStr = "未知";
    if (state == null)
      stateStr = "";
    else if (state.intValue() == 1)
      stateStr = "割接封锁";
    else if (state.intValue() == 2)
      stateStr = "正常";
    else if (state.intValue() == 3) {
      stateStr = "不可用";
    }
    return stateStr; }

  public static int getRstateInt(String state) {
    int stateStr = 0;
    if ("割接封锁".equals(state))
      stateStr = 1;
    else if ("正常".equals(state))
      stateStr = 2;
    else if ("不可用".equals(state)) {
      stateStr = 3;
    }
    return stateStr;
  }

  public static String getCstateStr(Integer state) {
    String stateStr = "";
    if (state == null)
      stateStr = "";
    else if (state.intValue() == 1)
      stateStr = "割接封锁";
    else if (state.intValue() == 2)
      stateStr = "正常";
    else if (state.intValue() == 3) {
      stateStr = "不可用";
    }
    return stateStr;
  }

  public static int getCstateInt(String state) {
    int stateStr = 0;
    if ("割接封锁".equals(state))
      stateStr = 1;
    else if ("正常".equals(state))
      stateStr = 2;
    else if ("不可用".equals(state)) {
      stateStr = 3;
    }
    return stateStr;
  }

  public static String getSharetypeStr(Integer sharetype) {
    String sharetypeStr = "";
    if (sharetype == null)
      sharetypeStr = "";
    else if (sharetype.intValue() == 1)
      sharetypeStr = "共建";
    else if (sharetype.intValue() == 2)
      sharetypeStr = "共享(我方共享他方)";
    else if (sharetype.intValue() == 3)
      sharetypeStr = "共享(他方共享我方)";
    else if (sharetype.intValue() == 4)
      sharetypeStr = "自建自用";
    else if (sharetype.intValue() == 5) {
      sharetypeStr = "自建预留";
    }
    return sharetypeStr; }

  public static int getSharetypeInt(String sharetype) {
    int sharetypeInt = 0;
    if ("共建".equals(sharetype))
      sharetypeInt = 1;
    else if ("共享(我方共享他方)".equals(sharetype))
      sharetypeInt = 2;
    else if ("共享(他方共享我方)".equals(sharetype))
      sharetypeInt = 3;
    else if ("自建自用".equals(sharetype))
      sharetypeInt = 4;
    else if ("自建预留".equals(sharetype)) {
      sharetypeInt = 5;
    }

    return sharetypeInt;
  }

  public static EqutInfoBean setEqutInfoToChinese(EqutInfoBean teBean) {
    teBean.setEtype(getEqutType(teBean.getEtype()));
    teBean.setEstat(getEqutState(teBean.getEstat()));
    return teBean;
  }

  public static String getEqutType(String typeStr)
  {
    String renString = "";

    if (typeStr.equals("1"))
    {
      renString = "光配线架(ODF)";
    }
    else if (typeStr.equals("2"))
    {
      renString = "综合性光配线架(MODF)";
    }
    else if (typeStr.equals("3"))
    {
      renString = "光交接箱";
    }
    else if (typeStr.equals("4"))
    {
      renString = "光分路器(分光器)";
    }
    else if (typeStr.equals("5"))
    {
      renString = "光分纤盒";
    }
    else if (typeStr.equals("6"))
    {
      renString = "光缆接头盒(终端盒)";
    } else if (typeStr.equals("7"))
    {
      renString = "ONU";
    }
    else {
      renString = "未知设备";
    }
    return renString;
  }

  public static String getEqutState(String stateStr)
  {
    String renString = "";

    if (stateStr.equals("1"))
    {
      renString = "空闲";
    }
    else if (stateStr.equals("2"))
    {
      renString = "故障";
    }
    else if (stateStr.equals("3"))
    {
      renString = "可用";
    }
    return renString;
  }

  public static PointInfoBean setPointInfoToChinese(PointInfoBean p)
  {
    p.setPtype(getPtypeStr(p.getPtype()));
    p.setPstat(getPstatStr(p.getPstat()));
    p.setDirection(getDirection(p.getDirection()));
    return p;
  }

  public static String getPtypeStr(String ptype)
  {
    String strPtype = "";
    if ("1".equals(ptype))
      strPtype = "普通端子";
    else if ("2".equals(ptype))
      strPtype = "分光器端子";
    else if ("3".equals(ptype))
      strPtype = "λ端子";
    else if ("4".equals(ptype))
      strPtype = "ONU上连端子";
    else if ("5".equals(ptype))
      strPtype = "ONU网口";
    else {
      strPtype = "未知";
    }
    return strPtype;
  }

  public static String getDirection(String direction)
  {
    String strDirection = "";
    if ("1".equals(direction))
      strDirection = "向局端";
    else if ("2".equals(direction))
      strDirection = "向用户端";
    else {
      strDirection = "未知";
    }
    return strDirection;
  }

  public static String getPstatStr(String pstat) {
    String str = "";
    if ("1".equals(pstat))
      str = "空闲";
    else if ("2".equals(pstat))
      str = "故障";
    else if ("3".equals(pstat))
      str = "可用";
    else if ("4".equals(pstat))
      str = "占用";
    else if ("5".equals(pstat)) {
      str = "待核查";
    }

    return str;
  }

  public static String creatFibercode()
  {
    String fibercode = "";
    byte versionNo = -128;
    byte productType_jumpfiber1 = -63;
    byte productType_jumpfiber2 = 1;
    byte timeCode = 0;
    return fibercode;
  }

  public static Date getOrderOverTime(int timeInt)
  {
    Date now = new Date();
    Date over = new Date(now.getTime() + timeInt * 60 * 60 * 1000);
    return over;
  }

  public static boolean workOrderCanEdit(String start)
  {
    if (start == null) {
      return false;
    }

    return (("0".equals(start)) || ("1".equals(start)) || ("7".equals(start)));
  }

  public static String hex2binary(String hexString)
  {
    if ((hexString == null) || (hexString.length() % 2 != 0))
      return null;
    String bString = "";
    for (int i = 0; i < hexString.length(); ++i) {
      String tmp = "0000" + Integer.toBinaryString(Integer.parseInt(hexString.substring(i, i + 1), 16));
      bString = bString + tmp.substring(tmp.length() - 4);
    }
    return bString;
  }

  public static List<String> Dev2Pids(String devmap, int column)
  {
    List pids = new ArrayList();
    char[] dev = devmap.toCharArray();
    for (int i = 0; i < dev.length; ++i) {
      if (dev[i] == '1') {
        int pidPart1 = i / 16 + 1;
        int pidPart2 = i % 16 + 1;
        for (int j = 1; j <= column; ++j) {
        	String pid = ((pidPart1 > 9) ? pidPart1+"" : new StringBuilder("0").append(pidPart1).toString())+
                    ((pidPart2 > 9) ? pidPart2 : new StringBuilder("0").append(pidPart2).toString()) + 
                    ((j > 9) ? j : new StringBuilder("0").append(j).toString());
            pids.add(pid);
        }
      }
    }
    return pids;
  }

  public static List<String> panelPids2Pids(String panelPids, int column)
  {
    List pids = new ArrayList();
    String[] frames = panelPids.split("n");
    for (int i = 0; i < frames.length; ++i) {
      String[] panels = frames[i].split(",");
      for (int j = 0; j < panels.length; ++j) {
        for (int k = 1; k <= column; ++k) {
          String pid = panels[j] + ((k > 9) ? k : new StringBuilder("0").append(k).toString());
          pids.add(pid);
        }
      }
    }
    return pids;
  }

  public static List<String> Dev2PanelPids(String devmap, int column)
  {
    List pids = new ArrayList();
    char[] dev = devmap.toCharArray();
    for (int i = 0; i < dev.length; ++i) {
      if (dev[i] == '1') {
        int pidPart1 = i / 16 + 1;
        int pidPart2 = i % 16 + 1;
       /* String pid = ((pidPart1 > 9) ? pidPart1 : new StringBuilder("0").append(pidPart1).toString()) + 
          ((pidPart2 > 9) ? pidPart2 : new StringBuilder("0").append(pidPart2).toString());*/
        String pid = pidPart1+"";
        pids.add(pid);
      }
    }
    return pids;
  }

  public static PointInfoBean fillPointInfo(DocPointInfoBean docPointInfoBean)
  {
    PointInfoBean p = new PointInfoBean();
    if (docPointInfoBean != null) {
      p.setPlineno(docPointInfoBean.getPlineno());
      p.setProwno(docPointInfoBean.getProwno());
      p.setPstat(docPointInfoBean.getPstat());
      p.setPtype(docPointInfoBean.getPtype());
      p.setDirection(docPointInfoBean.getDirection());
      p.setCablename(docPointInfoBean.getCablename());
      p.setMflag(Integer.valueOf(1));
      p.setDel("0");
    }
    return p;
  }

  public static PointInfoBean fillPointInfo() {
    PointInfoBean p = new PointInfoBean();
    p.setPstat("1");
    p.setPtype("1");
    p.setDirection("1");
    p.setMflag(Integer.valueOf(1));
    return p;
  }

  public static String getResoureno(int i) {
    SimpleDateFormat sdFormat = new SimpleDateFormat("HHmmss");
    String str = sdFormat.format(Long.valueOf(new Date().getTime() - (i * 1000)));
    return i + str.substring(1);
  }

  public static boolean noHasMenu(List<MenuInfoBean> menus, MenuInfoBean parent)
  {
    if (menus.isEmpty()) {
      return false;
    }
    for (int i = 0; i < menus.size(); ++i) {
      MenuInfoBean m = (MenuInfoBean)menus.get(i);
      if (m.getCode().equals(parent.getCode())) {
        return false;
      }

    }

    return true;
  }

  public static String creatPid(int i, int j, int k) {
    String pid = i+"";
    return pid;
  }

  public static String creatPid(int i, int j) {
	  String pid = i+"";
    return pid;
  }

  public static String creatPid(int i) {
    String pid = "0" + i;
    return pid;
  }

  public static String[] pointAppearAndHide(String oldDev, String newDev, String col) {
    int column = Integer.parseInt(col);
    String[] pidsArray = { "", "" };
    String pidsAppear = "";
    String pidsHide = "";
    String pid = "";
    char[] oldDevArray = hex2binary(oldDev).toCharArray();
    char[] newDevArray = hex2binary(newDev).toCharArray();
    for (int i = 0; i < oldDevArray.length; ++i) {
      if (oldDevArray[i] != newDevArray[i]) {
        int pidPart1 = i / 16 + 1;
        int pidPart2 = i % 16 + 1;
        for (int j = 1; j <= column; ++j) {
          pid = pidPart1+"";
          if (oldDevArray[i] == '1')
            pidsHide = pidsHide + pid + ",";
          else {
            pidsAppear = pidsAppear + pid + ",";
          }
        }
      }
    }
    if (pidsAppear.length() > 0) {
      pidsAppear = pidsAppear.substring(0, pidsAppear.length() - 1);
    }
    if (pidsHide.length() > 0) {
      pidsHide = pidsHide.substring(0, pidsHide.length() - 1);
    }
    pidsArray[0] = pidsAppear;
    pidsArray[1] = pidsHide;
    return pidsArray;
  }

  public static int[] parseDevmap(String devmap) {
    int[] result = new int[2];
    int frameAmount = 0;
    int panelAmount = 0;
    boolean isNewframe = false;
    char[] devArray = hex2binary(devmap).toCharArray();
    for (int i = 0; i < devArray.length; ++i) {
      isNewframe = i % 16 == 0;
      if (devArray[i] == '1') {
        if (isNewframe) {
          ++frameAmount;
          isNewframe = false;
        }
        ++panelAmount;
      }
    }
    result[0] = frameAmount;
    result[1] = panelAmount;
    return result;
  }

  public static String pointHide(String oldDev, String newDev)
  {
    return null; }

  public static int trans1(char[] ch) {
    int sum = 0;
    for (int i = 0; i < 4; ++i) {
      int y = 8;
      if (ch[i] == '1') {
        for (int j = 1; j <= i; ++j) {
          y /= 2;
        }
        sum += y;
      }
    }
    return sum;
  }

  public static String switchS(int i) {
    String s = "";
    switch (i)
    {
    case 10:
      s = "a";
      break;
    case 11:
      s = "b";
      break;
    case 12:
      s = "c";
      break;
    case 13:
      s = "d";
      break;
    case 14:
      s = "e";
      break;
    case 15:
      s = "f";
      break;
    default:
      s = i+"";
    }
    return s;
  }

  public static String transBinToHex(String str)
  {
    int len = str.length() / 4;
    String res = "";
    for (int i = 0; i < len; ++i) {
      char[] ch = new char[4];
      str.getChars(i * 4, i * 4 + 4, ch, 0);
      res = res + switchS(trans1(ch));
    }
    return res;
  }

  public static byte[] hexStringToByte(String hex)
  {
    int len = hex.length() / 2;
    byte[] result = new byte[len];
    char[] achar = hex.toUpperCase().toCharArray();
    for (int i = 0; i < len; ++i) {
      int pos = i * 2;
      result[i] = (byte)(toByte(achar[pos]) << 4 | toByte(achar[(pos + 1)]));
    }
    return result; }

  private static byte toByte(char c) {
    byte b = (byte)"0123456789ABCDEF".indexOf(c);
    return b;
  }

  public static final String bytesToHexString(byte[] bArray)
  {
    if (bArray == null)
      return "";
    StringBuffer sb = new StringBuffer(bArray.length);

    for (int i = 0; i < bArray.length; ++i) {
      String sTemp = Integer.toHexString(0xFF & bArray[i]);
      if (sTemp.length() < 2)
        sb.append(0);
      sb.append(sTemp.toUpperCase());
    }
    return sb.toString();
  }

  public static String changeDevmap(String devmap, String address, char changTo)
  {
    int no = (Integer.parseInt(address.substring(0, 2)) - 1) * 16 + Integer.parseInt(address.substring(2, 4)) - 1;
    char[] devArray = hex2binary(devmap).toCharArray();
    devArray[no] = changTo;
    String temp = "";
    for (char c : devArray) {
      temp = temp + c;
    }
    return transBinToHex(temp);
  }

  public static String readFileByLines(File file)
  {
    BufferedReader reader = null;
    String tempString = null;
    try {
      System.out.println("以行为单位读取文件内容，一次读一整行：");
      reader = new BufferedReader(new FileReader(file));
      int line = 1;

      tempString = reader.readLine();
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
      return "";
    } finally {
      if (reader != null)
        try {
          reader.close();
        }
        catch (IOException localIOException2) {
        }
    }
    return tempString; }

  public static Integer setNulltoZero(Integer str) {
    return Integer.valueOf((str == null) ? 0 : str.intValue());
  }

  public static String[] parseFibercodeToStr(String fibercode) {
    String[] meaning = { "", "", "", "", "", "", "", "" };
    meaning[0] = "版本号：" + Integer.parseInt(fibercode.substring(0, 2));
    String type = "";
    switch (getFibercodeType(fibercode))
    {
    case 1:
      type = "跳纤";
      break;
    case 2:
      type = "尾纤";
      break;
    case 3:
      type = "跳缆";
      break;
    case 4:
      type = "分光器输入端";
      break;
    case 5:
      type = "分光器输出端";
      break;
    default:
      type = "未知";
    }

    meaning[1] = "芯片类型：" + type;
    meaning[2] = "编号：" + fibercode.substring(4, 42);
    meaning[3] = "端口序号：" + fibercode.substring(42, 44);
    meaning[4] = "入端口数：" + Integer.parseInt(fibercode.substring(44, 46));
    meaning[5] = "出端口数：" + Integer.parseInt(fibercode.substring(46, 48));
    meaning[6] = "运营商标识：" + fibercode.substring(48, 58);
    meaning[7] = "CRC：" + fibercode.substring(62, 64);
    return meaning;
  }

  public static int getFibercodeType(String fibercode)
  {
    try
    {
      if (fibercode != null) {
        int temp = Integer.parseInt(fibercode.substring(2, 4));
        if (temp == 44) {
          return 4;
        }
        if (temp == 84) {
          return 5;
        }
        return temp;
      }
    } catch (Exception e) {
      return 0;
    }
    return 0;
  }

  public String[] getSort(String[] array) {
    for (int i = 0; i < array.length; ++i) {
      for (int j = i; j < array.length; ++j) {
        if (Integer.valueOf(array[j]).intValue() > Integer.valueOf(array[(j + 1)]).intValue()) {
          String temp = array[j];
          array[j] = array[(j + 1)];
          array[(j + 1)] = temp;
        }
      }
    }
    return array; }

  public static List<MenuInfoBean> getSorts(List<MenuInfoBean> ts) {
   /* Collections.sort(ts, new Comparator(){
      public int compare(MenuInfoBean arg0, MenuInfoBean arg1) {
        int result = Integer.valueOf(arg0.getCode()).intValue() - Integer.valueOf(arg1.getCode()).intValue();
        return result;
      }
    });*/
    return ts;
  }

  public static List<PointInfoBean> pointPlaceChange(List<PointInfoBean> pointList, String col) {
    int amount = pointList.size();
    int cols = Integer.parseInt(col);
    int rows = amount / cols;
    List resultList = new ArrayList();
    for (int i = 0; i < amount; ++i) {
      resultList.add(new PointInfoBean());
    }
    for (int i = 0; i < amount; ++i) {
      PointInfoBean p = (PointInfoBean)pointList.get(i);
      int thisRow = i / cols;
      int thisCol = i % cols;

      int newPosition = thisCol * rows + thisRow;
      resultList.set(newPosition, p);
    }
    return resultList;
  }

  public static List<PointInfoBean> pointPlaceChanges(List<PointInfoBean> pointList, String col, String str) {
    int amount = pointList.size();
    int cols = Integer.parseInt(col);
    int rows = 0;
    int rowz = 0;
    String[] strs = str.split("n");
    List resultList = new ArrayList();
    for (int i = 0; i < amount; ++i) {
      resultList.add(new PointInfoBean());
    }
    for (int i = 0; i < strs.length; ++i) {
      rows = strs[i].split(",").length;
      int realrows = 0;

      int n = 0;
      for (int m = 0; m < rows; ++m) {
        String pid = ((PointInfoBean)pointList.get(n)).getPid();
        boolean flag = pid.startsWith(strs[i].split(",")[m]);
        if (flag) {
          ++realrows;
          n += cols;
          if (n >= amount) {
            break;
          }
        }
      }
      int nowtotals = cols * realrows;
      int j;
      PointInfoBean p;
      int thisRow;
      int thisCol;
      int newPosition;
      if (i == 0)
        for (j = 0; j < nowtotals; ++j) {
          p = (PointInfoBean)pointList.get(j);
          thisRow = j / cols;
          thisCol = j % cols;

          newPosition = thisCol * realrows + thisRow;
          resultList.set(newPosition, p);
        }
      else {
        for (j = 0; j < nowtotals; ++j) {
          p = (PointInfoBean)pointList.get(j + rowz * cols);
          thisRow = j / cols;
          thisCol = j % cols;

          newPosition = thisCol * realrows + thisRow + rowz * cols;
          resultList.set(newPosition, p);
        }
      }
      rowz += realrows;
    }
    return resultList;
  }

  public static String todecing(String str) {
    if ((str == null) || (str.equals(""))) {
      return null;
    }
    str = str.replace("^^", "#");
    return str;
  }

  public static String getOppoTubeNumber(TubeInfoBean tube, int cols, int amount) {
    String tubeNum = tube.getTubeNumber();
    String no1 = tubeNum.substring(0, 2);
    int no2 = Integer.parseInt(tubeNum.substring(2, 4));
    int no3 = Integer.parseInt(tubeNum.substring(4, 6));
    int newNo2 = cols + 1 - no2;
    int newNo3 = amount + 1 - no3;
    return no1 + ((newNo2 > 9) ? Integer.valueOf(newNo2) : new StringBuilder("0").append(newNo2).toString()) + ((newNo3 > 9) ? Integer.valueOf(newNo3) : new StringBuilder("0").append(newNo3).toString());
  }

  public static String getOppoTubeNumber(String tubeNumber, int cols, int amount) {
    String tubeNum = tubeNumber;
    String no1 = tubeNum.substring(0, 2);
    int no2 = Integer.parseInt(tubeNum.substring(2, 4));
    int no3 = Integer.parseInt(tubeNum.substring(4, 6));
    int newNo2 = cols + 1 - no2;
    int newNo3 = amount + 1 - no3;
    return no1 + ((newNo2 > 9) ? Integer.valueOf(newNo2) : new StringBuilder("0").append(newNo2).toString()) + ((newNo3 > 9) ? Integer.valueOf(newNo3) : new StringBuilder("0").append(newNo3).toString());
  }

  public static String getOppoTubeNumber(TubeInfoBean tube, int cols) {
    String tubeNum = tube.getTubeNumber();
    String no1 = tubeNum.substring(0, 2);
    int no2 = Integer.parseInt(tubeNum.substring(2, 4));
    int newNo2 = cols + 1 - no2;
    return no1 + ((newNo2 > 9) ? Integer.valueOf(newNo2) : new StringBuilder("0").append(newNo2).toString());
  }

  public static String getOppoTubeNumber(String tubeNumber, int cols) {
    String tubeNum = tubeNumber;
    String no1 = tubeNum.substring(0, 2);
    int no2 = Integer.parseInt(tubeNum.substring(2, 4));
    int newNo2 = cols + 1 - no2;
    return no1 + ((newNo2 > 9) ? Integer.valueOf(newNo2) : new StringBuilder("0").append(newNo2).toString());
  }

  public static Integer elementToIngeger(Element el, String tagName)
  {
    String elContent = el.elementTextTrim(tagName);
    if ((elContent == null) || (elContent.equals(""))) {
      return null;
    }
    return Integer.valueOf(Integer.parseInt(elContent));
  }

  private static double rad(double d)
  {
    return (d * 3.141592653589793D / 180.0D);
  }

  public static double GetDistance(double lat1, double lng1, double lat2, double lng2)
  {
    double radLat1 = rad(lat1);
    double radLat2 = rad(lat2);
    double a = radLat1 - radLat2;
    double b = rad(lng1) - rad(lng2);
    double s = 2.0D * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2.0D), 2.0D) + 
      Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2.0D), 2.0D)));
    s = s * 6378.1369999999997D * 1000.0D;
    return s;
  }
}