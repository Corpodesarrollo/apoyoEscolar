<jsp:useBean id="filtroDescriptores" class="siges.reportePlan.beans.FiltroBeanReportesDescriptores" scope="session"/><jsp:setProperty name="filtroDescriptores" property="*"/>
<jsp:useBean id="reporteVO" class="siges.util.beans.ReporteVO" scope="session"/>
<jsp:setProperty name="reporteVO" property="*"/>
<jsp:include page="ReportesPlan.do" flush="true">
<jsp:param name="reporte" value="3"/>
</jsp:include>