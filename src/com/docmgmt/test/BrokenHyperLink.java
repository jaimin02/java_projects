package com.docmgmt.test;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Vector;

import org.pdfbox.pdfparser.PDFParser;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.pdmodel.PDPage;
import org.pdfbox.pdmodel.common.PDRectangle;
import org.pdfbox.pdmodel.common.filespecification.PDFileSpecification;
import org.pdfbox.pdmodel.interactive.action.type.PDAction;
import org.pdfbox.pdmodel.interactive.action.type.PDActionLaunch;
import org.pdfbox.pdmodel.interactive.action.type.PDActionURI;
import org.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.pdfbox.pdmodel.interactive.annotation.PDAnnotationLink;

public class BrokenHyperLink {

	public static final int FIND_FIRST_LINK = 0;
	public static final int FIND_ALL_LINKS = 1;
	public static final int FIND_FIRST_BROKEN_LINK = 2;
	public static final int FIND_ALL_BROKEN_LINKS = 3;
	private Vector filesContainingBrokenLinks;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BrokenHyperLink b = new BrokenHyperLink();
		b.searchFilesContainingBrokenLinks("");
	}

	public Vector searchFilesContainingBrokenLinks(String sequenceNoFolderPath) {

		filesContainingBrokenLinks = new Vector();

		try {
			System.out.println("fffff");
			File sequenceNoFolder = new File(sequenceNoFolderPath);
			searchSubDirectoriesAndFilesForHyperlinks(sequenceNoFolder);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return filesContainingBrokenLinks;
	}

	private void searchSubDirectoriesAndFilesForHyperlinks(File parentDirectory)
			throws Exception {

		File[] children = parentDirectory.listFiles();

		for (int i = 0; i < children.length; i++) {
			if (children[i].isDirectory()) {
				// System.out.println(children[i].getAbsolutePath());
				searchSubDirectoriesAndFilesForHyperlinks(children[i]);
			} else {

				// System.out.println(children[i].getName());

				if (children[i].getName().endsWith(".pdf")) {
					Vector brokenLinks = findBrokenLinksInFile(children[i],
							FIND_FIRST_BROKEN_LINK);

					if (brokenLinks.size() > 0) {

						filesContainingBrokenLinks.addElement(children[i]
								.getAbsolutePath());
					}
				}

			}

		}// for end

	}

	public Vector findBrokenLinksInFile(String filewithpath, int mode) {

		File file = new File(filewithpath);
		return findBrokenLinksInFile(file, mode);
	}

	public Vector findBrokenLinksInFile(File file, int mode) {

		Vector brokenLinks = new Vector();

		Vector fileLinks = getLinks(file, FIND_ALL_LINKS);

		for (int k = 0; k < fileLinks.size(); k++) {
			String[] link = (String[]) fileLinks.get(k);
			StringBuffer linkReferencePath = new StringBuffer(link[1]);

			// int depth = 0;
			File refFileParent = file.getParentFile();
			boolean isFileReference = false;
			while (true)// find the base directory for the reference file path
			// in the link
			{
				if (linkReferencePath.substring(0, 3).equals("../")) {
					// depth++;
					isFileReference = true;

					refFileParent = refFileParent.getParentFile();
					linkReferencePath = linkReferencePath.delete(0, 3);

					// System.out.println(depth);
					// System.out.println(linkReferencePath);
				} else
					break;

			}
			if (!isFileReference) // to check whether it is a web reference...
			{
				StringBuffer temp = new StringBuffer(linkReferencePath);

				// System.out.println("temp::::::"+temp);
				if (!temp.toString().contains("http:")
						&& !temp.toString().contains("www.")
						&& !temp.toString().contains(".com")
						&& !temp.toString().contains(".gov")
						&& !temp.toString().contains(".net")
						&& !temp.toString().contains(".org")) {

					temp = temp.delete(temp.lastIndexOf("."), temp.length());
					if (temp.indexOf(".") == -1) {
						isFileReference = true;
					}
				}
			}
			// refFileParent =
			// findParentDirectoryForReferencePath(file.getParentFile(), depth);
			// System.out.println("Reference Path ::: "+refFileParent.getAbsolutePath()+" ::: "+linkReferencePath.toString());
			if (isFileReference) {
				File refrenceFile = new File(refFileParent.getAbsolutePath()
						+ "/" + linkReferencePath.toString());

				if (!refrenceFile.exists()) {
					// System.out.println("File Does Not Exists ::: "+refrenceFile.getAbsolutePath());
					brokenLinks.addElement(link);

					if (mode == FIND_FIRST_BROKEN_LINK) {
						return brokenLinks;
					}

				}
			}
		}// for end
		return brokenLinks;
	}

	public Vector getLinks(String fileWithPath, int mode) {

		File f = new File(fileWithPath);
		return getLinks(f, mode);
	}

	public Vector getLinks(File f, int mode) {
		// System.out.println(f.getAbsolutePath());
		Vector foundLinks = new Vector();

		try {
			PDFParser parser = new PDFParser(new FileInputStream(f));
			parser.parse();

			PDDocument doc = parser.getPDDocument();

			List pages = null;
			pages = doc.getDocumentCatalog().getAllPages();

			if (!(pages == null || pages.isEmpty())) {

				for (int i = 0; i < pages.size(); i++) {
					PDPage page = (PDPage) pages.get(i);
					List annotations = page.getAnnotations();

					Vector pageLinks = new Vector();

					String pageno = "" + (i + 1);

					for (int j = 0; j < annotations.size(); j++) {
						PDAnnotation annot = (PDAnnotation) annotations.get(j);
						if (annot instanceof PDAnnotationLink) {
							PDAnnotationLink link = (PDAnnotationLink) annot;
							PDAction action = link.getAction();

							PDRectangle rect = link.getRectangle();
							// need to reposition link rectangle to match text
							// space
							float x = rect.getLowerLeftX();
							float y = rect.getUpperRightY();
							float width = rect.getWidth();
							float height = rect.getHeight();

							int rotation = page.findRotation();
							if (rotation == 0) {
								PDRectangle pageSize = page.findMediaBox();
								y = pageSize.getHeight() - y;
							} else if (rotation == 90) {
								// do nothing
							}

							// System.out.println("x::"+x+"y::"+y+"width::"+width+"height::"+height);

							if (action instanceof PDActionLaunch
									|| action instanceof PDActionURI) {
								String[] linkdetail = new String[5];

								if (action instanceof PDActionLaunch) {
									final PDActionLaunch launch = (PDActionLaunch) action;
									PDFileSpecification file = launch.getFile();
									String strfile = file.getFile();
									// System.out.println("Link Reference File->"+strfile);

									linkdetail[1] = strfile; // destination file
																// path

								} else {
									final PDActionURI uri = (PDActionURI) action;
									final String strURI = uri.getURI();

									// System.out.println("Link Reference File->"+strURI);
									linkdetail[1] = strURI; // destination file
															// path
								}

								linkdetail[0] = pageno; // page no
								linkdetail[2] = "" + x; // x co-ordinate
								linkdetail[3] = "" + y; // y co-ordinate
								linkdetail[4] = "" + (j + 1); // original
																// sequence no
																// of link

								pageLinks.addElement(linkdetail);

								if (mode == FIND_FIRST_LINK) {
									doc.close();
									return pageLinks;
								}

							}

						}
					}
					// System.out.println("pagelinks size :::"+pageLinks.size());

					// int pageLinksSize = pageLinks.size();
					for (int k = 0; k < pageLinks.size(); k++) {

						String[] strK = (String[]) pageLinks.get(k);
						float xK = Float.parseFloat(strK[2]);
						float yK = Float.parseFloat(strK[3]);
						// System.out.println("loop k :::"+k+":::xK->"+xK+":::yK->"+yK);
						for (int l = k + 1; l < pageLinks.size(); l++) {

							String[] strL = (String[]) pageLinks.get(l);
							float xL = Float.parseFloat(strL[2]);
							float yL = Float.parseFloat(strL[3]);
							// System.out.println("loop l :::"+l+":::xL->"+xL+":::yL->"+yL);
							if (yK > yL) {
								pageLinks.setElementAt(strL, k); // swapping of
																	// elements
								pageLinks.setElementAt(strK, l); //
								strK = strL;
								xK = xL;
								yK = yL;
							} else if (yK == yL) {
								if (xK > xL) {
									pageLinks.setElementAt(strL, k); // swapping
																		// of
																		// elements
									pageLinks.setElementAt(strK, l); //
									strK = strL;
									xK = xL;
								}

							}

						}
					}
					for (int a = 0; a < pageLinks.size(); a++) {
						String[] b = (String[]) pageLinks.get(a);
						// System.out.println(":::"+b[2]+":::"+b[3]+":::"+b[1]);
					}

					// System.out.println();
					for (int m = 0; m < pageLinks.size(); m++) {
						String[] strM = (String[]) pageLinks.get(m);
						float xM = Float.parseFloat(strM[2]);
						float yM = Float.parseFloat(strM[3]);

						if (m != pageLinks.size() - 1) {
							String[] strM1 = (String[]) pageLinks.get(m + 1);
							float xM1 = Float.parseFloat(strM1[2]);
							float yM1 = Float.parseFloat(strM1[3]);

							if ((xM == xM1) && (yM == yM1)) {
								pageLinks.removeElementAt(m);
							}
						}

						String[] temp = (String[]) pageLinks.get(m);
						/*
						 * for(int f1 = 0; f1 < temp.length; f1++) {
						 * System.out.print(":::"+temp[f1]); }
						 */
						// System.out.println();
						foundLinks.addElement(pageLinks.get(m));
					}
				}
				// System.out.println("foundLinks.size:::"+foundLinks.size());

			}
			doc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return foundLinks;

	} // getLinks end

}
