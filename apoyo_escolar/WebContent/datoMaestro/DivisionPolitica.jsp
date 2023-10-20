<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@include file="../parametros.jsp"%><c:set var="dat" value="1" scope="page"/>
	<html>
	<head>
		<title>Datos Maestros - División Política</title>
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
	<body onLoad='cargar(frmNuevo)' bgcolor="#F6F6F6">
	<%@include file="../mensaje.jsp"%>
	<font size="1">
	<form method="post" name="frmNuevo"  onSubmit=" return validarForma(frmNuevo)" action="<c:url value="/datoMaestro/DatoMaestroSeleccionEdit.do"/>">
<!--//////////////////-->		
	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="dato" value='<c:out value="${pageScope.dat}"/>'>
	<input type="hidden" name="cmd" value="Cancelar">
	<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" bgcolor="#F6F6F6">
			<tr height="1">
				<td width="10" bgcolor="#FFFFFF">&nbsp;</td>			
				<td rowspan="2" width="780" bgcolor="#FFFFFF"><img src="../etc/img/tabs/departamentos_1.gif" alt="Departamentos"  height="26" border="0"><a href="javaScript:lanzar(21)"><img src="../etc/img/tabs/localidad_0.gif" alt="Localidades"  height="26" border="0"></a></td>
			</tr>
  </table>
		<!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
				<TABLE width="100%" cellpadding="3" cellSpacing="0">
							<tr>
								<TD>
									<iframe id="uno" name="1" marginheight="0" marginwidth="0"  frameborder="NO"  width="100%" src='<c:url value="/datoMaestro/DatoMaestroListaEdit.do?ext=1"/>' valign="top" ALIGN="center" height="200px"></iframe>
								</TD>
							</TR>
							<TR>
								<TD>
									<iframe id="dos" name="2" marginheight="0" marginwidth="0"  frameborder="NO" width="100%" SRC='<c:url value="/datoMaestro/DatoMaestroNuevoEdit.do?ext=1"/>' valign="top" ALIGN="center" height="200px" ></iframe>	
								</TD>
							</TR>
					</TABLE>
	</font>		
</form>
</body>
</html>