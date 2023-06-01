package com.docmgmt.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfDestination;
import com.itextpdf.text.pdf.PdfOutline;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.SimpleBookmark;

public class OutLineTree {

	/** The resulting PDF. */
	public static final String RESULT = "D:/outline_tree.pdf";

	static ArrayList<DTOWorkSpaceNodeDetail> tree;
	/** An XML representing the outline tree */

	/**
	 * Creates a PDF document.
	 * 
	 * @param filename
	 *            the path to the new PDF document
	 * @throws DocumentException
	 * @throws IOException
	 * @throws SQLException
	 */

	PdfOutline root;
	PdfOutline movieBookmark;
	PdfOutline link;
	String title;
	PdfWriter writer;
	Document document = new Document();

	public void createPdf(String filename) throws IOException,
			DocumentException, SQLException {

		// // step 2
		// writer = PdfWriter
		// .getInstance(document, new FileOutputStream(filename));
		// writer.setViewerPreferences(PdfWriter.PageModeUseOutlines);
		// // step 3
		// document.open();
		// // step 4
		// root = writer.getRootOutline();

		tree = new ArrayList<DTOWorkSpaceNodeDetail>();

		// first argument->NodeId
		// second->NodeName
		// third -> ParentNodeId

		tree.add(getDTO(1, "Root", 0));
		tree.add(getDTO(239, "Node-1", 1));
		tree.add(getDTO(242, "Node-2", 239));
		tree.add(getDTO(243, "Node-3", 239));
		tree.add(getDTO(244, "Node-4", 242));
		tree.add(getDTO(245, "Node-5", 243));
		tree.add(getDTO(246, "Node-6", 245));
		tree.add(getDTO(247, "Node-4.1", 244));
		tree.add(getDTO(248, "Node-4.2", 244));

		// displayTree(tree.get(0));
		
		calendar.put("Title", "Calendar");
		outline.add(calendar);

		 processBookmarkTree2(tree.get(0), 0,calendar);

		//outline.add(map);
		//map.put("Title", "Root");

	//	processBookmarkTree3(tree.get(0), 0);

		PdfReader reader = new PdfReader(new FileInputStream("D://big.pdf"));
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(
				"D://test1.pdf"));
		stamper.setOutlines(outline);
		stamper.close();
		reader.close();

		document.close();
		// Close the database connection

	}

	private void processBookmarkTree(DTOWorkSpaceNodeDetail dto, int level,
			PdfOutline root) throws DocumentException {

		ArrayList<DTOWorkSpaceNodeDetail> childs = selectChild(dto.getNodeId());
		if (level == 0)
			root = new PdfOutline(root, new PdfDestination(PdfDestination.FITH,
					writer.getVerticalPosition(true)),
					dto.getNodeDisplayName(), true);

		
		for (DTOWorkSpaceNodeDetail obj : childs) {

			link = new PdfOutline(root, new PdfDestination(PdfDestination.FITH,
					writer.getVerticalPosition(true)),
					obj.getNodeDisplayName(), true);
			document.add(new Paragraph(obj.getNodeDisplayName()));

			processBookmarkTree(obj, level + 1, link);

		}
		document.newPage();

	}

	ArrayList<HashMap<String, Object>> outline = new ArrayList<HashMap<String, Object>>();

	HashMap<String, Object> map = new HashMap<String, Object>();
	ArrayList<HashMap<String, Object>> kids = new ArrayList<HashMap<String, Object>>();

	ArrayList<HashMap<String, Object>> kids1 = new ArrayList<HashMap<String, Object>>();

	int branch = 1;

	private void processBookmarkTree2(DTOWorkSpaceNodeDetail dto, int level, HashMap<String, Object> calendar2) throws DocumentException {

		ArrayList<DTOWorkSpaceNodeDetail> childs = selectChild(dto.getNodeId());
		ArrayList<HashMap<String, Object>> days = new ArrayList<HashMap<String, Object>>();
		calendar2.put("Kids",days);
		for (DTOWorkSpaceNodeDetail obj : childs) {
			HashMap<String, Object> day = new HashMap<String, Object>();
			 day.put("Title", obj.getNodeDisplayName());
			 day.put("Action", "GoTo");
	         day.put("Page", String.format("%d XYZ 0 0", 1));
			
			days.add(day);
			// document.add(new Paragraph(obj.getNodeDisplayName()));

			processBookmarkTree2(obj, level + 1,day);


		}
		
	//	calendar.put("Kids", days);
		// document.newPage();

	}
	
	HashMap<String, Object> calendar = new HashMap<String, Object>();

	
	private void processBookmarkTree3(DTOWorkSpaceNodeDetail dto, int level) throws DocumentException {
		
		HashMap<String, Object> day = new HashMap<String, Object>();
		day.put("Title", "Monday");
		HashMap<String, Object> day1 = new HashMap<String, Object>();
		day1.put("Title", "Tue");
		
		ArrayList<HashMap<String, Object>> days = new ArrayList<HashMap<String, Object>>();
		calendar.put("Kids", days);
		days.add(day);
		days.add(day1);
		
		
		
		//------
		HashMap<String, Object> hour = new HashMap<String, Object>();
		HashMap<String, Object> hour1 = new HashMap<String, Object>();
		hour.put("Title", "10 AM");
		hour1.put("Title", "20 AM");

		
		ArrayList<HashMap<String, Object>> hours = new ArrayList<HashMap<String, Object>>();
		hours.add(hour);
		hours.add(hour1);
		day.put("Kids", hours);

		
	}
	
	private static ArrayList<DTOWorkSpaceNodeDetail> selectChild(int nodeID) {
		ArrayList<DTOWorkSpaceNodeDetail> list = new ArrayList<DTOWorkSpaceNodeDetail>();
		for (int i = 0; i < tree.size(); i++) {
			if (tree.get(i).getParentID() == nodeID) {
				list.add(tree.get(i));
			}
		}
		return list;
	}

	public static DTOWorkSpaceNodeDetail getDTO(int nodeId, String nodeName,
			int parentID) {
		DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
		dto.setNodeId(nodeId);
		dto.setNodeDisplayName(nodeName);
		dto.setParentID(parentID);

		return dto;
	}

	public void createXml(String src, String dest) throws IOException {
		PdfReader reader = new PdfReader(src);
		List<HashMap<String, Object>> list = SimpleBookmark.getBookmark(reader);
		SimpleBookmark.exportToXML(list, new FileOutputStream(dest),
				"ISO8859-1", true);
		reader.close();
	}

	/**
	 * Main method.
	 * 
	 * @param args
	 *            no arguments needed
	 * @throws DocumentException
	 * @throws IOException
	 * @throws SQLException
	 */
	public static void main(String[] args) throws IOException,
			DocumentException, SQLException {
		
		
		System.out.println(NumberFormat.getNumberInstance(Locale.US).format(1171906 ));
		OutLineTree example = new OutLineTree();
		//example.createPdf(RESULT);
		// example.createXml(RESULT, RESULTXML);
	}
}