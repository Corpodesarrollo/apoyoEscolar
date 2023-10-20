<jsp:useBean id="filtroB" class="siges.reporte.beans.FiltroBeanEvaluacionLogro" scope="session"/><jsp:setProperty name="filtroB" property="*"/>
<jsp:include page="ControllerFiltroSave.do" flush="true">
<jsp:param name="reporte" value="2"/>
<jsp:param name="tipo" value="2"/>
<jsp:param name="modulo" value="15"/>
</jsp:include>
