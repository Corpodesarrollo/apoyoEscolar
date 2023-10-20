<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="planDeEstudiosVO" class="siges.gestionAcademica.planDeEstudios.vo.PlanDeEstudiosVO" scope="session"/>
<jsp:useBean id="paramsVO" class="siges.gestionAcademica.planDeEstudios.vo.ParamsVO" scope="page"/>
<html>
<head>
<script languaje="javaScript">
	var nav4=window.Event ? true : false;

	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}
		
	function hacerValidaciones_frmNuevo(forma){
		validarLista(forma.plaMetodologia, "- Metodologia", 1)
		validarCampo(forma.plaCriterio, "- Criterios de Evaluación", 1, 3000)
		validarCampo(forma.plaProcedimiento, "- Procedimientos de Evaluación", 1, 3000)
		validarCampo(forma.plaPlanEspecial, "- Planes Especiales de Apoyo", 1, 3000)
	}
		
	function nuevo(){
		document.frmNuevo.cmd.value=document.frmNuevo.NUEVO.value;
		document.frmNuevo.action='<c:url value="/planDeEstudios/Nuevo.do"/>';
		document.frmNuevo.submit();
	}

	function guardar(){
		if(validarForma(document.frmNuevo)){
			document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
			document.frmNuevo.submit();
		}
	}
	
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:150px;overflow:auto;vertical-align:top;background-color:#ffffff;">
				<c:import url="/siges/gestionAcademica/planDeEstudios/FiltroPlan.jsp"/>
			</div>
		</td></tr>
	</table>
	<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/siges/gestionAcademica/planDeEstudios/SavePlan.jsp"/>'>
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_PLAN}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="plaInstitucion" value='<c:out value="${sessionScope.planDeEstudiosVO.plaInstitucion}"/>'>
		<input type="hidden" name="plaVigencia" value='<c:out value="${sessionScope.planDeEstudiosVO.plaVigencia}"/>'>
	  <table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
		  <caption>Ingreso / edición de plan de estudios</caption>
				<tr>
					<td colspan="4">
						<c:if test="${sessionScope.NivelPermiso==2}">
    					<input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton">
							<input name="cmd1" type="button" value="Nuevo" onClick="nuevo()" class="boton">
    				</c:if>
			  		</td>
			 	</tr>	
			<tr>
			<td width="30%"><span class="style2">*</span> Metodologia:</td>
			<td width="70%">
				<select name="plaMetodologia" style="width:120px;" <c:out value="${sessionScope.planDeEstudiosVO.formaDisabled}"/>>
					<option value="-99" selected>--Seleccione uno--</option>
					<c:forEach begin="0" items="${requestScope.listaMetodologia}" var="metod">
						<option value="<c:out value="${metod.codigo}"/>" <c:if test="${metod.codigo==sessionScope.planDeEstudiosVO.plaMetodologia}">selected</c:if>><c:out value="${metod.nombre}"/></option>
					</c:forEach>
				</select>
			</td>
		 	</tr>	
			<tr>
				<td><span class="style2">*</span>Criterios de Evaluación:</td>
				<td colspan="3"><textarea name="plaCriterio" cols="50" rows="4" onKeyDown="textCounter(this,3000,3000);" onKeyUp="textCounter(this,3000,3000);"><c:out value="${sessionScope.planDeEstudiosVO.plaCriterio}"/></textarea></td>
		 	</tr>
			<tr>
				<td><span class="style2">*</span>Procedimientos de Evaluación:</td>
				<td colspan="3"><textarea name="plaProcedimiento" cols="50" rows="4" onKeyDown="textCounter(this,3000,3000);" onKeyUp="textCounter(this,3000,3000);"><c:out value="${sessionScope.planDeEstudiosVO.plaProcedimiento}"/></textarea></td>
		 	</tr>
			<tr>
				<td><span class="style2">*</span>Planes Especiales de Apoyo:</td>
				<td colspan="3"><textarea name="plaPlanEspecial" cols="50" rows="4" onKeyDown="textCounter(this,3000,3000);" onKeyUp="textCounter(this,3000,3000);"><c:out value="${sessionScope.planDeEstudiosVO.plaPlanEspecial}"/></textarea></td>
		 	</tr>
		</table>	
	</form>
</body>
</html>