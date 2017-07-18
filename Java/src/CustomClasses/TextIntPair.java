package CustomClasses;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

public class TextIntPair implements WritableComparable<TextIntPair>{

	private Text key;
	private IntWritable order;
	
	public TextIntPair () {
		this.key = new Text();
		this.order = new IntWritable();
	}
	
	public TextIntPair(Text inKey, int inOrder){
		this.key = new Text(inKey);
		this.order = new IntWritable(inOrder);
	}
	
	public Text getKey() {
		return key;
	}

	public void setKey(Text key) {
		this.key = key;
	}

	public IntWritable getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order.set(order);
	}	
	
	public TextIntPair(String key, int order){
		this.key = new Text(key);
		this.order = new IntWritable(order);
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
	public int compareTo(TextIntPair other) {
		// TODO Auto-generated method stub
		int cmp = key.compareTo(other.key);
		if (cmp != 0) {
			return cmp;
		}
		return order.compareTo(other.order);
	}

	@Override
	public int hashCode() {
		return key.hashCode() * 163 + order.get();
	}

	public boolean equals(Object other) {
		if (other instanceof TextIntPair) {
			TextIntPair tip = (TextIntPair) other;
			return key.equals(tip.key) && order.equals(tip.order);
		}
		return false;
	}
}
