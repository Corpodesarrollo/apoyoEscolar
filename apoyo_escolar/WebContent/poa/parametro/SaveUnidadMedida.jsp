<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="parametroUnidadMedida" class="poa.parametro.vo.UnidadMedidaVO" scope="session"/>
<jsp:setProperty name="parametroUnidadMedida" property="*"/>
<c:import url="/poa/parametro/Nuevo.do"/>