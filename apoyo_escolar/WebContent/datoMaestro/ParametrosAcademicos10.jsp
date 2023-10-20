<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
	<html>
	<head>
		<title>Datos Maestros - Parámetros Académicos</title>
		<%@include file="../parametros.jsp"%>
		<script languaje='javaScript'>
		<!--
			var nav4=window.Event ? true : false;
			function acepteNumeros(eve){
				var key=nav4?eve.which :eve.keyCode;
				return(key<=13 || (key>=48 && key<=57));
			}
			function hacerValidaciones_frmNuevo(forma){
//				validarCampo(forma.criterios, "- Criterios", 1, 100)
//				validarCampo(forma.procedimientos, "- Procedimientos", 1, 100)
//				validarCampo(forma.planes, "- Planes especiales", 1, 100)
			} 

			function lanzar(tipo2){				
					document.frmNuevo.action='<c:url value="/datoMaestro/DatoMaestroSeleccionEdit.do"/>?dato='+tipo2;
					document.frmNuevo.submit();
			}
			function guardar(tipo2){
				if(validarForma(document.frmNuevo)){
					document.frmNuevo.tipo2.value=tipo2;
					document.frmNuevo.cmd.value="Guardar";
					document.frmNuevo.submit();
				}	
			}
			function cancelar(){
				if (confirm('¿Desea cancelar la configuración de los datos maestros?')){
// 					document.frmNuevo.cmd.value="Cancelar";					
				 location.href='<c:url value="/bienvenida.jsp"/>';
				}		
			}			
			function inhabilitarFormulario(){
				document.frmNuevo.estnumdoc.disabled=true;
			}		
			function cargar(forma){
				//<c:if test="${sessionScope.nuevoBasica.estado== '1'}">inhabilitarFormulario();</c:if>
				<c:if test="${requestScope.ok=='ok' && sessionScope.editar=='1'}">
						document.f.submit();
				</c:if>
				mensaje(document.getElementById("msg"));
			}
			//-->
		</script>
	</head>
	<body onLoad='cargar(frmNuevo)'>
	<%@include file="../mensaje.jsp"%>
	<font size="1">
	<!--	<form name="f" target='1' action="<c:url value="/estudiante/ControllerFiltroSave"/>" ></form>-->
	<form method="post" name="frmNuevo"  onSubmit=" return validarForma(frmNuevo)" action="<c:url value="/plandeEstudios/NuevoGuardar.jsp"/>">
<!--//////////////////-->		
	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="tipo2" value="1">
	<input type="hidden" name="cmd" value="Cancelar">
	<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1">
				<td width="10" bgcolor="#FFFFFF">&nbsp;</td>			
				<td rowspan="2" width="780" bgcolor="#FFFFFF"><a href="javaScript:lanzar(12)"><img src="../etc/img/especialidad_0.gif" alt="Especialidades" width="117" height="26" border="0"></a><a href="javaScript:lanzar(26)"><img src="../etc/img/discapacidad_institucion_0.gif" alt="Discapacidad Instituci&oacute;n" width="117" height="26" border="0"></a><a href="javaScript:lanzar(15)"><img src="../etc/img/discapacidad_estudiante_0.gif" alt="Discapacidad Estudiante" width="117" height="26" border="0"></a><img src="../etc/img/etnias_1.gif" alt="Etnias" width="117" height="26" border="0"><a href="javaScript:lanzar(16)"><img src="../etc/img/capacidades_excepcionales_0.gif" alt="Capacidades Excepcionales" width="117" height="26" border="0"></a><a href="javaScript:lanzar(13)"><img src="../etc/img/condiciones_0.gif" alt="Condici&oacute;nes" width="117" height="26" border="0"></a><a href="javaScript:lanzar(44)"><img src="../etc/img/rango_tarifa_0.gif" alt="Rangos de Tarifa" width="117" height="26" border="0"></a><a href="javaScript:lanzar(45)"><img src="../etc/img/asociaciones_privadas_0.gif" alt="Asociaciones Privadas" width="117" height="26" border="0"></a><a href="javaScript:lanzar(47)"><img src="../etc/img/cargo_gobierno_escolar_0.gif" alt="Cargos de Gobierno Escolar" width="117" height="26" border="0"></a></td>
			</tr>
  </table>
		<!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
				<TABLE width="100%" cellpadding="3" cellSpacing="0">
					<tr><TD>
						<iframe id="uno" name="1" marginheight="0" marginwidth="0" frameborder="NO" src='<c:url value="/datoMaestro/DatoMaestroListaEdit.do?ext=1"/>' valign="top" ALIGN="center" height="200px" width="100%"></iframe>
					</TD></TR><TR><TD>
						<iframe id="dos" name="2" marginheight="0" marginwidth="0" frameborder="NO" SRC='<c:url value="/datoMaestro/DatoMaestroNuevoEdit.do?ext=1"/>' valign="top" ALIGN="center" height="200px" width="100%"></iframe>					
					</TD></TR>
				</TABLE>
	</font>		
<!--//////////////////////////////-->
</form>
</body>
</html>