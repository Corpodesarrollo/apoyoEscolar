<jsp:useBean id="filtroInstitucion" class="siges.institucion.beans.FiltroInstitucion" scope="session"/><jsp:setProperty name="filtroInstitucion" property="*"/>   
<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@include file="../parametros.jsp"%><c:set var="tip" value="1" scope="page"/>
<script languaje='javaScript'>
		<!--
			var nav4=window.Event ? true : false;
			function acepteNumeros(eve){
				var key=nav4?eve.which :eve.keyCode;
				return(key<=13 || (key>=48 && key<=57));
			}
			function hacerValidaciones_frmInst(forma){
				if(forma.cmd.value=='Editar')
					validarSeleccion(forma.id, "- Debe seleccionar un item")
			} 
			function guardarFiltro(tipo){
				if(validarForma(document.frmInst)){
					document.frmInst.nucleo.value=document.frmInst.municipio.options[document.frmInst.municipio.selectedIndex].value;
					document.frmInst.tipo.value=tipo;
					document.frmInst.cmd.value="Guardar";
					document.frmInst.submit();
				}
			}
			function cancelarFiltro(){
				location.href='<c:url value="/bienvenida.jsp"/>';
			}
			function editarFiltro(tipo){
				document.frmInst.cmd.value=tipo;
				if(document.frmInst.cmd.value=='Editar'){
					document.frmInst.ext2.value='/institucion/Filtro.jsp';
					//document.frmInst.target='frame';
				}
			}
			function buscarFiltro(tipo){
				document.frmInst.ext2.value='';
				document.frmInst.tipo.value=tipo;
				document.frmInst.cmd.value="";
				document.frmInst.submit();
			}
			<c:if test="${empty sessionScope.buscado}">
			function Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre){
				this.id_Hijos= id_Hijos; this.Hijos= Hijos; this.Sel_Hijos= Sel_Hijos; this.id_Padre=id_Padre;
			}
			function borrar_combo(combo){
				for(var i = combo.length - 1; i >= 0; i--) {
					if(navigator.appName == "Netscape") combo.options[i] = null;
					else combo.remove(i);
				}
				combo.options[0] = new Option("-seleccione una-","-1");
			}
			</c:if>
			//-->
		</script>
<%@include file="../mensaje.jsp"%>
	<font size="1">
	<form method="post" name="frmInst" onSubmit="return validarForma(frmInst)" action='<c:url value="/institucion/FiltroGuardar.jsp"/>'>
	<br><table border="1" align="center" bordercolor="#FFFFFF" width="95%">
		<caption>Filtro de búsqueda</caption>
		<tr>
		  <td width="45%" bgcolor="#FFFFFF">
				<c:if test="${empty sessionScope.buscado}">
					<input class='boton' name="cmd1" style='width:100px;' type="button" value="Buscar" onClick="guardarFiltro(document.frmInst.tipo.value)">
					<input class='boton' name="cmd12" type="button" value="Cancelar" onClick="cancelarFiltro()">
				</c:if>
				<c:if test="${!empty sessionScope.buscado}">
					<input class='boton' id="cmd1" name="cmd1" type="submit" value="Buscar" onClick="buscarFiltro(document.frmInst.tipo.value)">
					<input class='boton' id="cmd1" name="cmd1" type="submit" value="Editar" onClick="return editarFiltro('Editar')">
					<INPUT TYPE="hidden" NAME="ext2"  VALUE=''>
				</c:if>
		  </td>
		</tr>
	</table>
	<input type="hidden" name="cmd" value="Cancelar"><input type="hidden" name="id2" value="">
	<INPUT TYPE="hidden" NAME="nucleo"  VALUE=''>
	<input type="hidden" name="tipo" value='<c:out value="${tip}"/>'><INPUT TYPE="hidden" NAME="height" VALUE='150'>
					<TABLE width="100%" cellpadding="0" cellSpacing="10">
						<tr>
							<td>
								<div style="width:100%;height:200px;overflow:auto;">
								<c:if test="${empty sessionScope.buscado}">
								<TABLE width="100%" cellpadding="3" cellSpacing="0">
									<tr>
										<td><span class="style2">*</span> Localidad:</td>
										<td>
											<select name="municipio" style='width:150px;'>
												<option value='-1'>-seleccione una-</option>
												<c:forEach begin="0" items="${sessionScope.filtroMunicipioDep}" var="fila">
													<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.filtroInstitucion.municipio== fila[0]}">SELECTED</c:if>>
													<c:out value="${fila[1]}"/></option>
												</c:forEach>
											</select>											
										</td>	
									</tr>
									<tr>
										<td>Codigo DANE:</td>
										<td>
											<input type='text' name='dane' value='<c:out value="${sessionScope.filtroInstitucion.dane}"/>' onKeyPress='return acepteNumeros(event)'>
										</td>
									</tr>
									<tr>
										<td>Nombre del colegio:</td>
										<td>
											<input type='text' name='nombre' value='<c:out value="${sessionScope.filtroInstitucion.nombre}"/>'>
										</td>
									</tr>
									<tr>
										<td>Ordenado por:</td>
										<td>
											<select name="orden">
												<option value='-1' <c:if test="${sessionScope.filtroInstitucion.orden== '-1'}">SELECTED</c:if>>Codigo interno</option>
												<option value='0' <c:if test="${sessionScope.filtroInstitucion.orden== '0'}">SELECTED</c:if>>Codigo DANE</option>
												<option value='1' <c:if test="${sessionScope.filtroInstitucion.orden== '1'}">SELECTED</c:if>>Nombre</option>
											</select>	
										</td>
									</tr>									
							  </TABLE>
								</c:if>
								<c:if test="${!empty sessionScope.buscado}">
								<center>
								<table border="1" align="center" width="100%" cellpadding="0" cellspacing="0" bordercolor="Silver">
									<tr>
										<th colspan='2' class="EncabezadoColumna">Código Dane</th><th class="EncabezadoColumna">Nombre</th>
									</tr>
									<c:if test="${empty sessionScope.filtroInstituciones}">
									<tr><td class="EncabezadoColumna" colspan='3'>NO HAY REGISTROS DE ESTA BUSQUEDA</td></tr></c:if>
									<c:if test="${!empty sessionScope.filtroInstituciones}">
									<c:forEach begin="0" items="${sessionScope.filtroInstituciones}" var="fila" varStatus="st">
									<tr>
										<td class='Fila<c:out value="${st.count%2}"/>' width='1%'><INPUT class='Fila<c:out value="${st.count%2}"/>' type='radio' name='id' value='<c:out value="${fila[1]}"/>' onClick="javaScript:document.frmInst.id2.value='<c:out value="${fila[0]}"/>'"></td>
										<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[1]}"/></td>
										<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[2]}"/></td>
									</tr>
									</c:forEach>
									</c:if>
							  </TABLE>
								</center>
								</c:if>
								</div>
							</td>
						</tr>
					</table>
	</font>
</form>
<script><c:if test="${empty sessionScope.buscado}">//document.frmInst.municipio.onchange();</c:if></script>