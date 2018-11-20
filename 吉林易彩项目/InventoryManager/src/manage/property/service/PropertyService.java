package manage.property.service;

import base.exceptions.DataAccessException;
import manage.property.pojo.PropertyInfoBean;

public abstract interface PropertyService
{
  public abstract PropertyInfoBean getproperty(PropertyInfoBean paramPropertyInfoBean)
    throws DataAccessException;
}

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     manage.property.service.PropertyService
 * JD-Core Version:    0.5.3
 */