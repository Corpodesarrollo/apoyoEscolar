<jsp:useBean id="filtroGest" class="siges.ruta.vo.FiltroBeanGestVO" scope="session"/><jsp:setProperty name="filtroGest" property="*"/>
<jsp:include page="/ruta/reportes/ControllerReporteFiltroEdit.do">
<jsp:param name="tipo" value="2"/>
</jsp:include>


