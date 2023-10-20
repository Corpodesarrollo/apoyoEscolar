<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@include file="../../parametros.jsp"%><c:set var="tip" value="1" scope="page"/>
		<script languaje='javaScript'>
		<!--
			var extensiones = new Array();
			extensiones[0]=".xls";
			var nav4=window.Event ? true : false;
			function acepteNumeros(eve){
				var key=nav4?eve.which :eve.keyCode;
				return(key<=13 || (key>=48 && key<=57));
			}
			function hacerValidaciones_frmNuevo(forma){
        validarExtension(forma.archivo, "- Archivo (debe ser Excel)",extensiones)
			}
			function lanzar(tipo){
				document.frmNuevo.tipo.value=tipo;
				document.frmNuevo.action="<c:url value="/importar/evaluacion/ControllerImportarEdit.do"/>";
				if(parseInt(tipo)==10){
					document.frmNuevo.cmd.value='<c:out value="${paramsVO.CMD_NUEVO}"/>';
					document.frmNuevo.action='<c:url value="/importar/comportamiento/Nuevo.do"/>';
				}
				document.frmNuevo.target="";
				document.frmNuevo.submit();
			}
			function guardar(tipo){
				if(validarForma(document.frmNuevo)){
          document.frmNuevo.encoding="multipart/form-data";
					document.frmNuevo.tipo.value=tipo;
					document.frmNuevo.cmd.value="Buscar";
					document.frmNuevo.submit();
				}
			}
			function cancelar(){
				if (confirm('¿Desea cancelar la importación de la plantilla?')){
 					document.frmNuevo.cmd.value="Cancelar";
					document.frmNuevo.target="";
          document.frmNuevo.submit(); 
				}
			}
			//-->
		</script>
<%@include file="../../mensaje.jsp"%>
	<font size="1">
	<form method="post" name="frmNuevo"  onSubmit="return validarForma(frmNuevo)" action='<c:url value="/importar/evaluacion/ControllerImportarSave.do"/>'>
	<br>
	<table border="1" align="center" bordercolor="#FFFFFF" width="95%">
	<caption>Importación de Evaluación de Asignatura</caption>
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
			<td rowspan="2" width="588" bgcolor="#FFFFFF"><img src="../../etc/img/tabs/Evaluacion_Asignaturas_1.gif" alt='-Asignatura-' width="84"  height="26" border="0"><a href="javaScript:lanzar(2)"><img src="../../etc/img/tabs/Evaluacion_Areas_0.gif" alt="Logros" width="84" height="26" border="0"></a><a href="javaScript:lanzar(3)"><img src="../../etc/img/tabs/Evaluacion_Preescolar_0.gif" alt="Preescolar" width="84"  height="26" border="0"></a><a href="javaScript:lanzar(10)"><img src='<c:url value="/etc/img/tabs/Evaluacion_Comportamiento_0.gif"/>' alt="Comportamiento" width="84"  height="26" border="0"></a></td>
		</tr>
  </table><!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
								<TABLE width="100%" cellpadding="3" cellSpacing="0">
									<tr>
										<td><span class="style2">*</span>Archivo de plantilla a importar:</td>
										<td><input type='file' name='archivo' value='' style='width:420px;'></td>
									</tr>
								</form>
									<c:if test="${!empty requestScope.plantilla && requestScope.plantilla!='--'}">
						            <tr>
										<td colspan="6" align='center' valign="top"><form name='frmGenerar' action='<c:url value="/${requestScope.plantilla}"/>' method='post' target='_blank'><input type='hidden' name='dir' value='<c:out value="${requestScope.plantilla}"/>'><input type='hidden' name='tipo' value='xls'><input type='hidden' name='accion' value='0'><input type='hidden' name='aut' value='1'>Hubo inconsistencias en los datos de la plantilla.<br>Pulse en el Icono para descargar el archivo y ver los datos equivocados.<br></form>
											>><a href='javaScript:document.frmGenerar.submit();'><img border=0 src='<c:url value="/etc/img/xls.gif"/>'></A><<
										</td>
									</tr>
									</c:if><font size="2">
									<c:if test="${!empty requestScope.resultado}">
							          <tr><td colspan="6" align='center' valign="top"><b><span class="style2">Resultado de la importación de Evaluación de Asignatura.</span></b><br></td></tr>
							          <tr><td valign="top"><b>Estudiantes evaluados:</b></td><td><c:out value="${requestScope.resultado[0]}"/></td></tr>
							          <tr><td valign="top"><b>Notas de Estudiantes insertadas:</b></td><td><c:out value="${requestScope.resultado[2]}"/></td></tr>
							          <tr><td valign="top"><b>Notas de Estudiantes actualizadas:</b></td><td><c:out value="${requestScope.resultado[1]}"/></td></tr>
							          <tr><td valign="top"><b>Notas de Estudiantes eliminadas:</b></td><td><c:out value="${requestScope.resultado[3]}"/></td></tr>									
									</c:if>
									<tr><td colspan="2"><hr></td></tr>
									<c:if test="${!empty requestScope.resultado2}">
							          <tr><td colspan="6" align='center' valign="top"><b><span class="style2">Resultado de la importación de evaluación de Logros.</span></b><br></td></tr>
							          <tr><td valign="top"><b>Estudiantes evaluados:</b></td><td><c:out value="${requestScope.resultado2[0]}"/></td></tr>
							          <tr><td valign="top"><b>Logros insertados:</b></td><td><c:out value="${requestScope.resultado2[2]}"/></td></tr>
							          <tr><td valign="top"><b>Logros actualizados:</b></td><td><c:out value="${requestScope.resultado2[1]}"/></td></tr>
							          <tr><td valign="top"><b>Logros eliminados:</b></td><td><c:out value="${requestScope.resultado2[3]}"/></td></tr>
									</c:if>
									</font>
								</TABLE>
	</font>