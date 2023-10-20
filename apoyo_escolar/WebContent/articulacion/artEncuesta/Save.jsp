<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="encuestaVO" class="articulacion.artEncuesta.vo.EncuestaVO" scope="session"/>
<jsp:setProperty name="encuestaVO" property="*"/>
<c:import url="/articulacion/artEncuesta/Filtro.do"/>