package CustomClasses;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

public class TextLongPair implements WritableComparable<TextLongPair>{

	private Text key;
	private LongWritable order;
	
	public TextLongPair () {
		this.key = new Text();
		this.order = new LongWritable();
	}
	
	public TextLongPair(Text inKey, Long inOrder){
		this.key = new Text(inKey);
		this.order = new LongWritable (inOrder);
	}
	
	public Text getKey() {
		return key;
	}

	public void setKey(Text key) {
		this.key = key;
	}

	public LongWritable getOrder() {
		return order;
	}

	public void setOrder(Long order) {
		this.order.set(order);
	}	
	
	public TextLongPair(String key, Long order){
		this.key = new Text(key);
		this.order = new LongWritable (order);
	}
	
	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		key.readFields(in);
		order.readFields(in);
		
	}

	@Override
	public void write(DataOutput out) throws IOException {
		// TODO Auto-generated method stub
		key.write(out);
		order.write(out);
	}

	@Override
	public int compareTo (TextLongPair other) {
		// TODO Auto-generated method stub
		int cmp = key.compareTo(other.key);
		if (cmp != 0) {
			return cmp;
		}
		
		return order.compareTo(other.order);
		
		
	}

	@Override
	public int hashCode() {
		return key.hashCode() * 163 + (int) order.get();
		//return key.hashCode() * 163;
	}

	public boolean equals(Object other) {
		if (other instanceof TextLongPair) {
			TextLongPair tip = (TextLongPair) other;
			return key.equals(tip.key) && order.equals(tip.order);
		}
		return false;
	}

}
