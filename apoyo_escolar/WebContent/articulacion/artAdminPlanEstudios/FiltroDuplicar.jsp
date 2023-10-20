<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="filtroBorrarVO" class="articulacion.artAdminPlanEstudios.vo.FiltroBorrarVO" scope="session"/>
<jsp:useBean id="paramsVO" class="articulacion.artAdminPlanEstudios.vo.ParamsVO" scope="page"/>
<html>
<head>
<c:import url="/parametros.jsp"/>
<script language="javaScript">
<!--
	var nav4=window.Event ? true : false;

	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}
	
	function buscar(){
		if(validarForma(document.frmFiltro)){
			document.frmFiltro.cmd.value=document.frmFiltro.BUSCAR.value;
			document.frmFiltro.submit();
		}
	}

	function lanzar(tipo){
		document.frmFiltro.tipo.value=tipo;
		document.frmFiltro.action='<c:url value="/articulacion/artAdminPlanEstudios/Filtro.do"/>';
		document.frmFiltro.cmd.value='<c:out value="${paramsVO.CMD_NUEVO}"/>';
		document.frmFiltro.target="";
		document.frmFiltro.submit();
	}
	
	function hacerValidaciones_frmFiltro(forma){
		validarLista(forma.filAnhoVigencia, "- Año de vigencia origen", 0)
		validarLista(forma.filPerVigencia, "- Periodo de vigencia origen", 1)
		validarLista(forma.filAnhoVigencia2, "- Año de vigencia destino", 0)
		validarLista(forma.filPerVigencia2, "- Periodo de vigencia destino", 1)
		validarSeleccion(forma.filOpcion, "- Opción de duplicado")
	}
	
//-->
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form action='<c:url value="/articulacion/artAdminPlanEstudios/Save.jsp"/>' method="post" name="frmFiltro" onSubmit="return validarForma(frmFiltro)">
		<INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_DUPLICAR}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
		<input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
		<input type="hidden" name="ELIMINAR" value='<c:out value="${paramsVO.CMD_ELIMINAR}"/>'>
		<input type="hidden" name="filInstitucion" value='<c:out value="${sessionScope.login.instId}"/>'>
		<input type="hidden" name="filMetodologia" value='<c:out value="${sessionScope.login.metodologiaId}"/>'>
		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1">
				<td width="10">&nbsp;</td>
				<td rowspan="2" width="520">
					<a href='javaScript:lanzar(<c:out value="${paramsVO.FICHA_BORRAR}"/>)'><img src='<c:url value="/etc/img/tabs/borrar_plan_0.gif"/>' alt="Importar" width="84"  height="26" border="0"></a>
					<img src='<c:url value="/etc/img/tabs/duplicar_plan_1.gif"/>' alt="Plantilla" height="26" border="0">
				</td>	
			</tr>
		</table>
		<table border="0" align="center" width="95%" cellpadding="2" cellspacing="0">
			<caption>DUPLICAR PLAN DE ESTUDIOS</caption>
			<tr><td width="45%">
        <input name="cmd1" type="button" value="Aceptar" onClick="buscar()" class="boton">
		  </td></tr>	
	  </table>
		<table border="0" align="center" width="95%" cellpadding="2" cellspacing="0">
			<tr>
			<td width="25%"><span class="style2">*</span>Vigencia origen:</td>
			<td width="75%">
					<select name="filAnhoVigencia" style='width:50px;'>
						<c:forEach begin="0" items="${requestScope.listaVigencia}" var="item">
							<option value='<c:out value="${item.codigo}"/>' <c:if test="${item.codigo==sessionScope.filtroBorrarVO.filAnhoVigencia}">selected</c:if>><c:out value="${item.nombre}"/></option>
						</c:forEach>
					</select>&nbsp;-&nbsp;
					<select name="filPerVigencia" style='width:30px;'>
						<option value="-99">--</option>
						<option value="1" <c:if test="${1==sessionScope.filtroBorrarVO.filPerVigencia}">selected</c:if>>1</option>
						<option value="2" <c:if test="${2==sessionScope.filtroBorrarVO.filPerVigencia}">selected</c:if>>2</option>
					</select>
			</td>
			</tr>
			<tr>
			<td><span class="style2">*</span>Vigencia destino:</td>
			<td>
					<select name="filAnhoVigencia2" style='width:50px;'>
						<c:forEach begin="0" items="${requestScope.listaVigencia}" var="item">
							<option value='<c:out value="${item.codigo}"/>' <c:if test="${item.codigo==sessionScope.filtroBorrarVO.filAnhoVigencia2}">selected</c:if>><c:out value="${item.nombre}"/></option>
						</c:forEach>
					</select>&nbsp;-&nbsp;
					<select name="filPerVigencia2" style='width:30px;'>
						<option value="-99">--</option>
						<option value="1" <c:if test="${1==sessionScope.filtroBorrarVO.filPerVigencia2}">selected</c:if>>1</option>
						<option value="2" <c:if test="${2==sessionScope.filtroBorrarVO.filPerVigencia2}">selected</c:if>>2</option>
					</select>
			</td>
			</tr>
			<tr>
				<td><span class="style2">*</span>Opción de duplicación:</td>
				<td>
					<table border="0" align="left" width="70%" cellpadding="2" cellspacing="0">
						<TR><td><input type="radio" name="filOpcion" value='<c:out value="${paramsVO.OPCION_TODO}"/>'> Todo</td></TR>
						<TR><td><input type="radio" name="filOpcion" value='<c:out value="${paramsVO.OPCION_AREA}"/>'> Área</td></TR>
						<TR><td><input type="radio" name="filOpcion" value='<c:out value="${paramsVO.OPCION_ASIGNATURA}"/>'> Asignatura</td></TR>
						<TR><td><input type="radio" name="filOpcion" value='<c:out value="${paramsVO.OPCION_PRUEBA}"/>'> Prueba</td></TR>
						<TR><td><input type="radio" name="filOpcion" value='<c:out value="${paramsVO.OPCION_ASIGNACION}"/>'> Asignación académica</td></TR>
						<TR><td><input type="radio" name="filOpcion" value='<c:out value="${paramsVO.OPCION_ESCALA}"/>'> Escala valorativa</td></TR>
						<TR><td><input type="radio" name="filOpcion" value='<c:out value="${paramsVO.OPCION_GRUPO}"/>'> Grupo</td></TR>
						<TR><td><input type="radio" name="filOpcion" value='<c:out value="${paramsVO.OPCION_HORARIO}"/>'> horario</td></TR>
					</table>
				</td>
			</tr>
		</table>		
	</form>
	<c:if test="${requestScope.formaEstado==1}">
		<TABLE width="95%" cellpadding="3" cellSpacing="0" align="center" border="0">
			<caption>RESULTADOS DE LA DUPLICACIÓN</caption>
			<c:forEach begin="0" items="${requestScope.listaResultado}" var="r">
				<tr>
					<th width="50%" align="right"><c:out value="${r.nombre}"/></th>
					<td align="left"><c:out value="${r.codigo}"/></td>
				</tr>
			</c:forEach>
		</TABLE>				
	</c:if>
</body>
</html>