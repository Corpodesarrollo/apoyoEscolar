<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ page import="siges.util.Recursos" %> 
<jsp:useBean id="nuevoBasica" class="siges.estudiante.beans.Basica" scope="session"/>
<jsp:useBean id="nuevoFamiliar" class="siges.estudiante.beans.Familiar" scope="session"/><jsp:setProperty name="nuevoFamiliar" property="*"/>
<jsp:useBean id="nuevoSalud" class="siges.estudiante.beans.Salud" scope="session"/><jsp:setProperty name="nuevoSalud" property="*"/>
<jsp:useBean id="nuevoConvivencia" class="siges.estudiante.beans.Convivencia" scope="session"/><jsp:setProperty name="nuevoConvivencia" property="*"/>
<jsp:useBean id="params2VO" class="siges.filtro.vo.FichaVO" scope="page"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<c:import url="/parametros.jsp"/>
<head>
<script language="javaScript">
<!--
function hacerValidaciones_frmFiltro(forma){
}
	
	function editarFiltro(){
		if(document.frmFiltro.id){
			if(validarForma(document.frmFiltro)){
				document.frmFiltro.cmd.value='Editar';
				document.frmFiltro.ext2.value='/estudiante/FiltroResultado.jsp';
				document.frmFiltro.target="centro";
				document.frmFiltro.action='<c:url value="/estudiante/ControllerFiltroSave.do"/>';
				document.frmFiltro.submit();
			}
		}
	}
	function editarPersona(id){
		document.frmFiltro.id.value=id;
		<c:if test="${!empty sessionScope.ModoConsulta}">
			document.frmFiltro.modcon.value='1';
		</c:if>
		editarFiltro();
	}
	
	function reorganizarTabla(){
		var tabla = document.getElementById("tablaAnuario");
		var arregloEstudiantes = [];
		var noCeldas = tabla.rows[0].cells.length;
		
		if(tabla){
			var ancho = tabla.offsetWidth;
			
			var noCeldasNuevo = parseInt(ancho/140);  
			
				
			for(var i = 0 ; i<tabla.rows.length ; i++){
				for(var j = 0; j < tabla.rows[i].cells.length; j++){
					arregloEstudiantes.push(tabla.rows[i].cells[j].innerHTML);
				}
			}
			tabla.innerHTML="";
			var i = 0;
			var row=tabla.insertRow(-1);
			while (arregloEstudiantes.length >0){
				if(i<noCeldasNuevo){
					var cell=row.insertCell(-1);
					cell.innerHTML = arregloEstudiantes.shift();
					i++;
				}else{
					row=tabla.insertRow(-1);
					i=0;
				}
			}
		}
		
	}
	//-->
	
	
//-->
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));" onresize="reorganizarTabla()" >
	<form action='<c:url value="/estudiante/ControllerFiltroEdit.do?cmd2=buscar"/>' method="post" id="frmFiltro" name="frmFiltro" onSubmit="return validarForma(frmFiltro)">
		<input type="hidden" name="cmd" value="">
	    <INPUT TYPE="hidden" NAME="cmd2"  VALUE="buscar">
		<input type="hidden" name="tipo" value="">
		<INPUT TYPE="hidden" NAME="ext2"  VALUE=''>
		<INPUT TYPE="hidden" NAME="height" VALUE='130'>
		<INPUT type='hidden' name='id'>
		<INPUT type='hidden' name='modcons' value='<c:out value="${requestScope.ModoConsulta}"/>'>
		<INPUT type='hidden' name='modcon' >
		
		<table border="0" align="center" width="95%">
			<caption>MOSAICO ESTUDIANTIL</caption>
			<caption><c:out value="${sessionScope.login.inst}"/></caption>
			<caption> Sede: 
				<c:forEach begin="0" items="${sessionScope.filtroSedeF}" var="fila">
					<c:if test="${sessionScope.filtroEst.sede == fila[0]}">
						<c:out value="${fila[1]}"/>
					</c:if>
				</c:forEach>
			</caption>
			<c:set var="mostrado" scope="page" value="false"/>
			<caption> Jornada: 
				<c:forEach begin="0" items="${sessionScope.filtroJornadaF}" var="fila" varStatus="st">
					<c:if test="${sessionScope.filtroEst.jornada == fila[0] and mostrado == false}">
						<c:out value="${fila[1]}"/>
						<c:set var="mostrado" value="true"/>
					</c:if>
				</c:forEach>
				<c:set var="mostrado" value="true"/>
			</caption>
			<caption> Grado: 
				<c:forEach begin="0" items="${sessionScope.listaGrado}" var="fila">
					<c:if test="${sessionScope.filtroEst.grado == fila.codigo}">
						<c:out value="${fila.nombre}"/>
					</c:if>
				</c:forEach>
			</caption>
			<caption> Grupo: 
				<c:forEach begin="0" items="${sessionScope.listaGrupo}" var="fila">
					<c:if test="${sessionScope.filtroEst.grupo == fila.codigo}">
						<c:out value="${fila.nombre}"/>
					</c:if>
				</c:forEach>
				<c:if test="${sessionScope.filtroEst.grupo eq '-9'}">
					Todos
				</c:if>
			</caption>
			<table id="tablaAnuario" border="1" align="center" width="100%" cellpadding="0" cellspacing="0" bordercolor="Silver">
					<!-- <tr>
						<th class="EncabezadoColumna">N°</th>
						<th class="EncabezadoColumna" >&nbsp;</th>
						<th class="EncabezadoColumna">Grupo</th>
						<th class="EncabezadoColumna">Identificación</th>
						<th class="EncabezadoColumna">Codigo Estudiantil</th>
					</tr> -->
					<c:if test="${empty sessionScope.filtroEstudiantes}">
						<tr>
							<th colspan='6'>NO HAY REGISTROS DE ESTA BUSQUEDA</th>
						</tr>
					</c:if>
					<c:if test="${!empty sessionScope.filtroEstudiantes}">
							<tr>
							<c:forEach begin="0" items="${sessionScope.filtroEstudiantes}" var="fila" varStatus="st">
								<td class='Fila<c:out value="${st.count%2}"/>'>
									<table width="100%" height="100%" style="height: 100%;">
										<tr>
											<td style="text-align: center;">
												<img src='<c:url value="/recursos/imagen.jpg?tipo=${params2VO.RECURSO_FOTOGRAFIA_ESTUDIANTE}&param=${fila[0]}"/>' width="113" height="149">
											</td>
										</tr>
										<tr>
											<td align="center" style=" height: 50px;" width="140">
												<a href='javaScript:editarPersona(<c:out value="${fila[0]}"/>);' onclick="setTimeout(function(){parent.close()},50);"><c:out value="${fila[3]}"/><br/><c:out value="${fila[2]}"/></a>
											</td>
										</tr>
									</table>
								</td>
								<c:if test="${st.count%5 eq '0'}">
								</tr>
								<tr>
								</c:if>
							</c:forEach>
							</tr>
					</c:if>
				</table>
		</table>
	</form>
</body>
</html>
