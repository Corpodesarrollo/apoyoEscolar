<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="indicadorDescriptorVO" class="siges.indicadores.vo.DescriptorVO" scope="session"/>
<jsp:setProperty name="indicadorDescriptorVO" property="*"/>
<jsp:useBean id="filtroDescriptorVO" class="siges.indicadores.vo.FiltroDescriptorVO" scope="session"/>
<jsp:setProperty name="filtroDescriptorVO" property="*"/>
<c:import url="/indicadores/Nuevo.do"/>