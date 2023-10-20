<jsp:useBean id="filtroEst" class="siges.estudiante.beans.FiltroBean" scope="session"/>
<jsp:setProperty name="filtroEst" property="*"/>
<%String val = (String)request.getParameter("val");
if (val != null && val.equals("1")){
%>
<jsp:include page="ControllerFiltroRegistrar.do"/>
<%}else if (val != null && val.equals("2")){%>
<jsp:include page="ControllerFiltroRegistrarInactivar.do"/>
<%}else{%>
<jsp:include page="ControllerFiltroSave.do"/>
<%}%>