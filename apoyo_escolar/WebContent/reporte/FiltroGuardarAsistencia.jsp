<jsp:useBean id="filtroA" class="siges.reporte.beans.FiltroBeanAsistencia" scope="session"/><jsp:setProperty name="filtroA" property="*"/>
<jsp:include page="ControllerFiltroSave.do" flush="true">
<jsp:param name="reporte" value="1"/>
<jsp:param name="tipo" value="1"/>
<jsp:param name="modulo" value="14"/>
</jsp:include>