<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="parametroProgramacionFechas" class="poa.parametro.vo.ProgramacionFechasVO" scope="session"/>
<jsp:setProperty name="parametroProgramacionFechas" property="*"/>
<c:import url="/poa/parametro/Nuevo.do"/>