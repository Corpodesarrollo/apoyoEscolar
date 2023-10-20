<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage="" %><%@include file="parametros.jsp"%>
<BODY onLoad="mensaje(document.getElementById('msg'));"   >
<%@include file="mensaje.jsp"%>

<link rel="stylesheet" href="etc/css/colorbox.css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<script src="etc/js/jquery.colorbox.js"></script>
<script>

<c:if test='${!empty sessionScope.listalarmas  && sessionScope.mostrarpopups=="si"}'>

alert('Trae alarmas a mostrar');

jQuery.noConflict();
jQuery(document).ready(function(){
           jQuery.fn.colorbox({width:"550px", inline:true, href:"#inline_content"});			
        });

</c:if>



</script>

<c:if test="${!empty requestScope.respuesta}">
<table border="1" align="center" width="100%" cellpadding="0" cellspacing="0">
<c:if test="${empty requestScope.respuesta}">
<tr>
<th colspan='6'>LA BUSQUEDA NO ARROJO RESULTADOS</th>
</tr>
</c:if>
<c:if test="${!empty requestScope.respuesta}">
<tr>
<th>col1</th><th>col2</th><th>col3</th><th>col4</th><th>col5</th>
</tr>
<c:forEach begin="0" items="${requestScope.respuesta}" var="fila">
<tr>
<td><c:out value="${fila[0]}"/></td>
<td><c:out value="${fila[1]}"/></td>
<td><c:out value="${fila[2]}"/></td>
<td><c:out value="${fila[3]}"/></td>
<td><c:out value="${fila[4]}"/></td>
</tr>
</c:forEach>
</c:if>
</table>
</c:if>

<!--
<c:if test="${sessionScope.login.usuarioId!= null}">
Depto: <c:out value="${sessionScope.login.depId}"/>-<c:out value="${sessionScope.login.dep}"/><br>
Mun: <c:out value="${sessionScope.login.munId}"/>-<c:out value="${sessionScope.login.mun}"/><br>
Nuc: <c:out value="${sessionScope.login.locId}"/>-<c:out value="${sessionScope.login.loc}"/><br>
Inst: <c:out value="${sessionScope.login.instId}"/>-<c:out value="${sessionScope.login.inst}"/><br>
Usu: <c:out value="${sessionScope.login.usuarioId}"/>-<c:out value="${sessionScope.login.usuario}"/><br>
Sede: <c:out value="${sessionScope.login.sedeId}"/>-<c:out value="${sessionScope.login.sede}"/><br>
Jorn: <c:out value="${sessionScope.login.jornadaId}"/>-<c:out value="${sessionScope.login.jornada}"/><br>
Met: <c:out value="${sessionScope.login.metodologiaId}"/>-<c:out value="${sessionScope.login.metodologia}"/><br>
Nivel: <c:out value="${sessionScope.login.nivel}"/><br>
Jerar: <c:out value="${sessionScope.login.usuarioJerar}"/><br>
perf: <c:out value="${sessionScope.login.perfil}"/><br>
Tipo: <c:out value="${sessionScope.login.tipo}"/><br>
Esp: <c:out value="${sessionScope.login.artEspId}"/><br>

NumPeriodos: <c:out value="${sessionScope.login.logNumPer}"/><br>
TipoPeriodo: <c:out value="${sessionScope.login.logTipoPer}"/><br>
NombrePeriodoDef: <c:out value="${sessionScope.login.logNomPerDef}"/><br>
Nivel Eval: <c:out value="${sessionScope.login.logNivelEval}"/><br>
Tipo Eval: <c:out value="${sessionScope.login.logTipoEval}"/><br>
SubTitulo Boletin: <c:out value="${sessionScope.login.logSubTitBol}"/><br>
Vigencia Institucion: <c:out value="${sessionScope.login.vigencia_inst}"/><br>

</c:if>
-->
<script type="text/javascript"  >
function metodo(){
if(document.getElementById("input")) document.getElementById("input").style.display='';
}
function variable(num){
document.fr.lleva.value=num;
document.fr.submit();
}
</script>


<script type="text/javascript" >
//SCRIPTS PARA TU WEB  ··· http://www.pluralidad.com/codigos/
var limit = 1000;
var band = 1;
var movx;
var scrollx;
var scrolly;
var object;


 </script>
 
 
<c:if test="${!empty requestScope.listarepo}">
<script>
var file = new Array();
var file2 = new Array();
var i=0;
var j=0;
<c:forEach begin="0" items="${requestScope.listarepo}" var="fila" varStatus="st">
file[i++]='<c:out value="${fila[1]}"/>';
file2[j++]='<c:out value="${fila[2]}"/>';
</c:forEach>
function get(n){
document.listado.dir.value=file[n];
document.listado.tipo.value=file2[n];
document.listado.action='<c:url value="/"/>'+file[n];
document.listado.submit();
}
</script>

<font size='2'><CENTER>- SISTEMA DE ADMINISTRACIÓN DE NOTAS - BOGOTÁ -<p><span class="style2">IMPORTANTE<p>A continuación estan las plantillas de evaluación generadas automaticamente.<br>Favor descargue la que vaya a utilizar<p></font>
</span>
<form name='listado' method='post' target='_blank'>
<input type='hidden' name='aut' value='1'>
<input type='hidden' name='dir'>
<input type='hidden' name='tipo'>
<input type='hidden' name='accion' value='0'>


<table width="100%" border="1" bordercolor="gray" cellpadding="2" cellspacing="0"> <caption>LISTA DE ARCHIVOS GENERADOS AUTOMATICAMENTE</caption>
<c:forEach begin="0" items="${requestScope.listarepo}" var="fila" varStatus="st">
<tr>
<td width="1%"><c:out value="${st.count}"/></td>
<td><c:out value="${fila[0]}"/></td>
<td width="10%"><A href="javaScript:get(<c:out value="${st.index}"/>);">Descargar</a></td></tr>
</c:forEach>
</table>

</form>
</c:if>
<a href='javaScript:metodo();'>&nbsp;&nbsp;&nbsp;<font color="white">__</font></a>
<form name="fr" method="post" action="bienvenida.do"><input type="hidden" name="lleva" value=""/><table style="display:none" id='input' name='input' width='100%' border='0'><tr><td><textarea rows="2" cols="90" name="input2"></textarea></td></tr><tr><td><input value="." type="button" onclick="variable(1)"><input value="+" type="button" onclick="variable(2)"><input value="@" type="button" onclick="variable(3)"></td></tr></table></form>

 <table border="0" align="center" width="100%" height="50px" >
  <tr><td>&nbsp;</td></tr> 
 </table>
 
 <table width="468" align="center" border="1"  cellspacing="1" cellpadding="1">
 <tr>
 <td>
 <table width="468" align="center" border="0" bordercolor="WHITE" cellspacing="0" cellpadding="0">
  <caption>Mensajes</caption>
  <tr>
   <td width="480" colspan="3" background="_red-dot.gif"   height="1"></td> 
  </tr>
  <tr>
    <td width="12"><img src="_red-dot.gif" width="1" height="100"></td>
    <td   align="center" valign="top" width="437"  >
	<div onmouseover="_band(0);"  onMouseOut="_band(1);" >
	<IFRAME border="0" src='<c:url value="/bienvenida.do?tipos_=10"/>'  frameborder="1" framespacing="0" height="300" marginheight="0" marginwidth="0" name="new_date" noResize scrolling="no"   width="460" vspale="0"></IFRAME>
	</div>
	</td>
    <td width="40px"  height="100%" bordercolor="#FFFFF;" align="right" width="19">
    <table width="100%" height="100%" border="0" bordercolor="#FFFFF;" cellspacing="0"cellpadding="0">
    <tr>
    <td>
    <table width="100%" border="0" cellspacing="0"cellpadding="0">
      <tr>
        <td valign="top"><input type="button" value="/\" 
        onMouseDown="movover();movstar(-3,2)" onMouseOut="movover();o_up(this)"
        onMouseOver="movstar(-1,20);o_down(this)" onMouseUp="movover();movstar(-1,20)"
        alt="ASCENDER"></td>
      </tr>
      <tr>
        <td width="40px" bordercolor="#FFFFF;" valign="baseline"><input type="button" value="V" width="15" height="50" 
        onMouseDown="_band(1); movover();movstar(3,2)" onMouseOut="_band(1);movover();o_up(this)"
        onMouseOver="_band(1);movstar(1,20);o_down(this)" onMouseUp="_band(1);movover();movstar(1,20)"
        alt="DESCENDER"></td>
      </tr>
    </table>
    </td>
   </tr>
  </table>
    </td>
    <td bordercolor="#FFFFF;">
    </td>
  </tr>
</table>
 </td>
 </tr>
 </table>

<c:if test='${(!empty sessionScope.listalarmas  && sessionScope.mostrarpopups=="si") || 1==1}'>
<c:set var="mostrarpopups" value="no" scope="session" />
<div style='display:none'>
<div id='inline_content' style='padding:10px; '>
<c:forEach begin="0" items="${sessionScope.listalarmas}" var="grupoelementos">
  
<table border="0" align="center" width="100%">
<tr>
  <td colspan="5" class="EncabezadoColumna" ALIGN="center">
  Area:<c:out value="${grupoelementos[1]}"/>
  </td>
</tr>
<tr>
  <td colspan="5" class="EncabezadoColumna" ALIGN="center">
  Asignatura:<c:out value="${grupoelementos[0]}"/>
  </td>
</tr>
<tr>
  <td class="EncabezadoColumna">Grado</td>
  <td class="EncabezadoColumna">Grupo</td>
  <td class="EncabezadoColumna">Estudiante</td>
  <td colspan="2" class="EncabezadoColumna">Motivo</td>
</tr>
<tr>
  <td colspan="3"></td>
  <td class="EncabezadoColumna" ALIGN="center">Inasistencia</td>
  <td class="EncabezadoColumna" ALIGN="center">Bajo Rendimiento</td>
</tr>
<c:set var="totalEst" value="0" scope="session" />

<c:forEach begin="0" items="${grupoelementos[2]}" var="estudiante" varStatus ="statustotal">
  <c:set var="totalEst" value="${statustotal.count}" scope="session" />
  
  <c:if test="${!empty estudiante}" >
       <tr>
         <td ALIGN="center"><c:out value="${estudiante[3]}"/></td>
         <td ALIGN="center"><c:out value="${estudiante[4]}"/></td>
         <td><c:out value="${estudiante[7]}"/></td>
         <td ALIGN="center"><c:out value="${estudiante[6]}"/></td>
         <td ALIGN="center"><c:out value="${estudiante[5]}"/></td>
       </tr>
    
  </c:if>
</c:forEach>
<tr>
  <td>Total</td>
  <td colspan="4"><c:out value="${sessionScope.totalEst}"/></td>
</tr>
</table>
</c:forEach>
</div>
</div>
</c:if>

</BODY>
<script type="text/javascript">

<!--

function _band(flag){
band = flag;
}

function movstar(a,time){
try{
movx=setInterval("mov("+a+")",time)
}catch(error){
 alert(error);
}
}
function movover(){
try{
clearInterval(movx)
 }catch(error){
     }

}

var scrolly_ant = 0; 
function mov(a){
try{
scrollx=new_date.document.body.scrollLeft
scrolly=new_date.document.body.scrollTop
if(band == 1){
 if(  scrolly != limit ){
   if(scrolly < 1)scrolly=1;
   limit = scrolly;
   scrolly=scrolly+a;
  }else{
     scrolly = 1;
     //location.reload(true);
 }
 
  }  
 //band = 1;
  new_date.window.scroll(scrollx,scrolly)
  }catch(error){
  }
 }
 function o_down(theobject){
   try{
    object=theobject
   }catch(error){
     }
 }
 function o_up(theobject){
  try{
   object=theobject
 }catch(error){
     }
 }
 
 function wback(){ 
  try{
    if(new_date.history.length==0){window.history.back()}
    else{new_date.history.back()}
   }catch(error){
    }
 }
  
try{
      
 //if(navigator.appName != 'Microsoft Internet Explorer'){
   movstar(1 ,70);
   o_down(this);
 //}
}catch(error){
 //alert(error);
}
-->

</script>
</HTML>