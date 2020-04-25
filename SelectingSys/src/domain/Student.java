package domain;

public class Student {
	private String num;
	private String name;
	private int cls;
	private String contact;
	
	public Student(String num, String name, int cls, String contact) {
		super();
		this.num = num;
		this.name = name;
		this.cls = cls;
		this.contact = contact;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCls() {
		return cls;
	}

	public void setCls(int cls) {
		this.cls = cls;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
	
	
	
}
