<jsp:useBean id="filtroGrupos" class="articulacion.repHorariosArt.vo.FiltroBeanReporteGrupos" scope="session"/>
<jsp:setProperty name="filtroGrupos" property="*"/>
<jsp:include page="/articulacion/repHorariosArt/ControllerRepHorariosArtFiltroSave.do?reporte=5&modulo=54"/>