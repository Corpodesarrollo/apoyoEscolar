<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/parametros.jsp"/>
<jsp:useBean id="paramsVO" class="siges.subirFotografia.vo.ParamsVO" scope="page"/>
<jsp:useBean id="params2VO" class="siges.filtro.vo.FichaVO" scope="page"/>
<jsp:useBean id="nuevoBasica" class="siges.estudiante.beans.Basica" scope="session"/>
<html>
<head>
		<script language="JavaScript">
			<!--
    var extensiones = new Array();
    extensiones[0]=".jpe";
    extensiones[1]=".jpg";
    
			function guardar(){
				if(validarForma(document.frmNuevo)){
						document.frmNuevo.encoding="multipart/form-data";
						document.frmNuevo.cmd.value='<c:out value="${paramsVO.CMD_GUARDAR}"/>';
						document.frmNuevo.submit();
				}
			}

 		 function lanzar(tipo){				
				document.frmNuevo.tipo.value=tipo;					
				document.frmNuevo.action="<c:url value="/estudiante/ControllerNuevoEdit.do"/>";
				document.frmNuevo.submit();
			}
			
			
		function hacerValidaciones_frmNuevo(forma){
			validarExtension(forma.filArchivo, "- Archivo (foto en formato jpe o jpg)", extensiones)
		}
		
		function capturar(){
			//if(confirm('¿ESTA SEGURO DE APROBAR EL NICK?')){ 			
				remote = window.open('<c:url value="/subirFotografia/fotoCaptura.jsp"/>','3','width=400,height=400,resizable=no,toolbar=no,directories=no,menubar=no,status=yes')
				document.frmNuevo.target = '3';
				remote.moveTo(250, 370);
				//if (remote.opener == null) remote.opener = window;				
				remote.opener.name = "centro";
				remote.focus();
				//document.frmNuevo.target = '_self';
			//}
		}

		//-->
		</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
		<!-- TABS -->
		  <table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
		    <tr height="1">
					<td width="10" bgcolor="#FFFFFF">&nbsp;</td>
		      <td rowspan="2" width="588" bgcolor="#FFFFFF">
					<a href="javaScript:lanzar(1)"><img src="../etc/img/tabs/datos_basicos_0.gif" alt="Info. B&aacute;sica"  height="26" border="0"></a>
					<a href="javaScript:lanzar(2)"><img src="../etc/img/tabs/inf_familiar_0.gif" alt="Info. Familiar"  height="26" border="0"></a>
					<a href="javaScript:lanzar(3)"><img src="../etc/img/tabs/inf_salud_0.gif" alt="Inf. Salud"  height="26" border="0"></a>
					<a href="javaScript:lanzar(4)"><img src="../etc/img/tabs/inf_convivencia_0.gif" alt="Convivencia"  height="26" border="0"></a>
					<a href="javaScript:lanzar(5)"><img src="../etc/img/tabs/inf_academica_0.gif" alt="Inf. Académica"  height="26" border="0"></a>
					<a href="javaScript:lanzar(7)"><img src="../etc/img/tabs/atencion_especial_0.gif" alt="Atención Especial"  height="26" border="0"></a>
					<img src="../etc/img/tabs/fotografia_1.gif" alt="Subir Foto Estudiante"  height="26" border="0">
					
					</td>
				</tr>
		  </table>
		<!-- /TABS -->
	<FORM ACTION='<c:url value="/subirFotografia/Nuevo.do"/>' METHOD="POST" name='frmNuevo'>
		<INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_SUBIR}"/>'>
		<input type="hidden" name="cmd">
		<input type="hidden" name="ext2" value='<c:out value="${sessionScope.subframe}"/>'>
		<INPUT TYPE="hidden" NAME="height" VALUE='130'>
		<table width="95%" border="0" align="center" cellpadding="1" cellspacing="0">
			<caption>Cargar fotografia</caption>
				<tr>
			    <td>
						<input class='boton' id="cmd0" name="cmd0" type="button" value="Guardar" onClick="javaScript:guardar()">
						<input class='boton' id="cmd0" name="cmd0" type="button" value="Capturar" onClick="javaScript:capturar()">
					</td>
				</tr>
		</table>		
		<table width="95%" border="0" align="center" cellpadding="1" cellspacing="0">
				<tr>
					<td>Archivo:</td>
					<td>
						<input type='file' name='filArchivo' style='width:400px;'>
					</td>
				</tr>
				<tr>
				<td>
				<img src='<c:url value="/recursos/imagen.jpg?tipo=${params2VO.RECURSO_FOTOGRAFIA_ESTUDIANTE}&param=${sessionScope.nuevoBasica.estcodigo}"/>' width="113" height="141">
				</td>
				</tr>
			</table>
	</form>
	
</body>
</html>