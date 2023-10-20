<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="paramsVO" class="articulacion.artRotacion.vo.ParamsVO" scope="page"/>
<script>
<!--
	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("--Seleccione uno--","-99");
	}
		
	<c:choose>
	  <c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_ESTRUCTURA}">
	  		borrar_combo(parent.document.frmLista.filEstructura);
		  	<c:forEach begin="0" items="${requestScope.lEstructuraVO}" var="est" varStatus="st">
					parent.document.frmLista.filEstructura.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${est.nombre}"/>','<c:out value="${est.codigo}"/>');
				</c:forEach>
		</c:when>
	</c:choose>
//-->
</script>