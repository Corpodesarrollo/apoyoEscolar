<jsp:useBean id="filtroLog" class="siges.boletines.beans.FiltroBeanFormulario" scope="session"/><jsp:setProperty name="filtroLog" property="*"/>
<jsp:include page="ControllerReporteFiltroSave.do" flush="true">
<jsp:param name="reporte" value="2"/>
<jsp:param name="tipo" value="4"/>
<jsp:param name="modulo" value="28"/>
</jsp:include>
