<!--	VERSION		FECHA			AUTOR				DESCRIPCION
			1.0		23/02/2021		JORGE CAMACHO		CODIGO ESPAGUETI Y SE MOFIFICÓ EL DISEÑO DE LA PÁGINA
														CON LAS NUEVAS IMÁGENES Y QUE FUERA RESPONSIVE
			2.0		02/06/2021		JORGE CAMACHO		SE CAMBIA LA CODIFICACIÓN DE LA PAGINA DE UTF-8 A windows-1252
-->

<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage="" %>

<jsp:useBean id="filtroComportamiento" class="siges.evaluacion.beans.FiltroComportamiento" scope="session"/><jsp:setProperty name="filtroComportamiento" property="*"/>
<jsp:useBean id="paramsVO" class="siges.observacion.vo.ParamsVO" scope="page"/>

<%@include file="../parametros.jsp"%>

<c:set var="tip" value="10" scope="page"/>

<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link href='<c:url value="/etc/css/2020/apoyo_escolar_2020.css"/>' rel="stylesheet" type="text/css">

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
			var key=nav4?eve.which :eve.keyCode;
			return(key<=13 || (key>=48 && key<=57));
		}
		
		function setVisible(){
			if(document.getElementById('t1')){ 
				document.getElementById('t1').style.display='none'; 
				document.getElementById('t2').style.display='none';
			}
		}
		
		function hacerValidaciones_frmFiltro(forma){
			validarLista(forma.filMetodologia,"- Metodologia",1)
			validarLista(forma.filGrado,"- Grado",1)
			validarLista(forma.filGrupo,"- Grupo",1)
			validarLista(forma.filPeriodo,"- Periodo",1)
		}
		
		function lanzar(tipo){
			document.frmFiltro.tipo.value=tipo;
			document.frmFiltro.action='<c:url value="/evaluacion/ControllerEvaluacionEdit.do"/>';
			document.frmFiltro.submit();
		}
		
		function lanzarServicio(n){
			document.frmFiltro.action='<c:url value="/observacion/NuevoEval.do"/>';  	
			document.frmFiltro.tipo.value=n;
			document.frmFiltro.cmd.value='<c:out value="${paramsVO.CMD_NUEVO}"/>';
			document.frmFiltro.target="";
			document.frmFiltro.submit();
		}
				
		function buscar(tipo){
			if(validarForma(document.frmFiltro)){
				document.frmFiltro.tipo.value=tipo;
				document.frmFiltro.cmd.value="Buscar";
				document.frmFiltro.ext2.value='/evaluacion/EvaluacionGuardar2.jsp?tipo=10';  //peticion
				document.frmFiltro.submit();
			}
		}
				
		function cancelar(){
			if (confirm('¿Desea cancelar la evaluación?')){
				document.frmFiltro.cmd.value="Cancelar";
				document.frmFiltro.submit(); 
			}
		}
				
		function Padre(id_Hijos, Hijos, Sel_Hijos, id_Padre){
			this.id_Hijos = id_Hijos;
			this.Hijos = Hijos;
			this.Sel_Hijos = Sel_Hijos;
			this.id_Padre = id_Padre;
		}
		
		function borrar_combo(combo){
			for(var i = combo.length - 1; i >= 0; i--) {
				if(navigator.appName == "Netscape") combo.options[i] = null;
				else combo.remove(i);
			}
			combo.options[0] = new Option("--Seleccione uno--","-99");
		}
				
		function filtroMetodologia(combo_padre,combo_hijo){
			borrar_combo(combo_hijo); 
				var Padres = new Array();
					<c:forEach begin="0" items="${requestScope.lMetodologia}" var="metod" varStatus="st">
						id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
					<c:forEach begin="0" items="${requestScope.lGrado}" var="grado">
					<c:if test="${metod.codigo==grado.padre}">Sel_Hijos[k] = '<c:if test="${sessionScope.filtroComportamiento.filGrado==grado.codigo}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${grado.codigo}"/>'; Hijos[k] = '<c:out value="${grado.nombre}"/>'; id_Padre[k] = '<c:out value="${grado.padre}"/>'; k++;</c:if></c:forEach>Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
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
			}
		}
		
		function filtroGrado(combo_abuelo,combo_padre,combo_hijo){
			borrar_combo(combo_hijo); 
			var Padres = new Array();
			<c:forEach begin="0" items="${requestScope.lGrado}" var="grado" varStatus="st">
				id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
			<c:forEach begin="0" items="${requestScope.lGrupo}" var="grupo">
			<c:if test="${grado.codigo==grupo.padre && grado.padre==grupo.padre2}">Sel_Hijos[k] = '<c:if test="${sessionScope.filtroComportamiento.filGrupo==grupo.codigo}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${grupo.codigo}"/>'; Hijos[k] = '<c:out value="${grupo.nombre}"/>'; id_Padre[k] = '<c:out value="${grupo.padre2}"/>|<c:out value="${grupo.padre}"/>'; k++;</c:if></c:forEach>Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
			var niv=combo_abuelo.options[combo_abuelo.selectedIndex].value+'|'+combo_padre.options[combo_padre.selectedIndex].value;
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
			}
		}
		
	//-->
</script>

<%@include file="../mensaje.jsp"%>

<form method="post" name="frmFiltro" class="formulario-evaluacion-general" onSubmit="return validarForma(frmFiltro)" action='<c:url value="/evaluacion/Save.jsp"/>'>
	<table cellpadding="0" cellspacing="0" border="0" align="center" bordercolor="#FFFFFF" width="95%">
		<caption>Evaluación de Comportamiento</caption>
  	</table>

	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="tipo" value='<c:out value="${pageScope.tip}"/>'>
	<input type="hidden" name="cmd" value="Cancelar">
	<input type="hidden" name="ext2"  value=''>
	<input type="hidden" name="height" value='auto'>

	<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" class="menu_top">
	<tr height="1">
		<script languaje='javaScript'>
			if(fichaAsignatura==1)document.write('<td width="14%" height="26"><a href="javaScript:lanzar(2)">Evaluación de Asignaturas</a></td>');
			if(fichaLogro==1)document.write('<c:forEach begin="0" items="${sessionScope.logroanddesc}" var="logdes"><td width="14%" height="26"><a onclick="javaScript:lanzar(1)">Evaluación de <c:out value="${logdes[0]}"/></a></td></c:forEach>');
			if(fichaArea==1)document.write('<td width="14%" height="26"><a href="javaScript:lanzar(4)">Evaluación de Áreas</a></td>');
			if(fichaDescriptor==1)document.write('<c:forEach begin="0" items="${sessionScope.logroanddesc}" var="logdes"><td width="14%" height="26"><a onclick="javaScript:lanzar(3)">Evaluación de <c:out value="${logdes[1]}"/></a></td></c:forEach></tr><tr>');
			/* if(fichaRecuperacion==1)document.write('<td width="14%" height="26"><a href="javaScript:lanzar(5)"><img src="../etc/img/tabs/Recuperacion_0.gif" alt="Logros" width="84"  height="26" border="0"></a></td>'); */
			if(fichaPreescolar==1)document.write('<td width="14%" height="26"><a href="javaScript:lanzar(6)">Evaluación por Dimensiones</a></td>');
			if(fichaComportamiento==1)document.write('<td width="14%" height="26"><a class="active" href="javaScript:lanzar(10)">Evaluación por Comportamiento</a></td>');
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
				<select name="filMetodologia" style='width:120px;' onChange='setVisible();filtroMetodologia(document.frmFiltro.filMetodologia,document.frmFiltro.filGrado);'>
					<option value='-99'>-- Seleccione uno --</option>
					<c:forEach begin="0" items="${requestScope.lMetodologia}" var="metod">
						<option value="<c:out value="${metod.codigo}"/>" <c:if test="${sessionScope.filtroComportamiento.filMetodologia==metod.codigo}">SELECTED</c:if>><c:out value="${metod.nombre}"/></option>
					</c:forEach>
				</select>
			</td>	
			<td><span class="style2">*</span>Grado:</td>
			<td>
				<select name="filGrado" style='width:120px;' onChange='setVisible();filtroGrado(document.frmFiltro.filMetodologia,document.frmFiltro.filGrado,document.frmFiltro.filGrupo);'>
					<option value='-99'>-- Seleccione uno --</option>
					<c:forEach begin="0" items="${requestScope.lGrado}" var="grado">
						<option value="<c:out value="${grado.codigo}"/>" <c:if test="${sessionScope.filtroComportamiento.filGrado==grado.codigo}">SELECTED</c:if>><c:out value="${grado.nombre}"/></option>
					</c:forEach>
				</select>
			</td>	
			<td><span class="style2">*</span>Grupo:</td>
			<td>
				<select name="filGrupo" style='width:100px;' onChange='setVisible()'>
					<option value='-99'>-- Seleccione uno --</option>
				</select>
			</td>
		</tr>
		<tr>
			<td><span class="style2">*</span>Periodo:</td>
			<td>
				<select name="filPeriodo" style='width:100px;' onChange='setVisible()'>
					<option value='-99'>-- Seleccione uno --</option>
					<c:forEach begin="0" items="${requestScope.lPeriodo}" var="per">
						<option value="<c:out value="${per.codigo}"/>" <c:if test="${per.codigo==sessionScope.filtroComportamiento.filPeriodo}">selected</c:if>><c:out value="${per.nombre}"/></option>
					</c:forEach>
				</select>
			</td>
			<td>Orden:</td>
			<td>
				<select name="filOrden" style='width:100px;' onChange='setVisible()'>
					<option value='0' <c:if test="${sessionScope.filtroComportamiento.filOrden== '0'}">SELECTED</c:if>>Apellidos</option>
					<option value='1' <c:if test="${sessionScope.filtroComportamiento.filOrden== '1'}">SELECTED</c:if>>Nombres</option>
					<option value='2' <c:if test="${sessionScope.filtroComportamiento.filOrden== '2'}">SELECTED</c:if>>Identificación</option>
				</select>
			</td>
		</tr>
		<tr>
			<td colspan="8" align="center">
				<input class='boton' name="cmd1" type="button" value="Buscar" onClick="buscar(document.frmFiltro.tipo.value)">
			</td>
		</tr>
	</table>

</form>

<script>
	filtroMetodologia(document.frmFiltro.filMetodologia,document.frmFiltro.filGrado);
	filtroGrado(document.frmFiltro.filMetodologia,document.frmFiltro.filGrado,document.frmFiltro.filGrupo);
</script>
