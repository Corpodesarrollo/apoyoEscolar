<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="areaPlanVO" class="siges.gestionAcademica.planDeEstudios.vo.AreaVO" scope="session"/>
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
	
	    validarLista(forma.areVigencia, "- Vigencia", 1)
		validarLista(forma.areMetodologia, "- Metodologia", 1)
		validarLista(forma.areCodigo, "- Área", 1)
		validarCampo(forma.areNombre, "- Nombre", 1, 60)
		validarCampo(forma.areAbreviatura, "- Abreviatura", 1, 10)
		validarEntero(forma.areOrden, "- Orden", 1, 999)
		validarSeleccion(forma.areGrado_, "- Grado (al menos debe seleccionar uno)")
	}
		
	function nuevo(){
		document.frmNuevo.cmd.value=document.frmNuevo.NUEVO.value;
		document.frmNuevo.action='<c:url value="/planDeEstudios/Nuevo.do"/>';
		document.frmNuevo.submit();
	}

	function guardar(){
		if(validarForma(document.frmNuevo)){
			validarDatos(document.frmNuevo);
			document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
			document.frmNuevo.submit();
		}
	}
	
	function validarDatos(forma){
		if(forma.areGrado){
			if(forma.areGrado.length){
				for(i=0;i<forma.areGrado.length;i++){
					if(forma.areGrado_[i].checked==false){
						forma.areGrado[i].value='-1';
					}
				}
			}
		}
	}
	
	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) {
			if(navigator.appName == "Netscape") combo.options[i] = null;
			else combo.remove(i);
		}
		combo.options[0] = new Option("-Seleccione una-","-99");
	}
		
	function ajaxGrado(){
		document.frmAjax.ajax[0].value=document.frmNuevo.areInstitucion.value;
		document.frmAjax.ajax[1].value=document.frmNuevo.areMetodologia.value;
		document.frmAjax.ajax[2].value=document.frmNuevo.areVigencia.value;
		if(parseInt(document.frmAjax.ajax[1].value)!=-99){
			document.frmAjax.cmd.value=document.frmNuevo.AJAX_GRADO.value;
	 		document.frmAjax.target="frameAjax";
			document.frmAjax.submit();
		}	
	}
	
	function ajaxAreaBase(){
		document.frmAjax.ajax[0].value=document.frmNuevo.areCodigo.value;
		if(parseInt(document.frmAjax.ajax[0].value)!=-99){
			document.frmAjax.cmd.value=document.frmNuevo.AJAX_AREA_BASE.value;
	 		document.frmAjax.target="frameAjax";
			document.frmAjax.submit();
		}
	}
		
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:200px;overflow:auto;vertical-align:top;background-color:#ffffff;">
				<c:import url="/siges/gestionAcademica/planDeEstudios/FiltroArea.jsp"/>
			</div>
		</td></tr>
	</table>
	<form method="post" name="frmAjax" action='<c:url value="/planDeEstudios/Ajax.do"/>'><INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_AREA}"/>'><input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_AJAX}"/>'><c:forEach begin="0" end="5" var="i"><input type="hidden" name="ajax"></c:forEach></form>
	<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/siges/gestionAcademica/planDeEstudios/SaveArea.jsp"/>'>
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_AREA}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="AJAX_GRADO" value='<c:out value="${paramsVO.CMD_AJAX_GRADO}"/>'>
		<input type="hidden" name="AJAX_AREA_BASE" value='<c:out value="${paramsVO.CMD_AJAX_AREA_BASE}"/>'>
		<input type="hidden" name="areInstitucion" value='<c:out value="${sessionScope.areaPlanVO.areInstitucion}"/>'>
		<!-- <input type="hidden" name="areVigencia" value='<c:out value="${sessionScope.areaPlanVO.areVigencia}"/>'> -->
	  <table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
		  <caption>Ingreso / edición de área</caption>
				<tr>
					<td colspan="4">
						<c:if test="${sessionScope.NivelPermiso==2}">
    					<input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton">
							<input name="cmd1" type="button" value="Nuevo" onClick="nuevo()" class="boton">
	    			</c:if>
			  		</td>
			 	</tr>	
			<tr style="display:none"><td><iframe name="frameAjax" id="frameAjax"></iframe></td></tr>	
			<tr>
			<td width="30%"><span class="style2">*</span> Vigencia:</td>
			<td width="25%">
				<select name="areVigencia" style="width:120px;" onchange="javaScript:ajaxGrado()" <c:out value="${sessionScope.areaPlanVO.formaDisabled}"/>>
					<option value="-99" selected>--Seleccione uno--</option>
					<c:forEach begin="0" items="${requestScope.listaVigencia}" var="metod">
						<option value="<c:out value="${metod.codigo}"/>" <c:if test="${metod.codigo==sessionScope.areaPlanVO.areVigencia}">selected</c:if>><c:out value="${metod.nombre}"/></option>
					</c:forEach>
				</select>
			</td>
			<td width="30%"><span class="style2">*</span> Metodologia:</td>
			<td width="25%">
				<select name="areMetodologia" style="width:120px;" onchange="javaScript:ajaxGrado()" <c:out value="${sessionScope.areaPlanVO.formaDisabled}"/>>
					<option value="-99" selected>--Seleccione uno--</option>
					<c:forEach begin="0" items="${requestScope.listaMetodologia}" var="metod">
						<option value="<c:out value="${metod.codigo}"/>" <c:if test="${metod.codigo==sessionScope.areaPlanVO.areMetodologia}">selected</c:if>><c:out value="${metod.nombre}"/></option>
					</c:forEach>
				</select>
			</td>
			</tr>
			<tr>
			<td><span class="style2">*</span> Área:</td>
			<td>
				<select name="areCodigo" style="width:200px;" onchange="javaScript:ajaxAreaBase()" <c:out value="${sessionScope.areaPlanVO.formaDisabled}"/>>
					<option value="-99" selected>--Seleccione uno--</option>
					<c:forEach begin="0" items="${requestScope.listaAreaBase}" var="areab">
						<option value="<c:out value="${areab.codigo}"/>" <c:if test="${areab.codigo==sessionScope.areaPlanVO.areCodigo}">selected</c:if>><c:out value="${areab.nombre}"/></option>
					</c:forEach>
				</select>
			</td>
		 	</tr>	
			<tr>
				<td><span class="style2">*</span> Nombre:</td>
				<td colspan="3">
				<input type="text" name="areNombre" maxlength="60" size="30" value='<c:out value="${sessionScope.areaPlanVO.areNombre}"/>' ></input>
			</tr>	
			<tr>
				<td><span class="style2">*</span> Abreviatura:</td>
				<td colspan="3">
				<input type="text" name="areAbreviatura" maxlength="10" size="10" value='<c:out value="${sessionScope.areaPlanVO.areAbreviatura}"/>' ></input>
				</td>
			</tr>	
			<tr>
				<td><span class="style2">*</span> Orden:</td>
				<td colspan="3">
				<input type="text" name="areOrden" maxlength="3" size="3" value='<c:out value="${sessionScope.areaPlanVO.areOrden}"/>' onKeyPress='return acepteNumeros(event)'></input>
				</td>
			</tr>	
		 	<c:if test="${sessionScope.areaPlanVO.formaEstado==1}">
			 	<tr><td align="center" colspan="4"><span class="style2">*</span>Grados</td></tr>
			 	<tr><td align="center" colspan="4">
			 		<table border="0" align="center" width="100%" cellpadding="0" cellspacing="0">
				 		<tr>
				 		<c:forEach begin="0" items="${requestScope.listaGrado}" var="grado" varStatus="stg">
					 		<td width="50%">
					 		<input type="checkbox" name="areGrado_" value='<c:out value="${grado.codigo}"/>' <c:out value="${grado.padre2}"/>> &nbsp;<c:out value="${grado.nombre}"/>
					 		<input type="hidden" name="areGrado" value='<c:out value="${grado.codigo}"/>'>
					 		</td><c:if test="${stg.count%2==0}"></tr><tr></c:if>
				 		</c:forEach>
				 		</tr>
			 		</table>
			 	</td></tr>
		 	</c:if>
		 	<c:if test="${sessionScope.areaPlanVO.formaEstado==0}">
			 	<tr><td align="center" colspan="4"><span class="style2">*</span>Grados</td></tr>
			 	<tr><td align="center" colspan="4">
			 		<table border="0" align="center" width="100%" cellpadding="0" cellspacing="0">
				 		<tr>
				 		<c:forEach begin="0" items="${requestScope.listaGrado}" var="grado" varStatus="stg">
					 		<td width="50%">
						 		<input type="checkbox" name="areGrado_" value='<c:out value="${grado.codigo}"/>' disabled> &nbsp;<c:out value="${grado.nombre}"/>
						 		<input type="hidden" name="areGrado" value='<c:out value="${grado.codigo}"/>'>
						 		</td><c:if test="${stg.count%2==0}"></tr><tr></c:if>
				 		</c:forEach>
				 		</tr>
			 		</table>
			 	</td></tr>
		 	</c:if>
		</table>	
	</form>
</body>
</html>