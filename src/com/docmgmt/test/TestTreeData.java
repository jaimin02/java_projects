package com.docmgmt.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class TestTreeData {

	/**
	 * @param args
	 */

	public static final String RESULT = "D://first_table.pdf";
	public static PdfPCell cell;
	static PdfPTable table;
	static ArrayList<DTOWorkSpaceNodeDetail> tree;

	static int maxLevel = 8;

	public TestTreeData(ArrayList<DTOWorkSpaceNodeDetail> data) {
		tree = data;
	}

	public TestTreeData() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws FileNotFoundException,
			DocumentException {
		// TODO Auto-generated method stub
		tree = new ArrayList<DTOWorkSpaceNodeDetail>();
		tree.add(getDTO(1, "Thailand", 0));

		tree.add(getDTO(2, "Part-I Adminstrative Data and Product Information",
				1));
		tree.add(getDTO(4, "Section-A Introduction", 2));
		tree.add(getDTO(5, "Section-B-Table of Contents", 2));
		tree
				.add(getDTO(
						6,
						"Section-C-Guidence for Administrative Data and Product Information",
						2));
		tree.add(getDTO(7, "1-Application Form", 6));
		tree.add(getDTO(8, "2-Letter of Auhorization (where applicable)", 6));
		tree.add(getDTO(9, "3-Certifications", 6));
		tree.add(getDTO(12, "3-1-For Contract Manufacturing", 9));
		tree.add(getDTO(13, "3-2-For Manufacturing Under License", 9));
		tree.add(getDTO(14, "3-3-For-locally-manufactured", 9));
		tree.add(getDTO(15, "3-4-For-imported-products", 9));
		tree.add(getDTO(10, "4-Labeling", 6));
		tree.add(getDTO(11, "5-Product Information", 6));

		tree.add(getDTO(3, "Part-II-Quality", 1));
		tree.add(getDTO(16, "Section-A-Table of Contents", 3));
		tree.add(getDTO(17, "Section-B-Quality Overall Summary", 3));
		tree.add(getDTO(18, "Section-C-Body of Data", 3));
		tree.add(getDTO(19, "S-Drug Substance", 18));
		tree.add(getDTO(21, "S-1-General Information", 19));
		tree.add(getDTO(22, "S-2-Manufacture", 19));
		tree.add(getDTO(23, "S-3-Characterisation", 19));
		tree.add(getDTO(24, "S-4-Control of Drug Substance", 19));
		tree.add(getDTO(25, "S-5-Reference Standards or Materials", 19));

		tree.add(getDTO(20, "P-Drug Product", 18));
		TestTreeData test = new TestTreeData();

		test.createTable();

	}

	public void createTable() {
		// TODO Auto-generated method stub
		Document document = new Document();
		// step 2
		try {
			PdfWriter.getInstance(document, new FileOutputStream(RESULT));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// step 3
		document.open();
		table = new PdfPTable(maxLevel);
		table.setWidthPercentage(100f);
		float[] columnWidths = new float[maxLevel];

		for (int i = 0; i < maxLevel - 2; i++) {

			columnWidths[i] = 10f;

		}
		columnWidths[maxLevel - 2] = 60;
		columnWidths[maxLevel - 1] = 20;
		try {
			table.setWidths(columnWidths);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			table.setSplitRows(true);
			displayTree(tree.get(0), 0);
			document.add(table);
			document.close();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static DTOWorkSpaceNodeDetail getDTO(int nodeId, String nodeName,
			int parentID) {
		DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
		dto.setNodeId(nodeId);
		dto.setNodeDisplayName(nodeName);
		dto.setParentID(parentID);

		return dto;
	}

	public static void displayTree(DTOWorkSpaceNodeDetail dto, int level)
			throws DocumentException {
		ArrayList<DTOWorkSpaceNodeDetail> childs = selectChild(dto.getNodeId());
		System.out.print(prefix(level));
		System.out.println(dto.getNodeDisplayName() + "->" + level);
		if (level != 0) {

			int i = 0;
			for (i = 0; i < level - 1; i++) {
				cell = new PdfPCell(new Phrase(""));

				//
				cell.setPadding(4);
				if(i==0){
					cell.setBorderWidthLeft(1);
					cell.setBorderWidthRight(1);
					
					cell.setBorderWidthTop(0);
					cell.setBorderWidthBottom(0);
				}
				else if(i!=level-1){
					cell.setBorderWidthLeft(0);
					cell.setBorderWidthRight(1);
					
					cell.setBorderWidthTop(0);
					cell.setBorderWidthBottom(0);
					
				}
				else
					cell.setBorderWidth(1);
				
				// cell.setBorderWidthLeft(1); // cell.setBorderWidthRight(1);
				// //
				// cell.setBorderWidthTop(1; //cell.setBorderWidthBottom(0); //

				table.addCell(cell);
			}

			/*
			 * if (level != 1) { cell = new PdfPCell(new Phrase(""));
			 * cell.setColspan(level); // cell.setBorderWidth(1);
			 * table.addCell(cell);
			 * 
			 * 
			 * }
			 */
			
			
			cell = new PdfPCell(new Phrase(dto.getNodeDisplayName()));

			if(level!=1)
			{
				cell.setBorderWidthLeft(0);
			
			}
			
			if (level == 551) {
				cell.setColspan(maxLevel - level - 1);
			} else
				cell.setColspan(maxLevel - level);

			cell.setPadding(4);
			cell.setBorderWidth(1);

			// cell.setBorderColorBottom(new BaseColor(0, 0, 0, 0.3f));
			// cell.setBorderColorTop(new BaseColor(250, 250, 250));
			// cell.setBackgroundColor(new BaseColor(255, 255, 255));

			table.addCell(cell);

			cell = new PdfPCell(new Phrase("" + level));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setColspan(maxLevel - level);
			cell.setPadding(4);
			cell.setBorderWidth(1);
			// cell.setBorderColorBottom(new BaseColor(0, 0, 0, 0.3f));
			// cell.setBorderColorTop(new BaseColor(250, 250, 250));
			table.addCell(cell);
		}
		for (DTOWorkSpaceNodeDetail obj : childs) {
			displayTree(obj, level + 1);
		}
	}

	public static ArrayList<DTOWorkSpaceNodeDetail> selectChild(int nodeID) {
		ArrayList<DTOWorkSpaceNodeDetail> list = new ArrayList<DTOWorkSpaceNodeDetail>();
		for (int i = 0; i < tree.size(); i++) {
			if (tree.get(i).getParentID() == nodeID) {
				list.add(tree.get(i));
			}
		}
		return list;
	}

	static String prefix(int level) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < level; i++) {
			s.append("----");
		}
		return s.toString();
	}

}
