<!--	VERSION		FECHA			AUTOR				DESCRIPCION
			1.0		30/04/2018		JORGE CAMACHO		CODIGO ESPAGUETI Y ELIMINAR LOS CAMPOS PASSWORD
 -->

<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>

<%@page import="siges.util.Recursos"%>

<jsp:useBean id="nuevoUsuario" class="siges.usuario.beans.Usuario" scope="session" />
<jsp:setProperty name="nuevoUsuario" property="*"/>

<%pageContext.setAttribute("filtroLoc",Recursos.recurso[Recursos.LOCALIDAD]);%>

<%@include file="../parametros.jsp"%>

<script language='javaScript'>
	<!--
		var nav4=window.Event ? true : false;
			
		function borrar_combo(combo) {
			for(var i = combo.length - 1; i >= 0; i--) {
				if(navigator.appName == "Netscape")
					combo.options[i] = null;
				else
					combo.remove(i);
			}
			combo.options[0] = new Option("--Seleccione uno--","-1");
		}
		
		/*function cargaDepartamento() {
			borrar_combo(document.frmNuevoUsuario.municipio);
			borrar_combo(document.frmNuevoUsuario.nucleo);
			borrar_combo(document.frmNuevoUsuario.institucion);
			borrar_combo(document.frmNuevoUsuario.sede);
			borrar_combo(document.frmNuevoUsuario.jornada);
			document.frmAux.depar.value = document.frmNuevoUsuario.departamento.options[document.frmNuevoUsuario.departamento.selectedIndex].value;
			document.frmAux.combo.value = "1";
 			document.frmAux.target = "frame";
			document.frmAux.submit();
		}*/
			
		function cargaMunicipio() {
			//borrar_combo(document.frmNuevoUsuario.nucleo);
			borrar_combo(document.frmNuevoUsuario.institucion);
			borrar_combo(document.frmNuevoUsuario.sede);
			borrar_combo(document.frmNuevoUsuario.jornada);
			document.frmAux.munic.value = document.frmNuevoUsuario.municipio.options[document.frmNuevoUsuario.municipio.selectedIndex].value;
			document.frmAux.combo.value = "3";
 			document.frmAux.target = "frame";
			document.frmAux.submit();
		}
			
		/*function cargaNucleo() {
			borrar_combo(document.frmNuevoUsuario.institucion);
			borrar_combo(document.frmNuevoUsuario.sede);
			borrar_combo(document.frmNuevoUsuario.jornada);
			document.frmAux.nucleo.value = document.frmNuevoUsuario.nucleo.options[document.frmNuevoUsuario.nucleo.selectedIndex].value;
			document.frmAux.combo.value = "3";
 			document.frmAux.target = "frame";
			document.frmAux.submit();
		}*/
			
		function cargaSede() {
			borrar_combo(document.frmNuevoUsuario.sede);
			borrar_combo(document.frmNuevoUsuario.jornada);
			document.frmAux.inst.value = document.frmNuevoUsuario.institucion.options[document.frmNuevoUsuario.institucion.selectedIndex].value;
			document.frmAux.combo.value = "4";
 			document.frmAux.target = "frame";
			document.frmAux.submit();
		}
		
		function cargaJornada() {
			borrar_combo(document.frmNuevoUsuario.jornada);
			document.frmAux.inst.value = document.frmNuevoUsuario.institucion.options[document.frmNuevoUsuario.institucion.selectedIndex].value;
			document.frmAux.sede.value = document.frmNuevoUsuario.sede.options[document.frmNuevoUsuario.sede.selectedIndex].value;
			document.frmAux.combo.value = "5";
 			document.frmAux.target = "frame";
			document.frmAux.submit();
		}

		function validarPerfil() {

			//document.frmNuevoUsuario.nucleo.disabled = true;
			//document.frmNuevoUsuario.departamento.disabled = true;
			
			document.frmNuevoUsuario.sede.disabled = true;
			document.frmNuevoUsuario.jornada.disabled = true;
			document.frmNuevoUsuario.municipio.disabled = true;
			document.frmNuevoUsuario.institucion.disabled = true;
			
			//VALIDACION PARA PERFILES DE NIVEL CENTRAL POA = 0
			if(document.frmNuevoUsuario.usuperfcodigo.value.substring(0, document.frmNuevoUsuario.usuperfcodigo.value.indexOf('|')) == '0') {
				document.frmNuevoUsuario.usucoddependencia.disabled = false;
				document.frmNuevoUsuario.municipio.disabled = true;
				//document.frmNuevoUsuario.nucleo.disabled = true;
				document.frmNuevoUsuario.institucion.disabled = true;
				document.frmNuevoUsuario.sede.disabled = true;
				document.frmNuevoUsuario.jornada.disabled = true;
				
			} else if(document.frmNuevoUsuario.usuperfcodigo.value.substring(0, document.frmNuevoUsuario.usuperfcodigo.value.indexOf('|')) == '1') {
				//document.frmNuevoUsuario.departamento.disabled = false;
				document.frmNuevoUsuario.usucoddependencia.disabled = true;
				document.frmNuevoUsuario.municipio.disabled = true;
				//document.frmNuevoUsuario.nucleo.disabled = true;
				document.frmNuevoUsuario.institucion.disabled = true;
				document.frmNuevoUsuario.sede.disabled = true;
				document.frmNuevoUsuario.jornada.disabled = true;
				
			} else if(document.frmNuevoUsuario.usuperfcodigo.value.substring(0, document.frmNuevoUsuario.usuperfcodigo.value.indexOf('|')) == '2') {
				document.frmNuevoUsuario.usucoddependencia.disabled = true;
				//document.frmNuevoUsuario.departamento.disabled = false;
				document.frmNuevoUsuario.municipio.disabled = false;
				//document.frmNuevoUsuario.nucleo.disabled = true;
				document.frmNuevoUsuario.institucion.disabled = true;
				document.frmNuevoUsuario.sede.disabled = true;
				document.frmNuevoUsuario.jornada.disabled = true;
				
			} else if(document.frmNuevoUsuario.usuperfcodigo.value.substring(0, document.frmNuevoUsuario.usuperfcodigo.value.indexOf('|')) == '3') {
				document.frmNuevoUsuario.usucoddependencia.disabled = true;
				//document.frmNuevoUsuario.departamento.disabled = false;
				document.frmNuevoUsuario.municipio.disabled = false;
				//document.frmNuevoUsuario.nucleo.disabled = false;
				document.frmNuevoUsuario.institucion.disabled = true;
				document.frmNuevoUsuario.sede.disabled = true;
				document.frmNuevoUsuario.jornada.disabled = true;
				
			} else if(document.frmNuevoUsuario.usuperfcodigo.value.substring(0, document.frmNuevoUsuario.usuperfcodigo.value.indexOf('|')) == '4') {
				document.frmNuevoUsuario.usucoddependencia.disabled = true;
				//document.frmNuevoUsuario.departamento.disabled = false;
				document.frmNuevoUsuario.municipio.disabled = false;
				//document.frmNuevoUsuario.nucleo.disabled = false;
				document.frmNuevoUsuario.institucion.disabled = false;
				document.frmNuevoUsuario.sede.disabled = true;
				document.frmNuevoUsuario.jornada.disabled = true;
				
			} else if(document.frmNuevoUsuario.usuperfcodigo.value.substring(0, document.frmNuevoUsuario.usuperfcodigo.value.indexOf('|')) == '6') {
				document.frmNuevoUsuario.usucoddependencia.disabled = true;
				//document.frmNuevoUsuario.departamento.disabled = false;
				document.frmNuevoUsuario.municipio.disabled = false;
				//document.frmNuevoUsuario.nucleo.disabled = false;
				document.frmNuevoUsuario.institucion.disabled = false;
				document.frmNuevoUsuario.sede.disabled = false;
				document.frmNuevoUsuario.jornada.disabled = false;
				
			}
			
		}

		function acepteNumeros(eve) {
			var key=nav4?eve.which :eve.keyCode;
			return(key<=13 || (key>=48 && key<=57));
		}
			
		function hacerValidaciones_frmNuevoUsuario(frmNuevoUsario) {
		
			//validarCampo(frmNuevoUsuario.usulogin, "- Login de Usuario", 1, 12);
			validarCampo(frmNuevoUsuario.usupernumdocum, "- Número de Documento", 1, 20);
			validarLista(frmNuevoUsuario.usuperfcodigo, "- Perfil de Usuario", 1);
			
			if(frmNuevoUsuario.usucoddependencia.disabled == false)
				validarLista(frmNuevoUsuario.usucoddependencia, "- Dependencia", 1);
				
			if(frmNuevoUsuario.municipio.disabled == false)
				validarLista(frmNuevoUsuario.municipio, "- Localidad", 1);
				
			/*if(frmNuevoUsuario.nucleo.disabled == false)
				validarLista(frmNuevoUsuario.nucleo, "- Nucleo", 1)*/
				
			if(frmNuevoUsuario.institucion.disabled == false)
				validarLista(frmNuevoUsuario.institucion, "- Colegio", 1);
			
			if(frmNuevoUsuario.sede.disabled == false)
				validarLista(frmNuevoUsuario.sede, "- Sede", 1);
				
			if(frmNuevoUsuario.jornada.disabled == false)
				validarLista(frmNuevoUsuario.jornada, "- Jornada", 1);
				
			//validarContrasena(frmNuevoUsuario.usupassword, frmNuevoUsuario.usupassword2, "Las contraseñas no coinciden, por favor digitelas nuevamente");
		}
		
		/*function lanzar(tipo){			
			document.frmNuevo.tipo.value=tipo;					
			document.frmNuevo.action="<c:url value="/filtro/ControllerNuevoEdit.do"/>";
			document.frmNuevo.submit();
		}*/
			
		function guardar(tipo) {
		
			if(validarForma(document.frmNuevoUsuario)) {
				document.frmNuevoUsuario.codNivel.value = document.frmNuevoUsuario.usuperfcodigo.value.substring(0,document.frmNuevoUsuario.usuperfcodigo.value.indexOf('|'));
				document.frmNuevoUsuario.tipo.value = tipo;
				document.frmNuevoUsuario.cmd.value = "GuardarUsuario";
				document.frmNuevoUsuario.action = '<c:url value="/usuario/NuevoGuardar.jsp"/>';
				document.frmNuevoUsuario.submit();
			}
		}
		
		function ayuda() {
		}

		function cancelar() {
			if (confirm('¿Desea cancelar la inserción de la información del usuario?')) {
				location.href='<c:url value="/bienvenida.jsp"/>';
			}		
		}
			
		function inhabilitarFormulario() {
			//document.frmNuevoUsuario.usupernumdocum.readOnly=true;
		}
			
		function buscar() {
			document.frmNuevoUsuario.tipo.value='0';
			document.frmNuevoUsuario.cmd.value='buscar';
			document.frmNuevoUsuario.action='<c:url value="/usuario/ControllerUsuarioNuevoEdit.do"/>';
			document.frmNuevoUsuario.submit();
		}
	//-->
</script>

<%@include file="../mensaje.jsp" %>

<font size='1'>
	
	<form method="post" name="frmNuevoUsuario" onSubmit=" return validarForma(frmNuevoUsuario)" action='<c:url value="/usuario/NuevoGuardar.jsp"/>'>
		
		<table border="1" align="center" bordercolor="#FFFFFF" width="100%">
			<caption>Nuevo Usuario</caption>
			<tr>		
		  		<td>
					<input name="cmd1" type="button" value="Guardar" onClick="guardar(1)" class="boton">
					<input id="cmd1" name="cmd1" type="button" value="Buscar" onClick="buscar()" class="boton">
		  		</td>   	
			</tr>	
  		</table>
  	
		<input type="hidden" name="tipo" value="1">
		<input type="hidden" name="cmd" value="Cancelar">
		<input type="hidden" name="codNivel" value="1">
		
		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1">
				<td width="10">&nbsp;</td>
				<td rowspan="2" width="469">
					<img src="../etc/img/tabs/usuarios_1.gif" alt="Usuarios" height="26" border="0">
				</td>
			</tr>
		</table>
		<br>
		
		<table border="1" align="center" width="80%" cellpadding="0" cellspacing="0" bordercolor="#FFFFFF">
			<tr>
				<td>
					<span class="style2">*</span><b> Número de documento:</b>
				</td>
				<td>
					<input type="text" name="usupernumdocum"  maxlength="12" onKeyPress='return acepteNumeros(event)' <c:if test="${sessionScope.nuevoUsuario.estadousu==1 || sessionScope.nuevoUsuario.usupernumdocum!=null}">value='<c:out value="${sessionScope.nuevoUsuario.usupernumdocum}"/>' readOnly</c:if>></input>
				</td>
			</tr>
			<!-- 
			<tr>
				<td>
					<span class="style2">*</span><b> Login:</b>
				</td>
				<td>
					<input type="text" name="usulogin" maxlength="20" <c:if test="${sessionScope.nuevoUsuario.estadousu==1}">value='<c:out value="${sessionScope.nuevoUsuario.usulogin}"/>'</c:if><c:if test="${sessionScope.nuevoUsuario.estadousu==1 && sessionScope.nuevoUsuario.usupernumdocum!=null}">value='<c:out value="${sessionScope.nuevoUsuario.usupernumdocum}"/>' readOnly</c:if>></input>
				</td>
			</tr> 
			<tr>
				<td>
					<span class="style2">*</span><b> Password:</b>
				</td>
				<td>
					<input type="password" name="usupassword" maxlenght="20" <c:if test="${sessionScope.nuevoUsuario.estadousu==1}">value='<c:out value="${sessionScope.nuevoUsuario.usupassword}"/>'</c:if>></input>
				</td>
			</tr>
			<tr>
				<td>
					<span class="style2">*</span><b> Confirmar Password:</b>
				</td>
				<td>
					<input type="password" name="usupassword2" maxlenght="20" <c:if test="${sessionScope.nuevoUsuario.estadousu==1}">value='<c:out value="${sessionScope.nuevoUsuario.usupassword}"/>'</c:if>></input>
				</td>
			</tr>
			-->
			<tr>
				<td>
					<span class="style2">*</span><b> Perfil:</b>
				</td>
				<td>
					<select name="usuperfcodigo" onchange='validarPerfil()' style='width:300px;'>
						<option value="-1">-- Seleccione uno --</option>
							<c:forEach begin="0" items="${requestScope.filtroPerfil}" var="fila">
								<option value="<c:out value="${fila[4]}"/>|<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.nuevoUsuario.estadousu==1}"><c:if test="${sessionScope.nuevoUsuario.usuperfcodigo== fila[0]}">SELECTED</c:if></c:if>>
									(<c:out value="${fila[0]}"/>)&nbsp;<c:out value="${fila[1]}"/>
								</option>
							</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>
					<span class="style2">*</span><b> Dependencia:</b>
				</td>
				<td>
					<select name='usucoddependencia' disabled='true' style='width:300px;'>
						<option value="-1">-- Seleccione uno --</option>
							<c:forEach begin="0" items="${requestScope.filtroDependencias}" var="fila">
								<option value="<c:out value="${fila.codigo}"/>" <c:if test="${requestScope._editar[0][0]==fila.codigo}">selected</c:if>>
									<c:out value="${fila.nombre}"/></option>
							</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>
					<span class="style2">*</span><b> Localidad:</b>
				</td>
				<td>
					<select name='municipio' disabled='true' onChange='cargaMunicipio()' style='width:300px;'>
						<option value="-1">-- Seleccione uno --</option>
						<c:forEach begin="0" items="${filtroLoc}" var="fila">
							<option value="<c:out value="${fila[0]}"/>" <c:if test="${requestScope._editar[0][1]==fila[0]}">selected</c:if>>
							<c:out value="${fila[1]}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<!-- 
			<tr>
				<td>
					<span class="style2">*</span><b> Nucleo:</b>
				</td>
				<td>
					<select name='nucleo' disabled='true' onChange='cargaNucleo()' style='width:300px;'>
						<option value="-1">-- Seleccione uno --</option>
					</select>
				</td>
			</tr>
			-->
			<tr>
				<td>
					<span class="style2">*</span><b> Colegio:</b>
				</td>
				<td>
					<select name='institucion' disabled='true' onChange='cargaSede()' style='width:300px;'>
						<option value="-1">-- Seleccione uno --</option>
						<c:if test="${sessionScope.nuevoUsuario.estadousu==1}">
							<c:forEach begin="0" items="${requestScope.colinst}" var="fila">
								<option value="<c:out value="${fila[0]}"/>" <c:if test="${requestScope._editar[0][3]==fila[0]}">selected</c:if>>
								<c:out value="${fila[1]}"/></option>
							</c:forEach>
						</c:if>
					</select>
				</td>
			</tr>
			<tr>
				<td>
					<span class="style2">*</span><b> Sede:</b>
				</td>
				<td>
					<select name='sede' disabled='true' onChange='cargaJornada()' style='width:300px;'>
						<option value="-1">-- Seleccione uno --</option>
						<c:if test="${sessionScope.nuevoUsuario.estadousu==1}">
							<c:forEach begin="0" items="${requestScope.colsede}" var="fila">
								<option value="<c:out value="${fila[0]}"/>" <c:if test="${requestScope._editar[0][4]==fila[0]}">selected</c:if>>
								<c:out value="${fila[1]}"/></option>
							</c:forEach>
						</c:if>
					</select>
				</td>
			</tr>
			<tr>
				<td>
					<span class="style2">*</span><b> Jornada:</b>
				</td>
				<td>
					<select name='jornada' disabled='true' style='width:300px;'>
						<option value="-1">-- Seleccione uno --</option>
						<c:if test="${sessionScope.nuevoUsuario.estadousu==1}">
							<c:forEach begin="0" items="${requestScope.coljorn}" var="fila">
								<option value="<c:out value="${fila[0]}"/>" <c:if test="${requestScope._editar[0][5]==fila[0]}">selected</c:if>>
								<c:out value="${fila[1]}"/></option>
							</c:forEach>
						</c:if>
					</select>
				</td>
			</tr>
			<tr>
				<td style="display:none">
				<iframe name="frame" id="frame"></iframe>
				</td>
			</tr>
		</table>
		
	</form>
	
	<form method="post" name="frmAux" action="<c:url value="/usuario/ControllerUsuarioFiltroEdit.do"/>">
		<input type="hidden" name="combo" value="">
		<input type="hidden" name="depar" value="">
		<input type="hidden" name="munic" value="">
		<input type="hidden" name="nucleo" value="">
		<input type="hidden" name="inst" value="">
		<input type="hidden" name="sede" value="">
		<input type="hidden" name="jornada" value="">
		<input type="hidden" name="ext" value="1">
		<input type="hidden" name="forma" value="frmNuevoUsuario">
	</form>
	
</font>
	
<script>
	<c:if test="${sessionScope.nuevoUsuario.estadousu==1}">
		validarPerfil()
	</c:if>
</script>
