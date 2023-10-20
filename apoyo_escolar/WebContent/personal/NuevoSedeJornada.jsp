<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<jsp:useBean id="nuevoPersonal" class="siges.personal.beans.Personal" scope="session"/><jsp:setProperty name="nuevoPersonal" property="*"/>
<jsp:useBean id="nuevoConvivencia" class="siges.estudiante.beans.Convivencia" scope="session"/>
<jsp:useBean id="nuevoSalud" class="siges.estudiante.beans.Salud" scope="session"/><jsp:setProperty name="nuevoSalud" property="*"/>
<jsp:useBean id="laboralVO" class="siges.personal.beans.LaboralVO" scope="session"/><jsp:setProperty name="laboralVO" property="*"/>
<jsp:useBean id="asistenciaVO" class="siges.estudiante.beans.AsistenciaVO" scope="session"/><jsp:setProperty name="asistenciaVO" property="*"/>
<jsp:useBean id="formacionVO" class="siges.personal.beans.FormacionVO" scope="session"/><jsp:setProperty name="formacionVO" property="*"/>
<jsp:useBean id="paramsVO" class="siges.personal.beans.ParamsVO" scope="page"/>
<%@include file="../parametros.jsp"%><c:set var="tip" value="3" scope="page"/>
<c:if test="${sessionScope.NivelPermiso==1}"><c:set var="permisoBoton" value="none"/></c:if>
		<script languaje='javaScript'>			
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

			function hacerValidaciones_frmNuevo(forma){
				//validarSeleccion(forma.jornada,"- Perfil (debe seleccionar por lo menos uno)",0)
			}
			
			function inhabilitarFormulario(){
				for(var i=0;i<document.frmNuevo.elements.length;i++){
					if(document.frmNuevo.elements[i].type != "hidden" && document.frmNuevo.elements[i].type != "button" && document.frmNuevo.elements[i].type != "submit")
					document.frmNuevo.elements[i].disabled=true;
				}	
			}
			function lanzar(tipo){				
					document.frmNuevo.tipo.value=tipo;
					document.frmNuevo.action='<c:url value="/personal/ControllerNuevoEdit.do"/>';
					document.frmNuevo.submit();
			}
			
			function guardar(tipo){
				if(validarForma(document.frmNuevo)){ 
					document.frmNuevo.tipo.value=tipo;
					document.frmNuevo.cmd.value="Guardar";
					document.frmNuevo.submit();
				}
			}
			
			//-->
		</script>
	<%@include file="../mensaje.jsp"%>
	<font size="1">
		<form method="post" name="frmNuevo" onSubmit=" return validarForma(frmNuevo)"  action='<c:url value="/personal/NuevoGuardar.jsp"/>'>
		<table border="0" align="center" width="100%" cellpadding="1" cellspacing="0" bordercolor="silver">
			<tr>
				<td width="45%" bgcolor="#FFFFFF">
				<c:if test="${sessionScope.nuevoPersonal.pertipo!=2}"><input class='boton' name="cmd1" type="button" value="Guardar" onClick="guardar(document.frmNuevo.tipo.value)"></c:if>
			</tr>
		</table>
<!--//////////////////-->
	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="tipo" value='<c:out value="${pageScope.tip}"/>'>
	
	
   <c:if test="${  (sessionScope.login.perfil == paramsVO.PERFIL_RECTOR or sessionScope.login.perfil == paramsVO.PERFIL_ADMIN_ACADEMICO )}">
	 <input type="hidden" name="ext2" value='<c:out value="${sessionScope.subframePer}"/>'>
	</c:if>
	
	
	<INPUT TYPE="hidden" NAME="height" VALUE='180'>
	<input type="hidden" name="cmd" value="Cancelar">
	<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
		<tr height="1">
			<td width="10" bgcolor="#FFFFFF">&nbsp;</td>
			<td width="510" bgcolor="#FFFFFF"><script>
			if(fichaPersonal==1)document.write('<a href="javaScript:lanzar(1)"><img src="../etc/img/tabs/datos_basicos_0.gif" alt="Datos Básicos"  height="26" border="0"></a>');
			if(fichaSalud==1)document.write('<a href="javaScript:lanzar(4)"><img src="../etc/img/tabs/inf_salud_0.gif" alt="Salud"  height="26" border="0"></a>');
			if(fichaConvivencia==1)document.write('<a href="javaScript:lanzar(2)"><img src="../etc/img/tabs/inf_convivencia_0.gif" alt="Datos B&aacute;sicos"  height="26" border="0"></a>');
			if(fichaSede==1)document.write('<img src="../etc/img/tabs/Doc_Jor1.gif" alt="Convivencia"  height="26" border="0">');
			if(fichaLaboral==1)document.write('<a href="javaScript:lanzar(5)"><img src="../etc/img/tabs/inf_laboral_0.gif" alt="Laboral"  height="26" border="0"></a>');
			if(fichaAcademica==1)document.write('<a href="javaScript:lanzar(6)"><img src="../etc/img/tabs/for_academica_0.gif" alt="Academica"  height="26" border="0"></a><br>');
			if(fichaAsistencia==1)document.write('<a href="javaScript:lanzar(7)"><img src="../etc/img/tabs/asistencia_0.gif" alt="Asistencia" height="26" border="0"></a>');
			if(fichaCarga==1)document.write('<a href="javaScript:lanzar(8)"><img src="../etc/img/tabs/carga_academica_0.gif" alt=""  height="26" border="0"></a>');
			if(fichaFoto==1)document.write('<a href="javaScript:lanzar(9)"><img src="../etc/img/tabs/fotografia_0.gif" alt=""  height="26" border="0"></a>');
			</script></td>
			<td align="right"><c:out value="${sessionScope.nuevoPersonal.pernombre1}"/> <c:out value="${sessionScope.nuevoPersonal.pernombre2}"/> <c:out value="${sessionScope.nuevoPersonal.perapellido1}"/> <c:out value="${sessionScope.nuevoPersonal.perapellido2}"/></td>
			</tr>
		</table>
		<!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
									<TABLE align='center' width="95%" cellpadding="0" cellSpacing="0" border="1">
										<c:forEach begin="0" items="${sessionScope.filtroSedeF}" var="fila">
									 
										<c:set var="z" value="${fila[0]}" scope="request"/>
										<tr>
											<th width="150"><c:out value="${fila[1]}"/></th>
											<td>
												<TABLE align='center' width="100%" cellpadding="0" cellSpacing="0" border="1">
					                   <c:forEach begin="0" items="${sessionScope.filtroJornadaF}" var="fila2">
					              
					                  	<c:if test="${fila[0]==fila2[2]}">
													<tr>		
														<td>
														<input type="hidden"  value='<c:out value="${fila2[0]}" />'    > 
							              	<c:set var="y" value="${fila2[0]}" scope="request"/>
															<c:out value="${fila2[1]}"/>
														</td>
														<td>
				                    	<table align='center' width="100%" cellpadding="1" cellSpacing="0" border="0">
																<c:forEach begin="0" items="${sessionScope.filtroPerfilF}" var="item">
									              	<c:set var="x" value="${item.codigo}" scope="request"/>
																	<tr><td> 
																	<input type="checkbox"  name='jornada' onclick="javaScirpt: return msgValidar(this);"  value='<c:out value="${fila[0]}"/>|<c:out value="${fila2[0]}"/>|<c:out value="${item.codigo}"/>' <%=nuevoPersonal.seleccion((String)request.getAttribute("z")+"|"+(String)request.getAttribute("y")+"|"+(Long)request.getAttribute("x"))%>>
																	   <c:out value="${item.nombre}"/>
																 
																	</td></tr>
																</c:forEach>                    
															</table>	
														</td>
													</tr>	
					              	</c:if>
					              </c:forEach>
					              </TABLE>
											</td>
										</tr>
										</c:forEach>
									</TABLE>
<!--//////////////////////////////-->
</form>
<script>


function msgValidar(obje){
  var band = 0; 
  var ind = 0; 
  var _Array = new Array(); 
   for(var i1=0 ; i1 < document.frmNuevo.jornada.length ; i1++ ){
     if(document.frmNuevo.jornada[i1].checked == true){
       band = 0;
       for(var i2 = 0; i2 < _Array.length; i2++){
       
          if(_Array[i2].substring(0, _Array[i2].length - 3) ==  document.frmNuevo.jornada[i1].value.substring(0, document.frmNuevo.jornada[i1].value.length-3)){
           band ++;    
           alert("Solo puede seleccionar un perfil para esta jornada."); 
           return false;      
          }
       }
        
    
         _Array[ind] = document.frmNuevo.jornada[i1].value; 
         ind++;
      
       
     }
   }
   
  
  
     return true;
 
} 
<c:if test="${sessionScope.NivelPermiso==1}">inhabilitarFormulario();</c:if></script>