<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
<jsp:useBean  id="paramsVO"   class="siges.common.vo.Params" scope="page"/>

<script>
<!--	
 
  
	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("--","-99");
	}
	function borrar_combo2(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("--Seleccione uno","-99");
	}
 
 	function borrar_combo3(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("--Seleccione uno","-99");
	}
	
	 	function borrar_combo33(combo){  
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("--Todos--","-9");
	}
	
	
  
 
	       
	<c:choose>
	 
	 <c:when test="${sessionScope.ajaxParam == paramsVO.CMD_AJAX_SED }">
	   borrar_combo2(parent.document.getElementById('filsede') ); 
	   <c:forEach begin="0" items="${sessionScope.listaSede }" var="grupo" varStatus="st">
		   if(parent.document.getElementById('filsede')){
		     parent.document.getElementById('filsede').options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
		   }else{
		      parent.document.getElementById('sede').options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
		   }
	     </c:forEach> 
	  </c:when>
	 
	  <c:when test="${sessionScope.ajaxParam==paramsVO.CMD_AJAX_JORD }">
	  	 if(parent.document.getElementById('filjornd')){
	  	    borrar_combo2(parent.document.getElementById('filjornd')); 
	  	  }else{
	  	    borrar_combo2(parent.document.getElementById('jornada')); 
	  	  } 
	  	  <c:forEach begin="0" items="${sessionScope.listaJornada }" var="grupo" varStatus="st">
		  
		    if(parent.document.getElementById('filjornd')){
		      parent.document.getElementById('filjornd').options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
		    }else{
		    parent.document.getElementById('jornada').options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
		    }
		   </c:forEach> 
	 
	</c:when>
	
	
	 <c:when test="${sessionScope.ajaxParam==paramsVO.CMD_AJAX_METD  }">
          if(parent.document.getElementById('filmetod')){
	  	    borrar_combo2(parent.document.getElementById('filmetod')); 
	  	  }else{
	  	    parent.document.getElementById('metodologia')
	  	  }
	  	  <c:forEach begin="0" items="${sessionScope.listaMetodo }" var="grupo" varStatus="st">
		   
		    if(parent.document.getElementById('filmetod')){
		     parent.document.getElementById('filmetod').options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
		    }else{
		      parent.document.getElementById('metodologia').options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
		    }
		   </c:forEach> 
		 
	</c:when>
	
	<c:when test="${sessionScope.ajaxParam==paramsVO.CMD_AJAX_GRAD  }">
	    if(parent.document.getElementById('filgrado') ){
	  	  borrar_combo2(parent.document.getElementById('filgrado')); 
	  	 }else{
	  	   borrar_combo2(parent.document.getElementById('grado')); 
	  	 }
	  	  <c:forEach begin="0" items="${sessionScope.listaGrado}" var="grupo" varStatus="st">
		    if(parent.document.getElementById('filgrado')){
		     parent.document.getElementById('filgrado').options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
		    }else{
		     parent.document.getElementById('grado').options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
		    }
		   </c:forEach> 
		 
	</c:when>
	
	
	 <c:when test="${sessionScope.ajaxParam==paramsVO.CMD_AJAX_GRUP }">
	  	 if(parent.document.getElementById('filgrupo') ){
	  	  borrar_combo2(parent.document.getElementById('filgrupo')); 
	  	 }else{
	  	  borrar_combo33(parent.document.getElementById('grupo')); 
	  	 }
	  	  <c:forEach begin="0" items="${sessionScope.listaGrupo}" var="grupo" varStatus="st">
		    if(parent.document.getElementById('filgrupo')){
		      parent.document.getElementById('filgrupo').options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
		    }else{
		     parent.document.getElementById('grupo').options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
		    }
		   </c:forEach> 
		  
	</c:when>
	
	
 
	
	
	
	
	</c:choose>	
//-->
</script>