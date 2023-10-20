<jsp:useBean id="filtrobcc" class="siges.boletines.beans.FiltroBeanConstancias" scope="session"/><jsp:setProperty name="filtrobcc" property="*"/>
<jsp:include page="constancias.do" flush="true">
<jsp:param name="modulo" value="4"/>
</jsp:include>
