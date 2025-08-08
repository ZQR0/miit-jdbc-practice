package utils;

public class SQLErrorsValidator {

    public static void validate(String SQLState) {
        String classOfErrors = SQLState.substring(0, 2);
        switch (classOfErrors) {
            case "01" -> {
                System.out.println("Warning state");
            }
            case "02" -> {
                System.out.println("No Data (this is also a warning class per the SQL standard)");
            }
            case "03" -> {
                System.out.println("SQL Statement Not Yet Complete");
            }
            case "08" -> {
                System.out.println("Connection Exception");
            }
            case "09" -> {
                System.out.println("Triggered Action Exception");
            }
            case "0A" -> {
                System.out.println("Feature Not Supported");
            }
            case "0B" -> {
                System.out.println("Invalid Transaction Initiation");
            }
            case "0F" -> {
                System.out.println("Locator Exception");
            }
            case "0L" -> {
                System.out.println("Invalid Grantor");
            }
            case "0P" -> {
                System.out.println("Invalid Role Specification");
            }
            case "0Z" -> {
                System.out.println("Diagnostics Exception");
            }
            case "20" -> {
                System.out.println("Case Not Found");
            }
            case "21" -> {
                System.out.println("Cardinality Violation");
            }
            case "22" -> {
                System.out.println("Data Exception");
            }
            case "23" -> {
                System.out.println("Integrity Constraint Violation");
            }
            case "24" -> {
                System.out.println("Invalid Cursor State");
            }
            case "25" -> {
                System.out.println("Invalid Transaction State");
            }
            case "26" -> {
                System.out.println("Invalid SQL Statement Name");
            }
            case "27" -> {
                System.out.println("Triggered Data Change Violation");
            }
            case "28" -> {
                System.out.println("Invalid Authorization Specification");
            }
            case "2B" -> {
                System.out.println("Dependent Privilege Descriptors Still Exist");
            }
            case "2D" -> {
                System.out.println("Invalid Transaction Termination");
            }
            case "2F" -> {
                System.out.println("SQL Routine Exception");
            }
            case "34" -> {
                System.out.println("Invalid Cursor Name");
            }
            case "38" -> {
                System.out.println("External Routine Exception");
            }
            case "39" -> {
                System.out.println("External Routine Invocation Exception");
            }
            case "3B" -> {
                System.out.println("Savepoint Exception");
            }
            case "3D" -> {
                System.out.println("Invalid Catalog Name");
            }
            case "3F" -> {
                System.out.println("Invalid Schema Name");
            }
            case "40" -> {
                System.out.println("Transaction Rollback");
            }
            case "42" -> {
                System.out.println("Syntax Error or Access Rule Violation");
            }
            case "44" -> {
                System.out.println("WITH CHECK OPTION Violation");
            }
            case "53" -> {
                System.out.println("Insufficient Resources");
            }
            case "54" -> {
                System.out.println("Program Limit Exceeded");
            }
            case "55" -> {
                System.out.println("Object Not In Prerequisite State");
            }
            case "57" -> {
                System.out.println("Operator Intervention");
            }
            case "58" -> {
                System.out.println("System Error (errors external to PostgreSQL itself)");
            }
            case "F0" -> {
                System.out.println("Configuration File Error");
            }
            case "HV" -> {
                System.out.println("Foreign Data Wrapper Error (SQL/MED)");
            }
            case "P0" -> {
                System.out.println("PL/pgSQL Error");
            }
            case "XX" -> {
                System.out.println("Internal Error");
            }
            default -> System.out.println("Invalid value passed");
        }
    }

}
