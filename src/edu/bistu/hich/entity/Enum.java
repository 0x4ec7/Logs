package edu.bistu.hich.entity;

/**
 * @ClassName: Enum 
 * @Description: Enumeration 
 * @author 仇之东   hich.cn@gmail.com 
 * @date May 17, 2014 4:45:48 PM 
 *
 */
public class Enum {
	
	/**
	 * @ClassName: RecordFlagState 
	 * @Description: the state of record's existence
	 * @author 仇之东  hich.cn@gmail.com 
	 * @date May 17, 2014 4:46:51 PM 
	 *
	 */
	public enum RecordFlagState{
		NOT_EXIST, EXIST;
		
		public static RecordFlagState fromInt(int value){
			if (value == NOT_EXIST.ordinal()) {
				return NOT_EXIST;
			} else {
				return EXIST;
			}
		}
	}
	
	/**
	 * @ClassName: RecordState 
	 * @Description: the state of record's storage
	 * <p>LOCAL: record exists in external storage, and flag of the record is EXIST 
	 * <p>DELETED: record exists in external storage, and flag of the record is EXIST, but the record state flag in database had been set to deleted
	 * @author 仇之东   hich.cn@gmail.com 
	 * @date May 17, 2014 4:47:59 PM 
	 */
	public enum RecordState{
		LOCAL, DELETED;
		
		public static RecordState fromInt(int value){
			if (value == LOCAL.ordinal()) {
				return LOCAL;
			} else {
				return DELETED;
			}
		}
	}
	
	/**
	 * @ClassName: LogState 
	 * @Description: the state of log 
	 * <p>NOT_DELETED: call log exists both in call log content and database 
	 * <p>ONLY_LOCAL: call log exists only in database 
	 * <p>BOTH_DELETED: call log had been deleted both in call log content and database 
	 * @author 仇之东   hich.cn@gmail.com 
	 * @date May 17, 2014 5:05:17 PM 
	 */
	@Deprecated
	public enum LogState{
		NOT_DELETED, ONLY_LOCAL, BOTH_DELETED
	}
	
	/**
	 * @ClassName: CallType 
	 * @Description: the type of call  
	 * @author 仇之东   hich.cn@gmail.com  
	 * @date May 17, 2014 5:09:04 PM 
	 * 
	 */
	public enum CallType{
		INCOMING_CALL, OUTGOING_CALL, MISSED, UNKNOWN;
		
		public static CallType fromInt(int value){
			if (value == INCOMING_CALL.ordinal()) {
				return INCOMING_CALL;
			} else if (value == OUTGOING_CALL.ordinal()) {
				return OUTGOING_CALL;
			} else if (value == MISSED.ordinal()) {
				return MISSED;
			} else {
				return UNKNOWN;
			}
		}

	}
	
}
