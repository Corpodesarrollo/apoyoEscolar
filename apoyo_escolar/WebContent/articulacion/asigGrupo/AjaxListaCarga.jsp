<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="paramsVO" class="articulacion.asigGrupo.vo.ParamsVO" scope="session"/>
<c:forEach begin="0" items="${requestScope.lAsignatura}" var="asignatura" >
</c:forEach>
						
<script>
	<c:if test="${sessionScope.ajaxCMD eq sessionScope.paramsVO.CMD_AJAX_GET_ASIGNATURA}">
	var combo = parent.document.frmNuevo.asignatura;
		combo.innerHTML = "";
		combo.options[0] = new Option("-- Seleccione una--",0);
		<c:forEach begin="0" items="${sessionScope.lAsignatura}" var="asignatura" varStatus="st">
			combo.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${asignatura.nombre}"/>', <c:out value="${asignatura.codigo}"/>);
			<c:if test="${asignatura.codigo==sessionScope.datosVO.especialidad}">
				combo.options[<c:out value="${st.count}"/>].setAttribute("selected", "selected");
			</c:if>
		</c:forEach>
	</c:if>
	<c:if test="${sessionScope.ajaxCMD eq sessionScope.paramsVO.CMD_AJAX_GET_GRUPOS}">
	var combo = parent.document.frmNuevo.grupo;
		combo.innerHTML = "";
		combo.options[0] = new Option("-- Seleccione una--",0);
		<c:forEach begin="0" items="${sessionScope.lGrupos}" var="grupo" varStatus="st">
			combo.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.gruNombre}"/>', <c:out value="${grupo.gruJerGrupo}"/>);
			<c:if test="${grupo.gruCodigo==sessionScope.datosVO.especialidad}">
				combo.options[<c:out value="${st.count}"/>].setAttribute("selected", "selected");
			</c:if>
		</c:forEach>
	</c:if>
	<c:if test="${requestScope.ajaxCMD eq 2}">
		parent.document.getElementById('frmListaCarga').contentDocument.location.reload(true);
	</c:if>
</script>
