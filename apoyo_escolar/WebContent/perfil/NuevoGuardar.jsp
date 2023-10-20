	<jsp:useBean id="nuevoPerfil" class="siges.perfil.beans.Perfil" scope="session"/>
	<jsp:setProperty name="nuevoPerfil" property="*"/>
	<jsp:include page="ControllerPerfilNuevoSave.do"/>