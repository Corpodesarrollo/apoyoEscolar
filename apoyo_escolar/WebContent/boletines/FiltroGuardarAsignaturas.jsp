<jsp:useBean id="filtroResumenAsignaturas" class="siges.boletines.beans.FiltroBeanResumenAsignaturas" scope="session"/><jsp:setProperty name="filtroResumenAsignaturas" property="*"/>
<jsp:include page="ControllerResumenFiltroSave.do" flush="true">
<jsp:param name="modulo" value="23"/>
<jsp:param name="tipo" value="2"/>
<jsp:param name="reporte_solicitado" value="2"/>
</jsp:include>