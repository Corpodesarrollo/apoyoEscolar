<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="capacitacionPEIVO" class="pei.registro.vo.CapacitacionVO" scope="session"/>
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
		if(confirm('¿Confirma la eliminación de la capacitación?')){
			document.frmFiltro.capInstitucion.value=inst;
			document.frmFiltro.capCodigo.value=cod;
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
	remote.opener.name = "frameCapacitacion";
	remote.focus();
}
	
//-->
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form action='<c:url value="/pei/registro/SaveCapacitacion.jsp"/>' method="post" name="frmFiltro" onSubmit="return validarForma(frmFiltro)">
		<input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_CAPACITACION}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="ELIMINAR" value='<c:out value="${paramsVO.CMD_ELIMINAR}"/>'>
		<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
		<input type="hidden" name="capInstitucion" value='<c:out value="${sessionScope.capacitacionPEIVO.capInstitucion}"/>'>
		<input type="hidden" name="capCodigo" value='<c:out value="${sessionScope.capacitacionPEIVO.capCodigo}"/>'>

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
					<td class="EncabezadoColumna" align="center" width="50%">Tipo de formación y duración</td>
					<td class="EncabezadoColumna" align="center" width="15%">Cantidad de docentes</td>
					<td class="EncabezadoColumna" align="center" width="35%">Institución universitaria</td>
				</tr>
				<c:forEach begin="0" items="${requestScope.listaCapacitacion}" var="lista" varStatus="st">
					<tr>
						<th class='Fila<c:out value="${st.count%2}"/>'>
							<c:if test="${sessionScope.NivelPermiso==2  && sessionScope.desarrolloCurricularPEIVO.desDisabled==false}"><a href='javaScript:eliminar(<c:out value="${lista.capInstitucion}"/>,<c:out value="${lista.capCodigo}"/>);'><img border='0' src='<c:url value="/etc/img/eliminar.png"/>' width='15' height='15'></a></c:if>
						</th>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center">
							<textarea rows="3" cols="40" class='txt<c:out value="${st.count%2}"/>' readonly="readonly"><c:out value="${lista.capNombreFormacion}"/></textarea>
						</td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.capDocentes}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.capNombreUniversidad}"/></td>
					</tr>
				</c:forEach>
		</table>
	</form>
</body>
</html>