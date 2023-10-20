<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@page import="siges.util.Recursos"%>
<jsp:useBean id="nuevoUsuario" class="siges.usuario.beans.Usuario" scope="session" /><jsp:setProperty name="nuevoUsuario" property="*"/>
<%pageContext.setAttribute("filtroLoc",Recursos.recurso[Recursos.LOCALIDAD]);%>
<%@include file="../parametros.jsp"%>
		<script language='javaScript'>
		<!--
				
			var nav4=window.Event ? true : false;
			
			function borrar_combo(combo){
				for(var i = combo.length - 1; i >= 0; i--) {
					if(navigator.appName == "Netscape") combo.options[i] = null;
					else combo.remove(i);
				}
				combo.options[0] = new Option("--Seleccione uno--","-1");
			}
			
			/*function cargaDepartamento(){
				borrar_combo(document.frmValNuevoUsuario.municipio);
				borrar_combo(document.frmValNuevoUsuario.nucleo);
				borrar_combo(document.frmValNuevoUsuario.institucion);
				borrar_combo(document.frmValNuevoUsuario.sede);
				borrar_combo(document.frmValNuevoUsuario.jornada);
				document.frmAux1.depar.value=document.frmValNuevoUsuario.departamento.options[document.frmValNuevoUsuario.departamento.selectedIndex].value;
				document.frmAux1.combo.value="1";
  			document.frmAux1.target="frame1";
				document.frmAux1.submit();
			}*/
			
			function cargaMunicipio(){
				//borrar_combo(document.frmValNuevoUsuario.nucleo);
				borrar_combo(document.frmValNuevoUsuario.institucion);
				borrar_combo(document.frmValNuevoUsuario.sede);
				borrar_combo(document.frmValNuevoUsuario.jornada);
				document.frmAux1.munic.value=document.frmValNuevoUsuario.municipio.options[document.frmValNuevoUsuario.municipio.selectedIndex].value;
				document.frmAux1.combo.value="3";
  			document.frmAux1.target="frame1";
				document.frmAux1.submit();
			}
			
			/*function cargaNucleo(){
				borrar_combo(document.frmValNuevoUsuario.institucion);
				borrar_combo(document.frmValNuevoUsuario.sede);
				borrar_combo(document.frmValNuevoUsuario.jornada);
				document.frmAux1.nucleo.value=document.frmValNuevoUsuario.nucleo.options[document.frmValNuevoUsuario.nucleo.selectedIndex].value;
				document.frmAux1.combo.value="3";
  			document.frmAux1.target="frame1";
				document.frmAux1.submit();
			}*/
			
			function cargaSede(){
				borrar_combo(document.frmValNuevoUsuario.sede);
				borrar_combo(document.frmValNuevoUsuario.jornada);
				document.frmAux1.inst.value=document.frmValNuevoUsuario.institucion.options[document.frmValNuevoUsuario.institucion.selectedIndex].value;
				document.frmAux1.combo.value="4";
  			document.frmAux1.target="frame1";
				document.frmAux1.submit();
			}
			
			function cargaJornada(){
				borrar_combo(document.frmValNuevoUsuario.jornada);
				document.frmAux1.inst.value=document.frmValNuevoUsuario.institucion.options[document.frmValNuevoUsuario.institucion.selectedIndex].value;
				document.frmAux1.sede.value=document.frmValNuevoUsuario.sede.options[document.frmValNuevoUsuario.sede.selectedIndex].value;
				document.frmAux1.combo.value="5";
  			document.frmAux1.target="frame1";
				document.frmAux1.submit();
			}

			function validarPerfil(){

					//document.frmValNuevoUsuario.departamento.disabled=true;
					document.frmValNuevoUsuario.municipio.disabled=true;
					//document.frmValNuevoUsuario.nucleo.disabled=true;
					document.frmValNuevoUsuario.institucion.disabled=true;
					document.frmValNuevoUsuario.sede.disabled=true;
					document.frmValNuevoUsuario.jornada.disabled=true;

				
				if(document.frmValNuevoUsuario.usuperfcodigo.value.substring(0,document.frmValNuevoUsuario.usuperfcodigo.value.indexOf('|'))=='1'){

					//document.frmValNuevoUsuario.departamento.disabled=false;
					document.frmValNuevoUsuario.municipio.disabled=true;
					//document.frmValNuevoUsuario.nucleo.disabled=true;
					document.frmValNuevoUsuario.institucion.disabled=true;
					document.frmValNuevoUsuario.sede.disabled=true;
					document.frmValNuevoUsuario.jornada.disabled=true;
				}
				else if(document.frmValNuevoUsuario.usuperfcodigo.value.substring(0,document.frmValNuevoUsuario.usuperfcodigo.value.indexOf('|'))=='2'){

					//document.frmValNuevoUsuario.departamento.disabled=false;
					document.frmValNuevoUsuario.municipio.disabled=false;
					//document.frmValNuevoUsuario.nucleo.disabled=true;
					document.frmValNuevoUsuario.institucion.disabled=true;
					document.frmValNuevoUsuario.sede.disabled=true;
					document.frmValNuevoUsuario.jornada.disabled=true;
				}
				else if(document.frmValNuevoUsuario.usuperfcodigo.value.substring(0,document.frmValNuevoUsuario.usuperfcodigo.value.indexOf('|'))=='3'){

					//document.frmValNuevoUsuario.departamento.disabled=false;
					document.frmValNuevoUsuario.municipio.disabled=false;
					//document.frmValNuevoUsuario.nucleo.disabled=false;
					document.frmValNuevoUsuario.institucion.disabled=true;
					document.frmValNuevoUsuario.sede.disabled=true;
					document.frmValNuevoUsuario.jornada.disabled=true;
				}
				else if(document.frmValNuevoUsuario.usuperfcodigo.value.substring(0,document.frmValNuevoUsuario.usuperfcodigo.value.indexOf('|'))=='4'){

					//document.frmValNuevoUsuario.departamento.disabled=false;
					document.frmValNuevoUsuario.municipio.disabled=false;
					//document.frmValNuevoUsuario.nucleo.disabled=false;
					document.frmValNuevoUsuario.institucion.disabled=false;
					document.frmValNuevoUsuario.sede.disabled=true;
					document.frmValNuevoUsuario.jornada.disabled=true;
				}
				else if(document.frmValNuevoUsuario.usuperfcodigo.value.substring(0,document.frmValNuevoUsuario.usuperfcodigo.value.indexOf('|'))=='6'){

					//document.frmValNuevoUsuario.departamento.disabled=false;
					document.frmValNuevoUsuario.municipio.disabled=false;
					//document.frmValNuevoUsuario.nucleo.disabled=false;
					document.frmValNuevoUsuario.institucion.disabled=false;
					document.frmValNuevoUsuario.sede.disabled=false;
					document.frmValNuevoUsuario.jornada.disabled=false;
				}
			}

			function acepteNumeros(eve){
				var key=nav4?eve.which :eve.keyCode;
				//return(key<=13 || (key>=48 && key<=57));
				return((key>=48 && key<=57));
			}
			
			function hacerValidaciones_frmValNuevoUsuario(frmValNuevoUsario){
					//validarLista(frmValNuevoUsuario.usuperfcodigo,"- Perfil de Usuario",1)
					/*if(frmValNuevoUsuario.departamento.disabled==false)
						validarLista(frmValNuevoUsuario.departamento,"- Departamento",1)*/
					if(frmValNuevoUsuario.municipio.disabled==false)
						validarLista(frmValNuevoUsuario.municipio,"- Localidad",1)
					/*if(frmValNuevoUsuario.nucleo.disabled==false)
						validarLista(frmValNuevoUsuario.nucleo,"- Nucleo",1)*/
					if(frmValNuevoUsuario.institucion.disabled==false)
						validarLista(frmValNuevoUsuario.institucion,"- Colegio",1)
					if(frmValNuevoUsuario.sede.disabled==false)
						validarLista(frmValNuevoUsuario.sede,"- Sede",1)
					if(frmValNuevoUsuario.jornada.disabled==false)
						validarLista(frmValNuevoUsuario.jornada,"- Jornada",1)
			}
			
			function guardar(tipo){
				if(validarForma(document.frmValNuevoUsuario)){
					document.frmValNuevoUsuario.tipo.value=tipo;
					document.frmValNuevoUsuario.cmd.value="Buscar";
					document.frmValNuevoUsuario.submit();
				}	
			}
			
			function nuevo(){
				document.frmValNuevoUsuario.tipo.value='5';
				document.frmValNuevoUsuario.ext2.value='';
				document.frmValNuevoUsuario.cmd.value='NuevoPersonal';
				document.frmValNuevoUsuario.action='<c:url value="/usuario/ControllerUsuarioNuevoEdit.do"/>';
				document.frmValNuevoUsuario.submit();
			}
			
			function cancelar(){
					location.href='<c:url value="/bienvenida.jsp"/>';
			}
		-->
		</script>
		
<%@include file="../mensaje.jsp" %>

		<font size='1'>
			<form method="post" name="frmValNuevoUsuario"  onSubmit=" return validarForma(frmValNuevoUsuario)" action='<c:url value="/usuario/ControllerUsuarioNuevoEdit.do"/>'>
			<table border="1" align="center" bordercolor="#FFFFFF" width="100%">
			<caption>Busqueda de Usuarios</caption>
				<tr>		
			  	<td>
					<input name="cmd1" type="button" value="Buscar" onClick="guardar(6)" class="boton">
					<input name="cmd1" type="button" value="Agregar Persona" onClick="nuevo()" class="boton">
			  	</td>
				</tr>
	  	</table>
			
			<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
				<tr height="1">
						<td width="10">&nbsp;</td>
					<td rowspan="2" width="469"><img src="../etc/img/tabs/usuarios_1.gif" alt="Usuarios"  height="26" border="0"></td>
					</tr>
			</table>
			<br>
				<input type="hidden" name="tipo" value="2">
				<input type="hidden" name="cmd" value="Cancelar">
				<INPUT TYPE="hidden" NAME="ext2"  VALUE=''>
				<table border="1" align="center" width="80%" cellpadding="0" cellspacing="0" bordercolor="#FFFFFF">
					<tr>
						<td>
							<span class="style2"></span> N&uacute;mero de documento:
						</td>
						<td>
							<input type="text" name="usupernumdocum"  maxlength="12" onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.nuevoUsuario.usupernumdocum}"/>'></input>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<hr>
						</td>
					</tr>
					<tr>
						<td>
							<span class="style2"></span> Perfil:
						</td>
						<td>
							<select name="usuperfcodigo" onchange='validarPerfil()' style='width:200px;'>
								<option value="-1">--Seleccione uno--</option>
									<c:forEach begin="0" items="${requestScope.filtroPerfil}" var="fila">
										<option value="<c:out value="${fila[4]}"/>|<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.nuevoUsuario.usuperfcodigo== fila[0]}">SELECTED</c:if>>
											(<c:out value="${fila[0]}"/>)&nbsp;<c:out value="${fila[1]}"/></option>
									</c:forEach>
							</select>
						</td>
					</tr>
					
					<!--<tr>
						<td>
							<span class="style2"></span> Departamento:
						</td>
						<td>
							<select name='departamento' disabled='true' onChange='cargaDepartamento()' style='width:200px;'>
								<option value="-1">--Seleccione uno--</option>
									<c:forEach begin="0" items="${filtroDepartamento0}" var="fila">
										<option value="<c:out value="${fila[0]}"/>">
											<c:out value="${fila[1]}"/></option>
									</c:forEach>
							</select>
						</td>
					</tr>-->
					<tr>
						<td>
								<span class="style2"></span> Localidad:
						</td>
						<td>
							<select name='municipio' disabled='true' onChange='cargaMunicipio()' style='width:200px;'>
								<option value="-1">--Seleccione uno--</option>
								<c:forEach begin="0" items="${filtroLoc}" var="fila">
									<option value="<c:out value="${fila[0]}"/>">
										<c:out value="${fila[1]}"/></option>
								</c:forEach>
							</select>
						</td>
					</tr>
<!--					<tr>
						<td>
							<span class="style2"></span> N&uacute;cleo:
						</td>
						<td>
							<select name='nucleo' disabled='true' onChange='cargaNucleo()' style='width:200px;'>
								<option value="-1">--Seleccione uno--</option>
							</select>
						</td>
					</tr>-->
					<tr>
						<td>
							<span class="style2"></span> Colegio:
						</td>
						<td>
							<select name='institucion' disabled='true' onChange='cargaSede()' style='width:300px;'>
								<option value="-1">--Seleccione uno--</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							<span class="style2"></span> Sede:
						</td>
						<td>
							<select name='sede' disabled='true' onChange='cargaJornada()' style='width:300px;'>
								<option value="-1">--Seleccione uno--</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							<span class="style2"></span> Jornada:
						</td>
						<td>
							<select name='jornada' disabled='true' style='width:200px;'>
								<option value="-1">--Seleccione uno--</option>
							</select>
						</td>
					</tr>
					<tr>
						<td style="display:none">
						<iframe name="frame1" id="frame1"></iframe>
						</td>
					</tr>
				</table>
			</form>
		<form method="post" name="frmAux1" action="<c:url value="/usuario/ControllerUsuarioFiltroEdit.do"/>">
			<input type="hidden" name="combo" value="">
			<input type="hidden" name="depar" value="">
			<input type="hidden" name="munic" value="">
			<input type="hidden" name="nucleo" value="">
			<input type="hidden" name="inst" value="">
			<input type="hidden" name="sede" value="">
			<input type="hidden" name="jornada" value="">
			<input type="hidden" name="ext" value="1">
			<input type="hidden" name="forma" value="frmValNuevoUsuario">
		</form>
	</font>
<script>
		//borrar_combo(document.frmValNuevoUsuario.departamento);
		//borrar_combo(document.frmValNuevoUsuario.municipio);
		//borrar_combo(document.frmValNuevoUsuario.nucleo);
		borrar_combo(document.frmValNuevoUsuario.institucion);
		borrar_combo(document.frmValNuevoUsuario.sede);
		borrar_combo(document.frmValNuevoUsuario.jornada);
</script>