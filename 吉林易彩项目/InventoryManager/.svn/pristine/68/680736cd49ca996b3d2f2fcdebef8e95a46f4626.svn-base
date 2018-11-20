package interfaces.hwinterface.interfaces.equt.pojo;

import base.util.CommonUtil;
import interfaces.hwinterface.interfaceUtil.pojo.DataStruct;
import interfaces.hwinterface.interfaceUtil.util.ValueUtil;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import manage.point.pojo.PointEventInfoBean;

public class EqutInspectStruct extends DataStruct
{
  private static final String TAG = "EqutInspectStruct Class";

  public EqutInspectStruct()
  {
    this.head = 21930;
    this.ln = 0;
    this.u32bdg = 0;
    this.u32dev = 0;
    this.cmd = 3;
    this.p1 = 0;
    this.p2 = 0;
    this.crc = calCRC();
  }

  public List<PointEventInfoBean> getEventList() {
    List eventList = new ArrayList();
    int[][] temp = new int[12][6];
    ByteBuffer byteb = ByteBuffer.wrap(this.datload);

    for (int i = 0; i < 12; ++i) {
      for (int j = 0; j < 6; ++j) {
        byteb.position(48 + i * 6 * 4 + j * 4);
        temp[i][j] = byteb.asIntBuffer().get();
      }
    }
    byteb.position(336);
    byte[] tempByte = new byte[144];
    System.arraycopy(byteb.array(), 336, tempByte, 0, 144);
    String tempStr = ValueUtil.bytesToHexString(tempByte);

    for (int i = 0; i < 12; ++i) {
      for (int j = 0; j < 6; ++j)
        if (temp[i][j] != 0) {
          String temps = tempStr.substring((i * 6 + j) * 4, 
            (i * 6 + j) * 4 + 4);

          String ta = temps.substring(0, 2);
          String tb = temps.substring(2, 4);
          temps = tb + ta;
          int tempType = Integer.parseInt(temps.toLowerCase(), 16);
          int sun = 1;

          for (int q = 0; q < 12; ++q) {
            int state = tempType & (int)Math.pow(2.0D, q);

            if (state == 0) {
              ++sun;
              PointEventInfoBean e = new PointEventInfoBean();
              e.setPid(CommonUtil.creatPid(i, j, q));
              e.setState(state+"");
              e.setType("0");
              e.setIsEvent("1");
              e.setIsLatest("1");
              eventList.add(e);
            }
            else {
              ++sun;
            }
          }
        }
    }
    return eventList;
  }
  }
