<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ page import="siges.util.Recursos" %>
<jsp:useBean id="liderVO" class="siges.institucion.organizacion.beans.LiderVO" scope="session" />
<jsp:useBean id="organizacionParams" class="siges.institucion.organizacion.beans.Params" scope="page"/>
<%pageContext.setAttribute("filtroDocumento",Recursos.recurso[Recursos.TIPODOCUMENTO]);
pageContext.setAttribute("filtroTipoCargo",Recursos.recurso[Recursos.TIPOCARGO]);%>
<%@include file="../../parametros.jsp"%>
<html>
<head><title>Registro/Edición de lider</title></head>
		<c:if test="${sessionScope.NivelPermiso==1}"><c:set var="permisoBoton" value="none"/></c:if>
		<script languaje='javaScript'>			
			<!--
		  var nav4=window.Event ? true : false;
		  function acepteNumeros(eve){
			var key=nav4?eve.which :eve.keyCode;
			return(key<=13 || (key>=48 && key<=57));
		  }
			
			function cancelar(){
				parent.close();
			}
			
  		function hacerValidaciones_frmNuevo(forma){
				validarLista(forma.lidSede, "- Sede",1)
				validarLista(forma.lidJor, "- Jornada",1)
				validarLista(forma.lidGrado, "- Grado",1)
				validarLista(forma.lidCodEstud, "- Estudiantes",1)
				if(forma.lidSedeJornada.length)
				validarSeleccion(forma.lidSedeJornada, "- Lider por")
				validarCorreoOpcional(forma.lidCorreo, "- Correo electrónico")
			}
			
			function inhabilitarFormulario(){
				for(var i=0;i<document.frmNuevo.elements.length;i++){
					if(document.frmNuevo.elements[i].type != "hidden" && document.frmNuevo.elements[i].type != "button" && document.frmNuevo.elements[i].type != "submit")
					document.frmNuevo.elements[i].disabled=true;
				}
			}
			
			function guardar(tipo){
				if(validarForma(document.frmNuevo)){
					document.frmNuevo.target='centro';
					document.frmNuevo.tipo2.value=tipo;
					document.frmNuevo.cmd.value="Guardar";
					document.frmNuevo.submit();
					parent.close();
				}
			}
			
			function cargaEstudiantesGrado(forma){
				document.frmAux1.sede.value=forma.lidSede.options[forma.lidSede.selectedIndex].value;
				document.frmAux1.jornada.value=forma.lidJor.options[forma.lidJor.selectedIndex].value;
				document.frmAux1.metod.value=forma.lidMet.value;
				document.frmAux1.grado.value=forma.lidGrado.options[forma.lidGrado.selectedIndex].value;
				document.frmAux1.combo.value="1";
				document.frmAux1.target="frame1";
				document.frmAux1.submit();
			}
			
			function cargaEstudiantesGrupo(forma){
				document.frmAux1.sede.value=forma.lidSede.options[forma.lidSede.selectedIndex].value;
				document.frmAux1.jornada.value=forma.lidJor.options[forma.lidJor.selectedIndex].value;
				document.frmAux1.metod.value=forma.lidMet.value;
				document.frmAux1.grado.value=forma.lidGrado.options[forma.lidGrado.selectedIndex].value;
				document.frmAux1.grupo.value=forma.lidGrupo.options[forma.lidGrupo.selectedIndex].value;
				document.frmAux1.combo.value="2";
				document.frmAux1.target="frame1";
				document.frmAux1.submit();
			}
			
			function setEstudiante(forma,combo){
				if(combo.length){
					for(var i=0;i<combo.length;i++) {
						if(combo.options[i].value=='<c:out value="${sessionScope.liderVO.lidCodEstud}"/>'){
							combo.options[i].selected=true;
						}
					}
				}
			}
			
			function Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre){
				this.id_Hijos= id_Hijos; this.Hijos= Hijos; this.Sel_Hijos= Sel_Hijos; this.id_Padre=id_Padre;
			}
			function borrar_combo(combo){
				for(var i = combo.length - 1; i >= 0; i--) {
					if(navigator.appName == "Netscape") combo.options[i] = null;
					else combo.remove(i);
				}
				combo.options[0] = new Option("-- Seleccione uno --","-9");
			}
			
			function filtro(combo_padre,combo_hijo,combo_hijo2,combo_hijo3,combo_hijo4){
					borrar_combo(combo_hijo); borrar_combo(combo_hijo2); borrar_combo(combo_hijo3);
					<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF}">var Padres = new Array();
						<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila" varStatus="st">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
							<c:forEach begin="0" items="${requestScope.filtroJornadaF}" var="fila2"><c:if test="${fila[0]==fila2[2]}">Sel_Hijos[k] = '<c:if test="${sessionScope.liderVO.lidJor==fila2[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k] = '<c:out value="${fila2[2]}"/>'; k++;</c:if></c:forEach>
							Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
						var niv=combo_padre.options[combo_padre.selectedIndex].value;
						var val_padre = -9;
						for(var k=0;k<Padres.length;k++){
							if(Padres[k].id_Padre[0]==niv) val_padre=k;							
						}
						if(val_padre!=-9){ var no_hijos = Padres[val_padre].Hijos.length;
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
						<c:forEach begin="0" items="${requestScope.filtroGradoF2}" var="fila3"><c:if test="${fila2[2]==fila3[3] && fila2[0]==fila3[4] && fila3[5]==fila4[0]}">Sel_Hijos[k] = '<c:if test="${sessionScope.liderVO.lidGrado== fila3[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila3[0]}"/>'; Hijos[k] = '<c:out value="${fila3[1]}"/>'; id_Padre[k] = '<c:out value="${fila3[3]}"/>|<c:out value="${fila3[4]}"/>|<c:out value="${fila3[5]}"/>'; k++;</c:if></c:forEach>
						Padres[id++] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach></c:forEach>
						var niv=combo_padre.options[combo_padre.selectedIndex].value+'|'+combo_padre2.options[combo_padre2.selectedIndex].value+'|'+combo_padre3.value;
						var val_padre = -9;
						for(var k=0;k<Padres.length;k++){
							if(Padres[k].id_Padre[0]==niv) val_padre=k;
						}
						if(val_padre!=-9){ var no_hijos = Padres[val_padre].Hijos.length;
							for(i=0; i < no_hijos; i++){
								if(Padres[val_padre].Sel_Hijos[i] == "SELECTED"){
									combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED"); combo_hijo.selectedIndex = i+1;
								}else
									combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
							}
						}
				</c:if>}
				
			function filtro3(combo_padre,combo_padre2,combo_padre3,combo_hijo){
					borrar_combo(combo_hijo);
					<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF && !empty requestScope.filtroGradoF2 && !empty requestScope.filtroGrupoF}">var Padres = new Array();
						<c:forEach begin="0" items="${requestScope.filtroGradoF2}" var="fila2"  varStatus="st">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
							<c:forEach begin="0" items="${requestScope.filtroGrupoF}" var="fila3"><c:if test="${fila2[0]==fila3[4] && fila2[3]==fila3[2] && fila2[4]==fila3[3] }">Sel_Hijos[k] = '<c:if test="${sessionScope.liderVO.lidGrupo== fila3[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila3[0]}"/>'; Hijos[k] = '<c:out value="${fila3[1]}"/>'; id_Padre[k] = '<c:out value="${fila3[2]}"/>|<c:out value="${fila3[3]}"/>|<c:out value="${fila3[4]}"/>'; k++;</c:if></c:forEach>
						Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
						var niv=combo_padre.options[combo_padre.selectedIndex].value+'|'+combo_padre2.options[combo_padre2.selectedIndex].value+'|'+combo_padre3.options[combo_padre3.selectedIndex].value;
						var val_padre = -9;
						for(var k=0;k<Padres.length;k++){
							if(Padres[k].id_Padre[0]==niv) val_padre=k;
						}
						if(val_padre!=-9){ var no_hijos = Padres[val_padre].Hijos.length;
							for(i=0; i < no_hijos; i++){
								if(Padres[val_padre].Sel_Hijos[i] == "SELECTED"){
									combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED"); combo_hijo.selectedIndex = i+1;
								}else
									combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
							}
						}
				</c:if>}
			
	//	-->
	</script>
	<%@include file="../../mensaje.jsp"%>
	<font size="1">
	<form method="post" name="frmNuevo" onSubmit=" return validarForma(frmNuevo)"  action='<c:url value="/institucion/organizacion/OrganizacionGuardar.jsp"/>'>
		<input type="hidden" name="tipo2"	value='<c:out value="${pageScope.organizacionParams.FICHA_LID2}"/>'> 
		<input type="hidden" name="cmd" value="Cancelar">
		<input type="hidden" name="ext2" value='<c:out value="${sessionScope.subframe2}"/>'>
		<input type="hidden" name="lidInst" value='<c:out value="${sessionScope.login.instId}"/>'>
		<input type="hidden" name="lidMet" value='<c:out value="${sessionScope.login.metodologiaId}"/>'>
		<input type="hidden" name="lidCargo" value='<c:out value="${requestScope.tipoLider}"/>'>
		<table border="0" align="center" bordercolor="#FFFFFF" width="100%">
		<caption>AGREGAR / EDITAR LIDER</caption>
			<tr>		
			 <td width="45%">
	       <input name="cmd1" type="button" class='boton' value="Guardar" onClick="guardar(document.frmNuevo.tipo2.value)"  style='display:<c:out value="${permisoBoton}"/>'>
	       <input name="cmd1" type="button" class='boton' value="Cancelar" onClick="cancelar()">
			 </td>
			</tr>		
		</table>
		<TABLE width="100%" cellpadding="3" cellSpacing="0">
				<c:if test="${requestScope.tipoLider==pageScope.organizacionParams.PARAM_CONTRALOR || requestScope.tipoLider==pageScope.organizacionParams.PARAM_PERSONERO  || requestScope.tipoLider==pageScope.organizacionParams.PARAM_PRESIDENTE}">
				<tr>
					<td><span class="style2">*</span> Lider por:</td>
					<td colspan="3">
						Sede&nbsp;&nbsp;<input type="radio" name="lidSedeJornada" value='<c:out value="${pageScope.organizacionParams.PARAM_SEDE}"/>' <c:if test="${sessionScope.liderVO.lidSedeJornada==pageScope.organizacionParams.PARAM_SEDE}">CHECKED</c:if>>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						Sede y jornada&nbsp;&nbsp;<input type="radio" name="lidSedeJornada" value='<c:out value="${pageScope.organizacionParams.PARAM_SEDE_JORNADA}"/>' <c:if test="${sessionScope.liderVO.lidSedeJornada==pageScope.organizacionParams.PARAM_SEDE_JORNADA}">CHECKED</c:if>>
					</td>	
				</tr>
				</c:if>
				<c:if test="${requestScope.tipoLider==pageScope.organizacionParams.PARAM_CONSEJO}">
					<input type="hidden" name="lidSedeJornada" value='<c:out value="${pageScope.organizacionParams.PARAM_SEDE}"/>'>
				</c:if>
				<tr>
					<td><span class="style2">*</span> Sede:</td>
					<td colspan="3">
						<select name="lidSede" onChange='filtro(document.frmNuevo.lidSede, document.frmNuevo.lidJor,document.frmNuevo.lidGrado,document.frmNuevo.lidGrupo,document.frmNuevo.lidMet)' style='width:406px;'>
						<option value='-9'>-- Seleccione uno --</option>
						<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila">
						<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.liderVO.lidSede==fila[0]}">SELECTED</c:if>>
						<c:out value="${fila[1]}"/></option>
						</c:forEach>
						</select>											
					</td>	
				</tr>
				<tr>
					<td><span class="style2">*</span> Jornada:</td>
					<td>
						<select name="lidJor" style='width:120px;' onChange='filtro2(document.frmNuevo.lidSede,document.frmNuevo.lidJor,document.frmNuevo.lidMet,document.frmNuevo.lidGrado,document.frmNuevo.lidGrupo)'>
						<option value='-9'>-- Seleccione uno --</option>
						</select>							
					</td>
				<td><span class="style2">*</span> Grado:</td>
					<td>
            <select name="lidGrado" onChange='filtro3(document.frmNuevo.lidSede, document.frmNuevo.lidJor,document.frmNuevo.lidGrado,document.frmNuevo.lidGrupo);cargaEstudiantesGrado(document.frmNuevo);' style='width:120px;'>
            <option value='-9'>-- Seleccione uno --</option>
            </select>
					</td>	
				</tr>
				<tr>
					<td>Grupo:</td>
					<td>
						<select name="lidGrupo" style='width:120px;' onChange='cargaEstudiantesGrupo(document.frmNuevo);'>
							<option value='-9'>-- Seleccione uno --</option>
						</select>							
					</td>
					<td>Tel&eacute;fono:</td>
					<td><input type="text" name="lidTelefono"  maxlength="20" size="20" value='<c:out value="${sessionScope.liderVO.lidTelefono}"/>'></td>
				</tr>
				<tr>
					<td><span class="style2">*</span>Estudiante:</td>
					<td colspan='3'>
						<select name="lidCodEstud" style='width:350px;'>
							<option value='-9'>-- Seleccione uno --</option>
						</select>							
					</td>
				</tr>
				<tr>
					<td>Correo electrónico:</td>
					<td colspan='3'><input type="text" name="lidCorreo"  maxlength="50" size="70" value='<c:out value="${sessionScope.liderVO.lidCorreo}"/>'></td>
				</tr>
				<tr>
					<td style="display:none">
						<iframe name="frame1" id="frame1"></iframe>
					</td>
				</tr>
		</table>
		</form>
	<form method="post" name="frmAux1" action='<c:url value="/institucion/organizacion/ControllerOrganizacionEdit.do"/>'>
		<input type="hidden" name="combo" value="">
		<input type="hidden" name="tipo2" value='<c:out value="${pageScope.organizacionParams.FILTRO_LID}"/>'>
		<input type="hidden" name="sede" value="">
		<input type="hidden" name="jornada" value="">
		<input type="hidden" name="metod" value="">
		<input type="hidden" name="grado" value="">
		<input type="hidden" name="grupo" value="">
		<input type="hidden" name="ext" value="1">
		<input type="hidden" name="forma" value="frmNuevo">
	</form>
<script><c:if test="${sessionScope.NivelPermiso==1}">inhabilitarFormulario();</c:if>
</script>
<script>
document.frmNuevo.lidSede.onchange();
filtro2(document.frmNuevo.lidSede, document.frmNuevo.lidJor,document.frmNuevo.lidMet,document.frmNuevo.lidGrado,document.frmNuevo.lidGrupo);
document.frmNuevo.lidGrado.onchange();
</script>