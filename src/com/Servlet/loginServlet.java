package com.Servlet;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.Util.EncryptionUtils;
import com.jspsmart.upload.SmartUpload;


public class loginServlet extends HttpServlet {

	//doget()
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	
	//dopost()
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		
		String lovepass=request.getParameter("lovepass");
		String key = "0001000300010004";
		byte[] keyToByte = EncryptionUtils.hex2byte(key);
		
		//º”√‹
		String edcodeString = EncryptionUtils.Encrypt(lovepass,keyToByte);
		if(edcodeString.equals("3E7441B96D7E97BE214FA7D30D226F87")){
			session.setAttribute("login", "yes");
			out.write("yes");
			out.close();
		}else{
			session.setAttribute("login", "no");
			out.write("no");
			out.close();
		}
	}
}
