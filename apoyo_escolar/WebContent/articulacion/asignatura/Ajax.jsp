<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="paramsVO" class="articulacion.asignatura.vo.ParamsVO" scope="page"/>
<script>
<!--
	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("--Seleccione uno--","-99");
	}
	<c:choose>
	 	<c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_AREA}">
	  		borrar_combo(parent.document.frmFiltro.area);
		  	<c:forEach begin="0" items="${requestScope.ajaxArea}" var="area" varStatus="st">
		  		<c:if test="${area.codigo==sessionScope.datosVO.area}">
					parent.document.frmFiltro.area.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${area.nombre}"/>','<c:out value="${area.codigo}"/>','selected');
				</c:if>
				<c:if test="${area.codigo!=sessionScope.datosVO.area}">
					parent.document.frmFiltro.area.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${area.nombre}"/>','<c:out value="${area.codigo}"/>');
				</c:if>
			</c:forEach>
		</c:when>
		<c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_ASIGNATURA}">
	  		borrar_combo(parent.document.frmFiltro.asignatura);
		  	<c:forEach begin="0" items="${requestScope.listaAsignaturaPVO}" var="asignatura" varStatus="st">
		  		<c:if test="${asignatura.artAsigCodigo==sessionScope.datosVO.asignatura}">
					parent.document.frmFiltro.asignatura.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${asignatura.artAsigNombre}"/>','<c:out value="${asignatura.artAsigCodigo}"/>','selected');
				</c:if>
				<c:if test="${asignatura.artAsigCodigo!=sessionScope.datosVO.asignatura}">
					parent.document.frmFiltro.asignatura.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${asignatura.artAsigNombre}"/>','<c:out value="${asignatura.artAsigCodigo}"/>');
				</c:if>
			</c:forEach>
		</c:when>
	</c:choose>
//-->
</script>