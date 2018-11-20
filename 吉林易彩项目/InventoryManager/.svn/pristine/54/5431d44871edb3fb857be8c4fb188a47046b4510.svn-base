package interfaces.pdainterface.point.service;

import base.exceptions.DataAccessException;
import java.util.List;
import manage.equt.pojo.EqutInfoBean;
import manage.fiber.pojo.JumpFiberInfoBean;
import manage.point.pojo.PointEventInfoBean;
import manage.point.pojo.PointInfoBean;
import manage.user.pojo.UserInfoBean;

public abstract interface PDAPointInfoService
{
  public abstract List<PointInfoBean> getPointInfoList(PointInfoBean paramPointInfoBean)
    throws DataAccessException;

  public abstract List<PointInfoBean> getPointInfoList(PointInfoBean paramPointInfoBean, int paramInt1, int paramInt2)
    throws DataAccessException;

  public abstract int getPointInfoCount(PointInfoBean paramPointInfoBean)
    throws DataAccessException;

  public abstract void savePointSync(PointInfoBean paramPointInfoBean)
    throws DataAccessException;

  public abstract EqutInfoBean getEqutInfo(PointInfoBean paramPointInfoBean)
    throws DataAccessException;

  public abstract List<PointInfoBean> changeODM(PointInfoBean paramPointInfoBean, UserInfoBean paramUserInfoBean);

  public abstract void savePointFiber(JumpFiberInfoBean paramJumpFiberInfoBean, UserInfoBean paramUserInfoBean)
    throws DataAccessException;

  public abstract void savePointOppo(PointInfoBean paramPointInfoBean, UserInfoBean paramUserInfoBean)
    throws DataAccessException;
}

