package servlet.teacher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servlet implementation class UploadFileServlet
 */
//@WebServlet("/UploadFileServlet")
public class UploadFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadFileServlet() {
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
		String backNews;
		  
	  	ServletInputStream is = request.getInputStream();
	  	String filepath = "/whoeiy/documents/2020";
	  	String filename = "fromTeacher.txt";
	  	File file0 = new File(filepath);
	  		if(!file0.exists()) {
	   		file0.mkdirs();
	 	 }
	  	File file = new File(filepath, filename);
	  	if(!file.exists()) {
	   		file.createNewFile();
	 	 }
	  	FileOutputStream fos = new FileOutputStream(file);
	  	int len = 0;
	  	byte[] buf = new byte[1024];
	 	while((len = is.read(buf)) != -1) {
	  		fos.write(buf, 0, len);
	  	}
	  	fos.flush();
	  	fos.close();
	  	
	  	backNews = "上传成功";
	  	PrintWriter out = response.getWriter();
	  	out.println(backNews);
	  	response.flushBuffer();
		out.close();
	}

}
