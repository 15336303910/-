package interfaces.pdainterface.file.pojo;

public class FileInfoBean
{
  private String filename;
  private Integer filetype;

  public String getFilename()
  {
    return this.filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }

  public Integer getFiletype() {
    return this.filetype;
  }

  public void setFiletype(Integer filetype) {
    this.filetype = filetype;
  }
}