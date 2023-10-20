<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="Encuesta2VO" class="articulacion.artEncuesta.vo.Encuesta2VO" scope="session"/>
<jsp:useBean id="paramsVO" class="articulacion.artEncuesta.vo.ParamsVO" scope="page"/>
<c:import url="/articulacion/artEncuesta/Filtro.do"><c:param name="tipo" value="2"/><c:param name="cmd" value="${paramsVO.CMD_NUEVO}"/></c:import>
<html>
<head>
<c:import url="/parametros.jsp"/>
<SCRIPT language='javascript' src='<c:url value="/etc/js/graphs.js"/>'></SCRIPT>
<script language="javaScript">
<!--
	var nav4=window.Event ? true : false;
	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57) || key==46);
	}
	
	function guardar(){
		if(validarForma(document.frmNuevo)){
			setValores(document.frmNuevo);
			document.frmNuevo.cmd.value=document.frmNuevo.DESCARGAR.value;
			document.frmNuevo.submit();
		}
	}
	
	function setValores(forma){
		var nota=0;
		if(forma.plaNota){
				if(forma.plaNota.length){
					for(var i=0;i<forma.plaNota.length;i++){
						if((trim(forma.plaNota[i].value)).length==0){
							nota=-99;
						}else{
							nota=parseFloat(trim(forma.plaNota[i].value));
						}
						forma.plaData[i].value=forma.plaData[i].value+nota;
					}
				}
			}
	 }
	
	function hacerValidaciones_frmNuevo(forma){
		if(forma.plaNota){
				if(forma.plaNota.length){
					for(var i=0;i<forma.plaNota.length;i++){
						validarFloat(forma.plaNota[i], "- Nota debe estar en el rango 0 y "+forma.plaNotaMax.value, 0,forma.plaNotaMax.value)
					}
				}
			}
	}
//-->

</script>


</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form action='<c:url value="/articulacion/artEncuesta/Filtro.do"/>' method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)">
		<INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="2"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="modulo" value='49'>
		<input type="hidden" name="DESCARGAR" value='<c:out value="${paramsVO.CMD_DESCARGAR}"/>'>
		<table border="0" align="center" width="95%" cellpadding="2" cellspacing="0">
			<caption>CONSOLIDADO ESTUDIANTES - ENCUESTA</caption>
			<tr><td width="45%">
        <input name="cmd1" type="button" value="Generar Reporte" onClick="guardar()" class="boton">
		  </td></tr>	
	  </table>
	  <table border="0" align="center" width="95%" cellpadding="2" cellspacing="0">
			<tr><td class="caption">CANTIDAD DE ESTUDIANTES: <c:out value="${sessionScope.encuesta2VO.totalEstudiantes}"/></td></tr>
		</table>	
		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray">
			<tr>
				<th class="EncabezadoColumna">Estado</th>
				<th class="EncabezadoColumna">Cantidad</th>
				<th class="EncabezadoColumna">Porcentaje</th>				
			</tr>
			<script>var valores="";
					var nombresLabels="";
			</script>
			<c:forEach begin="0" items="${requestScope.listaEstado}" var="con">
				<tr>
					<!-- <td><input type="hidden" name="plaEst" value='<c:out value="${est.estCodigo}"/>'><c:out value="${est.estConsecutivo}"/></td> -->
					<td><c:out value="${con.conEstado}"/></td>
					<script>nombresLabels=nombresLabels+<c:out value="${con.conNumero}"/>+",";</script>
					<td align="center"><c:out value="${con.conNumero}"/></td>
					<script>valores=valores+<c:out value="${con.conNumero}"/>+",";</script>
					<td align="center"><c:out value="${con.conPorcentaje}"/> %</td>					
				</tr>
			</c:forEach>
		</table>		
		<hr>
		<br>
		<div id="divGraph">
		<div id = "divGraph" align="center">
	<script>
	valores=valores.substring(0,valores.lastIndexOf(","));
	nombresLabels=nombresLabels.substring(0,nombresLabels.lastIndexOf(","));
	graph = new BAR_GRAPH("vBar");
	graph.labels = nombresLabels;
	graph.values = valores;
	graph.legend = "Estudiantes";
	graph.graphBGColor = "#B0E0B0";
	graph.graphBorder = "1px solid green";
	graph.graphPadding = 10;
	graph.barColors = "#FF9900,#80D080";//#FF9900 #C0D0C0,#80D080
	graph.barBGColor = "#D0F0D0";
	graph.labelColor = "black";
	graph.labelBGColor = "#60C060";
	graph.absValuesColor = "black";
	graph.absValuesBGColor = "#60C060";
	graph.legendColor = "#00A000";
	graph.legendBGColor = "#D0F0D0";
	graph.showValues = 4;
	document.write(graph.create());
	</script>
	</div>
	</div>
</form>
</body>
</html>