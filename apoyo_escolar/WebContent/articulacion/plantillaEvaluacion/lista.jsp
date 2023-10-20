<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="paramsVO" class="articulacion.plantillaEvaluacion.vo.ParamsVO" scope="page"/>
<html>
<c:import url="/parametros.jsp"/>
<head>
<script language="javaScript">
<!--
	function letra(forma,base){
		 var num,let,ini,fin;
		 if(forma.artEvaNotaNum){
			 if(forma.artEvaNotaNum.length){
				 for(var i=0;i<forma.artEvaNotaNum.length ;i++){
				 	num=parseFloat(forma.artEvaNotaNum[i].value);
				 	for(var j=0;j<base.letra.length ;j++){
				 		ini=parseFloat(base.inicio[j].value);
				 		fin=parseFloat(base.fin[j].value);
				 		if(num>=ini && num<=fin){
				 			forma.artEvaNotaConc[i].value=base.letra[j].value;
				 		}
				 	}
				 	if(forma.estado[i].value=="true"){
				 		forma.artEvaNotaNum[i].disabled=true;
				 		forma.artEvaEstado_.checked=true;
				 		forma.artEvaEstado_.disabled=true;
				 		forma.cmd1.disabled=true;
				 	}
				 }
			 }
		}
	}
	
	function cambioLetra(caja,oculto,base){
		 var num,let,ini,fin,max=0,min=0;
		 if(caja.value!=""){
		 	num=parseFloat(caja.value);
		 	for(var j=0;j<base.letra.length ;j++){
		 		ini=parseFloat(base.inicio[j].value);
		 		fin=parseFloat(base.fin[j].value);
		 		if(fin>max){
		 			max=fin;
		 		}
		 		if(ini<min){
		 			min=ini;
		 		}
		 		if(num>=ini && num<=fin){
		 			document.frmLista.artEvaNotaConc[oculto-1].value=base.letra[j].value;
		 			document.getElementById(oculto).innerHTML=base.letra[j].value;
		 		}
		 	}
		 }else{
	 		document.frmLista.artEvaNotaConc[oculto-1].value="p";
	 		document.getElementById(oculto).innerHTML="....";
 		}
		 if(num>max||min>num){
		 	alert("Valor fuera del rango permitido");
		 	document.frmLista.artEvaNotaConc[oculto-1].value="p";
		 	document.frmLista.artEvaNotaNum[oculto-1].value="";
		 	document.getElementById(oculto).innerHTML="....";
		 }
		 
	}
    
    
	function guardar(){
	//	copiarChecks(document.frmLista);
		if(document.frmLista.artEvaEstado_.checked==true){
			document.frmLista.artEvaEstado.value='true';
		}else{
			document.frmLista.artEvaEstado.value='false';
		}
		document.frmLista.cmd.value=document.frmLista.INSERTAR.value;
		document.frmLista.submit();

	}
//-->
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
<div style="width:100%;overflow:auto;vertical-align:top:50px;background-color:#ffffff;">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" ><c:import url="/articulacion/plantillaEvaluacion/Filtro.do"/></td></tr>
	</table>
	<div style="width:100%;height:190px;overflow:auto;vertical-align:top;background-color:#ffffff;">
	<form action='<c:url value="/articulacion/plantillaEvaluacion/Save.jsp"/>' method="post" name="frmLista" onSubmit="return validarForma(frmLista)">
		<input type="hidden" name="cmd" value=''>
		<input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_DEFAULT}"/>'>
		<input type="hidden" name="INSERTAR" value='<c:out value="${paramsVO.CMD_INSERTAR}"/>'>
		<input type="hidden" name="artEvaCodInst" value='<c:out value="${sessionScope.login.instId}"/>'>
		<input type="hidden" name="artEvaCodMetod" value='<c:out value="${sessionScope.login.metodologiaId}"/>'>
		<input type="hidden" name="artEvaAnVigencia" value='<c:out value="${sessionScope.FilEvaluacionPVO.anVigencia}"/>'>
		<input type="hidden" name="artEvaPerVigencia" value='<c:out value="${sessionScope.FilEvaluacionPVO.perVigencia}"/>'>
		<input type="hidden" name="artEvaCodAsig" value='<c:out value="${sessionScope.FilEvaluacionPVO.asignatura}"/>'>
		<input type="hidden" name="artEvaCodPrueba" value='<c:out value="${sessionScope.FilEvaluacionPVO.prueba}"/>'>
		<input type="hidden" name="artEvaCodEstado" value='1'>
		<table border="0" align="center" width="95%" cellpadding="0" cellspacing="0">
		 	<c:if test="${empty sessionScope.listaEstudiantesVO}"></c:if>
			<c:if test="${!empty requestScope.plantilla && requestScope.plantilla!='No'}">
					<table border="0" align="center" bordercolor="#FFFFFF" width="100%">
					<tr>
						<td colspan="6" align='center' valign="top">
							La plantilla fue generada satisfactoriamente.<br>Pulse en el Icono para descargar el archivo.<br>
							<a href='<c:url value="/${requestScope.rutaDescarga}"><c:param name="tipo" value="xls"/></c:url>' target='_blank'><img border=0 src='<c:url value="/etc/img/xls.gif"/>'></a>
						</td>
					</tr>
					</table>
			</c:if>
		</table>
	</form>
	<form name="rangos">
		<c:forEach begin="0" items="${requestScope.rangosVO}" var="rangos" varStatus="st">
			<input type="hidden" name="letra" value="<c:out value="${rangos.conceptual}"/>">
			<input type="hidden" name="inicio" value="<c:out value="${rangos.inicio}"/>">
			<input type="hidden" name="fin" value="<c:out value="${rangos.fin}"/>">
		</c:forEach>
	</form>
</body>
<script>
	//letra(document.frmLista,document.rangos);
</script>
</html>