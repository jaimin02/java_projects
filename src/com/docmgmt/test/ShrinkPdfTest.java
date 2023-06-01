package com.docmgmt.test;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;

import javax.imageio.ImageIO;

import org.apache.poi.openxml4j.exceptions.InvalidOperationException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PRStream;
import com.itextpdf.text.pdf.PdfArray;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfCopy.PageStamp;
import com.itextpdf.text.pdf.parser.PdfImageObject;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

public class ShrinkPdfTest {

	
	public void resize() throws Exception {
		PdfReader reader = new PdfReader("D:/test/scaned4.pdf");
		Document doc = new Document(PageSize.LEGAL, 0, 0, 0, 0);
		PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(
				"D:/test/scaned_out4.pdf"));
		doc.open();
		PdfContentByte cb = writer.getDirectContent();
		for (int i = 1; i <= reader.getNumberOfPages(); i++) {
			doc.newPage();
			PdfImportedPage page = writer.getImportedPage(reader, i);

			System.out.println(doc.getPageSize().getRotation());
			// doc.setPageSize(page.getWidth() <= page.getHeight() ? PageSize.A4
			// : PageSize.A4.rotate());
			// doc.newPage();

			float widthFactor = doc.getPageSize().getWidth() / page.getWidth();
			float heightFactor = doc.getPageSize().getHeight()
					/ page.getHeight();
			float factor = Math.min(widthFactor, heightFactor);

			System.out.println(page.getWidth());
			System.out.println(page.getHeight());
			System.out.println(doc.getPageSize().getWidth());
			System.out.println(doc.getPageSize().getHeight());

			try {
				float offsetX = (doc.getPageSize().getWidth() - (page
						.getWidth() * factor)) / 2;
				float offsetY = (doc.getPageSize().getHeight() - (page
						.getHeight() * factor)) / 2;

				System.out.println("Factor->" + factor + "===>" + offsetX
						+ "==>" + offsetY);
				AffineTransform af = new AffineTransform();
				af.scale(0.9, 0.9);

				af.rotate(180);

				// af.rotate()

				// af = new AffineTransform(1, 0, 0, 1, 0, 2);

				cb.addTemplate(page, af);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		doc.close();
	}

	public void resize2() throws IOException, DocumentException {

		PdfReader reader = new PdfReader("D:/test/nonscaned.pdf");
		Document document = new Document(PageSize.A4, 36, 36, 54, 36);

		System.out.println(reader.isTampered());

		PdfCopy copy = new PdfCopy(document, new FileOutputStream(
				"D:/test/result.pdf"));
		PageStamp stamp;
		document.open();
		PdfImportedPage page;
		int pow = 0;
		float offsetX, offsetY, factor;
		Rectangle pageSize = reader.getPageSize(1);
		Rectangle newSize = (pow % 2) == 0 ? new Rectangle(pageSize.getWidth(),
				pageSize.getHeight()) : new Rectangle(pageSize.getHeight(),
				pageSize.getWidth());
		Rectangle unitSize = new Rectangle(pageSize.getWidth(), pageSize
				.getHeight());
		int n = (int) Math.pow(2, pow);
		int r = (int) Math.pow(2, pow / 2);
		int c = n / r;
		Rectangle currentSize;
		for (int i = 1; i <= reader.getNumberOfPages(); i++) {
			page = copy.getImportedPage(reader, i);

			int rot = reader.getPageRotation(i);
			System.out.println(rot);
			stamp = copy.createPageStamp(page);
			PdfContentByte pcb = stamp.getOverContent();

			currentSize = reader.getPageSize(++i);
			factor = Math.min(unitSize.getWidth() / currentSize.getWidth(),
					unitSize.getHeight() / currentSize.getHeight());
			offsetX = unitSize.getWidth() * ((i % n) % c)
					+ (unitSize.getWidth() - (currentSize.getWidth() * factor))
					/ 2f;
			offsetY = newSize.getHeight()
					- (unitSize.getHeight() * (((i % n) / c) + 1))
					+ (unitSize.getHeight() - (currentSize.getHeight() * factor))
					/ 2f;

			pcb.addTemplate(page, factor, 0, 0, 0.85F, offsetX, offsetY + 60);
			stamp.alterContents();
			copy.addPage(page);

		}
		reader.close();
		document.close();

	}

	public void manipulatePdf(String src, String dest, int pow)
			throws IOException, DocumentException {

		src = "D:/test/test7.pdf";
		dest = "D:/test/resu2.pdf";
		pow = 0;

		PdfReader reader = new PdfReader(src);

		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document,
				new FileOutputStream(String.format(dest)));
		document.open();
		PdfContentByte cb = writer.getDirectContent();
		PdfImportedPage page;

		float offsetX, offsetY, factor;
		int total = reader.getNumberOfPages();
		for (int i = 0; i < total;) {
			page = writer.getImportedPage(reader, ++i);

			Rectangle pageSize = new Rectangle(page.getWidth(), page
					.getHeight());

			document.setPageSize(reader.getPageSizeWithRotation(i));
			// document.setPageSize(reader.getPageSize(i));
			document.newPage();

			float widthFactor = document.getPageSize().getWidth()
					/ page.getWidth();
			float heightFactor = document.getPageSize().getHeight()
					/ page.getHeight();
			factor = Math.min(widthFactor, heightFactor);

			offsetX = (document.getPageSize().getWidth() - (page.getWidth() * factor)) / 2;
			offsetY = (document.getPageSize().getHeight() - (page.getHeight() * factor)) / 2;

			System.out.println(factor);
			System.out.println("Rotation->" + reader.getPageRotation(i));

			int rotation = reader.getPageRotation(i);

			float pageWidth = reader.getPageSizeWithRotation(i).getWidth();
			float pageHeight = reader.getPageSizeWithRotation(i).getHeight();

			switch (rotation) {
			case 0:
				cb.addTemplate(page, 1f, 0, 0, 0.85f, 0, 60);
				// cb.addTemplate(page, 1f, 0, 0, 1f, 0, 0);
				
				writer.setPageSize(new Rectangle(pageWidth, pageHeight));

				break;

			case 90:
				cb.addTemplate(page, 0, -1f, 0.85f, 0, 60, pageHeight);
				// cb.addTemplate(page, 0, -1f, 1f, 0, 0, pageHeight);

				break;

			case 180:
				// writer.setPageSize(new Rectangle(pageWidth,pageHeight));

				cb.addTemplate(page, -1f, 0, 0, -0.85f, pageWidth,
						pageHeight - 60);
				break;

			case 270:
				cb.addTemplate(page, 0, 1f, -0.85f, 0, pageWidth - 60, 0);

				// cb.addTemplate(page, 0, 0.9f, -1f, 0, pageWidth, 60);

				break;

			default:
				throw new InvalidOperationException(String.format(
						"Unexpected page rotation: [{0}].", rotation));

			}

			/*
			 * 
			 * 
			 * //http://stackoverflow.com/questions/3579058/rotating-pdf-in-c-sharp
			 * -using-itextsharp
			 */
			// cb.addTemplate(page, factor, 0, 0, 0.85F, offsetX, offsetY + 60);
			// cb.addTemplate(page, 0, 0);

		}
		document.close();
	}

	public void resize3() throws IOException, DocumentException {
		PdfName key = new PdfName("ITXT_SpecialId");
		PdfName value = new PdfName("123456789");
		PdfReader reader = new PdfReader("D:/test/scaned4.pdf");
		int n = reader.getXrefSize();
		PdfObject object;
		PRStream stream;
		for (int i = 0; i < n; i++) {
			object = reader.getPdfObject(i);

			if (object == null || !object.isStream())
				continue;
			stream = (PRStream) object;
			if (value.equals(stream.get(key))) {
				PdfImageObject image = new PdfImageObject(stream);
				BufferedImage bi = image.getBufferedImage();
				if (bi == null)
					continue;
				int width = (int) (bi.getWidth() * 0.5);
				int height = (int) (bi.getHeight() * 0.5);
				BufferedImage img = new BufferedImage(width, height,
						BufferedImage.TYPE_INT_RGB);
				AffineTransform at = AffineTransform.getScaleInstance(0.5, 0.5);
				Graphics2D g = img.createGraphics();
				g.drawRenderedImage(bi, at);
				ByteArrayOutputStream imgBytes = new ByteArrayOutputStream();
				ImageIO.write(img, "JPG", imgBytes);

				stream.setData(imgBytes.toByteArray(), false,
						PRStream.NO_COMPRESSION);
				stream.put(PdfName.TYPE, PdfName.XOBJECT);
				stream.put(PdfName.SUBTYPE, PdfName.IMAGE);
				stream.put(key, value);
				stream.put(PdfName.FILTER, PdfName.DCTDECODE);
				stream.put(PdfName.WIDTH, new PdfNumber(width));
				stream.put(PdfName.HEIGHT, new PdfNumber(height));
				stream.put(PdfName.BITSPERCOMPONENT, new PdfNumber(8));
				stream.put(PdfName.COLORSPACE, PdfName.DEVICERGB);
			}
		}
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(
				"D:/test/test2.pdf"));
		stamper.close();

	}

	public void resize4() {
		try {
			Document document = new Document();
			PdfReader reader = new PdfReader("d:/test/scaned4.pdf");
			PdfWriter write = PdfWriter.getInstance(document,
					new FileOutputStream("D:/test/test4.pdf"));
			document.open();
			System.out.println(reader.isTampered());
			PdfImportedPage page = write.getImportedPage(reader, 1);

			AffineTransform af;
			af = new AffineTransform(1, 0, 0.4, 1, -750, -650);

			af.scale(0.5, 0.5);
			// cb.addTemplate(page,af);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void parsePdf(String pdf, String txt) throws IOException {
		PdfReader reader = new PdfReader(pdf);
		PdfReaderContentParser parser = new PdfReaderContentParser(reader);
		PrintWriter out = new PrintWriter(new FileOutputStream(txt));
		TextExtractionStrategy strategy;
		for (int i = 1; i <= reader.getNumberOfPages(); i++) {
			strategy = parser.processContent(i,
					new SimpleTextExtractionStrategy());
			out.println(strategy.getResultantText());
		}
		out.flush();
		out.close();
		reader.close();
	}

	public void manipulatePdf(String src, String dest) throws IOException,
			DocumentException {
		PdfReader reader = new PdfReader(src);
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
		int n = reader.getNumberOfPages();
		PdfDictionary page;
		PdfArray crop;
		PdfArray media;
		for (int p = 1; p <= n; p++) {
			page = reader.getPageN(p);
			media = page.getAsArray(PdfName.CROPBOX);
			if (media == null) {
				media = page.getAsArray(PdfName.MEDIABOX);
			}
			crop = new PdfArray();
			crop.add(new PdfNumber(0));
			crop.add(new PdfNumber(0));
			crop.add(new PdfNumber(media.getAsNumber(2).floatValue() / 2));
			crop.add(new PdfNumber(media.getAsNumber(3).floatValue() / 2));
			page.put(PdfName.MEDIABOX, crop);
			page.put(PdfName.CROPBOX, crop);
			stamper.getUnderContent(p)
					.setLiteral("\nq 0.5 0 0 0.5 0 0 cm\nq\n");
			stamper.getOverContent(p).setLiteral("\nQ\nQ\n");
		}
		stamper.close();
		reader.close();
	}

	public void manipulatePdf2(String src, String dest) throws IOException,
			DocumentException {
		PdfReader reader = new PdfReader(src);
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
		int n = reader.getNumberOfPages();
		float percentage = 0.8f;
		for (int p = 1; p <= n; p++) {
			float offsetX = (reader.getPageSize(p).getWidth() * (1 - percentage)) / 2;
			float offsetY = (reader.getPageSize(p).getHeight() * (1 - percentage)) / 2;
			// stamper.getUnderContent(p).setLiteral(
			// String.format("\nq %s 0 0 %s %s %s cm\nq\n", percentage,
			// percentage, offsetX, offsetY));
			// stamper.getUnderContent(p).setLiteral(
			// String.format("\nq 1 0 0 %s 0 %s cm\nq\n", percentage, offsetY));
			//            
			stamper.getUnderContent(p).setLiteral(
					String.format("\nq 1 0 0 %s 0 %s cm\nq\n", percentage,
							offsetY));

			// stamper.getOverContent(p).addTemplate(stamper.getImportedPage(reader,
			// p), 0, -1f, 0.85f, 0, 60,
			// reader.getPageSizeWithRotation(p).getHeight());
			stamper.getOverContent(p).setLiteral("\nQ\nQ\n");
		}
		stamper.close();
		reader.close();
	}

	public void manipulatePdf3(String src, String dest) throws IOException,
			DocumentException, IllegalArgumentException, IllegalAccessException {
		PdfReader reader = new PdfReader(src);
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
		int n = reader.getNumberOfPages();
		float percentage = 0.85f;
		Field f;
		try {
//			f = reader.getClass().getDeclaredField(
//			"encrypted");
//		
//			f.setAccessible(true);
//			f.set(reader, false);
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block

			e1.printStackTrace();
					
		}
	
		for (int p = 1; p <= n; p++) {

			int rotation = reader.getPageRotation(p);
			
			float pageWidth = reader.getPageSizeWithRotation(p).getWidth();
			float pageHeight = reader.getPageSizeWithRotation(p).getHeight();
			
			//pagewidth=300 =100%
			float scaleX=((130*100)/pageWidth)/100;
			float scaleY=((130*100)/pageHeight)/100;
			System.out.println("ScaleX->"+scaleX + "   = ScaleY->"+scaleY);

			float offsetX = (pageWidth * (1 - percentage)) / 2;
			float offsetY = (pageHeight * (1 - percentage)) / 2;
			
			if(pageWidth>pageHeight)
				percentage=((130*100)/pageHeight)/100;
			else
				percentage=((130*100)/pageWidth)/100;
			
			offsetX = (pageWidth *  percentage) / 2;
			offsetY = (pageHeight * percentage) / 2;

			percentage=1-percentage;
		
			switch (rotation) {
			case 0:
				
				stamper.getUnderContent(p).setLiteral(
						String.format("\nq %s 0 0 %s %s %s cm\nq\n", percentage,
								percentage, offsetX, offsetY));
	
				break;

			case 90:
				
				stamper.getUnderContent(p).setLiteral(
						String.format("\nq 0 %s %s 0 %s %s cm\nq\n",-percentage,percentage,offsetX,
								pageHeight-offsetY));

				break;

			case 180:	
			
				stamper.getUnderContent(p).setLiteral(
						String.format("\nq %s 0 0 %s %s %s cm\nq\n",-percentage,-percentage,pageWidth-offsetX,
								pageHeight-offsetY));
				
				break;

			case 270:
				
				stamper.getUnderContent(p).setLiteral(
						String.format("\nq 0 %s %s 0 %s %s cm\nq\n",percentage, -percentage,pageWidth-offsetX,
								offsetY));
							
				break;

			default:
				System.out.println("TestError");
				throw new InvalidOperationException(String.format(
						"Unexpected page rotation: [{0}].", rotation));

			}
			stamper.getOverContent(p).setLiteral("\nQ\nQ\n");

		}
		stamper.close();
		reader.close();
	}
	
	
	
	public static void main(String arg[]) throws Exception {
		ShrinkPdfTest spt = new ShrinkPdfTest();

	
		// spt.resize2();
		// spt.manipulatePdf("a", "b", 2);

		String src = "D:/test/diff/7.pdf";
		String dest = "D:/test/diff/Output.pdf";
		spt.manipulatePdf3(src, dest);

		// spt.resize2();
		// spt.parsePdf("D:/test/scaned_out.pdf","test");

	}
	
	
	public void manulationPdf4(String src,String dest)  throws IOException, DocumentException
	{

		PdfReader reader = new PdfReader(src);
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
		int n = reader.getNumberOfPages();
		float percentage = 0.85f;

	
		for (int p = 1; p <= n; p++) {

		

			int rotation = reader.getPageRotation(p);

			float pageWidth = reader.getPageSizeWithRotation(p).getWidth();
			float pageHeight = reader.getPageSizeWithRotation(p).getHeight();
			
			//pagewidth=300 =100%
			float scaleX=((100*100)/pageWidth)/100;
			float scaleY=((100*100)/pageHeight)/100;
			
	
			
			System.out.println("Scale->"+scaleX + "   = Width->"+scaleY);
			
			
			
//			float offsetX = (pageWidth * (1 - percentage)) / 2;
//			float offsetY = (pageHeight * (1 - percentage)) / 2;
			
			
			float offsetX = (pageWidth * (1 - percentage)) / 2;
			float offsetY = (pageHeight * (1 - percentage)) / 2;
			
			
		
			

			System.out.println("Rotation->" + rotation);
			switch (rotation) {
			case 0:
				ColumnText.showTextAligned(stamper
						.getOverContent(p),
						Element.ALIGN_CENTER, new Phrase("test"), 20,100, 0);
			
				stamper.getUnderContent(p).setLiteral(
						String.format("\nq %s 0 0 %s %s %s cm\nq\n",percentage,percentage,
								offsetX,offsetY));
				
				
				
			
				
//				stamper.getUnderContent(p).setLiteral(
//						String.format("\nq 1 0 0 0.85 0 %s cm\nq\n",
//								scale));
				
				//System.out.println(offsetX +"==>"+offsetY);
				
				
				//	cb.addTemplate(page, 1f, 0, 0, 0.85f, 0, 60);
				break;

			case 90:
				
				stamper.getUnderContent(p).setLiteral(
						String.format("\nq 0 %s %s 0 %s %s cm\nq\n",-percentage,percentage,offsetX,
								pageHeight-offsetY));

				break;

			case 180:
			
				
				stamper.getUnderContent(p).setLiteral(
						String.format("\nq %s 0 0 %s %s %s cm\nq\n", -percentage,
								-percentage, pageWidth - offsetX, pageHeight
										- offsetY));
				
				//	cb.addTemplate(page, -1f, 0, 0, -0.85f, pageWidth,
				//pageHeight - 60);
				System.out.println(offsetX +"==>"+offsetY);
				System.out.println(percentage);
				break;

			case 270:
				
				stamper.getUnderContent(p).setLiteral(
						String.format("\nq 0 %s %s 0 %s %s cm\nq\n",percentage, -percentage,pageWidth-offsetX,
								offsetY));
				
				
				break;

			default:
				
				System.out.println("test-error");
				throw new InvalidOperationException(String.format(
						"Unexpected page rotation: [{0}].", rotation));

			}
			stamper.getOverContent(p).setLiteral("\nQ\nQ\n");

		}
		stamper.close();
		reader.close();
	
	}


}
