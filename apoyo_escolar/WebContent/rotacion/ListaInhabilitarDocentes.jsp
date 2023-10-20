<jsp:useBean id="rotacion" class="siges.rotacion.beans.Rotacion" scope="session"/>
<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage="" %>
<%@include file="../parametros.jsp"%>
<SCRIPT language=javascript src='<c:url value="/etc/js/rotacion.js"/>'></SCRIPT>
<script language="javaScript">
<!--
var locked=<c:out value="${sessionScope.isLockedRotacion}"/>;

	function hacerValidaciones_frmLista(forma){
		validarSeleccion(forma.id, "- Debe seleccionar un item");			
	}
	
	function cancelar(){
		location.href='<c:url value="/bienvenida.jsp"/>';
	}
	
	function editar(n){
		if(document.frmLista.id){
				document.frmLista.id.value=n;
				document.frmLista.tipo.value='52';
				document.frmLista.cmd.value='Editar';
				document.frmLista.action='<c:url value="/rotacion/ControllerEditar.do"/>';
				document.frmLista.submit();
		}
	}
	
	function eliminar(n){
		if(document.frmLista.id){
			if(locked!=0){
				alert('Registro solo para consulta');
				return;
			}
				if(confirm('¿DESEA ELIMINAR ESTE REGISTRO?')){
					document.frmLista.id.value=n;
					document.frmLista.tipo.value='53';
					document.frmLista.cmd.value='Eliminar';
					document.frmLista.action='<c:url value="/rotacion/ControllerEditar.do"/>';
					document.frmLista.submit();
				}
		}
	}

	function setheight(tipo){
		switch(tipo){
			case 0:	document.frmLista.height.value=Htipo_receso;		break;
			case 10:	document.frmLista.height.value=Hestructura;		break;
			case 20:	document.frmLista.height.value=Hfijar_espacio;		break;
			case 60:	document.frmLista.height.value=Hfijar_asignatura;		break;
			case 70:	document.frmLista.height.value=Hfijar_espacio_docente;		break;
			case 40:	document.frmLista.height.value=Hinhabilitar_espacio;		break;
			case 50:	document.frmLista.height.value=Hinhabilitar_docentes;		break;
			case 80:	document.frmLista.height.value=Hpriorizar;		break;
			case 100:	document.frmLista.height.value=Hfijar_horario;		break;
			case 110:	document.frmLista.height.value=Hborrar_horario;		break;
			case 120:	document.frmLista.height.value=Hinhabilitar_hora;		break;
		}
	}
	
	function lanzar(tipo){
		setheight(tipo);
		document.frmLista.tipo.value=tipo;
		document.frmLista.action='<c:url value="/rotacion/ControllerEditar.do"/>';
		document.frmLista.target="";
		document.frmLista.submit();
	}

	function lanzar2(tipo){
		setheight(tipo);
		document.frmLista.tipo.value=tipo;
		document.frmLista.action='<c:url value="/rotacion/ControllerEditar.do"/>';
		document.frmLista.target="";
		document.frmLista.submit();
	}

	function buscar(){
		document.frmLista.tipo.value='59';
		document.frmLista.cmd.value='Buscar';
		document.frmLista.action='<c:url value="/rotacion/FiltroSave.jsp"/>';
		document.frmLista.submit();
	}
//-->
</script>

<%@include file="../mensaje.jsp" %>
		<form action="" method="post" name="frmLista" onSubmit="return validarForma(frmLista)">
			<input type="hidden" name="cmd" value="">
			<INPUT TYPE="hidden" NAME="height" VALUE="180">
			<INPUT TYPE="hidden" NAME="tipo" VALUE="">
			<input type="hidden" name="ext" value="">
			<input type="hidden" name="id" value="">
			<input type="hidden" name="guia" value="<c:out value="${requestScope.guia}"/>">

			<table>
				<tr>
					<td rowspan="2" width="600">
						<a href="javaScript:lanzar(10)"><img src='<c:url value="/etc/img/tabs/estructura_0.gif"/>' alt='Estructura' height="26" border="0"></a>
						<a href="javaScript:lanzar(20)"><img src="../etc/img/tabs/espacios_0.gif" alt="Fijar Espacios Físicos" height="26" border="0"></a>
						<a href="javaScript:lanzar(60)"><img src='<c:url value="/etc/img/tabs/fijar_asignatura_0.gif"/>' alt='Fijar Asignatura' height="26" border="0"></a>
						<a href="javaScript:lanzar(70)"><img src='<c:url value="/etc/img/tabs/espacio_docente_0.gif"/>' alt='Fijar Espacio por Docente' height="26" border="0"></a>
						<a href="javaScript:lanzar(40)"><img src="../etc/img/tabs/espfis_jornada_0.gif" alt="Inhabilitar Espacios Físicos" height="26" border="0"></a>
						<img src='<c:url value="/etc/img/tabs/inhabilitar_docentes_1.gif"/>' alt='Inhabilitar Docentes' height="26" border="0"><br>
						<a href="javaScript:lanzar2(80)"><img src='<c:url value="/etc/img/tabs/priorizar_0.gif"/>' alt='Priorizar' height="26" border="0"></a>
						<a href="javaScript:lanzar2(100)"><img src='<c:url value="/etc/img/tabs/fijar_horario_0.gif"/>' alt='Fijar horario' height="26" border="0"></a>
						<a href="javaScript:lanzar2(110)"><img src='<c:url value="/etc/img/tabs/borrar_horario_0.gif"/>' alt='Borrar horario' height="26" border="0"></a>
						<a href="javaScript:lanzar(120)"><img src='<c:url value="/etc/img/tabs/inhabilitar_horas_0.gif"/>' alt='Inhabilitar Horas' height="26" border="0"></a>
						<a href="javaScript:lanzar(130)"><img src='<c:url value="/etc/img/tabs/docente_grupo_0.gif"/>' alt='Docente por grupo' height="26" border="0"></a>
						<a href="javaScript:lanzar(140)"><img src='<c:url value="/etc/img/tabs/espacio_grado_0.gif"/>' alt='Espacio por grado' height="26" border="0"></a>
					</td>
				</tr>
			</table>

		  <table border="0" align="center" width="95%" cellpadding="0" cellspacing="0">
			  <caption>Filtro de Búsqueda.</caption>
			  <tr><td colspan="2"><input name="cmd2" class="boton" type="button" value="Buscar" onClick="buscar()"></td></tr>
			  <tr><td align="right"><span class="style2">*</span> Vigencia: &nbsp;&nbsp;&nbsp;</td>
						<td><select name="filAnhoVigencia" style='width:50px;'>
							<c:forEach begin="0" items="${requestScope.listaVigencia}" var="vig">
								<option value="<c:out value="${vig}"/>" <c:if test="${vig==sessionScope.filtroRotacion.filAnhoVigencia}">selected</c:if>><c:out value="${vig}"/></option>
							</c:forEach>
						</select></td>
						<td align="right"><span class="style2">*</span> Metodologia: &nbsp;&nbsp;&nbsp;</td>
						<td>
						<select name="filMetodologia" style='width:150px;'>
							<c:forEach begin="0" items="${requestScope.listaMetodologia}" var="vig">
								<option value="<c:out value="${vig.codigo}"/>" <c:if test="${vig.codigo==sessionScope.filtroRotacion.filMetodologia}">selected</c:if>><c:out value="${vig.nombre}"/></option>
							</c:forEach>
						</select>
						</td>
			  </tr>
		  </table>
		  <table border="1" align="center" width="95%" cellpadding="0" cellspacing="0">
		  	<caption>Resultado de la Búsqueda.</caption>
			  <c:if test="${empty requestScope.inhdocente}">
					<tr>
						<th class="Fila1" colspan='6'>LA BÚSQUEDA NO ARROJO RESULTADOS</th>
					</tr>
				</c:if>
		  	<c:if test="${!empty requestScope.inhdocente}">
			  	<tr>
						<th width='35' class="EncabezadoColumna">&nbsp;</th>
						<th class="EncabezadoColumna">Estructura</th>
						<th class="EncabezadoColumna">Docente</th>
						<th class="EncabezadoColumna">D&iacute;a</th>
						<th class="EncabezadoColumna">Hora Inicio</th>
						<th class="EncabezadoColumna">Hora Fin</th>
					</tr>
					<c:forEach begin="0" items="${requestScope.inhdocente}" var="fila" varStatus="st">
						<tr>
							<c:set var="guiab"><c:out value="${fila.inhEstructura}"/>|<c:out value="${fila.inhDocente}"/>|<c:out value="${fila.inhDia}"/>|<c:out value="${fila.inhHoraIni}"/>|<c:out value="${fila.inhHoraFin}"/></c:set>
							<td class='Fila<c:out value="${st.count%2}"/>' >
								<a href='javaScript:editar("<c:out value="${guiab}"/>");'><img border='0' src='../etc/img/<c:if test="${requestScope.guia==guiab}">X</c:if>editar.png' width='15' height='15'></a>
								<a href='javaScript:eliminar("<c:out value="${guiab}"/>");'><img border='0' src='../etc/img/eliminar.png' width='15' height='15'></a>
							</td>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${fila.inhNomEstructura}"/></td>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${fila.inhNomDocente}"/></td>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${fila.inhNomDia}"/></td>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${fila.inhHoraIni}"/></td>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${fila.inhHoraFin}"/></td>
						</tr>
					</c:forEach>
				</c:if>
		  </table>
 	  </form>