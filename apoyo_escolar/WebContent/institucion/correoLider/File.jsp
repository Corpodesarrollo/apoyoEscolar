<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="paramsVO" class="siges.institucion.correoLider.beans.ParamsVO" scope="page"/>
<script>
<!--
	<c:choose>
	  <c:when test="${requestScope.fileParam==paramsVO.CMD_FILE_ADJUNTO}">
			parent.document.frmNuevo.corrBandera.value='<c:out value="${requestScope.fileBandera}"/>';
			parent.document.frmNuevo.corrAdjunto.value='<c:out value="${requestScope.fileRuta}"/>';
			parent.validadoFile();
		</c:when>
	</c:choose>
//-->
</script>