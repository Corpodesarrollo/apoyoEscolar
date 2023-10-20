<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
  <jsp:useBean id="nuevoConflicto" class="siges.conflicto.beans.ConflictoEscolar" scope="session"/><jsp:setProperty name="nuevoConflicto" property="*"/>
  <jsp:useBean id="nuevoTipo" class="siges.conflicto.beans.TipoConflicto" scope="session"/><jsp:setProperty name="nuevoTipo" property="*"/>
  <jsp:useBean id="nuevoResolucion" class="siges.conflicto.beans.ResolucionConflictos" scope="session"/><jsp:setProperty name="nuevoResolucion" property="*"/>
  <jsp:useBean id="nuevoMedidas" class="siges.conflicto.beans.MedidasInst" scope="session"/><jsp:setProperty name="nuevoMedidas" property="*"/>
  <jsp:useBean id="nuevoProyectos" class="siges.conflicto.beans.Proyectos" scope="session"/><jsp:setProperty name="nuevoProyectos" property="*"/>
  <jsp:useBean id="nuevoInfluencia" class="siges.conflicto.beans.InfluenciaConflictos" scope="session"/><jsp:setProperty name="nuevoInfluencia" property="*"/>
<%@include file="../parametros.jsp"%>
<%@ page import="siges.util.Recursos" %>
<%pageContext.setAttribute("filtroPeriodo",Recursos.recursoEstatico[Recursos.PERIODO]);%>
	<script languaje='javaScript'>
		<!--
		  var nav4=window.Event ? true : false;
		  
		  function borrar_combo(combo){
				for(var i = combo.length - 1; i >= 0; i--) {
					if(navigator.appName == "Netscape") combo.options[i] = null;
					else combo.remove(i);
				}
				combo.options[0] = new Option("-- Seleccione uno --","-1");
			}

			function Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre){
				this.id_Hijos= id_Hijos; this.Hijos= Hijos; this.Sel_Hijos= Sel_Hijos; this.id_Padre=id_Padre;
			}

			function filtro(combo_padre,combo_hijo){
				borrar_combo(combo_hijo);
				<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF}">var Padres = new Array();
				<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila" varStatus="st">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
				<c:forEach begin="0" items="${requestScope.filtroJornadaF}" var="fila2"><c:if test="${fila[0]==fila2[2]}">Sel_Hijos[k] = '<c:if test="${sessionScope.nuevoConflicto.cejornada== fila2[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k] = '<c:out value="${fila2[2]}"/>'; k++;</c:if></c:forEach>
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

		  function acepteNumeros(eve){
				var key=nav4?eve.which :eve.keyCode;
				return(key<=13 || (key>=48 && key<=57));
		  }
			
			function cancelar(){
				//if (confirm('¿DESEA CANCELAR LA INSERCIÓN DE UN NUEVO REGISTRO?')){
					document.frmNuevo.cmd.value="Cancelar";
					document.frmNuevo.submit();
				//}
			}
			
			function inhabilitarFormulario(){
				for(var i=0;i<document.frmNuevo.elements.length;i++){
					if(document.frmNuevo.elements[i].type != "hidden" && document.frmNuevo.elements[i].type != "button" && document.frmNuevo.elements[i].type != "submit")
					document.frmNuevo.elements[i].disabled=true;
				}	
			}
			
			function lanzar(tipo,cat){
				document.frmNuevo.tipo.value=tipo;
				document.frmNuevo.categoria.value=cat;
				if(tipo==0)	document.frmNuevo.action="<c:url value="/conflictoGrupo/ControllerNuevo.do"/>";
				document.frmNuevo.submit();
			}
			
			function guardar(tipo,cat){
				if(validar1(document.frmNuevo)){
					document.frmNuevo.tipo.value=tipo;
					document.frmNuevo.categoria.value=cat;
					document.frmNuevo.cmd.value="Guardar";
					document.frmNuevo.submit();
				}
			}
			
			function hacerValidaciones_frmNuevo(forma){				
				validarLista(forma.cesede, "- Sede",1)
				validarLista(forma.cejornada, "- Jornada",1)
				validarLista(forma.cemetodologia, "- Metodologia",1)
				validarLista(forma.ceperiodo, "- Periodo",1)
			}
			
			function buscar(tipo,cat){
				if(validarForma(document.frmNuevo)){
					document.frmNuevo.tipo.value=tipo;
					document.frmNuevo.categoria.value=cat;
					document.frmNuevo.cmd.value="Buscar";
					document.frmNuevo.submit();
				}
			}
		-->
	</script>
	<%@include file="../mensaje.jsp"%>
		<form method="post" name="frmNuevo" onSubmit=" return validarForma(frmNuevo)"  action='<c:url value="/conflicto/NuevoGuardar.jsp"/>'>
		<table border="1" align="center" bordercolor="#FFFFFF" width="100%">
			<tr>		
				<td width="45%" bgcolor="#FFFFFF">
				<input class='boton' name="cmd1" type="button" value="Buscar" onClick="buscar(60,-1)">
				</td>
			</tr>		
		</table>
	<!--//////////////////-->		
	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="tipo" value="">
	<input type="hidden" name="cmd" value="Cancelar"><INPUT TYPE="hidden" NAME="height" VALUE='150'>
	<input type="hidden" name="ext2" value=''>
	<input type="hidden" name="categoria" value=''>
	<!--<input type="hidden" name="ceperiodo" value='0'>-->
		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1">
				<td width="10" bgcolor="#FFFFFF">&nbsp;</td>			
				<td rowspan="2" width="600" bgcolor="#FFFFFF">
					<a href="javaScript:lanzar(10,-1)"><img src="../etc/img/tabs/tipos_conflicto_0.gif" alt="Tipos de Conflicto" border="0"  height="26"></a>
					<a href="javaScript:lanzar(20,2)"><img src="../etc/img/tabs/resolucion_conflictos_0.gif" alt="Formas comunes de resolución de conflictos" border="0"  height="26"></a>
					<!--<a href="javaScript:lanzar(30,3)"><img src="../etc/img/tabs/medidas_institucionales_0.gif" alt="Medidas Institucionales" border="0"  height="26"></a>-->
					<a href="javaScript:lanzar(40,4)"><img src="../etc/img/tabs/proyectos_0.gif" alt="Proyectos que apoyan la resolución de conflictos" border="0"  height="26"></a>
					<!--<a href="javaScript:lanzar(50,5)"><img src="../etc/img/tabs/influencia_conflictos_0.gif" alt="Como influyen los conflictos en los siguientes aspectos de la vida del colegio" border="0"  height="26"></a>-->
					<img src="../etc/img/tabs/consolidado_grupo_1.gif" alt="Consolidado por grupo" border="0"  height="26">
				</td>
			</tr>
		</table>
		<!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
		<table align="center" border="0">
			<tr>
				<td><span class="style2" >*</span>Sede:</td>
		    <td>
					<select name="cesede" onChange='filtro(document.frmNuevo.cesede, document.frmNuevo.cejornada)' style='width:300px;'>
						<option value='-1'>-- Seleccione uno --</option>
						<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila">
							<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.nuevoConflicto.cesede== fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option>
						</c:forEach>
					</select>
				</td>
				<td><span class="style2" >*</span>Jornada:</td>
			  <td>
					<select name="cejornada" style='width:150px;' onChange=''>
						<option value='-1'>-- Seleccione uno --</option>
					</select>							
			  </td>
			</tr>
		 <tr>
				<td><span class="style2">*</span>Metodologia:</td>
				<td>
					<select name="cemetodologia">
	          <option value="-1">--seleccione uno--</option>
	          <c:forEach begin="0" items="${filtroMetodologia}" var="fila">
	      	    <option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.nuevoConflicto.cemetodologia==fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/>
						</c:forEach>
	        </select>
	       </td>
				<td><span class="style2">*</span>Periodo:</td>
				<td>
					<select name="ceperiodo">
	          <option value="-1">--seleccione uno--</option>
	          <c:forEach begin="0" items="${filtroPeriodo}" var="fila">
	      	    <option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.nuevoConflicto.ceperiodo== fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/>
						</c:forEach>
	        </select>
	       </td>
		</tr>
	</table>
		
	<c:if test="${(sessionScope.nuevoConflicto.ceperiodo!='-1') && (sessionScope.nuevoConflicto.cesede!='-1') && (sessionScope.nuevoConflicto.cejornada!='-1')}">
		<c:forEach begin="0" items="${requestScope.filtroClase}" var="fila">
			<TABLE align="center" width="95%" cellpadding="0" cellSpacing="0" border="0">
			<caption><c:out value="${fila[1]}"/></caption>
				<tr>
					<th>Grados</th>
					<c:forEach begin="0" items="${requestScope.filtroTipo}" var="fila2">
						<c:if test="${fila[0]==fila2[3]}">
							<th width="" align="center"><c:out value="${fila2[1]}"/></th>
						</c:if>
					</c:forEach>
				</tr>
				<c:forEach begin="0" items="${requestScope.filtroGradoF2}" var="fila3">
					<tr>
						<td title=""><c:out value="${fila3[1]}"/></td>
						<c:forEach begin="0" items="${requestScope.filtroTipo}" var="fila2">
							<c:if test="${fila[0]==fila2[3]}">
								<td id="uno" name="uno" colspan="" align="center" style="display:"><input title="Número de alumnos | Número de eventos" type="text" style="background-color=#FfFfFf;text-align:center;" name="c<c:out value="${fila2[0]}"/>g<c:out value="${fila3[0]}"/>" maxlength="4" size="4" readonly/></td>
							</c:if>
						</c:forEach>
					</tr>
				</c:forEach>
				<tr><td colspan="20"><hr/></td></tr>
			</table>
		</c:forEach>
  </c:if>
<!--//////////////////////////////-->
</form>
<script>
	filtro(document.frmNuevo.cesede, document.frmNuevo.cejornada)
	<c:if test="${(sessionScope.nuevoConflicto.ceperiodo!='-1') && (sessionScope.nuevoConflicto.cesede!='-1') && (sessionScope.nuevoConflicto.cejornada!='-1')}">
		<c:forEach begin="0" items="${requestScope.lista}" var="fila">
			if(document.frmNuevo.<c:out value="${fila[0]}"/>){
				document.frmNuevo.<c:out value="${fila[0]}"/>.value='<c:out value="${fila[1]}"/>'+" | "+'<c:out value="${fila[2]}"/>';
			}
		</c:forEach>
	</c:if>
</script>