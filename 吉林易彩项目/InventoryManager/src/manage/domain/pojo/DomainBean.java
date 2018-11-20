/*     */ package manage.domain.pojo;
/*     */ 
/*     */ import java.util.List;
/*     */ 
/*     */ public class DomainBean
/*     */ {
/*     */   private Integer domainId;
/*     */   private String domainCode;
/*     */   private String domainName;
/*     */   private Integer parentId;
/*     */   private String parentName;
/*     */   private String isParent;
/*     */   private String beantype;
/*     */   private Integer responsibleid;
/*     */   private String responsiblename;
/*     */   private String responsibleusername;
/*     */   private String responsibleUserType;
/*     */   private String responsiblephone;
/*     */   private boolean hasEqut;
/*     */   private boolean hasUser;
/*     */   private Boolean checked;
/*     */   private List<DomainBean> domains;
/*     */   private String manage;
/*     */   private String roleId;
/*     */   private String roleName;
/*     */ 
/*     */   public String getRoleId()
/*     */   {
/*  27 */     return this.roleId; }
/*     */ 
/*     */   public void setRoleId(String roleId) {
/*  30 */     this.roleId = roleId; }
/*     */ 
/*     */   public String getRoleName() {
/*  33 */     return this.roleName; }
/*     */ 
/*     */   public void setRoleName(String roleName) {
/*  36 */     this.roleName = roleName; }
/*     */ 
/*     */   public String getDomainCode() {
/*  39 */     return this.domainCode; }
/*     */ 
/*     */   public void setDomainCode(String domainCode) {
/*  42 */     this.domainCode = domainCode; }
/*     */ 
/*     */   public String getDomainName() {
/*  45 */     return this.domainName; }
/*     */ 
/*     */   public void setDomainName(String domainName) {
/*  48 */     this.domainName = domainName; }
/*     */ 
/*     */   public Integer getParentId() {
/*  51 */     return this.parentId; }
/*     */ 
/*     */   public void setParentId(Integer parentId) {
/*  54 */     this.parentId = parentId; }
/*     */ 
/*     */   public String getParentName() {
/*  57 */     return this.parentName; }
/*     */ 
/*     */   public void setParentName(String parentName) {
/*  60 */     this.parentName = parentName;
/*     */   }
/*     */ 
/*     */   public String getIsParent() {
/*  64 */     return this.isParent; }
/*     */ 
/*     */   public void setIsParent(String isParent) {
/*  67 */     this.isParent = isParent; }
/*     */ 
/*     */   public String getBeantype() {
/*  70 */     return this.beantype; }
/*     */ 
/*     */   public void setBeantype(String beantype) {
/*  73 */     this.beantype = beantype; }
/*     */ 
/*     */   public Integer getResponsibleid() {
/*  76 */     return this.responsibleid; }
/*     */ 
/*     */   public void setResponsibleid(Integer responsibleid) {
/*  79 */     this.responsibleid = responsibleid; }
/*     */ 
/*     */   public String getResponsiblename() {
/*  82 */     return this.responsiblename; }
/*     */ 
/*     */   public void setResponsiblename(String responsiblename) {
/*  85 */     this.responsiblename = responsiblename; }
/*     */ 
/*     */   public String getResponsiblephone() {
/*  88 */     return this.responsiblephone; }
/*     */ 
/*     */   public void setResponsiblephone(String responsiblephone) {
/*  91 */     this.responsiblephone = responsiblephone; }
/*     */ 
/*     */   public boolean isHasEqut() {
/*  94 */     return this.hasEqut; }
/*     */ 
/*     */   public boolean getHasEqut() {
/*  97 */     return this.hasEqut; }
/*     */ 
/*     */   public void setHasEqut(boolean hasEqut) {
/* 100 */     this.hasEqut = hasEqut; }
/*     */ 
/*     */   public boolean isHasUser() {
/* 103 */     return this.hasUser; }
/*     */ 
/*     */   public boolean getHasUser() {
/* 106 */     return this.hasUser; }
/*     */ 
/*     */   public void setHasUser(boolean hasUser) {
/* 109 */     this.hasUser = hasUser; }
/*     */ 
/*     */   public List<DomainBean> getDomains() {
/* 112 */     return this.domains; }
/*     */ 
/*     */   public void setDomains(List<DomainBean> domains) {
/* 115 */     this.domains = domains; }
/*     */ 
/*     */   public Integer getDomainId() {
/* 118 */     return this.domainId; }
/*     */ 
/*     */   public void setDomainId(Integer domainId) {
/* 121 */     this.domainId = domainId; }
/*     */ 
/*     */   public String getResponsibleusername() {
/* 124 */     return this.responsibleusername; }
/*     */ 
/*     */   public void setResponsibleusername(String responsibleusername) {
/* 127 */     this.responsibleusername = responsibleusername; }
/*     */ 
/*     */   public String getResponsibleUserType() {
/* 130 */     return this.responsibleUserType; }
/*     */ 
/*     */   public void setResponsibleUserType(String responsibleUserType) {
/* 133 */     this.responsibleUserType = responsibleUserType; }
/*     */ 
/*     */   public Boolean getChecked() {
/* 136 */     return this.checked; }
/*     */ 
/*     */   public void setChecked(Boolean checked) {
/* 139 */     this.checked = checked; }
/*     */ 
/*     */   public String getManage() {
/* 142 */     return this.manage; }
/*     */ 
/*     */   public void setManage(String manage) {
/* 145 */     this.manage = manage;
/*     */   }
/*     */ }

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     manage.domain.pojo.DomainBean
 * JD-Core Version:    0.5.3
 */