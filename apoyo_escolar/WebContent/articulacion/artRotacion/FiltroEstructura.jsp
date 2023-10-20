<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="paramsVO" class="articulacion.artRotacion.vo.ParamsVO" scope="page"/>
<html>
<head>
<c:import url="/parametros.jsp"/>
<script language="javaScript">
<!--
	var nav4=window.Event ? true : false;

	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}
	function editar(n,m,o,p,anho,per){
		if(document.frmLista.id){
				document.frmLista.id.value=n;
				document.frmLista.id2.value=m;
				document.frmLista.id3.value=o;
				document.frmLista.id4.value=p;
				document.frmLista.id5.value=anho;
				document.frmLista.id6.value=per;
				document.frmLista.cmd.value=document.frmLista.EDITAR.value;
				document.frmLista.submit();
		}
	}
	
	function eliminar(n,m,o,p,anho,per){
		if(document.frmLista.id){
			if(confirm('¿DESEA ELIMINAR ESTE REGISTRO?')){
				document.frmLista.id.value=n;
				document.frmLista.id2.value=m;
				document.frmLista.id3.value=o;
				document.frmLista.id4.value=p;
				document.frmLista.id5.value=anho;
				document.frmLista.id6.value=per;
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
		validarLista(forma.anhoVigencia, "- Año de vigencia", 1)
		validarLista(forma.perVigencia, "- Periodo de vigencia", 1)
	}

	function buscar(){
		if(validarForma(document.frmLista)){
			document.frmLista.target="";
			document.frmLista.submit();
		}
	}
//-->
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form action='<c:url value="/articulacion/artRotacion/SaveEstructura.jsp"/>' method="post" name="frmLista" onSubmit="return validarForma(frmLista)">
		<INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_ESTRUCTURA}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="id" value=""><input type="hidden" name="id2" value=""><input type="hidden" name="id3" value=""><input type="hidden" name="id4" value=""><input type="hidden" name="id5" value=""><input type="hidden" name="id6" value="">
		<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>	
		<input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
		<input type="hidden" name="ELIMINAR" value='<c:out value="${paramsVO.CMD_ELIMINAR}"/>'>
		<input type="hidden" name="guia" value="<c:out value="${requestScope.guia}"/>">
		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1"><td width="10">&nbsp;</td>
				<td rowspan="2" width="469">
				<img src='<c:url value="/etc/img/tabs/parametros_1.gif"/>' alt="Estructura" height="26" border="0">
				<a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_RECESO}"/>)"><img src='<c:url value="/etc/img/tabs/receso_0.gif"/>' alt="Receso"  height="26" border="0"></a>
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
					<td align="right" width="50%"><span class="style2">*</span> Vigencia: &nbsp;&nbsp;&nbsp;</td>
					<td>
					<select name="anhoVigencia" style='width:50px;'>
						<option value="-99">--</option>
						<c:forEach begin="0" items="${requestScope.listaVigencia}" var="item">
							<option value='<c:out value="${item.codigo}"/>' <c:if test="${item.codigo==sessionScope.artRotFiltroEstructuraVO.anhoVigencia}">selected</c:if>><c:out value="${item.nombre}"/></option>
						</c:forEach>
					</select>
					<select name="perVigencia" style='width:30px;'>
						<option value="-99">--</option>
						<c:forEach begin="0" items="${requestScope.listaPeriodo}" var="vig">
							<option value="<c:out value="${vig}"/>" <c:if test="${vig==sessionScope.artRotFiltroEstructuraVO.perVigencia}">selected</c:if>><c:out value="${vig}"/></option>
						</c:forEach>
					</select>
					<td>
				</tr>
	 	</table>
	 	
		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0">
	 	<caption>LISTADO DE ESTRUCTURAS</caption>
		  <c:if test="${empty requestScope.listaHorarioParamVO}">
				<tr><th class="Fila1" colspan='6'>NO HAY REGISTROS</th></tr>
			</c:if>
		 	<c:if test="${!empty requestScope.listaHorarioParamVO}">
	  		<tr>
					<th width='30' class="EncabezadoColumna">&nbsp;</th>
					<th class="EncabezadoColumna">Nombre</th>
					<th class="EncabezadoColumna">Sede</th>
					<th class="EncabezadoColumna">Jornada</th>
					<th class="EncabezadoColumna">Componente</th>
					<th class="EncabezadoColumna">Hora Inicio</th>
					<th class="EncabezadoColumna">Hora Fin</th>
				</tr>
				<c:forEach begin="0" items="${requestScope.listaHorarioParamVO}" var="lista" varStatus="st">
					<tr>
						<th class='Fila<c:out value="${st.count%2}"/>'>
						<a href='javaScript:editar(<c:out value="${lista.parInstitucion}"/>,<c:out value="${lista.parSede}"/>,<c:out value="${lista.parJornada}"/>,<c:out value="${lista.parComponente}"/>,<c:out value="${lista.parAnhoVigencia}"/>,<c:out value="${lista.parPerVigencia}"/>);'><img border='0' src='../../etc/img/<c:if test="${requestScope.guia==lista.parInstitucion && requestScope.guia2==lista.parSede && requestScope.guia3==lista.parJornada && requestScope.guia4==lista.parComponente && requestScope.guia5==lista.parAnhoVigencia && requestScope.guia6==lista.parPerVigencia}">X</c:if>editar.png' width='15' height='15'></a>
						<c:if test="${sessionScope.NivelPermiso==2}"><a href='javaScript:eliminar(<c:out value="${lista.parInstitucion}"/>,<c:out value="${lista.parSede}"/>,<c:out value="${lista.parJornada}"/>,<c:out value="${lista.parComponente}"/>,<c:out value="${lista.parAnhoVigencia}"/>,<c:out value="${lista.parPerVigencia}"/>);'><img border='0' src='<c:url value="/etc/img/eliminar.png"/>' width='15' height='15'></a></c:if>
						</th>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.parNombre}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.parNombreSede}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.parNombreJornada}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.parNombreComponente}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.parHoraInicio}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.parHoraFinalizacion}"/></td>
					</tr>
				</c:forEach>
			</c:if>
		</table>
	</form>
</body>
</html>