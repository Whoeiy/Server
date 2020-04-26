package domain;

public class Choose {
	private String student;
	private String course;
	private String score;
	private String datetime;
	
	public Choose(String student, String course, String score, String datetime) {
		super();
		this.student = student;
		this.course = course;
		this.score = score;
		this.datetime = datetime;
	}

	public String getStudent() {
		return student;
	}

	public void setStudent(String student) {
		this.student = student;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	
	
	
}
