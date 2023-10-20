<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="paramsVO" class="articulacion.plantillaArticulacion.vo.ParamsVO" scope="page"/>
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
	  		borrar_combo(parent.document.frmFiltro.grupo);
		  	<c:forEach begin="0" items="${requestScope.ajaxGrupo}" var="grupo" varStatus="st">
			  	<c:if test="${grupo.codigo==sessionScope.filtroPlantillaArticulacionVO.grupo}">
						parent.document.frmFiltro.grupo.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>','selected');
						parent.document.frmFiltro.grupo.selectedIndex=<c:out value="${st.count}"/>;
				</c:if>
				<c:if test="${grupo.codigo!=sessionScope.filtroPlantillaArticulacionVO.grupo}">
						parent.document.frmFiltro.grupo.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>');
				</c:if>
				</c:forEach>
		</c:when>
	</c:choose>
//-->
</script>