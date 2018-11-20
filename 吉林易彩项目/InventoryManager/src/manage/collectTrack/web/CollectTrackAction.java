package manage.collectTrack.web;

import org.apache.log4j.Logger;

import interfaces.pdainterface.collectTrack.pojo.CollectTrackBean;
import interfaces.pdainterface.collectTrack.service.impl.ICollectTrackService;
import base.web.PaginationAction;

import com.opensymphony.xwork2.ModelDriven;


/**
 * 轨迹的前台
 * @author chenqp
 *
 */
public class CollectTrackAction extends PaginationAction implements ModelDriven{
	private static final Logger log = Logger.getLogger(CollectTrackAction.class);
	private ICollectTrackService collectTrackService;
	private CollectTrackBean object = new CollectTrackBean();
	
	
	
	@Override
	public Object getModel() {
		return object;
	}
	public ICollectTrackService getCollectTrackService() {
		return collectTrackService;
	}
	public void setCollectTrackService(ICollectTrackService collectTrackService) {
		this.collectTrackService = collectTrackService;
	}
	public CollectTrackBean getObject() {
		return object;
	}
	public void setObject(CollectTrackBean object) {
		this.object = object;
	}
	public static Logger getLog() {
		return log;
	}
}
