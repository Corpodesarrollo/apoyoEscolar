<!--	VERSION		FECHA			AUTOR				DESCRIPCION
			1.0		07/05/2018		JORGE CAMACHO		CODIGO ESPAGUETI Y SE AGREGÓ EL LINK DE OLVIDO DE CONTRASEÑA
			2.0		23/02/2021		JORGE CAMACHO		SE MOFIFICÓ EL DISEÑO DE LA PÁGINA CON LAS NUEVAS IMÁGENES
														Y QUE FUERA RESPONSIVE
			2.0		02/06/2021		JORGE CAMACHO		SE CAMBIA LA CODIFICACIÓN DE LA PAGINA DE UTF-8 A windows-1252
-->

<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage="" %>

<%@ page session="false" %>

<!DOCTYPE html>
<html lang="es-CO">

	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	
	<!--
		VALORES DE BANDERA 0=ACTIVO SIEMPRE 1=INACTIVO SIEMPRE 2=RANGO DE HORA EN QUE SE DESACTIVA
		VALORES DE Hinicio Hora militar entre 0 y 23
		VALORES DE Hfin Hora militar entre 0 y 23
	-->

	<jsp:useBean id="now" class="java.util.Date"/>
	<fmt:formatDate value="${now}" type="time" pattern="H" scope="page" var="hora"/>
	<c:set var="bandera" value="0" scope="page"/>
	<c:set var="Hinicio" value="22" scope="page"/>
	<c:set var="Hfin" value="6" scope="page"/>

	<head>
		<title>SISTEMA DE APOYO ESCOLAR -Login de acceso-</title>
		<meta charset="windows-1252">
    	<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<script language=javascript src='<c:url value="/etc/js/validar.js"/>'></script>
		<link href='<c:url value="/etc/css/2020/bootstrap.min.css"/>' rel="stylesheet" type="text/css">
		<link href='<c:url value="/etc/css/2020/fontawesome/css/all.min.css"/>' rel="stylesheet" type="text/css">
		<link href='<c:url value="/etc/css/2020/apoyo_escolar_2020.css"/>' rel="stylesheet" type="text/css">
		<script languaje='javaScript'>
			<!--
			
				if(self.parent.frames.length > 0) {
					top.location.href = '<c:url value="/login.jsp"/>';
					top.alert('La sesión se ha terminado. Ingrese de nuevo al sistema');
			 	}
	
				function hacerValidaciones_frmLogin(forma) {
			  		if(forma.perfilPedido) {
						forma.key.value = '1';
						validarSeleccion(forma.perfilPedido, "- Perfil de acceso");
					}
					
					validarCampo(forma.login, "- Login de usuario", 1, 15);
					validarCampo(forma.password, "- Password ", 1, 15);
					
					<c:if test="${requestScope.multiple2!=null}">
						forma.key.value = '2';
						validarLista(forma.inst, "- Colegio", 1);
						validarLista(forma.sede, "- Sede", 1);
						validarLista(forma.jornada, "- Jornada", 1);
						
						forma.inst_.value = forma.inst.options[forma.inst.selectedIndex].text;
						forma.sede_.value = forma.sede.options[forma.sede.selectedIndex].text;
						forma.jornada_.value = forma.jornada.options[forma.jornada.selectedIndex].text;
					</c:if>
				}
				
				function cargar(forma) {
					cargarImagenEstablecimiento();
					if(forma.login && forma.login.readOnly==false)
						forma.login.focus();
					
					mensaje(document.getElementById("msg"));
				}
				
				function inhabilitar() {
					document.frmLogin.login.readOnly = true;
					document.frmLogin.password.readOnly = true;
				}
				
				function colocarVariable() {
					document.frmLogin.cambio.value = '1';
				}
				
				function superfil() {
					document.frmLogin.key.value = '-99';
					document.frmLogin.submit();
				}
			
			  	function PadrePrfil() {
					document.frmLogin.key.value = '-999';
					document.frmLogin.submit();
				}
				
				function olvidoContrasena() {
					document.frmLogin.key.value = '12';
					document.frmLogin.submit();
				}
				
				function irFamilia() {
					if(window.location.search.includes('flia')){
						PadrePrfil();
					}
					if(window.location.search.includes('pass')){
						olvidoContrasena();
					}
				}
				
				function cargarImagenEstablecimiento() {
					var nombreArchivo = Math.floor(Math.random() * 6);
					var rutaImagen = '<c:url value="/etc/img/establecimientos/' + nombreArchivo + '.jpg"/>';
					document.getElementById("imagenEstablecimiento").src = rutaImagen;
					
			    }
				
				//funciones de filtro
				<c:if test="${requestScope.multiple2!=null}">
				
					function Padre(id_Hijos, Hijos, Sel_Hijos, id_Padre) {
						this.id_Hijos =  id_Hijos;
						this.Hijos = Hijos;
						this.Sel_Hijos = Sel_Hijos;
						this.id_Padre = id_Padre;
					}
					
					function borrar_combo(combo) {
						for(var i = combo.length - 1; i >= 0; i--) {
							if(navigator.appName == "Netscape")
								combo.options[i] = null;
							else
								combo.remove(i);
						}
						combo.options[0] = new Option("-- Seleccione uno --","-1");
					}
					
					function filtro2(combo_padre, combo_hijo, combo_hijo2) {
						borrar_combo(combo_hijo);
						borrar_combo(combo_hijo2);
						
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
							}
						</c:if>
					}
					
					function filtro(combo_padre0, combo_padre, combo_hijo) {
						borrar_combo(combo_hijo);
						
						<c:if test="${!empty requestScope.LoginSede && !empty requestScope.LoginJor}">var Padres = new Array();
							<c:forEach begin="0" items="${requestScope.LoginSede}" var="fila" varStatus="st">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
								<c:forEach begin="0" items="${requestScope.LoginJor}" var="fila2">
								<c:if test="${fila[0]==fila2[2] && fila[2]==fila2[3]}">Sel_Hijos[k] = '';id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k]='<c:out value="${fila[2]}"/>|<c:out value="${fila[0]}"/>'; k++; </c:if>
								</c:forEach>Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
							var niv=combo_padre0.options[combo_padre0.selectedIndex].value +'|'+ combo_padre.options[combo_padre.selectedIndex].value;
							var val_padre = -1;
							for(var k=0;k<Padres.length;k++) {
								if(Padres[k].id_Padre[0]==niv)
									val_padre=k;
							}
							if(val_padre!=-1) {
								var no_hijos = Padres[val_padre].Hijos.length;
								for(i=0; i < no_hijos; i++){
									if(Padres[val_padre].Sel_Hijos[i] == "SELECTED"){
										combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED"); combo_hijo.selectedIndex = i+1;
									}else
										combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
								}
							}
						</c:if>
					}
				</c:if>	
			-->
		</script>
	
	</head>

	<body onLoad='cargar(document.frmLogin)'>
	
		<form action='<c:url value="/Inicio.dos"/>' method="POST" name='frmLogin' onSubmit="return validarForma(frmLogin)">
		
			<input type="hidden" name="ext" value="1">
			<input type="hidden" name="key" value="0">
			<input type="hidden" name="cambio" value="">
			<input type="hidden" name="inst_">
			<input type="hidden" name="sede_">
			<input type="hidden" name="jornada_">
			<input type="hidden" name="imagenEstablacimiento">
			
			<div class="container-fluid container-start">
			
				<div class="header">
					<img src='<c:url value="/etc/img/2020/header.png"/>' alt="Sistema de Apoyo Escolar">
				</div>
				
				<div class="container-fluid container-central">
					<div class="container container-card card-login">
						<div class="row row-central shadow">
							<div class="col-lg-6 col-content">
								<h2 class="login-title">Bienvenido</h2>
								<div class="input-group">
									<div class="input-group-prepend">
										<span class="input-group-text" id="basic-addon1">
											<i class="fas fa-user"></i>
										</span>
									</div>
									<input type="text" name="login" id="login" placeholder="Usuario" maxlength="22" size="21" class="form-control">
								</div>
								<div class="input-group">
									<div class="input-group-prepend">
										<span class="input-group-text" id="basic-addon1">
											<i class="fas fa-lock"></i>
										</span>
									</div>
									<input type="password" name="password" id="password" placeholder="Contraseña" maxlength="12" size="21" class="form-control">
								</div>
								<div class="input-group">
									<a href="javaScript:olvidoContrasena()" class="w-100 text-right"><i class="fas fa-key"></i> Olvidó la contraseña?</a>
								</div>
								<div class="input-group">
									<input type="submit" name="cmd1" id="cmd1" class="btn login-btn" value="Iniciar Sesión"/>
								</div>
								<div class="input-group">
									<!-- <a href="javaScript:superfil()"><i class="fas fa-chalkboard-teacher"></i> Formulario para Inscripción de Docentes a Cursos de Informática Educativa</a> -->
									<a href="javaScript:PadrePrfil()"><i class="fas fa-user-graduate"></i> Consulta de boletines</a>
								</div>
							</div>
							<div class="col-lg-6 col-content">
								<img id="imagenEstablecimiento" src="" alt="Bienvenido">
								<!-- <img src="https://picsum.photos/490/395" alt="Bienvenido"> -->
								<div class="hidden_data">
									Nodo Remoto: <%=request.getRemoteHost() %>
									Nodo Local: <%=request.getLocalAddr() %>
								</div>
							</div>
							<%@ include file="mensaje.jsp"%>
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
			
		</form>
				
	</body>
	
</html>

<script>irFamilia();</script>

<c:if test="${requestScope.multiple2!=null}">
	<script>document.frmLogin.inst.onchange();document.frmLogin.sede.onchange();inhabilitar();</script>
</c:if>
