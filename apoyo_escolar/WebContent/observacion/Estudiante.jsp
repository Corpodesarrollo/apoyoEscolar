<!--	VERSION		FECHA			AUTOR				DESCRIPCION
			1.0		23/02/2021		JORGE CAMACHO		CODIGO ESPAGUETI Y SE MOFIFICÓ EL DISEÑO DE LA PÁGINA
														CON LAS NUEVAS IMÁGENES Y QUE FUERA RESPONSIVE
			2.0		02/06/2021		JORGE CAMACHO		SE CAMBIA LA CODIFICACIÓN DE LA PAGINA DE UTF-8 A windows-1252
-->

<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage="" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="paramsVO" class="siges.observacion.vo.ParamsVO" scope="page"/>
<jsp:useBean id="observacionEstudianteVO" class="siges.observacion.vo.ObservacionEstudianteVO" scope="session"/>

<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link href='<c:url value="/etc/css/2020/apoyo_escolar_2020.css"/>' rel="stylesheet" type="text/css">

<html>

<head>

	<c:import url="/parametros.jsp"/>
	
	<script language="javaScript">
		<!--
			function lanzarServicio(n){
				document.frmLista.action='<c:url value="/evaluacion/ControllerEvaluacionEdit.do"/>';  	
				document.frmLista.tipo.value=n;
				document.frmLista.cmd.value='';
				document.frmLista.target="";
				document.frmLista.submit();
			}
	
	   		function lanzarServicio2(n){
	    		document.frmLista.action='<c:url value="/observacion/NuevoEval.do"/>';  	
				document.frmLista.tipo.value=n;
				document.frmLista.cmd.value='<c:out value="${paramsVO.CMD_NUEVO}"/>';
				document.frmLista.target="";
				document.frmLista.submit();
			}

			function lanzar(n){
				document.frmLista.action='<c:url value="/observacion/Nuevo.do"/>';  	
				document.frmLista.tipo.value=n;
				document.frmLista.cmd.value='<c:out value="${paramsVO.CMD_NUEVO}"/>';
				document.frmLista.target="";
				document.frmLista.submit();
			}

			function buscar(){
				if(validarForma(document.frmLista)){
					document.frmLista.cmd.value=document.frmLista.BUSCAR.value;
					document.frmLista.submit();
				}
			}
			
			function hacerValidaciones_frmLista(forma){
				//validarLista(forma.obsSede, "- Sede", 1)
				//validarLista(forma.obsJornada, "- Jornada", 1)
				validarLista(forma.obsMetodologia, "- Metodologia", 1)
				validarLista(forma.obsGrado, "- Grado", 1)
				validarLista(forma.obsGrupo, "- Grupo", 1)
				validarLista(forma.obsPeriodo, "- Periodo", 1)
			}
	
			function Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre){
				this.id_Hijos = id_Hijos;
				this.Hijos = Hijos;
				this.Sel_Hijos = Sel_Hijos;
				this.id_Padre = id_Padre;
			}

			function borrar_combo(combo){
				for(var i = combo.length - 1; i >= 0; i--) {
					if(navigator.appName == "Netscape") combo.options[i] = null;
					else combo.remove(i);
				}
				combo.options[0] = new Option("--Seleccione una--","-99");
			}
	
			function filtro(combo_padre,combo_hijo){
				borrar_combo(combo_hijo); 
				<c:if test="${!empty requestScope.listaSede && !empty requestScope.listaJornada}">
					var Padres = new Array();
					<c:forEach begin="0" items="${requestScope.listaSede}" var="sede" varStatus="st">
						id_Hijos=new Array();  Hijos= new Array();  Sel_Hijos= new Array();  id_Padre= new Array();  var k=0;
						<c:forEach begin="0" items="${requestScope.listaJornada}" var="jornada">
							<c:if test="${sede.codigo==jornada.padre}"> Sel_Hijos[k] = '<c:if test="${sessionScope.observacionEstudianteVO.obsJornada==jornada.codigo}">SELECTED</c:if>'; id_Hijos[k] = '<c:out value="${jornada.codigo}"/>';  Hijos[k] = '<c:out value="${jornada.nombre}"/>'; id_Padre[k] = '<c:out value="${jornada.padre}"/>'; k++;</c:if>
						</c:forEach>Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);
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
								combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED"); combo_hijo.selectedIndex = i+1;
							}else
								combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
						}
					}
				</c:if>
			}

			function ajaxMetodologia(){
				document.frmLista.obsMetodologia.selectedIndex=0; 
			}
	
			function ajaxGrado(){
				borrar_combo(document.frmLista.obsGrado); 
				borrar_combo(document.frmLista.obsGrupo); 
				document.frmAjax.param[0].value=document.frmLista.obsInstitucion.value;
				document.frmAjax.param[1].value=document.frmLista.obsMetodologia.value;
				document.frmAjax.param[2].value=document.frmLista.obsSede.value;
				document.frmAjax.param[3].value=document.frmLista.obsJornada.value;
				document.frmAjax.cmd.value='<c:out value="${paramsVO.CMD_AJAX_GRADO}"/>';
		 		document.frmAjax.target="frameAjax";
				document.frmAjax.submit();
			}
	
			function ajaxGrupo(){
				borrar_combo(document.frmLista.obsGrupo); 
				document.frmAjax.param[0].value=document.frmLista.obsInstitucion.value;
				document.frmAjax.param[1].value=document.frmLista.obsMetodologia.value;
				document.frmAjax.param[2].value=document.frmLista.obsSede.value;
				document.frmAjax.param[3].value=document.frmLista.obsJornada.value;
				document.frmAjax.param[4].value=document.frmLista.obsGrado.options[document.frmLista.obsGrado.selectedIndex].value;
				
				document.frmAjax.cmd.value='<c:out value="${paramsVO.CMD_AJAX_GRUPO}"/>';
		 		document.frmAjax.target="frameAjax";
				document.frmAjax.submit();
			}
		//-->
	</script>
	
</head>

<c:import url="/mensaje.jsp"/>

<body onLoad="mensaje(document.getElementById('msg'));">

	<form method="post" name="frmAjax" action="<c:url value="/observacion/AjaxEval.do"/>">
		<input type="hidden" name="param">
		<input type="hidden" name="param">
		<input type="hidden" name="param">
		<input type="hidden" name="param">
		<input type="hidden" name="param">
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_AJAX}"/>'>
		<input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_AJAX}"/>'>
	</form>
	
	<form method="post" name="frmLista" class="formulario-evaluacion-general" onSubmit="return validarForma(frmLista)" action='<c:url value="/observacion/SaveEval.jsp"/>' >
		<table border="0" align="center" width="95%" cellpadding="0" cellspacing="0">
			<caption>OBSERVACIÓN POR ESTUDIANTE</caption>
		</table>
		
		<!-- FICHAS A MOSTRAR AL USUARIO -->
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_OBSERVACION_ESTUDIANTE}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
		<input type="hidden" name="obsInstitucion" value='<c:out value="${sessionScope.login.instId}"/>'>
		<input type="hidden" name="obsSede" value='<c:out value="${sessionScope.login.sedeId}"/>'>
		<input type="hidden" name="obsJornada" value='<c:out value="${sessionScope.login.jornadaId}"/>'>

		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" class="menu_top">
			<tr height="1">
				<td width="14%" height="26"><a href="javaScript:lanzarServicio(2)">Evaluación de Asignaturas</a></td>
				<c:forEach begin="0" items="${sessionScope.logroanddesc}" var="logdes"><td width="14%" height="26"><a onclick="javaScript:lanzarServicio(1)">Evaluación de <c:out value="${logdes[0]}"/></a></td></c:forEach>
				<td width="14%" height="26"><a href="javaScript:lanzarServicio(4)">Evaluación de Áreas</a></td>
				<c:forEach begin="0" items="${sessionScope.logroanddesc}" var="logdes"><td width="14%" height="26"><a onclick="javaScript:lanzarServicio(3)">Evaluación de <c:out value="${logdes[1]}"/></a></td></c:forEach></tr><tr>
				<!-- <td width="14%" height="26"><a href="javaScript:lanzarServicio(5)"><img src="../etc/img/tabs/Recuperacion_0.gif" alt="Logros" width="84"  height="26" border="0"></a></td> -->
				<td width="14%" height="26"><a href="javaScript:lanzarServicio(6)">Evaluación por Dimensiones</a></td>
				<td width="14%" height="26"><a href="javaScript:lanzarServicio(10)">Evaluación por Comportamiento</a></td>
				<td width="14%" height="26"><a href="javaScript:lanzarServicio2('<c:out value="${paramsVO.FICHA_OBSERVACION_ASIGNATURA}"/>')">Observación por Asignatura</a></td>
				<td width="14%" height="26"><a class="active" href="javaScript:lanzarServicio2('<c:out value="${paramsVO.FICHA_OBSERVACION_ESTUDIANTE}"/>')">Observación por Estudiante</a></td>	
			</tr>
		</table>
		<!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
		
		<table border="0" align="center" width="95%" cellpadding="3" cellspacing="0" class="tabla-filtros">
			<tr>
				<td><span class="style2">*</span><b>Metodología:</b></td>
				<td>
					<select name="obsMetodologia" style='width:120px;' onchange="ajaxGrado()">
						<option value="-99">-- Seleccione una --</option>
						<c:forEach begin="0" items="${requestScope.listaMetodologia}" var="metod">
							<option value='<c:out value="${metod.codigo}"/>' <c:if test="${metod.codigo==sessionScope.observacionEstudianteVO.obsMetodologia}">selected</c:if>><c:out value="${metod.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
				<td><span class="style2">*</span><b>&nbsp;Grado:</b></td>
				<td>
					<select name="obsGrado" style='width:100px;' onchange="ajaxGrupo()">
						<option value="-99">-- Seleccione una --</option>
						<c:forEach begin="0" items="${requestScope.listaGrado}" var="grado">
							<option value='<c:out value="${grado.codigo}"/>' <c:if test="${grado.codigo==sessionScope.observacionEstudianteVO.obsGrado}">selected</c:if>><c:out value="${grado.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
				<td><span class="style2">*</span><b>Grupo:</b></td>
				<td>
					<select name="obsGrupo" style='width:100px;'>
						<option value="-99">-- Seleccione una --</option>
						<c:forEach begin="0" items="${requestScope.listaGrupo}" var="grupo">
							<option value='<c:out value="${grupo.codigo}"/>' <c:if test="${grupo.codigo==sessionScope.observacionEstudianteVO.obsGrupo}">selected</c:if>><c:out value="${grupo.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td><span class="style2">*</span>Periodo:</td>
				<td>
					<select name="obsPeriodo" style='width:100px;'>
						<option value="-99">-- Seleccione uno --</option>
						<c:forEach begin="0" items="${requestScope.listaPeriodo}" var="per">
							<option value="<c:out value="${per.codigo}"/>" <c:if test="${per.codigo==sessionScope.observacionEstudianteVO.obsPeriodo}">selected</c:if>><c:out value="${per.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
				<td>Orden:</td>
				<td>
					<select name="obsOrden" style='width:100px;'>
				  		<option value="1">Apellidos</option>
				  		<option value="2">Nombres</option>
				 	</select>
				</td>
			</tr>
			<tr>
				<td colspan="8" align="center">
					<input name="cmd1" type="button" value="Buscar" onClick="buscar()" class="boton">
				</td>
			</tr>
			<tr style="display:none">
				<td>
					<iframe name="frameAjax" id="frameAjax"></iframe>
				</td>
			</tr>
		</table>
			
	</form>
	
</body>

<script>
	//filtro(document.frmLista.obsSede,document.frmLista.obsJornada);
</script>

</html>
