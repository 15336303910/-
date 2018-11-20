package interfaces.pdainterface.pipe.service;

import base.exceptions.DataAccessException;
import java.util.List;

import manage.pipe.pojo.PipeSegCustomInfoBean;
import manage.pipe.pojo.WellCustomInfoBean;

public abstract interface PDAPipeCustomService {
	
	/**
	 * 获取井信息
	 * 
	 * @param paramWellInfoBean
	 * @return
	 * @throws DataAccessException
	 */
	public abstract List<WellCustomInfoBean> getWellCustom(WellCustomInfoBean well)
		throws DataAccessException;

    /**
     * 增加井
     * @param paramWellInfoBean
     * @return
     * @throws DataAccessException
     */
    public abstract Integer insertWellCustom(WellCustomInfoBean well)
        throws DataAccessException;

    /**
     * 更新井
     * @param paramWellInfoBean
     * @return
     * @throws DataAccessException
     */
    public abstract Integer updateWellCustom(WellCustomInfoBean well)
    	throws DataAccessException;

    /**
     * 删除井
     * @param paramWellInfoBean
     * @return
     * @throws DataAccessException
     */
    public abstract Integer deleteWellCustom(WellCustomInfoBean well)
    	throws DataAccessException;
    
    /**
     * 得到管道段长度
     * @param pipeseg
     * @return
     */
    public PipeSegCustomInfoBean setPipeSegLength(PipeSegCustomInfoBean pipeseg);
    
    /**
     * 增加管道段
     * @param pipeseg
     * @return
     * @throws DataAccessException
     */
    public Integer insertPipesegCustom(PipeSegCustomInfoBean pipeseg)
    	throws DataAccessException;
    
    /**
     * 获取管道段
     * @param pipeseg
     * @return
     * @throws DataAccessException
     */
    public List<PipeSegCustomInfoBean> getPipeSegCustom(PipeSegCustomInfoBean pipeseg)
    	throws DataAccessException;
    
    /**
     * 更新管道段
     * @param pipeseg
     * @return
     * @throws DataAccessException
     */
    public Integer updatePipeSegCustom(PipeSegCustomInfoBean pipeseg)
    	throws DataAccessException;
    
    /**
     * 删除管道段
     * @param pipeseg
     * @return
     * @throws DataAccessException
     */
    public Integer deletePipeSegCustom(PipeSegCustomInfoBean pipeseg)
    	throws DataAccessException;
}
