<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="encuesta4VO" class="articulacion.artEncuesta.vo.Encuesta4VO" scope="session"/>
<jsp:useBean id="paramsVO" class="articulacion.artEncuesta.vo.ParamsVO" scope="page"/>
<html>
<head>
<c:import url="/parametros.jsp"/>
<script language="javaScript">
<!--
	function buscar(){
		if(validarForma(document.frmFiltro)){
			document.frmFiltro.cmd.value=document.frmFiltro.BUSCAR.value;
			document.frmFiltro.submit();
		}
	}

	function lanzar(tipo){
		document.frmFiltro.tipo.value=tipo;
		document.frmFiltro.action='<c:url value="/articulacion/artEncuesta/Filtro.do"/>';
		document.frmFiltro.cmd.value='<c:out value="${paramsVO.CMD_NUEVO}"/>';
		document.frmFiltro.target="";
		document.frmFiltro.submit();
	}
	
	function hacerValidaciones_frmFiltro(forma){
		validarLista(forma.filAsignatura, "- Asignatura",1)
		validarLista(forma.filOpcion, "- Opción", 1)		
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
	

	
	function ajaxInstitucion(obj,obj2,obj3,obj4,obj5){
		borrar_combo(obj2); 
		borrar_combo(obj3); 
		borrar_combo(obj4); 
		borrar_combo(obj5); 
		var val=obj.options[obj.selectedIndex].value;
		if(val!=-99){
			document.frmAjax.ajax[0].value=document.frmFiltro.plaLocalidad.value;			
			document.frmAjax.cmd.value='<c:out value="${paramsVO.CMD_AJAX_COLEGIO}"/>';
	 		document.frmAjax.target="frameAjax";
			document.frmAjax.submit();
		}
	}
	
		function ajaxSedes(obj,obj2,obj3,obj4){
		borrar_combo(obj2); 
		borrar_combo(obj3); 
		borrar_combo(obj4); 
		var val=obj.options[obj.selectedIndex].value;
		if(val!=-99){
			document.frmAjax.ajax[0].value=document.frmFiltro.plaInstitucion.value;			
			document.frmAjax.cmd.value='<c:out value="${paramsVO.CMD_AJAX_SEDE}"/>';
	 		document.frmAjax.target="frameAjax";
			document.frmAjax.submit();
		}
	}
	
	function ajaxJornadas(obj,obj2,obj3){
		borrar_combo(obj2); 
		borrar_combo(obj3); 
		var val=obj.options[obj.selectedIndex].value;
		if(val!=-99){
			document.frmAjax.ajax[0].value=document.frmFiltro.plaInstitucion.value;
			document.frmAjax.ajax[1].value=document.frmFiltro.plaSede.value;			
			document.frmAjax.cmd.value='<c:out value="${paramsVO.CMD_AJAX_JORNADA}"/>';
	 		document.frmAjax.target="frameAjax";
			document.frmAjax.submit();
		}
	}
	

	function ajaxGrado(obj,obj2){
		borrar_combo(obj2); 
		var val=obj.options[obj.selectedIndex].value;
		if(val!=-99){
			document.frmAjax.ajax[0].value=document.frmFiltro.plaInstitucion.value;
			document.frmAjax.ajax[1].value=document.frmFiltro.plaSede.value;
			document.frmAjax.ajax[2].value=document.frmFiltro.plaJornada.value;
			document.frmAjax.cmd.value='<c:out value="${paramsVO.CMD_AJAX_GRADO}"/>';
	 		document.frmAjax.target="frameAjax";
			document.frmAjax.submit();
		}
	
	
	}
	
//-->
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form action='<c:url value="/articulacion/artEncuesta/Save4.jsp"/>' method="post" name="frmFiltro" onSubmit="return validarForma(frmFiltro)">
		<INPUT TYPE="hidden" NAME="tipo" VALUE='4'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_DESCARGAR}"/>'>
		<input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
		<input type="hidden" name="ELIMINAR" value='<c:out value="${paramsVO.CMD_ELIMINAR}"/>'>		
		<input type="hidden" name="plaMetodologia" value='<c:out value="${sessionScope.login.metodologiaId}"/>'>
		<input type="hidden" name="modulo" value='51'>
		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1">
				<td width="10">&nbsp;</td>
				<td rowspan="2" width="520">
				<a href='javaScript:lanzar(1)'><img src='<c:url value="/etc/img/tabs/EstadoPorEstudiante_0.gif"/>' alt="Estado por Estudiante" width="84" height="26" border="0"></a>
				<a href='javaScript:lanzar(2)'><img src='<c:url value="/etc/img/tabs/consolidado_num_estudiantes_0.gif"/>' alt="Consolidado # Estudiantes" width="84" height="26" border="0"></a>
				<a href='javaScript:lanzar(3)'><img src='<c:url value="/etc/img/tabs/consolidado_encuesta_0.gif"/>' alt="Consolidado General Preguntas" width="84" height="26" border="0"></a>
				<img src='<c:url value="/etc/img/tabs/motivo_de_preferencia_1.gif"/>' alt="Motivos de Preferencia" height="26" border="0">					
				</td>	
			</tr>
		</table>
		<table border="0" align="center" width="95%" cellpadding="2" cellspacing="0">
			<caption>Filtro de solicitud</caption>
			<tr><td width="45%">
        <input name="cmd1" type="button" value="Generar Reporte" onClick="buscar()" class="boton">
		  </td></tr>	
	  </table>
		<table border="0" align="center" width="95%" cellpadding="2" cellspacing="0">
		<tr>
  			<td><b>Localidad:</b></td>
	  		<td colspan="3"><select name="plaLocalidad" onchange="javaScript:ajaxInstitucion(document.frmFiltro.plaLocalidad,document.frmFiltro.plaInstitucion,document.frmFiltro.plaSede,document.frmFiltro.plaJornada,document.frmFiltro.plaGrado)" style='width:160px;' <c:if test='${sessionScope.encuesta4VO.habilitarLocalidad==1}'>disabled</c:if>>
						<option value="-99">-Seleccione uno-</option>
						<c:forEach begin="0" items="${requestScope.listaLocalidadesVO}" var="loc">
							<option value='<c:out value="${loc.codigo}"/>' <c:if test="${loc.codigo==sessionScope.encuesta4VO.plaLocalidad}">selected</c:if>><c:out value="${loc.nombre}"/></option>
						</c:forEach>
					</select>
  			</td>
  		</tr>
  		<tr>	
  			<td><b>Colegio:</b></td>
	  		<td colspan="3">
					<select name="plaInstitucion" onchange="ajaxSedes(document.frmFiltro.plaInstitucion,document.frmFiltro.plaSede,document.frmFiltro.plaJornada,document.frmFiltro.plaGrado)" style='width:250px;' <c:if test='${sessionScope.encuesta4VO.habilitarColegio==1}'>disabled</c:if>>
						<option value="-99">-Seleccione uno-</option>
						<c:forEach begin="0" items="${requestScope.listaColegiosVO}" var="col">
							<option value='<c:out value="${col.codigo}"/>' <c:if test="${col.codigo==sessionScope.encuesta4VO.plaInstitucion}">selected</c:if>><c:out value="${col.nombre}"/></option>
						</c:forEach>
					</select>
  			</td>
	  	</tr>
			<tr>
				<td><b>Sede:</b></td>
		 		<td colspan="3">
					<select name="plaSede" onchange="ajaxJornadas(document.frmFiltro.plaSede,document.frmFiltro.plaJornada,document.frmFiltro.plaGrado)" style='width:250px;'>
						<option value="-99">-Seleccione uno-</option>
						<c:forEach begin="0" items="${requestScope.listaSedeVO}" var="sede">
							<option value="<c:out value="${sede.codigo}"/>" <c:if test="${sede.codigo==sessionScope.encuesta4VO.plaSede}">selected</c:if>><c:out value="${sede.nombre}"/></option>
						</c:forEach>
					</select>
  			</td>
  		</tr>
  		<tr>	
  			<td><b>Jornada:</b></td>
	  		<td>
					<select name="plaJornada" style='width:100px;' onchange="ajaxGrado(document.frmFiltro.plaJornada,document.frmFiltro.plaGrado)">
						<option value="-99">-Seleccione uno-</option>
						<c:forEach begin="0" items="${requestScope.listaJornadaVO}" var="jor"><option value="<c:out value="${jor.codigo}"/>" <c:if test="${jor.codigo==sessionScope.encuesta4VO.plaJornada}">selected</c:if>><c:out value="${jor.nombre}"/></option></c:forEach>
					</select>
  			</td>
  			<td><b>Grado:</b></td>
	  		<td>
					<select name="plaGrado" style='width:100px;'>
						<option value="-99">-Seleccione uno-</option>
						<c:forEach begin="0" items="${requestScope.listaGradoVO}" var="grado">
							<option value='<c:out value="${grado.codigo}"/>' <c:if test="${grado.codigo==sessionScope.encuesta4VO.plaGrado}">selected</c:if>><c:out value="${grado.nombre}"/></option>
						</c:forEach>
					</select>
  			</td>
  		</tr>	
  		<tr>  			
		  	<td><span class="style2">*</span><b>Asignatura:</b></td>
	  		<td>
					<select name="filAsignatura" style='width:150px;'>
						<option value="-99">-Seleccione uno-</option>
						<c:forEach begin="0" items="${requestScope.listaAsignaturasVO}" var="asig">
							<option value='<c:out value="${asig.codigo}"/>' <c:if test="${asig.codigo==sessionScope.encuesta4VO.filAsignatura}">selected</c:if>><c:out value="${asig.nombre}"/></option>
						</c:forEach>
					</select>
  			</td>
  			<td><span class="style2">*</span><b>Opción:</b></td>
	  		<td>
					<select name="filOpcion" style='width:80px;'>
						<option value="-99">- Seleccione uno -</option>
						<c:forEach begin="0" items="${requestScope.listaOpcionesVO}" var="opc">
							<option value='<c:out value="${opc.codigo}"/>' <c:if test="${opc.codigo==sessionScope.encuesta4VO.filOpcion}">selected</c:if>><c:out value="${opc.nombre}"/></option>
						</c:forEach>
					</select>
  			</td>  		
		 	</tr>
			<tr><td style="display:none"><iframe name="frameAjax" id="frameAjax"></iframe></td></tr>
		</table>		
	</form>
	<form method="post" name="frmAjax" action='<c:url value="/articulacion/artEncuesta/Ajax.do"/>'>
		<input type="hidden" name="ajax">
		<input type="hidden" name="ajax">
		<input type="hidden" name="ajax">
		<input type="hidden" name="ajax">
		<INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="3"/>'>
		<input type="hidden" name="cmd">
	</form>
</body>
<script>
</script>
</html>