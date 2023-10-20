<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="paramsVO" class="siges.institucion.correoLider.beans.ParamsVO" scope="page"/>
<script>
<!--
	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("--Todos--","-99");
	}
		
	<c:choose>
	  <c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_INSTITUCION}">
	  		borrar_combo(parent.document.frmNuevo.corrInstitucion);
		  	<c:forEach begin="0" items="${requestScope.lInstitucion}" var="inst" varStatus="st">
					parent.document.frmNuevo.corrInstitucion.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${inst.nombre}"/>','<c:out value="${inst.codigo}"/>');
				</c:forEach>
		</c:when>
	  <c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_CARGO}">
	  		borrar_combo(parent.document.frmNuevo.corrCargo);
		  	<c:forEach begin="0" items="${requestScope.lCargo}" var="cargo" varStatus="st">
					parent.document.frmNuevo.corrCargo.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${cargo.nombre}"/>','<c:out value="${cargo.codigo}"/>');
				</c:forEach>
		</c:when>
	</c:choose>
//-->
</script>