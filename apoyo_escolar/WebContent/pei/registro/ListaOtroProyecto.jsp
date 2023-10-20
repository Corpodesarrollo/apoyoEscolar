<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="otroProyectoPEIVO" class="pei.registro.vo.ProyectoVO" scope="session"/>
<jsp:useBean id="paramsVO" class="pei.registro.vo.ParamsVO" scope="page"/>
<html>
<c:import url="/parametros.jsp"/>
<head>
	<STYLE type='text/css'>
		.txt1{
			background-color: #FFF;
			border-color: #FFF;
			overflow: auto;
		}
		.txt0{
			background-color: #C9efef;
			border-color: #C9efef;
			overflow: auto;
		}
	</STYLE>

<script language="javaScript">
<!--
	function eliminar(inst,cod){
		if(confirm('¿Confirma la eliminación del proyecto?')){
			document.frmFiltro.proyInstitucion.value=inst;
			document.frmFiltro.proyCodigo.value=cod;
			document.frmFiltro.cmd.value=document.frmFiltro.ELIMINAR.value;
			document.frmFiltro.target='';
			document.frmFiltro.submit();
		}
	}

	function editar(inst,cod){
		document.frmFiltro.proyInstitucion.value=inst;
		document.frmFiltro.proyCodigo.value=cod;
		document.frmFiltro.cmd.value=document.frmFiltro.EDITAR.value;
		remote = window.open("","1","width=450,height=250,resizable=yes,toolbar=no,directories=no,menubar=no,status=no");
		remote.moveTo(250,350);
		document.frmFiltro.target='1';
		document.frmFiltro.submit();
		if(remote.opener == null) remote.opener = window;
		remote.opener.name = "frameOtroProyecto";
		remote.focus();
	}
	
	function agregar(){
		document.frmFiltro.cmd.value=document.frmFiltro.NUEVO.value;
		remote = window.open("","1","width=450,height=250,resizable=yes,toolbar=no,directories=no,menubar=no,status=no");
		remote.moveTo(250,350);
		document.frmFiltro.target='1';
		document.frmFiltro.submit();
		if(remote.opener == null) remote.opener = window;
		remote.opener.name = "frameOtroProyecto";
		remote.focus();
	}
	
//-->
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form action='<c:url value="SaveOtroProyecto.jsp"/>' method="post" name="frmFiltro" onSubmit="return validarForma(frmFiltro)">
		<input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_OTRO_PROYECTO}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="ELIMINAR" value='<c:out value="${paramsVO.CMD_ELIMINAR}"/>'>
		<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
		<input type="hidden" name="proyInstitucion" value='<c:out value="${sessionScope.otroProyectoPEIVO.proyInstitucion}"/>'>
		<input type="hidden" name="proyCodigo" value='<c:out value="${sessionScope.otroProyectoPEIVO.proyCodigo}"/>'>

		<table border="0" align="center" width="95%">
			<tr>
				<td>
					<c:if test="${sessionScope.NivelPermiso==2 && sessionScope.desarrolloCurricularPEIVO.desDisabled==false}">
					<input name="cmd1" type="button" value="Agregar" onClick="agregar()" class="boton">
   				</c:if>
		  		</td>
		 	</tr>
		</table>
		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray">
				<tr>
					<th width='40' class="EncabezadoColumna">&nbsp;</th>
					<td class="EncabezadoColumna" align="center">Nombre</td>
					<td class="EncabezadoColumna" align="center">Alcances</td>
					<td class="EncabezadoColumna" align="center">Dificultades</td>
					<td class="EncabezadoColumna" align="center">Entidad que apoya</td>
				</tr>
				<c:forEach begin="0" items="${requestScope.listaOtroProyecto}" var="lista" varStatus="st">
					<tr>
						<th class='Fila<c:out value="${st.count%2}"/>'>
							<a href='javaScript:editar(<c:out value="${lista.proyInstitucion}"/>,<c:out value="${lista.proyCodigo}"/>);'><img border='0' src='<c:url value="/etc/img/editar.png"/>' width='15' height='15'></a>
							<c:if test="${sessionScope.NivelPermiso==2 && sessionScope.desarrolloCurricularPEIVO.desDisabled==false}"><a href='javaScript:eliminar(<c:out value="${lista.proyInstitucion}"/>,<c:out value="${lista.proyCodigo}"/>);'><img border='0' src='<c:url value="/etc/img/eliminar.png"/>' width='15' height='15'></a></c:if>
						</th>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.proyNombre}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center">
							<textarea rows="3" cols="25" class='txt<c:out value="${st.count%2}"/>' readonly="readonly"><c:out value="${lista.proyAlcance}"/></textarea>
						</td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center">
							<textarea rows="3" cols="25" class='txt<c:out value="${st.count%2}"/>' readonly="readonly"><c:out value="${lista.proyDificultad}"/></textarea>
						</td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.proyEntidad}"/></td>
					</tr>
				</c:forEach>
		</table>
	</form>
</body>
</html>