<jsp:useBean id="filtroH" class="siges.reporte.beans.FiltroBeanEvaluacionTipoDescriptor" scope="session"/><jsp:setProperty name="filtroH" property="*"/>
<jsp:include page="ControllerFiltroSave.do" flush="true">
<jsp:param name="reporte" value="8"/>
<jsp:param name="modulo" value="36"/>
<jsp:param name="tipo" value="8"/>
</jsp:include>
