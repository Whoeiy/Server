package domain;

public class Teacher {
	private String num;
	private String name;
	private String contact;
	
	public Teacher(String num, String name, String contact) {
		super();
		this.num = num;
		this.name = name;
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

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
	
	
	
}
