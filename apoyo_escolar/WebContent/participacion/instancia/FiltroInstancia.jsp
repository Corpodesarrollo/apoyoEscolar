<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="filtroInstanciaVO" class="participacion.instancia.vo.FiltroInstanciaVO" scope="session"/>
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
	}
	
	function buscar(){
		if(validarForma(document.frmFiltro)){
			document.frmFiltro.cmd.value=document.frmFiltro.BUSCAR.value;
			document.frmFiltro.submit();
		}
	}
	
	function editar(n){
		if(document.frmFiltro.filInstancia){
				document.frmFiltro.filInstancia.value=n;
				document.frmFiltro.cmd.value=document.frmFiltro.EDITAR.value;
				document.frmFiltro.submit();
		}
	}
	
	function eliminar(n){
		if(confirm('¿Desea eliminar la instancia indicada?')){
			if(document.frmFiltro.filInstancia){
					document.frmFiltro.filInstancia.value=n;
					document.frmFiltro.cmd.value=document.frmFiltro.ELIMINAR.value;
					document.frmFiltro.submit();
			}
		}	
	}

//-->
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form action='<c:url value="/participacion/instancia/SaveInstancia.jsp"/>' method="post" name="frmFiltro">
		<input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_INSTANCIA}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="filInstancia" value='<c:out value="${sessionScope.filtroInstanciaVO.filInstancia}"/>'>
		<input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
		<input type="hidden" name="ELIMINAR" value='<c:out value="${paramsVO.CMD_ELIMINAR}"/>'>
		<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1">
				<td width="10">&nbsp;</td>
				<td rowspan="2" width="469">
					<img src='<c:url value="/etc/img/tabs/instancia_1.gif"/>' alt="Filtro" height="26" border="0">
					<a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_RANGO}"/>)"><img src='<c:url value="/etc/img/tabs/rango_0.gif"/>' alt="Rango" height="26" border="0"></a>
				</td>
			</tr>
		</table>
		<table border="0" align="center" width="95%">
		 	<caption>Filtro de búsqueda</caption>
			<tr>
				<td><input name="cmd1" type="button" value="Buscar" onClick="buscar()" class="boton"></td>
		 	</tr>	
		</table>
		<table border="0" align="center" width="95%">
				<TR>
				<td><span class="style2">*</span> Nivel:</td>
				<td>
					<select name="filNivel" style="width:100px">
						<option value="-99" selected>-Seleccione uno-</option>
						<c:forEach begin="0" items="${requestScope.listaNivelVO}" var="niv">
							<option value="<c:out value="${niv.codigo}"/>" <c:if test="${niv.codigo==sessionScope.filtroInstanciaVO.filNivel}">selected</c:if>><c:out value="${niv.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
		</table>
		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray">
		 	<caption>Listado de intancias</caption>
		 	<c:if test="${empty requestScope.listaInstanciaVO}"><tr><th class="Fila1" colspan='6'>No hay instancias</th></tr></c:if>
			<c:if test="${!empty requestScope.listaInstanciaVO}">
				<tr>
					<th width='20' class="EncabezadoColumna" colspan="2">&nbsp;</th>
					<td class="EncabezadoColumna" align="center">Nombre</td>
					<td class="EncabezadoColumna" align="center">Norma</td>
					<td class="EncabezadoColumna" align="center">Integrantes</td>
				</tr>
				<c:forEach begin="0" items="${requestScope.listaInstanciaVO}" var="lista" varStatus="st">
					<tr>
						<th class='Fila<c:out value="${st.count%2}"/>'><c:out value="${st.count}"/></th>
						<th class='Fila<c:out value="${st.count%2}"/>'>
							<a href='javaScript:editar(<c:out value="${lista.instCodigo}"/>);'><img border='0' src='<c:url value="/etc/img/editar.png"/>' width='15' height='15'></a>
							<c:if test="${sessionScope.NivelPermiso==2}"><a href='javaScript:eliminar(<c:out value="${lista.instCodigo}"/>);'><img border='0' src='<c:url value="/etc/img/eliminar.png"/>' width='15' height='15'></a></c:if>
						</th>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center" valign="middle"><c:out value="${lista.instNombre}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center" valign="middle">&nbsp;<c:out value="${lista.instNorma}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center" valign="middle">&nbsp;<c:out value="${lista.instIntegrantes}"/></td>
					</tr>
				</c:forEach>
			</c:if>
		</table>
	</form>
</body>
</html>