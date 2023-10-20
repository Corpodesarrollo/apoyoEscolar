<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="logros" class="siges.plantilla.beans.Logros" scope="session"/>
<jsp:useBean id="paramsVO" class="siges.plantilla.beans.ParamsVO" scope="page"/>
<jsp:useBean id="propertiesVO" class="siges.util.Properties" scope="page"/>
<c:import url="/parametros.jsp"/>
<c:set var="tip" value="4" scope="page"/>
		<script languaje='javaScript'>
		<!--
			var nav4=window.Event ? true : false;
			function acepteNumeros(eve){
				var key=nav4?eve.which :eve.keyCode;
				return(key<=13 || (key>=48 && key<=57));
			}
			function generarPlantilla(){
				document.frmGenerar.submit();
			}
			function hacerValidaciones_frmNuevo(forma){
				validarLista(forma.plantillaMetodologia,"- Metodologia",1)
				//validarLista(forma.plantillaGrado,"- Grado",1)
				//validarLista(forma.plantillaAsignatura,"- Asignatura",1)
				if(forma.plantillaAsignatura.selectedIndex>0 && forma.plantillaDocente){
					//validarLista(forma.plantillaDocente,"- Docente (obligatorio si se selecciona asignatura)",1)
					validarLista(forma.plantillaDocente,"- Una vez que seleccione una asignatura debe escoger el docente en particular",1)
					
				}
			}
			
			function lanzar(tipo){
				document.frmNuevo.tipo.value=tipo;					
				document.frmNuevo.action="<c:url value="/plantilla/bateria/ControllerPlantillaEdit.do"/>";
				document.frmNuevo.submit();
			}
			
			function guardar(tipo){
				if(validarForma(document.frmNuevo)){
					document.frmNuevo.plantillaGrado_.value=document.frmNuevo.plantillaGrado.options[document.frmNuevo.plantillaGrado.selectedIndex].text;
					document.frmNuevo.plantillaAsignatura_.value=document.frmNuevo.plantillaAsignatura.options[document.frmNuevo.plantillaAsignatura.selectedIndex].text;
					document.frmNuevo.plantillaMetodologia_.value=document.frmNuevo.plantillaMetodologia.options[document.frmNuevo.plantillaMetodologia.selectedIndex].text;
					document.frmNuevo.tipo.value=tipo;
					document.frmNuevo.cmd.value="Buscar";
					document.frmNuevo.submit();
				}
			}
			
			function cancelar(){
				if (confirm('¿Desea cancelar la Generación de plantilla?')){
 					document.frmNuevo.cmd.value="Cancelar";
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
				combo.options[0] = new Option("-- Todos --","-99");
			}
			
			function filtro2(combo_padre,combo_hijo,combo_hijo2){
				borrar_combo(combo_hijo);borrar_combo(combo_hijo2);
				<c:if test="${!empty requestScope.filtroMetodologia && !empty requestScope.filtroGrado2}">var Padres = new Array();
					<c:forEach begin="0" items="${requestScope.filtroMetodologia}" var="fila" varStatus="st">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
					<c:forEach begin="0" items="${requestScope.filtroGrado2}" var="fila2">
						<c:if test="${fila[0]==fila2[2] && (fila2[0]>=0 || fila2[0]==40 || fila2[0]==41)}">Sel_Hijos[k] = '<c:if test="${sessionScope.logros.plantillaGrado== fila2[0]}">SELECTED</c:if>'; id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k] = '<c:out value="${fila2[2]}"/>'; k++; </c:if>
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
			
			function filtro(combo_padre0,combo_padre,combo_hijo){//met.gra.asig
				borrar_combo(combo_hijo);
				<c:if test="${!empty requestScope.filtroGrado2 && !empty requestScope.filtroGradoAsignatura2}">var Padres = new Array();
					<c:forEach begin="0" items="${requestScope.filtroGrado2}" var="fila" varStatus="st">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
						<c:forEach begin="0" items="${requestScope.filtroGradoAsignatura2}" var="fila2">
						<c:if test="${fila[0]==fila2[2] && fila[2]==fila2[3]}">Sel_Hijos[k] = '<c:if test="${sessionScope.logros.plantillaAsignatura== fila2[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k]='<c:out value="${fila2[4]}"/>'; k++; </c:if>
						</c:forEach>Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
					var niv=combo_padre.options[combo_padre.selectedIndex].value +'|'+ combo_padre0.value;
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
		//-->

	function ajaxDocente(){
		if(document.frmNuevo.plantillaDocente){
			borrar_combo(document.frmNuevo.plantillaDocente); 
			document.frmAjax.ajax[0].value=document.frmNuevo.plantillaInstitucion.value;
			document.frmAjax.ajax[1].value=document.frmNuevo.plantillaMetodologia.value;
			document.frmAjax.ajax[2].value=document.frmNuevo.plantillaVigencia.value;
			document.frmAjax.ajax[3].value=document.frmNuevo.plantillaGrado.value;
			document.frmAjax.ajax[4].value=document.frmNuevo.plantillaAsignatura.value;
			if(parseInt(document.frmAjax.ajax[4].value)!=-99){
				document.frmAjax.cmd.value=document.frmNuevo.AJAX_DOCENTE.value;
		 		document.frmAjax.target="frameAjax";
				document.frmAjax.submit();
			}
		}
	}
		</script>

<c:import url="/mensaje.jsp"/>
	<form method="post" name="frmAjax" action='<c:url value="/plantilla/bateria/ControllerPlantillaAjax.do"/>'>
		<INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${propertiesVO.TIPOBATERIALOGRO}"/>'>
		<input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_AJAX}"/>'>
		<c:forEach begin="0" end="5" var="i"><input type="hidden" name="ajax"></c:forEach>
	</form>

	<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/plantilla/bateria/PlantillaGuardar.jsp"/>'>
	<br>
	<table border="0" align="center" bordercolor="#FFFFFF" width="95%">
	<caption>Generar plantillas de Logros</caption>
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
	<input type="hidden" name="plantillaMetodologia_" value=''>
	<input type="hidden" name="plantillaGrado_" value='<c:out value="${sessionScope.logros.plantillaGrado_}"/>'>
	<input type="hidden" name="plantillaAsignatura_" value='<c:out value="${sessionScope.logros.plantillaAsignatura_}"/>'>
	<input type="hidden" name="plantillaInstitucion" value='<c:out value="${sessionScope.logros.plantillaInstitucion}"/>'>
	<input type="hidden" name="plantillaVigencia" value='<c:out value="${sessionScope.logros.plantillaVigencia}"/>'>
	<input type="hidden" name="AJAX_DOCENTE" value='<c:out value="${paramsVO.CMD_AJAX_DOCENTE}"/>'>
	<table cellpadding="0" cellspacing="0" border="0" align="CENTER" width="100%" >
  		<tr height="1">
  				<td width="10" bgcolor="#FFFFFF">&nbsp;</td>
  				<td rowspan="2" width="588" bgcolor="#FFFFFF"><img src="../../etc/img/tabs/logros_1.gif" alt="Asignatura" width="84"  height="26" border="0"><a href="javaScript:lanzar(5)"><img src="../../etc/img/tabs/descriptores_0.gif" alt="Asignatura" width="84"  height="26" border="0"></a></td>
 				</tr>
 		</table>
	<!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
								<TABLE width="100%" cellpadding="3" cellSpacing="0">
										<tr>
											<th><span class="style2">*</span>Metodologia:</th>
											<td>
												<select name="plantillaMetodologia" style='width:120px;' onChange='filtro2(document.frmNuevo.plantillaMetodologia,document.frmNuevo.plantillaGrado,document.frmNuevo.plantillaAsignatura)'>
													<option value='-99'>-- Todos --</option>
													<c:forEach begin="0" items="${requestScope.filtroMetodologia}" var="doc">
														<option value="<c:out value="${doc[0]}"/>" <c:if test="${doc[0]==sessionScope.logros.plantillaMetodologia}">selected</c:if>><c:out value="${doc[1]}"/></option>
													</c:forEach>
												</select>
											</td>	
											<th>Grado:</th>
											<td>
												<select name="plantillaGrado" style='width:120px;' onChange='filtro(document.frmNuevo.plantillaMetodologia,document.frmNuevo.plantillaGrado, document.frmNuevo.plantillaAsignatura)'>
													<option value='-99'>-- Todos --</option>
												</select>
											</td>	
										</tr>
										<tr>
											<th>Asignatura:</th>
											<td colspan="3"><select name="plantillaAsignatura" style='width:220px;' onchange="javaScript:ajaxDocente()">
													<option value='-99'>-- Todos --</option>
													</select>
											</td>	
										</tr>
										<c:if test="${sessionScope.logros.estadoPlanEstudios==1}">
										<tr>
											<th><span class="style2">*</span>Docente:</th>
											<td colspan="3">
												<select name="plantillaDocente" style='width:250px;' >
													<option value='-99'>--Seleccione uno--</option>
													<c:forEach begin="0" items="${requestScope.listaDocente}" var="doc">
														<option value="<c:out value="${doc.codigo}"/>" <c:if test="${doc.codigo==sessionScope.logros.plantillaDocente}">selected</c:if>><c:out value="${doc.nombre}"/></option>
													</c:forEach>
												</select>
											</td>	
										</tr>
										</c:if>
										<tr style="display:none"><td><iframe name="frameAjax" id="frameAjax"></iframe></td></tr>
										</form>
										<c:if test="${!empty requestScope.plantilla && requestScope.plantilla!='--' && requestScope.plantilla!='0'}"><tr>
										<td colspan="6" align='center' valign="top"><form name='frmGenerar' action='<c:url value="/${requestScope.plantilla}"/>' method='post' target='_blank'><input type='hidden' name='dir' value='<c:out value="${requestScope.plantilla}"/>'><input type='hidden' name='tipo' value='<c:out value="${requestScope.tipoArchivo}"/>'><input type='hidden' name='accion' value='0'><input type='hidden' name='aut' value='1'>La plantilla fue generada satisfactoriamente.<br>Pulse en el Icono para descargar el archivo.<br></form>
											>><a href='javaScript:document.frmGenerar.submit();'><img border=0 src='<c:url value="/etc/img/xls.gif"/>'></A><<</td>
										</tr></c:if>
										<c:if test="${requestScope.plantilla=='--'}"><tr><td colspan="6" valign="top" align='center'>No se pudo generar la plantilla de Excel.<br></td></tr></c:if>
										<c:if test="${requestScope.plantilla=='0'}"><tr><td colspan="6" valign="top" align='center'>Se han generado las plantillas. <br>Para ver los archivos generados y descargarlos haga click aqui<br>>><a href='<c:url value="/Reportes.do"/>'><img border=0 src='<c:url value="/etc/img/xls.gif"/>'></A><<<br></td></tr></c:if>
								</TABLE>
<script>
filtro2(document.frmNuevo.plantillaMetodologia,document.frmNuevo.plantillaGrado,document.frmNuevo.plantillaAsignatura);
document.frmNuevo.plantillaGrado.onchange();</script>