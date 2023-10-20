<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %><%@ page import="siges.util.Recursos" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="filtroPromocion" class="siges.gestionAcademica.promocion.beans.FiltroPromocion" scope="session"/><jsp:setProperty name="filtroPromocion" property="*"/>
<c:import url="/parametros.jsp"/>
<c:set var="tip" value="1" scope="page"/>
<%pageContext.setAttribute("filtroAusencia",Recursos.recurso[Recursos.AUSENCIA]);%>
<link type="text/css"rel="stylesheet"
     href="http://jquery-ui.googlecode.com/svn/tags/1.7/themes/redmond/jquery-ui.css" />   
  <script type="text/javascript"
     src="jquery.min.js"></script>
  <script type="text/javascript"
     src="jquery-ui.min.js"></script>
<script type="text/javascript">
							$(document).ready(function() {
						      $('.fecha').datepicker({
						    	  dateFormat: 'dd/mm/yy',
						    	  dayNamesMin: ['Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'Sa'],
						          monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo',
						              'Junio', 'Julio', 'Agosto', 'Septiembre',
						              'Octubre', 'Noviembre', 'Diciembre'],
						          monthNamesShort: ['Ene', 'Feb', 'Mar', 'Abr',
						              'May', 'Jun', 'Jul', 'Ago',
						              'Sep', 'Oct', 'Nov', 'Dic'] 
						    	  });
						  });
						  </script> 

		<script language="JavaScript">
			<!--
			     
			
			
			
			
		
	
	
	
			
			
			
			
			
			
			var log=1;
			var est=0;
			var nav4=window.Event ? true : false;
			function acepteNumeros(eve,combo,combo2,a,b){
				var key=nav4?eve.which :eve.keyCode;
				if(key==13){next(combo,combo2,a,b);return;}
				if (combo[0].length) {
					for (var i = 0; i < combo[0].length; i++) {
						if (combo[0].options[i].text){
							if(combo[0].options[i].text.toLowerCase()==tecla){
								next(combo,combo2,a,b);
								return;
							}
						}
					}
				}
			}
			
			function hacerValidaciones_frmNuevo(forma){
			   document.frmNuevo.tipoPromocion.value = document.frmFiltro.tipoPromocion.value;
			   validarLista(document.frmFiltro.tipoPromocion, "- Tipo de Promoción.",1);
			   var s;
			  if(forma.ape){ 
			   if(forma.ape.length){
			  
			    for(var i=0; i < forma.ape.length; i++){
			     validarLista(forma.nota[i], "- Para el estudiante "+ forma.ape[i].value +", por favor seleccione el valor promovido.",0);
			     s=document.frmNuevo.nota[i].value;
			     if(forma.nota[i].selectedIndex==1){
			    	 //appendErrorMessage(forma.motivo2[i])
			    	 if(forma.motivo2[i].value==""||forma.motivo2[i].value==null){
			     		appendErrorMessage("- Para el estudiante "+ forma.ape[i].value +", por favor Digite una Observación.")
			     	}
			     }
			     if(forma.nota[i].selectedIndex==4){
			    	 //appendErrorMessage(forma.motivo2[i])
			    	 if(document.getElementById(s.substring(0,  s.lastIndexOf("|"))).value==""||document.getElementById(s.substring(0,  s.lastIndexOf("|"))).value==null){
			     		appendErrorMessage("- Para el estudiante "+ forma.ape[i].value +", por favor Digite la fecha de estado.")
			     	}
			     }
			    }//for(var i=0; i < forma.nota.length; i++)
			   }else{
			   		validarLista(forma.nota, "- Campo 'Promovido' del estudiante "+ forma.ape.value +".",0);
			     	s=document.frmNuevo.nota.value;
				     if(forma.nota.selectedIndex==1){
				    	 //appendErrorMessage(forma.motivo2[i])
				    	 if(forma.motivo2.value==""||forma.motivo2.value==null){
				     		appendErrorMessage("- Para el estudiante "+ forma.ape.value +", por favor Digite una Observación.")
				     	}
				     }
				     if(forma.nota.selectedIndex==4){
				    	 //appendErrorMessage(forma.motivo2[i])
				    	 if(document.getElementById(s.substring(0,  s.lastIndexOf("|"))).value==""||document.getElementById(s.substring(0,  s.lastIndexOf("|"))).value==null){
				     		appendErrorMessage("- Para el estudiante "+ forma.ape.value +", por favor Digite la fecha de estado.");
				     	}
				     }
			     }
			       
			       //validarLista(forma.nota,"- Para el estudiante "+ forma.ape.value +", por favor Digite una Observación.",0);
			    }//if(forma.nota.length){
			  }//if(forma.nota)
			//}
			
			function guardar(tipo){
				var s;
				if(validarForma(document.frmNuevo))
				{
					if(document.frmNuevo.motivo.length){
						for(var i=0;i<document.frmNuevo.motivo.length;i++){
							s=document.frmNuevo.nota[i].value;
							if(trim(document.frmNuevo.motivo2[i].value)!="")
								document.frmNuevo.motivo[i].value=document.frmNuevo.motivo[i].value+"|"+document.frmNuevo.motivo2[i].value;
							else
							document.frmNuevo.motivo[i].value=document.frmNuevo.motivo[i].value+"| ";
								//document.frmNuevo.motivo[i].value="";
							if(trim(document.getElementById(s.substring(0,  s.lastIndexOf("|"))).value)!="")
								document.frmNuevo.fecha_estado[i].value=s.substring(0,  s.lastIndexOf("|"))+"|"+document.getElementById(s.substring(0,  s.lastIndexOf("|"))).value;
							else
								document.frmNuevo.fecha_estado[i].value=s.substring(0,  s.lastIndexOf("|"))+"| ";
							if(document.frmNuevo.nota[i].selectedIndex==0)
								document.frmNuevo.motivo2[i].value="";
						}
					}else{
					s=document.frmNuevo.nota.value; //nota: opción de promoción (select list)
						if(trim(document.frmNuevo.motivo2.value)!=""){ //motivo2: observación
							document.frmNuevo.motivo.value=document.frmNuevo.motivo.value+"|"+document.frmNuevo.motivo2.value;
						}
						else{
							document.frmNuevo.motivo.value=document.frmNuevo.motivo.value+"| "; //motivo: codigo estudiante
						}
						if(trim(document.getElementById(s.substring(0,  s.lastIndexOf("|"))).value)!="" ){
							document.frmNuevo.fecha_estado.value=s.substring(0,  s.lastIndexOf("|"))+"|"+document.getElementById(s.substring(0,  s.lastIndexOf("|"))).value;
						}
						else{
							document.frmNuevo.fecha_estado.value=s.substring(0,  s.lastIndexOf("|"))+"|";
						}
						//}
						if(document.frmNuevo.nota.selectedIndex==0)
							document.frmNuevo.motivo2.value="";
					}
					document.frmNuevo.gradoNom.value = document.frmFiltro.grado.options[document.frmFiltro.grado.options.selectedIndex].text;
					document.frmNuevo.tipo.value=1;
					document.frmNuevo.cmd.value="Aceptar";
					document.frmNuevo.ext2.value='/promocion/ControllerPromocionEdit.do?tipo=1';
					document.frmNuevo.submit();
				}
			}
			
			function next(combo,combo2,estudiante,logro){
				estado_fecha();
				if(estudiante==est){
					estudiante=1;
					combo=combo2;
					if(logro==log) logro=1;
					else logro++;
				}else
					estudiante++;
				var posicion=((estudiante*log)-(log-logro))-1;
				if(combo[posicion]) combo[posicion].focus();
				//else alert(posicion);
			}
			
			function estado_fecha(){
				var s;
				if(document.frmNuevo.nota.value != null){
					//Solo trae un registro
					s=document.frmNuevo.nota.value;
					if( document.frmNuevo.nota.selectedIndex==4){
						document.getElementById(s.substring(0,  s.lastIndexOf("|"))).style.display='block';
					}
					else{
						document.getElementById(s.substring(0,  s.lastIndexOf("|"))).style.display='none';
						document.getElementById(s.substring(0,  s.lastIndexOf("|"))).value="";
						document.frmNuevo.fecha_estado.value="";
					}
				}else{
					for(var i=0;i<document.frmNuevo.nota.length;i++){
						s=document.frmNuevo.nota[i].value;
						if( document.frmNuevo.nota[i].selectedIndex==4){
							document.getElementById(s.substring(0,  s.lastIndexOf("|"))).style.display='block';
						}
						else{
							document.getElementById(s.substring(0,  s.lastIndexOf("|"))).style.display='none';
							document.getElementById(s.substring(0,  s.lastIndexOf("|"))).value="";
							document.frmNuevo.fecha_estado[i].value="";
						}
					}
				}
			}
			
			function onchagefecha(){
				if (document.frmNuevo.nota.value != null){
					s = document.frmNuevo.nota.value;
					document.frmNuevo.fecha_estado.value = document.getElementById(s.substring(0, s.lastIndexOf("|"))).value;					
				}
				else{
					for(var i=0;i<document.frmNuevo.nota.length;i++){
						s=document.frmNuevo.nota[i].value;
						document.frmNuevo.fecha_estado[i].value=document.getElementById(s.substring(0,  s.lastIndexOf("|"))).value;
					}
				}
			}
			
			function inhabilitar(forma){
				for(var i=0;i<forma.nota.length;i++){
					forma.nota[i].disabled=true;
					forma.motivo2[i].disabled=true;
				}
					forma.action='';
					forma.target='_blank';
					forma.cmd0.style.display='none';
			}
			function validar(forma){
				if(document.frmNuevo.motivo && document.frmNuevo.motivo.length){
					for(var i=0;i<document.frmNuevo.motivo.length;i++){
						if(document.frmNuevo.nota[i].selectedIndex==0)
							document.frmNuevo.motivo2[i].value="";
					}
				}else{
						if(document.frmNuevo.nota && document.frmNuevo.nota.selectedIndex==0)
							document.frmNuevo.motivo2.value="";
					}			
			}
			
			function cambioTotal(objeto, nota){
			
				var posicion=0;
				posicion=objeto.selectedIndex;
				if(posicion!=0){
					posicion=posicion-1;
					if(confirm('¿Desea cambiar la promoción para todos los estudiantes?')){
							if(nota){
							nota.selectedIndex=posicion;
							if(nota.length){
								for(i=0;i<nota.length;i++){
												nota[i].selectedIndex=posicion;
								}
							}else{//solo hay una
								nota.selectedIndex=posicion;
							}
						}
					}
				}
				estado_fecha();
			}
			function c_c(){
				document.frmNuevo.tipo.value=2;
				document.frmNuevo.submit();
			}
			function c_r(){
				document.frmNuevo.tipo.value=3;
				document.frmNuevo.submit();
			}
			function desabilitarpromo(){
				//alert("Esta Promocion esta en Modo Consulta, Esperando Actualizacion de Matricula")
				//document.frmNuevo.cmd0.disabled=true;
			}
			window.onload = function(){
				estado_fecha();
				   }
			
			</script>
<c:import url="/mensaje.jsp"/>
	<FORM ACTION='<c:url value="/siges/gestionAcademica/promocion/PromocionGuardar.jsp"/>' METHOD="POST" name='frmNuevo' >
		<input type="hidden" name="cmd" value="">
		<input type="hidden" name="ext2">
		<INPUT TYPE="hidden" NAME="height" VALUE='130'>
		<INPUT TYPE="hidden" NAME="tipo"  VALUE='<c:out value="${pageScope.tip}"/>'>
		<INPUT type="hidden" name="tipoPromocion" id="tipoPromocion"  value='<c:out value="${filtroPromocion.tipoPromocion}"/>'>
		<input type="hidden" name="gradoNom" id="gradoNom" >
		<c:if test="${empty sessionScope.filtroResultado}">
	    <table border="0" align="center" width="100%">
			<tr><c:if test="${empty sessionScope.filtroResultado}"><th>No hay estudiantes a evaluar<th></c:if></tr>
		</table>
		</c:if>
			<c:if test="${!empty sessionScope.filtroResultado}">
			<table style="display:" id='t1' name='t1' width="100%" border="0" align="center" cellpadding="0" cellspacing="1">
			<caption>Gestión Académica - Promoci&oacute;n - Formulario de evaluación</caption>
				<tr>
			    <td colspan="6">
						<input class='boton' id="cmd0" name="cmd0" class="boton" type="button"  value="Guardar" style="display:" onClick="guardar(document.frmNuevo.tipo.value)">
						<c:if test="${!empty requestScope.plantilla}"><a href='<c:url value="/${requestScope.plantilla}"/>' target='_blank'><img src="../etc/img/xls.gif" alt="Descargar Archivo Excel" border="0"></a></c:if>
					</td>
				</tr>
				<c:if test="${requestScope.Rollink=='RECTOR(A)'}">
				<tr>
			    <td colspan="6">
						Para Acceder al Cierre de vigencia haga click<a href='<c:url value="javaScript:c_c()"/>' style="font-size:1.3em" style="font-weight:bold">Aquí</a>.  
					</td>
				</tr>
				</c:if>
				<c:if test="${!empty requestScope.Validavig}">
				<c:forEach begin="0" items="${requestScope.Validavig}" var="lista" varStatus="st">
					<c:if test="${lista[0]==requestScope.Vigenciaact && lista[1]=='1' && lista[2]==requestScope.Validaviginst}">
					<script language="JavaScript">desabilitarpromo();</script> 
					</c:if>
				</c:forEach>
				</c:if>
			</table>
	    <table style="display:" id='t2' name='t2' width="100%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC">
				<tr>
					<th class="EncabezadoColumna" colspan='3'>Estudiante</th>
					<th class="EncabezadoColumna" rowspan='2'>Promoción<br>
						<select name='notaTotal' onChange='cambioTotal(this,document.frmNuevo.nota)'>
							<option value='4'>--Seleccione Uno--</option>
							<option value='4'>Borrar todas las promociones</option>
							<option value='5'>Pendiente</option>
							<option value='1'>Promovido</option>
							<option value='0'>No Promovido</option>
							<option value='2'>Promovido Anticipadamente</option>
							<option value='3'>Retirado</option>
						</select>
					</th>
					
					<th class="EncabezadoColumna" rowspan='2'>Observación</th>
					<th class="EncabezadoColumna" rowspan='2'>Fecha Estado</th>
				</tr>
				<tr>
					<th class="EncabezadoColumna" width='1%'>&nbsp;</th>
					<th class="EncabezadoColumna" >Apellidos</th>
					<th class="EncabezadoColumna" >Nombres</th>
				</tr>
				<c:forEach begin="0" items="${sessionScope.filtroResultado}" var="fila" varStatus="st2"><c:set var="x" value="${fila[0]}" scope="request"/>
				<tr><c:if test="${st2.last}"><script>est=<c:out value="${st2.count}"/>;</script></c:if>
					<td  class='Fila<c:out value="${st2.count%2}"/>' align='center'><b><c:out value="${st2.count}"/></b></td>
					<td  class='Fila<c:out value="${st2.count%2}"/>' width='20%'>
					  <c:out value="${fila[2]}"/> <c:out value="${fila[3]}"/>
					  <input type="hidden" name="ape" value='<c:out value="${fila[2]}"/> <c:out value="${fila[3]}"/> <c:out value="${fila[4]}"/> <c:out value="${fila[5]}"/>' >   
					</td>
					<td  class='Fila<c:out value="${st2.count%2}"/>' width='20%'><c:out value="${fila[4]}"/> <c:out value="${fila[5]}"/></td>
					<th  class='Fila<c:out value="${st2.count%2}"/>' width='5%'>
						<select class='Fila<c:out value="${st2.count%2}"/>' name='nota' id='nota' onChange='next(document.frmNuevo.nota,document.frmNuevo.nota,<c:out value="${st2.count}"/>,1)'>
						<option value='<c:out value="${fila[0]}"/>|4' <%=filtroPromocion.seleccionPromocion((String)request.getAttribute("x")+"|4",1)%>></option>
						<option value='<c:out value="${fila[0]}"/>|5' <%=filtroPromocion.seleccionPromocion((String)request.getAttribute("x")+"|5",1)%>>Pendiente</option>
						<option value='<c:out value="${fila[0]}"/>|1' <%=filtroPromocion.seleccionPromocion((String)request.getAttribute("x")+"|1",1)%>>Promovido</option>
						<option value='<c:out value="${fila[0]}"/>|0' <%=filtroPromocion.seleccionPromocion((String)request.getAttribute("x")+"|0",1)%>>No Promovido</option>
						<option value='<c:out value="${fila[0]}"/>|2' <%=filtroPromocion.seleccionPromocion((String)request.getAttribute("x")+"|2",1)%>>Promovido Anticipadamente</option>
						<option value='<c:out value="${fila[0]}"/>|3' <%=filtroPromocion.seleccionPromocion((String)request.getAttribute("x")+"|3",1)%>>Retirado</option>
						</select>
					</th>
				     <th class='Fila<c:out value="${st2.count%2}"/>' width='50%'><input type="hidden" name='motivo' value='<c:out value="${fila[0]}"/>'><textarea  class='Fila<c:out value="${st2.count%2}"/>' name='motivo2' cols='55' rows='5' wrap='virtual'><%=filtroPromocion.seleccionPromocion((String)request.getAttribute("x"),2)%></textarea></th>
					 <th class='Fila<c:out value="${st2.count%2}"/>' width='20%'><input type="hidden" name='fecha_estado' value=<%=filtroPromocion.seleccionPromocion((String)request.getAttribute("x"),4)%>><input type="text" class="fecha" id='<c:out value="${fila[0]}" />' size="6" onchange="onchagefecha()" value=<%=filtroPromocion.seleccionPromocion((String)request.getAttribute("x"),4)%>></th>
				</tr>
				</c:forEach>
			</table></c:if>
		</form>
<script type="text/javascript">

hacerTrim();
function hacerTrim( ){
				//if(validarForma(document.frmNuevo)){
					if(document.frmNuevo.motivo.length){
						for(var i=0;i<document.frmNuevo.motivo.length;i++){
						 	document.frmNuevo.motivo2[i].value =  trim(document.frmNuevo.motivo2[i].value);
						 
						}
					}else{
						 document.frmNuevo.motivo2.value =  trim(document.frmNuevo.motivo2.value);
					}
				 
				//}
			}

</script>

