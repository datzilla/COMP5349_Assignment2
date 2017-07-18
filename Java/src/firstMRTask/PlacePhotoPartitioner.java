package firstMRTask;

import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.Partitioner;
import org.apache.hadoop.io.Text;

import CustomClasses.*;

public class PlacePhotoPartitioner implements Partitioner <TextIntPair, Text> {

	@Override
	public int getPartition(TextIntPair key, Text value, int numPartition) {
		return (key.getKey().hashCode() & Integer.MAX_VALUE) % numPartition;
	}

	@Override
	public void configure(JobConf arg0) {
		
	}

}