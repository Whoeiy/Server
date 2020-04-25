package domain;

public class User {
	private String num;
	private String pswd;

	public User(String num, String pswd) {
		super();
		this.num = num;
		this.pswd = pswd;
	}
	
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getPswd() {
		return pswd;
	}

	public void setPswd(String pswd) {
		this.pswd = pswd;
	}
}
