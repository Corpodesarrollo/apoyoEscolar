<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/parametros.jsp"/>
<jsp:useBean id="paramsVO" class="articulacion.artPlantillaFinal.vo.ParamsVO" scope="page"/>
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
				document.frmNuevo.action='<c:url value="/articulacion/artPlantillaFinal/Filtro.do"/>';
				document.frmNuevo.cmd.value='<c:out value="${paramsVO.CMD_NUEVO}"/>';
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
				if (confirm('¿Desea cancelar la importación de la plantilla?')){
					location.href='<c:url value="/bienvenida.jsp"/>';
				}
			}
			//-->
</script>
<c:import url="/mensaje.jsp"/>
	<form method="post" name="frmNuevo"  onSubmit="return validarForma(frmNuevo)" action='<c:url value="/articulacion/artPlantillaFinal/FiltroImp.do"/>'>
<!--//////////////////-->		
	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_FILTRO_IMP}"/>'>
	<input type="hidden" name="cmd">
	<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
		<tr height="1">
			<td width="10" bgcolor="#FFFFFF">&nbsp;</td>							
			<td rowspan="2" width="588" bgcolor="#FFFFFF">
				<a href='javaScript:lanzar(<c:out value="${paramsVO.FICHA_FILTRO_EVAL}"/>)'><img src='<c:url value="/etc/img/tabs/art_enlinea_0.gif"/>' alt="En linea" width="84"  height="26" border="0"></a>
				<a href='javaScript:lanzar(<c:out value="${paramsVO.FICHA_FILTRO}"/>)'><img src='<c:url value="/etc/img/tabs/art_generar_plantillas_0.gif"/>' alt="Importar" width="84"  height="26" border="0"></a>
				<img src='<c:url value="/etc/img/tabs/art_importar_plantillas_1.gif"/>' alt="Importar" width="84"  height="26" border="0">
			</td>
		</tr>
  </table><!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
	<table border="0" align="center" bordercolor="#FFFFFF" width="95%">
	<caption>Importación de Evaluación final</caption>
		<tr>
		  <td width="45%" bgcolor="#FFFFFF">
        <input   class='boton' name="cmd1" type="button" value="Aceptar" onClick="guardar(document.frmNuevo.tipo.value)">
			</td>
		</tr>
	</table>
	<TABLE width="95%" cellpadding="3" cellSpacing="0" align="center">
		<tr>
			<td><span class="style2">*</span>Archivo de plantilla a importar:</td>
			<td><input type='file' name='archivo' style='width:420px;'></td>
		</tr>
	</TABLE>
	</form>
	<c:if test="${requestScope.formaEstado==1}">
		<TABLE width="100%" cellpadding="3" cellSpacing="0">
			<tr>
				<td colspan="6" align='center' valign="top">
				La plantilla de inconsistencias fue generada.<br>Pulse en el ícono para descargar el archivo.<br>
				<a href='<c:url value="/${requestScope.plantilla}"/>'><img border=0 src='<c:url value="/etc/img/xls.gif"/>'></a>
				</td>
			</tr>
		</TABLE>
	</c:if>
	<c:if test="${requestScope.formaEstado==2}">
		<TABLE width="50%" cellpadding="3" cellSpacing="0" border="0" align="left">
			<tr><td colspan="2" align='center' valign="top"><b><span class="style2">Resultado de la importación de evaluación final.</span></b><br></td></tr>
			<c:forEach begin="0" items="${requestScope.resultadoImportacion}" var="r">
				<tr>
					<td><c:out value="${r.nombre}"/></td>
					<td><c:out value="${r.codigo}"/></td>
				</tr>
			</c:forEach>
		</TABLE>
	</c:if>