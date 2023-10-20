<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import = "java.util.List"%>
<jsp:useBean id="logros" class="siges.plantilla.beans.Logros" scope="session"/>
<jsp:setProperty name="logros" property="*"/>
<jsp:useBean id="paramsVO" class="siges.plantilla.beans.ParamsVO" scope="page"/>
<jsp:useBean id="propertiesVO" class="siges.util.Properties" scope="page"/>
<c:import url="/parametros.jsp"/>
<c:set var="tip" value="4" scope="page"/>
		<script languaje='javaScript'>
		<!--
		<%
		int lindx;
		if (request.getHeader("Referer").lastIndexOf('?')==-1)
			lindx=request.getHeader("Referer").length();
		else
			lindx=request.getHeader("Referer").lastIndexOf('?');%>
			var nav4=window.Event ? true : false;
			function acepteNumeros(eve){
				var key=nav4?eve.which :eve.keyCode;
				return(key<=13 || (key>=48 && key<=57));
			}
			function generarPlantilla(){
				document.frmGenerar.submit();
			}
			function hacerValidaciones_frmNuevo(forma){
				validarLista(forma.plantillaMetodologia,"- Metodologia",1)
				//validarLista(forma.plantillaGrado,"- Grado",1)
				//validarLista(forma.plantillaAsignatura,"- Asignatura",1)
				if(forma.plantillaAsignatura.selectedIndex>0 && forma.plantillaDocente){
					//validarLista(forma.plantillaDocente,"- Docente (obligatorio si se selecciona asignatura)",1)
					<c:if test="${sessionScope.login.perfil!=422}">
					validarLista(forma.plantillaDocente,"- Una vez que seleccione una asignatura debe escoger el docente en particular",1)
					</c:if>
					<c:if test="${sessionScope.login.perfil==422}">
					var no_hijos = document.frmNuevo.plantillaDocente.length;
					var sessiondoc ='<c:out value="${sessionScope.login.usuarioId}"/>';
					var bol=0;
					var aux;
					for(i=0; i < no_hijos; i++){
						if(document.frmNuevo.plantillaDocente.options[i].value==sessiondoc){
							bol=bol+1;
							aux=i;
						}
					}
					if(bol>0){
						document.frmNuevo.plantillaDocente.selectedIndex=aux;
					}else{
						validarLista(forma.plantillaDocente,"- Usted no tiene asignada esta asignatura en este grado",1)
					}
					</c:if>
				}
			}
			
			function lanzar(tipo){
				document.frmNuevo.tipo.value=tipo;					
				document.frmNuevo.action="<c:url value="/plantilla/bateria/ControllerPlantillaEdit.do"/>";
				document.frmNuevo.submit();
			}
			
			function guardar(tipo){
				if(validarForma(document.frmNuevo)){
					document.frmNuevo.plantillaGrado_.value=document.frmNuevo.plantillaGrado.options[document.frmNuevo.plantillaGrado.selectedIndex].text;
					document.frmNuevo.plantillaAsignatura_.value=document.frmNuevo.plantillaAsignatura.options[document.frmNuevo.plantillaAsignatura.selectedIndex].text;
					document.frmNuevo.plantillaMetodologia_.value=document.frmNuevo.plantillaMetodologia.options[document.frmNuevo.plantillaMetodologia.selectedIndex].text;
					document.frmNuevo.tipo.value=tipo;
					document.frmNuevo.cmd.value="Buscar";
					document.frmNuevo.submit();
				}
			}
			
			function cancelar(){
				if (confirm('¿Desea cancelar la Generación de plantilla?')){
 					document.frmNuevo.cmd.value="Cancelar";
				    document.frmNuevo.submit(); 
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
				combo.options[0] = new Option("-- Todos --","-99");
			}
			
			function filtro2(combo_padre,combo_hijo,combo_hijo2){
				borrar_combo(combo_hijo);borrar_combo(combo_hijo2);
				<c:if test="${!empty requestScope.filtroMetodologia && !empty requestScope.filtroGrado2}">var Padres = new Array();
					<c:forEach begin="0" items="${requestScope.filtroMetodologia}" var="fila" varStatus="st">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
					<c:forEach begin="0" items="${requestScope.filtroGrado2}" var="fila2">
						<c:if test="${fila[0]==fila2[2] && (fila2[0]>=0 || fila2[0]==40 || fila2[0]==41)}">
						<% if (request.getHeader("Referer").substring(request.getHeader("Referer").lastIndexOf('/')+1,lindx).equals("ControllerPlantillaEdit.do")|| request.getHeader("Referer").substring(request.getHeader("Referer").lastIndexOf('/')+1,lindx).equals("PlantillaGuardar.jsp")){ %>
						Sel_Hijos[k] = '<c:if test="${sessionScope.logros.plantillaGrado== fila2[0]}">SELECTED</c:if>';
						<% }else{ %>
						Sel_Hijos[k] ='';
						<% }%>
						id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k] = '<c:out value="${fila2[2]}"/>'; k++; </c:if>
						</c:forEach>Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
					var niv=combo_padre.value;
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
				}</c:if>
			}
			
			function filtro(combo_padre0,combo_padre,combo_hijo){//met.gra.asig
				borrar_combo(combo_hijo);
				<c:if test="${!empty requestScope.filtroGrado2 && !empty requestScope.filtroGradoAsignatura2}">var Padres = new Array();
					<c:forEach begin="0" items="${requestScope.filtroGrado2}" var="fila" varStatus="st">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
						<c:forEach begin="0" items="${requestScope.filtroGradoAsignatura2}" var="fila2">
						<c:if test="${fila[0]==fila2[2] && fila[2]==fila2[3]}">
						<% if (request.getHeader("Referer").substring(request.getHeader("Referer").lastIndexOf('/')+1,lindx).equals("ControllerPlantillaEdit.do")|| request.getHeader("Referer").substring(request.getHeader("Referer").lastIndexOf('/')+1,lindx).equals("PlantillaGuardar.jsp")){ %>
						Sel_Hijos[k] = '<c:if test="${sessionScope.logros.plantillaAsignatura== fila2[0]}">SELECTED</c:if>';
						<% }else{ %>
						Sel_Hijos[k] ='';
						<% }%>
						id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k]='<c:out value="${fila2[4]}"/>'; k++; </c:if>
						</c:forEach>Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
					var niv=combo_padre.options[combo_padre.selectedIndex].value +'|'+ combo_padre0.value;
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
				}</c:if>
			}
		//-->

	
		
		function ajaxDocente(){
			
			<c:if test='${sessionScope.perfilactual!=422}'>
		if(document.frmNuevo.plantillaDocente){
			borrar_combo(document.frmNuevo.plantillaDocente); 
			document.frmAjax.ajax[0].value=document.frmNuevo.plantillaInstitucion.value;
			document.frmAjax.ajax[1].value=document.frmNuevo.plantillaMetodologia.value;
			document.frmAjax.ajax[2].value=document.frmNuevo.plantillaVigencia.value;
			document.frmAjax.ajax[3].value=document.frmNuevo.plantillaGrado.value;
			document.frmAjax.ajax[4].value=document.frmNuevo.plantillaAsignatura.value;
			if(parseInt(document.frmAjax.ajax[4].value)!=-99){
				document.frmAjax.cmd.value=document.frmNuevo.AJAX_DOCENTE.value;
		 		document.frmAjax.target="frameAjax";
		 		document.frmNuevo.plantillaDocente.selectedIndex=0;
				document.frmAjax.submit();
			}
		}
		</c:if>
		<c:if test='${sessionScope.perfilactual==422}'>
		//alert('cambiooo 1232');
				document.frmAjax.cmd.value=<c:out value="${sessionScope.iduseractual}"/>;
		 		document.frmAjax.target="frameAjax";
				document.frmAjax.submit();
		</c:if>
	}
		</script>
		<head>
		<style type="text/css">
TD.norepeat{
background-image:url(../../etc/img/tabs/pestana_1.gif); 
background-repeat:no-repeat;
}

TD.norepeat2{
background-image:url(../../etc/img/tabs/pestana_0.gif); 
background-repeat:no-repeat;
cursor:pointer;
}

.sombra {
color:white;
text-shadow:#666666 3px 3px 3px;
filter: progid:DXImageTransform.Microsoft.Shadow(color='#666666', Direction=135, Strength=4);
font-size: 85%;
font-stretch: ultra-expanded;
line-height: 95%;
font-weight: bolder;
}
.sombran {
text-shadow:#666666 3px 3px 3px;
filter: progid:DXImageTransform.Microsoft.Shadow(color='#666666', Direction=135, Strength=4);
font-size: 85%;
font-stretch: ultra-expanded;
line-height: 95%;
font-weight: bolder;
}
</style>
</head>
<c:import url="/mensaje.jsp"/>
	<form method="post" name="frmAjax" action='<c:url value="/plantilla/bateria/ControllerPlantillaAjax.do"/>'>
		<INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${propertiesVO.TIPOBATERIALOGRO}"/>'>
		<input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_AJAX}"/>'>
		<c:forEach begin="0" end="5" var="i"><input type="hidden" name="ajax"></c:forEach>
	</form>

	<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/plantilla/bateria/PlantillaGuardar.jsp"/>'>
	<br>
	<table border="0" align="center" bordercolor="#FFFFFF" width="95%">
	<caption>Generar plantillas de Logros</caption>
		<tr>
		  <td width="45%" bgcolor="#FFFFFF">
        <input class='boton' name="cmd1" type="button" value="Generar" onClick="guardar(document.frmNuevo.tipo.value)">
			</td>
		</tr>
	</table>
<!--//////////////////-->		
	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="tipo" value='<c:out value="${pageScope.tip}"/>'>
	<input type="hidden" name="cmd" value="Cancelar">
	<input type="hidden" name="plantillaMetodologia_" value=''>
	<input type="hidden" name="plantillaGrado_" value='<c:out value="${sessionScope.logros.plantillaGrado_}"/>'>
	<input type="hidden" name="plantillaAsignatura_" value='<c:out value="${sessionScope.logros.plantillaAsignatura_}"/>'>
	<input type="hidden" name="plantillaInstitucion" value='<c:out value="${sessionScope.logros.plantillaInstitucion}"/>'>
	<input type="hidden" name="plantillaVigencia" value='<c:out value="${sessionScope.logros.plantillaVigencia}"/>'>
	<input type="hidden" name="AJAX_DOCENTE" value='<c:out value="${paramsVO.CMD_AJAX_DOCENTE}"/>'>
 		<table border="0" ALIGN="CENTER" width="95%">
			<tr align="left" colspan="2">
			<c:forEach begin="0" items="${sessionScope.logroanddesc}" var="logdes">
				<td width="15%" height="26" class="norepeat">
					<p align="center" class="sombra"><c:out value="${logdes[0]}"/></p>
				</td>
				<td width="15%" height="26" class="norepeat2" onclick='javaScript:lanzar(5)'>
				<p align="center" class="sombran"><c:out value="${logdes[1]}"/></p>
				</td>
				<td width="70%" height="26" >
				</td>
			</c:forEach>
			</tr>
		</table>
	<!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
								<TABLE width="100%" cellpadding="3" cellSpacing="0">
										<tr>
											<th><span class="style2">*</span>Metodologia:</th>
											<td>
												<select name="plantillaMetodologia" style='width:120px;' onChange='filtro2(document.frmNuevo.plantillaMetodologia,document.frmNuevo.plantillaGrado,document.frmNuevo.plantillaAsignatura)'>
													<option value='-99'>-- Todos --</option>
													<c:forEach begin="0" items="${requestScope.filtroMetodologia}" var="doc">
														<option value="<c:out value="${doc[0]}"/>" <c:if test="${doc[0]==sessionScope.logros.plantillaMetodologia}"> <% if (request.getHeader("Referer").substring(request.getHeader("Referer").lastIndexOf('/')+1,lindx).equals("ControllerPlantillaEdit.do")|| request.getHeader("Referer").substring(request.getHeader("Referer").lastIndexOf('/')+1,lindx).equals("PlantillaGuardar.jsp")){ %>' SELECTED '<% } %></c:if>><c:out value="${doc[1]}"/></option>
													</c:forEach>
												</select>
											</td>	
											<th>Grado:</th>
											<td>
												<select name="plantillaGrado" style='width:120px;' onChange='filtro(document.frmNuevo.plantillaMetodologia,document.frmNuevo.plantillaGrado, document.frmNuevo.plantillaAsignatura)'>
													<option value='-99'>-- Todos --</option>
												</select>
											</td>	
										</tr>
										<tr>
											<th>Asignatura:</th>
											<td colspan="3"><select name="plantillaAsignatura" style='width:220px;' onchange="javaScript:ajaxDocente()">
													<option value='-99'>-- Todos --</option>
													</select>
											</td>	
										</tr>
										<c:if test="${sessionScope.logros.estadoPlanEstudios==1}">
										<tr>
											<th style='<c:if test="${sessionScope.login.perfil==422}">display:none;</c:if>'><span class="style2" >*</span>Docente:</th>
											<td colspan="3" style='<c:if test="${sessionScope.login.perfil==422}">display:none;</c:if>'>
												<select name="plantillaDocente" style='width:250px;<c:if test="${sessionScope.login.perfil==422}">display:none;</c:if>' >
													<option value='-99'>--Seleccione uno--</option>
													<c:forEach begin="0" items="${requestScope.listaDocente}" var="doc">
														<option value="<c:out value="${doc.codigo}"/>" <c:if test="${doc.codigo==sessionScope.logros.plantillaDocente}">selected</c:if>><c:out value="${doc.nombre}"/></option>
													</c:forEach>
												</select>
											</td>	
										</tr>
										</c:if>
										<c:if test="${sessionScope.logros.estadoPlanEstudios!=1 && sessionScope.login.perfil==422}">
										<tr>
											<th><span class="style2" >*</span>Docente:</th>
											<td colspan="3" >
												<c:out value="${sessionScope.login.usuario}"/>
											</tr>
										</c:if>
										
										<tr style="display:none"><td><iframe name="frameAjax" id="frameAjax"></iframe></td></tr>
										</form>
										<c:if test="${!empty requestScope.plantilla && requestScope.plantilla!='--' && requestScope.plantilla!='0'}"><tr>
										<td colspan="6" align='center' valign="top"><form name='frmGenerar' action='<c:url value="/${requestScope.plantilla}"/>' method='post' target='_blank'><input type='hidden' name='dir' value='<c:out value="${requestScope.plantilla}"/>'><input type='hidden' name='tipo' value='<c:out value="${requestScope.tipoArchivo}"/>'><input type='hidden' name='accion' value='0'><input type='hidden' name='aut' value='1'>La plantilla fue generada satisfactoriamente.<br>Pulse en el Icono para descargar el archivo.<br></form>
											>><a href='javaScript:document.frmGenerar.submit();'><% if ("-99".equals(request.getParameter("plantillaAsignatura"))) { %><img border=0 src='<c:url value="/etc/img/zip.gif"/>'><% }else { %><img border=0 src='<c:url value="/etc/img/xls.gif"/>'><% } %></A><<</td>
										</tr></c:if>
										<c:if test="${requestScope.plantilla=='--'}"><tr><td colspan="6" valign="top" align='center'>No se pudo generar la plantilla de Excel.<br></td></tr></c:if>
										<c:if test="${requestScope.plantilla=='0'}"><tr><td colspan="6" valign="top" align='center'>Se han generado las plantillas. <br>Para ver los archivos generados y descargarlos haga click aqui<br>>><a href='<c:url value="/Reportes.do"/>'><% if ("-99".equals(request.getParameter("plantillaAsignatura"))) { %><img border=0 src='<c:url value="/etc/img/zip.gif"/>'><% }else { %><img border=0 src='<c:url value="/etc/img/xls.gif"/>'><% } %></A><<<br></td></tr></c:if>
								</TABLE>
<script>
filtro2(document.frmNuevo.plantillaMetodologia,document.frmNuevo.plantillaGrado,document.frmNuevo.plantillaAsignatura);
document.frmNuevo.plantillaGrado.onchange();
if (document.frmNuevo.plantillaMetodologia.selectedIndex==0 && document.frmNuevo.plantillaGrado.selectedIndex==0 && document.frmNuevo.plantillaAsignatura.selectedIndex==0){
	if(document.frmNuevo.plantillaDocente)document.frmNuevo.plantillaDocente.selectedIndex=0;
}
</script>