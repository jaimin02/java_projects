package Test;

import java.io.IOException;
import java.sql.SQLException;

import org.pdfbox.exceptions.COSVisitorException;

import com.itextpdf.text.DocumentException;

public class test {

	private static final String CONFIG_FILENAME = "config.properties";
		public static void main(String[] args) throws DocumentException, IOException, ClassNotFoundException, SQLException, COSVisitorException
		{
			String nDisplayName = "Validation Summary Report{Physical,Sign is not required}-1";
			nDisplayName=nDisplayName.substring(nDisplayName.indexOf(0)+1, nDisplayName.indexOf("{"));
			 System.out.println(nDisplayName); 
			 if(nDisplayName.contains("-")){
				 nDisplayName=nDisplayName.substring(nDisplayName.indexOf(0)+1, nDisplayName.indexOf("-"));
			 	 System.out.println(nDisplayName);}
			/*
			String src = "F:\\1-Mahavir\\Appendix-1-Validation_Team_-test_1-Otsuka.pdf";
			String dest = "F:\\1-Mahavir\\Appendix-1-Validation_Team_-test_1-Otsuka_watermark.pdf";		
			PdfReader reader = new PdfReader(src);
		    PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
		    PdfContentByte under = stamper.getUnderContent(1);
		    Font f = new Font(FontFamily.HELVETICA, 35);
		    Phrase p = new Phrase("This watermark is added UNDER the existing content", f);
		    //ColumnText.showTextAligned(under, Element.ALIGN_CENTER, p, 297, 550, 0);
		    PdfContentByte over = stamper.getOverContent(1);
		    //p = new Phrase("This watermark is added ON TOP OF the existing content", f);
		    //ColumnText.showTextAligned(over, Element.ALIGN_CENTER, p, 297, 500, 0);
		    p = new Phrase("DRAFT", f);
		    over.saveState();
		    PdfGState gs1 = new PdfGState();
		    gs1.setFillOpacity(0.5f);
		    over.setGState(gs1);
		    ColumnText.showTextAligned(over, Element.ALIGN_CENTER, p, 297, 450, 20);
		    over.restoreState();
		    stamper.close();
		    reader.close();
			
			File uploadFile=new File("F:\\1-Mahavir\\PDFs\\Validation 1 - Encyrpted.pdf");
			boolean flagtemp=false;
			try{
			PDDocument document = PDDocument.load(uploadFile);
			if(document.isEncrypted())
		    {
		    	flagtemp=true;
		    	System.out.println("is encrypted");
		    }
		    document.close();
			}
			catch (InvalidPasswordException e) {
	            System.out.println("PDF is password protected..");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		    
			
			String sample = "69";
		      char[] chars = sample.toCharArray();
		      StringBuilder sb = new StringBuilder();
		      for(char c : chars){
		         if(Character.isDigit(c)){
		            //sb.append(c);
		         }
		      }
		      System.out.println(sb);
		      
		      for (int i = 0; i < sample.length(); i++) {
		    	  
		            // Check if character is not a digit between 0-9 then return false
		            if (sample.charAt(i) < '0' || sample.charAt(i) > '9') {
		            	sb.append(sample.charAt(i));
		            }
		        }
		      System.out.println(sb);
		      
		      String s="1X24X105 1422.96";
		      String arr[]=s.split(" ");
		      
		      
			
			 String inputFilePath = "F:/1-Mahavir/OQ Knet-CT.pdf"; // Existing file
	           String outputFilePath = "F:/1-Mahavir/OQ Knet-CT_Output.pdf"; // New file
	 
	           OutputStream fos = new FileOutputStream(new File(outputFilePath));
	 
	           PdfReader pdfReader = new PdfReader(inputFilePath);
	           PdfStamper pdfStamper = new PdfStamper(pdfReader, fos);
	           
	        float xCordinates=(float)374.0641;
			float yCordinates=(float) 564.3571;
	        	   //float xCordinates=(float)484.4351;
	        	   //float yCordinates=(float) 583.4024;
			float y2Cordinates=(float) 368.89285;
	        
			 String IMG = "E:\\FileServerDoc\\userSignature\\15\\38acc8c2-3b6c-4b8e-9f2f-78b8edbd46f7\\VB.png";
			 //PdfReader reader = new PdfReader("E:\\FileServerDoc\\workspace\\2022\\3\\22\\0155\\100\\103\\KNET___Aarti___eCTD-Application_Database-FR-22-IQP-01-032022-Production.pdf");
		    //    PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("E:\\FileServerDoc\\CropSignPdf\\0155\\100\\103\\KNET___Aarti___eCTD-Application_Database-FR-22-IQP-01-032022-Production.pdf"));
		        Image image = Image.getInstance(IMG);
		        PdfImage stream = new PdfImage(image, "", null);
		        stream.put(new PdfName("ITXT_SpecialId"), new PdfName("123456789"));
		        PdfIndirectObject ref = pdfStamper.getWriter().addToBody(stream);
		        image.setDirectReference(ref.getIndirectReference());
		        image.scaleToFit(20f, 20f); //Scale image's width and height
		        //image.setAbsolutePosition(xCordinates, y2Cordinates-20);
		        //PdfContentByte pdfContentByte = pdfStamper.getOverContent(14);
		        pdfContentByte.beginText();
		        pdfContentByte.setFontAndSize(BaseFont.createFont(BaseFont.TIMES_BOLD,BaseFont.CP1257,BaseFont.EMBEDDED), 6);
                pdfContentByte.setTextMatrix(xCordinates, y2Cordinates); // set x and y co-ordinates
                pdfContentByte.newlineShowText("Name : : Virendra Barad");
                pdfContentByte.setLeading(5);
                pdfContentByte.newlineShowText("Role : Executive-QA");
                pdfContentByte.setLeading(5);
                pdfContentByte.newlineShowText("Date : 12/7/21 3:32:06 PM.973");
                pdfContentByte.endText();

			
			 //PdfReader reader = new PdfReader("F:\\1-Mahavir\\User-Acceptance-Test (67).pdf");
			  //  FileOutputStream fileOutputStream = new FileOutputStream("F:\\1-Mahavir\\SSPL-SW-FR-10-TC.pdf");
			    try {   

			        //PdfStamper stamper= new PdfStamper(reader, fileOutputStream);           
			        PdfContentByte overContentByte = pdfStamper.getOverContent(14);
			        float[] widthsForTable = { 7.5f, 10 };
					PdfPTable pdfPTable = new PdfPTable(widthsForTable);
			         pdfPTable.setTotalWidth(93);
			         pdfPTable.setLockedWidth(true);
			            //Create cells
			            PdfPCell pdfPCell1 = new PdfPCell();
			            PdfPCell pdfPCell2 = new PdfPCell();
			            //Add cells to table
			            //String IMG = "E:\\FileServerDoc\\userSignature\\15\\38acc8c2-3b6c-4b8e-9f2f-78b8edbd46f7\\VB.png";
						 //Image image = Image.getInstance(IMG);
						 image.scaleToFit(38f, 80f); //Scale image's width and height
			            pdfPCell1.addElement(image);
			            pdfPCell1.setFixedHeight(40f);
			            pdfPCell1.setPaddingTop(8f);
			            pdfPCell1.setVerticalAlignment(Element.ALIGN_TOP);
				       pdfPCell1.setPaddingLeft(1f);
				       pdfPCell1.setPaddingRight(1f);
				       pdfPCell1.setBorder(Rectangle.NO_BORDER);
				       pdfPCell1.setBackgroundColor(new BaseColor(255,255,255));
			            
			            //pdfPCell1.setFixedHeight(100);
			            
			            pdfPTable.addCell(pdfPCell1);
			            
			            Font font2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 4.4f, BaseColor.BLACK);
					      Paragraph p=new Paragraph("Name : "+"xfgxvxcvxc Barad",font2);
					      Paragraph paragraph = new Paragraph("Date :"+"03-Sep-2019",font2);
					      pdfPCell2.setVerticalAlignment(Element.ALIGN_TOP);
					      pdfPCell2.addElement(p);
					      Paragraph roll = new Paragraph("Role : "+"Execyutive QA",font2);
					      pdfPCell2.addElement(roll);
					     // pdfPCell2.addElement(paragraph);
					      pdfPCell2.setFixedHeight(20f);
					      pdfPCell2.setBorder(Rectangle.NO_BORDER);
					      pdfPCell2.setBackgroundColor(new BaseColor(255,255,255));
					  
					      Paragraph SignId = new Paragraph("Sign-Id:"+"d5dd76b1-6e9b-4ce7-81a7-005ea9d2cd38",font2);
					      pdfPCell2.addElement(SignId);
					      
					      Paragraph action = new Paragraph("Action : "+"Send for review",font2);
					      pdfPCell2.addElement(action);
					      
					      Phrase phrase = new Phrase();
					      Chunk chunk = new Chunk("\nName : Virendra Barad \n"
					    		  	+ "Date : 20-Oct-2021 16:49 IST (+05:30 GMT) \n"
					    		  	+ "Role : Executive-QA \n"
						      		+ "Sign-Id: 4381d5f0-4c04-4bb7-9d2c-70c9a04e4d29 \n"
						      		+ "Action : Send For Review",font2);
					      chunk.setAnchor("http://www.canvas.com");
					      phrase.add(chunk);
					      PdfPCell cellOne = new PdfPCell(new Phrase(phrase));
					      //cellOne.setFixedHeight(40f);
					      cellOne.setNoWrap(false);
					      cellOne.setPaddingTop(0f);
					      cellOne.setVerticalAlignment(Element.ALIGN_TOP);
						  cellOne.setBorder(Rectangle.NO_BORDER);
						  cellOne.setBackgroundColor(new BaseColor(255,255,255));
					      //pdfPTable.addCell(pdfPCell2);
						  pdfPTable.addCell(cellOne);
					      //pdfPTable.addCell(pdfPCell2);
						  pdfPTable.writeSelectedRows(0, -1, xCordinates-46.9f, yCordinates+20f, overContentByte);
					     cellOne.setBorder(Rectangle.NO_BORDER);
					     cellOne.setBackgroundColor(new BaseColor(255,255,255));
				        //pdfPTable.addCell(pdfPCell2);
				        pdfPTable.addCell(cellOne);
			            pdfPTable.writeSelectedRows(0, -1, xCordinates-41.9f, yCordinates+10, overContentByte);
			            ColumnText.showTextAligned(pdfStamper.getOverContent(4), Element.ALIGN_CENTER,
			    			new Phrase("*This is an electronically certified true copy."), 30, 12, 0);
						  //pdfContentByte.addImage(image);
			            pdfStamper.close();
			            pdfReader.close();
			    } catch (DocumentException e) {
			        e.printStackTrace();
			    }
			
			
		        
                //pdfContentByte.addImage(image);
               // pdfStamper.close();
                //pdfReader.close();
			
			
			
			//String CONFIG_FILENAME = "config.properties";
//			FileInputStream fis=new FileInputStream("E:\\Repository\\DocStack-v1.2.0\\KnowledgeNET-Test\\src\\com\\docmgmt\\server\\db\\dbcp\\config.properties"); 
		        
		        InputStream is = test.class.getResourceAsStream(CONFIG_FILENAME);
		        Properties dbProps=new Properties (); 
	            dbProps.load(is);
		        
		        String dname= (String) dbProps.get ("database"); 
		        //jdbc:microsoft:sqlserver://90.0.0.15:1433;DatabaseName=DocStack-v1.2.0","sa","Sarjen"
		        String dbDriverName=dbProps.getProperty("driver").trim();
		        String dbUser=dbProps.getProperty("user").trim();
		        String dbPassword=dbProps.getProperty("password").trim();
		        String dbServer=dbProps.getProperty("server").trim();
		        String dbDatabase=dbProps.getProperty("database").trim();
		        String dbPort=dbProps.getProperty("port").trim();
		    
		        String url="jdbc:microsoft:sqlserver://";
		        Class.forName(dbDriverName); 
		        Connection con = DriverManager.getConnection(url+dbServer+":"+dbPort+";DatabaseName="+dbDatabase, dbUser, dbPassword); 
		        Statement stmt = con.createStatement(); 
		        ResultSet rs = stmt.executeQuery("select * from usermst"); 
		        while (rs.next()) { 
		            System.out.println(rs.getString("vUsername") + " " + rs.getString(2) + " "+ rs.getString(3)); 
		        } 
		        rs.close(); 
		        stmt.close(); 
		        con.close(); 
			
			String outFile = "F:\\1-Mahavir\\PDFs\\SSPL-SW-FR-10-TC-1.pdf";
			String inputFilePath = "F:\\1-Mahavir\\PDFs\\SSPL-SW-FR-10-TC-Copy.pdf";
			
			Document document = new Document();
			FileOutputStream fos1 = new FileOutputStream(outFile);
			PdfCopy copy = new PdfCopy(document, fos1);
			document.open();
			PdfImportedPage page;
			PdfCopy.PageStamp stamp;
			Phrase phrase;
			BaseFont bf = BaseFont.createFont();
			PdfReader reader2 = new PdfReader(inputFilePath);
			//Font font1 = new Font(bf, 9);
			int n ;
			
			Rectangle pagesize;
			for (int i = 1; i <= reader2.getNumberOfPages(); i++) {
			    page = copy.getImportedPage(reader2, i);
			    stamp = copy.createPageStamp(page);
			    //phrase = new Phrase("Printed By : "+userName, fontSize_12);
			    pagesize = reader2.getPageSizeWithRotation(i);
			    
			    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("*This is a test"),
			    		488, 335, 0);
			    float x = 1f;
			    x = page.getWidth();
				float y = 40;
			    /*ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER,
				new Phrase("*This is an electronically certified true copy. Certified by:  Mahavir R Patel on: 28-Sep-2021 11:37 "
						+ "IST(+05:30 GMT)"), x / 2, y - 30, 0);

			    stamp.alterContents();
			    copy.addPage(page);
			}
			
			document.close();
			reader2.close();
			fos1.close();
			
			
			try {
	            Calendar currentdate = Calendar.getInstance();
	            String strdate = "27-Dec-2017 21:58";
	           
	            DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
	            Date date1 =(Date)formatter.parse(strdate);
	            TimeZone obj = TimeZone.getTimeZone("EST");

	            formatter.setTimeZone(obj);
	            
	            String theResult =formatter.format(date1);

	           System.out.println("The date and time in :: "+ obj.getDisplayName() + " is ::" + theResult);
	        } catch (ParseException e) {
	           e.printStackTrace();
	        }
	    */}
		}
	
