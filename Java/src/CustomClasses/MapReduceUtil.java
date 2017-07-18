package CustomClasses;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MapReduceUtil
{
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue( Map<K, V> map ){
        List<Map.Entry<K, V>> list = new LinkedList <Map.Entry<K, V>>( map.entrySet() );
        Collections.sort( list, new Comparator<Map.Entry<K, V>>()
        {
            public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
            {
            	// reverse the order
            	int compResult =  (o1.getValue()).compareTo( o2.getValue() );
            	if (compResult > 0) 
            	{ 
            		return -1; 
            	}
            	else if (compResult < 0)
            	{
            		return 1;
            	}
            	else 
            	{
            		//compare alphabetical order if it's equal
            		return o1.getKey().toString().compareTo(o2.getKey().toString());
            	}
            }
        } );

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list)
        {
            result.put( entry.getKey(), entry.getValue() );
        }
        list = null;
        return result;
    }
    
    public static String truncatePlaceName (String inPlaceName) 
    {
  		String [] placeNameTokens = inPlaceName.split(",");
    	String newPlaceName = "";
		for (int i = placeNameTokens.length - 1; i > 0; i--) {
			newPlaceName = placeNameTokens [i] + ", " + newPlaceName;
		}
		
		//remove the trailing spaces and comma
		newPlaceName = newPlaceName.trim();
		newPlaceName = newPlaceName.substring(0, newPlaceName.length() - 1);
		
		return newPlaceName;
    }
    
    public static String getCountry (String inPlaceName) 
    {
    	//This assumes all input string are placeName and the country is the last piece
    	//  suburbs, locality, state, country
    	String countryName ="";
  		String [] placeNameTokens = inPlaceName.split(",");
  		// The last token is the country name
  		countryName = placeNameTokens[placeNameTokens.length-1].trim();
  		
		return countryName;
    }
    
    public static String getLocality (String inPlaceName) {
    	// This assumes all place string input is a type 7
    	// suburbs, locality, state, country
    	String localityName ="";
  		String [] placeNameTokens = inPlaceName.split(",");
  		
  		// we'll have a lot of spaces at the front and end of string
  		localityName = placeNameTokens[0].trim();
  		
		return localityName;
    }
    
    
    public static String rejoin (String [] inStringTokens, String inDelimiter) 
    {
    	String joinedString ="";
    	for (String value: inStringTokens) {
    		if (value.compareTo(" ")==0 || value.length() == 0) continue;
    		joinedString = value.trim() + inDelimiter + joinedString;
    	}
    	return joinedString.substring(0, joinedString.length() - 1);   	
    }
    
    public static boolean isNumeric (String str) 
    {  
    	for (char c : str.toCharArray())
        {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }
    
    public static void replaceAll(StringBuilder builder, String from, String to)
    {
        int index = builder.indexOf(from);
        while (index != -1)
        {
            builder.replace(index, index + from.length(), to);
            index += to.length(); // Move to the end of the replacement
            index = builder.indexOf(from, index);
        }
    }
    
    
    public static double dateDiff (String inDate1, String inDate2) throws ParseException {
    	SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		Date leftDate = sdf.parse(inDate1);
    	Date rightDate = sdf.parse(inDate2);
    	long datediff;
    	
    	try {
	    	if (dateStringToLong (inDate1) > dateStringToLong (inDate2)) {
	    		datediff = leftDate.getTime() - rightDate.getTime();
	    	} else {
	    		datediff = rightDate.getTime() - leftDate.getTime();
	    	}
	    	//return two decimal places in days
	    	return (double) Math.ceil((datediff/(1000.00*60.00*60.00*24.00))*10.0)/10.0;
    	} catch (Exception e){
    		return 0;
    	}
    }
    
    public static long dateStringToLong (String inString) {
    	String newTimeString = inString;
    	newTimeString = newTimeString.replaceAll(" ", "");
    	newTimeString = newTimeString.replaceAll("-", "");
    	newTimeString = newTimeString.replaceAll(":", "");
    	
    	try {
    		return Long.parseLong(newTimeString);
    	}
    	catch (Exception e) {
    		return 0;
    	}
    }
    
    public static int compareLong (Long inLValue, Long inRValue) {
    	return (inRValue < inLValue ? -1 : (inLValue==inRValue ? 0 : 1));
    }
    
    public static int compareTwoDates (String inString1, String inString2) {
    	return compareLong (dateStringToLong (inString1), dateStringToLong (inString2));
    }
}