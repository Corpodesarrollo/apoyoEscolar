<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/parametros.jsp"/>
<jsp:useBean id="paramsVO" class="siges.plantilla.beans.ParamsVO" scope="page"/>
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
				document.frmNuevo.action='<c:url value="/importar/evaluacion/ControllerImportarEdit.do"/>';
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
					document.frmNuevo.cmd.value='<c:out value="${paramsVO.CMD_GUARDAR}"/>';
					document.frmNuevo.submit();
				}
			}
			function cancelar(){
				if (confirm('�Desea cancelar la importaci�n de la plantilla?')){
					location.href='<c:url value="/bienvenida.jsp"/>';
				}
			}
			//-->
</script>
<c:import url="/mensaje.jsp"/>
	<form method="post" name="frmNuevo"  onSubmit="return validarForma(frmNuevo)" action='<c:url value="/importar/comportamiento/Nuevo.do"/>'>
	<br>
	<table border="1" align="center" bordercolor="#FFFFFF" width="95%">
	<caption>Importaci�n de Evaluaci�n de Comportamiento</caption>
		<tr>
		  <td width="45%" bgcolor="#FFFFFF">
        <input   class='boton' name="cmd1" type="button" value="Aceptar" onClick="guardar(document.frmNuevo.tipo.value)">
			</td>
		</tr>
	</table>
<!--//////////////////-->		
	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_COMPORTAMIENTO}"/>'>
	<input type="hidden" name="cmd">
	<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
		<tr height="1">
			<td width="10" bgcolor="#FFFFFF">&nbsp;</td>							
			<td rowspan="2" width="588" bgcolor="#FFFFFF">
				<a href="javaScript:lanzar(1)"><img src='<c:url value="/etc/img/tabs/Evaluacion_Asignaturas_0.gif"/>' alt="Asignatura" width="84"  height="26" border="0"></a>
				<a href="javaScript:lanzar(2)"><img src='<c:url value="/etc/img/tabs/Evaluacion_Areas_0.gif"/>' alt='-Area-' width="84"  height="26" border="0"></a>
				<a href="javaScript:lanzar(3)"><img src='<c:url value="/etc/img/tabs/Evaluacion_Preescolar_0.gif"/>' alt="Preescolar" width="84"  height="26" border="0"></a>
				<img src='<c:url value="/etc/img/tabs/Evaluacion_Comportamiento_1.gif"/>' alt="Comportamiento" width="84"  height="26" border="0">
			</td>
		</tr>
  </table><!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
	<TABLE width="100%" cellpadding="3" cellSpacing="0">
		<tr>
			<td><span class="style2">*</span>Archivo de plantilla a importar:</td>
			<td><input type='file' name='archivo' style='width:420px;'></td>
		</tr>
	</TABLE>
	</form>
	<c:if test="${requestScope.formaEstado==1}">
		<form name='frmGenerar' action='<c:url value="/${requestScope.plantilla}"/>' method='post' target='_blank'>
				<input type='hidden' name='dir' value='<c:out value="${requestScope.plantilla}"/>'>
				<!-- <input type='hidden' name='tipo' value='<c:out value="${requestScope.tipoArchivo}"/>'>-->
				<input type='hidden' name='tipo' value='xls'>
				<input type='hidden' name='accion' value='0'>
				<input type='hidden' name='aut' value='1'>
		<TABLE width="100%" cellpadding="3" cellSpacing="0">
			<tr>
				<td colspan="6" align='center' valign="top">
				La plantilla de inconsistecias fue generada.<br>Pulse en el �cono para descargar el archivo.<br>				
				<!--   <a href='<c:url value="/${requestScope.plantilla}"/>'><img border=0 src='<c:url value="/etc/img/xls.gif"/>'></A>-->
				>><a href='javaScript:document.frmGenerar.submit();'><img border=0 src='<c:url value="/etc/img/xls.gif"/>'></A><<
				</td>
			</tr>
		</TABLE>
		</form>
	</c:if>
	<c:if test="${requestScope.formaEstado==2}">
		<TABLE width="50%" cellpadding="3" cellSpacing="0" border="0" align="left">
			<tr><td colspan="2" align='center' valign="top"><b><span class="style2">Resultado de la importaci�n de evaluaci�n de Comportamiento.</span></b><br></td></tr>
			<c:forEach begin="0" items="${requestScope.resultadoImportacion}" var="r">
				<tr>
					<td><c:out value="${r.nombre}"/></td>
					<td><c:out value="${r.codigo}"/></td>
				</tr>
			</c:forEach>
		</TABLE>
	</c:if>