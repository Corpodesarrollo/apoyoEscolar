<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="observacionPeriodoVO" class="siges.observacion.vo.ObservacionPeriodoVO" scope="session"/>
<jsp:setProperty name="observacionPeriodoVO" property="*"/>
<jsp:useBean id="observacionGrupoVO" class="siges.observacion.vo.ObservacionGrupoVO" scope="session"/>
<jsp:setProperty name="observacionGrupoVO" property="*"/>
<c:import url="/observacion/Nuevo.do"/>