<jsp:useBean id="filtro" class="articulacion.repEstudiantesArt.vo.FiltroBeanReporteEstXTutor" scope="session"/>
<jsp:setProperty name="filtro" property="*"/>
<jsp:include page="/articulacion/repEstudiantesArt/ControllerRepEstudiantesArtFiltroSave.do?reporte=1&modulo=55"/>
