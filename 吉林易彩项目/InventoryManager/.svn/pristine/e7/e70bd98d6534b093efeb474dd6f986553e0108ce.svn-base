package base.web;

import javax.servlet.http.HttpSession;

import base.session.SessionContext;
import base.util.GetProperties;
import base.util.Page;
import base.util.PageHelper;
import manage.event.service.EventService;

public abstract class PaginationAction extends BaseAction {
	protected int page = 1;

	protected int pageSize = 10;
	protected int totalCount;
	protected String url;
	protected String param;
	protected String pageBar;
	protected EventService eventService;
	public static GetProperties properties = new GetProperties();

	protected int getStartPage() {
		Page pageObj = new Page(this.page, this.totalCount, this.pageSize,
				this.url, this.param);
		this.pageBar = PageHelper.getPageBar(pageObj);
		return pageObj.getStartOfPage();
	}

	public static GetProperties getProperties() {
		return properties;
	}

	public static void setProperties(GetProperties properties) {
		PaginationAction.properties = properties;
	}

	public int getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPage() {
		return this.page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getParam() {
		return this.param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getPageBar() {
		return this.pageBar;
	}

	public void setPageBar(String pageBar) {
		this.pageBar = pageBar;
	}

	public EventService getEventService() {
		return this.eventService;
	}

	public void setEventService(EventService eventService) {
		this.eventService = eventService;
	}
}