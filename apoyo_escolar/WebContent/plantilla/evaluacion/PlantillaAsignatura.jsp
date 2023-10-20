<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="filtroPlantilla" class="siges.plantilla.beans.FiltroPlantilla" scope="session"/><jsp:setProperty name="filtroPlantilla" property="*"/>
<jsp:useBean id="paramsVO" class="siges.plantilla.beans.ParamsVO" scope="page"/>
<jsp:useBean id="propertiesVO" class="siges.util.Properties" scope="page"/>
<c:import url="/parametros.jsp"/>
<c:set var="tip" value="1" scope="page"/>
<script languaje='javaScript'>
		<!--
			var nav4=window.Event ? true : false;
			function acepteNumeros(eve){
				var key=nav4?eve.which :eve.keyCode;
				return(key<=13 || (key>=48 && key<=57));
			}
			function hacerValidaciones_frmNuevo(forma){
				validarLista(forma.metodologia,"- Metodologia",1)
				validarLista(forma.grado,"- Grado",1)
				//validarLista(forma.grupo,"- Grupo",1)
				//validarLista(forma.asignatura,"- Asignatura",1)
				validarLista(forma.periodo,"- Periodo",1)
				if(forma.asignatura.selectedIndex>0 && forma.docente){
					validarLista(forma.docente,"- Docente (obligatorio si se selecciona asignatura)",1)
				}
			}
			
			function lanzar(tipo){
				document.frmNuevo.tipo.value=tipo;
				document.frmNuevo.action="<c:url value="/plantilla/evaluacion/ControllerPlantillaEdit.do"/>";
				if(parseInt(tipo)==10){
					document.frmNuevo.cmd.value='<c:out value="${paramsVO.CMD_NUEVO}"/>';
					document.frmNuevo.action='<c:url value="/plantilla/comportamiento/Nuevo.do"/>';
				}
				document.frmNuevo.target="";
				document.frmNuevo.submit();
			}
			
			function guardar(tipo){
				if(validarForma(document.frmNuevo)){
					document.frmNuevo.grado_.value=document.frmNuevo.grado.options[document.frmNuevo.grado.selectedIndex].text;
					document.frmNuevo.grupo_.value=document.frmNuevo.grupo.options[document.frmNuevo.grupo.selectedIndex].text;
					document.frmNuevo.asignatura_.value=document.frmNuevo.asignatura.options[document.frmNuevo.asignatura.selectedIndex].text;
					document.frmNuevo.periodo_.value=document.frmNuevo.periodo.options[document.frmNuevo.periodo.selectedIndex].text;
					if(document.frmNuevo.docente) document.frmNuevo.docente_.value=document.frmNuevo.docente.options[document.frmNuevo.docente.selectedIndex].text;
					document.frmNuevo.metodologia_.value=document.frmNuevo.metodologia.options[document.frmNuevo.metodologia.selectedIndex].text;
					document.frmNuevo.periodoAbrev.value=document.frmNuevo.periodo.selectedIndex;
					document.frmNuevo.tipo.value=tipo;
					document.frmNuevo.cmd.value="Buscar";
					document.frmNuevo.submit();
				}
			}
			function cancelar(){
				if (confirm('¿Desea cancelar la evaluacion?')){
 					document.frmNuevo.cmd.value="Cancelar";
					document.frmNuevo.target="";
				    document.frmNuevo.submit(); 
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
				combo.options[0] = new Option("-- Todos --","-3");
			}
			function filtro(combo_padre0,combo_padre,combo_hijo){
				borrar_combo(combo_hijo);
				<c:if test="${!empty requestScope.filtroGrado2 && !empty requestScope.filtroGradoAsignatura}">var Padres = new Array();
					<c:forEach begin="0" items="${requestScope.filtroGrado2}" var="fila" varStatus="st">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
						<c:forEach begin="0" items="${requestScope.filtroGradoAsignatura}" var="fila2">
							<c:if test="${fila[0]==fila2[2] && fila[2]==fila2[3]}">Sel_Hijos[k] = '<c:if test="${sessionScope.filtroPlantilla.asignatura== fila2[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k] = '<c:out value="${fila2[4]}"/>'; k++;</c:if>
						</c:forEach>Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);
					</c:forEach>var niv=combo_padre.options[combo_padre.selectedIndex].value+'|'+combo_padre0.value;
					var val_padre = -1;
					for(var k=0;k<Padres.length;k++){
						if(Padres[k].id_Padre[0]==niv) val_padre=k;
					}
					if(val_padre!=-1){
						var no_hijos = Padres[val_padre].Hijos.length;
						for(i=0; i < no_hijos; i++){
							if(Padres[val_padre].Sel_Hijos[i] == "SELECTED"){
								combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED"); combo_hijo.selectedIndex = i+1;
							}else
								combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
						}
					}</c:if>
			}
			function filtro2(combo_padre0,combo_padre,combo_hijo){
				borrar_combo(combo_hijo);
				<c:if test="${!empty requestScope.filtroGrado2 && !empty requestScope.filtroGrupo2}">var Padres = new Array();
					<c:forEach begin="0" items="${requestScope.filtroGrado2}" var="fila" varStatus="st">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
					<c:forEach begin="0" items="${requestScope.filtroGrupo2}" var="fila2">
						<c:if test="${fila[0]==fila2[2] && fila[2]==fila2[3]}">Sel_Hijos[k] = '<c:if test="${sessionScope.filtroPlantilla.grupo== fila2[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k] = '<c:out value="${fila2[4]}"/>'; k++;</c:if>
					</c:forEach>Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
					var niv=combo_padre.options[combo_padre.selectedIndex].value+'|'+combo_padre0.value;
					var val_padre = -1;
					for(var k=0;k<Padres.length;k++){
						if(Padres[k].id_Padre[0]==niv)
							val_padre=k;
					}
					if(val_padre!=-1){
						var no_hijos = Padres[val_padre].Hijos.length;
						for(i=0; i < no_hijos; i++){
							if(Padres[val_padre].Sel_Hijos[i] == "SELECTED"){
								combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED"); combo_hijo.selectedIndex = i+1;
							}else
								combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
						}
				}</c:if>
			}
			function filtro3(combo_padre,combo_hijo,combo_hijo2,combo_hijo3){
				borrar_combo(combo_hijo); borrar_combo(combo_hijo2);borrar_combo(combo_hijo3);
					<c:if test="${!empty requestScope.filtroMetodologia && !empty requestScope.filtroGrado2}">var Padres = new Array();
						<c:forEach begin="0" items="${requestScope.filtroMetodologia}" var="fila" varStatus="st">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
							<c:forEach begin="0" items="${requestScope.filtroGrado2}" var="fila2">
							<c:if test="${fila[0]==fila2[2] && (fila2[0]>=0 || fila2[0]==40 || fila2[0]==41)}">Sel_Hijos[k] = '<c:if test="${sessionScope.filtroPlantilla.grado== fila2[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k] = '<c:out value="${fila2[2]}"/>'; k++;</c:if>
							</c:forEach>Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
						var niv=combo_padre.value;
						var val_padre = -1;
						for(var k=0;k<Padres.length;k++){
							if(Padres[k].id_Padre[0]==niv)
								val_padre=k;
						}
					if(val_padre!=-1){
						var no_hijos = Padres[val_padre].Hijos.length;
						for(i=0; i < no_hijos; i++){
							if(Padres[val_padre].Sel_Hijos[i] == "SELECTED"){
								combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED"); combo_hijo.selectedIndex = i+1;
							}else
								combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
						}
				}</c:if>
			}

	function ajaxDocente(){
		if(document.frmNuevo.docente){
			borrar_combo(document.frmNuevo.docente); 
			document.frmAjax.ajax[0].value=document.frmNuevo.institucion.value;
			document.frmAjax.ajax[1].value=document.frmNuevo.metodologia.value;
			document.frmAjax.ajax[2].value=document.frmNuevo.vigencia.value;
			document.frmAjax.ajax[3].value=document.frmNuevo.grado.value;
			document.frmAjax.ajax[4].value=document.frmNuevo.asignatura.value;
			if(parseInt(document.frmAjax.ajax[4].value)!=-99){
				document.frmAjax.cmd.value=document.frmNuevo.AJAX_DOCENTE.value;
		 		document.frmAjax.target="frameAjax";
				document.frmAjax.submit();
			}	
		}	
	}
			//-->
		</script>
<c:import url="/mensaje.jsp"/>
	<form method="post" name="frmAjax" action='<c:url value="/plantilla/evaluacion/ControllerPlantillaAjax.do"/>'>
		<INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${propertiesVO.PLANTILLALOGROASIG}"/>'>
		<input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_AJAX}"/>'>
		<c:forEach begin="0" end="5" var="i"><input type="hidden" name="ajax"></c:forEach>
	</form>
	<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/plantilla/evaluacion/PlantillaGuardar.jsp"/>'>
	<br>
	<table border="0" align="center" bordercolor="#FFFFFF" width="95%">
	<caption>Generar plantillas de Evaluación de Asignatura </caption>
		<tr>
		  <td width="45%" bgcolor="#FFFFFF">
        <input class='boton' name="cmd1" type="button" value="Generar" onClick="guardar(document.frmNuevo.tipo.value)">
			</td>
		</tr>
	</table>
<!--//////////////////-->		
	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="tipo" value='<c:out value="${pageScope.tip}"/>'>
	<input type="hidden" name="cmd" value="Cancelar">
	<input type="hidden" name="grado_" value='<c:out value="${sessionScope.filtroPlantilla.grado_}"/>'>
	<input type="hidden" name="grupo_" value='<c:out value="${sessionScope.filtroPlantilla.grupo_}"/>'>
	<input type="hidden" name="asignatura_" value='<c:out value="${sessionScope.filtroPlantilla.asignatura_}"/>'>
	<input type="hidden" name="periodo_" value='<c:out value="${sessionScope.filtroPlantilla.periodo_}"/>'>
	<input type="hidden" name="metodologia_" value='<c:out value="${sessionScope.filtroPlantilla.metodologia_}"/>'>
	<input type="hidden" name="docente_" value='<c:out value="${sessionScope.filtroPlantilla.docente_}"/>'>
	<input type="hidden" name="periodoAbrev" value='<c:out value="${sessionScope.filtroPlantilla.periodoAbrev}"/>'>
	<input type="hidden" name="plantilla" value='1'>

	<input type="hidden" name="institucion" value='<c:out value="${sessionScope.filtroPlantilla.institucion}"/>'>
	<input type="hidden" name="vigencia" value='<c:out value="${sessionScope.filtroPlantilla.vigencia}"/>'>
	<input type="hidden" name="AJAX_DOCENTE" value='<c:out value="${paramsVO.CMD_AJAX_DOCENTE}"/>'>

	<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
		<tr height="1">
			<td width="10" bgcolor="#FFFFFF">&nbsp;</td>											
			<td rowspan="2" width="588" bgcolor="#FFFFFF"><img src="../../etc/img/tabs/Evaluacion_Asignaturas_1.gif" alt='-Asignatura-' width="84"  height="26" border="0"><a href="javaScript:lanzar(2)"><img src="../../etc/img/tabs/Evaluacion_Areas_0.gif" alt="Asignatura" width="84"  height="26" border="0"></a><a href="javaScript:lanzar(3)"><img src="../../etc/img/tabs/Evaluacion_Preescolar_0.gif" alt="Preescolar" width="84"  height="26" border="0"></a><a href="javaScript:lanzar(6)"><img src="../../etc/img/tabs/Recuperacion_0.gif" alt="Recuperacion" width="84"  height="26" border="0"></a><a href="javaScript:lanzar(10)"><img src='<c:url value="/etc/img/tabs/Evaluacion_Comportamiento_0.gif"/>' alt="Comportamiento" width="84"  height="26" border="0"></a></td>
		</tr>
  </table><!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
								<TABLE width="100%" cellpadding="3" cellSpacing="0">
										<tr>
											<th><span class="style2">*</span>Metodologia:</th>
											<td>
												<select name="metodologia" style='width:120px;' onChange='filtro3(document.frmNuevo.metodologia,document.frmNuevo.grado, document.frmNuevo.grupo, document.frmNuevo.asignatura);'>
													<option value='-3'>-- Todos --</option>
													<c:forEach begin="0" items="${requestScope.filtroMetodologia}" var="fila">
													<option value='<c:out value="${fila[0]}"/>' <c:if test="${sessionScope.filtroPlantilla.metodologia==fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option>
													</c:forEach>
												</select>
											</td>	
											<th><span class="style2">*</span>Grado:</th>
											<td><select name="grado" style='width:120px;' onChange='filtro2(document.frmNuevo.metodologia,document.frmNuevo.grado, document.frmNuevo.grupo);filtro(document.frmNuevo.metodologia,document.frmNuevo.grado, document.frmNuevo.asignatura)'><option value='-3'>-- Todos --</option></select></td>
											<th>Grupo:</th>
											<td><select name="grupo" style='width:100px;' ><option value='-3'>-- Todos --</option></select></td>
										</tr>
										<tr>
											<th>Asignatura:</th>
											<td><select name="asignatura" style='width:120px;' onchange="javaScript:ajaxDocente()"><option value='-3'>-- Todos --</option></select></td>
											
											<th><span class="style2">*</span>Periodo:</th>
											<td><select name="periodo" style='width:120px;'>
												<option value='-3'>-- Seleccione uno --</option>
													<c:forEach begin="0" items="${requestScope.filtroPeriodo}" var="per">
														<option value="<c:out value="${per.codigo}"/>" <c:if test="${per.codigo==sessionScope.filtroPlantilla.periodo}">selected</c:if>><c:out value="${per.nombre}"/></option>
													</c:forEach>
											</select></td>	
											<th>Orden:</th>
											<td>
												<select name="orden" style='width:100px;'>
													<option value='0' <c:if test="${sessionScope.filtroPlantilla.orden== '0'}">SELECTED</c:if>>Apellidos</option>
													<option value='1' <c:if test="${sessionScope.filtroPlantilla.orden== '1'}">SELECTED</c:if>>Nombres</option>
													<option value='2' <c:if test="${sessionScope.filtroPlantilla.orden== '2'}">SELECTED</c:if>>Identificación</option>
													<option value='3' <c:if test="${sessionScope.filtroPlantilla.orden== '3'}">SELECTED</c:if>>Código interno</option>
												</select>
											</td>
										</tr>
										<c:if test="${sessionScope.filtroPlantilla.filPlanEstudios==1}">
										<tr>
											<th>Docente:</th>
											<td colspan="3">
												<select name="docente" style='width:250px;' >
													<option value='-99'>--Seleccione uno--</option>
													<c:forEach begin="0" items="${requestScope.listaDocente}" var="doc">
														<option value="<c:out value="${doc.codigo}"/>" <c:if test="${doc.codigo==sessionScope.filtroPlantilla.docente}">selected</c:if>><c:out value="${doc.nombre}"/></option>
													</c:forEach>
												</select>
											</td>	
										</tr>
										</c:if>
										<tr style="display:none"><td><iframe name="frameAjax" id="frameAjax"></iframe></td></tr>
										</form>
										<c:if test="${!empty requestScope.plantilla && requestScope.plantilla!='--' && requestScope.plantilla!='_'}">
									    <tr>
										<td colspan="6" align='center' valign="top"><form name='frmGenerar' action='<c:url value="/${requestScope.plantilla}"/>' method='post' target='_blank'><input type='hidden' name='dir' value='<c:out value="${requestScope.plantilla}"/>'><input type='hidden' name='tipo' value='<c:out value="${requestScope.tipoArchivo}"/>'><input type='hidden' name='accion' value='0'><input type='hidden' name='aut' value='1'>La plantilla fue generada satisfactoriamente.<br>Pulse en el Icono para descargar el archivo.<br></form>
											>><a href='javaScript:document.frmGenerar.submit();'><img border=0 src='<c:url value="/etc/img/xls.gif"/>'></A><<
											<p><p><span class="style2">Nota: Solo se generan plantillas para los grupos con estudiantes</span>
										</td>
									</tr>
									</c:if>
									<c:if test="${requestScope.plantilla=='--'}"><tr><td colspan="6" valign="top">No se pudo generar la plantilla de Excel.<br></td></tr></c:if>
									<c:if test="${requestScope.plantilla=='_'}"><tr><td align="center" colspan="6" valign="top"><p><br><span class="style2"><font size="2">Las plantillas se estan generando en este momento. <br> Pulse en el vinculo '<b>Listado de Reportes</b>' para ver si el reporte ya fue generado.<br> Si el reporte no esta listo, continue actualizando la lista de reportes hasta que el reporte se genere.<P></span><a class='A' href='<c:url value="/Reportes.do"/>'>>> LISTADO DE REPORTES <<</a></font><br></td></tr></c:if>
								</TABLE>
<script>
filtro3(document.frmNuevo.metodologia,document.frmNuevo.grado, document.frmNuevo.grupo, document.frmNuevo.asignatura);
document.frmNuevo.grado.onchange();</script>