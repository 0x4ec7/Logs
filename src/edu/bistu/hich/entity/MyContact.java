package edu.bistu.hich.entity;

/** 
 * @ClassName: MyContact 
 * @Description: contact entity 
 * @author 仇之东   hich.cn@gmail.com 
 * @date May 19, 2014 10:30:05 PM 
 *  
 */ 
public class MyContact {
	private String name;
	private String number;
	private long date;
	private int count;
	
	public MyContact(String name, String number, long date, int count) {
		super();
		this.name = name;
		this.number = number;
		this.date = date;
		this.count = count;
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

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
}
