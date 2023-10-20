<jsp:useBean id="filtroG" class="siges.reporte.beans.FiltroBeanEvaluacionAreaGrupo" scope="session"/><jsp:setProperty name="filtroG" property="*"/>
<jsp:include page="ControllerFiltroSave.do" flush="true">
<jsp:param name="reporte" value="7"/>
<jsp:param name="tipo" value="7"/>
<jsp:param name="modulo" value="35"/>
</jsp:include>
