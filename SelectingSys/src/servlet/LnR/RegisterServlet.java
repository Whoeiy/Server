package servlet.LnR;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Student;
import domain.Teacher;
import domain.User;

/**
 * Servlet implementation class RegisterServlet
 */
//@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void init(ServletConfig config) throws ServletException{
		
		super.init(config);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch(Exception e){}
	}
	
	public String handleString(String s){
		try {
			byte bb[] = s.getBytes("UTF-8");
			s = new String(bb);
		}
		catch(Exception ee) {}
		return s;
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		Teacher teacher = null;
		Student student = null;
		User user = null;
		String num = handleString(request.getParameter("num"));
		String name = handleString(request.getParameter("name"));
		String pswd = handleString(request.getParameter("pswd"));
		String contact = handleString(request.getParameter("contact"));
		String identity = handleString(request.getParameter("identity"));
		
		if(identity.equals("0")) {		// teacher
			teacher = new Teacher(num, name, contact);
			user = new User(num, pswd);
		}else {		// student
			int cls = Integer.parseInt(request.getParameter("cls"));
			student = new Student(num, name, cls, contact);
			user = new User(num, pswd);
		}
		
		String uri = "jdbc:mysql://localhost:3306/SelectingSys?"+"user=root&password=Reborn22&characterEncoding=gb2312";
		Connection con;
		CallableStatement cstmt;
		String backNews;
		int flag = 0;
		
		try {
			con = DriverManager.getConnection(uri);
			if(identity.equals("0")) {	// teacher
				cstmt = con.prepareCall("{ call proc_register_th(?,?,?,?,?) }");
				cstmt.setString(1, teacher.getNum());
				cstmt.setString(2, teacher.getName());
				cstmt.setString(3, teacher.getContact());
				cstmt.registerOutParameter(4, Types.INTEGER);
				cstmt.setString(5, user.getPswd());
			}else {		// student
				cstmt = con.prepareCall("{ call proc_register_stu(?,?,?,?,?,?) }");
				cstmt.setString(1, student.getNum());
				cstmt.setString(2, student.getName());
				cstmt.setString(3, student.getContact());
				cstmt.registerOutParameter(4, Types.INTEGER);
				cstmt.setInt(5, student.getCls());
				cstmt.setString(6, user.getPswd());
			}
			cstmt.executeQuery();
			
			flag = cstmt.getInt(4);
			if(flag == 0) {
				backNews = "注册失败";
			}else {
				backNews = "注册成功";
				con.close();
			}
		}catch(SQLException exp){
			exp.printStackTrace();
			backNews = "数据库连接失败";
		}
		PrintWriter out = response.getWriter();
		out.write(backNews);
		response.flushBuffer();
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
