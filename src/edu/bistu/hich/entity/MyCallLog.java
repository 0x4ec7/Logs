package edu.bistu.hich.entity;

import edu.bistu.hich.entity.Enum.CallType;

/** 
 * @ClassName: MyCallLog 
 * @Description: my call log entity class, mapping calllog content 
 * @author 仇之东   hich.cn@gmail.com 
 * @date May 17, 2014 5:12:22 PM 
 *  
 */ 
public class MyCallLog {
	private String name;
	private String number;
	private int duration;
	private long date;
	private CallType callType;
	
	public MyCallLog(String name, String number, int duration, long date,
			CallType callType) {
		super();
		this.name = name;
		this.number = number;
		this.duration = duration;
		this.date = date;
		this.callType = callType;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public CallType getCallType() {
		return callType;
	}

	public void setCallType(CallType callType) {
		this.callType = callType;
	}

	@Override
	public String toString() {
		return "MyCallLog [name=" + name + ", number=" + number + ", duration="
				+ duration + ", date=" + date + ", callType=" + callType + "]";
	}
	
}
