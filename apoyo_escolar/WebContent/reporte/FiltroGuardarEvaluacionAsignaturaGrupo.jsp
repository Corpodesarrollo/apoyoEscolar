<jsp:useBean id="filtroF" class="siges.reporte.beans.FiltroBeanEvaluacionAsignaturaGrupo" scope="session"/><jsp:setProperty name="filtroF" property="*"/>
<jsp:include page="ControllerFiltroSave.do" flush="true">
<jsp:param name="reporte" value="6"/>
<jsp:param name="modulo" value="19"/>
<jsp:param name="tipo" value="6"/>
<jsp:param name="modulo" value="19"/>
</jsp:include>
