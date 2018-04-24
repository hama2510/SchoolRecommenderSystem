/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hust.kien.schoolrecsys.common.util;

import org.primefaces.event.FileUploadEvent;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Administrator
 */
public class FileUploadUtil {

    public static void upload(byte[] bytes, String path, String fileName) throws IOException {
        OutputStream out = new BufferedOutputStream(new FileOutputStream(new File(path + "/" + fileName)));
        out.write(bytes);
        out.close();
    }

    public static String uploadImage(FileUploadEvent event) {
        try {
            String fileName = String.valueOf(new Date().getTime()) + ".jpg";
            byte[] data = event.getFile().getContents();
            String path = System.getProperty("catalina.home") + "\\webapps\\ROOT\\resources\\images";
            FileUploadUtil.upload(ImageUtil.scale(data, 300, 400), path, fileName);
            path = "E:\\Work\\GR\\code\\SchoolRecommendation\\src\\main\\webapp\\resources\\images";
            FileUploadUtil.upload(ImageUtil.scale(data, 300, 400), path, fileName);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Upload thành công"));
            return "/resources/images/" + fileName;
        } catch (Exception ex) {
            Logger.getLogger(FileUploadUtil.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, "Lỗi"));
            return null;
        }
    }

}
