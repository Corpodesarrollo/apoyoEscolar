<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean  id="paramsVO"   class="siges.gestionAdministrativa.repResultadosAca.vo.ParamsVO" scope="page"/>

<script>
<!--	//alert("llego ajax");
	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("--","-99");
	}
	function borrar_combo2(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("--Seleccione uno--","-99");
	}
 
 	function borrar_combo3(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("--Seleccione uno--","-99");
	}
	
	
 	<c:choose>
	  <c:when test="${sessionScope.ajaxParam==paramsVO.CMD_AJAX_INST}">
	  	 borrar_combo2(parent.document.frmNuevo.inst); 
	  	  <c:forEach begin="0" items="${sessionScope.listaColegio}" var="grupo" varStatus="st">
		    parent.document.frmNuevo.inst.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
		   </c:forEach> 
	 
	</c:when>

	 <c:when test="${sessionScope.ajaxParam==paramsVO.CMD_AJAX_PER}">
  	 borrar_combo2(parent.document.frmNuevo.periodo); 
  	  <c:forEach begin="0" items="${sessionScope.listaPeriodo}" var="grupo" varStatus="st">
	    parent.document.frmNuevo.periodo.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
	   </c:forEach> 
	</c:when>

	
	  <c:when test="${sessionScope.ajaxParam==paramsVO.CMD_AJAX_SEDE}">
	  	 borrar_combo2(parent.document.frmNuevo.sede); 
	  	  <c:forEach begin="0" items="${sessionScope.listaSede}" var="grupo" varStatus="st">
		    parent.document.frmNuevo.sede.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
		   </c:forEach> 
	 
	</c:when>
	
	
	  <c:when test="${sessionScope.ajaxParam==paramsVO.CMD_AJAX_JORD }">
	  	 borrar_combo2(parent.document.frmNuevo.jornd); 
	  	  <c:forEach begin="0" items="${sessionScope.listaJornada }" var="grupo" varStatus="st">
		    parent.document.frmNuevo.jornd.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
		   </c:forEach> 
	 
	</c:when>
	
	
	 <c:when test="${sessionScope.ajaxParam==paramsVO.CMD_AJAX_METD}">
	  	 borrar_combo2(parent.document.frmNuevo.metodo); 
	  	  <c:forEach begin="0" items="${sessionScope.listaMetodo }" var="grupo" varStatus="st">
		    parent.document.frmNuevo.metodo.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
		   </c:forEach> 
		 
	</c:when>
	
		  <c:when test="${sessionScope.ajaxParam==paramsVO.CMD_AJAX_GRAD}">
	  	 borrar_combo2(parent.document.frmNuevo.grado); 
	  	  <c:forEach begin="0" items="${sessionScope.listaGrado}" var="grupo" varStatus="st">
		    parent.document.frmNuevo.grado.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
		   </c:forEach> 
		 
	</c:when>
	
	
	 <c:when test="${sessionScope.ajaxParam==paramsVO.CMD_AJAX_GRUP}">
	  	 borrar_combo2(parent.document.frmNuevo.grupo); 
	  	  <c:forEach begin="0" items="${sessionScope.listaGrupo}" var="grupo" varStatus="st">
		    parent.document.frmNuevo.grupo.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
		   </c:forEach> 
		  
	</c:when>
	
	
	</c:choose>	
//-->
</script>