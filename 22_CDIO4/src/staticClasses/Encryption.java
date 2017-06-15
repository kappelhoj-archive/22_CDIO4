package staticClasses;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Encryption {

	/**
	 * Our non-keyed cryptographic hash function
	 * @param base
	 * @return
	 * 			Hash key
	 */
	public static String sha512(String base) {
        MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-512");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        byte[] hash = null;
		try {
			hash = digest.digest(base.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        StringBuffer hexString = new StringBuffer();
	    try{

	        for (int i = 0; i < hash.length; i++) {
	            String hex = Integer.toHexString((0xff & hash[i]));
	            if(hex.length() == 1) hexString.append('0');
	            hexString.append(hex);
	        }

	        return hexString.toString();
	        
	    } catch(Exception ex){
	    	return hexString.toString();
	    }
	}
	
	/**
	 * A specific method which is used to split a string in a ArrayList of strings at a number of chars.
	 * It may not be used in this project.
	 * @author PeterElHabr s165202
	 * @param string
	 * @param index
	 * @return
	 * 			ArrayList of strings
	 */
	public static ArrayList<String> splitAt(String string, int index){
		StringBuffer line = new StringBuffer();
		ArrayList<String> result = new ArrayList<String>();
		
		char[] chars = string.toCharArray();
		
		int count = 0;
		for(char c : chars){
			++count;
			line.append(c);
			
			if(count == index){
				count = 0;
				result.add(line.toString());
				line = new StringBuffer();
			}
		}
		return result;
	}
	
}
