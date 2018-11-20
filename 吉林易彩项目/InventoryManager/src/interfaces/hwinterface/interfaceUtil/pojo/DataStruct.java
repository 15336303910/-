package interfaces.hwinterface.interfaceUtil.pojo;

import interfaces.hwinterface.interfaceUtil.util.CRCUtil;
import java.io.PrintStream;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import org.apache.log4j.Logger;

public class DataStruct
{
  private static final Logger log = Logger.getLogger(DataStruct.class);
  public static volatile boolean isBTResultReady = false;
  public static final int PACKAGE_HEAD_LENGTH = 22;
  public static boolean D = true;
  private static final String TAG = "BTStruct class";
  public static final short CMD_FLASH_LED = 1;
  public static final short CMD_BUZZER = 2;
  public static final short CMD_GET_DEVMAP = 3;
  public static final short CMD_GET_STATUS = 4;
  public static final short CMD_READ_FLASH = 5;
  public static final short CMD_WRITE_FLASH = 6;
  public static final short CMD_FLASH_JUMP = 8;
  public static final short LED_OPEN_ALL = 1;
  public static final short LED_CLOSE_ALL = 2;
  public static final short LED_ON_1 = 3;
  public static final short LED_OFF_1 = 4;
  public static final short LED_SLOW_1 = 5;
  public static final short LED_FAST_1 = 6;
  public static final short LED_SELF_TEST = 7;
  public static final short LED_F3S_ON = 8;
  public static final short LED_F3S_OFF = 9;
  public static final short CMD_TAG_ONLINE = 10;
  public static final short CMD_TAG_READ = 11;
  public static final short CMD_TAG_WRITE = 12;
  public static final short CMD_TAG_TEST_SELF = 13;
  public static final short CMD_DEV_ATTR = 15;
  public static final short CMD_DIGITAL = 16;
  public static final short CMD_IPCONFIG = 20;
  public static final short CMD_RESET = 21;
  public static final short CMD_FIX_CHANNEL = 25;
  public static final short ERR_DEVID = 2;
  public static final short ERR_ERROR = 1;
  public static final short ERR_OK = 0;
  public static final short ALLOW_STORE_POSI = 270;
  public short head;
  public short crc;
  public short ln;
  public int u32bdg;
  public int u32dev;
  public short cmd;
  public short fRet;
  public short rand;
  public short p1;
  public short p2;
  public short p3;
  public byte[] datload;

  public DataStruct(DataStruct myin)
  {
    this.head = myin.head;
    this.crc = myin.crc;
    this.ln = myin.ln;
    this.u32bdg = myin.u32bdg;
    this.u32dev = myin.u32dev;

    this.cmd = myin.cmd;
    this.fRet = myin.fRet;

    this.rand = myin.rand;
    this.p1 = myin.p1;
    this.p2 = myin.p2;
    this.p3 = myin.p3;

    this.datload = myin.datload;
  }

  public synchronized void setDataStruct(DataStruct myin)
  {
    this.head = myin.head;
    this.crc = myin.crc;
    this.ln = myin.ln;

    this.u32bdg = myin.u32bdg;
    this.u32dev = myin.u32dev;

    this.cmd = myin.cmd;
    this.fRet = myin.fRet;

    this.rand = myin.rand;
    this.p1 = myin.p1;
    this.p2 = myin.p2;
    this.p3 = myin.p3;

    this.datload = myin.datload;
    isBTResultReady = true;
    if (D)
    {
      log.debug("notify all isBTResultReady=" + isBTResultReady);
    }

    super.notifyAll();
  }

  public DataStruct() {
    this.head = 21930;
  }

  public void clear() {
    this.head = 0;
    this.crc = 0;
    this.ln = 0;
    this.u32bdg = 0;
    this.u32dev = 0;

    this.cmd = 0;
    this.fRet = -1;

    this.rand = 0;
    this.p1 = 0;
    this.p2 = 0;
    this.p3 = 0;
    this.datload = null;
  }

  public boolean set_head(String exp)
  {
    try {
      this.head = Short.decode(exp).shortValue();
      return true;
    } catch (NumberFormatException e) {
      if (D)
        log.error("set_head出错" + e.toString()); 
    }
    return false;
  }

  public boolean set_crc(String exp)
  {
    try {
      this.crc = Short.decode(exp).shortValue();
      return true;
    } catch (NumberFormatException e) {
      if (D)
        log.error("set_crc出错" + e.toString()); 
    }
    return false;
  }

  public boolean set_ln(String exp)
  {
    try {
      this.ln = Short.decode(exp).shortValue();
      return true;
    } catch (NumberFormatException e) {
      if (D)
        log.error("set_ln出错" + e.toString()); 
    }
    return false;
  }

  public boolean set_cmd(String exp)
  {
    try {
      this.cmd = Short.decode(exp).shortValue();
      return true;
    } catch (NumberFormatException e) {
      if (D)
        log.error("set_cmd出错" + e.toString()); 
    }
    return false;
  }

  public boolean set_fret(String exp)
  {
    try {
      this.fRet = Short.decode(exp).shortValue();
      return true;
    } catch (NumberFormatException e) {
      if (D)
        log.error("set_fret出错" + e.toString()); 
    }
    return false;
  }

  public boolean set_rand(String exp)
  {
    try {
      this.rand = Short.decode(exp).shortValue();
      return true;
    } catch (NumberFormatException e) {
      if (D)
        log.error("set_rand出错" + e.toString()); 
    }
    return false;
  }

  public boolean set_p1(String exp)
  {
    try {
      this.p1 = Short.decode(exp).shortValue();
      return true;
    } catch (NumberFormatException e) {
      if (D)
        log.error("set_p1出错" + e.toString()); 
    }
    return false;
  }

  public boolean set_p2(String exp)
  {
    try {
      this.p2 = Short.decode(exp).shortValue();
      return true;
    } catch (NumberFormatException e) {
      if (D)
        log.error("set_p2出错" + e.toString()); 
    }
    return false;
  }

  public boolean set_p3(String exp) {
    try {
      this.p3 = Short.decode(exp).shortValue();
      return true;
    } catch (NumberFormatException e) {
      if (D)
        log.error("set_p3出错" + e.toString()); 
    }
    return false;
  }

  public boolean set_datloadbystring(String exp)
  {
    try {
      this.datload = exp.getBytes();
      return true;
    } catch (Exception e) {
      if (D)
        log.error("set_dataloadbystring出错" + e.toString()); 
    }
    return false;
  }

  public boolean set_datloadbyhexstring(String exp)
  {
    return true;
  }

  public boolean set_all(byte[] receivebytes)
  {
    boolean tran = false;

    ByteBuffer mbuf = ByteBuffer.wrap(receivebytes);
    mbuf.position(0);
    this.head = mbuf.asShortBuffer().get();

    if (this.head == 21930) {
      tran = false;
    }
    if (this.head == -21931) {
      tran = true;
    }

    if (tran) {
      byte fir = 0;
      byte sec = 0;
      byte[] newBytes = new byte[receivebytes.length];
      for (int i = 0; i < receivebytes.length; ++i) {
        fir = receivebytes[i];
        sec = receivebytes[(++i)];
        newBytes[(i - 1)] = sec;
        newBytes[i] = fir;
      }
      receivebytes = newBytes;
    }

    StringBuffer sb = new StringBuffer(receivebytes.length);

    for (int i = 0; i < receivebytes.length; ++i) {
      String sTemp = Integer.toHexString(0xFF & receivebytes[i]);
      if (sTemp.length() < 2)
        sb.append(0);
      sb.append(sTemp.toUpperCase());
    }
    log.debug("返回串为" + sb);
    try
    {
      mbuf = ByteBuffer.wrap(receivebytes);
      mbuf.position(0);
      this.head = mbuf.asShortBuffer().get();
      mbuf.position(2);
      this.crc = mbuf.asShortBuffer().get();
      mbuf.position(4);
      this.ln = mbuf.asShortBuffer().get();
      mbuf.position(6);
      this.u32bdg = mbuf.asIntBuffer().get();
      mbuf.position(10);
      this.u32dev = mbuf.asIntBuffer().get();
      mbuf.position(14);
      this.cmd = mbuf.asShortBuffer().get();
      log.debug("通过返回串得知我发送的命令是" + this.cmd);
      mbuf.position(16);
      this.fRet = mbuf.asShortBuffer().get();
      mbuf.position(18);
      this.rand = mbuf.asShortBuffer().get();
      mbuf.position(20);
      this.p1 = mbuf.asShortBuffer().get();
      mbuf.position(22);
      this.p2 = mbuf.asShortBuffer().get();
      mbuf.position(24);
      this.p3 = mbuf.asShortBuffer().get();

      if (this.ln > 0) {
        this.datload = new byte[this.ln];
        mbuf.position(26);
        mbuf.get(this.datload, 0, this.ln);
      }
      return true;
    } catch (Exception e) {
      log.error("set_all() exception happen!" + e.toString()); }
    return false;
  }

  public short calCRC()
  {
    return CRCUtil.crc16Short(getBody());
  }

  public short calln() {
    try {
      return (short)this.datload.length; } catch (Exception e) {
    }
    return 0;
  }

  public byte[] getBody()
  {
    ByteBuffer mbuf = ByteBuffer.allocate(22 + this.ln);
    try {
      mbuf.position(0);
      mbuf.asShortBuffer().put(this.ln);
      mbuf.position(2);
      mbuf.asIntBuffer().put(this.u32bdg);
      mbuf.position(6);
      mbuf.asIntBuffer().put(this.u32dev);
      mbuf.position(10);
      mbuf.asShortBuffer().put(this.cmd);
      mbuf.position(12);
      mbuf.asShortBuffer().put(this.fRet);
      mbuf.position(14);
      mbuf.asShortBuffer().put(this.rand);
      mbuf.position(16);
      mbuf.asShortBuffer().put(this.p1);
      mbuf.position(18);
      mbuf.asShortBuffer().put(this.p2);
      mbuf.position(20);
      mbuf.asShortBuffer().put(this.p3);
      if (this.ln > 0) {
        mbuf.position(22);
        mbuf.put(this.datload);
      }
      return mbuf.array();
    }
    catch (BufferOverflowException e)
    {
      if (D)
        log.error("getbody()出错" + e.toString()); 
    }
    return mbuf.array();
  }

  public synchronized boolean receiveBTResult(int waitms) {
    if (waitms < 0)
      waitms = 100;
    log.debug("isBTResultReady" + isBTResultReady);
    isBTResultReady = false;
    try {
      super.wait(waitms);
      return isBTResultReady;
    } catch (InterruptedException ie) {
      System.out.println(ie.getStackTrace()); }
    return false;
  }

  public byte[] getByteArray()
  {
    ByteBuffer mbuf = ByteBuffer.allocate(26 + this.ln);
    try {
      mbuf.position(0);
      mbuf.asShortBuffer().put(this.head);
      mbuf.position(2);
      mbuf.asShortBuffer().put(this.crc);
      mbuf.position(4);
      mbuf.asShortBuffer().put(this.ln);
      mbuf.position(6);
      mbuf.asIntBuffer().put(this.u32bdg);
      mbuf.position(10);
      mbuf.asIntBuffer().put(this.u32dev);
      mbuf.position(14);
      mbuf.asShortBuffer().put(this.cmd);
      mbuf.position(16);
      mbuf.asShortBuffer().put(this.fRet);
      mbuf.position(18);
      mbuf.asShortBuffer().put(this.rand);
      mbuf.position(20);
      mbuf.asShortBuffer().put(this.p1);
      mbuf.position(22);
      mbuf.asShortBuffer().put(this.p2);
      mbuf.position(24);
      mbuf.asShortBuffer().put(this.p3);
      if (this.ln > 0) {
        mbuf.position(26);
        mbuf.put(this.datload);
      }
      return mbuf.array();
    } catch (Exception e) {
      if (D)
        log.error("getbody()出错" + e.toString()); 
    }
    return mbuf.array();
  }

  public boolean dataCheck(int cmd)
  {
    if (this.head != 21930) {
      return false;
    }
    if (this.fRet != 0) {
      return false;
    }

    return (this.cmd == cmd);
  }

  public short getHead()
  {
    return this.head;
  }

  public void setHead(short head) {
    this.head = head;
  }

  public short getCrc() {
    return this.crc;
  }

  public void setCrc(short crc) {
    this.crc = crc;
  }

  public short getLn() {
    return this.ln;
  }

  public void setLn(short ln) {
    this.ln = ln;
  }

  public int getU32bdg() {
    return this.u32bdg;
  }

  public void setU32bdg(int u32bdg) {
    this.u32bdg = u32bdg;
  }

  public int getU32dev() {
    return this.u32dev;
  }

  public void setU32dev(int u32dev) {
    this.u32dev = u32dev;
  }

  public short getCmd() {
    return this.cmd;
  }

  public void setCmd(short cmd) {
    this.cmd = cmd;
  }

  public short getFRet() {
    return this.fRet;
  }

  public void setFRet(short ret) {
    this.fRet = ret;
  }

  public short getRand() {
    return this.rand;
  }

  public void setRand(short rand) {
    this.rand = rand;
  }

  public short getP1() {
    return this.p1;
  }

  public void setP1(short p1) {
    this.p1 = p1;
  }

  public short getP2() {
    return this.p2;
  }

  public void setP2(short p2) {
    this.p2 = p2;
  }

  public short getP3() {
    return this.p3;
  }

  public void setP3(short p3) {
    this.p3 = p3;
  }

  public byte[] getDatload() {
    return this.datload;
  }

  public void setDatload(byte[] datload) {
    this.datload = datload;
  }
}