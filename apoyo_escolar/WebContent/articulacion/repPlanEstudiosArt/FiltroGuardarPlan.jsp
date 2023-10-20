<jsp:useBean id="filtroPlan" class="articulacion.repPlanEstudiosArt.vo.FiltroBeanReportePlan" scope="session"/>
<jsp:setProperty name="filtroPlan" property="*"/>
<jsp:include page="/articulacion/repPlanEstudiosArt/ControllerRepPlanEstudiosArtFiltroSave.do?reporte=1&modulo=52"/>
