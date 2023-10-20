<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="paramsVO" class="siges.rotacion.beans.ParamsVO" scope="page"/>
<script>
<!--
	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("--Seleccione uno--","-99");
	}
	function borrar_combo2(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
	}
		
	<c:choose>
	  <c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_DOCENTE_GRUPO_DOC0}">
	  		borrar_combo(parent.document.frmLista.filDocente);
		  	<c:forEach begin="0" items="${requestScope.ajaxDocente}" var="doc" varStatus="st">
					parent.document.frmLista.filDocente.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${doc.nombre}"/>','<c:out value="${doc.codigo}"/>');
				</c:forEach>
		</c:when>
	  <c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_DOCENTE_GRUPO_DOC}">
	  		borrar_combo(parent.document.frmNuevo.docGruDocente);
		  	<c:forEach begin="0" items="${requestScope.ajaxDocente}" var="doc" varStatus="st">
					parent.document.frmNuevo.docGruDocente.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${doc.nombre}"/>','<c:out value="${doc.codigo}"/>');
				</c:forEach>
		</c:when>
	  <c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_DOCENTE_GRUPO_ASIG}">
	  		borrar_combo(parent.document.frmNuevo.docGruAsignatura);
		  	<c:forEach begin="0" items="${requestScope.ajaxAsignatura}" var="asig" varStatus="st">
					parent.document.frmNuevo.docGruAsignatura.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${asig.nombre}"/>','<c:out value="${asig.codigo}"/>');
				</c:forEach>
		</c:when>
	  <c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_DOCENTE_GRUPO_GRA}">
	  		borrar_combo(parent.document.frmNuevo.docGruGrado);
		  	<c:forEach begin="0" items="${requestScope.ajaxGrado}" var="gra" varStatus="st">
					parent.document.frmNuevo.docGruGrado.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${gra.nombre}"/>','<c:out value="${gra.codigo}"/>');
				</c:forEach>
		</c:when>
	  <c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_DOCENTE_GRUPO_GRU}">
	  		borrar_combo2(parent.document.frmNuevo.docGruGrupo);
		  	<c:forEach begin="0" items="${requestScope.ajaxGrupo}" var="gru" varStatus="st">
					parent.document.frmNuevo.docGruGrupo.options[<c:out value="${st.index}"/>] = new Option('<c:out value="${gru.nombre}"/>','<c:out value="${gru.codigo}"/>');
				</c:forEach>
		</c:when>
	  <c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_ESPACIO_GRADO_ESP}">
	  		borrar_combo(parent.document.frmNuevo.espGraEspacio);
	  		borrar_combo2(parent.document.frmNuevo.espGraGrado);
		  	<c:forEach begin="0" items="${requestScope.ajaxEspacio}" var="esp" varStatus="st">
					parent.document.frmNuevo.espGraEspacio.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${esp.nombre}"/>','<c:out value="${esp.codigo}"/>');
				</c:forEach>
		  	<c:forEach begin="0" items="${requestScope.ajaxGrado}" var="gra" varStatus="st">
					parent.document.frmNuevo.espGraGrado.options[<c:out value="${st.index}"/>] = new Option('<c:out value="${gra.nombre}"/>','<c:out value="${gra.codigo}"/>');
				</c:forEach>
		</c:when>
	</c:choose>

//-->
</script>