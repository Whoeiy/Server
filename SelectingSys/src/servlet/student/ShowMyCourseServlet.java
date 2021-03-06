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
 * Servlet implementation class ShowMyCourseServelt
 */
//@WebServlet("/ShowMyCourseServelt")
public class ShowMyCourseServlet extends HttpServlet {
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
    public ShowMyCourseServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setCharacterEncoding("UTF-8");
		

		String student = request.getParameter("student").trim();
		student = new String(student.getBytes("iso-8859-1"),"UTF-8");
		
		String uri = "jdbc:mysql://localhost:3306/SelectingSys?"+"user=root&password=Reborn22&characterEncoding=gb2312";
		Connection con;
		CallableStatement cstmt;
		ResultSet rs;
		String backNews;
		Course course = null;
		int tmp = 0;
		List<Course> courselist = new ArrayList<Course> ();
		int flag = 0;
		
		try {
			con = DriverManager.getConnection(uri);
			cstmt = con.prepareCall("{  call proc_stu_my_choose(?,?) }");
			cstmt.setString(1, student);
			cstmt.registerOutParameter(2, Types.INTEGER);
			rs = cstmt.executeQuery();
			flag = cstmt.getInt(2);
			
			while(rs.next()) {
				String num = rs.getString("num");
				String name = rs.getString("name");
				String descrip = rs.getString("descrip");
				String teacher = rs.getString("teacher");
				course = new Course(num, name, tmp, descrip, teacher, tmp);
				courselist.add(course);
			}
			
			if(flag == 0) {
				backNews = "连接失败";
			}else{
				backNews = "连接成功";
				con.close();
			}
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
