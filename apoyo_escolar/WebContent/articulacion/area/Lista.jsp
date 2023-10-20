<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="filAreVO" class="articulacion.area.vo.FiltroVO" scope="session"/>
<jsp:useBean id="paramsVO" class="articulacion.area.vo.ParamsVO" scope="page"/>
<jsp:useBean id="params2VO" class="articulacion.asignatura.vo.ParamsVO" scope="page"/>
<html>
<head>
<c:import url="/parametros.jsp"/>
<script language="javaScript">
<!--
	function lanzarServicio(n){
		if(n==1){//especialidad
			document.frmLista.action='<c:url value="/articulacion/especialidad/Nuevo.do"/>';  	
			document.frmLista.tipo.value='';
  	}
		if(n==2){//Area
			document.frmLista.action='<c:url value="/articulacion/area/Nuevo.do"/>';  	
			document.frmLista.tipo.value='';
  	}
		if(n==3){//Asignatura
			document.frmLista.action='<c:url value="/articulacion/asignatura/Nuevo.do"/>';  	
			document.frmLista.tipo.value='<c:out value="${params2VO.FICHA_ASIGNATURA}"/>';
  	}
		if(n==4){//Prueba
			document.frmLista.action='<c:url value="/articulacion/asignatura/Nuevo.do"/>';  	
			document.frmLista.tipo.value='<c:out value="${params2VO.FICHA_PRUEBA}"/>';
  	}
		if(n==5){//Asignación académica
  		document.frmLista.action='<c:url value="/articulacion/asigAcademica/Nuevo.do"/>';  	
			document.frmLista.tipo.value='';
  	}
		if(n==6){//Escala valorativa
  		document.frmLista.action='<c:url value="/articulacion/escValorativa/Nuevo.do"/>';  	
			document.frmLista.tipo.value='';
  	}
		document.frmLista.target="";
		document.frmLista.submit();
	}


	function hacerValidaciones_frmLista(forma){
		validarSeleccion(forma.id, "- Debe seleccionar un item");
	}
	
	function editar(inst,met,cod,anho,per){
		if(document.frmLista.id){
				document.frmLista.id.value=inst;
				document.frmLista.id2.value=met;
				document.frmLista.id3.value=cod;
				document.frmLista.id4.value=anho;
				document.frmLista.id5.value=per;
				document.frmLista.cmd.value=document.frmLista.EDITAR.value;
				document.frmLista.submit();
		}
	}
	
	function eliminar(inst,met,cod,anho,per){
		if(document.frmLista.id){
			if(confirm('¿Confirma la eliminación del área?')){
				document.frmLista.id.value=inst;
				document.frmLista.id2.value=met;
				document.frmLista.id3.value=cod;
				document.frmLista.id4.value=anho;
				document.frmLista.id5.value=per;
				document.frmLista.cmd.value=document.frmLista.ELIMINAR.value;
				document.frmLista.submit();
			}
		}
	}
	
	function buscar(){
		if(validarForma(document.frmLista)){
			document.frmLista.cmd.value=document.frmLista.BUSCAR.value;
			document.frmLista.submit();
		}
	}
	function hacerValidaciones_frmLista(forma){
		validarLista(forma.filAnho, "- Año de vigenca", 0)
		validarLista(forma.filPer, "- Periodo de vigenca", 1)
	}
//-->
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form action='<c:url value="/articulacion/area/Save.jsp"/>' method="post" name="frmLista" onSubmit="return validarForma(frmLista)">
		<INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_DEFAULT}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="id" value=""><input type="hidden" name="id2" value=""><input type="hidden" name="id3" value=""><input type="hidden" name="id4" value=""><input type="hidden" name="id5" value="">
		<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
		<input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
		<input type="hidden" name="ELIMINAR" value='<c:out value="${paramsVO.CMD_ELIMINAR}"/>'>
		<input type="hidden" name="guia" value="<c:out value="${requestScope.guia}"/>">
		<input type="hidden" name="filInstitucion" value='<c:out value="${sessionScope.login.instId}"/>'>
		<input type="hidden" name="filMetodologia" value='<c:out value="${sessionScope.login.metodologiaId}"/>'>
		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1">
				<td width="10">&nbsp;</td>
				<td rowspan="2" width="520">
					<a href="javaScript:lanzarServicio(1)"><img src='<c:url value="/etc/img/tabs/especialidad_0.gif"/>' alt="Especialidad" height="26" border="0"></a>
					<img src='<c:url value="/etc/img/tabs/areas_1.gif"/>' alt="Area" height="26" border="0">
					<a href="javaScript:lanzarServicio(3)"><img src='<c:url value="/etc/img/tabs/asignatura_0.gif"/>' alt="Asignatura" height="26" border="0"></a>
					<a href="javaScript:lanzarServicio(4)"><img src='<c:url value="/etc/img/tabs/prueba_0.gif"/>' alt="Prueba" height="26" border="0"></a>
					<a href="javaScript:lanzarServicio(5)"><img src='<c:url value="/etc/img/tabs/carga_academica_0.gif"/>' alt="Asignación académica" height="26" border="0"></a>
					<a href="javaScript:lanzarServicio(6)"><img src='<c:url value="/etc/img/tabs/escalas_valorativas_0.gif"/>' alt="Escala valorativa" height="26" border="0"></a>

				</tr>
		</table>
		<table border="0" align="center" bordercolor="#FFFFFF" width="100%">
			<caption>FILTRO DE BÚSQUEDA</caption>
				<tr><td width="45%">
		        <input name="cmd1" type="button" value="Buscar" onClick="buscar()" class="boton">
			  </td></tr>	
	  </table>
		<table border="0" align="center" width="95%" cellpadding="2" cellspacing="0">
			<tr>
			<td>&nbsp;Vigencia:</td>
			<td>&nbsp; 
					<select name="filAnho" style='width:50px;'>
						<c:forEach begin="0" items="${requestScope.listaVigencia}" var="item">
							<option value='<c:out value="${item.codigo}"/>' <c:if test="${item.codigo==sessionScope.filAreVO.filAnho}">selected</c:if>><c:out value="${item.nombre}"/></option>
						</c:forEach>
					</select>
					<select name="filPer" style='width:30px;'>
						<option value="-99">--</option>
						<option value="1" <c:if test="${1==sessionScope.filAreVO.filPer}">selected</c:if>>1</option>
						<option value="2" <c:if test="${2==sessionScope.filAreVO.filPer}">selected</c:if>>2</option>
					</select>
			</td>
			</tr>
		</table>		
		<c:if test="${sessionScope.filAreVO.formaEstado==1}">
			<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0">
		 	<caption>LISTADO DE AREAS</caption>
			  <c:if test="${empty requestScope.listaAreaVO}">
					<tr><th class="Fila1" colspan='6'>NO EXISTEN REGISTROS DE AREA</th></tr>
				</c:if>
			 	<c:if test="${!empty requestScope.listaAreaVO}">
		  		<tr>
						<th width='30' class="EncabezadoColumna">&nbsp;</th>
						<th class="EncabezadoColumna">Código</th>
						<th class="EncabezadoColumna">Nombre</th>
						<th class="EncabezadoColumna">Abreviatura</th>
					</tr>
					<c:forEach begin="0" items="${requestScope.listaAreaVO}" var="lista" varStatus="st">
						<tr>
							<th class='Fila<c:out value="${st.count%2}"/>'>
							<a href='javaScript:editar(<c:out value="${lista.areInstitucion}"/>,<c:out value="${lista.areMetodologia}"/>,<c:out value="${lista.areCodigo}"/>,<c:out value="${lista.areAnhoVigencia}"/>,<c:out value="${lista.arePerVigencia}"/>);'>
							<img border='0' src='../../etc/img/<c:if test="${requestScope.guia==lista.areInstitucion && requestScope.guia2==lista.areMetodologia && requestScope.guia3==lista.areCodigo && requestScope.guia4==lista.areAnhoVigencia && requestScope.guia5==lista.arePerVigencia}">X</c:if>editar.png' width='15' height='15'>
							</a>
							<c:if test="${sessionScope.NivelPermiso==2}"><a href='javaScript:eliminar(<c:out value="${lista.areInstitucion}"/>,<c:out value="${lista.areMetodologia}"/>,<c:out value="${lista.areCodigo}"/>,<c:out value="${lista.areAnhoVigencia}"/>,<c:out value="${lista.arePerVigencia}"/>);'><img border='0' src='../../etc/img/eliminar.png' width='15' height='15'></a></c:if>
							</th>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.areIdentificacion}"/></td>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.areNombre}"/></td>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.areAbreviatura}"/></td>
						</tr>
					</c:forEach>
				</c:if>
			</table>
		</c:if>
	</form>
</body>
</html>