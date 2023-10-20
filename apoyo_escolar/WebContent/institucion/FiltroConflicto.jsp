<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %><%@ page import="siges.util.Recursos" %> 
  <jsp:useBean id="nuevoInstitucion" class="siges.institucion.beans.Institucion" scope="session"/><jsp:setProperty name="nuevoInstitucion" property="*"/>
  <jsp:useBean id="nuevoJornada" class="siges.institucion.beans.Jornada" scope="session"/><jsp:setProperty name="nuevoJornada" property="*"/>
  <jsp:useBean id="nuevoSede" class="siges.institucion.beans.Sede" scope="session"/><jsp:setProperty name="nuevoSede" property="*"/>
  <jsp:useBean id="nuevoEspacio" class="siges.institucion.beans.Espacio" scope="session"/><jsp:setProperty name="nuevoEspacio" property="*"/>
  <jsp:useBean id="nuevoNivel" class="siges.institucion.beans.Nivel" scope="session"/><jsp:setProperty name="nuevoNivel" property="*"/>  
	<jsp:useBean id="nuevoTransporte" class="siges.institucion.beans.Transporte" scope="session"/><jsp:setProperty name="nuevoTransporte" property="*"/>
  <jsp:useBean id="nuevoCafeteria" class="siges.institucion.beans.Cafeteria" scope="session"/><jsp:setProperty name="nuevoCafeteria" property="*"/>
  <jsp:useBean id="nuevoConflicto" class="siges.institucion.beans.ConflictoEscolar" scope="session"/>
	<%pageContext.setAttribute("filtroPeriodo",Recursos.recursoEstatico[Recursos.PERIODO]);%>
<%@include file="../parametros.jsp"%>
		<c:if test="${sessionScope.NivelPermiso==1}"><c:set var="permisoBoton" value="none"/></c:if>
		<script languaje='javaScript'>			
			<!--
			var fichaBasico=1;
			var fichaSede=1;
			var fichaEspacio=1;
			var fichaGobierno=1;
			var fichaTransporte=0;
			var fichaCafeteria=0;
			var fichaConflicto=0;
		  var nav4=window.Event ? true : false;
		  
		  function acepteNumeros(eve){
			var key=nav4?eve.which :eve.keyCode;
			return(key<=13 || (key>=48 && key<=57));
		  }
			function cancelar(){
				//if (confirm('¿Desea cancelar la inserción de la información de gobierno escolar?')){
					document.frmNuevo.cmd.value="Cancelar";
					document.frmNuevo.submit();
				//}
			}
			function hacerValidaciones_frmNuevo(forma){				
				validarLista(forma.cesede, "- Sede",1)
				validarLista(forma.ceperiodo, "- Periodo",1)
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
					document.frmNuevo.cesednom.value=document.frmNuevo.cesede.options[document.frmNuevo.cesede.selectedIndex].text;
					document.frmNuevo.cepernom.value=document.frmNuevo.ceperiodo.options[document.frmNuevo.ceperiodo.selectedIndex].text;
					document.frmNuevo.tipo2.value=tipo;
					document.frmNuevo.cmd.value="Guardar";
					document.frmNuevo.submit();
				}
			}
		-->
	</script>
	<%@include file="../mensaje.jsp"%>
	<font size="1">
		<form method="post" name="frmNuevo" onSubmit=" return validarForma(frmNuevo)"  action='<c:url value="/institucion/NuevoGuardar.jsp"/>'>
		<table border="0" align="center" bordercolor="#FFFFFF" width="100%">
			<tr>		
			  <td width="45%" >
       <input  class='boton' name="cmd1" type="button" value="Ver Formulario" onClick="guardar(8)"  style='display:<c:out value="${permisoBoton}"/>'>
			</tr>		
		</table>
<!--//////////////////-->		
	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="tipo2" value="7">
	<input type="hidden" name="cmd" value="Cancelar"><INPUT TYPE="hidden" NAME="height" VALUE='150'>
	<input type="hidden" name="ext2" value='<c:out value="${sessionScope.subframe2}"/>'>
	<input type="hidden" name="cesednom" value=''>
	<input type="hidden" name="cepernom" value=''>
		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1">
				<td width="10" bgcolor="#FFFFFF">&nbsp;</td>			
				<td rowspan="2" width="600" bgcolor="#FFFFFF"><script>if(fichaBasico==1)document.write('<a href="javaScript:lanzar(1)"><img src="../etc/img/tabs/datos_basicos_0.gif" alt="Inf B&aacute;sica"  height="26" border="0"></a>');if(fichaSede==1)document.write('<a href="javaScript:lanzar(2)"><img src="../etc/img/tabs/sedes_0.gif" alt="Sedes"  height="26" border="0"></a>');if(fichaEspacio==1)document.write('<a href="javaScript:lanzar(3)"><img src="../etc/img/tabs/espacios_fisicos_0.gif" alt="Espacios Fisicos"  height="26" border="0"></a>');if(fichaGobierno==1)document.write('<a href="javaScript:lanzar(5)"><img src="../etc/img/tabs/gobierno_escolar_0.gif" alt="Gobierno"  height="26" border="0"></a>');if(fichaTransporte==1)document.write('<a href="javaScript:lanzar(6)"><img src="../etc/img/tabs/transporte_0.gif" alt="transporte"  height="26" border="0"></a>');if(fichaCafeteria==1)document.write('<img src="../etc/img/tabs/cafeteria_1.gif" alt="Cafeteria"  height="26" border="0">');if(fichaConflicto==1)document.write('<img src="../etc/img/tabs/conflicto_escolar_1.gif" alt="Conflicto escolar" border="0"  height="26">');</script></td>
			</tr>
		</table>
		<!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
								<TABLE width="100%" cellpadding="3" cellSpacing="0">
								<caption>SELECCIONE UNA JORNADA Y UN PERIODO PARA VER SU CONFLICTO ESCOLAR</caption>
								    <tr><td><span class="style2">*</span> Sede:</td>
										<td>
											<select name="cesede" style='width:220px;'>
												<option value="-1">--seleccione una--</option>
													<c:forEach begin="0" items="${sessionScope.filtroSedeJerarquia}" var="fila">
														<option value='<c:out value="${fila[0]}"/>'  <c:if test="${sessionScope.nuevoConflicto.cesede== fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option>
													</c:forEach>
											</select>
										</td>
										<td><span class="style2">*</span> Periodo:</td>
										<td><select name="ceperiodo">
	                    <option value="-1">--seleccione uno--</option>
	                    <c:forEach begin="0" items="${filtroPeriodo}" var="fila">
	                    <option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.nuevoConflicto.ceperiodo== fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/> </c:forEach>
	                  </select></td>
									</tr>
						  </TABLE>
</form>