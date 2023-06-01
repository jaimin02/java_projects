package com.docmgmt.test;

import java.io.FileOutputStream;
import java.util.logging.Logger;

import org.pdfclown.Version;
import org.pdfclown.VersionEnum;
import org.pdfclown.documents.Document;
import org.pdfclown.documents.Page;
import org.pdfclown.documents.PageAnnotations;
import org.pdfclown.documents.Document.PageLayoutEnum;
import org.pdfclown.documents.Document.PageModeEnum;
import org.pdfclown.documents.interaction.actions.Action;
import org.pdfclown.documents.interaction.actions.GoToDestination;
import org.pdfclown.documents.interaction.annotations.Annotation;
import org.pdfclown.documents.interaction.annotations.Link;
import org.pdfclown.documents.interaction.navigation.document.Bookmark;
import org.pdfclown.documents.interaction.navigation.document.Bookmarks;
import org.pdfclown.documents.interaction.navigation.document.Destination;
import org.pdfclown.documents.interaction.navigation.document.Destination.ModeEnum;
import org.pdfclown.documents.interaction.viewer.ViewerPreferences;
import org.pdfclown.files.File;
import org.pdfclown.files.SerializationModeEnum;
import org.pdfclown.objects.PdfObjectWrapper;

import com.itextpdf.text.pdf.PdfAction;
import com.itextpdf.text.pdf.PdfDestination;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

public class BookmarksParsingSample {

	/**
	 * @param args
	 */
	/**
	 * {@link Logger} instance.
	 */
	private static final Logger logger = Logger.getLogger(Wizard.class
			.getName());

	/**
	 * @see {@link SerializationModeEnum}
	 */
	private final SerializationModeEnum serializationMode = SerializationModeEnum.Incremental;
	/**
	 * Total number of modified files.
	 */
	private int fileCount;
	/**
	 * Total number of modified bookmarks.
	 */
	private int bookmarkCountGlobal;
	/**
	 * Number of modified bookmarks within the current processed PDF file.
	 */
	private int bookmarkCountLocal;

	/**
	 * Directory or file to work with.
	 */
	private java.io.File root;
	/**
	 * <i>Filename&lt;infix&gt;.pdf</i> for copies, <code>null</code> if the
	 * original document will be overwritten.
	 */
	private String filenameInfix;
	/**
	 * Zoom to apply to all bookmarks.
	 */
	private Double zoom;
	/**
	 * Mode to apply to all bookmarks.
	 */
	private ModeEnum mode;
	/**
	 * Version number for serialization, <code>null</code> if the original
	 * version will be inherited.
	 */
	private Version version;

	static final int Fit_page = 1;
	static final int Actual_size = 2;
	static final int Fit_Width = 3;
	static final int Fit_Visible = 4;
	static final int Inherit_zoom = 5;

	static final int[] versions = { 0, 1, 2, 3, 4, 5, 6, 7 };
	
	//version 4=4.0 , 5=5.0 ......

	public static void main(String[] args) {
		// TODO Auto-generated method stub

//		BookmarksParsingSample b = new BookmarksParsingSample(new java.io.File(
//				"D:/vijay/bookmark.pdf"), "pdf", Inherit_zoom, versions[5]);

		BookmarksParsingSample b1 = new BookmarksParsingSample();

		 b1.changeViewerPreference();

	}

	public BookmarksParsingSample() {

	}

	public BookmarksParsingSample(java.io.File root, String filenameInfix,
			int zoom, int version1) {
		this.root = root;
		this.filenameInfix = filenameInfix;
		computeVersion(version1);
		computeZoom(zoom);
		
		try {
			File pdf = new File("D:/vijay/bookmark.pdf");
			Document document = pdf.getDocument();

			document.setVersion(version);

		//	document.setPageLayout(PageLayoutEnum.TwoColumnLeft);

			// SinglePage = SinglePage

			// One Column = continues
			// TwoColumnLeft =default

			// TwoColumnRight = Continuous-facing
			// TwoPageLeft= default

			// TwoPageRight=facing

			ViewerPreferences view = new ViewerPreferences(document); // Instantiates
			// viewer
			// preferences
			// inside
			view.setFitWindow(true);
			view.setCenterWindow(true);// function.
			
			java.io.File output = new java.io.File("D:/vijay/bookmark8.pdf");

			modifyBookmarks(document.getBookmarks());

			System.out.println(document.getBookmarks().size());
			
			System.out.println(document.getPageLayout().getName());
			
			modifyHyperLink(document);
			document.setPageLayout(PageLayoutEnum.SinglePage);
			document.setPageMode(PageModeEnum.Simple);

			pdf.save(output, serializationMode);

			pdf.close();

		} catch (Exception e) {

			e.printStackTrace();
			// TODO: handle exception
		}

		// computeVersion(version);

	}

	
	//Used to set version for pdf
	
	private void computeVersion(int version) {
		switch (version) {
		case 0:
			this.version = VersionEnum.PDF10.getVersion();
			break;
		case 1:
			this.version = VersionEnum.PDF11.getVersion();
			break;
		case 2:
			this.version = VersionEnum.PDF12.getVersion();
			break;
		case 3:
			this.version = VersionEnum.PDF13.getVersion();
			break;
		case 4:
			this.version = VersionEnum.PDF14.getVersion();
			break;
		case 5:
			this.version = VersionEnum.PDF15.getVersion();
			break;
		case 6:
			this.version = VersionEnum.PDF16.getVersion();
			break;
		case 7:
			this.version = VersionEnum.PDF17.getVersion();
			
		}
	}

	private void computeZoom(int zoom) {
		switch (zoom) {
		case Fit_page:
			mode = ModeEnum.Fit;
			break;
		case Actual_size:
			this.zoom = 1.0;
			mode = ModeEnum.XYZ;
			break;
		case Fit_Width:
			mode = ModeEnum.FitHorizontal;
			break;
		case Fit_Visible:
			this.zoom = 0.0;
			mode = ModeEnum.FitBoundingBoxHorizontal;
			break;
		case Inherit_zoom:
			mode = ModeEnum.XYZ;
		}
	}

	//Used to change properties of all hyperlinks to inherit zoom 
	private void modifyHyperLink(Document document) {
		
		for (Page page : document.getPages()) {

			PageAnnotations annotations = page.getAnnotations();
			if (annotations == null) {
				System.out.println("No annotations here.");
				continue;
			}

			boolean linkFound = false;
			for (Annotation annotation : annotations) {
				if (annotation instanceof Link) {
					linkFound = true;
					Link link = (Link) annotation;

					PdfObjectWrapper<?> target = link.getTarget();
					if (link.getTarget() instanceof GoToDestination<?>) {
						try{
						Destination destination = ((GoToDestination<?>) link
								.getTarget()).getDestination();

						destination.setMode(mode);
						destination.setZoom(zoom);
						
						System.out.println("yes");
						
						} catch (Exception e) {
							e.printStackTrace();
							// TODO: handle exception
						}
					}
					
					if (target instanceof Destination) {
						System.out.println("dest");
					} else if (target instanceof Action) {
						
						
						
						System.out.println("Action");
					} else if (target == null) {
						System.out.println("[not available]");
					} else {
						System.out.println("[unknown type: "
								+ target.getClass().getSimpleName() + "]");
					}

				}

			}

		}

		// for(Action action:actions){

		// }

	}

	//Used to change properties of all bookmark to inherit zoom 
	private void modifyBookmarks(Bookmarks bookmarks) {
		for (Bookmark bookmark : bookmarks) {
			// TODO Change to bookmark.getBookmarks().isEmpty when it's
			// implemented.
			if (bookmark.getBookmarks().size() != 0) {
				modifyBookmarks(bookmark.getBookmarks());
			}

			if (bookmark.getTarget() instanceof GoToDestination<?>) {
				// FIXME PDFs containing bookmarks with broken destinations
				// sometimes don't serialize.
				try {
					Destination destination = ((GoToDestination<?>) bookmark
							.getTarget()).getDestination();

					destination.setMode(mode);
					destination.setZoom(zoom);
					bookmarkCountGlobal++;
					bookmarkCountLocal++;
					logger.fine("Successfully set \"" + bookmark.getTitle()
							+ "\" to use mode " + mode + " and zoom " + zoom
							+ ".");
				} catch (Exception e) {
					logger.severe("\"" + bookmark.getTitle()
							+ "\" has a broken destination.");
				}
			}
		}
	}

	
	//set pageLayout to default and Magnification to default
	private void changeViewerPreference() {
		try {
			PdfReader reader = new PdfReader("D:/vijay/bookmark5.pdf");
			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(
					"D:/vijay/view1.pdf"));

			PdfAction action = PdfAction.gotoLocalPage(1, new PdfDestination(
					PdfDestination.XYZ, 0, 0, 0.0f), stamper.getWriter());
			stamper.getWriter().setOpenAction(action);
				
			 stamper.setViewerPreferences(PdfWriter.FitWindow);
			 stamper.setViewerPreferences(PdfWriter.PageModeUseOutlines);
			// stamper.addViewerPreference(PdfName.PRINTSCALING, PdfName.NONE);
			// stamper.addViewerPreference(PdfName.VIEWERPREFERENCES, new
			// PdfNumber(1));
			stamper.close();
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void changeMagnification()
	{
		try {
			PdfReader reader = new PdfReader("D:/vijay/view.pdf");
			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(
					"D:/vijay/view1.pdf"));

			PdfAction action = PdfAction.gotoLocalPage(1, new PdfDestination(
					PdfDestination.XYZ, 0, 0, 0.0f), stamper.getWriter());
			
			
			stamper.getWriter().setOpenAction(action);
			// stamper.setViewerPreferences(PdfWriter.PageModeFullScreen);
			// stamper.addViewerPreference(PdfName.PRINTSCALING, PdfName.NONE);
			// stamper.addViewerPreference(PdfName.VIEWERPREFERENCES, new
			// PdfNumber(1));
			stamper.close();
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
