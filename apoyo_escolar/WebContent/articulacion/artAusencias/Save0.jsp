<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="FilAusenciasVO" class="articulacion.artAusencias.vo.DatosVO" scope="session"/>
<jsp:setProperty name="FilAusenciasVO" property="*"/>
<c:import url="/articulacion/artAusencias/Lista.do"/>