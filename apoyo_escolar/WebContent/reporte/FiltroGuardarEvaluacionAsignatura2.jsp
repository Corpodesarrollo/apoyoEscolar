<jsp:useBean id="filtroC" class="siges.reporte.beans.FiltroBeanEvaluacionAsignatura" scope="session"/><jsp:setProperty name="filtroC" property="*"/>
<jsp:include page="/reporte/ControllerFiltroEdit.do" flush="true">
<jsp:param name="reporte" value="3"/>
</jsp:include>




