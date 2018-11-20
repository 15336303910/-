package manage.event.service;

import base.exceptions.DataAccessException;
import manage.event.pojo.EventInfoBean;

public abstract interface EventService
{
  public abstract void saveEvent(EventInfoBean paramEventInfoBean)
    throws DataAccessException;
}

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     manage.event.service.EventService
 * JD-Core Version:    0.5.3
 */