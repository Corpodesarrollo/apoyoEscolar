<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="filtroPlantillaComportamiento" class="siges.plantilla.beans.FiltroComportamiento" scope="session"/>
<jsp:setProperty name="filtroPlantillaComportamiento" property="*"/>
<jsp:useBean id="paramsVO" class="siges.plantilla.beans.ParamsVO" scope="page"/>
<c:import url="/parametros.jsp"/>
<script languaje='javaScript'>
		<!--  
			var nav4=window.Event ? true : false;
			function acepteNumeros(eve){
				var key=nav4?eve.which :eve.keyCode;
				return(key<=13 || (key>=48 && key<=57));
			}
			function hacerValidaciones_frmFiltro(forma){
				validarLista(forma.filMetodologia,"- Metodologia",1)
				validarLista(forma.filGrado,"- Grado",1)
				validarLista(forma.filGrupo,"- Grupo",1)
				validarLista(forma.filPeriodo,"- Periodo",1)
			}
			function lanzar(tipo){
				document.frmFiltro.tipo.value=tipo;
				document.frmFiltro.action='<c:url value="/plantilla/evaluacion/ControllerPlantillaEdit.do"/>';
				document.frmFiltro.cmd.value='';
				document.frmFiltro.submit();
			}

			function buscar(tipo){
				if(validarForma(document.frmFiltro)){
					document.frmFiltro.tipo.value=tipo;
					document.frmFiltro.cmd.value='<c:out value="${paramsVO.CMD_BUSCAR}"/>';
					document.frmFiltro.submit();
				}
			}
			
			function cancelar(){
				if (confirm('¿Desea cancelar la generación de la plantilla?')){
 					document.frmFiltro.cmd.value="Cancelar";
					document.frmFiltro.submit(); 
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
				combo.options[0] = new Option("--Seleccione uno--","-99");
			}
			
			function filtroMetodologia(combo_padre,combo_hijo){
				borrar_combo(combo_hijo); 
					var Padres = new Array();
						<c:forEach begin="0" items="${requestScope.lMetodologia}" var="metod" varStatus="st">
							id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
						<c:forEach begin="0" items="${requestScope.lGrado}" var="grado">
						<c:if test="${metod.codigo==grado.padre}">Sel_Hijos[k] = '<c:if test="${sessionScope.filtroPlantillaComportamiento.filGrado==grado.codigo}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${grado.codigo}"/>'; Hijos[k] = '<c:out value="${grado.nombre}"/>'; id_Padre[k] = '<c:out value="${grado.padre}"/>'; k++;</c:if></c:forEach>Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
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
			}
			function filtroGrado(combo_abuelo,combo_padre,combo_hijo){
				borrar_combo(combo_hijo); 
					var Padres = new Array();
						<c:forEach begin="0" items="${requestScope.lGrado}" var="grado" varStatus="st">
							id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
						<c:forEach begin="0" items="${requestScope.lGrupo}" var="grupo">
						<c:if test="${grado.codigo==grupo.padre && grado.padre==grupo.padre2}">Sel_Hijos[k] = '<c:if test="${sessionScope.filtroPlantillaComportamiento.filGrupo==grupo.codigo}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${grupo.codigo}"/>'; Hijos[k] = '<c:out value="${grupo.nombre}"/>'; id_Padre[k] = '<c:out value="${grupo.padre2}"/>|<c:out value="${grupo.padre}"/>'; k++;</c:if></c:forEach>Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
						var niv=combo_abuelo.options[combo_abuelo.selectedIndex].value+'|'+combo_padre.options[combo_padre.selectedIndex].value;
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
			}
			//-->
		</script>
<c:import url="/mensaje.jsp"/>
<form method="post" name="frmFiltro"  onSubmit="return validarForma(frmFiltro)" action='<c:url value="/plantilla/comportamiento/Save.jsp"/>'>
<br>
<table cellpadding="0" cellspacing="0" border="0" align="center" bordercolor="#FFFFFF" width="95%">
<caption>Plantilla de Comportamiento</caption>
	<tr><td width="45%" bgcolor="#FFFFFF"></td></tr>
		<tr>		
		  <td width="45%" bgcolor="#FFFFFF">
        <input class='boton' name="cmd1" type="button" value="Buscar" onClick="buscar(document.frmFiltro.tipo.value)">
		  </td>
		</tr>
	<tr><td width="45%" bgcolor="#FFFFFF"></td></tr>
  </table>
<!--//////////////////-->		
<!-- FICHAS A MOSTRAR AL USUARIO -->
<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_DEFAULT}"/>'>
<input type="hidden" name="cmd">
<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
	<tr height="1">
		<td width="10" bgcolor="#FFFFFF">&nbsp;</td>
		<td width="600" bgcolor="#FFFFFF">
				<a href="javaScript:lanzar(1)"><img src='<c:url value="/etc/img/tabs/Evaluacion_Asignaturas_0.gif"/>' alt="Asignatura" width="84"  height="26" border="0"></a>
				<a href="javaScript:lanzar(2)"><img src='<c:url value="/etc/img/tabs/Evaluacion_Areas_0.gif"/>' alt="Areas" width="84"  height="26" border="0"></a>
				<a href="javaScript:lanzar(3)"><img src='<c:url value="/etc/img/tabs/Evaluacion_Preescolar_0.gif"/>' alt="Preescolar" width="84"  height="26" border="0"></a>
				<a href="javaScript:lanzar(6)"><img src='<c:url value="/etc/img/tabs/Recuperacion_0.gif"/>' alt="Recuperacion" width="84"  height="26" border="0"></a>
				<img src='<c:url value="/etc/img/tabs/Evaluacion_Comportamiento_1.gif"/>' alt="Comportamiento" width="84"  height="26" border="0">
		</td>
		</tr>
	</table>
	<!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
	<TABLE width="100%" cellpadding="3" cellSpacing="0">
			<tr>											
				<td><span class="style2">*</span>Metodologia:</td>
				<td>
					<select name="filMetodologia" style='width:120px;' onChange='filtroMetodologia(document.frmFiltro.filMetodologia,document.frmFiltro.filGrado);'>
						<option value='-99'>-- Seleccione uno --</option>
						<c:forEach begin="0" items="${requestScope.lMetodologia}" var="metod">
							<option value="<c:out value="${metod.codigo}"/>" <c:if test="${sessionScope.filtroPlantillaComportamiento.filMetodologia==metod.codigo}">SELECTED</c:if>><c:out value="${metod.nombre}"/></option>
						</c:forEach>
					</select>
				</td>	
				<td><span class="style2">*</span>Grado:</td>
				<td>
					<select name="filGrado" style='width:120px;' onChange='filtroGrado(document.frmFiltro.filMetodologia,document.frmFiltro.filGrado,document.frmFiltro.filGrupo);'>
						<option value='-99'>-- Seleccione uno --</option>
						<c:forEach begin="0" items="${requestScope.lGrado}" var="grado">
							<option value="<c:out value="${grado.codigo}"/>" <c:if test="${sessionScope.filtroPlantillaComportamiento.filGrado==grado.codigo}">SELECTED</c:if>><c:out value="${grado.nombre}"/></option>
						</c:forEach>
					</select>
				</td>	
				<td><span class="style2">*</span>Grupo:</td>
				<td><select name="filGrupo" style='width:100px;'>
							<option value='-99'>-- Seleccione uno --</option>
						</select>
				</td>
			</tr>
			<tr>
				<td><span class="style2">*</span>Periodo:</td>
				<td><select name="filPeriodo" style='width:100px;'>
						<option value='-99'>-- Seleccione uno --</option>
						<c:forEach begin="0" items="${requestScope.lPeriodo}" var="per">
							<option value="<c:out value="${per.codigo}"/>" <c:if test="${per.codigo==sessionScope.filtroPlantillaComportamiento.filPeriodo}">selected</c:if>><c:out value="${per.nombre}"/></option>
						</c:forEach>
						
				</select></td>
				<td>Orden:</td>
				<td>
					<select name="filOrden" style='width:100px;'>
						<option value='0' <c:if test="${sessionScope.filtroPlantillaComportamiento.filOrden== '0'}">SELECTED</c:if>>Apellidos</option>
						<option value='1' <c:if test="${sessionScope.filtroPlantillaComportamiento.filOrden== '1'}">SELECTED</c:if>>Nombres</option>
						<option value='2' <c:if test="${sessionScope.filtroPlantillaComportamiento.filOrden== '2'}">SELECTED</c:if>>Identificación</option>
					</select>
				</td>
			</tr>
	</TABLE>
	</form>
	<c:if test="${sessionScope.filtroPlantillaComportamiento.formaEstado==1}">
		<form name='frmGenerar' action='<c:url value="/${requestScope.plantilla}"/>' method='post' target='_blank'>
				<input type='hidden' name='dir' value='<c:out value="${requestScope.plantilla}"/>'>
				<input type='hidden' name='tipo' value='<c:out value="${requestScope.tipoArchivo}"/>'>
				<input type='hidden' name='accion' value='0'>
				<input type='hidden' name='aut' value='1'>
		<TABLE width="100%" cellpadding="3" cellSpacing="0">
			<tr>
				<td colspan="6" align='center' valign="top">
				La plantilla fue generada satisfactoriamente.<br>Pulse en el icono para descargar el archivo.<br>				
				<a href='javaScript:document.frmGenerar.submit()'><img border=0 src='<c:url value="/etc/img/xls.gif"/>'></A>
				<p><p><span class="style2">Nota: Solo se generan plantillas para los grupos con estudiantes</span>
				</td>
			</tr>
		</TABLE>				
		</form>
	</c:if>	
<!--//////////////////////////////-->
<script>
filtroMetodologia(document.frmFiltro.filMetodologia,document.frmFiltro.filGrado);
filtroGrado(document.frmFiltro.filMetodologia,document.frmFiltro.filGrado,document.frmFiltro.filGrupo);</script>