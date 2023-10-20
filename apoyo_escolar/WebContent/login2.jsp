<!--	VERSION		FECHA			AUTOR				DESCRIPCION
			1.0		23/02/2021		JORGE CAMACHO		CODIGO ESPAGUETI Y SE MOFIFICÓ EL DISEÑO DE LA PÁGINA
														CON LAS NUEVAS IMÁGENES Y QUE FUERA RESPONSIVE
			2.0		16/04/2021		JORGE CAMACHO		SE AJUSTÓ LA PRESENTACIÓN DE LOS REGISTROS CUANDO
														INICIA SESIÓN UN USUARIO CON MULTIPLES ACCESOS
			3.0		02/06/2021		JORGE CAMACHO		SE CAMBIA LA CODIFICACIÓN DE LA PAGINA DE UTF-8 A windows-1252
-->

<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage="" %>

<%@ page session="false" %>

<!DOCTYPE html>
<html lang="es-CO">

	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	
	<head>
	
		<title>SISTEMA DE APOYO ESCOLAR - Login de acceso -</title>
		<meta charset="windows-1252">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<script language=javascript src='<c:url value="/etc/js/validar.js"/>'></script>
		<link href='<c:url value="/etc/css/2020/bootstrap.min.css"/>' rel="stylesheet" type="text/css">
		<link href='<c:url value="/etc/css/2020/fontawesome/css/all.min.css"/>' rel="stylesheet" type="text/css">
		<link href='<c:url value="/etc/css/2020/apoyo_escolar_2020.css"/>' rel="stylesheet" type="text/css">
		<script languaje='javaScript'>
			<!--
			function cargar(forma){
				cargarImagenEstablecimiento();
				mensaje(document.getElementById("msg"));
			}
			
			function cargarImagenEstablecimiento() {
				var nombreArchivo = Math.floor(Math.random() * 6);
				var rutaImagen = '<c:url value="/etc/img/establecimientos/' + nombreArchivo + '.jpg"/>';
				document.getElementById("imagenEstablecimiento2").src = rutaImagen;
				
		    }
			
			function hacerValidaciones_frmLogin(forma){
				if(forma.perfilPedido){
						forma.key.value='1';
						validarSeleccion(forma.perfilPedido,"- Perfil de acceso");
					}
				<c:if test="${requestScope.multiple2!=null || requestScope.multiple4!=null}">
					forma.key.value='2';
					validarLista(forma.inst,"- Colegio",1)
					validarLista(forma.sede,"- Sede",1)
					validarLista(forma.jornada,"- Jornada",1)
					forma.inst_.value=forma.inst.options[forma.inst.selectedIndex].text;
					forma.sede_.value=forma.sede.options[forma.sede.selectedIndex].text;
					forma.jornada_.value=forma.jornada.options[forma.jornada.selectedIndex].text;
				</c:if>
				<c:if test="${requestScope.multiple3!=null}">
					forma.key.value='3';
					validarLista(forma.sede,"- Sede",1)
					validarLista(forma.jornada,"- Jornada",1)
					forma.sede_.value=forma.sede.options[forma.sede.selectedIndex].text;
					forma.jornada_.value=forma.jornada.options[forma.jornada.selectedIndex].text;
				</c:if>
			}

			function colocarVariable(){
				document.frmLogin.cambio.value='1';
			}
			
			//funciones de filtro
			function Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre){
				this.id_Hijos= id_Hijos; this.Hijos= Hijos; this.Sel_Hijos= Sel_Hijos; this.id_Padre=id_Padre;
			}
			
			function borrar_combo(combo){
				for(var i = combo.length - 1; i >= 0; i--) {
					if(navigator.appName == "Netscape") combo.options[i] = null;
						else combo.remove(i);
				}
				combo.options[0] = new Option("-- Seleccione uno --","-1");
			}
			
			<c:if test="${requestScope.multiple2!=null || requestScope.multiple4!=null}">
				function filtro2(combo_padre,combo_hijo,combo_hijo2){
					borrar_combo(combo_hijo);borrar_combo(combo_hijo2);
					<c:if test="${!empty requestScope.LoginInst && !empty requestScope.LoginSede}">var Padres = new Array();
						<c:forEach begin="0" items="${requestScope.LoginInst}" var="fila" varStatus="st">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
						<c:forEach begin="0" items="${requestScope.LoginSede}" var="fila2">
							<c:if test="${fila[0]==fila2[2]}">Sel_Hijos[k] = ''; id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k] = '<c:out value="${fila2[2]}"/>'; k++; </c:if>
							</c:forEach>Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
						var niv=combo_padre.options[combo_padre.selectedIndex].value;
						var val_padre = -1;
						for(var k=0;k<Padres.length;k++){
							if(Padres[k].id_Padre[0]==niv)
								val_padre=k;
						}
						if(val_padre!=-1){
							var no_hijos = Padres[val_padre].Hijos.length;
							for(i=0; i < no_hijos; i++){
								if(Padres[val_padre].Sel_Hijos[i] == "SELECTED"){
									combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED"); combo_hijo.selectedIndex = i+1;
								}else
									combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
							}
					}</c:if>
				}
				
				function filtro(combo_padre0,combo_padre,combo_hijo){
					borrar_combo(combo_hijo);
					<c:if test="${!empty requestScope.LoginSede && !empty requestScope.LoginJor}">var Padres = new Array();
						<c:forEach begin="0" items="${requestScope.LoginSede}" var="fila" varStatus="st">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
							<c:forEach begin="0" items="${requestScope.LoginJor}" var="fila2">
							<c:if test="${fila[0]==fila2[2] && fila[2]==fila2[3]}">Sel_Hijos[k] = '';id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k]='<c:out value="${fila[2]}"/>|<c:out value="${fila[0]}"/>'; k++; </c:if>
							</c:forEach>Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
						var niv=combo_padre0.options[combo_padre0.selectedIndex].value +'|'+ combo_padre.options[combo_padre.selectedIndex].value;
						var val_padre = -1;
						for(var k=0;k<Padres.length;k++){
							if(Padres[k].id_Padre[0]==niv)
								val_padre=k;
						}
						if(val_padre!=-1){
							var no_hijos = Padres[val_padre].Hijos.length;
							for(i=0; i < no_hijos; i++){
								if(Padres[val_padre].Sel_Hijos[i] == "SELECTED"){
									combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED"); combo_hijo.selectedIndex = i+1;
								}else
									combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
							}
					}</c:if>
				}
			</c:if>
			
			<c:if test="${requestScope.multiple3!=null}">
				function filtro(combo_padre,combo_hijo){
					borrar_combo(combo_hijo);
					<c:if test="${!empty requestScope.LoginSede && !empty requestScope.LoginJor}">var Padres = new Array();
						<c:forEach begin="0" items="${requestScope.LoginSede}" var="fila" varStatus="st">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
							<c:forEach begin="0" items="${requestScope.LoginJor}" var="fila2">
							<c:if test="${fila[0]==fila2[2]}">Sel_Hijos[k] = '';id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k]='<c:out value="${fila[0]}"/>'; k++; </c:if>
							</c:forEach>Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
						var niv=combo_padre.options[combo_padre.selectedIndex].value;
						var val_padre = -1;
						for(var k=0;k<Padres.length;k++){
							if(Padres[k].id_Padre[0]==niv)
								val_padre=k;
						}
						if(val_padre!=-1){
							var no_hijos = Padres[val_padre].Hijos.length;
							for(i=0; i < no_hijos; i++){
								if(Padres[val_padre].Sel_Hijos[i] == "SELECTED"){
									combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED"); combo_hijo.selectedIndex = i+1;
								}else
									combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
							}
					}</c:if>
				}
			</c:if>	

			function salir(){
				top.location.href='<c:url value="/Inicio.dos"/>';
			}
			-->
		</script>
		
	</head>
	
	<body onLoad='cargar(document.frmLogin)'>
	
		<div class="container-fluid container-start">
		
			<div class="header">
				<img src='<c:url value="/etc/img/2020/header.png"/>' alt="Sistema de Apoyo Escolar">
			</div>
			
			<div class="container-fluid container-central">
			
				<div class="container container-card">
				
					<div class="row row-central shadow">
					
						<form action='<c:url value="/Inicio.dos"/>' method="POST" name='frmLogin' onSubmit="return validarForma(frmLogin)" class="col-content row">
						
							<input type='hidden' name='login' value='<c:out value="${requestScope.log}"/>'>
							<input type="hidden" name="password" value='<c:out value="${requestScope.pass}"/>'>
							<input type="hidden" name="ext" value="1">
							<input type="hidden" name="key" value="0">
							<input type="hidden" name="cambio" value="">
							<input type="hidden" name="inst_">
							<input type="hidden" name="sede_">
							<input type="hidden" name="jornada_">
							
							<div class="col-lg-12">
								<%@include file="mensaje.jsp"%>
							</div>
							
							<div class="col-lg-4 imagen-form" style="align-self: center">
								<img id="imagenEstablecimiento2" src="" alt="Bienvenido">
							</div>
							
							<div class="col-lg-5">
							
								<!-- 1 -->
								<c:if test="${!empty requestScope.multiple}">
									<div class="alert alert-primary">Seleccione el acceso por el cual va a trabajar</div>
									<div class="pb-3" style="font-size: 10px">
										<c:forEach begin="0" items="${requestScope.multiple}" var="fila">
											<input type='radio' name='perfilPedido' value='<c:out value="${fila[0]}"/>|<c:out value="${fila[1]}"/>'>
											<c:out value="${fila[7]}"/>
											<%-- <c:out value="${fila[2]}"/> --%>
											<c:out value="${fila[3]}"/>
											<c:out value="${fila[4]}"/>
											<c:out value="${fila[5]}"/>
											<c:out value="${fila[6]}"/>
											<br>
											<c:out value="-------------------------------------------------------------------------------------"/>
											<br>
										</c:forEach>
										
									</div>
								</c:if>
								
								<!-- 2 -->
								<c:if test="${requestScope.multiple2!=null || requestScope.multiple4!=null}">
									<div class="alert alert-primary">Seleccione el colegio, la sede y la jornada en la cual va a trabajar</div>
									<div class="pb-3">
										<h6>Colegio:</h6>
										<select name="inst" onChange='filtro2(document.frmLogin.inst,document.frmLogin.sede,document.frmLogin.jornada)' class="custom-select">
											<option value='-1'>-- Seleccione uno --</option>
											<c:forEach begin="0" items="${requestScope.LoginInst}" var="fila">
												<option value='<c:out value="${fila[0]}"/>'><c:out value="${fila[1]}"/></option>
											</c:forEach>
										</select>
									</div>
									<div class="pb-3">
										<h6>Sede:</h6>
										<select name="sede" onChange='filtro(document.frmLogin.inst,document.frmLogin.sede,document.frmLogin.jornada)'  class="custom-select">
											<option value='-1'>-- Seleccione uno --</option>
										</select>
									</div>
									<div class="pb-3">
										<h6>Jornada:</h6>
										<select name="jornada" class="custom-select">
											<option value='-1'>-- Seleccione uno --</option>
										</select>
									</div>
								</c:if>
								
								<!-- 3 -->
								<c:if test="${requestScope.multiple3!=null}">
									<div class="alert alert-primary">Seleccione la sede y la jornada en la cual va a trabajar</div>
									<div class="pb-3">
										<h6>Sede:</h6>
										<select name="sede" onChange='filtro(document.frmLogin.sede,document.frmLogin.jornada)' class="custom-select">
											<option value='-1'>-- Seleccione uno --</option>
											<c:forEach begin="0" items="${requestScope.LoginSede}" var="fila">
												<option value='<c:out value="${fila[0]}"/>'><c:out value="${fila[1]}"/></option>
											</c:forEach>
										</select>
									</div>
									<div class="pb-3">
										<h6>Jornada:</h6>
										<select name="jornada" class="custom-select">
											<option value='-1'>-- Seleccione uno --</option>
										</select>
									</div>
								</c:if>
									
							</div>
							
							<div class="col-lg-3" style="align-self: center">
								<div class="input-group">
									<button type="submit" class="btn btn-block login-btn" name="cmd1" id="cmd1">ENTRAR</button>
								</div>
								<div class="input-group">
									<button type="submit" class="btn btn-block btn-primary" name="cmd2" id="cmd2" onClick="colocarVariable()">CAMBIAR CONTRASEÑA</button>
								</div>
								<div class="input-group">
									<input type="button" name="cmd3" id="cmd3" class="btn btn-primary btn-block" value="INICIO" onclick="salir();">
								</div>
							</div>
							
						</form>
						
					</div>
					
				</div>
				
			</div>
			
			<div class="container-fluid footer">
				<div class="row">
					<div class="col-lg-4 col-md-5">
						<ul class="info">
							<li>
								Secretaría de Educación del Distrito
							</li>
							<li>
								Avenida El Dorado No. 66 - 63
							</li>
							<li>
								Teléfono (57+1) 324 10 00
							</li>
							<li>
								Bogotá DC - Colombia
							</li>
						</ul>
					</div>
					<div class="col-lg-4 col-md-4 text-center">
						<h4 class="text-break">
							<a href="http://www.educacionbogota.edu.co" target="_blank">www.educacionbogota.edu.co</a>
						</h4>
						<ul class="social">
							<li>
								<a href="#" title="Siguenos en Twitter">
									<img src='<c:url value="/etc/img/2020/icon-twt.png"/>' alt="">
								</a>
							</li>
							<li>
								<a href="#" title="Siguenos en FaceBook">
									<img src='<c:url value="/etc/img/2020/icon-fb.png"/>' alt="">
								</a>
							</li>
							<li>
								<a href="#" title="Siguenos en Youtube">
									<img src='<c:url value="/etc/img/2020/icon-yt.png"/>' alt="">
								</a>
							</li>
							<li>
								<a href="#" title="Siguenos en Instagram">
									<img src='<c:url value="/etc/img/2020/icon-inst.png"/>' alt="">
								</a>
							</li>
						</ul>
					</div>
					<div class="col-lg-4 col-md-3 text-center">
						<%-- <img src='<c:url value="/etc/img/2020/logo-footer.png"/>' alt="La educación en primer lugar" class="footer-logo"> --%>
					</div>
				</div>
			</div>
		</div>
	</body>
	
	<c:if test="${requestScope.multiple2!=null || requestScope.multiple4!=null}">
		<script>document.frmLogin.inst.onchange();document.frmLogin.sede.onchange();</script>
	</c:if>
	
	<c:if test="${requestScope.multiple3!=null}">
		<script>document.frmLogin.sede.onchange();</script>
	</c:if>
	
</html>
