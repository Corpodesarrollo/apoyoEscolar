<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="primeraInfanciaPEIVO" class="pei.registro.vo.PrimeraInfanciaVO" scope="session"/>
<jsp:useBean id="paramsVO" class="pei.registro.vo.ParamsVO" scope="page"/>
<html>
<c:import url="/parametros.jsp"/>
<head>
<script language="javaScript">
<!--
	function eliminar(inst,cod){
		if(confirm('¿Confirma la eliminación de registro del registro de Primera Infancia?')){
			document.frmFiltro.priCodigoInstitucion.value=inst;
			document.frmFiltro.priConsecutivo.value=cod;
			document.frmFiltro.cmd.value=document.frmFiltro.ELIMINAR.value;
			document.frmFiltro.target='';
			document.frmFiltro.submit();
		}
	}

	function agregar(){
		var frmFiltroNuevo = document.frmFiltro;
		document.frmFiltro.cmd.value=document.frmFiltro.NUEVO.value;
		
		remote = window.open("","1","width=450,height=200,resizable=yes,toolbar=no,directories=no,menubar=no,status=no");
		remote.moveTo(250,350);
		document.frmFiltro.target='1';
		document.frmFiltro.submit();
		if(remote.opener == null) remote.opener = window;
		remote.opener.name = "framePrimeraInfancia";
		remote.focus();
	}
	
//-->
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form action='<c:url value="/pei/registro/SavePrimeraInfancia.jsp"/>' method="post" id="frmFiltro" name="frmFiltro" onSubmit="return validarForma(frmFiltro)">
		<input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_PRIMERA_INFANCIA}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="ELIMINAR" value='<c:out value="${paramsVO.CMD_ELIMINAR}"/>'>
		<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
		<input type="hidden" name="priCodigoInstitucion" value='<c:out value="${sessionScope.desarrolloCurricularPEIVO.desInstitucion}"/>'>
		<input type="hidden" name="priConsecutivo" value='<c:out value="${sessionScope.poblacionesPEIVO.pobConsecutivo}"/>'>
		<!-- <table border="0" align="center" width="95%">
			<tr>
				<td>
				<c:if test="${sessionScope.NivelPermiso==2 && sessionScope.desarrolloCurricularPEIVO.desDisabled==false}">
					<input name="cmd1" type="button" value="Agregar" onClick="agregar()" class="boton">
   				</c:if>
		  		</td>
		 	</tr>
		</table> -->
		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray">
				<tr>
					<!-- <th width='20' class="EncabezadoColumna">&nbsp;</th> -->
					<td class="EncabezadoColumna" align="center">Grado</td>
					<td class="EncabezadoColumna" align="center">Cantidad de Cursos</td>
					<td class="EncabezadoColumna" align="center">Cantidad de Estudiantes</td>
				</tr>
				<c:forEach begin="0" items="${requestScope.listaPrimeraInfancia}" var="lista" varStatus="st">
					<tr>
						<!-- <th class='Fila<c:out value="${st.count%2}"/>' title='Eliminar'>
							<c:if test="${sessionScope.NivelPermiso==2 && sessionScope.desarrolloCurricularPEIVO.desDisabled==false}">
								<a href='javaScript:eliminar(<c:out value="${lista.priCodigoInstitucion}"/>,<c:out value="${lista.priConsecutivo}"/>);'>
									<img border='0' src='<c:url value="/etc/img/eliminar.png"/>' width='15' height='15'>
								</a>
							</c:if>
						</th> -->
						<td class='Fila<c:out value="${st.count%2}"/>' align="center">
							<textarea rows="2" cols="40" readonly><c:out value="${lista.priGrado}"/></textarea>
						</td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center">
							<input type="text" name="pobEstudiantes" readonly value='<c:out value="${lista.priCursos}"/>'>
						</td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center">
							<input type="text" name="pobEstudiantes" readonly value='<c:out value="${lista.priEstudiantes}"/>'>
						</td>
					</tr>
				</c:forEach>
		</table>
	</form>
</body>
</html>