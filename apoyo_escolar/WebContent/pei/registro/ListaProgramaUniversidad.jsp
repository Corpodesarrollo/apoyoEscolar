<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="programaUniversidadPEIVO" class="pei.registro.vo.ProgramaVO" scope="session"/>
<jsp:useBean id="paramsVO" class="pei.registro.vo.ParamsVO" scope="page"/>
<html>
<c:import url="/parametros.jsp"/>
<head>
<script language="javaScript">
<!--
	function eliminar(inst,univ,cod){
		if(confirm('¿Confirma la eliminación del programa?')){
			document.frmFiltro.proInstitucion.value=inst;
			document.frmFiltro.proUniversidad.value=univ;
			document.frmFiltro.proCodigo.value=cod;
			document.frmFiltro.cmd.value=document.frmFiltro.ELIMINAR.value;
			document.frmFiltro.target='';
			document.frmFiltro.submit();
		}
	}

	function agregar(){
		document.frmFiltro.cmd.value=document.frmFiltro.NUEVO.value;
		remote = window.open("","1","width=450,height=200,resizable=yes,toolbar=no,directories=no,menubar=no,status=no");
		remote.moveTo(250,350);
		document.frmFiltro.target='1';
		document.frmFiltro.submit();
		if(remote.opener == null) remote.opener = window;
		remote.opener.name = "frameProgramaUniversidad";
		remote.focus();
	}
	
//-->
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form action='<c:url value="/pei/registro/SaveProgramaUniversidad.jsp"/>' method="post" name="frmFiltro" onSubmit="return validarForma(frmFiltro)">
		<input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_PROGRAMA_UNIVERSIDAD}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="ELIMINAR" value='<c:out value="${paramsVO.CMD_ELIMINAR}"/>'>
		<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
		<input type="hidden" name="proInstitucion" value='<c:out value="${sessionScope.programaUniversidadPEIVO.proInstitucion}"/>'>
		<input type="hidden" name="proUniversidad" value='<c:out value="${sessionScope.programaUniversidadPEIVO.proUniversidad}"/>'>
		<input type="hidden" name="proCodigo" value='<c:out value="${sessionScope.programaUniversidadPEIVO.proCodigo}"/>'>

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
					<th width='20' class="EncabezadoColumna">&nbsp;</th>
					<td class="EncabezadoColumna" align="center">Programa</td>
					<td class="EncabezadoColumna" align="center">Universidad</td>
					<td class="EncabezadoColumna" align="center">Número de estudiantes</td>
				</tr>
				<c:forEach begin="0" items="${requestScope.listaProgramaUniversidad}" var="lista" varStatus="st">
					<tr>
						<th class='Fila<c:out value="${st.count%2}"/>' title='<c:out value="${lista.proDescripcion}"/>'>
							<c:if test="${sessionScope.NivelPermiso==2 && sessionScope.desarrolloCurricularPEIVO.desDisabled==false}"><a href='javaScript:eliminar(<c:out value="${lista.proInstitucion}"/>,<c:out value="${lista.proUniversidad}"/>,<c:out value="${lista.proCodigo}"/>);'><img border='0' src='<c:url value="/etc/img/eliminar.png"/>' width='15' height='15'></a></c:if>
						</th>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"  title='<c:out value="${lista.proDescripcion}"/>'><c:out value="${lista.proNombre}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center" title='<c:out value="${lista.proDescripcionUniversidad}"/>'><c:out value="${lista.proNombreUniversidad}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center" ><c:out value="${lista.proEstudiantes}"/></td>
					</tr>
				</c:forEach>
		</table>
	</form>
</body>
</html>