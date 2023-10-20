<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@include file="../parametros.jsp"%><c:set var="dat" value="1" scope="page"/>
	<html>
	<head>
		<title>Datos Maestros - Parámetros Generales</title>

		<script languaje='javaScript'>
		<!--
			var nav4=window.Event ? true : false;
			function acepteNumeros(eve){
				var key=nav4?eve.which :eve.keyCode;
				return(key<=13 || (key>=48 && key<=57));
			}

			function lanzar(dato){
				document.frmNuevo.dato.value=dato;	
				document.frmNuevo.action="<c:url value="/datoMaestro/DatoMaestroSeleccionEdit.do"/>";
				document.frmNuevo.submit();
			}
			
			function cancelar(){
				if (confirm('¿Desea cancelar la configuración de los datos maestros?')){
				 location.href='<c:url value="/bienvenida.jsp"/>';
				}		
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

		<form method="post" name="frmNuevo"  onSubmit=" return validarForma(frmNuevo)" action="<c:url value="/datoMaestro/DatoMaestroSeleccionEdit.do"/>">
<!--//////////////////-->		
	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="dato" value='<c:out value="${pageScope.dat}"/>'>
	<input type="hidden" name="cmd" value="Cancelar">
	<table cellpadding="0" cellspacing="0" border="0" width="100%">
			<tr height="1">
				<td width="8"  wbgcolor="#FFFFFF">&nbsp;</td>			
				<td  width="650px" rowspan="2" bgcolor="#FFFFFF"><a href="javaScript:lanzar(2)"><img src="../etc/img/tabs/tipos_documento_0.gif" alt="Tipos de documento"  height="26" border="0"></a><a href="javaScript:lanzar(25)"><img src="../etc/img/tabs/tipos_propiedad_0.gif" alt="Tipos de Propiedad"  height="26" border="0"></a><a href="javaScript:lanzar(6)"><img src="../etc/img/tabs/tipos_espacio_fisico_0.gif" alt="Tipos de Espacio F&iacute;sico"  height="26" border="0"></a><a href="javaScript:lanzar(11)"><img src="../etc/img/tabs/tipos_ocupantes_0.gif" alt="Tipos de Ocupante"  height="26" border="0" usemap="#ipoi"></a><a href="javaScript:lanzar(19)"><img src="../etc/img/tabs/tipos_programa_0.gif" alt="Tipos de Programas"  height="26" border="0"></a><a href="javaScript:lanzar(8)"><img src="../etc/img/tabs/tipos_reconocimiento_0.gif" alt="Tipos de Reconocimiento"  height="26" border="0"></a><a href="javaScript:lanzar(14)"><img src="../etc/img/tabs/tipos_convivencia_0.gif" alt="Tipos de Convivencia"  height="26" border="0"></a><a href="javaScript:lanzar(18)"><img src="../etc/img/tabs/motivos_ausencia_0.gif" alt="Motivos de Ausencia"  height="26" border="0"></a><a href="javaScript:lanzar(17)"><img src="../etc/img/tabs/etnias_0.gif" alt="Etnias"  height="26" border="0"></a><a href="javaScript:lanzar(13)"><img src="../etc/img/tabs/condiciones_0.gif" alt="Condici&oacute;nes"  height="26" border="0"></a><a href="javaScript:lanzar(44)"><img src="../etc/img/tabs/rango_tarifa_0.gif" alt="Rangos de Tarifa"  height="26" border="0"></a><a href="javaScript:lanzar(47)"><img src="../etc/img/tabs/cargo_gobierno_escolar_0.gif" alt="Cargos de Gobierno Escolar"  height="26" border="0"></a><img src="../etc/img/tabs/tipo_escala_valorativa_1.gif" alt="Tipos de Escala Valorativa" width="84"  height="26" border="0"><a href="javaScript:lanzar(50)"><img src="../etc/img/tabs/tipos_receso_0.gif" alt="Tipos de Receso"  height="26" border="0"></a><a href="javaScript:lanzar(49)"><img src="../etc/img/tabs/tipos_atencion_especial_0.gif" alt="Tipos de Atención Especial"  height="26" border="0"></a><a href="javaScript:lanzar(65)"><img src="../etc/img/tabs/calendario_0.gif" alt="Calendario"  height="26" border="0"></a></td>
			</tr>
  	</table>
		<!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
					<TABLE width="100%" cellpadding="3" cellSpacing="0">
						<tr><TD>
							<iframe id="uno" name="1" marginheight="0" marginwidth="0"  frameborder="NO"  width="100%" src='<c:url value="/datoMaestro/DatoMaestroListaEdit.do?ext=1"/>' valign="top" ALIGN="center" height="200px"></iframe>
						</TD></TR><TR><TD>
							<iframe id="dos" name="2" marginheight="0" marginwidth="0"  frameborder="NO" width="100%" SRC='<c:url value="/datoMaestro/DatoMaestroNuevoEdit.do?ext=1"/>' valign="top" ALIGN="center" height="200px"></iframe>					
						</TD></TR>
				</TABLE>
	</font>		
</form>
</body>
</html>