<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean  id="filtroEnviarVO" class="siges.gestionAdministrativa.enviarMensajes.vo.FiltroEnviarVO" scope="session"/>
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
	<c:when test="${sessionScope.ajaxParam == paramsVO.CMD_AJAX_INST  }">
	  	if(parent.document.getElementById('filinst')){
	  	
	  	  borrar_combo33(parent.document.getElementById('filinst'));
	  	  <c:forEach begin="0" items="${sessionScope.listaColegio }" var="grupo" varStatus="st">
		     parent.document.getElementById('filinst').options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
	      <c:if test="${!empty sessionScope.login.instId and    sessionScope.login.instId  == grupo.codigo }">		
		         parent.document.getElementById('filinst').selectedIndex = '<c:out value="${st.count}"/>';
		   </c:if>
	      </c:forEach> 
	      

	    }else{  
	    
	      borrar_combo33(parent.document.frmNuevo.inst);
	       <c:forEach begin="0" items="${sessionScope.listaColegio }" var="grupo" varStatus="st">
		     parent.document.frmNuevo.inst.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
	        <c:if test="${!empty sessionScope.login.instId and    sessionScope.login.instId  == grupo.codigo }">		
		       parent.document.frmNuevo.inst.selectedIndex = '<c:out value="${st.count}"/>';
		       parent.document.frmNuevo.inst.disabled = true;
		        <c:if test="${ mensajesVO.formaEstado != 1 }">
                 parent.agrega_row(3, parent.document.frmNuevo.inst, parent.document.frmNuevo.local_inst);
	           </c:if>
		    </c:if>
	      </c:forEach> 
	       
	    
	    
	    }  
	</c:when>
	
	
	 <c:when test="${sessionScope.ajaxParam == paramsVO.CMD_AJAX_SED }">
	   borrar_combo33(parent.document.getElementById('sede') ); 
	   <c:forEach begin="0" items="${sessionScope.listaSede }" var="grupo" varStatus="st">
		     parent.document.getElementById('sede').options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
		 </c:forEach> 
	  </c:when>
	 
	  <c:when test="${sessionScope.ajaxParam==paramsVO.CMD_AJAX_JORD }">
	  	  borrar_combo33(parent.document.getElementById('jornada')); 
	  	  var combo = new Array();
	  	  var combo_ = new Array();
	  	  var k = 0;
	  	  <c:forEach begin="0" items="${sessionScope.listaJornada }" var="grupo" varStatus="st">
		    parent.document.getElementById('jornada').options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
		    combo[<c:out value="${st.count - 1 }"/> ] = '<c:out value="${grupo.codigo }"/>' + '|' + '<c:out value="${grupo.nombre }"/>'; 
		   </c:forEach> 

		  for(var i = 0;i < combo.length; i++){ 
		     for(var j = 0;j < parent.listJord.length; j++){
		       if(combo[i] == parent.listJord[j]){
		         combo_[k++] = combo[i];
		       } 
		     }
		   }
		  parent.listJord = null;
		  parent.listJord = combo_;
          parent.pintarTabla(parent.listJord, parent.tipo_jord);
          
	 </c:when>
	
	
	  
	
	 
	
	
	 
	
 
	
	
	
	</c:choose>	
//-->
</script>