import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class EmailValidation {
	//Regular Expression Test
	public static boolean isValidRegex(String email){
		String regex = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(email);
		if(m.find()){
			return true;
		}
		else{
			return false;
		}
	}
	
	//from http://www.rgagnon.com/javadetails/java-0452.html
	//MX (Mail Exchange) Domain Test
	public static boolean isValidMX(String email) throws NamingException{
		try{
			String hostName = email.substring(email.indexOf("@") + 1, email.length());
		    Hashtable env = new Hashtable();
		    env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
		    DirContext ictx = new InitialDirContext( env );
		    Attributes attrs = ictx.getAttributes
		                            ( hostName, new String[] { "MX" });
		    Attribute attr = attrs.get( "MX" );
		    
		    if( attr == null ){
		    	return false;
		    }
		    else{
		    	return true;
		    }
		}
		catch(NamingException e){
			return false;
		}
	}
	
	public static String checkEmail(String email) throws NamingException {
		if( isValidRegex(email) ){
			if( isValidMX(email) ){
				return "Y";
			}
			else{
				return "N";
			}
		}
		else{
			return "N";
		}
	}
}