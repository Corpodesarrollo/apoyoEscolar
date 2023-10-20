<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="paramsVO" class="siges.indicadores.vo.ParamsVO" scope="page"/>
<script>
<!--
	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("--Seleccione uno--","-99");
	}
	<c:choose>
	  <c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_GRADO}">
	  		borrar_combo(parent.document.frmFiltro.filGrado);
		  	<c:forEach begin="0" items="${requestScope.listaGrado}" var="asig" varStatus="st">
					parent.document.frmFiltro.filGrado.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${asig.nombre}"/>','<c:out value="${asig.codigo}"/>');
				</c:forEach>
		</c:when>
	  <c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_ASIGNATURA}">
	  		borrar_combo(parent.document.frmFiltro.filAsignatura);
		  	<c:forEach begin="0" items="${requestScope.listaAsignatura}" var="asig" varStatus="st">
					parent.document.frmFiltro.filAsignatura.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${asig.nombre}"/>','<c:out value="${asig.codigo}"/>');
				</c:forEach>
		</c:when>
	  <c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_DOCENTE}">
	  		borrar_combo(parent.document.frmFiltro.filDocente);
		  	<c:forEach begin="0" items="${requestScope.listaDocente}" var="doc" varStatus="st">
					parent.document.frmFiltro.filDocente.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${doc.nombre}"/>','<c:out value="${doc.codigo}"/>');
				</c:forEach>
		</c:when>

	  <c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_GRADO2}">
	  		borrar_combo(parent.document.frmNuevo.logGrado);
		  	<c:forEach begin="0" items="${requestScope.listaGrado}" var="asig" varStatus="st">
					parent.document.frmNuevo.logGrado.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${asig.nombre}"/>','<c:out value="${asig.codigo}"/>');
				</c:forEach>
		</c:when>
	  <c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_ASIGNATURA2}">
	  		borrar_combo(parent.document.frmNuevo.logAsignatura);
		  	<c:forEach begin="0" items="${requestScope.listaAsignatura}" var="asig" varStatus="st">
					parent.document.frmNuevo.logAsignatura.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${asig.nombre}"/>','<c:out value="${asig.codigo}"/>');
				</c:forEach>
		</c:when>
	  <c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_DOCENTE2}">
	  		borrar_combo(parent.document.frmNuevo.logDocente);
		  	<c:forEach begin="0" items="${requestScope.listaDocente}" var="doc" varStatus="st">
					parent.document.frmNuevo.logDocente.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${doc.nombre}"/>','<c:out value="${doc.codigo}"/>');
				</c:forEach>
		</c:when>
	</c:choose>
//-->
</script>