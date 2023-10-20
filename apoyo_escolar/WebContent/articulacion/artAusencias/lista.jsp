<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="paramsVO" class="articulacion.artAusencias.vo.ParamsVO" scope="page"/>
<html>
<c:import url="/parametros.jsp"/>
<head>
<script language="javaScript">
   
	function guardar(){
		for(var i=0; i<document.frmLista.diasAusencia_.length;i++){
			if(document.frmLista.diasAusencia_[i].checked!=true){
				document.frmLista.estDia[i].disabled=true;
			}
		}
		document.frmLista.cmd.value=document.frmLista.INSERTAR.value;
		document.frmLista.submit();
	}
		
	function abrir(id,url,b,c){
		if(document.getElementById(id).checked!=true){
			alert("Debe seleccionar la casilla para poder ver la ventana");
		}else{
			window.open(url,b,c);
		}
	}
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:160px;overflow:auto;vertical-align:top;background-color:#ffffff;">
				<c:import url="/articulacion/artAusencias/Filtro.do"/>
			</div>
		</td></tr>
	</table>
	
	<form action='<c:url value="/articulacion/artAusencias/Save.jsp"/>' method="post" name="frmLista" onSubmit="return validarForma(frmLista)">
		<input type="hidden" name="cmd" value=''>
		<input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_DEFAULT}"/>'>
		<input type="hidden" name="INSERTAR" value='<c:out value="${paramsVO.CMD_INSERTAR}"/>'>
		<input type="hidden" name="artEvaCodInst" value='<c:out value="${sessionScope.login.instId}"/>'>
		<input type="hidden" name="artEvaCodMetod" value='<c:out value="${sessionScope.login.metodologiaId}"/>'>
		<input type="hidden" name="artEvaAnVigencia" value='<c:out value="${sessionScope.FilAusenciasVO.anVigencia}"/>'>
		<input type="hidden" name="artEvaPerVigencia" value='<c:out value="${sessionScope.FilAusenciasVO.perVigencia}"/>'>
		<input type="hidden" name="artEvaCodAsig" value='<c:out value="${sessionScope.FilAusenciasVO.asignatura}"/>'>
		
		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0">
		 	<caption>LISTADO DE ESTUDIANTES</caption>
		 	<c:if test="${empty sessionScope.listaEstAusenciaVO}">
				<tr>
					<th class="Fila1" colspan='6'>NO EXISTEN REGISTROS DE ESTUDIANTES</th>
				</tr>
			</c:if>
			<c:if test="${!empty sessionScope.listaEstAusenciaVO}">
				<tr>
					<td colspan="2">	
	 					<input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton">
	  				</td>
	  			</tr>
				<tr>
					<td align="center" class="EncabezadoColumna">Apellidos</td>
					<td align="center" class="EncabezadoColumna">Nombres</td>
					<c:forEach begin="0" items="${requestScope.listaDias}" varStatus="st" var="dia">
						<th class="EncabezadoColumna" align="center"><c:out value="${dia.nombre}"/><br>(<c:out value="${dia.codigo}"/>)</th>
					</c:forEach>
				</tr>
				<tr>
					<c:forEach begin="0" items="${sessionScope.listaEstAusenciaVO}" var="lista" varStatus="st">
					<tr>
						<input type="hidden" name="artAusCodEst" value='<c:out value="${lista.codigo}"/>'/>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.apellido}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.nombre}"/></td>
					<c:forEach begin="0" items="${lista.dias}" var="dia" varStatus="st2">
						<input type="hidden" name="estDia" value='<c:out value="${lista.codigo}"/>-<c:out value="${dia.codigo}"/>'>
						<td class='Fila<c:out value="${st.count%2}"/>'>	
						<input type="checkbox" name="diasAusencia_" id='<c:out value="${lista.codigo}"/>-<c:out value="${st2.count}"/>' <c:if test="${dia.habilitar==false}">disabled</c:if> <c:if test="${dia.check==true}">checked</c:if>>
						<img src='<c:url value="/etc/img/z.gif"/>'onclick="javaScript:abrir('<c:out value="${lista.codigo}"/>-<c:out value="${st2.count}"/>','<c:url value="/artAusencias/Motivo.do"/>?cod=<c:out value="${lista.codigo}"/>&dia=<c:out value="${st2.count}"/>&numDia=<c:out value="${dia.numDia}"/>','3','width=350,height=250,resizable=yes,toolbar=no,directories=no,menubar=no,status=yes')" >
						</td>
					</c:forEach>
					</tr>
					<c:if test="${(st.count%10)==0}">
					<td align="center" class="EncabezadoColumna">Apellidos</td>
					<td align="center" class="EncabezadoColumna">Nombres</td>
				<c:forEach begin="0" items="${requestScope.listaDias}" varStatus="st" var="dia2">
					<th class="EncabezadoColumna" align="center"><c:out value="${dia2.nombre}"/><br>(<c:out value="${dia2.codigo}"/>)</th>
				</c:forEach>
					</c:if>
				</c:forEach>
			</c:if>
		</table>
	</form>
</body>
<script>
	//letra(document.frmLista,document.rangos);
</script>
</html>