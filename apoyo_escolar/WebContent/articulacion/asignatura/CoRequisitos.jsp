<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="paramsVO" class="articulacion.asignatura.vo.ParamsVO" scope="page"/>
<html>
<head>
<c:import url="/parametros.jsp"/>
<script languaje="javaScript">
	function copiarChecks(forma){
        if(forma.nuevoRequisito_){
            if(forma.nuevoRequisito_.length){
                for(var i=0;i<forma.nuevoRequisito_.length ;i++){
                    if(forma.nuevoRequisito_[i].checked!=true){
                        forma.nuevoRequisito[i].value=0;
                    }
                }
            }else{
                if(forma.nuevoRequisito_.checked!=true){
                    forma.nuevoRequisito.value=0;
                }
            }
        }
    }
    
	function enviar(){
		copiarChecks(document.frmCoreq);
		document.frmCoreq.cmd.value=document.frmCoreq.GUARDAR.value;
		document.frmCoreq.target="correquisito";
		document.frmCoreq.submit();
		//opener.refrescar();
		parent.close();
	}
	
	function cancelar(){
		parent.close();
	}
	
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form method="post" name="frmCoreq" action='<c:url value="/articulacion/asignatura/Save2.jsp"/>'>
		<input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_DEFAULT}"/>'>
		<input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="asigCodigo" value='<c:out value="${sessionScope.asignaturaVO.artAsigCodigo}"/>'/>
		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0">
		 	<caption>LISTADO DE ASIGNATURAS COREQUISITO</caption>
		 	<c:if test="${empty requestScope.CoRequisitos}">
				<tr><th class="Fila1" colspan='6'>NO EXISTEN REGISTROS DE ASIGNATURAS</th></tr>
			</c:if>
			<c:if test="${!empty requestScope.CoRequisitos}">
				<tr>
					<th width='30' class="EncabezadoColumna">&nbsp;</th>
					<th class="EncabezadoColumna">Código</th>
					<th class="EncabezadoColumna">Asignatura</th>
				</tr>
				<c:forEach begin="0" items="${requestScope.CoRequisitos}" var="lista" varStatus="st">
					<tr>
						<th class='Fila<c:out value="${st.count%2}"/>'>
							<input type="checkbox" name="nuevoRequisito_" <c:out value="${lista.asigChecked_}"/>/><br>
							<input type="hidden" name="nuevoRequisito" value='<c:out value="${lista.asigCodigo}"/>'/>
						</th>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.asigReCodigo}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.asigNombre}"/></td>
					</tr>
				</c:forEach>
				<tr><td colspan="3" align="center"><input name="cmd1" type="button" value="Adicionar" onClick="enviar()" class="boton"><input name="cmd2" type="button" value="Cancelar" onClick="cancelar()" class="boton"></td></tr>
			</c:if>
		</table>
	</form>
</body>
</html>