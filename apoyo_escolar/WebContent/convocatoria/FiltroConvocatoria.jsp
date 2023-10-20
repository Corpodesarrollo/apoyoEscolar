<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@include file="../parametros.jsp"%>
		<script language='javaScript'>
		<!--
			var nav4=window.Event ? true : false;

			function acepteNumeros(eve){
				var key=nav4?eve.which :eve.keyCode;
				//return(key<=13 || (key>=48 && key<=57));
				return((key>=48 && key<=57));
			}
			
			function hacerValidaciones_frm(frm){
				//validarCampo(frm.docum, "- Documento", 1,10)
				validarEntero(frm.docum, "- Documento", 1,99999999999)
			}
			
			function guardar(tipo){
				if(validarForma(document.frm)){
					document.frm.tipo.value=tipo;
					document.frm.cmd.value="Buscar";
					document.frm.submit();
				}	
			}
		-->
		</script>
		
	<%@include file="../mensaje.jsp" %>
		<form method="post" name="frm"  onSubmit=" return validarForma(frmValNuevoUsuario)" action='<c:url value="/convocatoria/ControllerEditar.do"/>'>
			<input type="hidden" name="tipo" value="1">
			<input type="hidden" name="cmd" value="Cancelar">
			<INPUT TYPE="hidden" NAME="ext2"  VALUE=''>
		
			<table border="0" align="center" bordercolor="#FFFFFF" width="100%">
			<caption>Búsqueda de Inscripción</caption>
				<tr>
					<td>
						<span class="style2"></span> Digite su n&uacute;mero de documento:
					</td>
					<td>
						<input type="text" name="docum"  maxlength="12" onKeyPress='return acepteNumeros(event)' value=''></input>
					</td>
		  		<td><input name="cmd1" type="button" value="Buscar" onClick="guardar(1)" class="boton"></td>
				</tr>
			</table>
		</form>