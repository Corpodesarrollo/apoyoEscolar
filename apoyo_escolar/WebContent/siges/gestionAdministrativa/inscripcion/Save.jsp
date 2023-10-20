<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="paramsVO" class="articulacion.inscripcion.vo.ParamsVO" scope="page"/>

<jsp:useBean id="filtroInscripcionVO" class="articulacion.inscripcion.vo.FiltroInscripcionVO" scope="session"/>
<jsp:setProperty name="filtroInscripcionVO" property="*"/>

<c:import url="siges/gestionAdministrativa/inscripcion/Nuevo.do"/>