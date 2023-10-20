<%@page import = "java.io.FileInputStream"%>
<%@page import = "java.io.IOException"%>
<%@page import = "java.io.OutputStream"%>
<%@page import = "javax.servlet.ServletException"%>
<%@page import = "javax.servlet.http.HttpServlet"%>
<%@page import = "javax.servlet.http.HttpServletRequest"%>
<%@page import = "javax.servlet.http.HttpServletRequestWrapper"%>
<%@page import = "javax.servlet.http.HttpServletResponse"%>
<%@page import = "org.apache.poi.hssf.usermodel.HSSFCell"%>
<%@page import = "org.apache.poi.hssf.usermodel.HSSFRow"%>
<%@page import = "org.apache.poi.hssf.usermodel.HSSFSheet"%>
<%@page import = "org.apache.poi.hssf.usermodel.HSSFWorkbook"%>
<%@page import = "org.apache.poi.poifs.filesystem.POIFSFileSystem"%>
<%@page import = "java.util.Collection"%>;
<%@page import = "java.util.*"%>;
<% 
response.setContentType("application/vnd.ms-excel");
response.setHeader ("Content-disposition", "attachment; filename=informe.xls");
ServletContext context = (ServletContext) request.getSession().getServletContext();
String excel2 = context.getRealPath("/");
excel2 += "institucion/formatoc600 para diligenciar.xls";

POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(excel2));
HSSFWorkbook wb = new HSSFWorkbook(fs);
HSSFSheet sheet = wb.getSheetAt(0);
int l=3;
Object[] o;
Collection address = (Collection)request.getAttribute("EncaPgEg"); 
Iterator addressIt = address.iterator();
while(addressIt.hasNext()) {
	o=(Object[]) addressIt.next();
	HSSFRow row1 = sheet.getRow(l);
	HSSFCell b2 = row1.getCell((short)1);
	HSSFCell c2 = row1.getCell((short)6);
	HSSFCell d2 = row1.getCell((short)7);
	b2.setCellValue(Double.valueOf(o[0].toString()).doubleValue());
	c2.setCellValue(Double.valueOf(o[1].toString()).doubleValue());
	d2.setCellValue((String)o[2]);
}
sheet = wb.getSheetAt(4);
l=8;
Collection addresses = (Collection)request.getAttribute("HombresPgEg"); 
Iterator addressIter = addresses.iterator();
while(addressIter.hasNext()) {
	o=(Object[]) addressIter.next();
	HSSFRow row1 = sheet.getRow(l);
	HSSFCell b2 = row1.getCell((short)2);
	HSSFCell c2 = row1.getCell((short)3);
	HSSFCell d2 = row1.getCell((short)4);
	HSSFCell b3 = row1.getCell((short)5);
	HSSFCell c3 = row1.getCell((short)6);
	HSSFCell d3 = row1.getCell((short)7);
	HSSFCell b4 = row1.getCell((short)8);
	HSSFCell c4 = row1.getCell((short)9);
	HSSFCell d4 = row1.getCell((short)10);
	HSSFCell b5 = row1.getCell((short)11);
	HSSFCell c5 = row1.getCell((short)12);
	HSSFCell d5 = row1.getCell((short)13);
	HSSFCell b6 = row1.getCell((short)14);
	HSSFCell c6 = row1.getCell((short)15);
	HSSFCell d6 = row1.getCell((short)16);
	HSSFCell b7 = row1.getCell((short)17);
	HSSFCell c7 = row1.getCell((short)18);
	HSSFCell d7 = row1.getCell((short)19);
	b2.setCellValue(Double.valueOf(o[1].toString()).doubleValue());
	c2.setCellValue(Double.valueOf(o[2].toString()).doubleValue());
	d2.setCellValue(Double.valueOf(o[3].toString()).doubleValue());
	b3.setCellValue(Double.valueOf(o[4].toString()).doubleValue());
	c3.setCellValue(Double.valueOf(o[5].toString()).doubleValue());
	d3.setCellValue(Double.valueOf(o[6].toString()).doubleValue());
	b4.setCellValue(Double.valueOf(o[7].toString()).doubleValue());
	c4.setCellValue(Double.valueOf(o[8].toString()).doubleValue());
	d4.setCellValue(Double.valueOf(o[9].toString()).doubleValue());
	b5.setCellValue(Double.valueOf(o[10].toString()).doubleValue());
	c5.setCellValue(Double.valueOf(o[11].toString()).doubleValue());
	d5.setCellValue(Double.valueOf(o[12].toString()).doubleValue());
	b6.setCellValue(Double.valueOf(o[13].toString()).doubleValue());
	c6.setCellValue(Double.valueOf(o[14].toString()).doubleValue());
	d6.setCellValue(Double.valueOf(o[15].toString()).doubleValue());
	b7.setCellValue(Double.valueOf(o[16].toString()).doubleValue());
	c7.setCellValue(Double.valueOf(o[17].toString()).doubleValue());
	d7.setCellValue(Double.valueOf(o[18].toString()).doubleValue());
	l++;
}
Collection addresse = (Collection)request.getAttribute("MujeresPgEg"); 
Iterator addressIte = addresse.iterator();
l=36;
while(addressIte.hasNext()) {
	o=(Object[]) addressIte.next();
	HSSFRow row1 = sheet.getRow(l);
	HSSFCell b2 = row1.getCell((short)2);
	HSSFCell c2 = row1.getCell((short)3);
	HSSFCell d2 = row1.getCell((short)4);
	HSSFCell b3 = row1.getCell((short)5);
	HSSFCell c3 = row1.getCell((short)6);
	HSSFCell d3 = row1.getCell((short)7);
	HSSFCell b4 = row1.getCell((short)8);
	HSSFCell c4 = row1.getCell((short)9);
	HSSFCell d4 = row1.getCell((short)10);
	HSSFCell b5 = row1.getCell((short)11);
	HSSFCell c5 = row1.getCell((short)12);
	HSSFCell d5 = row1.getCell((short)13);
	HSSFCell b6 = row1.getCell((short)14);
	HSSFCell c6 = row1.getCell((short)15);
	HSSFCell d6 = row1.getCell((short)16);
	HSSFCell b7 = row1.getCell((short)17);
	HSSFCell c7 = row1.getCell((short)18);
	HSSFCell d7 = row1.getCell((short)19);
	b2.setCellValue(Double.valueOf(o[1].toString()).doubleValue());
	c2.setCellValue(Double.valueOf(o[2].toString()).doubleValue());
	d2.setCellValue(Double.valueOf(o[3].toString()).doubleValue());
	b3.setCellValue(Double.valueOf(o[4].toString()).doubleValue());
	c3.setCellValue(Double.valueOf(o[5].toString()).doubleValue());
	d3.setCellValue(Double.valueOf(o[6].toString()).doubleValue());
	b4.setCellValue(Double.valueOf(o[7].toString()).doubleValue());
	c4.setCellValue(Double.valueOf(o[8].toString()).doubleValue());
	d4.setCellValue(Double.valueOf(o[9].toString()).doubleValue());
	b5.setCellValue(Double.valueOf(o[10].toString()).doubleValue());
	c5.setCellValue(Double.valueOf(o[11].toString()).doubleValue());
	d5.setCellValue(Double.valueOf(o[12].toString()).doubleValue());
	b6.setCellValue(Double.valueOf(o[13].toString()).doubleValue());
	c6.setCellValue(Double.valueOf(o[14].toString()).doubleValue());
	d6.setCellValue(Double.valueOf(o[15].toString()).doubleValue());
	b7.setCellValue(Double.valueOf(o[16].toString()).doubleValue());
	c7.setCellValue(Double.valueOf(o[17].toString()).doubleValue());
	d7.setCellValue(Double.valueOf(o[18].toString()).doubleValue());
	l++;
}
OutputStream outu = response.getOutputStream();
wb.write(outu);
out.close();
%>