package secondMRTask;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;

import CustomClasses.MapReduceUtil;
import CustomClasses.TextLongPair;
import CustomClasses.VisitInstance;

public class VisitDurationReducer extends MapReduceBase implements Reducer <TextLongPair, Text, Text, Text> {

	Text value = new Text ();
	Text key = new Text ();
	Map <String, VisitInstance> countryVisited = new HashMap ();
	
	String [] dataToken;
	String prevTimeStamp;
	String prevCountry;
	String country;
	String timeStamp;
	double timediff;
	StringBuilder countryVisitedString = new StringBuilder ();
	
		
	public void reduce (TextLongPair key, Iterator <Text> values, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
		
		//Make sure the previous data is cleared first.
		//and intitiate the variables
		countryVisited.clear(); 
		prevCountry = "";
		prevTimeStamp = "";
		
		//The data input is now sorted by time
		// key = userid
		// value = timestamp\tcountry
		// iterate through each value
		while (values.hasNext()){
			dataToken = values.next().toString().split("\t"); // dataToken[0] = timestamp, dataToken[1] = country
			timeStamp = dataToken[0];
			country = dataToken[1];
			
			//if there's no record of the country yet, create it. - first instance of that country
			if (!countryVisited.containsKey(country)) {
				countryVisited.put(country, new VisitInstance(timeStamp));
				//countryVisited.get(country).incrementInstance(); // increase from 0 to 1
				countryVisited.get(country).setFlag(1);
			}
			
			// where does it increase in the instnce?
			
			
			//if there's a change in country 
			if (!prevCountry.equalsIgnoreCase("") && !country.equalsIgnoreCase(prevCountry)) {
				
				//mark the first start date in a different country
				countryVisited.get(country).setTimeStamp(timeStamp);
				countryVisited.get(country).setFlag(1);
				countryVisited.get(prevCountry).incrementInstance();
				timediff = 0;
				
				// calculate the time stayed in previous country
				if (countryVisited.get(prevCountry).getFlag() == 1) {			
					if (MapReduceUtil.compareTwoDates(timeStamp, countryVisited.get(prevCountry).getTimeStamp()) != 0) {
						try {
							timediff = MapReduceUtil.dateDiff(timeStamp, countryVisited.get(prevCountry).getTimeStamp());
						} catch (ParseException e) {
						}
						if (timediff == 0.000) timediff = 0.1; //if there's an instance but duration is 0, means they must have stayed for 0.1 day.
						countryVisited.get(prevCountry).addDuration(timediff);
						if (countryVisited.get(prevCountry).getMinTime() > timediff || countryVisited.get(prevCountry).getMinTime() == 0.0) countryVisited.get(prevCountry).setMinTime(timediff); //set min
						if (countryVisited.get(prevCountry).getMaxTime() < timediff || countryVisited.get(prevCountry).getMaxTime() == 0.0) countryVisited.get(prevCountry).setMaxTime(timediff); // set max
						countryVisited.get(prevCountry).setFlag(2);
					}
				}
			}
			
			// deal with the last record
			if (!values.hasNext()) {
				countryVisited.get(country).incrementInstance();
				if (MapReduceUtil.compareTwoDates (timeStamp, countryVisited.get(country).getTimeStamp()) != 0) {
					try {
						timediff = MapReduceUtil.dateDiff(timeStamp, countryVisited.get(country).getTimeStamp());							
					} catch (ParseException e) {
					}
					if (timediff == 0.000) timediff = 0.1; //if there's an instance but duration is 0, means they must have stayed for 0.1 day.
					countryVisited.get(country).addDuration(timediff);
					if (countryVisited.get(country).getMinTime() > timediff || countryVisited.get(country).getMinTime() == 0.0) countryVisited.get(country).setMinTime(timediff); // set min time
					if (countryVisited.get(country).getMaxTime() < timediff || countryVisited.get(country).getMaxTime() == 0.0) countryVisited.get(country).setMaxTime(timediff); // set max time
				}
			}
			
			// remember previous records
			prevCountry = country;
			prevTimeStamp = timeStamp;
		}
		
		//convert the hashmap into string
		countryVisitedString.setLength(0);
		int i = 0;
		for (String visitedcountry: countryVisited.keySet()){
			countryVisitedString.append( visitedcountry + " (" + 
			 Integer.toString(countryVisited.get(visitedcountry).getVisitInstance()) + "," +
			 Double.toString(countryVisited.get(visitedcountry).getMaxTime()) + "," +
			 Double.toString(countryVisited.get(visitedcountry).getMinTime()) + "," +
			 Double.toString(countryVisited.get(visitedcountry).getAvgTime()) + "," +
			 Double.toString(countryVisited.get(visitedcountry).getTotalTime()) + "), ");
		}
		//remove the two last characters ', '
		countryVisitedString.deleteCharAt(countryVisitedString.length()-2);
		value.set(countryVisitedString.toString());
		
		output.collect(key.getKey(), value);		
	}

}