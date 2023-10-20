<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="filtroRecuperacionVO" class="siges.recuperacion.vo.FiltroRecuperacionVO" scope="session"/>
<jsp:useBean id="paramsVO" class="siges.recuperacion.vo.ParamsVO" scope="page"/>
<html>
<c:import url="/parametros.jsp"/>
<head>
<style type="text/css">@import url("<c:url value="/etc/css/calendar-win2k-1.css"/>");</style>
<script type="text/javascript" src='<c:url value="/etc/js/calendar.js"/>'></script>
<script type="text/javascript" src='<c:url value="/etc/js/lang/calendar-es.js"/>'></script>
<script type="text/javascript" src='<c:url value="/etc/js/calendar-setup.js"/>'></script>
<script language="javaScript">
<!--
	function lanzar(tipo){
  	document.frmFiltro.tipo.value=tipo;
		document.frmFiltro.target="";
		document.frmFiltro.submit();
	}
	
	function hacerValidaciones_frmFiltro(forma){
		validarLista(forma.filVigencia, "- Vigencia");
		validarLista(forma.filMetodologia, "- Metodologia");
		validarLista(forma.filGrado, "- Grado");
		validarLista(forma.filGrupo, "- Grupo");
		validarLista(forma.filAsignatura, "- Asignatura");
		validarLista(forma.filOrden, "- Orden");
	}
	
	function ponerNota(forma){
		if (!confirm('¿Está seguro de que quiere reemplazar la recuperación para todos?')) return;	
		pos=forma.recTodo.selectedIndex;
		var n=getCantidad(forma,"select-one","recNotas");
		if(n>1){
			for(var i=0;i<n;i++){
				forma.recNotas[i].selectedIndex=pos;
			}
		}else{
			forma.recNotas.selectedIndex=pos;
		}
	}

	function ponerFecha(forma){
		if (!confirm('¿Está seguro de que quiere reemplazar la fecha para todos?')) return;	
		pos=forma.recFecha0.value;
		var n=getCantidad(forma,"select-one","recNotas");
		if(n>1){
			for(var i=0;i<n;i++){
				document.getElementById('recFecha'+(i+1)).value=pos;
			}
		}else{
			document.getElementById('recFecha1').value=pos;
		}
	}

	function hacerValidaciones_frmNuevo(forma){
		var n=getCantidad(forma,"select-one","recNotas");
		if(n>1){
			for(var i=0;i<n;i++){
				if(forma.recNotas[i].selectedIndex>0){
					validarFechaUnCampo(document.getElementById('recFecha'+(i+1)), "- Fecha requerida si recupera (dd/mm/yyyy)");
				}else{
					document.getElementById('recFecha'+(i+1)).value='';
				}
			}
		}else{
			if(forma.recNotas.selectedIndex>0){
				validarFechaUnCampo(document.getElementById('recFecha1'), "- Fecha requerida si recupera (dd/mm/yyyy)");
			}else{
				document.getElementById('recFecha1').value='';
			}
		}
	}

	function validarDatos(forma){
		var n=getCantidad(forma,"select-one","recNotas");
		if(n>1){
			for(var i=0;i<n;i++){
				if(forma.recNotas[i].selectedIndex>0){
					forma.recFechas[i].value=document.getElementById('recFecha'+(i+1)).value;
				}else{
					forma.recFechas[i].value='';
				}
			}
		}else{
			if(forma.recNotas.selectedIndex>0){
				forma.recFechas.value=document.getElementById('recFecha1').value;
			}else{
				forma.recFechas.value='';
			}
		}
	}
	
	function buscar(){
		if(validarForma(document.frmFiltro)){
			document.frmFiltro.cmd.value=document.frmFiltro.BUSCAR.value;
			document.frmFiltro.submit();
		}
	}
	
	function cancelar(){
		document.frmFiltro.cmd.value=document.frmFiltro.CANCELAR.value;
		document.frmFiltro.submit();
	}
	
	function guardar(){
		if(validarForma(document.frmNuevo)){
			validarDatos(document.frmNuevo);
			document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
			document.frmNuevo.submit();
		}
	}
	
	function ajaxGrado(){
		borrar_combo(document.frmFiltro.filGrado); 
		document.frmAjax0.ajax[0].value=document.frmFiltro.filInstitucion.value;
		document.frmAjax0.ajax[1].value=document.frmFiltro.filMetodologia.value;
		if(parseInt(document.frmAjax0.ajax[1].value)!=-99){
			document.frmAjax0.cmd.value=document.frmFiltro.AJAX_GRADO.value;
	 		document.frmAjax0.target="frameAjax0";
			document.frmAjax0.submit();
		}	
	}

	function ajaxGrupo(){
		borrar_combo(document.frmFiltro.filGrupo); 
		borrar_combo(document.frmFiltro.filAsignatura); 
		document.frmAjax0.ajax[0].value=document.frmFiltro.filInstitucion.value;
		document.frmAjax0.ajax[1].value=document.frmFiltro.filSede.value;
		document.frmAjax0.ajax[2].value=document.frmFiltro.filJornada.value;
		document.frmAjax0.ajax[3].value=document.frmFiltro.filMetodologia.value;
		document.frmAjax0.ajax[4].value=document.frmFiltro.filGrado.value;
		document.frmAjax0.ajax[5].value=document.frmFiltro.filVigencia.value;
		if(parseInt(document.frmAjax0.ajax[4].value)!=-99){
			document.frmAjax0.cmd.value=document.frmFiltro.AJAX_GRUPO.value;
	 		document.frmAjax0.target="frameAjax0";
			document.frmAjax0.submit();
		}	
	}

	function ajaxVigencia(){
		document.frmFiltro.filVigencia.selectedIndex=0; 
	}
//-->
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form method="post" name="frmAjax0" action='<c:url value="/recuperacion/Ajax.do"/>'>
		<INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_RECUPERACION_ASIGNATURA}"/>'>
		<input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_AJAX}"/>'>
		<c:forEach begin="0" end="6" var="i"><input type="hidden" name="ajax"></c:forEach>
	</form>
	<form action='<c:url value="/recuperacion/SaveAsig.jsp"/>' method="post" name="frmFiltro" onSubmit="return validarForma(frmFiltro)">
		<input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_RECUPERACION_ASIGNATURA}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="CANCELAR" value='<c:out value="${paramsVO.CMD_CANCELAR}"/>'>
		<input type="hidden" name="AJAX_GRADO" value='<c:out value="${paramsVO.CMD_AJAX_GRADO}"/>'>
		<input type="hidden" name="AJAX_GRUPO" value='<c:out value="${paramsVO.CMD_AJAX_GRUPO}"/>'>
		<input type="hidden" name="filInstitucion" value='<c:out value="${sessionScope.filtroRecuperacionVO.filInstitucion}"/>'>
		<input type="hidden" name="filSede" value='<c:out value="${sessionScope.filtroRecuperacionVO.filSede}"/>'>
		<input type="hidden" name="filJornada" value='<c:out value="${sessionScope.filtroRecuperacionVO.filJornada}"/>'>

		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1">
				<td width="10">&nbsp;</td>
				<td rowspan="2" width="469">
					<a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_RECUPERACION_AREA}"/>)"><img src='<c:url value="/etc/img/tabs/recuperacion_area_0.gif"/>' alt="Area" height="26" border="0"></a>
					<img src='<c:url value="/etc/img/tabs/recuperacion_asig_1.gif"/>' alt="Asignatura" height="26" border="0">
				</td>
			</tr>
		</table>
		<table border="0" align="center" width="95%">
		 	<caption>Recuperación de asignatura</caption>
			<tr>
				<td>
					<c:if test="${sessionScope.NivelPermiso==2}">
						<input name="cmd1" type="button" value="Buscar" onClick="buscar()" class="boton">
						<input name="cmd1" type="button" value="Cancelar" onClick="cancelar()" class="boton">
						</c:if>
		  		</td>
		 	</tr>	
		</table>

		<table border="0" align="center" width="95%">
			<tr>
				<td><span class="style2">*</span> Metodología:</td>
				<td>
					<select name="filMetodologia" style="width:150px" onchange="javaScript:ajaxVigencia()" >
						<option value="-99" selected>--Seleccione uno--</option>
						<c:forEach begin="0" items="${requestScope.listaMetodologia}" var="met">
							<option value="<c:out value="${met.codigo}"/>" <c:if test="${met.codigo==sessionScope.filtroRecuperacionVO.filMetodologia}">selected</c:if>><c:out value="${met.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
				<td><span class="style2">*</span> Vigencia:</td>
				<td>
					<select name="filVigencia" style="width:80px" onchange="javaScript:ajaxGrado()">
						<option value="-99" selected>--Seleccione uno--</option>
						<c:forEach begin="0" items="${requestScope.listaVigencia}" var="vig">
							<option value="<c:out value="${vig.codigo}"/>" <c:if test="${vig.codigo==sessionScope.filtroRecuperacionVO.filVigencia}">selected</c:if>><c:out value="${vig.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>	
			<tr>
				<td><span class="style2">*</span> Grado:</td>
				<td>
					<select name="filGrado" style="width:120px" onchange="javaScript:ajaxGrupo()">
						<option value="-99" selected>--Seleccione uno--</option>
						<c:forEach begin="0" items="${requestScope.listaGrado}" var="grado">
							<option value="<c:out value="${grado.codigo}"/>" <c:if test="${grado.codigo==sessionScope.filtroRecuperacionVO.filGrado}">selected</c:if>><c:out value="${grado.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
				<td><span class="style2">*</span> Grupo:</td>
				<td>
					<select name="filGrupo" style="width:50px">
						<option value="-99" selected>--Seleccione uno--</option>
						<c:forEach begin="0" items="${requestScope.listaGrupo}" var="grupo">
							<option value="<c:out value="${grupo.codigo}"/>" <c:if test="${grupo.codigo==sessionScope.filtroRecuperacionVO.filGrupo}">selected</c:if>><c:out value="${grupo.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>	
			<tr>
				<td><span class="style2">*</span> Asignatura:</td>
				<td>
					<select name="filAsignatura" style="width:220px">
						<option value="-99" selected>--Seleccione uno--</option>
						<c:forEach begin="0" items="${requestScope.listaAsignatura}" var="asig">
							<option value="<c:out value="${asig.codigo}"/>" <c:if test="${asig.codigo==sessionScope.filtroRecuperacionVO.filAsignatura}">selected</c:if>><c:out value="${asig.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
				<td><span class="style2">*</span> Orden:</td>
				<td>
					<select name="filOrden" style="width:80px">
						<option value="-99" selected>--Seleccione uno--</option>
						<c:forEach begin="0" items="${requestScope.listaOrden}" var="orden">
							<option value="<c:out value="${orden.codigo}"/>" <c:if test="${orden.codigo==sessionScope.filtroRecuperacionVO.filOrden}">selected</c:if>><c:out value="${orden.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr style="display:none"><td><iframe name="frameAjax0" id="frameAjax0"></iframe></td></tr>
		</table><br> 	
		</form>
		<!-- TABLA DE LOS RESULTADOS DE LA BUSQUEDA -->
		<c:if test="${requestScope.recuperacionBuscar==1}">

	<form action='<c:url value="/recuperacion/SaveAsig.jsp"/>' method="post" name="frmNuevo">
		<input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_RECUPERACION_ASIGNATURA}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="CANCELAR" value='<c:out value="${paramsVO.CMD_CANCELAR}"/>'>
		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray">
		 	<caption>Lista de resultados de asignatura</caption>
		 	<c:if test="${empty requestScope.listaRecuperacion}"><tr><th class="Fila1" colspan='6'>No hay resultados</th></tr></c:if>
			<c:if test="${!empty requestScope.listaRecuperacion}">
				<tr><td colspan="6">
						<c:if test="${sessionScope.NivelPermiso==2}"><input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton"></c:if>
						<input name="cmd1" type="button" value="Cancelar" onClick="cancelar()" class="boton">
				</td>
				<tr>
				<tr>
					<th width='20' class="EncabezadoColumna">&nbsp;</th>
					<td class="EncabezadoColumna" align="center">Apellido</td>
					<td class="EncabezadoColumna" align="center">Nombre</td>
					<td class="EncabezadoColumna" align="center">Nota final</td>
					<td class="EncabezadoColumna" align="center">Recuperación<br>
							<select name="recTodo" style="width:40px" onchange='ponerNota(document.frmNuevo)'>
								<option value="0"> </option><c:forEach begin="0" items="${requestScope.listaNota}" var="nota"><option value='<c:out value="${nota.codigo}"/>'><c:out value="${nota.nombre}"/></option></c:forEach>
							</select>
					</td>
					<td class="EncabezadoColumna" align="center">Fecha<br>
						<input type="text" size="12" maxlength="10" id='recFecha0' name='recFecha0'>
						<img src='<c:url value="/etc/img/calendario.gif"/>' alt="Seleccione una fecha" id="imgfecha0" style="cursor: pointer; title="Date selector" onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
						<a href="javaScript:ponerFecha(document.frmNuevo);"><img src='<c:url value="/etc/img/flecha.gif"/>' alt="Poner la misma fecha a todos" title="Poner la misma fecha a todos" border="0"/></a>
					</td>
				</tr>
				<c:forEach begin="0" items="${requestScope.listaRecuperacion}" var="lista" varStatus="st">
					<tr>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.recConsecutivo}"/><input type="hidden" name="recCodigos" value='<c:out value="${lista.recCodigo}"/>'></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.recApellido}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.recNombre}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.recNotaFinal}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center">
							<select name="recNotas" style="width:40px">
								<option value="0"> </option>
								<c:forEach begin="0" items="${requestScope.listaNota}" var="nota">
									<option value='<c:out value="${nota.codigo}"/>' <c:if test="${nota.codigo==lista.recNotaRecuperacion}">selected</c:if>><c:out value="${nota.nombre}"/></option>
								</c:forEach>
							</select>
						</td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center">
							<input type="hidden" name="recFechas">							
							<input type="text" size="12" maxlength="10" id='recFecha<c:out value="${st.count}"/>' name='recFecha<c:out value="${st.count}"/>' value='<c:out value="${lista.recFecha}"/>'>
							<img src='<c:url value="/etc/img/calendario.gif"/>' alt="Seleccione una fecha" id="imgfecha<c:out value="${st.count}"/>" style="cursor: pointer; title="Date selector" onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
						</td>
					</tr>
				</c:forEach>
			</c:if>
		</table>
		</form>
			<script type="text/javascript">
					<c:if test="${!empty requestScope.listaRecuperacion}">Calendar.setup({inputField:"recFecha0",ifFormat:"%d/%m/%Y",button:"imgfecha0",align:"Br"});</c:if>
					<c:forEach begin="0" items="${requestScope.listaRecuperacion}" var="lista" varStatus="st">Calendar.setup({inputField:"recFecha<c:out value="${st.count}"/>",ifFormat:"%d/%m/%Y",button:"imgfecha<c:out value="${st.count}"/>",align:"Br"});</c:forEach>
			</script>

		</c:if>
</body>
</html>