<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
	<html>
	<head>
		<title>Datos Maestros - División Política</title>
		<%@include file="../parametros.jsp"%>

		<script languaje='javaScript'>
		<!--
			/*                                                                                                             
			function ayuda(){
 			 remote = window.open("");
			 remote.location.href="/sae/tutor/nuevo_registro_orientadora/index.html";
			}
			*/
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
	    <style type="text/css">
			<!--
			.style2 {color: #FF6666}
			-->
        </style>
	</head>
	<body onLoad='cargar(frmNuevo)' bgcolor="#F6F6F6">
	<%@include file="../mensaje.jsp"%>
	<font size="1">
	<!--	<form name="f" target='1' action="<c:url value="/estudiante/ControllerFiltroSave"/>" ></form>-->
	<form method="post" name="frmNuevo" onSubmit=" return validarForma(frmNuevo)" action="<c:url value="/plandeEstudios/NuevoGuardar.jsp"/>">
			<table border="1" align="center" bordercolor="#FFFFFF" width="80%">
			<caption>
			Generaci&oacute;n de Boletines -  Filtro de busqueda -
			</caption>
				<tr>
				  <td>
						<INPUT TYPE="submit" NAME="cmd1"  VALUE="Generar" onClick="generar()">
						<INPUT TYPE="button" NAME="cmd3"  VALUE="Cancelar"  onClick="cancelar()">
						<input type="button" name="cmd13" value="Ayuda" onClick="ayuda()">
						<br>
				  </td>
				</tr>			

			</table>
<!--//////////////////-->		
	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="tipo2" value="1">
	<input type="hidden" name="cmd" value="Cancelar">
			<table border="1" align="center" bordercolor="#FFFFFF" width="80%">
						<tr height="1">
							<td rowspan="2" width="782" bgcolor="#FFFFFF"><img src="../etc/img/tabs/Boletines_0.gif" alt="Departamentos" width="84"  height="26" border="0"><a href="javaScript:lanzar(88)"><img src="../etc/img/tabs/Certificados_0.gif" alt="Municipios" width="84"  height="26" border="0"></a><img src="../etc/img/tabs/Constancia_0.gif" alt="N&uacute;cleos" width="84"  height="26" border="0"><img src="../etc/img/tabs/Carnes_0.gif" alt="N&uacute;cleos" width="84"  height="26" border="0"></td>
						</tr>
						<tr>
							<td bgcolor="#000000" height="1"><img src="../img/pixel.gif" width="1" height="1"></td>
							<td width="184" height="1" bgcolor="#000000"><img src="../img/pixel.gif" width="1" height="1"></td>
						</tr>
		  </table>
            <table border="1" align="center" bordercolor="#FFFFFF" width="80%">
				<tr>
					<td>Sede:</td>
					<td>
						<select name="sede" onChange='filtro(document.frm.sede, document.frm.jornada,document.frm.grado,document.frm.grupo)' style='width:300px;'>
							<option value='-1'>-- Todos --</option>
							<c:forEach begin="0" items="${sessionScope.filtroSedeF}" var="fila">
								<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.filtro.sede== fila[0]}">SELECTED</c:if>>
								<c:out value="${fila[1]}"/></option>
							</c:forEach>
						</select>											
					</td>	
					<td>Jornada:</td>
					<td>
						<select name="jornada" onChange='filtro2(document.frm.sede, document.frm.jornada,document.frm.grado,document.frm.grupo)' style='width:120px;'>
							<option value='-1'>-- Todos --</option>
						</select>							
					</td>
				</tr>
				<tr>
				<td><span class="style2">*</span>Metodología:</td>
				<td>
					<select name="metodologia" style='width:120px;'>
						<option value='-1'>-- Seleccione una --</option>
						<c:forEach begin="0" items="${sessionScope.filtroMetodologiaF}" var="fila">
						<option value='<c:out value="${fila[0]}"/>'><c:out value="${fila[1]}"/></option>
						</c:forEach>
				  </select>
				  </td>	
				<td>Grado:</td>
					<td>
						<select name="grado" onChange='filtro3(document.frm.sede, document.frm.jornada,document.frm.grado,document.frm.grupo)' style='width:120px;'>
							<option value='-1'>-- Todos --</option>
						</select>											
					</td>	
				</tr>
				<tr>
					<td>Grupo:</td>
					<td>
						<select name="grupo" style='width:120px;'>
							<option value='-1'>-- Todos --</option>
						</select>							
					</td>
					<td>&nbsp;</td>
					<td>&nbsp;
					</td>	
				</tr>	
	<input type='hidden' name='dato' value='<%=request.getSession().getAttribute("dato")%>'>				
			</table>
<p align="center">
<b>La Generación del documento fue realizada satisfactoriamente.</b><br><br>
<b>Pulse en el icono para descargar el archivo.</b><br>
<a href="../SIGE MER.zip"><img src="../etc/img/xls.gif"></a>
</p>
		</FORM>
	</font>		
