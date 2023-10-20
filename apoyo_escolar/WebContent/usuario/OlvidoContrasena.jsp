<!--	VERSION		FECHA			AUTOR				DESCRIPCION
			1.0		08/05/2018		JORGE CAMACHO		CREACION DE LA PAGINA WEB
-->

<%@ page contentType="text/html; charset=ISO-8859-1" language="java" %>

<%@include file="../parametros.jsp"%>

<script language='javaScript'>
	<!--
		var nav4=window.Event ? true : false;
		
		function acepteNumeros(eve) {
			var key=nav4?eve.which :eve.keyCode;
			return(key<=13 || (key>=48 && key<=57));
		}
		
		function guardar(tipo) {
		
			if(validarForma(document.frmOlvidoPassword)) {
				document.frmOlvidoPassword.tipo.value = tipo;
				document.frmOlvidoPassword.cmd.value = "Guardar";
				document.frmOlvidoPassword.submit();
			}
				
		}
	
		function hacerValidaciones_frmOlvidoPassword(frmOlvidoPassword) {
			
			validarCampo(frmOlvidoPassword.documento, "- Número de Documento", 1, 12);
		}
		
		function cancelar() {
			if (confirm('¿Desea cancelar el cambio de contraseña?'))
				location.href='<c:url value="/login.jsp"/>';
		}
	//-->
</script>

<%@include file="../mensaje.jsp" %>

<font size='1'>

	<form method="post" name="frmOlvidoPassword" onSubmit="return validarForma(frmOlvidoPassword)" action='<c:url value="/usuario/OlvidoContrasena.do"/>'>
	
		<table border="1" align="center" bordercolor="#FFFFFF" width="100%">
			<caption>Olvido Contrase&ntilde;a</caption>
			<tr>
				<td>
					<input name="cmd1" type="button" value="Enviar" onClick="guardar(1)" class="boton">
					<input name="delete" type="button" value="Cancelar" onClick="cancelar()" class="boton">
				</td>
			</tr>
		</table>
		  	
		<input type="hidden" name="tipo" value="1">
		<input type="hidden" name="cmd" value="Cancelar">
	
		<table cellpadding="0" cellspacing="0" border="0" align="center" width="100%" >
			<tr height="1">
				<td width="10">&nbsp;</td>
				<td rowspan="2" width="469">
					<img src="<c:url value="/etc/img/tabs/usuarios_1.gif" />" alt="Olvido Clave" height="26" border="0">
				</td>
			</tr>
		</table>
		<br>

		<table border="1" align="center" width="80%" cellpadding="0" cellspacing="0" bordercolor="#FFFFFF">
			<tr>
				<td>
					<span class="style2">*</span><b> N&uacute;mero de Documento:</b>
				</td>
				<td>
					<input type="text" name="documento" maxlength="12" onKeyPress='return acepteNumeros(event)'></input>
				</td>
			</tr>
		</table>
		
	</form>
	
</font>
