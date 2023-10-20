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
function borrar_combo(combo){
	for(var i = combo.length - 1; i >= 0; i--) {
		if(navigator.appName == "Netscape") combo.options[i] = null;
			else combo.remove(i);
	}
	combo.options[0] = new Option("-- Seleccione uno --","-1");
	
}
function Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre){
	this.id_Hijos= id_Hijos; 
	this.Hijos= Hijos; 
	this.Sel_Hijos= Sel_Hijos;
	this.id_Padre=id_Padre;
}
function filtro2(combo_padre,combo_hijo,combo_hijo2,combo_hijo3,combo_hijo4){
	borrar_combo(combo_hijo);
	borrar_combo(combo_hijo2);
	borrar_combo(combo_hijo3);
	borrar_combo(combo_hijo4);
	//document.getElementById("mns").value="recorrio "+i++;
	<c:if test="${!empty requestScope.LoginInst && !empty requestScope.LoginSedescol}">
	var Padres = new Array();
		<c:forEach begin="0" items="${requestScope.LoginInst}" var="fila" varStatus="st">
		id_Hijos=new Array();
		Hijos= new Array();
		Sel_Hijos= new Array();
		id_Padre= new Array();
		var k=0;
		<c:forEach begin="0" items="${requestScope.LoginSedescol}" var="fila2">
			<c:if test="${fila[0]==fila2[2]}">
			if (t==1)
				Sel_Hijos[k] = '<c:if test="${requestScope.sede_== fila2[0]}">SELECTED</c:if>';
			else
				Sel_Hijos[k] = '';
			id_Hijos[k] = '<c:out value="${fila2[0]}"/>';
			Hijos[k] = '<c:out value="${fila2[1]}"/>';
			id_Padre[k] = '<c:out value="${fila2[2]}"/>';
			k++;
			</c:if>
			</c:forEach>
			Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);
			</c:forEach>
		var niv=combo_padre.options[combo_padre.selectedIndex].value;
		var val_padre = -1;
		for(var k=0;k<Padres.length;k++){
			if(Padres[k].id_Padre[0]==niv){
				val_padre=k;
			}
		}
		if(val_padre!=-1){
			var no_hijos = Padres[val_padre].Hijos.length;
			for(i=0; i < no_hijos; i++){
				if(Padres[val_padre].Sel_Hijos[i] == "SELECTED"){
					combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED");
					combo_hijo.selectedIndex = i+1;
				}else
					combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
			}
	}</c:if>
	<c:if test="${!empty requestScope.LoginInst && !empty requestScope.LogiGrado}">
	var Padres = new Array();
		<c:forEach begin="0" items="${requestScope.LoginInst}" var="fila" varStatus="st">
		id_Hijos=new Array();
		Hijos= new Array();
		Sel_Hijos= new Array();
		id_Padre= new Array();
		var k=0;
		<c:forEach begin="0" items="${requestScope.LogiGrado}" var="fila2">
			<c:if test="${fila[0]==fila2[2]}">
			Sel_Hijos[k] = '';
			id_Hijos[k] = '<c:out value="${fila2[0]}"/>';
			Hijos[k] = '<c:out value="${fila2[1]}"/>';
			id_Padre[k] = '<c:out value="${fila2[2]}"/>';
			k++;
			</c:if>
			</c:forEach>
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
					combo_hijo3.options[i+1] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED");
					combo_hijo3.selectedIndex = i+1;
				}else
					combo_hijo3.options[i+1] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
			}
	}</c:if>
	t++;
}
function filtro(combo_padre0,combo_padre,combo_hijo){
	borrar_combo(combo_hijo);
	<c:if test="${!empty requestScope.LoginSedescol && !empty requestScope.LoginJorcol}">var Padres = new Array();
		<c:forEach begin="0" items="${requestScope.LoginSedescol}" var="fila" varStatus="st">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
			<c:forEach begin="0" items="${requestScope.LoginJorcol}" var="fila2">
			<c:if test="${fila[0]==fila2[2] && fila[2]==fila2[3]}">
			if (b==1) Sel_Hijos[k] = '<c:if test="${requestScope.jornada_== fila2[0]}">SELECTED</c:if>';
			else
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
	b++;
}
function filtro3(combo_padre0,combo_padre,combo_hijo){
	borrar_combo(combo_hijo);
	<c:if test="${!empty requestScope.LogiGrado && !empty requestScope.LoginGrupo}">
	var Padres = new Array();
		<c:forEach begin="0" items="${requestScope.LogiGrado}" var="fila" varStatus="st">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
			<c:forEach begin="0" items="${requestScope.LoginGrupo}" var="fila2">
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
	document.frmLogin.tipo.value=5;
	document.frmLogin.submit();
}
function buscarPromo(){
	document.frmLogin.tipo.value=4;
	document.frmLogin.submit();
}
window.onload = function(){
	<c:if test="${empty requestScope.LogiGrado && empty requestScope.LoginGrupo}">
	document.frmLogin.inst.onchange();
	</c:if>
	document.frmLogin.grado.onchange();
	   }
</script>
</head>
<FORM ACTION='<c:url value="/siges/gestionAcademica/promocion/PromocionGuardar.jsp"/>' METHOD="POST" name='frmLogin' >
<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_CIERRE_VIG}"/>'>
		<input type="hidden" name="cmd" value=''>
<table align='center' width="100%"  border="0" cellspacing="2" cellpadding="2">
					<CAPTION><b>Consulta De Promoción</b></CAPTION>
					<tr>		
					  <td width="15%" bgcolor="#FFFFFF">
		            <input class='boton' name="cmd1" type="button"  class="boton" value="Consultar" onClick="buscarPromo()">
					  </td>
			  		</tr>
						<tr>
							<th bgcolor="#336699"><div align="center"><p class="style4">Colegio:</p></div></th>
							<td>
								<select name="inst" style='width:400px;' onChange='filtro2(document.frmLogin.inst,document.frmLogin.sede,document.frmLogin.jornada,document.frmLogin.grado,document.frmLogin.grupo)'>
									<option value='-1'>-- Seleccione uno --</option>
									<c:forEach begin="0" items="${requestScope.LoginInst}" var="fila"><option value='<c:out value="${fila[0]}"/>' '<c:if test="${requestScope.inst_==fila[0]}">' SELECTED '</c:if>'><c:out value="${fila[1]}"/></option></c:forEach>
								</select>
							</td>	
						</tr><tr>
							<th bgcolor="#336699"><div align="center"><p class="style4">Sede:</p></div></th>
							<td><select name="sede" style='width:400px;' onChange='filtro(document.frmLogin.inst,document.frmLogin.sede,document.frmLogin.jornada)'><option value='-1'>-- Seleccione uno --</option>
							</select></td>
						</tr><tr>
							<th bgcolor="#336699"><div align="center"><p class="style4">Jornada:</p></div></th>
							<td><select name="jornada" style='width:120px;' onChange="javaScript:c_c()" ><option value='-1'>-- Seleccione uno --</option></select>						
							</td>
						</tr>
						<tr>
							<th bgcolor="#336699"><div align="center"><p class="style4">Grado:</p></div></th>
							<td>
							<select name="grado" style='width:120px;' onChange='filtro3(document.frmLogin.inst,document.frmLogin.grado,document.frmLogin.grupo)'><option value='-1'>-- Seleccione uno --</option></select>
							</td>
						</tr>
						<tr>
							<th bgcolor="#336699"><div align="center"><p class="style4">Grupo:</p></div></th>
							<td>	
								<select name="grupo" style='width:120px;' ><option value='-1'>-- Seleccione uno --</option></select>
							</td>
						</tr>
					</table>		
					</table>
					</form>
					<script languaje='javaScript'>
	document.frmLogin.inst.onchange();
	document.frmLogin.sede.onchange();
	</script>
					</html>