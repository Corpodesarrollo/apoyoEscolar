<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="paramsVO" class="articulacion.inscripcion.vo.ParamsVO" scope="page"/>
<html>
<head>
<c:import url="/parametros.jsp"/>
<script language="javaScript">
<!--
	function hacerValidaciones_frmLista(forma){
	}
	
	function guardar(){
		var forma=document.forms['frmNuevo'];
		if(forma){
			if(forma.x){
				if(forma.x.length){
					for(var i=0;i<forma.x.length;i++){
						if(forma.x[i].checked==true && forma.x[i].disabled==true){
							forma.x[i].disabled=false;
						}
					}
				}
			}
		}
		document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
    document.frmNuevo.submit(); 
	}

	function cambiocheck(obj){
		var valor=obj.value.split(':');
		var asig=parseInt(valor[0]);
		var grupo=parseInt(valor[1]);
		var forma=document.forms['frmNuevo'];
		if(forma){
			if(forma.x){
				if(forma.x.length){
					for(var i=0;i<forma.x.length;i++){
						var valor2=forma.x[i].value.split(':');
						if(parseInt(valor2[0])==asig){//es la misma asignatura
							if(parseInt(valor2[1])!=grupo){
								forma.x[i].checked=false;
							}
						}
					}
				}
			}
		}
	}
//-->
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form method="post" name="frmNuevo" action='<c:url value="/articulacion/inscripcion/Nuevo.do"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<table border="0" align="center" bordercolor="#FFFFFF" width="100%">
				<tr>
					<td width="45%"><c:if test="${!empty requestScope.listaAsignaturaVO}"><input name="cmd1" type="button" value="Inscribir" onClick="guardar()" class="boton"></c:if></td>
			 	</tr>	
	  </table>
		<table border="1" cellpadding="0" cellspacing="0"  width="100%">
		 	<caption>INSCRIPCIÓN</caption>
		 	<c:if test="${empty requestScope.listaAsignaturaVO}">
				<tr><th class="Fila1" colspan='6'>NO EXISTEN REGISTROS DE ASIGNATURAS</th></tr>
			</c:if>
			<c:if test="${!empty requestScope.listaAsignaturaVO}">
				<tr>
					<th width='3%' rowspan=2 bgcolor="#ffffff" class="EncabezadoColumna"  align="center">&nbsp;</th>
					<th width='4%' colspan=2  class="EncabezadoColumna" align="center">Asignatura</th>
					<th width='4%' rowspan=2 class="EncabezadoColumna" align="center">Grupo</th>
					<th width='30%' rowspan=2 class="EncabezadoColumna" align="center">Docentes</th>
					<th width='4%' rowspan=2 class="EncabezadoColumna" align="center">Créditos</th>
					<th width='4%' rowspan=2 class="EncabezadoColumna" align="center">Cupos</th>
					<TH width='4%' rowspan=2 class="EncabezadoColumna" align="center">Día</TH>
					<TH width='7%' rowspan=2 class="EncabezadoColumna" align="center">Hora Inicio</TH>
					<TH width='7%' rowspan=2 class="EncabezadoColumna" align="center">Hora Final</TH>
					<TH width='10%' rowspan=2 class="EncabezadoColumna" align="center">Espacio Físico</TH>
				</tr>
				<tr>
					<th width='4%' class="EncabezadoColumna" align="center">Código</th>
					<th width='10%' class="EncabezadoColumna" align="center">Título </th>
				</tr>
				
				<c:forEach begin="0" items="${requestScope.listaAsignaturaVO}" var="lista" varStatus="st">
					<tr>
						<th rowspan='<c:out value="${lista.filas+1}"/>' align="center">
				    	<input type="checkbox" name='x' value='<c:out value="${lista.codigo}"/>:<c:out value="${lista.codigoGrupo}"/>' onclick='javaScript:cambiocheck(this)'  <c:out value="${lista.checked}"/> <c:if test="${!lista.cupoLibre && lista.cupo==0}">disabled="disabled"</c:if>>
				    </th>
						<td rowspan='<c:out value="${lista.filas+1}"/>' align="center">&nbsp;<c:out value="${lista.identificacion}"/></td>
						<td rowspan='<c:out value="${lista.filas+1}"/>' align="center" title='<c:out value="${lista.descripcion}"/>' >&nbsp;<c:out value="${lista.nombre}"/></td>
						<td rowspan='<c:out value="${lista.filas+1}"/>' align="center">&nbsp;<c:out value="${lista.grupo}"/></td>
						<td rowspan='<c:out value="${lista.filas+1}"/>' align="center">&nbsp;<c:out value="${lista.docente}"/></td>
						<td rowspan='<c:out value="${lista.filas+1}"/>' align="center">&nbsp;<c:out value="${lista.creditos}"/></td>
						<td rowspan='<c:out value="${lista.filas+1}"/>' align="center">&nbsp;<c:out value="${lista.cupo}"/></td>
						</tr>
						<c:forEach begin="0" items="${lista.listaDia}" var="dia" varStatus="st3">
							<tr valign="baseline" align="center">
								<th valign="middle" align="center"><c:out value="${dia.dia}"/></th>
								<th valign="middle" align="center"><c:out value="${dia.horaIni}"/></th>
								<th valign="middle" align="center"><c:out value="${dia.horaFin}"/></th>
								<th valign="middle" align="center"><c:out value="${dia.espacioFisico}"/></th>
							</tr>
						</c:forEach>	
				</c:forEach>		
				</c:if>
		</table>
	</form>
</body>
</html>