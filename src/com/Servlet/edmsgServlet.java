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

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.jspsmart.upload.SmartUpload;

public class edmsgServlet extends HttpServlet {

	//doget()
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	
	//dopost()
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String baseurl=request.getSession().getServletContext().getRealPath("");//获取项目目录
		String edid="";
		
		//上传图片操作
		SmartUpload su=new SmartUpload();
		su.initialize(this.getServletConfig(),request,response);
		su.setMaxFileSize(1000000);
		su.setTotalMaxFileSize(4000000);
		su.setAllowedFilesList("bmp,jpg,tiff,gif,pcx,tga,exif,fpx,svg,psd,cdr,pcd,dxf,ufo,eps,ai,raw,jpeg,png");
		try {
			su.setDeniedFilesList("exe,bat,jsp,htm,html,,");
			su.upload();
			
			edid=java.net.URLDecoder.decode(su.getRequest().getParameter("edid"),"utf-8");
			String edimgtxt=java.net.URLDecoder.decode(su.getRequest().getParameter("edimgtxt"),"utf-8");
			String edorder=java.net.URLDecoder.decode(su.getRequest().getParameter("edorder"),"utf-8");
			String eddate=java.net.URLDecoder.decode(su.getRequest().getParameter("eddate"),"utf-8");
			String edcontent=java.net.URLDecoder.decode(su.getRequest().getParameter("edcontent"),"utf-8");
			boolean missfileflag=false;if(su.getFiles().getCount()==0){missfileflag=true;}
			for (int i=0;i<su.getFiles().getCount();i++){
				com.jspsmart.upload.File file=su.getFiles().getFile(i);
				if(file.isMissing()){
					continue;
				}else{
					File files=new File(baseurl+"images\\"+edimgtxt);  
					if(files.exists()){files.renameTo(new File(baseurl+"images\\"+edimgtxt+".imgbak"+System.currentTimeMillis()));}
					file.saveAs("/images/"+edid+"."+file.getFileExt());
				}
			} 
			
			SAXReader reader = new SAXReader();Document doc = reader.read(new FileInputStream(new File(baseurl+"story\\lovestory.xml")));//
			Element timemachine = null;
			timemachine=(Element) doc.selectSingleNode("./timemachine");
			Element a = (Element) timemachine.selectSingleNode("(//memory)["+edid+"]");
			//修改节点数据
			List orderc = a.elements("order");
			for(Iterator childs1=orderc.iterator();childs1.hasNext();){Element everyone = (Element)childs1.next();everyone.setText(edorder);}
			List contentc = a.elements("content");
			for(Iterator childs2=contentc.iterator();childs2.hasNext();){Element everyone = (Element)childs2.next();everyone.setText(edcontent);}
			List datec = a.elements("date");
			for(Iterator childs3=datec.iterator();childs3.hasNext();){Element everyone = (Element)childs3.next();everyone.setText(eddate);}
			List imgc = a.elements("img");
			System.out.println(missfileflag);
			if(!missfileflag){for(Iterator childs4=imgc.iterator();childs4.hasNext();){Element everyone = (Element)childs4.next();everyone.setText(edid+"."+su.getFiles().getFile(0).getFileExt());}}
			
			
			BufferedWriter fileWriter = new BufferedWriter( new OutputStreamWriter(new FileOutputStream(baseurl+"story\\lovestory.xml"),"UTF-8"));
			OutputFormat format = OutputFormat.createPrettyPrint();format.setEncoding("utf-8");
			XMLWriter writer = new XMLWriter(System.out, format);
			writer.setWriter(fileWriter);
			writer.write(doc);
			writer.close();
		} catch (Exception e) {e.printStackTrace();} 
	}
}
