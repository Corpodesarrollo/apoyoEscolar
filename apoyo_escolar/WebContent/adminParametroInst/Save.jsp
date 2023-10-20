<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean  id="instParVO" class="siges.adminParamsInst.vo.InstParVO" scope="session"/>
<jsp:setProperty name="instParVO" property="*"/>
<jsp:useBean  id="escalaConceptualVO" class="siges.adminParamsInst.vo.EscalaConceptualVO" scope="session"/>
<jsp:setProperty name="escalaConceptualVO" property="*"/>
<jsp:useBean  id="filtroEscalaVO" class="siges.adminParamsInst.vo.FiltroEscalaVO" scope="session"/>
<jsp:setProperty name="filtroEscalaVO" property="*"/>
<jsp:useBean  id="filtroEscalaNumVO" class="siges.adminParamsInst.vo.FiltroEscalaNumVO" scope="session"/>
<jsp:setProperty name="filtroEscalaNumVO" property="*"/>
<jsp:useBean  id="escalaNumericaVO" class="siges.adminParamsInst.vo.EscalaNumericaVO" scope="session"/>
<jsp:setProperty name="escalaNumericaVO" property="*"/>
<jsp:useBean  id="instNivelEvaluacionVO" class="siges.adminParamsInst.vo.InstNivelEvaluacionVO" scope="session"/>
<jsp:setProperty name="instNivelEvaluacionVO" property="*"/>
<jsp:useBean  id="tipoEvaluacionInstAsigVO" class="siges.adminParamsInst.vo.TipoEvaluacionInstAsigVO" scope="session"/>
<jsp:setProperty name="tipoEvaluacionInstAsigVO" property="*"/>
<jsp:useBean  id="filtroNivelEvalVO" class="siges.adminParamsInst.vo.FiltroNivelEvalVO" scope="session"/>
<jsp:setProperty name="filtroNivelEvalVO" property="*"/>
<jsp:useBean  id="periodoPrgfechasVO" class="siges.adminParamsInst.vo.PeriodoPrgfechasVO" scope="session"/>
<jsp:setProperty name="periodoPrgfechasVO" property="*"/>
<jsp:useBean  id="ponderacionPrgPeriodoVO" class="siges.adminParamsInst.vo.PonderacionPorPeriodoVO" scope="session"/>
<jsp:setProperty name="ponderacionPrgPeriodoVO" property="*"/><
<c:import url="/adminParamsInst/Nuevo.do"/>
