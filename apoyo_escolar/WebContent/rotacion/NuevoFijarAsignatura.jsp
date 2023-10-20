<%@ page language="java" errorPage="" %>
<%@include file="../parametros.jsp"%>
<jsp:useBean id="rotacion" class="siges.rotacion.beans.Rotacion" scope="session"/>
<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor='#ffffff'>
	<tr><td width="100%" valign='top'>
		<div style="width:100%;height:250px;overflow:auto;vertical-align:top;background-color:#ffffff;"><c:import url="rotacion/ControllerFiltro.do?tipo=60"/></div>
	</td></tr>
</table>
<script languaje="javaScript">
<!--
	var locked=<c:out value="${sessionScope.isLockedRotacion}"/>;

	function llenarBlqminimo(combo2,combo){
		var n=parseInt(combo2.selectedIndex)-1;
		var max;
		var k=0;
		borrar_combo3(combo);
		if(n>=0){
			if(document.frmNuevo.numblq){
				if(document.frmNuevo.numblq.length){
					max=document.frmNuevo.numblq[n].value;
				}
				else{
					max=document.frmNuevo.numblq.value;
				}
			}
			var aux;
			for(var i=0;i<max;i++){
				<c:if test="${sessionScope.rotacion.estado==1}">
					aux='<c:out value="${sessionScope.rotacion.rotfasblqminimo}"/>';
				</c:if>
				<c:if test="${sessionScope.rotacion.estado!=1}">
					aux=-1;
				</c:if>
				if(parseInt(aux)==parseInt(i+1)){
					combo.options[k+1] = new Option(i+1,i+1,"SELECTED");
					combo.selectedIndex = k+1;
				}
				else{
					combo.options[k+1] = new Option(i+1,i+1);
				}
				k++;
			}
		}
	}

	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) {
			if(navigator.appName == "Netscape") combo.options[i] = null;
			else combo.remove(i);
		}
		combo.options[0] = new Option("-- Seleccione uno --","-1");
	}

	function borrar_combo3(combo){
		for(var i = combo.length - 1; i >= 0; i--) {
			if(navigator.appName == "Netscape") combo.options[i] = null;
			else combo.remove(i);
		}
		combo.options[0] = new Option("--","-1");
	}
	
	function borrar_combo2(combo){
		for(var i = combo.length - 1; i >= 0; i--) {
			if(navigator.appName == "Netscape") combo.options[i] = null;
			else combo.remove(i);
		}
	}

	var nav4=window.Event ? true : false;

	function cancelar(){
		location.href='<c:url value="/bienvenida.jsp"/>';
	}

	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}

	function nuevo(){
		document.frmNuevo.tipo.value=66;
		document.frmNuevo.cmd.value="Nuevo";
		document.frmNuevo.submit();
	}

	function guardar(){
		if(validarForma(document.frmNuevo)){
			document.frmNuevo.tipo.value=61;
			document.frmNuevo.cmd.value="Guardar";
			document.frmNuevo.submit();
		}	
	}

	function hacerValidaciones_frmNuevo(forma){
		validarLista(forma.rotfasestructura, "- Seleccione una estructura",1)
		validarLista(forma.rotfasasignatura, "- Seleccione una asignatura",1)
		validarLista(forma.rotfasblqminimo, "- Seleccione el bloque inicial minimo",1)
		validarLista(forma.rotfasgrados, "- Debe seleccionar al menos un grado",0)
	}

	function Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre){
		this.id_Hijos= id_Hijos; this.Hijos= Hijos; this.Sel_Hijos= Sel_Hijos; this.id_Padre=id_Padre;
	}

	function filtro(combo_padre,combo_hijo,combo_hijo4){
		var id=0;
		var vec=new Array();
		var au=0;
		var est,_est,est_,_gra,gra_,sed_,_sed,_jor,jor_;
		borrar_combo2(combo_hijo4); 
		<c:if test="${!empty requestScope.listaEstructura && !empty requestScope.listaEstructuraGrado && !empty requestScope.filtroGradoF2}">
			var Padres = new Array();
			<c:forEach begin="0" items="${requestScope.listaEstructura}" var="fila1" varStatus="st">
				id_Hijos=new Array();
				Hijos= new Array();
				Sel_Hijos= new Array();
				id_Padre= new Array();
				var k=0;
				<c:forEach begin="0" items="${requestScope.listaEstructuraGrado}" var="fila2" varStatus="st">
					<c:forEach begin="0" items="${requestScope.filtroGradoF2}" var="fila3">
						<c:if test="${fila1.estCodigo==fila2.estCodigo && fila1.estSede==fila3[3] && fila1.estJornada==fila3[4] && fila2.estGrado==fila3[0]}">
							Sel_Hijos[k] = '<c:forEach begin="0" items="${sessionScope.rotacion.rotfasgrados}" var="filax"><c:if test="${filax == fila2.estGrado}">SELECTED</c:if></c:forEach>';
							id_Hijos[k] = '<c:out value="${fila3[0]}"/>';
							Hijos[k] = '<c:out value="${fila3[1]}"/>';
							id_Padre[k] = '<c:out value="${fila1.estCodigo}"/>';
							k++;
						</c:if>
					</c:forEach>
				</c:forEach>
				Padres[id++] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);
			</c:forEach>
			var niv=combo_padre.options[combo_padre.selectedIndex].value;
			var val_padre = -1;
			for(var k=0;k<Padres.length;k++){
				if(Padres[k].id_Padre[0]==niv) val_padre=k;
			}
			if(val_padre!=-1){
				var no_hijos = Padres[val_padre].Hijos.length;
				for(i=0; i < no_hijos; i++){
					if(Padres[val_padre].Sel_Hijos[i] == "SELECTED"){
						vec[au]=i;au+=1;
						combo_hijo4.options[i] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED");
						combo_hijo4.selectedIndex = i;
					}else	combo_hijo4.options[i] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
				}
			}
			if(combo_hijo4.length && vec.length){
				for(var j=0;j<combo_hijo4.length;j++){
					for(var k=0;k<vec.length;k++){
						if(j==vec[k]){
							combo_hijo4.options[j].selected=true;
						}
					}
				}
			}
		</c:if>
	}

//-->
</script>
<%@include file="../mensaje.jsp" %>
		<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action="<c:url value="/rotacion/NuevoGuardar.jsp"/>">
			<input type="hidden" name="cmd" value="">
			<INPUT TYPE="hidden" NAME="height" VALUE="180">
			<INPUT TYPE="hidden" NAME="tipo" VALUE="">
			<input type="hidden" name="ext" value="">
			<input type="hidden" name="rotstrmetodologia" value='<c:out value="${sessionScope.filtroRotacion.filMetodologia}"/>'>
			<input type="hidden" name="vigencia" value='<c:out value="${sessionScope.filtroRotacion.filAnhoVigencia}"/>'>
			<table align="center" width="100%">
				<tr>
				  <td width="45%" align="left">
					<script>
					if(locked==0){
					  document.write('<input name="cmd2" class="boton" type="button" value="Guardar" onClick="guardar()">&nbsp;');
					  document.write('<input name="cmd1" class="boton" type="button" value="Nuevo" onClick="nuevo()">');
					}
					</script>
					  <c:if test="${sessionScope.isLockedRotacion==1}">
						  <span class="style2">Nota: Información de solo lectura. Proceso de rotación pendiente por aprobar o rechazar</span>
					  </c:if>
					</td>
				</tr>
  	  </table>

  	  <table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
				<caption>Fijar Asignaturas</caption>
				<tr>
					<td><span class="style2" >*</span>Estructura:</td>
			    <td colspan="2">
						<select name="rotfasestructura" onChange='filtro(document.frmNuevo.rotfasestructura,document.frmNuevo.rotstrmetodologia,document.frmNuevo.rotfasgrados);llenarBlqminimo(document.frmNuevo.rotfasestructura,document.frmNuevo.rotfasblqminimo)' style='width:200px;'>
							<option value='-1'>-- Seleccione uno --</option>
							<c:forEach begin="0" items="${requestScope.listaEstructura}" var="fila">
								<option value="<c:out value="${fila.estCodigo}"/>" <c:if test="${sessionScope.rotacion.rotfasestructura == fila.estCodigo}">SELECTED</c:if>><c:out value="${fila.estNombre}"/></option>
							</c:forEach>
						</select>
						<c:forEach begin="0" items="${requestScope.listaEstructura}" var="fila">
							<input type="hidden" name="numblq" value="<c:out value="${fila.estBloqueXJornada}"/>">
						</c:forEach>
			   </td>
			    <td><span class="style2">*</span>Bloque inicial m&iacute;nimo:</td>
					<td><select name="rotfasblqminimo"><option value='-1'>--</option></select></td>
				</tr>
				<tr valign="top">
					<td><span class="style2" >*</span>Asignatura:</td>
			    <td colspan="2">
						<select name="rotfasasignatura" onChange='' style='width:200px;'>
							<option value='-1'>-- Seleccione uno --</option>
							<c:forEach begin="0" items="${requestScope.filtroAsignatura}" var="fila">
								<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.rotacion.rotfasasignatura == fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option>
							</c:forEach>
						</select>
			    </td>
					<td rowspan="2"><span class="style2" >*</span>Grado:</td>
				  <td rowspan="2">
	          <select name="rotfasgrados" style='width:150px;' multiple size='7'></select>
					</td>
				</tr>
				<tr valign="top">
					<td><span class="style2" >*</span>Vigencia:</td>
					<td><c:out value="${sessionScope.filtroRotacion.filAnhoVigencia}"/></td>
				</tr>
  	  </table>
 	  </form>
<script>
<c:if test="${sessionScope.rotacion.estado==1}">
  filtro(document.frmNuevo.rotfasestructura,document.frmNuevo.rotstrmetodologia,document.frmNuevo.rotfasgrados);llenarBlqminimo(document.frmNuevo.rotfasestructura,document.frmNuevo.rotfasblqminimo)
</c:if>
</script>