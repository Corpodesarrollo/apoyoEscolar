/*
 * Generated by JasperReports - 6/06/06 11:20 AM
 */
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.fill.*;

import java.util.*;
import java.math.*;
import java.text.*;
import java.io.*;
import java.net.*;

import java.util.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.*;


/**
 *
 */
public class escalas_valorativas extends JRCalculator
{


    /**
     *
     */
    private JRFillParameter parameter_REPORT_RESOURCE_BUNDLE = null;
    private JRFillParameter parameter_REPORT_CONNECTION = null;
    private JRFillParameter parameter_REPORT_PARAMETERS_MAP = null;
    private JRFillParameter parameter_REPORT_LOCALE = null;
    private JRFillParameter parameter_REPORT_DATA_SOURCE = null;
    private JRFillParameter parameter_REPORT_SCRIPTLET = null;
    private JRFillParameter parameter_REPORT_MAX_COUNT = null;

    private JRFillField field_ESCNOMBRE = null;
    private JRFillField field_ESCCODIGO = null;
    private JRFillField field_UPDATE_ID = null;
    private JRFillField field_ESCABREVIATURA = null;
    private JRFillField field_ESCTIPO = null;

    private JRFillVariable variable_PAGE_NUMBER = null;
    private JRFillVariable variable_COLUMN_NUMBER = null;
    private JRFillVariable variable_REPORT_COUNT = null;
    private JRFillVariable variable_PAGE_COUNT = null;
    private JRFillVariable variable_COLUMN_COUNT = null;
    private JRFillVariable variable_test = null;
    private JRFillVariable variable_esctipo_COUNT = null;


    /**
     *
     */
    public void customizedInit(
        Map pm,
        Map fm,
        Map vm
        ) throws JRException
    {
        parameter_REPORT_RESOURCE_BUNDLE = (JRFillParameter)parsm.get("REPORT_RESOURCE_BUNDLE");
        parameter_REPORT_CONNECTION = (JRFillParameter)parsm.get("REPORT_CONNECTION");
        parameter_REPORT_PARAMETERS_MAP = (JRFillParameter)parsm.get("REPORT_PARAMETERS_MAP");
        parameter_REPORT_LOCALE = (JRFillParameter)parsm.get("REPORT_LOCALE");
        parameter_REPORT_DATA_SOURCE = (JRFillParameter)parsm.get("REPORT_DATA_SOURCE");
        parameter_REPORT_SCRIPTLET = (JRFillParameter)parsm.get("REPORT_SCRIPTLET");
        parameter_REPORT_MAX_COUNT = (JRFillParameter)parsm.get("REPORT_MAX_COUNT");

        field_ESCNOMBRE = (JRFillField)fldsm.get("ESCNOMBRE");
        field_ESCCODIGO = (JRFillField)fldsm.get("ESCCODIGO");
        field_UPDATE_ID = (JRFillField)fldsm.get("UPDATE_ID");
        field_ESCABREVIATURA = (JRFillField)fldsm.get("ESCABREVIATURA");
        field_ESCTIPO = (JRFillField)fldsm.get("ESCTIPO");

        variable_PAGE_NUMBER = (JRFillVariable)varsm.get("PAGE_NUMBER");
        variable_COLUMN_NUMBER = (JRFillVariable)varsm.get("COLUMN_NUMBER");
        variable_REPORT_COUNT = (JRFillVariable)varsm.get("REPORT_COUNT");
        variable_PAGE_COUNT = (JRFillVariable)varsm.get("PAGE_COUNT");
        variable_COLUMN_COUNT = (JRFillVariable)varsm.get("COLUMN_COUNT");
        variable_test = (JRFillVariable)varsm.get("test");
        variable_esctipo_COUNT = (JRFillVariable)varsm.get("esctipo_COUNT");
    }


    /**
     *
     */
    public Object evaluate(int id) throws Throwable
    {
        Object value = null;

        switch (id)
        {
            case 279 : // variableInitialValue_esctipo_COUNT
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 276 : // variableInitialValue_test
            {
                value = (java.lang.String)("test");
                break;
            }
            case 277 : // group_esctipo
            {
                value = (java.lang.Object)(null);
                break;
            }
            case 268 : // variableInitialValue_COLUMN_NUMBER
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 278 : // variable_esctipo_COUNT
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 273 : // variable_COLUMN_COUNT
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 280 : // textField_1
            {
                value = (java.lang.String)(((java.lang.String)field_ESCCODIGO.getValue()));
                break;
            }
            case 271 : // variable_PAGE_COUNT
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 269 : // variable_REPORT_COUNT
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 272 : // variableInitialValue_PAGE_COUNT
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 281 : // textField_2
            {
                value = (java.lang.String)(((java.lang.String)field_ESCNOMBRE.getValue()));
                break;
            }
            case 270 : // variableInitialValue_REPORT_COUNT
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 267 : // variableInitialValue_PAGE_NUMBER
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 274 : // variableInitialValue_COLUMN_COUNT
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 275 : // variable_test
            {
                value = (java.lang.String)("test");
                break;
            }
           default :
           {
           }
        }
        
        return value;
    }


    /**
     *
     */
    public Object evaluateOld(int id) throws Throwable
    {
        Object value = null;

        switch (id)
        {
            case 279 : // variableInitialValue_esctipo_COUNT
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 276 : // variableInitialValue_test
            {
                value = (java.lang.String)("test");
                break;
            }
            case 277 : // group_esctipo
            {
                value = (java.lang.Object)(null);
                break;
            }
            case 268 : // variableInitialValue_COLUMN_NUMBER
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 278 : // variable_esctipo_COUNT
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 273 : // variable_COLUMN_COUNT
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 280 : // textField_1
            {
                value = (java.lang.String)(((java.lang.String)field_ESCCODIGO.getOldValue()));
                break;
            }
            case 271 : // variable_PAGE_COUNT
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 269 : // variable_REPORT_COUNT
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 272 : // variableInitialValue_PAGE_COUNT
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 281 : // textField_2
            {
                value = (java.lang.String)(((java.lang.String)field_ESCNOMBRE.getOldValue()));
                break;
            }
            case 270 : // variableInitialValue_REPORT_COUNT
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 267 : // variableInitialValue_PAGE_NUMBER
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 274 : // variableInitialValue_COLUMN_COUNT
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 275 : // variable_test
            {
                value = (java.lang.String)("test");
                break;
            }
           default :
           {
           }
        }
        
        return value;
    }


    /**
     *
     */
    public Object evaluateEstimated(int id) throws Throwable
    {
        Object value = null;

        switch (id)
        {
            case 279 : // variableInitialValue_esctipo_COUNT
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 276 : // variableInitialValue_test
            {
                value = (java.lang.String)("test");
                break;
            }
            case 277 : // group_esctipo
            {
                value = (java.lang.Object)(null);
                break;
            }
            case 268 : // variableInitialValue_COLUMN_NUMBER
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 278 : // variable_esctipo_COUNT
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 273 : // variable_COLUMN_COUNT
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 280 : // textField_1
            {
                value = (java.lang.String)(((java.lang.String)field_ESCCODIGO.getValue()));
                break;
            }
            case 271 : // variable_PAGE_COUNT
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 269 : // variable_REPORT_COUNT
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 272 : // variableInitialValue_PAGE_COUNT
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 281 : // textField_2
            {
                value = (java.lang.String)(((java.lang.String)field_ESCNOMBRE.getValue()));
                break;
            }
            case 270 : // variableInitialValue_REPORT_COUNT
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 267 : // variableInitialValue_PAGE_NUMBER
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 274 : // variableInitialValue_COLUMN_COUNT
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 275 : // variable_test
            {
                value = (java.lang.String)("test");
                break;
            }
           default :
           {
           }
        }
        
        return value;
    }


}