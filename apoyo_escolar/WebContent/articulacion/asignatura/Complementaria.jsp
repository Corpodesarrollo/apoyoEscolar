<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="paramsVO" class="articulacion.asignatura.vo.ParamsVO" scope="page"/>
<html>
<c:import url="/parametros.jsp"/>
<head>
<script languaje="javaScript">
	function copiarChecks(forma){
        if(forma.nuevoComplemento_){
            if(forma.nuevoComplemento_.length){
                for(var i=0;i<forma.nuevoComplemento_.length ;i++){
                    if(forma.nuevoComplemento_[i].checked==true){
                        forma.nuevoComplemento[i].value=forma.nuevo[i].value;
                    }
                }
            }else{
                if(forma.nuevoComplemento_.checked==true){
                    forma.nuevoComplemento.value=forma.nuevo.value;
                }
            }
        }
    }
	function enviar(){
		copiarChecks(document.frmCom);
		document.frmCom.cmd.value=document.frmCom.GUARDAR.value;
		document.frmCom.target="complementaria";
		document.frmCom.submit();
		parent.close();
	}
	
	function cancelar(){
		parent.close();
	}
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form method="post" name="frmCom" action='<c:url value="/articulacion/asignatura/Save4.jsp"/>'>
		<input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_DEFAULT}"/>'>
		<input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="asigCodigo" value='<c:out value="${sessionScope.asignaturaVO.artAsigCodigo}"/>'/>
		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0">
		 	<caption>LISTADO DE ASIGNATURAS COMPLEMENTARIAS</caption>
		 	<c:if test="${empty requestScope.ComplementariaVO}"><tr><th class="Fila1" colspan='6'>NO EXISTEN REGISTROS DE ASIGNATURAS</th></tr></c:if>
			<c:if test="${!empty requestScope.ComplementariaVO}">
				<tr>
					<th width='30' class="EncabezadoColumna">&nbsp;</th>
					<td class="EncabezadoColumna">Código</td>
					<td class="EncabezadoColumna">Asignatura</td>
				</tr>
				<c:forEach begin="0" items="${requestScope.ComplementariaVO}" var="lista" varStatus="st">
						<tr>
							<th class='Fila<c:out value="${st.count%2}"/>'>
								<input type="checkbox" name="nuevoComplemento_" <c:out value="${lista.asigChecked_}"/>/><br>
								<input type="hidden" name="nuevoComplemento" value='0'/>
								<input type="hidden" name="nuevo" value='<c:out value="${lista.asigCodigo}"/>'/>
							</th>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.asigComCodigo}"/></td>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.asigNombre}"/></td>
						</tr>
				</c:forEach>
			<tr>
				<td colspan="3" align="center">
					<input name="cmd1" type="button" value="Adicionar" onClick="enviar()" class="boton">
					<input name="cmd1" type="button" value="Cancelar" onClick="cancelar()" class="boton">
				</td>
			</tr>
			</c:if>
		</table>
	</form>
</body>
</html>