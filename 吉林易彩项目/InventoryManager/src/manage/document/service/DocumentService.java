package manage.document.service;

import base.exceptions.DataAccessException;
import java.util.List;
import manage.document.pojo.DocEqutInfoBean;
import manage.document.pojo.DocJumpInfoBean;
import manage.document.pojo.DocPointInfoBean;
import manage.route.pojo.CableInfoBean;
import manage.route.pojo.RouteInfoBean;
import manage.user.pojo.UserInfoBean;

public abstract interface DocumentService
{
  public abstract DocEqutInfoBean loadDocumentEqutGrid(DocEqutInfoBean paramDocEqutInfoBean)
    throws DataAccessException;

  public abstract int inportDocumentInfo(List<DocEqutInfoBean> paramList)
    throws Exception;

  public abstract int deleteDocEqutInfo(List<String> paramList)
    throws DataAccessException;

  public abstract int deleteDocEqutInfoJump(List<String> paramList)
    throws DataAccessException;

  public abstract DocPointInfoBean loadDocPointGrid(DocPointInfoBean paramDocPointInfoBean)
    throws DataAccessException;

  public abstract int inportDocumentInfoWorkerorder(List<DocEqutInfoBean> paramList, String[] paramArrayOfString, UserInfoBean paramUserInfoBean)
    throws Exception;

  public abstract int importDocumentEqutInfo(List<DocEqutInfoBean> paramList)
    throws Exception;

  public abstract int importDocumentPointInfo(List<DocPointInfoBean> paramList)
    throws Exception;

  public abstract int importDocumentCableInfo(List<CableInfoBean> paramList)
    throws Exception;

  public abstract int importDocumentRouteInfo(List<RouteInfoBean> paramList)
    throws Exception;

  public abstract int importDocumentJumpInfo(List<DocJumpInfoBean> paramList)
    throws Exception;

  public abstract void insertDocEuqt(DocEqutInfoBean paramDocEqutInfoBean)
    throws Exception;

  public abstract void insertDocPoint(List<DocPointInfoBean> paramList)
    throws Exception;
}

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     manage.document.service.DocumentService
 * JD-Core Version:    0.5.3
 */