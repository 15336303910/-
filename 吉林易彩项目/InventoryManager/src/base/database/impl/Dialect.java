package base.database.impl;

public abstract interface Dialect
{
  public abstract boolean supportsLimit();

  public abstract String getMySqlLimit(String paramString, int paramInt1, int paramInt2);

  public abstract String getOracleLimit(String paramString, int paramInt1, int paramInt2);
}

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     base.database.impl.Dialect
 * JD-Core Version:    0.5.3
 */