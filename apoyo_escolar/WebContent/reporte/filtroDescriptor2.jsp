<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@include file="../parametros.jsp"%><c:set var="tip" value="6" scope="page"/>
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
			}
			function lanzar(tipo){
				document.frmNuevo.tipo.value=tipo;
				document.frmNuevo.action="<c:url value="/reporte/ControllerFiltroEdit.do"/>";
				document.frmNuevo.target="";
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
<%@include file="../mensaje.jsp"%>
	<font size="1">
	<form method="post" name="frmNuevo"  onSubmit="return validarForma(frmNuevo)" action='<c:url value="/reporte/filtroGuardar.jsp"/>'>
	<table border="1" align="center" bordercolor="#FFFFFF" width="95%">
	<caption>Gestión Administrativa - Reportes Estadisticos</caption>
		<tr>
		  <td width="45%" bgcolor="#FFFFFF">
        <input  name="cmd1" type="button" value="Aceptar" onClick="guardar(document.frmNuevo.tipo.value)">
				<input  name="cmd12" type="button" value="Cancelar" onClick="cancelar()">
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
			<td rowspan="2" width="588" bgcolor="#FFFFFF"><a href="javaScript:lanzar(1)"><img src="../etc/img/tabs/asistencia_entrega_boletines_0.gif" width="84" height="26" border="0"></a><a href="javaScript:lanzar(2)"><img src="../etc/img/tabs/evaluacion_logros_grupo_0.gif" width="84" height="26" border="0"></a><a href="javaScript:lanzar(3)"><img src="../etc/img/tabs/evaluacion_asig_grupo_0.gif" width="84"  height="26" border="0"></a><a href="javaScript:lanzar(4)"><img src="../etc/img/tabs/evaluacion_areas_grupo_0.gif" width="84"  height="26" border="0"></a><a href="javaScript:lanzar(5)"><img src="../etc/img/tabs/fortalezas_grupo_area_0.gif" width="84"  height="26" border="0"></a><a href="javaScript:lanzar(6)"><img src="../etc/img/tabs/fortalezas_menos_recurrentes_1.gif" width="84"  height="26" border="0"></a></td>
		</tr>
  </table><!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
		<table cellpadding="0" cellspacing="0" border="0" width="100%">
			<tr>
				<td>
					<TABLE width="100%" cellpadding="0" cellSpacing="10">
						<tr>
							<td>
								<TABLE width="100%" cellpadding="3" cellSpacing="0">
									<tr>
										<td><span class="style2"></span><font size=1><p align='justify'>
												Muestra el número y % de estudiantes que presentan una fortaleza, siempre y 
												cuando este porcentaje esté por debajo de un porcentaje establecido por parámetro. 
												El porcentaje se calcula como el número de estudiantes a los que se les relacionó cada fortaleza / Nº de estudiantes del grupo. 
												Mostrar un cuadro: filas: área y nombre de cada fortaleza, columnas: 1 - 
												Nº de estudiantes que la presentan, 2 - % contra el total.
										</p></font></td>
									</tr>
									<tr>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td align='center'><font size=3 COLOR='SILVER'><< ...PÁGINA EN CONSTRUCCIÓN... >> </font></td>
									</tr>									
								</form>
									</font>
								</TABLE>
							</td>
						</tr>
					</table>
				</td>
		</table>	
	</font>		
<!--//////////////////////////////-->