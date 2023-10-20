<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="paramsVO" class="articulacion.horarioArticulacion.vo.ParamsVO" scope="page"/>
<html>
<head>
<c:import url="/parametros.jsp"/>
<script languaje="javaScript">
<!--
	var nav4=window.Event ? true : false;

			function remoto(id,clase,dia,esp){
				if(document.frmNuevo.check[id].checked==false){
					alert('debe seleccionar la casilla para poder escoger espacio físico')
					return;
				}
				if(document.frmNuevo.check[id].disabled==true){
					alert('no puede cambiar el espacio físico porque esta desahabilitado el día')
					return;
				}
				remote = window.open("","3","width=300,height=150,resizable=no,toolbar=no,directories=no,menubar=no,status=yes");
				document.fr.target='3';
				remote.moveTo(200,200);
				document.fr.id.value=id;
				document.fr.dia.value=dia;
				document.fr.clase.value=clase;
				document.fr.espacio.value=esp;
				document.fr.submit();
				if(remote.opener == null) remote.opener = window;
				remote.opener.name = "centro";
				remote.focus();
			}
			
			function guardar(){
				if(validarForma(document.frmNuevo)){
					if(validacionHorario(document.frmNuevo)){
							copiarChecks(document.frmNuevo);
							//alert(document.frmNuevo.filIH.value);
							document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
							document.frmNuevo.submit();
						}
				}
			}

		function copiarChecks(forma){
			if(forma.check.length){
				for(var i=0;i<forma.check.length;i++){
					if(forma.check[i].disabled==false){
						if(forma.check[i].checked==true){
							forma.checkHor[i].value=forma.check[i].value;
						}else{
							forma.checkHor[i].value=-99;
							forma.checkHor[i].disabled=true;
							forma.checkEsp[i].value=-99;
							forma.checkEsp[i].disabled=true;
						}
					}else{
						forma.checkHor[i].value=-99;
						forma.checkHor[i].disabled=true;
						forma.checkEsp[i].value=-99;
						forma.checkEsp[i].disabled=true;
					}
				}
			}
		}
		
		function validacionHorario(forma){
			var ih=0;
			//validar si esta deshabilitado el horario
			if(forma.filDisabled.value==1){
					alert('No se puede registrar otro docente en la misma asignatura y grupo');
					return false;
			}
			if(forma.check.length){
			//VALIDAR IH
				for(var i=0;i<forma.check.length;i++){
					if(forma.check[i].disabled==false){
						if(forma.check[i].checked==true){
							ih++;	
						}
					}
				}
				if(ih!=0){
					if(ih>forma.filIH.value){
						alert('La cantidad de clases seleccionadas excede la intensidad horaria ('+forma.filIH.value+')');
						return false;
					}
					if(ih<forma.filIH.value){
						alert('La cantidad de clases seleccionadas es menor de la intensidad horaria ('+forma.filIH.value+')');
						return false;
					}
				}	
				//FIN DE VALIDAR IH
				
			}
			return true;
		}
		
		function hacerValidaciones_frmNuevo(forma){
		}
//-->
</script>
	<STYLE type='text/css'>
		.c1{COLOR:red;}
		.c2{COLOR:blue;}
		.c3{COLOR:green;}
		.c4{BACKGROUND:orange;}
		.c5{BACKGROUND:yellow;}
		.c6{BACKGROUND:cyan;}
		.c7{COLOR:brown;}
	</STYLE>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">

	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:200px;overflow:auto;vertical-align:top;background-color:#ffffff;"><c:import url="/articulacion/horarioArticulacion/Lista.do"/></div>
		</td></tr>
	</table>
	<FORM ACTION='<c:url value="/articulacion/horarioArticulacion/PopUpEspacio.html"/>' METHOD="POST" name='fr'><input type="hidden" name="dia">
	<input type="hidden" name="clase"><input type="hidden" name="espacio">
	<input type="hidden" name="id"></form>
	<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/articulacion/horarioArticulacion/SaveHorario.jsp"/>'>
		<INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_HORARIO}"/>'>
			<input type="hidden" name="cmd" value=''>
			<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
			<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
			<input type="hidden" name="filIH" value='<c:out value="${sessionScope.filtrohorArtVO.filIH}"/>'>
			<input type="hidden" name="filDisabled" value='<c:out value="${sessionScope.filtrohorArtVO.filDisabled}"/>'>
		<c:if test="${sessionScope.horArtVO.formaEstado==1}">
			<input type="hidden" name="lEspacio" value='-99'><input type="hidden" name="lEspacio_" value='--Seleccione uno--'>	
			<c:forEach begin="0" items="${sessionScope.horArtVO.horEspacio}" var="espacio">
					<input type="hidden" name="lEspacio" value='<c:out value="${espacio.codigo}"/>'><input type="hidden" name="lEspacio_" value='<c:out value="${espacio.nombre}"/>'>	
			</c:forEach>

		<table border="1" align="center" bordercolor="#FFFFFF" width="100%">
				<tr><td width="45%">
		        <input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton">
			  </td>
			  <td><span class="style2">Nota: </span>La cantidad de clases a seleccionar debe ser <c:out value="${sessionScope.filtrohorArtVO.filIH}"/></td>
			  </tr>	
	  </table>
			<table border="1" align="center" bordercolor="#FFFFFF" width="100%"><caption>HORARIO</caption></table>
			<table border="1" align="center" bordercolor="black" width="100%" cellpadding="0" cellspacing="0">
				<tr><th width="5%">&nbsp;</th>
					<c:forEach begin="0" items="${sessionScope.horArtVO.horEncabezado}" var="encabezado">
					<th width="20%">&nbsp;<c:out value="${encabezado.nombre}"/>&nbsp;</th>
					</c:forEach>
				</tr>
					<c:set var="i" scope="page" value="0"/>
					<c:forEach begin="0" items="${sessionScope.horArtVO.horClase}" var="clase">
					<tr>
						<th>&nbsp;<c:out value="${clase.hora}"/>&nbsp;</th>
						<c:forEach begin="0" items="${clase.dia}" var="dia">
							<td align="center" class='<c:out value="${dia.style}"/>'>
								
								<c:set var="i" scope="page" value="${i+1}"/>
								<br>
								<span class="c1"><c:out value="${dia.asignatura}"/></span><br>
								<span class="c2"><c:out value="${dia.docente}"/></span><br>
								<span class="c3"><c:out value="${dia.espacio}"/></span><br>
								<span class="c7"><c:out value="${dia.grupo}"/></span><br>
							</td>
						</c:forEach>
					<tr>
					</c:forEach>
		  </table>
			<table border="1" align="center" bordercolor="black" width="30%" cellpadding="0" cellspacing="0">
			<tr><th colspan="1" align="center"><b>CONVENCIONES</b></th></tr>
			<tr><td align="center"><span class="c1"> --Asignatura-- </span></td></tr>
			<tr><td align="center"><span class="c2"> --Docente-- </span></td></tr>
			<tr><td align="center"><span class="c3"> --Espacio físico-- </span></td></tr>
			<tr><td align="center"><span class="c7"> --Grupo-- </span></td></tr>
			<tr><td align="center" class="c4">Otro docente</td></tr>
			<tr><td align="center" class="c5">Otro grupo</td></tr>
			<tr><td align="center" class="c6">Otra asignatura</td></tr>
			</table>		  
		</c:if>
	</form>
</body>
</html>