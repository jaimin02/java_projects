package com.docmgmt.server.webinterface.services.pdf;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.docmgmt.server.prop.KnetProperties;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfAction;
import com.itextpdf.text.pdf.PdfArray;
import com.itextpdf.text.pdf.PdfDestination;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.SimpleBookmark;

public class PdfPropUtilities {

	private String PdfTempDir = "";
	private String QpdfToolPath = "";
	String tempFile = "";
	String tempFile1 = "";
	private int pdfVersion;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PdfPropUtilities pdfPropUtilObj = new PdfPropUtilities();
		// b1.changeViewerPreference();
		// pdfPropUtilObj.autoCorrectPdfProp("//90.0.0.15\\docmgmtandpub\\3.2.S.3Characterisation11112.pdf");
		pdfPropUtilObj.autoCorrectPdfProp("D:/reference-2.pdf");
		// pdfPropUtilObj.test();

	}

	public PdfPropUtilities() {
		KnetProperties knetProperties = KnetProperties.getPropInfo();
		PdfTempDir = knetProperties.getValue("PdfPropTempDir");
		QpdfToolPath = knetProperties.getValue("QpdfToolService");
	}

	public void autoCorrectPdfProp(String sourceFile) {
		try {
			boolean success = changeViewerPreference(sourceFile);
			if (success)
				linearize("\"" + PdfTempDir + "/" + tempFile1 + "\"", "\""
						+ sourceFile + "\"");
			else {
				tempFile = PdfTempDir + "/"
						+ String.valueOf(System.currentTimeMillis()) + ".pdf";
				linearize("\"" + sourceFile + "\"", "\"" + tempFile + "\"");
				java.io.File outFile = null;
				outFile = new java.io.File(PdfTempDir + "/" + tempFile);
				outFile.renameTo(new java.io.File(sourceFile));
				outFile = null;
				new java.io.File(PdfTempDir + "/" + tempFile).delete();
			}
			new java.io.File(PdfTempDir + "/" + tempFile1).delete();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public void autoCorrectPdfProp(String sourceFile, boolean isTOCFile) {
		try {

			tempFile = PdfTempDir + "/"
					+ String.valueOf(System.currentTimeMillis()) + ".pdf";
			linearize("\"" + sourceFile + "\"", "\"" + tempFile + "\"");
			java.io.File outFile = null;
			outFile = new java.io.File(tempFile);
			new java.io.File(sourceFile).delete();
			boolean isRename=outFile.renameTo(new java.io.File(sourceFile));
			
			if(!isRename)
				FileUtils.moveFile(outFile, new java.io.File(sourceFile));
			
			outFile = null;
			new java.io.File(tempFile).delete();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public void changeLinkZoomLevel(PdfReader reader) {
		try {
			int totalPages = reader.getNumberOfPages();
			for (int count = 0; count < totalPages; count++) {
				PdfDictionary page = reader.getPageN(count + 1);

				PdfArray annots = page.getAsArray(PdfName.ANNOTS);
				if (annots != null) {
					int annotationSize = annots.size();
					for (int i = 0; i < annotationSize; i++) {
						PdfDictionary annotation = annots.getAsDict(i);
						if (PdfName.LINK.equals(annotation
								.getAsName(PdfName.SUBTYPE))) {
							if (annotation.get(PdfName.A) == null)
								continue;
							PdfDictionary action = annotation
									.getAsDict(PdfName.A);
							PdfArray destArray;
							if (action != null) {
								destArray = action.getAsArray(PdfName.D);
								if (destArray != null) {
									if (PdfName.FITH.equals(destArray
											.getAsName(1))) {
										PdfNumber DestY = destArray
												.getAsNumber(2);
										destArray.set(1, PdfName.XYZ);
										destArray.set(2, new PdfNumber(0));
										destArray.add(3, DestY);
										destArray.add(4, new PdfNumber(0));
									} else if (PdfName.FITBH.equals(destArray
											.getAsName(1))) {
										PdfNumber DestY = destArray
												.getAsNumber(2);
										destArray.set(1, PdfName.XYZ);
										destArray.set(2, new PdfNumber(0));
										destArray.add(3, DestY);
										destArray.add(4, new PdfNumber(0));
									} else if (PdfName.FITR.equals(destArray
											.getAsName(1))) {
										destArray.set(1, PdfName.XYZ);
										destArray.set(2, destArray
												.getAsNumber(2));
										destArray.set(3, destArray
												.getAsNumber(5));
										destArray.set(4, new PdfNumber(0));
										destArray.remove(5);
									} else if (PdfName.FIT.equals(destArray
											.getAsName(1))) {
										Rectangle pagesize = reader
												.getPageSizeWithRotation(count + 1);
										destArray.set(1, PdfName.XYZ);
										destArray.add(2, new PdfNumber(0));
										destArray.add(3, new PdfNumber(pagesize
												.getHeight()));
										destArray.add(4, new PdfNumber(0));
									} else if (PdfName.XYZ.equals(destArray
											.getAsName(1))) {

										destArray.set(4, new PdfNumber(0));

									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void changeBookmarkZoomLevel(List<HashMap<String, Object>> list,
			PdfReader reader) {
		for (HashMap<String, Object> entry : list) {

			for (String key : entry.keySet()) {
				// System.out.println(key);
				if ("Kids".equals(key)) {
					Object o = entry.get(key);

					changeBookmarkZoomLevel((List<HashMap<String, Object>>) o,
							reader);
				} else if ("Page".equals(key)) {
					String dest = (String) entry.get(key);
					// System.out.println(dest);
					if (dest.contains("FitR")) {

						String cord[] = dest.substring(dest.indexOf("R"))
								.split(" ");
						entry.put("Page", dest.replaceAll("FitR", "XYZ "
								+ cord[1] + " " + cord[4] + " 0"));
					} else if (dest.contains("FitH")) {
						String cord[] = dest.substring(dest.indexOf("H"))
								.split(" ");
						dest = dest.substring(0, dest.indexOf("H") + 1);
						entry.put("Page", dest.replaceAll("FitH", "XYZ 0 "
								+ cord[1] + " 0"));
						// Problem may be occured when there is rotation
						// entry.put("Page", dest.replaceAll("FitH",
						// "XYZ "+cord[1]+" "
						// + cord[1] + " 0"));

					} else if (dest.contains("XYZ")) {
						String cord[] = dest.substring(dest.indexOf("XYZ"))
								.split(" ");
						entry.put("Page", dest.replaceAll("XYZ", "XYZ "
								+ cord[1] + " " + cord[2] + " 0"));
					} else if (dest.contains("FitBH")) {
						String cord[] = dest.substring(dest.indexOf("FitBH"))
								.split(" ");
						entry.put("Page", dest.replaceAll("FitBH", "XYZ 0 "
								+ cord[1] + " 0"));
					} else if (dest.contains("Fit")) {
						String cord[] = dest.split(" ");
						Rectangle pagesize = reader
								.getPageSizeWithRotation(Integer
										.parseInt(cord[0]));
						// System.out.println("-->"+ pagesize.getHeight());
						// Page=reader.getPageN(pageNum)
						entry.put("Page", dest.replaceAll("Fit", "XYZ 0 "
								+ pagesize.getHeight() + " 0"));
					}
				}
			}
		}
	}

	private synchronized boolean changeViewerPreference(String sourceFile) {
		try {

			tempFile1 = String.valueOf(System.currentTimeMillis()) + ".pdf";
			PdfReader reader = new PdfReader(sourceFile);
			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(
					PdfTempDir + "/" + tempFile1));
			pdfVersion = reader.getPdfVersion();

			List<HashMap<String, Object>> list = SimpleBookmark
					.getBookmark(reader);
			if (list != null && list.size() != 0) {
				changeBookmarkZoomLevel(list, reader);
				stamper.setOutlines(list);
				stamper.setViewerPreferences(PdfWriter.PageModeUseOutlines);
			} else
				stamper.setViewerPreferences(PdfWriter.PageModeUseNone);

			try {
				changeLinkZoomLevel(reader);
				PdfWriter writer = stamper.getWriter();
				PdfAction ac = PdfAction.gotoLocalPage(1, new PdfDestination(
						PdfDestination.XYZ, -1, -1, 0), writer);
				writer.setOpenAction(ac);

			} catch (Exception e) {
				e.printStackTrace();
			}
			// stamper.setViewerPreferences(PdfWriter.FitWindow|PdfWriter.PageModeUseThumbs);
			stamper.close();
			reader.close();
		} catch (Exception e) {
			System.out.println("Error setting preference");
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public void linearize(String sourceFile, String destFile) {

		String command = QpdfToolPath + " --linearize " + sourceFile + " "
				+ destFile + " ";

		if (pdfVersion <= 51) {
			command = QpdfToolPath + " --linearize --force-version=1.4 "
					+ sourceFile + " " + destFile + " ";
		}

		try {
			Runtime rt = Runtime.getRuntime();
			Process pCD = rt.exec(command);
			InputStream stderr = pCD.getErrorStream();
			InputStreamReader isr = new InputStreamReader(pCD.getInputStream());

			// BufferedReader br = new BufferedReader(isr);
			String line = null;

			BufferedReader input = new BufferedReader(new InputStreamReader(pCD
					.getInputStream()));
			while ((line = input.readLine()) != null)
				System.out.println(line);

			int exitVal = pCD.waitFor();
			int len;
			if ((len = pCD.getErrorStream().available()) > 0) {
				byte[] buf = new byte[len];
				pCD.getErrorStream().read(buf);
				System.err.println("Command error:\t\"" + new String(buf)
						+ "\"");
			}

			stderr.close();
			isr.close();
			System.gc();
			System.out.println("Process exitValue: " + exitVal);
		} catch (Exception e) {
			System.out.println("Error...");
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	public void autoCorrectPdfPropFolder(java.io.File folder) {
		// final java.io.File folder = new java.io.File("D:\\vijay\\PDFFiles");
		listFilesForFolder(folder);
	}

	public void listFilesForFolder(final java.io.File folder) {
		for (final java.io.File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else {
				System.out.println("File To Correct->"
						+ fileEntry.getAbsolutePath());
				final String FileToCorrect = fileEntry.getAbsolutePath();

				if (FileToCorrect.endsWith(".pdf")) {
					new Thread(new Runnable() {
						public void run() {

							PdfPropUtilities pdfUtil = new PdfPropUtilities();
							pdfUtil.autoCorrectPdfProp(FileToCorrect);
						}

					}).start();
					try {
						Thread.sleep(2000);

					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	public synchronized int isLinearized(String source) {

		String command = QpdfToolPath + " --check " + source + " ";
		BufferedReader input = null;
		InputStream stderr = null;
		InputStreamReader isrError = null;
		int isLinearized = 1;

		try {
			Runtime rt = Runtime.getRuntime();
			Process pCD = rt.exec(command);
			stderr = pCD.getErrorStream();
			input = new BufferedReader(new InputStreamReader(pCD
					.getInputStream()));
			isrError = new InputStreamReader(stderr);
			String line = null;
			// // wait until process complete
			while ((line = input.readLine()) != null) {
				if (line.equals("File is linearized")) {
					isLinearized = 0;
				}
				System.out.println(line);
			}
			int exitVal = pCD.waitFor();

			if (exitVal == 2) {
				isLinearized = 2;
			}
			// exitVal==0 Success
			System.out.println("Process exitValue: " + exitVal);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			try {
				input.close();
				isrError.close();
				stderr.close();
				System.gc();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return isLinearized;
	}

	boolean isLinearized(PdfReader reader) {
		final PdfName LINEARIZED = new PdfName("Linearized");

		for (int i = 0; i < reader.getXrefSize(); ++i) {
			PdfObject testObj = reader.getPdfObject(i);
			// getPdfObjectRelease()?
			if (testObj.type() == PdfObject.DICTIONARY) {
				if (((PdfDictionary) testObj).contains(LINEARIZED)) {
					return true;
				}
			}

		}
		return false;
	}

}
