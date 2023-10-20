<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="paramsVO" class="siges.observacion.vo.ParamsVO" scope="page"/>
<script>
<!--
	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("--Seleccione una--","-99");
	}
		
	<c:choose>
	  <c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_GRADO}">
	  		borrar_combo(parent.document.frmLista.obsGrado);
		  	<c:forEach begin="0" items="${requestScope.listaGrado}" var="grado" varStatus="st">
					parent.document.frmLista.obsGrado.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grado.nombre}"/>','<c:out value="${grado.codigo}"/>');
				</c:forEach>
		</c:when>
	  <c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_GRUPO}">
	  		borrar_combo(parent.document.frmLista.obsGrupo);
		  	<c:forEach begin="0" items="${requestScope.listaGrupo}" var="grupo" varStatus="st">
					parent.document.frmLista.obsGrupo.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>');
				</c:forEach>
		</c:when>
	</c:choose>
//-->
</script>