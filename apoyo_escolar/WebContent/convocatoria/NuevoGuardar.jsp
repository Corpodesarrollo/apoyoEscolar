	<jsp:useBean id="nuevoConvocatoria" class="siges.convocatoria.beans.Convocatoria" scope="session"/>
	<jsp:setProperty name="nuevoConvocatoria" property="*"/>
	<jsp:include page="ControllerEditar.do"/>