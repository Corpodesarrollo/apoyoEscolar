<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
 <jsp:useBean id="nuevoConflicto" class="siges.conflicto.beans.ConflictoEscolar" scope="session"/><jsp:setProperty name="nuevoConflicto" property="*"/>
 <jsp:useBean id="nuevoTipo" class="siges.conflicto.beans.TipoConflicto" scope="session"/>
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
		  
		  function hacerValidaciones_frmNuevo(forma){				
				validarLista(forma.cesede, "- Sede",1)
				validarLista(forma.cejornada, "- Jornada",1)
				validarLista(forma.ceperiodo, "- Periodo",1)
			}

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
			
			function validar1(forma){
				var val=0;var valL=0;
				<c:forEach begin="0" items="${requestScope.filtroTipo}" var="fila2">
					if(forma.k<c:out value="${fila2[0]}"/>){
						if(forma.k<c:out value="${fila2[0]}"/>.checked==true){
							var cont=0;var contL=0;
							if(forma.c<c:out value="${fila2[0]}"/>){
								for(var i=0;i<forma.c<c:out value="${fila2[0]}"/>.length;i++){
									if(forma.c<c:out value="${fila2[0]}"/>[i].checked==false){
										cont++;
									}
								}
								if(forma.c<c:out value="${fila2[0]}"/>.length==cont){
									val=1;
								}
							}
							
							if(forma.c_ent<c:out value="${fila2[0]}"/>){
								if(forma.c_ent<c:out value="${fila2[0]}"/>.selectedIndex==-9){
									contL++;
								}else{
									valL=1;
								}
							}
						}
					}
				</c:forEach>
				if(val==0)	return true;
				else if(val==1){
					alert("- Debe evaluar el tipo de conflicto que selecciono - ")
					return false;
				}

				
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
				if(tipo==0)	document.frmNuevo.action="<c:url value="/conflicto/ControllerNuevo.do"/>";
				document.frmNuevo.submit();
			}
			
			function guardar(tipo,cat){
				if(validarForma(document.frmNuevo)){
					if(validar1(document.frmNuevo)){
						ver();
						/*document.frmNuevo.cesednom.value=document.frmNuevo.cesede.options[document.frmNuevo.cesede.selectedIndex].text;
						document.frmNuevo.cejornom.value=document.frmNuevo.cejornada.options[document.frmNuevo.cejornada.selectedIndex].text;*/
						document.frmNuevo.cepernom.value=document.frmNuevo.ceperiodo.options[document.frmNuevo.ceperiodo.selectedIndex].text;
						document.frmNuevo.tipo.value=tipo;
						document.frmNuevo.categoria.value=cat;
						document.frmNuevo.cmd.value="Guardar";
						document.frmNuevo.submit();
					}
				}
			}
			
			function buscar(tipo,cat){
				if(validarForma(document.frmNuevo)){
					document.frmNuevo.cesednom.value=document.frmNuevo.cesede.options[document.frmNuevo.cesede.selectedIndex].text;
					document.frmNuevo.cejornom.value=document.frmNuevo.cejornada.options[document.frmNuevo.cejornada.selectedIndex].text;
					document.frmNuevo.cepernom.value=document.frmNuevo.ceperiodo.options[document.frmNuevo.ceperiodo.selectedIndex].text;
					document.frmNuevo.tipo.value=tipo;
					document.frmNuevo.categoria.value=cat;
					document.frmNuevo.cmd.value="Buscar";
					document.frmNuevo.submit();
				}
			}
			
			function ver(){
				<c:forEach begin="0" items="${requestScope.filtroTipo}" var="fila2">
					if(document.frmNuevo.k<c:out value="${fila2[0]}"/>.checked==true){
						document.frmNuevo.k<c:out value="${fila2[0]}"/>.value="2";
					}else if(document.frmNuevo.k<c:out value="${fila2[0]}"/>.checked==false){
						document.frmNuevo.k<c:out value="${fila2[0]}"/>.value="1";
					}
				</c:forEach>
			}
			
			function habilitar(num){
				<c:forEach begin="0" items="${requestScope.filtroTipo}" var="fila2">
					if(num=='c<c:out value="${fila2[0]}"/>'){
						for(var i=0;i<document.frmNuevo.c<c:out value="${fila2[0]}"/>.length;i++){
							if(document.frmNuevo.c<c:out value="${fila2[0]}"/>[i].disabled==true){
								document.frmNuevo.c<c:out value="${fila2[0]}"/>[i].disabled=false;
								document.frmNuevo.c_ent<c:out value="${fila2[0]}"/>.disabled=false;
							}else if(document.frmNuevo.c<c:out value="${fila2[0]}"/>[i].disabled==false){
								document.frmNuevo.c<c:out value="${fila2[0]}"/>[i].disabled=true;
								document.frmNuevo.c_ent<c:out value="${fila2[0]}"/>.disabled=true;
							}
						}
					}
					
				</c:forEach>
			}
			-->
</script>
	<%@include file="../mensaje.jsp"%>
	<font size="1">
		<form method="post" name="frmNuevo" onSubmit=" return validarForma(frmNuevo)"  action='<c:url value="/conflicto/NuevoGuardar.jsp"/>'>
		<table border="1" align="center" bordercolor="#FFFFFF" width="100%">
			<tr>		
				<td width="45%" bgcolor="#FFFFFF">
	        <input class='boton' name="cmd1" type="button" value="Buscar" onClick="buscar(10,-1)">
        <c:if test="${(sessionScope.nuevoConflicto.ceperiodo!='-1') && (sessionScope.nuevoConflicto.cesede!='-1') && (sessionScope.nuevoConflicto.cejornada!='-1')}">
					<c:if test="${requestScope.sololectura!=1}">
		        <input class='boton' name="cmd1" type="button" value="Guardar" onClick="guardar(11,-1)">
	        </c:if>
        </c:if>
				</td>
			</tr>
		</table>
	<!--//////////////////-->
	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="tipo" value="">
	<input type="hidden" name="cmd" value="Cancelar"><INPUT TYPE="hidden" NAME="height" VALUE='150'>
	<input type="hidden" name="ext2" value=''>
	<input type="hidden" name="cesednom" value=''>
	<input type="hidden" name="cejornom" value=''>
	<input type="hidden" name="cepernom" value=''>
	<input type="hidden" name="categoria" value=''>
	<!--<input type="hidden" name="ceperiodo" value=''>-->
		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1">
				<td width="10" bgcolor="#FFFFFF">&nbsp;</td>			
				<td rowspan="2" width="600" bgcolor="#FFFFFF">
					<!-- <a href="javaScript:lanzar(0,0)"><img src="../etc/img/tabs/conflicto_escolar_0.gif" alt="Filtro" border="0"  height="26"></a> -->
					<img src="../etc/img/tabs/tipos_conflicto_1.gif" alt="Tipos de Conflicto" border="0"  height="26">
					<a href="javaScript:lanzar(20,2)"><img src="../etc/img/tabs/resolucion_conflictos_0.gif" alt="Formas comunes de resolución de conflictos" border="0"  height="26"></a>
					<!--<a href="javaScript:lanzar(30,3)"><img src="../etc/img/tabs/medidas_institucionales_0.gif" alt="Medidas Institucionales" border="0"  height="26"></a>-->
					<a href="javaScript:lanzar(40,4)"><img src="../etc/img/tabs/proyectos_0.gif" alt="Proyectos que apoyan la resolución de conflictos" border="0"  height="26"></a>
					<!--<a href="javaScript:lanzar(50,5)"><img src="../etc/img/tabs/influencia_conflictos_0.gif" alt="Como influyen los conflictos en los siguientes aspectos de la vida del colegio" border="0"  height="26"></a>-->
					<!--<a href="javaScript:lanzar(60,-1)"><img src="../etc/img/tabs/consolidado_grupo_0.gif" alt="Consolidado por grupo" border="0"  height="26"></a>-->
				</td>
			</tr>
		</table>
		<!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
		<table align="center" border="0">
			<tr>
				<td><span class="style2">*</span>Sede:</td>
				<td><select name="cesede" onChange='filtro(document.frmNuevo.cesede, document.frmNuevo.cejornada)' style='width: 300px;'>
						<option value='-1'>-- Seleccione uno --</option>
						<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila">
							<option value="<c:out value="${fila[0]}"/>"
								<c:if test="${sessionScope.nuevoConflicto.cesede== fila[0]}">SELECTED</c:if>>
								<c:out value="${fila[1]}"/>
							</option>
						</c:forEach>
				</select></td>
				<td><span class="style2">*</span>Jornada:</td>
				<td><select name="cejornada" style='width: 150px;' onChange=''>
						<option value='-1'>-- Seleccione uno --</option>
					</select>
				</td>
			</tr>
			<tr>
				<td><span class="style2">*</span>Periodo:</td>
				<td><select name="ceperiodo">
						<option value="-1">--seleccione uno--</option>
						<c:if test="${sessionScope.login.vigencia_actual != null and sessionScope.login.vigencia_actual < 2013}">
							<c:forEach begin="0" items="${filtroPeriodo}" var="fila">
								<option value="<c:out value="${fila[0]}"/>"
									<c:if test="${sessionScope.nuevoConflicto.ceperiodo== fila[0]}">SELECTED</c:if>>
									<c:out value="${fila[1]}" />
							</c:forEach>
						</c:if>
						<c:if test="${sessionScope.login.vigencia_actual != null and sessionScope.login.vigencia_actual >= 2013}">
							<c:forEach begin="0" items="${requestScope.listaPeriodos}" var="fila">
								<option value="<c:out value="${fila[0]}"/>"
									<c:if test="${sessionScope.nuevoConflicto.ceperiodo== fila[0]}">SELECTED</c:if>>
									<c:out value="${fila[1]}" />
							</c:forEach>
						</c:if>

				</select></td>
			</tr>
		</table>

<c:if test="${requestScope.sololectura==1}"><p style="color:red;font-size:12;font-weight:bold" align="center">No puede guardar el formulario debido a que el periodo de Conflicto Escolar se encuentra cerrado.</p></c:if>

	<c:if test="${(sessionScope.nuevoConflicto.ceperiodo!='-1') && (sessionScope.nuevoConflicto.cesede!='-1') && (sessionScope.nuevoConflicto.cejornada!='-1')}">
		<table cellpadding="0" cellspacing="0" border="0" width="100%">
			<tr>
				<td>
					<c:forEach begin="0" items="${requestScope.filtroClase}" var="fila">
						<TABLE width="98%" align="center" cellpadding="0" cellSpacing="0" border="0">
							<tr class="caption">
								<th rowspan="2" colspan="2" width="60%"><c:out value="${fila[1]}"/></th>
							</tr>
							<tr class="caption">
								<th title="Ninguno" width="5%">N</th>
								<th title="Bajo" width="5%">B</th>
								<th title="Medio" width="5%">M</th>
								<th title="Alto" width="5%">A</th>
								<th title="Completo" width="5%">C</th>
								<th title="Entorno Escolar" width="15%">Entorno Escolar</th>
							</tr>
							<tr><td colspan="2" class="subcaption">Marque los tipos de conflicto que se presentan</td><td colspan="5" class="subcaption" align="center">Avance Alcanzado</td></tr>
							<c:forEach begin="0" items="${requestScope.filtroTipo}" var="fila2">
								<c:if test="${fila[0]==fila2[3]}">
									<tr>
										<td align="center"><input type="checkbox" value="-1" name='k<c:out value="${fila2[0]}"/>' onclick="habilitar('c<c:out value="${fila2[0]}"/>')"/></td>
										<td <c:if test="${fila2[4]==2}">title='<c:out value="${fila2[5]}"/>'</c:if>><c:out value="${fila2[1]}"/></td>
										<td id="uno" name="uno" colspan="5" align="center" style="display:">
										<input type="radio" value="1" disabled name='c<c:out value="${fila2[0]}"/>'/>&nbsp;
										<input type="radio" value="2" disabled name='c<c:out value="${fila2[0]}"/>'/>&nbsp;
										<input type="radio" value="3" disabled name='c<c:out value="${fila2[0]}"/>'/>&nbsp;
										<input type="radio" value="4" disabled name='c<c:out value="${fila2[0]}"/>'/>&nbsp;
										<input type="radio" value="5" disabled name='c<c:out value="${fila2[0]}"/>'/>
										</td>
										<td id="dos" name="dos" colspan="" align="center" style="display:">
											<select name="c_ent<c:out value="${fila2[0]}"/>" disabled <c:if test="${requestScope.sololectura==1}">readonly</c:if> >
												<option value='-9'> -- </option>
												<option value='1'> Dentro del Colegio </option>
												<option value='2'> Fuera del Colegio </option>
												<option value='3'> Ambos </option>
											</select>		  
										 </td>
									</tr>
								</c:if>
							</c:forEach>
							<tr><td colspan="7" class="subcaption">Convenciones: (N) Ninguno - (B) Bajo - (M) Medio - (A) Alto - (C) Completo</td></tr>
						</table>
						<hr>
				</c:forEach>
				</td>
	  </table>
	</c:if>
<!--//////////////////////////////-->
</form>
<script>
	filtro(document.frmNuevo.cesede, document.frmNuevo.cejornada)
	<c:if test="${(sessionScope.nuevoConflicto.ceperiodo!='-1') && (sessionScope.nuevoConflicto.cesede!='-1') && (sessionScope.nuevoConflicto.cejornada!='-1')}">
		<c:forEach begin="0" items="${sessionScope.nuevoTipo.tipoconflicto}" var="fila">
			document.frmNuevo.k<c:out value="${fila[0]}"/>.checked=true; 
			habilitar('c<c:out value="${fila[0]}"/>')
			<c:choose>
				<c:when test="${fila[1]==1}">document.frmNuevo.c<c:out value="${fila[0]}"/>[0].checked=true;</c:when>
				<c:when test="${fila[1]==2}">document.frmNuevo.c<c:out value="${fila[0]}"/>[1].checked=true;</c:when>
				<c:when test="${fila[1]==3}">document.frmNuevo.c<c:out value="${fila[0]}"/>[2].checked=true;</c:when>
				<c:when test="${fila[1]==4}">document.frmNuevo.c<c:out value="${fila[0]}"/>[3].checked=true;</c:when>
				<c:when test="${fila[1]==5}">document.frmNuevo.c<c:out value="${fila[0]}"/>[4].checked=true;</c:when>
				</c:choose>
				<c:choose>
				
				<c:when test="${fila[2]==1}">document.frmNuevo.c_ent<c:out value="${fila[0]}"/>.selectedIndex=1</c:when>
				<c:when test="${fila[2]==2}">document.frmNuevo.c_ent<c:out value="${fila[0]}"/>.selectedIndex=2</c:when>
				<c:when test="${fila[2]==3}">document.frmNuevo.c_ent<c:out value="${fila[0]}"/>.selectedIndex=3</c:when>
			</c:choose>
		</c:forEach>
	</c:if>
</script>