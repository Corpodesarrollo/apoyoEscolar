<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/parametros.jsp"/>
<c:set var="tip" value="3" scope="page"/>
<jsp:useBean id="paramsVO" class="articulacion.importarArticulacion.objetos.ParamsVO" scope="page"/>
<script languaje='javaScript'>
		<!--
	function lanzarServicio(n){
		if(n==1){//plantilla
			document.frmNuevo.action='<c:url value="/articulacion/plantillaArticulacion/Nuevo.do"/>';  	
  	}
		if(n==2){//importacion
			document.frmNuevo.action='<c:url value="/articulacion/importarArticulacion/Nuevo.jsp"/>';  	
  	}
		document.frmNuevo.target="";
		document.frmNuevo.submit();
	}
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
			
				document.frmNuevo.submit();
			}
			
			function guardar(tipo){
				//document.frmNuevo.cmd.value=document.frmNuevo.NUEVO.value;
				if(validarForma(document.frmNuevo))
				{
                document.frmNuevo.encoding="multipart/form-data";
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
	<form method="post" name="frmNuevo"  onSubmit="return validarForma(frmNuevo)" action='<c:url value="/articulacion/importarArticulacion/Nuevo.do"/>'>
     <input type="hidden" name="cmd" value=''>
     <input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="tipo" value=10>
	<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
		<tr height="1">
			<td width="10">&nbsp;</td>
			<td rowspan="2" width="469">
				<a href="javaScript:lanzarServicio(1)"><img src='<c:url value="/etc/img/tabs/art_generar_plantillas_0.gif"/>' alt="" height="26" border="0"></a>
				<img src='<c:url value="/etc/img/tabs/art_importar_plantillas_1.gif"/>' alt="Asignaturas" height="26" border="0">
			</td>
		</tr>
	</table>
	<table border="0" align="center" bordercolor="#FFFFFF" width="95%">
	<caption>Importación de Estudiantes Articulación</caption>
		<tr>
		  <td width="45%" bgcolor="#FFFFFF">
       <input class='boton' name="cmd1" type="button" value="Aceptar" onClick="guardar(document.frmNuevo.tipo.value)">
			</td>
		</tr>
	</table>
	<table width="95%" cellpadding="3" cellSpacing="0" align="center">
		<tr>
		  <td><span class="style2">*</span>Archivo de plantilla a importar:</td>
			 <td><input type='file' name='archivo' style='width:420px;'></td>
		</tr>
		<c:if test="${!empty requestScope.plantilla && requestScope.plantilla!='--'}">
			<tr>
				<td colspan="6" align='center' valign="top">
				Hubo inconsistencias en los datos de la plantilla.<br>Pulse en el Icono para descargar el archivo y ver los datos equivocados.<br>
				<a href='<c:url value="/${requestScope.plantilla}"/>' target='_blank'><img border=0 src='<c:url value="/etc/img/xls.gif"/>'></A>
				</td>
			</tr>
		</c:if>		
		<c:if test="${!empty requestScope.resultado}">
       <tr><td colspan="6" align='center' valign="top"><b>Resultado de la importación</b><br></td></tr>
       <tr><td valign="top"><b>Registros insertados:</b></td><td><c:out value="${requestScope.resultado.ingresados}"/></td></tr>
       <tr><td valign="top"><b>Resgistros actualizados:</b></td><td><c:out value="${requestScope.resultado.actualizados}"/></td></tr>
       <tr><td valign="top"><b>Registros eliminados:</b></td><td><c:out value="${requestScope.resultado.eliminados}"/></td></tr>									
		</c:if>
	</table>
</form>