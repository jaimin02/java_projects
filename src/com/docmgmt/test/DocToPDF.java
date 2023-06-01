package com.docmgmt.test;

import java.io.IOException;


public class DocToPDF {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String fileName = "C:\\Tomcat 7.0\\bin\\restart.bat";
        String[] commands = {"cmd", "/c", "start", "\"DummyTitle\"",fileName};
        Runtime.getRuntime().exec(commands);
		
		/*
		POIFSFileSystem fs = null;
		Document document = new Document();
		try {
			System.out.println("Starting the test");
			
			//D:\vijay\doctopdf
			fs = new POIFSFileSystem(new FileInputStream("D:/vijay/doctopdf/test.doc"));

			HWPFDocument doc = new HWPFDocument(fs);
			WordExtractor we = new WordExtractor(doc);

			OutputStream file = new FileOutputStream(new File("D:/vijay/doctopdf/test.pdf"));

			PdfWriter writer = PdfWriter.getInstance(document, file);

			Range range = doc.getRange();
			document.open();
			writer.setPageEmpty(true);
			document.newPage();
			writer.setPageEmpty(true);

			String[] paragraphs = we.getParagraphText();
			for (int i = 0; i < paragraphs.length; i++) {

				org.apache.poi.hwpf.usermodel.Paragraph pr = range
						.getParagraph(i);
				// CharacterRun run = pr.getCharacterRun(i);
				// run.setBold(true);
				// run.setCapitalized(true);
				// run.setItalic(true);
				paragraphs[i] = paragraphs[i].replaceAll("\\cM?\r?\n", "");
				System.out.println("Length:" + paragraphs[i].length());
				System.out.println("Paragraph" + i + ": "
						+ paragraphs[i].toString());

				// add the paragraph to the document
				document.add(new Paragraph(paragraphs[i]));
			}

			System.out.println("Document testing completed");
		} catch (Exception e) {
			System.out.println("Exception during test");
			e.printStackTrace();
		} finally {
			// close the document
			document.close();
		}
	*/
	}

}
