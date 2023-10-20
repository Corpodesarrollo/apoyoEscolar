<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="paramsVO" class="articulacion.retiroEstudiantes.vo.ParamsVO" scope="page"/>
<html>
<c:import url="/parametros.jsp"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script language="javaScript">
<!--
	function hacerValidaciones_frmLista(forma){
		validarSeleccion(forma.id, "- Debe seleccionar un item");
	}
	
	function guardar(){
    if(document.forms['frmNuevo'].elements){
        for(var i=0;i<document.forms['frmNuevo'].elements.length;i++){
            if(document.forms['frmNuevo'].elements[i].type=='checkbox'){
                if(document.forms['frmNuevo'].elements[i].name.substring(0,2)=='x|'){
                    if(document.forms['frmNuevo'].elements[i].checked==true && document.forms['frmNuevo'].elements[i].disabled==true){
                        document.forms['frmNuevo'].elements[i].disabled=false;
                    }
                }
            }
        }
    }
	document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
    document.frmNuevo.submit(); 
	}

	function editar(n,f){
	
		if(document.frmLista.id && document,frmLista.id2){
				document.frmLista.id.value=n;
				document.frmLista.id2.value=f;
				document.frmLista.cmd.value=document.frmLista.EDITAR.value;
				document.frmLista.submit();
		}
	}
	
	function eliminar(n,f){
		if(document.frmLista.id && document,frmLista.id2){
			if(confirm('¿DESEA ELIMINAR ESTE REGISTRO?')){
				document.frmLista.id.value=n;
				document.frmLista.id2.value=f;
				document.frmLista.cmd.value=document.frmLista.ELIMINAR.value;
				document.frmLista.submit();
			}
		}
	}
	
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
					<td width="45%">
					<c:if test="${!empty requestScope.listaAsignaturaVO}">
					<input name="cmd1" type="button" value="Inscribir" onClick="guardar()" class="boton">
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
				<c:forEach begin="0" items="${lista.conteo}" var="conteo" varStatus="st2">
					<tr>
						
						<c:if test="${lista.cupogeneral!=0||lista.cupogeneral>0}">
							<c:if test="${lista.cupogeneral==lista.inscgeneral}">
								<th rowspan='<c:out value="${lista.filas+1}"/>' align="center">
									<input type="checkbox" <c:if test="${lista.cupoLibre==false}">disabled="disabled"</c:if> name='x|<c:out value="${lista.codigo}"/>' value='<c:out value="${lista.codigoGrupo}"/>' <c:out value="${lista.checked}"/>>
								</th>
							</c:if>
							<c:if test="${lista.cupogeneral!=lista.inscgeneral}">
								<th rowspan='<c:out value="${lista.filas+1}"/>' align="center">
						    		<input type="checkbox" name='x|<c:out value="${lista.codigo}"/>' value='<c:out value="${lista.codigoGrupo}"/>' onclick='javaScript:cambiocheck(this)'  <c:out value="${lista.checked}"/>>
						    	</th>
						    </c:if>
					    
						</c:if>
						<c:if test="${lista.cupogeneral==0||lista.cupogeneral<0}">
							<c:if test="${lista.nivel==1}">
									<c:if test="${lista.grucuponivel==lista.gruinscnivel}">
										<th rowspan='<c:out value="${lista.filas+1}"/>' align="center">
											<input type="checkbox" <c:if test="${lista.cupoLibre==false}">disabled="disabled"</c:if> name='x|<c:out value="${lista.codigo}"/>'  value='<c:out value="${lista.codigoGrupo}"/>' <c:out value="${lista.checked}"/>>
										</th>
									</c:if>
						<c:if test="${lista.grucuponivel!=lista.gruinscnivel}">
									<th rowspan='<c:out value="${lista.filas+1}"/>' align="center">
								    <input type="checkbox" name='x|<c:out value="${lista.codigo}"/>' value='<c:out value="${lista.codigoGrupo}"/>' onclick='javaScript:cambiocheck(this)'  <c:out value="${lista.checked}"/>>
								    </th>
						    	</c:if>
									
						    </c:if>
						
						<c:if test="${lista.nivel==0}">
							   
						    <c:if test="${lista.grucupononivel==lista.gruinscnonivel }">
										<th rowspan='<c:out value="${lista.filas+1}"/>' align="center">
											<input type="checkbox" <c:if test="${lista.cupoLibre==false}">disabled="disabled"</c:if> name='x|<c:out value="${lista.codigo}"/>' value='<c:out value="${lista.codigoGrupo}"/>' <c:out value="${lista.checked}"/>>
										</th>
									</c:if>
									
							    <c:if test="${lista.grucupononivel!=lista.gruinscnonivel}">
									<th rowspan='<c:out value="${lista.filas+1}"/>' align="center">
								    <input type="checkbox" name='x|<c:out value="${lista.codigo}"/>' value='<c:out value="${lista.codigoGrupo}"/>' onclick='javaScript:cambiocheck(this)'  <c:out value="${lista.checked}"/>>
								    </th>
							    </c:if>
					    </c:if>
					</c:if>
						
						<td rowspan='<c:out value="${lista.filas+1}"/>' align="center">&nbsp;<c:out value="${lista.identificacion}"/></td>
						<td title='<c:out value="${lista.descripcion}"/>' rowspan='<c:out value="${lista.filas+1}"/>' align="center">&nbsp;<c:out value="${lista.nombre}"/></td>
						<td rowspan='<c:out value="${lista.filas+1}"/>' align="center">&nbsp;<c:out value="${lista.grupo}"/></td>
						<td rowspan='<c:out value="${lista.filas+1}"/>' align="center">&nbsp;<c:out value="${lista.docente}"/></td>
						<td rowspan='<c:out value="${lista.filas+1}"/>' align="center">&nbsp;<c:out value="${lista.creditos}"/></td>
						
						<c:if test="${lista.cupogeneral!=0||lista.cupogeneral>0}">
							<td rowspan='<c:out value="${lista.filas+1}"/>' align="center">&nbsp;<c:out value="${lista.cupogeneral-lista.inscgeneral}"/></td>
							<input type="hidden" name="cuposn" value="inscgeneral">
						</c:if>
						
						<c:if test="${lista.cupogeneral==0||lista.cupogeneral<0}">					
							<c:if test="${lista.nivel==1}">
								<td rowspan='<c:out value="${lista.filas+1}"/>' align="center">&nbsp;<c:out value="${lista.grucupononivel-lista.gruinscnonivel}"/></td>
								<input type="hidden" name="cuposn" value="gruinscnonivel">
							</c:if>
						</c:if>
						
						<c:if test="${lista.cupogeneral==0||lista.cupogeneral<0}">
							<c:if test="${lista.nivel==0}">
								<td rowspan='<c:out value="${lista.filas+1}"/>' align="center">&nbsp;<c:out value="${lista.grucuponivel-lista.gruinscnivel}"/></td>
								<input type="hidden" name="cuposn" value="gruinscnivel">
							</c:if>
					    </c:if>	
							
									<c:forEach begin="0" items="${lista.listaDia}" var="dia" varStatus="st3">
										<tr valign="baseline" align="center">
										<th valign="middle" align="center"><c:out value="${dia.dia}"/></th>
										<th valign="middle" align="center"><c:out value="${dia.horaIni}"/></th>
										<th valign="middle" align="center"><c:out value="${dia.horaFin}"/></th>
										<th valign="middle" align="center"><c:out value="${dia.espacioFisico}"/></th>
										</tr>
									</c:forEach>	
									</c:forEach>							
				</c:forEach>		
				</c:if>
		</table>
	</form>
</body>
</html>