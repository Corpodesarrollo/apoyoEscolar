<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<jsp:useBean id="filtroEst" class="siges.estudiante.beans.FiltroBean" scope="session"/>
<jsp:setProperty name="filtroEst" property="*"/>
<jsp:useBean id="paramsVO" class="siges.common.vo.Params" scope="request"/>

<%@include file="../parametros.jsp"%>
		<script languaje='javaScript'>
			<!--
			var nav4=window.Event ? true : false;
			function acepteNumeros(eve){
				var key=nav4?eve.which :eve.keyCode;
				return(key<=13 || (key>=48 && key<=57));
			}
			function hacerValidaciones_frm(forma){
				forma.id.value=trim(forma.id.value);
				forma.nombre1.value=trim(forma.nombre1.value);
				forma.nombre2.value=trim(forma.nombre2.value);
				forma.apellido1.value=trim(forma.apellido1.value);
				forma.apellido2.value=trim(forma.apellido2.value);
				if(forma.id.value=='' && forma.nombre1.value=='' && forma.nombre2.value=='' && forma.apellido1.value=='' && forma.apellido2.value==''){
					validarLista(forma.sede,"- Sede",1)
					validarLista(forma.jornada,"- Jornada",1)
					validarLista(forma.metodologia,"- Metodologia",1)
					validarLista(forma.grado,"- Grado",1)
				}
				if(forma.id.value=='')	forma.id.value=' ';
				if(forma.nombre1.value=='') forma.nombre1.value=' ';
				if(forma.nombre2.value=='') forma.nombre2.value=' ';
				if(forma.apellido1.value=='')	forma.apellido1.value=' ';
				if(forma.apellido2.value=='')	forma.apellido2.value=' ';
			}

			function nuevo(forma){
				forma.tipo.value='';
				forma.action='<c:url value="/estudiante/ControllerNuevoEdit.do"/>';
				forma.submit();
			}
			
			function cancelar(){
				parent.location.href='<c:url value="/bienvenida.jsp"/>';
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
			
			function borrar_combo2(combo){
				combo.selectedIndex=0;
			}
			function filtroEst4(combo_hijo4){
					borrar_combo2(combo_hijo4);
			}		
			
			 
	function ajaxSede(){  
	  //borrar_combo2(document.frm.jornada);
	  borrar_combo2(document.frm.metodologia);
  	  borrar_combo2(document.frm.grado);
 	  borrar_combo2(document.frm.grupo);  
    if(document.frm.filinst.value > 0){
   	  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
	  document.frmAjaxNuevo.ajax[0].value =  document.frm.filinst.value;
	  document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_SED.value;
	  document.frmAjaxNuevo.submit();
	  }
	}
	
	
	 
	function ajaxJornada(){ 
	  //borrar_combo(document.frm.jornada);
	  borrar_combo2(document.frm.metodologia);
  	  borrar_combo2(document.frm.grado);
 	  borrar_combo2(document.frm.grupo);	 
 	  
    if(document.frm.sede.value > 0){
   	  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
	  document.frmAjaxNuevo.ajax[0].value =  document.frm.filinst.value;
	  document.frmAjaxNuevo.ajax[1].value =  document.frm.sede.value;
	  document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_JORD.value;
	  document.frmAjaxNuevo.submit();
		}
	  }//else{
	  //alert("no entro");
	    //borrar_combo(document.frm.jornada);
	  //}
	
	
	function ajaxMetodologia(){
		//alert('ajaxMetodologia 2: '+document.frm.jornada.value);
	  borrar_combo2(document.frm.metodologia);
  	  borrar_combo2(document.frm.grado);
 	  borrar_combo2(document.frm.grupo); 
 	  if(document.frm.jornada.value > 0  ){
	   document.frmAjaxNuevo.target = 'frameAjaxNuevo';
	   document.frmAjaxNuevo.ajax[0].value =  document.frm.filinst.value;
	    document.frmAjaxNuevo.ajax[1].value = document.frm.sede.value;
	    document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_METD.value;
	   document.frmAjaxNuevo.submit();
	  }
	}
	 
	 
 
	 
	 
	 function ajaxGrado(){
 	  borrar_combo2(document.frm.grado);
 	  borrar_combo2(document.frm.grupo);
 	  if(document.frm.metodologia.value > 0){
	  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
	  document.frmAjaxNuevo.ajax[0].value =  document.frm.filinst.value;
	  document.frmAjaxNuevo.ajax[1].value = document.frm.sede.value;
      document.frmAjaxNuevo.ajax[2].value = document.frm.jornada.value;
      document.frmAjaxNuevo.ajax[3].value = document.frm.metodologia.value;
      document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_GRAD.value;
      document.frmAjaxNuevo.submit();
     }
	}
	
	
   function ajaxGrupo(){
	  borrar_combo2(document.frm.grupo);
	  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
      document.frmAjaxNuevo.ajax[0].value = document.frm.filinst.value;
      document.frmAjaxNuevo.ajax[1].value = document.frm.sede.value;
      document.frmAjaxNuevo.ajax[2].value = document.frm.jornada.value;
      document.frmAjaxNuevo.ajax[3].value = document.frm.metodologia.value;
      document.frmAjaxNuevo.ajax[4].value = document.frm.grado.value;
      document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_GRUP.value;
	  document.frmAjaxNuevo.submit();
   }
			 
			 
			//-->
		</script>
		<%@include file="../mensaje.jsp"%>	
	  
	   <form method="post" name="frmAjaxNuevo" action='<c:url value="/AjaxCommon.do"/>'>
		<input type="hidden" name="tipo" value='1'>
		<input type="hidden" name="cmd"  value='-1'>
		<input type="hidden" name="CMD_AJAX_SED"   id="CMD_AJAX_SED"   value='<c:out value="${paramsVO.CMD_AJAX_SED  }"/>'>
		<input type="hidden" name="CMD_AJAX_JORD"  id="CMD_AJAX_JORD"  value='<c:out value="${paramsVO.CMD_AJAX_JORD}"/>'>
		<input type="hidden" name="CMD_AJAX_METD"  id="CMD_AJAX_METD"  value='<c:out value="${paramsVO.CMD_AJAX_METD}"/>'>
		<input type="hidden" name="CMD_AJAX_GRUP"  id="CMD_AJAX_GRUP"  value='<c:out value="${paramsVO.CMD_AJAX_GRUP}"/>'>
		<input type="hidden" name="CMD_AJAX_GRAD"  id="CMD_AJAX_GRAD"  value='<c:out value="${paramsVO.CMD_AJAX_GRAD}"/>'>
		<input type="hidden" name="CMD_BUSCAR"     id="CMD_BUSCAR"     value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
		<c:forEach begin="0" end="5" var="i"><input type="hidden" name="ajax"></c:forEach>
	   </form>
	  
	  
	
		<FORM  METHOD="POST" name='frm' onSubmit=" return validarForma(frm)" action='<c:url value="/estudiante/FiltroGuardar.jsp"/>'>
			<INPUT TYPE="hidden" NAME="nivel"  VALUE="1">
			<INPUT TYPE="hidden" NAME="tipo"  VALUE="1">
			<INPUT TYPE="hidden" NAME="cmd2"  VALUE="buscar">
			<input type="hidden" name="filinst"      id="filinst"  value='<c:out value="${login.instId}"/>'>
			<table border="0" align="center" bordercolor="#FFFFFF" width="90%">
			<caption>Estudiantes, filtroEst de busqueda</caption>
				<tr>
				  <td>
						<INPUT class='boton' TYPE="submit" NAME="cmd1"  VALUE="Aceptar">
					</td>
				</tr>			
			</table>
			<table border="0" align="center" bordercolor="#FFFFFF" width="90%" cellpadding="2" cellSpacing="0">
				<tr><th colspan='4' class='Fila0'>Búsqueda por ubicación</th></tr>
				<tr>
					<td style='<c:if test="${sessionScope.login.perfil!=410 && sessionScope.login.perfil!=421}">display:none;</c:if>'><span class="style2">*</span> Sede:</td>
					<td style='<c:if test="${sessionScope.login.perfil!=410 && sessionScope.login.perfil!=421}">display:none;</c:if>' colspan="3">
						<select name="sede" id="sede" onChange='JavaScript:ajaxJornada();' style='width:406px;<c:if test="${sessionScope.login.perfil!=410 && sessionScope.login.perfil!=421}">display:none;</c:if>'>
						<option value='-99'>-- Seleccione uno --</option>
						<c:forEach begin="0" items="${sessionScope.filtroSedeF}" var="fila">
						<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.login.perfil==410 || sessionScope.login.perfil==421}"><c:if test="${sessionScope.filtroEst.sede== fila[0]}">SELECTED</c:if></c:if> <c:if test="${sessionScope.login.perfil!=410 && sessionScope.login.perfil!=421}"><c:if test="${sessionScope.login.sedeId== fila[0]}">SELECTED</c:if></c:if>>
						<c:out value="${fila[1]}"/></option>
						</c:forEach>
						</select>											
					</td>	
				</tr>
				<tr>
					<td style='<c:if test="${sessionScope.login.perfil!=410 && sessionScope.login.perfil!=421}">display:none;</c:if>'><span class="style2">*</span> Jornada:</td>
					<td style='<c:if test="${sessionScope.login.perfil!=410 && sessionScope.login.perfil!=421}">display:none;</c:if>' >
						<select name="jornada" id="jornada" style='width:120px;<c:if test="${sessionScope.login.perfil!=410 && sessionScope.login.perfil!=421}">display:none;</c:if>' onChange='JavaScript:ajaxMetodologia();'>
						  <option value='-9'>-- Seleccione uno --</option>
						  <c:if test="${sessionScope.login.perfil!=410 && sessionScope.login.perfil!=421}">
							  	<c:forEach begin="0" items="${sessionScope.filtroJornadaF}" var="fila">
							  	<c:if test="${sessionScope.login.sedeId== fila[2]}">
							     <option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.login.jornadaId== fila[0]}">SELECTED</c:if>>
							     <c:out value="${fila[1]}"/></option></c:if>
							   </c:forEach>
						  </c:if>
						  <c:if test="${sessionScope.login.perfil==410 || sessionScope.login.perfil==421}">
							   <c:forEach begin="0" items="${sessionScope.listaJornada}" var="fila">
							     <option value="<c:out value="${fila.codigo}"/>" <c:if test="${sessionScope.filtroEst.jornada == fila.codigo }">SELECTED</c:if>>
							     <c:out value="${fila.nombre}"/></option>
							   </c:forEach>
						   </c:if>
						</select>							
					</td>
					
					<td><span class="style2">*</span> Metodologia:</td>
					<td>
						<select name="metodologia" id="metodologia" style='width:120px;' onChange='JavaScript:ajaxGrado();'>
						<option value='-9'>-- Seleccione uno --</option>
				            <c:forEach begin="0" items="${sessionScope.listaMetodo}" var="fila">
				              <option value='<c:out value="${fila.codigo}"/>' <c:if test="${sessionScope.filtroEst.metodologia==fila.codigo}">SELECTED</c:if>><c:out value="${fila.nombre}"/></option>
				            </c:forEach>
						</select>							
					</td>
				</tr>
				<tr>
				<td><span class="style2">*</span> Grado:</td>
					<td>
                    <select name="grado" id="grado" onChange='JavaScript:ajaxGrupo();'>
                     <option value='-9'>-- Seleccione uno --</option>
                        <c:forEach begin="0" items="${sessionScope.listaGrado}" var="fila">
				            <option value='<c:out value="${fila.codigo}"/>' <c:if test="${sessionScope.filtroEst.grado==fila.codigo}">SELECTED</c:if>><c:out value="${fila.nombre}"/></option>
	                     </c:forEach>
                      </select>
					</td>	
					<td>Grupo:</td>
					<td>
						<select name="grupo" id="grupo" style='width:120px;'>
							<option value='-9'>-- Todos --</option>
							 <c:forEach begin="0" items="${sessionScope.listaGrupo}" var="fila">
				            <option value='<c:out value="${fila.codigo}"/>' <c:if test="${sessionScope.filtroEst.grupo ==fila.codigo}">SELECTED</c:if>><c:out value="${fila.nombre}"/></option>
	                     </c:forEach>
						</select>							
					</td>
				</tr>
				</table>
				<table border="0" align="center" bordercolor="#FFFFFF" width="90%" cellpadding="2" cellSpacing="0">
				<tr><th colspan='4' class='Fila0'>Búsqueda por identificación</th></tr>
				<tr>
					<td>Nº de identificación:</td>
					<td>
						<input type='text' name='id'  maxlength="15" value='<c:out value="${sessionScope.filtroEst.id}"/>'>
					</td>	
					<td>Primer nombre:</td>
					<td>
						<input type='text' name='nombre1' value='<c:out value="${sessionScope.filtroEst.nombre1}"/>'>
					</td>	
				</tr>	
				<tr>
					<td>Segundo nombre:</td>
					<td>
						<input type='text' name='nombre2' value='<c:out value="${sessionScope.filtroEst.nombre2}"/>'>
					</td>	
					<td>Primer apellido:</td>
					<td>
						<input type='text' name='apellido1' value='<c:out value="${sessionScope.filtroEst.apellido1}"/>'>
					</td>	
				</tr>	
				<tr>
					<td>Segundo apellido:</td>
					<td>
						<input type='text' name='apellido2' value='<c:out value="${sessionScope.filtroEst.apellido2}"/>'>
					</td>	
				</tr>	
			</table>
				<table border="0" align="center" bordercolor="#FFFFFF" width="90%" cellpadding="2" cellSpacing="0">
				<tr><th colspan='4' class='Fila0'>Criterio de ordenación</th></tr>
				<tr>
					<td>Orden:</td>
					<td>
						<select name="orden">
							<option value='4' <c:if test="${sessionScope.filtroEst.orden== '4'}">SELECTED</c:if>>Apellidos</option>
							<option value='3' <c:if test="${sessionScope.filtroEst.orden== '3'}">SELECTED</c:if>>Nombres</option>
							<option value='2' <c:if test="${sessionScope.filtroEst.orden== '2'}">SELECTED</c:if>>Número de identificación</option>
						</select>	
					</td>
				</tr>
				<tr style="display:none"><td><iframe name="frameAjaxNuevo" id="frameAjaxNuevo"></iframe></td></tr>	
			</table>	
		</FORM>
<script languaje='javaScript'>
ajaxMetodologia();
</script>