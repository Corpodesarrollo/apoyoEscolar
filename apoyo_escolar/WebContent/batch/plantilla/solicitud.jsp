<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@include file="../../parametros.jsp"%><c:set var="tip" value="1" scope="page"/>
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
				validarLista(forma.periodo,"- Periodo",1)			
			}
			function lanzar(tipo){
				document.frmNuevo.tipo.value=tipo;
				document.frmNuevo.action="<c:url value="/batch/plantilla/ControllerPeticionEdit.do"/>";
				document.frmNuevo.submit();
			}
			function guardar(tipo){
				if(validarForma(document.frmNuevo)){
					document.frmNuevo.tipo.value=tipo;
					document.frmNuevo.cmd.value="Aceptar";
					document.frmNuevo.submit();
				}
			}
			function cancelar(){
				if (confirm('¿Desea cancelar la solicitud de plantillas?')){
					location.href='<c:url value="/bienvenida.jsp"/>';
				}
			}
			//-->
</script>
<%@include file="../../mensaje.jsp"%>
	<font size="1">
	<form method="post" name="frmNuevo"  onSubmit="return validarForma(frmNuevo)" action='<c:url value="/batch/plantilla/ControllerPeticionEdit.do"/>'>
	<br>
	<table border="1" align="center" bordercolor="#FFFFFF" width="95%">
	<caption>Gestion Académica - Solicitud de plantillas -</caption>
		<tr>
		  <td width="45%" bgcolor="#FFFFFF">
        <input class='boton' name="cmd1" type="button" value="Solicitar" onClick="guardar(document.frmNuevo.tipo.value)">
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
			<td rowspan="2" width="588" bgcolor="#FFFFFF"><img src="../../etc/img/tabs/solicitud_1.gif" alt="Asignatura" width="84"  height="26" border="0"></td>
		</tr>
  </table><!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
								<TABLE width="100%" cellpadding="3" cellSpacing="0">
									<tr>
										<td align='center'><font size=1><span class="style2">* </span>Periodo a generar:</font></td>
										<td align='center'>
											<select name="periodo" style='width:120px;'>
												<option value='-1'>-- Seleccione uno --</option>
												<c:forEach begin="0" items="${requestScope.periodos}" var="fila">
													<option value='<c:out value="${fila[0]}"/>'><c:out value="${fila[1]}"/></option>
												</c:forEach>
											</select>
										</td>
									</tr>
									<tr>
										<td colspan=2><span class="style2"><font size=2>Nota:</font><br><br></span>
										<font size=1>Ahora debe seleccionar el periodo para el cual desea generar las plantillas masivas. Recuerde que no puede solicitar mas de una a la vez.</font>
										</td>
									</tr>									
								</TABLE>
	</font>
<!--//////////////////////////////-->