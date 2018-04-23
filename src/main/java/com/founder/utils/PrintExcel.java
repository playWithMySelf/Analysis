/*    */ package com.founder.utils;
/*    */

/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;

		 import net.sf.json.JSONArray;

/*    */ import org.apache.poi.hssf.usermodel.HSSFCell;
/*    */ import org.apache.poi.hssf.usermodel.HSSFCellStyle;
/*    */ import org.apache.poi.hssf.usermodel.HSSFFont;
/*    */ import org.apache.poi.hssf.usermodel.HSSFRichTextString;
/*    */ import org.apache.poi.hssf.usermodel.HSSFRow;
/*    */ import org.apache.poi.hssf.usermodel.HSSFSheet;
		 import org.apache.poi.hssf.usermodel.HSSFWorkbook;
/*    */
/*    */ public class PrintExcel
/*    */ {
/*    */   public void exportExcel(String title, String paramString1, String paramString2, JSONArray dynamicBeans, OutputStream out, String pattern)
/*    */   {
/* 30 */     HSSFWorkbook workbook = new HSSFWorkbook();
/*    */
/* 32 */     HSSFSheet sheet = workbook.createSheet(title);
/*    */
/* 34 */     sheet.setDefaultColumnWidth(20);
/*    */
/* 36 */     HSSFCellStyle style = workbook.createCellStyle();
/*    */
/* 38 */     style.setFillForegroundColor((short)40);
/* 39 */     style.setFillPattern((short)1);
/* 40 */     style.setBorderBottom((short)1);
/* 41 */     style.setBorderLeft((short)1);
/* 42 */     style.setBorderRight((short)1);
/* 43 */     style.setBorderTop((short)1);
/* 44 */     style.setAlignment((short)2);
/*    */
/* 46 */     HSSFFont font = workbook.createFont();
/* 47 */     font.setColor((short)20);
/* 48 */     font.setBoldweight((short)700);
/*    */
/* 50 */     style.setFont(font);
/*    */
/* 52 */     HSSFCellStyle style2 = workbook.createCellStyle();
/* 53 */     style2.setFillForegroundColor((short)43);
/* 54 */     style2.setFillPattern((short)1);
/* 55 */     style2.setBorderBottom((short)1);
/* 56 */     style2.setBorderLeft((short)1);
/* 57 */     style2.setBorderRight((short)1);
/* 58 */     style2.setBorderTop((short)1);
/* 59 */     style2.setAlignment((short)2);
/* 60 */     style2.setVerticalAlignment((short)1);
/*    */
/* 62 */     HSSFFont font2 = workbook.createFont();
/* 63 */     font2.setBoldweight((short)400);
/*    */
/* 65 */     style2.setFont(font2);
/*    */
/* 67 */     HSSFRow row = sheet.createRow(0);
/* 68 */     System.out.println("标题头:" + paramString2);
/* 69 */     String[] paramString2_arr = paramString2.split(",");
/* 70 */     for (int i = 0; i < paramString2_arr.length; i++) {
/* 71 */       HSSFCell cell = row.createCell(i);
/* 72 */       cell.setCellStyle(style);
/* 73 */       HSSFRichTextString text = new HSSFRichTextString(paramString2_arr[i]);
/* 74 */       cell.setCellValue(text);
/*    */     }
/* 76 */     System.out.println("数据库对应字段:" + paramString1);
/* 77 */     for (int i = 0; i < dynamicBeans.size(); i++) {
/* 78 */       row = sheet.createRow(i + 1);
/* 79 */       int j = 0;
/* 80 */       String[] paramString1_arr = paramString1.split(",");
/* 81 */       for (int k = 0; k < paramString1_arr.length; k++) {
/* 82 */         if (dynamicBeans.getJSONObject(i).get(paramString1_arr[k]) != null && ((dynamicBeans.getJSONObject(i)).get(paramString1_arr[k]) != "")) {
/* 83 */           row.createCell(j++).setCellValue((dynamicBeans.getJSONObject(i)).get(paramString1_arr[k]).toString());
/*    */         }
/*    */       }
/*    */     }
/*    */     try
/*    */     {
/* 89 */       workbook.write(out);
/*    */     } catch (IOException e) {
/* 91 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ }
