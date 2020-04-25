package servlet.student;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import domain.Course;

/**
 * Servlet implementation class ShowCourseListServlet
 */
//@WebServlet("/ShowCourseListServlet")
public class ShowCourseListServlet extends HttpServlet {
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
    public ShowCourseListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		
		String uri = "jdbc:mysql://localhost:3306/SelectingSys?"+"user=root&password=Reborn22&characterEncoding=gb2312";
		Connection con;
		CallableStatement cstmt;
		ResultSet rs;
		String backNews;
		Course course = null;
		List<Course> courselist = new ArrayList<Course> ();
		int flag = 0;
		
		try {
			con = DriverManager.getConnection(uri);
			cstmt = con.prepareCall("{  call proc_stu_show_course_list(?) }");
			cstmt.registerOutParameter(1, Types.INTEGER);
			rs = cstmt.executeQuery();
			flag = cstmt.getInt(1);
			
			while(rs.next()) {
				String name = rs.getString("name");
				String descrip = rs.getString("descrip");
				String teacher = rs.getString("teacher");
				int uplimit = rs.getInt("uplimit");
				int chosen = rs.getInt("chosen");
				course = new Course(name, uplimit, descrip, teacher, chosen);
				courselist.add(course);
			}
			
			if(flag == 0) {
				backNews = "连接失败";
			}else{
				backNews = "连接成功";
				con.close();
			}
//			if(courselist.size() == 0) {
//				backNews = "暂无课程";
//			}
		}catch(SQLException exp){
			exp.printStackTrace();
			backNews = "数据库连接失败";
		}
		
		// List<Person> --> jsonArrayString
		JSONArray jsonArr = new JSONArray(courselist);
		String jsonStr = jsonArr.toString();
		System.out.println(jsonStr); 
		
		// 输出响应结果（JSON格式的字符串）
		PrintWriter out = response.getWriter();
		out.write(jsonStr);
		response.flushBuffer();	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
