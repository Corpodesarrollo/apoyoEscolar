<jsp:useBean id="filtrolibro" class="siges.librodeNotas.beans.FiltroBeanLibro" scope="session"/><jsp:setProperty name="filtrolibro" property="*"/>
<jsp:include page="libros.do" flush="true">
<jsp:param name="modulo" value="8"/>
</jsp:include>