<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@include file="../parametros.jsp"%><c:set var="dat" value="1" scope="page"/>
	<html>
	<head>
		<title>Datos Maestros - Parámetros Académicos</title>

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
			
			        function lanzar_(tipo){				
				document.frmNuevo.tipo.value=tipo;
				document.frmNuevo.cmd.value='6';
				document.frmNuevo.action = '<c:url value="/adminParamsAcad/Nuevo.do"/>';
				document.frmNuevo.target = ""; 
				document.frmNuevo.submit();
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
	<input type="hidden" name="tipo" value="1">
	<table cellpadding="0" cellspacing="0" border="0" >
			<tr height="1">
				<td width="8"  bgcolor="#FFFFFF">&nbsp;</td>			
				<td  width="650px" rowspan="2" bgcolor="#FFFFFF"><a href="javaScript:lanzar(22)"><img src="../etc/img/tabs/areas_0.gif" alt="&Aacute;reas"  height="26" border="0"></a><a href="javaScript:lanzar(29)"><img src="../etc/img/tabs/asignatura_0.gif" alt="Asignaturas"  height="26" border="0"></a><a href="javaScript:lanzar(1)"><img src="../etc/img/tabs/jornada_0.gif" alt="Jornadas"  height="26" border="0"></a><img src="../etc/img/tabs/nivel_1.gif" alt="Niveles"  height="26" border="0"><a href="javaScript:lanzar(23)"><img src="../etc/img/tabs/grados_0.gif" alt="Grados"  height="26" border="0"></a><a href="javaScript:lanzar(24)"><img src="../etc/img/tabs/calendario_0.gif" alt="Calendarios"  height="26" border="0"></a><a href="javaScript:lanzar(9)"><img src="../etc/img/tabs/metodologia_0.gif" alt="Metodolog&iacute;as"  height="26" border="0"></a><a href="javaScript:lanzar(46)"><img src="../etc/img/tabs/idiomas_0.gif" alt="Idiomas"  height="26" border="0"></a><a href="javaScript:lanzar(42)"><img src="../etc/img/tabs/escalas_valorativas_0.gif" alt="Escalas Valorativas"  height="26" border="0"></a><a href="javaScript:lanzar(12)"><img src="../etc/img/tabs/especialidad_0.gif" alt="Especialidades"  height="26" border="0"></a><a href="javaScript:lanzar(53)"><img src="../etc/img/tabs/tipos_enfasis_0.gif" alt="Tipos de Énfasis"  height="26" border="0"></a><a href="javaScript:lanzar(52)"><img src="../etc/img/tabs/tipos_modalidad_0.gif" alt="Tipos de Modalidad"  height="26" border="0"></a><a href="javaScript:lanzar(51)"><img src="../etc/img/tabs/tipos_especialidad_0.gif" alt="Tipos de Especialidad"  height="26" border="0"></a><a href="javaScript:lanzar_(1)"><img src='<c:url value="/etc/img/tabs/adm_nivelEval_0.gif"/>' alt="Nivel Evaluación"  height="26" border="0"></a>
				<a href="javaScript:lanzar_(2)"><img src="../etc/img/tabs/dimensiones_0.gif" alt="Dimensiones"  height="26" border="0"></a>
				</td>
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
<!--//////////////////////////////-->
</form>
</body>
</html>