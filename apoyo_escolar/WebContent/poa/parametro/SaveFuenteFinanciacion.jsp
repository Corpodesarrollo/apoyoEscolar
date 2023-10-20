<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="parametroFuenteFinanciacion" class="poa.parametro.vo.FuenteFinanciacionVO" scope="session"/>
<jsp:setProperty name="parametroFuenteFinanciacion" property="*"/>
<c:import url="/poa/parametro/Nuevo.do"/>