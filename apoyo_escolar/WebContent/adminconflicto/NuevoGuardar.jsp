<jsp:useBean id="adminconflicto" class="siges.adminconflicto.beans.AdminConflicto" scope="session"/>
<jsp:setProperty name="adminconflicto" property="*"/>
<jsp:useBean id="filtroconflicto" class="siges.adminconflicto.beans.FiltroConflicto" scope="session"/>
<jsp:setProperty name="filtroconflicto" property="*"/>
<jsp:include page="ControllerEditar.do"/>