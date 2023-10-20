<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="paramsVO" class="articulacion.artRotacion.vo.ParamsVO" scope="page"/>
<script>
<!--
	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("--Seleccione uno--","-99");
	}
	function borrar_comboTodos(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("--Todos--","-99");
	}
		
	<c:choose>
	  <c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_ESPECIALIDAD}">
	  		borrar_combo(parent.document.frmLista.filEspecialidad);
		  	<c:forEach begin="0" items="${requestScope.ajaxEspecialidad}" var="esp" varStatus="st">
					parent.document.frmLista.filEspecialidad.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${esp.nombre}"/>','<c:out value="${esp.codigo}"/>');
				</c:forEach>
		</c:when>
	  <c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_GRUPO}">
	  		borrar_comboTodos(parent.document.frmLista.filGrupo);
		  	<c:forEach begin="0" items="${requestScope.ajaxGrupo}" var="gru" varStatus="st">
					parent.document.frmLista.filGrupo.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${gru.nombre}"/>','<c:out value="${gru.codigo}"/>');
				</c:forEach>
		</c:when>
	  <c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_ASIGNATURA}">
	  		borrar_comboTodos(parent.document.frmLista.filAsignatura);
		  	<c:forEach begin="0" items="${requestScope.ajaxAsignatura}" var="asig" varStatus="st">
					parent.document.frmLista.filAsignatura.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${asig.nombre}"/>','<c:out value="${asig.codigo}"/>');
				</c:forEach>
		</c:when>
	  <c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_ASIGNATURA2}">
	  		borrar_comboTodos(parent.document.frmLista.filGrupo);
		  	<c:forEach begin="0" items="${requestScope.ajaxGrupo}" var="gru" varStatus="st">
					parent.document.frmLista.filGrupo.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${gru.nombre}"/>','<c:out value="${gru.codigo}"/>');
				</c:forEach>
		</c:when>
	</c:choose>

//-->
</script>