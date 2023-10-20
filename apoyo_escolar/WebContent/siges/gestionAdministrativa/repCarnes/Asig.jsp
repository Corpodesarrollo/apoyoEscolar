<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean  id="paramsVO"   class="siges.gestionAdministrativa.comparativos.vo.ParamsVO" scope="page"/>
<html>
<c:import url="/parametros.jsp"/>
<head>
<script languaje="javaScript">
var codigosAsig="-99";
var abrevAsig="";
var numChecks=0;
var numAsig=<c:out value="${sessionScope.filtroReportesVO.numAsig}"/>;
	function copiarChecks(forma){
		codigosAsig="-99";
		abrevAsig="";
		numChecks=0;
        if(forma.nuevoAsig_){
            if(forma.nuevoAsig_.length){
                for(var i=0;i<forma.nuevoAsig_.length ;i++){
                    if(forma.nuevoAsig_[i].checked==true){
                    	codigosAsig=codigosAsig+","+forma.nuevoAsig[i].value;
                    	abrevAsig=abrevAsig+forma.nuevoAsigAbrev[i].value+", ";
                    	numChecks=numChecks+1;
                    }
                }
            }else{
                if(forma.nuevoAsig_.checked==true){
                	codigosAsig=codigosAsig+","+forma.nuevoAsig[i].value;
                	abrevAsig=abrevAsig+forma.nuevoAsigAbrev[i].value;
                }
            }
        }
  }
    
	function enviar(){
		copiarChecks(document.frmAsig);
		if(numChecks<=numAsig){
			opener.document.frmNuevo.asigCodigos.value=codigosAsig; 		
			opener.document.frmNuevo.asigNombres.value=abrevAsig;
			document.frmAsig.target="centro";
			parent.close();
		}else{
			alert("Ha seleccionado un número mayor de asignaturas a las permitidas, por favor verifique esta información");
		}
	}
	
	function cancelar(){
		parent.close();
	}
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form method="post" name="frmAsig"'>
		<input TYPE="hidden" NAME="tipo" VALUE='1'>
		<input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="asigCodigo" value=''/>
		<div style="width:100%;height:350px;overflow:auto;vertical-align:top;background-color:#ffffff;">
		
		<table border="0" align="center" width="95%" cellpadding="0" cellspacing="0">
		 	<caption>Agregar Asignaturas </caption>
		 	<c:if test="${empty requestScope.listaAsignaturas}">
				<tr><th class="Fila1" colspan='6'>NO EXISTEN REGISTROS DE ASIGNATURAS</th></tr>
			</c:if>
			<c:if test="${!empty requestScope.listaAsignaturas}">
				<tr>
					<th width='30' class="EncabezadoColumna">&nbsp;</th>
					<th class="EncabezadoColumna">Abrev.</td>
					<th class="EncabezadoColumna">Asignatura</td>
				</tr>
				<c:forEach begin="0" items="${requestScope.listaAsignaturas}" var="lista" varStatus="st">
					<tr>
						<th class='Fila<c:out value="${st.count%2}"/>'>
							<input type="checkbox" name="nuevoAsig_" <c:out value="${lista.padre2}"/>/><br>
							<input type="hidden" name="nuevoAsig" value='<c:out value="${lista.codigo}"/>'/>							
							<input type="hidden" name="nuevoAsigAbrev" value='<c:out value="${lista.codigo2}"/>'/>
						</th>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.codigo2}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.nombre}"/></td>
					</tr>
				</c:forEach>				
			</c:if>
		</table>
		</div>
		<table align="center">
			<tr>
				<td colspan="3" align="center">
					<input name="cmd1" type="button" value="Adicionar" onClick="enviar()" class="boton">
					<input name="cmd2" type="button" value="Cancelar" onClick="cancelar()" class="boton">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>