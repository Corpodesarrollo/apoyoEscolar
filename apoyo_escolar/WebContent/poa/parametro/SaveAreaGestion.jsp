<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="parametroAreaGestion" class="poa.parametro.vo.AreaGestionVO" scope="session"/>
<jsp:setProperty name="parametroAreaGestion" property="*"/>
<c:import url="/poa/parametro/Nuevo.do"/>