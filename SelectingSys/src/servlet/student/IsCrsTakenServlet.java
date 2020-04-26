package servlet.student;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class IsCrsTaken
 */
//@WebServlet("/IsCrsTaken")
public class IsCrsTakenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void init(ServletConfig config) throws ServletException{
		
		super.init(config);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch(Exception e){}
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IsCrsTakenServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		
		String student = request.getParameter("student").trim();
		String course = request.getParameter("course").trim();
		
		student = new String(student.getBytes("iso-8859-1"),"UTF-8");
		course = new String(course.getBytes("iso-8859-1"),"UTF-8");
		
		String uri = "jdbc:mysql://localhost:3306/SelectingSys?"+"user=root&password=Reborn22&characterEncoding=gb2312";
		Connection con;
		CallableStatement cstmt;
		ResultSet rs;
		String backNews;
		
		try {
			con = DriverManager.getConnection(uri);
			cstmt = con.prepareCall("{  call proc_stu_is_course_taken(?,?) }");
			cstmt.setString(1, student);
			cstmt.setString(2, course);
			rs = cstmt.executeQuery();
			
			if(rs.next()) {
				backNews = "taken";
			}else {
				backNews = "nottaken";
			}
			con.close();
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
