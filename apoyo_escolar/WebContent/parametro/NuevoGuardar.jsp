<jsp:useBean id="parametros" class="siges.parametro.beans.Parametros" scope="session"/>
<jsp:setProperty name="parametros" property="*"/>
<jsp:include page="ControllerParametroFiltro.do"/>