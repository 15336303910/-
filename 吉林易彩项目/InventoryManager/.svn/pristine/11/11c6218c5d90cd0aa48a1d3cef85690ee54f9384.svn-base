package manage.equt.service;

import base.exceptions.DataAccessException;
import java.util.List;
import manage.domain.pojo.DomainBean;
import manage.equt.pojo.ApplyInfoBean;
import manage.equt.pojo.CodeIndexInfoBean;
import manage.equt.pojo.EqutInfoBean;
import manage.equt.pojo.EqutModelInfoBean;
import manage.fiber.pojo.JumpFiberInfoBean;
import manage.point.pojo.PointEventInfoBean;
import manage.point.pojo.PointInfoBean;
import manage.route.pojo.CableInfoBean;
import manage.tree.pojo.Tree;
import manage.user.pojo.UserInfoBean;

public abstract interface EqutInfoService
{
  public abstract EqutInfoBean getEqutInfoBean(EqutInfoBean paramEqutInfoBean)
    throws DataAccessException;

  public abstract List<EqutInfoBean> getEqutList(EqutInfoBean paramEqutInfoBean)
    throws DataAccessException;

  public abstract List<EqutInfoBean> getUpEqutList(EqutInfoBean paramEqutInfoBean)
    throws DataAccessException;

  public abstract List<EqutInfoBean> getDownEqutList(EqutInfoBean paramEqutInfoBean)
    throws DataAccessException;

  public abstract List<Tree> getDomainAndEqutTree(DomainBean paramDomainBean)
    throws DataAccessException;

  public abstract int updateEqutInfo(EqutInfoBean paramEqutInfoBean)
    throws DataAccessException;

  public abstract List<EqutInfoBean> getEqutListAll(EqutInfoBean paramEqutInfoBean)
    throws DataAccessException;

  public abstract List<Tree> getEqutInfoTreeList(EqutInfoBean paramEqutInfoBean)
    throws DataAccessException;

  public abstract EqutModelInfoBean getEmodelInfoBean(EqutInfoBean paramEqutInfoBean)
    throws DataAccessException;

  public abstract List<UserInfoBean> getEqutUserCK(EqutInfoBean paramEqutInfoBean)
    throws DataAccessException;

  public abstract void saveLastEid(UserInfoBean paramUserInfoBean)
    throws DataAccessException;

  public abstract void saveEqutHeartBeat(EqutInfoBean paramEqutInfoBean)
    throws Exception;

  public abstract UserInfoBean getUserInfo(EqutInfoBean paramEqutInfoBean)
    throws DataAccessException;

  public abstract List<UserInfoBean> getPrlUserList(EqutInfoBean paramEqutInfoBean)
    throws DataAccessException;

  public abstract void deleteEqut(EqutInfoBean paramEqutInfoBean)
    throws DataAccessException;

  public abstract List<CableInfoBean> getCableList(CableInfoBean paramCableInfoBean)
    throws DataAccessException;

  public abstract void insertEqutInfo(EqutInfoBean paramEqutInfoBean)
    throws DataAccessException;

  public abstract List<EqutInfoBean> getEqutByAreano(EqutInfoBean paramEqutInfoBean);

  public abstract int getCableAlarmCount(EqutInfoBean paramEqutInfoBean);

  public abstract List<EqutInfoBean> getEqutCheck(EqutInfoBean paramEqutInfoBean)
    throws DataAccessException;

  public abstract int updateEqutDomain(EqutInfoBean paramEqutInfoBean)
    throws DataAccessException;

  public abstract List<EqutInfoBean> getNoLatLonEqut(EqutInfoBean paramEqutInfoBean)
    throws DataAccessException;

  public abstract List<EqutInfoBean> getSonList(EqutInfoBean paramEqutInfoBean)
    throws DataAccessException;

  public abstract PointInfoBean getEqutPoint(EqutInfoBean paramEqutInfoBean)
    throws DataAccessException;


  public abstract void insertCodeFibercode(List<JumpFiberInfoBean> paramList)
    throws DataAccessException;

  public abstract int getIndex(CodeIndexInfoBean paramCodeIndexInfoBean)
    throws DataAccessException;

  public abstract void insertIndex(CodeIndexInfoBean paramCodeIndexInfoBean)
    throws DataAccessException;

  public abstract void updatePoint(PointInfoBean paramPointInfoBean)
    throws DataAccessException;

  public abstract boolean isFibercodeExist(JumpFiberInfoBean paramJumpFiberInfoBean)
    throws DataAccessException;

  public abstract ApplyInfoBean applyGrid(ApplyInfoBean paramApplyInfoBean)
    throws DataAccessException;

  public abstract int delApplyBean(ApplyInfoBean paramApplyInfoBean)
    throws DataAccessException;

  public abstract int updateEqutIP(EqutInfoBean paramEqutInfoBean)
    throws DataAccessException;

  public abstract EqutInfoBean getEqutGrid(EqutInfoBean paramEqutInfoBean)
    throws DataAccessException;
}

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     manage.equt.service.EqutInfoService
 * JD-Core Version:    0.5.3
 */