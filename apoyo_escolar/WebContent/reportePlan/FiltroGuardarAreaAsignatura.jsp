<jsp:useBean id="filtroAreas" class="siges.reportePlan.beans.FiltroBeanReportesAreas" scope="session"/><jsp:setProperty name="filtroAreas" property="*"/>
<jsp:useBean id="reporteVO" class="siges.util.beans.ReporteVO" scope="session"/>
<jsp:setProperty name="reporteVO" property="*"/>
<jsp:include page="ReportesPlan.do" flush="true">
<jsp:param name="reporte" value="1"/>  
</jsp:include> 