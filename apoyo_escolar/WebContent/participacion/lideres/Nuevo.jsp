<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/parametros.jsp"/>
<jsp:useBean id="lideresVO" class="participacion.lideres.vo.LideresVO" scope="session"/>
<jsp:useBean id="paramsVO" class="participacion.lideres.vo.ParamsVO" scope="page"/>
<html>
<head>
<script type="text/javascript" src="<c:url value="/etc/js/validar.js"/>"></script>
<script languaje="javaScript">
	var nav4=window.Event ? true : false;
	
	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}
	
	function lanzar(tipo){
		if(validarForma(document.frmNuevo)){
		  	document.frmNuevo.tipo.value=tipo;
			document.frmNuevo.target="";
			document.frmNuevo.submit();
		}			
	}
	
	function nuevo(){
		document.frmNuevo.action='<c:url value="/lideres/Nuevo.jsp"/>';
		document.frmNuevo.submit();
	}

	function guardar(){
		if(validarForma(document.frmNuevo)){
			alert("La información fue guardada satisfactoriamente");
			document.frmNuevo.submit();
		}
	}

	function hacerValidaciones_frmNuevo(forma){
		validarLista(forma.instancia, "- Instancia");
		validarLista(forma.vigencia, "- vigencia");
		validarLista(forma.rol, "- Rol");
		validarLista(forma.tipoDocumento, "- Tipo Documento");
		validarCampo(forma.numeroDocumento, "- Número de Documento",1,20);
		validarCampo(forma.primerApellido, "- Primer Apellido",1,60);
		validarCampo(forma.primerNombre, "- Primer Nombre",1,60);
		validarCampo(forma.telefonos, "- Teléfonos",1,60);
		validarLista(forma.etnia, "- Etnia");
		validarLista(forma.suplenteRol, "- Suplente del Rol");
		validarLista(forma.localidadResidencia, "- Localidad Residencia");
	}
</script>
<style type="text/css">
<!--
.Estilo1 {font-weight: bold}
-->
</style>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">

	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	               <tr><td width="100%" valign='top'>
	                       <div style="width:100%;height:250px;overflow:auto;vertical-align:top;">
	                               <c:import url="/lideres/Filtro.jsp"/>
	                       </div>
	               </td></tr>
	</table>		

<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/lideres/Nuevo.jsp"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="cmd1" value=''>
		<INPUT TYPE="hidden" NAME="tipo" VALUE='1'>
		<input type="hidden" name="id" value=""><input type="hidden" name="id2" value=""><input type="hidden" name="id3" value="">
		<input type="hidden" name="guia" value="<c:out value="${requestScope.guia}"/>">
		

		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1"><td width="10">&nbsp;</td>
				<td rowspan="2" width="469">
				<img src='<c:url value="/etc/img/tabs/lideres_0.gif"/>'  alt="Lideres" height="26" border="0">
				</td>
			</tr>
		</table>



		<table border="0" align="center" bordercolor="#FFFFFF" width="100%">
			<caption>Ingreso / Edición de Registro</caption>
				<tr>
					<td width="45%">
							 <input name="cmd1" type="button" value="Nuevo" onClick="nuevo()" class="boton">  
    						<input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton">
			  		</td>
			 	</tr>	
	  </table>
		  <table width="592" border="0">
		  
			<tr>
				<td><span class="style2">*</span>Instancia:</td>
				<td colspan="2">
					<select name="instancia" style="width:250px" >
						<option value="-99" selected>-Seleccione uno-</option>
						<option value="1" >El Consejo Directivo</option>
						<option value="2" >Comite de convivencia</option>
						<option value="3" >Comité consultivo para el relacionamiento de la educación media con el sector empresarial</option>
						<option value="4" >Cabildante menor</option>
					</select>	
				</td>
			</tr>

			<tr>
				<td ><span class="style2">*</span>Vigencia:</td>
				<td>
					<select name="vigencia" >
						<option value="-99" selected>--</option>
						<option value="1">2009 - 2010</option>
						<option value="2" >2010 - 2011</option>
					</select>
				</td>
				
				<td ><span class="style2">*</span>Rol:</td>
				<td>
					<select name="rol" style="width:200px" >
						<option value="-99" selected>-Seleccione uno-</option>
						<option value="1" >Alcalde Local</option>
						<option value="2" >Edíl</option>
						<option value="3" >Rector</option>
						<option value="4" >Docente</option>
						<option value="5" >Docente Coordinador</option>
					</select>	
				</td>
			</tr>
			<tr>
				<td ><span class="style2">*</span>Tipo Documento:</td>
				<td>
					<select name="tipoDocumento">
						<option value="-99" selected>--</option>
						<option value="1" >NIP</option>
						<option value="2" >SEC</option>
						<option value="3" >NUIP</option>
						<option value="4" >CC</option>
						<option value="5" >CE</option>
					</select>	
				</td>

				<td ><span class="style2">*</span>Número Documento:</td>
				<td><input type="text" name="numeroDocumento" maxlength="12" size="14" onKeyPress='return acepteNumeros(event)' ></input></td>
			</tr>
			
			<tr>
				<td ><span class="style2">*</span>Primer Apellido:</td>
				<td><input type="text" name="primerApellido"></input></td>

				<td>Segundo Apellido:</td>
				<td><input type="text" name="segundoApellido"></input></td>
			</tr>
			
			<tr>
				<td ><span class="style2">*</span>Primer Nombre:</td>
				<td><input type="text" name="primerNombre"></input></td>

				<td>Segundo Nombre:</td>
				<td><input type="text" name="segundoNombre"></input></td>
			</tr>


			<tr>
				<td>Correo Electrónico:</td>
				<td><input type="text" name="correoElectronico"></input></td>

				<td ><span class="style2">*</span>Teléfonos:</td>
				<td><input type="text" name="telefonos"></input></td>
			</tr>

			<tr>
				<td>Número Celular:</td>
				<td><input type="text" name="numeroCelular" onKeyPress='return acepteNumeros(event)'></input></td>

				<td >Edad:</td>
				<td><input type="text" name="edad" size="2"  onKeyPress='return acepteNumeros(event)'>&nbsp;&nbsp;Años</input></td>
			</tr>

			<tr>
				<td ><span class="style2">*</span>Etnia:</td>
				<td>
					<select name="etnia">
						<option value="-99" selected>-Seleccione uno-</option>
						<option value="1" >ROM</option>
						<option value="2" >Raizal</option>
						<option value="3" >Afrodescendiente</option>
						<option value="4" >Indigena</option>
					</select>	
				</td>

				<td ><span class="style2">*</span>Suplente del Rol:</td>
				<td>
					<select name="suplenteRol">
						<option value="-99" selected>--</option>
						<option value="1" >Si</option>
						<option value="2" >No</option>
					</select>	
				</td>
			</tr>
			
			
			<tr>
				<td>Localidad:</td>
				<td>
					<select name="localidad" disabled="disabled">
						<option value="1" selected>KENNEDY</option>
					</select>	
				</td>

				<td>Tipo Colegio:</td>
				<td>
					<select name="tipoColegio" disabled="disabled">
						<option value="1" selected>PRIVADO</option>
					</select>	
				</td>
			</tr>

			<tr>
				<td>Colegio:</td>
				<td>
					<select name="colegio" disabled="disabled" style="width:250px">
						<option value="1" selected>COLEGIO DE FORMACIÓN INTEGRAL SAN RAFAEL</option>
					</select>	
				</td>

				<td>Sede:</td>
				<td>
					<select name="sede" disabled="disabled" style="width:200px">
						<option value="1" selected>1_SAN RAFAEL</option>
					</select>	
				</td>
			</tr>

			<tr>
				<td>Jornada:</td>
				<td>
					<select name="jornada" disabled="disabled">
						<option value="1" selected>MAÑANA</option>
					</select>	
				</td>

				<td>Grado:</td>
				<td>
					<select name="grado" disabled="disabled">
						<option value="1" selected>Primero</option>
						<option value="2" selected>Segundo</option>
						<option value="3" selected>Tercero</option>
						<option value="4" selected>Cuarto</option>
						<option value="5" selected>Quinto</option>
					</select>	
				</td>
			</tr>


			<tr>
				<td><span class="style2">*</span>Localidad de Residencia:</td>
				<td>
					<select name="localidadResidencia">
					<option value="-99" selected>-Seleccione uno-</option>
						<option value="1">(1)USAQUÉN</option>
						<option value="2" >(2)CHAPINERO</option>
						<option value="3" >(3)SANTAFÉ</option>
						<option value="4" >(3)SAN CRISTÓBAL</option>
						<option value="5" >(5)USME</option>
						<option value="6" >(6)TUNJUELITO</option>
						<option value="7" >(7)BOSA</option>
						<option value="8" >(8)KENNEDY</option>
						<option value="9" >(9)FONTIBÓN</option>
						<option value="10">(10)ENGATIVÁ</option>
						<option value="11" >(11)SUBA</option>
						<option value="12" >(12)BARRIOS UNIDOS</option>
						<option value="13" >(13)TEUSAQUILLO</option>
						<option value="14" >(14)LOS MÁRTIRES</option>
						<option value="15" >(15)ANTONIO NARIÑO</option>
						<option value="16" >(16)PUENTE ARANDA</option>
						<option value="17" >(17)LA CANDELARIA</option>
						<option value="18" >(18)RAFAEL URIBE</option>
						<option value="19" >(19)CIUDAD BOLÍVAR</option>
						<option value="20" >(20)SUMAPAZ</option>
					</select>	
				</td>

				<td>Discapacidad:</td>
				<td>
					<select name="discapacidad">
						<option value="-99" selected>-Seleccione uno-</option>
						<option value="1" >Visual</option>
						<option value="2" >Auditiva</option>
						<option value="3" >Talla baja</option>
						<option value="5" >Cognositiva</option>
						<option value="6" >Motora</option>
						<option value="7" >Autísmo</option>
						<option value="8" >Multidéficit</option>
					</select>	
				</td>
			</tr>

			<tr>
				<td>Desplazado:</td>
				<td>
					<select name="desplazado">
						<option value="-99" selected>--</option>
						<option value="1" >Si</option>
						<option value="2" >No</option>
					</select>	
				</td>
				
				<td>Amenazado:</td>
				<td>
					<select name="amenazado">
						<option value="-99" selected>--</option>
						<option value="1" >Si</option>
						<option value="2" >No</option>
					</select>	
				</td>
				
			</tr>

			<tr>
				<td>LGTB:</td>
				<td>
					<select name="lgtb">
						<option value="-99" selected>-Seleccione uno-</option>
						<option value="1" >Lesbiana</option>
						<option value="2" >Gay</option>
						<option value="3" >Transgenerista</option>
						<option value="5" >Bisexual</option>
					</select>	
				</td>
		
			</tr>
			
          </table>
      <p>&nbsp;</p>
</form>
</body>
</html>