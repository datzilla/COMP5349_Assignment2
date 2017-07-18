package firstMRTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;

import CustomClasses.*;

public class PlacePhotoReducer extends MapReduceBase implements Reducer<TextIntPair, Text, Text, Text>  {
	
	Text value = new Text ();
	String placeName;
	
	public void reduce (TextIntPair key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {

		// Assume a photo always has a place associated with it, 
		//if it doesn't then we don't really care for it

		if (key.getOrder().get() == 0){// the key is from the place table
			placeName = values.next().toString().trim();

			while (values.hasNext()){	
				value.set ( placeName + "\t" + values.next().toString() );
				output.collect(key.getKey(), value);
			}
		}else{ // the key is not from the place table, but the photo table
			/* we do not care this key as these photos do not have a place
			 * associated with it
			 */
			
		}
	}
}
