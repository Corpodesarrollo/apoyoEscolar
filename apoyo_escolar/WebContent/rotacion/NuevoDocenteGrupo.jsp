<%@ page language="java" errorPage=""%>
<%@include file="../parametros.jsp"%>
<jsp:useBean id="docXGrupoVO" class="siges.rotacion.beans.DocXGrupoVO" scope="session"/>
<jsp:useBean id="paramsVO" class="siges.rotacion.beans.ParamsVO" scope="page"/>
<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor='#ffffff'>
	<tr><td width="100%" valign='top'>
		<div style="width:100%;height:250px;overflow:auto;vertical-align:top;background-color:#ffffff;"><c:import url="rotacion/ControllerFiltro.do?tipo=${paramsVO.FICHA_DOCENTE_GRUPO_NUEVO}"/></div>
	</td></tr>
</table>
<%@include file="../mensaje.jsp" %>
<script>
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
			
			function borrar_combo2(combo){
				for(var i = combo.length - 1; i >= 0; i--) {
					if(navigator.appName == "Netscape") combo.options[i] = null;
					else combo.remove(i);
				}
			}
			
			function filtroJornada(combo_padre,combo_hijo,combo_hijo2){
					borrar_combo(combo_hijo); 
					borrar_combo(combo_hijo2); 
					<c:if test="${!empty requestScope.lSedeVO && !empty requestScope.lJornadaVO}">
						var Padres = new Array();
						<c:forEach begin="0" items="${requestScope.lSedeVO}" var="sede" varStatus="st">
							id_Hijos=new Array();  Hijos= new Array();  Sel_Hijos= new Array();  id_Padre= new Array();  var k=0;
							<c:forEach begin="0" items="${requestScope.lJornadaVO}" var="jornada">
								<c:if test="${sede.codigo==jornada.sede}"> Sel_Hijos[k] = '<c:if test="${sessionScope.docXGrupoVO.docGruJornada==jornada.codigo}">SELECTED</c:if>'; id_Hijos[k] = '<c:out value="${jornada.codigo}"/>';  Hijos[k] = '<c:out value="${jornada.nombre}"/>'; id_Padre[k] = '<c:out value="${jornada.sede}"/>'; k++;</c:if>
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
			borrar_combo(document.frmNuevo.docGruDocente); 
			document.frmAjax.ajax[0].value=document.frmNuevo.docGruInstitucion.value;
			document.frmAjax.ajax[1].value=document.frmNuevo.docGruSede.options[document.frmNuevo.docGruSede.selectedIndex].value;
			document.frmAjax.ajax[2].value=document.frmNuevo.docGruJornada.options[document.frmNuevo.docGruJornada.selectedIndex].value;
			document.frmAjax.cmd.value='<c:out value="${paramsVO.CMD_AJAX_DOCENTE_GRUPO_DOC}"/>';
	 		document.frmAjax.target="frameAjax";
			document.frmAjax.submit();
		}
		
		function ajaxMetodologia(){
			document.frmNuevo.docGruMetodologia.selectedIndex=0; 
		}
		
		function ajaxAsignatura(){
			borrar_combo(document.frmNuevo.docGruAsignatura); 
			document.frmAjax.ajax[0].value=document.frmNuevo.docGruInstitucion.value;
			document.frmAjax.ajax[1].value=document.frmNuevo.docGruSede.options[document.frmNuevo.docGruSede.selectedIndex].value;
			document.frmAjax.ajax[2].value=document.frmNuevo.docGruJornada.options[document.frmNuevo.docGruJornada.selectedIndex].value;
			document.frmAjax.ajax[3].value=document.frmNuevo.docGruMetodologia.value;
			document.frmAjax.ajax[4].value=document.frmNuevo.docGruDocente.options[document.frmNuevo.docGruDocente.selectedIndex].value;
			document.frmAjax.ajax[5].value=document.frmNuevo.docGruVigencia.value;
			document.frmAjax.cmd.value='<c:out value="${paramsVO.CMD_AJAX_DOCENTE_GRUPO_ASIG}"/>';
	 		document.frmAjax.target="frameAjax";
			document.frmAjax.submit();
		}
		function ajaxGrado(){
			borrar_combo(document.frmNuevo.docGruGrado); 
			borrar_combo2(document.frmNuevo.docGruGrupo); 
			document.frmAjax.ajax[0].value=document.frmNuevo.docGruInstitucion.value;
			document.frmAjax.ajax[1].value=document.frmNuevo.docGruSede.options[document.frmNuevo.docGruSede.selectedIndex].value;
			document.frmAjax.ajax[2].value=document.frmNuevo.docGruJornada.options[document.frmNuevo.docGruJornada.selectedIndex].value;
			document.frmAjax.ajax[3].value=document.frmNuevo.docGruMetodologia.value;
			document.frmAjax.ajax[4].value=document.frmNuevo.docGruDocente.options[document.frmNuevo.docGruDocente.selectedIndex].value;
			document.frmAjax.ajax[5].value=document.frmNuevo.docGruAsignatura.options[document.frmNuevo.docGruAsignatura.selectedIndex].value;
			document.frmAjax.cmd.value='<c:out value="${paramsVO.CMD_AJAX_DOCENTE_GRUPO_GRA}"/>';
	 		document.frmAjax.target="frameAjax";
			document.frmAjax.submit();
		}
		function ajaxGrupo(){
			borrar_combo2(document.frmNuevo.docGruGrupo); 
			document.frmAjax.ajax[0].value=document.frmNuevo.docGruInstitucion.value;
			document.frmAjax.ajax[1].value=document.frmNuevo.docGruSede.options[document.frmNuevo.docGruSede.selectedIndex].value;
			document.frmAjax.ajax[2].value=document.frmNuevo.docGruJornada.options[document.frmNuevo.docGruJornada.selectedIndex].value;
			document.frmAjax.ajax[3].value=document.frmNuevo.docGruMetodologia.value;
			document.frmAjax.ajax[4].value=document.frmNuevo.docGruDocente.options[document.frmNuevo.docGruDocente.selectedIndex].value;
			document.frmAjax.ajax[5].value=document.frmNuevo.docGruAsignatura.options[document.frmNuevo.docGruAsignatura.selectedIndex].value;
			document.frmAjax.ajax[6].value=document.frmNuevo.docGruGrado.options[document.frmNuevo.docGruGrado.selectedIndex].value;
			document.frmAjax.cmd.value='<c:out value="${paramsVO.CMD_AJAX_DOCENTE_GRUPO_GRU}"/>';
	 		document.frmAjax.target="frameAjax";
			document.frmAjax.submit();
		}
		
		function filtroGrupo(combo){
			<c:forEach begin="0" items="${sessionScope.docXGrupoVO.docGruGrupo}" var="grupo">
					if(combo.length){
						for(var i=0;i<combo.length;i++){
							if(combo.options[i].value==<c:out value="${grupo}"/>){
								combo.options[i].selected=true;
							}
						}
					}
			</c:forEach>
		}

		function guardar(){
			if(validarForma(document.frmNuevo)){
					document.frmNuevo.cmd.value='<c:out value="${paramsVO.CMD_GUARDAR}"/>';
					document.frmNuevo.submit();
			}
		}
		function nuevo(){
				document.frmNuevo.cmd.value='<c:out value="${paramsVO.CMD_NUEVO}"/>';
				document.frmNuevo.submit();
		}
		
		function hacerValidaciones_frmNuevo(forma){
			validarLista(forma.docGruSede, "- Sede", 1)
			validarLista(forma.docGruJornada, "- Jornada", 1)
			validarLista(forma.docGruMetodologia, "- Metodologia", 1)
			validarLista(forma.docGruDocente, "- Docente", 1)
			validarLista(forma.docGruAsignatura, "- Asignatura", 1)
			validarLista(forma.docGruGrado, "- Grado", 1)
			validarLista(forma.docGruGrupo, "- Grupo", 0)
		}
		
</script>
		<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/rotacion/NuevoGuardar.jsp"/>'>
			<input type="hidden" name="cmd" value="">
			<INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_DOCENTE_GRUPO_NUEVO}"/>'>
			<input type="hidden" name="docGruInstitucion" value='<c:out value="${sessionScope.login.instId}"/>'>
			<input type="hidden" name="docGruVigencia" value='<c:out value="${sessionScope.filtroDocXGrupoVO.filVigencia}"/>'>
			
			<table align="center" width="100%">
				<tr>
				  <td width="45%" align="left">
					  <c:if test="${sessionScope.isLockedRotacion==0}">
						  <input name="cmd2" class="boton" type="button" value="Guardar" onClick="guardar()">
						  <input name="cmd1" class="boton" type="button" value="Nuevo" onClick="nuevo()">
					  </c:if>
					  <c:if test="${sessionScope.isLockedRotacion==1}">
						  <span class="style2">Nota: Información de solo lectura. Proceso de rotación pendiente por aprobar o rechazar</span>
					  </c:if>
						</td>
				</tr>
  	  </table>
  	  <table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
					<caption>FIJAR DOCENTE POR GRUPO</caption>
					<tr>
						<td><span class="style2" >*</span>Sede:</td>
				    <td>
							<select name="docGruSede" onChange='filtroJornada(document.frmNuevo.docGruSede, document.frmNuevo.docGruJornada,document.frmNuevo.docGruDocente)' style='width:220px;' <c:if test="${sessionScope.docXGrupoVO.formaEstado==1}">disabled</c:if>>
								<option value='-99'>-- Seleccione uno --</option>
								<c:forEach begin="0" items="${requestScope.lSedeVO}" var="sede">
									<option value="<c:out value="${sede.codigo}"/>" <c:if test="${sessionScope.docXGrupoVO.docGruSede == sede.codigo}">SELECTED</c:if>><c:out value="${sede.nombre}"/></option></c:forEach>
							</select>
				   </td>
						<td><span class="style2" >*</span>Jornada:</td>
					  <td>
							<select name="docGruJornada" style='width:100px;' onChange='ajaxDocente()' <c:if test="${sessionScope.docXGrupoVO.formaEstado==1}">disabled</c:if>>
								<option value='-99'>-- Seleccione uno --</option>
							</select>
					  </td>
				</tr>
				<tr>
						<td><span class="style2" >*</span>Docente:</td>
					  <td>
							<select name="docGruDocente" style='width:220px;' onChange='ajaxMetodologia()' <c:if test="${sessionScope.docXGrupoVO.formaEstado==1}">disabled</c:if>>
								<option value='-99'>-- Seleccione uno --</option>
								<c:forEach begin="0" items="${requestScope.ajaxDocente}" var="docente">
									<option value="<c:out value="${docente.codigo}"/>" <c:if test="${sessionScope.docXGrupoVO.docGruDocente == docente.codigo}">SELECTED</c:if>><c:out value="${docente.nombre}"/></option></c:forEach>
							</select>
					  </td>
						<td><span class="style2" >*</span>Vigencia:&nbsp;&nbsp;<c:out value="${sessionScope.filtroDocXGrupoVO.filVigencia}"/></td>
						<td><span class="style2" >*</span>Metodologia:</td>
					  <td>
							<select name="docGruMetodologia" style='width:100px;' onChange='ajaxAsignatura()' <c:if test="${sessionScope.docXGrupoVO.formaEstado==1}">disabled</c:if>>
								<option value='-99'>-- Seleccione uno --</option>
								<c:forEach begin="0" items="${requestScope.lMetodologiaVO}" var="metod">
								<option value="<c:out value="${metod.codigo}"/>" <c:if test="${sessionScope.docXGrupoVO.docGruMetodologia==metod.codigo}">SELECTED</c:if>><c:out value="${metod.nombre}"/></option></c:forEach>
							</select>
					  </td>
				</tr>
				<tr>
						<td><span class="style2" >*</span>Asignatura:</td>
					  <td>
							<select name="docGruAsignatura" style='width:220px;' onChange='ajaxGrado()' <c:if test="${sessionScope.docXGrupoVO.formaEstado==1}">disabled</c:if>>
								<option value='-99'>-- Seleccione uno --</option>
								<c:forEach begin="0" items="${requestScope.ajaxAsignatura}" var="asignatura">
									<option value="<c:out value="${asignatura.codigo}"/>" <c:if test="${sessionScope.docXGrupoVO.docGruAsignatura == asignatura.codigo}">SELECTED</c:if>><c:out value="${asignatura.nombre}"/></option></c:forEach>
							</select>
					  </td>
						<td><span class="style2" >*</span>Grado:</td>
					  <td>
							<select name="docGruGrado" style='width:100px;' onChange='ajaxGrupo()' <c:if test="${sessionScope.docXGrupoVO.formaEstado==1}">disabled</c:if>>
								<option value='-99'>-- Seleccione uno --</option>
								<c:forEach begin="0" items="${requestScope.ajaxGrado}" var="grado">
									<option value="<c:out value="${grado.codigo}"/>" <c:if test="${sessionScope.docXGrupoVO.docGruGrado == grado.codigo}">SELECTED</c:if>><c:out value="${grado.nombre}"/></option></c:forEach>
							</select>
					  </td>
					</tr>
					<tr>
						<td><span class="style2" >*</span>Grupo:</td>
					  <td colspan="3">
							<select name="docGruGrupo" style='width:100px;' multiple="multiple" size="8">
								<c:forEach begin="0" items="${requestScope.ajaxGrupo}" var="grupo">
									<option value="<c:out value="${grupo.codigo}"/>"><c:out value="${grupo.nombre}"/></option></c:forEach>
							</select>
					  </td>
					</tr>
			<tr><td style="display:none"><iframe name="frameAjax" id="frameAjax"></iframe></td></tr>
  	  </table>
 	  </form>
		<form method="post" name="frmAjax" action="<c:url value="/rotacion/Ajax.do"/>">
			<input type="hidden" name="ajax"><input type="hidden" name="ajax"><input type="hidden" name="ajax">
			<input type="hidden" name="ajax"><input type="hidden" name="ajax"><input type="hidden" name="ajax">
			<input type="hidden" name="ajax"><input type="hidden" name="ajax">
			<INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_DOCENTE_GRUPO_NUEVO}"/>'>
			<input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_AJAX}"/>'>
		</form>
		<script>
			filtroJornada(document.frmNuevo.docGruSede, document.frmNuevo.docGruJornada,document.frmNuevo.docGruJornada);
			filtroGrupo(document.frmNuevo.docGruGrupo);
		</script>		