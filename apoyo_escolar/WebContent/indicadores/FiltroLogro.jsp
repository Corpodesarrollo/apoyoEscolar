<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="filtroLogroVO" class="siges.indicadores.vo.FiltroLogroVO" scope="session"/>
<jsp:useBean id="paramsVO" class="siges.indicadores.vo.ParamsVO" scope="page"/>
<html>
<c:import url="/parametros.jsp"/>
<head>
<script language="javaScript">
<!--
	function lanzar(tipo){
  	document.frmFiltro.tipo.value=tipo;
		document.frmFiltro.target="";
		document.frmFiltro.submit();
	}
	
	function hacerValidaciones_frmFiltro(forma){
		validarLista(forma.filMetodologia, "- Metodologia");
		validarLista(forma.filGrado, "- Grado");
		validarLista(forma.filAsignatura, "- Asignatura");
		if(forma.filDocente) validarLista(forma.filDocente, "- Docente");
	}
	
	function buscar(){
		if(validarForma(document.frmFiltro)){
			document.frmFiltro.cmd.value=document.frmFiltro.BUSCAR.value;
			document.frmFiltro.submit();
		}
	}
	
	function editar(n,m,o){
		if(document.frmFiltro.id){
				document.frmFiltro.id[0].value=n;
				document.frmFiltro.id[1].value=m;
				document.frmFiltro.id[2].value=o;
				document.frmFiltro.cmd.value=document.frmFiltro.EDITAR.value;
				document.frmFiltro.submit();
		}
	}

	function eliminar(n,m,o){
		if(confirm('¿desea eliminar el logro?')){
			if(document.frmFiltro.id){
					document.frmFiltro.id[0].value=n;
					document.frmFiltro.id[1].value=m;
					document.frmFiltro.id[2].value=o;
					document.frmFiltro.cmd.value=document.frmFiltro.ELIMINAR.value;
					document.frmFiltro.submit();
			}
		}
	}
	
	function ajaxGrado0(){
		borrar_combo(document.frmFiltro.filGrado); 
		document.frmAjax0.ajax[0].value=document.frmFiltro.filInstitucion.value;
		document.frmAjax0.ajax[1].value=document.frmFiltro.filMetodologia.value;
		if(parseInt(document.frmAjax0.ajax[1].value)!=-99){
			document.frmAjax0.cmd.value=document.frmFiltro.AJAX_GRADO.value;
	 		document.frmAjax0.target="frameAjax0";
			document.frmAjax0.submit();
		}	
	}
	
	function ajaxAsignatura0(){
		borrar_combo(document.frmFiltro.filAsignatura); 
		document.frmAjax0.ajax[0].value=document.frmFiltro.filInstitucion.value;
		document.frmAjax0.ajax[1].value=document.frmFiltro.filMetodologia.value;
		document.frmAjax0.ajax[2].value=document.frmFiltro.filVigencia.value;
		document.frmAjax0.ajax[3].value=document.frmFiltro.filGrado.value;
		if(parseInt(document.frmAjax0.ajax[3].value)!=-99){
			document.frmAjax0.cmd.value=document.frmFiltro.AJAX_ASIGNATURA.value;
	 		document.frmAjax0.target="frameAjax0";
			document.frmAjax0.submit();
		}	
	}

	function ajaxDocente0(){
		borrar_combo(document.frmFiltro.filDocente); 
		document.frmAjax0.ajax[0].value=document.frmFiltro.filInstitucion.value;
		document.frmAjax0.ajax[1].value=document.frmFiltro.filMetodologia.value;
		document.frmAjax0.ajax[2].value=document.frmFiltro.filVigencia.value;
		document.frmAjax0.ajax[3].value=document.frmFiltro.filGrado.value;
		document.frmAjax0.ajax[4].value=document.frmFiltro.filAsignatura.value;
		if(parseInt(document.frmAjax0.ajax[4].value)!=-99){
			document.frmAjax0.cmd.value=document.frmFiltro.AJAX_DOCENTE.value;
	 		document.frmAjax0.target="frameAjax0";
			document.frmAjax0.submit();
		}	
	}
//-->
</script>
<style type="text/css">
TD.norepeat{
background-image:url(../etc/img/tabs/pestana_1.gif); 
background-repeat:no-repeat;
}

TD.norepeat2{
background-image:url(../etc/img/tabs/pestana_0.gif); 
background-repeat:no-repeat;
cursor:pointer;
}

.sombra {
color:white;
text-shadow:#666666 3px 3px 3px;
filter: progid:DXImageTransform.Microsoft.Shadow(color='#666666', Direction=135, Strength=4);
font-size: 85%;
font-stretch: ultra-expanded;
line-height: 95%;
font-weight: bolder;
}
.sombran {
text-shadow:#666666 3px 3px 3px;
filter: progid:DXImageTransform.Microsoft.Shadow(color='#666666', Direction=135, Strength=4);
font-size: 85%;
font-stretch: ultra-expanded;
line-height: 95%;
font-weight: bolder;
}
</style>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">

	<form method="post" name="frmAjax0" action='<c:url value="/indicadores/Ajax.do"/>'>
		<INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_LOGRO}"/>'>
		<input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_AJAX}"/>'>
		<c:forEach begin="0" end="5" var="i"><input type="hidden" name="ajax"></c:forEach>
	</form>
	<form action='<c:url value="/indicadores/SaveLogro.jsp"/>' method="post" name="frmFiltro" onSubmit="return validarForma(frmFiltro)">
		<input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_LOGRO}"/>'>
		<input type="hidden" name="cmd" value=''>
		<c:forEach begin="0" end="4" var="i"><input type="hidden" name="id"></c:forEach>
		<input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
		<input type="hidden" name="ELIMINAR" value='<c:out value="${paramsVO.CMD_ELIMINAR}"/>'>
		<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
		<input type="hidden" name="AJAX_ASIGNATURA" value='<c:out value="${paramsVO.CMD_AJAX_ASIGNATURA}"/>'>
		<input type="hidden" name="AJAX_DOCENTE" value='<c:out value="${paramsVO.CMD_AJAX_DOCENTE}"/>'>
		<input type="hidden" name="AJAX_GRADO" value='<c:out value="${paramsVO.CMD_AJAX_GRADO}"/>'>
		<input type="hidden" name="filInstitucion" value='<c:out value="${sessionScope.filtroLogroVO.filInstitucion}"/>'>
		<input type="hidden" name="filVigencia" value='<c:out value="${sessionScope.filtroLogroVO.filVigencia}"/>'> 
		<table border="0" ALIGN="CENTER" width="95%">
			<tr align="left" colspan="2">
			<c:forEach begin="0" items="${sessionScope.logroanddesc}" var="logdes">
				<td width="15%" height="26" class="norepeat">
					<p align="center" class="sombra"><c:out value="${logdes[0]}"/></p>
				</td>
				<td width="15%" height="26" class="norepeat2" onclick='javaScript:lanzar(<c:out value="${paramsVO.FICHA_DESCRIPTOR}"/>)'>
				<p align="center" class="sombran"><c:out value="${logdes[1]}"/></p>
				</td>
				<td width="70%" height="26" >
				</td>
			</c:forEach>
			</tr>
		</table>
		<table border="0" align="center" width="95%">
		<tr><td style="BACKGROUND: #FF9000;font-weight:bold;COLOR: #003366;FONT-SIZE: 11px;LINE-HEIGHT: bolder;FONT-FAMILY: Trebuchet MS,Arial, Helvetica, sans-serif;TEXT-ALIGN: center;TEXT-DECORATION: bold;vertical-align: middle;text-transform: uppercase;">Filtro de búsqueda</td></tr>
			<tr>
				<td>
 						<input name="cmd1" type="button" value="Buscar" onClick="buscar()" class="boton">
		  		</td>
		 	</tr>	
		</table>
		<table border="0" align="center" width="95%">
			<tr>
				<td><span class="style2">*</span> Metodología:</td>
				<td>
					<select name="filMetodologia" style="width:120px" onchange="javaScript:ajaxGrado0()">
						<option value="-99" selected>--Seleccione uno--</option>
						<c:forEach begin="0" items="${requestScope.listaMetodologia}" var="metod">
							<option value="<c:out value="${metod.codigo}"/>" <c:if test="${metod.codigo==sessionScope.filtroLogroVO.filMetodologia}">selected</c:if>><c:out value="${metod.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
				<td><span class="style2">*</span> Grado:</td>
				<td>
					<select name="filGrado" style="width:120px" onchange="javaScript:ajaxAsignatura0()">
						<option value="-99" selected>--Seleccione uno--</option>
						<c:forEach begin="0" items="${requestScope.listaGrado}" var="grado">
							<option value="<c:out value="${grado.codigo}"/>" <c:if test="${grado.codigo==sessionScope.filtroLogroVO.filGrado}">selected</c:if>><c:out value="${grado.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
				</tr>
				<tr>
				<td><span class="style2">*</span> Asignatura:</td>
				<td>
					<select name="filAsignatura" style="width:200px" onchange="javaScript:ajaxDocente0()">
						<option value="-99" selected>--Seleccione uno--</option>
						<c:forEach begin="0" items="${requestScope.listaAsignatura}" var="asig">
							<option value="<c:out value="${asig.codigo}"/>" <c:if test="${asig.codigo==sessionScope.filtroLogroVO.filAsignatura}">selected</c:if>><c:out value="${asig.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<c:if test="${sessionScope.filtroLogroVO.filPlanEstudios==1}">
			<tr>
				<td><span class="style2">*</span> Docente:</td>
				<td colspan="3">
					<select name="filDocente" style="width:200px">
						<option value="-99" selected>--Seleccione uno--</option>
						<c:forEach begin="0" items="${requestScope.listaDocente}" var="doc">
							<option value="<c:out value="${doc.codigo}"/>" <c:if test="${doc.codigo==sessionScope.filtroLogroVO.filDocente}">selected</c:if>><c:out value="${doc.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
			</c:if>			
			<tr style="display:none"><td><iframe name="frameAjax0" id="frameAjax0"></iframe></td></tr>
		</table><br> 	
		<!-- TABLA DE LOS RESULTADOS DE LA BUSQUEDA -->
		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray">
		 	<caption>Listado de logros</caption>
		 	<c:if test="${empty requestScope.listaLogro}"><tr><th class="Fila1" colspan='6'>No hay logros</th></tr></c:if>
			<c:if test="${!empty requestScope.listaLogro}">
				<tr>
					<th width='20' class="EncabezadoColumna" colspan="2">&nbsp;</th>
					<td class="EncabezadoColumna" align="center">Nombre</td>
					<td class="EncabezadoColumna" align="center">Abreviatura</td>
					<td class="EncabezadoColumna" align="center">Orden</td>
				</tr>
				<c:forEach begin="0" items="${requestScope.listaLogro}" var="lista" varStatus="st">
					<tr>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.logConsecutivo}"/></td>
						<th class='Fila<c:out value="${st.count%2}"/>'>
							<a href='javaScript:editar(<c:out value="${lista.logCodigoJerarquia}"/>,<c:out value="${lista.logCodigo}"/>,<c:out value="${lista.logVigencia}"/>);'><img border='0' src='<c:url value="/etc/img/editar.png"/>' width='15' height='15'></a>
							<c:if test="${sessionScope.NivelPermiso==2 && sessionScope.filtroLogroVO.filInHabilitado==0}"><a href='javaScript:eliminar(<c:out value="${lista.logCodigoJerarquia}"/>,<c:out value="${lista.logCodigo}"/>,<c:out value="${lista.logVigencia}"/>);'><img border='0' src='<c:url value="/etc/img/eliminar.png"/>' width='15' height='15'></a></c:if>
						</th>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.logNombre}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.logAbreviatura}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.logOrden}"/></td>
					</tr>
				</c:forEach>
			</c:if>
		</table>
	</form>
</body>
</html>