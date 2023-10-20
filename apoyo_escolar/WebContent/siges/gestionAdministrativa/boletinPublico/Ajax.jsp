<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean  id="plantillaBoletionPubVO" class="siges.gestionAdministrativa.boletinPublico.vo.PlantillaBoletionPubVO" scope="session"/>
<jsp:useBean  id="paramsVO"   class="siges.gestionAdministrativa.boletin.vo.ParamsVO" scope="page"/>

<script>
<!--	alert("llego ajax");
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
	
	 
	 
	<c:choose>
	  <c:when test="${sessionScope.ajaxParam==paramsVO.CMD_AJAX_JORD }">
	  	 borrar_combo2(parent.document.frmNuevo.plaboljornd); 
	  	  <c:forEach begin="0" items="${sessionScope.listaJornada }" var="grupo" varStatus="st">
		    parent.document.frmNuevo.plaboljornd.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
		   </c:forEach> 
	 
	</c:when>
	

	 <c:when test="${sessionScope.ajaxParam==paramsVO.CMD_AJAX_METD  }">

	  	 borrar_combo2(parent.document.frmNuevo.plabolmetodo); 
	  	  <c:forEach begin="0" items="${sessionScope.listaMetodo }" var="grupo" varStatus="st">
		    parent.document.frmNuevo.plabolmetodo.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
		   </c:forEach> 
		 
	</c:when>
	
	<c:when test="${sessionScope.ajaxParam==paramsVO.CMD_AJAX_GRAD  }">
	  
	  	 borrar_combo2(parent.document.frmNuevo.plabolgrado); 
	  	  <c:forEach begin="0" items="${sessionScope.listaGrado}" var="grupo" varStatus="st">
		    parent.document.frmNuevo.plabolgrado.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
		   </c:forEach> 

	</c:when>
	
	
	 <c:when test="${sessionScope.ajaxParam==paramsVO.CMD_AJAX_GRUP }">
	  	 borrar_combo2(parent.document.frmNuevo.plabolgrupo); 
	  	  <c:forEach begin="0" items="${sessionScope.listaGrupo}" var="grupo" varStatus="st">
		    parent.document.frmNuevo.plabolgrupo.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
		   </c:forEach> 
		  
	</c:when>
	
	
	 <c:when test="${sessionScope.ajaxParam==paramsVO.CMD_AJAX_EST }">
 
 
 
 	        borrar_combo2(parent.document.frmNuevo.plabolsede); 
	  	  <c:forEach begin="0" items="${sessionScope.listaSedes }" var="grupo" varStatus="st">
		    <c:if test="${grupo.codigo == sessionScope.plantillaBoletionPubVO.plabolsede}">
		     parent.document.frmNuevo.plabolsede.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' ,'selected');
		     parent.document.frmNuevo.plabolsede.selectedIndex = '<c:out value="${st.count}"/>';
		    </c:if>
		    <c:if test="${grupo.codigo != sessionScope.plantillaBoletionPubVO.plabolsede}">
		     parent.document.frmNuevo.plabolsede.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
		    </c:if>
		    
		   </c:forEach> 
 	     
 	      	 borrar_combo2(parent.document.frmNuevo.plaboljornd); 
	  	  <c:forEach begin="0" items="${sessionScope.listaJornada  }" var="grupo" varStatus="st">
		     
		      <c:if test="${grupo.codigo == sessionScope.plantillaBoletionPubVO.plaboljornd}">
		       parent.document.frmNuevo.plaboljornd.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>','selected' );
		       parent.document.frmNuevo.plaboljornd.selectedIndex = '<c:out value="${st.count}"/>';
		      </c:if>
		      <c:if test="${grupo.codigo != sessionScope.plantillaBoletionPubVO.plaboljornd}">
		      parent.document.frmNuevo.plaboljornd.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
		      </c:if>
		   </c:forEach> 
		   
 	      borrar_combo2(parent.document.frmNuevo.plabolmetodo); 
	  	  <c:forEach begin="0" items="${sessionScope.listaMetodo }" var="grupo" varStatus="st">
		    <c:if test="${grupo.codigo == sessionScope.plantillaBoletionPubVO.plabolmetodo}">
		     parent.document.frmNuevo.plabolmetodo.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>','selected' );
		     parent.document.frmNuevo.plabolmetodo.selectedIndex = '<c:out value="${st.count}"/>';
		    </c:if>
		     <c:if test="${grupo.codigo != sessionScope.plantillaBoletionPubVO.plabolmetodo}">
		    parent.document.frmNuevo.plabolmetodo.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
		    </c:if>
		   </c:forEach> 
		   
 	      borrar_combo2(parent.document.frmNuevo.plabolgrado); 
	  	  <c:forEach begin="0" items="${sessionScope.listaGrado}" var="grupo" varStatus="st">
		   <c:if test="${grupo.codigo == sessionScope.plantillaBoletionPubVO.plabolgrado}">
		    parent.document.frmNuevo.plabolgrado.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>','selected' );
            parent.document.frmNuevo.plabolgrado.selectedIndex = '<c:out value="${st.count}"/>';
           </c:if>
           <c:if test="${grupo.codigo != sessionScope.plantillaBoletionPubVO.plabolgrado}">
		    parent.document.frmNuevo.plabolgrado.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
           </c:if>	
		   </c:forEach> 
		   
		   
 	      borrar_combo2(parent.document.frmNuevo.plabolgrupo); 
	  	  <c:forEach begin="0" items="${sessionScope.listaGrupo}" var="grupo" varStatus="st">
		  	   <c:if test="${grupo.codigo == sessionScope.plantillaBoletionPubVO.plabolgrupo}">
			       parent.document.frmNuevo.plabolgrupo.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' ,'selected');
				   parent.document.frmNuevo.plabolgrupo.selectedIndex = '<c:out value="${st.count}"/>';
				</c:if>
			    <c:if test="${grupo.codigo != sessionScope.plantillaBoletionPubVO.plabolgrupo}">
			      parent.document.frmNuevo.plabolgrupo.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
				</c:if>
		   </c:forEach> 
		     
		   
		       borrar_combo2(parent.document.frmNuevo.plabolperido); 
	  	  <c:forEach begin="0" items="${sessionScope.filtroPeriodo}" var="grupo" varStatus="st">
		  	   <c:if test="${grupo.codigo == sessionScope.plantillaBoletionPubVO.plabolperido }">
			       parent.document.frmNuevo.plabolperido.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' ,'selected');
			       parent.document.frmNuevo.plabolperido.selectedIndex = '<c:out value="${st.count}"/>';
				</c:if>
			    <c:if test="${grupo.codigo != sessionScope.plantillaBoletionPubVO.plabolperido }">
			      parent.document.frmNuevo.plabolperido.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>' );
				</c:if>
		   </c:forEach> 
		   
		   
		   parent.document.frmNuevo.plabolsede.disabled = 'true';
		   parent.document.frmNuevo.plaboljornd.disabled = 'true';
		   parent.document.frmNuevo.plabolmetodo.disabled = 'true';
		   parent.document.frmNuevo.plabolgrado.disabled = 'true';
		   parent.document.frmNuevo.plabolgrupo.disabled = 'true'; 
		      
		   parent.document.frmNuevo.plabolnomb.value = '<c:out value="${sessionScope.estudianteVO.estnombre }"/>' ;
		   parent.document.frmNuevo.plabolinstBNomb.value = '<c:out value="${sessionScope.nombreInst  }"/>' ;
		   parent.document.frmNuevo.plabolinst.value = '<c:out value="${sessionScope.codigoInst  }"/>' ;
		   parent.document.frmNuevo.plabolperidoNomb.value = '<c:out value="${sessionScope.plabolperidoNomb }"/>' ;
		   
		   
		   
		   <c:if test="${empty sessionScope.estudianteVO.estnombre }"> 
		  
		     parent.document.frmNuevo.plabolnomb.value ='';
		     alert("-Verifique la siguiente información:\n\n    -No se encontro el estudiante");
		     </c:if>
		 
	</c:when>
	
	
	
	
	</c:choose>	
//-->
</script>