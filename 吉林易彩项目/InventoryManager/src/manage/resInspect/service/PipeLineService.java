package manage.resInspect.service;

import org.springframework.jdbc.core.JdbcTemplate;

import manage.buriedPart.pojo.BuriedPartObj;
import manage.buriedPart.service.impl.IburiedPartService;
import manage.pipe.pojo.PipeSegmentInfoBean;
import manage.pipe.pojo.WellInfoBean;
import manage.pipe.service.PipeInfoService;
import manage.poleline.pojo.PoleInfoBean;
import manage.poleline.pojo.PolelineSegmentInfoBean;
import manage.poleline.service.PolelineInfoService;
import manage.resInspect.service.impl.IPipeLineService;
import manage.stone.pojo.StoneInfoBean;
import manage.stone.service.impl.IstoneInfoService;
import base.database.DataBase;
import base.util.JsonUtil;
import base.util.TextUtil;

/**
 * 
 * @author chenqp
 *
 */
public class PipeLineService extends DataBase implements IPipeLineService{
	private JdbcTemplate jdbcTemplate;
	//查询管道的数据
	private PipeInfoService pipeInfoService;
	//杆路数据
	private PolelineInfoService polelineInfoService;
	//直埋数据
	private IburiedPartService buriedPartServie;
	private IstoneInfoService stoneService;
	
	
	
	/**
	 * 得到管道段数据
	 * @param id
	 * @return
	 */
	public String getPipeSeg(String id){
		PipeSegmentInfoBean pipeSeg = new PipeSegmentInfoBean();
		if(TextUtil.isNotNull(id)){
			pipeSeg.setPipeSegmentId(Integer.parseInt(id));
			pipeSeg = this.pipeInfoService.getPipeSegmentInfoBean(pipeSeg);
		}
		return JsonUtil.getJsonString4JavaPOJO(pipeSeg);
		
	}
	/**
	 * 得到井数据
	 * @param id
	 * @return
	 */
	public String getWellInfo(String id){
		WellInfoBean well = new WellInfoBean();
		if(TextUtil.isNotNull(id)){
			well.setWellId(Integer.parseInt(id));
			well = this.pipeInfoService.getWell(well);
		}
		return JsonUtil.getJsonString4JavaPOJO(well);
	}
	
	
	/**
	 * 杆路段数据
	 * @param id
	 * @return
	 */
	public String getPoleSeg(String id){
		PolelineSegmentInfoBean poleSeg = new PolelineSegmentInfoBean();
		if(TextUtil.isNotNull(id)){
			poleSeg.setPoleLineSegmentId(Integer.parseInt(id));
			poleSeg = this.polelineInfoService.getPolelineSegmentInfoBean(poleSeg);
		}
		return JsonUtil.getJsonString4JavaPOJO(poleSeg);
	}
	
	
	/**
	 *  根据id得到电杆数据
	 * @param id
	 * @return
	 */
	public String getPoleInfo(String id){
		PoleInfoBean pole = new PoleInfoBean();
		if(TextUtil.isNotNull(id)){
			pole.setPoleId(Integer.parseInt(id));
			pole = this.polelineInfoService.getPoleInfoBean(pole);
		}
		return JsonUtil.getJsonString4JavaPOJO(pole);
	}
	
	/**
	 * 得到直埋段的数据
	 * @param id
	 * @return
	 */
	public String getBuriedPart(String id){
		BuriedPartObj obj = new BuriedPartObj();
		if(TextUtil.isNotNull(id)){
			obj.setId(Integer.parseInt(id));
			obj = this.buriedPartServie.getBuriedPartPojo(obj);
		}
		return JsonUtil.getJsonString4JavaPOJO(obj);
	}
	/**
	 * 得到stone 的数据
	 * @param id
	 * @return
	 */
	public String getStoneInfo(String id){
		StoneInfoBean obj = new StoneInfoBean();
		if(TextUtil.isNotNull(id)){
			obj.setStoneId(Integer.parseInt(id));
			obj = this.stoneService.getStoneObj(obj);
		}
		return JsonUtil.getJsonString4JavaPOJO(obj);
	}
	
	public IburiedPartService getBuriedPartServie() {
		return buriedPartServie;
	}
	public void setBuriedPartServie(IburiedPartService buriedPartServie) {
		this.buriedPartServie = buriedPartServie;
	}
	public IstoneInfoService getStoneService() {
		return stoneService;
	}
	public void setStoneService(IstoneInfoService stoneService) {
		this.stoneService = stoneService;
	}
	public PolelineInfoService getPolelineInfoService() {
		return polelineInfoService;
	}
	public void setPolelineInfoService(PolelineInfoService polelineInfoService) {
		this.polelineInfoService = polelineInfoService;
	}
	public PipeInfoService getPipeInfoService() {
		return pipeInfoService;
	}
	public void setPipeInfoService(PipeInfoService pipeInfoService) {
		this.pipeInfoService = pipeInfoService;
	}
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
