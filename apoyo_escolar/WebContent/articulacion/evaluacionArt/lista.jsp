<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="paramsVO" class="articulacion.evaluacionArt.vo.ParamsVO" scope="page"/>
<html>
<c:import url="/parametros.jsp"/>
<head>
<script language="javaScript">

	var nav4=window.Event ? true : false;
	var min=0,max=0,cantEst=0;

	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key==46 || key<=13 || (key>=48 && key<=57));
	}
	
	function lanzarServicio(n){
		if(n==0){//linea
			document.frmFiltro.action='<c:url value="/articulacion/evaluacionArt/Lista.do"/>';
  	}
		if(n==1){//plantilla
			document.frmFiltro.action='<c:url value="/articulacion/plantillaEvaluacion/Lista.do"/>';  	
  	}
		if(n==2){//importar
			document.frmFiltro.action='<c:url value="/articulacion/importarEvaluacion/Nuevo.jsp"/>';  	
  	}
		document.frmFiltro.target="";
		document.frmFiltro.submit();
	}
	
	function x(forma,pruebas,rangos){
		min=parseInt(rangos.limitA.value);
 		max=parseInt(rangos.limitB.value);
 		if(forma.artEvaCodEstudiante){
			if(forma.artEvaCodEstudiante.length){
				cantEst=1;
			}else{
				cantEst=0;
			}
		}
		if(document.rangos.cierre.value=="true"){
			forma.artEvaEstado_.disabled=true;
			if(cantEst==1){
				for(var a=0;a<forma.artEvaCodEstudiante.length;a++){
					for(var b=0;b<(pruebas.codPru.length-1);b++){
						document.getElementById("subPru"+(a+1)+(b+1)).disabled=true;
					}
				}
			}else{
				for(var b=0;b<(pruebas.codPru.length-1);b++){
					document.getElementById("subPru1"+(b+1)).disabled=true;
				}
			}
		}
	}
	
	function cambioCaja(forma,rangos,pruebas,est){
		var dato="";
		var cant=0;
		for(var i=0;i<(pruebas.codPru.length-1);i++){
			dato=document.getElementById("subPru"+""+est+""+(i+1)).value;
			document.getElementById("OcultoSubPru"+""+est+""+(i+1)).value=document.getElementById("subPru"+""+est+""+(i+1)).value;
			if(dato=="."){
				dato="";
				document.getElementById("subPru"+""+est+""+(i+1)).value="";
			}
			if(dato==""){
				cant=cant+1;
			}
		}
		if(cant==(pruebas.codPru.length-1)){
			if(cantEst==1){
				forma.artEvaNotaNum[est-1].value="-1";
				forma.artEvaNotaConc[est-1].value="";
				document.getElementById("num"+est).value="";
				document.getElementById("let"+est).value="";
			}
			else{
				forma.artEvaNotaNum.value="-1";
				document.getElementById("let1").value="";
				document.getElementById("num1").value="";
			}
		}else{
			calcular(forma,rangos,pruebas,est);
		}
	}
	
    function calcular(forma,rangos,pruebas,est){
   		var suma=0,caja=0,num=0,porc=0,max1=0;
   		var val="";
   		
 	 	for(var i=0;i<(pruebas.codPru.length-1);i++){
	 	 	if(cantEst==1)
		 		val=document.getElementById("subPru"+est+(i+1)).value;
		 	else
		 		val=document.getElementById("subPru1"+(i+1)).value;

			if(val!=""){
	 		 	max1=max1+parseFloat(pruebas.porcen[i+1].value);
			 	caja=parseFloat(val);
			 	
		 	 	if(caja>max||caja<min){
				 	alert("Valor fuera del rango permitido");
				 	if(cantEst==1){
				 		document.getElementById("subPru"+""+est+""+(i+1)).value="";
				 		document.getElementById("OcultoSubPru"+""+est+""+(i+1)).value="";
				 	}
				 	else{
					 	document.getElementById("subPru1"+(i+1)).value="";
					 	document.getElementById("OcultoSubPru1"+(i+1)).value="";
					 }
				 	caja=0;
				 	max1=max1-parseFloat(pruebas.porcen[i+1].value);
				 	calcular(forma,rangos,pruebas,est);
				}
				caja=(caja*parseFloat(pruebas.porcen[i+1].value))/100;	
				suma=suma+caja;
			}
		}
		num=(suma*100)/max1;
		if(cantEst==1){
			if(!isNaN(num)){
				document.getElementById("num"+""+est).value=num; 
				forma.artEvaNotaNum[(est-1)].value=num;	 	
				
			}else{
				forma.artEvaNotaNum[(est-1)].value="-1";
			}
		}
		else{
			document.getElementById("num1").value=num;
			forma.artEvaNotaNum.value=num;
		}
		porc=(num*100)/max;	 	
	 	for(var j=0;j<rangos.letra.length ;j++){
	 		ini=parseFloat(rangos.inicio[j].value);
	 		fin=parseFloat(rangos.fin[j].value);
	 		if(porc>=ini && porc<=fin){
	 			if(cantEst==1){
		 			document.getElementById("let"+est).value=rangos.letra[j].value;
		 			forma.artEvaNotaConc[(est-1)].value=rangos.letra[j].value;
		 		}else{
		 			document.getElementById("let1").value=rangos.letra[j].value;
		 			forma.artEvaNotaConc.value=rangos.letra[j].value;
		 		}
		 		
	 		}
	 	}
    }
    
    
	function guardar(){
		//ceros(document.frmLista,document.porcentajes,0);
		if(document.rangos.cierre.value!="true"){
			
			var elements = document.getElementsByTagName('input');
			
			for(var i = 0; i < elements.length; i++){
				if(elements[i].type == "checkbox"){
					if(elements[i].checked == true){
						document.getElementById(elements[i].id+"_").disabled="disabled";
					}
				}
			}
			
			/*if(document.frmLista.artEvaEstado_.checked==true){
				document.frmLista.artEvaEstado.value='true';
			}else{
				document.frmLista.artEvaEstado.value='false';
			}
		//	alert("estado="+document.frmLista.artEvaEstado.value);*/
			habilitarCampos();
			document.frmLista.cmd.value=document.frmLista.INSERTAR.value;
			document.frmLista.submit();
		}else{
			alert("La prueba se encuentra Cerrada...");
		}
	}
	
	function validarValorRango(caja){
		var numero = parseFloat(caja.value);
		min=parseInt(rangos.limitA.value);
 		max=parseInt(rangos.limitB.value);
 		if(typeof numero == 'number' && !isNaN(numero)){
 			var numero = parseFloat(numero);
 			if(numero >= min &&  numero <= max){
 				calcularNotaConceptual(caja);
 				return true;
 			}else{
 				alert("El numero no se encuentra en la escala valorativa, ingrese un numero entre "+min+" y "+max);
 				caja.value = "";
 				return false;
 			}
 				
 		}else{
 			alert("El texto ingresado no es un numero");
 			caja.value="";
 			return false;
 		}
	}
	
	function calcularNotaConceptual(caja){
		var id = caja.id.replace("Num","Conc");
		var cajaConcec = document.getElementById(id);
		var ini, fin;
		if (cajaConcec != null){
			if(rangos.letra.length){
				for(var j=0;j<document.rangos.letra.length ;j++){
			 		ini=parseFloat(document.rangos.inicio[j].value);
			 		fin=parseFloat(document.rangos.fin[j].value);
			 		if(caja.value>=ini && caja<=fin){
			 			cajaConcec.value=document.rangos.letra[j].value;
			 		}
			 	}	
			}else{
				ini=parseFloat(document.rangos.inicio.value);
	 			fin=parseFloat(document.rangos.fin.value);
	 			if(parseFloat(caja.value)>=ini && parseFloat(caja.value)<=fin){
		 			cajaConcec.value=document.rangos.letra.value;
		 		}
			}
			
		}
	}
	
	function habilitarCampos(){
		var elements = document.getElementsByTagName('input');
		
		for(var i = 0; i < elements.length; i++){
			if(elements[i].type == "text"){
				elements[i].disabled="";
			}
		}
	}
	
	function ceros(form1,p,x){
		if(x==0){
			 if(form1.artEvaCodEstudiante){
			 	 if(form1.artEvaCodEstudiante.length){
			 	 	 for(var a=0;a<form1.artEvaCodEstudiante.length;a++){
					 	 for(var b=0;b<p.codPru.length-1;b++){
					 		 if(document.getElementById("subPru"+(a+1)+(b+1)).value==""){
					 		 //	document.getElementById("subPru"+(a+1)+(b+1)).value=-1;
					 		 	document.getElementById("OcultoSubPru"+(a+1)+(b+1)).value=-1;
					 		 }

					 	 }
					 }
				}else{
					for(var b=0;b<p.codPru.length-1;b++){
				 		 if(document.getElementById("subPru1"+(b+1)).value==""){
				 		 	//document.getElementById("subPru1"+(b+1)).value=-1;
				 		 	document.getElementById("OcultoSubPru1"+(b+1)).value=-1;
				 		 }
				 	 }
				}
			}
		}else{
			if(form1.artEvaCodEstudiante){
			 	 if(form1.artEvaCodEstudiante.length){
			 	 	 for(var a=0;a<form1.artEvaCodEstudiante.length;a++){
					 	 for(var b=0;b<p.codPru.length-1;b++){
					 	 //alert(document.getElementById("subPru"+(a+1)+(b+1)).value);
					 		 if(document.getElementById("subPru"+(a+1)+(b+1)).value=="-1.0"){
					 		 	document.getElementById("subPru"+(a+1)+(b+1)).value="";
					 		 }
					 	 }
					 	 if(form1.artEvaNotaNum[a].value=="-1.0"){
				 		 	form1.artEvaNotaNum[a].value=="";
				 		 	document.getElementById("num"+(a+1)).value="";
				 		 }
					 }
				}else{
					for(var b=0;b<p.codPru.length-1;b++){
				 		 if(document.getElementById("subPru1"+(b+1)).value=="-1.0"){
				 		 	document.getElementById("subPru1"+(b+1)).value="";
				 		 }
				 	 }
				 	 if(form1.artEvaNotaNum.value=="-1.0"){
			 		 	form1.artEvaNotaNum.value=="";
			 		 	document.getElementById("num1").value="";
			 		 }
				}
			}
		}
	}
		
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:200px;overflow:auto;vertical-align:top;background-color:#ffffff;">
				<c:import url="/articulacion/evaluacionArt/Filtro.do"/>
			</div>
		</td></tr>
	</table>
	
	<form action='<c:url value="/articulacion/evaluacionArt/Save.jsp"/>' method="post" name="frmLista" onSubmit="return validarForma(frmLista)">
		<input type="hidden" name="cmd" value=''>
		<input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_DEFAULT}"/>'>
		<input type="hidden" name="INSERTAR" value='<c:out value="${paramsVO.CMD_INSERTAR}"/>'>
		<input type="hidden" name="artEvaCodInst" value='<c:out value="${sessionScope.login.instId}"/>'>
		<input type="hidden" name="artEvaCodMetod" value='<c:out value="${sessionScope.login.metodologiaId}"/>'>
		<input type="hidden" name="artEvaAnVigencia" value='<c:out value="${sessionScope.FilEvaluacionVO.anVigencia}"/>'>
		<input type="hidden" name="artEvaPerVigencia" value='<c:out value="${sessionScope.FilEvaluacionVO.perVigencia}"/>'>
		<input type="hidden" name="artEvaCodAsig" value='<c:out value="${sessionScope.FilEvaluacionVO.asignatura}"/>'>
		<input type="hidden" name="artEvaCodPrueba" value='<c:out value="${sessionScope.FilEvaluacionVO.prueba}"/>'>
		<input type="hidden" name="artEvaBimestre" value='<c:out value="${sessionScope.FilEvaluacionVO.bimestre}"/>'>
		
	 	<c:if test="${empty sessionScope.listaEstudiantesVO}">
			<table border="0" align="center" width="95%" cellpadding="0" cellspacing="0">
			 	<caption>LISTADO DE ESTUDIANTES</caption>
				<tr><th class="Fila1" colspan='6'>NO EXISTEN REGISTROS DE ESTUDIANTES</th></tr>
			</table>
		</c:if>
		<c:if test="${!empty sessionScope.listaEstudiantesVO}">
			<table border="0" align="center" width="95%" cellpadding="0" cellspacing="0">
			 	<caption>LISTADO DE ESTUDIANTES</caption>
				<tr>
						<td width="80%"><input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton"></td>
	  			</tr>
	  		</table>
	  		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0">
				<tr>
					<th class="EncabezadoColumna" Colspan ="3" ali	gn="center">Datos Estudiante</th>
					<c:set var="contadorPruebas" value="0" scope="page"/>
					<c:forEach begin="0" items="${sessionScope.listaPruebas}" var="prueba">
						<c:set var="contadorPruebas" value="${pageScope.contadorPruebas+1}"/>
					</c:forEach>
					<th style="width:35px;"  class="EncabezadoColumna" align="center" colspan='<c:out value="${pageScope.contadorPruebas * 2}"/>'>Pruebas</th>
				</tr>
				<tr>
					<th align="center" class="EncabezadoColumna" rowspan="3">&nbsp;</th>
					<th align="center" class="EncabezadoColumna" rowspan="3">Apellidos</th>
					<th align="center" class="EncabezadoColumna" rowspan="3">Nombres</th>
					<c:forEach begin="0" items="${sessionScope.listaPruebas}" var="prueba">
						<th align="center" class="EncabezadoColumna" colspan="2">
							Cerrar Prueba: 
							<input id="<c:out value="${prueba.codigo}"/>cerrada" name="cerrada" type="checkbox" value="true"
								<c:if test="${requestScope.cerrada=='true'}">checked</c:if>
							>
							<input id="<c:out value="${prueba.codigo}"/>cerrada_" name="cerrada" type="hidden" value="false">
						</th>
					</c:forEach>
				</tr>
				<tr>
					<c:forEach begin="0" items="${sessionScope.listaPruebas}" var="prueba">
						<th align="center" class="EncabezadoColumna" colspan="2"><c:out value="${prueba.nombre}"/></th>
					</c:forEach>
				</tr>
				<tr>
					<c:forEach begin="0" items="${sessionScope.listaPruebas}" var="prueba" varStatus="st2">
						<th align="center" class="EncabezadoColumna">Nota Numerica</th>
						<th align="center" class="EncabezadoColumna">Nota Conceptual</th>
					</c:forEach>
				</tr>
				
				<c:forEach begin="0" items="${sessionScope.listaEstudiantesVO}" var="lista" varStatus="st">
					<tr>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${st.count}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.apellido}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.nombre}"/></td>
						<c:forEach begin="0" items="${sessionScope.listaPruebas}" var="prueba" varStatus="st2">
							<c:set var="pintado" value="false" scope="page"/>
							<c:forEach begin="0" items="${requestScope.notasEstudiantes}" var="nota" varStatus="st2">
								<c:if test="${lista.codigo == nota[0] and prueba.codigo == nota[1]}">
									<td style="text-align: center;" class='Fila<c:out value="${st.count%2}"/>'>
										<input  style="text-align:right" id='<c:out value="${lista.codigo}"/>Num<c:out value="${prueba.codigo}"/>' name='<c:out value="${lista.codigo}"/>Num' type="text" class='Fila<c:out value="${st.count%2}"/>' maxlength="4" size="1" 
											value='<c:out value="${nota[2]}"/>' onchange="validarValorRango(this);">
									</td>
									<td style="width:35px;" class='Fila<c:out value="${st.count%2}"/>' align="center">
										<input  style="text-align:right" id='<c:out value="${lista.codigo}"/>Conc<c:out value="${prueba.codigo}"/>' name='<c:out value="${lista.codigo}"/>Conc' type="text" class='Fila<c:out value="${st.count%2}"/>' maxlength="4" size="1" 
											value='<c:out value="${nota[3]}"/>' disabled="disabled">
									</td>
									<c:set var="pintado" value="true"/>
								</c:if>
							</c:forEach>
							<c:if test="${!pageScope.pintado}">
								<td style="text-align: center;" class='Fila<c:out value="${st.count%2}"/>'>
									<input  style="text-align:right" id='<c:out value="${lista.codigo}"/>Num<c:out value="${prueba.codigo}"/>' name='<c:out value="${lista.codigo}"/>Num' type="text" class='Fila<c:out value="${st.count%2}"/>' maxlength="4" size="1" 
										value='' onchange="validarValorRango(this);">
								</td>
								<td style="width:35px;" class='Fila<c:out value="${st.count%2}"/>' align="center">
									<input  style="text-align:right" id='<c:out value="${lista.codigo}"/>Conc<c:out value="${prueba.codigo}"/>' name='<c:out value="${lista.codigo}"/>Conc' type="text" class='Fila<c:out value="${st.count%2}"/>' maxlength="4" size="1" 
										value='' disabled="disabled">
								</td>
							</c:if>
						</c:forEach>		
					</tr>
						<input type="hidden" name="artEvaCodEstudiante" value='<c:out value="${lista.codigo}"/>'/>
						
						<input type="hidden" name="artEvaNotaConc" value='<c:out value="${lista.notaCon}"/>'/>
						<input type="hidden" name="estado" value='<c:out value="${lista.estado}"/>'/>
				</c:forEach>
			</table>
		</c:if>
	</form>
	<form name="rangos">
		<input type="hidden" name="limitA" value="<c:out value="${requestScope.limites.limitA}"/>">
		<input type="hidden" name="limitB" value="<c:out value="${requestScope.limites.limitB}"/>">
		<input type="hidden" name="cierre" value="<c:out value="${requestScope.cerrada}"/>">
		<c:forEach begin="0" items="${requestScope.rangosVO}" var="rangos" varStatus="st">
			<input type="hidden" name="letra" value="<c:out value="${rangos.conceptual}"/>">
			<input type="hidden" name="inicio" value="<c:out value="${rangos.inicio}"/>">
			<input type="hidden" name="fin" value="<c:out value="${rangos.fin}"/>">
		</c:forEach>
	</form>
	<form name="porcentajes">
		<c:forEach begin="0" items="${requestScope.subPruebasVO}" var="lista" varStatus="st">
			<input type="hidden" name="codPru" value='<c:out value="${lista.codigo}"/>'>
			<input type="hidden" name="porcen" value='<c:out value="${lista.porcentaje}"/>'>
		</c:forEach>
	</form>
</body>
<script>
	//ceros(document.frmLista,document.porcentajes,1);
	x(document.frmLista,document.porcentajes,document.rangos);
//	calcular(document.frmLista,document.rangos,document.porcentajes);
//	deshabilitar(document.frmLista);
</script>
</html>