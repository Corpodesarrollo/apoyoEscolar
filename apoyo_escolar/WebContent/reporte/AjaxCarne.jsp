<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean  id="plantillaBoletionVO" class="siges.gestionAdministrativa.boletin.vo.PlantillaBoletionVO" scope="session"/>
<jsp:useBean id="paramsVO" class="siges.reporte.beans.ParamsVO" scope="request"/>

<script>
<!--	alert("llego ajax");
	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("--","-9");
	}
	function borrar_combo2(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("--Seleccione uno","-9");
	}
 
 	function borrar_combo3(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("--Seleccione uno","-9");
	}
 
	   
	 
	<c:choose>

    <c:when test="${sessionScope.ajaxParam == paramsVO.CMD_AJAX_LOC  }">
	  	 borrar_combo2(parent.document.frm.localidad); 
	  	 
	  	  <c:forEach begin="0" items="${sessionScope.listaLocalidad }" var="grupo" varStatus="st">
		    parent.document.frm.localidad.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
	        
	        	<c:if test="${!empty sessionScope.login.munId and    sessionScope.login.munId  == grupo.codigo }">		
		         parent.document.frm.localidad.selectedIndex = '<c:out value="${st.count}"/>';
				</c:if>
	      </c:forEach> 
	      
	      
	       borrar_combo2(parent.document.frm.listaTiposCol); 
	  	  <c:forEach begin="0" items="${sessionScope.listaTipoColegio }" var="grupo" varStatus="st">
		    parent.document.frm.listaTiposCol.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
		     <c:if test="${!empty sessionScope.sectorId and sessionScope.sectorId == grupo.codigo }">		
		         parent.document.frm.listaTiposCol.selectedIndex = '<c:out value="${st.count}"/>';
			 </c:if>
	      </c:forEach>  

         borrar_combo2(parent.document.frm.institucion); 
	  	  <c:forEach begin="0" items="${sessionScope.listaColegio }" var="grupo" varStatus="st">
		    parent.document.frm.institucion.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
		     <c:if test="${!empty sessionScope.login.instId and    sessionScope.login.instId  == grupo.codigo }">		
		         parent.document.frm.institucion.selectedIndex = '<c:out value="${st.count}"/>';
			 </c:if>
	      </c:forEach> 
	      
	  	 borrar_combo2(parent.document.frm.sede); 
	  	  <c:forEach begin="0" items="${sessionScope.listaSede }" var="grupo" varStatus="st">
		    parent.document.frm.sede.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
	         <c:if test="${!empty sessionScope.login.sedeId and    sessionScope.login.sedeId  == grupo.codigo }">		
		         parent.document.frm.sede.selectedIndex = '<c:out value="${st.count}"/>';
			 </c:if>
	      </c:forEach> 
	      
	      borrar_combo2(parent.document.frm.jornada); 
	  	  <c:forEach begin="0" items="${sessionScope.listaJornada }" var="grupo" varStatus="st">
		   
		    parent.document.frm.jornada.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
		     <c:if test="${!empty sessionScope.login.jornadaId and    sessionScope.login.jornadaId  == grupo.codigo }">		
		        parent.document.frm.jornada.selectedIndex = '<c:out value="${st.count}"/>';
			 </c:if>
	      </c:forEach> 
	      
	  	 borrar_combo2(parent.document.frm.metodologia); 
	  	  <c:forEach begin="0" items="${sessionScope.listaMetodo }" var="grupo" varStatus="st">
		    parent.document.frm.metodologia.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
	      </c:forEach> 
	      
	  	 borrar_combo2(parent.document.frm.grado); 
	  	  <c:forEach begin="0" items="${sessionScope.listaGrado }" var="grupo" varStatus="st">
		    parent.document.frm.grado.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
	      </c:forEach> 
	      
	  	 borrar_combo2(parent.document.frm.grupo); 
	  	  <c:forEach begin="0" items="${sessionScope.listaGrupo }" var="grupo" varStatus="st">
		    parent.document.frm.grupo.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
	      </c:forEach> 
	      	      	      
	      
	</c:when>
	
	<c:when test="${sessionScope.ajaxParam == paramsVO.CMD_AJAX_INST  }">
	  	 borrar_combo2(parent.document.frm.institucion); 
	  	  <c:forEach begin="0" items="${sessionScope.listaColegio }" var="grupo" varStatus="st">
		    parent.document.frm.institucion.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
	      </c:forEach> 
	</c:when>
	
	<c:when test="${sessionScope.ajaxParam == paramsVO.CMD_AJAX_SED }">
	  	 borrar_combo2(parent.document.frm.sede); 
	  	  <c:forEach begin="0" items="${sessionScope.listaSede }" var="grupo" varStatus="st">
		    parent.document.frm.sede.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
	      </c:forEach> 
	</c:when>
	
	
	<c:when test="${sessionScope.ajaxParam == paramsVO.CMD_AJAX_JORD }">
	  	 borrar_combo2(parent.document.frm.jornada); 
	  	  <c:forEach begin="0" items="${sessionScope.listaJornada }" var="grupo" varStatus="st">
		    parent.document.frm.jornada.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
	      </c:forEach> 
	</c:when>
	 
	<c:when test="${sessionScope.ajaxParam == paramsVO.CMD_AJAX_METD }">
	  	 borrar_combo2(parent.document.frm.metodologia); 
	  	  <c:forEach begin="0" items="${sessionScope.listaMetodo }" var="grupo" varStatus="st">
		    parent.document.frm.metodologia.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
	      </c:forEach> 
	</c:when>
	
	<c:when test="${sessionScope.ajaxParam == paramsVO.CMD_AJAX_GRAD }">
	  	 borrar_combo2(parent.document.frm.grado); 
	  	  <c:forEach begin="0" items="${sessionScope.listaGrado }" var="grupo" varStatus="st">
		    parent.document.frm.grado.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
	      </c:forEach> 
	</c:when>
	
	<c:when test="${sessionScope.ajaxParam == paramsVO.CMD_AJAX_GRUP }">
	  	 borrar_combo2(parent.document.frm.grupo); 
	  	  <c:forEach begin="0" items="${sessionScope.listaGrupo }" var="grupo" varStatus="st">
		    parent.document.frm.grupo.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
	      </c:forEach> 
	</c:when>
	
	
	</c:choose>	
//-->
</script>