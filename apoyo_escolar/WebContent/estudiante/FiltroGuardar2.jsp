<jsp:useBean id="filtroEst" class="siges.estudiante.beans.FiltroBean" scope="session"/>
<jsp:setProperty name="filtroEst" property="*"/>
<jsp:include page="ControllerFiltroRegistrar.do"/>