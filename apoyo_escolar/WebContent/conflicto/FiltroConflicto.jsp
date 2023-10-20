<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ page import="siges.util.Recursos" %> 
<%@include file="../parametros.jsp"%>
  <jsp:useBean id="nuevoConflicto" class="siges.conflicto.beans.ConflictoEscolar" scope="session"/>
  <jsp:useBean id="nuevoTipo" class="siges.conflicto.beans.TipoConflicto" scope="session"/><jsp:setProperty name="nuevoTipo" property="*"/>
  <jsp:useBean id="nuevoResolucion" class="siges.conflicto.beans.ResolucionConflictos" scope="session"/><jsp:setProperty name="nuevoResolucion" property="*"/>
  <jsp:useBean id="nuevoMedidas" class="siges.conflicto.beans.MedidasInst" scope="session"/><jsp:setProperty name="nuevoMedidas" property="*"/>
  <jsp:useBean id="nuevoProyectos" class="siges.conflicto.beans.Proyectos" scope="session"/><jsp:setProperty name="nuevoProyectos" property="*"/>
  <jsp:useBean id="nuevoInfluencia" class="siges.conflicto.beans.InfluenciaConflictos" scope="session"/><jsp:setProperty name="nuevoInfluencia" property="*"/>
	<%pageContext.setAttribute("filtroPeriodo",Recursos.recursoEstatico[Recursos.PERIODO]);%>
		<script languaje='javaScript'>			
			<!--
		  var nav4=window.Event ? true : false;
		  
		  function acepteNumeros(eve){
			var key=nav4?eve.which :eve.keyCode;
			return(key<=13 || (key>=48 && key<=57));
		  }
			function cancelar(){
				if (confirm('¿Desea cancelar la inserción de la información de gobierno escolar?')){
					document.frmNuevo.cmd.value="Cancelar";
					document.frmNuevo.submit();
				}
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
			function lanzar(t,c){
				guardar(t,c)
				//document.frmNuevo.tipo.value=tipo;
				//document.frmNuevo.action='<c:url value="/conflicto/ControllerEditar.do"/>';
				//document.frmNuevo.submit();
			}
			function guardar(tipo,cat){
				if(validarForma(document.frmNuevo)){
					document.frmNuevo.cesednom.value=document.frmNuevo.cesede.options[document.frmNuevo.cesede.selectedIndex].text;
					document.frmNuevo.cepernom.value=document.frmNuevo.ceperiodo.options[document.frmNuevo.ceperiodo.selectedIndex].text;
					document.frmNuevo.tipo.value=tipo;
					document.frmNuevo.categoria.value=cat;
					document.frmNuevo.cmd.value="Guardar";
					document.frmNuevo.submit();
				}
			}
		-->
	</script>
	<%@include file="../mensaje.jsp"%>
	<font size="1">
		<form method="post" name="frmNuevo" onSubmit=" return validarForma(frmNuevo)"  action='<c:url value="/conflicto/NuevoGuardar.jsp"/>'>
		<table border="0" align="center" bordercolor="#FFFFFF" width="100%">
			<tr>		
			  <td width="45%" >
       <input class='boton' name="cmd1" type="button" value="Ver Formulario" onClick="guardar(10,-1)">
			</tr>		
		</table>
<!--//////////////////-->		
	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="tipo" value="">
	<input type="hidden" name="cmd" value="Cancelar"><INPUT TYPE="hidden" NAME="height" VALUE='150'>
	<input type="hidden" name="ext2" value=''>
	<input type="hidden" name="cesednom" value=''>
	<input type="hidden" name="cepernom" value=''>
	<input type="hidden" name="categoria" value=''>
		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1">
				<td width="10" bgcolor="#FFFFFF">&nbsp;</td>
				<td rowspan="2" width="600" bgcolor="#FFFFFF">
					<img src="../etc/img/tabs/conflicto_escolar_1.gif" alt="Filtro" border="0"  height="26">
					<a href="javaScript:lanzar(10,-1)"><img src="../etc/img/tabs/tipos_conflicto_0.gif" alt="Tipos de Conflicto" border="0"  height="26"></a>
					<a href="javaScript:lanzar(20,2)"><img src="../etc/img/tabs/resolucion_conflictos_0.gif" alt="Formas comunes de resolución de conflictos" border="0"  height="26"></a>
					<!--<a href="javaScript:lanzar(30,3)"><img src="../etc/img/tabs/medidas_institucionales_0.gif" alt="Medidas Institucionales" border="0"  height="26"></a>-->
					<a href="javaScript:lanzar(40,4)"><img src="../etc/img/tabs/proyectos_0.gif" alt="Proyectos que apoyan la resolución de conflictos" border="0"  height="26"></a>
					<!--<a href="javaScript:lanzar(50,5)"><img src="../etc/img/tabs/influencia_conflictos_0.gif" alt="Como influyen los conflictos en los siguientes aspectos de la vida del colegio" border="0"  height="26"></a>-->
					<!--<a href="javaScript:lanzar(60,-1)"><img src="../etc/img/tabs/consolidado_grupo_0.gif" alt="Consolidado por grupo" border="0"  height="26"></a>-->
				</td>
			</tr>
		</table>
		<!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
		<table cellpadding="0" cellspacing="0" border="0" width="100%">
			<tr>
				<td width="1" bgcolor="#000000"><img src="../etc/img/pixel.gif" width="1" height="1"></td>
				<td>
					<TABLE width="100%" cellpadding="0" cellSpacing="10">
						<tr>
							<td>
								<TABLE width="100%" cellpadding="3" cellSpacing="0">
								<caption>SELECCIONE UNA JORNADA Y UN PERIODO PARA VER SU CONFLICTO ESCOLAR</caption>
								    <tr><td><span class="style2">*</span> Sede:</td>
										<td>
											<select name="cesede" style='width:220px;'>
												<option value="-1">--seleccione una--</option>
													<c:forEach begin="0" items="${requestScope.filtroSedeJerarquia}" var="fila">
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
							</td>
						</tr>
					</table>
				</td>								
				<td width="1" bgcolor="#000000"><img src="../etc/img/pixel.gif" width="1" height="1"></td>			
			<tr bgcolor="#000000" height="1">
				<td colspan="3"><img src="../etc/img/pixel.gif" width="1" height="1"></td>
			</tr>
	  </table>	
<!--//////////////////////////////-->
	</form>
<script>
</script>