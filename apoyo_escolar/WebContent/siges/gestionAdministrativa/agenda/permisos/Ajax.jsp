<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="paramsVO" class="siges.gestionAdministrativa.agenda.vo.ParamsVO" scope="page"/>

<script>

function borrar_combo(combo){
	for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
	combo.options[0] = new Option("--Seleccione uno--","-99");
}

	<c:choose>
		<c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_GRAD}">
			borrar_combo(parent.document.frmNuevoCircular.dato);
		  	<c:forEach begin="0" items="${requestScope.listaDatos}" var="linea" varStatus="st">
					parent.document.frmNuevoCircular.dato.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${linea.nombre}"/>','<c:out value="${linea.codigo}"/>');
			</c:forEach>
		</c:when>
	</c:choose>
	
	
	
</script>