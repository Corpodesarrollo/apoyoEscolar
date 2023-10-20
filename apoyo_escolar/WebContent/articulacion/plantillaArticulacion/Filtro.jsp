<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="paramsVO" class="articulacion.plantillaArticulacion.vo.ParamsVO" scope="page"/>
<jsp:useBean id="datosPVO" class="articulacion.plantillaArticulacion.vo.DatosVO" scope="session"/>

<html>
<head>
<script languaje="javaScript">
	
	function lanzarServicio(n){
		if(n==1){//plantilla
			document.frmFiltro.action='<c:url value="/articulacion/plantillaArticulacion/Nuevo.do"/>';  	
  	}
		if(n==2){//importacion
			document.frmFiltro.action='<c:url value="/articulacion/importarArticulacion/Nuevo.jsp"/>';  	
  	}	if(n==3){//Aprobación inscripciones articulación
			document.frmFiltro.action='<c:url value="/siges/gestionAdministrativa/inscripcion/Nuevo.do"/>';  	
  	}
		
		if(n==4){//Asignacion Estudiantes Grupo
			document.frmFiltro.action='<c:url value="/articulacion/asigGrupo/NuevoCarga.do"/>';  	
  	}
		document.frmFiltro.target="";
		document.frmFiltro.submit();
	}
	
	function lanzar(tipo){
	  	document.frmFiltro.tipo.value=tipo;
		document.frmFiltro.target="";
		document.frmFiltro.submit();
	}
	var nav4=window.Event ? true : false;
	
	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}
	
	function cambioSelect(objeto){
		var variable=objeto.options[objeto.selectedIndex].value
		if(variable=='0'){
			   document.getElementById ('esp').style.display='none';
		       document.getElementById ('esp1').style.display='none';
	//       document.getElementById ('per').style.display='none';
		}
		else if(variable=='2'){
		       document.getElementById ('esp').style.display='';
		       document.getElementById ('esp1').style.display='';
	//	       document.getElementById ('per').style.display='';
		}else if(variable=='1'){
			   document.getElementById ('esp').style.display='none';
		       document.getElementById ('esp1').style.display='none';
	//	       document.getElementById ('per').style.display='';
		}
		else{
		      //document.frmNuevo.combo2.selectedIndex=0;
		      document.getElementById('esp').style.display='none';
		}
	}

		function hacerValidaciones_frmFiltro(forma){
		//validarLista(forma.sede, "- Seleccione un Sede", 1);
        validarLista(forma.jornada, "- Seleccione un Jornada", 1);
		//	validarLista(forma.grado, "- Seleccione un Grado", 1);
		validarLista(forma.grupo, "- Seleccione una Grupo", 1);
		}
		
		function filtrar(){
		
		if(validarForma(document.frmFiltro))
		//if(hacerValidaciones_frmFiltro(document.frmFiltro))
		{
		document.frmFiltro.cmd.value=document.frmFiltro.FILTRO.value;
		document.frmFiltro.action='<c:url value="/articulacion/plantillaArticulacion/Save.jsp"/>';
		document.frmFiltro.submit();
		}
}
	//function hacerValidaciones_frmNuevo(forma){
	//validarEntero(forma.artAsigAnoVigencia, "- Vigencia", <c:out value="${ano}"/>, 9999);
	//}
	
	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) {
			if(navigator.appName == "Netscape") combo.options[i] = null;
			else combo.remove(i);
		}
		combo.options[0] = new Option("--Seleccione uno--","-99");
	}
	
	function iniciar(){
		document.frmFiltro.grado.selectedIndex=0;
		borrar_combo(document.frmFiltro.grupo);
	}
	
	function Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre){
			this.id_Hijos= id_Hijos; 
			this.Hijos= Hijos; 
			this.Sel_Hijos= Sel_Hijos; 
			this.id_Padre=id_Padre;
		}
	
	function setJornadas(combo_padre,combo_hijo){
		borrar_combo(combo_hijo); 
		<c:if test="${!empty requestScope.listaSedeVO && !empty requestScope.listaJornadaVO}">
			var Padres = new Array();
			<c:forEach begin="0" items="${requestScope.listaSedeVO}" var="sede" varStatus="st">
				id_Hijos=new Array();  Hijos= new Array();  Sel_Hijos= new Array();  id_Padre= new Array();  var k=0;
				<c:forEach begin="0" items="${requestScope.listaJornadaVO}" var="jornada">
					<c:if test="${sede.codigo==jornada.sede}"> 
						Sel_Hijos[k] = '<c:if test="${jornada.codigo==sessionScope.filtroPlantillaArticulacionVO.jornada}">SELECTED</c:if>'; 
						id_Hijos[k] = '<c:out value="${jornada.codigo}"/>';  
						Hijos[k] = '<c:out value="${jornada.nombre}"/>'; 
						id_Padre[k] = '<c:out value="${jornada.sede}"/>'; k++;
					</c:if>
				</c:forEach>
				Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);
			</c:forEach>
			var niv=combo_padre.options[combo_padre.selectedIndex].value;
			var val_padre = -99;
			for(var k=0;k<Padres.length;k++){
				if(Padres[k].id_Padre[0]==niv) val_padre=k;							
			}
			if(val_padre!=-99){ 
				var no_hijos = Padres[val_padre].Hijos.length;
				for(i=0; i < no_hijos; i++){
					if(Padres[val_padre].Sel_Hijos[i] == "SELECTED"){
						combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED"); 
						combo_hijo.selectedIndex = i+1;
					}else
						combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
				}
			}
		</c:if>		
		ajaxGrupo(document.frmFiltro.grado);
	}
	
	function ajaxGrupo(obj){
		borrar_combo(document.frmFiltro.grupo); 
		var val=obj.options[obj.selectedIndex].value;
		if(val!=-99){//
			document.frmAjax.ajax[1].value=document.frmFiltro.sede.value;
			document.frmAjax.ajax[2].value=document.frmFiltro.jornada.value;
			document.frmAjax.ajax[3].value=val;
			document.frmAjax.cmd.value='<c:out value="${paramsVO.CMD_AJAX_GRUPO}"/>';
	 		document.frmAjax.target="frameAjax";
			document.frmAjax.submit();
		}
	}
	
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body>
 <form method="post" name="frmFiltro" onSubmit="return validarForma(frmNuevo)">
	 <input type="hidden" name="cmd" value=''>
	 <input type="hidden" name="inst" value='<c:out value="${sessionScope.login.instId}"/>'>	 
	 <input type="hidden" name="FILTRO" value='<c:out value="${2}"/>'>
<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
	<tr>
		<td width="10">&nbsp;</td>
			<td rowspan="2" width="469">
			<img src='<c:url value="/etc/img/tabs/art_generar_plantillas_1.gif"/>' alt="Generar Plantillas Estudiantes" height="26" border="0">
			<a href="javaScript:lanzarServicio(2)"><img src='<c:url value="/etc/img/tabs/art_importar_plantillas_0.gif"/>' alt="" height="26" border="0"></a>
			<a href="javaScript:lanzarServicio(3)"><img src='<c:url value="/etc/img/tabs/aprob_inscr_0.gif"/>' alt="" height="26" border="0"></a>
			<a href="javaScript:lanzarServicio(4)"><img src='<c:url value="/etc/img/tabs/asig_grupo_media f_0.gif"/>' alt="" height="26" border="0"></a>
		</td>
	</tr>
</table>
		<table border="0" align="center" bordercolor="#FFFFFF" width="95%">
			<caption>Filtro de Búsqueda estudiantes articulación</caption>
				<c:if test="${!empty requestScope.listaSedeVO}">
					<tr><td style='width:30px;'><input name="cmd" type="button" value="Generar" onClick="filtrar()" class="boton"></td></tr>
			</c:if>
			<tr>
				<td><span class="style2">*</span><b>Sede:</b></td>
		 		<td>
					<select name="sede" onchange="setJornadas(this,document.frmFiltro.jornada)" style="width:250px;">
						<c:forEach begin="0" items="${requestScope.listaSedeVO}" var="sede">
							<option value="<c:out value="${sede.codigo}"/>" <c:if test="${sede.codigo==sessionScope.filtroPlantillaArticulacionVO.sede}">selected</c:if>><c:out value="${sede.nombre}"/></option>
						</c:forEach>
					</select>
  			</td>
  			<td><span class="style2">*</span><b>Jornada:</b></td>
	  		<td><select name="jornada" style="width:100px;"></select></td>
	  	</tr>
  		<tr>
	  		<td><span class="style2">*</span><b>Grado:</b></td>
				<td>
					<select name="grado" onchange="ajaxGrupo(this)" style="width:70px;">
							<option value="10" <c:if test="${10==sessionScope.filtroPlantillaArticulacionVO.grado}">selected</c:if>>Décimo</option>
							<option value="11" <c:if test="${11==sessionScope.filtroPlantillaArticulacionVO.grado}">selected</c:if>>Undécimo</option>
					</select>
 				</td>
 				<td><span  class="style2">*</span><b>Grupo Matrícula:</b></td>
				<td>
					<select name="grupo" style="width:70px;">
						<option value="-99">--Seleccione uno--</option>
						<c:forEach begin="0" items="${requestScope.ajaxGrupo}" var="grupo">
							<option value="<c:out value="${grupo.codigo}"/>"  <c:if test="${grupo.codigo==sessionScope.filtroPlantillaArticulacionVO.grupo}">selected</c:if>><c:out value="${grupo.nombre}"/></option>
						</c:forEach>
					</select>
		  		</td>
  		</tr>
			<tr>
				<td style="display:none"><iframe name="frameAjax" id="frameAjax"></iframe></td>
			</tr>
	 </table>
	 <br><br>
</form>
		<c:if test="${!empty requestScope.plantilla && requestScope.plantilla!='No'}">
			<table border="0" align="center" bordercolor="#FFFFFF" width="100%">
				<tr>
					<td colspan="6" align='center' valign="top">
						La plantilla fue generada satisfactoriamente.<br>Pulse en el Icono para descargar el archivo.<br>
						<a href='<c:url value="/${requestScope.rutaDescarga}"><c:param name="tipo" value="xls"/></c:url>' target='_blank'><img border=0 src='<c:url value="/etc/img/xls.gif"/>'></a>
					</td>
				</tr>
			</table>
		</c:if>
		<form method="post" name="frmAjax" action="<c:url value="/articulacion/plantillaArticulacion/Ajax.do"/>">
			<input type="hidden" name="ajax" value='<c:out value="${sessionScope.login.instId}"/>'>
			<input type="hidden" name="ajax">
			<input type="hidden" name="ajax">
			<input type="hidden" name="ajax">
			<INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_PERFIL}"/>'>
			<input type="hidden" name="cmd">
		</form>
</body>
<script>
	setJornadas(document.frmFiltro.sede,document.frmFiltro.jornada);
	ajaxGrupo(document.frmFiltro.grado);
	//setPeriodo(document.frmFiltro.especialidad,document.frmFiltro.periodo);
</script>
</html>