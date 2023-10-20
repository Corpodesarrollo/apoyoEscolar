<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="paramsVO" class="articulacion.asigTutor.vo.ParamsVO" scope="page"/>
<script>
<!--
	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("--Seleccione uno--","-99");
	}
	<c:choose>
	  <c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_DOCENTE}">
	  		borrar_combo(parent.document.frmFiltro.docente);
		  	<c:forEach begin="0" items="${sessionScope.ajaxDocente}" var="docente" varStatus="st">
			  	<c:if test="${docente.codigo==sessionScope.FilAsigTutorVO.docente}">
					parent.document.frmFiltro.docente.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${docente.nombre}"/>','<c:out value="${docente.codigo}"/>','selected');
				</c:if>
				<c:if test="${docente.codigo!=sessionScope.FilAsigTutorVO.docente}">
					parent.document.frmFiltro.docente.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${docente.nombre}"/>','<c:out value="${docente.codigo}"/>');
				</c:if>
			</c:forEach>
		</c:when>
	</c:choose>
//-->
</script>