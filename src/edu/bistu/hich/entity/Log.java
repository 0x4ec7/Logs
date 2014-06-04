package edu.bistu.hich.entity;

import java.sql.Timestamp;

import edu.bistu.hich.entity.Enum.CallType;
import edu.bistu.hich.entity.Enum.LogState;
import edu.bistu.hich.entity.Enum.RecordFlagState;
import edu.bistu.hich.entity.Enum.RecordState;

/** 
 * @ClassName: Log 
 * @Description: log entity class, mapping database table "logs"
 * @author 仇之东   hich.cn@gmail.com 
 * @date May 17, 2014 5:10:51 PM 
 *  
 */ 
@SuppressWarnings("deprecation")
public class Log {
	private int id;
	private String name;
	private String number;
	private int duration;
	private long date;
	private CallType callType;
	private String uid;
	private RecordFlagState recordFlag;
	private LogState logState;
	private RecordState recordState;
	private Timestamp addDate;
	
	public Log(int id, String name, String number, int duration, long date,
			CallType callType, String uid, RecordFlagState recordFlag,
			LogState logState, RecordState recordState, Timestamp addDate) {
		super();
		this.id = id;
		this.name = name;
		this.number = number;
		this.duration = duration;
		this.date = date;
		this.callType = callType;
		this.uid = uid;
		this.recordFlag = recordFlag;
		this.logState = logState;
		this.recordState = recordState;
		this.addDate = addDate;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public RecordFlagState isRecordFlag() {
		return recordFlag;
	}

	public void setRecordFlag(RecordFlagState recordFlag) {
		this.recordFlag = recordFlag;
	}

	public LogState getLogState() {
		return logState;
	}

	public void setLogState(LogState logState) {
		this.logState = logState;
	}

	public RecordState getRecordState() {
		return recordState;
	}

	public void setRecordState(RecordState recordState) {
		this.recordState = recordState;
	}

	public Timestamp getAddDate() {
		return addDate;
	}

	public void setAddDate(Timestamp addDate) {
		this.addDate = addDate;
	}

}
