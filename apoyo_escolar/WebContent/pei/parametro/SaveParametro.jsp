<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="parametroPEIVO" class="pei.parametro.vo.ParametroVO" scope="session"/>
<jsp:useBean id="filtroParametroPEIVO" class="pei.parametro.vo.FiltroParametroVO" scope="session"/>
<jsp:setProperty name="parametroPEIVO" property="*"/>
<jsp:setProperty name="filtroParametroPEIVO" property="*"/>
<c:import url="/pei/parametro/Nuevo.do"/>