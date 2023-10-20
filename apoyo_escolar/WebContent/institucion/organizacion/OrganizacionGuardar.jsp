<jsp:useBean id="consejoDirectivoVO" class="siges.institucion.organizacion.beans.ConsejoDirectivoVO" scope="session" /><jsp:setProperty name="consejoDirectivoVO" property="*"/>
<jsp:useBean id="consejoAcademicoVO" class="siges.institucion.organizacion.beans.ConsejoAcademicoVO" scope="session" /><jsp:setProperty name="consejoAcademicoVO" property="*"/>
<jsp:useBean id="liderVO" class="siges.institucion.organizacion.beans.LiderVO" scope="session" /><jsp:setProperty name="liderVO" property="*"/>
<jsp:useBean id="asociacionVO" class="siges.institucion.organizacion.beans.AsociacionVO" scope="session"/><jsp:setProperty name="asociacionVO" property="*"/>
<jsp:useBean id="asociacionIntegrantesVO" class="siges.institucion.organizacion.beans.AsociacionIntegrantesVO" scope="session"/><jsp:setProperty name="asociacionIntegrantesVO" property="*"/>
<jsp:include page="ControllerOrganizacionSave.do"/>