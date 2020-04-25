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


/**
 * Servlet implementation class LoginServlet
 */
//@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
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
    public LoginServlet() {
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
		
		System.out.println("Connected");
		
		String num = request.getParameter("num").trim();
		String pswd = request.getParameter("pswd").trim();
		
		System.out.println(num + "," + pswd);
		
		String uri = "jdbc:mysql://localhost:3306/SelectingSys?"+"user=root&password=Reborn22&characterEncoding=gb2312";
		Connection con;
		CallableStatement cstmt;
		ResultSet rs;
		String backNews;
		int flag = 0;

		num = handleString(num);
		pswd = handleString(pswd);
		
		try {
			con = DriverManager.getConnection(uri);
			cstmt = con.prepareCall("{ call proc_login(?,?,?) }");
			cstmt.setString(1, num);
			cstmt.setString(2, pswd);
			cstmt.registerOutParameter(3, Types.INTEGER);
			rs = cstmt.executeQuery();
			
			// flag表示用户的身份：0-not found 1-student 2-teacher
			flag = cstmt.getInt(3);
			if(flag == 0) {
				backNews = "用户不存在，请注册！";
			}else {
				String m = "0";
				if(rs.next()) {
					m = rs.getString(1);
				}
				System.out.println(m);
				if(m.equals("1")) {	//密码正确
					backNews = "登录成功";
				}
				else {
					backNews = "密码错误，请重新输入！";
				}
				con.close();
			}
		}catch(SQLException exp){
			exp.printStackTrace();
			backNews = "数据库连接失败";
		}
		String str = backNews + "," + flag;
		PrintWriter out = response.getWriter();
		out.write(str);
		response.flushBuffer();
		out.close();
	}
}
