<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %><%@ page import="siges.util.Recursos" %> 
  <jsp:useBean id="nuevoInstitucion" class="siges.institucion.beans.Institucion" scope="session"/><jsp:setProperty name="nuevoInstitucion" property="*"/>
  <jsp:useBean id="nuevoJornada" class="siges.institucion.beans.Jornada" scope="session"/><jsp:setProperty name="nuevoJornada" property="*"/>
  <jsp:useBean id="nuevoSede" class="siges.institucion.beans.Sede" scope="session"/><jsp:setProperty name="nuevoSede" property="*"/>
  <jsp:useBean id="nuevoEspacio" class="siges.institucion.beans.Espacio" scope="session"/><jsp:setProperty name="nuevoEspacio" property="*"/>
  <jsp:useBean id="nuevoNivel" class="siges.institucion.beans.Nivel" scope="session"/><jsp:setProperty name="nuevoNivel" property="*"/>  
	<jsp:useBean id="nuevoTransporte" class="siges.institucion.beans.Transporte" scope="session"/><jsp:setProperty name="nuevoTransporte" property="*"/>
  <jsp:useBean id="nuevoCafeteria" class="siges.institucion.beans.Cafeteria" scope="session"/>
<%@include file="../parametros.jsp"%>
		<c:if test="${sessionScope.NivelPermiso==1}"><c:set var="permisoBoton" value="none"/></c:if>
		<script languaje='javaScript'>			
			<!--
			var fichaBasico=1;
			var fichaSede=1;
			var fichaEspacio=1;
			var fichaTransporte=0;
			var fichaCafeteria=0;
			var fichaConflicto=0;
		  var nav4=window.Event ? true : false;
		  function acepteNumeros(eve){
				var key=nav4?eve.which :eve.keyCode;
				return(key<=13 || (key>=48 && key<=57));
		  }
			function cancelar(){
				//if (confirm('¿DESEA CANCELAR LA INSERCIÓN DE UN NUEVO REGISTRO?')){
					document.frmNuevo.cmd.value="Cancelar";
					document.frmNuevo.submit();
				//}
			}
			function hacerValidaciones_frmNuevo(forma){				
				validarLista(forma.restiposerv, "- Tipo de servicio",1)
				validarCampo(forma.resdescripcion, "- Descripción",1,200)
				validarCampo(forma.reshorario, "- Horario", 1, 20)
				validarCampo(forma.rescosto, "- Costo", 1, 5)
			}
			function inhabilitarFormulario(){
				for(var i=0;i<document.frmNuevo.elements.length;i++){
					if(document.frmNuevo.elements[i].type != "hidden" && document.frmNuevo.elements[i].type != "button" && document.frmNuevo.elements[i].type != "submit")
					document.frmNuevo.elements[i].disabled=true;
				}	
			}
			function lanzar(tipo){				
				document.frmNuevo.tipo2.value=tipo;
				document.frmNuevo.action="<c:url value="/institucion/ControllerNuevoEdit.do"/>";
				document.frmNuevo.submit();
			}
			function guardar(tipo){
				if(validarForma(document.frmNuevo)){
					document.frmNuevo.tipo2.value=tipo;
					document.frmNuevo.cmd.value="Guardar";
					document.frmNuevo.submit();
				}
			}
			function editar(tipo,nombre){	
				document.frm.tipo2.value=tipo;
				document.frm.cmd.value=nombre;
				if(document.frm.cmd.value=='Eliminar'){
					if (isChecked(document.frm.r)){
						if(!confirm("Realmente desea eliminarlo?"))
							return false;
					}
				}
				return true;	
			}
			function hacerValidaciones_frm(forma){
				if(forma.cmd.value!='Nuevo')
					validarSeleccion(forma.r, "- Debe seleccionar un item")
			}
			-->
</script>
	<%@include file="../mensaje.jsp"%>
	<font size="1">
	<c:if test="${!empty sessionScope.filtroCafeterias}">
	<center>
	<div style="width:80%;height:80px;overflow:auto;">
	<form method="post" name="frm"  onSubmit=" return validarForma(frm)" action='<c:url value="/institucion/ControllerNuevoEdit.do"/>'>
	<input type="hidden" name="tipo2" value="7">
	<input type="hidden" name="cmd" value=""><INPUT TYPE="hidden" NAME="height" VALUE='150'>
	<input type="hidden" name="ext2" value='<c:out value="${sessionScope.subframe2}"/>'>
    <input type="hidden" name="s" value="">
		<table border="0" align="center" width="100%" cellpadding="1" cellspacing="0" bordercolor="#FFFFFF">
			<tr>
				<td width='10'></td>
				<td><b>Tipo</b></td>
				<td><b>Horario</b></td>
				<td><b>Costo</b></td>
			</tr>
			<c:forEach begin="0" items="${sessionScope.filtroCafeterias}" var="fila">
			<tr>
				<td width='10%'>
					<input type='radio' name='r' value='<c:out value="${fila[0]}"/>'>
				</td>
				<td><c:out value="${fila[1]}"/></td>
				<td><c:out value="${fila[2]}"/></td>
				<td><c:out value="${fila[3]}"/></td>
			</tr>
			</c:forEach>
			<tr>
				<th colspan='4'>
					<input class='boton' name="cmd1" type="submit" value="Editar" onClick="return editar(7,'Editar')">
					<input class='boton' name="cmd1" type="submit" value="Nuevo" onClick="return editar(7,'Nuevo')"  style='display:<c:out value="${permisoBoton}"/>'>
					<input class='boton' name="cmd1" type="submit" value="Eliminar" onClick="return editar(7,'Eliminar')"  style='display:<c:out value="${permisoBoton}"/>'>
				</th>
			</tr>
		</table>
	</form>
	</div>
	</center>
	</c:if>
		<form method="post" name="frmNuevo" onSubmit=" return validarForma(frmNuevo)"  action='<c:url value="/institucion/NuevoGuardar.jsp"/>'>
		<table border="1" align="center" bordercolor="#FFFFFF" width="100%">
			<tr>		
				<td width="45%" bgcolor="#FFFFFF">
        <input class='boton' name="cmd1" type="button" value="Guardar" onClick="guardar(7)"  style='display:<c:out value="${permisoBoton}"/>'>
				 <input class='boton' name="cmd13" type="button" value="Ayuda" onClick="ayuda()">
				</td> 
			</tr>		
		</table>
<!--//////////////////-->		
	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="tipo2" value="7">
	<input type="hidden" name="cmd" value="Cancelar"><INPUT TYPE="hidden" NAME="height" VALUE='150'>
	<input type="hidden" name="ext2" value='<c:out value="${sessionScope.subframe2}"/>'>
		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1">
				<td width="10" bgcolor="#FFFFFF">&nbsp;</td>			
				<td rowspan="2" width="600" bgcolor="#FFFFFF"><script>if(fichaBasico==1)document.write('<a href="javaScript:lanzar(1)"><img src="../etc/img/tabs/datos_basicos_0.gif" alt="Inf B&aacute;sica"  height="26" border="0"></a>');if(fichaSede==1)document.write('<a href="javaScript:lanzar(2)"><img src="../etc/img/tabs/sedes_0.gif" alt="Sedes"  height="26" border="0"></a>');if(fichaEspacio==1)document.write('<a href="javaScript:lanzar(3)"><img src="../etc/img/tabs/espacios_fisicos_0.gif" alt="Espacios Fisicos"  height="26" border="0"></a>');if(fichaTransporte==1)document.write('<a href="javaScript:lanzar(6)"><img src="../etc/img/tabs/transporte_0.gif" alt="transporte"  height="26" border="0"></a>');if(fichaCafeteria==1)document.write('<img src="../etc/img/tabs/cafeteria_1.gif" alt="Cafeteria"  height="26" border="0">');if(fichaConflicto==1)document.write('<a href="javaScript:lanzar(8)"><img src="../etc/img/tabs/conflicto_escolar_0.gif" alt="Conflicto escolar" border="0"  height="26"></a>');</script></td>
			</tr>
		</table>
		<!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
								<TABLE width="100%" cellpadding="3" cellSpacing="0">
								  <tr><td><span class="style2">*</span> Tipo de servicio:</td>
										<td>
											<select name="restiposerv">
												<option value="">--seleccione uno--</option>
												<c:if test="${sessionScope.nuevoCafeteria.estado=='1'}">
													<option value="1" <c:if test="${sessionScope.nuevoCafeteria.restiposerv=='1'}">SELECTED</c:if>>Desayuno</option>
													<option value="2" <c:if test="${sessionScope.nuevoCafeteria.restiposerv=='2'}">SELECTED</c:if>>Almuerzo</option>
													<option value="3" <c:if test="${sessionScope.nuevoCafeteria.restiposerv=='3'}">SELECTED</c:if>>Refrigerio</option>
												</c:if>
												<c:if test="${sessionScope.nuevoCafeteria.estado!='1'}">
													<c:forEach begin="0" items="${sessionScope.filtroCafeterias}" var="fila"><c:if test="${fila[0]=='1'}"><c:set var="x" value="${fila[0]}" scope="page"/></c:if></c:forEach>
													<c:if test="${pageScope.x!='1'}"><option value="1" <c:if test="${sessionScope.nuevoCafeteria.restiposerv=='1'}">SELECTED</c:if>>Desayuno</option></c:if><c:remove var="pageScope.x"/>
													<c:forEach begin="0" items="${sessionScope.filtroCafeterias}" var="fila"><c:if test="${fila[0]=='2'}"><c:set var="x" value="${fila[0]}" scope="page"/></c:if></c:forEach>
													<c:if test="${pageScope.x!='2'}"><option value="2" <c:if test="${sessionScope.nuevoCafeteria.restiposerv=='2'}">SELECTED</c:if>>Almuerzo</option></c:if><c:remove var="pageScope.x"/>
													<c:forEach begin="0" items="${sessionScope.filtroCafeterias}" var="fila"><c:if test="${fila[0]=='3'}"><c:set var="x" value="${fila[0]}" scope="page"/></c:if></c:forEach>
													<c:if test="${pageScope.x!='3'}"><option value="3" <c:if test="${sessionScope.nuevoCafeteria.restiposerv=='3'}">SELECTED</c:if>>Refrigerio</option></c:if><c:remove var="pageScope.x"/>
												</c:if>
											</select>
										</td>
										<td><span class="style2">*</span> Horario:</td>
										<td><input type="text" name="reshorario"  maxlength="100" size="30" value='<c:out value="${sessionScope.nuevoCafeteria.reshorario}"/>'></td>
									</tr>
									<tr>
										<td><span class="style2">*</span> Descripción:</td>
										<td><textarea name="resdescripcion" cols="30" rows="3" wrap='virtual'><c:out value="${sessionScope.nuevoCafeteria.resdescripcion}"/></textarea></td>
										<td><span class="style2">*</span>Costo:</td>
										<td><input type="text" name="rescosto"  maxlength="100" size="30" onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.nuevoCafeteria.rescosto}"/>'></td>
									</tr>
									<tr>
										<td colspan="3"><span class="style2">*</span> Campos que son obligatorios </td>
										<td colspan="3">&nbsp;</td>
										</tr>
							  </TABLE>
</form>
<script><c:if test="${sessionScope.nuevoCafeteria.estado=='1'}">document.frmNuevo.restiposerv.disabled=true;</c:if>
<c:if test="${sessionScope.NivelPermiso==1}">inhabilitarFormulario();</c:if></script>