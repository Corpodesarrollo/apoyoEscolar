<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="FilEvaluacionPVO" class="articulacion.plantillaEvaluacion.vo.DatosPlanEvalVO" scope="session"/>
<jsp:setProperty name="FilEvaluacionPVO" property="*"/>
<c:import url="/articulacion/plantillaEvaluacion/Lista.do"/>