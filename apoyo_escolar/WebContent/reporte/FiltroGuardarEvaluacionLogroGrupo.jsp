<jsp:useBean id="filtroE" class="siges.reporte.beans.FiltroBeanEvaluacionLogroGrupo" scope="session"/><jsp:setProperty name="filtroE" property="*"/>
<jsp:include page="ControllerFiltroSave.do" flush="true">
<jsp:param name="reporte" value="5"/>
<jsp:param name="modulo" value="18"/>
<jsp:param name="tipo" value="5"/>
</jsp:include>
