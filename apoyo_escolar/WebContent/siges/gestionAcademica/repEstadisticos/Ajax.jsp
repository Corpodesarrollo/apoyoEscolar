<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean  id="plantillaBoletionVO" class="siges.gestionAdministrativa.boletin.vo.PlantillaBoletionVO" scope="session"/>
<jsp:useBean  id="paramsVO"   class="siges.gestionAcademica.repEstadisticos.vo.ParamsVO" scope="page"/>

<script>
<!--	

  
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
	 
	 <c:when test="${sessionScope.ajaxParam == paramsVO.CMD_AJAX_SED }">
	   borrar_combo2(parent.document.getElementById('filsede') ); 
	   <c:forEach begin="0" items="${sessionScope.listaSede }" var="grupo" varStatus="st">
		   parent.document.getElementById('filsede').options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
	    	<c:if test="${!empty sessionScope.login.sedeId and    sessionScope.login.sedeId  == grupo.codigo }">		
		         parent.document.getElementById('filsede').selectedIndex = '<c:out value="${st.count}"/>';
			</c:if>
	    </c:forEach> 
	    
	     <c:if test="${!empty sessionScope.listaJornada}">
	    	 borrar_combo2(parent.document.getElementById('filjornd')); 
	  	  <c:forEach begin="0" items="${sessionScope.listaJornada }" var="grupo" varStatus="st">
		    parent.document.getElementById('filjornd').options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
			<c:if test="${!empty sessionScope.login.jornadaId and    sessionScope.login.jornadaId  == grupo.codigo }">		
		        parent.document.getElementById('filjornd').selectedIndex = '<c:out value="${st.count}"/>';
			 </c:if>
		   </c:forEach> 
		   </c:if>
		   
		   <c:if test="${!empty sessionScope.listaMetodo}">
		   borrar_combo2(parent.document.getElementById('filmetod')); 
	  	  <c:forEach begin="0" items="${sessionScope.listaMetodo }" var="grupo" varStatus="st">
		    parent.document.getElementById('filmetod').options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
		   </c:forEach> 
		   </c:if>		   
	  </c:when>
	 
	  <c:when test="${sessionScope.ajaxParam==paramsVO.CMD_AJAX_JORD }">
	  	 borrar_combo2(parent.document.getElementById('filjornd')); 
	  	  <c:forEach begin="0" items="${sessionScope.listaJornada }" var="grupo" varStatus="st">
		    parent.document.getElementById('filjornd').options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
			<c:if test="${!empty sessionScope.login.jornadaId and    sessionScope.login.jornadaId  == grupo.codigo }">		
		        parent.document.getElementById('filjornd').selectedIndex = '<c:out value="${st.count}"/>';
			 </c:if>
		   </c:forEach> 
	</c:when>
	
	
	 <c:when test="${sessionScope.ajaxParam==paramsVO.CMD_AJAX_METD  }">

	  	 borrar_combo2(parent.document.getElementById('filmetod')); 
	  	  <c:forEach begin="0" items="${sessionScope.listaMetodo }" var="grupo" varStatus="st">
		    parent.document.getElementById('filmetod').options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
		   </c:forEach> 
		 
	</c:when>
	
	<c:when test="${sessionScope.ajaxParam==paramsVO.CMD_AJAX_GRAD  }">
	  
	  	 borrar_combo2(parent.document.getElementById('filgrado')); 
	  	  <c:forEach begin="0" items="${sessionScope.listaGrado}" var="grupo" varStatus="st">
		    parent.document.getElementById('filgrado').options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
		   </c:forEach> 
		 
	</c:when>
	
	
	 <c:when test="${sessionScope.ajaxParam==paramsVO.CMD_AJAX_GRUP }">
	  	 borrar_combo2(parent.document.getElementById('filgrupo')); 
	  	  <c:forEach begin="0" items="${sessionScope.listaGrupo}" var="grupo" varStatus="st">
		    parent.document.getElementById('filgrupo').options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
		   </c:forEach> 
		  
	</c:when>
	
	 <c:when test="${sessionScope.ajaxParam==paramsVO.CMD_AJAX_EST  }">
	  	 borrar_comboTodos(parent.document.getElementById('filest')); 
	  	  <c:forEach begin="0" items="${sessionScope.listaEstudiante}" var="grupo" varStatus="st">
		    parent.document.getElementById('filest').options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
		   </c:forEach> 
		  
	</c:when>	
	
	
   <c:when test="${sessionScope.ajaxParam==paramsVO.CMD_GRUPO_AREA}">
	   borrar_combo2(parent.document.getElementById('filgrupo')); 
	  	  <c:forEach begin="0" items="${sessionScope.listaGrupo}" var="grupo" varStatus="st">
		    parent.document.getElementById('filgrupo').options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
		   </c:forEach> 
	  
	  	 borrar_combo2(parent.document.getElementById('filarea')); 
	  	  <c:forEach begin="0" items="${sessionScope.listaArea}" var="grupo" varStatus="st">
		    parent.document.getElementById('filarea').options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
		   </c:forEach> 
		  
	</c:when>
	
	
	</c:choose>	
	
		function borrar_comboTodos(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("-- Todos --","-99");
	}
//-->
</script>