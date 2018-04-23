/*    */ package com.founder.utils;
/*    */
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import net.sf.cglib.beans.BeanGenerator;
/*    */ import net.sf.cglib.beans.BeanMap;
/*    需要引用cglib-nodep包    */
/*    */ public class DynamicBean
/*    */ {
/* 11 */   private Object object = null;
/* 12 */   private BeanMap beanMap = null;
/*    */
/*    */   public DynamicBean() {
/*    */   }
/*    */
/*    */   public DynamicBean(Map propertyMap) {
/* 18 */     this.object = generateBean(propertyMap);
/* 19 */     this.beanMap = BeanMap.create(this.object);
/*    */   }
/*    */   public void setValue(String property, Object value) {
/* 22 */     this.beanMap.put(property, value);
/*    */   }
/*    */   public Object getValue(String property) {
/* 25 */     return this.beanMap.get(property);
/*    */   }
/*    */   public Object getObject() {
/* 28 */     return this.object;
/*    */   }
/*    */
/*    */   private Object generateBean(Map propertyMap) {
/* 32 */     BeanGenerator generator = new BeanGenerator();
/* 33 */     Set keySet = propertyMap.keySet();
/* 34 */     for (Iterator i = keySet.iterator(); i.hasNext(); ) {
/* 35 */       String key = (String)i.next();
/* 36 */       generator.addProperty(key, (Class)propertyMap.get(key));
/*    */     }
/* 38 */     return generator.create();
/*    */   }
/*    */ }
