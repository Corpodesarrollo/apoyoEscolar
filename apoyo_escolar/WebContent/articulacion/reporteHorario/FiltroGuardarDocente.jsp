<jsp:useBean id="filtroDoc" class="articulacion.reporteHorario.beans.FiltroBeanReporteDocente" scope="session"/>
<jsp:setProperty name="filtroDoc" property="*"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/articulacion/reporteHorario/ControllerReporteHorarioFiltroSave.do?reporte=2&modulo=25"/>

