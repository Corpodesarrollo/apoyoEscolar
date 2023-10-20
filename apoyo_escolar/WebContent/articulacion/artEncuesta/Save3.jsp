<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="encuesta3VO" class="articulacion.artEncuesta.vo.Encuesta3VO" scope="session"/>
<jsp:setProperty name="encuesta3VO" property="*"/>
<c:import url="/articulacion/artEncuesta/Filtro.do"/>