<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage="" %>
<jsp:useBean id="paramsVO" class="articulacion.asigGrupo.vo.ParamsVO" scope="session"/>
<%@include file="../../parametros.jsp"%>
<c:set var="tip" value="8" scope="page"/>
<script language="javaScript">
<!--
			
	var nav4=window.Event ? true : false;

	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}

	
	function validarMaximoCampo(caja){

		var elementos = document.getElementsByTagName("input");
	    var elements = []; 
	    for (var index = 0; index < elementos.length; index++)
	        if (elementos[index].type == 'text'){
	        	if(elementos[index].id =="rotdagIHgrados_"){
	        		elements.push(elementos[index]);
	        	}
	        }	
		
		var maximo = parseInt(parent.document.frmNuevo.rotdaghoras.value);
		//var elements = document.getElementById("rotdagIHgrados_");
		var sumaCarga=0;
		while(elements.length >0){
			var elemento =elements.shift(); 
			if (elemento.value != ""){
		    	sumaCarga+=parseInt(elemento.value);
		    }
		}
			
		if(sumaCarga > parent.document.frmNuevo.rotdaghoras.value){
			alert("el total de horas asignadas excede en "+(sumaCarga-parent.document.frmNuevo.rotdaghoras.value)+" el numero de horas semanales disponibles del docente");
			caja.value = "";
		}
	}
	
	function validarUnicoGrupo(caja){
		if(caja.checked){
			var elementos = document.getElementsByName(caja.name);
		    for(var i = 0; i<elementos.length; i++){
		    	var elemento = elementos[i];
		    	if(elemento.value != caja.value && elemento.checked){
		    		caja.checked = false;
		    		break;
		    	}
		    	
		    }
		}
	}

//-->
</script>
	<style>
	th,td {
	    padding: 0px;
	    border: 1px solid #000;
		height:20px;
	    white-space: nowrap;
	    }
		
	.tablaAsignaturas {
	    float: left;   
	    }        
	.tablaGrados {
	    width: 25%;
	    overflow: auto;  
	    }
		
	.tablaAsignaturas, .tablaGrados>table th,td{
		height:30px;
	    }	
	#divCargaAcademica{
		width: 705px;
		}
	</style>
	<form method="post" name="frmCarga" action='<c:url value="/articulacion/asigGrupo/AjaxCarga.do"/>'>
		<input TYPE="hidden" NAME="tipo" value='<c:out value="${sessionScope.paramsVO.FICHA_ASIGNAR_GRUPO}"/>'>
		<input TYPE="hidden" NAME="cmd" VALUE='<c:out value="${sessionScope.paramsVO.CMD_AJAX_GUARDAR}"/>'>
		<input type="hidden" name="asignatura">
		<input type="hidden" name="grupo">

		
		<div id="divCargaAcademica">
		<table border="1" cellspacing="0" cellpadding="0" class="tablaAsignaturas" >
			<tr>
				<th class="EncabezadoColumna" colspan="3">Estudiante</th>
			</tr>
			<tr>
				<th class="EncabezadoColumna">Codigo</th>
				<th class="EncabezadoColumna">Apellidos</th>
				<th class="EncabezadoColumna">Nombres</th>
			</tr>
			<c:forEach begin="0" items="${sessionScope.lEstudiantesArt}" var="estudianteArt">
				<tr>
					<td style="white-space: nowrap; text-align: center;">
						<c:out value="${estudianteArt.estCodigo}"/>
					</td>
					<td style="white-space: nowrap; text-align: center;">
						<c:out value="${estudianteArt.estApellido1}"/> <c:out value="${estudianteArt.estApellido2}"/>
					</td>
					<td style="white-space: nowrap; text-align: center;">
						<c:out value="${estudianteArt.estNombre1}"/> <c:out value="${estudianteArt.estNombre2}"/>
					</td>
					
				</tr>
			</c:forEach>
		</table>
			<div style="overflow-x:auto; ">
				<table border="1" width="100%" cellpadding="0" cellspacing="0" class="tablaGrados">
					<tr>
						<c:forEach begin="0" items="${sessionScope.lGruposArt}" var="grupoArt">
								<th class="EncabezadoColumna" style="white-space: nowrap; height: 40px">
									<c:out value="${grupoArt.artGruConsecutivo}"/>
								</th>
						</c:forEach>
						
						<c:forEach begin="0" items="${sessionScope.lEstudiantesArt}" var="estudianteArt">
							<tr>
								<c:forEach begin="0" items="${sessionScope.lGruposArt}" var="grupoArt" varStatus="st0">
										<td style="white-space: nowrap; text-align: center;">
											<input type='checkbox' name='<c:out value="${estudianteArt.estCodigo}"/>' id='<c:out value="${estudianteArt.estCodigo}"/>' value='<c:out value="${grupoArt.artGruCodigo}"/>' onchange="validarUnicoGrupo(this);"
											<c:forEach begin="0" items="${sessionScope.lAsignaciones}" var="asignacionArt">
												<c:if test="${asignacionArt.artGruCodigoEstudiante eq estudianteArt.estCodigo and asignacionArt.artGruCodigoGrupo eq grupoArt.artGruCodigo}">
													checked="true"
												</c:if>
											</c:forEach>
											
											/>
										</td>
								</c:forEach>
							</tr>
						</c:forEach>
					</tr>
				</table>
			</div>
		</div>
		
	</form>


					
					