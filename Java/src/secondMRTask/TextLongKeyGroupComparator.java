package secondMRTask;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

import CustomClasses.TextLongPair;

public class TextLongKeyGroupComparator extends WritableComparator {

	protected TextLongKeyGroupComparator() {
		super(TextLongPair.class,true);
	}

	public int compare(WritableComparable w1, WritableComparable w2) {
		TextLongPair tip1 = (TextLongPair) w1;
		TextLongPair tip2 = (TextLongPair) w2;
		return tip1.getKey().compareTo(tip2.getKey());
	}
	
}