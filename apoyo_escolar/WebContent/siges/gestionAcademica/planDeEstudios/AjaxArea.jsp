<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="paramsVO" class="siges.gestionAcademica.planDeEstudios.vo.ParamsVO" scope="page"/>
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
	
	<c:choose>
	  <c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_GRADO}">
	  		borrar_check(parent.document.frmNuevo.areGrado_);
	  		for(var i = 0; i<parent.document.frmNuevo.areGrado.length; i++) { 
			  	<c:forEach begin="0" items="${requestScope.listaGrado}" var="asig" varStatus="st">
						if(parent.document.frmNuevo.areGrado[i].value==<c:out value="${asig.codigo}"/>){
							parent.document.frmNuevo.areGrado_[i].disabled=false;
						}
					</c:forEach>
				}	
		</c:when>
	  <c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_AREA_BASE}">
	  		parent.document.frmNuevo.areNombre.value='<c:out value="${requestScope.listaAreaBase.nombre}"/>';
	  		//parent.document.frmNuevo.areAbreviatura.value='<c:out value="${requestScope.listaAreaBase.nombre}"/>';
		</c:when>
	</c:choose>
//-->
</script>