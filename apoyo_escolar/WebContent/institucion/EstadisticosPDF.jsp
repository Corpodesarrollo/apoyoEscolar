<%@page import = "net.sf.jasperreports.engine.JRException"%>
<%@page import = "net.sf.jasperreports.engine.JRExporterParameter"%>
<%@page import = "net.sf.jasperreports.engine.JasperFillManager"%>
<%@page import = "net.sf.jasperreports.engine.JasperPrint"%>
<%@page import = "net.sf.jasperreports.engine.JasperReport"%>
<%@page import = "net.sf.jasperreports.engine.export.JRXlsAbstractExporterParameter"%>
<%@page import = "net.sf.jasperreports.engine.export.JRXlsExporter"%>
<%@page import = "net.sf.jasperreports.engine.export.JRXlsExporterParameter"%>
<%@page import = "net.sf.jasperreports.engine.util.JRLoader"%>
<%@page import = "net.sf.jasperreports.engine.export.JRPdfExporter"%>
<%@page import = "net.sf.jasperreports.engine.export.JRPdfExporterParameter"%>
<%@page import = "java.sql.DriverManager"%>
<%@page import = "java.sql.Connection"%>
<%@page import = "java.util.HashMap"%>
<%@page import = "net.sf.jasperreports.engine.JasperRunManager"%>
<%@page import = "java.io.File"%>
<%@page import = "net.sf.jasperreports.engine.JRExporter"%>
<%@page import = "net.sf.jasperreports.engine.export.JRHtmlExporter"%>
<%@page import = "siges.dao.Cursor"%>
<% 
response.setContentType("application/pdf");
response.setHeader ("Content-disposition", "inline; filename=ReportesEstadisticosXdocente.pdf");
Connection conn;
Cursor cursor = new Cursor();
conn = cursor.getConnection();
//JasperReport reporte = (JasperReport) JRLoader.loadObject("C:/SED/Apoyo_Escolar/apoyo_escolar/WebContent/siges/gestionAdministrativa/cierreVigencia/reportes/cierrevigenciadet.jasper");
HashMap parametros = new HashMap();
parametros.put("inst",request.getAttribute("inst"));
ServletOutputStream outu = response.getOutputStream();
ServletContext context = (ServletContext) request.getSession().getServletContext();
JasperPrint jasperPrint = JasperFillManager.fillReport(context.getRealPath("/")+"institucion/EstadisticasXdocente.jasper", parametros,conn);
JRExporter exporter = new JRPdfExporter();
exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outu);
exporter.exportReport();
%>