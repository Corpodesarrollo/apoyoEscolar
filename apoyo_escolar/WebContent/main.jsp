<!--	VERSION		FECHA			AUTOR				DESCRIPCION
			1.0		23/02/2021		JORGE CAMACHO		CODIGO ESPAGUETI Y SE MOFIFICÓ EL DISEÑO DE LA PÁGINA
														CON LAS NUEVAS IMÁGENES Y QUE FUERA RESPONSIVE
			2.0		02/06/2021		JORGE CAMACHO		SE CAMBIA LA CODIFICACIÓN DE LA PAGINA DE UTF-8 A windows-1252
-->

<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage="" %>

<!DOCTYPE html>

<html>

<head>

	<meta charset="windows-1252">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<%@include file="parametros.jsp"%>
	<title>SISTEMA DE APOYO ESCOLAR - SECRETARIA DE EDUCACIÓN DE BOGOTÁ -</title>
	<link href='<c:url value="/etc/css/2020/bootstrap.min.css"/>' rel="stylesheet" type="text/css">
	<link href='<c:url value="/etc/css/2020/fontawesome/css/all.min.css"/>' rel="stylesheet" type="text/css">
	<link href='<c:url value="/etc/css/2020/apoyo_escolar_2020.css"/>' rel="stylesheet" type="text/css">
	<script language=javascript src='<c:url value="/etc/js/2020/jquery-3.5.1.min.js"/>'></script>
	<script>
        $( document ).ready(function() {
            if ($(window).width() < 767) {
                $(".lateral-navigation").addClass("hidding");
                $(".btn_lateral-navigation .fas").removeClass('fa-arrow-left').addClass('fa-bars');
            };

            $(".btn_lateral-navigation").on("click", function(){
                $(".lateral-navigation").toggleClass("hidding");
                if($(".btn_lateral-navigation .fas").hasClass('fa-arrow-left')){
                    $(".btn_lateral-navigation .fas").removeClass('fa-arrow-left').addClass('fa-bars');
                } else {
                    $(".btn_lateral-navigation .fas").removeClass('fa-bars').addClass('fa-arrow-left');
                }
            });
        });
	</script>
</head>
<body>
	<div class="container-fluid container-start">
		<div class="header">
			<img src='<c:url value="/etc/img/2020/header.png"/>' alt="Sistema de Apoyo Escolar">
		</div>

		<div class="container-fluid container-central container-general">
			<div class="lateral-navigation">
				<div class="btn_lateral-navigation">
					<i class="fas fa-arrow-left"></i>
				</div>
				<div class="nav_user">
					<iframe name="user" id="user" src='<c:url value="/user.jsp?ext=1"/>' border="0" noresize scrolling="no"  frameborder='no'  marginheight='0' marginwidth='0'></iframe>
				</div>
				<div class="nav_menu">
					<iframe name="menu" id="menu" src='<c:url value="/ControllerMenu.do?ext=1"/>' border="0" noresize scrolling="yes"  frameborder='no'  marginheight='0' marginwidth='0'></iframe>
				</div>
			</div>
			<div class="central-navigation">
				<iframe name="ubicacion" id="ubicacion" src='<c:url value="/ubicacion.jsp?ext=1"/>' border="0" noresize scrolling="no"  frameborder='no'  marginheight='0' marginwidth='0'></iframe>
				<c:if test="${requestScope.serv!=null}">
				<iframe name="centro" id="centro" src='<c:url value="${requestScope.serv}?ext=1&redireccion=2"/>' border="0" frameborder='no'  marginheight='0' marginwidth='0' noresize></iframe>
				</c:if>
				<c:if test="${requestScope.serv==null}">
				<iframe name="centro" id="centro" src='<c:url value="/bienvenida.do?ext=1&redireccion=2"/>' border="0" frameborder='no'  marginheight='0' marginwidth='0' noresize ></iframe>
				</c:if>
			</div>
		</div>
	</div>
</body>
</html>