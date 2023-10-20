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
			if(parseInt(forma.formaEstado.value)==1){
				forma.lidSede.disabled=true;
				forma.lidJornada.disabled=true;
				<c:if test="${requestScope.porEstudiante=='true'}">
					forma.lidGrado.disabled=true;
					forma.lidGrupo.disabled=true;
					//forma.lidMet.disabled=true;
				</c:if>	
				forma.lidNumeroDocumento.disabled=true;
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
		//validarLista(forma.lidRango, "- Rango", 1);
		var nivel=forma.lidNivel.value;
		
			validarLista(forma.lidSede, "- Sede",1);
			validarLista(forma.lidJornada, "- Jornada",1);
			
			<c:if test="${requestScope.porEstudiante=='true'}">
				validarLista(forma.lidMet, "- Metodologia",1);
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

	function listaP(forma){
		//alert("***entroooooo por lista***");
		forma.tipo.value='<c:out value="${paramsVO.FICHA_LIDERES}"/>';
		forma.cmd.value='<c:out value="${paramsVO.CMD_AJAX}"/>';
		forma.accion.value='1';
		forma.sede.value=forma.lidSede.options[forma.lidSede.selectedIndex].value;
		forma.jornada.value=forma.lidJornada.options[forma.lidJornada.selectedIndex].value;
		
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
		//borrar_combo(document.frmNuevo.lidColegio); 
		document.frmAjax.ajax[0].value=document.frmNuevo.lidLocalidad.value;
		if(parseInt(document.frmAjax.ajax[0].value)>0){
			document.frmAjax.cmd.value=document.frmNuevo.AJAX_COLEGIO.value;
	 		document.frmAjax.target="frameAjax";
			document.frmAjax.submit();
		}	
	}


	
	function filtro(combo_padre,combo_hijo,combo_hijo2,combo_hijo3,combo_hijo4){
		//alert("entro a filtro");
		borrar_combo(combo_hijo); borrar_combo(combo_hijo2); borrar_combo(combo_hijo3);
		<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF}">var Padres = new Array();
			<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila" varStatus="st">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
				<c:forEach begin="0" items="${requestScope.filtroJornadaF}" var="fila2">
				<c:if test="${fila[0]==fila2[2]}">Sel_Hijos[k] = '<c:if test="${sessionScope.lideresVO.lidJornada==fila2[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k] = '<c:out value="${fila2[2]}"/>'; k++;</c:if></c:forEach>
				Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
			var niv=combo_padre.options[combo_padre.selectedIndex].value;
			var val_padre = -99;
			for(var k=0;k<Padres.length;k++){
				if(Padres[k].id_Padre[0]==niv) val_padre=k;							
			}
			if(val_padre!=-99){ var no_hijos = Padres[val_padre].Hijos.length;
				for(i=0; i < no_hijos; i++){
					if(Padres[val_padre].Sel_Hijos[i] == "SELECTED"){
						combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED"); combo_hijo.selectedIndex = i+1;
					}else
						combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
				}
			}
	</c:if>}


	function filtroP(combo_padre,combo_hijo){
		//alert("entro a filtroP");
		borrar_combo(combo_hijo);
		<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF}">var Padres = new Array();
			<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila" varStatus="st">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
				<c:forEach begin="0" items="${requestScope.filtroJornadaF}" var="fila2">
				<c:if test="${fila[0]==fila2[2]}">Sel_Hijos[k] = '<c:if test="${sessionScope.lideresVO.lidJornada==fila2[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k] = '<c:out value="${fila2[2]}"/>'; k++;</c:if></c:forEach>
				Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
			var niv=combo_padre.options[combo_padre.selectedIndex].value;
			var val_padre = -99;
			for(var k=0;k<Padres.length;k++){
				if(Padres[k].id_Padre[0]==niv) val_padre=k;
			}
			if(val_padre!=-99){ var no_hijos = Padres[val_padre].Hijos.length;
				for(i=0; i < no_hijos; i++){
					if(Padres[val_padre].Sel_Hijos[i] == "SELECTED"){
						combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED"); combo_hijo.selectedIndex = i+1;
					}else
						combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
				}
			}
	</c:if>}
	
	function filtro2(combo_padre,combo_padre2,combo_padre3,combo_hijo,combo_hijo2){
		borrar_combo(combo_hijo); borrar_combo(combo_hijo2);
		var id=0;
		<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF && !empty requestScope.filtroGradoF2 && !empty requestScope.filtroMetodologiaF}">var Padres = new Array();
			<c:forEach begin="0" items="${requestScope.filtroJornadaF}" var="fila2"  varStatus="st"><c:forEach begin="0" items="${requestScope.filtroMetodologiaF}" var="fila4">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
			<c:forEach begin="0" items="${requestScope.filtroGradoF2}" var="fila3"><c:if test="${fila2[2]==fila3[3] && fila2[0]==fila3[4] && fila3[5]==fila4[0]}">Sel_Hijos[k] = '<c:if test="${sessionScope.lideresVO.lidGrado== fila3[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila3[0]}"/>'; Hijos[k] = '<c:out value="${fila3[1]}"/>'; id_Padre[k] = '<c:out value="${fila3[3]}"/>|<c:out value="${fila3[4]}"/>|<c:out value="${fila3[5]}"/>'; k++;</c:if></c:forEach>
			Padres[id++] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach></c:forEach>
			var niv=combo_padre.options[combo_padre.selectedIndex].value+'|'+combo_padre2.options[combo_padre2.selectedIndex].value+'|'+combo_padre3.value;
			var val_padre = -99;
			for(var k=0;k<Padres.length;k++){
				if(Padres[k].id_Padre[0]==niv) val_padre=k;
			}
			if(val_padre!=-99){ var no_hijos = Padres[val_padre].Hijos.length;
				for(i=0; i < no_hijos; i++){
					if(Padres[val_padre].Sel_Hijos[i] == "SELECTED"){
						combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED"); combo_hijo.selectedIndex = i+1;
					}else
						combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
				}
			}
	</c:if>}

	function filtro3(combo_padre,combo_padre2,combo_padre3,combo_padre4,combo_hijo){
		borrar_combo(combo_hijo);
		<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF && !empty requestScope.filtroGradoF2 && !empty requestScope.filtroGrupoF}">var Padres = new Array();
			<c:forEach begin="0" items="${requestScope.filtroGradoF2}" var="fila2"  varStatus="st">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
			<c:forEach begin="0" items="${requestScope.filtroGrupoF}" var="fila3"><c:if test="${fila2[0]==fila3[4] && fila2[3]==fila3[2] && fila2[4]==fila3[3]  && fila2[5]==fila3[5]}">Sel_Hijos[k] = '<c:if test="${sessionScope.lideresVO.lidGrupo== fila3[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila3[0]}"/>'; Hijos[k] = '<c:out value="${fila3[1]}"/>'; id_Padre[k] = '<c:out value="${fila3[2]}"/>|<c:out value="${fila3[3]}"/>|<c:out value="${fila3[5]}"/>|<c:out value="${fila3[4]}"/>'; k++;</c:if></c:forEach>
			Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
			var niv=combo_padre.options[combo_padre.selectedIndex].value+'|'+combo_padre2.options[combo_padre2.selectedIndex].value+'|'+combo_padre3.options[combo_padre3.selectedIndex].value+'|'+combo_padre4.options[combo_padre4.selectedIndex].value;
			var val_padre = -99;
			for(var k=0;k<Padres.length;k++){
				if(Padres[k].id_Padre[0]==niv) val_padre=k;
			}
			if(val_padre!=-99){ var no_hijos = Padres[val_padre].Hijos.length;
				for(i=0; i < no_hijos; i++){
					if(Padres[val_padre].Sel_Hijos[i] == "SELECTED"){
						combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED"); combo_hijo.selectedIndex = i+1;
					}else
						combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
				}
			}
	</c:if>}
	
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
		<input type="hidden" name="NIVEL_DISTRITAL" value='<c:out value="${paramsVO2.NIVEL_INSTANCIA_DISTRITAL}"/>'>
		<input type="hidden" name="NIVEL_CENTRAL" value='<c:out value="${paramsVO2.NIVEL_INSTANCIA_CENTRAL}"/>'>
		<input type="hidden" name="NIVEL_LOCAL" value='<c:out value="${paramsVO2.NIVEL_INSTANCIA_LOCAL}"/>'>
		<input type="hidden" name="NIVEL_COLEGIO" value='<c:out value="${paramsVO2.NIVEL_INSTANCIA_COLEGIO}"/>'>
		<input type="hidden" name="lidLocalidad" value='<c:out value="${sessionScope.login.munId}"/>'>
		<input type="hidden" name="lidColegio" value='<c:out value="${sessionScope.login.instId}"/>'>
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
		

	  <table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
		  <caption>Ingreso / Edición de lideres oficiales</caption>
				<tr>
					<td width="45%"><c:if test="${sessionScope.NivelPermiso==2}"><c:if test="${requestScope.nuevoRegistro=='true'}"><input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton"></c:if>&nbsp;<input name="cmd1" type="button" value="Nuevo" onClick="nuevo()" class="boton"></c:if></td>
			 	</tr>	
		</table>	 	
		
	<c:if test="${requestScope.nuevoRegistro=='true'}">		
				<table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
					<c:if test="${requestScope.porEstudiante=='true'}">		
						<tr>
								<td><span class="style2">*</span> Sede:</td>
									<td colspan="3">
										<select name="lidSede" onChange='filtro(document.frmNuevo.lidSede, document.frmNuevo.lidJornada,document.frmNuevo.lidGrado,document.frmNuevo.lidGrupo,document.frmNuevo.lidMet)' style='width:406px;'>
										<option value='-99'>-- Seleccione uno --</option>
										<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila">
										<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.lideresVO.lidSede==fila[0]}">SELECTED</c:if>>
										<c:out value="${fila[1]}"/></option>
										</c:forEach>
										</select>											
									</td>	
							</tr>
						
							<tr>
								<td><span class="style2">*</span> Jornada:</td>
								<td>
									<select name="lidJornada" style='width:120px;' >
									<option value='-99'>-- Seleccione uno --</option>
									</select>							
								</td>
							<td><span class="style2">*</span> Metodologia:</td>
							<td>
						  	<select name="lidMet" style='width:120px;' onChange='filtro2(document.frmNuevo.lidSede,document.frmNuevo.lidJornada,document.frmNuevo.lidMet,document.frmNuevo.lidGrado,document.frmNuevo.lidGrupo)'>
	            		<option value='-99'>-- Seleccione uno --</option>
										<c:forEach begin="0" items="${requestScope.filtroMetodologiaF}" var="fila">
										<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.lideresVO.lidMet==fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option>
									  </c:forEach>
		            	</select>
							</td>	
							</tr>
							<tr>
								<td><span class="style2">*</span> Grado:</td>
								<td>
						            <select name="lidGrado" onChange='filtro3(document.frmNuevo.lidSede, document.frmNuevo.lidJornada,document.frmNuevo.lidMet,document.frmNuevo.lidGrado,document.frmNuevo.lidGrupo);' style='width:120px;'>
			            				<option value='-99'>-- Seleccione uno --</option>
											<c:forEach begin="0" items="${requestScope.filtroGradoF2}" var="fila">
											    <option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.lideresVO.lidGrado== fila[0]}">SELECTED</c:if>>
											    <c:out value="${fila[1]}"/></option>
										    </c:forEach>
						            </select>
								</td>	
								<td><span class="style2">*</span> Grupo:</td>
								<td>
									<select name="lidGrupo" style='width:120px;' onChange="lista(document.frmNuevo)">
										<option value='-99'>-- Seleccione uno --</option>
									</select>							
								</td>
							</tr>
							
						<c:if test="${sessionScope.lideresVO.formaEstado=='0'}">
								<tr>
									<td><span class="style2">*</span>Estudiante:</td>
									<td colspan='3'>
										<select name="lidNumeroDocumento" style='width:350px;'>
				                            <option value='-99'>-- Seleccione uno --</option>
				                             <c:forEach begin="0" items="${requestScope.listaEstudiantes}" var="fila">
										       <option value='<c:out value="${fila[0]}"/>'><c:out value="${fila[0]}"/> - <c:out value="${fila[1]}"/></option>
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
				                             <c:forEach begin="0" items="${requestScope.listaEstudiantes}" var="fila">
										       <option value='<c:out value="${fila[0]}"/>'><c:out value="${fila[0]}"/> - <c:out value="${fila[1]}"/></option>
											</c:forEach>																															
				                         </select>
									</td>
				 			  </tr>
			 			 </c:if>  
	 			  </c:if>  
					
				<c:if test="${requestScope.porPersonal=='true'}">
				
						<tr>
									<td><span class="style2">*</span> Sede:</td>
									<td colspan="3">
										<select name="lidSede" onChange='filtroP(document.frmNuevo.lidSede, document.frmNuevo.lidJornada)' style='width:406px;'>
										<option value='-99'>-- Seleccione uno --</option>
										<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila">
										<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.lideresVO.lidSede==fila[0]}">SELECTED</c:if>>
										<c:out value="${fila[1]}"/></option>
										</c:forEach>
										</select>											
									</td>	
							</tr>
						
							<tr>
								<td><span class="style2">*</span> Jornada:</td>
								<td>
									<select name="lidJornada" style='width:120px;' onChange="listaP(document.frmNuevo)">
									<option value='-99'>-- Seleccione uno --</option>
									</select>							
								</td>
							</tr>
							
						<c:if test="${sessionScope.lideresVO.formaEstado=='0'}">
								<tr>
									<td><span class="style2">*</span>Personal:</td>
									<td colspan='3'>
										<select name="lidNumeroDocumento" style='width:350px;'>
				                            <option value='-99'>-- Seleccione uno --</option>
				                             <c:forEach begin="0" items="${requestScope.listaPersonal}" var="fila">
										       <option value='<c:out value="${fila[0]}"/>'><c:out value="${fila[0]}"/> - <c:out value="${fila[1]}"/></option>
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
				                             <c:forEach begin="0" items="${requestScope.listaPersonal}" var="fila">
										       <option value='<c:out value="${fila[0]}"/>'><c:out value="${fila[0]}"/> - <c:out value="${fila[1]}"/></option>
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
	}else{
		
		<c:if test="${requestScope.porEstudiante=='true'}">		
			document.frmNuevo.lidSede.onchange();
			document.frmNuevo.lidMet.onchange();
			document.frmNuevo.lidGrado.onchange();
		</c:if>
	    <c:if test="${requestScope.porPersonal=='true'}">		
			document.frmNuevo.lidSede.onchange();
	    </c:if>		
	}
	
	if(parseInt(document.frmNuevo.formaEstado.value)==0){
		//alert("vale cero");
		ajaxRango();
	}	
	if(parseInt(document.frmNuevo.formaEstado.value)==1){
		//alert("vale uno");
	   //lista(document.frmNuevo);
	}
	
	
</c:if>
</script>
</html>