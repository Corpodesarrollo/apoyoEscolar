<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="FilEvaluacionVO" class="articulacion.evaluacionArt.vo.DatosVO" scope="session"/>
<jsp:setProperty name="FilEvaluacionVO" property="*"/>
<c:import url="/articulacion/evaluacionArt/Lista.do"/>