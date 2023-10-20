<jsp:useBean id="filtroD" class="siges.reporte.beans.FiltroBeanEvaluacionArea" scope="session"/><jsp:setProperty name="filtroD" property="*"/>
<jsp:include page="/reporte/ControllerFiltroEdit.do" flush="true">
<jsp:param name="reporte" value="4"/>
</jsp:include>



