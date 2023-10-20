<%@ page language="java" errorPage=""%>
<%@include file="../parametros.jsp"%>
<jsp:useBean id="espXGradoVO" class="siges.rotacion.beans.EspXGradoVO" scope="session"/>
<jsp:useBean id="paramsVO" class="siges.rotacion.beans.ParamsVO" scope="page"/>
<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor='#ffffff'>
	<tr><td width="100%" valign='top'>
		<div style="width:100%;height:250px;overflow:auto;vertical-align:top;background-color:#ffffff;"><c:import url="rotacion/ControllerFiltro.do?tipo=${paramsVO.FICHA_ESPACIO_GRADO}"/></div>
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
								<c:if test="${sede.codigo==jornada.sede}"> Sel_Hijos[k] = '<c:if test="${sessionScope.espXGradoVO.espGraJornada==jornada.codigo}">SELECTED</c:if>'; id_Hijos[k] = '<c:out value="${jornada.codigo}"/>';  Hijos[k] = '<c:out value="${jornada.nombre}"/>'; id_Padre[k] = '<c:out value="${jornada.sede}"/>'; k++;</c:if>
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

		function ajaxMetodologia(){
			document.frmNuevo.espGraMetodologia.selectedIndex=0; 
		}
		
		function ajaxEspacio(){
			borrar_combo(document.frmNuevo.espGraEspacio); 
			document.frmAjax.ajax[0].value=document.frmNuevo.espGraInstitucion.value;
			document.frmAjax.ajax[1].value=document.frmNuevo.espGraSede.options[document.frmNuevo.espGraSede.selectedIndex].value;
			document.frmAjax.ajax[2].value=document.frmNuevo.espGraJornada.options[document.frmNuevo.espGraJornada.selectedIndex].value;
			document.frmAjax.ajax[3].value=document.frmNuevo.espGraMetodologia.value;
			document.frmAjax.cmd.value='<c:out value="${paramsVO.CMD_AJAX_ESPACIO_GRADO_ESP}"/>';
	 		document.frmAjax.target="frameAjax";
			document.frmAjax.submit();
		}
		
		function filtroGrado(combo){
			<c:forEach begin="0" items="${sessionScope.espXGradoVO.espGraGrado}" var="grado">
					if(combo.length){
						for(var i=0;i<combo.length;i++){
							if(combo.options[i].value==<c:out value="${grado}"/>){
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
			validarLista(forma.espGraSede, "- Sede", 1)
			validarLista(forma.espGraJornada, "- Jornada", 1)
			validarLista(forma.espGraEspacio, "- Espacio físico", 1)
			validarLista(forma.espGraGrado, "- Grado", 0)
		}
		
</script>
		<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/rotacion/NuevoGuardar.jsp"/>'>
			<input type="hidden" name="cmd" value="">
			<INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_ESPACIO_GRADO}"/>'>
			<input type="hidden" name="espGraInstitucion" value='<c:out value="${sessionScope.login.instId}"/>'>
			<input type="hidden" name="espGraVigencia" value='<c:out value="${sessionScope.filtroEspXGradoVO.filVigencia}"/>'>
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
					<caption>FIJAR ESPACIO POR GRADO</caption>
					<tr>
						<td><span class="style2" >*</span>Sede:</td>
				    <td>
							<select name="espGraSede" onChange='filtroJornada(document.frmNuevo.espGraSede, document.frmNuevo.espGraJornada,document.frmNuevo.espGraEspacio)' style='width:220px;' <c:if test="${sessionScope.espXGradoVO.formaEstado==1}">disabled</c:if>>
								<option value='-99'>-- Seleccione uno --</option>
								<c:forEach begin="0" items="${requestScope.lSedeVO}" var="sede">
									<option value="<c:out value="${sede.codigo}"/>" <c:if test="${sessionScope.espXGradoVO.espGraSede == sede.codigo}">SELECTED</c:if>><c:out value="${sede.nombre}"/></option></c:forEach>
							</select>
				   </td>
						<td><span class="style2" >*</span>Jornada:</td>
					  <td>
							<select name="espGraJornada" style='width:150px;' onChange='ajaxMetodologia()' <c:if test="${sessionScope.espXGradoVO.formaEstado==1}">disabled</c:if>>
								<option value='-99'>-- Seleccione uno --</option>
							</select>
					  </td>
				</tr>
				<tr>
						<td><span class="style2" >*</span>Metodologia:</td>
					  <td>
							<select name="espGraMetodologia" style='width:100px;' onChange='ajaxEspacio()' <c:if test="${sessionScope.espXGradoVO.formaEstado==1}">disabled</c:if>>
								<option value='-99'>-- Seleccione uno --</option>
								<c:forEach begin="0" items="${requestScope.lMetodologiaVO}" var="metod">
								<option value="<c:out value="${metod.codigo}"/>" <c:if test="${sessionScope.espXGradoVO.espGraMetodologia==metod.codigo}">SELECTED</c:if>><c:out value="${metod.nombre}"/></option></c:forEach>
							</select>
					  </td>
						<td><span class="style2" >*</span>Espacio:</td>
					  <td>
							<select name="espGraEspacio" style='width:150px;' <c:if test="${sessionScope.espXGradoVO.formaEstado==1}">disabled</c:if>>
								<option value='-99'>-- Seleccione uno --</option>
								<c:forEach begin="0" items="${requestScope.ajaxEspacio}" var="espacio">
								<option value="<c:out value="${espacio.codigo}"/>" <c:if test="${sessionScope.espXGradoVO.espGraEspacio == espacio.codigo}">SELECTED</c:if>><c:out value="${espacio.nombre}"/></option></c:forEach>
							</select>
					  </td>
				</tr>
				<tr>
						<td><span class="style2" >*</span>Grado:</td>
					  <td>
							<select name="espGraGrado" style='width:120px;' multiple="multiple" size="5">
								<c:forEach begin="0" items="${requestScope.ajaxGrado}" var="grado">
									<option value="<c:out value="${grado.codigo}"/>"><c:out value="${grado.nombre}"/></option></c:forEach>
							</select>
					  </td>
						<td><span class="style2">*</span>Vigencia:</td>
						<td><c:out value="${sessionScope.filtroEspXGradoVO.filVigencia}"/></td>
				</tr>
				<tr><td style="display:none"><iframe name="frameAjax" id="frameAjax"></iframe></td></tr>
  	  </table>
 	  </form>
		<form method="post" name="frmAjax" action="<c:url value="/rotacion/Ajax.do"/>">
			<input type="hidden" name="ajax"><input type="hidden" name="ajax"><input type="hidden" name="ajax">
			<input type="hidden" name="ajax"><input type="hidden" name="ajax"><input type="hidden" name="ajax">
			<input type="hidden" name="ajax"><input type="hidden" name="ajax">
			<INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_ESPACIO_GRADO}"/>'>
			<input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_AJAX}"/>'>
		</form>
		<script>
			filtroJornada(document.frmNuevo.espGraSede, document.frmNuevo.espGraJornada,document.frmNuevo.espGraJornada);
			filtroGrado(document.frmNuevo.espGraGrado);
		</script>
		