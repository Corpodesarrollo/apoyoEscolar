<jsp:useBean id="filtroLogros" class="siges.reportePlan.beans.FiltroBeanReportesLogros" scope="session"/>
<jsp:setProperty name="filtroLogros" property="*"/>
<jsp:useBean id="reporteVO" class="siges.util.beans.ReporteVO" scope="session"/>
<jsp:include page="ReportesPlan.do" flush="true">
<jsp:param name="reporte" value="2"/>
</jsp:include>