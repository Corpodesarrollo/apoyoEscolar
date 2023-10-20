<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="paramsVO" class="siges.gestionAcademica.inasistencia.vo.ParamVO" scope="page"/>
<script>
<!--
	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("--Todos--","-99");
	}
		
	<c:choose>
	  <c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_ASIGNATURA}">
	  		borrar_combo(parent.document.frmFiltro.filAsignatura);
		  	<c:forEach begin="0" items="${requestScope.lAsignatura}" var="asig" varStatus="st">
					parent.document.frmFiltro.filAsignatura.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${asig.nombre}"/>','<c:out value="${asig.codigo}"/>');
				</c:forEach>
		</c:when>
	</c:choose>
//-->
</script>