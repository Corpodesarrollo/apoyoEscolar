<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="parametroTipoMeta" class="poa.parametro.vo.TipoMetaVO" scope="session"/>
<jsp:setProperty name="parametroTipoMeta" property="*"/>
<c:import url="/poa/parametro/Nuevo.do"/>