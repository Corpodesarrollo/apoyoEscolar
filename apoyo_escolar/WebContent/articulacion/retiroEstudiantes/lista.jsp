<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="paramsVO" class="articulacion.retiroEstudiantes.vo.ParamsVO" scope="page"/>
<html>
<c:import url="/parametros.jsp"/>
<head>
<script language="javaScript">
<!--
function cambiocheck(obj){
		var nombre=obj.name;
		var valor=obj.value;
    if(document.forms['frmNuevo'].elements){
        for(var i=0;i<document.forms['frmNuevo'].elements.length;i++){
            if(document.forms['frmNuevo'].elements[i].type=='checkbox'){
                if(document.forms['frmNuevo'].elements[i].name==nombre){
                    if(document.forms['frmNuevo'].elements[i].value!=valor){
                        document.forms['frmNuevo'].elements[i].checked=false;
                    }        
                }
            }
        }
    }
	}
	
 function setChange(item){
  var pagina='<c:url value="/articulacion/retiroEstudiantes/MotivoRetiro.do"/>';
  pagina+='?cmd=21';
  pagina+='&param='+item;
  //alert(pagina);
   window.open(pagina,'3','width=450,height=170,resizable=no,toolbar=no,directories=no,menubar=no,status=yes');
 }

 function setChange2(item){
  var pagina='<c:url value="/articulacion/retiroEstudiantes/MotivoRetiro.do"/>';
  pagina+='?cmd=20';
  pagina+='&param='+item;
 // alert(pagina);
   //disparar el metodo que abre la ventana
   window.open(pagina,'3','width=450,height=170,resizable=no,toolbar=no,directories=no,menubar=no,status=yes');
 }
	
	function guardar(){
	//	copiarChecks(document.frmLista);
	/*
		if(document.frmLista.artEvaEstado_.checked==true){
			document.frmLista.artEvaEstado.value='true';
		}else{
		
			document.frmLista.artEvaEstado.value='false';
		}
	*/	
		document.frmNuevo.cmd.value=3;
		document.frmNuevo.submit();

	}
		
	
//-->
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:190px;overflow:auto;vertical-align:top;background-color:#ffffff;">
				<c:import url="/articulacion/retiroEstudiantes/Filtro.do"/>
			</div>
		</td></tr>
	</table>
	
	<form action='<c:url value="/articulacion/retiroEstudiantes/Save.jsp"/>' method="post" name="frmNuevo" onSubmit="return validarForma(frmLista)">
		<input type="hidden" name="tipo" value="1">
		<input type="hidden" name="cmd" value="1">
		<table border="0" align="center" bordercolor="#FFFFFF" width="100%">
				<tr>
					<td width="45%">
					<c:if test="${!empty requestScope.listaAsignaturaVO}">
					<input name="cmd1" type="button" value="Aceptar" onClick="guardar()" class="boton">
					</c:if>
	    			</td>
			 	</tr>	
	  </table>
		<table border="1" cellpadding="0" cellspacing="0"  width="100%">
		 	<caption>INSCRIPCIÓN</caption>
		 	<c:if test="${empty requestScope.listaAsignaturaVO}">
				<tr>
					<th class="Fila1" colspan='6'>NO EXISTEN REGISTROS DE ASIGNATURAS</th>
				</tr>
			</c:if>
			<c:if test="${!empty requestScope.listaAsignaturaVO}">
				<tr>
					<th width='3%' rowspan=2 bgcolor="#ffffff" class="EncabezadoColumna"  align="center">&nbsp;</th>
					<th width='4%' colspan=3  class="EncabezadoColumna" align="center">Asignatura</th>
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
					<th width='10%' class="EncabezadoColumna" align="center">Estado </th>
					<th width='4%' class="EncabezadoColumna" align="center">Código</th>
					<th width='10%' class="EncabezadoColumna" align="center">Título </th>
				</tr>
				<c:set var="indice" value="-1" scope="page"/>
				<c:forEach begin="0" items="${requestScope.listaAsignaturaVO}" var="lista" varStatus="st">
					<c:set var="indice" value="${pageScope.indice+1}"/>
					<tr>
						<c:if test="${lista.estado==1}">
							<!-- Activo -->
							<th rowspan='<c:out value="${lista.filas+1}"/>' align="center">
								<input type="hidden" name='x' value='<c:out value="${lista.codigo}"/>|<c:out value="${lista.codigoGrupo}"/>'>
								<input type="hidden" name='band' value='0'>
								<input type="hidden" name='mot' value=''>
								<input type="hidden" name='desc' value=''>
								<input name="cmd1" type="button" value="Cancelar" onclick='javaScript:setChange(<c:out value="${pageScope.indice}"/>)' class="boton">
							</th>
						</c:if>
						<c:if test="${lista.estado==2}">
							<!-- Activo -->
							<th rowspan='<c:out value="${lista.filas+1}"/>' align="center">
								<input name="cmd1" type="button" value="Ver motivo" onclick="javaScript:setChange2('<c:out value="${lista.codigo}"/>|<c:out value="${lista.codigoGrupo}"/>')" class="boton">
							</th>
						</c:if>

						<td rowspan='<c:out value="${lista.filas+1}"/>' align="center">&nbsp;<c:out value="${lista.nombreEstado}"/></td>
						<td rowspan='<c:out value="${lista.filas+1}"/>' align="center">&nbsp;<c:out value="${lista.identificacion}"/></td>
						<td rowspan='<c:out value="${lista.filas+1}"/>' align="center" title='<c:out value="${lista.descripcion}"/>' >&nbsp;<c:out value="${lista.nombre}"/></td>
						<td rowspan='<c:out value="${lista.filas+1}"/>' align="center">&nbsp;<c:out value="${lista.grupo}"/></td>
						<td rowspan='<c:out value="${lista.filas+1}"/>' align="center">&nbsp;<c:out value="${lista.docente}"/></td>
						<td rowspan='<c:out value="${lista.filas+1}"/>' align="center">&nbsp;<c:out value="${lista.creditos}"/></td>
						<td rowspan='<c:out value="${lista.filas+1}"/>' align="center">&nbsp;<c:out value="${lista.cupo}"/></td>						
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