<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@include file="../parametros.jsp"%>
	<script language='javaScript'>
	<!--
		var nav4=window.Event ? true : false;

		function acepteNumeros(eve){
			var key=nav4?eve.which :eve.keyCode;
			return(key<=13 || (key>=48 && key<=57));
		}

		function hacerValidaciones_frmBoletin(frmBoletin){

		}

		function lanzar(tipo){			
			document.frmBoletin.tipo.value=tipo;
			document.frmBoletin.submit();
		}

		function guardar(tipo){
			if(validarForma(document.frmBoletin)){
				document.frmBoletin.tipo.value=tipo;
				document.frmBoletin.cmd.value="Guardar";
				document.frmBoletin.submit();
			}
		}

		function cancelar(){
			if (confirm('¿Desea cancelar la configuración?')){
					location.href='<c:url value="/bienvenida.jsp"/>';
			}
		}

		function borrar_combo(combo){
			for(var i = combo.length - 1; i >= 0; i--) {
				if(navigator.appName == "Netscape") combo.options[i] = null;
				else combo.remove(i);
			}
			combo.options[0] = new Option("--Seleccione uno--","-1");
		}
		
		function valorar(forma){
       if(forma.g_parboletin_.checked) forma.g_parboletin.value='1';
       else forma.g_parboletin.value='0';
		}
	//-->
	</script>
	<%@include file="../mensaje.jsp" %>
	<font size='1'>
		<form method="post" name="frmBoletin" onSubmit="return validarForma(frmBoletin)" action='<c:url value="/parametro/NuevoGuardar.jsp"/>'>
			<input type="hidden" name="tipo" value="1">
			<input type="hidden" name="cmd" value="Cancelar">
			<input type="hidden" name="g_parboletin" value="<c:out value="${sessionScope.parametros.g_parboletin}"/>">

			<table border="0" align="center" width="100%">
				<caption>Bolet&iacute;n</caption>
				<tr>
		  		<td width="45%">
            <input class="boton" name="cmd1" type="button" value="Guardar" onClick="guardar(-6)">
				  	<input class="boton" name="cmd12" type="button" value="Cancelar" onClick="cancelar()">
			  	</td>
				</tr>
	  	</table>

			<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
				<tr height="1">
					<td width="10" bgcolor="#FFFFFF">&nbsp;</td>
					<td rowspan="2" width="469" bgcolor="#FFFFFF"><a href="javaScript:lanzar(1)"><img src="../etc/img/tabs/vigencia_0.gif" alt="Vigencia" height="26" border="0"></a><a href="javaScript:lanzar(2)"><img src="../etc/img/tabs/phorario_0.gif" alt="Horario" height="26" border="0"></a><a href="javaScript:lanzar(3)"><img src="../etc/img/tabs/recolector_0.gif" alt="Recolector" height="26" border="0"></a><a href="javaScript:lanzar(4)"><img src="../etc/img/tabs/plantillas_batch_0.gif" alt="Plantillas Batch" height="26" border="0"></a><a href="javaScript:lanzar(5)"><img src="../etc/img/tabs/importacion_batch_0.gif" alt="Importación Batch" height="26" border="0"></a><img src="../etc/img/tabs/boletin_1.gif" alt="Boletín" height="26" border="0"><a href="javaScript:lanzar(7)"><img src="../etc/img/tabs/libro_notas_0.gif" alt="Libro de Notas" height="26" border="0"></a><a href="javaScript:lanzar(8)"><img src="../etc/img/tabs/integracion_matriculas_0.gif" alt="Integración con Matriculas" height="26" border="0"></a><a href="javaScript:lanzar(9)"><img src="../etc/img/tabs/reporte_estadisto_0.gif" alt="Reportes Estadisticos" height="26" border="0"></a><a href="javaScript:lanzar(10)"><img src="../etc/img/tabs/certificados_constancias_0.gif" alt="Certificados y Constancias" height="26" border="0"></a></td>
				</tr>
				<tr>
					<td bgcolor="#000000" height="1"><img src="../etc/img/pixel.gif" width="1" height="1"></td>
					<td bgcolor="#000000" height="1"><img src="../etc/img/pixel.gif" width="1" height="1"></td>
				</tr>
			</table>
			<br>
			<table border="0" align="center" width="80%" cellpadding="0" cellspacing="0">
				<tr>
					<td>Habilitado:</td>
					<td>
						<INPUT type='checkbox' name='g_parboletin_' value="1" onClick='valorar(document.frmBoletin);' <c:if test="${sessionScope.parametros.g_parboletin==1}">checked</c:if>>
					</td>
				</tr>
			</table>
		</form>
	</font>