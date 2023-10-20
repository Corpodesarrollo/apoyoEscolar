<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage="" %>
<jsp:useBean id="nuevoPersonal" class="siges.personal.beans.Personal" scope="session"/><jsp:setProperty name="nuevoPersonal" property="*"/>
<jsp:useBean id="nuevoConvivencia" class="siges.estudiante.beans.Convivencia" scope="session"/><jsp:setProperty name="nuevoConvivencia" property="*"/>
<jsp:useBean id="carga" class="siges.personal.beans.Carga" scope="session"/>
<jsp:useBean id="nuevoSalud" class="siges.estudiante.beans.Salud" scope="session"/><jsp:setProperty name="nuevoSalud" property="*"/>
<jsp:useBean id="laboralVO" class="siges.personal.beans.LaboralVO" scope="session"/><jsp:setProperty name="laboralVO" property="*"/>
<jsp:useBean id="asistenciaVO" class="siges.estudiante.beans.AsistenciaVO" scope="session"/><jsp:setProperty name="asistenciaVO" property="*"/>
<jsp:useBean id="formacionVO" class="siges.personal.beans.FormacionVO" scope="session"/><jsp:setProperty name="formacionVO" property="*"/>
<jsp:useBean id="paramsVO" class="siges.personal.beans.ParamsVO" scope="page"/>
<%@include file="../parametros.jsp"%><c:set var="tip" value="8" scope="page"/>
<script language="javaScript">
<!--
			var fichaPersonal=1;
			var fichaSede=1;
			var fichaConvivencia=1;
			var fichaSalud=1;
			var fichaLaboral=1;
			var fichaAcademica=1;
			var fichaAsistencia=1;
			var fichaCarga=1;
			var fichaFoto=1;
			var nav4=window.Event ? true : false;

	
	
	function cancelar(){
		location.href='<c:url value="/bienvenida.jsp"/>';
	}

	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}

	function getSubTotal(obj,obj2){
			var subTotal=0;
			if(obj.length){
				for(var i=0;i<obj.length;i++){
					if(obj[i].disabled==false && obj[i].checked==true){
						subTotal=parseInt(subTotal)+parseInt(obj2[i].value);
					}
				}
			}else{
					if(obj.disabled==false && obj.checked==true){
						subTotal=parseInt(subTotal)+parseInt(obj2.value);
					}
			}			
			return subTotal;
	}
	
	function guardar(tipo){
		if(validarForma(document.frmNuevo)){
			var subTotalUsuario=getSubTotal(document.frmNuevo.rotdaggrados_,document.frmNuevo.rotdagIHgrados_);//subtotoal de la asigantura del usuario
			var totalServidor=parseInt(document.frmNuevo.ihTotal2.value);//la carga total del servidor
			var subTotalServidor=parseInt(document.frmNuevo.ihTotal3.value);//subtotoal de la asigantura del servidor
			var totalUsuario=parseInt(document.frmNuevo.rotdaghoras.value);///lo que el usuario dice como total semanal
			var validacion=parseInt(totalServidor)-parseInt(subTotalServidor)+parseInt(subTotalUsuario)-parseInt(totalUsuario);
			var sobrante=parseInt(totalUsuario)-parseInt(totalServidor)-parseInt(subTotalServidor);
			/*
			alert('subTotalUsuario:'+subTotalUsuario);
			alert('totalServidor:'+totalServidor);
			alert('subTotalServidor:'+subTotalServidor);
			alert('totalUsuario:'+totalUsuario);
			alert('validacion:'+validacion);
			alert('sobrante:'+sobrante);
			*/
			if(parseInt(validacion)>0){
				alert('La carga académica excede el total de la semana. Solo dispone de '+sobrante+' para asignar');
				return false;
			}
			if(document.frmNuevo.rotdaggrados.length){
				for(var i=0;i<document.frmNuevo.rotdaggrados.length;i++){
					if(document.frmNuevo.rotdaggrados_[i].disabled==true || document.frmNuevo.rotdaggrados_[i].checked==false){
						document.frmNuevo.rotdaggrados[i].disabled=true;
						document.frmNuevo.rotdaggrados[i].value='';
						
						document.frmNuevo.rotdagIHgrados_[i].disabled=true;
						document.frmNuevo.rotdagIHgrados_[i].value='';
					}
				}
			}else{
					if(document.frmNuevo.rotdaggrados_.disabled==true || document.frmNuevo.rotdaggrados_.checked==false){
						document.frmNuevo.rotdaggrados.disabled=true;document.frmNuevo.rotdaggrados.value='';
						document.frmNuevo.rotdagIHgrados_.disabled=true;document.frmNuevo.rotdagIHgrados_.value='';
					}
			}
			document.frmNuevo.tipo.value=tipo;
			document.frmNuevo.cmd.value="Guardar";
			document.frmNuevo.submit();
		}	
	}

	function hacerValidaciones_frmNuevo(forma){
		validarLista(forma.rotdagsede, "- Seleccione una sede",1)
		validarLista(forma.rotdagjornada, "- Seleccione una jornada",1)
		validarLista(forma.rotdagasignatura, "- Seleccione una asignatura",1)
		validarEntero(forma.rotdaghoras, "- Total horas semanales", 1, 99)
		if(forma.rotdaggrados.length){
			for(var i=0;i<forma.rotdaggrados.length;i++){
				if(forma.rotdaggrados_[i].disabled==false && forma.rotdaggrados_[i].checked==true){
					validarEntero(forma.rotdagIHgrados_[i], "- Total horas semanales de grados seleccionados", 1, 99)
				}
			}
		}else{
				if(forma.rotdaggrados_.disabled==false && forma.rotdaggrados_.checked==true){
					validarEntero(forma.rotdagIHgrados_, "- Total horas semanales de grados seleccionados", 1, 99)
				}
		}
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
	
	function lanzar(tipo){
		document.frmNuevo.tipo.value=tipo;
		document.frmNuevo.action='<c:url value="/personal/ControllerNuevoEdit.do"/>';
		document.frmNuevo.target="";
		
		document.frmNuevo.submit();
	}
	
	function buscar(){
		document.frmNuevo.tipo.value='8';
		document.frmNuevo.action='<c:url value="/personal/FiltroSave.jsp"/>';
		document.frmNuevo.submit();
	}

	function agregar(grado,nombreGrado,asignatura,nombreAsignatura){
		document.frmCarga.tipo.value=12;
		document.frmCarga.gradoSeleccionado.value=grado;
		document.frmCarga.nombreGradoSeleccionado.value=nombreGrado;
		document.frmCarga.asignaturaSeleccionada.value=asignatura;
		document.frmCarga.nombreAsignaturaSeleccionada.value=nombreAsignatura;
		remote = window.open("","1","width=400,height=400,scrollbars=yes,resizable=yes,toolbar=no,directories=no,menubar=no,status=no");
		remote.moveTo(250,350);
		document.frmCarga.target='1';
		document.frmCarga.submit();
		if(remote.opener == null) 
			remote.opener = window;
		remote.opener.name = "frameNuevosGrupos";				
		remote.focus();
	}
	
	function guardar(){
		document.frmCarga.tipo.value=12;
		document.frmCarga.gradoSeleccionado.value=grado;
		document.frmCarga.nombreGradoSeleccionado.value=nombreGrado;
		document.frmCarga.asignaturaSeleccionada.value=asignatura;
		document.frmCarga.nombreAsignaturaSeleccionada.value=nombreAsignatura;
		remote = window.open("","1","width=400,height=400,scrollbars=yes,resizable=yes,toolbar=no,directories=no,menubar=no,status=no");
		remote.moveTo(250,350);
		document.frmCarga.target='1';
		document.frmCarga.submit();
		if(remote.opener == null) 
			remote.opener = window;
		remote.opener.name = "frameNuevosGrupos";				
		remote.focus();
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
	    width: 200px;
	    overflow: auto;  
	    }
	#left {
		float:left;
		width: 20%;
		overflow-x:auto;
	}
	#right {
		float:left;
		width: 80%;
		overflow-x:auto;
	}
	.tablaAsignaturas, .tablaGrados>table th,td{
		height:30px;
	    }	
	#divCargaAcademica{
		
		}
	</style>
	<form method="post" name="frmCarga" action='<c:url value="/personal/ControllerNuevoEdit.do"/>' onsubmit="">
		<input TYPE="hidden" NAME="tipo" VALUE=''>
		<input TYPE="hidden" NAME="cmd" VALUE=''>
		
		<input type="hidden" name="rotdagdocente" value='<c:out value="${sessionScope.nuevoPersonal.pernumdocum}"/>'>
		<input type="hidden" name="rotdagVigencia" value='<c:out value="${sessionScope.nuevoPersonal2.perVigencia}"/>'>
        <input type="hidden" name="rotdagsede" value=''>
        <input type="hidden" name="rotdagjornada" value=''>
        <input type="hidden" name="rotdagmetodologia" value=''>
        <input type="hidden" name="rotdaghoras" value=''>
        
        <input type="hidden" name="ihTotal2" value='<c:out value="${requestScope.ihTotal2}"/>'>
		<input type="hidden" name="ihTotal3" value='0'>
		
		<input type="hidden" name="sedeSeleccionada" value='<c:out value="${sessionScope.sedeSeleccionada}"/>'>
		<input type="hidden" name="jornadaSeleccionada" value='<c:out value="${sessionScope.jornadaSeleccionada}"/>'>
		<input type="hidden" name="metodologiaSeleccionada" value='<c:out value="${sessionScope.metodologiaSeleccionada}"/>'>
		<input type="hidden" name="asignaturaSeleccionada" value=''>
		<input type="hidden" name="nombreAsignaturaSeleccionada" value=''>
		<input type="hidden" name="gradoSeleccionado" value=''>
		<input type="hidden" name="nombreGradoSeleccionado" value=''>
		
		
		<div id="divCargaAcademica">
		<div id="left">
			<table border="1" cellspacing="0" cellpadding="0" class="tablaAsignaturas" >
				<tr>
					<th class="EncabezadoColumna">Asignatura</th>
				</tr>
				<c:forEach begin="0" items="${sessionScope.filtroAsignatura}" var="filtroAsignatura">
					<tr>
						<td>
						<!-- este valor (<c:out value="${fila[2]}"/>) es igual a (<c:out value="${sessionScope.sedeSeleccionada}"/>) -->
							<c:out value="${filtroAsignatura[1]}"/>
						</td>
					</tr>
				</c:forEach>
			</table>
			&nbsp;
			&nbsp;
		</div>
			<div id="right">
				<table border="1" width="100%" cellpadding="0" cellspacing="0" class="tablaGrados">
					<tr>
						<c:forEach begin="0" items="${sessionScope.filtroGrados}" var="fila">
								<th class="EncabezadoColumna" style="white-space: nowrap;">
									<c:out value="${fila[1]}"/>
								</th>
						</c:forEach>
					</tr>
					<c:set var="gradoHabilitado" scope="page" value="false"/>
					<c:forEach begin="0" items="${sessionScope.filtroAsignatura}" var="filtroAsignatura">
						<tr>
							<c:forEach begin="0" items="${sessionScope.filtroGrados}" var="filtroGrados" varStatus="st0">
									<td style="white-space: nowrap;">
									<!-- <input type='hidden' name='rotdaggrados' value='<c:out value="${filtroGrados[0]}"/>'> -->
									<c:forEach begin="0" items="${requestScope.filtroGradoAsignatura}" var="filtroGradoAsignatura">
										<c:if test="${
													filtroGradoAsignatura[0] eq filtroGrados[0] and
													filtroGradoAsignatura[2] eq sessionScope.sedeSeleccionada and 
										 			filtroGradoAsignatura[3] eq sessionScope.jornadaSeleccionada and
													filtroGradoAsignatura[4] eq sessionScope.metodologiaSeleccionada and
													filtroGradoAsignatura[5] eq filtroAsignatura[0]}">
											<!-- <input type='checkbox' name='rotdaggrados_' value='<c:out value="${fila[0]}"/>' disabled><c:out value="${fila[1]}"/> -->
											
											<!--<c:forEach begin="0" items="${requestScope.cargaTotal}" var="filtroCargaTotal" varStatus="st0">
											<c:out value="${filtroGradoAsignatura[0]} =  ${filtroCargaTotal.cargaHTGrado}"/><br/>
											<c:out value="${filtroGradoAsignatura[2]} =  ${filtroCargaTotal.cargaHTSede}"/><br/>
											<c:out value="${filtroGradoAsignatura[3]} =  ${filtroCargaTotal.cargaHTJornada}"/><br/>
											<c:out value="${filtroGradoAsignatura[4]} =  ${filtroCargaTotal.cargaHTMetodologia}"/><br/>
											<c:out value="${filtroGradoAsignatura[5]} =  ${filtroCargaTotal.cargaHTAsignatura}"/><br/>
											</c:forEach> -->
											
											<input type='text' size='2' maxlength='2' class="txtHorasAsignatura" id="rotdagIHgrados_" 
											name='cargaGradoAsignatura_<c:out value="${filtroGrados[0]}"/>_<c:out value="${filtroAsignatura[0]}"/>' 
											onKeyPress='return acepteNumeros(event)' 
											value="<c:forEach begin="0" items="${requestScope.cargaTotal}" var="filtroCargaTotal" varStatus="st0"><c:if test="${filtroGradoAsignatura[0] eq filtroCargaTotal.cargaHTGrado and filtroGradoAsignatura[2] eq filtroCargaTotal.cargaHTSede and filtroGradoAsignatura[3] eq filtroCargaTotal.cargaHTJornada and filtroGradoAsignatura[4] eq filtroCargaTotal.cargaHTMetodologia and filtroGradoAsignatura[5] eq filtroCargaTotal.cargaHTAsignatura}"><c:out value="${filtroCargaTotal.cargaHTIntensidadHoraria}"/></c:if></c:forEach>" 
											onblur="validarMaximoCampo(this)"
											>
											<!--
											<c:forEach begin="0" items="${sessionScope.maximoHorasAsignaturas}" var="maximoAsignatura">
												<c:if test="${
														filtroGradoAsignatura[0] eq maximoAsignatura[0] and 
														filtroGradoAsignatura[5] eq maximoAsignatura[1]}">
													onblur="validarMaximoCampo(this,<c:out value="${maximoAsignatura[2]}"/>)"
												</c:if>
											</c:forEach>
											 
											 -->
											
											<a href='javaScript:agregar(<c:out value="${filtroGrados[0]}"/>,"<c:out value="${filtroGrados[1]}"/>",<c:out value="${filtroAsignatura[0]}"/>,"<c:out value="${filtroAsignatura[1]}"/>")'>
												<img border='0' src="../etc/img/editar.png" width='15' height='15'>
											</a>
											<c:set var="gradoHabilitado" value="true"/>
										</c:if>
									</c:forEach>
									<c:if test='${gradoHabilitado eq "false"}'>
										&nbsp;
										<c:set var="gradoHabilitado" value="true"/>
									</c:if>	
									</td>
							</c:forEach>
						</tr>
					</c:forEach>
				</table>
				&nbsp;
				&nbsp;
			</div>
		</div>
		
	</form>
			
			
<script>
	filtro(document.frmNuevo.rotdagsede, document.frmNuevo.rotdagjornada,document.frmNuevo.rotdaggrados_,document.frmNuevo.rotdagmetodologia,document.frmNuevo.rotdagasignatura);
	filtroAsignatura(document.frmNuevo.rotdagmetodologia,document.frmNuevo.rotdagasignatura,document.frmNuevo.rotdaggrados_);
		<c:if test="${sessionScope.carga.estado==1}">
			filtro(document.frmNuevo.rotdagsede, document.frmNuevo.rotdagjornada,document.frmNuevo.rotdaggrados_,document.frmNuevo.rotdagmetodologia,document.frmNuevo.rotdagasignatura);
			filtroGrados(document.frmNuevo.rotdagsede, document.frmNuevo.rotdagjornada,document.frmNuevo.rotdagmetodologia,document.frmNuevo.rotdaggrados_,document.frmNuevo.rotdagasignatura,document.frmNuevo.rotdagIHgrados_);
		</c:if>
	</script>
