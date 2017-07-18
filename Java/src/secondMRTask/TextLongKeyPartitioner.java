package secondMRTask;

import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.Partitioner;
import org.apache.hadoop.io.Text;

import CustomClasses.*;

public class TextLongKeyPartitioner implements Partitioner <TextLongPair, Text> {

	@Override
	public int getPartition(TextLongPair key, Text value, int numPartition) {
		return (key.getKey().hashCode() & Integer.MAX_VALUE) % numPartition;
	}

	@Override
	public void configure(JobConf arg0) {
		
	}

}