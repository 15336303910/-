package interfaces.pdainterface.login.service;

import base.exceptions.DataAccessException;

import java.util.List;

import manage.domain.pojo.DomainBean;
import manage.user.pojo.SmsAuthCodeBean;
import manage.user.pojo.UserInfoBean;

public abstract interface PDALoginService
{
  public abstract UserInfoBean login(UserInfoBean paramUserInfoBean)
    throws DataAccessException;

  public abstract void changePwd(UserInfoBean paramUserInfoBean)
    throws DataAccessException;

  public abstract void changeOffLinePwd(UserInfoBean paramUserInfoBean)
    throws DataAccessException;

  public abstract List<DomainBean> getDomainList(DomainBean paramDomainBean)
    throws DataAccessException;
  
  
  /**
   * 得到版本号
   * 必须更新的版本号
   * @return
   */
  public String getVersion();
  
  
  /**
   * 增加登录日志
   * @param user
   */
  public void addLog(UserInfoBean user);
  
  
  /**
   * 添加验证码
   * @param obj
   * @return
   */
  public int addAuthCode(SmsAuthCodeBean obj);
  
  
  /**
   * 检查验证码
   * @param user
   * @return
   */
  public String checkAuthCode(UserInfoBean user);
  
  
  /**
   * 将验证码重置
   * @param userName
   */
  public void upAuthCode(String userName);
  
  /**
   * 
   * @param user
   * @return
   */
  public String getAuthCode(UserInfoBean user);
}
