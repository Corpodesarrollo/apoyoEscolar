	<jsp:useBean id="filtroreportes" class="siges.conflictoReportes.beans.ConflictoFiltro" scope="session"/>
	<jsp:setProperty name="filtroreportes" property="*"/>
	<jsp:include page="ControllerEditar.do"/>