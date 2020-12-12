/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.utils;

import com.app.constants.MyConstants;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
}
