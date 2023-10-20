<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="asignaturaVO" class="articulacion.asignatura.vo.AsignaturaVO" scope="session"/>
<jsp:setProperty name="asignaturaVO" property="*"/>
<c:import url="/articulacion/asignatura/Nuevo.do"/>