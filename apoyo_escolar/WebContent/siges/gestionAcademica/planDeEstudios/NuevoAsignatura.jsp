<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="asignaturaPlanVO" class="siges.gestionAcademica.planDeEstudios.vo.AsignaturaVO" scope="session"/>
<jsp:useBean id="paramsVO" class="siges.gestionAcademica.planDeEstudios.vo.ParamsVO" scope="page"/>
<html>
<head>
<script languaje="javaScript">
	var nav4=window.Event ? true : false;
	
	<c:if test="${sessionScope.login.jornada=='NOCTURNA'}">
	
	var validaciondeponderizacion='NOCTURNA';
	
	</c:if>
	<c:if test="${sessionScope.login.jornada!='NOCTURNA'}">
	
	var validaciondeponderizacion='DIURNA';
	
	</c:if>
	
	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}
		
	function hacerValidaciones_frmNuevo(forma){
		validarLista(forma.asiVigencia, "- Vigencia", 1)
		validarLista(forma.asiMetodologia, "- Metodologia", 1)
		validarLista(forma.asiArea, "- Área", 1)
		validarLista(forma.asiCodigo, "- Asignatura", 1)
		validarCampo(forma.asiNombre, "- Nombre", 1, 60)
		validarCampo(forma.asiAbreviatura, "- Abreviatura", 1, 10)
		validarEntero(forma.asiOrden, "- Orden", 1, 999)
		validarSeleccion(forma.asiGrado_, "- Grado (al menos debe seleccionar uno)")
		//validar la intensidad horaria
		if(forma.tipoponderacion!=null){
		//valida ponderacion nivel
		if(forma.tipoponderacion.value==1){
			if(validaciondeponderizacion=='NOCTURNA'){
				forma.jornpon.value='NOCTURNA';
				<c:if test="${empty requestScope.vigvali}">
	        	<c:if test="${empty sessionScope.listavalvalidaplanciclo1}">
	        		appendErrorMessage('- Por Favor diligenciar el formulario de busqueda de asignaturas para relacionarlas con el area y sus ponderaciones.');
	        	</c:if>
	        	</c:if>
	        	<c:if test="${!empty requestScope.vigvali}">
	        	if ('<c:out value="${requestScope.vigvali}"/>'!=document.frmNuevo.asiVigencia.value || '<c:out value="${requestScope.areavali}"/>'!=document.frmNuevo.asiArea.value || '<c:out value="${requestScope.metodvali}"/>'!=document.frmNuevo.asiMetodologia.value){
	        		appendErrorMessage('- Seleccione los mismos valores de Metodologia, Vigencia y Area que en el filtro de busqueda para la correcta validacion de las ponderaciones por nivel');
	        	}
	        	</c:if>
	        	<c:if test="${empty sessionScope.listavalvalidaplanciclo1}">
	        	 forma.planciclo1f.value='0';
	        	</c:if>
	        	<c:if test="${empty sessionScope.listavalvalidaplanciclo2}">
	        	 forma.planciclo2f.value='0';
	        	</c:if>
	        	<c:if test="${empty sessionScope.listavalvalidaplanciclo3}">
	        	 forma.planciclo3f.value='0';
	        	</c:if>
			<c:if test="${!empty sessionScope.listavalvalidaplanciclo1}">
			<c:forEach begin="0" items="${sessionScope.listavalvalidaplanciclo1}" var="asiab">
			var acciclo1=<c:out value="${asiab[4]}"/>;
			var atciclo1=<c:out value="${asiab[7]}"/>;
			var uaciclo1=atciclo1-acciclo1;
			var ppciclo1=<c:out value="${asiab[11]}"/>;
			var valacciclo1=<c:if test="${empty sessionScope.asignaturaPlanVO.planciclo1}">0</c:if><c:if test="${!empty sessionScope.asignaturaPlanVO.planciclo1}"><c:out value="${sessionScope.asignaturaPlanVO.planciclo1}"/></c:if>;
			var maxposciclo1=valacciclo1+ppciclo1;
				if (parseInt(forma.planciclo1.value) > maxposciclo1 && uaciclo1<=1)
					appendErrorMessage('- Porcentaje de ponderacion del ciclo1 debe ser menor o igual a '+maxposciclo1+' tenga en cuenta que es la ultima asignatura del area por ponderar');
				if(parseInt(forma.planciclo1.value) > maxposciclo1 && uaciclo1>1)
					appendErrorMessage('- Porcentaje de ponderacion del ciclo1 no debe exceder el '+maxposciclo1+'% ');
				if(parseInt(forma.planciclo1.value) <= maxposciclo1)
					forma.planciclo1f.value=maxposciclo1-parseInt(forma.planciclo1.value);
				if(forma.planciclo1f.value==null || forma.planciclo1f.value=="")
					forma.planciclo1f.value=100;
				</c:forEach>
			</c:if>
			<c:if test="${!empty sessionScope.listavalvalidaplanciclo2}">
			<c:forEach begin="0" items="${sessionScope.listavalvalidaplanciclo2}" var="asiab">
			var acciclo2=<c:out value="${asiab[5]}"/>;
			var atciclo2=<c:out value="${asiab[7]}"/>;
			var uaciclo2=atciclo2-acciclo2;
			var ppciclo2=<c:out value="${asiab[12]}"/>;
			var valacciclo2=<c:if test="${empty sessionScope.asignaturaPlanVO.planciclo2}">0</c:if><c:if test="${!empty sessionScope.asignaturaPlanVO.planciclo2}"><c:out value="${sessionScope.asignaturaPlanVO.planciclo2}"/></c:if>;
			var maxposciclo2=valacciclo2+ppciclo2;
				if (parseInt(forma.planciclo2.value) > maxposciclo2 && uaciclo2<=1)
					appendErrorMessage('- Porcentaje de ponderacion del ciclo2 debe ser menor o igual a '+maxposciclo2+' tenga en cuenta que es la ultima asignatura del area por ponderar');
				if(parseInt(forma.planciclo2.value) > maxposciclo2 && uaciclo2>1)
					appendErrorMessage('- Porcentaje de ponderacion del ciclo2 no debe exceder el '+maxposciclo2+'% ');
				if(parseInt(forma.planciclo2.value) <= maxposciclo2)
					forma.planciclo2f.value=maxposciclo2-parseInt(forma.planciclo2.value);
				if(forma.planciclo2f.value==null || forma.planciclo2f.value=="")
					forma.planciclo2f.value=100;
				</c:forEach>
			</c:if>
			<c:if test="${!empty sessionScope.listavalvalidaplanciclo3}">
			<c:forEach begin="0" items="${sessionScope.listavalvalidaplanciclo3}" var="asiab">
			var acciclo3=<c:out value="${asiab[6]}"/>;
			var atciclo3=<c:out value="${asiab[7]}"/>;
			var uaciclo3=atciclo3-acciclo3;
			var ppciclo3=<c:out value="${asiab[13]}"/>;
			var valacciclo3=<c:if test="${empty sessionScope.asignaturaPlanVO.planciclo3}">0</c:if><c:if test="${!empty sessionScope.asignaturaPlanVO.planciclo3}"><c:out value="${sessionScope.asignaturaPlanVO.planciclo3}"/></c:if>;
			var maxposciclo3=valacciclo3+ppciclo3;
				if (parseInt(forma.planciclo3.value) > maxposciclo3 && uaciclo3<=1)
					appendErrorMessage('- Porcentaje de ponderacion del ciclo3 debe ser menor o igual a '+maxposciclo3+' tenga en cuenta que es la ultima asignatura del area por ponderar');
				if(parseInt(forma.planciclo3.value) > maxposciclo3 && uaciclo3>1)
					appendErrorMessage('- Porcentaje de ponderacion del ciclo3 no debe exceder el '+maxposciclo3+'% ');
				if(parseInt(forma.planciclo3.value) <= maxposciclo3)
					forma.planciclo3f.value=maxposciclo3-parseInt(forma.planciclo3.value);
				if(forma.planciclo3f.value==null || forma.planciclo3f.value=="")
					forma.planciclo3f.value=100;
				</c:forEach>
			</c:if>
			}
			
			if(validaciondeponderizacion=='DIURNA'){
				forma.jornpon.value='DIURNA';
				<c:if test="${empty requestScope.vigvali}">
	        	<c:if test="${empty sessionScope.sessionScope.listavalvalidaplan1}">
	        		appendErrorMessage('- Por Favor diligenciar el formulario de busqueda de asignaturas para relacionarlas con el area y sus ponderaciones.');
	        	</c:if>
	        	</c:if>
	        	<c:if test="${!empty requestScope.vigvali}">
	        	if ('<c:out value="${requestScope.vigvali}"/>'!=document.frmNuevo.asiVigencia.value || '<c:out value="${requestScope.areavali}"/>'!=document.frmNuevo.asiArea.value || '<c:out value="${requestScope.metodvali}"/>'!=document.frmNuevo.asiMetodologia.value){
	        		appendErrorMessage('- Seleccione los mismos valores de Metodologia, Vigencia y Area que en el filtro de busqueda para la correcta validacion de las ponderaciones por nivel');
	        	}
	        	</c:if>
	        	<c:if test="${empty sessionScope.listavalvalidaplan1}">
	        	 forma.plan1f.value='0';
	        	</c:if>
	        	<c:if test="${empty sessionScope.listavalvalidaplan2}">
	        	 forma.plan2f.value='0';
	        	</c:if>
	        	<c:if test="${empty sessionScope.listavalvalidaplan3}">
	        	 forma.plan3f.value='0';
	        	</c:if>
				<c:if test="${!empty sessionScope.listavalvalidaplan1}">
				<c:forEach begin="0" items="${sessionScope.listavalvalidaplan1}" var="asiab">
				var acplan1=<c:out value="${asiab[1]}"/>;
				var atplan1=<c:out value="${asiab[7]}"/>;
				var uaplan1=atplan1-acplan1;
				var ppplan1=<c:out value="${asiab[8]}"/>;
				var valacplan1=<c:if test="${empty sessionScope.asignaturaPlanVO.plan1}">0</c:if><c:if test="${!empty sessionScope.asignaturaPlanVO.plan1}"><c:out value="${sessionScope.asignaturaPlanVO.plan1}"/></c:if>;
				var maxposplan1=valacplan1+ppplan1;
					if (parseInt(forma.plan1.value) > maxposplan1 && uaplan1<=1)
						appendErrorMessage('- Porcentaje de ponderacion de Primaria debe ser menor o igual a '+maxposplan1+'% ,tenga en cuenta que es la ultima asignatura del area por ponderar');
					if(parseInt(forma.plan1.value) > maxposplan1 && uaplan1>1)
						appendErrorMessage('- Porcentaje de ponderacion de Primaria no debe exceder el '+maxposplan1+'% ');
					if(parseInt(forma.plan1.value) <= maxposplan1)
						forma.plan1f.value=maxposplan1-parseInt(forma.plan1.value);
					</c:forEach>
					</c:if>
				<c:if test="${!empty sessionScope.listavalvalidaplan2}">
				<c:forEach begin="0" items="${sessionScope.listavalvalidaplan2}" var="asiab">
				var acplan2=<c:out value="${asiab[2]}"/>;
				var atplan2=<c:out value="${asiab[7]}"/>;
				var uaplan2=atplan2-acplan2;
				var ppplan2=<c:out value="${asiab[9]}"/>;
				var valacplan2=<c:if test="${empty sessionScope.asignaturaPlanVO.plan2}">0</c:if><c:if test="${!empty sessionScope.asignaturaPlanVO.plan2}"><c:out value="${sessionScope.asignaturaPlanVO.plan2}"/></c:if>;
				var maxposplan2=valacplan2+ppplan2;
					if (parseInt(forma.plan2.value) > maxposplan2 && uaplan2<=1)
						appendErrorMessage('- Porcentaje de ponderacion de Basica debe ser menor o igual a '+maxposplan2+' tenga en cuenta que es la ultima asignatura del area por ponderar');
					if(parseInt(forma.plan2.value) > maxposplan2 && uaplan2>1)
						appendErrorMessage('- Porcentaje de ponderacion de Basica no debe exceder el '+maxposplan2+'% ');
					if(parseInt(forma.plan2.value) <= maxposplan2)
						forma.plan2f.value=maxposplan2-parseInt(forma.plan2.value);
					</c:forEach>
					</c:if>
				<c:if test="${!empty sessionScope.listavalvalidaplan3}">
				<c:forEach begin="0" items="${sessionScope.listavalvalidaplan3}" var="asiab">
				var acplan3=<c:out value="${asiab[3]}"/>;
				var atplan3=<c:out value="${asiab[7]}"/>;
				var uaplan3=atplan3-acplan3;
				var ppplan3=<c:out value="${asiab[10]}"/>;
				var valacplan3=<c:if test="${empty sessionScope.asignaturaPlanVO.plan3}">0</c:if><c:if test="${!empty sessionScope.asignaturaPlanVO.plan3}"><c:out value="${sessionScope.asignaturaPlanVO.plan3}"/></c:if>;
				var maxposplan3=valacplan3+ppplan3;
					if (parseInt(forma.plan3.value) > maxposplan3 && uaplan3<=1)
						appendErrorMessage('- Porcentaje de ponderacion de Media debe ser menor o igual a '+maxposplan3+' tenga en cuenta que es la ultima asignatura del area por ponderar');
					if(parseInt(forma.plan3.value) > maxposplan3 && uaplan3>1)
						appendErrorMessage('- Porcentaje de ponderacion de Media no debe exceder el '+maxposplan3+'% ');
					if(parseInt(forma.plan3.value) <= maxposplan3)
						forma.plan3f.value=maxposplan3-parseInt(forma.plan3.value);
					</c:forEach>
					</c:if>
			}
		
		}
		//valida ponderacion institucion
        if(forma.tipoponderacion.value==2){
        	forma.jornpon.value='INS';
        	<c:if test="${empty requestScope.vigvali}">
        	<c:if test="${empty sessionScope.listavalvalidaplan1ins}">
        		appendErrorMessage('- Por Favor diligenciar el formulario de busqueda de asignaturas para relacionarlas con el area y sus ponderaciones.');
        	</c:if>
        	</c:if>
        	<c:if test="${!empty sessionScope.listavalvalidaplan1ins}">
        	<c:if test="${!empty requestScope.vigvali}">
        	if ('<c:out value="${requestScope.vigvali}"/>'!=document.frmNuevo.asiVigencia.value || '<c:out value="${requestScope.areavali}"/>'!=document.frmNuevo.asiArea.value){
        		appendErrorMessage('- Seleccione los mismos valores de Vigencia y Area que en el filtro de busqueda para la correcta validacion de las ponderaciones por institucion');
        	}
        	</c:if>
        	<c:forEach begin="0" items="${sessionScope.listavalvalidaplan1ins}" var="asiab">
		var acplan1ins=<c:out value="${asiab[0]}"/>;
		var atplan1ins=<c:out value="${asiab[1]}"/>;
		var uaplan1ins=atplan1ins-acplan1ins;
		var ppplan1ins=<c:out value="${asiab[2]}"/>;
		var valacplan1ins=<c:if test="${empty sessionScope.asignaturaPlanVO.plan1}">0</c:if><c:if test="${!empty sessionScope.asignaturaPlanVO.plan1}"><c:out value="${sessionScope.asignaturaPlanVO.plan1}"/></c:if>;
		var maxposplan1ins=valacplan1ins+ppplan1ins;
			if (parseInt(forma.plan1.value) > maxposplan1ins && uaplan1ins<=1)
				appendErrorMessage('- Porcentaje de ponderacion para el colegio debe ser menor o igual a '+maxposplan1ins+'% ,tenga en cuenta que es la ultima asignatura del area por ponderar');
			if(parseInt(forma.plan1.value) > maxposplan1ins && uaplan1ins>1)
				appendErrorMessage('- Porcentaje de ponderacion para el colegio no debe exceder el '+maxposplan1ins+'% ');
			if(parseInt(forma.plan1.value) <= maxposplan1ins)
				forma.planinsf.value=maxposplan1ins-parseInt(forma.plan1.value);
			if(forma.planinsf.value==null || forma.planinsf.value=="")
				forma.planinsf.value=100;
			</c:forEach>
			</c:if>
		}
		}
		
		if(forma.asiGrado){
			if(forma.asiGrado.length){
				for(i=0;i<forma.asiGrado.length;i++){
					if(forma.asiGrado_[i].checked==true){
						validarEntero(forma.asiGrado2_[i], "- Intensidad horaria del grado seleccionado", 1, 99)
					}
				}
			}
		}
	}
		
	function nuevo(){
		document.frmNuevo.cmd.value=document.frmNuevo.NUEVO.value;
		document.frmNuevo.action='<c:url value="/planDeEstudios/Nuevo.do"/>';
		document.frmNuevo.submit();
	}

	function guardar(){
		if(validarForma(document.frmNuevo)){
			validarDatos(document.frmNuevo);
			document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
			document.frmNuevo.submit();
		}
	}
	
	function validarDatos(forma){
		if(forma.asiGrado){
			if(forma.asiGrado.length){
				for(i=0;i<forma.asiGrado.length;i++){
					if(forma.asiGrado_[i].checked==false){
						forma.asiGrado[i].value='-1';
					}else{
						var estadoGraAsi="A";
						if(forma.asiGrado_e[i].checked==true){
							estadoGraAsi="A";
						}else{
							estadoGraAsi="I";
						}
						forma.asiGrado[i].value=forma.asiGrado[i].value+':'+forma.asiGrado2_[i].value+':'+estadoGraAsi;
					}
				}
			}
		}
	}


	function checkearEstado(gradoAsig){
		if(document.frmNuevo.asiGrado){
			if(document.frmNuevo.asiGrado.length){
				for(i=0;i<document.frmNuevo.asiGrado.length;i++){
					if(document.frmNuevo.asiGrado[i].value==gradoAsig){
					if(document.frmNuevo.asiGrado_[i].checked==true){
						document.frmNuevo.asiGrado_e[i].checked=true;
					}else{
						document.frmNuevo.asiGrado_e[i].checked=false;
					}
				}
				}
			}
		}
		habEstado();
	}


	function habEstado(){
		<c:if test="${sessionScope.asignaturaPlanVO.formaEstado==1}">
		if(document.frmNuevo.asiGrado){
			if(document.frmNuevo.asiGrado.length){
				for(i=0;i<document.frmNuevo.asiGrado.length;i++){					
					if(document.frmNuevo.asiGrado_[i].checked==false){
						document.frmNuevo.asiGrado_e[i].disabled=true;
					}else{
						document.frmNuevo.asiGrado_e[i].disabled=false;
					}				
				}
			}
		}
		</c:if>
	}
	
	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) {
			if(navigator.appName == "Netscape") combo.options[i] = null;
			else combo.remove(i);
		}
		combo.options[0] = new Option("-Seleccione una-","-99");
	}
		
	function ajaxArea(){
		document.frmAjax.ajax[0].value=document.frmNuevo.asiInstitucion.value;
		document.frmAjax.ajax[1].value=document.frmNuevo.asiMetodologia.value;
		document.frmAjax.ajax[2].value=document.frmNuevo.asiVigencia.value;
		if(parseInt(document.frmAjax.ajax[1].value)!=-99){
			document.frmAjax.cmd.value=document.frmNuevo.AJAX_AREA.value;
	 		document.frmAjax.target="frameAjax";
			document.frmAjax.submit();
		}
	}
	
	
	function ajaxGrado(){
		document.frmAjax.ajax[0].value=document.frmNuevo.asiInstitucion.value;
		document.frmAjax.ajax[1].value=document.frmNuevo.asiMetodologia.value;
		document.frmAjax.ajax[2].value=document.frmNuevo.asiVigencia.value;
		document.frmAjax.ajax[3].value=document.frmNuevo.asiArea.value;
		if(parseInt(document.frmAjax.ajax[3].value)!=-99){
			document.frmAjax.cmd.value=document.frmNuevo.AJAX_GRADO.value;
	 		document.frmAjax.target="frameAjax";
			document.frmAjax.submit();
		}
	}
	
	
	function ajaxAsignaturaBase(){
		document.frmAjax.ajax[0].value=document.frmNuevo.asiCodigo.value;
		if(parseInt(document.frmAjax.ajax[0].value)!=-99){
			document.frmAjax.cmd.value=document.frmNuevo.AJAX_ASIGNATURA_BASE.value;
	 		document.frmAjax.target="frameAjax";
			document.frmAjax.submit();
		}
	}
	
	function validarMetodologia(){
		switch(document.frmNuevo.asiMetodologia.value){
		case '10': 
			var retval = document.getElementById('pond_table');
			retval.innerHTML="<input type=\"hidden\" name=\"tipoponderacion\" value='1'>"+
			 "<table border=\"0\" width=\"200\" >"+
			  "<tr border=\"1\">"+
		      "<td>Plan de estudio</td>"+
		      "<td>Ponderaci&oacute;n</td>"+
		   "</tr>"+
		   "<tr border=\"1\">"+
		      "<td><span class=\"style2\">*</span>Ciclo 1 y 2</td>"+
		      "<td><input type=\"text\" name=\"planciclo1\" maxlength=\"4\" size=\"4\" value=\"<c:out value='${sessionScope.asignaturaPlanVO.planciclo1}'/>\" onKeyPress='return acepteNumeros(event)'></input></td>"+
		   "</tr>"+
		   "<tr border=\"1\">"+
		      "<td><span class=\"style2\">*</span>Ciclo 3 y 4</td>"+
		      "<td><input type=\"text\" name=\"planciclo2\" maxlength=\"4\" size=\"4\" value=\"<c:out value='${sessionScope.asignaturaPlanVO.planciclo2}' />\" onKeyPress='return acepteNumeros(event)'></input></td>"+
		   "</tr>"+
		   "<tr border=\"1\">"+
		      "<td><span class=\"style2\">*</span>Ciclo 5 y 6</td>"+
		      "<td><input type=\"text\" name=\"planciclo3\" maxlength=\"4\" size=\"4\" value=\"<c:out value='${sessionScope.asignaturaPlanVO.planciclo3}' />\" onKeyPress='return acepteNumeros(event)'></input></td>"+
		   "</tr>"+
		   "</table>";
		   validaciondeponderizacion='NOCTURNA';
		  break;
		
		case '1':
			var retval1 = document.getElementById('pond_table');
			retval1.innerHTML="<input type=\"hidden\" name=\"tipoponderacion\" value=\"1\">"+
			 "<table border=\"0\" width=\"200\" >"+
				"<tr border=\"1\">"+
		      "<td>Plan de estudio</td>"+
		      "<td>Ponderaci&oacute;n</td>"+
		   "</tr>"+
		   "<tr border=\"1\">"+
		      "<td><span class=\"style2\">*</span>Primaria</td>"+
		      "<td><input type=\"text\" name=\"plan1\" maxlength=\"4\" size=\"4\" value=\"<c:out value='${sessionScope.asignaturaPlanVO.plan1}'/>\" onKeyPress='return acepteNumeros(event)'></input></td>"+
		   "</tr>"+
		   "<tr border=\"1\">"+
		      "<td><span class=\"style2\">*</span>B&aacute;sica</td>"+
		      "<td><input type=\"text\" name=\"plan2\" maxlength=\"4\" size=\"4\" value=\"<c:out value='${sessionScope.asignaturaPlanVO.plan2}' />\" onKeyPress='return acepteNumeros(event)'></input></td>"+
		   "</tr>"+
		   "<tr border=\"1\">"+
		      "<td><span class=\"style2\">*</span>Media</td>"+
		      "<td><input type=\"text\" name=\"plan3\" maxlength=\"4\" size=\"4\" value=\"<c:out value='${sessionScope.asignaturaPlanVO.plan2}' />\" onKeyPress='return acepteNumeros(event)'></input></td>"+
		   "</tr>";
		   validaciondeponderizacion='DIURNA';
			break;
		}
	}
		
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:200px;overflow:auto;vertical-align:top;background-color:#ffffff;">
				<c:import url="/siges/gestionAcademica/planDeEstudios/FiltroAsignatura.jsp"/>
			</div>
		</td></tr>    
	</table>
	<form method="post" name="frmAjaxval" action='<c:url value="/siges/gestionAcademica/planDeEstudios/SaveAsignatura.jsp"/>'><INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_ASIGNATURA}"/>'><input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_AJAX}"/>'><INPUT TYPE="hidden" NAME="metod"><INPUT TYPE="hidden" NAME="area"><c:forEach begin="0" end="5" var="i"><input type="hidden" name="ajax"></c:forEach></form>
	<form method="post" name="frmAjax" action='<c:url value="/planDeEstudios/Ajax.do"/>'><INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_ASIGNATURA}"/>'><input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_AJAX}"/>'><c:forEach begin="0" end="5" var="i"><input type="hidden" name="ajax"></c:forEach></form>
	<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/siges/gestionAcademica/planDeEstudios/SaveAsignatura.jsp"/>'>
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_ASIGNATURA}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="planinsf">
		<input type="hidden" name="plan1f">
		<input type="hidden" name="plan2f">
		<input type="hidden" name="plan3f">
		<input type="hidden" name="planciclo1f">
		<input type="hidden" name="planciclo2f">
		<input type="hidden" name="planciclo3f">
		<input type="hidden" name="jornpon">
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="AJAX_AREA" value='<c:out value="${paramsVO.CMD_AJAX_AREA}"/>'>
		<input type="hidden" name="AJAX_ASIGNATURA" value='<c:out value="${paramsVO.CMD_AJAX_ASIGNATURA}"/>'>
		<input type="hidden" name="AJAX_GRADO" value='<c:out value="${paramsVO.CMD_AJAX_GRADO}"/>'>
		<input type="hidden" name="AJAX_ASIGNATURA_BASE" value='<c:out value="${paramsVO.CMD_AJAX_ASIGNATURA_BASE}"/>'>
		<input type="hidden" name="asiInstitucion" value='<c:out value="${sessionScope.asignaturaPlanVO.asiInstitucion}"/>'>
		<!-- <input type="hidden" name="asiVigencia" value='<c:out value="${sessionScope.asignaturaPlanVO.asiVigencia}"/>'> -->
	  <table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
		  <caption>Ingreso / edición de asignatura</caption>
				<tr>
					<td colspan="4">
						<c:if test="${sessionScope.NivelPermiso==2}">
    					<input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton">
						<input name="cmd1" type="button" value="Nuevo" onClick="nuevo()" class="boton">
	    			</c:if>
			  		</td>
			 	</tr>	
			<tr style="display:none"><td><iframe name="frameAjax" id="frameAjax"></iframe></td></tr>	
			<tr>
	         <td width="20%"><span class="style2">*</span>Vigencia:</td>
				<td width="30%">
					<select name="asiVigencia" style="width:120px;"  <c:out value="${sessionScope.asignaturaPlanVO.formaDisabled}"/>>
						<option value="-99" selected>--Seleccione uno--</option>
						<c:forEach begin="0" items="${requestScope.listaVigencia}" var="metod">
							<option value="<c:out value="${metod.codigo}"/>" <c:if test="${metod.codigo==sessionScope.asignaturaPlanVO.asiVigencia }">selected</c:if>><c:out value="${metod.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			<td width="25%"><span class="style2">*</span>Metodologia:</td>
			<td width="35%">
				<select name="asiMetodologia" style="width:120px;" onchange="javaScript:ajaxArea();validarMetodologia();" <c:out value="${sessionScope.asignaturaPlanVO.formaDisabled}"/>>
					<option value="-99" selected>--Seleccione uno--</option>
					<c:forEach begin="0" items="${requestScope.listaMetodologia}" var="metod">
						<option value="<c:out value="${metod.codigo}"/>" <c:if test="${metod.codigo==sessionScope.asignaturaPlanVO.asiMetodologia}">selected</c:if>><c:out value="${metod.nombre}"/></option>
					</c:forEach>
				</select>
			</td>
			</tr>
			<tr>
			<td width="30%"><span class="style2">*</span> Área:</td>
			<td width="25%">
				<select name="asiArea" style="width:250px;" onchange="javaScript:ajaxGrado()" <c:out value="${sessionScope.asignaturaPlanVO.formaDisabled}"/>>
					<option value="-99" selected>--Seleccione uno--</option>
					<c:forEach begin="0" items="${requestScope.listaAreaBase}" var="areab">
						<option value="<c:out value="${areab.codigo}"/>" <c:if test="${areab.codigo==sessionScope.asignaturaPlanVO.asiArea}">selected</c:if>><c:out value="${areab.nombre}"/></option>
					</c:forEach>
				</select>
			</td>
			<td><span class="style2">*</span> Asignatura:</td>
			<td colspan="3">
				<select name="asiCodigo" style="width:250px;" onchange="javaScript:ajaxAsignaturaBase()" <c:out value="${sessionScope.asignaturaPlanVO.formaDisabled}"/>>
					<option value="-99" selected>--Seleccione uno--</option>
					<c:forEach begin="0" items="${requestScope.listaAsignaturaBase}" var="asiab">
						<option value="<c:out value="${asiab.codigo}"/>" <c:if test="${asiab.codigo==sessionScope.asignaturaPlanVO.asiCodigo}">selected</c:if>><c:out value="${asiab.nombre}"/></option>
					</c:forEach>
				</select>
			</td>
		 	</tr>	
			<tr>
				<td><span class="style2">*</span> Nombre:</td>
				<td colspan="3">
				<input type="text" name="asiNombre" maxlength="60" size="30" value='<c:out value="${sessionScope.asignaturaPlanVO.asiNombre}"/>' ></input>
			</tr>	
			<tr>
				<td><span class="style2">*</span> Abreviatura:</td>
				<td colspan="3">
				<input type="text" name="asiAbreviatura" maxlength="10" size="10" value='<c:out value="${sessionScope.asignaturaPlanVO.asiAbreviatura}"/>' ></input>
				</td>
			</tr>	
			<tr>
				<td><span class="style2">*</span> Orden:</td>
				<td colspan="3">
				<input type="text" name="asiOrden" maxlength="3" size="3" value='<c:out value="${sessionScope.asignaturaPlanVO.asiOrden}"/>' onKeyPress='return acepteNumeros(event)'></input>
				</td>
			</tr>	
			
			<c:if test="${sessionScope.instParametros.insparniveval==0}">
			           <tr>
			           <td colspan="4">
			           <table><tr><td>
			           <input type="hidden" name="tipoponderacion" value='<c:out value="0"/>'>
			           <input type="hidden" name="plan1" value='0.0'></input>
			           <input type="hidden" name="plan2" value='0.0'></input>
			           <input type="hidden" name="plan3" value='0.0'></input>
			           No registran datos de parametros inciales para el colegio con vigencia actual.Por esta razón no cargan ponderaciones.</td></tr></table>
			           </td>
			             </tr>
			      </c:if>
			

			<c:if test="${sessionScope.instParametros.insparniveval!=0}">	
				<tr>
				<td>
				<c:if test="${sessionScope.instParametros.insparniveval==5}">
				 <div id="pond_table">
				<c:if test="${sessionScope.login.jornada!='NOCTURNA'}">
				<input type="hidden" name="tipoponderacion" value='<c:out value="1"/>'>
				<table border="0" id="pond_table__" width="200">
				   <tr border="1">
				      <td>Plan de estudio</td>
				      <td>Ponderaci&oacute;n</td>
				   </tr>
				   <tr border="1">
				     <td><span class="style2">*</span>Primaria</td>
				      <td><input type="text" name="plan1" maxlength="4" size="4" value='<c:out value="${sessionScope.asignaturaPlanVO.plan1}"/>' onKeyPress='return acepteNumeros(event)'></input>
					</td>
				   </tr>
				   <tr border="1">
				      <td><span class="style2">*</span>B&aacute;sica</td>
				      <td><input type="text" name="plan2" maxlength="4" size="4" value='<c:out value="${sessionScope.asignaturaPlanVO.plan2}"/>' onKeyPress='return acepteNumeros(event)'></input></td>
				   </tr>
				   <tr border="1">
				      <td><span class="style2">*</span>Media</td>
				      <td><input type="text" name="plan3" maxlength="4" size="4" value='<c:out value="${sessionScope.asignaturaPlanVO.plan3}"/>' onKeyPress='return acepteNumeros(event)'></input></td>
				   </tr>
				</table>
				
				</c:if>
				
				<c:if test="${sessionScope.login.jornada=='NOCTURNA'}">
				 <input type="hidden" name="tipoponderacion" value='<c:out value="1"/>'>
				 <table border="0" width="200">
				   <tr border="1">
				      <td>Plan de estudio</td>
				      <td>Ponderaci&oacute;n</td>
				   </tr>
				   <tr border="1">
				      <td><span class="style2">*</span>Ciclo 1 y 2</td>
				      <td><input type="text" name="planciclo1" maxlength="4" size="4" value='<c:out value="${sessionScope.asignaturaPlanVO.planciclo1}"/>' onKeyPress='return acepteNumeros(event)' onChance='ajaxvalidaponder()' ></input></td>
				   </tr>
				   <tr border="1">
				      <td><span class="style2">*</span>Ciclo 3 y 4</td>
				      <td><input type="text" name="planciclo2" maxlength="4" size="4" value='<c:out value="${sessionScope.asignaturaPlanVO.planciclo2}"/>' onKeyPress='return acepteNumeros(event)'></input></td>
				   </tr>
				   <tr border="1">
				      <td><span class="style2">*</span>Ciclo 5 y 6</td>
				      <td><input type="text" name="planciclo3" maxlength="4" size="4" value='<c:out value="${sessionScope.asignaturaPlanVO.planciclo3}"/>' onKeyPress='return acepteNumeros(event)'></input></td>
				   </tr>
				</table>
				
				</c:if>
				</div>
				</td>
				</tr>
				</c:if>
			</c:if>
			<c:if test="${sessionScope.instParametros.insparniveval==1}">
			    <input type="hidden" name="tipoponderacion" value='<c:out value="2"/>'>
				<table>
			   <tr border="1">
			      <td>Plan de estudio</td>
			      <td>Ponderaci&oacute;n</td>
			   </tr>
			   <tr border="1">
			      <td><span class="style2">*</span>Plan para todo el colegio</td>
			      <td><input type="text" name="plan1" maxlength="4" size="4" value='<c:out value="${sessionScope.asignaturaPlanVO.plan1}"/>' onKeyPress='return acepteNumeros(event)'></input></td>
			      <td><input type="hidden" name="plan2"  value='0.0'></input></td>
			      <td><input type="hidden" name="plan3"  value='0.0'></input></td>
			      <td><input type="hidden" name="planciclo1"  value='0.0'></input></td>
			      <td><input type="hidden" name="planciclo2"  value='0.0'></input></td>
			      <td><input type="hidden" name="planciclo3"  value='0.0'></input></td>
			   </tr>
			</table>
			
			
			</c:if>
			
			
			<table>
			</table>
			</tr>
			
			
		 	<c:if test="${sessionScope.asignaturaPlanVO.formaEstado==1}">
			 	
			 	<tr><td align="center" colspan="4">
			 		<table border="0" align="center" width="100%" cellpadding="1" cellspacing="0">
			 		<tr>
			 			<!-- <th align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;    GRADO  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;IH</th> -->
			 			<td width="50%">
			 			
			 			<table>
			 				<tr>
				 				<th width="170px">GRADO</th>
				 				<th width="40px">IH</th>
				 				<th width="30px">ACTIVO</th>
			 				</tr>
			 			</table>
						</td>
						
						<td>
			 			<table>
			 				<tr>
				 				<th width="170px">GRADO</th>
				 				<th width="40px">IH</th>
				 				<th width="30px">ACTIVO</th>
			 				</tr>
			 			</table>
						</td>			 			
			 		</tr>
				 		<tr>
				 		<c:forEach begin="0" items="${requestScope.listaGrado}" var="grado" varStatus="stg">
					 		<td width="50%">
					 		<table  border="1" bordercolor="#00000;">
					 		<tr bordercolor="#FFFFF;">
					 			<td width="170px" bordercolor="#FFFFF;"><input type="checkbox" name="asiGrado_" value='<c:out value="${grado.codigo}"/>' <c:out value="${grado.padre2}"/> onclick="javaScript:checkearEstado(<c:out value="${grado.codigo}"/>)"> &nbsp;<c:out value="${grado.nombre}"/></td>					 			
						 		<td width="40px" bordercolor="#FFFFF;"><input type="text" name="asiGrado2_" size="1" maxlength="2" onKeyPress='return acepteNumeros(event)' value='<c:out value="${grado.padre}"/>'></td>
						 		<td width="30px" align="center" bordercolor="#FFFFF;"><input type="checkbox" name="asiGrado_e" value='<c:out value="${grado.codigo}"/>' <c:out value="${grado.codigo2}"/>></td>
					 			<input type="hidden" name="asiGrado" value='<c:out value="${grado.codigo}"/>'>
					 		</tr>
					 		</table>
					 		</td><c:if test="${stg.count%2==0}"></tr><tr></c:if>
				 		</c:forEach>
				 		</tr>
			 		</table>
			 	</td></tr>
		 	</c:if>
		 	<c:if test="${sessionScope.asignaturaPlanVO.formaEstado==0}">
			 	<tr><td align="center" colspan="4"><span class="style2">*</span>Grados / Intensidad horaria</td></tr>
			 	<tr><td align="center" colspan="4">
			 		<table border="0" align="center" width="100%" cellpadding="1" cellspacing="0">
			 		
			 		<tr>
			 			<!-- <th align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;    GRADO  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;IH</th> -->
			 			<td width="50%">
			 			
			 			<table>
			 				<tr>
				 				<th width="170px">GRADO</th>
				 				<th width="40px">IH</th>
				 				<th width="30px">ACTIVO</th>
			 				</tr>
			 			</table>
						</td>
						
						<td>
			 			<table>
			 				<tr>
				 				<th width="170px">GRADO</th>
				 				<th width="40px">IH</th>
				 				<th width="30px">ACTIVO</th>
			 				</tr>
			 			</table>
						</td>			 			
			 		</tr>
			 		
				 		<tr>
				 		<c:forEach begin="0" items="${requestScope.listaGrado}" var="grado" varStatus="stg">
					 		<td width="50%">
					 		<table border="1" bordercolor="#00000;">
					 		<tr bordercolor="#FFFFF;">
					 			<td width="170px" bordercolor="#FFFFF;"><input type="checkbox" name="asiGrado_" value='<c:out value="${grado.codigo}"/>' disabled onclick="javaScript:checkearEstado(<c:out value="${grado.codigo}"/>)"> &nbsp;<c:out value="${grado.nombre}"/></td>					 			
						 		<td width="40px" bordercolor="#FFFFF;"><input type="text" name="asiGrado2_" size="1" maxlength="2" onKeyPress='return acepteNumeros(event)' value='<c:out value="${grado.padre}"/>' disabled></td>
						 		<td width="30px" align="center" bordercolor="#FFFFF;"><input type="checkbox" name="asiGrado_e" value='<c:out value="${grado.codigo}"/>' <c:out value="${grado.codigo2}"/> disabled></td>
					 			<input type="hidden" name="asiGrado" value='<c:out value="${grado.codigo}"/>'>
					 		</tr>
					 		</table>
					 		
					 		
						 	</td><c:if test="${stg.count%2==0}"></tr><tr></c:if>
				 		</c:forEach>
				 		</tr>
			 		</table>
			 	</td></tr>
		 	</c:if>
		</table>	
	</form>
	<script type="text/javascript">
	habEstado();
	validarMetodologia();
	</script>
</body>
</html>