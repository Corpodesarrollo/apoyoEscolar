<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/parametros.jsp"/>
<jsp:useBean id="paramsVO" class="siges.subirFotografia.vo.ParamsVO" scope="page"/>
<jsp:useBean id="params2VO" class="siges.filtro.vo.FichaVO" scope="page"/>
<jsp:useBean id="nuevoBasica" class="siges.estudiante.beans.Basica" scope="session"/>
<jsp:useBean id="filtroCarne" class="siges.reporte.beans.FiltroBeanCarne" scope="session"/>
 <jsp:setProperty name="filtroCarne" property="*"/>
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
		
	   function	generarCarnet(){
			    document.frmCarnet.cmd.value="Buscar";
			    
				document.frmCarnet.formatoCarne.value = 1; //Un carne por hoja
                document.frmCarnet.submit();	
		 }

	   function capturar(){
			//if(confirm('¿ESTA SEGURO DE APROBAR EL NICK?')){ 			
				//remote = window.open('http://serverata:8040/Uploader','3','width=800,height=400,resizable=yes,toolbar=no,directories=no,menubar=no,status=yes')
				remote = window.open('<c:url value="/subirFotografia/fotoCaptura.jsp"/>','3','width=800,height=400,resizable=yes,toolbar=no,directories=no,menubar=no,status=yes')
				document.frmNuevo.target = 'centro';
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

	<form method="post" name="frmCarnet"   action='<c:url value="/reporte/FiltroGuardarCarne.jsp"/>' >
	  	<input TYPE="hidden"   name="ext2"          value=''>
	  	<input TYPE="hidden"   name="ext"          value=''>
	  	<input TYPE="hidden"   name="tipo"          value=''>
	    <input TYPE="hidden"   name="cmd"           value=''>
	    <input TYPE="hidden"   name="formatoCarne"  value=''>
        <input type="hidden"   name="id"      id="id"      value='<c:out value="${sessionScope.nuevoBasica.estnumdoc}"/>' ></td>
        <input type="hidden"   name="escondido"     value='<c:out value="${sessionScope.nuevoBasica.estcodigo}"/>'    ></td>
        <input type="hidden"   name="sede" id="sede"        value='<c:out value="${sessionScope.nuevoBasica.estsede}"/>'  ></td>
        <input type="hidden"   name="jornada" id="jornada"  value='<c:out value="${sessionScope.nuevoBasica.estjor}"/>'   ></td>
        <input type="hidden"   name="metodologia" id="metodologia" value='<c:out value="${sessionScope.nuevoBasica.estmet}"/>'  ></td>
        <input type="hidden"   name="grado"       id="grado"      value='<c:out value="${sessionScope.nuevoBasica.estgra}"/>'   ></td>
        <input type="hidden"   name="grupo"       id="grupo"       value='<c:out value="${sessionScope.nuevoBasica.estgru}"/>'   ></td> 
        <input type="hidden"   name="orden"       id="orden"       value="-9"   ></td>
        <input type="hidden"   name="instId"        value='<c:out value="${sessionScope.login.instId}"/>'    >
		<input type="hidden"   name="institucion"        value='<c:out value="${sessionScope.login.instId}"/>'    >
		<input type="hidden"   name="localidad"        value='<c:out value="${sessionScope.login.locId}"/>'    >
		</td>
		
	</form>
	
	
	
		<!-- TABS -->
		  <table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
		    <tr height="1">
					<td width="10" bgcolor="#FFFFFF">&nbsp;</td>
		      <td rowspan="2" width="588" bgcolor="#FFFFFF">
					<a href="javaScript:lanzar(1)"><img src="../etc/img/tabs/datos_basicos_0.gif" alt="Info. B&aacute;sica"  height="26" border="0"></a>
					<a href="javaScript:lanzar(2)"><img src="../etc/img/tabs/inf_familiar_0.gif" alt="Info. Familiar"  height="26" border="0"></a>
					<a href="javaScript:lanzar(3)"><img src="../etc/img/tabs/inf_salud_0.gif" alt="Inf. Sa	lud"  height="26" border="0"></a>
					<a href="javaScript:lanzar(4)"><img src="../etc/img/tabs/inf_convivencia_0.gif" alt="Convivencia"  height="26" border="0"></a>
					<a href="javaScript:lanzar(5)"><img src="../etc/img/tabs/inf_academica_0.gif" alt="Inf. Académica"  height="26" border="0"></a>
					<a href="javaScript:lanzar(7)"><img src="../etc/img/tabs/atencion_especial_0.gif" alt="Atención Especial"  height="26" border="0"></a>
					<img src="../etc/img/tabs/fotografia_1.gif" alt="Subir Foto Estudiante"  height="26" border="0"></td>
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
						 |
						<input class='boton' id="cmd0" name="cmd0" type="button" value="Capturar" onClick="javaScript:capturar()"> 
						|
						<input class='boton' id="cmd1_" name="cmd1_" type="button" value="Imprimir Carné" onClick="generarCarnet();">
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
				<td><!-- <input class='boton' id="cmd1_" name="cmd1_" type="button" value="Imprimir Carnet" onClick="generarCarnet();"> -->
				</td>
				</tr>
			</table>
	</form>
</body>
</html>