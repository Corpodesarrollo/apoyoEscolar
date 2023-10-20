<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="escValorativaVO" class="articulacion.escValorativa.vo.EscValorativaVO" scope="session"/>
<jsp:useBean id="paramsVO" class="articulacion.escValorativa.vo.ParamsVO" scope="page"/>
<html>
<head>
<script languaje="javaScript">
	var nav4=window.Event ? true : false;
	
	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}
	
	function nuevo(){
		document.frmNuevo.cmd.value=document.frmNuevo.NUEVO.value;
		document.frmNuevo.action='<c:url value="/articulacion/escValorativa/Nuevo.do"/>';
		document.frmNuevo.submit();
	}

	function guardar(){
		var a=document.frmNuevo.artEscValRangoIni.value;
		var b=document.frmNuevo.artEscValRangoFin.value;
		
		if(validarForma(document.frmNuevo)){
			if(parseFloat(a)<parseFloat(b)){
				document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
				document.frmNuevo.submit();
			}else{
				alert("El Rango Final no puede ser menor o igual que el Rango Inicial")
			}
		}
		
		
	}

	function setNombre(obj){
		var a=obj.options[obj.selectedIndex].text;
		if(document.abr.nombre){
			if(document.abr.nombre.length){
				for(var j=0;j<document.abr.nombre.length ;j++){
			 		if(a==document.abr.abreviatura[j].value){
			 			document.frmNuevo.artEscValNombre.value=document.abr.nombre[j].value;
			 		}
			 	}
			 }else{
	//			 alert("solo hay uno");
			 	document.frmNuevo.artEscValNombre.value=document.abr.nombre.value;
			 }
	 	}
	}
	
	function hacerValidaciones_frmNuevo(forma){
		validarLista(forma.artEscValConceptual, "- Valor Conceptual", 1);
		validarCampo(forma.artEscValNombre, "- Nombre de la escala", 1, 60);
		validarFloatOpcional(forma.artEscValRangoIni,'- Rango Inicial',0,100);
		validarFloatOpcional(forma.artEscValRangoFin,'- Rango Final',0,100);
	}
	
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:100px;overflow:auto;vertical-align:top;background-color:#ffffff;">
				<c:import url="/articulacion/escValorativa/Filtro.do"/>
			</div>
		</td></tr>
	</table>
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:120px;overflow:auto;vertical-align:top;background-color:#ffffff;">
				<c:import url="/articulacion/escValorativa/Lista.do"/>
			</div>
		</td></tr>
	</table>
	
	
 	<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/articulacion/escValorativa/Save.jsp"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		
		<input type="hidden" name="artEscValCodInst" value='<c:out value="${sessionScope.login.instId}"/>'>
		<input type="hidden" name="artEscValCodMetod" value='<c:out value="${sessionScope.login.metodologiaId}"/>'>
		<input type="hidden" name="artEscValAnoVigencia" value='<c:out value="${sessionScope.filtroEscalasVO.anVigencia}"/>'>
		<input type="hidden" name="artEscValPerVigencia" value='<c:out value="${sessionScope.filtroEscalasVO.perVigencia}"/>'>
		<input type="hidden" name="estado" valie='<c:out value="${sessionScope.grupoVO.formaEstado}"/>'>
		
		
		<table border="0" align="center" bordercolor="#FFFFFF" width="100%">
			<caption>Información Escala Valorativa</caption>
				<tr>
					<td width="45%">
						<c:if test="${sessionScope.NivelPermiso==2}">
							<input name="cmd1" type="button" value="Nuevo" onClick="nuevo()" class="boton">
		        			<c:if test="${sessionScope.escValorativaVO.formaEstado==1}">
		        				<input name="cmd1" type="button" value="Actualizar" onClick="guardar()" class="boton">
		        			</c:if>
	    					<c:if test="${sessionScope.escValorativaVO.formaEstado!=1}">
	    						<input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton">
	    					</c:if>
	    				</c:if>
			  		</td>
			 	</tr>	
		  </table>
	 	 <table width="100%" border="0" cellspacing="2" cellpadding="0">
			<tr>
				<td>
					<span class="style2">*</span><b>Valor Conceptual:</b>
				</td>
				<td>
					<select name="artEscValConceptual" onchange="setNombre(this)">
						<option value="0" >--</option>
						<c:forEach begin="0" items="${requestScope.listaAbreviaturaVO}" var="abr">
							<option value="<c:out value="${abr.abreviatura}"/>" <c:if test="${abr.abreviatura==sessionScope.escValorativaVO.artEscValConceptual}">selected</c:if>><c:out value="${abr.abreviatura}"/></option>
						</c:forEach>
					</select>
				</td>
				<td>
					<span class="style2">*</span><b>Nombre:</b>
				</td>
				<td>
					<input type="text" name="artEscValNombre" maxlength="60" size="15"  value='<c:out value="${sessionScope.escValorativaVO.artEscValNombre}"/>'></input>
				</td>
			</tr>
			<tr>
				<td>
					<span class="style2">*</span><b>Rango Inicial Porcentual:</b>
				</td>
				<td>
					<input type="text" name="artEscValRangoIni" maxlength="5" size="5" value='<c:out value="${sessionScope.escValorativaVO.artEscValRangoIni}"/>'>%</input>
				</td>
				<td>
					<span class="style2">*</span><b>Rango Final Porcentual:</b>
				</td>
				<td>
					<input type="text" name="artEscValRangoFin" maxlength="5" size="5"  value='<c:out value="${sessionScope.escValorativaVO.artEscValRangoFin}"/>'>%</input>
				</td>
			</tr>
		</table>
	</form>
	<form name="abr">
		<c:forEach begin="0" items="${requestScope.listaAbreviaturaVO}" var="lista">
			<input type="hidden" name="abreviatura" value='<c:out value="${lista.abreviatura}"/>'>
			<input type="hidden" name="nombre" value='<c:out value="${lista.nombre}"/>'>
		</c:forEach>
	</form>
	<form name="rangos">
		<c:forEach begin="0" items="${requestScope.listaRangosVO}" var="lista2">
			<input type="hidden" name="abr" value='<c:out value="${lista2.abreviatura}"/>'>
			<input type="hidden" name="inicio" value='<c:out value="${lista2.raIni}"/>'>
			<input type="hidden" name="fin" value='<c:out value="${lista2.raFin}"/>'>
		</c:forEach>
	</form>
</body>
</html>