<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %><%@ page import="siges.util.Recursos" %>
<jsp:useBean id="nuevoPersonal" class="siges.personal.beans.Personal" scope="session"/>
<jsp:useBean id="nuevoConvivencia" class="siges.estudiante.beans.Convivencia" scope="session"/><jsp:setProperty name="nuevoConvivencia" property="*"/>
<jsp:useBean id="nuevoSalud" class="siges.estudiante.beans.Salud" scope="session"/><jsp:setProperty name="nuevoSalud" property="*"/>
<jsp:useBean id="laboralVO" class="siges.personal.beans.LaboralVO" scope="session"/><jsp:setProperty name="laboralVO" property="*"/>
<jsp:useBean id="paramsVO" class="siges.personal.beans.ParamsVO" scope="page"/>

<jsp:useBean id="asistenciaVO" class="siges.estudiante.beans.AsistenciaVO" scope="session"/><jsp:setProperty name="asistenciaVO" property="*"/>
<jsp:useBean id="formacionVO" class="siges.personal.beans.FormacionVO" scope="session"/><jsp:setProperty name="formacionVO" property="*"/>
<%pageContext.setAttribute("filtroDepartamento",Recursos.recurso[Recursos.DEPARTAMENTO]);
pageContext.setAttribute("filtroMunicipio",Recursos.recurso[Recursos.LOCALIDAD]);
pageContext.setAttribute("filtroDocumento",Recursos.recurso[Recursos.TIPODOCUMENTO]);
pageContext.setAttribute("filtroEtnia",Recursos.recurso[Recursos.ETNIA]);%>
<%@include file="../parametros.jsp"%><c:set var="tip" value="1" scope="page"/>
		<script languaje='javaScript'>
		<!--
			var fichaPersonal=1;
			var fichaSede=1;
			var fichaConvivencia=1;
			var fichaSalud=1;
			var fichaLaboral=1;
			var fichaAcademica=1;
			var fichaAsistencia=1;
			var fichaCarga=1;
			var fichaFoto=1;
			var nav4=window.Event ? true : false;
			function acepteNumeros(eve){
				var key=nav4?eve.which :eve.keyCode;
				return(key<=13 || (key>=48 && key<=57));
			}
			function hacerValidaciones_frmNuevo(forma){
	            <c:if test="${sessionScope.nuevoPersonal.estado != '1'}">
					validarSeleccion(forma.pertipo,"- Tipo de personal")
					validarLista(forma.pertipdocum,"- Tipo de documento",1)				
	  			    validarCampo(forma.pernumdocum, "- Número de identificación", 1, 12)				
					validarCampo(forma.perapellido1, "- Primer apellido", 1, 20)
					validarCampo(forma.pernombre1, "- Primer nombre", 1,20)
				</c:if>
				validarcorreo(forma.peremail,"- El formato del Email no es el correcto")
			}
			function lanzar(tipo){				
				document.frmNuevo.tipo.value=tipo;					
				document.frmNuevo.action="<c:url value="/personal/ControllerNuevoEdit.do"/>";
				document.frmNuevo.submit();
			}
			 
			function guardar(tipo){
				if(validarForma(document.frmNuevo)){
					document.frmNuevo.tipo.value=tipo;
					document.frmNuevo.cmd.value="Guardar";
					document.frmNuevo.submit();
				}				
			}
			 
			function cancelar(){
				//if (confirm('¿Desea cancelar la edición de la información básica del personal?')){
 					document.frmNuevo.cmd.value="Cancelar";
					document.frmNuevo.ext2.value="";
          document.frmNuevo.submit();
				//}
			}
			function inhabilitarFormulario(){
				for(var i=0;i<document.frmNuevo.elements.length;i++){
					if(document.frmNuevo.elements[i].type != "hidden" && document.frmNuevo.elements[i].type != "button" && document.frmNuevo.elements[i].type != "submit" && document.frmNuevo.elements[i].name != "peremail" )
					document.frmNuevo.elements[i].disabled=true;
				}	
			}
			/*
			function inhabilitarFormulario(){
				document.frmNuevo.pernumdocum.disabled=true;
			}	
			*/
			function ponerSeleccionado(){
				if(document.frmResultado){
					if(document.frmResultado.id){
						if(document.frmResultado.id.length){
							for(var i=0;i<document.frmResultado.id.length;i++){
								if(document.frmResultado.id[i].value==document.frmNuevo.ELHPSELECCIONADO.value){
									document.frmResultado.id[i].checked=true;
									document.frmResultado.id[i].focus();
									break;
								}
							}
						}else{
							if(document.frmResultado.id.value==document.frmNuevo.ELHPSELECCIONADO.value){
								document.frmResultado.id.checked=true;;
								document.frmResultado.id.focus();
							}
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
				combo.options[0] = new Option("--Seleccione uno--","-1");
			}
			function filtro(combo_padre,combo_hijo){
				if(combo_padre.selectedIndex==0)
					borrar_combo(combo_hijo); else{
					borrar_combo(combo_hijo);
					<c:if test="${!empty filtroDepartamento && !empty filtroMunicipio}">
						var Padres = new Array();
						<c:forEach begin="0" items="${filtroDepartamento}" var="fila" varStatus="st">
						<c:set var="z" value="${fila[0]}" scope="request"/>
							id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
							<c:forEach begin="0" items="${filtroMunicipio}" var="fila2">
							<c:if test="${fila[0]==fila2[2]}">
								<c:set var="y" value="${fila2[2]}" scope="request"/>
									Sel_Hijos[k] = '<c:if test="${sessionScope.nuevoPersonal.perexpdoccodmun== fila2[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k] = '<c:out value="${fila2[2]}"/>'; k++;
							</c:if></c:forEach>
							Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);
						</c:forEach>						
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
			}
			function filtro2(combo_padre,combo_hijo){
				if(combo_padre.selectedIndex==0)
					borrar_combo(combo_hijo); else{
					borrar_combo(combo_hijo);
					<c:if test="${!empty filtroDepartamento && !empty filtroMunicipio}">
						var Padres = new Array();
						<c:forEach begin="0" items="${filtroDepartamento}" var="fila" varStatus="st">
						<c:set var="z" value="${fila[0]}" scope="request"/>
							id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
							<c:forEach begin="0" items="${filtroMunicipio}" var="fila2">
							<c:if test="${fila[0]==fila2[2]}">
								<c:set var="y" value="${fila2[2]}" scope="request"/>
									Sel_Hijos[k] = '<c:if test="${sessionScope.nuevoPersonal.perlugnaccodmun== fila2[0]}">SELECTED</c:if>';
									id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k] = '<c:out value="${fila2[2]}"/>'; k++;
							</c:if></c:forEach>
							Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);
						</c:forEach>						
						var niv=combo_padre.options[combo_padre.selectedIndex].value;
						var val_padre = -1;
						for(var k=0;k<Padres.length;k++){
							if(Padres[k].id_Padre[0]==niv)
								val_padre=k;
						}
						if(val_padre!=-1){
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
			}
			//-->
		</script>
<%@include file="../mensaje.jsp"%>
	<font size="1">
  <form name="f" target='1' action='<c:url value="/personal/ControllerFiltroSave.do"/>'></form>
	<form method="post" name="frmNuevo"  onSubmit=" return validarForma(frmNuevo)" action='<c:url value="/personal/NuevoGuardar.jsp"/>'>
	<table border="0" align="center" width="100%" cellpadding="1" cellspacing="0" bordercolor="silver">
			<tr>		
			  <td width="45%" bgcolor="#FFFFFF">                
					<input class='boton' name="cmd1" type="button" value="Actualizar" onClick="guardar(document.frmNuevo.tipo.value)" style="display:">
			  </td>   	
			</tr>	
	  </table>
<!--//////////////////-->		
	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="ELHPSELECCIONADO" value="<c:out value="${sessionScope.nuevoPersonal.pernumdocum}"/>">
	<input type="hidden" name="tipo" value='<c:out value="${pageScope.tip}"/>'>
	
	<c:if test="${  (sessionScope.login.perfil == paramsVO.PERFIL_RECTOR or login.perfil == paramsVO.PERFIL_ADMIN_ACADEMICO )}">
	 <input type="hidden" name="ext2" value='<c:out value="${sessionScope.subframePer}"/>'>
	</c:if>
	<INPUT TYPE="hidden" NAME="height" VALUE='180'>
	<input type="hidden" name="cmd" value="Cancelar">
	<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
		<tr height="1">
			<td width="10" bgcolor="#FFFFFF">&nbsp;</td>
			<td width="510" bgcolor="#FFFFFF"><script>
			if(fichaPersonal==1)document.write('<img src="../etc/img/tabs/datos_basicos_1.gif" alt="Inf. Basica"  height="26" border="0">');
			if(fichaSalud==1)document.write('<a href="javaScript:lanzar(4)"><img src="../etc/img/tabs/inf_salud_0.gif" alt="Salud"  height="26" border="0"></a>');
			if(fichaConvivencia==1)document.write('<a href="javaScript:lanzar(2)"><img src="../etc/img/tabs/inf_convivencia_0.gif" alt="Convivencia"  height="26" border="0"></a>');
			if(fichaSede==1)document.write('<a href="javaScript:lanzar(3)"><img src="../etc/img/tabs/Doc_Jor0.gif" alt="Convivencia"  height="26" border="0"></a>');
			if(fichaLaboral==1)document.write('<a href="javaScript:lanzar(5)"><img src="../etc/img/tabs/inf_laboral_0.gif" alt="Laboral"  height="26" border="0"></a>');
			if(fichaAcademica==1)document.write('<a href="javaScript:lanzar(6)"><img src="../etc/img/tabs/for_academica_0.gif" alt="Academica"  height="26" border="0"></a><br>');
			if(fichaAsistencia==1)document.write('<a href="javaScript:lanzar(7)"><img src="../etc/img/tabs/asistencia_0.gif" alt="Asistencia" height="26" border="0"></a>');
			if(fichaCarga==1)document.write('<a href="javaScript:lanzar(8)"><img src="../etc/img/tabs/carga_academica_0.gif" alt=""  height="26" border="0"></a>');
			if(fichaFoto==1)document.write('<a href="javaScript:lanzar(9)"><img src="../etc/img/tabs/fotografia_0.gif" alt=""  height="26" border="0"></a>');
			</script></td>
			</tr>
  </table>
		<!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
								<TABLE align='center' width="95%" cellpadding="3" cellSpacing="0">
									<tr>
										<td>Tipo de persona:</td>
										<td colspan='2'>
											<input type="radio" name="pertipo" value="1" <c:if test="${sessionScope.nuevoPersonal.pertipo== '1'}">CHECKED</c:if>>Docente
											<input type="radio" name="pertipo" value="2" <c:if test="${sessionScope.nuevoPersonal.pertipo== '2'}">CHECKED</c:if>>Directivo
											<input type="radio" name="pertipo" value="3" <c:if test="${sessionScope.nuevoPersonal.pertipo== '3'}">CHECKED</c:if>>Administrativo
											<input type="radio" name="pertipo" value="4" <c:if test="${sessionScope.nuevoPersonal.pertipo== '4'}">CHECKED</c:if>>Administrador
											<input type="radio" name="pertipo" value="5" <c:if test="${sessionScope.nuevoPersonal.pertipo== '5'}">CHECKED</c:if>>Soporte Municipal
										</td>
									</tr>
									<tr>
										<td><span class="style2">*</span> Tipo de documento:</td>
										<td>
											<select name="pertipdocum">
													<option value="-1">--seleccione uno--</option>
													<c:forEach begin="0" items="${filtroDocumento}" var="fila">
														<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.nuevoPersonal.pertipdocum== fila[0]}">SELECTED</c:if>>
														<c:out value="${fila[1]}"/></option>
													</c:forEach>													
											</select>
										</td>
										<td ><span class="style2">*</span> Número de documento:</td>
										<td ><input type="text" name="pernumdocum"  maxlength="12" value='<c:out value="${sessionScope.nuevoPersonal.pernumdocum}"/>' onKeyPress='return acepteNumeros(event)'></td>
									</tr>
									<tr>
										<td ><span class="style2">*</span> Primer nombre:</td>
										<td >
											<input type="text" name="pernombre1"  maxlength="20" value='<c:out value="${sessionScope.nuevoPersonal.pernombre1}"/>'>
										</td>
											<td > Segundo nombre:</td>
										<td >
											<input type="text" name="pernombre2"  maxlength="20" value='<c:out value="${sessionScope.nuevoPersonal.pernombre2}"/>'>
										</td>
									</tr>
									<tr>
                   <td><span class="style2">*</span> Primer apellido:</td>
                   <td>
										  <input type="text" name="perapellido1"  maxlength="20" value='<c:out value="${sessionScope.nuevoPersonal.perapellido1}"/>'>
									  </td>
                     <td> Segundo apellido:</td>
										<td>
											<input type="text" name="perapellido2"  maxlength="20" value='<c:out value="${sessionScope.nuevoPersonal.perapellido2}"/>'>
										</td>
								</tr>
									 <tr><td>Email:</td>
					     <td colspan="3" ><input type="text" name="peremail" id="peremail" maxlength="200" size="50" value='<c:out value="${sessionScope.nuevoPersonal.peremail}"/>' >
					     </td>
					 </tr>
								</TABLE>
	</font>		
<!--//////////////////////////////-->
</form>
<c:remove var="filtroDepartamento"/> <c:remove var="filtroMunicipio"/> <c:remove var="filtroDocumento"/> <c:remove var="filtroEtnia"/> 
<script><c:if test="${sessionScope.nuevoPersonal.estado== '1'}">inhabilitarFormulario();</c:if>
ponerSeleccionado();
</script>