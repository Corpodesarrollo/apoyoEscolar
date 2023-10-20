<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean  id="paramsVO"   class="siges.gestionAdministrativa.comparativos.vo.ParamsVO" scope="page"/>
<jsp:useBean  id="filtroReportesVO" class="siges.gestionAdministrativa.comparativos.vo.FiltroReportesVO" scope="session"/>
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
		
		
		
		<table border="0" align="center" width="100%" cellpadding="1" cellspacing="1">	
		<c:if test="${sessionScope.filtroReportesVO.tipoEscala==1}">
			<caption>Escala Numerica</caption>
		</c:if>
		<c:if test="${sessionScope.filtroReportesVO.tipoEscala==2}">
			<caption>Escala Conceptual</caption>
		</c:if>
		<c:if test="${sessionScope.filtroReportesVO.tipoEscala==3}">
			<caption>Escala MEN</caption>
		</c:if>
			<tr><td colspan="1" >Valor Inicial</td> 
			    <td colspan="1">
		 	      <input type="text" name="valorIni" id="valorIni"  size="4" value='<c:out value="${requestScope.valorIni}"/>' ></textarea>	 	    	
		 	    </td>
		 	    <td colspan="1" >Valor Final</td>
		 	    <td colspan="1">
		 	    <input type="text" name="valorFin" id="valorFin"  size="4" value='<c:out value="${requestScope.valorFin}"/>' ></textarea>	 	    	
		 	    </td>	
		 	    <c:if test="${sessionScope.filtroReportesVO.tipoEscala==2}">		 	 
		 	    <td colspan="1">Escala</td>
		 	    <td colspan="1">
		 	    <select name="escala" id="escala"  style="width: 150px;">
		 	        <option value="-9">-- Todos--</option>
		 	         <c:forEach begin="0" items="${requestScope.tipoEvalComp.escala}" var="item">
				     <option value="<c:out value="${item.codigo}"/>" <c:if test="${requestScope.escala  ==item.codigo}">SELECTED</c:if>><c:out value="${item.nombre}"/></option>
                     </c:forEach>
		 	        </select>
		 	    	 
		 	    </td>
		 	    </c:if>
		 	    
		 	     <c:if test="${sessionScope.filtroReportesVO.tipoEscala==3}">		 	 
		 	    <td colspan="1">Escala</td>
		 	    <td colspan="1">
		 	    <select name="escala" id="escala"  style="width: 150px;">
		 	        <option value="-9">-- Todos--</option>
		 	         <c:forEach begin="0" items="${requestScope.tipoEvalComp.escala}" var="item">
				     <option value="<c:out value="${item.codigo}"/>" <c:if test="${requestScope.escala  ==item.codigo}">SELECTED</c:if>><c:out value="${item.nombre}"/></option>
                     </c:forEach>
		 	        </select>
		 	    	 
		 	    </td>
		 	    </c:if>
		 	   
		 	 </tr>  
		</table>
		
		<br></br>
		<table align="center">
			<tr>
				<td colspan="3" align="center">
					<input name="cmd1" type="button" value="Aceptar" onClick="enviar()" class="boton">
					<input name="cmd2" type="button" value="Cancelar" onClick="cancelar()" class="boton">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>