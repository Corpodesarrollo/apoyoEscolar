<jsp:useBean id="filtroc" class="siges.boletines.beans.FiltroBeanReports" scope="session"/><jsp:setProperty name="filtroc" property="*"/>
<jsp:include page="certificados.do" flush="true">
<jsp:param name="modulo" value="4"/>
</jsp:include>
