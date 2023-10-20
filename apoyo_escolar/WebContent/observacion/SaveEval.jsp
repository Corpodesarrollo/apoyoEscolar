<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="observacionAsignaturaVO" class="siges.observacion.vo.ObservacionAsignaturaVO" scope="session"/>
<jsp:setProperty name="observacionAsignaturaVO" property="*"/>
<jsp:useBean id="observacionEstudianteVO" class="siges.observacion.vo.ObservacionEstudianteVO" scope="session"/>
<jsp:setProperty name="observacionEstudianteVO" property="*"/>
<c:import url="/observacion/Nuevo.do"/>