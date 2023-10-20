<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="paramsVO" class="articulacion.asigAcademica.vo.ParamsVO" scope="page"/>
<jsp:useBean id="params2VO" class="articulacion.asignatura.vo.ParamsVO" scope="page"/>
<jsp:useBean id="filAsigAcaVO" class="articulacion.asigAcademica.vo.DatosVO" scope="session"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<c:import url="/parametros.jsp"/>
<script language="javaScript">
<!--
	function lanzarServicio(n){
		if(n==1){//especialidad
			document.frmLista.action='<c:url value="/articulacion/especialidad/Nuevo.do"/>';  	
			document.frmLista.tipo.value='';
  	}
		if(n==2){//Area
			document.frmLista.action='<c:url value="/articulacion/area/Nuevo.do"/>';  	
			document.frmLista.tipo.value='';
  	}
		if(n==3){//Asignatura
			document.frmLista.action='<c:url value="/articulacion/asignatura/Nuevo.do"/>';  	
			document.frmLista.tipo.value='<c:out value="${params2VO.FICHA_ASIGNATURA}"/>';
  	}
		if(n==4){//Prueba
			document.frmLista.action='<c:url value="/articulacion/asignatura/Nuevo.do"/>';  	
			document.frmLista.tipo.value='<c:out value="${params2VO.FICHA_PRUEBA}"/>';
  	}
		if(n==5){//Asignación académica
  		document.frmLista.action='<c:url value="/articulacion/asigAcademica/Nuevo.do"/>';  	
			document.frmLista.tipo.value='';
  	}
		if(n==6){//Escala valorativa
  		document.frmLista.action='<c:url value="/articulacion/escValorativa/Nuevo.do"/>';  	
			document.frmLista.tipo.value='';
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
								<c:if test="${sede.codigo==jornada.sede}"> Sel_Hijos[k] = '<c:if test="${sessionScope.filAsigAcaVO.jornada==jornada.codigo}">SELECTED</c:if>'; id_Hijos[k] = '<c:out value="${jornada.codigo}"/>';  Hijos[k] = '<c:out value="${jornada.nombre}"/>'; id_Padre[k] = '<c:out value="${jornada.sede}"/>'; k++;</c:if>
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

	function ajaxDocente(){
		borrar_combo(document.frmLista.docente); 
		document.frmAjax.ajax[0].value=document.frmLista.institucion.value;
		document.frmAjax.ajax[1].value=document.frmLista.sede.options[document.frmLista.sede.selectedIndex].value;
		document.frmAjax.ajax[2].value=document.frmLista.jornada.options[document.frmLista.jornada.selectedIndex].value;
		document.frmAjax.cmd.value='<c:out value="${paramsVO.CMD_AJAX_DOCENTE}"/>';
 		document.frmAjax.target="frameAjax";
		document.frmAjax.submit();
	}
		
	function buscar(){
		if(validarForma(document.frmLista)){
			document.frmLista.cmd.value=document.frmLista.BUSCAR.value;
			document.frmLista.submit();
		}
	}
	
	function hacerValidaciones_frmLista(forma){
		validarLista(forma.sede, "- Sede", 1)
		validarLista(forma.jornada, "- Jornada", 1)
		validarLista(forma.docente, "- Docente", 1)
		validarLista(forma.anho, "- Año de vigencia", 0)
		validarLista(forma.periodo, "- Periodo de vigencia", 1)
	}
	
	function editar(id1,id2,id3){
		if(document.frmLista.id.length){
				document.frmLista.id[0].value=id1;
				document.frmLista.id[1].value=id2;
				document.frmLista.id[2].value=id3;
				document.frmLista.cmd.value=document.frmLista.EDITAR.value;
				document.frmLista.submit();
		}
	}
	function eliminar(id1,id2,id3){
		if(confirm('¿Desea eliminar la asignación seleccionada?')){
			if(document.frmLista.id.length){
					document.frmLista.id[0].value=id1;
					document.frmLista.id[1].value=id2;
					document.frmLista.id[2].value=id3;
					document.frmLista.cmd.value=document.frmLista.ELIMINAR.value;
					document.frmLista.submit();
			}
		}	
	}
//-->
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form action='<c:url value="/articulacion/asigAcademica/SaveAsignacion.jsp"/>' method="post" name="frmLista" onSubmit="return validarForma(frmLista)">
		<INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_ASIGNACION}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="id" value="">
		<input type="hidden" name="id" value="">
		<input type="hidden" name="id" value="">
		<INPUT TYPE="hidden" NAME="institucion" VALUE='<c:out value="${sessionScope.login.instId}"/>'>
		<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
		<input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
		<input type="hidden" name="ELIMINAR" value='<c:out value="${paramsVO.CMD_ELIMINAR}"/>'>
		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1">
				<td width="10">&nbsp;</td>
				<td rowspan="2" width="520">
					<a href="javaScript:lanzarServicio(1)"><img src='<c:url value="/etc/img/tabs/especialidad_0.gif"/>' alt="Especialidad" height="26" border="0"></a>
					<a href="javaScript:lanzarServicio(2)"><img src='<c:url value="/etc/img/tabs/areas_0.gif"/>' alt="Area" height="26" border="0"></a>
					<a href="javaScript:lanzarServicio(3)"><img src='<c:url value="/etc/img/tabs/asignatura_0.gif"/>' alt="Asignatura" height="26" border="0"></a>
					<a href="javaScript:lanzarServicio(4)"><img src='<c:url value="/etc/img/tabs/prueba_0.gif"/>' alt="Prueba" height="26" border="0"></a>
					<img src='<c:url value="/etc/img/tabs/carga_academica_1.gif"/>' alt="Asignación" height="26" border="0">
					<a href="javaScript:lanzarServicio(6)"><img src='<c:url value="/etc/img/tabs/escalas_valorativas_0.gif"/>' alt="Escala valorativa" height="26" border="0"></a>
				</td>
				</tr>
		</table>
		<table border="0" align="center" bordercolor="#FFFFFF" width="100%">
				<caption>FILTRO DE BÚSQUEDA</caption>
				<tr><td width="45%">
		        <input name="cmd1" type="button" value="Buscar" onClick="buscar()" class="boton">
			  </td></tr>	
	  </table>
		<table width="100%" border="0" cellspacing="2" cellpadding="0">
			<tr>
				<td><span class="style2">*</span><b>&nbsp;Sede:</b></td>
				<td colspan="3">
					<select name="sede" style='width:350px;' onChange='filtro(document.frmLista.sede,document.frmLista.jornada,document.frmLista.docente)'>
						<option value"-99">--Seleccione una--</option>
						<c:forEach begin="0" items="${requestScope.lSedeVO}" var="sede">
							<option value="<c:out value="${sede.codigo}"/>" <c:if test="${sede.codigo==sessionScope.filAsigAcaVO.sede}">selected</c:if>><c:out value="${sede.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td><span class="style2">*</span><b>&nbsp;Jornada:</b></td>
				<td>
					<select name="jornada" style='width:100px;' onchange="ajaxDocente()">
						<option value="-99">--Seleccione una--</option>
					</select>
				</td>
				<td><span class="style2">*</span><b>&nbsp;Vigencia:</b></td>
				<td>
					<select name="anho" style='width:50px;'>
						<c:forEach begin="0" items="${requestScope.listaVigencia}" var="item">
							<option value='<c:out value="${item.codigo}"/>' <c:if test="${item.codigo==sessionScope.filAsigAcaVO.anho}">selected</c:if>><c:out value="${item.nombre}"/></option>
						</c:forEach>
					</select>-
					<select name="periodo" style='width:30px;'>
						<option value="-99">--</option>
						<option value="1" <c:if test="${1==sessionScope.filAsigAcaVO.periodo}">selected</c:if>>1</option>
						<option value="2" <c:if test="${2==sessionScope.filAsigAcaVO.periodo}">selected</c:if>>2</option>
					</select>
				</td>
			</tr>
			<tr>
				<td><span class="style2">*</span><b>&nbsp;Docente:</b></td>
				<td colspan="3">
					<select name="docente" style='width:250px;'>
						<option value="-99">--Seleccione una--</option>
						<c:forEach begin="0" items="${requestScope.lDocenteVO}" var="doc">
							<option value="<c:out value="${doc.codigo}"/>" <c:if test="${doc.codigo==sessionScope.filAsigAcaVO.docente}">selected</c:if>><c:out value="${doc.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr><td style="display:none"><iframe name="frameAjax" id="frameAjax"></iframe></td></tr>
		</table>
	</form>
		<form method="post" name="frmAjax" action="<c:url value="/articulacion/asigAcademica/Ajax.do"/>">
			<input type="hidden" name="ajax">
			<input type="hidden" name="ajax">
			<input type="hidden" name="ajax">
			<INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_ASIGNACION}"/>'>
			<input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_AJAX}"/>'>
		</form>
		<c:if test="${sessionScope.filAsigAcaVO.buscado}">
			<table border="1" align="center" bordercolor="black" width="95%" cellpadding="0" cellspacing="0">
				<caption>LISTADO DE ASIGNACIONES</caption>
				<tr>
					<th class="EncabezadoColumna" width="25">&nbsp;</th>
					<th class="EncabezadoColumna" width="60%">Asignatura</th>
					<th class="EncabezadoColumna" width="30%">Intensidad</th>
				</tr>
				<c:forEach begin="0" items="${requestScope.lAsignacionVO}" var="asignacion" varStatus="st">
					<tr>
						<th class='Fila<c:out value="${st.count%2}"/>'>
						<a href='javaScript:editar(<c:out value="${asignacion.asigDocente}"/>,<c:out value="${asignacion.asigAsignatura}"/>,<c:out value="${asignacion.asigSemestre}"/>);'><img border='0' src='<c:url value="/etc/img/editar.png"/>' width='15' height='15'></a>
						<c:if test="${sessionScope.NivelPermiso==2}"><a href='javaScript:eliminar(<c:out value="${asignacion.asigDocente}"/>,<c:out value="${asignacion.asigAsignatura}"/>,<c:out value="${asignacion.asigSemestre}"/>);'><img border='0' src='<c:url value="/etc/img/eliminar.png"/>' width='15' height='15'></a></c:if>
						</th>
						<th class='Fila<c:out value="${st.count%2}"/>'>&nbsp;<c:out value="${asignacion.asigNombreAsignatura}"/>&nbsp;</th>
						<th class='Fila<c:out value="${st.count%2}"/>'>&nbsp;<c:out value="${asignacion.asigIntHor}"/>&nbsp;</th>
					</tr>
				</c:forEach>
			</table>				
		</c:if>
</body>
<script>filtro(document.frmLista.sede,document.frmLista.jornada,document.frmLista.jornada);</script>
</html>