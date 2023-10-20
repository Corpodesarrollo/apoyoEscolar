<%@ page contentType="text/html; charset=iso-8859-9" language="java" errorPage="" %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="filtroEst" class="articulacion.repHorariosArt.vo.FiltroBeanRepEstudiante" scope="session"/>
<jsp:useBean id="paramsVO" class="articulacion.repHorariosArt.vo.ParamsVO" scope="page"/>
<c:import url="/parametros.jsp"/>
		<script languaje='JavaScript'>
			
			var nav4=window.Event ? true : false;
			function acepteNumeros(eve){
				var key=nav4?eve.which :eve.keyCode;
				return(key<=13 || (key>=48 && key<=57));
			}
			function hacerValidaciones_frm(forma){
			   validarLista(forma.sede, "- Sede", 1);
			   validarLista(forma.jornada, "- Jornada", 1);
			   validarLista(forma.filAnoVigenciaArt, "- Año Vigencia", 1);			   
			   validarLista(forma.filPerVigenciaArt, "- Periodo Vigencia", 1);			   			   
			   validarLista(forma.filEspecialidad, "- Especialidad", 1);
			   validarLista(forma.filComponenteArt, "- Componente", 1);			   			   
			}

			function lanzar(tipo){	
				document.frm.tipo.value=tipo;
				document.frm.action="<c:url value="/articulacion/repHorariosArt/ControllerRepHorariosArtFiltroEdit.do"/>";
				document.frm.target="";
				document.frm.submit();
			}

			function guardar(tipo){
				if(validarForma(document.frm)){
					document.frm.tipo.value=tipo;
					document.frm.cmd.value="Buscar";
					document.frm.submit();
				}	
			}
			function cancelar(){
				if (confirm('¿No desea generar Horarios Articulación?')){
 					document.frm.cmd.value="Cancelar";
					location.href='<c:url value="/bienvenida.jsp"/>';
				}
			}
			
			function borrar_combo(combo){
				for(var i = combo.length - 1; i >= 0; i--) {
					if(navigator.appName == "Netscape") combo.options[i] = null;
					else combo.remove(i);
				}
				combo.options[0] = new Option("--Seleccione uno--","-9");
			}
			function borrar_combo1(combo){
				for(var i = combo.length - 1; i >= 0; i--) {
					if(navigator.appName == "Netscape") combo.options[i] = null;
					else combo.remove(i);
				}
				combo.options[0] = new Option("--Seleccione una--","-9");
			}	
		
		function Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre){
			this.id_Hijos= id_Hijos; this.Hijos= Hijos; this.Sel_Hijos= Sel_Hijos; this.id_Padre=id_Padre;
		}
		
		function filtro(combo_padre,combo_hijo){
					borrar_combo(combo_hijo); 					
					<c:if test="${!empty requestScope.lSedeVO && !empty requestScope.lJornadaVO}">
						var Padres = new Array();
						<c:forEach begin="0" items="${requestScope.lSedeVO}" var="sede" varStatus="st">
							id_Hijos=new Array();  Hijos= new Array();  Sel_Hijos= new Array();  id_Padre= new Array();  var k=0;
							<c:forEach begin="0" items="${requestScope.lJornadaVO}" var="jornada">
								<c:if test="${sede.codigo==jornada.sede}"> Sel_Hijos[k] = '<c:if test="${sessionScope.filtroEst.jornada==jornada.codigo}">SELECTED</c:if>'; id_Hijos[k] = '<c:out value="${jornada.codigo}"/>';  Hijos[k] = '<c:out value="${jornada.nombre}"/>'; id_Padre[k] = '<c:out value="${jornada.sede}"/>'; k++;</c:if>
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
					
	function ajaxEstudiante(){
		borrar_combo(document.frm.filEstudiante); 
		document.frmAjax.ajax[0].value=document.frm.filInstitucion.value;				
		document.frmAjax.ajax[1].value=document.frm.sede.options[document.frm.sede.selectedIndex].value;				
		document.frmAjax.ajax[2].value=document.frm.filEspecialidad.options[document.frm.filEspecialidad.selectedIndex].value;				
		document.frmAjax.ajax[3].value=document.frm.jornada.options[document.frm.jornada.selectedIndex].value;				
		document.frmAjax.cmd.value='<c:out value="${paramsVO.CMD_AJAX_ESTUDIANTE}"/>';
 		document.frmAjax.target="frameAjax";
		document.frmAjax.submit();
	}				
</script>
<c:import url="/mensaje.jsp"/>
	<form method="post" name="frm" onSubmit="return validarForma(frm)" action='<c:url value="/articulacion/repHorariosArt/FiltroGuardarEstudiante.jsp"/>'>
	<input type="hidden" name="tipo" value='<c:out value="${pageScope.tip}"/>'>	
	<input type="hidden" name="cmd" value="Cancelar">
	<INPUT TYPE="hidden" NAME="ext2"  VALUE=''><INPUT TYPE="hidden" NAME="height" VALUE='180'>
	<INPUT TYPE="hidden" NAME="filInstitucion" VALUE='<c:out value="${sessionScope.login.instId}"/>'>
	<table border="0" align="center" width="100%">
	<caption>Filtro de busqueda</caption>
		<tr>
		  <td width="45%" bgcolor="#FFFFFF">
        <input class="boton" name="cmd1" type="button" value="Generar" onClick="guardar(document.frm.tipo.value)">
        <input class="boton" name="cmd12" type="button" value="Cancelar" onClick="cancelar()">
		 </td>
		</tr>
	</table>
<!--//////////////////-->	
	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<table border="0" align="center" width="100%">
		<tr height="1">
			<td rowspan="2" width="420" bgcolor="#FFFFFF"><a href="javaScript:lanzar(5)"><img src="../../etc/img/tabs/art_grupos_0.gif" width="84"  height="26" border="0"></a><img src="../../etc/img/tabs/Horario_Estudiante_1.gif" width="84"  height="26" border="0"><a href="javaScript:lanzar(1)"><img src="../../etc/img/tabs/horario_grupo_0.gif" width="84"  height="26" border="0"></a><a href="javaScript:lanzar(2)"><img src="../../etc/img/tabs/horario_docente_0.gif" width="84"  height="26" border="0"></a><a href="javaScript:lanzar(3)"><img src="../../etc/img/tabs/horario_espacio_fisico_0.gif" width="84"  height="26" border="0"></a></td>
		</tr>
  </table>
		<table width="100%" border="0" cellspacing="2" cellpadding="0">
			<tr>
				<td><span class="style2">*</span><b>&nbsp;Sede:</b></td>
				<td colspan="3">
					<select name="sede" style='width:350px;' onChange='filtro(document.frm.sede,document.frm.jornada)'>
						<option value="-99">--Seleccione una--</option>
						<c:forEach begin="0" items="${requestScope.lSedeVO}" var="sede">
							<option value="<c:out value="${sede.codigo}"/>" <c:if test="${sede.codigo==sessionScope.filtroEst.sede}">selected</c:if>><c:out value="${sede.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td><span class="style2">*</span><b>&nbsp;Jornada:</b></td>
				<td>
					<select name="jornada" style='width:100px;'>
						<option value="-99">--Seleccione una--</option>
					</select>
				</td>
				<td><span class="style2">*</span><b>&nbsp;Vigencia:</b></td>
				<td>
					<select name="filAnoVigenciaArt" style='width:50px;'>
						<option value="-99">--</option>
						<c:forEach begin="0" items="${requestScope.VigenciaArtHor}" var="vig">
							<option value="<c:out value="${vig}"/>" <c:if test="${vig==sessionScope.filtroEst.filAnoVigenciaArt}">selected</c:if>><c:out value="${vig}"/></option>
						</c:forEach>
					</select>
					<select name="filPerVigenciaArt" style='width:30px;'>
						<option value="-99">--</option>
						<c:forEach begin="0" items="${requestScope.PeriodoArtHor}" var="vig">
							<option value="<c:out value="${vig}"/>" <c:if test="${vig==sessionScope.filtroEst.filPerVigenciaArt}">selected</c:if>><c:out value="${vig}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td><span class="style2">*</span><b>&nbsp;Componente:</b></td>
				<td>
					<select name="filComponenteArt" style='width:150px;'>
						<option value="-99">--Seleccione una--</option>
						<c:forEach begin="0" items="${requestScope.comArt}" var="comp">
							<option value="<c:out value="${comp.codigo}"/>" <c:if test="${comp.codigo==sessionScope.filtroEst.filComponenteArt}">selected</c:if>><c:out value="${comp.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
				<td><span class="style2">*</span><b>&nbsp;Especialidad:</b></td>
				<td>
					<select name="filEspecialidad" style='width:250px;' onchange="ajaxEstudiante()">
						<option value="-99">--Seleccione una--</option>
						<c:forEach begin="0" items="${requestScope.lEspecialidadVO}" var="esp">
							<option value="<c:out value="${esp.codigo}"/>" <c:if test="${esp.codigo==sessionScope.filtroEst.filEspecialidad}">selected</c:if>><c:out value="${esp.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>	
			<tr>	
				<td><b>&nbsp;Estudiante:</b></td>
				<td colspan="3">
					<select name="filEstudiante" style='width:230px;'>
						<option value="-99">--Seleccione una--</option>
						<c:forEach begin="0" items="${requestScope.lEstudianteVO}" var="est">
							<option value="<c:out value="${est.codigo}"/>" <c:if test="${est.codigo==sessionScope.filtroEst.filEstudiante}">selected</c:if>><c:out value="${est.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr style="display:none"><td><iframe name="frameAjax" id="frameAjax"></iframe></td></tr>
		</table>
</form>
<form method="post" name="frmAjax" action="<c:url value="/articulacion/repHorariosArt/ajaxRepArt.do"/>"><input type="hidden" name="ajax"><input type="hidden" name="ajax"><input type="hidden" name="ajax"><input type="hidden" name="ajax"><INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_HORARIO_ESTUDIANTE}"/>'><input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_AJAX}"/>'></form>