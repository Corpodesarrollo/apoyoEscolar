<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@include file="../parametros.jsp"%>
	<script language='javaScript'>
	<!--
		function valorar(forma){
       if(forma.g_parhorario_.checked) forma.g_parhorario.value='1';
       else forma.g_parhorario.value='0';
		}
	
		var nav4=window.Event ? true : false;

		function acepteNumeros(eve){
			var key=nav4?eve.which :eve.keyCode;
			return(key<=13 || (key>=48 && key<=57));
		}

		function hacerValidaciones_frmHorario(frmHorario){

		}

		function lanzar(tipo){			
			document.frmHorario.tipo.value=tipo;
			document.frmHorario.submit();
		}

		function guardar(tipo){
			if(validarForma(document.frmHorario)){
				document.frmHorario.tipo.value=tipo;
				document.frmHorario.cmd.value="Guardar";
				document.frmHorario.submit();
			}
		}

		function cancelar(){
			if (confirm('�Desea cancelar la configuraci�n?')){
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
	//-->
	</script>
	<%@include file="../mensaje.jsp" %>
	<font size='1'>
		<form method="post" name="frmHorario" onSubmit=" return validarForma(frmHorario)" action='<c:url value="/parametro/NuevoGuardar.jsp"/>'>
			<input type="hidden" name="tipo" value="1">
			<input type="hidden" name="cmd" value="Cancelar">
			<input type="hidden" name="g_parhorario" value="<c:out value="${sessionScope.parametros.g_parhorario}"/>">
			
			<table border="0" align="center" width="100%">
				<caption>Habilitar Horarios</caption>
				<tr>
		  		<td width="45%">
            <input class="boton" name="cmd1" type="button" value="Guardar" onClick="guardar(-2)">
				  	<input class="boton" name="cmd12" type="button" value="Cancelar" onClick="cancelar()">
			  	</td>   	
				</tr>	
	  	</table>

			<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
				<tr height="1">
					<td width="10" bgcolor="#FFFFFF">&nbsp;</td>
				<td rowspan="2" width="469" bgcolor="#FFFFFF"><a href="javaScript:lanzar(1)"><img src="../etc/img/tabs/vigencia_0.gif" alt="Vigencia"  height="26" border="0"></a><img src="../etc/img/tabs/phorario_1.gif" alt="Horario" height="26" border="0"><a href="javaScript:lanzar(3)"><img src="../etc/img/tabs/recolector_0.gif" alt="Recolector" height="26" border="0"></a><a href="javaScript:lanzar(4)"><img src="../etc/img/tabs/plantillas_batch_0.gif" alt="Plantillas Batch" height="26" border="0"></a><a href="javaScript:lanzar(5)"><img src="../etc/img/tabs/importacion_batch_0.gif" alt="Importaci�n Batch" height="26" border="0"></a><a href="javaScript:lanzar(6)"><img src="../etc/img/tabs/boletin_0.gif" alt="Bolet�n" height="26" border="0"></a><a href="javaScript:lanzar(7)"><img src="../etc/img/tabs/libro_notas_0.gif" alt="Libro de Notas" height="26" border="0"></a><a href="javaScript:lanzar(8)"><img src="../etc/img/tabs/integracion_matriculas_0.gif" alt="Integraci�n con Matriculas" height="26" border="0"></a><a href="javaScript:lanzar(9)"><img src="../etc/img/tabs/reporte_estadisto_0.gif" alt="Reportes Estadisticos" height="26" border="0"></a><a href="javaScript:lanzar(10)"><img src="../etc/img/tabs/certificados_constancias_0.gif" alt="Certificados y Constancias" height="26" border="0"></a></td>
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
						<INPUT type='checkbox' name='g_parhorario_' value="1" onClick='valorar(document.frmHorario)' <c:if test="${sessionScope.parametros.g_parhorario==1}">checked</c:if>>
					</td>
				</tr>
			</table>
		</form>
	</font>