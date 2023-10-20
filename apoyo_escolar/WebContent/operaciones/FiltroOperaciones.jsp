<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<jsp:useBean id="filtroEst" class="siges.estudiante.beans.FiltroBean" scope="session"/>
<jsp:setProperty name="filtroEst" property="*"/>
<jsp:useBean id="paramsVO" class="siges.common.vo.Params" scope="request"/>

<%@include file="../parametros.jsp"%>
<%@include file="../mensaje.jsp"%>
 
	
		<FORM  METHOD="POST" name='frm' onSubmit=" return validarForma(frm)" action='<c:url value="/operacion/ControllerOperacion.do"/>'>
			<INPUT TYPE="hidden" NAME="nivel"  VALUE="1">
			<INPUT TYPE="hidden" NAME="tipo"  VALUE="1">
			<INPUT TYPE="hidden" NAME="cmd2"  VALUE="buscar">
			<input type="hidden" name="filinst"      id="filinst"  value='<c:out value="${login.instId}"/>'>
			<table border="0" align="center" bordercolor="#FFFFFF" width="90%">
			<caption>OPERACIONES</caption>
				<tr>
				  <td>
						<INPUT class='boton' TYPE="submit" NAME="cmd1"  VALUE="Registrar">
					</td>
				</tr>			
			</table>
			<table border="0" align="center" bordercolor="#FFFFFF" width="90%" cellpadding="2" cellSpacing="0">
				<tr><th colspan='4' class='Fila0'>Operacion</th></tr>
				<tr>
					<td><span class="style2" >*</span>Digite de a un script: (No use ; al final de la oración y los procedimientos use CALL)</td>
					<td>
			      		<textarea name="taOperacion" id="taOperacion" cols="100" rows="10" maxlength="4000"></textarea>
			      	</td>
				</tr>
				<tr>
					<td><span class="style2" >*</span>Descripcion Cambios</td>
					<td>
			      		<textarea name="taDescripcion" id="taDescripcion" cols="100" rows="10" maxlength="4000"></textarea>
			      	</td>
				</tr>	
			</table>	
		</FORM>