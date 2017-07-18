package CustomClasses;

public class VisitInstance {
	
	private int visitInstance = 0;
	private double minTime = 0;
	private double maxTime = 0;
	private double avgTime = 0;
	private double totalTime = 0;
	private String timeStamp = "";
	private int flag;
	
	public VisitInstance (String inTimeStamp) {
		timeStamp = inTimeStamp;
		flag = 1;
	}
	
	public void incrementInstance () {
		visitInstance++;
	}
	
	public int getVisitInstance () {
		return visitInstance;
	}
	
	public void setTimeStamp (String inTimeStamp) {
		timeStamp = inTimeStamp;
	}
	
	public String getTimeStamp () {
		return timeStamp;
	}
	
	public void addDuration (double inDuration) {
		totalTime = totalTime + inDuration;
	}
	
	public double getTotalTime () {
		return Math.round(totalTime*10.0)/10.0;
	}
	
	public void setMinTime (double inMinTime) {
		minTime = inMinTime;
	}
	
	public double getMinTime () {
		return minTime;
	}
	
	
	public void setMaxTime (double inMaxTime) {
		maxTime = inMaxTime;
	}
	
	public double getMaxTime () {
		return maxTime;
	}
		
	public double getAvgTime () {
		avgTime = Math.round(totalTime/visitInstance*10.0)/10.0;
		return avgTime;
	}
	
	public void setFlag (int inFlag) {
		flag = inFlag;
	}	
	
	public int getFlag () {
		return flag;
	}
	
	public void reset () {
		visitInstance = 0;
		minTime = 0;
		maxTime = 0;
		avgTime = 0;
		totalTime = 0;
		timeStamp = "";
		flag = 1;
	}

}
