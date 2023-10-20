<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="identificacionPEIVO" class="pei.registro.vo.IdentificacionVO" scope="session"/>
<jsp:setProperty name="identificacionPEIVO" property="*"/>
<jsp:useBean id="paramsVO" class="pei.registro.vo.ParamsVO" scope="page"/>
<html>
<head>
	<STYLE type='text/css'>
		.estadoPEI{
			COLOR: red;
			FONT-WEIGHT: bold;
			FONT-FAMILY: Arial, Helvetica, sans-serif;
			FONT-SIZE: 10pt;
		}
		.manual:hover{COLOR: red; FONT-WEIGHT: bold; FONT-FAMILY: Arial, Helvetica, sans-serif; FONT-SIZE: 10pt;}
		.manual:link{COLOR: red; FONT-WEIGHT: bold; FONT-FAMILY: Arial, Helvetica, sans-serif; FONT-SIZE: 10pt;}
		.manual:active{COLOR: red; FONT-WEIGHT: bold; FONT-FAMILY: Arial, Helvetica, sans-serif; FONT-SIZE: 10pt;}
		.manual:visited{COLOR: red; FONT-WEIGHT: bold; FONT-FAMILY: Arial, Helvetica, sans-serif; FONT-SIZE: 10pt;}
	</STYLE>
<SCRIPT language=javascript src='<c:url value="/pei/registro/NuevoIdentificacion.js"/>'></SCRIPT>
<script language="javaScript">var nav4=window.Event ? true : false;</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<!-- FORMULARIO DE FILTRO -->
	<c:if test="${sessionScope.filtroRegistroPEIVO.filDisabled==true}">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr><td width="100%" valign='top'>
				<div style="width:100%;overflow:auto;vertical-align:top;background-color:#ffffff;">
					<c:import url="/pei/registro/FiltroRegistro.jsp"/>
				</div>
			</td></tr>
		</table>
	</c:if>
	<!-- /FORMULARIO DE FILTRO -->
	<c:if test="${sessionScope.filtroRegistroPEIVO.filInstitucion>0}">
	<!-- TABS -->
	<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
		<tr height="1">
			<td width="10">&nbsp;</td>
			<td rowspan="2" width="469">
				<img src='<c:url value="/etc/img/tabs/pei/identificacion_1.gif"/>' alt="Identificacion" height="26" border="0">
				<a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_HORIZONTE}"/>)"><img src='<c:url value="/etc/img/tabs/pei/horizonte_0.gif"/>' alt="Horizonte" height="26" border="0"></a>
				<a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_DESARROLLO_CURRICULAR}"/>)"><img src='<c:url value="/etc/img/tabs/pei/desarrollo_curricular_0.gif"/>' alt="Desarrollo curricular" height="26" border="0"></a>
				<a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_DESARROLLO_ADMINISTRATIVO}"/>)"><img src='<c:url value="/etc/img/tabs/pei/desarrollo_administrativo_0.gif"/>' alt="Desarrollo administrativo" height="26" border="0"></a>
				<a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_DOCUMENTO}"/>)"><img src='<c:url value="/etc/img/tabs/pei/documento_0.gif"/>' alt="Documento" height="26" border="0"></a>
			</td>
		</tr>
	</table>
	
	    				   
	    			  
	<!-- /TABS -->
	<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='SaveIdentificacion.jsp'>
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_IDENTIFICACION}"/>'>
		<input type="hidden" name="cmd" value=''   >
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="GENERAR" value='<c:out value="${paramsVO.CMD_GENERAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="action2" value='<c:url value="/pei/registro/Nuevo.do"/>' >
		<INPUT TYPE="hidden" NAME="ext2"   VALUE=''>
		<input type="hidden" name="idenInstitucion" value='<c:out value="${sessionScope.identificacionPEIVO.idenInstitucion}"/>'>
		<input type="hidden" name="idenExiste" value='<c:out value="${sessionScope.identificacionPEIVO.idenExiste}"/>'>
		<input type="hidden" name="idenEstado" value='<c:out value="${sessionScope.identificacionPEIVO.idenEstado}"/>'>
		<input type="hidden" name="ESTADO_PEI_EN_CONSTRUCCION" value='<c:out value="${paramsVO.ESTADO_PEI_EN_CONSTRUCCION}"/>'>
		<input type="hidden" name="ESTADO_PEI_COMPLETO" value='<c:out value="${paramsVO.ESTADO_PEI_COMPLETO}"/>'>
		<c:forEach begin="0" items="${requestScope.listaEnfasis}" var="item"><input type="hidden" name="idenEnfasis_" value=<c:out value="${item.editable}"/>></c:forEach>
		<c:forEach begin="0" items="${requestScope.listaEnfoque}" var="item"><input type="hidden" name="idenEnfoque_" value=<c:out value="${item.editable}"/>></c:forEach>
		<table border="0" align="center" bordercolor="#FFFFFF" width="95%">
			<caption>Estado PEI: <span class='estadoPEI'><c:out value="${sessionScope.identificacionPEIVO.idenEstadoNombre}"/></span></caption>
				<tr>
					<td align="left" width="70%">
						<c:if test="${sessionScope.NivelPermiso==2 && sessionScope.identificacionPEIVO.idenDisabled==false}">
    					<input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton">
	    				</c:if>
	    				<c:if test="${sessionScope.identificacionPEIVO.idenDisabled==true}"><span class="style2">
	    				<input type="button" value=" Generar Reporte " style="width: 120px;height: 15px;font-family:Verdana, Arial,Helvetica;text-align: center;font-size: 10px;background-color: #009BB2;	color: #ffffff;" onclick="javaScript:reporte();"  >
	    				<br>
	    				No se puede actualizar</span></c:if>
			  		</td>
			  		<td align="right">
						<c:if test="${sessionScope.NivelPermiso==2 && sessionScope.identificacionPEIVO.idenDisabled==false}">
						<span class="style2">PEI COMPLETO:</span> <input name="chk" type="checkbox" value="1" onClick="completar(this)">
						</c:if>
						<a class="manual" href='<c:url value="${sessionScope.filtroRegistroPEIVO.filManual}"/>' target="_blank">&nbsp;Descargue el manual</a>			  		
			  		</td>
			 	</tr>	
		 </table>
		  <table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
			<tr>
				<td width="30%" title='Nombre del rector'><span class="style2">*</span> Nombre del rector:</td>
				<td width="25%" colspan="3">
					<input type="text" name="idenRector" maxlength="250" size="50" <c:out value="${sessionScope.identificacionPEIVO.idenDisabled_}"/> value='<c:out value="${sessionScope.identificacionPEIVO.idenRector}"/>' readonly="readonly"></input>				
				</td>
		 	</tr>	
			<tr>
				<td><span class="style2">*</span> Teléfono:</td>
				<td>
					<input type="text" name="idenTelefono" maxlength="50" size="15" <c:out value="${sessionScope.identificacionPEIVO.idenDisabled_}"/> value='<c:out value="${sessionScope.identificacionPEIVO.idenTelefono}"/>' readonly="readonly"></input>
				</td>
				<td><span class="style2">*</span> Correo:</td>
				<td>
					<input type="text" name="idenCorreo" maxlength="50" size="25" <c:out value="${sessionScope.identificacionPEIVO.idenDisabled_}"/> value='<c:out value="${sessionScope.identificacionPEIVO.idenCorreo}"/>' readonly="readonly"></input>
				</td>
		 	</tr>	
			<tr>
				<td><span class="style2">*</span> Estudiantes matriculados:</td>
				<td>
					<input type="text" name="idenEstudiantes" maxlength="10" size="10" <c:out value="${sessionScope.identificacionPEIVO.idenDisabled_}"/> value='<c:out value="${sessionScope.identificacionPEIVO.idenEstudiantes}"/>' readonly="readonly"></input>
				</td>
				<td><span class="style2">*</span> Número de sedes:</td>
				<td>
					<input type="text" name="idenSedes" maxlength="3" size="3" <c:out value="${sessionScope.identificacionPEIVO.idenDisabled_}"/> value='<c:out value="${sessionScope.identificacionPEIVO.idenSedes}"/>' onKeyPress='return acepteNumeros(event)' readonly="readonly"></input>
				</td>
		 	</tr>	
			<tr>
				<td><span class="style2">*</span> Número de jornadas:</td>
				<td>
					<input type="text" name="idenJornadas" maxlength="3" size="3" <c:out value="${sessionScope.identificacionPEIVO.idenDisabled_}"/> value='<c:out value="${sessionScope.identificacionPEIVO.idenJornadas}"/>' onKeyPress='return acepteNumeros(event)'></input>
				</td>
			</tr>
			
			
			
			<!-- Ajustes nuevos PEI -->
			<tr>
				<td colspan="4" class="Fila0"><span class="style2">*</span> Carácter del Colegio</td>
			</tr>
			
			<tr>
				<td>
					<input type="radio" name="idenCaracter" <c:out value="${sessionScope.identificacionPEIVO.idenDisabled_}"/> value='1' <c:if test="${sessionScope.identificacionPEIVO.idenCaracter == '1'}">checked</c:if>></input>Urbano
				</td>
				<td>
					<input type="radio" name="idenCaracter" <c:out value="${sessionScope.identificacionPEIVO.idenDisabled_}"/> value='2' <c:if test="${sessionScope.identificacionPEIVO.idenCaracter == '2'}">checked</c:if>></input>Rural
				</td>
			</tr>
			
			
			<tr>
				<td colspan="4" class="Fila0"><span class="style2">*</span> Educacion formal para Adultos</td>
			</tr>
			<tr>
				<td colspan="4" class="Fila0">
					El colegio brinda educación formal para adultos
				<td/>
			</tr>
			<tr>
				<td>
					<input type="radio" name="idenEducacionFormalAdulos" <c:out value="${sessionScope.identificacionPEIVO.idenDisabled_}"/> value='1' <c:if test="${sessionScope.identificacionPEIVO.idenEducacionFormalAdulos == '1'}">checked</c:if>></input>Si
				</td>
				<td>
					<input type="radio" name="idenEducacionFormalAdulos" <c:out value="${sessionScope.identificacionPEIVO.idenDisabled_}"/> value='0' <c:if test="${sessionScope.identificacionPEIVO.idenEducacionFormalAdulos == '0'}">checked</c:if>></input>No
				</td>
			</tr>
			<tr>
				<td colspan="3">
					Cantidad de alumnos adultos del colegio
				</td>
			
				<td>
					<input type="text" name="idenCantidadEducacionFormalAdultos" maxlength="4" <c:out value="${sessionScope.identificacionPEIVO.idenDisabled_}"/> value='<c:out value="${sessionScope.identificacionPEIVO.idenCantidadEducacionFormalAdultos}"/>' onKeyPress='return acepteNumeros(event)'></input>
				</td>
			</tr>
			<tr>
				<td colspan="4" class="Fila0"><span class="style2">*</span>Aceleración del Aprendizaje</td>
			</tr>
			<tr>
				<td colspan="4" class="Fila0">
					El colegio trabaja con metodología "Aceleración del Aprendizaje"
				<td/>
			</tr>
			<tr>
				<td>
					<input type="radio" name="idenAceleracionAprendizaje" <c:out value="${sessionScope.identificacionPEIVO.idenDisabled_}"/> value='1' <c:if test="${sessionScope.identificacionPEIVO.idenAceleracionAprendizaje == '1'}">checked</c:if>></input>Si
				</td>
				<td>
					<input type="radio" name="idenAceleracionAprendizaje" <c:out value="${sessionScope.identificacionPEIVO.idenDisabled_}"/> value='0' <c:if test="${sessionScope.identificacionPEIVO.idenAceleracionAprendizaje == '0'}">checked</c:if>></input>No
					
				</td>
			</tr>
			<tr>
				<td colspan="3">
					Cantidad de alumnos adultos del colegio en "Aceleración del Aprendizaje".
				</td>
				<td>
					<input type="text" name="idenCantidadAceleracionAprendizaje" maxlength="4" <c:out value="${sessionScope.identificacionPEIVO.idenDisabled_}"/> value='<c:out value="${sessionScope.identificacionPEIVO.idenCantidadAceleracionAprendizaje}"/>' onKeyPress='return acepteNumeros(event)'></input>
				</td>
			</tr>
			
			<!-- ----------------- -->
			
			
			<tr>
				<td colspan="4" class="Fila0"><span class="style2">*</span> Existencia del PEI</td>
			</tr>
			<tr>
				<td colspan="4" class="Fila0">Dado que existen colegios de muy reciente creación, indicar si en la actualidad existe o no, una propuesta de Proyecto Educativo Institucional</td>
			</tr>
			<tr>
				<td><span class="style2">*</span> Nombre del PEI:</td>
				<td colspan="3">
					<input type="text" name="idenNombre" maxlength="120" size="70" <c:out value="${sessionScope.identificacionPEIVO.idenDisabled_}"/> value='<c:out value="${sessionScope.identificacionPEIVO.idenNombre}"/>'></input>				
				</td>
		 	</tr>	
			<tr>
				<td colspan="4" class="Fila0"  title='<c:out value="${sessionScope.filtroRegistroPEIVO.lblEtapa}"/>'>Señalar la etapa de desarrollo en la que se encuentra el PEI</td>
			</tr>
			<tr>
				<td colspan="4">
					<table width="100%" border="0" align="center"><tr>
					<c:forEach begin="0" items="${requestScope.listaEtapa}" var="etapa" varStatus="st">
						<td title='<c:out value="${etapa.descripcion}"/>'>
						<input type="radio" name="idenEtapa" <c:out value="${sessionScope.identificacionPEIVO.idenDisabled_}"/> value='<c:out value="${etapa.codigo}"/>' <c:if test="${etapa.codigo==sessionScope.identificacionPEIVO.idenEtapa}">checked</c:if> ></input> <c:out value="${etapa.nombre}"/>
						</td>
						<c:if test="${st.count%4==0}"></tr><tr></c:if>
					</c:forEach>
					</tr>
					</table>				
				</td>
		 	</tr>	
			<tr>
				<td colspan="4" class="Fila0" title='<c:out value="${sessionScope.filtroRegistroPEIVO.lblEnfasis}"/>'>Señalar cuál de los siguientes énfasis guarda afinidad con la propuesta del colegio</td>
			</tr>
			<tr>
				<td colspan="4">
					<table width="100%" border="0" align="center"><tr>
					<c:forEach begin="0" items="${requestScope.listaEnfasis}" var="enfasis" varStatus="st">
						<td title='<c:out value="${enfasis.descripcion}"/>'>
						<input type="radio" name="idenEnfasis" <c:out value="${sessionScope.identificacionPEIVO.idenDisabled_}"/> value='<c:out value="${enfasis.codigo}"/>' <c:if test="${enfasis.codigo==sessionScope.identificacionPEIVO.idenEnfasis}">checked</c:if> onchange="javaScript:setCheckEnfasis(<c:out value="${enfasis.editable}"/>)"></input><c:out value="${enfasis.nombre}"/>
						</td>
						<c:if test="${st.count%4==0}"></tr><tr></c:if>
					</c:forEach>
					</tr>
					</table>
					<table width="100%" border="0" align="center">
					<tr><td>¿Cuál?</td>
					<td><input type="text" name="idenEnfasisOtro" maxlength="100" size="40" <c:out value="${sessionScope.identificacionPEIVO.idenDisabled_}"/> value='<c:out value="${sessionScope.identificacionPEIVO.idenEnfasisOtro}"/>'></input></td>
					</tr>
					</table>				
				</td>
		 	</tr>	
			<tr>
				<td colspan="4" class="Fila0"  title='<c:out value="${sessionScope.filtroRegistroPEIVO.lblEnfoque}"/>'>Seleccionar el enfoque pedagógico con mayor afinidad con el desarrollado en el colegio</td>
			</tr>
			<tr>
				<td colspan="4">
					<table width="100%" border="0" align="center"><tr>
					<c:forEach begin="0" items="${requestScope.listaEnfoque}" var="enfoque" varStatus="st">
						<td title='<c:out value="${enfoque.descripcion}"/>'>
						<input type="radio" name="idenEnfoque" <c:out value="${sessionScope.identificacionPEIVO.idenDisabled_}"/> value='<c:out value="${enfoque.codigo}"/>' <c:if test="${enfoque.codigo==sessionScope.identificacionPEIVO.idenEnfoque}">checked</c:if> onchange="javaScript:setCheckEnfoque(<c:out value="${enfoque.editable}"/>)"></input><c:out value="${enfoque.nombre}"/>
						</td>
						<c:if test="${st.count%4==0}"></tr><tr></c:if>
					</c:forEach>
					</tr>
					</table>				
					<table width="100%" border="0" align="center">
					<tr><td>¿Cuál?</td>
					<td><input type="text" name="idenEnfoqueOtro" maxlength="100" size="40" <c:out value="${sessionScope.identificacionPEIVO.idenDisabled_}"/> value='<c:out value="${sessionScope.identificacionPEIVO.idenEnfoqueOtro}"/>'></input></td>
					</tr>
					</table>				
				</td>
		 	</tr>
		 	<tr>
				<td colspan="4">
					<iframe id="frameAjuste" name="frameAjuste" marginheight="0" marginwidth="0"  frameborder="0" width="100%" height="300px" 
						src='<c:url value="Nuevo.do"><c:param name="tipo" value="${paramsVO.FICHA_AJUSTES}"/><c:param name="cmd" value="${paramsVO.CMD_BUSCAR}"/></c:url>' 
						onload='reSize()'>
					</iframe>
				</td>
			</tr>	
		</table>
	</form>
	<script type="text/javascript">
		setCeros();
		<c:if test="${!sessionScope.identificacionPEIVO.idenDisabled}">setEnfasis(document.frmNuevo);setEnfoque(document.frmNuevo);</c:if>
	</script>
	</c:if>
	
	<script type="text/javascript">
	 	<c:if test="${requestScope.resultadoConsultaVO.generado==true}">
		 document.frmNuevo.action = '<c:url value="/${requestScope.resultadoConsultaVO.ruta}"><c:param name="tipo" value="${requestScope.resultadoConsultaVO.tipo}"/></c:url>';
		 document.frmNuevo.submit();
		</c:if>
	</script>	
</body>
</html>	