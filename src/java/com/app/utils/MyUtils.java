/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

/**
 *
 * @author DuyNK
 */
public class MyUtils {

    private static final ClassLoader LOADER = Thread.currentThread().getContextClassLoader();

    public static String uploadFile(HttpServletRequest request, String paramName, String uploadDir) throws IOException, ServletException {
        String fileName = "";
        String imageUrl = "";
        Part filePart = request.getPart(paramName);
        try {
            fileName = getFileName(filePart);
            if (fileName != null && !fileName.isEmpty()) {
                String applicationPath = request.getServletContext().getRealPath("");

                InputStream inputStream = null;
                OutputStream outputStream = null;
                try {
                    String basePath = applicationPath + File.separator + uploadDir;
                    // generate randomly output file name with uuid
                    String uuid = UUID.randomUUID().toString();
                    fileName = uuid + "-" + fileName;

                    File dir = new File(basePath);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    String outputPath = basePath + File.separator + fileName;

                    File outputFilePath = new File(outputPath);
                    inputStream = filePart.getInputStream();
                    outputStream = new FileOutputStream(outputFilePath);
                    int read = 0;
                    final byte[] bytes = new byte[1024];
                    while ((read = inputStream.read(bytes)) != -1) {
                        outputStream.write(bytes, 0, read);
                    }
                    imageUrl = uploadDir + File.separator + fileName;
                } catch (Exception e) {
                    e.printStackTrace();
                    imageUrl = "";
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (outputStream != null) {
                        outputStream.close();
                    }
                }
            }

        } catch (Exception e) {
            imageUrl = "";
        }

        return imageUrl;
    }

    public static boolean deteteFile(String filePath) throws IOException, ServletException {
        boolean deleted = false;

        File file = new File(filePath);
        if (file.exists()) {
            deleted = file.delete();
        }

        return deleted;
    }

    private static String getFileName(Part part) {
        final String partHeader = part.getHeader("content-disposition");
        System.out.println("*****partHeader :" + partHeader);
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }

        return null;
    }

    public static String readHTMLFile(String path) throws FileNotFoundException, IOException {
        String content = null;
        StringBuilder contentBuilder = new StringBuilder();

        BufferedReader in = new BufferedReader(new FileReader(path));
        String str;
        while ((str = in.readLine()) != null) {
            contentBuilder.append(str);
        }
        in.close();

        content = contentBuilder.toString();

        return content;
    }

    public static String readTextFile(String path) throws IOException {
        InputStream in = LOADER.getResourceAsStream(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();

        String line;

        while ((line = br.readLine()) != null) {
            sb.append(line + System.lineSeparator());
        }

        return sb.toString();
    }
    
    public static String toNonAccentString(String src){
        src = src.replaceAll("à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ", "a");
        src = src.replaceAll("è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ", "e");
        src = src.replaceAll("ì|í|ị|ỉ|ĩ", "i");
        src = src.replaceAll("ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ", "o");
        src = src.replaceAll("ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ", "u");
        src = src.replaceAll("ỳ|ý|ỵ|ỷ|ỹ", "y");
        src = src.replaceAll("đ", "d");
        src = src.replaceAll("\u0300|\u0301|\u0303|\u0309|\u0323", ""); // Huyền sắc hỏi ngã nặng 
        src = src.replaceAll("\u02C6|\u0306|\u031B", "");
        
        return src;
    }
}
