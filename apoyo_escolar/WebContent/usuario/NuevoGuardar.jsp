	<jsp:useBean id="nuevoUsuario" class="siges.usuario.beans.Usuario" scope="session"/>
	<jsp:setProperty name="nuevoUsuario" property="*"/>
	<jsp:include page="ControllerUsuarioNuevoSave.do"/>