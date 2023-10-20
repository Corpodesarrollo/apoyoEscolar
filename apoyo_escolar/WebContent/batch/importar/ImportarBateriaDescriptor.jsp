<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %> 
<%@include file="../../parametros.jsp"%><c:set var="tip" value="5" scope="page"/>
		<script languaje='javaScript'>
		<!--
			var extensiones = new Array();
			extensiones[0]=".xls";
			extensiones[1]=".zip";
			var nav4=window.Event ? true : false;
			function acepteNumeros(eve){
				var key=nav4?eve.which :eve.keyCode;
				return(key<=13 || (key>=48 && key<=57));
			}
			function hacerValidaciones_frmNuevo(forma){
        validarCampo(forma.archivo, "- Archivo (debe ser Excel)")
				validarExtension(forma.archivo, "- Archivo (debe ser Excel)",extensiones)
			}
			function lanzar(tipo){
				document.frmNuevo.tipo.value=tipo;
				document.frmNuevo.action="<c:url value="/batch/importar/ControllerImportarEdit.do"/>";
				document.frmNuevo.submit();
			}
			function guardar(tipo){
				if(validarForma(document.frmNuevo)){
          document.frmNuevo.encoding="multipart/form-data";
					document.frmNuevo.tipo.value=tipo;
					document.frmNuevo.cmd.value="Aceptar";
					document.frmNuevo.submit();
				}
			}
			function cancelar(){
				if (confirm('¿Desea cancelar la importación de la plantilla?')){
					location.href='<c:url value="/bienvenida.jsp"/>';
				}
			}
			//-->
</script>
<%@include file="../../mensaje.jsp"%>
	<font size="1">
	<form method="post" name="frmNuevo"  onSubmit="return validarForma(frmNuevo)" action='<c:url value="/batch/importar/ControllerImportarSave.do"/>'>
	<br>
	<table border="1" align="center" bordercolor="#FFFFFF" width="95%">
	<caption>Importación masiva</caption>
		<tr>
		  <td width="45%" bgcolor="#FFFFFF">
        <input class='boton' name="cmd1" type="button" value="Aceptar" onClick="guardar(document.frmNuevo.tipo.value)">
			</td>
		</tr>
	</table>
<!--//////////////////-->		
	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="tipo" value='<c:out value="${pageScope.tip}"/>'>
	<input type="hidden" name="cmd" value="Cancelar">
	<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
		<tr height="1">
			<td width="10" bgcolor="#FFFFFF">&nbsp;</td>							
			<td rowspan="2" width="588" bgcolor="#FFFFFF"><a href="javaScript:lanzar(4)"><img src="../../etc/img/tabs/logros_0.gif" alt="Logros" width="84"  height="26" border="0"></a><img src="../../etc/img/tabs/descriptores_1.gif" alt="Asignatura" width="84"  height="26" border="0"><a href="javaScript:lanzar(1)"><img src="../../etc/img/tabs/Evaluacion_Asignaturas_0.gif" alt="Asignatura" width="84"  height="26" border="0"></a><a href="javaScript:lanzar(2)"><img src="../../etc/img/tabs/Evaluacion_Areas_0.gif" alt="Asignatura" width="84"  height="26" border="0"></a><a href="javaScript:lanzar(3)"><img src="../../etc/img/tabs/Evaluacion_Preescolar_0.gif" alt="Preescolar" width="84"  height="26" border="0"></a></td>
		</tr>
  </table><!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
								<TABLE width="100%" cellpadding="3" cellSpacing="0">
									<tr>
										<td><span class="style2">*</span>Archivo de plantilla a importar:</td>
										<td><input type='file' name='archivo' style='width:420px;'></td>
									</tr>
									<tr>
										<td colspan=2><span class="style2">Nota:<br></span>
										Puede importar plantillas EXCEL o un archivo ZIP con todas las plantillas de un mismo tipo.
										El archivo se almacenará para ser procesado en la noche junto con todas las solicitudes de importación
										de todos los usuarios. Es probable que debido a la cantidad de solicitudes sus archivos no sea importados
										en el tiempo previsto. en este caso sera procesados en la siguiente noche.
										</td>
									</tr>									
								</TABLE>
								<hr>
								<TABLE width="100%" cellpadding="3" cellSpacing="0">
									<tr>
										<th colspan=2><span class="style2"><font size='2'>Lista de archivos en espera de ser importados<br></font></span></th>
									</tr>									
									<tr><td><font size='1'>Evaluación de Asignaturas:</font></td><td><font size='1'><c:out value="${requestScope.cont1}"/></font></td></tr>
									<tr><td><font size='1'>Evaluación de Áreas:</font></td><td><font size='1'><c:out value="${requestScope.cont2}"/></font></td></tr>
									<tr><td><font size='1'>Evaluación de Preescolar:</font></td><td><font size='1'><c:out value="${requestScope.cont3}"/></font></td></tr>
									<tr><td><font size='1'>Batería de Logros:</font></td><td><font size='1'><c:out value="${requestScope.cont4}"/></font></td></tr>
									<tr><td><font size='1'>Batería de Descriptores:</font></td><td><font size='1'><c:out value="${requestScope.cont5}"/></font></td></tr>
									</form>
								</TABLE>
	</font>
<!--//////////////////////////////-->