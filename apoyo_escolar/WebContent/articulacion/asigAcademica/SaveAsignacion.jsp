<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="asignacionVO" class="articulacion.asigAcademica.vo.AsignacionVO" scope="session"/>
<jsp:useBean id="filAsigAcaVO" class="articulacion.asigAcademica.vo.DatosVO" scope="session"/>
<jsp:setProperty name="asignacionVO" property="*"/>
<jsp:setProperty name="filAsigAcaVO" property="*"/>
<c:import url="/articulacion/asigAcademica/Nuevo.do"/>