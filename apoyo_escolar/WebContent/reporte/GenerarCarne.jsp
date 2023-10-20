<%@ page contentType="text/html; charset=iso-8859-9" language="java" errorPage="" %>
<%@ page import="siges.util.Recursos" %> 
<jsp:useBean id="filtroCarne" class="siges.reporte.beans.FiltroBeanCarne" scope="session"/><jsp:setProperty name="filtroCarne" property="*"/>
<jsp:useBean id="paramsVO" class="siges.reporte.beans.ParamsVO" scope="request"/>

<%@include file="../parametros.jsp"%>   
		<script languaje='JavaScript'>
			
			var nav4=window.Event ? true : false;
			function acepteNumeros(eve){
				var key=nav4?eve.which :eve.keyCode;
				return(key<=13 || (key>=48 && key<=57));
			}
			function hacerValidaciones_frm(forma){
			  if (trim(forma.id.value).length == 0){
				  	validarLista(forma.localidad, "- Localidad", 1);
				  //validarLista(forma.listaTiposCol, "- Tipo de Institución", 1);
				  	validarLista(forma.institucion, "- Institución", 1);
				   	validarLista(forma.sede, "- Sede", 1);
				   	validarLista(forma.jornada, "- Jornada", 1); 
				  	validarLista(forma.metodologia, "- Metodología", 1);
				   	validarLista(forma.grado, "- Grado", 1);
			   }else{
				  if (trim(forma.id.value).length > 0)
					  validarCampo(forma.id, "- Identificación",1,20);
			   }
			   
			   validarSeleccion(forma.formatoCarne_,'- Seleccione el formato de carné.');
			}

			function guardar(tipo){ 
				if(validarForma(document.frm)){
					document.frm.tipo.value=tipo;
					document.frm.cmd.value="Buscar";
					 
					 //Formato de carne
					 if(document.frm.formatoCarne_[0].checked == true){
					   document.frm.formatoCarne.value = 1; //Un carne por hoja
					 }else if(document.frm.formatoCarne_[1].checked == true){
					   document.frm.formatoCarne.value = 2; //Varios carne por hoja (carta)
					 }else{
					   document.frm.formatoCarne.value = 3; //Varios carne por hoja (Oficio)
					 }
					  
					  
					  document.frm.localidad.disabled = false;
					  document.frm.institucion.disabled = false;
					  document.frm.sede.disabled = false;
					  document.frm.jornada.disabled = false;
					  document.frm.metodologia.disabled = false;
					  if(document.frm.id.value == ''){
					    document.frm.id.value = ' ';
					  }
					document.frm.submit();			
				}	
			}

			
			function cancelar(){
				if (confirm('¿Desea cancelar la generación de carnés?')){
 					document.frm.cmd.value="Cancelar";
					parent.location.href='<c:url value="/bienvenida.jsp"/>';
				}
			}
			
			function Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre){
				this.id_Hijos= id_Hijos; this.Hijos= Hijos; this.Sel_Hijos= Sel_Hijos; this.id_Padre=id_Padre;
			}
			function borrar_combo(combo){
				for(var i = combo.length - 1; i >= 0; i--) {
					if(navigator.appName == "Netscape") combo.options[i] = null;
					else combo.remove(i);
				}
				combo.options[0] = new Option("--Todos--","-9");
			}
			function borrar_combo1(combo){
				for(var i = combo.length - 1; i >= 0; i--) {
					if(navigator.appName == "Netscape") combo.options[i] = null;
					else combo.remove(i);
				}
				combo.options[0] = new Option("--Seleccione una--","-9");
			}
			
 
	function borrar_combo2(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("--Seleccione uno","-9");
	}
   

   function lanzar(tipo){
				document.frm.tipo.value=tipo;
				document.frm.action="<c:url value="/reporte/ControllerCarneFiltroEdit.do"/>";
				document.frm.target="";
				document.frm.submit();
  }
	
   function	ajaxLocalidad(){
     
      borrar_combo2(document.frm.localidad);
      borrar_combo2(document.frm.institucion);
	  borrar_combo2(document.frm.sede);
	  borrar_combo2(document.frm.jornada);
  	  borrar_combo2(document.frm.metodologia);
	  borrar_combo2(document.frm.grado);
	  borrar_combo2(document.frm.grupo);
	  
	  document.frmAjax.target = 'frameAjaxNuevo';
      document.frmAjax.cmd.value = document.frmAjax.CMD_AJAX_LOC.value;
      document.frmAjax.submit();
	}
	
	function	ajaxColegio(){
	 
	  borrar_combo2(document.frm.institucion);
	  borrar_combo2(document.frm.sede);
	  borrar_combo2(document.frm.jornada);
  	  borrar_combo2(document.frm.metodologia);
	  borrar_combo2(document.frm.grado);
	  borrar_combo2(document.frm.grupo);
	  document.frmAjax.target = 'frameAjaxNuevo';
	  document.frmAjax.ajax[0].value = document.frm.localidad.value;
	  document.frmAjax.ajax[1].value = document.frm.listaTiposCol.value;
      document.frmAjax.cmd.value = document.frmAjax.CMD_AJAX_INST.value;
      document.frmAjax.submit();
	}
	
	 function	ajaxSede(){
	  
	  borrar_combo2(document.frm.sede);
	  borrar_combo2(document.frm.jornada);
  	  borrar_combo2(document.frm.metodologia);
	  borrar_combo2(document.frm.grado);
	  borrar_combo2(document.frm.grupo);	  
	  document.frmAjax.target = 'frameAjaxNuevo';
	  document.frmAjax.ajax[0].value = document.frm.institucion.value;
      document.frmAjax.cmd.value = document.frmAjax.CMD_AJAX_SED.value;
      document.frmAjax.submit();
	}
	
	
	function	ajaxJornd(){
	 
	  borrar_combo2(document.frm.jornada);
  	  borrar_combo2(document.frm.metodologia);
	  borrar_combo2(document.frm.grado);
	  borrar_combo2(document.frm.grupo);	
	  document.frmAjax.target = 'frameAjaxNuevo';
	  document.frmAjax.ajax[0].value = document.frm.institucion.value;
	  document.frmAjax.ajax[1].value = document.frm.sede.value;
	  document.frmAjax.cmd.value = document.frmAjax.CMD_AJAX_JORD.value;
      document.frmAjax.submit();
	}
	
	function	ajaxMetodo(){
	 
  	  borrar_combo2(document.frm.metodologia);
	  borrar_combo2(document.frm.grado);
	  borrar_combo2(document.frm.grupo);	
	  document.frmAjax.target = 'frameAjaxNuevo';
	  document.frmAjax.ajax[0].value = document.frm.institucion.value;
	  document.frmAjax.ajax[1].value = document.frm.sede.value;
	  document.frmAjax.cmd.value = document.frmAjax.CMD_AJAX_METD.value;
      document.frmAjax.submit();
	}
	
	
	
	function	ajaxGrado(){
	  
	  borrar_combo2(document.frm.grado);
	  borrar_combo2(document.frm.grupo);		  
	  document.frmAjax.target = 'frameAjaxNuevo';
	  document.frmAjax.ajax[0].value = document.frm.institucion.value;
	  document.frmAjax.ajax[1].value = document.frm.sede.value;
	  document.frmAjax.ajax[2].value = document.frm.jornada.value;
	  document.frmAjax.ajax[3].value = document.frm.metodologia.value;
	  
	  document.frmAjax.cmd.value = document.frmAjax.CMD_AJAX_GRAD.value;
      document.frmAjax.submit();
	}
	
	
	function	ajaxGrupo(){
	 
	  borrar_combo2(document.frm.grupo);
	  document.frmAjax.target = 'frameAjaxNuevo';
	  document.frmAjax.ajax[0].value = document.frm.institucion.value;
	  document.frmAjax.ajax[1].value = document.frm.sede.value;
	  document.frmAjax.ajax[2].value = document.frm.jornada.value;
	  document.frmAjax.ajax[3].value = document.frm.metodologia.value;
	  document.frmAjax.ajax[4].value = document.frm.grado.value;
	  
	  document.frmAjax.cmd.value = document.frmAjax.CMD_AJAX_GRUP.value;
      document.frmAjax.submit();
	}
	
	
	
	function limpiarCombos(){
	
	
     if(!document.frm.localidad.disabled){
	   document.frm.localidad.selectedIndex  = 0;
	   limpiaCombo(document.frm.institucion);
	   limpiaCombo(document.frm.sede);
	   limpiaCombo(document.frm.jornada);
	   limpiaCombo(document.frm.metodologia);
	   limpiaCombo(document.frm.grado);
	   limpiaCombo(document.frm.grupo);
	 }
      //limpiaCombo(document.frm.localidad);
	/*limpiaCombo(document.frm.institucion);
	  limpiaCombo(document.frm.sede);
	  limpiaCombo(document.frm.jornada);
  	  limpiaCombo(document.frm.metodologia);
	  limpiaCombo(document.frm.grado);
	  limpiaCombo(document.frm.grupo);*/
	  
	  	  
	  if(!document.frm.institucion.disabled){
	      document.frm.institucion.selectedIndex  = 0;
		  document.frm.listaTiposCol.selectedIndex  = 0;
	      limpiaCombo(document.frm.sede);
		  limpiaCombo(document.frm.jornada);
	  	  limpiaCombo(document.frm.metodologia);
		  limpiaCombo(document.frm.grado);
		  limpiaCombo(document.frm.grupo);
	    return;
	  }
	  
	  if(!document.frm.sede.disabled){
	      document.frm.sede.selectedIndex  = 0;
	      limpiaCombo(document.frm.jornada);
	  	  limpiaCombo(document.frm.metodologia);
		  limpiaCombo(document.frm.grado);
		  limpiaCombo(document.frm.grupo);
	    return;
	  }
	  
	  if(!document.frm.jornada.disabled){
	      document.frm.jornada.selectedIndex  = 0;
	      limpiaCombo(document.frm.metodologia);
		  limpiaCombo(document.frm.grado);
		  limpiaCombo(document.frm.grupo);
	    return;
	  }
	    
	    if(!document.frm.metodologia.disabled){
	      document.frm.metodologia.selectedIndex  = 0;
		  limpiaCombo(document.frm.grado);
		  limpiaCombo(document.frm.grupo);
	    return;
	  }
	   
	}
	
	function limpiaCombo(object){
	   if(!object.disabled){
	     borrar_combo2(object); 
	   }
	}
	
	
	   function right(e)
{    
    if (navigator.appName == 'Netscape' && (e.which == 3 || e.which == 2 || e.which == 17)){
                    limpiarCombos();
      return false;
    }else if (navigator.appName == 'Microsoft Internet Explorer' && (event.button == 2 || event.button == 3 || event.button == 17 )) {
           limpiarCombos();
        return false;
    }
    
    
  return true;
}


function checkKey(evt) {
  if (evt.ctrlKey){
   limpiarCombos();
   return false;
  }
} 

		</script>
	<%@include file="../mensaje.jsp"%>
	
	<form method="post" name="frmAjax" action='<c:url value="/reporte/FiltroCarneAjax.do"/>'>
		<input type="hidden" name="tipo" value='<c:out value="${ paramsVO.FICHA_CARME}"/>'>
		<input type="hidden" name="cmd"  value='-1'>
		<input type="hidden" name="CMD_AJAX_INST"  id="CMD_AJAX_INST"  value='<c:out value="${paramsVO.CMD_AJAX_INST}"/>'>
		<input type="hidden" name="CMD_AJAX_LOC"  id="CMD_AJAX_LOC"  value='<c:out value="${paramsVO.CMD_AJAX_LOC}"/>'>
		<input type="hidden" name="CMD_AJAX_SED"  id="CMD_AJAX_SED"  value='<c:out value="${paramsVO.CMD_AJAX_SED }"/>'>
		<input type="hidden" name="CMD_AJAX_JORD"  id="CMD_AJAX_JORD"  value='<c:out value="${paramsVO.CMD_AJAX_JORD}"/>'>
		<input type="hidden" name="CMD_AJAX_METD"  id="CMD_AJAX_METD"  value='<c:out value="${paramsVO.CMD_AJAX_METD}"/>'>
		<input type="hidden" name="CMD_AJAX_GRAD"  id="CMD_AJAX_GRAD"  value='<c:out value="${paramsVO.CMD_AJAX_GRAD}"/>'>
		<input type="hidden" name="CMD_AJAX_GRUP"  id="CMD_AJAX_GRUP"  value='<c:out value="${paramsVO.CMD_AJAX_GRUP}"/>'>
		
		 <input type="hidden" name="CMD_BUSCAR"      id="CMD_BUSCAR"  value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>

		<c:forEach begin="0" end="5" var="i"><input type="hidden" name="ajax"></c:forEach>
	  </form>
	  
	  
	<form method="post" name="frm"   onKeyDown="checkKey(event)"  onSubmit="return validarForma(frm)" action='<c:url value="/reporte/FiltroGuardarCarne.jsp"/>'>
	<table border="0" align="center" width="100%">
	<caption>Generar Carn&eacute;s</caption>
		<tr>
		  <td width="45%" bgcolor="#FFFFFF">
        <input class="boton" name="cmd1" type="button" value="Generar" onClick="guardar(document.frm.tipo.value)">
        <input class="boton" name="cmd12" type="button" value="Cancelar" onClick="cancelar()">
		 </td>
		</tr>
	</table>
<!--//////////////////-->	
	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="tipo" value='<c:out value="${pageScope.tip}"/>'>	
	<input type="hidden" name="cmd" value="Cancelar">
	<INPUT TYPE="hidden" NAME="ext2"  VALUE=''>
	<INPUT TYPE="hidden" NAME="height" VALUE='180'>
	 
	
	<table border="0" align="center" width="100%">
		<tr height="1">
			<td rowspan="2" width="420" bgcolor="#FFFFFF"><img src="../etc/img/tabs/carnes_est_1.gif" alt="Generaci&oacute;n de Carnés" width="84"  height="26" border="0"><a href="javaScript:lanzar(2)"><img src="../etc/img/tabs/carnes_adm_doc_0.gif" alt="Administrativos y Docentes" width="84"  height="26" border="0"></a></td>
		</tr>
  </table>
  
  <TABLE width="100%" cellpadding="3" cellSpacing="0">
		<tr><td colspan="4" class="Fila0"   align="center">Formato de Carne Estudiantes</td></tr>
 
		<tr><td colspan="1"  align="center" width="120px;"><span class="style2" >*</span>Seleccione un formato:</td><td colspan="1"  align="center" width="150px">Un carne por hoja<input  type="radio" name="formatoCarne_" id="formatoCarne_"> </td><td colspan="1"  align="center">Formato Carta<input  type="radio"  name="formatoCarne_" id="formatoCarne_"> </td><td colspan="1"  align="center">Formato Oficio<input  type="radio"  name="formatoCarne_" id="formatoCarne_"> </td></tr>
		<tr><td colspan="4"><input  type="hidden" name="formatoCarne" id="formatoCarne"></td></tr>
  
	</TABLE>	
  
  
  								
									
								<TABLE width="100%" cellpadding="3" cellSpacing="0">
										<tr><td colspan="4" class="Fila0" align="center">Generación por estudiante</td></tr>
									  <tr>
										  <td colspan='4'>
											<span class="style2"><b>Nota:<br>
											Si necesita generar el carné de un solo estudiante, por favor ingrese el número de identificación del estudiante en el siguiente campo; de lo contrario, no lo diligencie.
											</b>
											</span>
											</td>
									  </tr>
										<tr>
										  <td>Número de Documento:</td>
										  <td><input type='text'   onmouseover="right(event)"  onmousedown="right(event)"    onclick="right(event);"  onkeydown="JavaScript:limpiarCombos();"   onkeypress="JavaScript:limpiarCombos();" name='id' id='id'  maxlength="20" value='<c:out value="${sessionScope.filtroCarne.id}"/>'    ></td>
										  <td>&nbsp;</td>
										  <td><input type='text' name='escondido'  maxlength="2" style="display:none"></td>
								  </tr>

		                          <!-- <tr><td colspan="4" class="Fila0" c align="center">Formato de Carne</td></tr>
		                           <tr><td colspan="1"  align="center" width="120px;"><span class="style2" >*</span>Seleccione un formato:</td><td colspan="1"  align="center" width="80px">Un carne por hoja<input  type="radio"> </td><td colspan="1"  align="center">Varios carnes por hoja<input  type="radio"> </td><td width="160px">&nbsp;</td></tr>
		                           <tr><td colspan="4"></td></tr>
                                   --> 
                                 <tr><td colspan="4" class="Fila0" align="center">Generación por ubicación</td></tr>
								 <tr><td><span class="style2" >*</span>Localidad:</td>
								      <td>  
								      <select name="localidad" style='width:210px;' onChange=' document.frm.id.value = ""; ajaxColegio();'  <c:if test="${!empty sessionScope.login.munId and    sessionScope.login.munId  > 0 }"> disabled="disabled" </c:if>	 >
                                       <option value='-9'>-- Seleccione una --</option> 
	                                    <c:forEach begin="0" items="${sessionScope.listaLocalidad}" var="fila">
									     <option value="<c:out value="${ fila.codigo }"/>" <c:if test="${sessionScope.filtroCarne.metodologia== fila.codigo }">SELECTED</c:if>><c:out value="${ fila.nombre }"/></option>
                                        </c:forEach>
                                      </select> 
                                   </td>
                                   <td>Tipo Colegio:</td>
								      <td>  
								     <select name="listaTiposCol" style='width:210px;' onChange='document.frm.id.value = ""; ajaxColegio();'  <c:if test="${!empty sessionScope.login.instId and    sessionScope.login.instId  > 0 }"> disabled="disabled" </c:if>	 >
                                        <option value="-99">-- Todos --</option>
                                        <c:forEach begin="0" items="${sessionScope.listaTipoColegio}" var="fila">
									     <option value="<c:out value="${ fila.codigo }"/>" <c:if test="${sessionScope.sectorId == fila.codigo }">SELECTED</c:if>><c:out value="${ fila.nombre }"/></option>
                                        </c:forEach>
                                      </select> 
                                   </td>
                                   </td>
                                  </tr>
                                  <tr> 
                                     <td><span class="style2" >*</span>Colegio:</td>
                                         <td colspan="3"> <select name="institucion"  style="width: 350px;"  onChange=' document.frm.id.value = ""; ajaxSede();'  <c:if test="${!empty sessionScope.login.instId and    sessionScope.login.instId  > 0 }"> disabled="disabled" </c:if> >
                                       <option value='-9'>-- Seleccione una --</option>
	                                    <c:forEach begin="0" items="${sessionScope.listaColegio}" var="fila">
									     <option value="<c:out value="${ fila.codigo }"/>" <c:if test="${sessionScope.filtroCarne.metodologia== fila.codigo }">SELECTED</c:if>><c:out value="${ fila.nombre }"/></option>
                                        </c:forEach>
                                      </select> 
								      <td>  
								     
                                   </td>                                   
								 </tr>
								 <tr><td><span class="style2" >*</span>Sede:</td>
								      <td>  
								      <select name="sede" style='width:210px;' onChange=' document.frm.id.value = ""; ajaxJornd();'  <c:if test="${!empty sessionScope.login.sedeId and    sessionScope.login.sedeId  > 0 }"> disabled="disabled" </c:if> >
                                       <option value='-9'>-- Seleccione una --</option>
	                                    <c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila">
									     <option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.filtroCarne.metodologia==fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option>
                                        </c:forEach>
                                      </select> 
                                   </td>
                                     <td><span class="style2" >*</span>Jornada:</td>
								      <td>  
								      <select name="jornada" style='width:210px;' onChange=' document.frm.id.value = ""; ajaxMetodo();' <c:if test="${!empty sessionScope.login.jornadaId and    sessionScope.login.jornadaId  > 0 }"> disabled="disabled" </c:if> >
                                       <option value='-9'>-- Seleccione una --</option>
	                                    <c:forEach begin="0" items="${requestScope.filtroJornadaF}" var="fila">
									     <option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.filtroCarne.metodologia==fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option>
                                        </c:forEach>
                                      </select> 
                                   </td>                                   
								 </tr>								 
								 <tr><td><span class="style2" >*</span>Metodolog&iacute;a:</td>
                           <td>
                        <select name="metodologia" style='width:210px;' onChange=' document.frm.id.value = ""; ajaxGrado();'>
                           <option value='-9'>-- Seleccione una --</option>
	                          <c:forEach begin="0" items="${requestScope.filtroMetodologiaF}" var="fila">
														<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.filtroCarne.metodologia==fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option>
                            </c:forEach>
                          </select>
                      </td>
										  <td><span class="style2" >*</span>Grado:</td>
										  <td>
                        <select name="grado" style='width:210px;' onChange=' document.frm.id.value = ""; ajaxGrupo();' >
												<option value='-9'>-- Seleccione una --</option>
												<c:forEach begin="0" items="${requestScope.filtroGradoF2}" var="fila">
													<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.filtroCarne.grado== fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option>
												</c:forEach>
                        </select>
                       </td>
								  </tr>
									<tr>
										<td>Grupo:</td>
										<td>
                      <select name="grupo"  onchange='document.frm.id.value = "";' style='width:210px;'>
                      <option value='-9'>-- Todos --</option>
                      </select>
                    </td> <input type="hidden" name="orden" value="-9"> <!-- 
										<td>Orden:</td>
										<td>
												<select name="orden">
													<option value='-' <c:if test="${sessionScope.filtroCarne.orden== '-9'}">SELECTED</c:if>>Apellidos</option>
													<option value='2' <c:if test="${sessionScope.filtroCarne.orden== '0'}">SELECTED</c:if>>Identificación</option>
													<option value='3' <c:if test="${sessionScope.filtroCarne.orden== '1'}">SELECTED</c:if>>Nombres</option>
													<option value='4' <c:if test="${sessionScope.filtroCarne.orden== '2'}">SELECTED</c:if>>Código</option>
												</select>										  
                      </td>
                       -->
								  </tr>
	<tr style="display:none"><td><iframe name="frameAjaxNuevo" id="frameAjaxNuevo"></iframe></td></tr>
  </TABLE>
<!--//////////////////////////////-->
</form>
<script>
	ajaxLocalidad();
	if(document.frm.localidad.value > 0){
	  ajaxColegio()
	}
//document.frm.metodologia.onchange();
//document.frm.grado.onchange();
</script>