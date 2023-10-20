<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean  id="filtroEscalaNumVO" class="siges.adminParamsInst.vo.FiltroEscalaNumVO" scope="session"/>
<jsp:useBean  id="paramsVO"   class="siges.adminParamsInst.vo.ParamsVO" scope="page"/>
<html>
<head>
  
<script languaje="javaScript">

  
 function borrar_combo2(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("--Seleccione uno","-99");
	}
	
	
	 
  function editar(inst, vig, nvlEval, codEqInst,codEqMEN, sede, jorn, metodo, nivel, grado,  valorMax, valorMin){
	 if(document.frmFiltro.param){
			document.frmFiltro.param[0].value = inst;
			document.frmFiltro.param[1].value = vig;
			document.frmFiltro.param[2].value = nvlEval;
			document.frmFiltro.param[3].value = codEqInst;
			document.frmFiltro.param[4].value = codEqMEN;
			document.frmFiltro.param[5].value = sede;
	 	    document.frmFiltro.param[6].value = jorn;
		    document.frmFiltro.param[7].value = metodo;
		    document.frmFiltro.param[8].value = nivel;
		    document.frmFiltro.param[9].value = grado;
		    document.frmFiltro.param[10].value = valorMin;
		    document.frmFiltro.param[11].value = valorMax;
		    
			document.frmFiltro.cmd.value = document.frmFiltro.EDITAR.value;
			document.frmFiltro.submit();
		}
	}

 
	
  function eliminar(inst, vig, nvlEval, codEqInst,codEqMEN, sede, jorn, metodo, nivel, grado,  valorMax, valorMin){
   if(document.frmFiltro.param  && confirm("¿Esta seguro de eliminar esta información?")){
	      document.frmFiltro.param[0].value = inst;
			document.frmFiltro.param[1].value = vig;
			document.frmFiltro.param[2].value = nvlEval;
			document.frmFiltro.param[3].value = codEqInst;
			document.frmFiltro.param[4].value = codEqMEN;
			document.frmFiltro.param[5].value = sede;
	 	    document.frmFiltro.param[6].value = jorn;
		    document.frmFiltro.param[7].value = metodo;
		    document.frmFiltro.param[8].value = nivel;
		    document.frmFiltro.param[9].value = grado;
		    document.frmFiltro.param[10].value = valorMin;
		    document.frmFiltro.param[11].value = valorMax; 
		    
		    
		document.frmFiltro.cmd.value = document.frmFiltro.ELIMINAR.value;
		document.frmFiltro.submit();
	}
  }
	
  function buscar(){
	if(validarForma(document.frmFiltro)){
	    document.frmFiltro.cmd.value = document.frmFiltro.BUSCAR.value;
		document.frmFiltro.submit();
	}
  }
	
  function hacerValidaciones_frmFiltro(forma){
      try{
      validarLista(forma.filNumVigencia,'- Vigencia');
    
    
    
    
      if(forma.filNumSede){
      validarLista(forma.filNumSede,'- Sede');
      }
      if(forma.filNumJorn){
      validarLista(forma.filNumJorn,'- Jornada');
      }
      if(forma.filNumMetodo){
      validarLista(forma.filNumMetodo,'- Metodologia');
      }
       if(forma.filNumNivel){
      validarLista(forma.filNumNivel,'- Nivel');
      }
       if(forma.filNumGrado){
      validarLista(forma.filNumGrado,'- Grado');
      }
      
    }catch(e){
      alert(e);
    }
      
  }
	   
	   
	   
	   
	   
	
	function ajaxJornada_1(){

	 if(document.frmFiltro.filNumSede){ 
	  borrar_combo2(document.frmFiltro.filNumJorn);
	  document.frmAjaxFiltro.target = 'frameAjaxFiltro';
	  document.frmAjaxFiltro.ajax[0].value = document.frmFiltro.filNumSede.value;
	  document.frmAjaxFiltro.cmd.value = document.frmAjaxFiltro.CMD_AJAX_JORD.value;
	  document.frmAjaxFiltro.submit();
	 }
	}
	
	
	function ajaxMetodologia_1(){
	 if(document.frmFiltro.filNumSede){
	  borrar_combo2(document.frmFiltro.filNumMetodo);
	  document.frmAjaxFiltro.target = 'frameAjaxFiltro';
	  document.frmAjaxFiltro.ajax[0].value = document.frmFiltro.filNumSede.value;
	  document.frmAjaxFiltro.cmd.value = document.frmAjaxFiltro.CMD_AJAX_METD.value;
	  document.frmAjaxFiltro.submit();
	 }
	}
	
  function ajaxNivel_1(){
	 if(document.frmFiltro.filNumSede){
	  borrar_combo2(document.frmFiltro.filNumNivel);
	  document.frmAjaxFiltro.target = 'frameAjaxFiltro';
	   if(document.frmFiltro.filNumMetodo){
	    document.frmAjaxFiltro.ajax[0].value = document.frmFiltro.filNumMetodo.value;
	    document.frmAjaxFiltro.cmd.value = document.frmAjaxFiltro.CMD_AJAX_NVL.value;
	  if( document.frmFiltro.filNumMetodo.value > -99){
	    document.frmAjaxFiltro.submit();
	    }
	   
	   }else{
	    document.frmAjaxFiltro.ajax[0].value = -99;
	    document.frmAjaxFiltro.cmd.value = document.frmAjaxFiltro.CMD_AJAX_NVL.value;
	    document.frmAjaxFiltro.submit();
	   }

	 }
	}
	
	
	 function ajaxGrado_1(){
	 if(document.frmFiltro.filNumGrado){
	  borrar_combo2(document.frmFiltro.filNumGrado);
	  document.frmAjaxFiltro.target = 'frameAjaxFiltro';
	   
	   //Verifique que existe la metodologia
	   if(document.frmFiltro.filNumMetodo){
	    document.frmAjaxFiltro.ajax[0].value = document.frmFiltro.filNumMetodo.value;
	   }else{
	    document.frmAjaxFiltro.ajax[0].value = -99;
	   }

	   //Verifique que existe el nivel	   
	   if(document.frmFiltro.filNumNivel){
	    document.frmAjaxFiltro.ajax[1].value = document.frmFiltro.filNumNivel.value;
	   }else{
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
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_ESCL_NUM}"/>'>
		<input type="hidden" name="cmd"  value='-1'>
		<input type="hidden" name="formulario" id="formulario"  value='1'>
		<input type="hidden" name="CMD_AJAX_JORD"  id="CMD_AJAX_JORD"  value='<c:out value="${paramsVO.CMD_AJAX_JORD}"/>'>
		<input type="hidden" name="CMD_AJAX_METD"  id="CMD_AJAX_METD"  value='<c:out value="${paramsVO.CMD_AJAX_METD}"/>'>
		<input type="hidden" name="CMD_AJAX_NVL"   id="CMD_AJAX_NVL"   value='<c:out value="${paramsVO.CMD_AJAX_NVL}"/>'>
		<input type="hidden" name="CMD_AJAX_GRAD"  id="CMD_AJAX_GRAD"  value='<c:out value="${paramsVO.CMD_AJAX_GRAD}"/>'>
		
		
		
		<c:forEach begin="0" end="5" var="i">
		   <input type="hidden" name="ajax" id="ajax">
		  </c:forEach>
	  </form>
	
	<form method="post" name="frmFiltro"  action='<c:url value="/adminParametroInst/Save.jsp"/>'>
		 <input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_ESCL_NUM}"/>'>
		<input type="hidden" name="cmd" value=''>
		<c:forEach begin="0" end="15"><input type="hidden" name="param" value=''></c:forEach>
		<input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
		<input type="hidden" name="ELIMINAR" value='<c:out value="${paramsVO.CMD_ELIMINAR}"/>'>
		<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="filNumInst" id="filNumInst" value='<c:out value="${sessionScope.filtroEscalaNumVO.filNumInst}"/>'>
		<input type="hidden" name="filNumInst_" id="filNumInst_" value='<c:out value="${sessionScope.filtroEscalaNumVO.filNumInst}"/>'>
	    <input type="hidden" name='filNumSede_' id='filNumSede_' value='<c:out value="${sessionScope.filtroEscalaNumVO.filNumSede}"/>'>
	    <input type="hidden" name='filNumJorn_' id='filNumJorn_' value='<c:out value="${sessionScope.filtroEscalaNumVO.filNumJorn}"/>'>
		<input type="hidden" name='filNumMetodo_' id='filNumMetodo_' value='<c:out value="${sessionScope.filtroEscalaNumVO.filNumMetodo}"/>'>
	    <input type="hidden" name='filNumNivel_' id='filNumNivel_' value='<c:out value="${sessionScope.filtroEscalaNumVO.filNumNivel}"/>'>
	    <input type="hidden" name='filNumGrado_' id='filNumGrado_' value='<c:out value="${sessionScope.filtroEscalaNumVO.filNumGrado}"/>'>
	   
	 
         <!-- FILTRO DE BUSQUDA -->
           <table border="0" align="center" width="95%" cellpadding="2" cellspacing="2" bordercolor="gray">
		     <tr> <td   bgcolor="#FFFFFF"><input class='boton' name="cmd1" type="button" value="Buscar" onClick="buscar()"></td>
			 </tr>
		     <tr><td width="80px"><font color="red">*</font>Vigencia</td>
		         <td width="250px" ><select name="filNumVigencia" style="width: 60px;">
					   <option value="-99"> --  //  -- </option>
					     <c:forEach  begin="0" items="${sessionScope.listaVigencia }" var="lista" >
					       <option value='<c:out  value="${ lista.codigo}"/>' <c:if test="${lista.codigo  == filtroEscalaNumVO.filNumVigencia }">SELECTED</c:if > ><c:out value="${lista.nombre}" /> </option>
					    </c:forEach>
					   </select>
				 </td>
				
		   <c:choose> 
	       <c:when test="${sessionScope.filtroEscalaNumVO.filNumniveval  == paramsVO.NVLEVA_INST }">
		        <td width="100px">&nbsp;</td><td width="100px">&nbsp;</td>
		   </c:when>
		   
		   <c:when test="${sessionScope.filtroEscalaNumVO.filNumniveval  == paramsVO.NVLEVA_SED  }">
	             <td width="80px"><font color="red">*</font>Sede</td><td width="250px" >
	                <select style="width: 140px;" name="filNumSede"    id="filNumSede"      onchange="javaScript:ajaxJornada();" >
	                <option value="-99" >--Seleccione uno--</option>
					   <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.filtroEscalaNumVO.filNumSede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>  
	                </select>
	              </td>
		   </c:when>

           <c:when test="${sessionScope.filtroEscalaNumVO.filNumniveval  == paramsVO.NVLEVA_JORN }">
	               <td width="80px"><font color="red">*</font> Jornada</td><td width="250px" >
	                <select style="width: 120px;" name="filNumJorn"     id="filNumJorn"        >
	                  <option value="-99" >--Seleccione uno--</option>
					   <c:forEach begin="0" items="${sessionScope.listaJornada}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.filtroEscalaNumVO.filNumJorn}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>  
	                </select></td>
			</c:when>
		     
		   <c:when test="${sessionScope.filtroEscalaNumVO.filNumniveval  == paramsVO.NVLEVA_METD }">
	            <td width="80px"><font color="red">*</font>Metodología</td> <td width="250px" >
	               <select style="width: 140px;" name="filNumMetodo"  id="filNumMetodo"   onchange="javaScript:ajaxNivel();" >
	                  <option value="-99" >--Seleccione uno--</option>
					   <c:forEach begin="0"  items="${sessionScope.listaMetodo }" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.instNivelEvaluacionVO.insnivcodmetod}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
	               </select>
	             </td>
		   </c:when>
		     
		    <c:when test="${sessionScope.filtroEscalaNumVO.filNumniveval  == paramsVO.NVLEVA_NVL }">
	          <td width="80px"><font color="red">*</font>Nivel</td><td width="250px" >
	          
	             <select style="width: 140px;" name="filNumNivel"   id="filNumNivel"    onchange="javaScript:ajaxGrado();">
                      <option value="-99" >--Seleccione uno--</option>
					   <c:forEach begin="0" items="${sessionScope.listaNivel}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.filtroEscalaNumVO.filNumNivel }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
	             </select>
	             </td>
	       </c:when>
	       
	        <c:when test="${sessionScope.filtroEscalaNumVO.filNumniveval  == paramsVO.NVLEVA_GRD }">
	         <td> Grado</td><td width="250px" >
	           <select style="width: 140px;" name="filNumGrado" id="filNumGrado"   >
	                 <option value="-99" >--Seleccione uno--</option>
					   <c:forEach begin="0" items="${sessionScope.listaGrado  }" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.filtroEscalaNumVO.filNumGrado }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
	           </select>
	           </td>
	       </c:when>
	       
	       
	       <c:when test="${sessionScope.filtroEscalaNumVO.filNumniveval  == paramsVO.NVLEVA_SED_JORN }">
	          <td width="80px"><font color="red">*</font>Sede</td>
	          <td width="250px" >
	            <select style="width: 140px;" name="filNumSede"    id="filNumSede"      onchange="javaScript:ajaxJornada_1();" >
	              <option value="-9" >--Seleccione uno--</option>
					   <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.filtroEscalaNumVO.filNumSede }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					  </c:forEach> 
	            </select>
	           </td>
	          </tr>
			  <tr>
				<td width="80px"><font color="red">*</font>Jornada</td><td width="250px" >
				  <select style="width: 120px;" name="filNumJorn"    id="filNumJorn"  >
				    <option value="-9" >--Seleccione uno--</option>
					 <c:forEach begin="0" items="${sessionScope.listaJornada}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaNumVO.filNumJorn }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					 </c:forEach> 
				  </select>
				  </td> 
		   </c:when>
		   
		  <c:when test="${sessionScope.filtroEscalaNumVO.filNumniveval  == paramsVO.NVLEVA_SED_METD }">
	          <td width="80px"><font color="red">*</font>Sede</td><td width="250px" >
	              <select style="width: 140px;" name="filNumSede"    id="filNumSede"      onchange="javaScript:ajaxMetodologia_1();" >
	                <option value="-9" >--Seleccione uno--</option>
					 <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.filtroEscalaNumVO.filNumSede }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					 </c:forEach> 
	              </select>
	           </td>
	          </tr>
			  <tr><td width="80px"><font color="red">*</font>Metodología</td> <td width="250px" >
			    <select style="width: 140px;" name="filNumMetodo"  id="filNumMetodo"   onchange="javaScript:ajaxNivel();" >
			        <option value="-9" >--Seleccione uno--</option>
					 <c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.filtroEscalaNumVO.filNumMetodo }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					 </c:forEach> 
			    </select>
			    </td>
		   </c:when>
		   
		     <c:when test="${sessionScope.filtroEscalaNumVO.filNumniveval  == paramsVO.NVLEVA_SED_NVL }">
	            <td width="80px"><font color="red">*</font>Sede</td>
	            <td width="250px" > 
	             <select style="width: 140px;" name="filNumSede"    id="filNumSede" onchange="JavaScript:ajaxNivel_1();">
	                 <option value="-9" >--Seleccione uno--</option>
	                 <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.filtroEscalaNumVO.filNumSede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
	             </select>
	            </td>
	          </tr>
			  <tr>
				 <td width="80px"><font color="red">*</font>Nivel</td>
				 <td width="250px" >
				  <select style="width: 140px;" name="filNumNivel"   id="filNumNivel"     >
				    <option value="-9" >--Seleccione uno--</option> 
	                 <c:forEach begin="0" items="${sessionScope.listaNivel}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.filtroEscalaNumVO.filNumNivel}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
				  </select>
				  </td>
		   </c:when>
		   
		    <c:when test="${sessionScope.filtroEscalaNumVO.filNumniveval  == paramsVO.NVLEVA_SED_GRD }">
	          <td width="80px"><font color="red">*</font>Sede</td>
	            <td width="250px" >
	              <select style="width: 140px;" name="filNumSede"    id="filNumSede"      onchange="javaScript:ajaxGrado_1();" >
	                <option value="-9" >--Seleccione uno--</option> 
	                 <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.filtroEscalaNumVO.filNumSede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
	                </select>
	              </td>
	          </tr>
			  <tr>
			  <td> Grado</td><td width="250px" >
			      <select style="width: 140px;" name="filNumGrado" id="filNumGrado"   >
			        <option value="-9" >--Seleccione uno--</option> 
			          <c:forEach begin="0" items="${sessionScope.listaGrado}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaNumVO.filNumGrado}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
	              </select>
			    </td>
		   </c:when>
		   
		        		        
	       <c:when test="${sessionScope.filtroEscalaNumVO.filNumniveval  == paramsVO.NVLEVA_SED_JORN_METD }">
	             <td width="80px"><font color="red">*</font>Sede</td><td width="250px" >
	                <select style="width: 140px;" name="filNumSede"    id="filNumSede"      onchange="javaScript:ajaxJornada_1(); " >
	                <option value="-9" >--Seleccione uno--</option> 
			          <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaNumVO.filNumSede }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
	                </select>
	               </td>
				 </tr>
				 <tr>
				  <td width="80px"><font color="red">*</font>Jornada</td><td width="250px" >
				     <select style="width: 120px;" name="filNumJorn"    id="filNumJorn"      onchange="javaScript:ajaxMetodologia_1();" >
				     <option value="-9" >--Seleccione uno--</option> 
			          <c:forEach begin="0" items="${sessionScope.listaJornada}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaNumVO.filNumJorn  }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
				     </select>
				    </td> 
				   <td width="80px"><font color="red">*</font>Metodología</td> 
				   <td width="250px" >
				     <select style="width: 140px;" name="filNumMetodo"  id="filNumMetodo"    >
				     <option value="-9" >--Seleccione uno--</option> 
			          <c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaNumVO.filNumMetodo}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
				     </select>
				  </td>
		     </c:when>
		     
		     
		      <c:when test="${sessionScope.filtroEscalaNumVO.filNumniveval  == paramsVO.NVLEVA_SED_JORN_NVL }">
	             <td width="80px"><font color="red">*</font>Sede</td>
	             <td width="250px" >
	                <select style="width: 140px;" name="filNumSede"    id="filNumSede"      onchange="javaScript:ajaxJornada_1();" >
	                 <option value="-9" >--Seleccione uno--</option> 
			          <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaNumVO.filNumSede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
				     </select>
	               </td>
				 </tr>
				 <tr>
				  <td width="80px"><font color="red">*</font>Jornada</td><td width="250px" >
				    <select style="width: 120px;" name="filNumJorn"    id="filNumJorn"      onchange="javaScript:ajaxNivel_1();" >
				      <option value="-9" >--Seleccione uno--</option> 
			          <c:forEach begin="0" items="${sessionScope.listaJornada}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaNumVO.filNumJorn}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
				    </select>
				  </td> 
				  <td width="80px"><font color="red">*</font>Nivel</td>
				  <td width="250px" >
				    <select style="width: 140px;" name="filNumNivel"   id="filNumNivel"    onchange="javaScript:ajaxGrado();">
				       <option value="-9" >--Seleccione uno--</option> 
			           <c:forEach begin="0" items="${sessionScope.listaNivel}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaNumVO.filNumNivel}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
				    </select>
				  </td>
				  
		     </c:when>
		     
		     
		     
		     
		     <c:when test="${sessionScope.filtroEscalaNumVO.filNumniveval  == paramsVO.NVLEVA_SED_JORN_GRD }">
	             <td width="80px"><font color="red">*</font>Sede</td>
	             <td width="250px" >
	                <select style="width: 140px;" name="filNumSede"    id="filNumSede"       onchange="javaScript:ajaxJornada_1();" >
	                   <option value="-9" >--Seleccione uno--</option> 
			           <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaNumVO.filNumNivel}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
				  
	                </select>
	               </td>
				 </tr>
				 <tr>
				 <td width="80px"><font color="red">*</font>Jornada</td>
				 <td width="250px" >
				    <select style="width: 120px;" name="filNumJorn"    id="filNumJorn"      onchange="javaScript:ajaxGrado_1();" >
				       <option value="-9" >--Seleccione uno--</option> 
			           <c:forEach begin="0" items="${sessionScope.listaJornada}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaNumVO.filNumGrado}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
				  
				    </select>
				   </td> 
				 <td> Grado</td>
				 <td width="250px" >
				   <select style="width: 140px;" name="filNumGrado" id="filNumGrado"   >
				     <option value="-9" >--Seleccione uno--</option> 
			           <c:forEach begin="0" items="${sessionScope.listaGrado}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaNumVO.filNumGrado}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
				  
				   </select>
				  </td>
				  
		     </c:when>
		    
	      
	      
	       <c:when test="${sessionScope.filtroEscalaNumVO.filNumniveval  == paramsVO.NVLEVA_SED_METD_NVL  }">
	             <td width="80px"><font color="red">*</font>Sede</td>
	             <td width="250px" >
	               <select style="width: 140px;" name="filNumSede"    id="filNumSede"      onchange="javaScript:   ajaxMetodologia_1(); " >
	                 <option value="-9" >--Seleccione uno--</option> 
			           <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaNumVO.filNumSede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
				   </select>
				   </td>
				 </tr>
				 <tr>
				 <td width="80px"><font color="red">*</font>Metodología</td> 
				 <td width="250px" >
				   <select style="width: 140px;" name="filNumMetodo"  id="filNumMetodo"   onchange="javaScript:ajaxNivel_1();" >
				    <option value="-9" >--Seleccione uno--</option> 
			           <c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaNumVO.filNumMetodo}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
				   </select>
				 </td>
		         <td width="80px"><font color="red">*</font>Nivel</td>
		         <td width="250px" >
		             <select style="width: 140px;" name="filNumNivel"   id="filNumNivel"    onchange="javaScript:ajaxGrado();">
		              <option value="-9" >--Seleccione uno--</option> 
			           <c:forEach begin="0" items="${sessionScope.listaNivel}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaNumVO.filNumNivel}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
				   </select>
		            </td>
				 
		     </c:when>
		     
		     
		     
		     
		          		        
	       <c:when test="${sessionScope.filtroEscalaNumVO.filNumniveval  == paramsVO.NVLEVA_SED_METD_GRD }">
	             <td width="80px"><font color="red">*</font>Sede</td>
	              <td width="250px" >
	              <select style="width: 140px;" name="filNumSede"    id="filNumSede"    onchange="javaScript:ajaxMetodologia_1();" >
	                 <option value="-9" >--Seleccione uno--</option> 
			           <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaNumVO.filNumSede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
	               </select>
	             </td>
				 </tr>
				 <tr>
				   <td width="80px"><font color="red">*</font>Metodología</td> 
				   <td width="250px" >
				     <select style="width: 140px;" name="filNumMetodo"  id="filNumMetodo"  >
				      <option value="-9" >--Seleccione uno--</option> 
			           <c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaNumVO.filNumMetodo }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
				     </select>
				   <td>Grado</td>
				    <td width="250px" >
				    <select style="width: 140px;" name="filNumGrado" id="filNumGrado"   >
				        <option value="-9" >--Seleccione uno--</option> 
			           <c:forEach begin="0" items="${sessionScope.listaGrado}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaNumVO.filNumGrado}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
				    </select>
				  </td>
		     </c:when>
		     
		     
		     
		     
	       <c:when test="${sessionScope.filtroEscalaNumVO.filNumniveval  == paramsVO.NVLEVA_SED_NVL_GRD }">
	              <td width="80px"><font color="red">*</font>Sede</td>
	              <td width="250px" >
	               <select style="width: 140px;" name="filNumSede"    id="filNumSede"      onchange="javaScript:ajaxNivel_1();" >
	                 <option value="-9" >--Seleccione uno--</option> 
			           <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaNumVO.filNumSede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
	               </select>
	              </td>
				 </tr>
				 <tr><td width="80px"><font color="red">*</font>Nivel</td>
				      <td width="250px" >
				         <select style="width: 140px;" name="filNumNivel"   id="filNumNivel"    onchange="javaScript:ajaxGrado_1();">
				           <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaNivel}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaNumVO.filNumNivel}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
				         </select>
				        </td>
				     <td> Grado</td>
				      <td width="250px" >
				      <select style="width: 140px;" name="filNumGrado" id="filNumGrado"   >
				        <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaGrado}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaNumVO.filNumGrado }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
				     </select>
				     </td>
		     </c:when>
		     
	     
		     
	       <c:when test="${sessionScope.filtroEscalaNumVO.filNumniveval  == paramsVO.NVLEVA_SED_JORD_METD_NVL }">
	             <td width="80px"><font color="red">*</font>Sede</td>
	             <td width="250px" >
	                <select style="width: 140px;" name="filNumSede"    id="filNumSede"      onchange="javaScript: borrar_combo2(document.frmFiltro.filMetodo);borrar_combo2(document.frmFiltro.filNivel);ajaxJornada_1();" >
	                      <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaNumVO.filNumSede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
	                </select>
	               </td>
				 </tr>
				 <tr>
				  <td width="80px"><font color="red">*</font>Jornada</td>
				  <td width="250px" >
				    <select style="width: 120px;" name="filNumJorn"    id="filNumJorn"      onchange="javaScript:ajaxMetodologia_1();" >
				        <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaJornada}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaNumVO.filNumJorn }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
				    </select>
				   </td> 
				  <td width="80px"><font color="red">*</font>Metodología</td> <td width="250px" >
				   <select style="width: 140px;" name="filNumMetodo"  id="filNumMetodo"   onchange="javaScript:ajaxNivel_1();" >
				        <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaNumVO.filNumMetodo}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
				   </select>
				  </td>
		         </tr>
		         <tr><td width="80px"><font color="red">*</font>Nivel</td>
		             <td width="250px" >
		               <select style="width: 140px;" style="width: 140px;" name="filNumNivel"   id="filNumNivel"    >
	   			         <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaNivel}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaNumVO.filNumNivel}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
		               </select>
		               </td>
		     </c:when>
		     
		       <c:when test="${sessionScope.filtroEscalaNumVO.filNumniveval  == paramsVO.NVLEVA_SED_JORD_METD_GRD }">
	              <td width="80px"><font color="red">*</font>Sede</td>
	              <td width="250px" >
	                <select style="width: 140px;"  name="filNumSede"    id="filNumSede"      onchange="javaScript: borrar_combo2(document.frmFiltro.filMetodo);  borrar_combo2(document.frmFiltro.filGrado); ajaxJornada_1();" >
	                     <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaNumVO.filNumSede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
	                </select>
	              </td>
				 </tr>
				 <tr>
				  <td width="80px"><font color="red">*</font>Jornada</td>
				  <td width="250px" >
				    <select style="width: 120px;" name="filNumJorn"    id="filNumJorn"      onchange="javaScript:ajaxMetodologia_1();" >
				          <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaJornada}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaNumVO.filNumJorn}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
				    </select>
				   </td> 
				  <td width="80px"><font color="red">*</font>Metodología</td> 
				    <td width="250px" >
				      <select style="width: 140px;" name="filNumMetodo"  id="filNumMetodo"  onchange="JavaScript:ajaxGrado_1();" >
				          <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaNumVO.filNumMetodo}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
				      
				      </select>
				      </td>
		         </tr>
		         <tr><td> Grado</td>
		             <td width="250px" >
		               <select style="width: 140px;" name="filNumGrado" id="filNumGrado" >
		                  <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaGrado}" var="sed">
						    <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaNumVO.filNumGrado}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					       </c:forEach>
		               </select>
		              </td>
		      </c:when>
		      
		       <c:when test="${sessionScope.filtroEscalaNumVO.filNumniveval  == paramsVO.NVLEVA_SED_METD_NVL_GRD  }">
	              <td width="80px"><font color="red">*</font>Sede</td>
	              <td width="250px" >
	                <select style="width: 140px;" name="filNumSede"    id="filNumSede"      onchange="javaScript:borrar_combo2(document.frmFiltro.filNivel); borrar_combo2(document.frmFiltro.filGrado); ajaxMetodologia_1();" >
	                    <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaNumVO.filNumSede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
	                </select>
	               </td>
				 </tr>
				 <tr>
				  <td width="80px"><font color="red">*</font>Metodología</td>
				  <td width="250px" >
				    <select style="width: 140px;" name="filNumMetodo"  id="filNumMetodo"   onchange="javaScript:ajaxNivel_1();" >
				       <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaNumVO.filNumMetodo}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
				    </select>
				   </td>
                  <td width="80px"><font color="red">*</font>Nivel</td>
                  <td width="250px" >
                     <select style="width: 140px;" style="width: 140px;" name="filNumNivel"   id="filNumNivel"    onchange="javaScript:ajaxGrado_1();">
                          <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaNivel}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaNumVO.filNumNivel  }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
                     </select>
                    </td>
                 </tr>
		         <tr>
		          <td> Grado</td>
		          <td width="250px" >
		            <select style="width: 140px;" name="filNumGrado" id="filNumGrado" >
		                <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaGrado}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaNumVO.filNumGrado  }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
		            </select>
		           </td>
		      </c:when>
		      
		        <c:when test="${sessionScope.filtroEscalaNumVO.filNumniveval  == paramsVO.NVLEVA_SED_JORD_METD_NVL_GRD }">
	              <td width="80px"><font color="red">*</font>Sede</td>
	                 <td width="250px" >
	                    <select style="width: 140px;" name="filNumSede"    id="filNumSede"      onchange="javaScript:ajaxJornada_1();" >
	                       <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						    <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaNumVO.filNumSede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
	                    </select>
	                 </td>
				 </tr>
				 <tr>
				  <td width="80px"><font color="red">*</font>Jornada</td>
				  <td width="250px" >
				       <select style="width: 120px;" name="filNumJorn"    id="filNumJorn"      onchange="javaScript:ajaxMetodologia_1();" >
				          <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaJornada}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaNumVO.filNumJorn}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
				       </select></td> 
				  <td width="80px"><font color="red">*</font>Metodología</td> 
				  <td width="250px" ><select style="width: 140px;" name="filNumMetodo"  id="filNumMetodo"   onchange="javaScript:ajaxNivel_1();" >
				       <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaNumVO.filNumMetodo }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
				  </select></td>
                 </tr>
		         <tr>
		          <td width="80px"><font color="red">*</font>Nivel</td>
		          <td width="250px" >
		            <select style="width: 140px;" style="width: 140px;" name="filNumNivel"   id="filNumNivel"    onchange="javaScript:ajaxGrado_1();">
		              <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaNivel}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaNumVO.filNumNivel}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					  </c:forEach>
		            </select>
		          </td>
                  <td> Grado</td>
                    <td width="250px" >
                      <select style="width: 140px;" name="filNumGrado" id="filNumGrado" >
                          <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaGrado}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaNumVO.filNumGrado }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
                      </select>
                     </td>
		      </c:when>
		   </c:choose>
		    </tr>
		   </table>
        </form>  
        
        <div style="width:100%;height:120px;overflow:auto;vertical-align:top;background-color:#ffffff;">
	    <!-- LISTA DE RESULTADOS -->
        <table border="1" align="center" width="100%" cellpadding="0" cellspacing="0" bordercolor="gray"  class="table01" style="text-align: left;" >
			 	<caption>Lista Escala Numerica/Porcentual</caption>
			 	<c:if test="${empty requestScope.listaAllEscalaNum}"><tr><th class="Fila1" colspan='6'>No hay datos</th></tr></c:if>
				<c:if test="${!empty requestScope.listaAllEscalaNum}">
					<tr> 
						<th class="EncabezadoColumna" align="center" rowspan="2" >&nbsp;</th>
						<th class="EncabezadoColumna" align="center" rowspan="2">&nbsp;</th>
						<th class="EncabezadoColumna" colspan="2" align="center" >Valor </th> 
						<th class="EncabezadoColumna" colspan="1" align="center">Equivalencia</th>
						<th class="EncabezadoColumna" rowspan="2" align="center">Descripción</th>
						<th class="EncabezadoColumna" rowspan="2" align="center">Orden</th>
					</tr>
					<tr>  
						<th class="EncabezadoColumna" align="center" align="center"	> Minimo</th>
						<th class="EncabezadoColumna" align="center" align="center" > Maximo</th>
						<th class="EncabezadoColumna" align="center"> MEN</th>
					 <!-- 	<th class="EncabezadoColumna" align="center"> Institución</th>  -->
					</tr>
					
					
					<c:forEach begin="0" items="${requestScope.listaAllEscalaNum  }" var="lista" varStatus="st">
						<tr>
						<th class='Fila<c:out value="${st.count%2}"/>' width="10px;"><c:out value="${st.count}"/>
						</th>
						  <th class='Fila<c:out value="${st.count%2}"/>' width="40px;">
							<a href='javaScript:editar(<c:out value="${lista.insnumcodinst}"/>,<c:out value="${lista.insnumvigencia}"/>,<c:out value="${lista.insnumniveval}"/>,<c:out value="${lista.insnumequinst }"/>,<c:out value="${lista.insnumequmen }"/>,<c:out value="${lista.insnumcodsede }"/>,<c:out value="${lista.insnumcodjorn}"/>,<c:out value="${lista.insnumcodmetod} "/>,<c:out value="${lista.insnumcodnivel}"/>,<c:out value="${lista.insnumcodgrado }"/>  ,<c:out value="${lista.insnumvalmax}"/>,<c:out value="${lista.insnumvalmin}"/>);'><img border='0' src='<c:url value="/etc/img/editar.png"/>' width='15' height='15'></a>
							<c:if test="${sessionScope.NivelPermiso==2 }">
							<a href='javaScript:eliminar(<c:out value="${lista.insnumcodinst}"/>,<c:out value="${lista.insnumvigencia}"/>,<c:out value="${lista.insnumniveval}"/>,<c:out value="${lista.insnumequinst }"/>,<c:out value="${lista.insnumequmen }"/>,<c:out value="${lista.insnumcodsede }"/>,<c:out value="${lista.insnumcodjorn}"/>,<c:out value="${lista.insnumcodmetod} "/>,<c:out value="${lista.insnumcodnivel}"/>,<c:out value="${lista.insnumcodgrado }"/>  ,<c:out value="${lista.insnumvalmax}"/>,<c:out value="${lista.insnumvalmin}"/>);'><img border='0' src='<c:url value="/etc/img/eliminar.png"/>' width='15' height='15'></a></c:if>
						   </th>
						   	<td class='Fila<c:out value="${st.count%2}"  />' align="right"   >&nbsp;<c:out value="${lista.insnumvalmin}"/>&nbsp;&nbsp;</td> 
							<td class='Fila<c:out value="${st.count%2}"/>' align="right"  >&nbsp;<c:out value="${lista.insnumvalmax}"/>&nbsp;&nbsp;</td>
							<td class='Fila<c:out value="${st.count%2}"/>'  >&nbsp;<c:out value="${lista.insnumequmenNom}"/></td>
 <!-- 							<td class='Fila<c:out value="${st.count%2}"/>'  >&nbsp;<c:out value="${lista.insnumequinstNom}"/> </td> -->
							<td class='Fila<c:out value="${st.count%2}"/>'  align="center" ><textarea readonly="readonly" cols="20" rows="1" style="height: 15px;" ><c:out value="${lista.insnumdescripcion}"/></textarea></td>
							<td class='Fila<c:out value="${st.count%2}"/>'  >&nbsp;<c:out value="${lista.insnumorden}"/></td>
						
						</tr>
					</c:forEach>
				</c:if>
								<tr style="display:none"><td><iframe name="frameAjaxFiltro" id="frameAjaxFiltro"></iframe></td></tr>
			</table>
			</div>
	 
	</body>
	
<script>
//llamado al cargar la pagina para que el combo de localidades esté lleno

  
	      
	       
</script>
</html>