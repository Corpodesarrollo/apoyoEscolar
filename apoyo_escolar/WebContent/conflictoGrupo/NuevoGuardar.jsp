<jsp:useBean id="nuevoConflictoGrupo" class="siges.conflictoGrupo.beans.ConflictoGrupo" scope="session"/><jsp:setProperty name="nuevoConflictoGrupo" property="*"/>
<jsp:useBean id="nuevoConflictoFiltro" class="siges.conflictoGrupo.beans.ConflictoFiltro" scope="session"/><jsp:setProperty name="nuevoConflictoFiltro" property="*"/>
<jsp:include page="ControllerEditar.do"/>