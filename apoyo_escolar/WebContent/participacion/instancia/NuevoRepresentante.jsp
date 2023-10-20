<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="representanteVO" class="participacion.instancia.vo.RepresentanteVO" scope="session"/>
<jsp:useBean id="paramsVO" class="participacion.instancia.vo.ParamsVO" scope="page"/>
<html>
<head>
<c:import url="/parametros.jsp"/>
<script languaje="javaScript">
	var nav4=window.Event ? true : false;

	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}
		
	function guardarRepresentante(){
		if(validarForma(document.frmNuevoRepresentante)){
			validarDatos(document.frmNuevoRepresentante);
			document.frmNuevoRepresentante.target='centro';
			document.frmNuevoRepresentante.cmd.value=document.frmNuevoRepresentante.GUARDAR.value;
			document.frmNuevoRepresentante.submit();
			parent.close();
		}
	}

	function cancelarRepresentante(){
		parent.close();
	}
	
	function hacerValidaciones_frmNuevoRepresentante(forma){
		if(forma.repRoles_){
			if(forma.repRoles_.length){
				for(var i=0;i<forma.repRoles_.length;i++){
					if(forma.repRoles_[i].checked==true){
						validarEntero(forma.repCantidad[i], "- Cantidad", 1, 99999)			
						validarCampo(forma.repElige[i], "- Elige", 1, 200)			
					}else{
						forma.repCantidad[i].value=0;
						forma.repElige[i].value='';
					}
				}
			}else{
					if(forma.repRoles_.checked==true){
						validarEntero(forma.repCantidad, "- Cantidad", 1, 99999)			
						validarCampo(forma.repElige, "- Elige", 1, 200)			
					}else{
						forma.repCantidad.value=0;
						forma.repElige.value='';
					}
			}
		}
	}
	
	function validarDatos(forma){
		if(forma.repRoles_){
			if(forma.repRoles_.length){
				for(var i=0;i<forma.repRoles_.length;i++){
					if(forma.repRoles_[i].checked==true){
						forma.repRoles[i].value=forma.repRoles[i].value+':1:'+forma.repCantidad[i].value+':'+forma.repElige[i].value;
					}else{
						forma.repRoles[i].value=forma.repRoles[i].value+':0:'+forma.repCantidad[i].value+':'+forma.repElige[i].value;
					}
					//alert(forma.repRoles[i].value);
				}
			}else{
					if(forma.repRoles_.checked==true){
						forma.repRoles.value=forma.repRoles.value+':1:'+forma.repCantidad.value+':'+forma.repElige.value;
					}else{
						forma.repRoles.value=forma.repRoles.value+':0:'+forma.repCantidad.value+':'+forma.repElige.value;
					}
			}
		}
	}
	
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form method="post" name="frmNuevoRepresentante" action='<c:url value="/participacion/instancia/SaveInstancia.jsp"/>'>
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_REPRESENTANTE}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="CANCELAR" value='<c:out value="${paramsVO.CMD_CANCELAR}"/>'>
		<input type="hidden" name="repInstancia" value='<c:out value="${sessionScope.instanciaVO.instCodigo}"/>'>
	  <table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
		  <caption>Ingreso / Edición de participante</caption>
				<tr>
					<td width="45%"><c:if test="${sessionScope.NivelPermiso==2}"><input name="cmd1" type="button" value="Guardar" onClick="guardarRepresentante()" class="boton">&nbsp;<input name="cmd1" type="button" value="Cancelar" onClick="cancelarRepresentante()" class="boton"></c:if></td>
			 	</tr>	
		</table>	 	
	 	<c:if test="${empty requestScope.representanteVO}"><tr><th class="Fila1" colspan='6'>No hay participantes</th></tr></c:if>
		<c:if test="${!empty requestScope.representanteVO}">
		<table border="1" align="center" width="95%" cellpadding="1" cellspacing="0">
			<tr>
				<th class="EncabezadoColumna">Cargo / Rol</th>
				<th class="EncabezadoColumna">Selección</th>
				<th class="EncabezadoColumna">Cantidad</th>
				<th class="EncabezadoColumna">Elige</th>
		 	</tr>
				<c:forEach begin="0" items="${requestScope.representanteVO}" var="lista" varStatus="st">
					<tr>
						<th class='Fila<c:out value="${st.count%2}"/>'><c:out value="${lista.repNombre}"/></th>
						<th class='Fila<c:out value="${st.count%2}"/>'><input type="hidden" name="repRoles" value='<c:out value="${lista.repCodigo}"/>'>
						<input type="checkbox" name="repRoles_" value='<c:out value="${lista.repCodigo}"/>' <c:out value="${lista.repChecked}"/>>
						</th>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center" valign="middle">
						<input type="text" size="5" maxlength="5" name="repCantidad" value='<c:out value="${lista.repCantidad}"/>'>
						</td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center" valign="middle">
						<input type="text" size="40" maxlength="200" name="repElige" value='<c:out value="${lista.repElige}"/>'>
						</td>
					</tr>
				</c:forEach>
		</table>
		</c:if>
	</form>
</body>
</html>