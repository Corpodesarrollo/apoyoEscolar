<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/parametros.jsp"/>
<jsp:useBean id="paramsVO" class="siges.subirFotografia.vo.ParamsVO" scope="page"/>
<jsp:useBean id="paramsVO2" class="siges.personal.beans.ParamsVO" scope="page"/>
<jsp:useBean id="nuevoPersonal" class="siges.personal.beans.Personal" scope="session"/>
<html>
<head>
		<script language="JavaScript">
			<!--
    var extensiones = new Array();
    extensiones[0]=".jpe";
    extensiones[1]=".jpg";
    
    
			var fichaPersonal=1;
			var fichaSede=1;
			var fichaConvivencia=1;
			var fichaSalud=1;
			var fichaLaboral=1;
			var fichaAcademica=1;
			var fichaAsistencia=1;
			var fichaCarga=1;
			var fichaFoto=1;
    
			function guardar(){
				if(validarForma(document.frmNuevo)){
						document.frmNuevo.encoding="multipart/form-data";
						document.frmNuevo.cmd.value='<c:out value="${paramsVO.CMD_GUARDAR}"/>';
					//	alert(document.frmNuevo.action);
						document.frmNuevo.submit();
				}
			}

			function lanzar(tipo){
				document.frmNuevo.tipo.value=tipo;
				document.frmNuevo.action='<c:url value="/personal/ControllerNuevoEdit.do"/>';
				document.frmNuevo.target="";
				document.frmNuevo.submit();
			}
			
			
		function hacerValidaciones_frmNuevo(forma){
			validarExtension(forma.filArchivo, "- Archivo (foto en formato jpe o jpg)", extensiones)
		}

		//-->
		</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
		<!-- TABS -->
			<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
				<tr height="1">
					<td width="10" bgcolor="#FFFFFF">&nbsp;</td>
					<td width="510" bgcolor="#FFFFFF"><script>
					if(fichaPersonal==1)document.write('<a href="javaScript:lanzar(1)"><img src="../etc/img/tabs/datos_basicos_0.gif" alt="Datos Básicos"  height="26" border="0"></a>');
					if(fichaSalud==1)document.write('<a href="javaScript:lanzar(4)"><img src="../etc/img/tabs/inf_salud_0.gif" alt="Salud"  height="26" border="0"></a>');
					if(fichaConvivencia==1)document.write('<a href="javaScript:lanzar(2)"><img src="../etc/img/tabs/inf_convivencia_0.gif" alt="Convivencia"  height="26" border="0"></a>');
					if(fichaSede==1)document.write('<a href="javaScript:lanzar(3)"><img src="../etc/img/tabs/Doc_Jor0.gif" alt="Convivencia"  height="26" border="0"></a>');
					if(fichaLaboral==1)document.write('<a href="javaScript:lanzar(5)"><img src="../etc/img/tabs/inf_laboral_0.gif" alt="Laboral"  height="26" border="0"></a>');
					if(fichaAcademica==1)document.write('<a href="javaScript:lanzar(6)"><img src="../etc/img/tabs/for_academica_0.gif" alt="Academica"  height="26" border="0"></a><br>');
					if(fichaAsistencia==1)document.write('<a href="javaScript:lanzar(7)"><img src="../etc/img/tabs/asistencia_0.gif" alt="Asistencia" height="26" border="0"></a>');
					if(fichaCarga==1)document.write('<a href="javaScript:lanzar(8)"><img src="../etc/img/tabs/carga_academica_0.gif" alt=""  height="26" border="0"></a>');
					if(fichaFoto==1)document.write('<img src="../etc/img/tabs/fotografia_1.gif" alt="Subir Foto"  height="26" border="0">');
					</script></td>
					</tr>
		  </table>
		
		<!-- /TABS -->
	<FORM ACTION='<c:url value="/subirFotografia/Nuevo.do"/>' METHOD="POST" name='frmNuevo'>
		<INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_SUBIR_PERSONAL}"/>'>
		<input type="hidden" name="cmd">
		<input type="hidden"  name="estCodigo"  value='<c:out value="${sessionScope.nuevoPersonal.pernumdocum}"/>' >
		
		
		<c:if test="${  (sessionScope.login.perfil == paramsVO2.PERFIL_RECTOR or sessionScope.login.perfil == paramsVO2.PERFIL_ADMIN_ACADEMICO )}">
			 <input type="hidden" name="ext2" value='<c:out value="${sessionScope.subframePer}"/>'>
	 </c:if>
		
		
		
		
		<INPUT TYPE="hidden" NAME="height" VALUE='180'>
		<table width="95%" border="0" align="center" cellpadding="1" cellspacing="0">
			<caption>Cargar fotografia</caption>
				<tr>
			    <td>
						<input class='boton' id="cmd0" name="cmd0" type="button" value="Guardar" onClick="javaScript:guardar()">
					</td>
				</tr>
		</table>		
		<table width="95%" border="0" align="center" cellpadding="1" cellspacing="0">
				<tr>
					<td>Archivo:</td>
					<td>
						<input type='file' name='filArchivo' style='width:400px;'>
					</td>
				</tr>
				
				<tr>
				<td>
			<!-- 	<img src='<c:url value="/recursos/imagen.jpg?tipo=${paramsVO.FICHA_SUBIR_PERSONAL}&param=${sessionScope.nuevoPersonal.pernumdocum}"/>' width="113" height="141"> -->
				</td>
				</tr>
				
			</table>
	</form>
</body>
</html>