<!--	VERSION		FECHA			AUTOR				DESCRIPCION
			1.0		04/05/2018		JORGE CAMACHO		CODIGO ESPAGUETI Y MODIFICAR LA VALIDACION DE LA NUEVA CONTRASEÑA
														A 15 CARACTERES TAL COMO ESTA EN LA PAGINA: Login.jsp
			2.0		21/08/2015		JORGE CAMACHO		SE AGREGÓ UNA EXPLICACIÓN DEL FORMATO ESPERADO DE LA NUEVA CONTRASEÑA 
														SE VALIDA QUE LA NUEVA CONTRASEÑA NO SEA IGUAL A LA ACTUAL
-->

<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>

<%@include file="../parametros.jsp"%>

<script language='javaScript'>
	<!--
		var nav4=window.Event ? true : false;
	
		function acepteNumeros(eve) {
			var key=nav4?eve.which :eve.keyCode;
			return(key<=13 || (key>=48 && key<=57));
		}
		
		function hacerValidaciones_frmEditarPassword(frmEditarPassword) {
			
			validarCampo(frmEditarPassword.documento, "- Número de Documento", 1, 12);
		    validarCampo(frmEditarPassword.actual, "- Contraseña Actual", 1, 12);
		    validarCampo(frmEditarPassword.nuevo, "- Nueva Contraseña", 8, 12);
			validarCampo(frmEditarPassword.confirmar, "- Confirmar Contraseña", 8, 12);
			validarContrasena(frmEditarPassword.nuevo, frmEditarPassword.confirmar, "- Las contraseñas no coinciden, por favor digítelas nuevamente");
			validarReusarContrasena(frmEditarPassword.actual, frmEditarPassword.nuevo, "- La contraseña actual no puede volver a ser su nueva contraseña");
			validarUsuarioComoContrasena(frmEditarPassword.documento, frmEditarPassword.nuevo, "- El Número de Documento no puede ser usado como contraseña");
		}
		
		function lanzar(tipo) {
			document.frmNuevo.tipo.value = tipo;
			document.frmNuevo.action = "<c:url value="/perfil/ControllerPerfilNuevoEdit.do"/>";
			document.frmNuevo.submit();
		}
		
		function guardar(tipo) {
		
			if(validarForma(document.frmEditarPassword)) {
				document.frmEditarPassword.tipo.value = tipo;
				document.frmEditarPassword.cmd.value = "Guardar";
				document.frmEditarPassword.submit();
			}
				
		}
		
		function cancelar() {
			if (confirm('¿Desea cancelar el cambio de contraseña?'))
				location.href='<c:url value="/bienvenida.jsp"/>';
		}
		
		/*function inhabilitarFormulario() {
			document.frmEditarPassword.estnumdoc.disabled = true;
		}*/
				
		function borrar_combo(combo) {
			for(var i = combo.length - 1; i >= 0; i--) {
				if(navigator.appName == "Netscape")
					combo.options[i] = null;
				else
					combo.remove(i);
			}
			
			combo.options[0] = new Option("--Seleccione uno--", "-1");
		}
	//-->
</script>

<%@include file="../mensaje.jsp" %>

<font size='1'>

	<form method="post" name="frmEditarPassword" onSubmit=" return validarForma(frmEditarPassword)" action='<c:url value="/usuario/GuardarContrasena.do"/>'>
	
		<table border="1" align="center" bordercolor="#FFFFFF" width="100%">
			<caption>Cambiar Contrase&ntilde;a</caption>
			<tr>
				<td>
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
				<td rowspan="2" width="469">
					<img src="<c:url value="/etc/img/tabs/usuarios_1.gif" />" alt="Cambiar Clave" height="26" border="0">
				</td>
			</tr>
		</table>
  		<br>
		
		<table border="1" align="center" width="80%" cellpadding="0" cellspacing="0" bordercolor="#FFFFFF">
			<tr>
				<td>
					<h3><span class="style2">*</span><b> N&uacute;mero de Documento:</b></h3>
				</td>
				<td>
					<input type="text" name="documento" maxlength="12" onKeyPress='return acepteNumeros(event)' value='<c:out value="${numeroDocumento}"/>'></input>
				</td>
			</tr>
			<tr>
				<td>
					<h3><span class="style2">*</span><b> Contrase&ntilde;a Actual:</b></h3>
				</td>
				<td>
					<input type="password" name="actual" maxlength="12"></input>
				</td>
			</tr>
			<tr>
				<td>
					<h3><span class="style2">*</span><b> Nueva Contrase&ntilde;a:</b></h3>
				</td>
				<td>
					<input type="password" name="nuevo" maxlength="12"></input>
				</td>
			</tr>
			<tr>
				<td>
					<h3><span class="style2">*</span><b> Confirmar Contrase&ntilde;a:</b></h3>
				</td>
				<td>
					<input type="password" name="confirmar" maxlength="12"></input>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<br/><br/>
					<h3>Características de la contraseña:</h3>
					
					<ul>
						<li><h3><i>Mínimo 8 caracteres y máximo 12</i></h3></li>
						<li><h3><i>El Número de Documento no puede ser usado como contraseña</i></h3></li>
					</ul>
				</td>
			</tr>
		</table>
		
	</form>
	
</font>

<script>
	<c:if test="${requestScope.ok=='ok' && sessionScope.editar=='1'}">
		document.f.submit();
	</c:if>
</script>
