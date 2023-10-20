<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean  id="escalaConceptualVO" class="siges.adminParamsInst.vo.EscalaConceptualVO" scope="session"/>
<jsp:useBean  id="filtroEscalaVO" class="siges.adminParamsInst.vo.FiltroEscalaVO" scope="session"/>
<jsp:useBean  id="paramsVO"   class="siges.adminParamsInst.vo.ParamsVO" scope="page"/>
<html>
<head>
  
   <script type='text/javascript'  src='<c:url value="/dwr/interface/factory_adminParamsInst.js"/>' ></script>
   <script type='text/javascript'  src='<c:url value="/dwr/engine.js"/>' ></script>
   <script type='text/javascript'  src='<c:url value="/dwr/util.js"/>' ></script>
   <script type='text/javascript'  src='<c:url value="/adminParametroInst/AjaxEscalaConp.js"/>' ></script>
   

<script languaje="text/javascript">
<!--
function validarLetras(e) { 
    tecla = (document.all) ? e.keyCode : e.which; 
    if (tecla==8) return true; 
    patron =/[A-Za-z\s]/; 
    te = String.fromCharCode(tecla); 
    return patron.test(te); 
} 

function validarNumeros(e) {
    tecla = (document.all) ? e.keyCode : e.which;
 
    if (tecla==8 || tecla==46) return true; 
    patron = /\d/;
    te = String.fromCharCode(tecla);  
    return patron.test(te); 
} 

function validarNumeros2(e) { 
    tecla = (document.all) ? e.keyCode : e.which;
    if (tecla==8) return true; 
    patron = /^\d/;
    te = String.fromCharCode(tecla); 
    return patron.test(te); 
} 



	function hacerValidaciones_frmNuevo(forma){
	
	  if(forma.insconcodsede){
	   validarLista(forma.insconcodsede,'- Sede', 1);
	  }
	  if(forma.insconcodjorn){
	   validarLista(forma.insconcodjorn,'- Jornada', 1);
	  }
	  
	  if(forma.insconcodmetod){
	   validarLista(forma.insconcodmetod,'- Metodologia', 1);
	  }
	  
	  if(forma.insconcodnivel){
	   validarLista(forma.insconcodnivel,'- Nivel', 1);
	  }
	  
	  if(forma.insconcodgrado){
	   validarLista(forma.insconcodgrado,'- Grado', 1);
	  }
	  
	  
	  
	
	  validarCampo(forma.insconliteral ,'- Literal ( Letra ) ');
	  validarFloat(forma.insconvalnum ,'- Valor Númerico',0.00 ,999.99);
	   validarCampo(forma.insconnombre ,'- Nombre', 1);
   if(forma.inscondescripcion.value != '')
	     validarCampo(forma.inscondescripcion,'- Nombre',0,  200);
	   validarLista(forma.insconequmen ,'- Equivalencia Escala MEN', 1);
	   validarCampo(forma.insconorden,'- Orden ');
	 }
		
	function guardar(){
		if(validarForma(document.frmNuevo)){
	       //Si el literal o el nombre del literal es modificado, y 
	       // ya se usaron para evaluar  entoces mostrar
	       // Validacion
	       
	       if( (document.frmNuevo.insconliteral.value !=  document.frmNuevo.insconliteralAntes.value  ||
	            document.frmNuevo.insconnombre.value !=  document.frmNuevo.insconnombreAntes.value ) &&
		        document.frmNuevo.msgValidarLiteral.value.length > 0 ){
			  
			  if( confirm("¿Esta seguro de actualizar esta información?.") &&
			       confirm(document.frmNuevo.msgValidarLiteral.value) ){
			      
		  	   }else{
			    return false;  
		  	  }//if( !confirm("¿Esta seguro de actualizar esta información?.") ||
		   }//if(document.frmNuevo.insparniveval.value !=  document.frmNuevo.insparnivevalAntes.value &&	
	
			 document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
			 document.frmNuevo.submit();
		}
	}
  
  
  
  
  function nuevo(){
	 document.frmNuevo.cmd.value=document.frmNuevo.NUEVO.value;
	 document.frmNuevo.submit();
  }
	
	
	function lanzar(tipo){				
				document.frmNuevo.tipo.value=tipo;
				document.frmNuevo.cmd.value= -1;
				document.frmNuevo.action = '<c:url value="/grupoPeriodo/ControllerAdminEdit.do"/>';
				document.frmNuevo.target = ""; 
			 	document.frmNuevo.submit();
			}
			
	function lanzar_(tipo){				
				document.frmNuevo.tipo.value=tipo;
				document.frmNuevo.cmd.value = -1;
				document.frmNuevo.action = '<c:url value="/adminParamsInst/Nuevo.do"/>';
				document.frmNuevo.target = ""; 
				document.frmNuevo.submit();
			}
			
	function cancelar(){
				location.href='<c:url value="/bienvenida.jsp"/>';
	}
	 
	 
	 
	 
	 
 	function ajaxJornada_2(){
	 if(document.frmNuevo.insconcodjorn){
	  borrar_combo2(document.frmNuevo.insconcodjorn);
	  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
	  document.frmAjaxNuevo.ajax[0].value = document.frmNuevo.insconcodsede.value;
	  document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_JORD.value;
	  document.frmAjaxNuevo.submit();
	 }
	}
	
	
	function ajaxMetodologia_2(){
	 if(document.frmNuevo.insconcodmetod){
	  borrar_combo2(document.frmNuevo.insconcodmetod);
	  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
	  document.frmAjaxNuevo.ajax[0].value = document.frmNuevo.insconcodsede.value;
	  document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_METD.value;
	  document.frmAjaxNuevo.submit();
	 }
	}
	 
	 
	function ajaxNivel_2(){
	 if(document.frmNuevo.insconcodnivel){
	  borrar_combo2(document.frmNuevo.insconcodnivel);
	  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
	  if(document.frmNuevo.insconcodmetod){
	  document.frmAjaxNuevo.ajax[0].value = document.frmNuevo.insconcodmetod.value;
	  }else{
	  document.frmAjaxNuevo.ajax[0].value = -99;
	  }
	  document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_NVL.value;
	  document.frmAjaxNuevo.submit();
	 }
	}
	 
	 
	 
	  function ajaxGrado_2(){
	 if(document.frmNuevo.insconcodgrado ){
	  borrar_combo2(document.frmNuevo.insconcodgrado);
	  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
	   
	   //Verifique que existe la metodologia
	   if(document.frmNuevo.insconcodmetod){
	    document.frmAjaxNuevo.ajax[0].value = document.frmNuevo.insconcodmetod.value;
	   }else{
	    document.frmAjaxNuevo.ajax[0].value = -99;
	   }

	   //Verifique que existe el nivel	   
	   if(document.frmNuevo.insconcodnivel){
	    document.frmAjaxNuevo.ajax[1].value = document.frmNuevo.insconcodnivel.value;
	   }else{
	    document.frmAjaxNuevo.ajax[1].value = -99;
	   }
	   
	  document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_GRAD.value;
	  document.frmAjaxNuevo.submit();
	 }
	}
	
	
	
	  
	 --></script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
 	   <form method="post" name="frmAjaxNuevo" action='<c:url value="/adminParametroInst/Ajax.do"/>'>
		<input type="hidden" name="tipo" value='<c:out value="${ paramsVO.FICHA_ESCL_CONCEPT }"/>'>
		<input type="hidden" name="cmd"  value='-1'>
		<input type="hidden" name="formulario" id="formulario"  value='2'>
		<input type="hidden" name="CMD_AJAX_JORD"  id="CMD_AJAX_JORD"  value='<c:out value="${paramsVO.CMD_AJAX_JORD}"/>'>
		<input type="hidden" name="CMD_AJAX_METD"  id="CMD_AJAX_METD"  value='<c:out value="${paramsVO.CMD_AJAX_METD}"/>'>
		<input type="hidden" name="CMD_AJAX_NVL"   id="CMD_AJAX_NVL"   value='<c:out value="${paramsVO.CMD_AJAX_NVL}"/>'>
		<input type="hidden" name="CMD_AJAX_GRAD"  id="CMD_AJAX_GRAD"  value='<c:out value="${paramsVO.CMD_AJAX_GRAD}"/>'>
		
		
		<c:forEach begin="0" end="5" var="i"><input type="hidden" name="ajax"></c:forEach>
	  </form>
	
		 <table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
		  <caption>Gestión Administrativa >> Escala Conceptual</caption>
	         <tr style="height: 20px"><td>&nbsp;</td></tr>
		    <tr height="1">
			
			 <td width="10" bgcolor="#FFFFFF">&nbsp;</td>							
			 <td rowspan="2" bgcolor="#FFFFFF">
			       <a href="javaScript:lanzar(1)"><img src='<c:url value="/etc/img/tabs/validacion_horarios_0.gif"/>' alt="Parametros" height="26" border="0"></a>
				   <a href="javaScript:lanzar_('<c:out value="${paramsVO.FICHA_PERIODO }" />')"><img src='<c:url value="/etc/img/tabs/adm_periodos_0.gif"/>' alt="Parametros " height="26" border="0"></a>
		           <a href="javaScript:lanzar_('<c:out value="${paramsVO.FICHA_INST_NIVEL_EVAL}" />')"><img src='<c:url value="/etc/img/tabs/adm_nivelEval_0.gif"/>' alt="Nivel Evaluación " height="26" border="0"></a>
		           <img src='<c:url value="/etc/img/tabs/adm_escalaConpt_1.gif"/>' border="0"  height="26" alt='Escala Conceptual'>
		           <a href="javaScript:lanzar_('<c:out value="${paramsVO.FICHA_ESCL_NUM }" />')"><img src='<c:url value="/etc/img/tabs/adm_escalaNum_0.gif"/>' alt="Parametros " height="26" border="0"></a>
		           <a href="javaScript:lanzar_('<c:out value="${paramsVO.FICHA_PER_PROG_FECHAS }" />')"><img src='<c:url value="/etc/img/tabs/adm_perProgFechas_0.gif"/>' alt="Programar Periodos" height="26" border="0"></a>
		           <a href="javaScript:lanzar_('<c:out value="${paramsVO.FICHA_PONDERACION_POR_PERIODO }" />')"><img src='<c:url value="/etc/img/tabs/adm_ponporperiodo_0.png"/>' alt="Ponderación por periodos" height="26" border="0"></a>
		        </td>
            </tr>
          </table>
          
       <!-- LISTA DE RESULTADOS -->
        <table   align="center"  width="100%">
         <caption >Filtro de Busqueda</caption>
         <tr>
          <td>
            <c:import url="/adminParametroInst/Lista.do?"><c:param value="${paramsVO.FICHA_ESCL_CONCEPT}" name="tipo" /></c:import>
		   
		   </td>
		   </tr>
		 </table>	
		
   <!-- FORMUALARIO DE EDICION CREACION--> 		  
	<form method="post" name="frmNuevo"  action='<c:url value="/adminParametroInst/Save.jsp"/>'>
        <input type="hidden" name="tipo"    value='<c:out value="${paramsVO.FICHA_ESCL_CONCEPT}"/>'>
        <input type="hidden" name="cmd"     value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NUEVO"   value='<c:out value="${paramsVO.CMD_NUEVO}"/>'> 
		<input type="hidden" name="insconvigencia" id="insconvigencia" value='<c:out value="${sessionScope.filtroEscalaVO.filVigencia}"/>'>
		<input type="hidden" name="insconcodinst" id="insconcodinst" value='<c:out value="${sessionScope.filtroEscalaVO.filInst}"/>'>
		<input type="hidden" name="insconniveval" id="insconniveval" value='<c:out value="${sessionScope.filtroEscalaVO.filniveval}"/>'>
		<input type="hidden" name="insconcodsede_" id="insconcodsede_" value='<c:out value="${sessionScope.escalaConceptualVO.insconcodsede}"/>'>
		<input type="hidden" name="insconcodjorn_" id="insconcodjorn_" value='<c:out value="${sessionScope.escalaConceptualVO.insconcodjorn}"/>'>
		<input type="hidden" name="insconcodmetod_" id"insconcodmetod_" value='<c:out value="${sessionScope.escalaConceptualVO.insconcodmetod}"/>'>
		<input type="hidden" name="insconcodnivel_" id"insconcodnivel_" value='<c:out value="${sessionScope.escalaConceptualVO.insconcodnivel}"/>'>
		<input type="hidden" name="insconcodgrado_" id"insconcodgrado_" value='<c:out value="${sessionScope.escalaConceptualVO.insconcodgrado}"/>'>
		<input type="hidden" name='insconequmen_' id='insconequmen_' value='<c:out value="${sessionScope.escalaConceptualVO.insconequmen}"/>'>	    
	    <input type="hidden" name='yaFueUtilizado' id='yaFueUtilizado' value='<c:out value="${sessionScope.escalaConceptualVO.yaFueUtilizado}"/>'>	
	    <input type="hidden" name='insconliteralAntes' id='insconliteralAntes' value='<c:out value="${sessionScope.escalaConceptualVO.insconliteralAntes}"/>'>	    
	    <input type="hidden" name='insconnombreAntes' id='insconnombreAntes' value='<c:out value="${sessionScope.escalaConceptualVO.insconnombreAntes}"/>'>	        
	    <input type="hidden" name='msgValidarLiteral' id='msgValidarLiteral' value='<c:out value="${sessionScope.escalaConceptualVO.msgValidarLiteral}"/>'>	        
	    
	    

	      <table border="0" align="center" width="100%" cellpadding="2" cellspacing="2">
			<caption>Nuevo/Edición de escala conceptual</caption>
			   <tr>
			     <td   bgcolor="#FFFFFF" colspan="4">
                  <c:if test="${sessionScope.escalaConceptualVO.edicion}">
            			<input class='boton' name="cmd1" type="button" value="Actualizar" onClick="guardar()">
                  </c:if>

                  <c:if test="${ ! sessionScope.escalaConceptualVO.edicion}">
            			<input class='boton' name="cmd1" type="button" value="Guardar" onClick="guardar()">
                  </c:if>

					<input class='boton' name="cmd13" type="button" value="Nuevo" onClick="nuevo()">
					<input class='boton' name="cmd12" type="button" value="Cancelar" onClick="cancelar()">
			      </td>
			     </tr>
			          <tr><td width="80px"><font color="red">*</font>Vigencia</td>
		         <td width="250px"><select name="filVigencia" style="width: 60px;"  onmouseover="this.disabled=true;" onmouseout="this.disabled=false;" >
					   <option value="-99"> --  //  -- </option>
					     <c:forEach  begin="0" items="${sessionScope.listaVigencia }" var="lista" >
					       <option value='<c:out  value="${ lista.codigo}"/>' <c:if test="${lista.codigo  == filtroEscalaVO.filVigencia }">SELECTED</c:if > ><c:out value="${lista.nombre}" /> </option>
					    </c:forEach>
					   </select>
				 </td>
				
		

	<c:choose> 
	       <c:when test="${sessionScope.filtroEscalaVO.filniveval  == paramsVO.NVLEVA_INST }">
		        <td width="70px">&nbsp;</td><td width="70px">&nbsp;</td>
		   </c:when>
		   
		   <c:when test="${sessionScope.filtroEscalaVO.filniveval  == paramsVO.NVLEVA_SED }">
	             <td width="70px"><font color="red">*</font>Sede</td>
	             <td width="250px" >
	                <select  '<c:if test="${sessionScope.escalaConceptualVO. edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insconcodsede"    id="insconcodsede"      onchange="javaScript:ajaxJornada_();" >
	                 <option value="-99" >--Seleccione uno--</option>
					   <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaConceptualVO.insconcodsede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					  </c:forEach>   
	                </select>
	             </td>
		   </c:when>

           <c:when test="${sessionScope.filtroEscalaVO.filniveval  == paramsVO.NVLEVA_JORN }">
	               <td width="70px" ><font color="red">*</font>Jornada</td>
	               <td width="250px">
	                 <select  '<c:if test="${sessionScope.escalaConceptualVO. edicion}">' DISABLED '</c:if>'  style="width: 120px;" name="insconcodjorn"    id="insconcodjorn"      onchange="javaScript:ajaxMetodologia_();" >
	                   <option value="-99" >--Seleccione uno--</option>
					   <c:forEach begin="0" items="${sessionScope.listaJornada}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaConceptualVO.insconcodjorn }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>   
	                 </select>
	               </td>
			</c:when>
		     
		   <c:when test="${sessionScope.filtroEscalaVO.filniveval  == paramsVO.NVLEVA_METD }">
	            <td width="70px" ><font color="red">*</font>Metodología</td><td width="250px" >
	               <select  '<c:if test="${sessionScope.escalaConceptualVO. edicion}">' DISABLED '</c:if>'  style="width: 120px;" name="insconcodmetod"  id="insconcodmetod"     >
	                   <option value="-99" >--Seleccione uno--</option>
					   <c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaConceptualVO.insconcodmetod}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
	               </select>
	            </td>
		   </c:when>
		     
		    <c:when test="${sessionScope.filtroEscalaVO.filniveval  == paramsVO.NVLEVA_NVL }">
	          <td width="70px"><font color="red">*</font>Nivel</td><td>
	              <select  '<c:if test="${sessionScope.escalaConceptualVO. edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insconcodnivel"   id="insconcodnivel"    onchange="javaScript:ajaxGrado_2();">
	                   <option value="-99" >--Seleccione uno--</option>
					   <c:forEach begin="0" items="${sessionScope.listaNivel}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.escalaConceptualVO.insconcodnivel }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
	              </select>
	           </td>
	       </c:when>
	       
	        <c:when test="${sessionScope.filtroEscalaVO.filniveval  == paramsVO.NVLEVA_GRD }">
	         <td width="70px"><font color="red">*</font>Grado</td>
	         <td  width="250px">
	           <select  '<c:if test="${sessionScope.escalaConceptualVO. edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insconcodgrado" id="insconcodgrado"   >
	               <option value="-99" >--Seleccione uno--</option>
					  <c:forEach begin="0" items="${sessionScope.listaGrado  }" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaConceptualVO.insconcodgrado}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					  </c:forEach> 
	           </select>
	          </td>
	       </c:when>
	       
	       
	       <c:when test="${sessionScope.filtroEscalaVO.filniveval  == paramsVO.NVLEVA_SED_JORN }">
	           <td width="70px"><font color="red">*</font>Sede</td>
	            <td width="250px" >
	            <select  '<c:if test="${sessionScope.escalaConceptualVO. edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insconcodsede"    id="insconcodsede"      onchange="javaScript:ajaxJornada_2();" >
	                 <option value="-9" >--Seleccione uno--</option>
					   <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaConceptualVO.insconcodsede }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					    </c:forEach> 
					  
	             </select>
	            </td>
	          </tr>
			  <tr>
				<td width="70px"><font color="red">*</font>Jornada</td>
				<td><select  '<c:if test="${sessionScope.escalaConceptualVO. edicion}">' DISABLED '</c:if>'  style="width: 120px;" name="insconcodjorn"    id="insconcodjorn"      onchange="javaScript:ajaxMetodologia_();" >
				     <option value="-99" >--Seleccione uno--</option>
				         <c:forEach begin="0" items="${sessionScope.listaJornada}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaConceptualVO.insconcodjorn }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					    </c:forEach> 
				    </select>
				</td> 
		   </c:when>
		   
		  <c:when test="${sessionScope.filtroEscalaVO.filniveval  == paramsVO.NVLEVA_SED_METD }">
	          <td width="70px"><font color="red">*</font>Sede</td><td>
	                <select  '<c:if test="${sessionScope.escalaConceptualVO. edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insconcodsede"    id="insconcodsede"      onchange="javaScript:ajaxMetodologia_2();" >
	                  <option value="-9" >--Seleccione uno--</option>
					   <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaConceptualVO.insconcodsede }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					  </c:forEach> 
	                </select>
	             </td>
	          </tr>
			  <tr >
				 <td width="70px"><font color="red">*</font>Metodología</td> <td>
				     <select  '<c:if test="${sessionScope.escalaConceptualVO. edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insconcodmetod"  id="insconcodmetod"   onchange="javaScript:ajaxNivel_();" >
				      <option value="-99" >--Seleccione uno--</option> 
					   <c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaConceptualVO.insconcodmetod }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
				     </select>
				   </td>
		   </c:when>
		   
		     <c:when test="${sessionScope.filtroEscalaVO.filniveval  == paramsVO.NVLEVA_SED_NVL }">
	          <td width="70px">Sede</td><td width="250px">
	             <select  '<c:if test="${sessionScope.escalaConceptualVO. edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insconcodsede"    id="insconcodsede"      onchange="javaScript:ajaxNivel_2();" >
	                  <option value="-99" >--Seleccione uno--</option>
					   <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.escalaConceptualVO.insconcodsede }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
	             </select>
	             </td>
	          </tr>
			  <tr>
				 <td><font color="red">*</font>Nivel</td><td>
				      
				      <select  '<c:if test="${sessionScope.escalaConceptualVO. edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insconcodnivel"   id="insconcodnivel"     >
				      <option value="-9" >--Seleccione uno--</option>
				       <c:forEach begin="0" items="${sessionScope.listaNivel}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.escalaConceptualVO.insconcodnivel }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
				      </select>
				      </td>
		   </c:when>
		   
		    <c:when test="${sessionScope.filtroEscalaVO.filniveval  == paramsVO.NVLEVA_SED_GRD }">
	          <td width="70px"><font color="red">*</font>Sede</td><td width="250px">
	             <select  '<c:if test="${sessionScope.escalaConceptualVO. edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insconcodsede"   id="insconcodsede"    onchange="javaScript:ajaxGrado_2();"    >
	                 <option value="-99" >--Seleccione uno--</option>
					   <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaConceptualVO.insconcodsede }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
	              </select>
	             </td>
	          </tr>
			  <tr>
			  <td width="70px" ><font color="red">*</font>Grado</td>
			  <td width="250px">
			     <select  '<c:if test="${sessionScope.escalaConceptualVO. edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insconcodgrado" id="insconcodgrado"   >
			         <option value="-99" >--Seleccione uno--</option>
					   <c:forEach begin="0" items="${sessionScope.listaGrado}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaConceptualVO.insconcodgrado }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
	            </select>
			   </td>
		   </c:when>
		   
		        		        
	       <c:when test="${sessionScope.filtroEscalaVO.filniveval  == paramsVO.NVLEVA_SED_JORN_METD }">
	             <td width="70px"><font color="red">*</font>Sede</td><td width="250px">
	                <select  '<c:if test="${sessionScope.escalaConceptualVO. edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insconcodsede"    id="insconcodsede"      onchange="javaScript:ajaxJornada_2();" >
	                  <option value="-99" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaConceptualVO.insconcodsede }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
	                </select>
	               </td>
				 </tr>
				 <tr>
				  <td width="70px"><font color="red">*</font>Jornada</td>
				  <td><select  '<c:if test="${sessionScope.escalaConceptualVO. edicion}">' DISABLED '</c:if>'  style="width: 120px;" name="insconcodjorn"    id="insconcodjorn"      onchange="javaScript:ajaxMetodologia_2();" >
				     <option value="-99" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaJornada}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaConceptualVO.insconcodjorn }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
				     </select>
				   </td> 
				   <td><font color="red">*</font>Metodología</td>
				   <td width="250px">
				     <select  '<c:if test="${sessionScope.escalaConceptualVO. edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insconcodmetod"  id="insconcodmetod"    >
				     <option value="-99" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaConceptualVO.insconcodmetod }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
				     </select>
				  </td>
		     </c:when>
		     
		     
		      <c:when test="${sessionScope.filtroEscalaVO.filniveval  == paramsVO.NVLEVA_SED_JORN_NVL }">
	             <td width="70px"><font color="red">*</font>Sede</td>
	             <td width="250px">
	                <select  '<c:if test="${sessionScope.escalaConceptualVO. edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insconcodsede"    id="insconcodsede"      onchange="javaScript:ajaxJornada_2();" >
	                 <option value="-99" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaConceptualVO.insconcodsede }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
				    
	                </select>
	               </td>
				 </tr>
				 <tr>
				  <td width="70px"><font color="red">*</font>Jornada</td>
				     <td width="250px">
				       <select  '<c:if test="${sessionScope.escalaConceptualVO. edicion}">' DISABLED '</c:if>'   style="width: 120px;" name="insconcodjorn"    id="insconcodjorn"      onchange="javaScript:ajaxNivel_2();" >
				     <option value="-99" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaJornada}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaConceptualVO.insconcodjorn }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
				    
				      </select>
				    </td> 
				  <td width="70px"><font color="red">*</font>Nivel</td>
				  <td width="250px">
				    <select  '<c:if test="${sessionScope.escalaConceptualVO. edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insconcodnivel"   id="insconcodnivel"     >
				     <option value="-99" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaNivel}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaConceptualVO.insconcodnivel }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
				    
				    </select>
				  </td>
				  
		     </c:when>
		     
		     
		     <c:when test="${sessionScope.filtroEscalaVO.filniveval  == paramsVO.NVLEVA_SED_JORN_GRD }">
	              <td width="70px"><font color="red">*</font>Sede</td>
	              <td width="250px">
	                <select  '<c:if test="${sessionScope.escalaConceptualVO. edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insconcodsede"    id="insconcodsede"      onchange="javaScript:ajaxJornada_2();   " >
	                 <option value="-9" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaConceptualVO.insconcodsede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
	                </select>
	               </td>
				 </tr>
				 <tr>
				 <td width="70px"><font color="red">*</font>Jornada</td >
				  <td width="250px">
				    <select  '<c:if test="${sessionScope.escalaConceptualVO. edicion}">' DISABLED '</c:if>'  style="width: 120px;" name="insconcodjorn"    id="insconcodjorn"      onchange="javaScript:ajaxGrado_2();" >
				      <option value="-99" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaJornada}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaConceptualVO.insconcodjorn }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
				    </select>
				   </td> 
				 <td width="70px"><font color="red">*</font>Grado</td>
				 <td width="250px">
				   <select  '<c:if test="${sessionScope.escalaConceptualVO. edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insconcodgrado" id="insconcodgrado"   >
				   <option value="-99" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaGrado}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaConceptualVO.insconcodgrado }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
				   </select>
				 </td>
				  
		     </c:when>
		    
	      
	      
	       <c:when test="${sessionScope.filtroEscalaVO.filniveval  == paramsVO.NVLEVA_SED_METD_NVL }">
	             <td width="70px"><font color="red">*</font>Sede</td>
	             <td width="250px">
	               <select  '<c:if test="${sessionScope.escalaConceptualVO. edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insconcodsede"    id="insconcodsede"      onchange="javaScript:ajaxMetodologia_2();" >
	                  <option value="-9" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaConceptualVO.insconcodsede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
	               </select>
	             </td>
				 </tr>
				 <tr>
				 <td width="70px"><font color="red">*</font>Metodología</td> 
				 <td width="250px">
				   <select  '<c:if test="${sessionScope.escalaConceptualVO. edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insconcodmetod"  id="insconcodmetod"   onchange="javaScript:ajaxNivel_2();" >
				    <option value="-9" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaConceptualVO.insconcodmetod}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
				   </select>
				 </td>
		         <td width="70px"><font color="red">*</font>Nivel</td>
		         <td width="250px">
		          <select  '<c:if test="${sessionScope.escalaConceptualVO. edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insconcodnivel"   id="insconcodnivel"    onchange="javaScript:ajaxGrado_();">
		            <option value="-9" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaNivel}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaConceptualVO.insconcodnivel}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
		          </select>
		          </td>
				 
		     </c:when>
		          		        
	       <c:when test="${sessionScope.filtroEscalaVO.filniveval  == paramsVO.NVLEVA_SED_METD_GRD }">
	             <td width="70px"><font color="red">*</font>Sede</td><td><select  '<c:if test="${sessionScope.escalaConceptualVO. edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insconcodsede"    id="insconcodsede"      onchange="javaScript:ajaxMetodologia_2();" >
	             	<option value="-9" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaConceptualVO.insconcodsede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
	             </select></td>
				 </tr>
				 <tr>
				   <td width="70px"><font color="red">*</font>Metodología</td> 
				   <td width="250px">
				     <select  '<c:if test="${sessionScope.escalaConceptualVO. edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insconcodmetod"  id="insconcodmetod"  onchange="javaScript:ajaxGrado_2();"   >
				       <option value="-9" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaConceptualVO.insconcodmetod}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
				     </select>
				   <td width="70px" ><font color="red">*</font>Grado</td>
				   <td width="250px">
				     <select  '<c:if test="${sessionScope.escalaConceptualVO. edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insconcodgrado" id="insconcodgrado"   >
				       <option value="-9" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listGrado}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaConceptualVO.insconcodgrado}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
				    
				     </select>
				    </td>
		     </c:when>
		     
	       <c:when test="${sessionScope.filtroEscalaVO.filniveval  == paramsVO.NVLEVA_SED_NVL_GRD  }">
	             <td width="70px" ><font color="red">*</font>Sede</td>
	              <td>
	               <select  '<c:if test="${sessionScope.escalaConceptualVO. edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insconcodsede"    id="insconcodsede"      onchange="javaScript:ajaxNivel_2();" >
	                 <option value="-9" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaConceptualVO.insconcodsede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
	               </select>
	              </td>
				 </tr>
				 <tr><td width="70px" ><font color="red">*</font>Nivel</td>
				     <td width="250px">
				        <select  '<c:if test="${sessionScope.escalaConceptualVO. edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insconcodnivel"   id="insconcodnivel"    onchange="javaScript:ajaxGrado_2();">
				       	  <option value="-9" >--Seleccione uno--</option>
	                      <c:forEach begin="0" items="${sessionScope.listaNivel}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaConceptualVO.insconcodnivel}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
				        </select>
				        </td>
				     <td width="70px" ><font color="red">*</font>Grado</td>
				     <td width="250px">
				       <select  '<c:if test="${sessionScope.escalaConceptualVO. edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insconcodgrado" id="insconcodgrado"   >
				         <option value="-9" >--Seleccione uno--</option>
	                      <c:forEach begin="0" items="${sessionScope.listaGrado}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaConceptualVO.insconcodgrado}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
				       </select>
				       </td>
		     </c:when>
		     
	     
		     
	       <c:when test="${sessionScope.filtroEscalaVO.filniveval  == paramsVO.NVLEVA_SED_JORD_METD_NVL }">
	             <td width="70px" ><font color="red">*</font>Sede</td>
	             <td width="250px">
	               <select  '<c:if test="${sessionScope.escalaConceptualVO. edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insconcodsede"    id="insconcodsede"      onchange="javaScript: borrar_combo2(document.frmNuevo.insnivcodmetod  );borrar_combo2(document.frmNuevo.insnivcodnivel );  ajaxJornada_2();" >
	                 <option value="-9" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaConceptualVO.insconcodsede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
	               </select>
	              </td>
				 </tr>
				 <tr>
				  <td width="70px" ><font color="red">*</font>Jornada</td>
				  <td width="250px">
				    <select  '<c:if test="${sessionScope.escalaConceptualVO. edicion}">' DISABLED '</c:if>'  style="width: 120px;" name="insconcodjorn"    id="insconcodjorn"      onchange="javaScript:ajaxMetodologia_2();" >
				      <option value="-9" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaJornada}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaConceptualVO.insconcodjorn}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
				    </select>
				  </td> 
				  <td width="70px" ><font color="red">*</font>Metodología</td> 
				  <td width="250px" >
				    <select  '<c:if test="${sessionScope.escalaConceptualVO. edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insconcodmetod"  id="insconcodmetod"   onchange="javaScript:ajaxNivel_2();" >
				      <option value="-9" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaConceptualVO.insconcodmetod}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
				    </select></td>
		         </tr>
		         <tr><td width="70px" ><font color="red">*</font>Nivel</td>
		             <td width="250px">
		                <select  '<c:if test="${sessionScope.escalaConceptualVO. edicion}">' DISABLED '</c:if>'  style="width: 140px;" style="width: 140px;" name="insconcodnivel"   id="insconcodnivel"    onchange="javaScript:ajaxGrado3_();">
		                   <option value="-9" >--Seleccione uno--</option>
	                       <c:forEach begin="0" items="${sessionScope.listaNivel}" var="sed">
						  <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaConceptualVO.insconcodnivel}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					     </c:forEach>
				        </select>
		               </td>
		     </c:when>
		     
		       <c:when test="${sessionScope.filtroEscalaVO.filniveval  == paramsVO.NVLEVA_SED_JORD_METD_GRD }">
	              <td width="70px" ><font color="red">*</font>Sede</td>
	              <td width="250px">
	                <select  '<c:if test="${sessionScope.escalaConceptualVO. edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insconcodsede"    id="insconcodsede"      onchange="javaScript: borrar_combo2(document.frmNuevo.insnivcodmetod);borrar_combo2(document.frmNuevo.insnivcodgrado); ajaxJornada_2();" >
	                  <option value="-9" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaConceptualVO.insconcodsede }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
	                </select>
	                </td>
				 </tr>
				 <tr>
				  <td width="70px" ><font color="red">*</font>Jornada</td>
				  <td width="250px">
				    <select  '<c:if test="${sessionScope.escalaConceptualVO. edicion}">' DISABLED '</c:if>'  style="width: 120px;" name="insconcodjorn"    id="insconcodjorn"      onchange="javaScript:ajaxMetodologia_2();" >
				    	<option value="-9" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaJornada}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaConceptualVO.insconcodjorn}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
				    </select>
				   </td> 
				  <td width="70px" ><font color="red">*</font>Metodología</td> 
				  <td width="250px">
				     <select  '<c:if test="${sessionScope.escalaConceptualVO. edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insconcodmetod"  id="insconcodmetod"   onchange="javaScript:ajaxGrado_2();" >
				       <option value="-9" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaConceptualVO.insconcodmetod}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
				     </select>
				     </td>
		         </tr>
		         <tr><td width="70px" >Grado</td>
		         <td><select  '<c:if test="${sessionScope.escalaConceptualVO. edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insconcodgrado" id="insconcodgrado" >
		            <option value="-9" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaGrado}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaConceptualVO.insconcodgrado}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
				   
		         </select></td>
		      </c:when>
		     
		     
		     
		     
		     
		     
		     
		     
		      
		       <c:when test="${sessionScope.filtroEscalaVO.filniveval  == paramsVO.NVLEVA_SED_METD_NVL_GRD }">
	              <td width="70px"><font color="red">*</font>Sede</td><td width="250px">
	                 <select  '<c:if test="${sessionScope.escalaConceptualVO. edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insconcodsede"    id="insconcodsede"      onchange="javaScript:borrar_combo2(document.frmNuevo.insnivcodnivel); borrar_combo2(document.frmNuevo.insnivcodgrado); ajaxMetodologia_2();" >
	                  <option value="-9" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaConceptualVO.insconcodsede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
				      
	                 </select>
	                 </td>
				 </tr>
				 <tr>
				  <td width="70px"><font color="red">*</font>Metodología</td> 
				   <td width="250px" >
				      <select  '<c:if test="${sessionScope.escalaConceptualVO. edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insconcodmetod"  id="insconcodmetod"   onchange="javaScript:ajaxNivel_2();" >
				      <option value="-9" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaConceptualVO.insconcodmetod}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
				      </select>
				   </td>
                  <td width="70px" ><font color="red">*</font>Nivel</td>
                  <td width="250px" >
                    <select  '<c:if test="${sessionScope.escalaConceptualVO. edicion}">' DISABLED '</c:if>'  style="width: 140px;" style="width: 140px;" name="insconcodnivel"   id="insconcodnivel"    onchange="javaScript:ajaxGrado_2();">
                      <option value="-9" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaNivel}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaConceptualVO.insconcodnivel}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
				    </select>
                  </td>
                 </tr>
		         <tr>
		          <td width="70px" ><font color="red">*</font>Grado</td>
		          <td width="250px">
		           <select  '<c:if test="${sessionScope.escalaConceptualVO. edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insconcodgrado" id="insconcodgrado" >
                      <option value="-9" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaGrado}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaConceptualVO.insconcodgrado}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>		           
		           </select></td>
		      </c:when>
		      
		      
		      
		      
		      
		      
		      
		      
		      
		        <c:when test="${sessionScope.filtroEscalaVO.filniveval  == paramsVO.NVLEVA_SED_JORD_METD_NVL_GRD }">
	              <td width="70px"><font color="red">*</font>Sede</td><td width="250px">
	  
	                <select  '<c:if test="${sessionScope.escalaConceptualVO. edicion}">' DISABLED '</c:if>'   style="width: 140px;" name="insconcodsede"    id="insconcodsede"      onchange="javaScript:ajaxJornada_2();" >
	                   <option value="-9" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaConceptualVO.insconcodsede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
	                </select>
	  
	                </td>
				 </tr>
				 <tr>
				  <td width="70px" ><font color="red">*</font>Jornada</td>
				  <td width="250px">
		
				    <select  '<c:if test="${sessionScope.escalaConceptualVO. edicion}">' DISABLED '</c:if>'  style="width: 120px;" name="insconcodjorn"    id="insconcodjorn"      onchange="javaScript:ajaxMetodologia_2();" >
				     <option value="-9" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaJornada}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaConceptualVO.insconcodnivel}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
				   </select>
		
				  </td> 
				  <td width="70px"><font color="red">*</font>Metodología</td width="250px"><td>
		
				    <select  '<c:if test="${sessionScope.escalaConceptualVO. edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insconcodmetod"  id="insconcodmetod"   onchange="javaScript:ajaxNivel_2();" >
				   	  <option value="-9" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaConceptualVO.insconcodmetod}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
				    </select>
		
				   </td>
                 </tr>
		         <tr>
		         
                  <td width="70px"><font color="red">*</font>Nivel</td>
                  <td width="250px">
        
                    <select  '<c:if test="${sessionScope.escalaConceptualVO. edicion}">' DISABLED '</c:if>'  style="width: 140px;" style="width: 140px;" name="insconcodnivel"   id="insconcodnivel"    onchange="javaScript:ajaxGrado_2();">
                      <option value="-9" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaNivel}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaConceptualVO.insconcodnivel}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
                    </select>
        
                    </td>
                  <td width="70px"><font color="red">*</font>Grado</td>
                  <td width="250px">
        
                    <select  '<c:if test="${sessionScope.escalaConceptualVO. edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insconcodgrado" id="insconcodgrado" >
                      <option value="-9" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaGrado}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaConceptualVO.insconcodgrado }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
                    </select>
        
                    </td>
		      </c:when>
		    
	       </c:choose>
		</tr> 
					<tr><td width="60px"><font color="red">*</font>Literal</td>
					    <td width="40px"> <input name="insconliteral"  id="insconliteral" value='<c:out value="${sessionScope.escalaConceptualVO.insconliteral }"/>' size="1" maxlength="1"  onkeypress="return validarLetras(event)" ></td>
					  <td width="50px"><font color="red">*</font>Nombre</td>
					    <td><input type="text"  name="insconnombre"  id="insconnombre" value='<c:out value="${sessionScope.escalaConceptualVO.insconnombre }"/>'    maxlength="20"  style="font-size: 12;width:180px; "  >
					    </td>
					  </tr>
					  <tr>
				 	   
				 	   <td><font color="red">*</font>Valor Númerico</td>
					     <td  width="210px">
					       <table border="0" >
					        <tr>
					          <td>
					      <input  name="insconvalnum" id="insconvalnum" value='<c:out value="${ sessionScope.escalaConceptualVO.insconvalnum }"/>'  onkeypress="return validarNumeros(event)"  size="4" maxlength="4" >
					     </td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td> <td> <font color="red">*</font>Orden</td><td><input id="insconorden" name="insconorden" value='<c:out value="${sessionScope.escalaConceptualVO.insconorden }"/>'   onkeypress="return validarNumeros2(event)"   size="4" maxlength="2">
					     </td>
					     </table>
					     </td>
					      <td colspan="1" ><font color="red">*</font>Equivalencia Escala MEN</td>
					    <td  colspan="1" >
					      <select style="width: 180px;" name="insconequmen" id="insconequmen">
					          <option value="-99" >--Seleccione uno--</option>
					       <c:forEach begin="0" items="${requestScope.listaMen}" var="sed">
						      <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.escalaConceptualVO.insconequmen}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					       </c:forEach>  
					      </select>
					    </td>
					   
					   
					   </tr>
				 	   <tr>
				 	    <td>Descripción</td>
				 	    <td colspan="3"><textarea  name="inscondescripcion" id="inscondescripcion" cols="85" rows="3"  style="width:500px;height:50px;font-size: 12;"   ><c:out value="${sessionScope.escalaConceptualVO.inscondescripcion}"/></textarea></td>
				 	   
					    
				 	</tr>
				 	<tr style="display:none"><td><iframe name="frameAjaxNuevo" id="frameAjaxNuevo"></iframe></td></tr>
				 	 
			</table>	
			<!-- TABLA DE LOS RESULTADOS DE LA BUSQUEDA -->
	</form>
</body>
<script> 
   
</script>
</html>