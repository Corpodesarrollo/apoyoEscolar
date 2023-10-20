<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="circularFiltro" class="siges.gestionAdministrativa.agenda.vo.FiltroCircularesVO" scope="session"/>
<jsp:useBean id="paramsVO" class="siges.gestionAdministrativa.agenda.vo.ParamsVO" scope="page"/>
<html>
<c:import url="/parametros.jsp"/>
<head>
<script language="javaScript">
	
	function hacerValidaciones_frmFiltro(forma){
		validarLista(forma.mes, "- Mes", 1);
		validarLista(forma.nivel, "- Nivel", 1);
	}

	function buscar(){
		if(validarForma(document.frmFiltro)){
			document.frmFiltro.cmd.value=document.frmFiltro.BUSCAR.value;
			document.frmFiltro.submit();
		}
	}
	
	function editar(m){
		document.frmFiltro.cmd.value=document.frmFiltro.EDITAR.value;
		document.frmFiltro.ajax[0].value=m;
		document.frmFiltro.submit();
	}
	
	function download(name){
		document.listado.dir.value='/apoyo_escolar/private/resultados/uploadFiles/'+name;
		document.listado.tipo.value='doc';
		document.listado.action='/apoyo_escolar/private/resultados/uploadFiles/'+name;
		document.listado.submit();
	}
		
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form  name='listado' method='post' target='_blank'>
		<input type='hidden' name='aut' value='1'>
		<input type='hidden' name='dir'>
		<input type='hidden' name='tipo'>
	</form>
	<form method="post" name="frmAjax0" action='<c:url value="/siges/gestionAdministrativa/Ajax.do"/>'>
		<input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_AJAX}"/>'>
		<input type="hidden" name="ajax">
		<input type="hidden" name="ajax">
		<input type="hidden" name="ajax">
	</form>
	<form action='<c:url value="/siges/gestionAdministrativa/agenda/circulares/Save.jsp"/>' method="post" name="frmFiltro" >
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
		<input type="hidden" name="cmd"  value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="AJAX_INSTANCIA0" value='<c:out value="${paramsVO.CMD_AJAX_INSTANCIA0}"/>'> 
		<input type="hidden" name="GRADO" value='<c:out value="${paramsVO.CMD_AJAX_GRAD}"/>'> 
		<input type="hidden" name="GRUPO" value='<c:out value="${paramsVO.CMD_AJAX_GRUP}"/>'> 
		<input type="hidden" name="ASIGNATURA" value='<c:out value="${paramsVO.CMD_AJAX_AREA}"/>'> 
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_CIRCULARES}"/>'> 
		<input type="hidden" name="ajax">
		<input type="hidden" name="ajax">

		<table border="0" align="center" width="95%">
		 	<caption>Filtro de búsqueda</caption>
			<tr>
				<td>
 					<input name="cmd1" type="button" value="Buscar" onClick="buscar()" class="boton">
		  		</td>
		 	</tr>	
		 	<tr style="display:none"><td><iframe name="frameAjax0" id="frameAjax0"></iframe></td></tr>
		</table>
	

	<TABLE width="90%" cellpadding="2" cellSpacing="2" align="center">
		<tr>	
			<td><span class="style2" >*</span>Mes:</td>
			<td>
		      	<select name="mes" style='width:180px;'>
			        <option value='-9'>-- Seleccione mes --</option>
					<option value='1' <c:if test="${1==sessionScope.circularFiltro.mes}">selected</c:if>>Enero</option>
					<option value='2' <c:if test="${2==sessionScope.circularFiltro.mes}">selected</c:if>>Febrero</option>
					<option value='3' <c:if test="${3==sessionScope.circularFiltro.mes}">selected</c:if>>Marzo</option>
					<option value='4' <c:if test="${4==sessionScope.circularFiltro.mes}">selected</c:if>>Abril</option>
					<option value='5' <c:if test="${5==sessionScope.circularFiltro.mes}">selected</c:if>>Mayo</option>
					<option value='6' <c:if test="${6==sessionScope.circularFiltro.mes}">selected</c:if>>Junio</option>
					<option value='7' <c:if test="${7==sessionScope.circularFiltro.mes}">selected</c:if>>Julio</option>
					<option value='8' <c:if test="${8==sessionScope.circularFiltro.mes}">selected</c:if>>Agosto</option>
					<option value='9' <c:if test="${9==sessionScope.circularFiltro.mes}">selected</c:if>>Septiembre</option>
					<option value='10' <c:if test="${10==sessionScope.circularFiltro.mes}">selected</c:if>>Octubre</option>
					<option value='11' <c:if test="${11==sessionScope.circularFiltro.mes}">selected</c:if>>Noviembre</option>
					<option value='12' <c:if test="${12==sessionScope.circularFiltro.mes}">selected</c:if>>Diciembre</option>
		        </select>
	        </td>
	     
				<td><span class="style2" >*</span>Nivel:</td>
				<td>
			      	<select name="nivel"  style='width:120px;'>
							<option value='-9'>Seleccione Nivel</option>
							<option value='0' <c:if test="${0==sessionScope.circularFiltro.nivel}">selected</c:if>>Todas</option>
							<option value='1' <c:if test="${1==sessionScope.circularFiltro.nivel}">selected</c:if>>Colegio</option>
							<option value='2' <c:if test="${2==sessionScope.circularFiltro.nivel}">selected</c:if>>Jornada</option>
							<option value='3' <c:if test="${3==sessionScope.circularFiltro.nivel}">selected</c:if>>Sede</option>
							<option value='4' <c:if test="${4==sessionScope.circularFiltro.nivel}">selected</c:if>>Sede-Jornada</option>
							<option value='5' <c:if test="${5==sessionScope.circularFiltro.nivel}">selected</c:if>>Metodología-Grado</option>
							<option value='6' <c:if test="${6==sessionScope.circularFiltro.nivel}">selected</c:if>>Grupo</option>
			        </select>
			    </td>
	  		</tr>
	  
	 <tr>
		<td>Estado:</td>
		<td>
	       	<select name="estado"  style='width:180px;'>
				<option value='0' <c:if test="${0==sessionScope.circularFiltro.estado}">selected</c:if>>Todas --</option>
				<option value='1' <c:if test="${1==sessionScope.circularFiltro.estado}">selected</c:if>>Activas --</option>
				<option value='2' <c:if test="${2==sessionScope.circularFiltro.estado}">selected</c:if>>Inactivas --</option>
      		</select>
	   </td>
	 </tr>
	 </TABLE>
	 
		
		
		<br> 	
		<!-- TABLA DE LOS RESULTADOS DE LA BUSQUEDA -->
		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray">
		 	<caption>Listado de circulares</caption>
		 	<c:if test="${empty requestScope.listaCirculares}"><tr><th class="Fila1" colspan='6'>No hay circulares</th></tr></c:if>
			<c:if test="${!empty requestScope.listaCirculares}">
				<tr>
					<c:if test="${sessionScope.circularFiltro.nivelUsuario != 510}">
						<td width='5%' class="EncabezadoColumna" align="center">Acción</td>
					</c:if>
					<td width='10%' class="EncabezadoColumna" align="center">Nivel</td>
					<td width='30%' class="EncabezadoColumna" align="center">Asunto</td>
					<td width='30%' class="EncabezadoColumna" align="center">Descargar</td>
					<td width='30%' class="EncabezadoColumna" align="center">Fecha</td>
					<td width='30%' class="EncabezadoColumna" align="center">Estado</td>
				</tr>
				<c:forEach begin="0" items="${requestScope.listaCirculares}" var="list" varStatus="st">
					<tr>
						<c:if test="${sessionScope.circularFiltro.nivelUsuario != 510}">
							<th class='Fila<c:out value="${st.count%2}"/>'>
								<a href='javaScript:editar(<c:out value="${list.codigo}"/>);'><img border='0' src='<c:url value="/etc/img/editar.png"/>' width='15' height='15'></a>
							</th>
						</c:if>
						<c:if test="${list.nivel == 1}">
							<th class='Fila<c:out value="${st.count%2}"/>'>&nbsp;Colegio&nbsp;</th>
						</c:if>
						<c:if test="${list.nivel == 2}">
							<th class='Fila<c:out value="${st.count%2}"/>'>&nbsp;Sede&nbsp;</th>
						</c:if>
						<c:if test="${list.nivel == 3}">
							<th class='Fila<c:out value="${st.count%2}"/>'>&nbsp;Jornada&nbsp;</th>
						</c:if>
						<c:if test="${list.nivel == 4}">
							<th class='Fila<c:out value="${st.count%2}"/>'>&nbsp;Sede-Jornada&nbsp;</th>
						</c:if>
						<c:if test="${list.nivel == 5}">
							<th class='Fila<c:out value="${st.count%2}"/>'>&nbsp;Metodologia-Grado&nbsp;</th>
						</c:if>
						<c:if test="${list.nivel == 6}">
							<th class='Fila<c:out value="${st.count%2}"/>'>&nbsp;Grupo&nbsp;</th>
						</c:if>
						<c:if test="${list.nivel > 6}">
       <th class='Fila<c:out value="${st.count%2}"/>'>&nbsp;&nbsp;</th>
      </c:if>
						<th class='Fila<c:out value="${st.count%2}"/>'>&nbsp;<c:out value="${list.asunto}"/>&nbsp;</th> 
						<c:if test="${list.archivo!='tempData'}">
							<th class='Fila<c:out value="${st.count%2}"/>'>&nbsp;<input name="cmd2" type="button" value="Descargar" onClick='download("<c:out value="${list.archivo}"/>")' class="boton">&nbsp;</th>
						</c:if>
						<c:if test="${list.archivo=='tempData'}">
							<th class='Fila<c:out value="${st.count%2}"/>'>&nbsp;-&nbsp;</th>
						</c:if>
						<th class='Fila<c:out value="${st.count%2}"/>'>&nbsp;<c:out value="${list.fechaRegistro}"/>&nbsp;</th>
						<c:if test="${list.estado == 1}">
							<th class='Fila<c:out value="${st.count%2}"/>'>&nbsp;Activa&nbsp;</th>
						</c:if>
						<c:if test="${list.estado == 2}">
							<th class='Fila<c:out value="${st.count%2}"/>'>&nbsp;Inactiva&nbsp;</th>
						</c:if>
						
					</tr>
				</c:forEach>
			</c:if>
		</table>
	</form>
</body>
</html>
