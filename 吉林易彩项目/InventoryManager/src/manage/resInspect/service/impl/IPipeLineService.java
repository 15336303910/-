package manage.resInspect.service.impl;

public interface IPipeLineService {
	
	
	/**
	 * 得到管道段数据
	 * @param id
	 * @return
	 */
	public String getPipeSeg(String id);
	
	
	/**
	 * 得到井数据
	 * @param id
	 * @return
	 */
	public String getWellInfo(String id);
	
	
	/**
	 * 杆路段数据
	 * @param id
	 * @return
	 */
	public String getPoleSeg(String id);
	
	
	
	/**
	 *  根据id得到电杆数据
	 * @param id
	 * @return
	 */
	public String getPoleInfo(String id);

	/**
	 * 得到直埋段的数据
	 * @param id
	 * @return
	 */
	public String getBuriedPart(String id);
	
	/**
	 * 得到stone 的数据
	 * @param id
	 * @return
	 */
	public String getStoneInfo(String id);
}
