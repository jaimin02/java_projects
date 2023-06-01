package com.docmgmt.test;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

public class ImageScalingDemo {
    public static void main(String[] args) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document,
                    new FileOutputStream("D://ImageScaling.pdf"));
            document.open();
            //
            // Scale the image to an absolute width and an absolute
            // height
            //
            Image image ;
            String filename = "D://logo1.jpg";
            image = Image.getInstance(filename);
            image.scaleToFit(300f, 200f);
            document.add(image);
            
            image = Image.getInstance(filename);
            image.scaleAbsolute(300f, 200f);
            document.add(image);
                       //
            // Scales the image so that it fits a certain width and
            // height
            //
           
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }
}