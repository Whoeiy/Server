package servlet.teacher;

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

import domain.Course;

/**
 * Servlet implementation class AddCourseServlet
 */
//@WebServlet("/AddCourseServlet")
public class AddCourseServlet extends HttpServlet {
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
//    public AddCourseServlet() {
//        super();
        // TODO Auto-generated constructor stub
//    }

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
		response.setCharacterEncoding("UTF-8");
		
		String name, descrip, teachernum;
		int uplimit;
		
		name = request.getParameter("name");
		uplimit = Integer.parseInt(request.getParameter("uplimit"));
		descrip = request.getParameter("descrip");
		teachernum = request.getParameter("teachernum");
		
		name = new String(name.getBytes("iso-8859-1"),"UTF-8");
		descrip = new String(descrip.getBytes("iso-8859-1"),"UTF-8");
		teachernum = new String(teachernum.getBytes("iso-8859-1"),"UTF-8");
		
		Course course = new Course(name, uplimit, descrip, teachernum, 1);

		String uri = "jdbc:mysql://localhost:3306/SelectingSys?"+"user=root&password=Reborn22&characterEncoding=gb2312";
		Connection con;
		CallableStatement cstmt;
		String backNews;
		int flag = 0;
		
		try {
			con = DriverManager.getConnection(uri);
			cstmt = con.prepareCall("{  call proc_th_add_course(?,?,?,?,?) }");
			cstmt.setString(1, course.getName());
			cstmt.setInt(2, course.getUplimit());
			cstmt.setString(3, course.getDescription());
			cstmt.setString(4, course.getTeacher());
			cstmt.registerOutParameter(5, Types.INTEGER);
			
			cstmt.executeQuery();
			
			// 
			flag = cstmt.getInt(5);
			if(flag == 0) {
				backNews = "申请失败";
			}else {
				backNews = "申请成功";
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
