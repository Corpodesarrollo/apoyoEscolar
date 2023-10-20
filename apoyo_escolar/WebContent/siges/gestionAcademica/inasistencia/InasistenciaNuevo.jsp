<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/parametros.jsp"/>
<jsp:useBean id="paramsVO" class="siges.gestionAcademica.inasistencia.vo.ParamVO" scope="page"/>
<html>
<head>
		<script language="JavaScript">
			<!--
			var soloLectura='<c:out value="${sessionScope.NivelPermiso}"/>';
			var nav4=window.Event ? true : false;

			function cancelar(){
				parent.location.href='<c:url value="/bienvenida.jsp"/>';
			}
			
			function hacerValidaciones_frmNuevo(forma){
			}
			
			function guardar(){
				if(validarForma(document.frmNuevo)){
						document.frmNuevo.cmd.value='<c:out value="${paramsVO.CMD_GUARDAR}"/>';
						document.frmNuevo.submit();
				}
			}

			function cambio(forma,indice){
				if(forma.falla[indice].checked==true){
					forma.falla[indice].value=forma.falla2[indice].value;
				}else{
					forma.falla[indice].value='-';
				}
			}
			
						
			function remoto_(forma,indice,dia){
		 
				var inst='<c:out value="${sessionScope.filtroInasistencia.filInstitucion}"/>';
				var metod='<c:out value="${sessionScope.filtroInasistencia.filMetodologia}"/>';
				var gra='<c:out value="${sessionScope.filtroInasistencia.filGrado}"/>';
				var gru='<c:out value="${sessionScope.filtroInasistencia.filJerarquia}"/>';
				var asig='<c:out value="${sessionScope.filtroInasistencia.filAsignatura}"/>';
				var fr = document.frmPopUp;
				 
				if(forma.falla[indice].disabled==true){
					alert('No puede cambiar el motivo, no hay materias en el horario para este dia')
					return;
				}
				 
				if(forma.falla[indice].checked==false){
					alert('Debe seleccionar la casilla para poder escoger el motivo')
					return;
				}
				
				 
				//frmPopUp
				try{
				remote = window.open("","3","width=350,height=400, resizable=no,toolbar=no,directories=no,menubar=no,status=yes");
				remote.moveTo(200,200);
				alert('ventana 5');
				
				fr.target='_self';
				fr.param[0].value=inst;
				fr.param[1].value=metod;
				fr.param[2].value=gra;
				fr.param[3].value=gru;
				fr.param[4].value=dia;
				fr.param[5].value=indice;
				fr.param[6].value=asig;
				alert('ventana 7');
				
				alert('ventana 8');
				if(remote.opener == null) remote.opener = window;
				remote.opener.name = "centro";
				remote.focus();
				}catch(e){
				  alert('Error de JavaScript: ' + e);
				 }
				 fr.submit();
				 document.frmModelos.target = '_self';
			}
			
			
			
			function remoto(forma,indice,dia){
		 
				var inst='<c:out value="${sessionScope.filtroInasistencia.filInstitucion}"/>';
				var metod='<c:out value="${sessionScope.filtroInasistencia.filMetodologia}"/>';
				var gra='<c:out value="${sessionScope.filtroInasistencia.filGrado}"/>';
				var gru='<c:out value="${sessionScope.filtroInasistencia.filJerarquia}"/>';
				var asig='<c:out value="${sessionScope.filtroInasistencia.filAsignatura}"/>';
				var fr = document.frmPopUp;
				 
				if(forma.falla[indice].disabled==true){
					alert('No puede cambiar el motivo, no hay materias en el horario para este dia')
					return;
				}
				 
				if(forma.falla[indice].checked==false){
					alert('Debe seleccionar la casilla para poder escoger el motivo')
					return;
				}
				
				 
				//frmPopUp
		 

				var auxi = document.frmPopUp.actionPopup.value;
			
				//remote = window.open(auxi,'3','width=350,height=450,resizable=no,toolbar=no,directories=no,menubar=no,status=yes')
			    remote = window.open(auxi,"3","width=350,height=400, resizable=no,toolbar=no,directories=no,menubar=no,status=yes");
			    document.frmPopUp.target = '3';
				remote.moveTo(200,200);
			
				
				fr.target='3';
				fr.param[0].value=inst;
				fr.param[1].value=metod;
				fr.param[2].value=gra;
				fr.param[3].value=gru;
				fr.param[4].value=dia;
				fr.param[5].value=indice;
				fr.param[6].value=asig;
				
				if(remote.opener == null) remote.opener = window;
				remote.opener.name = "centro";
				
				remote.focus();
			 
				 fr.submit();
				 document.frmPopUp.target = '_self';
			}
			//-->
		</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">

	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:160px;overflow:auto;vertical-align:top;background-color:#ffffff;"><c:import url="/inasistencia/Filtro.do"/></div>
		</td></tr>
	</table>
	<FORM ACTION='<c:url value="/siges/gestionAcademica/inasistencia/Save.jsp"/>' METHOD="POST" name='frmNuevo' onSubmit="return validarForma(frmNuevo)">
		<input type="hidden" name="cmd">
		<INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_INASISTENCIA}"/>'>
		<c:if test="${empty requestScope.lEstudiante}">
	    <table border="0" align="center" width="100%">
			<tr><th>No hay estudiantes a evaluar<th></tr>
		</table>
		</c:if>
		<c:if test="${!empty requestScope.lEstudiante}">
			<table style="display:" id='t1' name='t1' width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
			<caption>Registro de inasistencia</caption>
				<tr>
			    <td colspan="6">
						<input class='boton' id="cmd0" name="cmd0" type="button" value="Guardar" style="display:" onClick="javaScript:guardar()">
					</td>
					<td><span class="style2">Nota:</span> Las casillas que estan bloqueadas indican que no hay asignaturas ese día</td>
				</tr>
			</table>
	    <table style="display:" id='t2' name='t2' width="100%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC">
				<tr>
					<th class="EncabezadoColumna" colspan='3'>Datos de estudiante</th>
					<th class="EncabezadoColumna" colspan='<c:out value="${sessionScope.filtroInasistencia.filTotalDias}"/>'>Mes<br></th>
				</tr>
				<tr>
					<th class="EncabezadoColumna" width='1%'>&nbsp;</th>
					<th class="EncabezadoColumna" width='23%'>Apellidos</th>
					<th class="EncabezadoColumna" width='20%'>Nombres</th>
					<c:forEach begin="0" items="${lDias}" var="dia" >
						<th class="EncabezadoColumna" width='5%'><c:out value="${dia.nombre}"/><br>(<c:out value="${dia.codigo}"/>)</th>
					</c:forEach>
				</tr>
				<c:set var="indice" value="-1" scope="page"/>
				<c:forEach begin="0" items="${requestScope.lEstudiante}" var="estudiante" varStatus="st2">
				<tr>
					<td class='Fila<c:out value="${st2.count%2}"/>' align='center'><b><c:out value="${estudiante.evalConsecutivo}"/></b></td>
					<td class='Fila<c:out value="${st2.count%2}"/>' ><c:out value="${estudiante.evalApellido}"/></td>
					<td class='Fila<c:out value="${st2.count%2}"/>' ><c:out value="${estudiante.evalNombre}"/></td>
					<c:forEach begin="0" items="${estudiante.evalFallas}" var="falla" varStatus="st3"><c:set var="indice" value="${pageScope.indice+1}" scope="page"/>
						<th class='Fila<c:out value="${st2.count%2}"/>'><input type="hidden" name="falla2" value='<c:out value="${estudiante.evalCodigo}"/>|<c:out value="${falla.faDia}"/>|<c:out value="${falla.faMotivo}"/>|<c:out value="${falla.faAsig}"/>'><input type="checkbox" onclick='javaScript:cambio(document.frmNuevo,<c:out value="${pageScope.indice}"/>);' name="falla" <c:if test="${falla.faChecked==1}">value='<c:out value="${estudiante.evalCodigo}"/>|<c:out value="${falla.faDia}"/>|<c:out value="${falla.faMotivo}"/>|<c:out value="${falla.faAsig}"/>' CHECKED</c:if> <c:if test="${falla.faDisabled==1}">DISABLED</c:if>> <a href='javaScript: remoto(document.frmNuevo,<c:out value="${pageScope.indice}"/>,<c:out value="${falla.faDiaSemana}"/>);'><img src='<c:url value="/etc/img/z.gif"/>' border='0'></a></th>
					</c:forEach></tr>
					<c:if test="${st2.count%10==0}"><th class="EncabezadoColumna" colspan="3">&nbsp;</th><c:forEach begin="0" items="${lDias}" var="dia"><th class="EncabezadoColumna" width='5%'><c:out value="${dia.nombre}"/><br>(<c:out value="${dia.codigo}"/>)</th></c:forEach></c:if>
					</c:forEach>
					<tr><th class="EncabezadoColumna" colspan="3">&nbsp;</th><c:forEach begin="0" items="${lDias}" var="dia"><th class="EncabezadoColumna" width='5%'><c:out value="${dia.nombre}"/><br>(<c:out value="${dia.codigo}"/>)</th></c:forEach></tr>
			</table>
		</c:if>
		</form>
		
		
		<FORM action='<c:url value="/inasistencia/PopUp.do"/>' METHOD="POST" name='frmPopUp'>
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_INASISTENCIA}"/>'>
		<input type="hidden" name="ext" value='1'>
			<input type="hidden" name="actionPopup" value='<c:url value="/inasistencia/PopUp.do"/>'>
		<input type="hidden" name="param"><input type="hidden" name="param"><input type="hidden" name="param">
		<input type="hidden" name="param"><input type="hidden" name="param"><input type="hidden" name="param">
		<input type="hidden" name="param">
		</form>
</body>
</html>		
