<!--	VERSION		FECHA			AUTOR				DESCRIPCION
			1.0		23/04/2018		JORGE CAMACHO		CODIGO ESPAGUETI
			2.0		27/04/2018		JORGE CAMACHO		AGREGAR EL CAMPO CORREO INSTITUCIONAL
			3.0		14/04/2020		JORGE CAMACHO		SE MODIFICO LA LONGITUD DEL CAMPO CORREO INSTITUCIONAL PARA ATENDER EL CASO DEXON 251385-20200402
			
-->

<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>

<%@page import="siges.util.Recursos"%>

<jsp:useBean id="nuevoUsuario" class="siges.usuario.beans.Usuario" scope="session"/>
<jsp:setProperty name="nuevoUsuario" property="*"/>

<%pageContext.setAttribute("filtroDepartamento0", Recursos.recurso[Recursos.LOCALIDAD]);
pageContext.setAttribute("filtroTipoDocumento", Recursos.recurso[Recursos.TIPODOCUMENTO]);%>

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
			combo.options[0] = new Option("-- Seleccione uno --", "-1");
		}
		
		/*function cargaDepartamento() {
			borrar_combo(document.frmNuevoPersonal.municipio);
			borrar_combo(document.frmNuevoPersonal.nucleo);
			borrar_combo(document.frmNuevoPersonal.institucion);
			borrar_combo(document.frmNuevoPersonal.sede);
			borrar_combo(document.frmNuevoPersonal.jornada);
			document.frmAux.depar.value = document.frmNuevoPersonal.departamento.options[document.frmNuevoPersonal.departamento.selectedIndex].value;
			document.frmAux.combo.value = "1";
 			document.frmAux.target = "frame";
			document.frmAux.submit();
		}*/
			
		function cargaMunicipio() {
			//borrar_combo(document.frmNuevoPersonal.nucleo);
			borrar_combo(document.frmNuevoPersonal.institucion);
			borrar_combo(document.frmNuevoPersonal.sede);
			borrar_combo(document.frmNuevoPersonal.jornada);
			document.frmAux.munic.value = document.frmNuevoPersonal.municipio.options[document.frmNuevoPersonal.municipio.selectedIndex].value;
			document.frmAux.combo.value = "3";
 			document.frmAux.target = "frame";
			document.frmAux.submit();
		}
			
		/*function cargaNucleo() {
			borrar_combo(document.frmNuevoPersonal.institucion);
			borrar_combo(document.frmNuevoPersonal.sede);
			borrar_combo(document.frmNuevoPersonal.jornada);
			document.frmAux.nucleo.value = document.frmNuevoPersonal.nucleo.options[document.frmNuevoPersonal.nucleo.selectedIndex].value;
			document.frmAux.combo.value = "3";
 			document.frmAux.target = "frame";
			document.frmAux.submit();
		}*/
			
		function cargaSede() {
			borrar_combo(document.frmNuevoPersonal.sede);
			borrar_combo(document.frmNuevoPersonal.jornada);
			document.frmAux.inst.value = document.frmNuevoPersonal.institucion.options[document.frmNuevoPersonal.institucion.selectedIndex].value;
			document.frmAux.combo.value = "4";
 			document.frmAux.target = "frame";
			document.frmAux.submit();
		}
			
		function cargaJornada() {
			borrar_combo(document.frmNuevoPersonal.jornada);
			document.frmAux.inst.value = document.frmNuevoPersonal.institucion.options[document.frmNuevoPersonal.institucion.selectedIndex].value;
			document.frmAux.sede.value = document.frmNuevoPersonal.sede.options[document.frmNuevoPersonal.sede.selectedIndex].value;
			document.frmAux.combo.value = "5";
 			document.frmAux.target = "frame";
			document.frmAux.submit();
		}
			
		function validarPerfil() {

			//document.frmNuevoPersonal.departamento.disabled = true;
			document.frmNuevoPersonal.municipio.disabled = true;
			//document.frmNuevoPersonal.nucleo.disabled = true;
			document.frmNuevoPersonal.institucion.disabled = true;
			document.frmNuevoPersonal.sede.disabled = true;
			document.frmNuevoPersonal.jornada.disabled = true;
		
			if(document.frmNuevoPersonal.pertipo.value == '1') {

				//document.frmNuevoPersonal.departamento.disabled = false;
				document.frmNuevoPersonal.municipio.disabled = true;
				//document.frmNuevoPersonal.nucleo.disabled = true;
				document.frmNuevoPersonal.institucion.disabled = true;
				document.frmNuevoPersonal.sede.disabled = true;
				document.frmNuevoPersonal.jornada.disabled = true;
				
			} else if(document.frmNuevoPersonal.pertipo.value == '2') {

				//document.frmNuevoPersonal.departamento.disabled = false;
				document.frmNuevoPersonal.municipio.disabled = false;
				//document.frmNuevoPersonal.nucleo.disabled = false;
				document.frmNuevoPersonal.institucion.disabled = false;
				document.frmNuevoPersonal.sede.disabled = true;
				document.frmNuevoPersonal.jornada.disabled = true;
				
			} else if(document.frmNuevoPersonal.pertipo.value == '3' || document.frmNuevoPersonal.pertipo.value == '4') {

				//document.frmNuevoPersonal.departamento.disabled = false;
				document.frmNuevoPersonal.municipio.disabled = false;
				//document.frmNuevoPersonal.nucleo.disabled = false;
				document.frmNuevoPersonal.institucion.disabled = false;
				document.frmNuevoPersonal.sede.disabled = false;
				document.frmNuevoPersonal.jornada.disabled = false;
				
			} else if(document.frmNuevoPersonal.pertipo.value == '5') {

				//document.frmNuevoPersonal.departamento.disabled = false;
				document.frmNuevoPersonal.municipio.disabled = false;
				//document.frmNuevoPersonal.nucleo.disabled = true;
				document.frmNuevoPersonal.institucion.disabled = true;
				document.frmNuevoPersonal.sede.disabled = true;
				document.frmNuevoPersonal.jornada.disabled = true;
			}
			
		}

		function acepteNumeros(eve) {
			var key=nav4?eve.which :eve.keyCode;
			return(key<=13 || (key>=48 && key<=57));
		}
			
		function hacerValidaciones_frmNuevoPersonal(frmNuevoUsario) {
		
			//validarLista(frmNuevoPersonal.pertipo,"- Tipo de Personal", 1);
			validarCampo(frmNuevoPersonal.pernombre1, "- Primer Nombre", 1, 75);
			validarCampo(frmNuevoPersonal.perapellido1, "- Primer Apellido", 1, 50);
			validarLista(frmNuevoPersonal.pertipdocum,"- Tipo de Documento", 1);
			validarCampo(frmNuevoPersonal.pernumdocum, "- Número de Documento", 1, 20);
			validarCampo(frmNuevoPersonal.percorreoinstitucional, "- Correo Institucional", 10, 200);
			
			validarDominioCorreo(frmNuevoPersonal.percorreoinstitucional, "- El dominio del Correo Institucional no es el correcto");
			
			/*if(frmNuevoPersonal.departamento.disabled==false)
				validarLista(frmNuevoPersonal.departamento,"- Departamento",1)
			if(frmNuevoPersonal.municipio.disabled==false)
				validarLista(frmNuevoPersonal.municipio,"- Municipio",1)
			if(frmNuevoPersonal.nucleo.disabled==false)
				validarLista(frmNuevoPersonal.nucleo,"- Nucleo",1)
			if(frmNuevoPersonal.institucion.disabled==false)
				validarLista(frmNuevoPersonal.institucion,"- Institucion",1)
			if(frmNuevoPersonal.sede.disabled==false)
				validarLista(frmNuevoPersonal.sede,"- Sede",1)
			if(frmNuevoPersonal.jornada.disabled==false)
				validarLista(frmNuevoPersonal.jornada,"- Jornada",1)*/
				
		}

		function guardar(tipo) {
			if(validarForma(document.frmNuevoPersonal)) {
				if(document.frmNuevoPersonal.pernombre2.value == "")
					document.frmNuevoPersonal.pernombre2.value = " ";
					
				if(document.frmNuevoPersonal.perapellido2.value == "")
					document.frmNuevoPersonal.perapellido2.value = " ";
					
				document.frmNuevoPersonal.tipo.value = tipo;
				document.frmNuevoPersonal.cmd.value = "GuardarPersonal";
				document.frmNuevoPersonal.submit();
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
			//document.frmNuevoPersonal.usupernumdocum.readOnly = true;
		}
		
		function buscar() {
			document.frmNuevoPersonal.tipo.value = '0';
			document.frmNuevoPersonal.cmd.value = 'buscar';
			document.frmNuevoPersonal.action = '<c:url value="/usuario/ControllerUsuarioNuevoEdit.do"/>';
			document.frmNuevoPersonal.submit();
		}
	//-->
</script>

<%@include file="../mensaje.jsp" %>

<font size='1'>

	<form method="post" name="frmNuevoPersonal" onSubmit=" return validarForma(frmNuevoPersonal)" action='<c:url value="/usuario/NuevoGuardar.jsp"/>'>
	
		<table border="1" align="center" bordercolor="#FFFFFF" width="100%">
			<caption>Nuevo Personal</caption>
			<tr>
	  			<td>
					<input name="cmd1" type="button" value="Guardar" onClick="guardar(1)" class="boton">
					<input id="cmd1" name="cmd1" type="button" value="Buscar" onClick="buscar()" class="boton">
	  			</td>
			</tr>
  		</table>
  	
		<input type="hidden" name="tipo" value="1">
		<input type="hidden" name="cmd" value="Cancelar">
		
		<table cellpadding="0" cellspacing="0" border="0" align="CENTER" width="100%">
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
					<span class="style2">*</span><b> Primer Nombre:</b>
				</td>
				<td>
					<input type="text" name="pernombre1" size="50" maxlength="75" <c:if test="${sessionScope.nuevoUsuario.estadoper==1}">value='<c:out value="${sessionScope.nuevoUsuario.pernombre1}"/>'</c:if>></input>
				</td>
			</tr>
			<tr style="height: 5px"></tr>
			<tr>
				<td>
					<span class="style2"></span><b> Segundo Nombre:</b>
				</td>
				<td>
					<input type="text" name="pernombre2" size="50" maxlength="50" <c:if test="${sessionScope.nuevoUsuario.estadoper==1}">value='<c:out value="${sessionScope.nuevoUsuario.pernombre2}"/>'</c:if>></input>
				</td>
			</tr>
			<tr style="height: 5px"></tr>
			<tr>
				<td>
					<span class="style2">*</span><b> Primer Apellido:</b>
				</td>
				<td>
					<input type="text" name="perapellido1" size="50" maxlength="50" <c:if test="${sessionScope.nuevoUsuario.estadoper==1}">value='<c:out value="${sessionScope.nuevoUsuario.perapellido1}"/>'</c:if>></input>
				</td>
			</tr>
			<tr style="height: 5px"></tr>
			<tr>
				<td>
					<span class="style2"></span><b> Segundo Apellido:</b>
				</td>
				<td>
					<input type="text" name="perapellido2" size="50" maxlength="50" <c:if test="${sessionScope.nuevoUsuario.estadoper==1}">value='<c:out value="${sessionScope.nuevoUsuario.perapellido2}"/>'</c:if>></input>
				</td>
			</tr>
			<tr style="height: 5px"></tr>
			<tr>
				<td>
					<span class="style2">*</span><b> Tipo de Documento:</b>
				</td>
				<td colspan=3>
					<select name="pertipdocum" style='width:330px;'>
						<option value="-1">-- Seleccione uno --</option>
							<c:forEach begin="0" items="${filtroTipoDocumento}" var="fila">
								<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.nuevoUsuario.pertipdocum== fila[0]}">SELECTED</c:if>>
								<c:out value="${fila[1]}"/></option>
							</c:forEach>
					</select>
				</td>
			</tr>
			<tr style="height: 5px"></tr>
			<tr>
				<td>
					<span class="style2">*</span><b> Número de Documento:</b>
				</td>
				<td colspan=3>
					<input type="text" name="pernumdocum" maxlength="12" onKeyPress='return acepteNumeros(event)' <c:if test="${sessionScope.nuevoUsuario.estadoper==1}">value='<c:out value="${sessionScope.nuevoUsuario.pernumdocum}"/>'</c:if>></input>
				</td>
			</tr>
			<tr style="height: 5px"></tr>
			<tr>
				<td>
					<span class="style2">*</span><b> Correo Institucional:</b>
				</td>
				<td>
					<input type="text" name="percorreoinstitucional" size="100" maxlength="200" <c:if test="${sessionScope.nuevoUsuario.estadoper==1}">value='<c:out value="${sessionScope.nuevoUsuario.percorreoinstitucional}"/>'</c:if>></input>
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
		<input type="hidden" name="forma" value="frmNuevoPersonal">
	</form>
	
</font>

<script>
	<c:if test="${sessionScope.nuevoUsuario.estadoper==1}">
		inhabilitarFormulario();
	</c:if>
</script>
