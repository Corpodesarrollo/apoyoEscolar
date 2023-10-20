<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="parametroLineaAccion" class="poa.parametro.vo.LineaAccionVO" scope="session"/>
<jsp:setProperty name="parametroLineaAccion" property="*"/>
<jsp:useBean id="parametroFiltroLineaAccion" class="poa.parametro.vo.FiltroLineaAccionVO" scope="session"/>
<jsp:setProperty name="parametroFiltroLineaAccion" property="*"/>
<c:import url="/poa/parametro/Nuevo.do"/>