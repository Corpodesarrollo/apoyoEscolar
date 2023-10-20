<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="filtroConsultaPOA" class="poa.consulta.vo.FiltroConsultaVO" scope="session"/>
<jsp:setProperty name="filtroConsultaPOA" property="*"/>
<c:import url="/poa/consulta/Nuevo.do"/>