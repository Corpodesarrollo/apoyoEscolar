<jsp:useBean id="filtroResumenAreaAsig" class="siges.boletines.beans.FiltroBeanResumenAreaAsig" scope="session"/><jsp:setProperty name="filtroResumenAreaAsig" property="*"/>
<jsp:include page="ControllerResumenFiltroSave.do" flush="true">
<jsp:param name="tipo" value="32"/>
<jsp:param name="modulo" value="32"/>
<jsp:param name="reporte_solicitado" value="32"/>
</jsp:include>