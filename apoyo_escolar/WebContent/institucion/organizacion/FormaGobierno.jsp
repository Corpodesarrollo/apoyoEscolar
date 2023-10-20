<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ page import="siges.util.Recursos" %>
<jsp:useBean id="consejoDirectivoVO" class="siges.institucion.organizacion.beans.ConsejoDirectivoVO" scope="session" />
<jsp:useBean id="organizacionParams" class="siges.institucion.organizacion.beans.Params" scope="page"/>
<%pageContext.setAttribute("filtroDocumento",Recursos.recurso[Recursos.TIPODOCUMENTO]);
pageContext.setAttribute("filtroTipoCargo",Recursos.recurso[Recursos.TIPOCARGO]);%>
<%@include file="../../parametros.jsp"%>
<html>
<head><title>Registro/Edición de consejo directivo</title></head>
		<c:if test="${sessionScope.NivelPermiso==1}"><c:set var="permisoBoton" value="none"/></c:if>
		<script languaje='javaScript'>			
			<!--
		  var nav4=window.Event ? true : false;
		  function acepteNumeros(eve){
			var key=nav4?eve.which :eve.keyCode;
			return(key<=13 || (key>=48 && key<=57));
		  }
			
			function cancelar(){
				parent.close();
			}
			
  		function hacerValidaciones_frmNuevo(forma){
				validarLista(forma.conDirCargo, "- Cargo",1)
		    validarCampo(forma.conDirNombre, "- Nombre", 1, 100)
		    validarCampo(forma.conDirApellido, "- Apellido", 1, 100)
				validarLista(forma.conDirGenero, "- Género", 1)
				validarLista(forma.conDirTipoDoc, "- Tipo de documento",1)
		    validarCampo(forma.conDirNumDoc, "- Numero de documento", 1, 12)
		    validarCorreoOpcional(forma.conDirCorreo, "- Correo electrónico", 1)
			}
			
			function inhabilitarFormulario(){
				for(var i=0;i<document.frmNuevo.elements.length;i++){
					if(document.frmNuevo.elements[i].type != "hidden" && document.frmNuevo.elements[i].type != "button" && document.frmNuevo.elements[i].type != "submit")
					document.frmNuevo.elements[i].disabled=true;
				}
			}
			
			function guardar(tipo){
				if(validarForma(document.frmNuevo)){
					document.frmNuevo.target='centro';
					document.frmNuevo.tipo2.value=tipo;
					document.frmNuevo.cmd.value="Guardar";
					document.frmNuevo.submit();
					parent.close();
				}
			}
			
			function Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre){
				this.id_Hijos= id_Hijos; this.Hijos= Hijos; this.Sel_Hijos= Sel_Hijos; this.id_Padre=id_Padre;
			}
			function borrar_combo(combo){
				for(var i = combo.length - 1; i >= 0; i--) {
					if(navigator.appName == "Netscape") combo.options[i] = null;
					else combo.remove(i);
				}
				combo.options[0] = new Option("-- Seleccione uno --","-1");
			}
			
			function filtro(combo_padre,combo_hijo){
				borrar_combo(combo_hijo);
					<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF}">
						var Padres = new Array();
						<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila" varStatus="st">
							id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
							<c:forEach begin="0" items="${requestScope.filtroJornadaF}" var="fila2">
								<c:if test="${fila[0]==fila2[2]}">Sel_Hijos[k] = '<c:if test="${sessionScope.consejoDirectivoVO.conDirJornada==fila2[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k] = '<c:out value="${fila2[2]}"/>'; k++;</c:if>
							</c:forEach>
						Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
						var niv=combo_padre.options[combo_padre.selectedIndex].value;
						var val_padre = -1;
						for(var k=0;k<Padres.length;k++){
							if(Padres[k].id_Padre[0]==niv) val_padre=k;							
						}
						if(val_padre!=-1){ var no_hijos = Padres[val_padre].Hijos.length;
							for(i=0; i < no_hijos; i++){
								if(Padres[val_padre].Sel_Hijos[i] == "SELECTED"){
									combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED"); combo_hijo.selectedIndex = i+1;
								}else
									combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
							}
						}
					</c:if>
			}
	//	-->
	</script>
	<%@include file="../../mensaje.jsp"%>
	<font size="1">
	<form method="post" name="frmNuevo" onSubmit=" return validarForma(frmNuevo)"  action='<c:url value="/institucion/organizacion/OrganizacionGuardar.jsp"/>'>
		<input type="hidden" name="tipo2"	value='<c:out value="${pageScope.organizacionParams.FICHA_GOB2}"/>'> 
		<input type="hidden" name="cmd" value="Cancelar">
		<input type="hidden" name="ext2" value='<c:out value="${sessionScope.subframe2}"/>'>
		<input type="hidden" name="conDirInst" value='<c:out value="${sessionScope.login.instId}"/>'>
		<table border="0" align="center" bordercolor="#FFFFFF" width="100%">
		<caption>AGREGAR / EDITAR INTEGRANTE DEL CONSEJO DIRECTIVO</caption>
			<tr>		
			 <td width="45%">
	       <input name="cmd1" type="button" class='boton' value="Guardar" onClick="guardar(document.frmNuevo.tipo2.value)"  style='display:<c:out value="${permisoBoton}"/>'>
	       <input name="cmd1" type="button" class='boton' value="Cancelar" onClick="cancelar()">
			 </td>
			</tr>		
		</table>
<!--//////////////////-->		
								<TABLE width="100%" cellpadding="3" cellSpacing="0">
								    <tr>
								    	<td width='20%'> Sede:</td>
										<td colspan="3">
											<select name="conDirSede" style='width:400px;' onChange='filtro(document.frmNuevo.conDirSede, document.frmNuevo.conDirJornada)' <c:if test="${sessionScope.nuevoGobierno.estado=='1'}">disabled</c:if>>
												<option value="-1">--seleccione una--</option>
													<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila">
														<option value='<c:out value="${fila[0]}"/>'  <c:if test="${sessionScope.consejoDirectivoVO.conDirSede== fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option>
													</c:forEach>
											</select>
										</td>
									</tr>
								    <tr>
								    	<td width='20%'> Jornada:</td>
										<td>
											<select name="conDirJornada" style='width:100px;' <c:if test="${sessionScope.nuevoGobierno.estado=='1'}">disabled</c:if>>
												<option value="-1">--seleccione una--</option>
											</select>
										</td>
										<td><span class="style2">*</span> Cargo:</td>
										<td>
										<select name="conDirCargo" style='width:200px;'>
                      <option value="-1">--seleccione uno--</option>
                      <c:forEach begin="0" items="${filtroTipoCargo}" var="fila">
                      <option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.consejoDirectivoVO.conDirCargo== fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option> </c:forEach>
                     </select>
										 </td>
									</tr>
									<tr>
									  <td><span class="style2">*</span> Nombre:</td>
									  <td><input type="text" name="conDirNombre"  maxlength="100" size="35" value='<c:out value="${sessionScope.consejoDirectivoVO.conDirNombre}"/>'></td>
									  <td><span class="style2">*</span> Apellido:</td>
									  <td><input type="text" name="conDirApellido"  maxlength="100" size="35" value='<c:out value="${sessionScope.consejoDirectivoVO.conDirApellido}"/>'></td>
									</tr>
									<tr>
									  <td><span class="style2">*</span> Género:</td>
										<td>
										<select name="conDirGenero" style='width:40px;' >
                      <option value="-1">--</option>
                      <option value="1" <c:if test="${sessionScope.consejoDirectivoVO.conDirGenero==1}">SELECTED</c:if>>M</option>
                      <option value="2" <c:if test="${sessionScope.consejoDirectivoVO.conDirGenero==2}">SELECTED</c:if>>F</option>
                     </select>
										</td> 
									  <td><span class="style2">*</span>Tipo de documento:</td>
										<td>
											<select name="conDirTipoDoc" style='width:150px;'>
                        <option value="-1">--seleccione uno--</option>
                        <c:forEach begin="0" items="${filtroDocumento}" var="fila">
                        <option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.consejoDirectivoVO.conDirTipoDoc==fila[0]}">SELECTED</c:if>> <c:out value="${fila[1]}"/></option> </c:forEach>
                      </select>
										</td>
									</tr>
									<tr>
									  <td><span class="style2">*</span>N&uacute;mero de documento:</td>
										<td><input type="text" name="conDirNumDoc"  maxlength="12" size="20" value='<c:out value="${sessionScope.consejoDirectivoVO.conDirNumDoc}"/>'></td>
									  <td>Tel&eacute;fono de contacto:</td>
										<td><input type="text" name="conDirTelefono"  maxlength="20" size="20" value='<c:out value="${sessionScope.consejoDirectivoVO.conDirTelefono}"/>'></td>
									</tr>
									<tr>
									  <td>Correo electrónico:</td>
										<td colspan="3"><input type="text" name="conDirCorreo"  maxlength="50" size="40" value='<c:out value="${sessionScope.consejoDirectivoVO.conDirCorreo}"/>'></td>
									</tr>
							  </TABLE>
					</form>
<script><c:if test="${sessionScope.NivelPermiso==1}">inhabilitarFormulario();</c:if>
document.frmNuevo.conDirSede.onchange();
</script>