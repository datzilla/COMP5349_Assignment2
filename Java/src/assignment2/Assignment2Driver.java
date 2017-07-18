package assignment2;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.lib.MultipleInputs;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import firstMRTask.*;
import secondMRTask.*;
import CustomClasses.*;


public class Assignment2Driver extends Configured implements Tool{

	public static void main(String[] args) throws Exception {

		int exitCode = ToolRunner.run(new Assignment2Driver (), args);
		System.exit(exitCode);
	}

	@Override
	public int run (String[] args) throws Exception {
		// TODO Auto-generated method stub

		if (args.length != 4) {
			System.err.println("JAR <JARFILE> <PACKAGE>.<DIRVER> <placedir> <photopdir> <outputdir> maxReducer1 maxReducer2");
			return -1;
		}
		
		Path photoTable1 = new Path(args[1]);
		Path placeTable1 = new Path(args[0]);
		Path outputTable1 = new Path(args[2]+"_J1");
		int maxReducer = Integer.parseInt(args[3]);

		// job 1
		JobConf conf1 = new JobConf(getConf(),getClass());
		conf1.setJobName("Job 1 - Join Photo & Place Tables");

		MultipleInputs.addInputPath(conf1, photoTable1, TextInputFormat.class, PhotoMapper.class);
		MultipleInputs.addInputPath(conf1, placeTable1, TextInputFormat.class,PlaceMapper.class);
		FileOutputFormat.setOutputPath(conf1, outputTable1);
		conf1.setNumReduceTasks(maxReducer);
		conf1.setMapOutputKeyClass(TextIntPair.class);
		conf1.setMapOutputValueClass(Text.class);
		conf1.setOutputKeyClass(Text.class);
		conf1.setOutputValueClass(Text.class);
		conf1.setOutputValueGroupingComparator(PlacePhotoGroupComparator.class);
		conf1.setReducerClass(PlacePhotoReducer.class);
		conf1.setPartitionerClass(PlacePhotoPartitioner.class);
		JobClient.runJob(conf1);

		// job 2
		Path outputTable2 = new Path(args[2]+"_J2");
	
		JobConf conf2 = new JobConf(getConf(),getClass());
		conf2.setJobName("Job 2 - Group Owner, Sort Timestamps and merge timestamps");
		TextInputFormat.addInputPath(conf2, outputTable1);
		FileOutputFormat.setOutputPath(conf2, outputTable2);
		conf2.setNumReduceTasks(maxReducer);
		conf2.setMapperClass (OwnerCountryMapper.class);
		conf2.setMapOutputKeyClass(TextLongPair.class);
		conf2.setMapOutputValueClass (Text.class);
		conf2.setOutputKeyClass(Text.class);
		conf2.setOutputValueClass(Text.class);
		conf2.setOutputValueGroupingComparator(TextLongKeyGroupComparator.class);
		//conf2.setOutputKeyComparatorClass(TextLongKeyComparator.class); // This is sorted using the .compareTo () method in TextLongPair
		conf2.setPartitionerClass(TextLongKeyPartitioner.class);
		
		conf2.setReducerClass(VisitDurationReducer.class);
		JobClient.runJob(conf2);
		long j2stoptime =  System.currentTimeMillis();
		
		// Finito!
		return 0;
	}

}
