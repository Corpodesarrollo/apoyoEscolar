<jsp:useBean id="filtroC" class="siges.reporte.beans.FiltroBeanEvaluacionAsignatura" scope="session"/><jsp:setProperty name="filtroC" property="*"/>
<jsp:include page="ControllerFiltroSave.do" flush="true">
<jsp:param name="reporte" value="3"/>
<jsp:param name="tipo" value="3"/>
<jsp:param name="modulo" value="16"/>
</jsp:include>
