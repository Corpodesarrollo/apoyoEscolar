<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="filtroEst" class="siges.estudiante.beans.FiltroBean" scope="session"/><jsp:setProperty name="filtroEst" property="*"/>
<jsp:useBean id="filtroc" class="siges.boletines.beans.FiltroBeanReports" scope="session"/><jsp:setProperty name="filtroc" property="*"/>
<script language='javaScript'>
var nav4=window.Event ? true : false;
function acepteNumeros(eve){
				var key=nav4?eve.which :eve.keyCode;
				return(key<=13 || (key>=48 && key<=57));
			}
			
function c_c(){
	var myTabled = document.getElementById('tabladet');
	myTabled.style.display='block';
}
function c_v(){
	document.frmcertificado.filTipoDoc.value=document.frmHistoricoEst.pertipdocum.value;
	document.frmcertificado.id.value=document.frmHistoricoEst.pernumdocum.value;
	window.open("","ModuloReportes","width=800,height=500,menubar=yes,scrollbars=1").focus();
	document.frmcertificado.submit();
}
function c_a(){
	document.frmAcademica.id.value=document.frmHistoricoEst.pernumdocum.value;
	window.open("","ModulohisAcademica","width=800,height=500,menubar=yes,scrollbars=1");
	document.frmAcademica.submit();
}
function c_i(){
	document.frmAsistencia.id.value=document.frmHistoricoEst.pernumdocum.value;
	document.frmAsistencia.nombrecompleto.value='<c:out value="${requestScope.nomcompleto123}"/>';
	document.frmAsistencia.tiposervlet.value='3';
	window.open("","ModulohisAsistencia","width=800,height=500,menubar=yes,scrollbars=1");
	document.frmAsistencia.submit();
}
function cancelar(){
	document.frmHistoricoEst.pertipdocum.selectedIndex=0;
	document.frmHistoricoEst.pergrado.selectedIndex=0;
	document.frmHistoricoEst.colegio.selectedIndex=0;
	document.frmHistoricoEst.pernumdocum.value="";
	document.frmHistoricoEst.pertipdocum.disabled=false;
	document.frmHistoricoEst.pernumdocum.disabled=false;
	document.frmAux.tiposervlet.value='2';
	document.frmAux.submit();
}

function borrar_combo(combo){
	for(var i = combo.length - 1; i >= 0; i--) {
		if(navigator.appName == "Netscape") combo.options[i] = null;
		else combo.remove(i);
	}
	combo.options[0] = new Option("-- Seleccione uno --","-1");
}

function asignarColegios(combo_padre,combo_hijo,combo_hijo2){
	borrar_combo(combo_hijo);
	var rows = document.getElementById('tablares').getElementsByTagName('tr');
	var myLink = document.getElementById('detallelink');
	if(rows.length==2){
		var myTabled = document.getElementById('tablares');
		myTabled.deleteRow(-1);
		myLink.style.display='none';
		var myTabled2 = document.getElementById('tabladet');
		myTabled2.style.display='none';
	}
	var gradosel=document.frmHistoricoEst.pergrado.value;
	var codgrado;
	var i=0;
	<c:if test="${!empty requestScope.filtroGradoColegiohis}">
		<c:forEach begin="0" items="${requestScope.filtroGradoColegiohis}" var="fila2">
		codgrado='<c:out value="${fila2[6]}"/>';
			if(gradosel==codgrado){
				combo_hijo.options[i+1] = new Option('<c:out value="${fila2[1]}"/>','<c:out value="${fila2[0]}"/>');
				combo_hijo2.options[i+1] = new Option('<c:out value="${fila2[9]}"/>','<c:out value="${fila2[9]}"/>');i++;
			}
		</c:forEach>		
	</c:if>
}
var i=0;
function resultado(combo_padre,combo_hijo,combo_hijo2){
	var gradosel=combo_padre.value;
	var colsel=combo_hijo.value;
	combo_hijo2.selectedIndex=combo_hijo.selectedIndex;
	var vigenci=combo_hijo2.value;
	var codgrado;
	var codcol;
	var codvig;
	var myLink = document.getElementById('detallelink');
		var rows = document.getElementById('tablares').getElementsByTagName('tr');
		if(rows.length==2){
			var myTabled = document.getElementById('tablares');
			myTabled.deleteRow(-1);
			myLink.style.display='none';
			var myTabled2 = document.getElementById('tabladet');
			myTabled2.style.display='none';
		}
	<c:if test="${!empty requestScope.filtroGradoColegiohis}">
		<c:forEach begin="0" items="${requestScope.filtroGradoColegiohis}" var="fila2">
		codgrado='<c:out value="${fila2[6]}"/>';
		codcol='<c:out value="${fila2[0]}"/>';
		codvig='<c:out value="${fila2[9]}"/>';
			if(gradosel==codgrado && colsel==codcol && codvig==vigenci){
				var myTable = document.getElementById('tablares');
				myTable.style.display='block';
				myLink.style.display='block';
				var oRow = myTable.insertRow(-1);
				var oCell = oRow.insertCell(-1);
				oCell.align='center';
				oCell.innerHTML = '<c:out value="${fila2[1]}"/>';
				document.frmAux.col.value='<c:out value="${fila2[0]}"/>';
				var oCell2 = oRow.insertCell(-1);
				oCell2.align='center';
				oCell2.innerHTML = '<c:out value="${fila2[3]}"/>';
				document.frmAux.sede.value='<c:out value="${fila2[2]}"/>';
				var oCell3 = oRow.insertCell(-1);
				oCell3.align='center';
				oCell3.innerHTML = '<c:out value="${fila2[9]}"/>';
				document.frmcertificado.filVigencia.value='<c:out value="${fila2[9]}"/>';
				document.frmcertificado.tipoProm.value='<c:out value="${fila2[10]}"/>';
				var oCell4 = oRow.insertCell(-1);
				oCell4.align='center';
				oCell4.innerHTML = '<c:out value="${fila2[7]}"/>';
				document.frmAux.grado.value='<c:out value="${fila2[6]}"/>';
				var oCell5 = oRow.insertCell(-1);
				oCell5.align='center';
				oCell5.innerHTML = '<c:out value="${fila2[8]}"/>';
				document.frmAux.grupo.value='<c:out value="${fila2[8]}"/>';
				document.frmAsistencia.metod.value='<c:out value="${fila2[11]}"/>';
				document.frmAsistencia.vigencia.value='<c:out value="${fila2[9]}"/>';
				document.frmAsistencia.col.value='<c:out value="${fila2[1]}"/>';
				document.frmAsistencia.sede.value='<c:out value="${fila2[3]}"/>';
				document.frmAsistencia.grado.value='<c:out value="${fila2[7]}"/>';
				document.frmAsistencia.grupo.value='<c:out value="${fila2[8]}"/>';
			}
		</c:forEach>		
	</c:if>
}

function buscar(){
	//document.frmHistoricoEst.tipo.value='0';
	//document.frmHistoricoEst.cmd.value='buscar';
	var msg="";
	if (document.frmHistoricoEst.pertipdocum.selectedIndex!=0 && document.frmHistoricoEst.pernumdocum.value!=""){
		document.frmAux.tiposervlet.value='1';
		document.frmAux.tipodoc.value=document.frmHistoricoEst.pertipdocum.value;
		document.frmAux.numdoc.value=document.frmHistoricoEst.pernumdocum.value;
		document.frmAux.submit();
	}else{
		if(document.frmHistoricoEst.pertipdocum.selectedIndex==0)
			msg=msg+"- Tipo de Documento \n";
		if(document.frmHistoricoEst.pernumdocum.value=="")
			msg=msg+"- Número de documento \n";
		alert("Por favor verifique la siguiente información: \n\n"+msg);
	}
}
var bool=0;
var j=1;
</script>
<html>
        <head>
                <title>Historico Estudiante </title>
        </head>
        <body>
<form method="post" name="frmHistoricoEst" action='<c:url value="/siges/gestionAdministrativa/HistoricoEst/HistoricoEstudiante.do"/>'>
		<input type="hidden" name="tiposervlet" value='0'>
		<input type="hidden" name="nombrecompleto" value='<c:if test="${!empty requestScope.nomcompleto123}"><c:out value="${requestScope.nomcompleto123}"/></c:if>'>
		<table border="1" align="center" bordercolor="#FFFFFF" width="100%">
			<caption>Historico Estudiante</caption>
				<tr>
			  	<td>
						<input id="cmd1" name="cmd1" type="button" value="Buscar" onClick="buscar()" class="boton">
						<input id="cmd2" name="cmd2" type="button" value="Cancelar" onClick="cancelar()" class="boton">
			  	</td>
				</tr>
				<tr>
								<td>
									<span class="style2">*</span> Tipo de Documento:
								</td>
								<td colspan=3>
									<select name="pertipdocum" style='width:300px;' <c:if test="${!empty requestScope.filtroGradoColegiohis}">disabled</c:if>>
										<option value="-1">--seleccione uno--</option>
											<c:forEach begin="0" items="${requestScope.filtroTipoDocumiento}" var="fila">
												<option value="<c:out value="${fila[0]}"/>" <c:if test="${requestScope.tipodocbean==fila[0]}">SELECTED</c:if> >
												<c:out value="${fila[1]}"/></option>
											</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td>
									<span class="style2">*</span> Número de Documento:
								</td>
								<td colspan=3>
									<input type="text" name="pernumdocum" maxlength="50" <c:if test="${requestScope.numdocbean!=null && requestScope.numdocbean!=''}">value='<c:out value="${requestScope.numdocbean}"/>'</c:if> <c:if test="${!empty requestScope.filtroGradoColegiohis}">disabled</c:if>></input>
								</td>
							</tr>
							<tr>
								<td style='<c:if test="${empty requestScope.filtroGradoColegiohis}">display:none;</c:if>'>
								 Nombre Completo : 
								</td>
								<td colspan=3 style='<c:if test="${empty requestScope.filtroGradoColegiohis}">display:none;</c:if>'>
									<c:if test="${!empty requestScope.nomcompleto123}"><c:out value="${requestScope.nomcompleto123}"/></c:if>
								</td>
							</tr>
							<tr>
								<td style='<c:if test="${empty requestScope.filtroGradoColegiohis}">display:none;</c:if>'>
									<span class="style2">*</span> Grado:	&nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp <select name="pergrado" style='width:100px;text-align:right;' onChange="asignarColegios(document.frmHistoricoEst.pergrado,document.frmHistoricoEst.colegio,document.frmHistoricoEst.vig)">
									<option value="-1">--seleccione uno--</option>
									<c:forEach begin="0" items="${requestScope.filtroGradoColegiohis}" var="fila">
										<script language='javaScript'>
										for(var i = document.frmHistoricoEst.pergrado.length - 1; i >= 0; i--) {
											if(document.frmHistoricoEst.pergrado.options[i].value == '<c:out value="${fila[6]}"/>')
												bool=bool+1;
										}
										if(bool==0){
											document.frmHistoricoEst.pergrado.options[j] = new Option('<c:out value="${fila[7]}"/>','<c:out value="${fila[6]}"/>');j++;
										}
										</script>
									</c:forEach>
								</select>
								</td>
								<td style='<c:if test="${empty requestScope.filtroGradoColegiohis}">display:none;</c:if>'>
								<span class="style2">*</span> Colegio:
								</td>
								<td style='<c:if test="${empty requestScope.filtroGradoColegiohis}">display:none;</c:if>'>
								<select name="colegio" style='width:250px;' onchange="resultado(document.frmHistoricoEst.pergrado,document.frmHistoricoEst.colegio,document.frmHistoricoEst.vig)">
									<option value='-1'>-- Seleccione uno --</option>
								</select>
								</td>
								<td style="display:none">
								<select name="vig" style='width:250px;' >
									<option value='-1'>-- Seleccione uno --</option>
								</select>
								</td>
							</tr>
							<table border="0" align="center" width="100%" id="tablares" style='display:none;'>
							<caption>Ubicación</caption>
							<tr>
							<td align="center" bgcolor="#009BB2" width="35%">
							Colegio
							</td>
							<td align="center" bgcolor="#009BB2" width="25%">
							Sede
							</td>
							<td align="center" bgcolor="#009BB2">
							Año
							</td>
							<td align="center" bgcolor="#009BB2">
							Grado
							</td>
							<td align="center" bgcolor="#009BB2">
							Grupo
							</td>
							</tr>
							</table>
							<tr id="detallelink" style='display:none;'>
							<td align="center">
							<a href='<c:url value="javaScript:c_c()"/>' style="font-size:1.3em" style="font-weight:bold" >Ver Detalle</a>
							</td>
							</tr>
							<table border="0" align="center" width="50%" id="tabladet" style='display:none;'>
							<caption>Detalle</caption>
							<tr>
							<td align="left" bgcolor="#009BB2" width="20%">
							Certificado:
							</td>
							<td align="center" width="20%">
							<a href='<c:url value="javaScript:c_v()"/>' style="font-size:1.0em" style="font-weight:bold" >Generar Certificado</a>
							</td>
							</tr>
							<tr>
							<td align="left" bgcolor="#009BB2" width="20%">
							Información Básica: 
							</td>
							<td align="center" width="20%">
							<a href='<c:url value="javaScript:c_a()"/>' style="font-size:1.0em" style="font-weight:bold" >Ver Información Básica</a>
							</td>
							</tr>
							<tr>
							<td align="left" bgcolor="#009BB2" width="20%">
							Asistencia:
							</td>
							<td align="center" width="20%">
							<a href='<c:url value="javaScript:c_i()"/>' style="font-size:1.0em" style="font-weight:bold" >Ver Asistencia</a>
							</td>
							</tr>
							</table>
							<c:if test="${!empty requestScope.Validacioncolegio}"><script language='javaScript'>alert('<c:out value="${requestScope.Validacioncolegio}"/>');</script></c:if>
	  	</table>
</form>
<form method="post" name="frmAux" action='<c:url value="/siges/gestionAdministrativa/HistoricoEst/HistoricoEstudiante.do"/>'><input type="hidden" name="tiposervlet" value=""><input type="hidden" name="tipodoc" value=""><input type="hidden" name="numdoc" value=""><input type="hidden" name="vig" value=""><input type="hidden" name="col" value=""><input type="hidden" name="sede" value=""><input type="hidden" name="grado" value=""><input type="hidden" name="grupo" value=""></form>
<form method="post" name="frmcertificado" action='<c:url value="/boletines/FiltroGuardarCertificados.jsp"/>' target="ModuloReportes"><input type="hidden" name="id" value=""><input type="hidden" name="filTipoDoc" value=""><input type="hidden" name="filVigencia" value=""><input type="hidden" name="tipoProm" value=""></form>
<form method="post" name="frmAcademica" action='<c:url value="/estudiante/FiltroGuardar.jsp"/>' target="ModulohisAcademica"><input type="hidden" name="id" value=""><input type="hidden" name="modcon" value="1"></form>
<form method="post" name="frmAsistencia" action='<c:url value="/siges/gestionAdministrativa/HistoricoEst/HistoricoEstudiante.do"/>' target="ModulohisAsistencia"><input type="hidden" name="tiposervlet" value=""><input type="hidden" name="id" value=""><input type="hidden" name="metod" value=""><input type="hidden" name="vigencia" value=""><input type="hidden" name="col" value=""><input type="hidden" name="sede" value=""><input type="hidden" name="grado" value=""><input type="hidden" name="grupo" value=""><input type="hidden" name="nombrecompleto" value=""></form>
</body>
</html>