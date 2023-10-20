<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="observacionGrupoVO" class="siges.observacion.vo.ObservacionGrupoVO" scope="session"/>
<jsp:useBean id="paramsVO" class="siges.observacion.vo.ParamsVO" scope="page"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<c:import url="/parametros.jsp"/>
<script language="javaScript">
<!--
	function lanzarServicio(n){
		document.frmLista.action='<c:url value="/grupoPeriodo/ControllerAbrirEdit.do"/>';  	
		document.frmLista.tipo.value=n;
		document.frmLista.target="";
		document.frmLista.submit();
	}
	function lanzar(n){
		document.frmLista.action='<c:url value="/observacion/Nuevo.do"/>';  	
		document.frmLista.tipo.value=n;
		document.frmLista.cmd.value='<c:out value="${paramsVO.CMD_NUEVO}"/>';
		document.frmLista.target="";
		document.frmLista.submit();
	}

	function buscar(){
		if(validarForma(document.frmLista)){
			document.frmLista.cmd.value=document.frmLista.BUSCAR.value;
			document.frmLista.submit();
		}
	}
	function hacerValidaciones_frmLista(forma){
		validarLista(forma.obsSede, "- Sede", 1)
		validarLista(forma.obsJornada, "- Jornada", 1)
		validarLista(forma.obsMetodologia, "- Metodologia", 1)
		validarLista(forma.obsPeriodo, "- Periodo", 1)
	}
	
	function Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre){
		this.id_Hijos= id_Hijos; this.Hijos= Hijos; this.Sel_Hijos= Sel_Hijos; this.id_Padre=id_Padre;
	}

	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) {
			if(navigator.appName == "Netscape") combo.options[i] = null;
			else combo.remove(i);
		}
		combo.options[0] = new Option("--Seleccione una--","-99");
	}
	
			function filtro(combo_padre,combo_hijo){
					borrar_combo(combo_hijo); 
					<c:if test="${!empty requestScope.listaSede && !empty requestScope.listaJornada}">
						var Padres = new Array();
						<c:forEach begin="0" items="${requestScope.listaSede}" var="sede" varStatus="st">
							id_Hijos=new Array();  Hijos= new Array();  Sel_Hijos= new Array();  id_Padre= new Array();  var k=0;
							<c:forEach begin="0" items="${requestScope.listaJornada}" var="jornada">
								<c:if test="${sede.codigo==jornada.padre}"> Sel_Hijos[k] = '<c:if test="${sessionScope.observacionGrupoVO.obsJornada==jornada.codigo}">SELECTED</c:if>'; id_Hijos[k] = '<c:out value="${jornada.codigo}"/>';  Hijos[k] = '<c:out value="${jornada.nombre}"/>'; id_Padre[k] = '<c:out value="${jornada.padre}"/>'; k++;</c:if>
							</c:forEach>Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
						var niv=combo_padre.options[combo_padre.selectedIndex].value;
						var val_padre = -99;
						for(var k=0;k<Padres.length;k++){
							if(Padres[k].id_Padre[0]==niv) val_padre=k;							
						}
						if(val_padre!=-99){ var no_hijos = Padres[val_padre].Hijos.length;
							for(i=0; i < no_hijos; i++){
								if(Padres[val_padre].Sel_Hijos[i] == "SELECTED"){
									combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED"); combo_hijo.selectedIndex = i+1;
								}else
									combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
							}
						}
				</c:if>}

//-->
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form action='<c:url value="/observacion/Save.jsp"/>' method="post" name="frmLista" onSubmit="return validarForma(frmLista)">
		<INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_OBSERVACION_GRUPO}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
		<input type="hidden" name="obsInstitucion" value='<c:out value="${sessionScope.login.instId}"/>'>
		<table border="0" align="center" width="95%" cellpadding="2" cellspacing="0">
			<caption>OBSERVACIÓN POR GRUPO - FILTRO DE BÚSQUEDA</caption>
			<tr><td width="45%">
	       <input name="cmd1" type="button" value="Buscar" onClick="buscar()" class="boton">
		  </td></tr>	
	  </table>
		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1">
				<td width="10">&nbsp;</td>
				<td rowspan="2" width="520">
					<a href="javaScript:lanzarServicio(1)"><img src="../etc/img/tabs/abrir_grupo_0.gif" height="26" border="0"></a>
					<c:if test="${sessionScope.login.perfil!=421}"><a href="javaScript:lanzarServicio(2)"><img src="../etc/img/tabs/cerrar_periodo_0.gif" alt="Cerrar Periodo"  height="26" border="0"></a></c:if>
					<a href='javaScript:lanzar(<c:out value="${paramsVO.FICHA_OBSERVACION_PERIODO}"/>)'><img src="../etc/img/tabs/observacion_periodo_0.gif" alt="Observacion periodo"  height="26" border="0"></a>
					<img src='<c:url value="/etc/img/tabs/observacion_grupo_1.gif"/>' alt="Observacion Grupo" height="26" border="0">
					</td>
				</tr>
		</table>
		<table border="0" align="center" width="95%" cellpadding="2" cellspacing="0">
			<tr>
				<td><span class="style2">*</span><b>&nbsp;Sede:</b></td>
				<td colspan="3">
					<select name="obsSede" style='width:350px;' onChange='filtro(document.frmLista.obsSede,document.frmLista.obsJornada)'>
						<option value="-99">--Seleccione una--</option>
						<c:forEach begin="0" items="${requestScope.listaSede}" var="sede">
							<option value="<c:out value="${sede.codigo}"/>" <c:if test="${sede.codigo==sessionScope.observacionGrupoVO.obsSede}">selected</c:if>><c:out value="${sede.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td><span class="style2">*</span><b>&nbsp;Jornada:</b></td>
				<td>
					<select name="obsJornada" style='width:100px;'>
						<option value="-99">--Seleccione una--</option>
					</select>
				</td>
				<td><span class="style2">*</span><b>&nbsp;Metodologia:</b></td>
				<td colspan="3">
					<select name="obsMetodologia" style='width:120px;'>
						<option value="-99">--Seleccione una--</option>
						<c:forEach begin="0" items="${requestScope.listaMetodologia}" var="metod">
							<option value="<c:out value="${metod.codigo}"/>" <c:if test="${metod.codigo==sessionScope.observacionGrupoVO.obsMetodologia}">selected</c:if>><c:out value="${metod.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td><span class="style2">*</span>Periodo:</td>
				<td>
						<select name="obsPeriodo" style='width:100px;'>
							<option value="-99">-Seleccione uno-</option>
							<c:forEach begin="0" items="${requestScope.listaPeriodo}" var="per">
								<option value="<c:out value="${per.codigo}"/>" <c:if test="${per.codigo==sessionScope.observacionGrupoVO.obsPeriodo}">selected</c:if>><c:out value="${per.nombre}"/></option>
							</c:forEach>
						</select>
				</td>
			</tr>
		</table>		
	</form>
</body>
<script>
filtro(document.frmLista.obsSede,document.frmLista.obsJornada);
</script>
</html>