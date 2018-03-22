package com.Servlet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;


public class delmsgServlet extends HttpServlet {

	//doget()
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	
	//dopost()
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String baseurl=request.getSession().getServletContext().getRealPath("");//获取项目目录
		String id=request.getParameter("id");
		
		//将更新的数据写入到xml中
		try {
			SAXReader reader = new SAXReader();Document doc = reader.read(new FileInputStream(new File(baseurl+"story\\lovestory.xml")));
			Element timemachine = null;
			try{timemachine=(Element) doc.selectSingleNode("./timemachine");}catch (Exception ex){System.out.println("异常信息:"+ex);}
			Element root = doc.getRootElement();Element foo;
			for (Iterator i=root.elementIterator("memory");i.hasNext();){
				foo=(Element) i.next();
				if(foo.elementText("id").equals(id)){
					Element a = (Element) timemachine.selectSingleNode("(//memory)["+id+"]");
					File file=new File(baseurl+"images\\"+a.elementText("img"));  
					if(file.exists()){file.renameTo(new File(baseurl+"images\\"+a.elementText("img")+".imgbak"+System.currentTimeMillis()));}
					a.getParent().remove(a);break;
				}
			}
			
			BufferedWriter fileWriter = new BufferedWriter( new OutputStreamWriter(new FileOutputStream(baseurl+"story\\lovestory.xml"),"UTF-8"));
			OutputFormat format = OutputFormat.createPrettyPrint();format.setEncoding("utf-8");
			XMLWriter writer = new XMLWriter(System.out, format);
			writer.setWriter(fileWriter);
			writer.write(doc);
			writer.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
