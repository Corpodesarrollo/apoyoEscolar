<jsp:useBean id="admincursos" class="siges.admincursos.beans.AdminCursos" scope="session"/>
<jsp:setProperty name="admincursos" property="*"/>
<jsp:include page="ControllerEditar.do"/>