package realprogress.helpers;

import java.util.Date;

public class ProgressStatus {

	private int Max;
	private String Message;
	private int current;
	private int progress;
	private Date lastUpdated;
	private Date lastRetrieved;
	
	public ProgressStatus(int Max, String key) {
		this.Max = Max;
	}
	public String getMessage() {
		setLastRetrieved(new Date());
		return Message;
	}
	public void setMessage(String message) {
		setLastUpdated(new Date());
		Message = message;
	}
	public int getMax() {
		setLastRetrieved(new Date());
		return Max;
	}
	public void setMax(int max) {
		setLastUpdated(new Date());
		Max = max;
	}
	public int getCurrent() {
		setLastRetrieved(new Date());
		return current;
	}
	public void setCurrent(int current) {
		setLastUpdated(new Date());
		this.current = current;
	}
	public int getProgress() {
		setLastRetrieved(new Date());
		return progress;
	}
	public void setProgress(int progress) {
		this.progress = progress;
	}

	public Date getLastRetrieved() {
		return lastRetrieved;
	}
	public void setLastRetrieved(Date lastRetrieved) {
		this.lastRetrieved = lastRetrieved;
	}
	public Date getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

}


