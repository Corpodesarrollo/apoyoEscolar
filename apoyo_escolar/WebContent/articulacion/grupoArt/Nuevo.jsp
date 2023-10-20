<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="grupoVO" class="articulacion.grupoArt.vo.GrupoArtVO" scope="session"/>
<jsp:useBean id="paramsVO" class="articulacion.grupoArt.vo.ParamsVO" scope="page"/>
<html>
<head>
<script languaje="javaScript">
	var nav4=window.Event ? true : false;
	
	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}
	
	function cambioChecks(forma){
		if(forma.artGruRepite_.checked==true){
			forma.artGruRepite.value='true';
			frmNuevo.artGruCupoNivel.value="0";
			document.getElementById ('nivelado').disabled=true;
			forma.general_.checked=false;
		//	verChecks(forma);
			if(document.frmNuevo.asignaturas_ && document.frmNuevo.asignaturas_.length){
				for(var a=0;a<document.frmNuevo.asignaturas_.length;a++){
					document.frmNuevo.asignaturas_[a].checked=false;
				}
			}else{
				if(document.frmNuevo.asignaturas_){
					document.frmNuevo.asignaturas_.checked=false;
				}
			}
		}else{
			forma.artGruRepite.value='false';
			if(forma.general_.checked==false)
				document.getElementById ('nivelado').disabled=false;
			verChecks(forma);
			if(document.frmNuevo.asignaturas_){
				if(document.frmNuevo.asignaturas_.length){
					for(var a=0;a<document.frmNuevo.asignaturas_.length;a++){
						document.frmNuevo.asignaturas_[a].checked=true;
					}
				}else{
					document.frmNuevo.asignaturas_.checked=true;
				}
			}
		}
		sumar();
	}
	
	function verChecks(forma){
		if(forma.general_.checked==true){
		//alert("entra");
			frmNuevo.artGruCupoNoNivel.value="0";
			frmNuevo.artGruCupoNivel.value="0";
			document.getElementById ('noNivelado').disabled=true;
			document.getElementById ('nivelado').disabled=true;
			document.getElementById ('general').disabled=false;
			
			forma.artGruRepite_.checked=false;
			document.getElementById ('artGruRepite_').disabled=true;
			if(forma.estado.value==""){
				for(var i=0;i<forma.asignaturas.length;i++){
					forma.asignaturas_[i].checked=true;
				}
			}
		}else{
			document.getElementById ('general').disabled=true;
			document.getElementById ('nivelado').disabled=false;
			document.getElementById ('noNivelado').disabled=false;
			frmNuevo.artGruCupoGeneral.value="0";
			document.getElementById ('artGruRepite_').disabled=false;
			//if(forma.estado.value==""){
			//	for(var i=0;i<forma.asignaturas.length;i++){
			//		forma.asignaturas_[i].checked=false;
			//	}
			//}
		}
		sumar();
	}
	
	function sumar(){
		var campo1=document.frmNuevo.artGruCupoNivel.value;
		var campo2=document.frmNuevo.artGruCupoNoNivel.value;
		var campo3=document.frmNuevo.artGruCupoGeneral.value;
		document.getElementById('sum').innerHTML=parseInt(campo1)+parseInt(campo2)+parseInt(campo3);
	}
	function llenar(){
		if(document.getElementById('ig'))
			document.getElementById('ig').innerHTML=document.disp.gen.value;
	}
	function llenar2(){
		if(document.getElementById('in')){
			document.getElementById('in').innerHTML=document.disp.niv.value;
			document.getElementById('inn').innerHTML=document.disp.noNiv.value;
		}
	//	if(document.frmNuevo.estado.value!="1"){
	//	alert("entra");
	//		if(document.frmNuevo.asignaturas_){
	//			if(document.frmNuevo.asignaturas_.length){
	//				alert("entra2");
	//				for(var a=0;a<document.frmNuevo.asignaturas_.length;a++){
	//					document.frmNuevo.asignaturas_[a].checked=true;
	//				}
	//			}else{
	//				document.frmNuevo.asignaturas_.checked=false;
	//			}
	//		}
	//	}
		
	}

	function nuevo(){
		document.frmNuevo.cmd.value=document.frmNuevo.NUEVO.value;
		document.frmNuevo.action='<c:url value="/articulacion/grupoArt/Nuevo.do"/>';
		document.frmNuevo.submit();
	}

	function guardar(){
		var x=false;
		/*if(document.frmNuevo.artGruCodAsig.value > 0){
			x=true;
			if(document.frmNuevo.asignaturas_.length){
				for(var a=0;a<document.frmNuevo.asignaturas_.length;a++){
					if(document.frmNuevo.asignaturas_[a].checked==true){
						x=true;
					}
				}
			}else{
				if(document.frmNuevo.asignaturas_.checked==true){
					x=true;
				}
			}
		}
		if(x)
		*/
		if(document.frmNuevo.artGruCodAsig.value > 0){
			if(document.frmNuevo.artGruCupoNivel.value!=0 || document.frmNuevo.artGruCupoGeneral.value!=0 || document.frmNuevo.artGruCupoNoNivel.value!=0 ){
				if(validarForma(document.frmNuevo)){
					//cambioChecks(document.frmNuevo);
					bloquearChecks(document.frmNuevo);
					document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
					document.getElementById ('nivelado').disabled=false;
					document.getElementById ('noNivelado').disabled=false;
					document.getElementById ('general').disabled=false;
					//alert(document.frmNuevo.artGruCupoGeneral.value);
					document.frmNuevo.submit();
				}
			}else{
				alert("Debe insertar cupos para este Grupo");
			}
		}else{
			alert("Debe haber al menos una asignatura asociada a este Grupo");
		}
	}
	
	function llenaAsignatura(formulario1,formulario2){
		if(document.frmNuevo.estado.value=="1"){
			if(document.frmNuevo.asignaturas_){
				if(document.frmNuevo.asignaturas_.length){
					for(var a=0;a<document.frmNuevo.asignaturas_.length;a++){
						document.frmNuevo.asignaturas_[a].checked=false;
					}
				}else{
					document.frmNuevo.asignaturas_.checked=false;
				}
			}
		}
		if(formulario2.codigo){
			if(formulario2.codigo.length){
				for(var i=0;i<formulario2.codigo.length;i++){
					for(var j=0;j<formulario1.asignaturas.length;j++){
						if(formulario2.codigo[i].value==formulario1.asignaturas[j].value){
					//	alert("e");
							formulario1.asignaturas_[j].checked=true;
						}
					}
				}
			}else{
				if(formulario1.asignaturas){
					if(formulario1.asignaturas.length){
						for(var i=0;i<formulario1.asignaturas.length;i++){
							if(formulario2.codigo.value==formulario1.asignaturas[i].value){
								formulario1.asignaturas_[i].checked=true;
							}
						}
					}else{
						if(formulario2.codigo.value==formulario1.asignaturas.value){
							formulario1.asignaturas_.checked=true;
						}
					}
				}
			}
		}
	}
	
	function bloquearChecks(formulario){
		if(formulario.asignaturas){
			if(formulario.asignaturas_.length){
				for(var i=0;i<formulario.asignaturas_.length;i++){
		//		alert("Check "+i+" ="+formulario.asignaturas_[i].checked);
					if(formulario.asignaturas_[i].checked==true){
						formulario.asignaturas[i].disabled=false;
					}else{
						formulario.asignaturas[i].disabled=true;
	//					alert("deshabilita");
					}
				}
			}else{
			//	alert("Check ="+formulario.asignaturas_.checked);
				if(formulario.asignaturas_.checked==true){
					formulario.asignaturas.disabled=false;
				}else{
					formulario.asignaturas.disabled=true;
	//				alert("deshabilita");
				}
			}
		}
	}

	function hacerValidaciones_frmNuevo(forma){
		validarEntero(forma.artGruConsecutivo, "- Código Consecutivo", 1, 99);
	}
	
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:165px;overflow:auto;vertical-align:top;background-color:#ffffff;">
				<c:import url="/articulacion/grupoArt/Filtro.do"/>
			</div>
		</td></tr>
	</table>
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:150px;overflow:auto;vertical-align:top;background-color:#ffffff;">
				<c:import url="/articulacion/grupoArt/Lista.do"/>
			</div>
		</td></tr>
	</table>
	
	<form name="disp">
		<input type="hidden" name="gen" value='<c:out value="${sessionScope.grupoVO.artGruInscGeneral}"/>'>
		<input type="hidden" name="niv" value='<c:out value="${sessionScope.grupoVO.artGruInscNivel}"/>'>
		<input type="hidden" name="noNiv" value='<c:out value="${sessionScope.grupoVO.artGruInscNoNivel}"/>'>
	</form>
	
 	<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/articulacion/grupoArt/Save.jsp"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		
		<input type="hidden" name="artGruCodInst" value='<c:out value="${sessionScope.login.instId}"/>'>
		<input type="hidden" name="artGruCodSede" value='<c:out value="${sessionScope.filtroGrupoVO.sede}"/>'>
		<input type="hidden" name="artGruCodJornada" value='<c:out value="${sessionScope.filtroGrupoVO.jornada}"/>'>
		<input type="hidden" name="artGruAnoVigencia" value='<c:out value="${sessionScope.filtroGrupoVO.anVigencia}"/>'>
		<input type="hidden" name="artGruPerVigencia" value='<c:out value="${sessionScope.filtroGrupoVO.perVigencia}"/>'>
		
		<input type="hidden" name="artGruPerEsp" value='<c:out value="${sessionScope.filtroGrupoVO.periodo}"/>'>
		<input type="hidden" name="artGruComponente" value='<c:out value="${sessionScope.filtroGrupoVO.componente}"/>'>
		<input type="hidden" name="artGruCodEsp" value='<c:out value="${sessionScope.filtroGrupoVO.especialidad}"/>'>
		<input type="hidden" name="estado" value='<c:out value="${sessionScope.grupoVO.formaEstado}"/>'>
		<table border="0" align="center" bordercolor="#FFFFFF" width="100%">
			<caption>Información Grupos</caption>
				<tr>
					<td width="45%">
						<c:if test="${sessionScope.NivelPermiso==2}">
							<input name="cmd1" type="button" value="Nuevo" onClick="nuevo()" class="boton">
		        			<c:if test="${sessionScope.grupoVO.formaEstado==1}">
		        				<input name="cmd1" type="button" value="Actualizar" onClick="guardar()" class="boton">
		        			</c:if>
	    					<c:if test="${sessionScope.grupoVO.formaEstado!=1}">
	    						<input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton">
	    					</c:if>
	    				</c:if>
			  		</td>
			 	</tr>	
		  </table>
	 	 <table width="100%" border="0" cellspacing="2" cellpadding="0">
			<tr>
				<td style="width:110;">
					<span class="style2">*</span><b>Asignatura:</b>
				</td>
				<td>
					<select name="artGruCodAsig">
						<option value="0">---</option>
							<c:forEach begin="0" items="${requestScope.listaAsignVO}" var="asignatura">
								<option value="<c:out value="${asignatura.codigo}"/>"  <c:if test="${asignatura.codigo==sessionScope.grupoVO.artGruCodAsig}">selected</c:if>><c:out value="${asignatura.nombre}"/></option>
							</c:forEach>
					</select>
			 		
				</td>
			</tr>
			<tr>
				<td style="width:110;">
					<span class="style2">*</span><b>Consecutivo Grupo:</b>
				</td>
				<td>
					<input type="text" name="artGruConsecutivo" maxlength="2" size="2" onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.grupoVO.artGruConsecutivo}"/>'></input>
				</td>
				<td>&nbsp</td>
				<td>&nbsp</td>
				<td>&nbsp</td>
				<td>
					<font style="font-weight:bold;font-size:11px;">Cupos</font>
				</td>
				<td><font style="font-weight:bold;font-size:11px;">Inscritos</font></td>
			</tr>
			<tr>
				<td>
					<b>Orden:</b>
				</td>
				<td>
					<input type="text" name="artGruOrden" maxlength="3" size="3" onKeyPress='return acepteNumeros(event)'  value='<c:out value="${sessionScope.grupoVO.artGruOrden}"/>'></input>
				</td>
				<td>&nbsp</td>
				<td>&nbsp</td>
				<td style="width:80;">
					<b>Nivelados:</b>
				</td>
				<td>
					<input id='nivelado' type="text" name="artGruCupoNivel" maxlength="3" size="3" onKeyPress='return acepteNumeros(event)' onchange='sumar(event)' value='<c:out value="${sessionScope.grupoVO.artGruCupoNivel}"/>'></input>
				</td>
				<td>
					<span id='in'>&nbsp;</span>
				</td>
			</tr>
			<tr>
				<td>&nbsp</td>
				<td>&nbsp</td>
				<td>
					Repitencia:
				</td>
				<td>
					<input type="checkbox" id="artGruRepite_" name="artGruRepite_" onclick="cambioChecks(document.frmNuevo);" value='<c:out value="${sessionScope.grupoVO.artGruRepite}"/>' <c:if test="${sessionScope.grupoVO.artGruRepite}">checked</c:if>></input>
					<input type="hidden" name="artGruRepite" value='<c:out value="${sessionScope.grupoVO.artGruRepite}"/>' ></input>
				</td>
				<td style="width:80;">
					No Nivelados:
				</td>
				<td>
					<input id='noNivelado' type="text" name="artGruCupoNoNivel" maxlength="3" size="3" onKeyPress='return acepteNumeros(event)' onchange='sumar(event)' value='<c:out value="${sessionScope.grupoVO.artGruCupoNoNivel}"/>'></input>
				</td>
				<td>
					<span id='inn'>&nbsp;</span> 
				</td>
			</tr>
			<tr>
				<td>&nbsp</td><td>&nbsp</td>
				<td style="width:80;">
					<b>General:</b>
				</td>
				<td>
					<input type="checkbox" name="general_" onclick="cambioChecks(document.frmNuevo);" <c:if test="${sessionScope.grupoVO.check}">checked</c:if>></input>
				</td>
				<td>
					<b>General:</b>
				</td>
				<td>
					<input id='general' type="text" name="artGruCupoGeneral" maxlength="3" size="3" onKeyPress='return acepteNumeros(event)' onchange='sumar(event)' value='<c:out value="${sessionScope.grupoVO.artGruCupoGeneral}"/>'></input>
				</td>
				<td>
					<span id='ig'>&nbsp;</span> &nbsp;
				</td>
			</tr>
			
			<tr>
				<td>&nbsp</td>
				<td>&nbsp</td>
				<td>&nbsp</td>
				<td>&nbsp</td>
				<td><font style="font-weight:bold;font-size:11px;">Total Cupos</font></td>
				<td><span id='sum'>&nbsp;</span></td>
			</tr>
		</table>
		<!-- <table border="1" align="center" width="100%" cellpadding="0" cellspacing="0">
		 	<caption>LISTADO DE ASIGNATURAS</caption>
		 	<c:if test="${empty requestScope.listaAsignVO}">
				<tr>
					<th class="Fila1" colspan='6'>NO EXISTEN REGISTROS DE ASIGNATURAS</th>
				</tr>
			</c:if>
			<c:if test="${!empty requestScope.listaAsignVO}">
				<tr>
					<th width='30' class="EncabezadoColumna">&nbsp;</th>
					<td class="EncabezadoColumna" align="center">Código</td>
					<td class="EncabezadoColumna" align="center">Nombre de la Asignatura</td>
				</tr>
				<c:forEach begin="0" items="${requestScope.listaAsignVO}" var="lista" varStatus="st">
					<tr>
						<input type="hidden" name="asignaturas" value="<c:out value="${lista.codigo}"/>">
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><input type="checkbox" name="asignaturas_" ></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.codigo2}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.nombre}"/></td>
					</tr>
				</c:forEach>
			</c:if>
		</table>  -->
	</form>
	<form name="frmInscri">
		<c:forEach begin="0" items="${requestScope.listaInscVO}" var="lista" varStatus="st">
			<input type="hidden" name="codigo" value="<c:out value="${lista.codigo}"/>">
		</c:forEach>
	</form>
</body>
<script>
	verChecks(document.frmNuevo);
	cambioChecks(document.frmNuevo);
	llenar2();
	llenar();
	llenaAsignatura(document.frmNuevo,document.frmInscri);
</script>
</html>