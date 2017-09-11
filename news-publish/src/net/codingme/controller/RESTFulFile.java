package net.codingme.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import net.codingme.po.User;
 

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/api/file")
public class RESTFulFile extends HttpServlet {
    private static final long serialVersionUID = 1L;
     
    // 上传文件存储目录
//    private static String UPLOAD_DIRECTORY = "upload";
    private static String UPLOAD_DIRECTORY = "D:\\CodeProgram\\upload";
 
    // 上传配置
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 3; // 3MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
 
    /**
     * 上传数据及保存文件
     */
    protected void doPost(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/html;charset=UTF-8");
    	PrintWriter out = response.getWriter();
    	HttpSession session = request.getSession();
    	User user = (User) session.getAttribute("user");
    	if(user == null){
    		PrintWriter writer = response.getWriter();
    		String json = "{\"result\":\"false\",\"url\":\"请先进行登陆！\"}";
 		    writer.println(json);
 		    writer.flush();
    		return ;
    	}
		// 检测是否为多媒体上传
		if (!ServletFileUpload.isMultipartContent(request)) {
		    // 如果不是则停止
		    PrintWriter writer = response.getWriter();
		    writer.println("Error: 表单必须包含 enctype=multipart/form-data");
		    writer.flush();
		    return;
		}
 
        // 配置上传参数
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 设置内存临界值 - 超过后将产生临时文件并存储于临时目录中
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // 设置临时存储目录
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
 
        ServletFileUpload upload = new ServletFileUpload(factory);
         
        // 设置最大文件上传值
        upload.setFileSizeMax(MAX_FILE_SIZE);
         
        // 设置最大请求值 (包含文件和表单数据)
        upload.setSizeMax(MAX_REQUEST_SIZE);

        // 中文处理
        upload.setHeaderEncoding("UTF-8"); 

        // 构造临时路径来存储上传的文件
        Calendar calendar=Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        // 这个路径相对当前应用的目录
//        String uploadPath = request.getServletContext().getRealPath("./") + File.separator +
//        		UPLOAD_DIRECTORY+File.separator+ year+File.separator + month;
        String uploadPath = UPLOAD_DIRECTORY+File.separator+ year+File.separator + month;
         
        // 如果目录不存在则创建
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
 
        String result = "";
        try {
            // 解析请求的内容提取文件数据
            @SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest(request);
            if (formItems != null && formItems.size() > 0) {
                // 迭代表单数据
                for (FileItem item : formItems) {
                    // 处理不在表单中的字段
                    if (!item.isFormField()) {
                        String fileName = new File(item.getName()).getName();
                        //判断上传类型
                        if(!fileName.endsWith(".jpg") && !fileName.endsWith(".png") && !fileName.endsWith(".gif") &&
                           !fileName.endsWith(".txt")){
                        	result="不允许上传的文件类型！";
                        	break;
                        }
                        String filePath = uploadPath + File.separator + fileName;
                        File storeFile = new File(filePath);
                        //如果已经存在，提示已经存在
                        if(storeFile.exists()){
                        	result = "文件已经存在！";
                        	break;
                        }
                        // 在控制台输出文件的上传路径
                        System.out.println(filePath);
                        // 保存文件到硬盘
                        item.write(storeFile);
                        result = "文件上传成功!";
//                        String fileUrl = "/upload/"+year+"/"+month+"/"+fileName;
                        String fileUrl = "/"+year+"/"+month+"/"+fileName;
                        String json = "{\"result\":\"true\",\"url\":\""+fileUrl+"\"}";
                        out.print(json);
                        return ;
                    }
                }
            }
        } catch (Exception ex) {
        	ex.printStackTrace();
        	result = "上传文件出错！";
        }
        // 跳转到 message.jsp
        String json = "{\"result\":\"false\",\"url\":\""+result+"\"}";
        out.print(json);
    }

    
    /**
     * 下载文件
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String path = request.getParameter("path");
        // path是指欲下载的文件的路径。
    	String realPath = UPLOAD_DIRECTORY+path;
    	
        File file = new File(realPath);
        // 取得文件名。
        String filename = file.getName();
        // 取得文件的后缀名。
        String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();
        // 以流的形式下载文件。
        InputStream fis = new BufferedInputStream(new FileInputStream(realPath));
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        fis.close();
        // 清空response
        response.reset();
        // 设置response的Header
        response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
        response.addHeader("Content-Length", "" + file.length());
        OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
        response.setContentType("application/octet-stream");
        toClient.write(buffer);
        toClient.flush();
        toClient.close();
    }
}