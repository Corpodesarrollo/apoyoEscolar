<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="filtroRegNotasVO" class="siges.gestionAcademica.regNotasVigencia.vo.FiltroRegNotasVO" scope="session"/>
<jsp:setProperty name="filtroRegNotasVO" property="*"/>
<c:import url="/siges/gestionAcademica/RegNotasVigencia/RegNotas.do"/>
