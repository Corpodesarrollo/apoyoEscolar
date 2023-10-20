<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="paramsVO" class="articulacion.asigTutor.vo.ParamsVO" scope="page"/>
<html>
<c:import url="/parametros.jsp"/>
<head>
<script language="javaScript">
<!--
	function copiarChecks(forma){
        if(forma.check_){
            if(forma.check_.length){
                for(var i=0;i<forma.check_.length ;i++){
                    if(forma.check_[i].checked==true){
                        forma.check[i].value=forma.artHisTutCodEst[i].value;
                    }else{
                    	forma.check[i].value="0";
                    }
                }
            }else{
                if(forma.check_.checked==true){
                    forma.check.value=forma.artHisTutCodEst.value;
                }
            }
        }
    }
    
    function marcar(forma){
	    if(forma.check_!=null){
		      if(forma.check_.length){
		          for(var i=0;i<forma.check_.length ;i++){
		              if(forma.nt[i].value!=""){
		                 if(forma.ct[i].value!=forma.artHisTutCodTutor.value){
		               		forma.check_[i].disabled=true;
		               		forma.check[i].disabled=true;
		               		forma.artHisTutCodEst[i].disabled=true;
		          	 	 }
		          	 	 forma.check_[i].checked=true;
		              }
		              
		          }
		      }else{
		  		  if(forma.nt.value!=""){
		               forma.check_.checked=true;
		               if(forma.ct.value!=forma.artHisTutCodTutor.value){
		               		forma.check_.disabled=true;
		               		forma.check.disabled=true;
		               		forma.artHisTutCodEst.disabled=true;
		          	   }
		          }
		      }
	      }
    }
	function guardar(){
		copiarChecks(document.frmLista);
		document.frmLista.cmd.value=document.frmLista.MODIFICAR.value;
		document.frmLista.submit();

	}
		
	
//-->
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:150px;overflow:auto;vertical-align:top;background-color:#ffffff;">
				<c:import url="/articulacion/asigTutor/Filtro.do"/>
			</div>
		</td></tr>
	</table>
	
	<form action='<c:url value="/articulacion/asigTutor/Save.jsp"/>' method="post" name="frmLista" onSubmit="return validarForma(frmLista)">
		<input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_DEFAULT}"/>'>
		<input type="hidden" name="MODIFICAR" value='<c:out value="${paramsVO.CMD_MODIFICAR}"/>'>
		<input type="hidden" name="cmd" value=''>
		
		<input type="hidden" name="artHisTutCodTutor" value='<c:out value="${sessionScope.FilAsigTutorVO.docente}"/>'>
		<input type="hidden" name="artHisTutSemestre" value='<c:out value="${sessionScope.FilAsigTutorVO.semestre}"/>'>
		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0">
		 	<caption>LISTADO DE ESTUDIANTES</caption>
		 	<c:if test="${empty requestScope.listaEstudiantesVO}">
				<tr>
					<th class="Fila1" colspan='6'>NO EXISTEN REGISTROS DE ESTUDIANTES</th>
				</tr>
			</c:if>
			<c:if test="${!empty requestScope.listaEstudiantesVO}">
				<tr>
					<td>	
	 					<input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton">
	  				</td>
	  			</tr>
				<tr>
					<th width='5%' class="EncabezadoColumna">&nbsp;</th>
					<td width='60%' class="EncabezadoColumna" align="center">Estudiante</td>
					<td width='35%' class="EncabezadoColumna" align="center">Tutor</td>
				</tr>
				<c:forEach begin="0" items="${requestScope.listaEstudiantesVO}" var="lista" varStatus="st">
					<tr>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center">
							<input type="hidden" name="artHisTutCodEst" value='<c:out value="${lista.codigo}"/>'/>
							<input type="hidden" name="check" value='0'/>
							<input type="hidden" name="nt" value='<c:out value="${lista.nomTutor}"/>'/>
							<input type="hidden" name="ct" value='<c:out value="${lista.codTutor}"/>'/>
							<input type="checkbox" name="check_" >
						</td>
						<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${lista.nombre}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${lista.nomTutor}"/>&nbsp;</td>
					</tr>
				</c:forEach>
			</c:if>
		</table>
	</form>
</body>
<script>
	marcar(document.frmLista);
</script>
</html>