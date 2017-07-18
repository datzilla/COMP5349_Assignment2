package firstMRTask;
import java.io.*;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;

import CustomClasses.TextIntPair;

public class PhotoMapper implements Mapper <Object, Text, TextIntPair, Text> {
	
	//private static CharsetEncoder asciiEncoder = Charset.forName("US-ASCII").newEncoder();
	Text placeID = new Text ();
	Text textValue = new Text();
	TextIntPair textKey = new TextIntPair ();
	
	public void map (Object key, Text value,
			OutputCollector<TextIntPair, Text> output, Reporter reporter) throws IOException {


			
		String [] valueTokens = value.toString().split("\t");
		if (valueTokens.length < 6 ) return;
		
		if (valueTokens [0].length() == 0 || //photo id
			valueTokens [1].length() == 0 || //owner id
			valueTokens [2].length() == 0 || //tags
			valueTokens [3].length() == 0 || //date taken
			valueTokens [4].length() == 0 || //placeid
			valueTokens [5].length() == 0) return; //accuracy
			
		// Key: MapperID\tplaceID Value: placeName
		placeID.set (valueTokens [4].trim());
		textValue.set(valueTokens[1].trim() + "\t" + valueTokens[3].trim());
		textKey.setKey(placeID);
		textKey.setOrder(1);
		output.collect(textKey, textValue);
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

