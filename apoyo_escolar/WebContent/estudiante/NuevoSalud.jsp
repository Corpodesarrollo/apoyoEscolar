<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
	<jsp:useBean id="nuevoBasica" class="siges.estudiante.beans.Basica" scope="session"/><jsp:setProperty name="nuevoBasica" property="*"/>
	<jsp:useBean id="nuevoFamiliar" class="siges.estudiante.beans.Familiar" scope="session"/><jsp:setProperty name="nuevoFamiliar" property="*"/>
	<jsp:useBean id="nuevoSalud" class="siges.estudiante.beans.Salud" scope="session"/>
	<jsp:useBean id="nuevoConvivencia" class="siges.estudiante.beans.Convivencia" scope="session"/><jsp:setProperty name="nuevoConvivencia" property="*"/>
	<jsp:useBean id="academicaVO" class="siges.estudiante.beans.AcademicaVO" scope="session"/><jsp:setProperty name="academicaVO" property="*"/>
	<jsp:useBean id="asistenciaVO" class="siges.estudiante.beans.AsistenciaVO" scope="session"/><jsp:setProperty name="asistenciaVO" property="*"/>
	<jsp:useBean id="nuevoAtencion" class="siges.estudiante.beans.AtencionEspecial" scope="session"/><jsp:setProperty name="nuevoAtencion" property="*"/>
<%@include file="../parametros.jsp"%><c:set var="tip" value="3" scope="page"/>
		<c:if test="${sessionScope.NivelPermiso==1}"><c:set var="permisoBoton" value="none"/></c:if>
		<script languaje=' javaScript'>
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
			validarCampo(forma.salperemerg, "- Persona a quien llamar en caso de emergencia",1,60)			
		} 			
		function lanzar(tipo){				
			document.frmNuevo.tipo.value=tipo;
			document.frmNuevo.action="<c:url value="/estudiante/ControllerNuevoEdit.do"/>";
			document.frmNuevo.submit();
		}
		function guardar(tipo){
			if(validarForma(frmNuevo)){
				document.frmNuevo.tipo.value=tipo;
				document.frmNuevo.cmd.value="Guardar";
				document.frmNuevo.submit();
			}
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
	<form method="post" name="frmNuevo" onSubmit=" return validarForma(frmNuevo)" action='<c:url value="/estudiante/NuevoGuardar.jsp"/>'>
		<table border="1" align="center" bordercolor="#FFFFFF" width="100%">
			<tr>		
			  <td width="45%" bgcolor="#FFFFFF">                
					<c:if test="${empty sessionScope.ModoConsulta}"><input class="boton" name="cmd1" type="button" value="Guardar" onClick="guardar(document.frmNuevo.tipo.value)" style='display:<c:out value="${permisoBoton}"/>'></c:if>
					<c:if test="${sessionScope.ModoConsulta==1}"><a href="javascript:print()"><input  class='boton' name="impr1" type="button" value="Imprimir" style="margin-top:2px;margin-bottom:2px;" onClick="javascript:window.print(); return false;" /></a></c:if>
				</td>
			</tr>		
		</table>
<!--//////////////////-->		
	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="ELHPSELECCIONADO" value="<c:out value="${sessionScope.nuevoBasica.estcodigo}"/>">
	<input type="hidden" name="tipo" value='<c:out value="${tip}"/>'>
	<input type="hidden" name="cmd" value="Cancelar"><INPUT TYPE="hidden" NAME="height" VALUE='130'>
	<input type="hidden" name="ext2" value='<c:out value="${sessionScope.subframe}"/>'>
  <table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
    <tr height="1">
			<td width="1%" bgcolor="#FFFFFF">&nbsp;</td>
      <td rowspan="2" width="504" bgcolor="#FFFFFF">
			<a href="javaScript:lanzar(1)"><img src="../etc/img/tabs/datos_basicos_0.gif" alt="Datos Básicos"  height="26" border="0"></a>
			<c:if test="${empty sessionScope.ModoConsulta}"><a href="javaScript:lanzar(2)"><img src="../etc/img/tabs/inf_familiar_0.gif" alt="Ficha Familiar"  height="26" border="0"></a></c:if>
			<img src="../etc/img/tabs/inf_salud_1.gif" alt="Salud"  height="26" border="0">
			<a href="javaScript:lanzar(4)"><img src="../etc/img/tabs/inf_convivencia_0.gif" alt="Convivencia"  height="26" border="0"></a>
			<a href="javaScript:lanzar(5)"><img src="../etc/img/tabs/inf_academica_0.gif" alt="Inf. Académica"  height="26" border="0"></a>
			<c:if test="${empty sessionScope.ModoConsulta}"><a href="javaScript:lanzar(7)"><img src="../etc/img/tabs/atencion_especial_0.gif" alt="Atención Especial"  height="26" border="0"></a></c:if>
			<c:if test="${empty sessionScope.ModoConsulta}"><a href="javaScript:lanzar(8)"><img src="../etc/img/tabs/fotografia_0.gif" alt="Subir Foto Estudiante"  height="26" border="0"></a></c:if></td>
			<td align='CENTER'><b><c:out value="${sessionScope.nuevoBasica.estnombre1}"/> <c:out value="${sessionScope.nuevoBasica.estnombre2}"/> <c:out value="${sessionScope.nuevoBasica.estapellido1}"/></b></td>
		</tr>
		</table>
		<!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
								<TABLE width="100%" cellpadding="3" cellSpacing="0">
										<tr><td><hr></td><td colspan="3">INFORMACION DE SEGURIDAD SOCIAL</td></tr>
									<tr>
									  <td>ARS: </td>
									  <td  >
									  <input type="text" name="salars"  maxlength='60' value='<c:out value="${sessionScope.nuevoSalud.salars}"/>' <c:if test="${!empty sessionScope.ModoConsulta}">disabled</c:if>>
									  </td>
										<td>EPS :</td>
										<td  >
										  <input type="text" name="saleps"  maxlength='60' value='<c:out value="${sessionScope.nuevoSalud.saleps}"/>' <c:if test="${!empty sessionScope.ModoConsulta}">disabled</c:if>>
										</td>
									</tr>
										<tr><td><hr></td><td colspan="3">INFORMACION DE SEGURIDAD SOCIAL</td></tr>
									<tr>
										<td><span class="style2">*</span> Telefono en caso de emergencia: </td>
										<td>
											<input type="text" name="saltelemerg"  maxlength='50' value='<c:out value="${sessionScope.nuevoSalud.saltelemerg}"/>' <c:if test="${!empty sessionScope.ModoConsulta}">disabled</c:if>>
										</td>
										<td><span class="style2">*</span>Persona a quien llamar en caso de emergencia: </td>
										<td>
											<input type="text" name="salperemerg"  maxlength='60' value='<c:out value="${sessionScope.nuevoSalud.salperemerg}"/>' <c:if test="${!empty sessionScope.ModoConsulta}">disabled</c:if>>
                                      </td>
								</tr>
									<tr>
									<td><span class="style2">*</span> Tipo de sangre: </td>
									<td><select name="saltiposangre" <c:if test="${!empty sessionScope.ModoConsulta}">disabled</c:if>>
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
									<td>Alergias:</td><td colspan='3'><textarea name="salalergias" cols="70" rows="1" <c:if test="${!empty sessionScope.ModoConsulta}">disabled</c:if>><c:out value="${sessionScope.nuevoSalud.salalergias}"/></textarea></td>
								  </tr><tr>
									<td>Enfermedades:</td><td  colspan='3'> <textarea name="salenfermedades" cols="70" rows="1" <c:if test="${!empty sessionScope.ModoConsulta}">disabled</c:if>><c:out value="${sessionScope.nuevoSalud.salenfermedades}"/></textarea></td>
								  </tr>
									<tr>
									<td>Medicamentos:</td><td colspan='3'><textarea name="salmedicamentos" cols="70" rows="1" <c:if test="${!empty sessionScope.ModoConsulta}">disabled</c:if>><c:out value="${sessionScope.nuevoSalud.salmedicamentos}"/></textarea></td>
                  </tr></tr>
								</TABLE>
<!--//////////////////////////////-->
</form>
<script><c:if test="${sessionScope.NivelPermiso==1}">inhabilitarFormulario();</c:if>
ponerSeleccionado();
</script>