<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<jsp:useBean id="nuevoPerfil" class="siges.perfil.beans.Perfil" scope="session" />
<jsp:setProperty name="nuevoPerfil" property="*"/>
<%@include file="../parametros.jsp"%>

		<script language='javaScript'>
		<!--

			var nav4=window.Event ? true : false;

			function acepteNumeros(eve){
				var key=nav4?eve.which :eve.keyCode;
				return(key<=13 || (key>=48 && key<=57));
			}
			
			/*function acepteNumerosNivel(eve){
				var key=nav4?eve.which :eve.keyCode;
				return(key<=13 || (key>=49 && key<=52 || key==54));
			}*/
			
			function hacerValidaciones_frmNuevoPerfil(frmNuevoPerfil){
		    validarLista(frmNuevoPerfil.perfnivel,"- Nivel del Usuario",1)
		    validarCampo(frmNuevoPerfil.perfcodigo, "- Código de Perfil", 1, 3)				
		    validarCampo(frmNuevoPerfil.perfnombre, "- Nombre del Perfil", 1, 32)				
		    validarCampo(frmNuevoPerfil.perfdescripcion, "- Descripción del Perfil (Máximo 300 caracteres)", 1, 300)				
			validarCampo(frmNuevoPerfil.perfabreviatura, "- Abreviatura del Perfil", 1, 20)
			}
			
			function lanzar(tipo){			
				document.frmNuevo.tipo.value=tipo;					
				document.frmNuevo.action="<c:url value="/perfil/ControllerPerfilNuevoEdit.do"/>";
				document.frmNuevo.submit();
			}
			
			function guardar(tipo){
				if(validarForma(document.frmNuevoPerfil)){
					document.frmNuevoPerfil.tipo.value=tipo;
					document.frmNuevoPerfil.cmd.value="Guardar";
					document.frmNuevoPerfil.submit();
				}	
			}
			
			function cancelar(){
				if (confirm('¿Desea cancelar la inserción del Perfil?')){
 					location.href='<c:url value="/bienvenida.jsp"/>';
				}		
			}
			
			/*function inhabilitarFormulario(){
				document.frmNuevoPerfil.estnumdoc.disabled=true;
			}*/
					
			function borrar_combo(combo){
				for(var i = combo.length - 1; i >= 0; i--) {
					if(navigator.appName == "Netscape") combo.options[i] = null;
					else combo.remove(i);
				}
				combo.options[0] = new Option("--Seleccione uno--","-1");
			}
			
			function inhabilitarFormulario(){
				/*for(var i=0;i<document.frmNuevoPerfil.elements.length;i++){
					if(document.frmNuevoPerfil.elements[i].type != "hidden" && document.frmNuevoPerfil.elements[i].type != "button" && document.frmNuevoPerfil.elements[i].type != "submit")
						document.frmNuevoPerfil.elements[i].disabled=true;
				}*/
				document.frmNuevoPerfil.perfcodigo.readOnly=true;
			}
		//-->
		</script>
	<%@include file="../mensaje.jsp" %>
	<font size='1'>

			<form name="f" target='1' action='<c:url value="/filtro/ControllerFiltroSave.do"/>'></form>
			<form method="post" name="frmNuevoPerfil"  onSubmit=" return validarForma(frmNuevoPerfil)" action='<c:url value="/perfil/NuevoGuardar.jsp"/>'>

				<table border="1" align="center" bordercolor="#FFFFFF" width="100%">
				<caption>Perfiles de Usuario</caption>
					<tr>		
			  		<td width="45%">
	            <input name="cmd1" type="button" value="Guardar" onClick="guardar(1)" class="boton">
							<input name="delete" type="reset" value="Borrar" class="boton">
				  	</td>   	
					</tr>	
		  	</table>
	  	
			<input type="hidden" name="tipo" value="1">
			<input type="hidden" name="cmd" value="Cancelar">
			
			<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
				<tr height="1">
						<td width="10">&nbsp;</td>
					<td rowspan="2" width="469"><img src="../etc/img/tabs/perfiles_1.gif" alt="Perfiles"  height="26" border="0"></td>
					</tr>
			</table>
			<br>
						<table border="0" align="center" width="80%" cellpadding="2" cellspacing="2" bordercolor="#FFFFFF">
							<tr>
								<td>
									<span class="style2">*</span><b> C&oacute;digo:</b>
								</td>
								<td>
									<input type="text" name="perfcodigo" maxlength="20" onKeyPress='return acepteNumeros(event)' <c:if test="${sessionScope.nuevoPerfil.estado==1}">value='<c:out value="${sessionScope.nuevoPerfil.perfcodigo}"/>'</c:if>></input>
								</td>
							</tr>
							<tr>
								<td>
									<span class="style2">*</span><b> Nombre:</b>
								</td>
								<td>
									<input type="text" name="perfnombre" maxlength="20" <c:if test="${sessionScope.nuevoPerfil.estado==1}">value='<c:out value="${sessionScope.nuevoPerfil.perfnombre}"/>'</c:if>></input>
								</td>
							</tr>
							<tr>
								<td>
									<span class="style2">*</span><b> Descripci&oacute;n:</b>
								</td>
								<td>
									<textarea name="perfdescripcion" id="perfdescripcion" cols="40" ><c:if test="${sessionScope.nuevoPerfil.estado==1}"><c:out value="${sessionScope.nuevoPerfil.perfdescripcion}"/></c:if></textarea>
								</td>
							</tr>
							<tr>
								<td>
									<span class="style2">*</span><b> Abreviatura:</b>
								</td>
								<td>
									<input type="text" name="perfabreviatura" maxlength="20" <c:if test="${sessionScope.nuevoPerfil.estado==1}">value='<c:out value="${sessionScope.nuevoPerfil.perfabreviatura}"/>'</c:if>></input>
								</td>
							</tr>
							<tr>
								<td>
									<span class="style2">*</span><b> Nivel:</b>
								</td>
								<td>
									<select name='perfnivel'>
										<option value="-1">--seleccione uno--</option>
												<c:if test="${sessionScope.login.nivel==1}">													
													<option value="1" <c:if test="${sessionScope.nuevoPerfil.estado== 1 && sessionScope.nuevoPerfil.perfnivel==1}">SELECTED</c:if>>Distrito</option>
													<option value="0" <c:if test="${sessionScope.nuevoPerfil.estado== 1 && sessionScope.nuevoPerfil.perfnivel==0}">SELECTED</c:if>>Nivel Central POA</option>
													<option value="2" <c:if test="${sessionScope.nuevoPerfil.estado== 1 && sessionScope.nuevoPerfil.perfnivel==2}">SELECTED</c:if>>Localidad</option>
													<option value="4" <c:if test="${sessionScope.nuevoPerfil.estado== 1 && sessionScope.nuevoPerfil.perfnivel==4}">SELECTED</c:if>>Instituci&oacute;n</option>
													<option value="6" <c:if test="${sessionScope.nuevoPerfil.estado== 1 && sessionScope.nuevoPerfil.perfnivel==6}">SELECTED</c:if>>Instituci&oacute;n/Sede/Jornada</option>
												</c:if>
												<c:if test="${sessionScope.login.nivel==2}">
													<option value="2" <c:if test="${sessionScope.nuevoPerfil.estado== 1 && sessionScope.nuevoPerfil.perfnivel==2}">SELECTED</c:if>>Localidad</option>
													<option value="4" <c:if test="${sessionScope.nuevoPerfil.estado== 1 && sessionScope.nuevoPerfil.perfnivel==4}">SELECTED</c:if>>Instituci&oacute;n</option>
													<option value="6" <c:if test="${sessionScope.nuevoPerfil.estado== 1 && sessionScope.nuevoPerfil.perfnivel==6}">SELECTED</c:if>>Instituci&oacute;n/Sede/Jornada</option>
												</c:if>
												<c:if test="${sessionScope.login.nivel==4}">
													<option value="4" <c:if test="${sessionScope.nuevoPerfil.estado== 1 && sessionScope.nuevoPerfil.perfnivel==4}">SELECTED</c:if>>Instituci&oacute;n</option>
													<option value="6" <c:if test="${sessionScope.nuevoPerfil.estado== 1 && sessionScope.nuevoPerfil.perfnivel==6}">SELECTED</c:if>>Instituci&oacute;n/Sede/Jornada</option>
												</c:if>
												<c:if test="${sessionScope.login.nivel==6}">
													<option value="6" <c:if test="${sessionScope.nuevoPerfil.estado== 1 && sessionScope.nuevoPerfil.perfnivel==6}">SELECTED</c:if>>Instituci&oacute;n/Sede/Jornada</option>
												</c:if>
									</select>
								</td>
							</tr>
						</table>
		</form>
	</font>
<script>
	<c:if test="${sessionScope.nuevoPerfil.estado==1}">
		inhabilitarFormulario();
	</c:if>
</script>