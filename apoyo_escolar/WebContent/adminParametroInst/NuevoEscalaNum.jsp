<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean  id="escalaNumericaVO" class="siges.adminParamsInst.vo.EscalaNumericaVO" scope="session"/>
<jsp:useBean  id="paramsVO"   class="siges.adminParamsInst.vo.ParamsVO" scope="page"/>
<html>
<head>
  
  <script type='text/javascript'   src='<c:url value="/dwr/interface/factory_adminParamsInst.js"/>' ></script>
  <script type='text/javascript'   src='<c:url value="/dwr/engine.js"/>' ></script>
  <script type='text/javascript'   src='<c:url value="/dwr/util.js"/>' ></script>
  <script type='text/javascript'   src='<c:url value="/adminParametroInst/Ajax.js"/>' ></script>
  <script type='text/javascript'   src='<c:url value="/adminParametroInst/AjaxEscalaNum.js"/>' ></script>
	

<script languaje="javaScript">


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
	
	  if(forma.insnumcodsede){
	   validarLista(forma.insnumcodsede,'- Sede', 1);
	  }
	  if(forma.insnumcodjorn){
	   validarLista(forma.insnumcodjorn,'- Jornada', 1);
	  }
	  
	  if(forma.insnumcodmetod){
	   validarLista(forma.insnumcodmetod,'- Metodologia', 1);
	  }
	  
	  if(forma.insnumcodnivel){
	   validarLista(forma.insnumcodnivel,'- Nivel', 1);
	  }
	  
	  if(forma.insnumcodgrado){
	   validarLista(forma.insnumcodgrado,'- Grado', 1);
	  }
	 
	  validarCampo(forma.insnumvalmin,'- Rango Minimo'); 
	  validarFloat(forma.insnumvalmin,'- Rango Minimo (formato 00.00)',0.00, 999.99);
	  validarFloat(forma.insnumvalmax,'- Rango Maximo (formato 00.00)',0.01,  999.99);
	  
	  	 if( (forma.insnumvalmin.value * 100) >= (forma.insnumvalmax.value *  100)){
	       appendErrorMessage('- El rango es incorrecto: El valor \"Desde\" no puede ser mayor o igual al valor \"Hasta\".');
	        if (_campoError == null) {
            _campoError = forma.insnumvalmin.value;
             }
	    }
	    
	  

      if(forma.insnumdescripcion.value != '')
	     validarCampo(forma.insnumdescripcion,'- Nombre',0,  200);
	   
	  // validarLista(forma.insnumequinst,'- Equivalencia Institución', 1);
	   validarLista(forma.insnumequmen ,'- Equivalencia Escala MEN', 1);
	  // if(forma.insnumorden.value =='' || forma.insnumorden.value == 0 )
	     validarEntero(forma.insnumorden,'- Orden  formatro (00)',1,99);
	  }
		
	function guardar(){
		       if( (document.frmNuevo.insnumvalminAntes.value !=  document.frmNuevo.insnumvalmin.value  ||
	            document.frmNuevo.insnumvalmax.value !=  document.frmNuevo.insnumvalmaxAntes.value ) &&
		        document.frmNuevo.msgValidarRango.value.length > 0 ){
			  
			  if( confirm("¿Esta seguro de actualizar esta información?.") &&
			       confirm(document.frmNuevo.msgValidarRango.value) ){
			      
		  	   }else{
			    return false;  
		  	  }//if( !confirm("¿Esta seguro de actualizar esta información?.") ||
		   }//if(document.frmNuevo.insparniveval.value !=  document.frmNuevo.insparnivevalAntes.value &&	
	
			  
		if(validarForma(document.frmNuevo)){
			
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
				document.frmNuevo.cmd.value=-1;
				document.frmNuevo.action = '<c:url value="/grupoPeriodo/ControllerAdminEdit.do"/>';
				document.frmNuevo.target = ""; 
				document.frmNuevo.submit();
			}
			
	function lanzar_(tipo){				
				document.frmNuevo.tipo.value=tipo;
				document.frmNuevo.cmd.value=-1;
				document.frmNuevo.action = '<c:url value="/adminParamsInst/Nuevo.do"/>';
				document.frmNuevo.target = ""; 
				
				 
				document.frmNuevo.submit();
			}
			
	function cancelar(){
				location.href='<c:url value="/bienvenida.jsp"/>';
	}
	 
	 
	 
	 
	 
	     
 	function ajaxJornada_2(){
	 if(document.frmNuevo.insnumcodjorn){
	  borrar_combo2(document.frmNuevo.insnumcodjorn);
	  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
	  document.frmAjaxNuevo.ajax[0].value = document.frmNuevo.insnumcodsede.value;
	  document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_JORD.value;
	  document.frmAjaxNuevo.submit();
	 }
	}
	
	
	function ajaxMetodologia_2(){
	 if(document.frmNuevo.insnumcodmetod){
	  borrar_combo2(document.frmNuevo.insnumcodmetod);
	  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
	  document.frmAjaxNuevo.ajax[0].value = document.frmNuevo.insnumcodsede.value;
	  document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_METD.value;
	  document.frmAjaxNuevo.submit();
	 }
	}
	 
	 
	function ajaxNivel_2(){
	 if(document.frmNuevo.insnumcodnivel){
	  borrar_combo2(document.frmNuevo.insnumcodnivel);
	  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
	  if(document.frmNuevo.insnumcodmetod){
	  document.frmAjaxNuevo.ajax[0].value = document.frmNuevo.insnumcodmetod.value;
	  }else{
	  document.frmAjaxNuevo.ajax[0].value = -99;
	  }
	  document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_NVL.value;
	  document.frmAjaxNuevo.submit();
	 }
	}
	 
	 
	 
	  function ajaxGrado_2(){
	 if(document.frmNuevo.insnumcodgrado ){
	  borrar_combo2(document.frmNuevo.insnumcodgrado);
	  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
	   
	   //Verifique que existe la metodologia
	   if(document.frmNuevo.insnumcodmetod){
	    document.frmAjaxNuevo.ajax[0].value = document.frmNuevo.insnumcodmetod.value;
	   }else{
	    document.frmAjaxNuevo.ajax[0].value = -99;
	   }

	   //Verifique que existe el nivel	   
	   if(document.frmNuevo.insnumcodnivel){
	    document.frmAjaxNuevo.ajax[1].value = document.frmNuevo.insnumcodnivel.value;
	   }else{
	    document.frmAjaxNuevo.ajax[1].value = -99;
	   }
	   
	  document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_GRAD.value;
	  document.frmAjaxNuevo.submit();
	 }
	}
	
	 
	
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
 
 
  	  <form method="post" name="frmAjaxNuevo" action='<c:url value="/adminParametroInst/Ajax.do"/>'>
		<input type="hidden" name="tipo" value='<c:out value="${ paramsVO.FICHA_ESCL_NUM }"/>'>
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
		           <a href="javaScript:lanzar_('<c:out value="${paramsVO.FICHA_INST_NIVEL_EVAL }" />')"><img src='<c:url value="/etc/img/tabs/adm_nivelEval_0.gif"/>' alt="Nivel Evaluación " height="26" border="0"></a>
		           <a href="javaScript:lanzar_('<c:out value="${paramsVO.FICHA_ESCL_CONCEPT  }" />')"><img src='<c:url value="/etc/img/tabs/adm_escalaConpt_0.gif"/>' alt="Escala Conceptual " height="26" border="0"></a>
		           <img src='<c:url value="/etc/img/tabs/adm_escalaNum_1.gif"/>' border="0"  height="26" alt='Escala Numérica'>
		           <a href="javaScript:lanzar_('<c:out value="${paramsVO.FICHA_PER_PROG_FECHAS }" />')"><img src='<c:url value="/etc/img/tabs/adm_perProgFechas_0.gif"/>' alt="Programar Periodos" height="26" border="0"></a>
		           <a href="javaScript:lanzar_('<c:out value="${paramsVO.FICHA_PONDERACION_POR_PERIODO }" />')"><img src='<c:url value="/etc/img/tabs/adm_ponporperiodo_0.png"/>' alt="Ponderación por periodos" height="26" border="0"></a>
			 
		        </td>
            </tr>
          </table>
       
       <!-- LISTA DE RESULTADOS -->
       <table    align="center"  width="100%">
         <caption >Filtro de Busqueda</caption>
         <tr>
          <td>
            <c:import url="/adminParametroInst/Lista.do?"><c:param value="${paramsVO.FICHA_ESCL_NUM}" name="tipo" /></c:import>
		   </td>
		   </tr>
		 </table>	
		 
   <!-- FORMUALARIO DE EDICION CREACION--> 	
 
   <table   align="center" cellpadding="2" cellspacing="2"  width="100%">
    <tr>
    <td>	  
	<form method="post" name="frmNuevo"  action='<c:url value="/adminParametroInst/Save.jsp"/>'>
         <input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_ESCL_NUM }"/>'>
        <input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'> 
		<input type="hidden" name="insnumvigencia" value='<c:out value="${sessionScope.filtroEscalaNumVO.filNumVigencia}"/>'>
		<input type="hidden" name="insnumcodinst" value='<c:out value="${sessionScope.filtroEscalaNumVO.filNumInst}"/>'>
		<input type="hidden" name="insnumniveval" id="insnumniveval" value='<c:out value="${sessionScope.filtroEscalaNumVO.filNumniveval}"/>'>
		
		<input type="hidden" name="filNumInst_" id="filNumInst_" value='<c:out value="${sessionScope.filtroEscalaNumVO.filNumInst}"/>'>
		<input type="hidden" name="insnumequinst_" value='<c:out value="${sessionScope.escalaNumericaVO.insnumequinst}"/>'>
		<input type="hidden" name="insnumcodsede_" value='<c:out value="${sessionScope.escalaNumericaVO.insnumcodsede}"/>'>
		<input type="hidden" name="insnumcodjorn_" value='<c:out value="${sessionScope.escalaNumericaVO.insnumcodjorn}"/>'>
		<input type="hidden" name="insnumcodmetod_" value='<c:out value="${sessionScope.escalaNumericaVO.insnumcodmetod}"/>'>
		<input type="hidden" name="insnumcodnivel_" value='<c:out value="${sessionScope.escalaNumericaVO.insnumcodnivel}"/>'>
		<input type="hidden" name="insnumcodgrado_" value='<c:out value="${sessionScope.escalaNumericaVO.insnumcodgrado}"/>'>
		<input type="hidden" name="yaFueUtilizado" value='<c:out value="${sessionScope.escalaNumericaVO.yaFueUtilizado}"/>'>
		<input type="hidden" name="insnumvalminAntes" value='<c:out value="${sessionScope.escalaNumericaVO.insnumvalminAntes }"/>' >
		<input type="hidden" name="insnumvalmaxAntes" value='<c:out value="${sessionScope.escalaNumericaVO.insnumvalmaxAntes }"/>' >
		<input type="hidden" name='msgValidarRango' id='msgValidarRango' value='<c:out value="${sessionScope.escalaNumericaVO.msgValidarRango}"/>'>	        
		
		 <input type="hidden" name='insnumequmen_' id='insnumequmen_' value='<c:out value="${sessionScope.escalaNumericaVO.insnumequmen}"/>'>	    
	       
		  <table border="0" align="center" width="100%" cellpadding="2" cellspacing="2">
			 <caption>Ingreso/Edición de Escala Numérica/Porcentual</caption>
				<tr>
			     <td   bgcolor="#FFFFFF" colspan="4">
                 <c:if test="${sessionScope.escalaNumericaVO.edicion}">
            			<input class='boton' name="cmd1" type="button" value="Actualizar" onClick="guardar()">
                 </c:if>
                   <c:if test="${ ! sessionScope.escalaNumericaVO.edicion}">
            			<input class='boton' name="cmd1" type="button" value="Guardar" onClick="guardar()">
                 </c:if>

					<input class='boton' name="cmd13" type="button" value="Nuevo" onClick="nuevo()">
					<input class='boton' name="cmd12" type="button" value="Cancelar" onClick="cancelar()">
			      </td>
			     </tr>
			          <tr><td width="60px"><font color="red">*</font>Vigencia</td>
		         <td width="250px"><select  '<c:if test="${sessionScope.escalaNumericaVO.edicion}">' DISABLED '</c:if>' name="filNumVigencia" style="width: 60px;"  onmouseover="this.disabled=true;" onmouseout="this.disabled=false;" >
					   <option value="-99"> --  //  -- </option>
					     <c:forEach  begin="0" items="${sessionScope.listaVigencia }" var="lista" >
					       <option value='<c:out  value="${ lista.codigo}"/>' <c:if test="${lista.codigo  ==  filtroEscalaNumVO.filNumVigencia }">SELECTED</c:if > ><c:out value="${lista.nombre}" /> </option>
					    </c:forEach>
					   </select>
				 </td>
				
	
  <c:choose> 
	       <c:when test="${sessionScope.filtroEscalaNumVO.filNumniveval  == paramsVO.NVLEVA_INST }">
		        <td width="70px">&nbsp;</td><td width="70px">&nbsp;</td>
		   </c:when>
		   
		   <c:when test="${sessionScope.filtroEscalaNumVO.filNumniveval  == paramsVO.NVLEVA_SED }">
	             <td width="70px"><font color="red">*</font>Sede</td>
	             <td width="250px" >
	                <select  '<c:if test="${sessionScope.escalaNumericaVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnumcodsede"    id="insnumcodsede"      onchange="javaScript:ajaxJornada_();" >
	                 <option value="-99" >--Seleccione uno--</option>
					   <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaNumericaVO.insnumcodsede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					  </c:forEach>   
	                </select>
	             </td>
		   </c:when>

           <c:when test="${sessionScope.filtroEscalaNumVO.filNumniveval  == paramsVO.NVLEVA_JORN }">
	               <td width="70px" ><font color="red">*</font>Jornada</td>
	               <td width="250px">
	                 <select  '<c:if test="${sessionScope.escalaNumericaVO.edicion}">' DISABLED '</c:if>'  style="width: 120px;" name="insnumcodjorn"    id="insnumcodjorn"      onchange="javaScript:ajaxMetodologia_();" >
	                   <option value="-99" >--Seleccione uno--</option>
					   <c:forEach begin="0" items="${sessionScope.listaJornada}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaNumericaVO.insnumcodjorn }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>   
	                 </select>
	               </td>
			</c:when>
		     
		   <c:when test="${sessionScope.filtroEscalaNumVO.filNumniveval  == paramsVO.NVLEVA_METD }">
	            <td width="70px" ><font color="red">*</font>Metodología</td><td width="250px" >
	               <select  '<c:if test="${sessionScope.escalaNumericaVO.edicion}">' DISABLED '</c:if>'  style="width: 120px;" name="insnumcodmetod"  id="insnumcodmetod"     >
	                   <option value="-99" >--Seleccione uno--</option>
					   <c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaNumericaVO.insnumcodmetod}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
	               </select>
	            </td>
		   </c:when>
		     
		    <c:when test="${sessionScope.filtroEscalaNumVO.filNumniveval  == paramsVO.NVLEVA_NVL }">
	          <td width="70px"><font color="red">*</font>Nivel</td><td>
	              <select  '<c:if test="${sessionScope.escalaNumericaVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnumcodnivel"   id="insnumcodnivel"    onchange="javaScript:ajaxGrado_2();">
	                   <option value="-99" >--Seleccione uno--</option>
					   <c:forEach begin="0" items="${sessionScope.listaNivel}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.escalaNumericaVO.insnumcodnivel }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
	              </select>
	           </td>
	       </c:when>
	       
	        <c:when test="${sessionScope.filtroEscalaNumVO.filNumniveval  == paramsVO.NVLEVA_GRD }">
	         <td width="70px"><font color="red">*</font>Grado</td>
	         <td  width="250px">
	           <select  '<c:if test="${sessionScope.escalaNumericaVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnumcodgrado" id="insnumcodgrado"   >
	               <option value="-99" >--Seleccione uno--</option>
					  <c:forEach begin="0" items="${sessionScope.listaGrado  }" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaNumericaVO.insnumcodgrado}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					  </c:forEach> 
	           </select>
	          </td>
	       </c:when>
	       
	       
	       <c:when test="${sessionScope.filtroEscalaNumVO.filNumniveval  == paramsVO.NVLEVA_SED_JORN }">
	           <td width="70px"><font color="red">*</font>Sede</td>
	            <td width="250px" >
	            <select  '<c:if test="${sessionScope.escalaNumericaVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnumcodsede"    id="insnumcodsede"      onchange="javaScript:ajaxJornada_2();" >
	                 <option value="-9" >--Seleccione uno--</option>
					   <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaNumericaVO.insnumcodsede }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					    </c:forEach> 
					  
	             </select>
	            </td>
	          </tr>
			  <tr>
				<td width="70px"><font color="red">*</font>Jornada</td>
				<td><select  '<c:if test="${sessionScope.escalaNumericaVO.edicion}">' DISABLED '</c:if>'  style="width: 120px;" name="insnumcodjorn"    id="insnumcodjorn"      onchange="javaScript:ajaxMetodologia_();" >
				     <option value="-99" >--Seleccione uno--</option>
				         <c:forEach begin="0" items="${sessionScope.listaJornada}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaNumericaVO.insnumcodjorn }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					    </c:forEach> 
				    </select>
				</td> 
		   </c:when>
		   
		  <c:when test="${sessionScope.filtroEscalaNumVO.filNumniveval  == paramsVO.NVLEVA_SED_METD }">
	          <td width="70px"><font color="red">*</font>Sede</td><td>
	                <select  '<c:if test="${sessionScope.escalaNumericaVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnumcodsede"    id="insnumcodsede"      onchange="javaScript:ajaxMetodologia_2();" >
	                  <option value="-9" >--Seleccione uno--</option>
					   <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaNumericaVO.insnumcodsede }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					  </c:forEach> 
	                </select>
	             </td>
	          </tr>
			  <tr >
				 <td width="70px"><font color="red">*</font>Metodología</td> <td>
				     <select  '<c:if test="${sessionScope.escalaNumericaVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnumcodmetod"  id="insnumcodmetod"   onchange="javaScript:ajaxNivel_();" >
				      <option value="-99" >--Seleccione uno--</option> 
					   <c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaNumericaVO.insnumcodmetod }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
				     </select>
				   </td>
		   </c:when>
		   
		     <c:when test="${sessionScope.filtroEscalaNumVO.filNumniveval  == paramsVO.NVLEVA_SED_NVL }">
	          <td width="70px">Sede</td><td width="250px">
	             <select  '<c:if test="${sessionScope.escalaNumericaVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnumcodsede"    id="insnumcodsede"      onchange="javaScript:ajaxNivel_2();" >
	                  <option value="-99" >--Seleccione uno--</option>
					   <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.escalaNumericaVO.insnumcodsede }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
	             </select>
	             </td>
	          </tr>
			  <tr>
				 <td><font color="red">*</font>Nivel</td><td>
				      
				      <select  '<c:if test="${sessionScope.escalaNumericaVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnumcodnivel"   id="insnumcodnivel"     >
				      <option value="-9" >--Seleccione uno--</option>
				       <c:forEach begin="0" items="${sessionScope.listaNivel}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.escalaNumericaVO.insnumcodnivel }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
				      </select>
				      </td>
		   </c:when>
		   
		    <c:when test="${sessionScope.filtroEscalaNumVO.filNumniveval  == paramsVO.NVLEVA_SED_GRD }">
	          <td width="70px"><font color="red">*</font>Sede</td><td width="250px">
	             <select  '<c:if test="${sessionScope.escalaNumericaVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnumcodsede"   id="insnumcodsede"    onchange="javaScript:ajaxGrado_2();"    >
	                 <option value="-99" >--Seleccione uno--</option>
					   <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaNumericaVO.insnumcodsede }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
	              </select>
	             </td>
	          </tr>
			  <tr>
			  <td width="70px" ><font color="red">*</font>Grado</td>
			  <td width="250px">
			     <select  '<c:if test="${sessionScope.escalaNumericaVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnumcodgrado" id="insnumcodgrado"   >
			         <option value="-99" >--Seleccione uno--</option>
					   <c:forEach begin="0" items="${sessionScope.listaGrado}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaNumericaVO.insnumcodgrado }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach> 
	            </select>
			   </td>
		   </c:when>
		   
		        		        
	       <c:when test="${sessionScope.filtroEscalaNumVO.filNumniveval  == paramsVO.NVLEVA_SED_JORN_METD }">
	             <td width="70px"><font color="red">*</font>Sede</td><td width="250px">
	                <select  '<c:if test="${sessionScope.escalaNumericaVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnumcodsede"    id="insnumcodsede"      onchange="javaScript:ajaxJornada_2();" >
	                  <option value="-99" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaNumericaVO.insnumcodsede }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
	                </select>
	               </td>
				 </tr>
				 <tr>
				  <td width="70px"><font color="red">*</font>Jornada</td>
				  <td><select  '<c:if test="${sessionScope.escalaNumericaVO.edicion}">' DISABLED '</c:if>'  style="width: 120px;" name="insnumcodjorn"    id="insnumcodjorn"      onchange="javaScript:ajaxMetodologia_2();" >
				     <option value="-99" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaJornada}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaNumericaVO.insnumcodjorn }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
				     </select>
				   </td> 
				   <td><font color="red">*</font>Metodología</td>
				   <td width="250px">
				     <select  '<c:if test="${sessionScope.escalaNumericaVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnumcodmetod"  id="insnumcodmetod"    >
				     <option value="-99" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaNumericaVO.insnumcodmetod }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
				     </select>
				  </td>
		     </c:when>
		     
		     
		      <c:when test="${sessionScope.filtroEscalaNumVO.filNumniveval  == paramsVO.NVLEVA_SED_JORN_NVL }">
	             <td width="70px"><font color="red">*</font>Sede</td>
	             <td width="250px">
	                <select  '<c:if test="${sessionScope.escalaNumericaVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnumcodsede"    id="insnumcodsede"      onchange="javaScript:ajaxJornada_2();" >
	                 <option value="-99" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaNumericaVO.insnumcodsede }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
				    
	                </select>
	               </td>
				 </tr>
				 <tr>
				  <td width="70px"><font color="red">*</font>Jornada</td>
				     <td width="250px">
				       <select  '<c:if test="${sessionScope.escalaNumericaVO.edicion}">' DISABLED '</c:if>'   style="width: 120px;" name="insnumcodjorn"    id="insnumcodjorn"      onchange="javaScript:ajaxNivel_2();" >
				     <option value="-99" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaJornada}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaNumericaVO.insnumcodjorn }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
				    
				      </select>
				    </td> 
				  <td width="70px"><font color="red">*</font>Nivel</td>
				  <td width="250px">
				    <select  '<c:if test="${sessionScope.escalaNumericaVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnumcodnivel"   id="insnumcodnivel"     >
				     <option value="-99" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaNivel}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaNumericaVO.insnumcodnivel }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
				    
				    </select>
				  </td>
				  
		     </c:when>
		     
		     
		     <c:when test="${sessionScope.filtroEscalaNumVO.filNumniveval  == paramsVO.NVLEVA_SED_JORN_GRD }">
	              <td width="70px"><font color="red">*</font>Sede</td>
	              <td width="250px">
	                <select  '<c:if test="${sessionScope.escalaNumericaVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnumcodsede"    id="insnumcodsede"      onchange="javaScript:ajaxJornada_2();   " >
	                 <option value="-9" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaNumericaVO.insnumcodsede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
	                </select>
	               </td>
				 </tr>
				 <tr>  
				 <td width="70px"><font color="red">*</font>Jornada</td >
				  <td width="250px">
				    <select  '<c:if test="${sessionScope.escalaNumericaVO.edicion}">' DISABLED '</c:if>'  style="width: 120px;" name="insnumcodjorn"    id="insnumcodjorn"      onchange="javaScript:ajaxGrado_2();" >
				      <option value="-99" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaJornada}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaNumericaVO.insnumcodjorn }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
				    </select>
				   </td> 
				 <td width="70px"><font color="red">*</font>Grado</td>
				 <td width="250px">
				   <select  '<c:if test="${sessionScope.escalaNumericaVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnumcodgrado" id="insnumcodgrado"   >
				   <option value="-99" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaGrado}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaNumericaVO.insnumcodgrado }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
				   </select>
				 </td>
				  
		     </c:when>
		    
	      
	      
	       <c:when test="${sessionScope.filtroEscalaNumVO.filNumniveval  == paramsVO.NVLEVA_SED_METD_NVL }">
	             <td width="70px"><font color="red">*</font>Sede</td>
	             <td width="250px">
	               <select  '<c:if test="${sessionScope.escalaNumericaVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnumcodsede"    id="insnumcodsede"      onchange="javaScript:ajaxMetodologia_2();" >
	                  <option value="-9" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaNumericaVO.insnumcodsede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
	               </select>
	             </td>
				 </tr>
				 <tr>
				 <td width="70px"><font color="red">*</font>Metodología</td> 
				 <td width="250px">
				   <select  '<c:if test="${sessionScope.escalaNumericaVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnumcodmetod"  id="insnumcodmetod"   onchange="javaScript:ajaxNivel_2();" >
				    <option value="-9" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaNumericaVO.insnumcodmetod}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
				   </select>
				 </td>
		         <td width="70px"><font color="red">*</font>Nivel</td>
		         <td width="250px">
		          <select  '<c:if test="${sessionScope.escalaNumericaVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnumcodnivel"   id="insnumcodnivel"    onchange="javaScript:ajaxGrado_();">
		            <option value="-9" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaNivel}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaNumericaVO.insnumcodnivel}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
		          </select>
		          </td>
				 
		     </c:when>
		          		        
	       <c:when test="${sessionScope.filtroEscalaNumVO.filNumniveval  == paramsVO.NVLEVA_SED_METD_GRD }">
	             <td width="70px"><font color="red">*</font>Sede</td><td><select  '<c:if test="${sessionScope.escalaNumericaVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnumcodsede"    id="insnumcodsede"      onchange="javaScript:ajaxMetodologia_2();" >
	             	<option value="-9" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaNumericaVO.insnumcodsede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
	             </select></td>
				 </tr>
				 <tr>
				   <td width="70px"><font color="red">*</font>Metodología</td> 
				   <td width="250px">
				     <select  '<c:if test="${sessionScope.escalaNumericaVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnumcodmetod"  id="insnumcodmetod" onchange="javaScript:ajaxGrado_2();"  >
				       <option value="-9" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaNumericaVO.insnumcodmetod}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
				     </select>
				   <td width="70px" ><font color="red">*</font>Grado</td>
				   <td width="250px">
				     <select  '<c:if test="${sessionScope.escalaNumericaVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnumcodgrado" id="insnumcodgrado"   >
				       <option value="-9" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listGrado}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaNumericaVO.insnumcodgrado}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
				    
				     </select>
				    </td>
		     </c:when>
		     
	       <c:when test="${sessionScope.filtroEscalaNumVO.filNumniveval  == paramsVO.NVLEVA_SED_NVL_GRD  }">
	             <td width="70px" ><font color="red">*</font>Sede</td>
	              <td>
	               <select  '<c:if test="${sessionScope.escalaNumericaVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnumcodsede"    id="insnumcodsede"      onchange="javaScript:ajaxNivel_2();" >
	                 <option value="-9" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaNumericaVO.insnumcodsede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
	               </select>
	              </td>
				 </tr>
				 <tr><td width="70px" ><font color="red">*</font>Nivel</td>
				     <td width="250px">
				        <select  '<c:if test="${sessionScope.escalaNumericaVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnumcodnivel"   id="insnumcodnivel"    onchange="javaScript:ajaxGrado_2();">
				       	  <option value="-9" >--Seleccione uno--</option>
	                      <c:forEach begin="0" items="${sessionScope.listaNivel}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaNumericaVO.insnumcodnivel}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
				        </select>
				        </td>
				     <td width="70px" ><font color="red">*</font>Grado</td>
				     <td width="250px">
				       <select  '<c:if test="${sessionScope.escalaNumericaVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnumcodgrado" id="insnumcodgrado"   >
				         <option value="-9" >--Seleccione uno--</option>
	                      <c:forEach begin="0" items="${sessionScope.listaGrado}" var="sed">
						   <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaNumericaVO.insnumcodgrado}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					      </c:forEach>
				       </select>
				       </td>
		     </c:when>
		     
	     
		     
	       <c:when test="${sessionScope.filtroEscalaNumVO.filNumniveval  == paramsVO.NVLEVA_SED_JORD_METD_NVL }">
	             <td width="70px" ><font color="red">*</font>Sede</td>
	             <td width="250px">
	               <select  '<c:if test="${sessionScope.escalaNumericaVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnumcodsede"    id="insnumcodsede"      onchange="javaScript: borrar_combo2(document.frmNuevo.insnivcodmetod  );borrar_combo2(document.frmNuevo.insnivcodnivel );  ajaxJornada_2();" >
	                 <option value="-9" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaNumericaVO.insnumcodsede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
	               </select>
	              </td>
				 </tr>
				 <tr>
				  <td width="70px" ><font color="red">*</font>Jornada</td>
				  <td width="250px">
				    <select  '<c:if test="${sessionScope.escalaNumericaVO.edicion}">' DISABLED '</c:if>'  style="width: 120px;" name="insnumcodjorn"    id="insnumcodjorn"      onchange="javaScript:ajaxMetodologia_2();" >
				      <option value="-9" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaJornada}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaNumericaVO.insnumcodjorn}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
				    </select>
				  </td> 
				  <td width="70px" ><font color="red">*</font>Metodología</td> 
				  <td width="250px" >
				    <select  '<c:if test="${sessionScope.escalaNumericaVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnumcodmetod"  id="insnumcodmetod"   onchange="javaScript:ajaxNivel_2();" >
				      <option value="-9" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaNumericaVO.insnumcodmetod}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
				    </select></td>
		         </tr>
		         <tr><td width="70px" ><font color="red">*</font>Nivel</td>
		             <td width="250px">
		                <select  '<c:if test="${sessionScope.escalaNumericaVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" style="width: 140px;" name="insnumcodnivel"   id="insnumcodnivel"    onchange="javaScript:ajaxGrado3_();">
		                   <option value="-9" >--Seleccione uno--</option>
	                       <c:forEach begin="0" items="${sessionScope.listaNivel}" var="sed">
						  <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaNumericaVO.insnumcodnivel}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					     </c:forEach>
				        </select>
		               </td>
		     </c:when>
		     
		       <c:when test="${sessionScope.filtroEscalaNumVO.filNumniveval  == paramsVO.NVLEVA_SED_JORD_METD_GRD }">
	              <td width="70px" ><font color="red">*</font>Sede</td>
	              <td width="250px">
	                <select  '<c:if test="${sessionScope.escalaNumericaVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnumcodsede"    id="insnumcodsede"      onchange="javaScript: borrar_combo2(document.frmNuevo.insnivcodmetod);borrar_combo2(document.frmNuevo.insnivcodgrado); ajaxJornada_2();" >
	                  <option value="-9" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaNumericaVO.insnumcodsede }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
	                </select>
	                </td>
				 </tr>
				 <tr>
				  <td width="70px" ><font color="red">*</font>Jornada</td>
				  <td width="250px">
				    <select  '<c:if test="${sessionScope.escalaNumericaVO.edicion}">' DISABLED '</c:if>'  style="width: 120px;" name="insnumcodjorn"    id="insnumcodjorn"      onchange="javaScript:ajaxMetodologia_2();" >
				    	<option value="-9" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaJornada}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaNumericaVO.insnumcodjorn}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
				    </select>
				   </td> 
				  <td width="70px" ><font color="red">*</font>Metodología</td> 
				  <td width="250px">
				     <select  '<c:if test="${sessionScope.escalaNumericaVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnumcodmetod"  id="insnumcodmetod"   onchange="javaScript:ajaxGrado_2();" >
				       <option value="-9" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaNumericaVO.insnumcodmetod}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
				     </select>
				     </td>
		         </tr>
		         <tr><td width="70px" >Grado</td>
		         <td><select  '<c:if test="${sessionScope.escalaNumericaVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnumcodgrado" id="insnumcodgrado" >
		            <option value="-9" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaGrado}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaNumericaVO.insnumcodgrado}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
				   
		         </select></td>
		      </c:when>
		     
		     
		     
		     
		     
		     
		     
		     
		      
		       <c:when test="${sessionScope.filtroEscalaNumVO.filNumniveval  == paramsVO.NVLEVA_SED_METD_NVL_GRD }">
	              <td width="70px"><font color="red">*</font>Sede</td><td width="250px">
	                 <select  '<c:if test="${sessionScope.escalaNumericaVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnumcodsede"    id="insnumcodsede"      onchange="javaScript:borrar_combo2(document.frmNuevo.insnivcodnivel); borrar_combo2(document.frmNuevo.insnivcodgrado); ajaxMetodologia_2();" >
	                  <option value="-9" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaNumericaVO.insnumcodsede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
				      
	                 </select>
	                 </td>
				 </tr>
				 <tr>
				  <td width="70px"><font color="red">*</font>Metodología</td> 
				   <td width="250px" >
				      <select  '<c:if test="${sessionScope.escalaNumericaVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnumcodmetod"  id="insnumcodmetod"   onchange="javaScript:ajaxNivel_2();" >
				      <option value="-9" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaNumericaVO.insnumcodmetod}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
				      </select>
				   </td>
                  <td width="70px" ><font color="red">*</font>Nivel</td>
                  <td width="250px" >
                    <select  '<c:if test="${sessionScope.escalaNumericaVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" style="width: 140px;" name="insnumcodnivel"   id="insnumcodnivel"    onchange="javaScript:ajaxGrado_2();">
                      <option value="-9" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaNivel}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaNumericaVO.insnumcodnivel}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
				    </select>
                  </td>
                 </tr>
		         <tr>
		          <td width="70px" ><font color="red">*</font>Grado</td>
		          <td width="250px">
		           <select  '<c:if test="${sessionScope.escalaNumericaVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnumcodgrado" id="insnumcodgrado" >
                      <option value="-9" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaGrado}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaNumericaVO.insnumcodgrado}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>		           
		           </select></td>
		      </c:when>
		      
		      
		      
		      
		      
		      
		      
		      
		      
		        <c:when test="${sessionScope.filtroEscalaNumVO.filNumniveval  == paramsVO.NVLEVA_SED_JORD_METD_NVL_GRD }">
	              <td width="70px"><font color="red">*</font>Sede</td><td width="250px">
	  
	                <select  '<c:if test="${sessionScope.escalaNumericaVO.edicion}">' DISABLED '</c:if>'   style="width: 140px;" name="insnumcodsede"    id="insnumcodsede"      onchange="javaScript:ajaxJornada_2();" >
	                   <option value="-9" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaNumericaVO.insnumcodsede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
	                </select>
	  
	                </td>
				 </tr>
				 <tr>
				  <td width="70px" ><font color="red">*</font>Jornada</td>
				  <td width="250px">
		
				    <select  '<c:if test="${sessionScope.escalaNumericaVO.edicion}">' DISABLED '</c:if>'  style="width: 120px;" name="insnumcodjorn"    id="insnumcodjorn"      onchange="javaScript:ajaxMetodologia_2();" >
				     <option value="-9" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaJornada}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaNumericaVO.insnumcodnivel}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
				   </select>
		
				  </td> 
				  <td width="70px"><font color="red">*</font>Metodología</td width="250px"><td>
		
				    <select  '<c:if test="${sessionScope.escalaNumericaVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnumcodmetod"  id="insnumcodmetod"   onchange="javaScript:ajaxNivel_2();" >
				   	  <option value="-9" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaNumericaVO.insnumcodmetod}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
				    </select>
		
				   </td>
                 </tr>
		         <tr>
		         
                  <td width="70px"><font color="red">*</font>Nivel</td>
                  <td width="250px">
        
                    <select  '<c:if test="${sessionScope.escalaNumericaVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" style="width: 140px;" name="insnumcodnivel"   id="insnumcodnivel"    onchange="javaScript:ajaxGrado_2();">
                      <option value="-9" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaNivel}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaNumericaVO.insnumcodnivel}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
                    </select>
        
                    </td>
                  <td width="70px"><font color="red">*</font>Grado</td>
                  <td width="250px">
        
                    <select  '<c:if test="${sessionScope.escalaNumericaVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnumcodgrado" id="insnumcodgrado" >
                      <option value="-9" >--Seleccione uno--</option>
	                   <c:forEach begin="0" items="${sessionScope.listaGrado}" var="sed">
						 <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaNumericaVO.insnumcodgrado }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   </c:forEach>
                    </select>
        
                    </td>
		      </c:when>
		    
	       </c:choose>
	       
	       
	       
		</tr> 
					<tr><td width="60px"><font color="red">*</font>Rango:</td>
					    <td width="260px"><table border="0" ><tr><td> &nbsp;Desde</td><td><input type="text" name="insnumvalmin"  id="insnumvalmin" value='<c:out value="${sessionScope.escalaNumericaVO.insnumvalmin }"/>' size="6" maxlength="6" style="width: 45px;font-size: 12;"   onkeypress="return validarNumeros(event)"   ></td><td>Hasta&nbsp;</rd><td><input type="text" name="insnumvalmax"  id="insnumvalmax" value='<c:out value="${sessionScope.escalaNumericaVO.insnumvalmax }"/>' size="6" maxlength="6"  style="width: 45px;font-size: 12;"  onkeypress="return validarNumeros(event)"  ></td>
					 	  </tr>
					 	  </table>
					 	      <td width="70px"><font color="red">*</font>Orden</td>
					    <td><input type="text" id="insnumorden" name="insnumorden" value='<c:out value="${sessionScope.escalaNumericaVO.insnumorden }"/>'   onkeypress="return validarNumeros2(event)"   size="2" maxlength="4" style="font-size: 12"></td>
									    					    
					 
					  </tr>
					  <tr>  
					   <!-- 
					   <td colspan="1" width="40px" ><font color="red">*</font>Equivalencia Institucion</td>
					    <td  colspan="1" ><select    style="width: 40px;" name="insnumequinst" id="insnumequinst"></select>
					    </td>
					     -->
					     <td colspan="1" width="40px"><font color="red">*</font>Equivalencia Escala MEN</td>
					    <td  colspan="2" >
					    <select   style="width: 180px;" name="insnumequmen" id="insnumequmen">
					        <option value="-99" >--Seleccione uno--</option>
					       <c:forEach begin="0" items="${requestScope.listaMen}" var="sed">
						      <option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.escalaNumericaVO.insnumequmen}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					       </c:forEach>  
					      </select>
					    </td>
                     </tr>
					   <tr>
				 	    <td>Descripción</td>
				 	    <td colspan="3"><textarea  name="insnumdescripcion" id="insnumdescripcion" cols="85" rows="3" style="width:500px;height:50px;font-size: 12;"   ><c:out value="${sessionScope.escalaNumericaVO.insnumdescripcion}"/></textarea></td>
				 	</tr>
				 	<tr style="display:none"><td><iframe name="frameAjaxNuevo" id="frameAjaxNuevo"></iframe></td></tr>
			</table>	
		 </form>
	 </td>
	</tr>
     </table>
 
</body>
<script> 
       	  
</script>
</html>