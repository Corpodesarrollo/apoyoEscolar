<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="paramsVO" class="articulacion.repHorariosArt.vo.ParamsVO" scope="page"/>
<script>
<!--
	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("--Todos--","-99");
	}
		
	<c:choose>
	  <c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_ESTUDIANTE}">
	  		borrar_combo(parent.document.frm.filEstudiante);
		  	<c:forEach begin="0" items="${requestScope.ajaxEstudiante}" var="doc" varStatus="st">
					parent.document.frm.filEstudiante.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${doc.nombre}"/>','<c:out value="${doc.codigo}"/>');
				</c:forEach>
		</c:when>	
		<c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_DOCENTE}">
	  		borrar_combo(parent.document.frm.docente);
		  	<c:forEach begin="0" items="${requestScope.ajaxDocente}" var="doc" varStatus="st">
					parent.document.frm.docente.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${doc.nombre}"/>','<c:out value="${doc.codigo}"/>');
				</c:forEach>
		</c:when> 
		<c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_ESPFISICO}">
	  		borrar_combo(parent.document.frm.espacio);
		  	<c:forEach begin="0" items="${requestScope.ajaxEspacio}" var="esp" varStatus="st">
					parent.document.frm.espacio.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${esp.nombre}"/>','<c:out value="${esp.codigo}"/>');
				</c:forEach>
		</c:when>  
		<c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_GRUPO}">
	  		borrar_combo(parent.document.frm.filGrupo);
		  	<c:forEach begin="0" items="${requestScope.ajaxGrupo}" var="gru" varStatus="st">
					parent.document.frm.filGrupo.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${gru.nombre}"/>','<c:out value="${gru.codigo}"/>');
				</c:forEach>
		</c:when> 
	</c:choose>

//-->
</script>