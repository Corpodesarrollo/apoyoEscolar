<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean  id="filtroNivelEvalVO" class="siges.adminParamsInst.vo.FiltroNivelEvalVO" scope="session"/>
<jsp:useBean  id="paramsVO"   class="siges.adminParamsInst.vo.ParamsVO" scope="page"/>
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


	  <c:when test="${sessionScope.ajaxTipo==paramsVO.FICHA_INST_NIVEL_EVAL}">
	  	 




	<c:choose>
	  <c:when test="${sessionScope.ajaxParam==paramsVO.CMD_AJAX_JORD}">
	  	 
	  		 <c:if test="${sessionScope.formulario == 1}">
	  		 
	  		borrar_combo2(parent.document.frmFiltro.filJorn); 
	  			<c:forEach begin="0" items="${sessionScope.listaJornada}" var="grupo" varStatus="st">
			  	<c:if test="${grupo.codigo==sessionScope.filtroNivelEvalVO.filJorn}">
					parent.document.frmFiltro.filJorn.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>','SELECTED');
				</c:if>
				<c:if test="${grupo.codigo!=sessionScope.FilAusenciasVO.grupo}">
					parent.document.frmFiltro.filJorn.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>');
				</c:if>
			</c:forEach> 
		 </c:if>
				
          <c:if test="${sessionScope.formulario == 2}">
		 
				borrar_combo2(parent.document.frmNuevo.insnivcodjorn); 
	  			<c:forEach begin="0" items="${sessionScope.listaJornada}" var="grupo" varStatus="st">
			  	<c:if test="${grupo.codigo==sessionScope.filtroNivelEvalVO.filJorn}">
					parent.document.frmNuevo.insnivcodjorn.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>','selected');
				</c:if>
				<c:if test="${grupo.codigo!=sessionScope.FilAusenciasVO.grupo}">
					parent.document.frmNuevo.insnivcodjorn.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>');
				</c:if>
				</c:forEach> 
			 </c:if>
	</c:when>
	
	
	
	
	
	  <c:when test="${sessionScope.ajaxParam==paramsVO.CMD_AJAX_METD}">
	   
	  		 <c:if test="${sessionScope.formulario == 1}">
	  		 
	  		borrar_combo2(parent.document.frmFiltro.filMetodo); 
	   	<c:forEach begin="0" items="${sessionScope.listaMetodo}" var="grupo" varStatus="st">
			  	<c:if test="${grupo.codigo==sessionScope.filtroNivelEvalVO.filJorn}">
					parent.document.frmFiltro.filMetodo.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>','SELECTED');
				</c:if>
				<c:if test="${grupo.codigo!=sessionScope.FilAusenciasVO.grupo}">
					parent.document.frmFiltro.filMetodo.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>');
				</c:if>
			</c:forEach> 
		 </c:if>
				
          <c:if test="${sessionScope.formulario == 2}">
		  	borrar_combo2(parent.document.frmNuevo.insnivcodmetod); 
	  			<c:forEach begin="0" items="${sessionScope.listaMetodo}" var="grupo" varStatus="st">
			  	<c:if test="${grupo.codigo==sessionScope.filtroNivelEvalVO.filJorn}">
					parent.document.frmNuevo.insnivcodmetod.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>','selected');
				</c:if>
				<c:if test="${grupo.codigo!=sessionScope.FilAusenciasVO.grupo}">
					parent.document.frmNuevo.insnivcodmetod.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>');
				</c:if>
				</c:forEach> 
			 </c:if>
	</c:when>
	
	
	
	
	 <c:when test="${sessionScope.ajaxParam==paramsVO.CMD_AJAX_NVL  }">
	   
	  		 <c:if test="${sessionScope.formulario == 1}">
	  		 
	  		borrar_combo2(parent.document.frmFiltro.filNivel); 
	  			<c:forEach begin="0" items="${sessionScope.listaNivel}" var="grupo" varStatus="st">
			  	<c:if test="${grupo.codigo==sessionScope.filtroNivelEvalVO.filNivel}">
					parent.document.frmFiltro.filNivel.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>','SELECTED');
				</c:if>
				<c:if test="${grupo.codigo!=sessionScope.FilAusenciasVO.grupo}">
					parent.document.frmFiltro.filNivel.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>');
				</c:if>
			</c:forEach> 
		 </c:if>
				
          <c:if test="${sessionScope.formulario == 2}">
		
				borrar_combo2(parent.document.frmNuevo.insnivcodnivel); 
	  			<c:forEach begin="0" items="${sessionScope.listaNivel}" var="grupo" varStatus="st">
			  	<c:if test="${grupo.codigo==sessionScope.filtroNivelEvalVO.filJorn}">
					parent.document.frmNuevo.insnivcodnivel.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>','selected');
				</c:if>
				<c:if test="${grupo.codigo!=sessionScope.FilAusenciasVO.grupo}">
					parent.document.frmNuevo.insnivcodnivel.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>');
				</c:if>
				</c:forEach> 
			 </c:if>
	</c:when>
	
	
	
	 <c:when test="${sessionScope.ajaxParam==paramsVO.CMD_AJAX_GRAD  }">
	  
	  		 <c:if test="${sessionScope.formulario == 1}">
	  	 
	  		borrar_combo2(parent.document.frmFiltro.filGrado); 
	  			<c:forEach begin="0" items="${sessionScope.listaGrado}" var="grupo" varStatus="st">
			  	<c:if test="${grupo.codigo==sessionScope.filtroNivelEvalVO.filNivel}">
					parent.document.frmFiltro.filGrado.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>','SELECTED');
				</c:if>
				<c:if test="${grupo.codigo!=sessionScope.FilAusenciasVO.grupo}">
					parent.document.frmFiltro.filGrado.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>');
				</c:if>
			</c:forEach> 
		 </c:if>
				
          <c:if test="${sessionScope.formulario == 2}">
	 
				borrar_combo2(parent.document.frmNuevo.insnivcodgrado); 
	  			<c:forEach begin="0" items="${sessionScope.listaGrado}" var="grupo" varStatus="st">
			  	<c:if test="${grupo.codigo==sessionScope.filtroNivelEvalVO.filJorn}">
					parent.document.frmNuevo.insnivcodgrado.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>','selected');
				</c:if>
				<c:if test="${grupo.codigo!=sessionScope.FilAusenciasVO.grupo}">
					parent.document.frmNuevo.insnivcodgrado.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>');
				</c:if>
				</c:forEach> 
			 </c:if>
	</c:when>
	</c:choose>
	<!--   fin de FICHA_ESCL_NUM-->
	</c:when>	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	  <c:when test="${sessionScope.ajaxTipo==paramsVO.FICHA_ESCL_NUM}">
	  	 




	<c:choose>
	  <c:when test="${sessionScope.ajaxParam==paramsVO.CMD_AJAX_JORD}">
	  	 
	  		 <c:if test="${sessionScope.formulario == 1}">
	  		 
	  		borrar_combo2(parent.document.frmFiltro.filNumJorn); 
	  			<c:forEach begin="0" items="${sessionScope.listaJornada}" var="grupo" varStatus="st">
			  	<c:if test="${grupo.codigo==sessionScope.filNumtroNivelEvalVO.filNumJorn}">
					parent.document.frmFiltro.filNumJorn.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>','SELECTED');
				</c:if>
				<c:if test="${grupo.codigo!=sessionScope.filNumAusenciasVO.grupo}">
					parent.document.frmFiltro.filNumJorn.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>');
				</c:if>
			</c:forEach> 
		 </c:if>
				
          <c:if test="${sessionScope.formulario == 2}">
		 
				borrar_combo2(parent.document.frmNuevo.insnumcodjorn); 
	  			<c:forEach begin="0" items="${sessionScope.listaJornada}" var="grupo" varStatus="st">
			  	<c:if test="${grupo.codigo==sessionScope.filNumtroNivelEvalVO.filNumJorn}">
					parent.document.frmNuevo.insnumcodjorn.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>','selected');
				</c:if>
				<c:if test="${grupo.codigo!=sessionScope.filNumAusenciasVO.grupo}">
					parent.document.frmNuevo.insnumcodjorn.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>');
				</c:if>
				</c:forEach> 
			 </c:if>
	</c:when>
	
	
	
	
	
	  <c:when test="${sessionScope.ajaxParam==paramsVO.CMD_AJAX_METD}">
	   
	  		 <c:if test="${sessionScope.formulario == 1}">
	  		 
	  		borrar_combo2(parent.document.frmFiltro.filNumMetodo); 
	   	<c:forEach begin="0" items="${sessionScope.listaMetodo}" var="grupo" varStatus="st">
			  	<c:if test="${grupo.codigo==sessionScope.filNumtroNivelEvalVO.filNumJorn}">
					parent.document.frmFiltro.filNumMetodo.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>','SELECTED');
				</c:if>
				<c:if test="${grupo.codigo!=sessionScope.filNumAusenciasVO.grupo}">
					parent.document.frmFiltro.filNumMetodo.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>');
				</c:if>
			</c:forEach> 
		 </c:if>
				
          <c:if test="${sessionScope.formulario == 2}">
		  	borrar_combo2(parent.document.frmNuevo.insnumcodmetod); 
	  			<c:forEach begin="0" items="${sessionScope.listaMetodo}" var="grupo" varStatus="st">
			  	<c:if test="${grupo.codigo==sessionScope.filNumtroNivelEvalVO.filNumJorn}">
					parent.document.frmNuevo.insnumcodmetod.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>','selected');
				</c:if>
				<c:if test="${grupo.codigo!=sessionScope.filNumAusenciasVO.grupo}">
					parent.document.frmNuevo.insnumcodmetod.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>');
				</c:if>
				</c:forEach> 
			 </c:if>
	</c:when>
	
	
	
	
	 <c:when test="${sessionScope.ajaxParam==paramsVO.CMD_AJAX_NVL  }">
	   
	  		 <c:if test="${sessionScope.formulario == 1}">
	  		 
	  		borrar_combo2(parent.document.frmFiltro.filNumNivel); 
	  			<c:forEach begin="0" items="${sessionScope.listaNivel}" var="grupo" varStatus="st">
			  	<c:if test="${grupo.codigo==sessionScope.filNumtroNivelEvalVO.filNumNivel}">
					parent.document.frmFiltro.filNumNivel.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>','SELECTED');
				</c:if>
				<c:if test="${grupo.codigo!=sessionScope.filNumAusenciasVO.grupo}">
					parent.document.frmFiltro.filNumNivel.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>');
				</c:if>
			</c:forEach> 
		 </c:if>
				
          <c:if test="${sessionScope.formulario == 2}">
		
				borrar_combo2(parent.document.frmNuevo.insnumcodnivel); 
	  			<c:forEach begin="0" items="${sessionScope.listaNivel}" var="grupo" varStatus="st">
			  	<c:if test="${grupo.codigo==sessionScope.filNumtroNivelEvalVO.filNumJorn}">
					parent.document.frmNuevo.insnumcodnivel.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>','selected');
				</c:if>
				<c:if test="${grupo.codigo!=sessionScope.filNumAusenciasVO.grupo}">
					parent.document.frmNuevo.insnumcodnivel.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>');
				</c:if>
				</c:forEach> 
			 </c:if>
	</c:when>
	
	
	
	 <c:when test="${sessionScope.ajaxParam==paramsVO.CMD_AJAX_GRAD  }">
	  
	  		 <c:if test="${sessionScope.formulario == 1}">
	  	 
	  		borrar_combo2(parent.document.frmFiltro.filNumGrado); 
	  			<c:forEach begin="0" items="${sessionScope.listaGrado}" var="grupo" varStatus="st">
			  	<c:if test="${grupo.codigo==sessionScope.filNumtroNivelEvalVO.filNumNivel}">
					parent.document.frmFiltro.filNumGrado.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>','SELECTED');
				</c:if>
				<c:if test="${grupo.codigo!=sessionScope.filNumAusenciasVO.grupo}">
					parent.document.frmFiltro.filNumGrado.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>');
				</c:if>
			</c:forEach> 
		 </c:if>
				
          <c:if test="${sessionScope.formulario == 2}">
	 
				borrar_combo2(parent.document.frmNuevo.insnumcodgrado); 
	  			<c:forEach begin="0" items="${sessionScope.listaGrado}" var="grupo" varStatus="st">
			  	<c:if test="${grupo.codigo==sessionScope.filNumtroNivelEvalVO.filNumJorn}">
					parent.document.frmNuevo.insnumcodgrado.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>','selected');
				</c:if>
				<c:if test="${grupo.codigo!=sessionScope.filNumAusenciasVO.grupo}">
					parent.document.frmNuevo.insnumcodgrado.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>');
				</c:if>
				</c:forEach> 
			 </c:if>
	  </c:when>
	 </c:choose>
	<!--   fin de FICHA_ESCL_NUM-->
	</c:when>	
	
	
	
	
	
	
	
	
	
	  <c:when test="${sessionScope.ajaxTipo==paramsVO.FICHA_ESCL_CONCEPT }">
	  	 




	<c:choose>
	  <c:when test="${sessionScope.ajaxParam==paramsVO.CMD_AJAX_JORD}">
	  	 
	  		 <c:if test="${sessionScope.formulario == 1}">
	  		 
	  		borrar_combo2(parent.document.frmFiltro.filJorn); 
	  			<c:forEach begin="0" items="${sessionScope.listaJornada}" var="grupo" varStatus="st">
			  	<c:if test="${grupo.codigo==sessionScope.filtroNivelEvalVO.filJorn}">
					parent.document.frmFiltro.filJorn.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>','SELECTED');
				</c:if>
				<c:if test="${grupo.codigo!=sessionScope.filAusenciasVO.grupo}">
					parent.document.frmFiltro.filJorn.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>');
				</c:if>
			</c:forEach> 
		 </c:if>
				
          <c:if test="${sessionScope.formulario == 2}">
		 
				borrar_combo2(parent.document.frmNuevo.insconcodjorn); 
	  			<c:forEach begin="0" items="${sessionScope.listaJornada}" var="grupo" varStatus="st">
			  	<c:if test="${grupo.codigo==sessionScope.filtroNivelEvalVO.filJorn}">
					parent.document.frmNuevo.insconcodjorn.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>','selected');
				</c:if>
				<c:if test="${grupo.codigo!=sessionScope.filAusenciasVO.grupo}">
					parent.document.frmNuevo.insconcodjorn.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>');
				</c:if>
				</c:forEach> 
			 </c:if>
	</c:when>
	
	
	
	
	
	  <c:when test="${sessionScope.ajaxParam==paramsVO.CMD_AJAX_METD}">
	   
	  		 <c:if test="${sessionScope.formulario == 1}">
	  		 
	  		borrar_combo2(parent.document.frmFiltro.filMetodo); 
	   	<c:forEach begin="0" items="${sessionScope.listaMetodo}" var="grupo" varStatus="st">
			  	<c:if test="${grupo.codigo==sessionScope.filtroNivelEvalVO.filJorn}">
					parent.document.frmFiltro.filMetodo.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>','SELECTED');
				</c:if>
				<c:if test="${grupo.codigo!=sessionScope.filAusenciasVO.grupo}">
					parent.document.frmFiltro.filMetodo.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>');
				</c:if>
			</c:forEach> 
		 </c:if>
				
          <c:if test="${sessionScope.formulario == 2}">
		  	borrar_combo2(parent.document.frmNuevo.insconcodmetod); 
	  			<c:forEach begin="0" items="${sessionScope.listaMetodo}" var="grupo" varStatus="st">
			  	<c:if test="${grupo.codigo==sessionScope.filtroNivelEvalVO.filJorn}">
					parent.document.frmNuevo.insconcodmetod.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>','selected');
				</c:if>
				<c:if test="${grupo.codigo!=sessionScope.filAusenciasVO.grupo}">
					parent.document.frmNuevo.insconcodmetod.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>');
				</c:if>
				</c:forEach> 
			 </c:if>
	</c:when>
	
	
	
	
	 <c:when test="${sessionScope.ajaxParam==paramsVO.CMD_AJAX_NVL  }">
	   
	  		 <c:if test="${sessionScope.formulario == 1}">
	  		 
	  		borrar_combo2(parent.document.frmFiltro.filNivel); 
	  			<c:forEach begin="0" items="${sessionScope.listaNivel}" var="grupo" varStatus="st">
			  	<c:if test="${grupo.codigo==sessionScope.filtroNivelEvalVO.filNivel}">
					parent.document.frmFiltro.filNivel.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>','SELECTED');
				</c:if>
				<c:if test="${grupo.codigo!=sessionScope.filAusenciasVO.grupo}">
					parent.document.frmFiltro.filNivel.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>');
				</c:if>
			</c:forEach> 
		 </c:if>
				
          <c:if test="${sessionScope.formulario == 2}">
		
				borrar_combo2(parent.document.frmNuevo.insconcodnivel); 
	  			<c:forEach begin="0" items="${sessionScope.listaNivel}" var="grupo" varStatus="st">
			  	<c:if test="${grupo.codigo==sessionScope.filtroNivelEvalVO.filJorn}">
					parent.document.frmNuevo.insconcodnivel.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>','selected');
				</c:if>
				<c:if test="${grupo.codigo!=sessionScope.filAusenciasVO.grupo}">
					parent.document.frmNuevo.insconcodnivel.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>');
				</c:if>
				</c:forEach> 
			 </c:if>
	</c:when>
	
	
	
	 <c:when test="${sessionScope.ajaxParam==paramsVO.CMD_AJAX_GRAD  }">
	  
	  		 <c:if test="${sessionScope.formulario == 1}">
	  	 
	  		borrar_combo2(parent.document.frmFiltro.filGrado); 
	  			<c:forEach begin="0" items="${sessionScope.listaGrado}" var="grupo" varStatus="st">
			  	<c:if test="${grupo.codigo==sessionScope.filtroNivelEvalVO.filNivel}">
					parent.document.frmFiltro.filGrado.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>','SELECTED');
				</c:if>
				<c:if test="${grupo.codigo!=sessionScope.filAusenciasVO.grupo}">
					parent.document.frmFiltro.filGrado.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>');
				</c:if>
			</c:forEach> 
		 </c:if>
				
          <c:if test="${sessionScope.formulario == 2}">
	 
				borrar_combo2(parent.document.frmNuevo.insconcodgrado); 
	  			<c:forEach begin="0" items="${sessionScope.listaGrado}" var="grupo" varStatus="st">
			  	<c:if test="${grupo.codigo==sessionScope.filtroNivelEvalVO.filJorn}">
					parent.document.frmNuevo.insconcodgrado.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>','selected');
				</c:if>
				<c:if test="${grupo.codigo!=sessionScope.filAusenciasVO.grupo}">
					parent.document.frmNuevo.insconcodgrado.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.nombre}"/>','<c:out value="${grupo.codigo}"/>');
				</c:if>
				</c:forEach> 
			 </c:if>
	  </c:when>
	 </c:choose>
	<!--   fin de FICHA_ESCL_CONCEPT -->
	</c:when>	
	
	
	
	
	
	
	</c:choose>	
//-->
</script>