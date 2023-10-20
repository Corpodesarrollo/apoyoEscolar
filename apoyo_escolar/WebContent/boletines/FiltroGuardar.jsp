<jsp:useBean id="filtrob" class="siges.boletines.beans.FiltroBeanReports" scope="session"/>
<jsp:setProperty name="filtrob" property="*"/>
<jsp:useBean id="reporteVO" class="siges.util.beans.ReporteVO" scope="session"/>
<jsp:setProperty name="reporteVO" property="*"/>
<jsp:include page="reportes.do?modulo=3"/>