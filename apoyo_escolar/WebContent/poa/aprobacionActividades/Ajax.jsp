<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="paramsVO" class="poa.aprobacionActividades.vo.ParamsVO" scope="page"/>
<script>
<!--
	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("--Seleccione uno--","-99");
	}
		
	<c:choose>
		<c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_TIPOGASTO}">
			parent.document.frmNuevo.rubroGasto.innerHTML = "";
			parent.document.frmNuevo.rubroGasto.options[0] = new Option('-Seleccione Uno-','-99');
			<c:forEach begin="0" items="${requestScope.listaRubroGasto}" var="vig" varStatus="rg">
				parent.document.frmNuevo.rubroGasto.options[<c:out value="${rg.count}"/>] = new Option('<c:out value="${vig[0]}"/>','<c:out value="${vig[1]}"/>');
			</c:forEach>
		</c:when>
	  <c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_LINEA}">
	  		borrar_combo(parent.document.frmNuevo.plaLineaAccion);
		  	<c:forEach begin="0" items="${requestScope.listaLineaAccion}" var="linea" varStatus="st">
					parent.document.frmNuevo.plaLineaAccion.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${linea.nombre}"/>','<c:out value="${linea.codigo}"/>');
				</c:forEach>
		</c:when>
	  <c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_COLEGIO}">
	  		borrar_combo(parent.document.frmFiltro.filInstitucion);
		  	<c:forEach begin="0" items="${requestScope.listaColegio}" var="linea" varStatus="st">
					parent.document.frmFiltro.filInstitucion.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${linea.nombre}"/>','<c:out value="${linea.codigo}"/>');
				</c:forEach>
		</c:when>
	</c:choose>
//-->
</script>