<jsp:useBean id="filtroAsig" class="articulacion.repPlanEstudiosArt.vo.FiltroBeanReporteAsignacion" scope="session"/>
<jsp:setProperty name="filtroAsig" property="*"/>
<jsp:include page="/articulacion/repPlanEstudiosArt/ControllerRepPlanEstudiosArtFiltroSave.do?reporte=2&modulo=53"/>
