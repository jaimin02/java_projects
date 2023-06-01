package com.docmgmt.test;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfAction;
import com.itextpdf.text.pdf.PdfAnnotation;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfCopy.PageStamp;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;

public class MergeWithToc {

    public static final String SRC1 = "D://pdfs/third.pdf";
    public static final String SRC3 = "D://pdfs/second.pdf";
    public static final String SRC2 = "D://pdfs/first1.pdf";
    public static final String SRCBLANK = "D://pdfs/blankpdf.pdf";
    public static final String DEST = "D://pdfs/merged.pdf";
    
    public Map<String, PdfReader> filesToMerge;
    
    public static void main(String[] args) throws IOException, DocumentException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        MergeWithToc app = new MergeWithToc();
        app.createPdf(DEST);
    }
    
    public MergeWithToc() throws IOException {
        filesToMerge = new TreeMap<String, PdfReader>();
        filesToMerge.put("Hello World", new PdfReader(SRC1));
        filesToMerge.put("Movies / Countries", new PdfReader(SRC2));
        filesToMerge.put("Third File", new PdfReader(SRC3));
        
    }
    
    public void createPdf(String filename) throws IOException, DocumentException {
        Map<Integer, String> toc = new TreeMap<Integer, String>();
        Document document = new Document();
        PdfCopy copy = new PdfCopy(document, new FileOutputStream(filename));
        PageStamp stamp;
        document.open();
        int n;
        int pageNo = 0;
    	int pdfCounter = 0;
        PdfImportedPage page;
        Chunk chunk;
        for (Map.Entry<String, PdfReader> entry : filesToMerge.entrySet()) {
            n = entry.getValue().getNumberOfPages();
            toc.put(pageNo + 1, entry.getKey());
            pdfCounter++;
            for (int i = 0; i < n; ) {
                pageNo++;
                page = copy.getImportedPage(entry.getValue(), ++i);
              
                stamp = copy.createPageStamp(page);
                chunk = new Chunk(String.format("Page %d", pageNo));
                if (i == 1){
                	Paragraph pTitle=new Paragraph(""+entry.getValue());
                	//Set font and style
                	Chapter c=new Chapter(pTitle,pageNo);
                	 
                     document.add(c);
                    chunk.setLocalDestination("p" + pageNo);
                }
                ColumnText.showTextAligned(stamp.getUnderContent(),
                        Element.ALIGN_RIGHT, new Phrase(chunk),
                        559, 810, 0);
                stamp.alterContents();
                copy.addPage(page);
            }
        }
        PdfReader reader = new PdfReader(SRCBLANK);
        page = copy.getImportedPage(reader, 1);
        stamp = copy.createPageStamp(page);
        Paragraph p;
        PdfAction action;
        PdfAnnotation link;
        float y = 770;
        ColumnText ct = new ColumnText(stamp.getOverContent());
        ct.setSimpleColumn(36, 36, 559, y);
        for (Map.Entry<Integer, String> entry : toc.entrySet()) {
            p = new Paragraph(entry.getValue());
            p.add(new Chunk(new DottedLineSeparator()));
            p.add(String.valueOf(entry.getKey()));
            ct.addElement(p);
            ct.go();
            action = PdfAction.gotoLocalPage("p" + entry.getKey(), false);
            link = new PdfAnnotation(copy, 36, ct.getYLine(), 559, y, action);
            stamp.addAnnotation(link);
            y = ct.getYLine();
            
            System.out.println(y);
            
        }
        ct.go();
        stamp.alterContents();
        copy.addPage(page);
   /* 
        page = copy.getImportedPage(reader, 1);
        stamp = copy.createPageStamp(page);
       
        y = 770;
       ct = new ColumnText(stamp.getOverContent());
        ct.setSimpleColumn(36, 36, 559, y);
        for (Map.Entry<Integer, String> entry : toc.entrySet()) {
            p = new Paragraph(entry.getValue());
            p.add(new Chunk(new DottedLineSeparator()));
            p.add(String.valueOf(entry.getKey()));
            ct.addElement(p);
            ct.go();
            action = PdfAction.gotoLocalPage("p" + entry.getKey(), false);
            link = new PdfAnnotation(copy, 36, ct.getYLine(), 559, y, action);
            stamp.addAnnotation(link);
            y = ct.getYLine();
            
            System.out.println(y);
            
        }
        ct.go();
        stamp.alterContents();
        
        
        copy.addPage(page);
        */
        document.close();
        for (PdfReader r : filesToMerge.values()) {
            r.close();
        }
        reader.close();
    }
}