<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ page import="siges.util.Recursos" %>
<jsp:useBean id="consejoAcademicoVO" class="siges.institucion.organizacion.beans.ConsejoAcademicoVO" scope="session" />
<jsp:useBean id="organizacionParams" class="siges.institucion.organizacion.beans.Params" scope="page"/>
<%pageContext.setAttribute("filtroDocumento",Recursos.recurso[Recursos.TIPODOCUMENTO]);
pageContext.setAttribute("filtroTipoCargo",Recursos.recurso[Recursos.TIPOCARGO]);%>
<%@include file="../../parametros.jsp"%>
<html>
<head><title>Registro/Edición de consejo directivo</title></head>
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
				validarLista(forma.conAcaSede, "- Sede",1)
				validarLista(forma.conAcaJornada, "- Jornada",1)
				validarLista(forma.conAcaCargo, "- Cargo",1)
				validarLista(forma.conAcaNumDoc, "- Docente",1)
				validarCorreoOpcional(forma.conAcaCorreo, "- Correo electrónico", 1)
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
			function Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre){
				this.id_Hijos= id_Hijos; this.Hijos= Hijos; this.Sel_Hijos= Sel_Hijos; this.id_Padre=id_Padre;
			}
			function borrar_combo(combo){
				for(var i = combo.length - 1; i >= 0; i--) {
					if(navigator.appName == "Netscape") combo.options[i] = null;
					else combo.remove(i);
				}
				combo.options[0] = new Option("-- Seleccione uno --","-1");
			}
			
			function filtro(combo_padre,combo_hijo,combo_hijo2){
				borrar_combo(combo_hijo);borrar_combo(combo_hijo2);
					<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF}">
						var Padres = new Array();
						<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila" varStatus="st">
							id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
							<c:forEach begin="0" items="${requestScope.filtroJornadaF}" var="fila2">
								<c:if test="${fila[0]==fila2[2]}">Sel_Hijos[k] = '<c:if test="${sessionScope.consejoAcademicoVO.conAcaJornada==fila2[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k] = '<c:out value="${fila2[2]}"/>'; k++;</c:if>
							</c:forEach>
						Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
						var niv=combo_padre.options[combo_padre.selectedIndex].value;
						var val_padre = -1;
						for(var k=0;k<Padres.length;k++){
							if(Padres[k].id_Padre[0]==niv) val_padre=k;							
						}
						if(val_padre!=-1){ var no_hijos = Padres[val_padre].Hijos.length;
							for(i=0; i < no_hijos; i++){
								if(Padres[val_padre].Sel_Hijos[i] == "SELECTED"){
									combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED"); combo_hijo.selectedIndex = i+1;
								}else
									combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
							}
						}
					</c:if>
			}
	function filtro2(combo_padre,combo_padre2,combo_hijo4){
		borrar_combo(combo_hijo4);
		var id=0;
		<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF && !empty requestScope.filtroDocente}">
			var Padres = new Array();
			<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila" varStatus="st">
				<c:forEach begin="0" items="${requestScope.filtroJornadaF}" var="fila1">
				id_Hijos=new Array();
				Hijos = new Array();
				Sel_Hijos= new Array();
				id_Padre= new Array();
				var k=0;
					<c:forEach begin="0" items="${requestScope.filtroDocente}" var="fila2">
						<c:if test="${fila[0]==fila1[2] && fila[0]==fila2[2] && fila1[0]==fila2[3] && fila1[2]==fila2[2]}">
							Sel_Hijos[k] = '<c:if test="${sessionScope.consejoAcademicoVO.conAcaNumDoc==fila2[0]}">SELECTED</c:if>';
							id_Hijos[k] = '<c:out value="${fila2[0]}"/>';
							Hijos[k] = '<c:out value="${fila2[1]}"/>';
							id_Padre[k] = '<c:out value="${fila2[2]}"/>|<c:out value="${fila2[3]}"/>';
							k++;
						</c:if>
					</c:forEach>
					Padres[id++] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);
				</c:forEach>
			</c:forEach>
			var niv=combo_padre.options[combo_padre.selectedIndex].value+'|'+combo_padre2.options[combo_padre2.selectedIndex].value;
			var val_padre = -1;
			for(var k=0;k<Padres.length;k++){
				if(Padres[k].id_Padre[0]==niv) val_padre=k;
			}
			if(val_padre!=-1){
				var no_hijos = Padres[val_padre].Hijos.length;
				for(i=0; i < no_hijos; i++){
					if(Padres[val_padre].Sel_Hijos[i] == "SELECTED"){
						combo_hijo4.options[i+1] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED");
						combo_hijo4.selectedIndex = i+1;
					}else	combo_hijo4.options[i+1] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
				}
			}
		</c:if>
	}
	//	-->
	</script>
	<%@include file="../../mensaje.jsp"%>
	<font size="1">
	<form method="post" name="frmNuevo" onSubmit=" return validarForma(frmNuevo)"  action='<c:url value="/institucion/organizacion/OrganizacionGuardar.jsp"/>'>
		<input type="hidden" name="tipo2"	value='<c:out value="${pageScope.organizacionParams.FICHA_GOB4}"/>'> 
		<input type="hidden" name="cmd" value="Cancelar">
		<INPUT TYPE="hidden" NAME="height" VALUE='150'> 
		<input type="hidden" name="conAcaInst" value='<c:out value="${sessionScope.login.instId}"/>'>
		<input type="hidden" name="ext2" value='<c:out value="${sessionScope.subframe2}"/>'>
		<table border="0" align="center" bordercolor="#FFFFFF" width="100%">
			<caption>AGREGAR / EDITAR INTEGRANTE DEL CONSEJO ACADÉMICO</caption>
			<tr>		
			 <td width="45%">
	       <input name="cmd1" type="button" class='boton' value="Guardar" onClick="guardar(document.frmNuevo.tipo2.value)"  style='display:<c:out value="${permisoBoton}"/>'>
	       <input name="cmd1" type="button" class='boton' value="Cancelar" onClick="cancelar()">
			 </td>
			</tr>		
		</table>
<!--//////////////////-->		
		<TABLE width="100%" cellpadding="3" cellSpacing="0">
			<tr>
			<td width='20%'><span class="style2">*</span> Sede:</td>
			<td colspan="3">
				<select name="conAcaSede" style='width:400px;' onChange='filtro(document.frmNuevo.conAcaSede, document.frmNuevo.conAcaJornada,document.frmNuevo.conAcaNumDoc)' <c:if test="${sessionScope.nuevoGobierno.estado=='1'}">disabled</c:if>>
				<option value="-1">--seleccione una--</option>
				<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila">
				<option value='<c:out value="${fila[0]}"/>'  <c:if test="${sessionScope.consejoAcademicoVO.conAcaSede==fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option>
				</c:forEach>
				</select>
			</td>
			</tr>
			<tr>
				<td width='20%'><span class="style2">*</span> Jornada:</td>
				<td colspan="3">
				<select name="conAcaJornada" style='width:400px;' onChange='filtro2(document.frmNuevo.conAcaSede,document.frmNuevo.conAcaJornada,document.frmNuevo.conAcaNumDoc)' <c:if test="${sessionScope.nuevoGobierno.estado=='1'}">disabled</c:if>>
				<option value="-1">--seleccione una--</option>
				</select>
			</td>
			</tr>
			<tr>
			<td width='20%'><span class="style2">*</span> Docente:</td>
			<td colspan="3">
				<select name="conAcaNumDoc" style='width:400px;' <c:if test="${sessionScope.consejoAcademicoVO.conAcaNumDoc=='1'}">disabled</c:if>>
				<option value="-1">--seleccione una--</option>
				</select>
			</td>
			</tr>
			<tr>
				<td><span class="style2">*</span> Cargo:</td>
				<td colspan="3">
				<select name="conAcaCargo" style='width:300px;' >
                <option value="-1">--seleccione uno--</option>
                <c:forEach begin="0" items="${filtroTipoCargo}" var="fila">
                <option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.consejoAcademicoVO.conAcaCargo== fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option> </c:forEach>
                </select>
				</td>
			</tr>
			<tr>
				<td>Correo electrónico:</td>
				<td colspan='3'><input type="text" name="conAcaCorreo"  maxlength="100" size="40" value='<c:out value="${sessionScope.consejoAcademicoVO.conAcaCorreo}"/>'></td>
			</tr>
			<tr>
				<td>Tel&eacute;fono:</td>
				<td colspan='3'><input type="text" name="conAcaTelefono"  maxlength="20" size="20" value='<c:out value="${sessionScope.consejoAcademicoVO.conAcaTelefono}"/>'></td>
			</tr>
		</TABLE>
</form>
<script><c:if test="${sessionScope.NivelPermiso==1}">inhabilitarFormulario();</c:if>
document.frmNuevo.conAcaSede.onchange();
document.frmNuevo.conAcaJornada.onchange();
</script>