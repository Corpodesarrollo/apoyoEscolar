<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="filtroRangoVO" class="participacion.instancia.vo.FiltroRangoVO" scope="session"/>
<jsp:useBean id="paramsVO" class="participacion.instancia.vo.ParamsVO" scope="page"/>
<html>
<c:import url="/parametros.jsp"/>
<head>
<script language="javaScript">
<!--
	function lanzar(tipo){
  	document.frmFiltro.tipo.value=tipo;
		document.frmFiltro.target="";
		document.frmFiltro.submit();
	}
	
	function hacerValidaciones_frmFiltro(forma){
		validarLista(forma.filNivel, "- Nivel",1);
		validarLista(forma.filInstancia, "- Instancia",1);
	}
	
	function buscar(){
		if(validarForma(document.frmFiltro)){
			document.frmFiltro.cmd.value=document.frmFiltro.BUSCAR.value;
			document.frmFiltro.submit();
		}
	}
	
	function editar(n){
		if(document.frmFiltro.filRango){
				document.frmFiltro.filRango.value=n;
				document.frmFiltro.cmd.value=document.frmFiltro.EDITAR.value;
				document.frmFiltro.submit();
		}
	}
	
	function eliminar(n){
		if(confirm('¿Desea eliminar el rango indicado?')){
			if(document.frmFiltro.filRango){
					document.frmFiltro.filRango.value=n;
					document.frmFiltro.cmd.value=document.frmFiltro.ELIMINAR.value;
					document.frmFiltro.submit();
			}
		}	
	}

	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) {
			if(navigator.appName == "Netscape") combo.options[i] = null;
			else combo.remove(i);
		}
		combo.options[0] = new Option("-Seleccione una-","-99");
	}
		
	function ajaxInstancia0(){
		borrar_combo(document.frmFiltro.filInstancia); 
		document.frmAjax0.ajax[0].value=document.frmFiltro.filNivel.value;
		if(parseInt(document.frmAjax0.ajax[0].value)>0){
			document.frmAjax0.cmd.value=document.frmFiltro.AJAX_INSTANCIA0.value;
	 		document.frmAjax0.target="frameAjax";
			document.frmAjax0.submit();
		}	
	}
//-->
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form method="post" name="frmAjax0" action='<c:url value="/participacion/instancia/Ajax.do"/>'><INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_RANGO}"/>'><input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_AJAX}"/>'><input type="hidden" name="ajax"><input type="hidden" name="ajax"><input type="hidden" name="ajax"></form>
	<form action='<c:url value="/participacion/instancia/SaveRango.jsp"/>' method="post" name="frmFiltro">
		<input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_RANGO}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="filRango" value='<c:out value="${sessionScope.filtroRangoVO.filRango}"/>'>
		<input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
		<input type="hidden" name="ELIMINAR" value='<c:out value="${paramsVO.CMD_ELIMINAR}"/>'>
		<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
		<input type="hidden" name="AJAX_INSTANCIA0" value='<c:out value="${paramsVO.CMD_AJAX_INSTANCIA0}"/>'>
		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1">
				<td width="10">&nbsp;</td>
				<td rowspan="2" width="469">
					<a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_INSTANCIA}"/>)"><img src='<c:url value="/etc/img/tabs/instancia_0.gif"/>' alt="Instancia" height="26" border="0"></a>
					<img src='<c:url value="/etc/img/tabs/rango_1.gif"/>' alt="Rango" height="26" border="0">
				</td>
			</tr>
		</table>
		<table border="0" align="center" width="95%">
		 	<caption>Filtro de búsqueda</caption>
			<tr>
				<td><input name="cmd1" type="button" value="Buscar" onClick="buscar()" class="boton"></td>
		 	</tr>	
		 	<tr style="display:none"><td><iframe name="frameAjax0" id="frameAjax0"></iframe></td></tr>
		</table>
		<table border="0" align="center" width="95%">
				<tr>
				<td><span class="style2">*</span> Nivel:</td>
				<td>
					<select name="filNivel" style="width:100px" onchange="javaScript:ajaxInstancia0()">
						<option value="-99" selected>-Seleccione uno-</option>
						<c:forEach begin="0" items="${requestScope.listaNivelVO}" var="niv">
							<option value="<c:out value="${niv.codigo}"/>" <c:if test="${niv.codigo==sessionScope.filtroRangoVO.filNivel}">selected</c:if>><c:out value="${niv.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
				<td><span class="style2">*</span> Instancia:</td>
				<td>
					<select name="filInstancia" style="width:250px">
						<option value="-99" selected>-Seleccione uno-</option>
						<c:forEach begin="0" items="${requestScope.listaInstanciaVO}" var="inst">
							<option value="<c:out value="${inst.codigo}"/>" <c:if test="${inst.codigo==sessionScope.filtroRangoVO.filInstancia}">selected</c:if>><c:out value="${inst.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
		</table>
		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray">
		 	<caption>Listado de rangos</caption>
		 	<c:if test="${empty requestScope.listaRangoVO}"><tr><th class="Fila1" colspan='6'>No hay rangos</th></tr></c:if>
			<c:if test="${!empty requestScope.listaRangoVO}">
				<tr>
					<th width='20' class="EncabezadoColumna" colspan="2">&nbsp;</th>
					<td class="EncabezadoColumna" align="center">Nombre</td>
					<td class="EncabezadoColumna" align="center">Fecha inicial</td>
					<td class="EncabezadoColumna" align="center">Fecha final</td>
				</tr>
				<c:forEach begin="0" items="${requestScope.listaRangoVO}" var="lista" varStatus="st">
					<tr>
						<th class='Fila<c:out value="${st.count%2}"/>'><c:out value="${st.count}"/></th>
						<th class='Fila<c:out value="${st.count%2}"/>'>
							<a href='javaScript:editar(<c:out value="${lista.ranCodigo}"/>);'><img border='0' src='<c:url value="/etc/img/editar.png"/>' width='15' height='15'></a>
							<c:if test="${sessionScope.NivelPermiso==2}"><a href='javaScript:eliminar(<c:out value="${lista.ranCodigo}"/>);'><img border='0' src='<c:url value="/etc/img/eliminar.png"/>' width='15' height='15'></a></c:if>
						</th>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center" valign="middle"><c:out value="${lista.ranNombre}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center" valign="middle"><c:out value="${lista.ranFechaIni}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center" valign="middle"><c:out value="${lista.ranFechaFin}"/></td>
					</tr>
				</c:forEach>
			</c:if>
		</table>
	</form>
</body>
</html>