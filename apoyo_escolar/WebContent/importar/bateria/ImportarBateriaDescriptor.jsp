<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@include file="../../parametros.jsp"%><c:set var="tip" value="5" scope="page"/>
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
        		validarCampo(forma.archivo, "- Archivo (debe ser Excel)")
				validarExtension(forma.archivo, "- Archivo (debe ser Excel)",extensiones)
			}
			function lanzar(tipo){
				document.frmNuevo.tipo.value=tipo;
				document.frmNuevo.action="<c:url value="/importar/bateria/ControllerImportarEdit.do"/>";
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
<style type="text/css">
TD.norepeat{
background-image:url(../../etc/img/tabs/pestana_1.gif); 
background-repeat:no-repeat;
}

TD.norepeat2{
background-image:url(../../etc/img/tabs/pestana_0.gif); 
background-repeat:no-repeat;
cursor:pointer;
}

.sombra {
color:white;
text-shadow:#666666 3px 3px 3px;
filter: progid:DXImageTransform.Microsoft.Shadow(color='#666666', Direction=135, Strength=4);
font-size: 85%;
font-stretch: ultra-expanded;
line-height: 95%;
font-weight: bolder;
}
.sombran {
text-shadow:#666666 3px 3px 3px;
filter: progid:DXImageTransform.Microsoft.Shadow(color='#666666', Direction=135, Strength=4);
font-size: 85%;
font-stretch: ultra-expanded;
line-height: 95%;
font-weight: bolder;
}
</style>
<%@include file="../../mensaje.jsp"%>
	<font size="1">
	<form method="post" name="frmNuevo"  onSubmit="return validarForma(frmNuevo)" action='<c:url value="/importar/bateria/ControllerImportarSave.do"/>'>
	<br>
	<table border="1" align="center" bordercolor="#FFFFFF" width="95%">
	<caption>Importación de Descriptores</caption>
		<tr>
		  <td width="45%" bgcolor="#FFFFFF">
       <input name="cmd1" type="button"  class='boton' value="Aceptar" onClick="guardar(document.frmNuevo.tipo.value)">
			</td>
		</tr>
	</table>
<!--//////////////////-->		
	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="tipo" value='<c:out value="${pageScope.tip}"/>'>
	<input type="hidden" name="cmd" value="Cancelar">
  <table border="0" ALIGN="CENTER" width="95%">
			<tr align="left" colspan="2">
			<c:forEach begin="0" items="${sessionScope.logroanddesc}" var="logdes">
				<td width="15%" height="26" class="norepeat2" onclick='javaScript:lanzar(4)'>
					<p align="center" class="sombran"><c:out value="${logdes[0]}"/></p>
				</td>
				<td width="15%" height="26" class="norepeat" >
				<p align="center" class="sombra"><c:out value="${logdes[1]}"/></p>
				</td>
				<td width="70%" height="26" >
				</td>
			</c:forEach>
			</tr>
		</table>
  <!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
								<TABLE width="100%" cellpadding="3" cellSpacing="0">
									<tr>
										<td><span class="style2">*</span>Archivo de plantilla a importar:</td>
										<td><input type='file' name='archivo' style='width:420px;'></td>
									</tr>
									</form>
									<c:if test="${!empty requestScope.plantilla && requestScope.plantilla!='--'}">
				          			<tr>
										<td colspan="6" align='center' valign="top"><form name='frmGenerar' action='<c:url value="/${requestScope.plantilla}"/>' method='post' target='_blank'><input type='hidden' name='dir' value='<c:out value="${requestScope.plantilla}"/>'><input type='hidden' name='tipo' value='xls'><input type='hidden' name='accion' value='0'><input type='hidden' name='aut' value='1'>Hubo inconsistencias en los datos de la plantilla.<br>Pulse en el Icono para descargar el archivo y ver los datos equivocados.<br></form>
											>><a href='javaScript:document.frmGenerar.submit();'><img border=0 src='<c:url value="/etc/img/xls.gif"/>'></A><<
										</td>
									</tr>									
									</c:if>
									<tr><td colspan="2"><hr></td></tr>
									<c:if test="${!empty requestScope.resultado}">
				          <tr><td colspan="6" align='center' valign="top"><b>Resultados de la importación.</b><br></td></tr>
				          <tr><td valign="top"><b>Descriptores evaluados.</b></td><td><c:out value="${requestScope.resultado[0]}"/></td></tr>
				          <tr><td valign="top"><b>Descriptores insertados.</b></td><td><c:out value="${requestScope.resultado[2]}"/></td></tr>
				          <tr><td valign="top"><b>Descriptores actualizados.</b></td><td><c:out value="${requestScope.resultado[1]}"/></td></tr>
									</c:if>
								</TABLE>
	</font>