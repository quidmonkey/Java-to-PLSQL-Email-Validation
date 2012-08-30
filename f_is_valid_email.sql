CREATE OR REPLACE FUNCTION f_is_valid_email
    (
        email_in        VARCHAR2
    ) RETURN VARCHAR2
    AS LANGUAGE JAVA 
    NAME 'EmailValidation.checkEmail(java.lang.String) return String' ;
/
