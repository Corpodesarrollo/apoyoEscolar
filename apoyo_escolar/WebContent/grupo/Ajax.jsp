<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="paramsVO" class="siges.adminGrupo.vo.ParamsVO" scope="page"/>
<script>
<!--
	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("--Seleccione uno--","-99");
	}
	function borrar_check(combo){
		for(var i = 0; i<combo.length; i++) { 
			combo[i].checked=false;
			combo[i].disabled=true;
		}
	}
	//alert("CMD ");
	<c:choose>
	  <c:when test="${requestScope.ajaxParam==paramsVO.CMD_GRADO}">
			borrar_combo(parent.document.frmFiltro.filCodGrado);
		  	<c:if test="${!empty requestScope.listaGrados}">
		  	<c:forEach begin="0" items="${requestScope.listaGrados}" var="grado" varStatus="st">		  	
		  		parent.document.frmFiltro.filCodGrado.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grado.nombre}"/>','<c:out value="${grado.codigo}"/>',"selected");
					//parent.document.frmFiltro.filCodGrado.selectedIndex = '<c:out value="${st.count}"/>';
				</c:forEach>
			</c:if>
					//parent.document.frmFiltro.filCodGrado.options[<c:out value="-99"/>] = new Option('<c:out value="-- Todos --"/>','<c:out value="-99"/>',"selected");
					//parent.document.frmFiltro.filCodGrado.selectedIndex = '<c:out value="-99"/>';
		</c:when>
	  <c:when test="${requestScope.ajaxParam==paramsVO.CMD_JORN}">
	  //alert("jornadas");
		  borrar_combo(parent.document.frmFiltro.filCodJorn);
		  	<c:if test="${!empty requestScope.listaJornadas}">
		  	<c:forEach begin="0" items="${requestScope.listaJornadas}" var="grado" varStatus="st">		  	
		  		parent.document.frmFiltro.filCodJorn.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grado.nombre}"/>','<c:out value="${grado.codigo}"/>',"selected");
					//parent.document.frmFiltro.filCodJorn.selectedIndex = '<c:out value="${st.count}"/>';
				</c:forEach>
			</c:if>
					//parent.document.frmFiltro.filCodJorn.options[<c:out value="-99"/>] = new Option('<c:out value="-- Seleccione uno --"/>','<c:out value="-99"/>',"selected");
					//parent.document.frmFiltro.filCodJorn.selectedIndex = '<c:out value="-99"/>';
		</c:when>

		<c:when test="${requestScope.ajaxParam==paramsVO.CMD_ESPACIO}">
		  //alert("jornadas");
			  borrar_combo(parent.document.frmNuevo.codEspacio);
			  	<c:if test="${!empty requestScope.listaEspacios}">
			  	<c:forEach begin="0" items="${requestScope.listaEspacios}" var="esp" varStatus="st">		  	
			  		parent.document.frmNuevo.codEspacio.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${esp.nombre}"/>','<c:out value="${esp.codigo}"/>',"selected");
						//parent.document.frmNuevo.codEspacio.selectedIndex = '<c:out value="${st.count}"/>';
					</c:forEach>
				</c:if>
						//parent.document.frmNuevo.codEspacio.options[<c:out value="-99"/>] = new Option('<c:out value="-- Seleccione uno --"/>','<c:out value="-99"/>',"selected");
						//parent.document.frmNuevo.codEspacio.selectedIndex = '<c:out value="-99"/>';
			</c:when>
	</c:choose>
-->
</script>
