<!--	VERSION		FECHA			AUTOR				DESCRIPCION
			1.0		23/02/2021		JORGE CAMACHO		CODIGO ESPAGUETI Y SE MOFIFICÓ EL DISEÑO DE LA PÁGINA
														CON LAS NUEVAS IMÁGENES Y QUE FUERA RESPONSIVE
			2.0		02/06/2021		JORGE CAMACHO		SE CAMBIA LA CODIFICACIÓN DE LA PAGINA DE UTF-8 A windows-1252
-->

<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage="" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="paramsVO" class="siges.observacion.vo.ParamsVO" scope="page"/>
<jsp:useBean id="params2VO" class="siges.evaluacion.beans.ParamsVO" scope="page"/>
<jsp:useBean id="filtroEvaluacion" class="siges.evaluacion.beans.FiltroBeanEvaluacion" scope="session"/>

<c:import url="/parametros.jsp"/>

<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link href='<c:url value="/etc/css/2020/apoyo_escolar_2020.css"/>' rel="stylesheet" type="text/css">

<c:set var="tip" value="1" scope="page"/>

<script languaje='javaScript'>
	<!--
		var fichaArea=1;
		var fichaLogro=1;
		var fichaDescriptor=1;
		var fichaPreescolar=1;
		var fichaAsignatura=1;
		var fichaObservacion=1;
		var fichaRecuperacion=1;
		var fichaComportamiento=1;
		var fichaObservacionEst=1;
		var nav4=window.Event ? true : false;

		function acepteNumeros(eve){
			var key = nav4 ? eve.which : eve.keyCode;
			return(key<=13 || (key>=48 && key<=57));
		}

		function setVisible(){
			if(document.getElementById('t1')){
				document.getElementById('t1').style.display='none';
				document.getElementById('t2').style.display='none';
			}
		}

		function hacerValidaciones_frmEvalLogro(forma){
			validarLista(forma.metodologia,"- Metodologia",1)
			validarLista(forma.grado,"- Grado",1)
			validarLista(forma.grupo,"- Grupo",1)
			validarLista(forma.asignatura,"- Asignatura",1)
			if(forma.filDocente){
				validarLista(forma.filDocente,"- Docente",1)
			}
			validarLista(forma.periodo,"- Periodo",1)
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
				document.frmEvalLogro.metodologia_.value=document.frmEvalLogro.metodologia.options[document.frmEvalLogro.metodologia.selectedIndex].text;
				document.frmEvalLogro.grado_.value=document.frmEvalLogro.grado.options[document.frmEvalLogro.grado.selectedIndex].text;
				document.frmEvalLogro.grupo_.value=document.frmEvalLogro.grupo.options[document.frmEvalLogro.grupo.selectedIndex].text;
				document.frmEvalLogro.asignatura_.value=document.frmEvalLogro.asignatura.options[document.frmEvalLogro.asignatura.selectedIndex].text;
				document.frmEvalLogro.periodo_.value=document.frmEvalLogro.periodo.options[document.frmEvalLogro.periodo.selectedIndex].text;
				document.frmEvalLogro.tipo.value=tipo;
				document.frmEvalLogro.cmd.value="Buscar";
				document.frmEvalLogro.ext2.value='/evaluacion/EvaluacionGuardar2.jsp?tipo=1';  //peticion
				document.frmEvalLogro.submit();
			}
		}
		
		function cancelarEvalAsignatura(){
			if (confirm('Desea cancelar la evaluación?')){
				document.frmEvalLogro.cmd.value="Cancelar";
				document.frmEvalLogro.submit(); 
			}
		}
		
		function Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre){
			this.id_Hijos = id_Hijos; this.Hijos= Hijos;
			this.Sel_Hijos = Sel_Hijos;
			this.id_Padre=id_Padre;
		}
		
		function borrar_combo(combo){
			for(var i = combo.length - 1; i >= 0; i--) {
				if(navigator.appName == "Netscape") combo.options[i] = null;
				else combo.remove(i);
			}
			combo.options[0] = new Option("--Seleccione uno--","-1");
		}
		
		function filtro(combo_padre0, combo_padre, combo_hijo){
			borrar_combo(combo_hijo);
			<c:if test="${!empty requestScope.filtroGrado2 && !empty requestScope.filtroGradoAsignatura}">var Padres = new Array();
				<c:forEach begin="0" items="${requestScope.filtroGrado2}" var="fila" varStatus="st">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
					<c:forEach begin="0" items="${requestScope.filtroGradoAsignatura}" var="fila2"><c:if test="${fila[0]==fila2[2] && fila[2]==fila2[3]}">Sel_Hijos[k] = '<c:if test="${sessionScope.filtroEvaluacion.asignatura== fila2[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k] = '<c:out value="${fila2[4]}"/>'; k++;</c:if></c:forEach>Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
				var niv=combo_padre.options[combo_padre.selectedIndex].value+'|'+combo_padre0.value;
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

<c:import url="/mensaje.jsp"/>

<form method="post" name="frmAjax" action='<c:url value="/evaluacion/ControllerEvaluacionAjax.do"/>'>
	<input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${params2VO.EVAL_LOG}"/>'>
	<input type="hidden" name="cmd" value='<c:out value="${params2VO.CMD_AJAX}"/>'>
	<c:forEach begin="0" end="5" var="i">
		<input type="hidden" name="ajax">
	</c:forEach>
</form>

<form method="post" name="frmEvalLogro" class="formulario-evaluacion-general" onSubmit="return validarForma(frmEvalLogro)" action='<c:url value="/evaluacion/EvaluacionGuardar.jsp"/>'>
	<table cellpadding="0" cellspacing="0" border="0" align="center" bordercolor="#FFFFFF" width="95%">
		<caption>Evaluación de logro</caption>
	</table>
	
	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="tipo" value='<c:out value="${pageScope.tip}"/>'>
	<input type="hidden" name="cmd" value="Cancelar">
	<input TYPE="hidden" name="ext2" value=''>
	<input TYPE="hidden" name="height" value='auto'>
	<input type="hidden" name="AJAX_DOCENTE" value='<c:out value="${params2VO.CMD_AJAX_DOCENTE}"/>'>
	<input type="hidden" name="metodologia_" value='<c:out value="${sessionScope.filtroEvaluacion.metodologia_}"/>'>
	<input type="hidden" name="institucion" value='<c:out value="${sessionScope.filtroEvaluacion.institucion}"/>'>
	<input type="hidden" name="vigencia" value='<c:out value="${sessionScope.filtroEvaluacion.vigencia}"/>'>
	<input type="hidden" name="grado_" value='<c:out value="${sessionScope.filtroEvaluacion.grado_}"/>'>
	<input type="hidden" name="grupo_" value='<c:out value="${sessionScope.filtroEvaluacion.grupo_}"/>'>
	<input type="hidden" name="asignatura_" value='<c:out value="${sessionScope.filtroEvaluacion.asignatura_}"/>'>
	<input type="hidden" name="periodo_" value='<c:out value="${sessionScope.filtroEvaluacion.periodo_}"/>'>
	
	<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" class="menu_top">
		<tr height="1">
			<script languaje='javaScript'>
				if(fichaAsignatura==1)document.write('<td width="14%" height="26"><a href="javaScript:lanzar(2)">Evaluación de Asignaturas</a></td>');
				if(fichaLogro==1)document.write('<c:forEach begin="0" items="${sessionScope.logroanddesc}" var="logdes"><td width="14%" height="26"><a class="active" onclick="javaScript:lanzar(1)">Evaluación de <c:out value="${logdes[0]}"/></a></td></c:forEach>');
				if(fichaArea==1)document.write('<td width="14%" height="26"><a href="javaScript:lanzar(4)">Evaluación de Áreas</a></td>');
				if(fichaDescriptor==1)document.write('<c:forEach begin="0" items="${sessionScope.logroanddesc}" var="logdes"><td width="14%" height="26"><a onclick="javaScript:lanzar(3)">Evaluación de <c:out value="${logdes[1]}"/></a></td></c:forEach></tr><tr>');
				/* if(fichaRecuperacion==1)document.write('<td width="14%" height="26"><a href="javaScript:lanzar(5)">Recuperación</a></td></tr><tr>'); */
				if(fichaPreescolar==1)document.write('<td width="14%" height="26"><a href="javaScript:lanzar(6)">Evaluación por Dimensiones</a></td>');
				if(fichaComportamiento==1)document.write('<td width="14%" height="26"><a href="javaScript:lanzar(10)">Evaluación por Comportamiento</a></td>');
				if(fichaObservacion==1){var a='<c:out value="${paramsVO.FICHA_OBSERVACION_ASIGNATURA}"/>'; 
					document.write('<td width="14%" height="26"><a href="javaScript:lanzarServicio('+a+')">Observación por Asignatura</a></td>'); }
				if(fichaObservacionEst==1){var a='<c:out value="${paramsVO.FICHA_OBSERVACION_ESTUDIANTE}"/>'; 
					document.write('<td width="14%" height="26"><a href="javaScript:lanzarServicio('+a+')">Observación por Estudiante</a></td>'); }
			</script>
		</tr>
  	</table>
  	<!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->

	<table width="100%" cellpadding="3" cellSpacing="0" class="tabla-filtros">
		<tr>
			<td><span class="style2">*</span>Metodología:</td>
			<td>
				<select name="metodologia" style='width:120px;' onChange='setVisible();filtro3(document.frmEvalLogro.metodologia,document.frmEvalLogro.grado,document.frmEvalLogro.grupo,document.frmEvalLogro.asignatura);'>
					<option value='-3'>-- Seleccione uno --</option>
					<c:forEach begin="0" items="${requestScope.filtroMetodologia}" var="fila">
						<option value='<c:out value="${fila[0]}"/>' <c:if test="${sessionScope.filtroEvaluacion.metodologia==fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option>
					</c:forEach>
				</select>
			</td>	
			<td><span class="style2">*</span>Grado:</td>
			<td>
				<select name="grado" style='width:120px;' onChange='setVisible();filtro2(document.frmEvalLogro.metodologia,document.frmEvalLogro.grado,document.frmEvalLogro.grupo);filtro(document.frmEvalLogro.metodologia,document.frmEvalLogro.grado,document.frmEvalLogro.asignatura)'>
					<option value='-3'>-- Seleccione uno --</option>
				</select>
			</td>	
			<td><span class="style2">*</span>Grupo:</td>
			<td>
				<select name="grupo" style='width:120px;' onChange='setVisible()'>
					<option value='-1'>-- Seleccione uno --</option>
				</select>
			</td>
		</tr>
		<tr>
			<td><span class="style2">*</span>Asignatura:</td>
			<td>
				<select name="asignatura" style='width:120px;' onChange='setVisible();ajaxDocente();'>
					<option value='-1'>-- Seleccione una --</option>
				</select>
			</td>	
			<td><span class="style2" >*</span>Periodo:</td>
			<td>
				<select name="periodo" style='width:120px;' onChange='setVisible()'>
					<option value='-1'>-- Seleccione uno --</option>
					<c:forEach begin="0" items="${requestScope.filtroPeriodo}" var="per">
						<option value="<c:out value="${per.codigo}"/>" <c:if test="${per.codigo==sessionScope.filtroEvaluacion.periodo}">selected</c:if>><c:out value="${per.nombre}"/></option>
					</c:forEach>
				</select>
			</td>
			<td>Orden:</td>
			<td>
				<select name="orden" style='width:120px;' onChange='setVisible()'>
					<option value='0' <c:if test="${sessionScope.filtroEvaluacion.orden=='0'}">SELECTED</c:if>>Apellidos</option>
					<option value='1' <c:if test="${sessionScope.filtroEvaluacion.orden=='1'}">SELECTED</c:if>>Nombres</option>
					<option value='2' <c:if test="${sessionScope.filtroEvaluacion.orden=='2'}">SELECTED</c:if>>Identificación</option>
					<option value='3' <c:if test="${sessionScope.filtroEvaluacion.orden=='3'}">SELECTED</c:if>>Código interno</option>
				</select>
			</td>
		</tr>
		<tr>
			<td colspan="8" align="center">
				<input class='boton' name="cmd1" type="button" value="Buscar" onClick="guardarEvalAsignatura(document.frmEvalLogro.tipo.value)">
			</td>
		</tr>
		<c:if test="${sessionScope.filtroEvaluacion.filPlanEstudios==1}">
		<tr>
			<th>Docente:</th>
			<td colspan="3">
				<select name="filDocente" style='width:250px;' >
					<option value='-99'>--Seleccione uno--</option>
					<c:forEach begin="0" items="${requestScope.listaDocente}" var="doc">
						<option value="<c:out value="${doc.codigo}"/>" <c:if test="${doc.codigo==sessionScope.filtroEvaluacion.filDocente}">selected</c:if>><c:out value="${doc.nombre}"/></option>
					</c:forEach>
				</select>
			</td>	
		</tr>
		</c:if>
		<tr style="display:none">
			<td>
				<iframe name="frameAjax" id="frameAjax"></iframe>
			</td>
		</tr>
	</table>

</form>

<script>
	filtro3(document.frmEvalLogro.metodologia,document.frmEvalLogro.grado, document.frmEvalLogro.grupo, document.frmEvalLogro.asignatura);
	document.frmEvalLogro.grado.onchange();
</script>
