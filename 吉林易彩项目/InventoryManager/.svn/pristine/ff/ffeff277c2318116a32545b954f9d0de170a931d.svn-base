package base.database.impl;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.engine.execution.SqlExecutor;
import com.ibatis.sqlmap.engine.impl.ExtendedSqlMapClient;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public abstract class BaseDaoiBatis extends SqlMapClientDaoSupport
{
  private SqlExecutor sqlExecutor;

  public SqlExecutor getSqlExecutor()
  {
    return this.sqlExecutor;
  }

  public void setSqlExecutor(SqlExecutor sqlExecutor) {
    this.sqlExecutor = sqlExecutor;
  }

  public void setEnableLimit(boolean enableLimit) {
    if (this.sqlExecutor instanceof LimitSqlExecutor)
      ((LimitSqlExecutor)this.sqlExecutor).setEnableLimit(enableLimit);
  }

  public void initialize() throws Exception
  {
    if (this.sqlExecutor != null) {
      SqlMapClient sqlMapClient = getSqlMapClientTemplate()
        .getSqlMapClient();
      if (sqlMapClient instanceof ExtendedSqlMapClient)
        ReflectUtil.setFieldValue(((ExtendedSqlMapClient)sqlMapClient)
          .getDelegate(), "sqlExecutor", SqlExecutor.class, 
          this.sqlExecutor);
    }
  }
}