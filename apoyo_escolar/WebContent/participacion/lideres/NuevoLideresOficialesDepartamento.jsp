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

	function Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre){
		this.id_Hijos= id_Hijos; this.Hijos= Hijos; this.Sel_Hijos= Sel_Hijos; this.id_Padre=id_Padre;
	}
	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) {
			if(navigator.appName == "Netscape") combo.options[i] = null;
			else combo.remove(i);
		}
		combo.options[0] = new Option("-- Seleccione uno --","-99");
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
			//alert("Rango: "+forma.lidRango.value);
			//alert("Rol: "+forma.lidRol.value);
			if(parseInt(nivel)==parseInt(forma.NIVEL_DISTRITAL.value) || parseInt(nivel)==parseInt(forma.NIVEL_CENTRAL.value)){
				forma.lidLocalidad.selectedIndex=0;
				forma.lidColegio.selectedIndex=0;
				forma.lidLocalidad.disabled=true;
				forma.lidColegio.disabled=true;
			}
			if(parseInt(nivel)==parseInt(forma.NIVEL_LOCAL.value)){
				if(parseInt(forma.lidTieneLocalidad.value)==0 && parseInt(forma.formaEstado.value)==0) forma.lidLocalidad.disabled=false;
				if(parseInt(forma.lidTieneColegio.value)==0){
					forma.lidColegio.selectedIndex=0;
					forma.lidColegio.disabled=true;
				}	
			}
			if(parseInt(nivel)==parseInt(forma.NIVEL_COLEGIO.value)){
				if(parseInt(forma.lidTieneLocalidad.value)==0 && parseInt(forma.formaEstado.value)==0) forma.lidLocalidad.disabled=false;
				if(parseInt(forma.lidTieneColegio.value)==0 && parseInt(forma.formaEstado.value)==0) forma.lidColegio.disabled=false;
			}

			if(parseInt(forma.formaEstado.value)==1){
				forma.lidSede.disabled=true;
				forma.lidJornada.disabled=true;

				<c:if test="${requestScope.porEstudiante=='true'}">
					forma.lidGrado.disabled=true;
					forma.lidGrupo.disabled=true;
					forma.lidMet.disabled=true;
				</c:if>
				
				forma.lidNumeroDocumento.disabled=true;
			}	
	}

	function nuevo(){
        if(document.frmNuevo.lidRol.value =='0' || document.frmNuevo.lidRol.value =='-99'){
        	alert("Recuerde: ¡Debe realizar la busqueda por ROL antes de insertar un nuevo registro!")
        }else{
            //alert("NUEVOOO  ");
    		document.frmNuevo.cmd.value=document.frmNuevo.CANCELAR.value;
    		document.frmNuevo.submit();
        }
	}
	
	
	function validarDatos(forma){
		forma.lidLocalidad.disabled=false;
		forma.lidColegio.disabled=false;
	}
	
	
	function hacerValidaciones_frmNuevo(forma){
		//validarLista(forma.lidNivel, "- Nivel", 1)
		//validarLista(forma.lidInstancia, "- Instancia", 1)
		//validarLista(forma.lidRango, "- Rango", 1);

		var nivel=forma.lidNivel.value
		if(parseInt(nivel)==parseInt(forma.NIVEL_LOCAL.value)){
			validarLista(forma.lidLocalidad, "- Localidad",1);
		}
		if(parseInt(nivel)==parseInt(forma.NIVEL_COLEGIO.value)){
			validarLista(forma.lidLocalidad, "- Localidad",1);
			validarLista(forma.lidColegio, "- Colegio",1);
		}
		validarLista(forma.lidSede, "- Sede",1);
		validarLista(forma.lidJornada, "- Jornada",1);
		
		<c:if test="${requestScope.porEstudiante=='true'}">
			validarLista(forma.lidGrado, "- Grado",1);
			validarLista(forma.lidGrupo, "- Grupo",1);
			if(parseInt(document.frmNuevo.formaEstado.value)==0)
				validarLista(forma.lidNumeroDocumento, "- Estudiante",1);
		</c:if>

		<c:if test="${requestScope.porPersonal=='true'}">
		if(parseInt(document.frmNuevo.formaEstado.value)==0)
			validarLista(forma.lidNumeroDocumento, "- Personal",1);
		</c:if>
		
		validarCampo(forma.lidTelefono, "- Teléfono ", 1, 20);
		validarLista(forma.lidEtnia, "- Etnia", 1);
		//validarLista(forma.lidRol, "- Rol", 1);
		validarLista(forma.lidSuplente, "- Suplente", 1);
		validarLista(forma.lidOcupacion, "- Ocupación", 1);
	}

	function lista(forma){
		//alert("***entroooooo por lista***");
		forma.tipo.value='<c:out value="${paramsVO.FICHA_LIDERES}"/>';
		forma.cmd.value='<c:out value="${paramsVO.CMD_AJAX}"/>';
		forma.accion.value='1';
		forma.sede.value=forma.lidSede.options[forma.lidSede.selectedIndex].value;
		forma.jornada.value=forma.lidJornada.options[forma.lidJornada.selectedIndex].value;
		forma.metod.value=forma.lidMet.value;
		forma.grado.value=forma.lidGrado.options[forma.lidGrado.selectedIndex].value;
		forma.grupo.value=forma.lidGrupo.options[forma.lidGrupo.selectedIndex].value;
		
		forma.action='<c:url value="/participacion/lideres/Filtro.jsp"/>';	
		forma.submit();
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
		//alert("document.frmNuevo.lidNivel.value: "+document.frmNuevo.lidNivel.value);
		//alert("document.frmNuevo.lidInstancia.value: "+document.frmNuevo.lidInstancia.value);
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
		borrar_combo(document.frmNuevo.lidColegio); 
		document.frmAjax.ajax[0].value=document.frmNuevo.lidLocalidad.value;
		if(parseInt(document.frmAjax.ajax[0].value)>0){
			document.frmAjax.cmd.value=document.frmNuevo.AJAX_COLEGIO.value;
	 		document.frmAjax.target="frameAjax";
			document.frmAjax.submit();
		}	
	}
	
	function ajaxSede(){
		borrar_combo(document.frmNuevo.lidSede);
		borrar_combo(document.frmNuevo.lidJornada);
		borrar_combo(document.frmNuevo.lidMet);
		borrar_combo(document.frmNuevo.lidGrado);
		borrar_combo(document.frmNuevo.lidGrupo);  
		borrar_combo(document.frmNuevo.lidNumeroDocumento);
		document.frmAjax.ajax[0].value=document.frmNuevo.lidColegio.value;
		if(parseInt(document.frmAjax.ajax[0].value)>0){
			document.frmAjax.cmd.value=document.frmNuevo.AJAX_SEDE.value;
	 		document.frmAjax.target="frameAjax";
			document.frmAjax.submit();
		}	
	}

	function ajaxSedePersonal(){
		borrar_combo(document.frmNuevo.lidSede);
		borrar_combo(document.frmNuevo.lidJornada);
		borrar_combo(document.frmNuevo.lidNumeroDocumento);
		document.frmAjax.ajax[0].value=document.frmNuevo.lidColegio.value;
		if(parseInt(document.frmAjax.ajax[0].value)>0){
			document.frmAjax.cmd.value=document.frmNuevo.AJAX_SEDEP.value;
	 		document.frmAjax.target="frameAjax";
			document.frmAjax.submit();
		}	
	}

	
	function ajaxJornada(){
		borrar_combo(document.frmNuevo.lidJornada); 
		borrar_combo(document.frmNuevo.lidGrado);
		borrar_combo(document.frmNuevo.lidGrupo);
		borrar_combo(document.frmNuevo.lidNumeroDocumento);
		document.frmAjax.ajax[0].value=document.frmNuevo.lidColegio.value;
		document.frmAjax.ajax[1].value=document.frmNuevo.lidSede.value;
		if(parseInt(document.frmAjax.ajax[0].value)>0 && parseInt(document.frmAjax.ajax[1].value)>0 ){
			document.frmAjax.cmd.value=document.frmNuevo.AJAX_JORNADA.value;
	 		document.frmAjax.target="frameAjax";
			document.frmAjax.submit();
		}	
	}

	function ajaxJornadaPersonal(){
		borrar_combo(document.frmNuevo.lidJornada); 
		borrar_combo(document.frmNuevo.lidNumeroDocumento);
		document.frmAjax.ajax[0].value=document.frmNuevo.lidColegio.value;
		document.frmAjax.ajax[1].value=document.frmNuevo.lidSede.value;
		if(parseInt(document.frmAjax.ajax[0].value)>0 && parseInt(document.frmAjax.ajax[1].value)>0 ){
			document.frmAjax.cmd.value=document.frmNuevo.AJAX_JORNADAP.value;
	 		document.frmAjax.target="frameAjax";
			document.frmAjax.submit();
		}	
	}

	
	function ajaxGrado(){
		borrar_combo(document.frmNuevo.lidGrado); 
		borrar_combo(document.frmNuevo.lidGrupo);
		borrar_combo(document.frmNuevo.lidNumeroDocumento);
		document.frmAjax.ajax[0].value=document.frmNuevo.lidColegio.value;
		document.frmAjax.ajax[1].value=document.frmNuevo.lidSede.value;
		document.frmAjax.ajax[2].value=document.frmNuevo.lidJornada.value;
		document.frmAjax.ajax[3].value=document.frmNuevo.lidMet.value;
		if(parseInt(document.frmAjax.ajax[0].value)>0 && parseInt(document.frmAjax.ajax[1].value)>0 && parseInt(document.frmAjax.ajax[2].value)>0 && parseInt(document.frmAjax.ajax[3].value)>0){
			document.frmAjax.cmd.value=document.frmNuevo.AJAX_GRADO.value;
	 		document.frmAjax.target="frameAjax";
			document.frmAjax.submit();
		}	
	}

	function ajaxGrupo(){
		borrar_combo(document.frmNuevo.lidGrupo);
		borrar_combo(document.frmNuevo.lidNumeroDocumento);
		document.frmAjax.ajax[0].value=document.frmNuevo.lidColegio.value;
		document.frmAjax.ajax[1].value=document.frmNuevo.lidSede.value;
		document.frmAjax.ajax[2].value=document.frmNuevo.lidJornada.value;
		document.frmAjax.ajax[3].value=document.frmNuevo.lidMet.value;
		document.frmAjax.ajax[4].value=document.frmNuevo.lidGrado.value;
		if(parseInt(document.frmAjax.ajax[0].value)>0 && parseInt(document.frmAjax.ajax[1].value)>0 && parseInt(document.frmAjax.ajax[2].value)>0 && parseInt(document.frmAjax.ajax[3].value)>0 && parseInt(document.frmAjax.ajax[4].value)>0){
			document.frmAjax.cmd.value=document.frmNuevo.AJAX_GRUPO.value;
	 		document.frmAjax.target="frameAjax";
			document.frmAjax.submit();
		}	
	}
	
	function ajaxEstudiante(){
		borrar_combo(document.frmNuevo.lidNumeroDocumento);
		
		document.frmAjax.ajax[0].value=document.frmNuevo.lidColegio.value;
		document.frmAjax.ajax[1].value=document.frmNuevo.lidSede.value;
		document.frmAjax.ajax[2].value=document.frmNuevo.lidJornada.value;
		document.frmAjax.ajax[3].value=document.frmNuevo.lidMet.value;
		document.frmAjax.ajax[4].value=document.frmNuevo.lidGrado.value;
		document.frmAjax.ajax[5].value=document.frmNuevo.lidGrupo.value;
		document.frmAjax.ajax[6].value=document.frmNuevo.lidRol.value;
		document.frmAjax.ajax[7].value=document.frmNuevo.lidInstancia.value;
		if(parseInt(document.frmAjax.ajax[0].value)>0 && parseInt(document.frmAjax.ajax[1].value)>0 && parseInt(document.frmAjax.ajax[2].value)>0 && parseInt(document.frmAjax.ajax[3].value)>0 && parseInt(document.frmAjax.ajax[4].value)>0 && parseInt(document.frmAjax.ajax[5].value)>0 && parseInt(document.frmAjax.ajax[6].value)>0 && parseInt(document.frmAjax.ajax[7].value)>0){
			document.frmAjax.cmd.value=document.frmNuevo.AJAX_ESTUDIANTE.value;
	 		document.frmAjax.target="frameAjax";
			document.frmAjax.submit();
		}	
	}

	
	function ajaxPersonal(){
		borrar_combo(document.frmNuevo.lidNumeroDocumento);
		
		document.frmAjax.ajax[0].value=document.frmNuevo.lidColegio.value;
		document.frmAjax.ajax[1].value=document.frmNuevo.lidSede.value;
		document.frmAjax.ajax[2].value=document.frmNuevo.lidJornada.value;
		document.frmAjax.ajax[3].value=document.frmNuevo.lidRol.value;
		document.frmAjax.ajax[4].value=document.frmNuevo.lidInstancia.value;
		if(parseInt(document.frmAjax.ajax[0].value)>0 && parseInt(document.frmAjax.ajax[1].value)>0 && parseInt(document.frmAjax.ajax[2].value)>0 && parseInt(document.frmAjax.ajax[3].value)>0 && parseInt(document.frmAjax.ajax[4].value)>0){
			document.frmAjax.cmd.value=document.frmNuevo.AJAX_PERSONAL.value;
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
			<div style="width:100%;height:220px;overflow:auto;vertical-align:top;background-color:#ffffff;">
				<c:import url="/participacion/lideres/FiltroLideres.jsp"/>
			</div>
		</td></tr>
		<tr style="display:none"><td><iframe name="frameAjax" id="frameAjax"></iframe></td></tr>
	</table>
	<form method="post" name="frmAjax" action='<c:url value="/participacion/lideres/Ajax.do"/>'><INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_LIDERES}"/>'><input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_AJAX}"/>'><input type="hidden" name="ajax"><input type="hidden" name="ajax"><input type="hidden" name="ajax"><input type="hidden" name="ajax"><input type="hidden" name="ajax"><input type="hidden" name="ajax"><input type="hidden" name="ajax"><input type="hidden" name="ajax"></form>
	
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
		<input type="hidden" name="AJAX_SEDE" value='<c:out value="${paramsVO.CMD_AJAX_SEDE}"/>'>
		<input type="hidden" name="AJAX_JORNADA" value='<c:out value="${paramsVO.CMD_AJAX_JORNADA}"/>'>
		<input type="hidden" name="AJAX_GRADO" value='<c:out value="${paramsVO.CMD_AJAX_GRADO}"/>'>
		<input type="hidden" name="AJAX_GRUPO" value='<c:out value="${paramsVO.CMD_AJAX_GRUPO}"/>'>
		<input type="hidden" name="AJAX_ESTUDIANTE" value='<c:out value="${paramsVO.CMD_AJAX_ESTUDIANTE}"/>'>
		
		<input type="hidden" name="AJAX_SEDEP" value='<c:out value="${paramsVO.CMD_AJAX_SEDEP}"/>'>
		<input type="hidden" name="AJAX_JORNADAP" value='<c:out value="${paramsVO.CMD_AJAX_JORNADAP}"/>'>
		<input type="hidden" name="AJAX_PERSONAL" value='<c:out value="${paramsVO.CMD_AJAX_PERSONAL}"/>'>
		
		<input type="hidden" name="NIVEL_DISTRITAL" value='<c:out value="${paramsVO2.NIVEL_INSTANCIA_DISTRITAL}"/>'>
		<input type="hidden" name="NIVEL_CENTRAL" value='<c:out value="${paramsVO2.NIVEL_INSTANCIA_CENTRAL}"/>'>
		<input type="hidden" name="NIVEL_LOCAL" value='<c:out value="${paramsVO2.NIVEL_INSTANCIA_LOCAL}"/>'>
		<input type="hidden" name="NIVEL_COLEGIO" value='<c:out value="${paramsVO2.NIVEL_INSTANCIA_COLEGIO}"/>'>
		
		<input type="hidden" name="accion" value="0">
		<input type="hidden" name="sede" value="">
		<input type="hidden" name="jornada" value="">
		<input type="hidden" name="metod" value="">
		<input type="hidden" name="grado" value="">
		<input type="hidden" name="grupo" value="">
		<input type="hidden" name="lidNivel" value='<c:out value="${sessionScope.filtroLideresVO.filNivel}"/>'>
		<input type="hidden" name="lidInstancia" value='<c:out value="${sessionScope.filtroLideresVO.filInstancia}"/>'>
		<input type="hidden" name="lidRango" value='<c:out value="${sessionScope.filtroLideresVO.filRango}"/>'>
		<input type="hidden" name="lidRol" value='<c:out value="${sessionScope.filtroLideresVO.filRol}"/>'>
		<input type="hidden" name="lidLocalidad" value='<c:out value="${sessionScope.filtroLideresVO.filLocalidad}"/>'>
		<input type="hidden" name="lidColegio" value='<c:out value="${sessionScope.filtroLideresVO.filColegio}"/>'>
		
		

	  <table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
		  <caption>Ingreso / Edición de lideres oficiales departamento</caption>
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
			
			<c:if test="${requestScope.porEstudiante=='true'}">
					
				 	
		
				 	<tr>
						<td><span class="style2">*</span>Sede:</td>
						<td colspan="3">
							<select name="lidSede" onchange="javaScript:ajaxJornada()" style="width:300px;" >
								<option value="-99" selected>-Seleccione uno-</option>
								<c:forEach begin="0" items="${requestScope.listaSedeVO}" var="sed">
									<option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.lideresVO.lidSede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
								</c:forEach>
							</select>
						</td>
				 	</tr>
		
				 	<tr>
						<td><span class="style2">*</span>Jornada:</td>
						<td>
							<select name="lidJornada">
								<option value="-99" selected>-Seleccione uno-</option>
								<c:forEach begin="0" items="${requestScope.listaJornadaVO}" var="jor">
									<option value="<c:out value="${jor.codigo}"/>" <c:if test="${jor.codigo==sessionScope.lideresVO.lidJornada}">selected</c:if>><c:out value="${jor.nombre}"/></option>
								</c:forEach>
							</select>
						</td>
						<td><span class="style2">*</span>Metodologia:</td>
						<td>
							<select name="lidMet" onchange="javaScript:ajaxGrado()">
								<option value="-99" selected>-Seleccione uno-</option>
								<c:forEach begin="0" items="${requestScope.listaMetodologiaVO}" var="met">
									<option value="<c:out value="${met.codigo}"/>" <c:if test="${met.codigo==sessionScope.lideresVO.lidMet}">selected</c:if>><c:out value="${met.nombre}"/></option>
								</c:forEach>
							</select>
						</td>
						
				 	</tr>
		
				 	<tr>
						<td><span class="style2">*</span>Grado:</td>
						<td colspan="3">
							<select name="lidGrado" onchange="javaScript:ajaxGrupo()" style="width:120px;" >
								<option value="-99" selected>-Seleccione uno-</option>
								<c:forEach begin="0" items="${requestScope.listaGradoVO}" var="gra">
									<option value="<c:out value="${gra.codigo}"/>" <c:if test="${gra.codigo==sessionScope.lideresVO.lidGrado}">selected</c:if>><c:out value="${gra.nombre}"/></option>
								</c:forEach>
							</select>
						</td>
				 	</tr>
		
				 	<tr>
						<td><span class="style2">*</span>Grupo:</td>
						<td colspan="3">
							<select name="lidGrupo" onchange="javaScript:ajaxEstudiante()" style="width:100px;" >
								<option value="-99" selected>-Seleccione uno-</option>
								<c:forEach begin="0" items="${requestScope.listaGrupoVO}" var="gru">
									<option value="<c:out value="${gru.codigo}"/>" <c:if test="${gru.codigo==sessionScope.lideresVO.lidGrupo}">selected</c:if>><c:out value="${gru.nombre}"/></option>
								</c:forEach>
							</select>
						</td>
				 	</tr>
		
						
					<c:if test="${sessionScope.lideresVO.formaEstado=='0'}">
							<tr>
								<td><span class="style2">*</span>Estudiante:</td>
								<td colspan='3'>
									<select name="lidNumeroDocumento" style='width:350px;'>
			              <option value='-99'>-- Seleccione uno --</option>
										<c:forEach begin="0" items="${requestScope.listaEstudianteVO}" var="est">
										<option value="<c:out value="${est.codigo2}"/>" <c:if test="${est.codigo2==sessionScope.lideresVO.lidNumeroDocumento}">selected</c:if>> <c:out value="${est.codigo2}"/> - <c:out value="${est.nombre}"/></option>
										</c:forEach>
			            </select>
								</td>
			 			  </tr>
		 			 </c:if>  
					<c:if test="${sessionScope.lideresVO.formaEstado=='1'}">
							<tr>
								<td>Tipo de Documento:</td>
								<td>
									<select name="lidTipoDocumento" disabled="disabled">
										<option value="-99" selected>-Seleccione uno-</option>
										<c:forEach begin="0" items="${requestScope.listaTipoDocumentoVO}" var="rol">
											<option value="<c:out value="${rol.codigo}"/>" <c:if test="${rol.codigo==sessionScope.lideresVO.lidTipoDocumento}">selected</c:if>><c:out value="${rol.nombre}"/></option>
										</c:forEach>
									</select>
								</td>
			 			  </tr>
							<tr>
								<td><span class="style2">*</span>Estudiante:</td>
								<td colspan='3'>
									<select name="lidNumeroDocumento" style='width:350px;'>
			                            <option value='-99'>-- Seleccione uno --</option>
										<c:forEach begin="0" items="${requestScope.listaEstudianteVO}" var="est">
											<option value="<c:out value="${est.codigo2}"/>" <c:if test="${est.codigo2==sessionScope.lideresVO.lidNumeroDocumento}">selected</c:if>> <c:out value="${est.codigo2}"/> - <c:out value="${est.nombre}"/></option>
										</c:forEach>
			                         </select>
								</td>
			 			  </tr>
		 			 </c:if>  
			</c:if>
			
			
			<c:if test="${requestScope.porPersonal=='true'}">
					
				 	
		
				 	<tr>
						<td><span class="style2">*</span>Sede:</td>
						<td colspan="3">
							<select name="lidSede" onchange="javaScript:ajaxJornadaPersonal()" style="width:300px;" >
								<option value="-99" selected>-Seleccione uno-</option>
								<c:forEach begin="0" items="${requestScope.listaSedeVO}" var="sed">
									<option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.lideresVO.lidSede}">selected</c:if>><c:out value="${sed.nombre}"/></option>
								</c:forEach>
							</select>
						</td>
				 	</tr>
		
				 	<tr>
						<td><span class="style2">*</span>Jornada:</td>
						<td>
							<select name="lidJornada" onchange="javaScript:ajaxPersonal()">
								<option value="-99" selected>-Seleccione uno-</option>
								<c:forEach begin="0" items="${requestScope.listaJornadaVO}" var="jor">
									<option value="<c:out value="${jor.codigo}"/>" <c:if test="${jor.codigo==sessionScope.lideresVO.lidJornada}">selected</c:if>><c:out value="${jor.nombre}"/></option>
								</c:forEach>
							</select>
						</td>
				 	</tr>
		
					<c:if test="${sessionScope.lideresVO.formaEstado=='0'}">
							<tr>
								<td><span class="style2">*</span>Personal:</td>
								<td colspan='3'>
									<select name="lidNumeroDocumento" style='width:350px;'>
			                            <option value='-99'>-- Seleccione uno --</option>
										<c:forEach begin="0" items="${requestScope.listaPersonalVO}" var="est">
											<option value="<c:out value="${est.codigo2}"/>" <c:if test="${est.codigo2==sessionScope.lideresVO.lidNumeroDocumento}">selected</c:if>> <c:out value="${est.codigo2}"/> - <c:out value="${est.nombre}"/></option>
										</c:forEach>
			                         </select>
								</td>
			 			  </tr>
		 			 </c:if>  
						
					<c:if test="${sessionScope.lideresVO.formaEstado=='1'}">
							<tr>
								<td>Tipo de Documento:</td>
								<td>
									<select name="lidTipoDocumento" disabled="disabled">
										<option value="-99" selected>-Seleccione uno-</option>
										<c:forEach begin="0" items="${requestScope.listaTipoDocumentoVO}" var="rol">
											<option value="<c:out value="${rol.codigo}"/>" <c:if test="${rol.codigo==sessionScope.lideresVO.lidTipoDocumento}">selected</c:if>><c:out value="${rol.nombre}"/></option>
										</c:forEach>
									</select>
								</td>
			 			  </tr>
							<tr>
								<td><span class="style2">*</span>Personal:</td>
								<td colspan='3'>
									<select name="lidNumeroDocumento" style='width:350px;'>
			                            <option value='-99'>-- Seleccione uno --</option>
										<c:forEach begin="0" items="${requestScope.listaPersonalVO}" var="est">
											<option value="<c:out value="${est.codigo2}"/>" <c:if test="${est.codigo2==sessionScope.lideresVO.lidNumeroDocumento}">selected</c:if>> <c:out value="${est.codigo2}"/> - <c:out value="${est.nombre}"/></option>
										</c:forEach>
			                         </select>
								</td>
			 			  </tr>
		 			 </c:if>  
			</c:if>			
			
			
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
					<select name="lidSuplente" >
						<option value="-99" selected>--</option>
						<option value='1' <c:if test="${sessionScope.lideresVO.lidSuplente== '1'}">SELECTED</c:if>>SUPLENTE</option>
						<option value='2' <c:if test="${sessionScope.lideresVO.lidSuplente== '2'}">SELECTED</c:if>>TITULAR</option>
					</select>
				</td>
			</tr>

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
	
	<form method="post" name="frmAux1" action='<c:url value="/participacion/lideres/Nuevo.do"/>'>
		<input type="hidden" name="combo" value="">
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_LIDERES}"/>'>
		<input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_AJAX}"/>'>
		<input type="hidden" name="sede" value="">
		<input type="hidden" name="jornada" value="">
		<input type="hidden" name="metod" value="">
		<input type="hidden" name="grado" value="">
		<input type="hidden" name="grupo" value="">
		<input type="hidden" name="ext" value="1">
		<input type="hidden" name="forma" value="frmNuevo">
	</form>
	
</body>
<script type="text/javascript">
	<c:if test="${requestScope.nuevoRegistro=='true'}">
	   cambioNivelNuevo(document.frmNuevo);
	</c:if>   
</script>
<script>
<c:if test="${requestScope.nuevoRegistro=='true'}">
	var nivel=document.frmNuevo.lidNivel.value;
	if(parseInt(nivel)==parseInt(document.frmNuevo.NIVEL_LOCAL.value)){
		document.frmNuevo.lidColegio.onchange();
	}
	if(parseInt(document.frmNuevo.formaEstado.value)==0){
		//alert("vale cero");
		ajaxRango();
	}	
	if(parseInt(document.frmNuevo.formaEstado.value)==1){
		//alert("vale uno");
	}
	
	<c:if test="${empty requestScope.listaEstudiantes}">
		//alert("no hay estudiantesssssssssssss");
	</c:if>
	
	<c:if test="${!empty requestScope.listaEstudiantes}">
	 //alert("si hay estudiantesssssssssssss");
	</c:if>
</c:if>

</script>
</html>