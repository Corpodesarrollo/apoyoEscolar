<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %><%@ page import="siges.util.Recursos" %>
<jsp:useBean id="nuevoBasica" class="siges.estudiante.beans.Basica" scope="session"/><jsp:setProperty name="nuevoBasica" property="*"/>
<jsp:useBean id="nuevoFamiliar" class="siges.estudiante.beans.Familiar" scope="session"/><jsp:setProperty name="nuevoFamiliar" property="*"/>
<jsp:useBean id="nuevoSalud" class="siges.estudiante.beans.Salud" scope="session"/><jsp:setProperty name="nuevoSalud" property="*"/>
<jsp:useBean id="nuevoConvivencia" class="siges.estudiante.beans.Convivencia" scope="session"/><jsp:setProperty name="nuevoConvivencia" property="*"/>
<jsp:useBean id="academicaVO" class="siges.estudiante.beans.AcademicaVO" scope="session"/>
<jsp:useBean id="asistenciaVO" class="siges.estudiante.beans.AsistenciaVO" scope="session"/><jsp:setProperty name="asistenciaVO" property="*"/>
<jsp:useBean id="nuevoAtencion" class="siges.estudiante.beans.AtencionEspecial" scope="session"/><jsp:setProperty name="nuevoAtencion" property="*"/>
<%pageContext.setAttribute("filtroGrado",Recursos.recurso[Recursos.GRADO]);%>
<%@include file="../parametros.jsp"%>
<c:set var="tip" value="5" scope="page"/>
		<c:if test="${sessionScope.NivelPermiso==1}"><c:set var="permisoBoton" value="none"/></c:if>
		<script languaje='javaScript'>			
		<!--
		function cancelar(){
			if (confirm('¿DESEA CANCELAR LA INSERCIÓN DE UN NUEVO REGISTRO?')){
 				document.frmNuevo.cmd.value="Cancelar";
                document.frmNuevo.submit();
			}
		}
		
			function inhabilitarFormulario(){
				for(var i=0;i<document.frmNuevo.elements.length;i++){
					if(document.frmNuevo.elements[i].type != "hidden" && document.frmNuevo.elements[i].type != "button" && document.frmNuevo.elements[i].type != "submit")
					document.frmNuevo.elements[i].disabled=true;
				}	
			}
			
		function hacerValidaciones_frmNuevo(forma){
			validarLista(forma.acaGrado, "- Grado",1)
			validarLista(forma.acaAnho, "- Año de culminación",1)
			validarLista(forma.acaGradoEstado, "- Estado",1)
			validarCampo(forma.acaInst, "- Colegio",1,60)
		}
		
		function lanzar(tipo){
				document.frmNuevo.tipo.value=tipo;
				document.frmNuevo.action='<c:url value="/estudiante/ControllerNuevoEdit.do"/>';
				document.frmNuevo.submit();
			}
			
			function guardar(tipo){
				if(validarForma(document.frmNuevo)){
					document.frmNuevo.tipo.value=tipo;
					document.frmNuevo.cmd.value="Guardar";
					document.frmNuevo.submit();
				}
			}
			
			function editar(tipo,nombre){	
				document.frmConvivencia.tipo.value=tipo;
				document.frmConvivencia.cmd.value=nombre;
				if(document.frmConvivencia.cmd.value=='Eliminar'){
					if (isChecked(document.frmConvivencia.r)){
						if(!confirm("Realmente desea eliminarlo?"))
							return false;
					}
				}
				return true;
			}
			
			function hacerValidaciones_frmConvivencia(forma){
				if(forma.cmd.value!='Nuevo')
					validarSeleccion(forma.r, "- Debe seleccionar un item")
			}
			
			function ponerSeleccionado(){
				if(document.frm){
					if(document.frm.id){
						if(document.frm.id.length){
							for(var i=0;i<document.frm.id.length;i++){
								if(document.frm.id[i].value==document.frmNuevo.ELHPSELECCIONADO.value){
									document.frm.id[i].checked=true;
									document.frm.id[i].focus();
									break;
								}
							}
						}else{
							if(document.frm.id.value==document.frmNuevo.ELHPSELECCIONADO.value){
								document.frm.id.checked=true;;
								document.frm.id.focus();
							}
						}
					}
				}
			}
			-->
		</script>
	<%@include file="../mensaje.jsp"%>
	<font size="1">
	<c:if test="${!empty requestScope.filtroAcademica}">
 <center>
 <div style="width:98%;height:100px;overflow:auto;">
	<form method="post" name="frmConvivencia"  onSubmit=" return validarForma(frmConvivencia)" action='<c:url value="/estudiante/ControllerNuevoEdit.do"/>'>
	<input type="hidden" name="tipo" value='<c:out value="${tip}"/>'>
	<input type="hidden" name="cmd" value="">
	<INPUT TYPE="hidden" NAME="height" VALUE='130'>
	<input type="hidden" name="ext2" value='<c:out value="${sessionScope.subframe}"/>'>
		<table border="0" align="center" width="100%" cellpadding="0" cellspacing="0" bordercolor="silver">
			<tr>
				<td colspan='4' align='left'>
					<input class='boton' name="cmd1" type="submit" value="<c:if test="${empty sessionScope.ModoConsulta}">Editar</c:if><c:if test="${!empty sessionScope.ModoConsulta}">Ver</c:if>" onClick="return editar(document.frmNuevo.tipo.value,'Editar')">
					<c:if test="${empty sessionScope.ModoConsulta}"><input class='boton' name="cmd1" type="submit" value="Nuevo" onClick="return editar(document.frmNuevo.tipo.value,'Nuevo')" style='display:<c:out value="${permisoBoton}"/>'></c:if>
					<c:if test="${empty sessionScope.ModoConsulta}"><input class='boton' name="cmd1" type="submit" value="Eliminar" onClick="return editar(document.frmNuevo.tipo.value,'Eliminar')" style='display:<c:out value="${permisoBoton}"/>'></c:if>
				</td>
			</tr>
		</table>	
		<table border="1" align="center" width="100%" cellpadding="0" cellspacing="0" bordercolor="silver">
			<tr bgcolor="#CCCCCC"> 
				<td width='1%'></td>				
				<th>Colegio</th>				
				<th>Grado</b></th>				
				<th>Año</b></th>
			</tr>
			<c:forEach begin="0" items="${requestScope.filtroAcademica}" var="fila">
			<tr>				
				<td width='1%'><input type='radio' name='r' value='<c:out value="${fila[0]}"/>'></td>
				<td><c:out value="${fila[1]}"/></td>
				<td><c:out value="${fila[2]}"/></td>
				<td><c:out value="${fila[3]}"/></td>
			</tr>
			</c:forEach>
		</table>
	</center>
	</form>
	</div>
	</c:if>
		<form method="post" name="frmNuevo" onSubmit=" return validarForma(frmNuevo)"  action='<c:url value="/estudiante/NuevoGuardar.jsp"/>'>
		<table border="0" align="center" bordercolor="#FFFFFF" width="100%">
			<tr>
			  <td width="45%" bgcolor="#FFFFFF">
	          <c:if test="${empty sessionScope.ModoConsulta}"><input class='boton' name="cmd1" type="button" value="Guardar" onClick="guardar(document.frmNuevo.tipo.value)" style='display:<c:out value="${permisoBoton}"/>'></c:if>
	          <c:if test="${sessionScope.ModoConsulta==1}"><a href="javascript:print()"><input  class='boton' name="impr1" type="button" value="Imprimir" style="margin-top:2px;margin-bottom:2px;" onClick="javascript:window.print(); return false;" /></a></c:if>
			</tr>		
		</table>
<!--//////////////////-->		
	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="ELHPSELECCIONADO" value="<c:out value="${sessionScope.nuevoBasica2.estcodigo}"/>">
	<input type="hidden" name="tipo" value='<c:out value="${tip}"/>'>
	<input type="hidden" name="cmd" value="Cancelar">
	<INPUT TYPE="hidden" NAME="height" VALUE='130'>
	<INPUT TYPE="hidden" NAME="acaId" VALUE='<c:out value="${sessionScope.academicaVO.acaId}"/>'>
	<input type="hidden" name="ext2" value='<c:out value="${sessionScope.subframe}"/>'>
  <table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
    <tr height="1">
			<td width="10" bgcolor="#FFFFFF">&nbsp;</td>							
      <td rowspan="2" width="504" bgcolor="#FFFFFF">
			<a href="javaScript:lanzar(1)"><img src="../etc/img/tabs/datos_basicos_0.gif" alt="Datos Básicos"  height="26" border="0"></a>
			<c:if test="${empty sessionScope.ModoConsulta}"><a href="javaScript:lanzar(2)"><img src="../etc/img/tabs/inf_familiar_0.gif" alt="Ficha Familiar"  height="26" border="0"></a></c:if>
			<a href="javaScript:lanzar(3)"><img src="../etc/img/tabs/inf_salud_0.gif" alt="Salud"  height="26" border="0"></a>
			<a href="javaScript:lanzar(4)"><img src="../etc/img/tabs/inf_convivencia_0.gif" alt="Convivencia"  height="26" border="0"></a>
			<img src="../etc/img/tabs/inf_academica_1.gif" alt="Academica"  height="26" border="0">
			<c:if test="${empty sessionScope.ModoConsulta}"><a href="javaScript:lanzar(7)"><img src="../etc/img/tabs/atencion_especial_0.gif" alt="Atención Especial"  height="26" border="0"></a></c:if>
			<c:if test="${empty sessionScope.ModoConsulta}"><a href="javaScript:lanzar(8)"><img src="../etc/img/tabs/fotografia_0.gif" alt="Subir Foto Estudiante"  height="26" border="0"></a></c:if></td>
			<td align='CENTER'><b><c:out value="${sessionScope.nuevoBasica.estnombre1}"/> <c:out value="${sessionScope.nuevoBasica.estnombre2}"/> <c:out value="${sessionScope.nuevoBasica.estapellido1}"/></b></td>
			</tr>
		</table>
		<!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
								<TABLE width="100%" cellpadding="3" cellSpacing="0">
								    <tr>
										<td><span class="style2">*</span> Grado:</td>
										<td>
											<select name="acaGrado" style='width:250px;' <c:if test="${!empty sessionScope.ModoConsulta}">disabled</c:if>>
												<option value="-1">--seleccione una--</option>
													<c:forEach begin="0" items="${filtroGrado}" var="fila">
														<option value="<c:out value="${fila[0]}"/>"  <c:if test="${sessionScope.academicaVO.acaGrado==fila[0]}">SELECTED</c:if>>
															<c:out value="${fila[1]}"/></option>
													</c:forEach>													
											</select>
										</td>
									</tr>
									<tr>
										<td><span class="style2">*</span> Colegio:</td>
										<td><input type="text" name="acaInst"  maxlength="60" size="50" value='<c:out value="${sessionScope.academicaVO.acaInst}"/>' <c:if test="${!empty sessionScope.ModoConsulta}">disabled</c:if>></td>
										<td><span class="style2">*</span> Año de culminación:</td>
										<td>
											<select name="acaAnho"  style='width:70px;' <c:if test="${!empty sessionScope.ModoConsulta}">disabled</c:if>>
												<option value="-1">--seleccione una--</option>
													<c:forEach begin="1980" end="2006" var="fila">
														<option value='<c:out value="${fila}"/>'  <c:if test="${sessionScope.academicaVO.acaAnho==fila}">SELECTED</c:if>>
															<c:out value="${fila}"/></option>
													</c:forEach>													
											</select>
										</td>
									</tr>
									<tr>
										<td>Título obtenido:</td>
										<td><input type="text" name="acaTitulo"  maxlength="60" size="50" value='<c:out value="${sessionScope.academicaVO.acaTitulo}"/>' <c:if test="${!empty sessionScope.ModoConsulta}">disabled</c:if>></td>
									  <td><span class="style2">*</span> Estado actual:</td>
										<td>
											<select name="acaGradoEstado"  style='width:70px;' <c:if test="${!empty sessionScope.ModoConsulta}">disabled</c:if>>
												<option value="-1">--seleccione una--</option>
												<option value="1" <c:if test="${sessionScope.academicaVO.acaGradoEstado==1}">SELECTED</c:if>>Terminado</option>
												<option value="2" <c:if test="${sessionScope.academicaVO.acaGradoEstado==2}">SELECTED</c:if>>En curso</option>
												<option value="3" <c:if test="${sessionScope.academicaVO.acaGradoEstado==3}">SELECTED</c:if>>Suspendido</option>
											</select>
										</td>
									</tr>
							  </TABLE>
	</form>
<script>
<c:if test="${sessionScope.NivelPermiso==1}">inhabilitarFormulario();</c:if>
</script>