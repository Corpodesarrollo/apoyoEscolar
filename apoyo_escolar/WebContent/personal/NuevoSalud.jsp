<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<jsp:useBean id="nuevoPersonal" class="siges.personal.beans.Personal" scope="session"/><jsp:setProperty name="nuevoPersonal" property="*"/>
<jsp:useBean id="nuevoConvivencia" class="siges.estudiante.beans.Convivencia" scope="session"/><jsp:setProperty name="nuevoConvivencia" property="*"/>
<jsp:useBean id="nuevoSalud" class="siges.estudiante.beans.Salud" scope="session"/>
<jsp:useBean id="laboralVO" class="siges.personal.beans.LaboralVO" scope="session"/><jsp:setProperty name="laboralVO" property="*"/>
<jsp:useBean id="asistenciaVO" class="siges.estudiante.beans.AsistenciaVO" scope="session"/><jsp:setProperty name="asistenciaVO" property="*"/>
<jsp:useBean id="formacionVO" class="siges.personal.beans.FormacionVO" scope="session"/><jsp:setProperty name="formacionVO" property="*"/>
<jsp:useBean id="paramsVO" class="siges.personal.beans.ParamsVO" scope="page"/>
<%@include file="../parametros.jsp"%>

<c:set var="tip" value="4" scope="page"/>
		<c:if test="${sessionScope.NivelPermiso==1}"><c:set var="permisoBoton" value="none"/></c:if>
		<script languaje=' javaScript'>
			var fichaPersonal=1;
			var fichaSede=1;
			var fichaConvivencia=1;
			var fichaSalud=1;
			var fichaLaboral=1;
			var fichaAcademica=1;
			var fichaAsistencia=1;
			var fichaCarga=1;
			var fichaFoto=1;
		function cancelar(){
			if (confirm('¿Desea editar la edición del registro?')){
 				document.frmNuevo.cmd.value="Cancelar";
        document.frmNuevo.submit();
			}
		}
		function inhabilitarFormulario(){
			for(var i=0;i<document.frmNuevo.elements.length;i++){
				if(document.frmNuevo.elements[i].type != "hidden" && document.frmNuevo.elements[i].type != "button" && document.frmNuevo.elements[i].type != "submit")
					document.frmNuevo.elements[i].disabled=true;
			}
		}
		function hacerValidaciones_frmNuevo(forma){
			validarLista(forma.saltiposangre, "- Tipo de sangre", 1)
			validarCampo(forma.saltelemerg, "- Teléfono en caso de emergencia",1,50)
			validarCampo(forma.salalergias, "- Alergias (máximo 100 carácteres)",1,100)
			validarCampo(forma.salenfermedades, "- Emfermedades (máximo 100 carácteres)",1,100)
			validarCampo(forma.salmedicamentos, "- Medicamentos (máximo 100 carácteres)",1,100)
			
		}
		function lanzar(tipo){
			document.frmNuevo.tipo.value=tipo;
			document.frmNuevo.action="<c:url value="/personal/ControllerNuevoEdit.do"/>";
			document.frmNuevo.submit();
		}
		function guardar(tipo){
			if(validarForma(frmNuevo)){
				document.frmNuevo.tipo.value=tipo;
				limpiarVacios(document.frmNuevo);
				document.frmNuevo.cmd.value="Guardar";
				document.frmNuevo.submit();
			}
		}
		
		
		function limpiarVacios(forma){
		  if(forma.saleps.value == ''){
				 forma.saleps.value = ' ';
		 }
		 
		  if(forma.saleps.value == ''){
				 forma.saleps.value = ' ';
		  }
		 
		 
		  if(forma.salars.value == ''){
				 forma.salars.value = ' ';
		  }
				
		}		
				
				
				
	function camposTrim(forma){
 		 forma.saleps.value = forma.saleps.value.replace(/^\s*|\s*$/g,""); 
         forma.salars.value =forma.salars.value.replace(/^\s*|\s*$/g,"");
	 }	
		
					
				
				
				
			function ponerSeleccionado(){
				if(document.frm){
					if(document.frm.id){
						if(document.frm.id.length){
							for(var i=0;i<document.frm.id.length;i++){
								if(document.frm.id[i].value==document.frmNuevo.ELHPSELECCIONADO.value){
									document.frm.id[i].checked=true;
									document.frm.id[i].focus();
									break;
								}
							}
						}else{
							if(document.frm.id.value==document.frmNuevo.ELHPSELECCIONADO.value){
								document.frm.id.checked=true;;
								document.frm.id.focus();
							}
						}
					}
				}
			}
	-->	
	</script>
<%@include file="../mensaje.jsp"%>	
	<font size="1">
	<form method="post" name="frmNuevo" onSubmit=" return validarForma(frmNuevo)" action='<c:url value="/personal/NuevoGuardar.jsp"/>'     onsubmit="javascript: return false;"  >
		<table border="1" align="center" bordercolor="#FFFFFF" width="100%">
			<tr>		
			  <td width="45%" bgcolor="#FFFFFF">                
					<input class="boton" name="cmd1" type="button" value="Guardar" onClick="guardar(document.frmNuevo.tipo.value)" style='display:<c:out value="${permisoBoton}"/>'>
				</td>
			</tr>		
		</table>
<!--//////////////////-->		
	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="ELHPSELECCIONADO" value="<c:out value="${sessionScope.nuevoBasica.estcodigo}"/>">
	<input type="hidden" name="tipo" value='<c:out value="${tip}"/>'>
	<input type="hidden" name="cmd" value="Cancelar">
	<INPUT TYPE="hidden" NAME="height" VALUE='180'>
	<c:if test="${  (sessionScope.login.perfil == paramsVO.PERFIL_RECTOR or sessionScope.login.perfil == paramsVO.PERFIL_ADMIN_ACADEMICO )}">
	 <input type="hidden" name="ext2" value='<c:out value="${sessionScope.subframePer}"/>'>
	</c:if>
	
  <table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%">
    <tr>
			<td width="10" bgcolor="#FFFFFF">&nbsp;</td>
     <td rowspan="2" width="510" bgcolor="#FFFFFF"><script>
			if(fichaPersonal==1)document.write('<a href="javaScript:lanzar(1)"><img src="../etc/img/tabs/datos_basicos_0.gif" alt="Datos Básicos"  height="26" border="0"></a>');
			if(fichaSalud==1)document.write('<img src="../etc/img/tabs/inf_salud_1.gif" alt="Salud"  height="26" border="0">');
			if(fichaConvivencia==1)document.write('<a href="javaScript:lanzar(2)"><img src="../etc/img/tabs/inf_convivencia_0.gif" alt="Convivencia"  height="26" border="0"></a>');
			if(fichaSede==1)document.write('<a href="javaScript:lanzar(3)"><img src="../etc/img/tabs/Doc_Jor0.gif" alt="Convivencia"  height="26" border="0"></a>');
			if(fichaLaboral==1)document.write('<a href="javaScript:lanzar(5)"><img src="../etc/img/tabs/inf_laboral_0.gif" alt="Laboral"  height="26" border="0"></a>');
			if(fichaAcademica==1)document.write('<a href="javaScript:lanzar(6)"><img src="../etc/img/tabs/for_academica_0.gif" alt="Academica"  height="26" border="0"></a><br>');
			if(fichaAsistencia==1)document.write('<a href="javaScript:lanzar(7)"><img src="../etc/img/tabs/asistencia_0.gif" alt="Asistencia" height="26" border="0"></a>');
			if(fichaCarga==1)document.write('<a href="javaScript:lanzar(8)"><img src="../etc/img/tabs/carga_academica_0.gif" alt=""  height="26" border="0"></a>');
			if(fichaFoto==1)document.write('<a href="javaScript:lanzar(9)"><img src="../etc/img/tabs/fotografia_0.gif" alt=""  height="26" border="0"></a>');
		 </script>
			</td>
			<td align="right"><c:out value="${sessionScope.nuevoPersonal.pernombre1}"/> <c:out value="${sessionScope.nuevoPersonal.perapellido1}"/></td>			
			</tr>
		</table>
		<!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
								<TABLE width="100%" cellpadding="3" cellSpacing="0">
										<tr><td><hr></td><td colspan="3">INFORMACION DE SEGURIDAD SOCIAL</td></tr>
									<tr>
									  <td>ARS :</td>
									  <td><input type="text" name="salars"  maxlength='60' value='<c:out value="${sessionScope.nuevoSalud.salars}"/>'></td>
										<td>EPS :</td>
										<td><input type="text" name="saleps"  maxlength='60' value='<c:out value="${sessionScope.nuevoSalud.saleps}"/>'></td>
									</tr>
										<tr><td><hr></td><td colspan="3">INFORMACION DE SEGURIDAD SOCIAL</td></tr>
									<tr>
										<td><span class="style2">*</span> Telefono en caso de emergencia: </td>
										<td><input type="text" name="saltelemerg"  maxlength='50' value='<c:out value="${sessionScope.nuevoSalud.saltelemerg}"/>'></td>
										<td><span class="style2">*</span>Persona a quien llamar en caso de emergencia: </td>
										<td><input type="text" name="salperemerg"  maxlength='60' value='<c:out value="${sessionScope.nuevoSalud.salperemerg}"/>'></td>
								</tr>
									<tr>
									<td><span class="style2">*</span> Tipo de sangre: </td>
									<td><select name="saltiposangre">
                        <option value="-1">--seleccione uno--</option>
												<option value="A+" <c:if test="${sessionScope.nuevoSalud.saltiposangre== 'A+'}">SELECTED</c:if>>A+</option>
												<option value="A-" <c:if test="${sessionScope.nuevoSalud.saltiposangre== 'A-'}">SELECTED</c:if>>A-</option>
												<option value="B+" <c:if test="${sessionScope.nuevoSalud.saltiposangre== 'B+'}">SELECTED</c:if>>B+</option>
												<option value="B-" <c:if test="${sessionScope.nuevoSalud.saltiposangre== 'B-'}">SELECTED</c:if>>B-</option>
												<option value="AB+" <c:if test="${sessionScope.nuevoSalud.saltiposangre== 'AB+'}">SELECTED</c:if>>AB+</option>
												<option value="AB-" <c:if test="${sessionScope.nuevoSalud.saltiposangre== 'AB-'}">SELECTED</c:if>>AB-</option>
												<option value="O+" <c:if test="${sessionScope.nuevoSalud.saltiposangre== 'O+'}">SELECTED</c:if>>O+</option>
												<option value="O-" <c:if test="${sessionScope.nuevoSalud.saltiposangre== 'O-'}">SELECTED</c:if>>O-</option>
                     </select>                                    
									</td>
									<td>&nbsp;</td><td>&nbsp;</td>
								  </tr>
									<tr>
									<td>Alergias:</td><td colspan='3'><textarea name="salalergias" cols="70" rows="1"><c:out value="${sessionScope.nuevoSalud.salalergias}"/></textarea></td>
								  </tr><tr>
									<td>Enfermedades:</td><td  colspan='3'> <textarea name="salenfermedades" cols="70" rows="1"><c:out value="${sessionScope.nuevoSalud.salenfermedades}"/></textarea></td>
								  </tr>
									<tr>
									<td>Medicamentos:</td><td colspan='3'><textarea name="salmedicamentos" cols="70" rows="1"><c:out value="${sessionScope.nuevoSalud.salmedicamentos}"/></textarea></td>
                  </tr></tr>
								</TABLE>
<!--//////////////////////////////-->
</form>
<script><c:if test="${sessionScope.NivelPermiso==1}">inhabilitarFormulario();</c:if>
ponerSeleccionado();
camposTrim(document.frmNuevo);
</script>