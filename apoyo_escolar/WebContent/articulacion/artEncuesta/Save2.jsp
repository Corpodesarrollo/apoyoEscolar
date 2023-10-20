<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="encuesta2VO" class="articulacion.artEncuesta.vo.Encuesta2VO" scope="session"/>
<jsp:setProperty name="encuesta2VO" property="*"/>
<c:import url="/articulacion/artEncuesta/Filtro.do"/>