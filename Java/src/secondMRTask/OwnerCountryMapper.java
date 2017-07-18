package secondMRTask;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;


import CustomClasses.MapReduceUtil;
import CustomClasses.TextIntPair;
import CustomClasses.TextLongPair;

public class OwnerCountryMapper implements Mapper <Object, Text, TextLongPair, Text> {
	
	Text newValue = new Text();
	TextLongPair newKey = new TextLongPair();
	Text OwnerID = new Text ();
	Long timestamp;

	String[] dataArray;
	
	// a mechanism to filter out non ascii tags
	static CharsetEncoder asciiEncoder = Charset.forName("US-ASCII").newEncoder(); 
	
	public void map(Object key, Text value, OutputCollector<TextLongPair, Text> output, Reporter reporter) throws IOException {
		
		// Value read from J1 will be in this format
		// <placeID> <placeType> <countryName> <userID> <timestamp>
		
		dataArray = value.toString().split("\t"); //split the data into array
		
		
		if (dataArray.length < 5){ //  record with incomplete data
			return; // don't emit anything
		}
		
		//OwnerID.set(dataArray[3]);
		//timestamp = MapReduceUtil.dateStringToLong (dataArray[4]);
		newKey.setKey(new Text (dataArray[3]));
		newKey.setOrder(MapReduceUtil.dateStringToLong (dataArray[4]));		
		newValue.set(dataArray[4] + "\t" + dataArray[2]);
		
		// just swap the key and value around
		output.collect(newKey, newValue);
	}

	@Override
	public void configure(JobConf arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}
}