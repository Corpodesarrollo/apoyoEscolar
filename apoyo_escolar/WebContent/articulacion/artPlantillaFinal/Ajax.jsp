<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="paramsVO" class="articulacion.artPlantillaFinal.vo.ParamsVO" scope="page"/>
<script>
<!--
	
	function borrar_combo(combo) {
		for ( var i = combo.length - 1; i >= 0; i--) {
			if (navigator.appName == "Netscape")
				combo.options[i] = null;
			else
				combo.remove(i);
		}
		combo.options[0] = new Option("--Seleccione uno--", "-99");
	}
	<c:choose>
		<c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_GRUPO}">
			borrar_combo(parent.document.frmFiltro.plaGrupo);
			<c:forEach begin="0" items="${requestScope.ajaxGrupo}" var="grupo" varStatus="st">
				parent.document.frmFiltro.plaGrupo.options[<c:out value="${st.count}"/>] = new Option(
				'<c:out value="${grupo.nombre}"/>',
				'<c:out value="${grupo.codigo}"/>');
			</c:forEach>
		</c:when>
		<c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_ESPECIALIDAD}">
			borrar_combo(parent.document.frmFiltro.plaEspecialidad);
			<c:forEach begin="0" items="${requestScope.ajaxEspecialidad}" var="esp" varStatus="st">
			parent.document.frmFiltro.plaEspecialidad.options[<c:out value="${st.count}"/>] = new Option(
					'<c:out value="${esp.nombre}"/>', '<c:out value="${esp.codigo}"/>');
			</c:forEach>
		</c:when>
		<c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_ASIGNATURA}">
			borrar_combo(parent.document.frmFiltro.plaAsignatura);
			<c:forEach begin="0" items="${requestScope.ajaxAsignatura}" var="asignatura" varStatus="st">
				parent.document.frmFiltro.plaAsignatura.options[<c:out value="${st.count}"/>] = new Option(
					'<c:out value="${asignatura.asiNombre}"/>', '<c:out value="${asignatura.asiCodigo}"/>');
			</c:forEach>
		</c:when>
	</c:choose>
//-->
</script>