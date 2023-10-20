<jsp:useBean id="horario" class="siges.rotacion.beans.Horario" scope="session"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="java.util.ArrayList" %>

<link href='<c:url value="/etc/css/styletooltip.css"/>' rel="stylesheet" type="text/css">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr><td width="100%" valign='top'>
		<div style="width:100%;height:200px;overflow:auto;vertical-align:top;background-color:#ffffff;"><c:import url="/rotacion/ControllerEditar.do?tipo=100"/></div>
	</td></tr>
</table>
<%@include file="../parametros.jsp"%><c:set var="tip" value="101" scope="page"/>
		<script>
			var locked=<c:out value="${sessionScope.isLockedRotacion}"/>;
			var nombreDocenteEscogido='Docente Actual:'+'<c:out value="${requestScope.nombreDocenteEscogido}"/>';
			var docenteescogido=<c:if test="${!empty sessionScope.docenteescogido}"><c:out value="${sessionScope.docenteescogido}"/></c:if><c:if test="${empty sessionScope.docenteescogido}">-1</c:if>;
			var docente='<c:out value="${sessionScope.horario.docente}"/>';
			var horDoc = new Array();//doc | hor | dia | asig
			var inDoc = new Array();
			var inDoc2 = new Array();
			var inHor = new Array();
			var inHor2 = new Array();
			var inEsp = new Array();
			var inEsp2 = new Array();
			var inEsp3 = new Array();
			var espaciosFisicos = new Array();
			var espaciosFisicos2 = new Array();
			var dias = new Array();
			dias[1]='Lunes';dias[2]='Martes';dias[3]='Miercoles';dias[4]='Jueves';dias[5]='Viernes';dias[6]='Sabado';dias[7]='Domingo';
			var jj=0;
			var espacioEscogido=-1;			
			var contAsignatura=0;
			var contAsignaturaEscogida=0;
			
			var grupo='<c:out value="${sessionScope.horario.jerGrupo}"/>';
			var asignatura='<c:out value="${sessionScope.horario.asignatura}"/>';
			jj=0;
			<c:forEach begin="0" items="${requestScope.listaInhabilidadesEsp}" var="fila" varStatus="st">inEsp[jj]='<c:out value="${fila[0]}"/>';inEsp2[jj]='<c:out value="${fila[1]}"/>';inEsp3[jj++]='<c:out value="${fila[2]}"/>';</c:forEach>
			jj=0;
			<c:forEach begin="0" items="${requestScope.listaInhabilidadesDocente}" var="fila" varStatus="st">inDoc[jj]='<c:out value="${fila[0]}"/>';inDoc2[jj++]='<c:out value="${fila[1]}"/>';</c:forEach>
			jj=0;
			<c:forEach begin="0" items="${requestScope.listaInhabilidadesHora}" var="fila" varStatus="st">inHor[jj]='<c:out value="${fila[0]}"/>';inHor2[jj++]='<c:out value="${fila[1]}"/>';</c:forEach>
			jj=0;
			<c:forEach begin="0" items="${requestScope.horDoc}" var="fila" varStatus="st">horDoc[jj++]='<c:out value="${fila}"/>';</c:forEach>
			jj=0;
			<c:forEach begin="0" items="${requestScope.listaEsp}" var="fila" varStatus="st">espaciosFisicos[jj]='<c:out value="${fila[0]}"/>'; espaciosFisicos2[jj++]='<c:out value="${fila[2]}"/>';</c:forEach>
			jj=0;
			</script>
			<script>
			function validarRestricciones(forma){
				var valor;
				var aux;								
				//calcular cantidad de horas que hay asignadas para ese mismo grupo-asig
				if(forma.horas_ && forma.horas_.length){
					for(var i=0;i<forma.horas_.length;i++){
						valor=forma.horas_[i].value;
						var tt=valor.split('|');	
						var g_=tt[0];
						var a_=tt[3];
						var d_=tt[4];
						if(g_==grupo && a_==asignatura && d_!=docente && forma.horas_[i].checked && forma.horas_[i].disabled)
							contAsignatura++;
					}
				}
				//calcular cantidad de horas que hay asignadas para ese mismo grupo-asig-docente osea las que tiene escogidas
				if(forma.horas_ && forma.horas_.length){
					for(var i=0;i<forma.horas_.length;i++){
						valor=forma.horas_[i].value;
						var tt=valor.split('|');	
						var g_=tt[0];
						var a_=tt[3];
						var d_=tt[4];
						if(g_==grupo && a_==asignatura && d_==docente && forma.horas_[i].checked && forma.horas_[i].disabled==false)
							contAsignaturaEscogida++;
					}
				}
				//validaciones de docente y asignatura 
				if(forma.horas_ && forma.horas_.length){
					for(var i=0;i<forma.horas_.length;i++){
						valor=forma.horas_[i].value;
						var tt=valor.split('|');						
						var _g=tt[0];//grupo
						var _h=tt[1];//hora
						var _d=tt[2];//dia
						var _a=tt[3];//asig
						var _doc=tt[4];//docente
						for(var j=0;j<horDoc.length;j++){
							var hh=horDoc[j].split('|');
							var g_=hh[0];//grupo
							var h_=hh[1];//hora
							var d_=hh[2];//dia
							var a_=hh[3];//asig
							
							if(_h==h_ && _d==d_ && _g==g_ && _a!=a_){
								forma.horas_[i].checked=true;
								forma.horas_[i].disabled=true;
								forma.txt[i].value='OTRA ASIGNATURA';
								break;
							}	
							if(_h==h_ && _d==d_ && _g==g_  && _a==a_ && docente!=_doc){
								break;
							}	
							if(_h==h_ && _d==d_ && _g!=g_ && docente!=_doc){
								forma.horas_[i].checked=true;
								forma.horas_[i].disabled=true;
								forma.txt[i].value='OTRO DOCENTE';
								forma.txt[i].style.color='green';
								break;
							}	
							if(_h==h_ && _d==d_ && _g!=g_ && docente==_doc){
								forma.horas_[i].checked=true;
								forma.horas_[i].disabled=true;
								forma.txt[i].value='OTRO GRUPO';
								forma.txt[i].style.color='blue';
								forma.horas_[i].value='  ';
								forma.horas[i].value='  ';
								break;
							}	
						}						
					}
				}
				
				//VALIDACION DE INHABILIDADES DE DOCENTE
				if(forma.horas_ && forma.horas_.length){
					for(var i=0;i<forma.horas_.length;i++){
						valor=forma.horas_[i].value;
						var tt=valor.split('|');	
						var d=tt[2];
						var h=tt[1];
						for(var j=0;j<inDoc.length;j++){
							var dia=inDoc[j];
							var hora=inDoc2[j];
							if(dia==d && hora==h){
								forma.horas_[i].checked=false;
								forma.horas_[i].disabled=true;
								forma.txt[i].value='INHABILIDAD DOC';
								forma.txt[i].style.color='orange';								
								forma.horas_[i].value='  ';
								forma.horas[i].value='  ';
								if(document.getElementById(valor)){
									document.getElementById(valor).style.display='none';
								}
								break;
							}	
						}						
					}
				}
				
				//VALIDACION DE INHABILIDADES DE HORA
				if(forma.horas_ && forma.horas_.length){
					for(var i=0;i<forma.horas_.length;i++){
						valor=forma.horas_[i].value;
						var tt=valor.split('|');	
						var d=tt[2];
						var h=tt[1];
						for(var j=0;j<inHor.length;j++){
							var dia=inHor[j];
							var hora=inHor2[j];
							if(dia==d && hora==h){
								forma.horas_[i].checked=false;
								forma.horas_[i].disabled=true;
								forma.txt[i].value='INHABILIDAD HORA';
								forma.txt[i].style.color='brown';								
								forma.horas_[i].value='  ';
								forma.horas[i].value='  ';
								if(document.getElementById(valor)){
									document.getElementById(valor).style.display='none';
								}
								break;
							}	
						}						
					}
				}
				
				//VALIDACION DE ESPACIO ESCOGIDO
				var value=-1;
				if(forma.horas_ && forma.horas_.length){
					for(var i=0;i<forma.horas_.length;i++){
						if(forma.horas_[i].checked==true && forma.horas_[i].disabled==false){
							value=forma.horas[i].value;
							var p=value.split('|');
							if(p[5].length>0){
								espacioEscogido=p[5];
							}
						}
					}
				}
				if(forma.espacio && forma.espacio.length){
					for(var i=0;i<forma.espacio.length;i++){
						if(forma.espacio.options[i].value==espacioEscogido){
							forma.espacio.options[i].selected=true;
							break;
						}
					}
				}
				
				if(forma.horas_ && forma.horas_.length){
					for(var i=0;i<forma.horas_.length;i++){
						if((forma.horas_[i].checked==true && forma.horas_[i].disabled==true) || forma.horas_[i].disabled==true){
							forma.horas_[i].style.display='none';
						}
					}
				}
				
				
			}
			
			//FUNCION QUE VALIDA ANTES DE  ENVIAR LA SOLICITUD
			function validar(forma){
				var value;
				var aux;
				var nn=0;
				var valor;
				var e=forma.espacio.options[forma.espacio.selectedIndex].value;				
				var ihd=forma.ihDocente.value;
				var ih=0+forma.ih.value;				
				//validar si la asignatura ya esta en la carga de otro docente
				
				if(contAsignatura>parseInt(ih)){
					//alert('La asignatura escogida ya esta puesta en la carga horaria de otro docente, este docente se agrega y no se puede asignar al docente seleccionado');
					alert('Ya ha sobrepasado la cantidad de horas de la intensidad horaria');
					
					return false;
				}
				
				//validar si la ih del docente alcanza para la ih de la asignatura
			/*	if(parseInt(forma.ihReal.value)>0){
					if((parseInt(ih)-parseInt(ihd))>0){
						alert('La asignatura escogida tiene una intensidad horaria superior a la cantidad de horas libres del docente ('+ihd+')');
						return false;
					}
				}*/
				
				
				//validar si la ih del docente alcanza para la ih de la asignatura
				if(parseInt(forma.ihReal.value)>0){
					
					if((parseInt(ih)-parseInt(ihd))>0){
						
						for(i=0; i <document.frmHor.asignar.length; i++){
					        if(document.frmHor.asignar[i].checked){
					            valorSeleccionado = document.frmHor.asignar[i].value;
					           
					        }
					    }

						if(valorSeleccionado=='NO'){
							alert('La asignatura escogida tiene una intensidad horaria superior a la cantidad de horas libres del docente ('+ihd+'), debe escoger un docente complementario para completar la intensidad horaria');
							return false;
						}
						
					}
				}
				
				//validar capacidad de espacio fisico
				for(var i=0;i<espaciosFisicos.length;i++){
					if(espaciosFisicos[i]==e){
						if(espaciosFisicos2[i]<forma.cupo.value){
							alert('La capacidad del espacio físico no es suficiente para el cupo del grupo ('+forma.cupo.value+')');
							return false;
						}
					}
				}
				//VALIDAR INHABILIDADES DEL ESPACIO FISICO
				if(forma.horas && forma.horas.length){
					for(var i=0;i<forma.horas.length;i++){
						if(forma.horas_[i].checked==true && forma.horas_[i].disabled==false){
							valor=forma.horas_[i].value;
							var tt=valor.split('|');	
							var d=tt[2];
							var h=tt[1];							
							//alert(e+'//'+d+'//'+h);
							for(var j=0;j<inEsp.length;j++){
								var esp=inEsp[j];
								var dia=inEsp2[j];
								var hora=inEsp3[j];
								//alert(esp+'//'+dia+'//'+hora);
								if(esp==e && dia==d && hora==h){
									alert('No se puede asignar horas el día '+dias[dia]+' a la hora '+hora+' porque el espacio físico esta inhabilitado');
									return false;
								}	
							}
						}
					}
				}
				
				//VALIDAR INTENSIDAD HORARIA CONTRA HORAS ESCOGIDAS
				if(forma.horas && forma.horas.length){
					for(var i=0;i<forma.horas.length;i++){
						if(forma.horas_[i].checked==true && forma.horas_[i].disabled==false){
							nn++;
						}
					}
					var band=0;
					if((nn==0 && forma.ih.value==0) || (nn==0 && forma.ih.value>0)){band=1;}					
					if(nn>forma.ih.value && band==0){
						alert('La cantidad de horas escogidas exede a la intensidad horaria del paramaetro de grado por asignatura es:'+forma.ih.value+' horas, actualmente escogido:'+nn+' horas');
						return false;
					}
					if(nn<forma.ih.value  && band==0){
						
						for(i=0; i <document.frmHor.asignar.length; i++){
					        if(document.frmHor.asignar[i].checked){
					            valorSeleccionado = document.frmHor.asignar[i].value;
					           
					        }
					    }

						if(valorSeleccionado=='NO'){
							alert('La cantidad de horas escogidas es menor a la intensidad horaria del paramaetro de grado por asignatura es:'+forma.ih.value+' horas, actualmente escogido:'+nn+' horas, el restante deben ser agregadas para docente complementario');
							return false;
						}else{
							alert('La cantidad de horas escogidas es menor a la intensidad horaria del paramaetro de grado por asignatura es:'+forma.ih.value+' horas, actualmente escogido:'+nn+' horas, el restante seran agregadas para el docente complementario');	
						}

					}
				}
				//quitar valor a checks no seleccionados
				if(forma.horas && forma.horas.length){
					for(var i=0;i<forma.horas.length;i++){
						if(forma.horas_[i].checked==false && forma.horas_[i].disabled==false){
							forma.horas_[i].value='  ';
							forma.horas[i].value='  ';
							//forma.horas[i].disabled=true;
						}
					}
				}
				//PONER EL ESPACIO A CADA CHECK
				if(forma.horas && forma.horas.length){
					for(var i=0;i<forma.horas.length;i++){
						if(forma.horas_[i].checked==true && forma.horas_[i].disabled==false){
							value=trim(forma.horas[i].value)
							if(value.length>0){
								var p=value.split('|');
								forma.horas[i].value=p[0]+'|'+p[1]+'|'+p[2]+'|'+p[3]+'|'+p[4]+'|'+e;
							}
						}
					}
				}	
				return true;
			}

			function guardar2(tipo){
				if(validarForma(document.frmHor)){
					calcularIhReal(document.frmHor);
					if(validar(document.frmHor)){
						document.frmHor.tipo.value=tipo;
						//alert(tipo);
						document.frmHor.cmd.value="Guardar2";
						document.frmHor.send.value="1";
						//alert(document.frmHor.asignar.value);
						if(document.getElementById("docente2").options[document.getElementById("docente2").selectedIndex].value!='-1'){
						document.frmHor.docenteescogido2.value=document.getElementById("docente2").options[document.getElementById("docente2").selectedIndex].value;
					    }else{
					    	document.frmHor.docenteescogido2.value='ninguno';	
					    }
						document.frmHor.submit();
					}	
				}
			}
			
			function mostrarFiltro2(valor){
				//alert('ES'+docenteescogido);
				select=document.getElementById("docente2");
				if(select.options.length<=1){
					
				alert('No posee más docentes designados para esta operación');
				}else{
				for(var i=0;i<select.options.length;i++){
					//alert('valor'+i+' '+select.options[i].value);
				  if(select.options[i].value==docenteescogido){
					  select.options[i]=null;
					  break;
				  }	
				}
				if(valor=='SI'){
					//alert('va cargar otro');
					//alert(valor);
					//document.frmHor.tabladespliegue1.style.display='block';
					document.getElementById("tabladespliegue1").style.display='block';
					/*document.getElementById("despliegue1").style.display='block';
					document.getElementById("despliegue2").style.display='block';*/
				}else{
					//alert(valor);
					/*document.getElementById("despliegue1").style.display='none';
					document.getElementById("despliegue2").style.display='none';*/
					document.getElementById("tabladespliegue1").style.display='none';
				}
				}
			}
			
			function cargarHorasDispDocente2(){
				//borrar_combo(forma.horasdisp);
				document.frmAux1.docente.value=document.getElementById("docente2").options[document.getElementById("docente2").selectedIndex].value;
				//forma.vigencia.options[forma.vigencia.selectedIndex].value;
				//alert('docente escogido es'+document.getElementById("docente2").value);
				document.frmAux1.combo.value="5";
				document.frmAux1.target="frame";
				document.frmAux1.submit();
			}
			
			
			function calcularIhReal(forma){
				var cant=0;
				if(forma.horas && forma.horas.length){
					for(var i=0;i<forma.horas.length;i++){
						if(forma.horas_[i].checked==true && forma.horas_[i].disabled==false){
							cant++;
						}
					}
				}
				forma.ihReal.value=cant-contAsignaturaEscogida;
			}
			
			
			function hacerValidaciones_frmHor(forma){
				var nn=0;
				if(forma.horas && forma.horas.length){
					for(var i=0;i<forma.horas.length;i++){
						if(forma.horas_[i].checked==true && forma.horas_[i].disabled==false){
							nn=1;
							break;
						}
					}					
				}	
				if(nn==1){
					validarLista(forma.espacio,"- Espacio físico",1);
				}
			}
			
		</script>
		<%@include file="../mensaje.jsp"%>
		<form method="post" name="frmHor" onSubmit="return validarForma(frmHor)" action='<c:url value="/rotacion/NuevoGuardar.jsp"/>'>
			<input type="hidden" name="tipo" value='<c:out value="${tip}"/>'>
			<input type="hidden" name="cmd" value="Cancelar">
			<input type="hidden" name="send" value="">
			<input type="hidden" name="jer" value='<c:out value="${sessionScope.horario.jerGrupo}"/>'>
			<input type="hidden" name="ih" value='<c:out value="${sessionScope.horario.ih}"/>'>
			<input type="hidden" name="cupo" value='<c:out value="${sessionScope.horario.cupo}"/>'>
			<input type="hidden" name="ihDocente" value='<c:out value="${sessionScope.horario.ihDocente}"/>'>
			<input type="hidden" name="docenteescogido2" id="docenteescogido2" value=''>
			<input type="hidden" name="ihReal" value='0'>
				<c:if test="${requestScope.hora!=null}">
			<script>visible=1;</script>
			<TABLE style="display:" id='th1' name='th1' width="90%" align='center' cellpadding="0" cellSpacing="0" border='0'>
			   <tr>
			      <td style="padding-left:60px" >
				<script>
				if(locked==0){
					document.write('<input name="cmd1" class="boton" type="button" value="Guardar Horario" onClick="guardar2(document.frmHor.tipo.value)">');
					document.write('</td><td colspan="1" style="margin-left:20px;padding-left:90px"><a href="javascript:print()"><input name="impr1" class="boton" type="button" value="Imprimir" style="margin-top:2px;margin-bottom:2px;" onClick="javascript:window.print(); return false;"></a>');
					
				}
				</script>
					  <c:if test="${sessionScope.isLockedRotacion==1}">
						  <span class="style2">Nota: Proceso de rotación pendiente por aprobar o rechazar</span>
					  </c:if>
				</td>
				</tr>
				<tr>
				  <td colspan="2"></td>
				</tr>
				<tr>
				<td>
				 	<script type="text/javascript">
					document.write(""+nombreDocenteEscogido+"");
					</script>
				</td>
				<td>
				<span class="style2" >*</span> Espacio físico (cap.):&nbsp;&nbsp;
				<select name="espacio" style='width:150px;'><option value=''>-- Seleccione uno --</option><c:forEach begin="0" items="${requestScope.listaEsp}" var="fila"><option value="<c:out value="${fila[0]}"/>" <c:if test="${requestScope.espacio==fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/> (<c:out value="${fila[2]}"/>)</option></c:forEach></select></td>
				</tr>
				<tr>
				<td>Niños del grupo:&nbsp; <font style='COLOR:#006699;'><c:out value="${sessionScope.horario.cupo}"/></font>&nbsp;&nbsp;&nbsp;</td>
				<td>Intensidad Horaria:&nbsp; <font style='COLOR:#006699;'><c:out value="${sessionScope.horario.ih}"/></font>&nbsp;&nbsp;&nbsp;</td>
			   
			</tr>
			</TABLE>
			<table>
			<tr>
			<td colspan="3">
			<span class="style2" >*</span> ¿ Desea asignar otro docente para esta misma asignatura y grupo ?
			No <input type="radio" name="asignar" value="NO" checked="checked" onclick="mostrarFiltro2('NO');">
			Si <input type="radio" name="asignar" value="SI" onclick="mostrarFiltro2('SI');" >
			</td>
			</tr>
			</table>
			<br>
			<table id="tabladespliegue1" style="display:none;">
			<tr  >
				<td><span class="style2" >*</span>Docente complementario:</td>
				<td colspan=3>
					<select name="docente2" id="docente2" style='width:300px;' onChange='cargarHorasDispDocente2()'>
						<option value='-1'>-- Seleccione uno --</option>
							<c:forEach begin="0" items="${requestScope.filtroDocenteF2}" var="fila"><option value="<c:out value="${fila[0]}"/>" ><c:out value="${fila[1]}"/></option></c:forEach>
					</select>
				</td>
			</tr>
			<tr >
				<td><span class="style2" ></span>Disponibilidad de horas x docente:</td>
				<td colspan=3>
					
					<input  name="horasdisp2" id="horadisp2" value="<c:if test="${!empty requestScope.horasdisp2}"><c:out value="${requestScope.horasdisp2}"/></c:if><c:if test="${empty requestScope.horasdisp2}">No ha escogido docente</c:if>" disabled="disabled" />
					
				</td>
			</tr>
			</table>
			<br>
			<TABLE style="display:" id='th2' name='th2' width="98%" align='center' cellpadding="0" cellSpacing="0" border='1'>
				<caption>HORARIO</caption>
				<tr>
				<th>Clase /Dia</th><th>Lunes</th><th>Martes</th><th>Miercoles</th><th>Jueves</th><th>Viernes</th>
				</tr><c:set var="num" scope="page" value="-1"/><c:set var="grupo" scope="page" value="${sessionScope.horario.jerGrupo}"/><c:set var="asig" scope="page" value="${sessionScope.horario.asignatura}"/><c:set var="doc" scope="page" value="${sessionScope.horario.docente}"/>
				<c:forEach begin="1" end="${requestScope.hora[0]}" step="1" var="hora" varStatus="status">
				<tr><th><c:out value="${hora}"/></th>
				<c:forEach begin="1" end="5" step="1" var="dia" varStatus="status2"><th>
				<c:set var="hh" scope="page" value=""/><c:set var="chk" scope="page" value=""/><c:set var="a_" scope="page" value=""/><c:set var="d_" scope="page" value=""/>
					<c:if test="${empty requestScope.hora2}"><c:set var="hh" scope="page"><c:out value="${grupo}"/>|<c:out value="${hora}"/>|<c:out value="${dia}"/>|<c:out value="${asig}"/>|<c:out value="${doc}"/>|</c:set></c:if>
					<c:forEach begin="0" items="${requestScope.hora2}" var="fila" varStatus="st">
						<c:choose>
							<c:when test="${dia==1 && fila[1]==hora}"><c:set var="a_" value="${fila[2]}"/><c:if test="${fila[2]==null}"><c:set var="a_" value="${asig}"/></c:if>   <c:set var="d_" value="${fila[3]}"/><c:if test="${fila[3]==null}"><c:set var="d_" value="${doc}"/></c:if>    <c:set var="hh" scope="page"><c:out value="${grupo}"/>|<c:out value="${fila[1]}"/>|1|<c:out value="${a_}"/>|<c:out value="${d_}"/>|<c:out value="${fila[4]}"/></c:set>  <c:if test="${fila[2]==asig && fila[3]==doc}"><c:set var="chk" scope="page" value="checked"/></c:if>    <c:if test="${(fila[2]!=null && fila[2]!=asig) || (fila[3]!=null && fila[3]!=doc)}"><c:set var="chk" scope="page" value="checked disabled"/></c:if>        </c:when>
							<c:when test="${dia==2 && fila[1]==hora}"><c:set var="a_" value="${fila[5]}"/><c:if test="${fila[5]==null}"><c:set var="a_" value="${asig}"/></c:if>   <c:set var="d_" value="${fila[6]}"/><c:if test="${fila[6]==null}"><c:set var="d_" value="${doc}"/></c:if>    <c:set var="hh" scope="page"><c:out value="${grupo}"/>|<c:out value="${fila[1]}"/>|2|<c:out value="${a_}"/>|<c:out value="${d_}"/>|<c:out value="${fila[7]}"/></c:set>  <c:if test="${fila[5]==asig && fila[6]==doc}"><c:set var="chk" scope="page" value="checked"/></c:if>    <c:if test="${(fila[5]!=null && fila[5]!=asig) || (fila[6]!=null && fila[6]!=doc)}"><c:set var="chk" scope="page" value="checked disabled"/></c:if>        </c:when>
							<c:when test="${dia==3 && fila[1]==hora}"><c:set var="a_" value="${fila[8]}"/><c:if test="${fila[8]==null}"><c:set var="a_" value="${asig}"/></c:if>   <c:set var="d_" value="${fila[9]}"/><c:if test="${fila[9]==null}"><c:set var="d_" value="${doc}"/></c:if>    <c:set var="hh" scope="page"><c:out value="${grupo}"/>|<c:out value="${fila[1]}"/>|3|<c:out value="${a_}"/>|<c:out value="${d_}"/>|<c:out value="${fila[10]}"/></c:set> <c:if test="${fila[8]==asig && fila[9]==doc}"><c:set var="chk" scope="page" value="checked"/></c:if>    <c:if test="${(fila[8]!=null && fila[8]!=asig) || (fila[9]!=null && fila[9]!=doc)}"><c:set var="chk" scope="page" value="checked disabled"/></c:if>        </c:when>
							<c:when test="${dia==4 && fila[1]==hora}"><c:set var="a_" value="${fila[11]}"/><c:if test="${fila[11]==null}"><c:set var="a_" value="${asig}"/></c:if> <c:set var="d_" value="${fila[12]}"/><c:if test="${fila[12]==null}"><c:set var="d_" value="${doc}"/></c:if>  <c:set var="hh" scope="page"><c:out value="${grupo}"/>|<c:out value="${fila[1]}"/>|4|<c:out value="${a_}"/>|<c:out value="${d_}"/>|<c:out value="${fila[13]}"/></c:set> <c:if test="${fila[11]==asig && fila[12]==doc}"><c:set var="chk" scope="page" value="checked"/></c:if>  <c:if test="${(fila[11]!=null && fila[11]!=asig) || (fila[12]!=null && fila[12]!=doc)}"><c:set var="chk" scope="page" value="checked disabled"/></c:if>    </c:when>
							<c:when test="${dia==5 && fila[1]==hora}"><c:set var="a_" value="${fila[14]}"/><c:if test="${fila[14]==null}"><c:set var="a_" value="${asig}"/></c:if> <c:set var="d_" value="${fila[15]}"/><c:if test="${fila[15]==null}"><c:set var="d_" value="${doc}"/></c:if>  <c:set var="hh" scope="page"><c:out value="${grupo}"/>|<c:out value="${fila[1]}"/>|5|<c:out value="${a_}"/>|<c:out value="${d_}"/>|<c:out value="${fila[16]}"/></c:set> <c:if test="${fila[14]==asig && fila[15]==doc}"><c:set var="chk" scope="page" value="checked"/></c:if>  <c:if test="${(fila[14]!=null && fila[14]!=asig) || (fila[15]!=null && fila[15]!=doc)}"><c:set var="chk" scope="page" value="checked disabled"/></c:if>    </c:when>
							<c:when test="${dia==6 && fila[1]==hora}"><c:set var="a_" value="${fila[17]}"/><c:if test="${fila[17]==null}"><c:set var="a_" value="${asig}"/></c:if> <c:set var="d_" value="${fila[18]}"/><c:if test="${fila[18]==null}"><c:set var="d_" value="${doc}"/></c:if>  <c:set var="hh" scope="page"><c:out value="${grupo}"/>|<c:out value="${fila[1]}"/>|6|<c:out value="${a_}"/>|<c:out value="${d_}"/>|<c:out value="${fila[19]}"/></c:set> <c:if test="${fila[17]==asig && fila[18]==doc}"><c:set var="chk" scope="page" value="checked"/></c:if>  <c:if test="${(fila[17]!=null && fila[17]!=asig) || (fila[18]!=null && fila[18]!=doc)}"><c:set var="chk" scope="page" value="checked disabled"/></c:if>    </c:when>
							<c:when test="${dia==7 && fila[1]==hora}"><c:set var="a_" value="${fila[20]}"/><c:if test="${fila[20]==null}"><c:set var="a_" value="${asig}"/></c:if> <c:set var="d_" value="${fila[21]}"/><c:if test="${fila[21]==null}"><c:set var="d_" value="${doc}"/></c:if>  <c:set var="hh" scope="page"><c:out value="${grupo}"/>|<c:out value="${fila[1]}"/>|7|<c:out value="${a_}"/>|<c:out value="${d_}"/>|<c:out value="${fila[22]}"/></c:set> <c:if test="${fila[20]==asig && fila[21]==doc}"><c:set var="chk" scope="page" value="checked"/></c:if>  <c:if test="${(fila[20]!=null && fila[20]!=asig) || (fila[21]!=null && fila[21]!=doc)}"><c:set var="chk" scope="page" value="checked disabled"/></c:if>    </c:when>
						</c:choose>
					
					</c:forEach><c:if test="${hh==null || hh==''}"><c:set var="hh" scope="page"><c:out value="${grupo}"/>|<c:out value="${hora}"/>|<c:out value="${dia}"/>|<c:out value="${asig}"/>|<c:out value="${doc}"/>|</c:set></c:if>
					<input type='hidden' name='horas' value='<c:out value="${hh}"/>'><c:set var="a" scope="page" value=""/>
					<%
					boolean validar=false;
					%>
					<c:if test="${chk=='checked disabled'}">
					
					<c:forEach begin="0" items="${requestScope.filtroAsignaturaH}" var="fila2">
					
					<c:if test="${fila2[0]==a_}">
					       <c:set var="a" scope="page" value="${fila2[1]}"/>
					         <c:set var="nomdoc" scope="request" value="Docente :"/>
					         
					       <c:forEach begin="0" items="${requestScope.nombrexdocentes}" var="comp1">
					               
					               <c:if test="${fila2[0]==comp1[1]}">

					                  <c:set var="nomdoc" scope="request" value="${nomdoc}<br/>-${comp1[0]}"/>      
					               </c:if>             
					       </c:forEach>
					       <%
					         validar=true;
					       %>
					 </c:if>
					 
					 </c:forEach>
					  <c:if test="${nomdoc=='Docente :'}">
					               <c:set var="nomdoc" scope="request" value="${nomdoc}<br/>No identificado"/> 
					               </c:if>   
					 </c:if>
					 <% if(validar){
						 out.println("<span class=\"hotspot\" onmouseover=\"tooltip.show('"+request.getAttribute("nomdoc")+"');\" onmouseout=\"tooltip.hide();\">");						    
					 } %>
					      <input type='text' size="15" align="middle" name='txt' readonly style="background-color=#FfFfFf;border=none;color=red" value='<c:out value="${a}"/>'>
					 <% if(validar){
						 out.println("</span>");						    
					 } %>
					  
					      <br>
					<input type='checkbox' name='horas_' value='<c:out value="${hh}"/>' style="display:" <c:out value="${chk}"/>><c:set var="num" scope="page" value="${num+1}"/>
				&nbsp;</th></c:forEach></c:forEach></tr>
			</TABLE></c:if>
			<center><b>CONVENCIONES</b><BR>
			<table border=0>
			<tr><td align='right'><FONT COLOR='RED' size='1'>< ASIGNATURA ></FONT></td><td><FONT size='1'> : Indica que el docente dicta otra asignatura para este mismo grupo.</FONT></td></tr>
			<tr><td align='right'><FONT COLOR='blue' size='1'>OTRO GRUPO</FONT></td><td><FONT size='1'> : Indica que el docente dicta esa u otra asignatura para otro grupo.</FONT></td></tr>
			<tr><td align='right'><FONT COLOR='green' size='1'>OTRO DOCENTE</FONT></td><td><FONT size='1'> : Indica que otro docente dicta esa u otra asignatura para este mismo grupo.</FONT></td></tr>
			<tr><td align='right'><FONT COLOR='orange' size='1'>INHABILIDAD DOCENTE</FONT></td><td><FONT size='1'> : Indica que el docente esta inhabilitado en ese dia - hora.</FONT></td></tr>
			<tr><td align='right'><FONT COLOR='brown' size='1'>INHABILIDAD HORA</FONT></td><td><FONT size='1'> : Indica que el horario esta inhabilitado en ese dia - hora.</FONT></td></tr>
			</table>
			</center>
		</form><script>if(visible==1)visibilidad=1;
		validarRestricciones(document.frmHor);</script> 
		
		<form method="post" name="frmAux1" action="<c:url value="/rotacion/FiltroAux.do"/>"><input type="hidden" name="combo" value=""><input type="hidden" name="vig" value="<c:out value="${requestScope.vigenciaseleccionada}"/>"><input type="hidden" name="docente" id="docente" value=""></form>
		<script type="text/javascript" language="javascript" src="<c:url value="/etc/js/scripttooltip.js"/>"></script>