<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="paramsVO"   class="siges.gestionAdministrativa.RepPuestoEstudiante.vo.ParamsVO" scope="page"/>
<html>
<c:import url="/parametros.jsp"/>
<head>
<title>Sistema de Apoyo Escolar - Selección Áreas/Asignaturas.</title>
<script languaje="javaScript">
var codigosAsig="-99";
var abrevAsig="";
var numChecks=0;
var conAreAsig=<c:out value="${requestScope.ajaxConAreAsig}"/>;

<c:if test="${requestScope.ajaxTipoRep!=1}">
	numAsig=1;	
</c:if>
	function copiarChecks(forma){
		codigosAsig="";
		abrevAsig="";
		numChecks=0;
        if(forma.nuevoAsig_){
            if(forma.nuevoAsig_.length){
                for(var i=0;i<forma.nuevoAsig_.length ;i++){
                    if(forma.nuevoAsig_[i].checked==true){                       
                    	codigosAsig=codigosAsig+forma.nuevoAsig[i].value+",";
                    	abrevAsig=abrevAsig+forma.nuevoAsigAbrev[i].value+", ";
                    	numChecks=numChecks+1;                       
                    }
                }
            }else{
                if(forma.nuevoAsig_.checked==true){
                	codigosAsig=forma.nuevoAsig[i].value;
                	abrevAsig=abrevAsig+forma.nuevoAsigAbrev[i].value;
                }
            }
        }
  }
    
	function enviar(){
		copiarChecks(document.frmAsig);		
		opener.document.frmNuevo.asigCodigos.value=codigosAsig; 		
		opener.document.frmNuevo.asigNombres.value=abrevAsig;
		document.frmAsig.target="centro";
		parent.close();		
	}
	
	function cancelar(){
		//alert(<c:out value="${sessionScope.filtroReportesVO.numAsig}"/>);
		parent.close();
	}

	//alert();
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
		<c:if test="${requestScope.ajaxConAreAsig==1}">
			<caption>Seleccionar Áreas</caption>
		</c:if>
		<c:if test="${requestScope.ajaxConAreAsig==2}">
			<caption>Seleccionar Asignaturas</caption>
		</c:if>
		 	
		 	<c:if test="${empty requestScope.listaAreasAsignaturas}">
		 	<c:if test="${requestScope.ajaxConAreAsig==1}">					
				<tr><th class="Fila1" colspan='6'>NO EXISTEN REGISTROS DE ÁREAS</th></tr>
			</c:if>
			<c:if test="${requestScope.ajaxConAreAsig==2}">					
				<tr><th class="Fila1" colspan='6'>NO EXISTEN REGISTROS DE ASIGNATURAS</th></tr>
			</c:if>
				
			</c:if>
			<c:if test="${!empty requestScope.listaAreasAsignaturas}">
				<tr>
					<th width='30' class="EncabezadoColumna">&nbsp;</th>
					<th class="EncabezadoColumna">Abrev.</td>
					<c:if test="${requestScope.ajaxConAreAsig==1}">					
						<th class="EncabezadoColumna">Área</td>
					</c:if>
					<c:if test="${requestScope.ajaxConAreAsig==2}">					
						<th class="EncabezadoColumna">Asignatura</td>
					</c:if>
				</tr>
				<c:forEach begin="0" items="${requestScope.listaAreasAsignaturas}" var="lista" varStatus="st">
					<tr>
						<th class='Fila<c:out value="${st.count%2}"/>'>	
							<input type="checkbox" name="nuevoAsig_" <c:out value="${lista.padre2}"/>/><br>
							
							<input type="hidden" name="nuevoAsig" value='<c:out value="${lista.codigo}"/>'/>							
							<input type="hidden" name="nuevoAsigAbrev" value='<c:out value="${lista.codigo2}"/>'/>
							<input type="hidden" name="nuevoAsigNombre" value='<c:out value="${lista.nombre}"/>'/>
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