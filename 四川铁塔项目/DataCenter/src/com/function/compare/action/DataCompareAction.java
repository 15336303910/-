package com.function.compare.action;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.function.compare.model.CompareColumn;
import com.function.compare.model.CompareDetail;
import com.function.compare.model.CompareMonitor;
import com.function.compare.service.CompareColumnService;
import com.function.compare.service.CompareDetailService;
import com.function.compare.service.CompareMonitorService;
import com.function.compare.service.DoCompareService;
import com.function.dbmanage.model.BasicColumn;
import com.function.dbmanage.service.BasicColumnService;
@Controller("com.function.compare.action.DataCompareAction")
@RequestMapping(value="/dataCompareAction")
public class DataCompareAction{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private BasicColumnService basicColumnService;
	
	@Autowired
	private CompareDetailService compareDetailService;
	
	@Autowired
	private CompareColumnService compareColumnService;
	
	@Autowired
	private DoCompareService doCompareService;
	
	@Autowired
	private CompareMonitorService compareMonitorService;
	
	/*
	 * 	立即执行比对
	 * 
	 * */
	@RequestMapping("/compareRightNow.ilf")
	public void compareRightNow(
		@RequestParam String compareId,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true}");
		CompareMonitor monitor = null;
		try{
			System.out.println("=>Compare:================== Data Compare Job ===================");
			CompareDetail compareDetail = compareDetailService.selectOne(Integer.parseInt(compareId));
			/*
			 * 	缓存比对规则包含的<效用属性>关系：COMPARE_COLUMN,样例数据：
			 * 
			 * 	==========================================================================================================
			 * 
			 * 	ID     BELONG_COMPARE_RULE   IS_CONNECT_COLUMN   A_COLUMN_ID   A_COLUMN_NAME   Z_COLUMN_ID   Z_COLUMN_NAME
			 * 
			 * 	1016   87				     Y					 7937		   site_code       100041		 site_code
			 * 
			 *  1017   87					 N					 7939		   site_name       100042		 site_name
			 *  
			 *  ==========================================================================================================
			 * 
			 * */
			compareDetail.setCompareColumns(
				compareColumnService.selectSome("from CompareColumn where BELONG_COMPARE_RULE = "+compareId)
			);
			List<CompareColumn> compareColumns = compareDetail.getCompareColumns();
			for(int i=0;i<compareColumns.size();i++){
				CompareColumn compareColumn = compareColumns.get(i);
				BasicColumn basicColumnA = basicColumnService.selectModel(compareColumn.getA_COLUMN_ID());
				if(basicColumnA!=null){
					compareColumns.get(i).setA_COLUMN_ALIAS(basicColumnA.getCOLUMN_ALIAS());
				}
				BasicColumn basicColumnB = basicColumnService.selectModel(compareColumn.getZ_COLUMN_ID());
				if(basicColumnB!=null){
					compareColumns.get(i).setZ_COLUMN_ALIAS(basicColumnB.getCOLUMN_ALIAS());
				}
			}
			compareDetail.setCompareColumns(compareColumns);
			System.out.println("=>Compare:Job rule coded by '"+compareDetail.getID()+"' has started.");
			/*
			 * 	创建监控：COMPARE_MONITOR
			 * 
			 * */
			monitor = new CompareMonitor();
			monitor.setBELONG_COMPARE(compareDetail.getID());
			monitor.setSTART_DATE(new Date());
			monitor.setIS_FINISHED("N");
			monitor.setIS_FATAL("N");
			Integer nowMonitorCode = compareMonitorService.insertModel(monitor);
			monitor.setID(nowMonitorCode);
			/*
			 * 	核查数据不一致的情况
			 * 
			 * */
			if("Y".equals(compareDetail.getIS_UNIFORM())){
				System.out.println("=>Compare:Uniform data compare job begin.");
				Integer unUniformCount = doCompareService.doCompareUniform(
					monitor,
					compareDetail.getCompareColumns(),
					compareDetail.getA_TABLE_ID(),
					compareDetail.getZ_TABLE_ID(),
					request
				);
				System.out.println("=>Compare:Uniform data compare job finished. Find "+unUniformCount+" fatal records fully.");
				monitor.setNOT_UNIFORM(unUniformCount);
			}
			/*
			 * 	核查A端数据异常：
			 * 
			 * */
			if("Y".equals(compareDetail.getIS_A_FATUAL())){
				System.out.println("=>Compare:Unnormal data in A terminal.");
				/*
				 * 	[1]效用字段为空.
				 * 
				 * */
				Integer aNullValueCount = doCompareService.doCompare(nowMonitorCode,compareDetail.getA_TABLE_ID(),compareDetail.getCompareColumns(),"IS_A_NULL",request);
				/*
				 * 	[2]效用字段重复.
				 * 
				 * */
				Integer aNotUniqueCount = doCompareService.doCompare(nowMonitorCode,compareDetail.getA_TABLE_ID(),compareDetail.getCompareColumns(),"IS_A_NOT_UNIQUE",request);
				/*
				 * 	[3]求和.
				 * 
				 * */
				monitor.setA_FATUAL(aNullValueCount+aNotUniqueCount);
				System.out.println("=>Compare:Unnormal data in A terminal be finded.Find "+(aNullValueCount+aNotUniqueCount)+" records fully.");
				compareMonitorService.updateModel(monitor);
			}
			/*
			 * 	核查Z端数据异常.
			 * 
			 * */
			if("Y".equals(compareDetail.getIS_Z_FATUAL())){
				System.out.println("=>Compare:Unnormal data in Z terminal.");
				/*
				 * 	[1]效用字段为空.
				 * 
				 * */
				Integer zNullValueCount = doCompareService.doCompare(nowMonitorCode,compareDetail.getZ_TABLE_ID(),compareDetail.getCompareColumns(),"IS_Z_NULL",request);
				/*
				 * 	[2]效用字段重复.
				 * 
				 * */
				Integer zNotUniqueCount = doCompareService.doCompare(nowMonitorCode,compareDetail.getZ_TABLE_ID(),compareDetail.getCompareColumns(),"IS_Z_NOT_UNIQUE",request);
				/*
				 * 	[3]求和.
				 * 
				 * */
				monitor.setZ_FATUAL(zNullValueCount+zNotUniqueCount);
				System.out.println("=>Compare:Unnormal data in Z terminal be finded. Find "+(zNullValueCount+zNotUniqueCount)+" records fully.");
				compareMonitorService.updateModel(monitor);
			}
			/*
			 * 	核查仅A端有数据
			 * 
			 * */
			if("Y".equals(compareDetail.getIS_Z_ONLY())){
				System.out.println("=>Compare:Data only in A terminal.");
				Integer isAonly = doCompareService.doCompareSameDb(nowMonitorCode,compareDetail.getCompareColumns(),compareDetail.getA_TABLE_ID(),compareDetail.getZ_TABLE_ID(),"IS_A_ONLY");
				monitor.setA_ONLY(isAonly);
				System.out.println("=>Compare:Data only in A terminal be finded. Find "+isAonly+" records fully.");
				compareMonitorService.updateModel(monitor);
			}
			/*
			 * 	核查仅Z端有数据
			 * 
			 * */
			if("Y".equals(compareDetail.getIS_Z_ONLY())){
				System.out.println("=>Compare:Data only in Z terminal.");
				Integer isZonly = doCompareService.doCompareSameDb(nowMonitorCode,compareDetail.getCompareColumns(),compareDetail.getA_TABLE_ID(),compareDetail.getZ_TABLE_ID(),"IS_Z_ONLY");
				monitor.setZ_ONLY(isZonly);
				System.out.println("=>Compare:Data only in Z terminal be finded. Find "+isZonly+" records fully.");
				compareMonitorService.updateModel(monitor);
			}
			monitor.setFINISH_DATE(new Date());
			monitor.setIS_FINISHED("Y");
			compareMonitorService.updateModel(monitor);
			System.out.println("=>Compare:================== Data Compare done. ==================");
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("success",true);
			/*
			 * 	如果比对任务执行过程中出现问题.则更新监控状态.
			 * 
			 * */
			monitor.setIS_FATAL("Y");
			compareMonitorService.updateModel(monitor);
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 	错误类型获取
	 * 
	 * */
	@RequestMapping("/fatalDescribe.ilf")
	public void fatalDescribe(@RequestParam String belongMonitor,@RequestParam String fatalType,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true}");
		try{
			List<Map<String,Object>> fatalTypes = jdbcTemplate.queryForList("SELECT DISTINCT(PROBLEM_DESC) FROM COMPARE_DATA WHERE BELONG_MONITOR = "+belongMonitor+" AND PROBLEM_TYPE = '"+fatalType+"'");
			resultObject.put("options",fatalTypes);
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("success",false);
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
}
