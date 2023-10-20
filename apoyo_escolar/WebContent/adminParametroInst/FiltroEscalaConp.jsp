<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean  id="filtroEscalaVO" class="siges.adminParamsInst.vo.FiltroEscalaVO" scope="session"/>
<jsp:useBean  id="paramsVO"   class="siges.adminParamsInst.vo.ParamsVO" scope="page"/>
<html>
<head>
  
<script languaje="javaScript">

    
 	function borrar_combo2(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("--Seleccione uno","-99");
	}
	 
	 
  function editar(inst, vig, nvlEval, codcodigo, sede, jorn, metodo, nivel, grado){
	 if(document.frmFiltro.param){
			 document.frmFiltro.param[0].value = inst;
			 document.frmFiltro.param[1].value = vig;
			 document.frmFiltro.param[2].value = nvlEval;
			 document.frmFiltro.param[3].value = codcodigo; 
			 document.frmFiltro.param[4].value = sede;
			 document.frmFiltro.param[5].value = jorn;
			 document.frmFiltro.param[6].value = metodo;
			 document.frmFiltro.param[7].value = nivel;
			 document.frmFiltro.param[8].value = grado;
			 document.frmFiltro.cmd.value = document.frmFiltro.EDITAR.value;
			 document.frmFiltro.submit();
		}
	}
	
	
  function eliminar(inst, vig, nvlEval,  codcodigo, sede, jorn, metodo, nivel, grado){
   if(document.frmFiltro.param  && confirm("�Esta seguro de eliminar esta informaci�n?")){
				 document.frmFiltro.param[0].value = inst;
			 document.frmFiltro.param[1].value = vig;
			 document.frmFiltro.param[2].value = nvlEval;
			 document.frmFiltro.param[3].value = codcodigo; 
			 document.frmFiltro.param[4].value = sede;
			 document.frmFiltro.param[5].value = jorn;
			 document.frmFiltro.param[6].value = metodo;
			 document.frmFiltro.param[7].value = nivel;
			 document.frmFiltro.param[8].value = grado;
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
      validarLista(forma.filVigencia,'- Vigencia');
      if(forma.filSede){
      validarLista(forma.filSede,'- Sede');
      }
      if(forma.filJorn){
      validarLista(forma.filJorn,'- Jornada');
      }
      
      if(forma.filMetodo){
      validarLista(forma.filMetodo,'- Metodologia');
      }
       if(forma.filNivel){
      validarLista(forma.filNivel,'- Nivel');
      }
      
      if(forma.filGrado){
      validarLista(forma.filGrado,'- Grado');
      }
      
    }catch(e){
      alert(e);
    }
  }
	   
	   
	   
	   
	
	
	function ajaxJornada_1(){
	 if(document.frmFiltro.filSede){
	  borrar_combo2(document.frmFiltro.filJorn);
	  document.frmAjaxFiltro.target = 'frameAjaxFiltro';
	  document.frmAjaxFiltro.ajax[0].value = document.frmFiltro.filSede.value;
	  document.frmAjaxFiltro.cmd.value = document.frmAjaxFiltro.CMD_AJAX_JORD.value;
	  document.frmAjaxFiltro.submit();
	 }
	}
	
	
	function ajaxMetodologia_1(){
	 if(document.frmFiltro.filSede){
	  borrar_combo2(document.frmFiltro.filMetodo);
	  document.frmAjaxFiltro.target = 'frameAjaxFiltro';
	  document.frmAjaxFiltro.ajax[0].value = document.frmFiltro.filSede.value;
	  document.frmAjaxFiltro.cmd.value = document.frmAjaxFiltro.CMD_AJAX_METD.value;
	  document.frmAjaxFiltro.submit();
	 }
	}
	
  function ajaxNivel_1(){
	 if(document.frmFiltro.filSede){
	  borrar_combo2(document.frmFiltro.filNivel);
	  document.frmAjaxFiltro.target = 'frameAjaxFiltro';
	   if(document.frmFiltro.filMetodo){
	    document.frmAjaxFiltro.ajax[0].value = document.frmFiltro.filMetodo.value;
	    document.frmAjaxFiltro.cmd.value = document.frmAjaxFiltro.CMD_AJAX_NVL.value;
	  if( document.frmFiltro.filMetodo.value > -99){
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
	 if(document.frmFiltro.filGrado){
	  borrar_combo2(document.frmFiltro.filGrado);
	  document.frmAjaxFiltro.target = 'frameAjaxFiltro';
	   
	   //Verifique que existe la metodologia
	   if(document.frmFiltro.filMetodo){
	    document.frmAjaxFiltro.ajax[0].value = document.frmFiltro.filMetodo.value;
	   }else{
	    document.frmAjaxFiltro.ajax[0].value = -99;
	   }

	   //Verifique que existe el nivel	   
	   if(document.frmFiltro.filNivel){
	    document.frmAjaxFiltro.ajax[1].value = document.frmFiltro.filNivel.value;
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
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_ESCL_CONCEPT }"/>'>
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
		
		 
	
	    <input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_ESCL_CONCEPT}"/>'>
		<input type="hidden" name="cmd" value=''>
		<c:forEach begin="0" end="12"><input type="hidden" name="param" value=''></c:forEach>
		<input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
		<input type="hidden" name="ELIMINAR" value='<c:out value="${paramsVO.CMD_ELIMINAR}"/>'>
		<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="filInst" value='<c:out value="${sessionScope.filtroEscalaVO.filInst}"/>'>
		<input type="hidden" name="filInst_" value='<c:out value="${sessionScope.filtroEscalaVO.filInst}"/>'>
	    <input type="hidden" name='filSede_' id='filSede_' value='<c:out value="${sessionScope.filtroEscalaVO.filSede}"/>'>
	    <input type="hidden" name='filJorn_' id='filJorn__' value='<c:out value="${sessionScope.filtroEscalaVO.filJorn}"/>'>
		<input type="hidden" name='filMetodo_' id='filMetodo_' value='<c:out value="${sessionScope.filtroEscalaVO.filMetodo}"/>'>
	    <input type="hidden" name='filNivel_' id='filNivel_' value='<c:out value="${sessionScope.filtroEscalaVO.filNivel}"/>'>
	    <input type="hidden" name='filGrado_' id='filGrado_' value='<c:out value="${sessionScope.filtroEscalaVO.filGrado}"/>'>
 
         <!-- FILTRO DE BUSQUDA -->
         <table border="0" align="center" width="95%" cellpadding="2" cellspacing="2" bordercolor="gray">
		     <tr> <td   bgcolor="#FFFFFF"><input class='boton' name="cmd1" type="button" value="Buscar" onClick="buscar()"></td>
			 </tr>
		     <tr><td width="80px"><font color="red">*</font>Vigencia</td>
		         <td width="250px" ><select name="filVigencia" style="width: 60px;">
					   <option value="-99"> --  //  -- </option>
					     <c:forEach  begin="0" items="${sessionScope.listaVigencia }" var="lista" >
					       <option value='<c:out  value="${ lista.codigo}"/>' <c:if test="${lista.codigo  == filtroEscalaVO.filVigencia }">SELECTED</c:if > ><c:out value="${lista.nombre}" /> </option>
					    </c:forEach>
					   </select>
				 </td>
				
				
		 
		 
		 

  <c:choose> 
	       <c:when test="${sessionScope.filtroEscalaVO.filniveval  == paramsVO.NVLEVA_INST }">
		        <td width="100px">&nbsp;</td><td width="100px">&nbsp;</td>
		   </c:when>
		   
		   <c:when test="${sessionScope.filtroEscalaVO.filniveval  == paramsVO.NVLEVA_SED  }">
	             <td width="80px"><font color="red">*</font>Sede</td><td width="250px" >
	                <select style="width: 140px;" name="filSede"    id="filSede"      onchange="javaScript:ajaxJornada();" >
	                <option value="-99" >--Seleccione uno--</option>
					   <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.filtroEscalaVO.filSede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>  
	                </select>
	              </td>
		   </c:when>

           <c:when test="${sessionScope.filtroEscalaVO.filniveval  == paramsVO.NVLEVA_JORN }">
	               <td width="80px"><font color="red">*</font> Jornada</td><td width="250px" >
	                <select style="width: 120px;" name="filJorn"     id="filJorn"        >
	                  <option value="-99" >--Seleccione uno--</option>
					   <c:forEach begin="0" items="${sessionScope.listaJornada}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.filtroEscalaVO.filJorn}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>  
	                </select></td>
			</c:when>
		     
		   <c:when test="${sessionScope.filtroEscalaVO.filniveval  == paramsVO.NVLEVA_METD }">
	            <td width="80px"><font color="red">*</font>Metodolog�a</td> <td width="250px" >
	               <select style="width: 140px;" name="filMetodo"  id="filMetodo"   onchange="javaScript:ajaxNivel();" >
	                  <option value="-99" >--Seleccione uno--</option>
					   <c:forEach begin="0" items="${ sessionScope.listaMetodo }" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.filtroEscalaVO.filMetodo}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
	               </select>
	             </td>
		   </c:when>
		     
		    <c:when test="${sessionScope.filtroEscalaVO.filniveval  == paramsVO.NVLEVA_NVL }">
	          <td width="80px"><font color="red">*</font>Nivel</td><td width="250px" >
	          
	             <select style="width: 140px;" name="filNivel"   id="filNivel"    onchange="javaScript:ajaxGrado();">
                      <option value="-99" >--Seleccione uno--</option>
					   <c:forEach begin="0" items="${sessionScope.listaNivel}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.filtroEscalaVO.filNivel }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
	             </select>
	             </td>
	       </c:when>
	       
	        <c:when test="${sessionScope.filtroEscalaVO.filniveval  == paramsVO.NVLEVA_GRD }">
	         <td> Grado</td><td width="250px" >
	           <select style="width: 140px;" name="filGrado" id="filGrado"   >
	                 <option value="-99" >--Seleccione uno--</option>
					   <c:forEach begin="0" items="${sessionScope.listaGrado  }" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.filtroEscalaVO.filGrado }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
	           </select>
	           </td>
	       </c:when>
	       
	       
	       <c:when test="${sessionScope.filtroEscalaVO.filniveval  == paramsVO.NVLEVA_SED_JORN }">
	          <td width="80px"><font color="red">*</font>Sede</td>
	          <td width="250px" >
	            <select style="width: 140px;" name="filSede"    id="filSede"      onchange="javaScript:ajaxJornada_1();" >
	              <option value="-9" >--Seleccione uno--</option>
					   <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.filtroEscalaVO.filSede }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					  </c:forEach> 
	            </select>
	           </td>
	          </tr>
			  <tr>
				<td width="80px"><font color="red">*</font>Jornada</td><td width="250px" >
				  <select style="width: 120px;" name="filJorn"    id="filJorn"  >
				    <option value="-9" >--Seleccione uno--</option>
					 <c:forEach begin="0" items="${sessionScope.listaJornada}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaVO.filJorn }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					 </c:forEach> 
				  </select>
				  </td> 
		   </c:when>
		   
		  <c:when test="${sessionScope.filtroEscalaVO.filniveval  == paramsVO.NVLEVA_SED_METD }">
	          <td width="80px"><font color="red">*</font>Sede</td><td width="250px" >
	              <select style="width: 140px;" name="filSede"    id="filSede"      onchange="javaScript:ajaxMetodologia_1();" >
	                <option value="-9" >--Seleccione uno--</option>
					 <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.filtroEscalaVO.filSede }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					 </c:forEach> 
	              </select>
	           </td>
	          </tr>
			  <tr><td width="80px"><font color="red">*</font>Metodolog�a</td> <td width="250px" >
			    <select style="width: 140px;" name="filMetodo"  id="filMetodo"   onchange="javaScript:ajaxNivel();" >
			        <option value="-9" >--Seleccione uno--</option>
					 <c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.filtroEscalaVO.filMetodo }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					 </c:forEach> 
			    </select>
			    </td>
		   </c:when>
		   
		     <c:when test="${sessionScope.filtroEscalaVO.filniveval  == paramsVO.NVLEVA_SED_NVL }">
	            <td width="80px"><font color="red">*</font>Sede</td>
	            <td width="250px" > 
	             <select style="width: 140px;" name="filSede"    id="filSede" onchange="JavaScript:ajaxNivel_1();">
	                 <option value="-9" >--Seleccione uno--</option>
	                 <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.filtroEscalaVO.filSede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
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
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.filtroEscalaVO.filNivel}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
				  </select>
				  </td>
		   </c:when>
		   
		    <c:when test="${sessionScope.filtroEscalaVO.filniveval  == paramsVO.NVLEVA_SED_GRD }">
	          <td width="80px"><font color="red">*</font>Sede</td>
	            <td width="250px" >
	              <select style="width: 140px;" name="filSede"    id="filSede"      onchange="javaScript:ajaxGrado_1();" >
	                <option value="-9" >--Seleccione uno--</option> 
	                 <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.filtroEscalaVO.filSede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
	                </select>
	              </td>
	          </tr>
			  <tr>
			  <td> Grado</td><td width="250px" >
			      <select style="width: 140px;" name="filGrado" id="filGrado"   >
			        <option value="-9" >--Seleccione uno--</option> 
			          <c:forEach begin="0" items="${sessionScope.listaGrado}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaVO.filGrado}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
	              </select>
			    </td>
		   </c:when>
		   
		        		        
	       <c:when test="${sessionScope.filtroEscalaVO.filniveval  == paramsVO.NVLEVA_SED_JORN_METD }">
	             <td width="80px"><font color="red">*</font>Sede</td><td width="250px" >
	                <select style="width: 140px;" name="filSede"    id="filSede"      onchange="javaScript:ajaxJornada_1(); " >
	                <option value="-9" >--Seleccione uno--</option> 
			          <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaVO.filSede }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
	                </select>
	               </td>
				 </tr>
				 <tr>
				  <td width="80px"><font color="red">*</font>Jornada</td><td width="250px" >
				     <select style="width: 120px;" name="filJorn"    id="filJorn"      onchange="javaScript:ajaxMetodologia_1();" >
				     <option value="-9" >--Seleccione uno--</option> 
			          <c:forEach begin="0" items="${sessionScope.listaJornada}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaVO.filJorn  }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
				     </select>
				    </td> 
				   <td width="80px"><font color="red">*</font>Metodolog�a</td> 
				   <td width="250px" >
				     <select style="width: 140px;" name="filMetodo"  id="filMetodo"    >
				     <option value="-9" >--Seleccione uno--</option> 
			          <c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaVO.filMetodo}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
				     </select>
				  </td>
		     </c:when>
		     
		     
		      <c:when test="${sessionScope.filtroEscalaVO.filniveval  == paramsVO.NVLEVA_SED_JORN_NVL }">
	             <td width="80px"><font color="red">*</font>Sede</td>
	             <td width="250px" >
	                <select style="width: 140px;" name="filSede"    id="filSede"      onchange="javaScript:ajaxJornada_1();" >
	                 <option value="-9" >--Seleccione uno--</option> 
			          <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaVO.filSede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
				     </select>
	               </td>
				 </tr>
				 <tr>
				  <td width="80px"><font color="red">*</font>Jornada</td><td width="250px" >
				    <select style="width: 120px;" name="filJorn"    id="filJorn"      onchange="javaScript:ajaxNivel_1();" >
				      <option value="-9" >--Seleccione uno--</option> 
			          <c:forEach begin="0" items="${sessionScope.listaJornada}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaVO.filJorn}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
				    </select>
				  </td> 
				  <td width="80px"><font color="red">*</font>Nivel</td>
				  <td width="250px" >
				    <select style="width: 140px;" name="filNivel"   id="filNivel"    onchange="javaScript:ajaxGrado();">
				       <option value="-9" >--Seleccione uno--</option> 
			           <c:forEach begin="0" items="${sessionScope.listaNivel}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaVO.filNivel}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
				    </select>
				  </td>
				  
		     </c:when>
		     
		     
		     
		     
		     <c:when test="${sessionScope.filtroEscalaVO.filniveval  == paramsVO.NVLEVA_SED_JORN_GRD }">
	             <td width="80px"><font color="red">*</font>Sede</td>
	             <td width="250px" >
	                <select style="width: 140px;" name="filSede"    id="filSede"       onchange="javaScript:ajaxJornada_1();" >
	                   <option value="-9" >--Seleccione uno--</option> 
			           <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaVO.filNivel}">selected</c:if>><c:out value="${sed.nombre}"/></option>
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
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaVO.filGrado}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
				  
				    </select>
				   </td> 
				 <td> Grado</td>
				 <td width="250px" >
				   <select style="width: 140px;" name="filGrado" id="filGrado"   >
				     <option value="-9" >--Seleccione uno--</option> 
			           <c:forEach begin="0" items="${sessionScope.listaGrado}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaVO.filGrado}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
				  
				   </select>
				  </td>
				  
		     </c:when>
		    
	      
	      
	       <c:when test="${sessionScope.filtroEscalaVO.filniveval  == paramsVO.NVLEVA_SED_METD_NVL  }">
	             <td width="80px"><font color="red">*</font>Sede</td>
	             <td width="250px" >
	               <select style="width: 140px;" name="filSede"    id="filSede"      onchange="javaScript:   ajaxMetodologia_1(); " >
	                 <option value="-9" >--Seleccione uno--</option> 
			           <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaVO.filSede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
				   </select>
				   </td>
				 </tr>
				 <tr>
				 <td width="80px"><font color="red">*</font>Metodolog�a</td> 
				 <td width="250px" >
				   <select style="width: 140px;" name="filMetodo"  id="filMetodo"   onchange="javaScript:ajaxNivel_1();" >
				    <option value="-9" >--Seleccione uno--</option> 
			           <c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaVO.filMetodo}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
				   </select>
				 </td>
		         <td width="80px"><font color="red">*</font>Nivel</td>
		         <td width="250px" >
		             <select style="width: 140px;" name="filNivel"   id="filNivel"    onchange="javaScript:ajaxGrado();">
		              <option value="-9" >--Seleccione uno--</option> 
			           <c:forEach begin="0" items="${sessionScope.listaNivel}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaVO.filNivel}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
				   </select>
		            </td>
				 
		     </c:when>
		     
		     
		     
		     
		          		        
	       <c:when test="${sessionScope.filtroEscalaVO.filniveval  == paramsVO.NVLEVA_SED_METD_GRD }">
	             <td width="80px"><font color="red">*</font>Sede</td>
	              <td width="250px" >
	              <select style="width: 140px;" name="filSede"    id="filSede"    onchange="javaScript:ajaxMetodologia_1();" >
	                 <option value="-9" >--Seleccione uno--</option> 
			           <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaVO.filSede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
	               </select>
	             </td>
				 </tr>
				 <tr>
				   <td width="80px"><font color="red">*</font>Metodolog�a</td> 
				   <td width="250px" >
				     <select style="width: 140px;" name="filMetodo"  id="filMetodo"  >
				      <option value="-9" >--Seleccione uno--</option> 
			           <c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaVO.filMetodo }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
				     </select>
				   <td>Grado</td>
				    <td width="250px" >
				    <select style="width: 140px;" name="filGrado" id="filGrado"   >
				        <option value="-9" >--Seleccione uno--</option> 
			           <c:forEach begin="0" items="${sessionScope.listaGrado}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaVO.filGrado}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
				    </select>
				  </td>
		     </c:when>
		     
		     
		     
		     
	       <c:when test="${sessionScope.filtroEscalaVO.filniveval  == paramsVO.NVLEVA_SED_NVL_GRD }">
	              <td width="80px"><font color="red">*</font>Sede</td>
	              <td width="250px" >
	               <select style="width: 140px;" name="filSede"    id="filSede"      onchange="javaScript:ajaxNivel_1();" >
	                 <option value="-9" >--Seleccione uno--</option> 
			           <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaVO.filSede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
	               </select>
	              </td>
				 </tr>
				 <tr><td width="80px"><font color="red">*</font>Nivel</td>
				      <td width="250px" >
				         <select style="width: 140px;" name="filNivel"   id="filNivel"    onchange="javaScript:ajaxGrado_1();">
				           <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaNivel}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaVO.filNivel}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
				         </select>
				        </td>
				     <td> Grado</td>
				      <td width="250px" >
				      <select style="width: 140px;" name="filGrado" id="filGrado"   >
				        <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaGrado}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaVO.filGrado }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
				     </select>
				     </td>
		     </c:when>
		     
	     
		     
	       <c:when test="${sessionScope.filtroEscalaVO.filniveval  == paramsVO.NVLEVA_SED_JORD_METD_NVL }">
	             <td width="80px"><font color="red">*</font>Sede</td>
	             <td width="250px" >
	                <select style="width: 140px;" name="filSede"    id="filSede"      onchange="javaScript: borrar_combo2(document.frmFiltro.filMetodo);borrar_combo2(document.frmFiltro.filNivel);ajaxJornada_1();" >
	                      <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaVO.filSede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
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
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaVO.filJorn }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
				    </select>
				   </td> 
				  <td width="80px"><font color="red">*</font>Metodolog�a</td> <td width="250px" >
				   <select style="width: 140px;" name="filMetodo"  id="filMetodo"   onchange="javaScript:ajaxNivel_1();" >
				        <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaVO.filMetodo}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
				   </select>
				  </td>
		         </tr>
		         <tr><td width="80px"><font color="red">*</font>Nivel</td>
		             <td width="250px" >
		               <select style="width: 140px;" style="width: 140px;" name="filNivel"   id="filNivel"    >
	   			         <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaNivel}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaVO.filNivel}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
		               </select>
		               </td>
		     </c:when>
		     
		       <c:when test="${sessionScope.filtroEscalaVO.filniveval  == paramsVO.NVLEVA_SED_JORD_METD_GRD }">
	              <td width="80px"><font color="red">*</font>Sede</td>
	              <td width="250px" >
	                <select style="width: 140px;"  name="filSede"    id="filSede"      onchange="javaScript: borrar_combo2(document.frmFiltro.filMetodo);  borrar_combo2(document.frmFiltro.filGrado); ajaxJornada_1();" >
	                     <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaVO.filSede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
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
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaVO.filJorn}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
				    </select>
				   </td> 
				  <td width="80px"><font color="red">*</font>Metodolog�a</td> 
				    <td width="250px" >
				      <select style="width: 140px;" name="filMetodo"  id="filMetodo"  onchange="JavaScript:ajaxGrado_1();" >
				          <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaVO.filMetodo}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
				      
				      </select>
				      </td>
		         </tr>
		         <tr><td> Grado</td>
		             <td width="250px" >
		               <select style="width: 140px;" name="filGrado" id="filGrado" >
		                  <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaGrado}" var="sed">
						    <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaVO.filGrado}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					       </c:forEach>
		               </select>
		              </td>
		      </c:when>
		      
		       <c:when test="${sessionScope.filtroEscalaVO.filniveval  == paramsVO.NVLEVA_SED_METD_NVL_GRD  }">
	              <td width="80px"><font color="red">*</font>Sede</td>
	              <td width="250px" >
	                <select style="width: 140px;" name="filSede"    id="filSede"      onchange="javaScript:borrar_combo2(document.frmFiltro.filNivel); borrar_combo2(document.frmFiltro.filGrado); ajaxMetodologia_1();" >
	                    <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaVO.filSede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
	                </select>
	               </td>
				 </tr>
				 <tr>
				  <td width="80px"><font color="red">*</font>Metodolog�a</td>
				  <td width="250px" >
				    <select style="width: 140px;" name="filMetodo"  id="filMetodo"   onchange="javaScript:ajaxNivel_1();" >
				       <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaVO.filMetodo}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
				    </select>
				   </td>
                  <td width="80px"><font color="red">*</font>Nivel</td>
                  <td width="250px" >
                     <select style="width: 140px;" style="width: 140px;" name="filNivel"   id="filNivel"    onchange="javaScript:ajaxGrado_1();">
                          <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaNivel}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaVO.filNivel  }">selected</c:if>><c:out value="${sed.nombre}"/></option>
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
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaVO.filGrado  }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
		            </select>
		           </td>
		      </c:when>
		      
		        <c:when test="${sessionScope.filtroEscalaVO.filniveval  == paramsVO.NVLEVA_SED_JORD_METD_NVL_GRD }">
	              <td width="80px"><font color="red">*</font>Sede</td>
	                 <td width="250px" >
	                    <select style="width: 140px;" name="filSede"    id="filSede"      onchange="javaScript:ajaxJornada_1();" >
	                       <option value="-9" >--Seleccione uno--</option> 
			  
			               <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						    <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaVO.filSede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
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
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaVO.filJorn}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
				       </select></td> 
				  <td width="80px"><font color="red">*</font>Metodolog�a</td> 
				  <td width="250px" ><select style="width: 140px;" name="filMetodo"  id="filMetodo"   onchange="javaScript:ajaxNivel_1();" >
				       <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaVO.filMetodo }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
				  </select></td>
                 </tr>
		         <tr>
		          <td width="80px"><font color="red">*</font>Nivel</td>
		          <td width="250px" >
		            <select style="width: 140px;" style="width: 140px;" name="filNivel"   id="filNivel"    onchange="javaScript:ajaxGrado_1();">
		              <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaNivel}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaVO.filNivel}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					  </c:forEach>
		            </select>
		          </td>
                  <td> Grado</td>
                    <td width="250px" >
                      <select style="width: 140px;" name="filGrado" id="filGrado" >
                          <option value="-9" >--Seleccione uno--</option> 
			               <c:forEach begin="0" items="${sessionScope.listaGrado}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.filtroEscalaVO.filGrado }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
                      </select>
                     </td>
		      </c:when>
		   </c:choose>
		    </tr>
		   </table>
        </form>  
         <!-- LISTA DE RESULTADOS -->
         <div style="width:100%;height:100px;overflow:auto;vertical-align:top;background-color:#ffffff;">
		   <table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray" class="table01" style="text-align: left;" >
			 	<caption>Lista Escalas Conceptuales</caption>
			 	<c:if test="${empty requestScope.listaAllEscalaConceptual}"><tr><th class="Fila1" colspan='6'>No hay datos</th></tr></c:if>
				<c:if test="${!empty requestScope.listaAllEscalaConceptual}">
					<tr> 
						<th class="EncabezadoColumna" align="center">&nbsp;</th>
						<th class="EncabezadoColumna" 	>Literal</th>
						<th class="EncabezadoColumna" align="center">Nombre</th>
						<th class="EncabezadoColumna" align="center">Equiv. MEN</th>
						<th class="EncabezadoColumna" align="center">Descripci�n</th>
						<th class="EncabezadoColumna" align="center">Valor</th>
						<th class="EncabezadoColumna" align="center">Orden</th>
					</tr>
					<c:forEach begin="0" items="${requestScope.listaAllEscalaConceptual  }" var="lista" varStatus="st">
						<tr>
						  <th class='Fila<c:out value="${st.count%2}"/>' width="40px;">
							<a href='javaScript:editar(<c:out value="${lista.insconcodinst}"/>,<c:out value="${lista.insconvigencia}"/>,<c:out value="${lista.insconniveval}"/>,<c:out value="${lista.insconcodigo}"/>,<c:out value="${lista.insconcodsede}"/>,<c:out value="${lista.insconcodjorn}"/>,<c:out value="${lista.insconcodmetod}"/>,<c:out value="${lista.insconcodnivel }"/>,<c:out value="${lista.insconcodgrado }"/>);'><img border='0' src='<c:url value="/etc/img/editar.png"/>' width='15' height='15'></a>
							<c:if test="${sessionScope.NivelPermiso==2 }">
							<a href='javaScript:eliminar(<c:out value="${lista.insconcodinst}"/>,<c:out value="${lista.insconvigencia}"/>,<c:out value="${lista.insconniveval}"/>,<c:out value="${lista.insconcodigo}"/>,<c:out value="${lista.insconcodsede}"/>,<c:out value="${lista.insconcodjorn}"/>,<c:out value="${lista.insconcodmetod}"/>,<c:out value="${lista.insconcodnivel }"/>,<c:out value="${lista.insconcodgrado }"/>);'><img border='0' src='<c:url value="/etc/img/eliminar.png"/>' width='15' height='15'></a></c:if>
						   </th>
							<td class='Fila<c:out value="${st.count%2}"  />'   >&nbsp;<c:out value="${lista.insconliteral}"/></td> 
							<td class='Fila<c:out value="${st.count%2}"/>'  >&nbsp;<c:out value="${lista.insconnombre}"/></td>
							<td class='Fila<c:out value="${st.count%2}"/>'  >&nbsp;<c:out value="${lista.insconequmenNom}"/></td>
							<td class='Fila<c:out value="${st.count%2}"/>'  align="center" ><textarea readonly="readonly" cols="20" rows="1"  style="height: 15px;"><c:out value="${lista.inscondescripcion}"/></textarea></td>
							<td class='Fila<c:out value="${st.count%2}"/>'  >&nbsp;<c:out value="${lista.insconvalnum}"/></td>
							<td class='Fila<c:out value="${st.count%2}"/>'  >&nbsp;<c:out value="${lista.insconorden}"/></td>
							
						</tr>
					</c:forEach>
				</c:if>
					<tr style="display:none"><td><iframe name="frameAjaxFiltro" id="frameAjaxFiltro"></iframe></td></tr>
			</table>
		</div>	
	</body>
</html>