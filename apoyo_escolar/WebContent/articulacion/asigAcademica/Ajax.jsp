<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="paramsVO" class="articulacion.asigAcademica.vo.ParamsVO" scope="page"/>
<script>
<!--
	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("--Seleccione uno--","-99");
	}
	<c:choose>
	  <c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_DOCENTE}">
	  		borrar_combo(parent.document.frmLista.docente);
		  	<c:forEach begin="0" items="${requestScope.lDocenteVO}" var="doc" varStatus="st">
					parent.document.frmLista.docente.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${doc.nombre}"/>','<c:out value="${doc.codigo}"/>');
				</c:forEach>
		</c:when>
	  <c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_ESPECIALIDAD}">
	  		borrar_combo(parent.document.frmNuevo.asigEspecialidad);
		  	<c:forEach begin="0" items="${requestScope.lEspecialidadVO}" var="esp" varStatus="st">
					parent.document.frmNuevo.asigEspecialidad.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${esp.nombre}"/>','<c:out value="${esp.codigo}"/>');
				</c:forEach>
		</c:when>
	  <c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_ASIGNATURA}">
	  		borrar_combo(parent.document.frmNuevo.asigAsignatura);
		  	<c:forEach begin="0" items="${requestScope.lAsignaturaVO}" var="asig" varStatus="st">
					parent.document.frmNuevo.asigAsignatura.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${asig.nombre}"/>','<c:out value="${asig.codigo}"/>');
				</c:forEach>
		</c:when>
	</c:choose>
//-->
</script>