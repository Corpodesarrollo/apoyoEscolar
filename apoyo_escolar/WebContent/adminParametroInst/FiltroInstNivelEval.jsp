<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean  id="filtroNivelEvalVO" class="siges.adminParamsInst.vo.FiltroNivelEvalVO" scope="session"/>
<jsp:useBean  id="paramsVO"   class="siges.adminParamsInst.vo.ParamsVO" scope="page"/>

<html>

	<head>
  
		<script languaje="javaScript">
 
 			function borrar_combo2(combo) {
				for(var i = combo.length - 1; i >= 0; i--) {
					if(navigator.appName == "Netscape")
						combo.options[i] = null;
					else
						combo.remove(i);
				}
				combo.options[0] = new Option("--Seleccione uno","-99");
			}
	 
  			function editar(inst, vig, nvlEval, sede, jorn, metodo, nivel, grado) {
	 			if(document.frmFiltro.param) {
					document.frmFiltro.param[0].value = inst;
					document.frmFiltro.param[1].value = vig;
					document.frmFiltro.param[2].value = nvlEval;
					document.frmFiltro.param[3].value = sede;
					document.frmFiltro.param[4].value = jorn;
					document.frmFiltro.param[5].value = metodo;
					document.frmFiltro.param[6].value = nivel;
					document.frmFiltro.param[7].value = grado; 
					
					document.frmFiltro.cmd.value = document.frmFiltro.EDITAR.value;
					document.frmFiltro.submit();
				}
			} 
	
  			function eliminar(inst, vig, nvlEval, sede, jorn, metodo, nivel, grado) {
   				if(document.frmFiltro.param  && confirm("¿Esta seguro de eliminar esta información?")) {
			      	document.frmFiltro.param[0].value = inst;
					document.frmFiltro.param[1].value = vig;
					document.frmFiltro.param[2].value = nvlEval;
					document.frmFiltro.param[3].value = sede;
			 	    document.frmFiltro.param[4].value = jorn;
				    document.frmFiltro.param[5].value = metodo;
				    document.frmFiltro.param[6].value = nivel;
				    document.frmFiltro.param[7].value = grado;
				    
					document.frmFiltro.cmd.value = document.frmFiltro.ELIMINAR.value;
					document.frmFiltro.submit();
				}
  			}
	
  			function buscar() {
				if(validarForma(document.frmFiltro)) {
	    			document.frmFiltro.cmd.value = document.frmFiltro.BUSCAR.value;
					document.frmFiltro.submit();
				}
  			}

  			function hacerValidaciones_frmFiltro(forma) {
     			try {
      				validarLista(forma.filVigencia,'- Vigencia');
      				
      				if(forma.filSede) {
      					validarLista(forma.filSede,'- Sede');
      				}
      
      				if(forma.filJorn) {
      					validarLista(forma.filJorn,'- Jornada');
      				}
      
      				if(forma.filMetodo) {
      					validarLista(forma.filMetodo,'- Metodologia');
      				}
      				
       				if(forma.filNivel) {
      					validarLista(forma.filNivel,'- Nivel');
      				}
    			} catch(e) {
      				alert(e);
    			}
  			}
	
			function ajaxJornada_1() {
	 			if(document.frmFiltro.filSede) {
	  				borrar_combo2(document.frmFiltro.filJorn);
	  				document.frmAjaxFiltro.target = 'frameAjaxFiltro';
	  				document.frmAjaxFiltro.ajax[0].value = document.frmFiltro.filSede.value;
	  				document.frmAjaxFiltro.cmd.value = document.frmAjaxFiltro.CMD_AJAX_JORD.value;
	  				document.frmAjaxFiltro.submit();
	 			}
			}
	
			function ajaxMetodologia_1() {
	 			if(document.frmFiltro.filSede) {
	  				borrar_combo2(document.frmFiltro.filMetodo);
	  				document.frmAjaxFiltro.target = 'frameAjaxFiltro';
	  				document.frmAjaxFiltro.ajax[0].value = document.frmFiltro.filSede.value;
	  				document.frmAjaxFiltro.cmd.value = document.frmAjaxFiltro.CMD_AJAX_METD.value;
	  				document.frmAjaxFiltro.submit();
	 			}
			}
	
  			function ajaxNivel_1() {
	 			if(document.frmFiltro.filSede) {
	  				borrar_combo2(document.frmFiltro.filNivel);
	  				document.frmAjaxFiltro.target = 'frameAjaxFiltro';
	   				if(document.frmFiltro.filMetodo) {
	    				document.frmAjaxFiltro.ajax[0].value = document.frmFiltro.filMetodo.value;
	    				document.frmAjaxFiltro.cmd.value = document.frmAjaxFiltro.CMD_AJAX_NVL.value;
	  					if(document.frmFiltro.filMetodo.value > -99) {
	    					document.frmAjaxFiltro.submit();
	    				}
	   				} else {
	    				document.frmAjaxFiltro.ajax[0].value = -99;
	    				document.frmAjaxFiltro.cmd.value = document.frmAjaxFiltro.CMD_AJAX_NVL.value;
	    				document.frmAjaxFiltro.submit();
	   				}
	 			}
			}
	
	 		function ajaxGrado_1() {
	 			if(document.frmFiltro.filGrado) {
	  				borrar_combo2(document.frmFiltro.filGrado);
	  				document.frmAjaxFiltro.target = 'frameAjaxFiltro';
	   
	   				//Verifique que existe la metodologia
	   				if(document.frmFiltro.filMetodo) {
	    				document.frmAjaxFiltro.ajax[0].value = document.frmFiltro.filMetodo.value;
	   				} else {
	    				document.frmAjaxFiltro.ajax[0].value = -99;
	   				}
	   				
	   				//Verifique que existe el nivel	   
	   				if(document.frmFiltro.filNivel) {
	    				document.frmAjaxFiltro.ajax[1].value = document.frmFiltro.filNivel.value;
	   				} else {
	    				document.frmAjaxFiltro.ajax[1].value = -99;
	   				}
	     
	  				document.frmAjaxFiltro.cmd.value = document.frmAjaxFiltro.CMD_AJAX_GRAD.value;
	  				document.frmAjaxFiltro.submit();
	 			}
			}
   
		</script>
	
	</head>

	<c:import url="/mensaje.jsp"/>

	<body onLoad="mensaje(document.getElementById('msg'));">
   	
   		<form method="post" name="frmAjaxFiltro" action='<c:url value="/adminParametroInst/Ajax.do"/>'>
			<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_INST_NIVEL_EVAL}"/>'>
			<input type="hidden" name="cmd" value='-1'>
			<input type="hidden" name="formulario" id="formulario"  value='1'>
			<input type="hidden" name="CMD_AJAX_JORD" id="CMD_AJAX_JORD" value='<c:out value="${paramsVO.CMD_AJAX_JORD}"/>'>
			<input type="hidden" name="CMD_AJAX_METD" id="CMD_AJAX_METD" value='<c:out value="${paramsVO.CMD_AJAX_METD}"/>'>
			<input type="hidden" name="CMD_AJAX_NVL" id="CMD_AJAX_NVL" value='<c:out value="${paramsVO.CMD_AJAX_NVL}"/>'>
			<input type="hidden" name="CMD_AJAX_GRAD" id="CMD_AJAX_GRAD" value='<c:out value="${paramsVO.CMD_AJAX_GRAD}"/>'>
		
			<c:forEach begin="0" end="5" var="i">
		   		<input type="hidden" name="ajax" id="ajax">
		  	</c:forEach>
	  	</form>
	  
		<form method="post" name="frmFiltro" action='<c:url value="/adminParametroInst/Save.jsp"/>'>
		
	    	<input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_INST_NIVEL_EVAL}"/>'>
			<input type="hidden" name="cmd" value=''>
			
			<c:forEach begin="0" end="15">
				<input type="hidden" name="param" value=''>
			</c:forEach>
			
			<input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
			<input type="hidden" name="ELIMINAR" value='<c:out value="${paramsVO.CMD_ELIMINAR}"/>'>
			<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
			<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
			<input type="hidden" name="filInst"    id="filInst" value='<c:out value="${sessionScope.filtroNivelEvalVO.filInst}"/>'>
			<input type="hidden" name="filInst_"   id="filInst_" value='<c:out value="${sessionScope.filtroNivelEvalVO.filInst}"/>'>
		    <input type="hidden" name='filSede_'   id='filSede_' value='<c:out value="${sessionScope.filtroNivelEvalVO.filSede}"/>'>
		    <input type="hidden" name='filJorn_'   id='filJorn_' value='<c:out value="${sessionScope.filtroNivelEvalVO.filJorn}"/>'>
			<input type="hidden" name='filMetodo_' id='filMetodo_' value='<c:out value="${sessionScope.filtroNivelEvalVO.filMetodo}"/>'>
		    <input type="hidden" name='filNivel_'  id='filNivel_' value='<c:out value="${sessionScope.filtroNivelEvalVO.filNivel}"/>'>
		    <input type="hidden" name='filGrado_'  id='filGrado_' value='<c:out value="${sessionScope.filtroNivelEvalVO.filGrado}"/>'>
	    
	 
	
          <!-- FILTRO DE BUSQUDA -->
           <table border="0" align="center" width="95%" cellpadding="2" cellspacing="2" bordercolor="gray">
		     <tr> <td  colspan="4" bgcolor="#FFFFFF"><input class='boton' name="cmd1" type="button" value="Buscar" onClick="buscar()"></td>
			 </tr>
		     <tr><td width="40px"><font color="red">*</font>Vigencia</td>
		         <td width="250px"><select name="filVigencia" style="width: 60px;">
					   <option value="-99"> --  //  -- </option>
					     <c:forEach  begin="0" items="${sessionScope.listaVigencia }" var="lista" >
					       <option value='<c:out  value="${ lista.codigo}"/>' <c:if test="${lista.codigo  == sessionScope.filtroNivelEvalVO.filVigencia }">SELECTED</c:if> ><c:out value="${lista.nombre}" /> </option>
					    </c:forEach>
					   </select>
				 </td>
				
				
		  <c:choose> 
	       <c:when test="${sessionScope.filtroNivelEvalVO.filniveval  == paramsVO.NVLEVA_INST }">
		        <td width="100px">&nbsp;</td><td width="100px">&nbsp;</td>
		   </c:when>
		   
		   <c:when test="${sessionScope.filtroNivelEvalVO.filniveval  == paramsVO.NVLEVA_SED  }">
	             <td width="80px"><font color="red">*</font>Sede</td><td width="250px" >
	                <select style="width: 140px;" name="filSede"    id="filSede"      onchange="javaScript:ajaxJornada();" >
	                <option value="-99" >--Seleccione uno--</option>
					   <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.filtroNivelEvalVO.filSede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>  
	                </select>
	              </td>
		   </c:when>

           <c:when test="${sessionScope.filtroNivelEvalVO.filniveval  == paramsVO.NVLEVA_JORN }">
	               <td width="80px"><font color="red">*</font> Jornada</td><td width="250px" >
	                <select style="width: 120px;" name="filJorn"     id="filJorn"        >
	                  <option value="-99" >--Seleccione uno--</option>
					   <c:forEach begin="0" items="${sessionScope.listaJornada}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.filtroNivelEvalVO.filJorn}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>  
	                </select></td>
			</c:when>
		     
		   <c:when test="${sessionScope.filtroNivelEvalVO.filniveval  == paramsVO.NVLEVA_METD }">
	            <td width="80px"><font color="red">*</font>Metodología</td> <td width="250px" >
	               <select style="width: 140px;" name="filMetodo"  id="filMetodo"   onchange="javaScript:ajaxNivel();" >
	                  <option value="-99" >--Seleccione uno--</option>
					   <c:forEach begin="0" items="${sessionScope.listaMetodo }" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.instNivelEvaluacionVO.insnivcodmetod}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
	               </select>
	             </td>
		   </c:when>
		     
		    <c:when test="${sessionScope.filtroNivelEvalVO.filniveval  == paramsVO.NVLEVA_NVL }">
	          <td width="80px"><font color="red">*</font>Nivel</td><td width="250px" >
	          
	             <select style="width: 140px;" name="filNivel"   id="filNivel"    onchange="javaScript:ajaxGrado();">
                      <option value="-99" >--Seleccione uno--</option>
					   <c:forEach begin="0" items="${sessionScope.listaNivel}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.filtroNivelEvalVO.filNivel }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
	             </select>
	             </td>
	       </c:when>
	       
	        <c:when test="${sessionScope.filtroNivelEvalVO.filniveval  == paramsVO.NVLEVA_GRD }">
	         <td> Grado</td><td width="250px" >
	           <select style="width: 140px;" name="filGrado" id="filGrado"   >
	                 <option value="-99" >--Seleccione uno--</option>
					   <c:forEach begin="0" items="${   sessionScope.listaGrado  }" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.filtroNivelEvalVO.filGrado }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
	           </select>
	           </td>
	       </c:when>
	       
	       
	       <c:when test="${sessionScope.filtroNivelEvalVO.filniveval  == paramsVO.NVLEVA_SED_JORN }">
	          <td width="80px"><font color="red">*</font>Sede</td>
	          <td width="250px" >
	            <select style="width: 140px;" name="filSede"    id="filSede"      onchange="javaScript:ajaxJornada_1();" >
	              <option value="-9" >--Seleccione uno--</option>
					   <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.filtroNivelEvalVO.filSede }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					  </c:forEach> 
	            </select>
	           </td>
	          </tr>
			  <tr>
				<td width="80px"><font color="red">*</font>Jornada</td><td width="250px" >
				  <select style="width: 120px;" name="filJorn"    id="filJorn"  >
				    <option value="-9" >--Seleccione uno--</option>
					 <c:forEach begin="0" items="${sessionScope.listaJornada}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroNivelEvalVO.filJorn }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					 </c:forEach> 
				  </select>
				  </td> 
		   </c:when>
		   
		  <c:when test="${sessionScope.filtroNivelEvalVO.filniveval  == paramsVO.NVLEVA_SED_METD }">
	          <td width="80px"><font color="red">*</font>Sede</td><td width="250px" >
	              <select style="width: 140px;" name="filSede"    id="filSede"      onchange="javaScript:ajaxMetodologia_1();" >
	                <option value="-9" >--Seleccione uno--</option>
					 <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.filtroNivelEvalVO.filSede }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					 </c:forEach> 
	              </select>
	           </td>
	          </tr>
			  <tr><td width="80px"><font color="red">*</font>Metodología</td> <td width="250px" >
			    <select style="width: 140px;" name="filMetodo"  id="filMetodo"   onchange="javaScript:ajaxNivel();" >
			        <option value="-9" >--Seleccione uno--</option>
					 <c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.filtroNivelEvalVO.filMetodo }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					 </c:forEach> 
			    </select>
			    </td>
		   </c:when>
		   
		     <c:when test="${sessionScope.filtroNivelEvalVO.filniveval  == paramsVO.NVLEVA_SED_NVL }">
	            <td width="80px"><font color="red">*</font>Sede</td>
	            <td width="250px" > 
	             <select style="width: 140px;" name="filSede"    id="filSede" onchange="JavaScript:ajaxNivel_1();">
	                 <option value="-9" >--Seleccione uno--</option>
	                 <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.filtroNivelEvalVO.filSede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
	             </select>
	            </td>
	          </tr>
			  <tr>
				 <td width="80px"><font color="red">*</font>Nivel</td>
				 <td width="250px" >
				  <select style="width: 140px;" name="filNivel"   id="filNivel"     >
				    <option value="-9" >--Seleccione uno--</option> 
	                 <c:forEach begin="0" items="${sessionScope.listaNivel}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.filtroNivelEvalVO.filNivel}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
				  </select>
				  </td>
		   </c:when>
		   
		    <c:when test="${sessionScope.filtroNivelEvalVO.filniveval  == paramsVO.NVLEVA_SED_GRD }">
	          <td width="80px"><font color="red">*</font>Sede</td>
	            <td width="250px" >
	              <select style="width: 140px;" name="filSede"    id="filSede"      onchange="javaScript:ajaxGrado_1();" >
	                <option value="-9" >--Seleccione uno--</option> 
	                 <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.filtroNivelEvalVO.filSede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
	                </select>
	              </td>
	          </tr>
			  <tr>
			  <td> Grado</td><td width="250px" >
			      <select style="width: 140px;" name="filGrado" id="filGrado"   >
			        <option value="-9" >--Seleccione uno--</option> 
			          <c:forEach begin="0" items="${sessionScope.listaGrado}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroNivelEvalVO.filGrado}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
	              </select>
			    </td>
		   </c:when>
		   
		        		        
	       <c:when test="${sessionScope.filtroNivelEvalVO.filniveval  == paramsVO.NVLEVA_SED_JORN_METD }">
	             <td width="80px"><font color="red">*</font>Sede</td><td width="250px" >
	                <select style="width: 140px;" name="filSede"    id="filSede"      onchange="javaScript:ajaxJornada_1(); " >
	                <option value="-9" >--Seleccione uno--</option> 
			          <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroNivelEvalVO.filSede }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
	                </select>
	               </td>
				 </tr>
				 <tr>
				  <td width="80px"><font color="red">*</font>Jornada</td><td width="250px" >
				     <select style="width: 120px;" name="filJorn"    id="filJorn"      onchange="javaScript:ajaxMetodologia_1();" >
				     <option value="-9" >--Seleccione uno--</option> 
			          <c:forEach begin="0" items="${sessionScope.listaJornada}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroNivelEvalVO.filJorn  }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
				     </select>
				    </td> 
				   <td width="80px"><font color="red">*</font>Metodología</td> 
				   <td width="250px" >
				     <select style="width: 140px;" name="filMetodo"  id="filMetodo"    >
				     <option value="-9" >--Seleccione uno--</option> 
			          <c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroNivelEvalVO.filMetodo}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
				     </select>
				  </td>
		     </c:when>
		     
		     
		      <c:when test="${sessionScope.filtroNivelEvalVO.filniveval  == paramsVO.NVLEVA_SED_JORN_NVL }">
	             <td width="80px"><font color="red">*</font>Sede</td>
	             <td width="250px" >
	                <select style="width: 140px;" name="filSede"    id="filSede"      onchange="javaScript:ajaxJornada_1();" >
	                 <option value="-9" >--Seleccione uno--</option> 
			          <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroNivelEvalVO.filSede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
				     </select>
	               </td>
				 </tr>
				 <tr>
				  <td width="80px"><font color="red">*</font>Jornada</td><td width="250px" >
				    <select style="width: 120px;" name="filJorn"    id="filJorn"      onchange="javaScript:ajaxNivel_1();" >
				      <option value="-9" >--Seleccione uno--</option> 
			          <c:forEach begin="0" items="${sessionScope.listaJornada}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroNivelEvalVO.filJorn}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
				    </select>
				  </td> 
				  <td width="80px"><font color="red">*</font>Nivel</td>
				  <td width="250px" >
				    <select style="width: 140px;" name="filNivel"   id="filNivel"    onchange="javaScript:ajaxGrado();">
				       <option value="-9" >--Seleccione uno--</option> 
			           <c:forEach begin="0" items="${sessionScope.listaNivel}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroNivelEvalVO.filNivel}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
				    </select>
				  </td>
				  
		     </c:when>
		     
		     
		     
		     
		     <c:when test="${sessionScope.filtroNivelEvalVO.filniveval  == paramsVO.NVLEVA_SED_JORN_GRD }">
	             <td width="80px"><font color="red">*</font>Sede</td>
	             <td width="250px" >
	                <select style="width: 140px;" name="filSede"    id="filSede"       onchange="javaScript:ajaxJornada_1();" >
	                   <option value="-9" >--Seleccione uno--</option> 
			           <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroNivelEvalVO.filNivel}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
				  
	                </select>
	               </td>
				 </tr>
				 <tr>
				 <td width="80px"><font color="red">*</font>Jornada</td>
				 <td width="250px" >
				    <select style="width: 120px;" name="filJorn"    id="filJorn"      onchange="javaScript:ajaxGrado_1();" >
				       <option value="-9" >--Seleccione uno--</option> 
			           <c:forEach begin="0" items="${sessionScope.listaJornada}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroNivelEvalVO.filGrado}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
				  
				    </select>
				   </td> 
				 <td> Grado</td>
				 <td width="250px" >
				   <select style="width: 140px;" name="filGrado" id="filGrado"   >
				     <option value="-9" >--Seleccione uno--</option> 
			           <c:forEach begin="0" items="${sessionScope.listaGrado}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroNivelEvalVO.filGrado}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
				  
				   </select>
				  </td>
				  
		     </c:when>
		    
	      
	      
	       <c:when test="${sessionScope.filtroNivelEvalVO.filniveval  == paramsVO.NVLEVA_SED_METD_NVL  }">
	             <td width="80px"><font color="red">*</font>Sede</td>
	             <td width="250px" >
	               <select style="width: 140px;" name="filSede"    id="filSede"      onchange="javaScript:   ajaxMetodologia_1(); " >
	                 <option value="-9" >--Seleccione uno--</option> 
			           <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroNivelEvalVO.filSede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
				   </select>
				   </td>
				 </tr>
				 <tr>
				 <td width="80px"><font color="red">*</font>Metodología</td> 
				 <td width="250px" >
				   <select style="width: 140px;" name="filMetodo"  id="filMetodo"   onchange="javaScript:ajaxNivel_1();" >
				    <option value="-9" >--Seleccione uno--</option> 
			           <c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroNivelEvalVO.filMetodo}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
				   </select>
				 </td>
		         <td width="80px"><font color="red">*</font>Nivel</td>
		         <td width="250px" >
		             <select style="width: 140px;" name="filNivel"   id="filNivel"    onchange="javaScript:ajaxGrado();">
		              <option value="-9" >--Seleccione uno--</option> 
			           <c:forEach begin="0" items="${sessionScope.listaNivel}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroNivelEvalVO.filNivel}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
				   </select>
		            </td>
				 
		     </c:when>
		     
		     
		     
		     
		          		        
	       <c:when test="${sessionScope.filtroNivelEvalVO.filniveval  == paramsVO.NVLEVA_SED_METD_GRD }">
	             <td width="80px"><font color="red">*</font>Sede</td>
	              <td width="250px" >
	              <select style="width: 140px;" name="filSede"    id="filSede"    onchange="javaScript:ajaxMetodologia_1();" >
	                 <option value="-9" >--Seleccione uno--</option> 
			           <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroNivelEvalVO.filSede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
	               </select>
	             </td>
				 </tr>
				 <tr>
				   <td width="80px"><font color="red">*</font>Metodología</td> 
				   <td width="250px" >
				     <select style="width: 140px;" name="filMetodo"  id="filMetodo" onchange="javaScript:ajaxGrado_1();" >
				      <option value="-9" >--Seleccione uno--</option> 
			           <c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroNivelEvalVO.filMetodo }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
				     </select>
				   <td>Grado</td>
				    <td width="250px" >
				    <select style="width: 140px;" name="filGrado" id="filGrado"   >
				        <option value="-9" >--Seleccione uno--</option> 
			           <c:forEach begin="0" items="${sessionScope.listaGrado}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroNivelEvalVO.filGrado}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
				    </select>
				  </td>
		     </c:when>
		     
		     
		     
		     
	       <c:when test="${sessionScope.filtroNivelEvalVO.filniveval  == paramsVO.NVLEVA_SED_NVL_GRD }">
	              <td width="80px"><font color="red">*</font>Sede</td>
	              <td width="250px" >
	               <select style="width: 140px;" name="filSede"    id="filSede"      onchange="javaScript:ajaxNivel_1();" >
	                 <option value="-9" >--Seleccione uno--</option> 
			           <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroNivelEvalVO.filSede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
	               </select>
	              </td>
				 </tr>
				 <tr><td width="80px"><font color="red">*</font>Nivel</td>
				      <td width="250px" >
				         <select style="width: 140px;" name="filNivel"   id="filNivel"    onchange="javaScript:ajaxGrado_1();">
				           <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaNivel}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroNivelEvalVO.filNivel}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
				         </select>
				        </td>
				     <td> Grado</td>
				      <td width="250px" >
				      <select style="width: 140px;" name="filGrado" id="filGrado"   >
				        <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaGrado}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroNivelEvalVO.filGrado }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
				     </select>
				     </td>
		     </c:when>
		     
	     
		     
	       <c:when test="${sessionScope.filtroNivelEvalVO.filniveval  == paramsVO.NVLEVA_SED_JORD_METD_NVL }">
	             <td width="80px"><font color="red">*</font>Sede</td>
	             <td width="250px" >
	                <select style="width: 140px;" name="filSede"    id="filSede"      onchange="javaScript: borrar_combo2(document.frmFiltro.filMetodo);borrar_combo2(document.frmFiltro.filNivel);ajaxJornada_1();" >
	                      <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroNivelEvalVO.filSede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
	                </select>
	               </td>
				 </tr>
				 <tr>
				  <td width="80px"><font color="red">*</font>Jornada</td>
				  <td width="250px" >
				    <select style="width: 120px;" name="filJorn"    id="filJorn"      onchange="javaScript:ajaxMetodologia_1();" >
				        <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaJornada}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroNivelEvalVO.filJorn }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
				    </select>
				   </td> 
				  <td width="80px"><font color="red">*</font>Metodología</td> <td width="250px" >
				   <select style="width: 140px;" name="filMetodo"  id="filMetodo"   onchange="javaScript:ajaxNivel_1();" >
				        <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroNivelEvalVO.filMetodo}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
				   </select>
				  </td>
		         </tr>
		         <tr><td width="80px"><font color="red">*</font>Nivel</td>
		             <td width="250px" >
		               <select style="width: 140px;" style="width: 140px;" name="filNivel"   id="filNivel"    >
	   			         <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaNivel}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroNivelEvalVO.filNivel}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
		               </select>
		               </td>
		     </c:when>
		     
		       <c:when test="${sessionScope.filtroNivelEvalVO.filniveval  == paramsVO.NVLEVA_SED_JORD_METD_GRD }">
	              <td width="80px"><font color="red">*</font>Sede</td>
	              <td width="250px" >
	                <select style="width: 140px;"  name="filSede"    id="filSede"      onchange="javaScript: borrar_combo2(document.frmFiltro.filMetodo);  borrar_combo2(document.frmFiltro.filGrado); ajaxJornada_1();" >
	                     <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroNivelEvalVO.filSede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
	                </select>
	              </td>
				 </tr>
				 <tr>
				  <td width="80px"><font color="red">*</font>Jornada</td>
				  <td width="250px" >
				    <select style="width: 120px;" name="filJorn"    id="filJorn"      onchange="javaScript:ajaxMetodologia_1();" >
				          <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaJornada}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroNivelEvalVO.filJorn}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
				    </select>
				   </td> 
				  <td width="80px"><font color="red">*</font>Metodología</td> 
				    <td width="250px" >
				      <select style="width: 140px;" name="filMetodo"  id="filMetodo"  onchange="JavaScript:ajaxGrado_1();" >
				          <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroNivelEvalVO.filMetodo}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
				      
				      </select>
				      </td>
		         </tr>
		         <tr><td> Grado</td>
		             <td width="250px" >
		               <select style="width: 140px;" name="filGrado" id="filGrado" >
		                  <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaGrado}" var="sed">
						    <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroNivelEvalVO.filGrado}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					       </c:forEach>
		               </select>
		              </td>
		      </c:when>
		      
		       <c:when test="${sessionScope.filtroNivelEvalVO.filniveval  == paramsVO.NVLEVA_SED_METD_NVL_GRD  }">
	              <td width="80px"><font color="red">*</font>Sede</td>
	              <td width="250px" >
	                <select style="width: 140px;" name="filSede"    id="filSede"      onchange="javaScript:borrar_combo2(document.frmFiltro.filNivel); borrar_combo2(document.frmFiltro.filGrado); ajaxMetodologia_1();" >
	                    <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroNivelEvalVO.filSede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
	                </select>
	               </td>
				 </tr>
				 <tr>
				  <td width="80px"><font color="red">*</font>Metodología</td>
				  <td width="250px" >
				    <select style="width: 140px;" name="filMetodo"  id="filMetodo"   onchange="javaScript:ajaxNivel_1();" >
				       <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroNivelEvalVO.filMetodo}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
				    </select>
				   </td>
                  <td width="80px"><font color="red">*</font>Nivel</td>
                  <td width="250px" >
                     <select style="width: 140px;" style="width: 140px;" name="filNivel"   id="filNivel"    onchange="javaScript:ajaxGrado_1();">
                          <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaNivel}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroNivelEvalVO.filNivel  }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
                     </select>
                    </td>
                 </tr>
		         <tr>
		          <td> Grado</td>
		          <td width="250px" >
		            <select style="width: 140px;" name="filGrado" id="filGrado" >
		                <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaGrado}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroNivelEvalVO.filGrado  }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
		            </select>
		           </td>
		      </c:when>
		      
		        <c:when test="${sessionScope.filtroNivelEvalVO.filniveval  == paramsVO.NVLEVA_SED_JORD_METD_NVL_GRD }">
	              <td width="80px"><font color="red">*</font>Sede</td>
	                 <td width="250px" >
	                    <select style="width: 140px;" name="filSede"    id="filSede"      onchange="javaScript:ajaxJornada_1();" >
	                       <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						    <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroNivelEvalVO.filSede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
	                    </select>
	                 </td>
				 </tr>
				 <tr>
				  <td width="80px"><font color="red">*</font>Jornada</td>
				  <td width="250px" >
				       <select style="width: 120px;" name="filJorn"    id="filJorn"      onchange="javaScript:ajaxMetodologia_1();" >
				          <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaJornada}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroNivelEvalVO.filJorn}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
				       </select></td> 
				  <td width="80px"><font color="red">*</font>Metodología</td> 
				  <td width="250px" ><select style="width: 140px;" name="filMetodo"  id="filMetodo"   onchange="javaScript:ajaxNivel_1();" >
				       <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroNivelEvalVO.filMetodo }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
				  </select></td>
                 </tr>
		         <tr>
		          <td width="80px"><font color="red">*</font>Nivel</td>
		          <td width="250px" >
		            <select style="width: 140px;" style="width: 140px;" name="filNivel"   id="filNivel"    onchange="javaScript:ajaxGrado_1();">
		              <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaNivel}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroNivelEvalVO.filNivel}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					  </c:forEach>
		            </select>
		          </td>
                  <td> Grado</td>
                    <td width="250px" >
                      <select style="width: 140px;" name="filGrado" id="filGrado" >
                          <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaGrado}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroNivelEvalVO.filGrado }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
                      </select>
                     </td>
		      </c:when>
		   </c:choose>
		    </tr>
		   </table>
        </form>  
         <!-- LISTA DE RESULTADOS -->
      
      
         <div style="width:100%;height:140px;overflow:auto;vertical-align:top;background-color:#ffffff;">
		   <table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray" class="table01" style="text-align: left;" >
			 	<caption>Lista Nivel Evaluación</caption>
			 	<c:if test="${empty requestScope.listaInstNivelEvalVO}"><tr><th class="Fila1" colspan='6'>No hay datos</th></tr></c:if>
				<c:if test="${!empty requestScope.listaInstNivelEvalVO}">
					<tr> 
						<th class="EncabezadoColumna"  rowspan="2" colspan="2" align="center">&nbsp;</th> 
                        <th class="EncabezadoColumna" colspan="2" align="center"	>Evaluacion</th>
                        <th class="EncabezadoColumna" colspan="3" align="center"	>Valor</th>
					</tr>
					<tr>	
					
					    <th class="EncabezadoColumna" align="center">Asignatura</th>
						<th class="EncabezadoColumna" align="center">Preescolar</th>
						<th class="EncabezadoColumna" align="center">Minimo</th>
						<th class="EncabezadoColumna" align="center">Maximo</th>
						<th class="EncabezadoColumna" align="center">Aprobación</th> 
					</tr>
					<c:forEach begin="0" items="${requestScope.listaInstNivelEvalVO  }" var="lista" varStatus="st">
						<tr>
						<th class='Fila<c:out value="${st.count%2}"/>' width="10px;"><c:out value="${st.count}"/>
						</th>
						  <th class='Fila<c:out value="${st.count%2}"/>' width="40px;">
							<a href='javaScript:editar(<c:out value="${lista.insnivcodinst}"/>,<c:out value="${lista.insnivvigencia}"/>,<c:out value="${lista.insnivcodniveleval}"/>,<c:out value="${lista.insnivcodsede }"/>,<c:out value="${lista.insnivcodjorn}"/>,<c:out value="${lista.insnivcodmetod} "/>,<c:out value="${lista.insnivcodnivel}"/>,<c:out value="${lista.insnivcodgrado }"/>);'><img border='0' src='<c:url value="/etc/img/editar.png"/>' width='15' height='15'></a>
							<c:if test="${sessionScope.NivelPermiso==2 }">
							<a href='javaScript:eliminar(<c:out value="${lista.insnivcodinst}"/>,<c:out value="${lista.insnivvigencia}"/>,<c:out value="${lista.insnivcodniveleval}"/>,<c:out value="${lista.insnivcodsede }"/>,<c:out value="${lista.insnivcodjorn}"/>,<c:out value="${lista.insnivcodmetod} "/>,<c:out value="${lista.insnivcodnivel}"/>,<c:out value="${lista.insnivcodgrado }"/>);'><img border='0' src='<c:url value="/etc/img/eliminar.png"/>' width='15' height='15'></a></c:if>
						   </th>
							<td class='Fila<c:out value="${st.count%2}"  />'   >&nbsp;<c:if test="${lista.insnivtipoevalasig == 1}">Numérico</c:if><c:if test="${lista.insnivtipoevalasig == 2}">Conceptual</c:if> <c:if test="${lista.insnivtipoevalasig == 3}">Porcentual</c:if> </td> 
							<td class='Fila<c:out value="${st.count%2}"/>'  >&nbsp;   <c:if test="${lista.insnivtipoevalprees == 1}"> Cualitativa</c:if><c:if test="${lista.insnivtipoevalprees == 2}">Asignatura</c:if></td>
							<td class='Fila<c:out value="${st.count%2}"  />'   >&nbsp;<c:if test="${lista.insnivtipoevalasig == 1 or lista.insnivtipoevalasig == 3}"><c:out value="${lista.insnivvalminnum}"/></c:if></td> 
							<td class='Fila<c:out value="${st.count%2}"/>'  >&nbsp;<c:if test="${lista.insnivtipoevalasig == 1 or lista.insnivtipoevalasig == 3}"><c:out value="${lista.insnivvalmaxnum}"/></c:if></td>
							<td class='Fila<c:out value="${st.count%2}"/>'  >&nbsp;<c:if test="${lista.insnivtipoevalasig == 1 or lista.insnivtipoevalasig == 3}"><c:out value="${lista.insnivvalaprobnum}"/></c:if></td>
							
						</tr>
					</c:forEach>
				</c:if>
					<tr style="display:none"><td><iframe name="frameAjaxFiltro" id="frameAjaxFiltro"></iframe></td></tr>
			</table>
		</div>	
		
	</body>
	<script>
 
	</script>
</htm>
