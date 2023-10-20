<!--	VERSION		FECHA			AUTOR				DESCRIPCION
			1.0		23/02/2021		JORGE CAMACHO		CODIGO ESPAGUETI Y SE MOFIFICÓ EL DISEÑO DE LA PÁGINA
														CON LAS NUEVAS IMÁGENES Y QUE FUERA RESPONSIVE
			2.0		02/06/2021		JORGE CAMACHO		SE CAMBIA LA CODIFICACIÓN DE LA PAGINA DE UTF-8 A windows-1252
-->

<%@ page contentType="text/html; charset=windows-1252" language="java"%>

<head>
	<%@include file="parametros.jsp"%>
	<meta charset="windows-1252">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link href='<c:url value="/etc/css/2020/bootstrap.min.css"/>' rel="stylesheet" type="text/css">
	<link href='<c:url value="/etc/css/2020/fontawesome/css/all.min.css"/>' rel="stylesheet" type="text/css">
	<link href='<c:url value="/etc/css/2020/apoyo_escolar_2020.css"/>' rel="stylesheet" type="text/css">
	<script language=javascript src='<c:url value="/etc/js/2020/jquery-3.5.1.min.js"/>'></script>
	<script>
		var targ = new Array();
		targ[0]='_self';
		targ[1]='centro';
		targ[2]='_top';
		targ[3]='_blank';
		var opc = new Array();var i=0;
		<c:forEach begin="0" items="${sessionScope.mnucat}" var="fila">opc[i++]=<c:out value="${fila[0]}"/>;</c:forEach>
	</script>
	<script>
		function inhabilitarHidden(){
			for(var i=0;i<document.elfo.elements.length;i++){
				if(document.elfo.elements[i].type == "hidden" && (document.elfo.elements[i].name == "ext" || document.elfo.elements[i].name == "ext2" || document.elfo.elements[i].name == "servTarget"))
					document.elfo.elements[i].value='';
			}	
		}

		function ir(n){
			document.elfo.catsolicitado.value=n;
			for(i=0;i<opc.length;i++){
				if(opc[i]==n){
					if(document.getElementById("t"+n)) document.getElementById("t"+n).style.display='';
				}else{
					if(document.getElementById("t"+opc[i])) document.getElementById("t"+opc[i]).style.display='none';
				}
			}
		}

		<c:if test="${sessionScope.login.perfil!=666}">
			<c:forEach begin="0" items="${sessionScope.mnuPrivado}" var="fila">
				function c_<c:out value="${fila[3]}"/>(){
						inhabilitarHidden();
						<c:forEach begin="0" items="${sessionScope.menuParam}" var="fila2">
							<c:if test="${fila2[0]==fila[3]}">
								if(document.elfo.<c:out value="${fila2[1]}"/>.length){
									for(var i=0;i<document.elfo.<c:out value="${fila2[1]}"/>.length;i++){
											document.elfo.<c:out value="${fila2[1]}"/>[i].value='<c:out value="${fila2[2]}"/>';
										}
								} else{ 
									document.elfo.<c:out value="${fila2[1]}"/>.value='<c:out value="${fila2[2]}"/>'; 
								}
							</c:if>
						</c:forEach>
						console.log("<c:out value="${fila[0]}"/>");
						var url = '<c:out value="${fila[0]}"/>';
						if(url.includes("apex")){
							console.log("entro aca");
							var a = document.createElement('a');
							a.href = url;
							a.target = "_blank";
							a.click();
						}else if(url.includes("ApoyoEscolarBE")){
							console.log("entro Apoyo Escolar BE");
							var a = document.createElement('a');
							a.href = url;
							a.target = targ[<c:out value="${fila[1]}"/>];
							a.click();
						} else{
							document.elfo.servTarget.value='<c:out value="${fila[1]}"/>';
							document.elfo.action = '<c:out value="${fila[0]}"/>';								
							document.elfo.servsolicitado.value='<c:out value="${fila[3]}"/>';
							document.elfo.target=targ[<c:out value="${fila[1]}"/>];
							document.elfo.submit();
						}
					}
			</c:forEach>
			
			<c:forEach begin="0" items="${sessionScope.mnuPublico}" var="fila">
			function c_<c:out value="${fila[3]}"/>(){
				inhabilitarHidden();
				<c:forEach begin="0" items="${sessionScope.menuParam}" var="fila2">
					<c:if test="${fila2[0]==fila[3]}">
						if(document.elfo.<c:out value="${fila2[1]}"/>.length){
							for(var i=0;i<document.elfo.<c:out value="${fila2[1]}"/>.length;i++){
								document.elfo.<c:out value="${fila2[1]}"/>[i].value='<c:out value="${fila2[2]}"/>';
								}
						} else{ 
							document.elfo.<c:out value="${fila2[1]}"/>.value='<c:out value="${fila2[2]}"/>'; 
						}
					</c:if>
				</c:forEach>
				document.elfo.servTarget.value='<c:out value="${fila[1]}"/>'; 
				document.elfo.action = '<c:out value="${fila[0]}"/>';
				document.elfo.servsolicitado.value='<c:out value="${fila[3]}"/>';
				document.elfo.target=targ[<c:out value="${fila[1]}"/>];
				document.elfo.submit(); 
			}	
			</c:forEach>

		</c:if>

		<c:if test="${sessionScope.login.perfil==666}">
			function c_2(){inhabilitarHidden();if(document.elfo.ext.length){for(var i=0;i<document.elfo.ext.length;i++){document.elfo.ext[i].value='1';}}else{ document.elfo.ext.value='1'; }
			document.elfo.servTarget.value='2'; document.elfo.action='<c:url value="/Inicio.dos"/>';document.elfo.servsolicitado.value='2'; document.elfo.target=targ[2]; document.elfo.submit(); }	
		</c:if>
	</script>
	<script>
		$(document).ready(function(){
			$('.menu-top h3').on('click', function(){
                $('.menu-top h3').removeClass('active');
                $(".menu-top ul").slideUp().removeClass('active');
                if(!$(this).next().is(":visible")){
                    $(this).next().slideDown().addClass('active');
                    $(this).addClass('active');
                }
			});

			$('ul li span').on('click', function(){
				$('ul li span').removeClass('active');
				$(this).addClass('active');
			});
		});
	</script>
</head>
<body class="menu-iframe">

	<c:set var="cont" value="${0}"/>
	<div class="hidden_data">
		Nodo Remoto: <%=request.getRemoteHost() %>
		Nodo Local: <%=request.getLocalAddr() %>
	</div>

	<div class="menu-principal">
		<div class="version">
			<a href="https://educacionbogota-my.sharepoint.com/personal/micuellar_educacionbogota_gov_co/_layouts/15/guestaccess.aspx?e=4%3ais7Ctk&at=9&CT=1562704901140&OR=OWA-NT&CID=130c09f2-cc82-4d20-901f-574b6ebea8f1&share=EadJEqCXN_FJqmkc3pgHC54BDkRapdRQqVNdSNgCec88Jg" target="_brank">5.12.0</a>
		</div>

		<form name='elfo' method='post'>
			<c:forEach begin="0" items="${sessionScope.menuParam}" var="fila2">
				<input type='hidden' name='<c:out value="${fila2[1]}"/>'>
			</c:forEach>
			<input type='hidden' name='servsolicitado'>
			<input type='hidden' name='catsolicitado' value='<c:out value="${sessionScope.categoriasolicitado}"/>'>
			<input type='hidden' name='servTarget'>
		</form>

		<c:if test="${sessionScope.login.perfil!=666}">
			<c:forEach begin="0" items="${sessionScope.mnucat}" var="fila" varStatus="n">
				<ul class="menu-top">
					<li>
						<h3 class="menu-link">
							<i class="fas fa-angle-down"></i> <c:out value="${fila[1]}"/>
						</h3>
						<ul id='t<c:out value="${fila[0]}"/>' name='t<c:out value="${fila[0]}"/>' class="sub-menu">
							<c:forEach begin="0" items="${sessionScope.mnuPrivado}" var="fila2" varStatus="m">
								<c:if test="${fila[0]==fila2[4]}">
									<li id='tr<c:out value="${cont}"/>'>
										<span onclick='javaScript:c_<c:out value="${fila2[3]}"/>()'>
											<c:out value="${fila2[2]}" />
										</span>
									</li>
									<c:set var="cont" value="${cont+1}" />
								</c:if>
							</c:forEach>
						</ul>
					</li>
				</ul>
			</c:forEach> 

			<ul class="menu-user">
				<c:forEach begin="0" items="${sessionScope.mnuPublico}" var="fila" varStatus="n">
					<li id='tr<c:out value="${cont}"/>'>
						<span onclick='javaScript:c_<c:out value="${fila[3]}"/>()'>
							<i class="fas fa-angle-right"></i> <c:out value="${fila[2]}"/>
						</span>
					</li>
					<c:set var="cont" value="${cont+1}" />
				</c:forEach>
			</ul>
		</c:if>

		<c:if test="${sessionScope.login.perfil==666}">
			<ul class="menu-user">
				<li id='tr<c:out value="${cont}"/>'>
					<span onclick='javaScript:c_2()' class='active'>
						<span>Cerrar Sesión</span>
					</span>
				</li>
			</ul>
		</c:if>

		<div class="logo-menu">
			<c:choose>
				<c:when test="${sessionScope.imagenEscudoBase64==null}">
					<img name="titulo_r2_c11"  src='<c:url value="/etc/img/2020/escudo.png"/>' height="auto">
				</c:when>
				<c:when test="${sessionScope.imagenEscudoBase64!=null}">
					<img src="data:image/png;base64, ${sessionScope.imagenEscudoBase64}" height="auto">
				</c:when>
			</c:choose>
		</div>

	</div>
</body>