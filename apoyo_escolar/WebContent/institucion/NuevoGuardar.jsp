<jsp:useBean id="nuevoInstitucion" class="siges.institucion.beans.Institucion" scope="session"/><jsp:setProperty name="nuevoInstitucion" property="*"/> 
<jsp:useBean id="nuevoJornada" class="siges.institucion.beans.Jornada" scope="session"/><jsp:setProperty name="nuevoJornada" property="*"/>
<jsp:useBean id="nuevoSede" class="siges.institucion.beans.Sede" scope="session"/><jsp:setProperty name="nuevoSede" property="*"/>
<jsp:useBean id="nuevoEspacio" class="siges.institucion.beans.Espacio" scope="session"/><jsp:setProperty name="nuevoEspacio" property="*"/>
<jsp:useBean id="nuevoNivel" class="siges.institucion.beans.Nivel" scope="session"/><jsp:setProperty name="nuevoNivel" property="*"/>
<jsp:useBean id="nuevoTransporte" class="siges.institucion.beans.Transporte" scope="session"/><jsp:setProperty name="nuevoTransporte" property="*"/>
<jsp:useBean id="nuevoCafeteria" class="siges.institucion.beans.Cafeteria" scope="session"/><jsp:setProperty name="nuevoCafeteria" property="*"/>
<jsp:useBean id="nuevoConflicto" class="siges.institucion.beans.ConflictoEscolar" scope="session"/><jsp:setProperty name="nuevoConflicto" property="*"/>
<jsp:include page="ControllerNuevoSave.do"/>