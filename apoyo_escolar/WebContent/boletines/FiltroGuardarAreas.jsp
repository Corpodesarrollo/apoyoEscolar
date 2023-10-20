<jsp:useBean id="filtroResumenAreas" class="siges.boletines.beans.FiltroBeanResumenAreas" scope="session"/><jsp:setProperty name="filtroResumenAreas" property="*"/>
<jsp:include page="ControllerResumenFiltroSave.do" flush="true">
<jsp:param name="tipo" value="1"/>
<jsp:param name="modulo" value="22"/>
<jsp:param name="reporte_solicitado" value="1"/>
</jsp:include>