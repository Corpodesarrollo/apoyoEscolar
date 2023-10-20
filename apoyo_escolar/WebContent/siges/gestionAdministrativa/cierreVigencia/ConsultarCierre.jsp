<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<link href='<c:url value="/etc/css/login.css"/>' rel="stylesheet" type="text/css">
<SCRIPT language=javascript src='<c:url value="/etc/js/validar.js"/>'></SCRIPT>
<script languaje='javaScript'>
var t=1;
var b=1;
var ab=0;
function borrar_combo(combo){
	for(var i = combo.length - 1; i >= 0; i--) {
		if(navigator.appName == "Netscape") combo.options[i] = null;
			else combo.remove(i);
	}
	combo.options[0] = new Option("-- Todos --","-1");
	
}
function Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre){
	this.id_Hijos= id_Hijos; 
	this.Hijos= Hijos; 
	this.Sel_Hijos= Sel_Hijos;
	this.id_Padre=id_Padre;
}
function filtro(combo_padre,combo_hijo){
	borrar_combo(combo_hijo);
	<c:if test="${!empty requestScope.LoginSedescol && !empty requestScope.LoginJorcolf}">var Padres = new Array();
		<c:forEach begin="0" items="${requestScope.LoginSedescol}" var="fila" varStatus="st">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
			<c:forEach begin="0" items="${requestScope.LoginJorcolf}" var="fila2">
			<c:if test="${fila[0]==fila2[2]}">
			if (b==1) Sel_Hijos[k] = '<c:if test="${requestScope.jornada_== fila2[0]}">SELECTED</c:if>';
			else
				Sel_Hijos[k] = '';id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k]='<c:out value="${fila[0]}"/>'; k++; </c:if>
			</c:forEach>Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
		var niv=combo_padre.options[combo_padre.selectedIndex].value;
		var val_padre = -1;
		for(var k=0;k<Padres.length;k++){
			if(Padres[k].id_Padre[0]==niv)
				val_padre=k;
		}
		if(val_padre!=-1){
			var no_hijos = Padres[val_padre].Hijos.length;
			//alert("no hijos "+no_hijos)
			for(i=0; i < no_hijos; i++){
				if(Padres[val_padre].Sel_Hijos[i] == "SELECTED"){
					combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED"); combo_hijo.selectedIndex = i+1;
				}else
					combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
			}
	}</c:if>
	b++;
}
function filtro3(combo_padre0,combo_padre,combo_hijo){
	borrar_combo(combo_hijo);
	<c:if test="${!empty requestScope.LogiGrado && !empty requestScope.LoginGrupof}">
	var Padres = new Array();
		<c:forEach begin="0" items="${requestScope.LogiGrado}" var="fila" varStatus="st">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
			<c:forEach begin="0" items="${requestScope.LoginGrupof}" var="fila2">
			<c:if test="${fila[0]==fila2[2]}">
			Sel_Hijos[k] = '';id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k]='<c:out value="${fila[2]}"/>|<c:out value="${fila[0]}"/>'; k++; </c:if>
			</c:forEach>Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
		var niv=combo_padre0.options[combo_padre0.selectedIndex].value +'|'+ combo_padre.options[combo_padre.selectedIndex].value;
		var val_padre = -1;
		for(var k=0;k<Padres.length;k++){
			if(Padres[k].id_Padre[0]==niv)
				val_padre=k;
		}
		if(val_padre!=-1){
			var no_hijos = Padres[val_padre].Hijos.length;
			//alert("no hijos "+no_hijos)
			for(i=0; i < no_hijos; i++){
				if(Padres[val_padre].Sel_Hijos[i] == "SELECTED"){
					combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED"); combo_hijo.selectedIndex = i+1;
				}else
					combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
			}
	}</c:if>
}
function c_c(){
	document.frmLogin.tipo.value=3;
	document.frmLogin.cmd.value=8;
	document.frmLogin.submit();
}
function buscarCierre(){
	document.frmLogin.tipo.value=4;
	document.frmLogin.nombregp.value=document.frmLogin.grupo.options[document.frmLogin.grupo.selectedIndex].text;
	document.frmLogin.submit();
}
function buscarCierre1(){
	<c:if test="${!empty requestScope.include}">	
		document.frmLogin.tipo.value=4;
		document.frmLogin.nombregp.value=document.frmLogin.grupo.options[document.frmLogin.grupo.selectedIndex].text;
		document.frmLogin.submit();
	</c:if>
}

function gruposelect(combo_padre0,combo_padre1,combo_padre,combo_hijo){
	borrar_combo(combo_hijo);
	var sed=combo_padre0.options[combo_padre0.selectedIndex].value;
	var jorn=combo_padre1.options[combo_padre1.selectedIndex].value;
	var grado=combo_padre.options[combo_padre.selectedIndex].value;
	var i=0;
	<c:if test="${!empty requestScope.LoginGrupo}">
	<c:forEach begin="0" items="${requestScope.LoginGrupo}" var="fila" >
	var sedsel='<c:out value="${fila[3]}"/>';
	var jorsel='<c:out value="${fila[4]}"/>';
	var gradosel='<c:out value="${fila[2]}"/>';
	var gru='<c:out value="${fila[0]}"/>';
	var grusel='<c:out value="${requestScope.grupo_}"/>';
	if(sed==sedsel && jorn==jorsel && grado==gradosel){
		if (grusel==gru){
			combo_hijo.options[i+1] = new Option('<c:out value="${fila[1]}"/>', gru,"SELECTED");combo_hijo.selectedIndex = i+1;
		}
		else
			combo_hijo.options[i+1] = new Option('<c:out value="${fila[1]}"/>', gru);
		i++;
	}
</c:forEach>
</c:if>
}

window.onload = function(){
	<c:if test="${empty requestScope.LogiGrado && empty requestScope.LoginGrupo}">
	document.frmLogin.inst.onchange();
	document.frmLogin.grado.onchange();
	</c:if>
	//document.frmLogin.grado.onchange();
	   }
</script>
</head>
<FORM ACTION='<c:url value="/siges/gestionAdministrativa/cierreVigencia/CierreVigencia.do"/>' METHOD="POST" name='frmLogin' >
<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_CIERRE_VIG}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="nombregp" value=''>
		<input type="hidden" name="inst" value='<c:out value="${requestScope.inst_}"/>'>
<table align='center' width="100%"  border="0" cellspacing="2" cellpadding="2">
					<CAPTION><b>Consulta Detallada Cierre de Vigencia</b></CAPTION>
					<tr>		
					  <td width="15%" bgcolor="#FFFFFF">
		            <input class='boton' name="cmd1" type="button"  class="boton" value="Consultar" onClick="buscarCierre()">
					  </td>
			  		</tr>
						<tr>
							<th bgcolor="#336699"><div align="center"><p class="style4">Sede:</p></div></th>
							<td><select name="sede" style='width:400px;' onChange='buscarCierre1()'>
							<option value='-1'>-- Todos --</option>
							<c:forEach begin="0" items="${requestScope.LoginSedescol}" var="fila"><option value='<c:out value="${fila[0]}"/>' '<c:if test="${requestScope.sede_==fila[0]}">' SELECTED '</c:if>'><c:out value="${fila[1]}"/></option></c:forEach>
							</select></td>
						</tr><tr>
							<th bgcolor="#336699"><div align="center"><p class="style4">Jornada:</p></div></th>
							<td><select name="jornada" style='width:120px;' onChange='buscarCierre1()'><option value='-1'>-- Todos --</option>
							<c:forEach begin="0" items="${requestScope.LoginJorcol}" var="fila"><option value='<c:out value="${fila[0]}"/>' '<c:if test="${requestScope.jornada_==fila[0]}">' SELECTED '</c:if>'><c:out value="${fila[1]}"/></option></c:forEach>
							</select>						
							</td>
						</tr>
						<tr>
							<th bgcolor="#336699"><div align="center"><p class="style4">Grado:</p></div></th>
							<td>
							<select name="grado" style='width:120px;' onChange='gruposelect(document.frmLogin.sede,document.frmLogin.jornada,document.frmLogin.grado,document.frmLogin.grupo);' ><option value='-1'>-- Todos --</option>
							<c:forEach begin="0" items="${requestScope.LogiGrado}" var="fila"><option value='<c:out value="${fila[0]}"/>' '<c:if test="${requestScope.grado_==fila[0]}">' SELECTED '</c:if>'><c:out value="${fila[1]}"/></option></c:forEach>
							</select>
							</td>
						</tr>
						<tr>
							<th bgcolor="#336699"><div align="center"><p class="style4">Grupo:</p></div></th>
							<td>	
								<select name="grupo" style='width:120px;' onChange='buscarCierre1()' ><option value='-1'>-- Todos --</option>
								</select>
							</td>
						</tr>
					</table>		
					</form>
					<script languaje='javaScript'>
					document.frmLogin.grado.onchange();
	</script>
					</html>
					<c:if test="${!empty requestScope.include}">
						<jsp:include page="/siges/gestionAdministrativa/cierreVigencia/FiltroCierre.jsp" flush="false" />	
					</c:if>