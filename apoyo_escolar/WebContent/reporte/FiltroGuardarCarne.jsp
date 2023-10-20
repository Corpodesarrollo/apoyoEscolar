<jsp:useBean id="filtroCarne" class="siges.reporte.beans.FiltroBeanCarne" scope="session"/><jsp:setProperty name="filtroCarne" property="*"/>
<jsp:include page="ControllerCarneFiltroSave.do" flush="true">
<jsp:param name="modulo" value="20"/>
</jsp:include>


