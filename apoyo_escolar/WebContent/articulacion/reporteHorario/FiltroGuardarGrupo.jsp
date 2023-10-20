<jsp:useBean id="filtro" class="articulacion.reporteHorario.beans.FiltroBeanReporte" scope="session"/>
<jsp:setProperty name="filtro" property="*"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/articulacion/reporteHorario/ControllerReporteHorarioFiltroSave.do?reporte=1&modulo=24"/>
