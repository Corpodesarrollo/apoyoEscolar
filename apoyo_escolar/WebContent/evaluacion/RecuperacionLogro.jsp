<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="filtroEvaluacion" class="siges.evaluacion.beans.FiltroBeanEvaluacion" scope="session"/><jsp:setProperty name="filtroEvaluacion" property="*"/>
<jsp:useBean id="paramsVO" class="siges.observacion.vo.ParamsVO" scope="page"/>
<jsp:useBean id="params2VO" class="siges.evaluacion.beans.ParamsVO" scope="page"/>
<c:import url="/parametros.jsp"/>
<c:set var="tip" value="5" scope="page"/>
		<script languaje='javaScript'>
		<!--
			var fichaAsignatura=1;
			var fichaLogro=1;
			var fichaArea=1;
			var fichaDescriptor=1;
			var fichaRecuperacion=1;
			var fichaPreescolar=1;
			var fichaComportamiento=1;
			var fichaObservacion=1;
			var fichaObservacionEst=1;
			
			var nav4=window.Event ? true : false;
			function acepteNumeros(eve){
				var key=nav4?eve.which :eve.keyCode;
				return(key<=13 || (key>=48 && key<=57));
			}
			function setVisible(){if(document.getElementById('t1')){ document.getElementById('t1').style.display='none'; document.getElementById('t2').style.display='none';}}
			function hacerValidaciones_frmEvalLogro(forma){
				validarLista(forma.metodologia,"- Metodologia",1)
				validarLista(forma.grado,"- Grado",1)
				validarLista(forma.grupo,"- Grupo",1)
				validarLista(forma.asignatura,"- Asignatura",1)
				validarLista(forma.periodo2,"- Periodo actual",1)
				validarLista(forma.periodo,"- Periodo a recuperar",1)
				if(forma.filDocente){
					validarLista(forma.filDocente,"- Docente",1)
				}
			}

			function lanzar(tipo){				
				document.frmEvalLogro.tipo.value=tipo;					
				document.frmEvalLogro.action="<c:url value="/evaluacion/ControllerEvaluacionEdit.do"/>";
				document.frmEvalLogro.submit();
			}
			
			function lanzarServicio(n){
				document.frmEvalLogro.action='<c:url value="/observacion/NuevoEval.do"/>';  	
				document.frmEvalLogro.tipo.value=n;
				document.frmEvalLogro.cmd.value='<c:out value="${paramsVO.CMD_NUEVO}"/>';
				document.frmEvalLogro.target="";
				document.frmEvalLogro.submit();
			}
			function guardarEvalAsignatura(tipo){
				if(validarForma(document.frmEvalLogro)){
					if(document.frmEvalLogro.periodo.selectedIndex<document.frmEvalLogro.periodo2.selectedIndex){
						document.frmEvalLogro.metodologia_.value=document.frmEvalLogro.metodologia.options[document.frmEvalLogro.metodologia.selectedIndex].text;
						document.frmEvalLogro.grado_.value=document.frmEvalLogro.grado.options[document.frmEvalLogro.grado.selectedIndex].text;
						document.frmEvalLogro.grupo_.value=document.frmEvalLogro.grupo.options[document.frmEvalLogro.grupo.selectedIndex].text;
						document.frmEvalLogro.asignatura_.value=document.frmEvalLogro.asignatura.options[document.frmEvalLogro.asignatura.selectedIndex].text;
						document.frmEvalLogro.periodo_.value=document.frmEvalLogro.periodo.options[document.frmEvalLogro.periodo.selectedIndex].text;
						document.frmEvalLogro.tipo.value=tipo;
						document.frmEvalLogro.cmd.value="Buscar";
						document.frmEvalLogro.ext2.value='/evaluacion/EvaluacionGuardar2.jsp?tipo=5';  //peticion
						document.frmEvalLogro.submit();
					}else{
						alert("El periodo a recuperar no puede ser mayor o igual al periodo actual");						
					}					
				}
			}
			function cancelarEvalAsignatura(){
				if (confirm('¿Desea cancelar la evaluación?')){
 					document.frmEvalLogro.cmd.value="Cancelar";
					document.frmEvalLogro.submit(); 
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
				combo.options[0] = new Option("--Seleccione uno--","-1");
			}
			
			function filtro(combo_padre,combo_hijo){
				borrar_combo(combo_hijo);
				<c:if test="${!empty requestScope.filtroGrado2 && !empty requestScope.filtroGradoAsignatura}">var Padres = new Array();
					<c:forEach begin="0" items="${requestScope.filtroGrado2}" var="fila" varStatus="st">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
					<c:forEach begin="0" items="${requestScope.filtroGradoAsignatura}" var="fila2"><c:if test="${fila[0]==fila2[2]}">Sel_Hijos[k] = '<c:if test="${sessionScope.filtroEvaluacion.asignatura== fila2[0]}">SELECTED</c:if>'; id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k] = '<c:out value="${fila2[2]}"/>'; k++; </c:if></c:forEach>Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
					var niv=combo_padre.options[combo_padre.selectedIndex].value;
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
			function filtro2(combo_padre0,combo_padre,combo_hijo){
				borrar_combo(combo_hijo);
				<c:if test="${!empty requestScope.filtroGrado2 && !empty requestScope.filtroGrupo2}">var Padres = new Array();
					<c:forEach begin="0" items="${requestScope.filtroGrado2}" var="fila" varStatus="st">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
					<c:forEach begin="0" items="${requestScope.filtroGrupo2}" var="fila2"><c:if test="${fila[0]==fila2[2] && fila[2]==fila2[3]}">Sel_Hijos[k] = '<c:if test="${sessionScope.filtroEvaluacion.grupo== fila2[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k] = '<c:out value="${fila2[4]}"/>'; k++;</c:if></c:forEach>Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
					var niv=combo_padre.options[combo_padre.selectedIndex].value+'|'+combo_padre0.value;;
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
							<c:forEach begin="0" items="${requestScope.filtroGrado2}" var="fila2"><c:if test="${fila[0]==fila2[2] && (fila2[0]>=0 || fila2[0]==40 || fila2[0]==41)}">Sel_Hijos[k] = '<c:if test="${sessionScope.filtroEvaluacion.grado== fila2[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k] = '<c:out value="${fila2[2]}"/>'; k++;</c:if></c:forEach>Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
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
		if(document.frmEvalLogro.filDocente){
			borrar_combo(document.frmEvalLogro.filDocente); 
			document.frmAjax.ajax[0].value=document.frmEvalLogro.institucion.value;
			document.frmAjax.ajax[1].value=document.frmEvalLogro.metodologia.value;
			document.frmAjax.ajax[2].value=document.frmEvalLogro.vigencia.value;
			document.frmAjax.ajax[3].value=document.frmEvalLogro.grado.value;
			document.frmAjax.ajax[4].value=document.frmEvalLogro.asignatura.value;
			if(parseInt(document.frmAjax.ajax[4].value)!=-99){
				document.frmAjax.cmd.value=document.frmEvalLogro.AJAX_DOCENTE.value;
		 		document.frmAjax.target="frameAjax";
				document.frmAjax.submit();
			}	
		}	
	}
			//-->
		</script>
		<style type="text/css">
TD.norepeat{
background-image:url(../etc/img/tabs/pestana_1.gif); 
background-repeat:no-repeat;
}

TD.norepeat2{
background-image:url(../etc/img/tabs/pestana_0.gif); 
background-repeat:no-repeat;
cursor:pointer;
}

.sombra {
color:white;
text-shadow:#666666 3px 3px 3px;
filter: progid:DXImageTransform.Microsoft.Shadow(color='#666666', Direction=135, Strength=4);
font-size: 85%;
font-stretch: ultra-expanded;
line-height: 95%;
font-weight: bolder;
}
.sombran {
text-shadow:#666666 3px 3px 3px;
filter: progid:DXImageTransform.Microsoft.Shadow(color='#666666', Direction=135, Strength=4);
font-size: 85%;
font-stretch: ultra-expanded;
line-height: 95%;
font-weight: bolder;
}
</style>
<c:import url="/mensaje.jsp"/>
	<form method="post" name="frmAjax" action='<c:url value="/evaluacion/ControllerEvaluacionAjax.do"/>'>
		<INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${params2VO.EVAL_REC}"/>'>
		<input type="hidden" name="cmd" value='<c:out value="${params2VO.CMD_AJAX}"/>'>
		<c:forEach begin="0" end="5" var="i"><input type="hidden" name="ajax"></c:forEach>
	</form>
	<form method="post" name="frmEvalLogro"  onSubmit="return validarForma(frmEvalLogro)" action='<c:url value="/evaluacion/EvaluacionGuardar.jsp"/>'>
	<br>
	<table cellpadding="0" cellspacing="0" border="0" align="center" bordercolor="#FFFFFF" width="95%">
	<caption>Recuperación de logros</caption>
		<tr><td width="45%" bgcolor="#FFFFFF"></td></tr>
		<tr>
		  <td width="45%" bgcolor="#FFFFFF">
		    <input  class='boton' name="cmd1" type="button" value="Buscar" onClick="guardarEvalAsignatura(document.frmEvalLogro.tipo.value)">
			</td>
		</tr>
		<tr><td width="45%" bgcolor="#FFFFFF"></td></tr>
	</table>
<!--//////////////////-->		
	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="tipo" value='<c:out value="${pageScope.tip}"/>'>
	<input type="hidden" name="cmd" value="Cancelar">
	<INPUT TYPE="hidden" NAME="ext2"  VALUE=''><INPUT TYPE="hidden" NAME="height" VALUE='180'>
	<input type="hidden" name="grado_" value='<c:out value="${sessionScope.filtroEvaluacion.grado_}"/>'>
	<input type="hidden" name="grupo_" value='<c:out value="${sessionScope.filtroEvaluacion.grupo_}"/>'>
	<input type="hidden" name="asignatura_" value='<c:out value="${sessionScope.filtroEvaluacion.asignatura_}"/>'>
	<input type="hidden" name="periodo_" value='<c:out value="${sessionScope.filtroEvaluacion.periodo_}"/>'>
	<input type="hidden" name="AJAX_DOCENTE" value='<c:out value="${params2VO.CMD_AJAX_DOCENTE}"/>'>
	<input type="hidden" name="metodologia_" value='<c:out value="${sessionScope.filtroEvaluacion.metodologia_}"/>'>
	<input type="hidden" name="institucion" value='<c:out value="${sessionScope.filtroEvaluacion.institucion}"/>'>
	<input type="hidden" name="vigencia" value='<c:out value="${sessionScope.filtroEvaluacion.vigencia}"/>'>
	<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
		<tr height="1">
			<td width="2" bgcolor="#FFFFFF">&nbsp;</td>							
			<script>
			if(fichaAsignatura==1)document.write('<td width="14%" height="26"><a href="javaScript:lanzar(2)"><img src="../etc/img/tabs/Evaluacion_Asignaturas_0.gif" alt="Asignatura" width="84"  height="26" border="0"></a></td>');
			if(fichaLogro==1)document.write('<c:forEach begin="0" items="${sessionScope.logroanddesc}" var="logdes"><td width="14%" height="26" class="norepeat2" onclick="javaScript:lanzar(1)"><p align="center" class="sombran">Evaluación de <c:out value="${logdes[0]}"/></p></td></c:forEach>');
			if(fichaArea==1)document.write('<td width="14%" height="26"><a href="javaScript:lanzar(4)"><img src="../etc/img/tabs/Evaluacion_Areas_0.gif" alt="Area" width="84"  height="26" border="0"></a></td>');
			if(fichaDescriptor==1)document.write('<c:forEach begin="0" items="${sessionScope.logroanddesc}" var="logdes"><td width="14%" height="26" class="norepeat2" onclick="javaScript:lanzar(3)"><p align="center" class="sombran">Evaluación de <c:out value="${logdes[1]}"/></p></td></c:forEach>');
				if(fichaRecuperacion==1)document.write('<td width="14%" height="26"><img src="../etc/img/tabs/Recuperacion_1.gif" alt="-Logro-" width="84"  height="26" border="0"></td>');
				if(fichaPreescolar==1)document.write('<td width="14%" height="26"><a href="javaScript:lanzar(6)"><img src="../etc/img/tabs/Evaluacion_Preescolar_0.gif" alt="-Preesoclar-" width="84"  height="26" border="0"></a></td>');
				if(fichaComportamiento==1) document.write('<td width="14%" height="26"><a href="javaScript:lanzar(10)"><img src="../etc/img/tabs/Evaluacion_Comportamiento_0.gif" alt="-Comportamiento-" width="84"  height="26" border="0"></a></td>');
				if(fichaObservacion==1){var a='<c:out value="${paramsVO.FICHA_OBSERVACION_ASIGNATURA}"/>'; document.write('</tr><tr><td width="2" bgcolor="#FFFFFF"></td><td width="14%" height="26"><a href="javaScript:lanzarServicio('+a+')"><img src="../etc/img/tabs/observacion_asignatura_0.gif" alt="-Observación-" width="84"  height="26" border="0"></a></td>'); }
				if(fichaObservacionEst==1){var a='<c:out value="${paramsVO.FICHA_OBSERVACION_ESTUDIANTE}"/>'; document.write('<td width="14%" height="26"><a href="javaScript:lanzarServicio('+a+')"><img src="../etc/img/tabs/observacion_estudiante_0.gif" alt="-Observación Est.-" width="84"  height="26" border="0"></a></td>'); }
				</script>
		</tr>
  </table><!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
		<TABLE width="100%" cellpadding="3" cellSpacing="0">
				<tr>
					<td><span class="style2">*</span>Metodologia:</td>
					<td>
						<select name="metodologia" style='width:120px;' onChange='setVisible();filtro3(document.frmEvalLogro.metodologia,document.frmEvalLogro.grado, document.frmEvalLogro.grupo, document.frmEvalLogro.asignatura);'>
							<option value='-3'>-- Seleccione uno --</option>
							<c:forEach begin="0" items="${requestScope.filtroMetodologia}" var="fila">
								<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.filtroEvaluacion.metodologia==fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option>
							</c:forEach>
						</select>
					</td>	
					<td><span class="style2">*</span>Grado:</td>
					<td><select name="grado" style='width:120px;' onChange='setVisible();filtro2(document.frmEvalLogro.metodologia,document.frmEvalLogro.grado, document.frmEvalLogro.grupo);filtro(document.frmEvalLogro.grado, document.frmEvalLogro.asignatura)'><option value='-3'>-- Seleccione uno --</option></select></td>	
					<td><span class="style2">*</span>Grupo:</td>
					<td><select name="grupo" style='width:100px;' onChange='setVisible()'><option value='-1'>-- Seleccione uno --</option></select></td>
				</tr>
				<tr>
					<td><span class="style2">*</span>Asignatura:</td>
					<td><select name="asignatura" style='width:120px;' onChange='setVisible();ajaxDocente();'><option value='-1'>-- Seleccione una --</option></select></td>	
					
					<td><span class="style2" >*</span>Periodo actual:</td>
					<td><select name="periodo2" style='width:80px;' onChange='setVisible()'>
							<option value='-1'>-- Seleccione uno --</option>
							<c:forEach begin="0" items="${requestScope.filtroPeriodo}" var="per">
												<option value="<c:out value="${per.codigo}"/>" <c:if test="${per.codigo==sessionScope.filtroEvaluacion.periodo2}">selected</c:if>><c:out value="${per.nombre}"/></option>
						</c:forEach>
							
						</select>
					</td>
					<td><span class="style2" >*</span>Periodo a recuperar:</td>
					<td><select name="periodo" style='width:80px;' onChange='setVisible()'>
							<option value='-1'>-- Seleccione uno --</option>
							<c:forEach begin="0" items="${requestScope.filtroPeriodo}" var="per">
												<option value="<c:out value="${per.codigo}"/>" <c:if test="${per.codigo==sessionScope.filtroEvaluacion.periodo}">selected</c:if>><c:out value="${per.nombre}"/></option>
						</c:forEach>
						</select>
					</td>
				</tr>
				<tr>					
					<td>Orden:</td>
					<td>
						<select name="orden" style='width:120px;' onChange='setVisible()'>
							<option value='0' <c:if test="${sessionScope.filtroEvaluacion.orden=='0'}">SELECTED</c:if>>Apellidos</option>
							<option value='1' <c:if test="${sessionScope.filtroEvaluacion.orden=='1'}">SELECTED</c:if>>Nombres</option>
							<option value='2' <c:if test="${sessionScope.filtroEvaluacion.orden=='2'}">SELECTED</c:if>>Identificación</option>
							<option value='3' <c:if test="${sessionScope.filtroEvaluacion.orden=='3'}">SELECTED</c:if>>Código interno</option>
						</select>
					</td>
					<c:if test="${sessionScope.filtroEvaluacion.filPlanEstudios==1}">
						<th>Docente:</th>
						<td colspan="3">
							<select name="filDocente" style='width:250px;' >
								<option value='-99'>--Seleccione uno--</option>
								<c:forEach begin="0" items="${requestScope.listaDocente}" var="doc">
								<option value="<c:out value="${doc.codigo}"/>" <c:if test="${doc.codigo==sessionScope.filtroEvaluacion.filDocente}">selected</c:if>><c:out value="${doc.nombre}"/></option>
								</c:forEach>
							</select>
						</td>	
					</c:if>
				</tr>
				<tr style="display:none"><td><iframe name="frameAjax" id="frameAjax"></iframe></td></tr>
		</TABLE>
	</font>
<!--//////////////////////////////-->
</form>
<script>
filtro3(document.frmEvalLogro.metodologia,document.frmEvalLogro.grado, document.frmEvalLogro.grupo, document.frmEvalLogro.asignatura);
document.frmEvalLogro.grado.onchange();</script>