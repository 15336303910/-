package interfaces.pdainterface.hcgd;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import manage.gd.gdManage.service.impl.IhcgdManageService;
import base.web.InterfaceAction;
public class SupportDataAction extends InterfaceAction{

	private static final long serialVersionUID = 9174549601096057125L;
	private IhcgdManageService hcgdManageService;
	
	/*
	 * 	地市
	 * 
	 * */
	public void findCities()throws Exception{
		
	}
	
	/*
	 * 	区县
	 * 
	 * */
	public void findRegions()throws Exception{
		
	}
	
	public void printString(String string, String contentType) throws Exception {
		this.getResponse().setHeader("Cache-Control","no-cache");
		this.getResponse().setContentType(contentType);
		this.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter pw = this.getResponse().getWriter();
		pw.write(string);
		pw.close();
	}
	
	public Map<String,Object> findUniqueObject(String sql){
		JdbcTemplate jdbcTemplate = hcgdManageService.getTemplate();
		List<Map<String,Object>> reses = jdbcTemplate.queryForList(sql);
		if(reses.size()>0){
			return reses.get(0);
		}else{
			return null;
		}
	}
	
	public IhcgdManageService getHcgdManageService() {
		return hcgdManageService;
	}
	
	public void setHcgdManageService(IhcgdManageService hcgdManageService) {
		this.hcgdManageService = hcgdManageService;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
