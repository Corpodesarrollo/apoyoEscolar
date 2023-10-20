<jsp:useBean id="filtroD" class="siges.reporte.beans.FiltroBeanEvaluacionArea" scope="session"/><jsp:setProperty name="filtroD" property="*"/>
<jsp:include page="ControllerFiltroSave.do" flush="true">
<jsp:param name="reporte" value="4"/>
<jsp:param name="tipo" value="4"/>
<jsp:param name="modulo" value="17"/>
</jsp:include>
