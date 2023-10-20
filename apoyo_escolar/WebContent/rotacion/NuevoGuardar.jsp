<jsp:useBean id="rotacion" class="siges.rotacion.beans.Rotacion" scope="session"/><jsp:setProperty name="rotacion" property="*"/>
<jsp:useBean id="horario" class="siges.rotacion.beans.Horario" scope="session"/><jsp:setProperty name="horario" property="*"/>
<jsp:useBean id="docXGrupoVO" class="siges.rotacion.beans.DocXGrupoVO" scope="session"/><jsp:setProperty name="docXGrupoVO" property="*"/>
<jsp:useBean id="filtroDocXGrupoVO" class="siges.rotacion.beans.FiltroDocXGrupoVO" scope="session"/><jsp:setProperty name="filtroDocXGrupoVO" property="*"/>
<jsp:useBean id="espXGradoVO" class="siges.rotacion.beans.EspXGradoVO" scope="session"/><jsp:setProperty name="espXGradoVO" property="*"/>
<jsp:useBean id="filtroEspXGradoVO" class="siges.rotacion.beans.FiltroEspXGradoVO" scope="session"/><jsp:setProperty name="filtroEspXGradoVO" property="*"/>
<jsp:include page="ControllerEditar.do"/>