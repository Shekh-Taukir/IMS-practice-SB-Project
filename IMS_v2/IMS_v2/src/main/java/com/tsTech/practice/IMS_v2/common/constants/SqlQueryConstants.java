package com.tsTech.practice.IMS_v2.common.constants;

/////////////////////////////////////////////
//
// Name: Sql Query Constants
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Aug 20, 2026 || TaukirS (ER 1015 - visitnote entity coding)
/////////////////////////////////////////////

public final class SqlQueryConstants {

    //NOTE: Following constants are predefined expression, for query's, so that don't have to write the expression, i.e. to get full patient name, in every query.

    private SqlQueryConstants() { } //prevents the class to be instantiated

    private static final String COM_STRING_CONCAT = " || ', ' || ";
    private static final String COM_BRACKET_OPEN=" || ' (' || ";
    private static final String COM_BRACKET_CLOSE=" || ')' ";

    public static final String PATIENT_NAME_EXPR =  " pat.lastName" + COM_STRING_CONCAT + "pat.firstName" +
                                                    COM_BRACKET_OPEN + "pat.aka" + COM_BRACKET_CLOSE + "as patientName ";

    public static final String OFFICE_NAME_EXPR =   " off.officeName " + COM_BRACKET_OPEN + "  off.officeCode " +
                                                    COM_BRACKET_CLOSE + " as officeName ";

    public static final String PROVIDER_NAME_EXPR =     " prov.lastName " + COM_STRING_CONCAT + " prov.firstName || ' (' || prov.prefix || ')' as providerName ";

}
