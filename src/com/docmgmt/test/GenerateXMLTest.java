package com.docmgmt.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;

import com.docmgmt.dto.PublishAttrForm;
import com.docmgmt.struts.resources.XmlWriter;

public class GenerateXMLTest {
	public StringWriter writer = new java.io.StringWriter();
	public XmlWriter xmlwriter = new XmlWriter(writer);
	public BufferedWriter out;
	public File regionalXml = new File(
			"D:/vijay/Knet_all/test-gcc/gc-regional-test.xml");

	public static void main(String arg[]) throws Exception {
		PublishAttrForm publishForm = new PublishAttrForm();
		publishForm.setAgencyName("agency Name");
		publishForm.setApplicant("applicant");
		publishForm.setApplicationNumber("123");
		publishForm.setApplicationType("app Type");
		publishForm.setAtc("11");
		publishForm.setCountry("india");
		publishForm.setRMSSelected('Y');
		publishForm.setSubmissionType_eu("subType");
		publishForm.setInventedName("Invented");
		publishForm.setProcedureType("Mutual");
		publishForm.setSeqNumber("0000");
		publishForm.setSubmissionDescription("sub desc");
		GenerateXMLTest gxt = new GenerateXMLTest();
		gxt.writeToXmlFile("gcc", publishForm);
	}

	private void writeToXmlFile(String stype, PublishAttrForm publishForm) {

		try {

			regionalXml.getParentFile().mkdirs();
			out = new BufferedWriter(new FileWriter(regionalXml));
			if (stype.equals("gcc")) {
				String xmlDeclaration = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
				String docTypeDeclaration = "<!DOCTYPE eu:eu-backbone SYSTEM \"util/dtd/eu-regional.dtd\">";
				String xslStyleSheetDeclaration = "<?xml-stylesheet type=\"text/xsl\" href=\"util/style/gc-regional.xsl\"?>";
				String XmldtdVersionDeclaration = "<gc:gc-backbone xmlns:gc=\"http://sfda.gov.sa\" xmlns:xlink=\"http://www.w3c.org/1999/xlink\" dtd-version=\"1.4\">";

				out.write(xmlDeclaration);
				out.write("\n" + docTypeDeclaration);
				out.write("\n" + xslStyleSheetDeclaration);
				out.write("\n" + XmldtdVersionDeclaration);
				out.write("\n");

				// Start of eu-envelope section
				out.write("\n" + "<gc-envelope>");

				/**
				 * In case of NP & CP envelope details will be found as if it is
				 * an RMS in case of MRP & DCP
				 */

				// Envelope for RMS // for loop for testing multiple envelope
				for (int i = 0; i <= 1; i++) {
					if (publishForm.getRMSSelected() == 'Y') {

						// Start of envelope

						out.write("\n" + "<envelope country=\""
								+ publishForm.getCountry().toLowerCase()
								+ "\">");

						/*******************************************************/
						// Submission nos.
						// high level tracking no
						// submission type and mode
						out.write("\n" + "<submission ");
						out.write("type=\""
								+ publishForm.getSubmissionType_eu() + "\"");
						if (publishForm.getSubmissionMode() != null
								&& !publishForm.getSubmissionMode().equals(""))
							out.write(" mode=\""
									+ publishForm.getSubmissionMode() + "\"");
						out.write(">");
						if (publishForm.getHighLvlNo() != null
								&& !publishForm.getHighLvlNo().equals("")) {
							out.write("<number>" + publishForm.getHighLvlNo()
									+ "</number>");
						}
						out.write("\n" + "<tracking>");
						if (publishForm.getApplicationNumber() != null
								&& !publishForm.getApplicationNumber().equals(
										"")) {
							String[] appNums = publishForm
									.getApplicationNumber().split(",");
							for (int iNum = 0; iNum < appNums.length; iNum++) {
								// out.write("\n" +
								// "<number>"+appNums[iNum].replaceAll("-",
								// "/").trim()+"</number>");
								out.write("\n" + "<number>"
										+ appNums[iNum].trim() + "</number>");
							}
						} else {
							out.write("\n" + "<number></number>");
						}
						out.write("\n" + "</tracking></submission>");
						/*******************************************************/

						// Applicant
						out.write("\n" + "<applicant>"
								+ publishForm.getApplicant() + "</applicant>");
						// Agency
						out.write("\n" + "<agency code=" + "\""
								+ publishForm.getAgencyName() + "\"" + " />");
						// Procedure Type
						out.write("\n" + "<procedure type=\""
								+ publishForm.getProcedureType() + "\"/>");
						// Invented-name
						out.write("\n" + "<invented-name>"
								+ publishForm.getInventedName()
								+ "</invented-name>");
						// INNs
						if (publishForm.getInn() != null
								&& !publishForm.getInn().equals("")) {
							String[] INNs = publishForm.getInn().split(",");
							for (int iInn = 0; iInn < INNs.length; iInn++) {
								out.write("\n" + "<inn>" + INNs[iInn].trim()
										+ "</inn>");
							}
						}
						// Sequence number
						out.write("\n" + "<sequence>"
								+ publishForm.getSeqNumber() + "</sequence>");
						// Related sequence numbers
						if (publishForm.getRelatedSeqNumber() != null
								&& !publishForm.getRelatedSeqNumber()
										.equals("")) {
							String[] relatedSeqs = publishForm
									.getRelatedSeqNumber().split(",");
							for (int iSeq = 0; iSeq < relatedSeqs.length; iSeq++) {
								out.write("\n" + "<related-sequence>"
										+ relatedSeqs[iSeq].trim()
										+ "</related-sequence>");
							}
						}
						// Submission-description
						out.write("\n" + "<submission-description>"
								+ publishForm.getSubmissionDescription()
								+ "</submission-description>");
						// End of envelope
						out.write("\n" + "</envelope>");
						out.write("\n");
					}

					System.out.print("ff");
				}
				// Envelopes for selected CMSs

			}
			out.write("\n" + "</gc-envelope>");// End of envelopes

			
			
			out.write("\n" + "<m1-gc>");

				out.write("\n" + "<m1-0-cover>");
				out.write("\n" + "<specific country='" + publishForm.getCountry()
						+ "'>");
				out.write("\n");
				out.write("<leaf ID='id-cover-1' operation='new' checksum='6b2e5cc92f76a1fe9157f0ec070aa408' checksum-type='md5' xlink:href='10-cover/sa/sa-cover.pdf'>");
				out.write("\n");
				out.write("<title>Cover Page for Saudi Food &amp; Drug Authority</title>");
				out.write("\n");
				out.write("</leaf>");
				out.write("\n" + "</specific>");
				out.write("\n" + "</m1-0-cover>");
				
				out.write("\n" + "<m1-2-form>");
				out.write("\n" + "<specific country='" + publishForm.getCountry()
						+ "'>");
				out.write("<leaf ID='id-cover-1' operation='new' checksum='6b2e5cc92f76a1fe9157f0ec070aa408' checksum-type='md5' xlink:href='10-cover/sa/sa-cover.pdf'>");
				out.write("\n");
				out.write("<title>Application Form</title>");
				out.write("\n");
				out.write("</leaf>");
				out.write("\n" + "</specific>");
				out.write("\n" + "</m1-2-form>");
				
				out.write("<m1-3-pi>");
					out.write("<m1-3-1-spc>");
						out.write("<pi-doc xml:lang='en' type='spc' country='sa'>");
					
							out.write("<leaf ID='spc-sa-en' operation='new' checksum='ca0e92200f2033edfe5f07645e708337' checksum-type='md5' xlink:href='13-pi/131-spc/sa/en/sa-spc.pdf'>");
								out.write("<title>SPC in English</title>");
							out.write("</leaf>");
						out.write("</pi-doc>");
						out.write("<pi-doc xml:lang='en' type='spc' country='sa'>");
							out.write("<leaf ID='spc-sa-en' operation='new' checksum='ca0e92200f2033edfe5f07645e708337' checksum-type='md5' xlink:href='13-pi/131-spc/sa/en/sa-spc.pdf'>");
								out.write("<title>SPC in Arabian</title>");
							out.write("</leaf>");
						out.write("</pi-doc>");
						out.write("<pi-doc xml:lang='en' type='spc' country='om'>");
						out.write("<leaf ID='spc-sa-en' operation='new' checksum='ca0e92200f2033edfe5f07645e708337' checksum-type='md5' xlink:href='13-pi/131-spc/sa/en/sa-spc.pdf'>");
							out.write("<title>SPC in Arabian</title>");
						out.write("</leaf>");
					out.write("</pi-doc>");
					out.write("</m1-3-1-spc>");
					
					out.write("<m1-3-2-label>");
						out.write("<pi-doc xml:lang='en' type='label' country='sa'>");
							out.write("<leaf ID='outer-sa-en' operation='new' checksum='b5d34e9add439771531315a2249d7857' checksum-type='md5' xlink:href='13-pi/132-labeling/sa/en/sa-label.pdf'>");
								out.write("<title>Labeling in English</title>");
							out.write("</leaf>");
						out.write("</pi-doc>");
						
						out.write("<pi-doc xml:lang='en' type='label' country='sa'>");
							out.write("<leaf ID='outer-sa-ar' operation='new' checksum='b5d34e9add439771531315a2249d7857' checksum-type='md5' xlink:href='13-pi/132-labeling/sa/en/sa-label.pdf'>");
								out.write("<title>Labeling in Arabian</title>");
							out.write("</leaf>");
						out.write("</pi-doc>");
						
						out.write("<pi-doc xml:lang='en' type='label' country='om'>");
							out.write("<leaf ID='outer-sa-ar' operation='new' checksum='b5d34e9add439771531315a2249d7857' checksum-type='md5' xlink:href='13-pi/132-labeling/sa/en/sa-label.pdf'>");
								out.write("<title>Labeling in Arabian</title>");
							out.write("</leaf>");
					out.write("</pi-doc>");
					out.write("</m1-3-2-label>");
					
					out.write("<m1-3-3-pil>");
						out.write("<pi-doc xml:lang='en' type='pil' country='sa'>");
							out.write("<leaf ID='pil-sa-ar' operation='new' checksum='b5d34e9add439771531315a2249d7857' checksum-type='md5' xlink:href='13-pi/132-labeling/sa/en/sa-label.pdf'>");
								out.write("<title>English PIL</title>");
							out.write("</leaf>");
						out.write("</pi-doc>");
						out.write("<pi-doc xml:lang='ar' type='pil' country='sa'>");
							out.write("<leaf ID='pil-sa-ar' operation='new' checksum='b5d34e9add439771531315a2249d7857' checksum-type='md5' xlink:href='13-pi/132-labeling/sa/en/sa-label.pdf'>");
								out.write("<title>Arabic PIL</title>");
							out.write("</leaf>");
						out.write("</pi-doc>");
						
						out.write("<pi-doc xml:lang='en' type='pil' country='om'>");
							out.write("<leaf ID='pil-sa-en' operation='new' checksum='b5d34e9add439771531315a2249d7857' checksum-type='md5' xlink:href='13-pi/132-labeling/sa/en/sa-label.pdf'>");
								out.write("<title>English PIL</title>");
							out.write("</leaf>");
						out.write("</pi-doc>");
						
					out.write("</m1-3-3-pil>");
					
					out.write("<m1-3-4-mockup>");
					out.write("\n" + "<specific country='" + publishForm.getCountry()
							+ "'>");
					out.write("\n");
					out.write("<leaf ID='id-mockup' operation='new' checksum='6b2e5cc92f76a1fe9157f0ec070aa408' checksum-type='md5' xlink:href='10-cover/sa/sa-cover.pdf'>");
					out.write("\n");
					out.write("<title>Artwork (Mock-ups)</title>");
					out.write("\n");
					out.write("</leaf>");
					out.write("</specific>");
					out.write("</m1-3-4-mockup>");
					
					
					out.write("<m1-3-5-samples>");
				
					out.write("<leaf ID='id-samples' operation='new' checksum='6b2e5cc92f76a1fe9157f0ec070aa408' checksum-type='md5' xlink:href='10-cover/sa/sa-cover.pdf'>");
					out.write("\n");
					out.write("<title>Samples</title>");
					out.write("\n");
					out.write("</leaf>");
					out.write("</m1-3-5-samples>");
				out.write("</m1-3-pi>");
				//m1-4
				
				
			out.write("\n" + "</m1-gc>");

			
			out.write(writer.toString());

			out.write("</gc:gc-backbone>");
			xmlwriter.close();
			out.flush();
			out.close();
			writer = null;
			xmlwriter = null;

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}
