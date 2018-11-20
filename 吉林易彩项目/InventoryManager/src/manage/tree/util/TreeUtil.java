/*     */ package manage.tree.util;
/*     */ 
/*     */ import manage.domain.pojo.DomainBean;
/*     */ import manage.equt.pojo.EqutInfoBean;
/*     */ import manage.tree.pojo.Tree;
/*     */ 
/*     */ public class TreeUtil
/*     */ {
/*     */   public static Tree domainToTreeNoHref(DomainBean domain)
/*     */   {
/*  11 */     Tree domainTree = new Tree();
/*  12 */     if (domain != null) {
/*  13 */       domainTree.setId(domain.getDomainId().toString());
/*  14 */       domainTree.setText(domain.getDomainName());
/*  15 */       String isp = domain.getIsParent().trim();
/*  16 */       domainTree.setIcon("imgs/domain.gif");
/*     */       boolean isParent;
/*  18 */       if ("false".equals(isp))
/*  19 */         isParent = false;
/*     */       else {
/*  21 */         isParent = true;
/*     */       }
/*  23 */       domainTree.setIsParent(Boolean.valueOf(isParent));
/*  24 */       domainTree.setLeaf(Boolean.valueOf((!(isParent)) && (!(domain.isHasEqut()))));
/*  25 */       domainTree.setParentId(domain.getParentId().toString());
/*  26 */       domainTree.setDomainCode(domain.getDomainCode());
/*  27 */       domainTree.setBeanType("domain");
/*  28 */       domainTree.setNodeType("async");
/*  29 */       domainTree.setChildren(null);
/*     */     }
/*  31 */     return domainTree;
/*     */   }
/*     */ 
/*     */   public static Tree domainToTree(DomainBean domain)
/*     */   {
/*  36 */     Tree domainTree = new Tree();
/*  37 */     if (domain != null) {
/*  38 */       domainTree.setId(domain.getDomainId().toString());
/*  39 */       domainTree.setText(domain.getDomainName());
/*  40 */       String isp = domain.getIsParent().trim();
/*  41 */       domainTree.setIcon("imgs/domain.gif");
/*     */       boolean isParent;
/*  44 */       if ("false".equals(isp))
/*  45 */         isParent = true;
/*     */       else {
/*  47 */         isParent = false;
/*     */       }
/*  49 */       domainTree.setIsParent(Boolean.valueOf(!(isParent)));
/*  50 */       domainTree.setLeaf(Boolean.valueOf(isParent));
/*  51 */       domainTree.setParentId(domain.getParentId().toString());
/*  52 */       domainTree.setDomainCode(domain.getDomainCode());
/*  53 */       if (domain.getDomainCode().length() <= 4) {
/*  54 */         domainTree.setExpanded(Boolean.valueOf(true));
/*     */       }
/*  56 */       domainTree.setBeanType("domain");
/*     */     }
/*  58 */     return domainTree;
/*     */   }
/*     */ 
/*     */   public static Tree domainToTree2(DomainBean domain)
/*     */   {
/*  63 */     Tree domainTree = new Tree();
/*  64 */     if (domain != null) {
/*  65 */       domainTree.setId(domain.getDomainId().toString());
/*  66 */       domainTree.setName(domain.getDomainName());
/*  67 */       String isp = domain.getIsParent().trim();
/*  68 */       domainTree.setIcon("imgs/domain.gif");
/*     */       boolean isParent;
/*  71 */       if ("false".equals(isp))
/*  72 */         isParent = true;
/*     */       else {
/*  74 */         isParent = false;
/*     */       }
/*  76 */       domainTree.setIsParent(Boolean.valueOf(!(isParent)));
/*  77 */       domainTree.setLeaf(Boolean.valueOf(isParent));
/*  78 */       domainTree.setParentId(domain.getParentId().toString());
/*  79 */       domainTree.setDomainCode(domain.getDomainCode());
/*  80 */       if (domain.getDomainCode().length() <= 4) {
/*  81 */         domainTree.setExpanded(Boolean.valueOf(true));
/*     */       }
/*  83 */       domainTree.setBeanType("domain");
/*     */     }
/*  85 */     return domainTree;
/*     */   }
/*     */ 
/*     */   public static Tree domainToTreeHasChackBox(DomainBean domain)
/*     */   {
/*  96 */     Tree domainTree = new Tree();
/*  97 */     if (domain != null) {
/*  98 */       domainTree.setId(domain.getDomainId().toString());
/*  99 */       domainTree.setText(domain.getDomainName());
/* 100 */       String isp = domain.getIsParent();
/* 101 */       domainTree.setIcon("imgs/domain.gif");
/*     */       boolean isParent;
/* 103 */       if ("false".equals(isp))
/* 104 */         isParent = false;
/*     */       else {
/* 106 */         isParent = true;
/*     */       }
/* 108 */       domainTree.setIsParent(Boolean.valueOf(isParent));
/* 109 */       domainTree.setLeaf(Boolean.valueOf(!(isParent)));
/* 110 */       domainTree.setBeanType("domain");
/* 111 */       domainTree.setNodeType("async");
/* 112 */       domainTree.setChildren(null);
/* 113 */       domainTree.setDomainCode(domain.getDomainCode());
/* 114 */       domainTree.setChecked(domain.getChecked());
/*     */     }
/* 116 */     return domainTree;
/*     */   }
/*     */ 
/*     */   public static Tree equtToTree(EqutInfoBean equt) {
/* 120 */     Tree equtTree = new Tree();
/* 121 */     if (equt != null) {
/* 122 */       equtTree.setId(equt.getEid());
/* 123 */       equtTree.setText(((equt.getEname() == null) || ("".equals(equt.getEname()))) ? equt.getEcode() : equt.getEname());
/* 124 */       equtTree.setIcon("imgs/equt/equt.gif");
/* 125 */       equtTree.setHref("equt!getEqutTopoView.action?equtInfoBean.eid=" + equt.getEid());
/* 126 */       equtTree.setIsParent(Boolean.valueOf(false));
/* 127 */       equtTree.setLeaf(Boolean.valueOf(true));
/* 129 */       equtTree.setBeanType("equt");
/*     */     }
/* 131 */     return equtTree;
/*     */   }
/*     */ 
/*     */   public static Tree equtToTreeNoHref(EqutInfoBean equt)
/*     */   {
/* 136 */     Tree equtTree = new Tree();
/* 137 */     if (equt != null) {
/* 138 */       equtTree.setId(equt.getEid());
/* 139 */       equtTree.setText(((equt.getEname() == null) || ("".equals(equt.getEname()))) ? equt.getEcode() : equt.getEname());
/* 140 */       equtTree.setIcon("imgs/equt/equt.gif");
/* 141 */       equtTree.setIsParent(Boolean.valueOf(false));
/* 142 */       equtTree.setLeaf(Boolean.valueOf(true));
/* 144 */       equtTree.setBeanType("equt");
/*     */     }
/* 148 */     return equtTree;
/*     */   }
/*     */ 
/*     */   public static Tree equtToTreeHasChackBox(EqutInfoBean equt)
/*     */   {
/* 157 */     Tree equtTree = new Tree();
/* 158 */     if (equt != null) {
/* 159 */       equtTree.setId(equt.getEid());
/* 160 */       equtTree.setText(((equt.getEname() == null) || ("".equals(equt.getEname()))) ? equt.getEcode() : equt.getEname());
/* 161 */       equtTree.setIcon("imgs/equt/equt.gif");
/* 162 */       equtTree.setIsParent(Boolean.valueOf(false));
/* 163 */       equtTree.setLeaf(Boolean.valueOf(true));
/* 165 */       equtTree.setDomainCode(equt.getAreano());
/* 166 */       equtTree.setBeanType("equt");
/*     */     }
/* 168 */     return equtTree;
/*     */   }
/*     */ }