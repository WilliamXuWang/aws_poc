package com.example.aws.demo.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Service;

import com.example.aws.demo.config.Constant;

@Service
public class UploadFileService {

	/**   
	 * @从制定URL下载文件并保存到指定目录 
	 * @param filePath 文件将要保存的目录   
	 * @param method 请求方法，包括POST和GET   
	 * @param url 请求的路径   
	 * @return   
	 */    
	public String saveUrlAs(String filePath){
		String url = filePath;
	    String fileName = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.length());     //为下载的文件命名  
	    System.out.println(fileName);
	    String fileBasePath = Constant.FILEBASEPATH + fileName;      //保存目录  
	     //创建不同的文件夹目录    
	     File file=new File(filePath);    
	     //判断文件夹是否存在    
	     if (!file.exists())    
	    {    
	        //如果文件夹不存在，则创建新的的文件夹    
	         file.mkdirs();    
	    }    
	     FileOutputStream fileOut = null;    
	     HttpURLConnection conn = null;    
	     InputStream inputStream = null;    
	     try    
	    {    
	         // 建立链接    
	         URL httpUrl=new URL(url);    
	         conn=(HttpURLConnection) httpUrl.openConnection();    
	         //以Post方式提交表单，默认get方式    
	         conn.setRequestMethod(Constant.METHOD);    
	         conn.setDoInput(true);      
	         conn.setDoOutput(true);    
	         // post方式不能使用缓存     
	         conn.setUseCaches(false);    
	         //连接指定的资源     
	         conn.connect();    
	         //获取网络输入流    
	         inputStream=conn.getInputStream();    
	         BufferedInputStream bis = new BufferedInputStream(inputStream);    
	         //写入到文件（注意文件保存路径的后面一定要加上文件的名称） 
	         System.out.println(fileName);
	         fileOut = new FileOutputStream(fileBasePath);    
	         BufferedOutputStream bos = new BufferedOutputStream(fileOut);    
	             
	         byte[] buf = new byte[4096];    
	         int length = bis.read(buf);    
	         //保存文件    
	         while(length != -1)    
	         {    
	             bos.write(buf, 0, length);    
	             length = bis.read(buf);    
	         }    
	         bos.close();    
	         bis.close();    
	         conn.disconnect();    
	    } catch (Exception e)    
	    {    
	         e.printStackTrace();    
	         System.out.println("抛出异常！！");    
	    }    
	         
	     return fileBasePath;    
	         
	 } 
	
}
