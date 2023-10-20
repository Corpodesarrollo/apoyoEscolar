<jsp:useBean id="rotacion2" class="siges.rotacion2.beans.Rotacion2" scope="session"/><jsp:setProperty name="rotacion2" property="*"/>
<jsp:useBean id="validacion" class="siges.rotacion2.beans.Validacion" scope="session"/><jsp:setProperty name="validacion" property="*"/>
<jsp:useBean id="horario" class="siges.rotacion.beans.Horario" scope="session"/><jsp:setProperty name="horario" property="*"/>
<jsp:include page="ControllerEditar.do"/>