<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="artRotEstructuraVO" class="articulacion.artRotacion.vo.EstructuraVO" scope="session"/>
<jsp:setProperty name="artRotEstructuraVO" property="*"/>
<jsp:useBean id="artRotFiltroEstructuraVO" class="articulacion.artRotacion.vo.FiltroEstructuraVO" scope="session"/>
<jsp:setProperty name="artRotFiltroEstructuraVO" property="*"/>
<c:import url="/articulacion/artRotacion/Nuevo.do"/>