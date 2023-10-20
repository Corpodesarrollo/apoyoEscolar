<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="lideresVO" class="participacion.lideres.vo.LideresVO" scope="session"/>
<jsp:useBean id="filtroLideresVO" class="participacion.lideres.vo.FiltroLideresVO" scope="session"/>
<jsp:useBean id="paramsVO" class="participacion.lideres.vo.ParamsVO" scope="page"/>
<jsp:useBean id="paramsVO2" class="participacion.common.vo.ParamParticipacion" scope="page"/>
<html>
<head>
<style type="text/css">@import url('<c:url value="/etc/css/calendar-win2k-1.css"/>');</style>
<script type="text/javascript" src='<c:url value="/etc/js/calendar.js"/>'></script>
<script type="text/javascript" src='<c:url value="/etc/js/lang/calendar-es.js"/>'></script>
<script type="text/javascript" src='<c:url value="/etc/js/calendar-setup.js"/>'></script>
<script languaje="javaScript">
  var extensiones = new Array();
  extensiones[0]=".zip";
  extensiones[1]=".doc";
  extensiones[2]=".xls";
  extensiones[3]=".pdf";
  extensiones[4]=".ppt";
   
	var nav4=window.Event ? true : false;

	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}
		
	function guardar(){
		if(document.frmNuevo.lidNivel.value =='0'|| document.frmNuevo.lidInstancia.value=='0'){
            alert("Recuerde: ¡Debe realizar primero la busqueda antes de insertar un nuevo registro!")
		}else{
			if(validarForma(document.frmNuevo)){
				validarDatos(document.frmNuevo);
				document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
				document.frmNuevo.submit();
			}
		}	
	}

	function cambioNivelNuevo(forma){
			var nivel=forma.lidNivel.value;
			if(parseInt(nivel)==parseInt(forma.NIVEL_DISTRITAL.value) || parseInt(nivel)==parseInt(forma.NIVEL_CENTRAL.value)){
				//forma.lidLocalidad.selectedIndex=0;
				//forma.lidColegio.selectedIndex=0;
				forma.lidLocalidad.disabled=true;
				forma.lidColegio.disabled=true;
			}
			if(parseInt(nivel)==parseInt(forma.NIVEL_LOCAL.value)){
				if(parseInt(forma.lidTieneLocalidad.value)==0 && parseInt(forma.formaEstado.value)==0) forma.lidLocalidad.disabled=false;
				if(parseInt(forma.lidTieneColegio.value)==0){
					//forma.lidColegio.selectedIndex=0;
					//forma.lidColegio.disabled=true;
				}	
			}
			if(parseInt(nivel)==parseInt(forma.NIVEL_COLEGIO.value)){
				if(parseInt(forma.lidTieneLocalidad.value)==0 && parseInt(forma.formaEstado.value)==0) forma.lidLocalidad.disabled=false;
				if(parseInt(forma.lidTieneColegio.value)==0 && parseInt(forma.formaEstado.value)==0) forma.lidColegio.disabled=false;
			}
	}

	function nuevo(){
        if(document.frmNuevo.lidRol.value =='0' || document.frmNuevo.lidRol.value =='-99'){
        	alert("Recuerde: ¡Debe realizar la busqueda por ROL antes de insertar un nuevo registro!")
        }else{
            //alert("valor de rol: "+document.frmNuevo.lidRol.value);
    		document.frmNuevo.cmd.value=document.frmNuevo.CANCELAR.value;
    		document.frmNuevo.submit();
        }
	}
	
	
	function validarDatos(forma){
		//forma.lidLocalidad.disabled=false;
		//forma.lidColegio.disabled=false;
	}
	
	
	function hacerValidaciones_frmNuevo(forma){
		//validarLista(forma.lidNivel, "- Nivel", 1)
		//validarLista(forma.lidInstancia, "- Instancia", 1)
		
		validarLista(forma.lidRango, "- Rango", 1)
		validarLista(forma.lidTipoDocumento, "- Tipo de Documento", 1)
		validarCampo(forma.lidNumeroDocumento, "- Número de Documento", 1, 20)
		validarCampo(forma.lidApellido1, "- Apellido 1", 1, 60)
		validarCampo(forma.lidNombre1, "- Nombre 1", 1, 60)
		validarCampo(forma.lidTelefono, "- Teléfono ", 1, 20)
		validarLista(forma.lidEtnia, "- Etnia", 1)
		validarLista(forma.lidRol, "- Rol", 1)
		validarLista(forma.lidSuplente, "- Suplente", 1)
		validarLista(forma.lidOcupacion, "- Ocupación", 1)
		
		var nivel=forma.lidNivel.value;
		if(parseInt(nivel)==parseInt(forma.NIVEL_LOCAL.value)){
			validarLista(forma.lidLocalidad, "- Localidad",1);
		}
		if(parseInt(nivel)==parseInt(forma.NIVEL_COLEGIO.value)){
			validarLista(forma.lidLocalidad, "- Localidad",1);
			validarLista(forma.lidColegio, "- Colegio",1);
		}
	}
	
	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) {
			if(navigator.appName == "Netscape") combo.options[i] = null;
			else combo.remove(i);
		}
		combo.options[0] = new Option("-Seleccione una-","-99");
	}
		
	function ajaxInstancia(){
		cambioNivelNuevo(document.frmNuevo);
		borrar_combo(document.frmNuevo.lidInstancia); 
		document.frmAjax.ajax[0].value=document.frmNuevo.lidNivel.value;
		if(parseInt(document.frmAjax.ajax[0].value)>0){
			document.frmAjax.cmd.value=document.frmNuevo.AJAX_INSTANCIA.value;
	 		document.frmAjax.target="frameAjax";
			document.frmAjax.submit();
		}	
	}
	
	function ajaxRango(){
        if(document.frmNuevo.lidNivel.value =='0'|| document.frmNuevo.lidInstancia.value=='0')
            alert("Recuerde: ¡Debe realizar primero la busqueda antes de insertar un nuevo registro!")
		
		//borrar_combo(document.frmNuevo.lidRango); 
        //borrar_combo(document.frmNuevo.lidRol);
		//document.frmAjax.ajax[0].value=document.frmNuevo.lidInstancia.value;
		//if(parseInt(document.frmAjax.ajax[0].value)>0){
			//document.frmAjax.cmd.value=document.frmNuevo.AJAX_RANGO.value;
	 		//document.frmAjax.target="frameAjax";
			//document.frmAjax.submit();
		//}	
	}


	
	function ajaxColegio(){
		//borrar_combo(document.frmNuevo.lidColegio); 
		document.frmAjax.ajax[0].value=document.frmNuevo.lidLocalidad.value;
		if(parseInt(document.frmAjax.ajax[0].value)>0){
			document.frmAjax.cmd.value=document.frmNuevo.AJAX_COLEGIO.value;
	 		document.frmAjax.target="frameAjax";
			document.frmAjax.submit();
		}	
	}

	function ajaxSede(){
		//borrar_combo(document.frmNuevo.lidSede);
		document.frmAjax.ajax[0].value=document.frmNuevo.lidColegio.value;
		if(parseInt(document.frmAjax.ajax[0].value)>0){
			document.frmAjax.cmd.value=document.frmNuevo.AJAX_SEDE_PRIVADO.value;
	 		document.frmAjax.target="frameAjax";
			document.frmAjax.submit();
		}	
	}
	
	function buscarPadreDeFamilia(){
		document.frmNuevo.cmd.value='42';
		if(document.frmNuevo.lidTipoDocumento.value > 0 && document.frmNuevo.lidNumeroDocumento.value > 0){
			document.frmNuevo.submit();
		}else{
			alert("Para buscar por numero de documento debe seleccionar Tipo de Documento e Ingresar Numero de Documento");
		}
	}

	
	
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:220px;overflow:auto;vertical-align:top;background-color:#ffffff;">
				<c:import url="/participacion/lideres/FiltroLideres.jsp"/>
			</div>
		</td></tr>
		<tr style="display:none"><td><iframe name="frameAjax" id="frameAjax"></iframe></td></tr>
	</table>
	<form method="post" name="frmAjax" action='<c:url value="/participacion/lideres/Ajax.do"/>'><INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_LIDERES}"/>'><input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_AJAX}"/>'><input type="hidden" name="ajax"><input type="hidden" name="ajax"><input type="hidden" name="ajax"></form>
	<form method="post" name="frmNuevo" action='<c:url value="/participacion/lideres/SaveLideres.jsp"/>'>
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_LIDERES}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="lidTieneLocalidad" value='<c:out value="${sessionScope.lideresVO.lidTieneLocalidad}"/>'>
		<input type="hidden" name="lidTieneColegio" value='<c:out value="${sessionScope.lideresVO.lidTieneColegio}"/>'>
		<input type="hidden" name="formaEstado" value='<c:out value="${sessionScope.lideresVO.formaEstado}"/>'>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="CANCELAR" value='<c:out value="${paramsVO.CMD_CANCELAR}"/>'>
		<input type="hidden" name="AJAX_INSTANCIA" value='<c:out value="${paramsVO.CMD_AJAX_INSTANCIA}"/>'>
		<input type="hidden" name="AJAX_RANGO" value='<c:out value="${paramsVO.CMD_AJAX_RANGO}"/>'>
		<input type="hidden" name="AJAX_COLEGIO" value='<c:out value="${paramsVO.CMD_AJAX_COLEGIO}"/>'>
		<input type="hidden" name="AJAX_SEDE_PRIVADO" value='<c:out value="${paramsVO.CMD_AJAX_SEDE_PRIVADO}"/>'>
		<input type="hidden" name="NIVEL_DISTRITAL" value='<c:out value="${paramsVO2.NIVEL_INSTANCIA_DISTRITAL}"/>'>
		<input type="hidden" name="NIVEL_CENTRAL" value='<c:out value="${paramsVO2.NIVEL_INSTANCIA_CENTRAL}"/>'>
		<input type="hidden" name="NIVEL_LOCAL" value='<c:out value="${paramsVO2.NIVEL_INSTANCIA_LOCAL}"/>'>
		<input type="hidden" name="NIVEL_COLEGIO" value='<c:out value="${paramsVO2.NIVEL_INSTANCIA_COLEGIO}"/>'>
		<input type="hidden" name="lidNivel" value='<c:out value="${sessionScope.filtroLideresVO.filNivel}"/>'>
		<input type="hidden" name="lidInstancia" value='<c:out value="${sessionScope.filtroLideresVO.filInstancia}"/>'>
		<input type="hidden" name="lidRango" value='<c:out value="${sessionScope.filtroLideresVO.filRango}"/>'>
		<input type="hidden" name="lidRol" value='<c:out value="${sessionScope.filtroLideresVO.filRol}"/>'>
		<input type="hidden" name="lidLocalidad" value='<c:out value="${sessionScope.filtroLideresVO.filLocalidad}"/>'>
		<input type="hidden" name="lidColegio" value='<c:out value="${sessionScope.filtroLideresVO.filColegio}"/>'>
		<c:if test="${requestScope.padreEncontrado eq true}">
			<input type="hidden" name="lidTipoDocumento" value='<c:out value="${sessionScope.lideresVO.lidTipoDocumento}"/>'/>
			<input type="hidden" name="lidNumeroDocumento" value='<c:out value="${sessionScope.lideresVO.lidNumeroDocumento}"/>'/>
			<input type="hidden" name="lidApellido1" value='<c:out value="${sessionScope.lideresVO.lidApellido1}"/>'/>
			<input type="hidden" name="lidApellido2" value='<c:out value="${sessionScope.lideresVO.lidApellido2}"/>'/>
			<input type="hidden" name="lidNombre1" value='<c:out value="${sessionScope.lideresVO.lidNombre1}"/>'/>
			<input type="hidden" name="lidNombre2" value='<c:out value="${sessionScope.lideresVO.lidNombre2}"/>'/>
		</c:if>
		
		
	  <table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
		  <caption>Ingreso / Edición de participantes.</caption>
				<tr>
					<td width="45%"><c:if test="${sessionScope.NivelPermiso==2}"><c:if test="${requestScope.nuevoRegistro=='true'}"><input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton"></c:if>&nbsp;<input name="cmd1" type="button" value="Nuevo" onClick="nuevo()" class="boton"></c:if></td>
			 	</tr>	
		</table>	 	
		
		<c:if test="${requestScope.nuevoRegistro=='true'}">		
				<table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
					<tr style="display:none">
						<td><span class="style2">*</span> Nivel:</td>
						<td>
						<!--
							<select name="lidNivel" style="width:80px;" onchange="javaScript:ajaxInstancia()" <c:out value="${sessionScope.lideresVO.formaDisabled}"/>>
								<option value="-99" selected>-Seleccione uno-</option>
								<c:forEach begin="0" items="${requestScope.listaNivelVO}" var="niv">
									<option value="<c:out value="${niv.codigo}"/>" <c:if test="${niv.codigo==sessionScope.lideresVO.lidNivel}">selected</c:if>><c:out value="${niv.nombre}"/></option>
								</c:forEach>
							</select>
						-->	
						</td>
						<td><span class="style2">*</span> Instancia:</td>
						<td>
						<!--
							<select name="lidInstancia" style="width:200px;" onchange="javaScript:ajaxRango()" <c:out value="${sessionScope.lideresVO.formaDisabled}"/>>
								<option value="-99" selected>-Seleccione uno-</option>
								<c:forEach begin="0" items="${requestScope.listaInstanciaVO}" var="inst">
									<option value="<c:out value="${inst.codigo}"/>" <c:if test="${inst.codigo==sessionScope.lideresVO.lidInstancia}">selected</c:if>><c:out value="${inst.nombre}"/></option>
								</c:forEach>
							</select>
						-->	
						</td>
				 	</tr>	
				 	<tr style="display:none">
				 	<!-- 
						<td><span class="style2">*</span> Rango:</td>
						<td>
							<select name="lidRango" style="width:200px;" <c:out value="${sessionScope.lideresVO.formaDisabled}"/>>
								<option value="-99" selected>-Seleccione uno-</option>
								<c:forEach begin="0" items="${requestScope.listaRangoVO}" var="inst">
									<option value="<c:out value="${inst.codigo}"/>" <c:if test="${inst.codigo==sessionScope.lideresVO.lidRango}">selected</c:if>><c:out value="${inst.nombre}"/></option>
								</c:forEach>
							</select>
						</td>
		
		
						<td><span class="style2">*</span> Rol:</td>
						<td>
							<select name="lidRol" style="width:200px;">
								<option value="-99" selected>-Seleccione uno-</option>
								<c:forEach begin="0" items="${requestScope.listaRolVO}" var="rol">
									<option value="<c:out value="${rol.codigo}"/>" <c:if test="${rol.codigo==sessionScope.lideresVO.lidRol}">selected</c:if>><c:out value="${rol.nombre}"/></option>
								</c:forEach>
							</select>
						</td>
					-->
				 	</tr>	
					<tr>
						<td><span class="style2">*</span> Tipo de Documento:</td>
						<td>
							<select name="lidTipoDocumento"  <c:if test="${requestScope.padreEncontrado eq true}">disabled</c:if>>
								<option value="-99" selected>-Seleccione uno-</option>
								<c:forEach begin="0" items="${requestScope.listaTipoDocumentoVO}" var="rol">
									<option value="<c:out value="${rol.codigo}"/>" <c:if test="${rol.codigo==sessionScope.lideresVO.lidTipoDocumento}">selected</c:if>><c:out value="${rol.nombre}"/></option>
								</c:forEach>
							</select>
						</td>
					
						<td><span class="style2">*</span> Número de Documento:</td>
						<td>
							<input type="text" name="lidNumeroDocumento" size="25" maxlength="20" value='<c:out value="${sessionScope.lideresVO.lidNumeroDocumento}"/>' <c:if test="${requestScope.padreEncontrado eq true}">readonly</c:if>>
							<a href='javaScript:buscarPadreDeFamilia()' <c:if test="${sessionScope.filtroLideresVO.filRol != 9}">style="display: none"</c:if>>
								<img border='0' src="../../etc/img/z.gif" width='15' height='15'>
							</a>
						</td>
					</tr>
					
		
					<tr>
						<td><span class="style2">*</span> Primer Apellido:</td>
						<td>
							<input type="text" name="lidApellido1" maxlength="60" value='<c:out value="${sessionScope.lideresVO.lidApellido1}"/>' <c:if test="${requestScope.padreEncontrado eq true}">readonly</c:if>>
						</td>
					
						<td> Segundo Apellido:</td>
						<td>
							<input type="text" name="lidApellido2" maxlength="60" value='<c:out value="${sessionScope.lideresVO.lidApellido2}"/>' <c:if test="${requestScope.padreEncontrado eq true}">readonly</c:if>>
						</td>
					</tr>
		
		
					<tr>
						<td><span class="style2">*</span> Primer Nombre:</td>
						<td>
							<input type="text" name="lidNombre1" maxlength="60" value='<c:out value="${sessionScope.lideresVO.lidNombre1}"/>' <c:if test="${requestScope.padreEncontrado eq true}">readonly</c:if>>
						</td>
					
						<td> Segundo Nombre:</td>
						<td>
							<input type="text" name="lidNombre2" maxlength="60" value='<c:out value="${sessionScope.lideresVO.lidNombre2}"/>' <c:if test="${requestScope.padreEncontrado eq true}">readonly</c:if>>
						</td>
					</tr>
		
					<tr>
						<td> Correo Electrónico:</td>
						<td>
							<input type="text" name="lidCorreoElectronico" maxlength="200" value='<c:out value="${sessionScope.lideresVO.lidCorreoElectronico}"/>'>
						</td>
					
						<td><span class="style2">*</span>Teléfono:</td>
						<td>
							<input type="text" name="lidTelefono" size="25" maxlength="20" value='<c:out value="${sessionScope.lideresVO.lidTelefono}"/>'>
						</td>
					</tr>
		
					<tr>
						<td> Número Celular:</td>
						<td>
							<input type="text" name="lidCelular" size="25" maxlength="20" value='<c:out value="${sessionScope.lideresVO.lidCelular}"/>'>
						</td>
					
						<td> Edad:</td>
						<td>
							<input type="text" name="lidEdad" size="5" maxlength="2"  onKeyPress='return acepteNumeros(event)'  value='<c:out value="${sessionScope.lideresVO.lidEdad}"/>'> &nbsp;&nbsp;Años 
						</td>
					</tr>
		
		
					<tr>
						<td><span class="style2">*</span> Etnia:</td>
						<td>
							<select name="lidEtnia" >
								<option value="-99" selected>-Seleccione uno-</option>
								<c:forEach begin="0" items="${requestScope.listaEtniaVO}" var="rol">
									<option value="<c:out value="${rol.codigo}"/>" <c:if test="${rol.codigo==sessionScope.lideresVO.lidEtnia}">selected</c:if>><c:out value="${rol.nombre}"/></option>
								</c:forEach>
							</select>
						</td>
		
						<td><span class="style2">*</span> Tipo Rol:</td>
						<td>
							<select name="lidSuplente">
								<option value="-99" selected>--</option>
								<option value='1' <c:if test="${sessionScope.lideresVO.lidSuplente== '1'}">SELECTED</c:if>>SUPLENTE</option>
								<option value='2' <c:if test="${sessionScope.lideresVO.lidSuplente== '2'}">SELECTED</c:if>>TITULAR</option>
							</select>
						</td>
					</tr>
		
					<c:if test="${requestScope.porEstudiante=='true'}">		
						 	
						 	
						 	<tr>
								<td>Sede:</td>
								<td colspan="3">
									<select name="lidSede" style="width:300px;">
										<option value="-99" selected>-Seleccione uno-</option>
										<c:forEach begin="0" items="${requestScope.listaSedeVO}" var="niv">
											<option value="<c:out value="${niv.codigo}"/>" <c:if test="${niv.codigo==sessionScope.lideresVO.lidSede}">selected</c:if>><c:out value="${niv.nombre}"/></option>
										</c:forEach>
									</select>
								</td>
						 	</tr>
						 	
						 	<tr>
								<td>Jornada:</td>
								<td>
									<select name="lidJornada">
										<option value="-99" selected>-Seleccione uno-</option>
										<c:forEach begin="0" items="${requestScope.listaJornadaVO}" var="jor">
											<option value="<c:out value="${jor.codigo}"/>" <c:if test="${jor.codigo==sessionScope.lideresVO.lidJornada}">selected</c:if>><c:out value="${jor.nombre}"/></option>
										</c:forEach>
									</select>
								</td>
						 	</tr>
				
						 	<tr>
								<td>Grado:</td>
								<td colspan="3">
									<select name="lidGrado" style="width:300px;">
										<option value="-99" selected>-Seleccione uno-</option>
										<c:forEach begin="0" items="${requestScope.listaGradoVO}" var="niv">
											<option value="<c:out value="${niv.codigo}"/>" <c:if test="${niv.codigo==sessionScope.lideresVO.lidGrado}">selected</c:if>><c:out value="${niv.nombre}"/></option>
										</c:forEach>
									</select>
								</td>
							</tr>
							
							<tr>	
								<td>Grupo:</td>
								<td colspan="3">
									<select name="lidGrupo" style="width:300px;">
										<option value="-99" selected>-Seleccione uno-</option>
										<c:forEach begin="0" items="${requestScope.listaGrupoVO}" var="gru">
											<option value="<c:out value="${gru.codigo}"/>" <c:if test="${gru.codigo==sessionScope.lideresVO.lidGrupo}">selected</c:if>><c:out value="${gru.nombre}"/></option>
										</c:forEach>
									</select>
								</td>
						 	</tr>
						 	
				 	</c:if>
				 		
					<c:if test="${requestScope.porPersonal=='true'}">
					
						 	
						 	
						 	<tr>
								<td>Sede:</td>
								<td colspan="3">
									<select name="lidSede" style="width:300px;">
										<option value="-99" selected>-Seleccione uno-</option>
										<c:forEach begin="0" items="${requestScope.listaSedeVO}" var="niv">
											<option value="<c:out value="${niv.codigo}"/>" <c:if test="${niv.codigo==sessionScope.lideresVO.lidSede}">selected</c:if>><c:out value="${niv.nombre}"/></option>
										</c:forEach>
									</select>
								</td>
						 	</tr>
						 	<tr>
								<td>Jornada:</td>
								<td>
									<select name="lidJornada">
										<option value="-99" selected>-Seleccione uno-</option>
										<c:forEach begin="0" items="${requestScope.listaJornadaVO}" var="jor">
											<option value="<c:out value="${jor.codigo}"/>" <c:if test="${jor.codigo==sessionScope.lideresVO.lidJornada}">selected</c:if>><c:out value="${jor.nombre}"/></option>
										</c:forEach>
									</select>
								</td>
						 	</tr>
					</c:if>				 		
				 		
				 	<tr>
						<td> Entidad donde labora:</td>
						<td>
							<input type="text" name="lidEntidad"  maxlength="200" value='<c:out value="${sessionScope.lideresVO.lidEntidad}"/>'>
						</td>
		
						<td> Localidad de Residencia:</td>
						<td>
							<select name="lidLocalidadResidencia">
								<option value="-99" selected>-Seleccione uno-</option>
								<c:forEach begin="0" items="${requestScope.listaLocalidadResidenciaVO}" var="inst">
									<option value="<c:out value="${inst.codigo}"/>" <c:if test="${inst.codigo==sessionScope.lideresVO.lidLocalidadResidencia}">selected</c:if>><c:out value="${inst.nombre}"/></option>
								</c:forEach>
							</select>
						</td>
				 	</tr>
		
		
				 	<tr>
						<td>Sector Económico:</td>
						<td>
							<select name="lidSectorEconomico">
								<option value="-99" selected>-Seleccione uno-</option>
								<c:forEach begin="0" items="${requestScope.listaSectorEconomicoVO}" var="niv">
									<option value="<c:out value="${niv.codigo}"/>" <c:if test="${niv.codigo==sessionScope.lideresVO.lidSectorEconomico}">selected</c:if>><c:out value="${niv.nombre}"/></option>
								</c:forEach>
							</select>
						</td>
				 	
						<td>Discapacidad:</td>
						<td>
							<select name="lidDiscapacidad">
								<option value="-99" selected>-Seleccione uno-</option>
								<c:forEach begin="0" items="${requestScope.listaDiscapacidadVO}" var="niv">
									<option value="<c:out value="${niv.codigo}"/>" <c:if test="${niv.codigo==sessionScope.lideresVO.lidDiscapacidad}">selected</c:if>><c:out value="${niv.nombre}"/></option>
								</c:forEach>
							</select>
						</td>
				 	</tr>
				 	
					<tr>		 	
						<td> Desplazado ?:</td>
						<td>
							<select name="lidDesplazado" >
								<option value="-99" selected>--</option>
								<option value='1' <c:if test="${sessionScope.lideresVO.lidDesplazado== '1'}">SELECTED</c:if>>Si</option>
								<option value='2' <c:if test="${sessionScope.lideresVO.lidDesplazado== '0'}">SELECTED</c:if>>No</option>
							</select>
						</td>
						
						<td> Amenazado ?:</td>
						<td>
							<select name="lidAmenazado" >
								<option value="-99" selected>--</option>
								<option value='1' <c:if test="${sessionScope.lideresVO.lidAmenazado== '1'}">SELECTED</c:if>>Si</option>
								<option value='2' <c:if test="${sessionScope.lideresVO.lidAmenazado== '0'}">SELECTED</c:if>>No</option>
							</select>
						</td>
		             </tr>
		             
				 	<tr>
						<td>LGTB:</td>
						<td>
							<select name="lidLgtb">
								<option value="-99" selected>-Seleccione uno-</option>
								<c:forEach begin="0" items="${requestScope.listaLgtbVO}" var="niv">
									<option value="<c:out value="${niv.codigo}"/>" <c:if test="${niv.codigo==sessionScope.lideresVO.lidLgtb}">selected</c:if>><c:out value="${niv.nombre}"/></option>
								</c:forEach>
							</select>
						</td>
				 	
						<td><span class="style2">*</span>Ocupación:</td>
						<td>
							<select name="lidOcupacion">
								<option value="-99" selected>-Seleccione uno-</option>
								<c:forEach begin="0" items="${requestScope.listaOcupacionVO}" var="niv">
									<option value="<c:out value="${niv.codigo}"/>" <c:if test="${niv.codigo==sessionScope.lideresVO.lidOcupacion}">selected</c:if>><c:out value="${niv.nombre}"/></option>
								</c:forEach>
							</select>
						</td>
				 	</tr>
		
				 	<tr>
						<td> Género:</td>
						<td>
							<select name="lidGenero" >
								<option value='1' <c:if test="${sessionScope.lideresVO.lidGenero== '1'}">SELECTED</c:if>>M</option>
								<option value='2' <c:if test="${sessionScope.lideresVO.lidGenero== '2'}">SELECTED</c:if>>F</option>
							</select>
						</td>
				 	</tr>
				 	
				 	
				</table>
		</c:if>					
	</form>	
</body>
	<script type="text/javascript">
	<c:if test="${requestScope.nuevoRegistro=='true'}">	
	    cambioNivelNuevo(document.frmNuevo);
	    if(parseInt(document.frmNuevo.formaEstado.value)==0)
	    	ajaxRango();

	    if(parseInt(document.frmNuevo.formaEstado.value)==1){
	    	//alert("vale uno");
	    }
    </c:if>	
	</script>
</html>