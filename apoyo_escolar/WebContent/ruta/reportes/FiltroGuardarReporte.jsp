<jsp:useBean id="filtroNut" class="siges.ruta.vo.FiltroBeanVO" scope="session"/><jsp:setProperty name="filtroNut" property="*"/>
<jsp:include page="/ruta/reportes/ControllerReporteFiltroSave.do">
<jsp:param name="reporte" value="9"/>
<jsp:param name="tipo" value="1"/>
<jsp:param name="modulo" value="29"/>
</jsp:include>
