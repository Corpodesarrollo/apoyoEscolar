<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean  id="nivelEvaluacionVO" class="siges.adminParamsAcad.vo.NivelEvaluacionVO" scope="session"/>
<jsp:setProperty name="nivelEvaluacionVO" property="*"/>
<jsp:useBean  id="dimensionVO" class="siges.adminParamsAcad.vo.DimensionVO" scope="session"/>
<jsp:setProperty name="dimensionVO" property="*"/>
<c:import url="/adminParamsAcad/Nuevo.do"/>