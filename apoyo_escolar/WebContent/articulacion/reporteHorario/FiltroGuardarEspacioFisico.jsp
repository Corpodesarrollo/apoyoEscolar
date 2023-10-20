<jsp:useBean id="filtroEsp" class="articulacion.reporteHorario.beans.FiltroBeanReporteEspacio" scope="session"/>
<jsp:setProperty name="filtroEsp" property="*"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/articulacion/reporteHorario/ControllerReporteHorarioFiltroSave.do?reporte=3&modulo=26"/>

