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
			borrar_combo(parent.document.frmNuevo.grado);
			borrar_combo(parent.document.frmNuevo.asignaturas);
		  	<c:forEach begin="0" items="${requestScope.listaGrado1}" var="linea" varStatus="st">
					parent.document.frmNuevo.grado.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${linea.nombre}"/>','<c:out value="${linea.codigo}"/>');
			</c:forEach>
		</c:when>
	</c:choose>	
	
	<c:choose>
		<c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_GRUP}">
			borrar_combo(parent.document.frmNuevo.asignaturas);
		  	<c:forEach begin="0" items="${requestScope.listaGrupo1}" var="linea" varStatus="st">
					parent.document.frmNuevo.grupo.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${linea.nombre}"/>','<c:out value="${linea.codigo}"/>');
			</c:forEach>
		</c:when>
	</c:choose>
	
	<c:choose>
		<c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_AREA}">
		  	<c:forEach begin="0" items="${requestScope.listaAsignaturas1}" var="linea" varStatus="st">
					parent.document.frmNuevo.asignaturas.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${linea.nombre}"/>','<c:out value="${linea.codigo}"/>');
			</c:forEach>
		</c:when>
	</c:choose>		
	
	
</script>