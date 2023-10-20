<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="asignaturaVO" class="articulacion.asignatura.vo.AsignaturaVO" scope="session"/>
<jsp:useBean id="paramsVO" class="articulacion.asignatura.vo.ParamsVO" scope="page"/>
<jsp:useBean id="now" class="java.util.Date"/>
<fmt:formatDate value="${now}" type="time" timeStyle="short" pattern="yyyy" var="ano"/>
<html>
<head>
<script languaje="javaScript">
	var nav4=window.Event ? true : false;
	
	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}
	
	function cambioChecks(forma){
		if(forma.artAsigHomologar_.checked==true){
			forma.artAsigHomologar.value='true';
		}else{
			forma.artAsigHomologar.value='false';
		}
	}
	
	function poner(obj){
		if(document.frmNuevo.artAsigComponente.value==1){
			var a=obj.options[obj.selectedIndex].text;
			alert(obj.value);
			document.frmNuevo.artAsigNombre.value=a;	
		}
		if(document.frmNuevo.artAsigComponente.value==2){
			var x;
			if(document.frmNuevo.artAsigCodAsigIns.lenght>1)
			x=document.frmNuevo.artAsigCodAsigIns.value.charAt(document.frmNuevo.artAsigCodAsigIns.lenght-1);
			x+=document.frmNuevo.artAsigCodAsigIns.value.value.charAt(document.frmNuevo.artAsigCodAsigIns.lenght-2);
			document.frmNuevo.codigo.value=x;
		}

		unir();
	}
	
	function dividir(dato){
		var x;
		if(document.frmNuevo.artAsigComponente.value==2){
			if(dato.value.length>2){
				x=dato.value.charAt(dato.value.length-2)+dato.value.charAt(dato.value.length-1);
				document.frmNuevo.comp.value=x;
			}
		}
	}
	
	function entrada(){
		/*if(document.frmNuevo.artAsigComponente.value==1){
			selCombo(document.frmNuevo.artAsigCodAsig,1);
		}*/
		if(document.frmNuevo.artAsigComponente.value==2){
			cambio(document.frmNuevo.comp);
		}
		if(document.frmNuevo.artAsigCom.value==2){
			cambio(document.frmNuevo.comp);
		}
	}
	
	function selCombo(combo,e){
		if(combo){
			var a=combo.options[combo.selectedIndex].text;
			if(combo.selectedIndex!=0 && e==0){
				document.frmNuevo.artAsigNombre.value=a;
			}
			var cod=document.frmNuevo.artAsigNumPeriodo.value;
			cod=cod+combo.value;
			document.frmNuevo.codigo.value=cod;
			document.frmNuevo.artAsigCodAsigIns.value=cod;
		}
	}
	
	function cambio(caja){
		var cod=document.frmNuevo.artAsigNumPeriodo.value;
		if(document.frmNuevo.artAsigComponente.value==2){
			cod=cod+document.frmNuevo.artAsigCodEsp.value;
		}
		var mitad=cod;
		if(caja){
			cod=cod+caja.value;
		}
		document.frmNuevo.artAsigCodAsigIns.value=cod;
		document.frmNuevo.codigo.value=mitad;

	}
	
	function taller(){
		if(document.frmNuevo.artAsigClasConsec.value.charAt(0)=='0')
			document.frmNuevo.artAsigClasConsec.value=document.frmNuevo.artAsigClasConsec.value.charAt(1);
		if(document.frmNuevo.artAsigIntHoraria.value.charAt(0)=='0')
			document.frmNuevo.artAsigIntHoraria.value=document.frmNuevo.artAsigIntHoraria.value.charAt(1);
		var a= parseInt(document.frmNuevo.artAsigIntHoraria.value);
		var b= parseInt(document.frmNuevo.artAsigClasConsec.value);
		if(a<b){
			alert("Las Horas Consecitivas Taller no deben superar la Intensidad Horaria");
			document.frmNuevo.artAsigClasConsec.value='';
		}
	}
	
	function nuevo(){
		document.frmNuevo.cmd.value=document.frmNuevo.NUEVO.value;
		document.frmNuevo.action='<c:url value="/articulacion/asignatura/Nuevo.do"/>';
		document.frmNuevo.submit();
	}

	function guardar(){
		if(validarForma(document.frmNuevo)){
			cambioChecks(document.frmNuevo);
			document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
			document.frmNuevo
			document.frmNuevo.submit();
		}
	}

	function hacerValidaciones_frmNuevo(forma){
		/*if(forma.artAsigCodAsig){
			validarLista(forma.artAsigCodAsig, "- Seleccione una Asignatura Base", 1);
		}
		if(forma.comp){
			validarEntero(forma.comp, "- Código de asignatura", 1, 99);
		}*/
		validarEntero(forma.artAsigCodAsigIns, "- Código de asignatura", 1, 99999);
		validarCampo(forma.artAsigNombre, "- Nombre de la asignatura", 1, 60);
		validarCampo(forma.artAsigAbreviatura, "- Abreviatura de la asignatura", 1, 10);
		validarEntero(forma.artAsigIntHoraria, "- Intensidad Horaria", 1, 99);
		validarEnteroOpcional(forma.artAsigClasConsec, "- Horas Consecutivas Taller", 0, 99);
		validarFloatOpcional(forma.artAsigNotMinHom,'- Nota Minima',0,5.0);
	//	validarEntero(forma.artAsigAnoVigencia, "- Vigencia", <c:out value="${ano}"/>, 9999);
	//	validarLista(forma.artAsigPerVigencia, "- Vigencia", 1)
	}
	
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:180px;overflow:auto;vertical-align:top;background-color:#ffffff;">
				<c:import url="/articulacion/asignatura/Filtro.do"/>
			</div>
		</td></tr>
	</table>
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:150px;overflow:auto;vertical-align:top;background-color:#ffffff;">
				<c:import url="/articulacion/asignatura/Lista.do"/>
			</div>
		</td></tr>
	</table>
 <form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/articulacion/asignatura/Save.jsp"/>'>
		<input type="hidden" name="cmd" value=''>
		<input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_DEFAULT}"/>'>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="artAsigComponente" value='2'>
		<c:if test="${sessionScope.datosVO.componente==2}"><input type="hidden" name="artAsigCodEsp" value='<c:out value="${sessionScope.datosVO.especialidad}"/>'></c:if>
		<c:if test="${sessionScope.datosVO.componente==1}"><input type="hidden" name="artAsigCodEsp" value='0'></c:if>
		<input type="hidden" name="artAsigCodArea" value='<c:out value="${sessionScope.datosVO.area}"/>'>
		<input type="hidden" name="artAsigNumPeriodo" value='<c:out value="${sessionScope.datosVO.periodo}"/>'>
		<input type="hidden" name="artAsigCom" value='<c:out value="${sessionScope.datosVO.complementaria}"/>'>
		<input type="hidden" name="artAsigAnoVigencia" value='<c:out value="${sessionScope.datosVO.anoVigencia}"/>'>
		<input type="hidden" name="artAsigPerVigencia" value='<c:out value="${sessionScope.datosVO.perVigencia}"/>'>
		<input type="hidden" name="artAsigCodDinst" value='<c:out value="${sessionScope.login.instId}"/>'>
		<input type="hidden" name="artAsigCodMetod" value='<c:out value="${sessionScope.login.metodologiaId}"/>'>
		<input type="hidden" name="artAsigCodigo" value='<c:out value="${sessionScope.asignaturaVO.artAsigCodigo}"/>'>
		<table border="0" align="center" bordercolor="#FFFFFF" width="100%">
			<caption>Información de asignatura</caption>
				<tr>
					<td width="45%">
						<c:if test="${sessionScope.NivelPermiso==2}">
							<input name="cmd1" type="button" value="Nuevo" onClick="nuevo()" class="boton">
		        			<c:if test="${sessionScope.asignaturaVO.formaEstado==1}">
		        				<input name="cmd1" type="button" value="Actualizar" onClick="guardar()" class="boton">
		        			</c:if>
	    					<c:if test="${sessionScope.asignaturaVO.formaEstado!=1}">
	    						<input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton">
	    					</c:if>
	    				</c:if>
			  		</td>
			 	</tr>	
		  </table>
		  <table width="100%" border="0" cellspacing="2" cellpadding="0">
				<tr>
					<td width="25%">
						<span class="style2">*</span><b>Código:</b>
					</td>
					<td width="25%">
						<!-- <input type="text" name="codigo" maxlength="5" size="5" onKeyPress='return acepteNumeros(event)' value=''></input> -->
						<input type="text" name="artAsigCodAsigIns" maxlength="5" size="5" onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.asignaturaVO.artAsigCodAsigIns}"/>'></input>
						<!-- <input type="hidden" name="artAsigCodAsigIns" value='<c:out value="${sessionScope.asignaturaVO.artAsigCodAsigIns}"/>'></input>
						<c:if test='${sessionScope.datosVO.componente=="2" || sessionScope.datosVO.complementaria=="2"}'>
							<input type="text" name="comp" maxlength="2" size="2" onKeyPress='return acepteNumeros(event)' onchange="cambio(this)" value="2"></input>
						</c:if>	-->
					</td>
					<!-- 
					<c:if test='${sessionScope.datosVO.componente=="1" && sessionScope.datosVO.complementaria=="1"}'>
						<td>
							<b>Asignatura Base:</b>
						</td>
						<td colspan="2">
							<select id="nom" name="artAsigCodAsig" onChange='javaScript:selCombo(document.frmNuevo.artAsigCodAsig,0);'>
								<option value="">--Opcion Asignaturas--</option>
								<c:forEach begin="0" items="${requestScope.listaNombres}" var="nombres">
									<option value="<c:out value="${nombres.codigo}"/>" <c:if test="${nombres.codigo==sessionScope.asignaturaVO.artAsigCodAsig}">selected</c:if>><c:out value="${nombres.nombre}"/></option>
								</c:forEach>
							</select>
						</td>
					</c:if> -->
				</tr>
				<tr>
			  		<td id='esp'><span class="style2">*</span><b>Especialidad:</b></td>
			  		<td id='esp1' colspan="4">
							<select name="especialidad">
								<option value="0">--Seleccione una--</option>
								<c:forEach begin="0" items="${requestScope.listaEspecialidadVO}" var="especialidad">
									<option value="<c:out value="${especialidad.codigo}"/>"  <c:if test="${especialidad.codigo==sessionScope.datosVO.especialidad}">selected</c:if>><c:out value="${especialidad.nombre}"/></option>
								</c:forEach>
							</select>
			  		</td>
		  		</tr>
				<tr>
					<td>
						<span class="style2">*</span><b>Nombre de la asignatura:</b>
					</td>
					<td colspan="4">
						<input type="text" name="artAsigNombre" maxlength="60" size="60"  value='<c:out value="${sessionScope.asignaturaVO.artAsigNombre}"/>'></input>
					</td>
				</tr>
				
				<tr>
					<td>
						<span class="style2">*</span><b>Abreviatura:</b>
					</td>
					<td>
						<input type="text" name="artAsigAbreviatura" maxlength="10" size="10"  value='<c:out value="${sessionScope.asignaturaVO.artAsigAbreviatura}"/>'></input>
					</td>
					<td>
						<b>Orden:</b>
					</td>
					<td width="25%">
						<input type="text" name="artAsigOrden" maxlength="3" size="3" onKeyPress='return acepteNumeros(event)' onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.asignaturaVO.artAsigOrden}"/>'></input>
					</td>
				</tr>
				<tr>
					<td width="30">
						<span class="style2">*</span><b>Intensidad Horaria:</b>
					</td>
					<td>
						<input type="text" name="artAsigIntHoraria" maxlength="2" size="2" onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.asignaturaVO.artAsigIntHoraria}"/>'></input>
					</td>
					<td width="30">
						<b>Horas Consecutivas Taller:</b>
					</td>
					<td>
						<input type="text" name="artAsigClasConsec" maxlength="2" size="2" onKeyPress='return acepteNumeros(event)' onkeyup="taller()" value='<c:out value="${sessionScope.asignaturaVO.artAsigClasConsec}"/>'></input>
					</td>
				</tr>
				
				<tr>
					<td>
						<b>Número de grupos:</b>
					</td>
					<td>
						<input type="text" name="artAsigSesion" maxlength="3" size="3" onKeyPress='return acepteNumeros(event)' onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.asignaturaVO.artAsigSesion}"/>'></input>
					</td>
					<td width="30">
						<b>Habilitable:</b>
					</td>
					<td>
						<select name="artAsigHabilitable">
								<option value="0">--Seleccione uno--</option>
								<option value="1" <c:if test="${1==sessionScope.asignaturaVO.artAsigHabilitable}">selected</c:if>>Habilitable</option>
								<option value="2" <c:if test="${2==sessionScope.asignaturaVO.artAsigHabilitable}">selected</c:if>>Rehabilitable</option>
						</select>
					</td>
				</tr>
				
				<tr>
					<td>
						<b>Número de Créditos:</b>
					</td>
					<td>
						<input type="text" name="artAsigCredito" maxlength="3" size="3" onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.asignaturaVO.artAsigCredito}"/>'></input>
					</td>
					<td>
						<b>Naturaleza:</b>
					</td>
					<td colspan="2">
						<select name="artAsigNat">
								<option value="0">--Seleccione uno--</option>
								<option value="1" <c:if test="${1==sessionScope.asignaturaVO.artAsigNat}">selected</c:if>>Teórica</option>
								<option value="2" <c:if test="${2==sessionScope.asignaturaVO.artAsigNat}">selected</c:if>>Práctica</option>
								<option value="3" <c:if test="${3==sessionScope.asignaturaVO.artAsigNat}">selected</c:if>>Teórico-Práctica</option>
						</select>
			  		</td>
					
					
				</tr>
				<tr>
					<td>
						<b>Homologable:</b>
					</td>
					<td>
						<input type="checkbox" name="artAsigHomologar_" value='<c:out value="${sessionScope.asignaturaVO.artAsigHomologar}"/>' <c:if test="${sessionScope.asignaturaVO.artAsigHomologar}">checked</c:if>>
						<input type="hidden" name="artAsigHomologar" value='<c:out value="${sessionScope.asignaturaVO.artAsigHomologar}"/>'></input>
					</td>
					<td>
						<b>Nota Mínima Homologable:</b>
					</td>
					<td>
						<input type="text" name="artAsigNotMinHom" maxlength="3" size="3" value='<c:out value="${sessionScope.asignaturaVO.artAsigNotMinHom}"/>'></input>
					</td>
				</tr>
				<tr>
					<td>
						<b>Tipo de Asignatura:</b>
					</td>
					<td>
						<select name="artAsigTip">
								<option value="0">--Seleccione uno--</option>
								<option value="1" <c:if test="${1==sessionScope.asignaturaVO.artAsigTip}">selected</c:if>>Fundamental</option>
								<option value="2" <c:if test="${2==sessionScope.asignaturaVO.artAsigTip}">selected</c:if>>Electiva</option>
						</select>
					</td>
				
			  		<td>
						<b>Modalidad:</b>
					</td>
					<td colspan="2">
						<select name="artAsigMod">
								<option value="0">--Seleccione uno--</option>
								<option value="1" <c:if test="${1==sessionScope.asignaturaVO.artAsigMod}">selected</c:if>>Presencial</option>
								<option value="2" <c:if test="${2==sessionScope.asignaturaVO.artAsigMod}">selected</c:if>>Semi-Presencial</option>
						</select>
			  		</td>
				</tr>
				<tr>
					<td>
						<b>Descripción:</b>
					</td>
					<td colspan="4">
						<textarea colspan="2" name="artAsigDescripcion" cols="67" rows="3"><c:out value="${sessionScope.asignaturaVO.artAsigDescripcion}"/></textarea>
					</td>
				</tr>
			</table>
		</form>
		<!-- *************** listado de pre-requisitos y co-requisitos***** -->
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:100px;overflow:auto;vertical-align:top;background-color:#ffffff;">
				<iframe id="prerequisito" name="prerequisito" marginheight="0" marginwidth="0"  frameborder="NO"  width="100%" src='<c:url value="/articulacion/asignatura/ListaPrereqIns.do"/>' valign="top" ALIGN="center" height="200px"></iframe>
			</div>
		</td></tr>
		</table>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:100px;overflow:auto;vertical-align:top;background-color:#ffffff;">
				<iframe id="correquisito" name="correquisito" marginheight="0" marginwidth="0"  frameborder="NO"  width="100%" src='<c:url value="/articulacion/asignatura/ListaCoreqIns.do"/>' valign="top" ALIGN="center" height="200px"></iframe>
			</div>
		</td></tr>
		</table>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:100px;overflow:auto;vertical-align:top;background-color:#ffffff;">
				<iframe id="complementaria" name="complementaria" marginheight="0" marginwidth="0"  frameborder="NO"  width="100%" src='<c:url value="/articulacion/asignatura/ListaCompIns.do"/>' valign="top" ALIGN="center" height="200px"></iframe>
			</div>
		</td></tr>
	</table>
</body>
<script>
<c:if test="${sessionScope.asignaturaVO.formaEstado==1}">
	dividir(document.frmNuevo.artAsigCodAsigIns);
</c:if>
	entrada();
</script>
</html>