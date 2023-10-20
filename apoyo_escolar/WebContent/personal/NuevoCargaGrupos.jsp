<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>
	
	function cerrarVentana(){
		parent.close();
	}

	function guardar(){
		var gruposSeleccionados = 0;
		if(document.frmGruposCarga.checkBoxGruposDocente_.length){
		
			for (var i =0; i<document.frmGruposCarga.checkBoxGruposDocente_.length;i++){
				var grupo = document.frmGruposCarga.checkBoxGruposDocente_[i]; 
					
				if (grupo.checked){
					gruposSeleccionados++;
					}
				}
			}else if (document.frmGruposCarga.checkBoxGruposDocente_.checked){
				gruposSeleccionados++;	
		}
		if(gruposSeleccionados > 0){
			document.frmGruposCarga.submit();
			//parent.close();
		}else{
			//parent.close();
		}
	}
	
</script>

	<form method="post" name="frmGruposCarga" action='<c:url value="/personal/ControllerNuevoEdit.do"/>' >
		
		<input type="hidden" name="rotdagdocente" value='<c:out value="${sessionScope.nuevoPersonal.pernumdocum}"/>'>
		<input type="hidden" name="rotdagVigencia" value='<c:out value="${sessionScope.nuevoPersonal2.perVigencia}"/>'>
		
		<input type="hidden" name="sedeSeleccionada" value='<c:out value="${sessionScope.sedeSeleccionada}"/>'>
		<input type="hidden" name="jornadaSeleccionada" value='<c:out value="${sessionScope.jornadaSeleccionada}"/>'>
		<input type="hidden" name="metodologiaSeleccionada" value='<c:out value="${sessionScope.metodologiaSeleccionada}"/>'>
		<input type="hidden" name="asignaturaSeleccionada" value='<c:out value="${requestScope.asignaturaSeleccionada}"/>'>
		<input type="hidden" name="tipo" value='13'>
		
		<c:if test="${not empty sessionScope.listaGrupos}">
		
		
			<table border="0" align="center" bordercolor="#FFFFFF" width="95%">
					<caption>SELECCIONAR GRUPOS DEL DOCENTE</caption>
					<tr>
						<td align="left" width="70%">
							
	    					<input name="cmd1" type="submit" value="Guardar"  class="boton" onClick="guardar()">
		    				<input name="cmd1" type="button" value="Cancelar" onClick="cerrarVentana()" class="boton">
				  		</td>
				 	</tr>	
			 </table>
			  <table border="1" align="center" width="95%" cellpadding="1" cellspacing="0">
				<caption>GRADO <c:out value="${requestScope.nombreGradoSeleccionado}"/></caption>
				<caption><c:out value="${requestScope.NombreAsignaturaSeleccionada}"/></caption>
				<tr>
					<th class="EncabezadoColumna">&nbsp</th>
					<th class="EncabezadoColumna">Codigo</th>
					<th class="EncabezadoColumna">Nombre</th>
					<th class="EncabezadoColumna">Codigo Jerarquia</th>
				</tr>
				<c:forEach begin="0" items="${sessionScope.listaGrupos}" var="grupo" varStatus="stGrupo">
					<tr>
						<td>
							<input type='checkbox' name='checkBoxGruposDocente_' value='<c:out value="${grupo.gruCodigoJerarquiaGrupo}"/>' 
								<c:forEach items="${sessionScope.listaGruposDocente}" var="grupoDocente">
									<c:if test="${grupo.gruCodigoJerarquiaGrupo eq grupoDocente}">checked</c:if>
								</c:forEach>
							/>
							
						</td>
						<td>
							<c:out value="${grupo.gruCodigo}"/>
						</td>
						<td>
							<c:out value="${grupo.gruNombre}"/>
						</td>
						<td>
							<c:out value="${grupo.gruCodigoJerarquia}"/><br/>
							<c:out value="${grupo.gruCodigoJerarquiaGrupo}"/>
						</td>
					</tr>
				</c:forEach>	
			</table>
		</c:if>
		<c:if test="${empty sessionScope.listaGrupos}">
			NO HAY RESULTADOS PARA ESTA BUSQUEDA
		</c:if>
		</form>
