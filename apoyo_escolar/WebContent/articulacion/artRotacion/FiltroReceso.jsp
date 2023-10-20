<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="paramsVO" class="articulacion.artRotacion.vo.ParamsVO" scope="page"/>
<html>
<head>
<c:import url="/parametros.jsp"/>
<script language="javaScript">
<!--
	function editar(n,m){
		if(document.frmLista.id){
				document.frmLista.id.value=n;
				document.frmLista.id2.value=m;
				document.frmLista.cmd.value=document.frmLista.EDITAR.value;
				document.frmLista.submit();
		}
	}
	
	function eliminar(n,m){
		if(document.frmLista.id){
			if(confirm('¿DESEA ELIMINAR ESTE REGISTRO?')){
				document.frmLista.id.value=n;
				document.frmLista.id2.value=m;
				document.frmLista.cmd.value=document.frmLista.ELIMINAR.value;
				document.frmLista.submit();
			}
		}
	}

	function lanzar(tipo){
  	document.frmLista.tipo.value=tipo;
		document.frmLista.target="";
		document.frmLista.submit();
	}
	
	function hacerValidaciones_frmLista(forma){
		validarLista(forma.filAnhoVigencia, "- Año de vigencia", 1)
		validarLista(forma.filPerVigencia, "- Periodo de vigencia", 1)
		validarLista(forma.filEstructura, "- Estructura", 1)
	}

	function buscar(){
		if(validarForma(document.frmLista)){
			document.frmLista.cmd.value=document.frmLista.BUSCAR.value;
			document.frmLista.target="";
			document.frmLista.submit();
		}
	}

	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) {
			if(navigator.appName == "Netscape") combo.options[i] = null;
			else combo.remove(i);
		}
		combo.options[0] = new Option("--Seleccione una--","-99");
	}


	function ajaxEstructura(){
		borrar_combo(document.frmLista.filEstructura); 
		document.frmAjax.ajax[0].value=document.frmLista.filInstitucion.value;
		document.frmAjax.ajax[1].value=document.frmLista.filSede.value;
		document.frmAjax.ajax[2].value=document.frmLista.filJornada.value;
		document.frmAjax.ajax[3].value=document.frmLista.filAnhoVigencia.options[document.frmLista.filAnhoVigencia.selectedIndex].value;
		document.frmAjax.ajax[4].value=document.frmLista.filPerVigencia.options[document.frmLista.filPerVigencia.selectedIndex].value;
		document.frmAjax.cmd.value='<c:out value="${paramsVO.CMD_AJAX_ESTRUCTURA}"/>';
 		document.frmAjax.target="frameAjax";
		document.frmAjax.submit();
	}

//-->
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form method="post" name="frmAjax" action='<c:url value="/articulacion/artRotacion/Ajax.do"/>'><input type="hidden" name="ajax"><input type="hidden" name="ajax"><input type="hidden" name="ajax"><input type="hidden" name="ajax"><input type="hidden" name="ajax"><input type="hidden" name="ajax"><input type="hidden" name="ajax"><input type="hidden" name="ajax"><input type="hidden" name="ajax"><INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_RECESO}"/>'><input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_AJAX}"/>'></form>
	<form action='<c:url value="/articulacion/artRotacion/SaveReceso.jsp"/>' method="post" name="frmLista" onSubmit="return validarForma(frmLista)">
		<INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_RECESO}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="id" value=""><input type="hidden" name="id2" value=""><input type="hidden" name="id3" value=""><input type="hidden" name="id4" value=""><input type="hidden" name="id5" value=""><input type="hidden" name="id6" value="">
		<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>	
		<input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
		<input type="hidden" name="ELIMINAR" value='<c:out value="${paramsVO.CMD_ELIMINAR}"/>'>
		<input type="hidden" name="guia" value="<c:out value="${requestScope.guia}"/>">
		<input type="hidden" name="filInstitucion" value='<c:out value="${sessionScope.login.instId}"/>'>
		<input type="hidden" name="filSede" value='<c:out value="${sessionScope.login.sedeId}"/>'>
		<input type="hidden" name="filJornada" value='<c:out value="${sessionScope.login.jornadaId}"/>'>
		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1"><td width="10">&nbsp;</td>
				<td rowspan="2" width="469">
				<a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_ESTRUCTURA}"/>)"><img src='<c:url value="/etc/img/tabs/Parametros_0.gif"/>' alt="Estructura"  height="26" border="0"></a>
				<img src='<c:url value="/etc/img/tabs/receso_1.gif"/>' alt="Receso"  height="26" border="0">
				<a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_HORARIO}"/>)"><img src='<c:url value="/etc/img/tabs/horario_0.gif"/>' alt="Horario"  height="26" border="0"></a>
				<a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_BORRAR_HORARIO}"/>)"><img src='<c:url value="/etc/img/tabs/borrar_horario_0.gif"/>' alt="Borrar Horario"  height="26" border="0"></a>
				</td></tr>
		</table>
		
		<table border="0" align="center" width="95%" cellpadding="0" cellspacing="0">
		 	<caption>FILTRO DE BÚSQUEDA</caption>
		 	<tr><td></td></tr>
	 	</table>
		<table border="0" align="center" width="95%" cellpadding="0" cellspacing="0">
	  		<tr><td colspan="2"><input name="cmd1" type="button" value="Buscar" onClick="buscar()" class="boton"></td></tr> 	
	  		<tr>
					<td align="right"><span class="style2">*</span> Vigencia: &nbsp;&nbsp;&nbsp;</td>
					<td>
					<select name="filAnhoVigencia" style='width:50px;'>
						<option value="-99">--</option>
						<c:forEach begin="0" items="${requestScope.listaVigencia}" var="item">
							<option value='<c:out value="${item.codigo}"/>' <c:if test="${item.codigo==sessionScope.artRotFiltroRecesoVO.filAnhoVigencia}">selected</c:if>><c:out value="${item.nombre}"/></option>
						</c:forEach>
					</select>
					<select name="filPerVigencia" style='width:30px;' onchange="javaScript:ajaxEstructura()">
						<option value="-99">--</option>
						<c:forEach begin="0" items="${requestScope.lPeriodoVO}" var="vig">
							<option value="<c:out value="${vig}"/>" <c:if test="${vig==sessionScope.artRotFiltroRecesoVO.filPerVigencia}">selected</c:if>><c:out value="${vig}"/></option>
						</c:forEach>
					</select>
					<td>
				<td><span class="style2">*</span>Estructura:</td>
				<td>
					<select name="filEstructura" style='width:200px;'>
						<option value="-99">--Seleccione una--</option>
						<c:forEach begin="0" items="${requestScope.lEstructuraVO}" var="est">
							<option value="<c:out value="${est.codigo}"/>" <c:if test="${est.codigo==sessionScope.artRotFiltroRecesoVO.filEstructura}">selected</c:if>><c:out value="${est.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
				</tr>
				<tr style="display:none"><td><iframe name="frameAjax" id="frameAjax"></iframe></td></tr>
	 	</table>
		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0">
	 	<caption>LISTADO DE RECESOS</caption>
		  <c:if test="${empty requestScope.listaRecesoVO}">
				<tr><th class="Fila1" colspan='6'>NO HAY REGISTROS</th></tr>
			</c:if>
		 	<c:if test="${!empty requestScope.listaRecesoVO}">
	  		<tr>
					<th width='30' class="EncabezadoColumna">&nbsp;</th>
					<th class="EncabezadoColumna">Nombre</th>
					<th class="EncabezadoColumna">Tipo</th>
					<th class="EncabezadoColumna">Hora Inicial</th>
					<th class="EncabezadoColumna">Duración en horas</th>
					<th class="EncabezadoColumna">Duración en minutos</th>
				</tr>
				<c:forEach begin="0" items="${requestScope.listaRecesoVO}" var="lista" varStatus="st">
					<tr>
						<th class='Fila<c:out value="${st.count%2}"/>'>
						<a href='javaScript:editar(<c:out value="${lista.resEstructura}"/>,<c:out value="${lista.resCodigo}"/>);'><img border='0' src='<c:url value="/etc/img/editar.png"/>' width='15' height='15'></a>
						<c:if test="${sessionScope.NivelPermiso==2}"><a href='javaScript:eliminar(<c:out value="${lista.resEstructura}"/>,<c:out value="${lista.resCodigo}"/>);'><img border='0' src='<c:url value="/etc/img/eliminar.png"/>' width='15' height='15'></a></c:if>
						</th>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.resNombre}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.resTipoNombre}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.resClase}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.resDuracionHor}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.resDuracionMin}"/></td>
					</tr>
				</c:forEach>
			</c:if>
		</table>
	</form>
</body>
</html>