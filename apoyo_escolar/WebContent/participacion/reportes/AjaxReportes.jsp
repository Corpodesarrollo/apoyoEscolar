<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="paramsVO" class="participacion.reportes.vo.ParamsVO" scope="page"/>
<script>
<!--
	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("--Seleccione uno--","-99");
	}
		
	<c:choose>
	  <c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_INSTANCIA0}">
	  		borrar_combo(parent.document.frmFiltro.filInstancia);
		  	<c:forEach begin="0" items="${requestScope.listaInstanciaVO}" var="linea" varStatus="st">
					parent.document.frmFiltro.filInstancia.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${linea.nombre}"/>','<c:out value="${linea.codigo}"/>');
			</c:forEach>
		</c:when>
	  <c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_RANGO0}">
	  		borrar_combo(parent.document.frmFiltro.filRango);
	  		borrar_combo(parent.document.frmFiltro.filRol);
		  	<c:forEach begin="0" items="${requestScope.listaRangoVO}" var="linea" varStatus="st">
					parent.document.frmFiltro.filRango.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${linea.nombre}"/>','<c:out value="${linea.codigo}"/>');
			</c:forEach>
		  	<c:forEach begin="0" items="${requestScope.listaRolVO}" var="linea" varStatus="st">
				parent.document.frmFiltro.filRol.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${linea.nombre}"/>','<c:out value="${linea.codigo}"/>');
			</c:forEach>
		</c:when>
	  <c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_COLEGIO0}">
	  		borrar_combo(parent.document.frmFiltro.filColegio);
		  	<c:forEach begin="0" items="${requestScope.listaColegioVO}" var="linea" varStatus="st">
					parent.document.frmFiltro.filColegio.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${linea.nombre}"/>','<c:out value="${linea.codigo}"/>');
			</c:forEach>
		</c:when>
		
		
	  <c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_INSTANCIA}">
	  		borrar_combo(parent.document.frmNuevo.lidInstancia);
		  	<c:forEach begin="0" items="${requestScope.listaInstanciaVO}" var="linea" varStatus="st">
					parent.document.frmNuevo.lidInstancia.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${linea.nombre}"/>','<c:out value="${linea.codigo}"/>');
			</c:forEach>
		</c:when>
	  <c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_RANGO}">
	  		borrar_combo(parent.document.frmNuevo.lidRango);
	  		borrar_combo(parent.document.frmNuevo.lidRol);
		  	<c:forEach begin="0" items="${requestScope.listaRangoVO}" var="linea" varStatus="st">
					parent.document.frmNuevo.lidRango.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${linea.nombre}"/>','<c:out value="${linea.codigo}"/>');
			</c:forEach>
				
		  	<c:forEach begin="0" items="${requestScope.listaRolVO}" var="linea" varStatus="st">
				parent.document.frmNuevo.lidRol.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${linea.nombre}"/>','<c:out value="${linea.codigo}"/>');
			</c:forEach>
		</c:when>
	  <c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_COLEGIO}">
	  		borrar_combo(parent.document.frmNuevo.lidColegio);
		  	<c:forEach begin="0" items="${requestScope.listaColegioVO}" var="linea" varStatus="st">
					parent.document.frmNuevo.lidColegio.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${linea.nombre}"/>','<c:out value="${linea.codigo}"/>');
				</c:forEach>
		</c:when>
	</c:choose>
//-->
</script>