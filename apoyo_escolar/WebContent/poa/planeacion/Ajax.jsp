<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="paramsVO" class="poa.planeacion.vo.ParamsVO" scope="page"/>
<script>
<!--
	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("--Seleccione uno--","-99");
	}
	function borrar_combo2(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("0","0");
	}
	
	<c:choose>
		<c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_TIPOGASTO}">
			parent.document.frmNuevo.plaRubroGasto.innerHTML = "";
			parent.document.frmNuevo.plaRubroGasto.options[0] = new Option('-Seleccione Uno-','-99');
			<c:forEach begin="0" items="${requestScope.listaRubroGasto}" var="rubroGasto" varStatus="rg">
				parent.document.frmNuevo.plaRubroGasto.options[<c:out value="${rg.count}"/>] = new Option('<c:out value="${rubroGasto[0]}"/>','<c:out value="${rubroGasto[1]}"/>');
			</c:forEach>
		</c:when>
	
	  <c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_AREA}">
	  		borrar_combo(parent.document.frmNuevo.plaAreaGestion);
	  		borrar_combo2(parent.document.frmNuevo.plaPonderadores);
		  	<c:forEach begin="0" items="${requestScope.listaAreaGestion}" var="linea" varStatus="st">
					parent.document.frmNuevo.plaAreaGestion.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${linea.nombre}"/>','<c:out value="${linea.codigo}"/>');
					parent.document.frmNuevo.plaPonderadores.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${linea.padre2}"/>','<c:out value="${linea.padre}"/>');
				</c:forEach>
		</c:when>
	  <c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_LINEA}">
	  		borrar_combo(parent.document.frmNuevo.plaLineaAccion);
		  	<c:forEach begin="0" items="${requestScope.listaLineaAccion}" var="linea" varStatus="st">
					parent.document.frmNuevo.plaLineaAccion.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${linea.nombre}"/>','<c:out value="${linea.codigo}"/>');
				</c:forEach>
		</c:when>
	</c:choose>
//-->
</script>