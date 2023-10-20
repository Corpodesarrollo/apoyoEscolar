<!--	VERSION		FECHA			AUTOR				DESCRIPCION
			1.0		23/02/2021		JORGE CAMACHO		CODIGO ESPAGUETI Y SE MOFIFICÓ EL DISEÑO DE LA PÁGINA
														CON LAS NUEVAS IMÁGENES Y QUE FUERA RESPONSIVE
-->

<%@include file="parametros.jsp"%>

<link href='<c:url value="/etc/css/2020/bootstrap.min.css"/>' rel="stylesheet" type="text/css">
<link href='<c:url value="/etc/css/2020/apoyo_escolar_2020.css"/>' rel="stylesheet" type="text/css">

<div class="user_data_in">
	<p>
		<strong><c:out value="${sessionScope.login.nomPerfil}"/></strong>
		<br>
		<c:choose>
			<c:when test="${sessionScope.login.nivel==2}">
				<c:out value="${sessionScope.login.mun}"/>
			</c:when>
			<c:when test="${sessionScope.login.nivel==3}">
				<c:out value="${sessionScope.login.loc}"/>
			</c:when>
			<c:when test="${sessionScope.login.nivel==4}">
				<c:out value="${sessionScope.login.usuario}"/>
			</c:when>
			<c:when test="${sessionScope.login.nivel==6}">
				<c:out value="${sessionScope.login.usuario}"/>
			</c:when>
			<c:otherwise>Administrador SEC</c:otherwise>
		</c:choose>
	</p>
</div>