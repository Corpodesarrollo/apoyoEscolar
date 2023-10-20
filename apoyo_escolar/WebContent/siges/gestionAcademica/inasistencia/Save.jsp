<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="filtroInasistencia" class="siges.gestionAcademica.inasistencia.vo.FiltroInasistencia" scope="session"/>
<jsp:setProperty name="filtroInasistencia" property="*"/>
<c:import url="/inasistencia/Nuevo.do"/>
