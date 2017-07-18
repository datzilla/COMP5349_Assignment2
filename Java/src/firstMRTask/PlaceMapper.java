package firstMRTask;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;

import CustomClasses.MapReduceUtil;
import CustomClasses.TextIntPair;

public class PlaceMapper implements Mapper <Object, Text, TextIntPair, Text> {
	
	//private static CharsetEncoder asciiEncoder = Charset.forName("US-ASCII").newEncoder();
	 Text textValue = new Text();
	 Text placeID = new Text();
	 int placeType;
	 String countryName="";
	 String [] valueTokens;
	 TextIntPair textintpairKey = new TextIntPair ();
	
	public void map (Object key, Text value, OutputCollector <TextIntPair, Text> output, Reporter reporter) throws IOException {

		
		valueTokens = value.toString().split("\t");
		
		if (valueTokens.length < 7 ) return;
		
		if (valueTokens [0].length() == 0 || //place ID
			valueTokens [1].length() == 0 || 
			valueTokens [2].length() == 0 ||
			valueTokens [3].length() == 0 || 
			valueTokens [4].length() == 0 || //place name
			valueTokens [5].length() == 0 || //place type
			valueTokens [6].length() == 0) return; //place URL
		
		placeID.set(valueTokens [0].trim());
		
		countryName = MapReduceUtil.getCountry(valueTokens [4].trim());
		
		
		// Key: MapperID - tplaceID Value: placeName
		textintpairKey.setKey(placeID);
		textintpairKey.setOrder(0);
		textValue.set(valueTokens [5].trim() + "\t" + countryName);
		output.collect(textintpairKey, textValue);
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

