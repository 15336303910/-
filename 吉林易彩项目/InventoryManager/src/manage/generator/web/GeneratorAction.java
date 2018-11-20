package manage.generator.web;

import base.exceptions.DataAccessException;
import base.util.ErrorMessage;
import base.web.PaginationAction;
import manage.equt.web.EqutAction;
import manage.generator.pojo.GeneratorInfoBean;
import manage.generator.pojo.HighFrequencySwitchingPowerSupplyInfoBean;
import manage.generator.pojo.StationBaseInfoBean;
import manage.generator.service.GeneratorService;
import org.apache.log4j.Logger;

public class GeneratorAction extends PaginationAction {
	private static final Logger log = Logger.getLogger(EqutAction.class);
	private ErrorMessage errorMessage;
	private GeneratorInfoBean generator;
	private StationBaseInfoBean stationBase;
	private GeneratorService generatorservice;
	private HighFrequencySwitchingPowerSupplyInfoBean hfs;
	private boolean success = false;
	private String dir;
	private String sort;
	private Integer start;
	private Integer limit;

	/**
	 * 得到所有站点
	 * @return
	 */
	public String getStation() {
		try {
			if (this.stationBase == null) {
				this.stationBase = new StationBaseInfoBean();
			}
			this.stationBase.setLimit(this.limit);
			this.stationBase.setStart(this.start);
			this.stationBase.setSort(this.sort);
			this.stationBase.setDir(this.dir);
			this.stationBase = this.generatorservice.getStationBase(this.stationBase);
		} catch (DataAccessException e) {
			log.error("GeneratorAction.getStation 获取信息异常...", e);
			this.errorMessage = new ErrorMessage();
			this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
			return "error";
		}

		return "getStation";
	}

	/**
	 * 修改站点
	 * @return
	 */
	public String updateStation() {
		try {
			int i = this.generatorservice.updateStationBase(this.stationBase);
			if (i == 1){
				this.success = true;
			}
		} catch (DataAccessException e) {
			log.error("GeneratorAction.getStation 获取信息异常...", e);
			this.errorMessage = new ErrorMessage();
			this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
			return "error";
		}
		return "success";
	}

	/**
	 * 删除站点
	 * @return
	 */
	public String deleteStation() {
		try {
			int i = this.generatorservice.deleteStationBase(this.stationBase);
			if (i == 1)
				this.success = true;
		} catch (DataAccessException e) {
			log.error("GeneratorAction.getStation 获取信息异常...", e);
			this.errorMessage = new ErrorMessage();
			this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
			return "error";
		}
		return "success";
	}

	public String getGeneratorInfo() {
		try {
			if (this.generator == null) {
				this.generator = new GeneratorInfoBean();
			}
			this.generator.setLimit(this.limit);
			this.generator.setStart(this.start);
			this.generator.setDir(this.dir);
			this.generator.setSort(this.sort);
			this.generator = this.generatorservice.getGenerator(this.generator);
		} catch (DataAccessException e) {
			log.error("GeneratorAction.getGeneratorInfo 获取信息异常...", e);
			this.errorMessage = new ErrorMessage();
			this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
			return "error";
		}
		return "getGeneratorInfo";
	}

	public String updateGenerator() {
		try {
			int i = this.generatorservice.updateGenerator(this.generator);
			if (i == 1)
				this.success = true;
		} catch (DataAccessException e) {
			log.error("GeneratorAction.updateGenerator 获取信息异常...", e);
			this.errorMessage = new ErrorMessage();
			this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
			return "error";
		}
		return "success";
	}

	public String deleteGenerator() {
		try {
			int i = this.generatorservice.deleteGenerator(this.generator);
			if (i == 1)
				this.success = true;
		} catch (DataAccessException e) {
			log.error("GeneratorAction.deleteGenerator 获取信息异常...", e);
			this.errorMessage = new ErrorMessage();
			this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
			return "error";
		}
		return "success";
	}


	public GeneratorInfoBean getGenerator() {
		return this.generator;
	}

	public void setGenerator(GeneratorInfoBean generator) {
		this.generator = generator;
	}

	public StationBaseInfoBean getStationBase() {
		return this.stationBase;
	}

	public void setStationBase(StationBaseInfoBean stationBase) {
		this.stationBase = stationBase;
	}

	public GeneratorService getGeneratorservice() {
		return this.generatorservice;
	}

	public void setGeneratorservice(GeneratorService generatorservice) {
		this.generatorservice = generatorservice;
	}

	public String getDir() {
		return this.dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getSort() {
		return this.sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public Integer getStart() {
		return this.start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return this.limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public ErrorMessage getErrorMessage() {
		return this.errorMessage;
	}

	public void setErrorMessage(ErrorMessage errorMessage) {
		this.errorMessage = errorMessage;
	}

	public boolean isSuccess() {
		return this.success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public HighFrequencySwitchingPowerSupplyInfoBean getHfs() {
		return this.hfs;
	}

	public void setHfs(HighFrequencySwitchingPowerSupplyInfoBean hfs) {
		this.hfs = hfs;
	}
}