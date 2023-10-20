<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="paramsVO" class="articulacion.horarioArticulacion.vo.ParamsVO" scope="page"/>
<jsp:useBean id="filtrohorArtVO" class="articulacion.horarioArticulacion.vo.FiltroHorarioVO" scope="session"/>
<html>
<head>
<c:import url="/parametros.jsp"/>
<script language="javaScript">
<!--
	function lanzarServicio(n){
		if(n==1){//grupo
			document.frmLista.action='<c:url value="/articulacion/grupoArt/Nuevo.do"/>';
  	}
		document.frmLista.target="";
		document.frmLista.submit();
  }	
	function lanzar(tipo){
  	document.frmLista.tipo.value=tipo;
		document.frmLista.target="";
		document.frmLista.submit();
	}

		function Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre){
			this.id_Hijos= id_Hijos; this.Hijos= Hijos; this.Sel_Hijos= Sel_Hijos; this.id_Padre=id_Padre;
		}

			function borrar_combo(combo){
				for(var i = combo.length - 1; i >= 0; i--) {
					if(navigator.appName == "Netscape") combo.options[i] = null;
					else combo.remove(i);
				}
				combo.options[0] = new Option("--Seleccione una--","-99");
			}
			
			function filtro(combo_padre,combo_hijo,combo_hijo2){
					borrar_combo(combo_hijo); 
					borrar_combo(combo_hijo2); 
					<c:if test="${!empty requestScope.lSedeVO && !empty requestScope.lJornadaVO}">
						var Padres = new Array();
						<c:forEach begin="0" items="${requestScope.lSedeVO}" var="sede" varStatus="st">
							id_Hijos=new Array();  Hijos= new Array();  Sel_Hijos= new Array();  id_Padre= new Array();  var k=0;
							<c:forEach begin="0" items="${requestScope.lJornadaVO}" var="jornada">
								<c:if test="${sede.codigo==jornada.sede}"> Sel_Hijos[k] = '<c:if test="${sessionScope.filtrohorArtVO.filJornada==jornada.codigo}">SELECTED</c:if>'; id_Hijos[k] = '<c:out value="${jornada.codigo}"/>';  Hijos[k] = '<c:out value="${jornada.nombre}"/>'; id_Padre[k] = '<c:out value="${jornada.sede}"/>'; k++;</c:if>
							</c:forEach>Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
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

	function initEspecialidad(obj){
		var val=obj.options[obj.selectedIndex].value;
		if(val==1){
			document.frmLista.filEspecialidad.disabled=true;
		}	
	}
	
	function ajaxEspecialidad(obj){
		borrar_combo(document.frmLista.filEspecialidad); 
		var val=obj.options[obj.selectedIndex].value;
		document.frmLista.filSemestre.selectedIndex=0;
		if(val==1){//academico
			document.frmLista.filEspecialidad.disabled=true;
		}else{//tecnico
			document.frmLista.filEspecialidad.disabled=false;
			document.frmAjax.ajax[0].value=document.frmLista.filInstitucion.value;
			document.frmAjax.cmd.value='<c:out value="${paramsVO.CMD_AJAX_ESPECIALIDAD}"/>';
	 		document.frmAjax.target="frameAjax";
			document.frmAjax.submit();
		}
	}

	function ajaxSemestre(obj){
		document.frmLista.filSemestre.selectedIndex=0;
	}

	function ajaxAsignaturaGrupo(){
		borrar_combo(document.frmLista.filGrupo); 
		document.frmAjax.ajax[0].value=document.frmLista.filInstitucion.value;
		document.frmAjax.ajax[1].value=document.frmLista.filAnhoVigencia.options[document.frmLista.filAnhoVigencia.selectedIndex].value;
		document.frmAjax.ajax[2].value=document.frmLista.filPerVigencia.options[document.frmLista.filPerVigencia.selectedIndex].value;
		document.frmAjax.ajax[3].value=document.frmLista.filComponente.options[document.frmLista.filComponente.selectedIndex].value;
		document.frmAjax.ajax[4].value=document.frmLista.filEspecialidad.options[document.frmLista.filEspecialidad.selectedIndex].value;
		document.frmAjax.ajax[5].value=document.frmLista.filSemestre.options[document.frmLista.filSemestre.selectedIndex].value;
		document.frmAjax.ajax[6].value=document.frmLista.filSede.options[document.frmLista.filSede.selectedIndex].value;
		document.frmAjax.ajax[7].value=document.frmLista.filJornada.options[document.frmLista.filJornada.selectedIndex].value;
		document.frmAjax.cmd.value='<c:out value="${paramsVO.CMD_AJAX_ASIGNATURA2}"/>';
 		document.frmAjax.target="frameAjax";
		document.frmAjax.submit();
	}
	
	function buscar(){
		if(validarForma(document.frmLista)){
			document.frmLista.filEspecialidad.disabled=false;
			document.frmLista.cmd.value=document.frmLista.BUSCAR.value;
			document.frmLista.submit();
		}
	}
	
	function hacerValidaciones_frmLista(forma){
		validarLista(forma.filSede, "- Sede", 1)
		validarLista(forma.filJornada, "- Jornada", 1)
		validarLista(forma.filAnhoVigencia, "- Año vigencia", 1)
		validarLista(forma.filPerVigencia, "- Periodo vigencia", 1)
		validarLista(forma.filComponente, "- Componente", 1)
		var j=forma.filComponente.options[forma.filComponente.selectedIndex].value;
		if(j==2){ validarLista(forma.filEspecialidad, "- Especialidad", 1) }
		validarLista(forma.filSemestre, "- Semestre", 1)
		validarLista(forma.filGrupo, "- Grupo", 1)
	}
//-->
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form action='<c:url value="/articulacion/horarioArticulacion/SaveHorario.jsp"/>' method="post" name="frmLista" onSubmit="return validarForma(frmLista)">
		<INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_HORARIO_GRUPO}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
		<INPUT TYPE="hidden" NAME="filInstitucion" VALUE='<c:out value="${sessionScope.login.instId}"/>'>
		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1">
				<td width="10">&nbsp;</td>
				<td rowspan="2" width="469">
				<a href="javaScript:lanzarServicio(1)"><img src='<c:url value="/etc/img/tabs/art_grupos_0.gif"/>' alt="Grupos" height="26" border="0"></a>
				<img src='<c:url value="/etc/img/tabs/horario_grupo_1.gif"/>' alt="Horario"  height="26" border="0">
				<a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_HORARIO_DOCENTE}"/>)"><img src='<c:url value="/etc/img/tabs/horario_docente_0.gif"/>' alt="Horario"  height="26" border="0"></a>
				</td>
				</tr>
		</table>
		<table border="0" align="center" width="100%" cellpadding="2" cellspacing="0">
	 	<caption>FILTRO DE BÚSQUEDA</caption>
		<tr><td width="45%">
        <input name="cmd1" type="button" value="Buscar" onClick="buscar()" class="boton">
	  </td></tr>	
	  </table>
		<table width="100%" border="0" cellspacing="2" cellpadding="2">
			<tr>
				<td><span class="style2">*</span><b>&nbsp;Sede:</b></td>
				<td colspan="3">
					<select name="filSede" style='width:350px;' onChange='filtro(document.frmLista.filSede,document.frmLista.filJornada,document.frmLista.filJornada)'>
						<option value="-99">--Seleccione una--</option>
						<c:forEach begin="0" items="${requestScope.lSedeVO}" var="sede">
							<option value="<c:out value="${sede.codigo}"/>" <c:if test="${sede.codigo==sessionScope.filtrohorArtVO.filSede}">selected</c:if>><c:out value="${sede.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td><span class="style2">*</span><b>&nbsp;Jornada:</b></td>
				<td>
					<select name="filJornada" style='width:100px;'>
						<option value="-99">--Seleccione una--</option>
					</select>
				</td>
				<td><span class="style2">*</span><b>&nbsp;Vigencia:</b></td>
				<td>
					<select name="filAnhoVigencia" style='width:50px;'>
						<option value="-99">--</option>
						<c:forEach begin="0" items="${requestScope.listaVigencia}" var="item">
							<option value='<c:out value="${item.codigo}"/>' <c:if test="${item.codigo==sessionScope.filtrohorArtVO.filAnhoVigencia}">selected</c:if>><c:out value="${item.nombre}"/></option>
						</c:forEach>
					</select>
					<select name="filPerVigencia" style='width:30px;'>
						<option value="-99">--</option>
						<c:forEach begin="0" items="${requestScope.lPeriodoVO}" var="vig">
							<option value="<c:out value="${vig}"/>" <c:if test="${vig==sessionScope.filtrohorArtVO.filPerVigencia}">selected</c:if>><c:out value="${vig}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td><span class="style2">*</span><b>&nbsp;Componente:</b></td>
				<td>
					<select name="filComponente" style='width:100px;' onchange="ajaxEspecialidad(this)">
						<option value="-99">--Seleccione una--</option>
						<c:forEach begin="0" items="${requestScope.lComponenteVO}" var="comp">
							<option value="<c:out value="${comp.codigo}"/>" <c:if test="${comp.codigo==sessionScope.filtrohorArtVO.filComponente}">selected</c:if>><c:out value="${comp.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
				<td><span class="style2">*</span><b>&nbsp;Especialidad:</b></td>
				<td>
					<select name="filEspecialidad" style='width:150px;' onchange="ajaxSemestre(this)">
						<option value="-99">--Seleccione una--</option>
						<c:forEach begin="0" items="${requestScope.lEspecialidadVO}" var="esp">
							<option value="<c:out value="${esp.codigo}"/>" <c:if test="${esp.codigo==sessionScope.filtrohorArtVO.filEspecialidad}">selected</c:if>><c:out value="${esp.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td><span class="style2">*</span><b>&nbsp;Semestre:</b></td>
				<td>
					<select name="filSemestre" style='width:30px;' onchange="ajaxAsignaturaGrupo()">
						<option value="-99">--</option>
						<c:forEach begin="0" items="${requestScope.lSemestreVO}" var="sem">
							<option value="<c:out value="${sem}"/>" <c:if test="${sem==sessionScope.filtrohorArtVO.filSemestre}">selected</c:if>><c:out value="${sem}"/></option>
						</c:forEach>
					</select>
				</td>
				<td><span class="style2">*</span><b>&nbsp;Grupo:</b></td>
				<td>
					<select name="filGrupo" style='width:100px;'>
						<option value="-99">--Seleccione una--</option>
						<c:forEach begin="0" items="${requestScope.lGrupoVO}" var="grupo">
							<option value="<c:out value="${grupo.codigo}"/>" <c:if test="${grupo.codigo==sessionScope.filtrohorArtVO.filGrupo}">selected</c:if>><c:out value="${grupo.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
			<td style="display:none"><iframe name="frameAjax" id="frameAjax"></iframe></td></tr>
		</table>
	</form>
		<form method="post" name="frmAjax" action="<c:url value="/articulacion/horarioArticulacion/Ajax.do"/>">
			<input type="hidden" name="ajax"><input type="hidden" name="ajax"><input type="hidden" name="ajax">
			<input type="hidden" name="ajax"><input type="hidden" name="ajax"><input type="hidden" name="ajax">
			<input type="hidden" name="ajax"><input type="hidden" name="ajax">
			<INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_HORARIO_GRUPO}"/>'>
			<input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_AJAX}"/>'>
		</form>
</body>
<script>
filtro(document.frmLista.filSede,document.frmLista.filJornada,document.frmLista.filJornada);
initEspecialidad(document.frmLista.filComponente);
</script>
</html>