/*    */ package manage.user.pojo;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ public class PowerinfoBean
/*    */ {
/*    */   private Integer id;
/*    */   private String powerstr;
/*    */   private String powername;
/*    */   private List<PowerinfoBean> powers;
/*    */   private String userId;
/*    */ 
/*    */   public Integer getId()
/*    */   {
/* 15 */     return this.id; }
/*    */ 
/*    */   public void setId(Integer id) {
/* 18 */     this.id = id; }
/*    */ 
/*    */   public String getPowerstr() {
/* 21 */     return this.powerstr; }
/*    */ 
/*    */   public void setPowerstr(String powerstr) {
/* 24 */     this.powerstr = powerstr; }
/*    */ 
/*    */   public String getPowername() {
/* 27 */     return this.powername; }
/*    */ 
/*    */   public void setPowername(String powername) {
/* 30 */     this.powername = powername; }
/*    */ 
/*    */   public List<PowerinfoBean> getPowers() {
/* 33 */     return this.powers; }
/*    */ 
/*    */   public void setPowers(List<PowerinfoBean> powers) {
/* 36 */     this.powers = powers; }
/*    */ 
/*    */   public String getUserId() {
/* 39 */     return this.userId; }
/*    */ 
/*    */   public void setUserId(String userId) {
/* 42 */     this.userId = userId;
/*    */   }
/*    */ }

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     manage.user.pojo.PowerinfoBean
 * JD-Core Version:    0.5.3
 */