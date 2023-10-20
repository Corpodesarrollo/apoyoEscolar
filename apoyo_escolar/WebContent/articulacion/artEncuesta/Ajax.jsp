<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="paramsVO" class="articulacion.artEncuesta.vo.ParamsVO" scope="page"/>
<script>
<!--
	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) { 
		if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i);
		 }
		combo.options[0] = new Option("--Seleccione uno--","-99");
	}
	<c:choose>
	  <c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_GRUPO}">
	  		borrar_combo(parent.document.frmFiltro.plaGrupo);
		  	<c:forEach begin="0" items="${requestScope.ajaxGrupo}" var="grupo" varStatus="st">
						parent.document.frmFiltro.plaGrupo.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>');
				</c:forEach>
		</c:when>
	  <c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_GRADO}">
	  		borrar_combo(parent.document.frmFiltro.plaGrado);
		  	<c:forEach begin="0" items="${requestScope.ajaxGrado}" var="esp" varStatus="st">
						parent.document.frmFiltro.plaGrado.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${esp.nombre}"/>','<c:out value="${esp.codigo}"/>');
				</c:forEach>
		</c:when>	
		<c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_COLEGIO}">
	  		borrar_combo(parent.document.frmFiltro.plaInstitucion);
		  	<c:forEach begin="0" items="${requestScope.ajaxColegio}" var="col" varStatus="st">
						parent.document.frmFiltro.plaInstitucion.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${col.nombre}"/>','<c:out value="${col.codigo}"/>');
				</c:forEach>
		</c:when>
		<c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_SEDE}">
	  		borrar_combo(parent.document.frmFiltro.plaSede);
		  	<c:forEach begin="0" items="${requestScope.ajaxSede}" var="sed" varStatus="st">
						parent.document.frmFiltro.plaSede.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${sed.nombre}"/>','<c:out value="${sed.codigo}"/>');
				</c:forEach>
		</c:when>
		<c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_JORNADA}">
	  		borrar_combo(parent.document.frmFiltro.plaJornada);
		  	<c:forEach begin="0" items="${requestScope.ajaxJornada}" var="jor" varStatus="st">
						parent.document.frmFiltro.plaJornada.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${jor.nombre}"/>','<c:out value="${jor.codigo}"/>');
				</c:forEach>
		</c:when>
	</c:choose>
//-->
</script>