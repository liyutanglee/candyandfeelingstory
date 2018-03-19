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

import com.jspsmart.upload.SmartUpload;

public class addmsgServlet extends HttpServlet {

	//doget()
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	
	//dopost()
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String baseurl=request.getSession().getServletContext().getRealPath("");//获取项目目录
		String newid="";//存储xml中最后一个id
		try {   
			File f = new File(baseurl+"story\\lovestory.xml");   
			SAXReader reader = new SAXReader();Document doc = reader.read(f);   
			Element root = doc.getRootElement();Element foo;
			if(doc.selectNodes("//memory").size()<0){
				newid="0";
			}else{
				for (Iterator i=root.elementIterator("memory");i.hasNext();){foo=(Element) i.next();newid+=foo.elementText("id")+",";}
				newid=newid.substring(0, newid.length()-1);newid=newid.split(",")[newid.split(",").length-1];
			}
		} catch (Exception e) {e.printStackTrace();}
		int memoryid=Integer.parseInt(newid)+1;
		
		//上传图片操作
		SmartUpload su=new SmartUpload();
		su.initialize(this.getServletConfig(),request,response);
		su.setMaxFileSize(1000000);
		su.setTotalMaxFileSize(4000000);
		su.setAllowedFilesList("bmp,jpg,tiff,gif,pcx,tga,exif,fpx,svg,psd,cdr,pcd,dxf,ufo,eps,ai,raw,jpeg,png");
		try {
			su.setDeniedFilesList("exe,bat,jsp,htm,html,,");su.upload();
			for (int i=0;i<su.getFiles().getCount();i++){com.jspsmart.upload.File file=su.getFiles().getFile(i);if(file.isMissing()) continue;file.saveAs("/images/"+memoryid+"."+file.getFileExt());} 
		} catch (Exception e) {e.printStackTrace();} 
		
	    //页面参数
		String addorder=java.net.URLDecoder.decode(su.getRequest().getParameter("addorder"),"utf-8");
		String adddate=java.net.URLDecoder.decode(su.getRequest().getParameter("adddate"),"utf-8");
		String addcontent=java.net.URLDecoder.decode(su.getRequest().getParameter("addcontent"),"utf-8");
		
		//新数据写入到xml中
		try {
			System.out.println("d:"+baseurl+"story\\lovestory.xml");
			SAXReader reader = new SAXReader();Document doc = reader.read(new FileInputStream(new File(baseurl+"story\\lovestory.xml")));
			Element timemachine = null;
			try{timemachine=(Element) doc.selectSingleNode("./timemachine");}catch (Exception ex){System.out.println("异常信息:"+ex);}
			
			timemachine.addElement("memory");
			Element a = (Element) timemachine.selectSingleNode("(//memory)[last()]");
			a.addElement("id").addText(memoryid+"");
			a.addElement("order").addText(addorder);
			a.addElement("img").addText(memoryid+"."+su.getFiles().getFile(0).getFileExt());
			a.addElement("content").addText(addcontent);
			a.addElement("date").addText(adddate);
			
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
