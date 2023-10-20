<jsp:useBean id="filtroComision" class="siges.comision.beans.FiltroBeanCom" scope="session"/>
<jsp:setProperty name="filtroComision" property="*"/>
<jsp:include page="ControllerFiltroSave.do"/>