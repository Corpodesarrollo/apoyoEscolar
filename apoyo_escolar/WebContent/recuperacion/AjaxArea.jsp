<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="paramsVO" class="siges.recuperacion.vo.ParamsVO" scope="page"/>
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

	  <c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_GRUPO}">
	  		borrar_combo(parent.document.frmFiltro.filGrupo);
		  	<c:forEach begin="0" items="${requestScope.listaGrupo}" var="doc" varStatus="st">
					parent.document.frmFiltro.filGrupo.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${doc.nombre}"/>','<c:out value="${doc.codigo}"/>');
				</c:forEach>

	  		borrar_combo(parent.document.frmFiltro.filArea);
		  	<c:forEach begin="0" items="${requestScope.listaArea}" var="doc" varStatus="st">
					parent.document.frmFiltro.filArea.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${doc.nombre}"/>','<c:out value="${doc.codigo}"/>');
				</c:forEach>
		</c:when>
	</c:choose>
//-->
</script>