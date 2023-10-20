<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@page import="siges.util.Recursos"%>
<jsp:useBean id="filtroG" class="siges.reporteLider.beans.FiltroBeanReporteGobierno" scope="session" /><jsp:setProperty name="filtroG" property="*"/>
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
				combo.options[0] = new Option("--Seleccione uno--","-999");
			}
		
			/*function cargaDepartamento(){
				borrar_combo(document.frmFiltro.localidad);
				borrar_combo(document.frmFiltro.nucleo);
				borrar_combo(document.frmFiltro.institucion);
				borrar_combo(document.frmFiltro.sede);
				borrar_combo(document.frmFiltro.jornada);
				document.frmAux.combo.value="1";
	  			document.frmAux.target="frame";
				document.frmAux.submit();
			}*/
			
			function cargaMunicipio(){
				//borrar_combo(document.frmFiltro.nucleo);
				borrar_combo(document.frmFiltro.institucion);
				borrar_combo(document.frmFiltro.sede);
				borrar_combo(document.frmFiltro.jornada);
				document.frmAux.munic.value=document.frmFiltro.localidad.options[document.frmFiltro.localidad.selectedIndex].value;
				document.frmAux.combo.value="3";
  			document.frmAux.target="frame";
				document.frmAux.submit();
			}
			
			/*function cargaNucleo(){
				borrar_combo(document.frmFiltro.institucion);
				borrar_combo(document.frmFiltro.sede);
				borrar_combo(document.frmFiltro.jornada);
				document.frmAux.nucleo.value=document.frmFiltro.nucleo.options[document.frmFiltro.nucleo.selectedIndex].value;
				document.frmAux.combo.value="3";
  			document.frmAux.target="frame";
				document.frmAux.submit();
			}*/
			
			function cargaSede(){
				borrar_combo(document.frmFiltro.sede);
				borrar_combo(document.frmFiltro.jornada);
				document.frmAux.inst.value=document.frmFiltro.institucion.options[document.frmFiltro.institucion.selectedIndex].value;
				document.frmAux.combo.value="4";
  			document.frmAux.target="frame";
				document.frmAux.submit();
			}
			
			function cargaJornada(){
				borrar_combo(document.frmFiltro.jornada);
				document.frmAux.inst.value=document.frmFiltro.institucion.options[document.frmFiltro.institucion.selectedIndex].value;
				document.frmAux.sede.value=document.frmFiltro.sede.options[document.frmFiltro.sede.selectedIndex].value;
				document.frmAux.combo.value="5";
  			document.frmAux.target="frame";
				document.frmAux.submit();
			}

			function validarPerfil(){

				//document.frmFiltro.departamento.disabled=true;
				document.frmFiltro.localidad.disabled=true;
				//document.frmFiltro.nucleo.disabled=true;
				document.frmFiltro.institucion.disabled=true;
				document.frmFiltro.sede.disabled=true;
				document.frmFiltro.jornada.disabled=true;
			
				if(document.frmFiltro.usuperfcodigo.value.substring(0,document.frmFiltro.usuperfcodigo.value.indexOf('|'))=='1'){

					//document.frmFiltro.departamento.disabled=false;
					document.frmFiltro.localidad.disabled=true;
					//document.frmFiltro.nucleo.disabled=true;
					document.frmFiltro.institucion.disabled=true;
					document.frmFiltro.sede.disabled=true;
					document.frmFiltro.jornada.disabled=true;
				}
				else if(document.frmFiltro.usuperfcodigo.value.substring(0,document.frmFiltro.usuperfcodigo.value.indexOf('|'))=='2'){

					//document.frmFiltro.departamento.disabled=false;
					document.frmFiltro.localidad.disabled=false;
					//document.frmFiltro.nucleo.disabled=true;
					document.frmFiltro.institucion.disabled=true;
					document.frmFiltro.sede.disabled=true;
					document.frmFiltro.jornada.disabled=true;
				}
				else if(document.frmFiltro.usuperfcodigo.value.substring(0,document.frmFiltro.usuperfcodigo.value.indexOf('|'))=='3'){

					//document.frmFiltro.departamento.disabled=false;
					document.frmFiltro.localidad.disabled=false;
					//document.frmFiltro.nucleo.disabled=false;
					document.frmFiltro.institucion.disabled=true;
					document.frmFiltro.sede.disabled=true;
					document.frmFiltro.jornada.disabled=true;
				}
				else if(document.frmFiltro.usuperfcodigo.value.substring(0,document.frmFiltro.usuperfcodigo.value.indexOf('|'))=='4'){

					//document.frmFiltro.departamento.disabled=false;
					document.frmFiltro.localidad.disabled=false;
					//document.frmFiltro.nucleo.disabled=false;
					document.frmFiltro.institucion.disabled=false;
					document.frmFiltro.sede.disabled=true;
					document.frmFiltro.jornada.disabled=true;
				}
				else if(document.frmFiltro.usuperfcodigo.value.substring(0,document.frmFiltro.usuperfcodigo.value.indexOf('|'))=='6'){

					//document.frmFiltro.departamento.disabled=false;
					document.frmFiltro.localidad.disabled=false;
					//document.frmFiltro.nucleo.disabled=false;
					document.frmFiltro.institucion.disabled=false;
					document.frmFiltro.sede.disabled=false;
					document.frmFiltro.jornada.disabled=false;
				}
			}

			function acepteNumeros(eve){
				var key=nav4?eve.which :eve.keyCode;
				return(key<=13 || (key>=48 && key<=57));
			}
			
			function hacerValidaciones_frmFiltro(frmFiltro){
					validarLista(frmFiltro.localidad,"- Localidad",1);
					validarLista(frmFiltro.institucion,"- Colegio",1);
			}
			
			function lanzar(tipo){	
				document.frmFiltro.tipo.value=tipo;
				document.frmFiltro.action="<c:url value="/reporteLider/ControllerReporteLiderFiltroEdit.do"/>";
				document.frmFiltro.target="";
				document.frmFiltro.submit();
			}
			
			function guardar(tipo){
				if(validarForma(document.frmFiltro)){
					document.frmFiltro.tipo.value=tipo;
					document.frmFiltro.cmd.value="GenerarReporte";
					document.frmFiltro.action='<c:url value="/reporteLider/filtroGuardarGobierno.jsp"/>';
					document.frmFiltro.submit();
				}	
			}
			
			function ayuda(){

			}

			function cancelar(){
				if (confirm('¿Desea cancelar la inserción de la información del usuario?')){
					location.href='<c:url value="/bienvenida.jsp"/>';
				}		
			}
			
			function inhabilitarFormulario(){
//				document.frmFiltro.usupernumdocum.readOnly=true;
			}
			
			function buscar(){
				document.frmFiltro.tipo.value='0';
				document.frmFiltro.cmd.value='buscar';
				document.frmFiltro.action='<c:url value="/usuario/ControllerUsuarioNuevoEdit.do"/>';
				document.frmFiltro.submit();
			}
//-->
		</script>
<%@include file="../mensaje.jsp" %>
		<font size='1'>
			<form method="post" name="frmFiltro"  onSubmit=" return validarForma(frmFiltro)" action='<c:url value="/reporteLider/filtroGuardarGobierno.jsp"/>'>
			<table border="1" align="center" bordercolor="#FFFFFF" width="100%">
			<caption>Filtro de Reporte</caption>
				<tr>		
			  	<td>
					<input name="cmd1" type="button" value="Generar" onClick="guardar(1)" class="boton">
			  	</td>   	
				</tr>	
	  	</table>
	  	
			<input type="hidden" name="tipo" value="2">
			<input type="hidden" name="cmd" value="Cancelar">
			
			<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
				<tr height="1">
					<td rowspan="2" width="420"><a href="javaScript:lanzar(1)"><img src="../etc/img/tabs/lideres_0.gif" width="84"  height="26" border="0"></a><img src="../etc/img/tabs/gobierno_escolar_1.gif" width="84"  height="26" border="0"><a href="javaScript:lanzar(3)"><img src="../etc/img/tabs/consolidado_lider_0.gif" width="84"  height="26" border="0"></a><a href="javaScript:lanzar(4)"><img src="../etc/img/tabs/consolidado_gobierno_0.gif" width="84"  height="26" border="0"></a></td>
				</tr>
			</table>
			<br>
						<table border="1" align="center" width="80%" cellpadding="0" cellspacing="0" bordercolor="#FFFFFF">
							<tr>
								<td>
									<span class="style2">*</span><b> Localidad:</b>
								</td>
								<td>
									<select name='localidad' onChange='cargaMunicipio()' style='width:300px;'>
										<option value="-999">--seleccione uno--</option>
										<c:forEach begin="0" items="${filtroLoc}" var="fila">
											<option value="<c:out value="${fila[0]}"/>" <c:if test="${requestScope._editar[0][1]==fila[0]}">selected</c:if>>
											<c:out value="${fila[1]}"/></option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td>
									<span class="style2">*</span><b> Colegio:</b>
								</td>
								<td>
									<select name='institucion' onChange='cargaSede()' style='width:300px;'>
										<option value="-999">--seleccione uno--</option>
										<c:if test="${sessionScope.filtroG.estadousu==1}">
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
									<b> Sede:</b>
								</td>
								<td>
									<select name='sede' onChange='cargaJornada()' style='width:300px;'>
										<option value="-999">--seleccione uno--</option>
										<c:if test="${sessionScope.filtroG.estadousu==1}">
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
									<b> Jornada:</b>
								</td>
								<td>
									<select name='jornada' style='width:300px;'>
										<option value="-999">--seleccione uno--</option>
										<c:if test="${sessionScope.filtroG.estadousu==1}">
											<c:forEach begin="0" items="${requestScope.coljorn}" var="fila">
												<option value="<c:out value="${fila[0]}"/>" <c:if test="${requestScope._editar[0][5]==fila[0]}">selected</c:if>>
												<c:out value="${fila[1]}"/></option>
											</c:forEach>
										</c:if>
									</select>
								</td>
							</tr>

							<tr>
								<td>
									<b> Cargo:</b>
								</td>
								<td>
									<select name='tipoLider' style='width:300px;'>
										<option value="-999">--seleccione uno--</option>
											<c:forEach begin="0" items="${requestScope.tiposLideres}" var="fila">
												<option value="<c:out value="${fila[0]}"/>">
												<c:out value="${fila[1]}"/></option>
											</c:forEach>
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
		<form method="post" name="frmAux" action="<c:url value="/reporteLider/ControllerReporteLiderFiltroEdit.do"/>">
			<input type="hidden" name="combo" value="">
			<input type="hidden" name="depar" value="">
			<input type="hidden" name="munic" value="">
			<input type="hidden" name="nucleo" value="">
			<input type="hidden" name="inst" value="">
			<input type="hidden" name="sede" value="">
			<input type="hidden" name="jornada" value="">
			<input type="hidden" name="ext" value="1">
			<input type="hidden" name="forma" value="frmFiltro">
		</form>
	</font>
