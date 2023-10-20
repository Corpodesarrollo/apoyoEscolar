<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="paramsVO" class="articulacion.artAusencias.vo.ParamsVO" scope="page"/>
<html>
<head>
<script languaje="javaScript">
	function guardar(){
		if(document.frmMotivo.asignaturas_){
			if(document.frmMotivo.asignaturas_.length){
				for(var i=0 ;i<document.frmMotivo.asignaturas_.length;i++){
					if(document.frmMotivo.asignaturas_[i].checked!=true){
						document.frmMotivo.asignaturas[i].disabled=true;
						document.frmMotivo.claseAsig[i].disabled=true;
					}
				}
			}else{
				if(document.frmMotivo.asignaturas_.checked!=true){
					document.frmMotivo.asignaturas.disabled=true;
					document.frmMotivo.claseAsig.disabled=true;
				}
			}
		}
		
		if(validarForma(document.frmMotivo)){
			document.frmMotivo.cmd.value=document.frmMotivo.GUARDAR.value;
			document.frmMotivo.action='<c:url value="/articulacion/artAusencias/Save1.jsp"/>';
			document.frmMotivo.submit();
			parent.close();
		}
	}
	function hacerValidaciones_frmMotivo(forma){
		validarLista(forma.motivo, "- Motivo de Ausencia", 1);
	}
	function llenar(){
		if(document.frmMotivo.asignaturas){
			if(document.frmMotivo.asignaturas.length){
				for(var a=0;a<document.frmMotivo.asignaturas.length;a++){
					if(document.ausentes.asignAu){
						if(document.ausentes.asignAu.length){
							for(var b=0;b<document.ausentes.asignAu.length;b++){
								if(document.frmMotivo.asignaturas[a].value==document.ausentes.asignAu[b].value){
									if(document.frmMotivo.claseAsig[a].value==document.ausentes.claseAu[b].value){
										document.frmMotivo.asignaturas_[a].checked=true;
									}
								}
							}
						}else{
							if(document.frmMotivo.asignaturas[a].value==document.ausentes.asignAu.value){
								if(document.frmMotivo.claseAsig[a].value==document.ausentes.claseAu.value){
									document.frmMotivo.asignaturas_[a].checked=true;
								}
							}
						}
					}
				}
			}else{
				if(document.ausentes.asignAu){
					if(document.frmMotivo.asignaturas.value==document.ausentes.asignAu.value){
						if(document.frmMotivo.claseAsig.value==document.ausentes.claseAu.value){
							document.frmMotivo.asignaturas_.checked=true;
						}
					}
				}
			}
		}
	}
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	
 	<form method="post" name="frmMotivo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/articulacion/escValorativa/Save.jsp"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDA_MOTIVO}"/>'>
		
		<input type="hidden" name="estudiante" value='<%=request.getParameter("cod") %>'>
		<input type="hidden" name="dia" value='<%=request.getParameter("dia") %>'>
		<input type="hidden" name="numDia" value='<%=request.getParameter("numDia") %>'>
		<table border="0" align="center" bordercolor="#FFFFFF" width="100%">
			<caption>Registro Ausencia</caption>
				<tr>
					<td width="45%">
						<c:if test="${sessionScope.NivelPermiso==2}">
	    					<c:if test="${sessionScope.grupoVO.formaEstado!=1}">
	    						<input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton">
	    					</c:if>
	    				</c:if>
			  		</td>
			 	</tr>	
		  </table>
	 	 <table width="100%" border="0" cellspacing="2" cellpadding="0">
			<tr>
				<td>
					<span class="style2">*</span><b>Motivo Ausencia</b>
				</td>
				<td>
					<select name="motivo">
						<option value="0" >--Seleccione Uno--</option>
						<c:forEach begin="0" items="${requestScope.listaMotivosVO}" var="motivo">
							<option value="<c:out value="${motivo.codigo}"/>" <c:if test="${motivo.codigo==requestScope.ausencia.motivo}">selected</c:if>><c:out value="${motivo.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>
					<span class="style2">*</span><b>Descripción</b>
				</td>
				<td>
					<textarea name="descripcion"><c:out value="${requestScope.ausencia.descripcion}"/></textarea>
				</td>
			</tr>
			<tr>
				<td>
					<span class="style2">*</span><b>Asignaturas Inscritas</b>
				</td>
			</tr>
			<c:forEach begin="0" items="${requestScope.listaAsignaEstVO}" var="asignatura">
			<tr>
				<td colspan="2">
					<input type="hidden" name="asignaturas" value="<c:out value="${asignatura.codigo}"/>">
					<input type="hidden" name="claseAsig" value="<c:out value="${asignatura.clase}"/>">
					<input type="checkbox" name="asignaturas_">
					<c:out value="${asignatura.nombre}"/>
				</td>
			</tr>
			</c:forEach>
		</table>
	</form>
	
	<c:set var="lista" scope="page" value="${requestScope.ausencia}"/>
	<form name="ausentes">
		<c:forEach begin="0" items="${lista.asignaturas}" var="asig">
		   <input type="hidden" name="asignAu" value='<c:out value="${asig}"/>'>
		</c:forEach>
		<c:forEach begin="0" items="${lista.claseAsig}" var="clase">
		   <input type="hidden" name="claseAu" value='<c:out value="${clase}"/>'>
		</c:forEach>
	</form>
</body>
<script>
	llenar();
</script>
</html>