<jsp:useBean id="filtroConsultaAsignaturasPerdidas" class="siges.boletines.beans.FiltroConsultaAsignaturasPerdidas" scope="session"/><jsp:setProperty name="filtroConsultaAsignaturasPerdidas" property="*"/>
<jsp:include page="ControllerResumenFiltroSave.do" flush="true">
<jsp:param name="tipo" value="3"/>
<jsp:param name="modulo" value="24"/>
<jsp:param name="reporte_solicitado" value="3"/>
</jsp:include>