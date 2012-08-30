Java-to-PLSQL-Email-Validation
==============================

PL/SQL is remarkably powerful, but sooner or later any database developer will hit the limits of what can be done in Oracle.

Oracle features its own SMTP object that can send emails. Unfortunately, Oracle has no way of validating emails; but fortunately, with a little script-fu, a Java .class can be loaded into Oracle and used in four easy steps:

## 1. Create Java Class

This is self-explanatory: you're gonna need a .java class to import. In this example, I provide a two-step email validation class: first, the passed-in email goes through a regex test - if it's in the form user@gateway.domain, it'll pass as valid to the second test; second, the passed-in email goes through a MX (Mail Exchange) Domain DNS test to verify a mail-server exists on the specified domain. If both tests are found to be valid, the class returns a 'Y'; otherwise, it returns a 'N'. The reason for the String types vs. a boolean is that Java and PL/SQL Booleans don't play well together.

## 2. Compile Java Class

This is, again, self-explanatory. A simple 'javac myClass.java' will suffice. I encourage you to use an identical Java version as the one being used by Oracle on the database server to avoid any version mismatches.

## 3. Import into Oracle

You can import a .class file into Oracle with this cmd:
```linux
loadjava -u user/password@DATABASE -resolve myClass.class
```
The resolve option is import as it will validate the .class during the import vs. at runtime. If for some reason the .class already exists in Oracle, you'll need to remove it prior to loading it by running this cmd:
 ```linux
dropjava -u user/password@DATABASE myClass.class
```
You can then verify your Java .class imported properly by running the following SQL script:
```sql
SELECT * FROM all_objects WHERE object_name = "myClass" ;
```
It should appear as valid.

## 4. Create a PL/SQL Function or Stored Procedure Wrapper

Once the Java-side is setup, you'll need to create a PL/SQL wrapper to call your Java .class. A simple function looks like this:
```sql
CREATE OR REPLACE FUNCTION f_java_wrapper RETURN VARCHAR2
    AS LANGUAGE JAVA 
    NAME 'myClass.myMethod() return String' ;
/
```
Included in this repo is a sample function for calling the EmailValidation class. It shows you how to pass-in parameters.

## 5. Summary

And that's it!

To sum up:
- Write .java class
- Compile into .class (Java bytecode)
- Import into Oracle use loadjava/dropjava
- Write a PL/SQL Wrapper to call the Java .class

This may seem small, but it opens all sorts of avenues. What you can in Java, can now be done in Oracle; and since you can do most anything with Java, the skies the limit for your database development.
