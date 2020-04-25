package domain;

public class Course {
	private String name;
	private int uplimit;
	private String description;
	private String teacher;
	private int choosen;
	
	public Course(String name, int uplimit, String description, String teacher, int choosen) {
		super();
		this.name = name;
		this.uplimit = uplimit;
		this.description = description;
		this.teacher = teacher;
		this.choosen = choosen;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getUplimit() {
		return uplimit;
	}
	public void setUplimit(int uplimit) {
		this.uplimit = uplimit;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTeacher() {
		return teacher;
	}
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	public int getChoosen() {
		return choosen;
	}
	public void setChoosen(int choosen) {
		this.choosen = choosen;
	}
	
	
}
