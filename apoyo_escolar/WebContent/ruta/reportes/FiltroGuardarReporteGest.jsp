<jsp:useBean id="filtroGest" class="siges.ruta.vo.FiltroBeanGestVO" scope="session"/><jsp:setProperty name="filtroGest" property="*"/>
<jsp:include page="/ruta/reportes/ControllerReporteFiltroSave.do">
<jsp:param name="reporte" value="10"/>
<jsp:param name="tipo" value="2"/>
<jsp:param name="modulo" value="30"/>
</jsp:include>
