package servlet.student;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Choose;
import domain.Course;

/**
 * Servlet implementation class AddChooseServlet
 */
//@WebServlet("/AddChooseServlet")
public class AddChooseServlet extends HttpServlet {
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
    public AddChooseServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		
		String student, course, time;
		int uplimit;
		
		student= request.getParameter("student");
		course = request.getParameter("course");
		time = request.getParameter("datetime");
		
		student = new String(student.getBytes("iso-8859-1"),"UTF-8");
		course = new String(course.getBytes("iso-8859-1"),"UTF-8");
		time= new String(time.getBytes("iso-8859-1"),"UTF-8");
		
		Choose choose = new Choose(student, course, null, time);

		String uri = "jdbc:mysql://localhost:3306/SelectingSys?"+"user=root&password=Reborn22&characterEncoding=gb2312";
		Connection con;
		CallableStatement cstmt;
		String backNews;
		int flag = 0;
		
		try {
			con = DriverManager.getConnection(uri);
			cstmt = con.prepareCall("{  call proc_stu_add_choose(?,?,?,?) }");
			cstmt.setString(1, choose.getStudent());
			cstmt.setString(2, choose.getCourse());
			cstmt.setString(3, choose.getDatetime());
			cstmt.registerOutParameter(4, Types.INTEGER);
			
			cstmt.executeQuery();
			
			flag = cstmt.getInt(4);
			if(flag == 0) {
				backNews = "选课失败";
			}else {
				backNews = "选课成功";
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

}
