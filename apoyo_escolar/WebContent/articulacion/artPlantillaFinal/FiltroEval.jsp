<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="plantillaFinalVO" class="articulacion.artPlantillaFinal.vo.PlantillaFinalVO" scope="session"/>
<jsp:useBean id="paramsVO" class="articulacion.artPlantillaFinal.vo.ParamsVO" scope="page"/>
<html>
<head>
<c:import url="/parametros.jsp"/>
<script language="javaScript">
<!--
	
	function buscar(){
		if(validarForma(document.frmFiltro)){
			document.frmFiltro.plaEspecialidad.disabled=false;
			document.frmFiltro.cmd.value=document.frmFiltro.BUSCAR.value;
			document.frmFiltro.submit();
		}
	}

	function lanzar(tipo){
		document.frmFiltro.tipo.value=tipo;
		document.frmFiltro.action='<c:url value="/articulacion/artPlantillaFinal/Filtro.do"/>';
		document.frmFiltro.cmd.value='<c:out value="${paramsVO.CMD_NUEVO}"/>';
		document.frmFiltro.target="";
		document.frmFiltro.submit();
	}
	
	function hacerValidaciones_frmFiltro(forma){
		validarLista(forma.plaAnhoVigencia, "- Año de vigencia", 0)
		validarLista(forma.plaPerVigencia, "- Periodo de vigencia", 1)
		validarLista(forma.plaSemestre, "- Semestre", 1)
		validarLista(forma.plaSede, "- Sede", 1)
		validarLista(forma.plaJornada, "- Jornada", 1)
		validarLista(forma.plaGrado, "- Grado", 1)
		validarLista(forma.plaGrupo, "- Grupo", 1)
		//validarLista(forma.plaComponente, "- Componente", 1)
		//var i=forma.plaComponente.options[forma.plaComponente.selectedIndex].value;
		//if(parseInt(i)==2){
			validarLista(forma.plaEspecialidad, "- Especialidad", 1)
		//}
	}
	
	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) {
			if(navigator.appName == "Netscape") combo.options[i] = null;
			else combo.remove(i);
		}
		combo.options[0] = new Option("--Seleccione uno--","-99");
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
					<c:if test="${sede.codigo==jornada.padre}"> 
						Sel_Hijos[k] = '<c:if test="${jornada.codigo==sessionScope.plantillaFinalVO.plaJornada}">SELECTED</c:if>'; 
						id_Hijos[k] = '<c:out value="${jornada.codigo}"/>';  
						Hijos[k] = '<c:out value="${jornada.nombre}"/>'; 
						id_Padre[k] = '<c:out value="${jornada.padre}"/>'; k++;
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
	}

	function ajaxGrupo(obj,obj2){
		borrar_combo(obj2); 
		var val=obj.options[obj.selectedIndex].value;
		if(val!=-99){
			document.frmAjax.ajax[1].value=document.frmFiltro.plaSede.value;
			document.frmAjax.ajax[2].value=document.frmFiltro.plaJornada.value;
			document.frmAjax.ajax[3].value=val;
			document.frmAjax.cmd.value='<c:out value="${paramsVO.CMD_AJAX_GRUPO}"/>';
	 		document.frmAjax.target="frameAjax";
			document.frmAjax.submit();
		}
	}
	
	function ajaxTraerAsignaturas(obj){
		borrar_combo(document.frmFiltro.plaAsignatura); 
		var val=obj.options[obj.selectedIndex].value;
		if(val!=-99){
			document.frmAjax.ajax[1].value=document.frmFiltro.plaAnhoVigencia.value;
			document.frmAjax.ajax[2].value=document.frmFiltro.plaPerVigencia.value;
			document.frmAjax.ajax[3].value=2;
			document.frmAjax.ajax[4].value=document.frmFiltro.plaEspecialidad.value;
			document.frmAjax.cmd.value='<c:out value="${paramsVO.CMD_AJAX_ASIGNATURA}"/>';
	 		document.frmAjax.target="frameAjax";
			document.frmAjax.submit();
		}
	}
	
	function ajaxEspecialidad(){
		document.frmFiltro.plaEspecialidad.disabled=false;
		borrar_combo(document.frmFiltro.plaEspecialidad); 
		var val=document.frmFiltro.plaComponente.options[document.frmFiltro.plaComponente.selectedIndex].value;
		if(val==2){
			document.frmAjax.cmd.value='<c:out value="${paramsVO.CMD_AJAX_ESPECIALIDAD}"/>';
	 		document.frmAjax.target="frameAjax";
			document.frmAjax.submit();
		}else{
			document.frmFiltro.plaEspecialidad.disabled=true;
		}	
	}
//-->
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form action='<c:url value="/articulacion/artPlantillaFinal/Save.jsp"/>' method="post" name="frmFiltro" onSubmit="return validarForma(frmFiltro)">
		<INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_FILTRO_EVAL}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
		<input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
		<input type="hidden" name="ELIMINAR" value='<c:out value="${paramsVO.CMD_ELIMINAR}"/>'>
		<input type="hidden" name="plaInstitucion" value='<c:out value="${sessionScope.login.instId}"/>'>
		<input type="hidden" name="plaMetodologia" value='<c:out value="${sessionScope.login.metodologiaId}"/>'>
		<input type="hidden" name="plaComponente" value="2">
		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1">
				<td width="10">&nbsp;</td>
				<td rowspan="2" width="520">
					<img src='<c:url value="/etc/img/tabs/art_enlinea_1.gif"/>' alt="En linea" height="26" border="0">
					<a href='javaScript:lanzar(<c:out value="${paramsVO.FICHA_FILTRO}"/>)'><img src='<c:url value="/etc/img/tabs/art_generar_plantillas_0.gif"/>' alt="Importar" width="84" height="26" border="0"></a>
					<a href='javaScript:lanzar(<c:out value="${paramsVO.FICHA_FILTRO_IMP}"/>)'><img src='<c:url value="/etc/img/tabs/art_importar_plantillas_0.gif"/>' alt="Importar" width="84"  height="26" border="0"></a>
				</td>	
			</tr>
		</table>
		<table border="0" align="center" width="95%" cellpadding="2" cellspacing="0">
			<caption>EVALUACIÓN FINAL EN LINEA</caption>
				<tr><td width="45%"><input name="cmd1" type="button" value="Buscar" onClick="buscar()" class="boton"></td></tr>
	  </table>
		<table border="0" align="center" width="95%" cellpadding="2" cellspacing="0">
			<tr>
			<td width="25%"><span class="style2">*</span>Vigencia:</td>
			<td width="25%">
					<select name="plaAnhoVigencia" style='width:50px;'>
						<c:forEach begin="0" items="${requestScope.listaVigencia}" var="item">
							<option value='<c:out value="${item.codigo}"/>' <c:if test="${item.codigo==sessionScope.plantillaFinalVO.plaAnhoVigencia}">selected</c:if>><c:out value="${item.nombre}"/></option>
						</c:forEach>
					</select>&nbsp;-&nbsp;
					<select name="plaPerVigencia" style='width:30px;'>
						<option value="-99">--</option>
						<option value="1" <c:if test="${1==sessionScope.plantillaFinalVO.plaPerVigencia}">selected</c:if>>1</option>
						<option value="2" <c:if test="${2==sessionScope.plantillaFinalVO.plaPerVigencia}">selected</c:if>>2</option>
					</select>
			</td>
			<td  width="25%"><span class="style2">*</span>Semestre:</td>
			<td  width="25%">
					<select name="plaSemestre" style='width:30px;'>
						<option value="-99">--</option>
						<option value="1" <c:if test="${1==sessionScope.plantillaFinalVO.plaSemestre}">selected</c:if>>1</option>
						<option value="2" <c:if test="${2==sessionScope.plantillaFinalVO.plaSemestre}">selected</c:if>>2</option>
						<option value="3" <c:if test="${3==sessionScope.plantillaFinalVO.plaSemestre}">selected</c:if>>3</option>
						<option value="4" <c:if test="${4==sessionScope.plantillaFinalVO.plaSemestre}">selected</c:if>>4</option>
					</select>
			</td>
			</tr>
			<tr>
				<td><span class="style2">*</span><b>Sede:</b></td>
		 		<td colspan="3">
					<select name="plaSede" onchange="setJornadas(this,document.frmFiltro.plaJornada)" style='width:300px;'>
						<option value="-99">-Seleccione uno-</option>
						<c:forEach begin="0" items="${requestScope.listaSedeVO}" var="sede">
							<option value="<c:out value="${sede.codigo}"/>" <c:if test="${sede.codigo==sessionScope.plantillaFinalVO.plaSede}">selected</c:if>><c:out value="${sede.nombre}"/></option>
						</c:forEach>
					</select>
  			</td>
  		</tr>	
  		<tr>
  			<td><span class="style2">*</span><b>Jornada:</b></td>
	  		<td>
					<select name="plaJornada" style='width:100px;'>
					<option value="-99">-Seleccione uno-</option></select>
  			</td>
  			<td><span class="style2">*</span><b>Grado:</b></td>
	  		<td>
					<select name="plaGrado" onchange="ajaxGrupo(document.frmFiltro.plaGrado,document.frmFiltro.plaGrupo)" style='width:100px;'>
						<option value="-99">-Seleccione uno-</option>
						<option value="10" <c:if test="${10==sessionScope.plantillaFinalVO.plaGrado}">selected</c:if>>10</option>
						<option value="11" <c:if test="${11==sessionScope.plantillaFinalVO.plaGrado}">selected</c:if>>11</option>
					</select>
  			</td>
	  	</tr>
	  	<tr>
  			<td><span class="style2">*</span><b>Grupo:</b></td>
	  		<td>
					<select name="plaGrupo" style='width:100px;'>
						<option value="-99">-Seleccione uno-</option>
						<c:forEach begin="0" items="${requestScope.listaGrupoVO}" var="grupo">
							<option value='<c:out value="${grupo.codigo}"/>' <c:if test="${grupo.codigo==sessionScope.plantillaFinalVO.plaGrupo}">selected</c:if>><c:out value="${grupo.nombre}"/></option>
						</c:forEach>
					</select>
  			</td>
  			<!-- <td><span class="style2">*</span><b>Componente:</b></td>
	  		<td>
					<select name="plaComponente" onchange="ajaxEspecialidad()" style='width:100px;'>
						<option value="-99">-Seleccione uno-</option>
						<option value="1" <c:if test="${1==sessionScope.plantillaFinalVO.plaComponente}">selected</c:if>>Académico</option>
						<option value="2" <c:if test="${2==sessionScope.plantillaFinalVO.plaComponente}">selected</c:if>>Técnico</option>
					</select>
  			</td> -->
	  	</tr>
	  	<tr>
  			<td><span class="style2">*</span><b>Especialidad:</b></td>
	  		<td colspan="3">
					<select name="plaEspecialidad" style='width:250px;' onchange="ajaxTraerAsignaturas(this);">
						<option value="-99">-Seleccione uno-</option>
						<c:forEach begin="0" items="${requestScope.listaEspecialidadVO}" var="especialidad">
							<option value='<c:out value="${especialidad.codigo}"/>' <c:if test="${especialidad.codigo==sessionScope.plantillaFinalVO.plaEspecialidad}">selected</c:if>><c:out value="${especialidad.nombre}"/></option>
						</c:forEach>
					</select>
  			</td>
  		</tr>
  		<tr>
  			<td><span class="style2">*</span><b>Asignatura:</b></td>
	  		<td colspan="3">
				<select name="plaAsignatura" style='width:250px;'>
					<option value="-99">-Seleccione uno-</option>
					<c:forEach begin="0" items="${requestScope.ajaxAsignatura}" var="asignatura">
						<option value='<c:out value="${asignatura.asiCodigo}"/>' <c:if test="${asignatura.asiCodigo==sessionScope.plantillaFinalVO.plaAsignatura}">selected</c:if>><c:out value="${asignatura.asiNombre}"/></option>
					</c:forEach>
				</select>
  			</td>
  		</tr>		
		<tr>
			<td style="display:none"><iframe name="frameAjax" id="frameAjax"></iframe>
			</td>
		</tr>
		</table>		
	</form>
	<form method="post" name="frmAjax" action='<c:url value="/articulacion/artPlantillaFinal/Ajax.do"/>'>
		<input type="hidden" name="ajax" value='<c:out value="${sessionScope.login.instId}"/>'>
		<input type="hidden" name="ajax">
		<input type="hidden" name="ajax">
		<input type="hidden" name="ajax">
		<input type="hidden" name="ajax">
		<input type="hidden" name="ajax">
		<INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_FILTRO}"/>'>
		<input type="hidden" name="cmd">
	</form>
</body>
<script>
	setJornadas(document.frmFiltro.plaSede,document.frmFiltro.plaJornada);
</script>
</html>