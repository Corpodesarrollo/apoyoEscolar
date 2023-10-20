<jsp:useBean id="nuevoConflicto" class="siges.conflicto.beans.ConflictoEscolar" scope="session"/><jsp:setProperty name="nuevoConflicto" property="*"/>
<jsp:useBean id="nuevoTipo" class="siges.conflicto.beans.TipoConflicto" scope="session"/><jsp:setProperty name="nuevoTipo" property="*"/>
<jsp:useBean id="nuevoResolucion" class="siges.conflicto.beans.ResolucionConflictos" scope="session"/><jsp:setProperty name="nuevoResolucion" property="*"/>
<jsp:useBean id="nuevoMedidas" class="siges.conflicto.beans.MedidasInst" scope="session"/><jsp:setProperty name="nuevoMedidas" property="*"/>
<jsp:useBean id="nuevoProyectos" class="siges.conflicto.beans.Proyectos" scope="session"/><jsp:setProperty name="nuevoProyectos" property="*"/>
<jsp:useBean id="nuevoInfluencia" class="siges.conflicto.beans.InfluenciaConflictos" scope="session"/><jsp:setProperty name="nuevoInfluencia" property="*"/>
<jsp:include page="ControllerEditar.do"/>