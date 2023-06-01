package com.docmgmt.struts.actions.labelandpublish.CH;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import com.docmgmt.dto.DTOSTFStudyIdentifierMst;
import com.docmgmt.dto.DTOSubmissionInfoForManualMode;
import com.docmgmt.dto.DTOSubmittedWorkspaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeAttrHistory;
import com.docmgmt.dto.DTOWorkSpaceNodeAttribute;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.dto.PublishAttrForm;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.PropertyInfo;
import com.docmgmt.struts.actions.labelandpublish.FileManager;
import com.docmgmt.struts.actions.labelandpublish.europeansubmission.TrackingTable.TrackingTableGenerator;
import com.docmgmt.struts.resources.MD5;
import com.docmgmt.struts.resources.XmlWriter;
import com.opensymphony.xwork2.ActionContext;

public class WorkspacePublishService_CH {

	public Writer writer;
	public XmlWriter xmlwriter;

	public BufferedWriter out;
	public BufferedWriter out1; // Second buffered writer required to when with
	// ch-regional selected.

	public Vector childNodeForParent;
	public Vector childAttrForNode;
	public Vector parentNodes;
	public Vector childAttrId;
	public Vector attrDetail;
	public Vector attrNameForNode;
	public Vector folderDtl;
	public Vector folderPathDtl;

	public Vector fileNameSubDtl;
	public String fileName;
	public String folderStructure;
	public String sourceFolderName;
	public String publishDestFolderName;
	public Integer attrId;
	public String attrValue, attrName;
	public Integer nodeId, nodeAttrId;
	public int childNode;
	public int childNodeSize = 0;
	public int iParentId = 1;
	public String wsId;
	public String stype = "";
	public String nodeName, nodeDisplayName;
	public StringBuffer folderName;
	public File folderStruct;
	public File createBaseFolder;
	public String LastPublishedVersion;

	private String relativePathToCreate;
	private String absolutePathToCreate;
	private String nodetypeindi;
	private MD5 md5 = null;
	private Vector STFXMLLocation;
	// Added For giving space
	String newPath = "";
	String studyId;
	private int upCount = 0;
	public String workspaceDesc;
	public String baseLocation;
	public String workspaceLabelId;
	public Integer userId;

	public Vector pathlst;

	public Vector trannolst;
	public int tranno;
	public String folder;

	// added by hardik shah

	Vector<Integer> AllNodesofHistory = new Vector<Integer>();

	DocMgmtImpl docMgmtInt = new DocMgmtImpl();

	HttpServletRequest request;

	/* Added By : Ashmak Shah */
	private String currentSequenceNumber;

	private char projectPublishType;
	private int[] leafIds;
	FileManager fileManager;

	public WorkspacePublishService_CH() {
		PropertyInfo propInfo = PropertyInfo.getPropInfo();
		publishDestFolderName = propInfo.getValue("PublishFolder");
		fileManager = new FileManager();
	}

	public void workspacePublish(String workspaceId,
			PublishAttrForm publishForm, HttpServletRequest request1,
			String wsDesc) {
		try {
			this.request = request1;
			wsId = workspaceId;
			workspaceDesc = wsDesc;
			writer = new java.io.StringWriter();
			xmlwriter = new XmlWriter(writer);

			folderName = new StringBuffer();
			childNodeForParent = new Vector();
			childAttrForNode = new Vector();
			parentNodes = new Vector();
			childAttrId = new Vector();
			attrDetail = new Vector();
			attrNameForNode = new Vector();
			STFXMLLocation = new Vector();
			pathlst = new Vector();

			trannolst = new Vector();
			md5 = new MD5();

			int labelNo = publishForm.getLabelNo();

			userId = Integer.parseInt(ActionContext.getContext().getSession()
					.get("userid").toString());

			// Change for New Submission Path
			// publishDestFolderName = publishDestFolderName + File.separator +
			// workspaceDesc + File.separator + publishForm.getLabelId();
			publishDestFolderName = publishForm.getPublishDestinationPath();
			createBaseFolder(publishDestFolderName, publishForm);

			stype = publishForm.getSubmissionFlag();
			currentSequenceNumber = publishForm.getSeqNumber();

			if (projectPublishType == 'P') {
				// get all nodes and its parent nodes where file attached
				AllNodesofHistory = docMgmtInt
						.getAllNodesFromHistoryForRevisedSubmission(wsId,
								labelNo);
			} else if (projectPublishType == 'M') {
				// In case of Manual Mode getting all parents of the selected
				// nodes
				AllNodesofHistory = docMgmtInt.getWorkspaceTreeNodesForLeafs(
						workspaceId, leafIds);
			}

			// Copy Util folder
			addutilFolder(stype);

			if (stype.equals("ch")) {
				iParentId = 1;
				File regionalXml = new File(absolutePathToCreate + "/m1/"
						+ stype + "/" + stype + "-regional.xml");
				regionalXml.getParentFile().mkdirs();
				out = new BufferedWriter(new FileWriter(regionalXml));

				writeToXmlFile(stype, publishForm);

				parentNodes = docMgmtInt.getChildNodeByParentForPublishForM1(
						iParentId, wsId);

				System.out.println("path=" + absolutePathToCreate);
				getChildNode(parentNodes, absolutePathToCreate, iParentId, 0,
						stype, publishForm);
				xmlwriter.close();
				out.write(writer.toString());

				out.write("</ch:ch-backbone>");

				out.flush();
				out.close();
				writer = null;
				xmlwriter = null;

				if (publishForm.getAddTT() == 'Y') {
					TrackingTableGenerator.addTrackingTable(regionalXml, wsId,
							publishForm.getSubmissionId(), "14");
				}
				if (publishForm.getAddTT() == 'N') {
					TrackingTableGenerator.addLinkInEuRegional(regionalXml,
							wsId, publishForm.getSubmissionId(), "14");
				}

				writer = new java.io.StringWriter();
				xmlwriter = new XmlWriter(writer);
				out = new BufferedWriter(new FileWriter((absolutePathToCreate)
						+ "/index.xml"));

				writeToXmlFile(stype + "m2-m5", publishForm);

				parentNodes = docMgmtInt
						.getChildNodeByParentForPublishFromM2toM5(iParentId,
								wsId);

				/*
				 * for(int i = 0;i<parentNodes.size();i++) {
				 * DTOWorkSpaceNodeDetail
				 * dto=(DTOWorkSpaceNodeDetail)parentNodes.get(i);
				 * nodeId=dto.getNodeId(); nodeName=dto.getNodeName();
				 * nodeDisplayName=dto.getNodeDisplayName(); dto=null;
				 * 
				 * }
				 */

				getChildNode(parentNodes, absolutePathToCreate, iParentId, 0,
						stype, publishForm);
				xmlwriter.close();
				out.write(writer.toString());
				out.write("</ectd:ectd>");
				out.flush();
				out.close();
			}

			checkSumForindexFile();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This Method recursively create Node for Parent Node
	 * 
	 * @param childNodes
	 * @throws Exception
	 */

	public void getChildNode(Vector childNodes, String pathToCreate,
			int parentId, int IdValue, String stype, PublishAttrForm publishForm)
			throws Exception {
		if (childNodes.size() == 0) {
			// if file attached at node or its parent node
			if (AllNodesofHistory.contains(nodeId)) {
				xmlwriter.writeEntity("leaf");
				Vector modifiedFile = null;

				// In case of Manual Mode Publish
				if (projectPublishType == 'M') {
					// Hardcoded '0001' as type of AttrForIndi. '0001'
					// attributes used for are publish on leaf nodes
					childAttrId = docMgmtInt.getLatestNodeAttrHistory(wsId,
							nodeId.intValue(), "0001");
					DTOSubmittedWorkspaceNodeDetail dtoSub = new DTOSubmittedWorkspaceNodeDetail();

					if (publishForm.isRecompile()) {
						DTOSubmissionInfoForManualMode dtomm = docMgmtInt
								.getWorkspaceManualDetail(wsId, publishForm.getSubmissionId(), nodeId);

						dtoSub.setLastPublishVersion(dtomm
								.getLastPublishedVersion());
						dtoSub.setIndexId(dtomm.getRefID());

					} else {
						//System.out.println("request-->"+request.getPara);
						
						dtoSub.setLastPublishVersion(request
								.getParameter("refSeq_" + nodeId));
						dtoSub.setIndexId(request.getParameter("refID_"
								+ nodeId));
					}

					modifiedFile = new Vector();
					modifiedFile.add(dtoSub);

				} else {
					childAttrId = docMgmtInt.getAttributesForNodeForPublish(
							nodeId.intValue(), wsId, publishForm.getLabelNo(),
							"0001");
					modifiedFile = docMgmtInt.getAttributeValueOfModifiedFile(
							wsId, nodeId.intValue());
				}

				if (childAttrId.size() != 0) {
					String operationValue = "";
					if (pathToCreate.matches(".*/m1/ch/.*-cover.*")) {
						for (int i = 0; i < childAttrId.size(); i++) {
							DTOWorkSpaceNodeAttrHistory dtowsnath = (DTOWorkSpaceNodeAttrHistory) childAttrId
									.get(i);
							attrName = dtowsnath.getAttrName();
							if (attrName.equalsIgnoreCase("operation")) {
								dtowsnath.setAttrValue("new");
							}
						}
					}
					for (int i = 0; i < childAttrId.size(); i++) {
						DTOWorkSpaceNodeAttrHistory dtowsnath = (DTOWorkSpaceNodeAttrHistory) childAttrId
								.get(i);
						attrId = dtowsnath.getAttrId();
						attrValue = dtowsnath.getAttrValue();
						attrName = dtowsnath.getAttrName();

						System.out.println("ID=" + attrId + "=Value="
								+ attrValue + " = Attr Name=" + attrName);

						// In case of Manual Mode
					
						if (attrName.equals("operation")
								&& projectPublishType == 'M') {

							if (publishForm.isRecompile()) {
								DTOSubmissionInfoForManualMode dtomm = docMgmtInt
										.getWorkspaceManualDetail(wsId, publishForm.getSubmissionId(),
												nodeId);
								attrValue = dtomm.getOperation();
								System.out
										.println("attrVAlue for manualMode-->"
												+ attrValue);

							} else {
								attrValue = request.getParameter("operation_"
										+ nodeId);
							}

						}
						if (attrName.equalsIgnoreCase("operation")
								&& attrValue.equals("new")) {

							xmlwriter
									.writeAttribute(attrName, attrValue.trim());
							// xmlwriter.writeAttribute(attrName,"aaaa");
						} else if (attrName.equalsIgnoreCase("operation")
								&& !attrValue.equals("new")) {
							StringBuffer query = new StringBuffer();
							for (int j = 0; j < modifiedFile.size(); j++) {
								DTOSubmittedWorkspaceNodeDetail dtosub = (DTOSubmittedWorkspaceNodeDetail) modifiedFile
										.get(j);
								String lastPubVersion = dtosub
										.getLastPublishVersion();
								if (publishForm.isRecompile() && projectPublishType == 'M') {
									DTOSubmissionInfoForManualMode dtomm = docMgmtInt
											.getWorkspaceManualDetail(wsId,
													publishForm.getSubmissionId(), nodeId);
									lastPubVersion = dtomm.getRelSeqNo();
									
								} else {

									lastPubVersion = dtosub
											.getLastPublishVersion();

								}
								String indexId = dtosub.getIndexId();

								/* IMP Change done By Ashmak Shah */
								if (pathToCreate.contains("m1/ch")) {
									query.append("../../../");
									query.append("" + lastPubVersion + "");

									query.append("/m1/ch/ch-regional.xml#");
								} else {
									query.append("../");
									query.append("" + lastPubVersion + "");

									query.append("/index.xml#");
								}
								query.append("" + indexId + "");
								xmlwriter.writeAttribute(attrName, attrValue
										.trim());
								xmlwriter.writeAttribute("modified-file", query
										.toString());

								System.out.println("ABC->Modified file=>"
										+ query.toString());
							}
							if (attrValue.equals("delete")) {
								operationValue = attrValue;
							}

						}

						if (attrName.equalsIgnoreCase("xlink:href")) {
							if (operationValue.equals("delete")) {
								xmlwriter.writeAttribute("xlink:href", "");
								xmlwriter.writeAttribute("checksum", "");
								String IDValue = "node-" + nodeId.toString();
								xmlwriter.writeAttribute("ID", IDValue);

							} else {
								// System.out.println("else - attribute value not delete");
								if (nodetypeindi.trim().equals("I")) {
									String newPath = pathToCreate.substring(0,
											pathToCreate.substring(
													0,
													pathToCreate
															.lastIndexOf("/"))
													.lastIndexOf("/"));
									// System.out.print("File Path(new)="+newPath);
									attrValue = copyFileforPublish(publishForm,
											newPath);
								} else
									attrValue = copyFileforPublish(publishForm,
											pathToCreate);
								attrValue = attrValue.toLowerCase();
								stype = stype.toLowerCase();
								// System.out.print("File Path(stype)="+stype);
								// Change for New Submission Path
								// String FilePathForCheckSum =
								// publishDestFolderName + File.separator +
								// publishForm.getApplicationNumber() +"/"+
								// publishForm.getSeqNumber() + "/"+attrValue;
								String FilePathForCheckSum = publishDestFolderName
										+ File.separator
										+ "/"
										+ publishForm.getSeqNumber()
										+ "/"
										+ attrValue;

								attrValue = attrValue.replaceAll("m1/" + stype
										+ "/", "");

								attrValue = attrValue.replaceAll("//", "/");
								xmlwriter.writeAttribute(attrName, attrValue
										.trim());

								String md5HashCodeforFile = md5
										.getMd5HashCode(FilePathForCheckSum);
								xmlwriter.writeAttribute("checksum",
										md5HashCodeforFile);
								String IDValue = "node-" + nodeId.toString();
								xmlwriter.writeAttribute("ID", IDValue);
							}
						} else {
							if (!attrName.equals("xlink:href")
									&& !attrName.equals("operation")
									&& !attrName.equals("modified-file")
									&& !attrName.equals("checksum")
									&& !attrName.equals("Keywords")
									&& !attrName.equals("Author")
									&& !attrName.equals("Description")
									&& !attrName.equals("ID")) {
								xmlwriter.writeAttribute(attrName, attrValue
										.trim());
							}
						}
					}
				}

				xmlwriter.writeEntity("title");
				xmlwriter.writeText(nodeDisplayName.trim());
				xmlwriter.endEntity();
				xmlwriter.endEntity();// end entity for <leaf>

			}// if end of history check

		}// if end
		else {

			String MergeAttributeStr = "";

			for (int i = 0; i < childNodes.size(); i++) {
				DTOWorkSpaceNodeDetail dtowsnd = (DTOWorkSpaceNodeDetail) childNodes
						.get(i);

				nodeId = dtowsnd.getNodeId();
				nodeName = dtowsnd.getNodeName();
				nodeDisplayName = dtowsnd.getNodeDisplayName().trim();
				String filepathelement = dtowsnd.getFolderName();
				String remark = dtowsnd.getRemark().trim();
				// if file attached at node or its parent node
				if (AllNodesofHistory.contains(nodeId)) {

					int isLeaf = docMgmtInt
							.isLeafNodes(wsId, nodeId.intValue());

					nodetypeindi = Character
							.toString(dtowsnd.getNodeTypeIndi());
					if (isLeaf == 0) {
						if (nodetypeindi.equalsIgnoreCase("T")) {
							int intchar = filepathelement.indexOf(".");
							String finalstr;
							if (intchar != -1) {
								finalstr = filepathelement
										.substring(0, intchar);
							} else {
								finalstr = filepathelement;
							}
							createSubFolders(pathToCreate, finalstr);
						} else if (nodetypeindi.equalsIgnoreCase("D")) {

							System.out.println("Status Found=D");
							createSubFolders(pathToCreate, "");
						} else if (nodetypeindi.equalsIgnoreCase("X")
								|| nodetypeindi.trim().equals("B")) {
							// Do Nothing
						} else {
							createSubFolders(pathToCreate, filepathelement);
						}
					}

					if (isLeaf == 0) {
						if (nodetypeindi.equalsIgnoreCase("T")) {
							// System.out.println(" STF File location : " +
							// pathToCreate + "\\" + filepathelement);
							DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
							dto.setNodeId(nodeId.intValue());

							// As we are adding stf on leaf node folder name of
							// the leaf node would be abc.pdf
							// so we need to remove .pdf extension
							int intchar = filepathelement.indexOf(".");
							String finalstr;
							if (intchar != -1) {
								finalstr = filepathelement
										.substring(0, intchar);
							} else {
								finalstr = filepathelement;
							}

							dto
									.setBaseworkfolder(pathToCreate + "/"
											+ finalstr);

							// System.out.println("STFXMLLocation:"+dto.getBaseworkfolder());

							STFXMLLocation.addElement(dto);
							generateSTFFile(wsId);
							dto = null;
						}
					}
					if (!nodeName
							.trim()
							.equals(
									"m1-administrative-information-and-prescribing-information")) {
						if (isLeaf == 0) {// This is a parent node
							if (!nodetypeindi.trim().equalsIgnoreCase("T")
									&& !nodetypeindi.trim().equals("F")
									&& !nodetypeindi.trim().equals("B")) {
								/**
								 * '&& !nodetypeindi.trim().equals("F")' is
								 * added by Ashmak Shah
								 * 
								 * In case of nodes like 'CRFs' under STF no
								 * parent node will create like
								 * '<case-report-forms>' in index.xml All crfs
								 * will be displayed with other stf nodes
								 */
								// System.out.println("node name : " +
								// nodeName.trim() + " NodeTypeIndi: " +
								// nodetypeindi.trim());

								// change by hardik (For Node-Extension)

								if (nodetypeindi.trim().equals("E")) {
									xmlwriter.writeEntity("node-extension");
									xmlwriter.writeEntity("title");
									xmlwriter.writeText(remark);
									xmlwriter.endEntity();// title
								} else {

									System.out.println("NodeName="
											+ nodeName.trim());

									xmlwriter.writeEntity(nodeName.trim());
								}

								/**
								 * For nodeTypeIndi = 'P'
								 */
								String country = "", language = "",foldername = "";

								String PathForAttrFolder = "";

								Vector nodeAttribute = docMgmtInt
										.getNodeAttributes(wsId, nodeId
												.intValue());
								if (nodeAttribute.size() > 0) {
									for (int k = 0; k < nodeAttribute.size(); k++) {
										DTOWorkSpaceNodeAttribute obj = (DTOWorkSpaceNodeAttribute) nodeAttribute
												.get(k);
										String attrname = obj.getAttrName();
										String attrvalue = obj.getAttrValue();

										/**
										 * For nodeTypeIndi = 'P'
										 */
										if (attrname
												.equalsIgnoreCase("country")) {
											
											country = attrvalue;
										}
										if (attrname
												.equalsIgnoreCase("xml:lang")) {
											language = attrvalue;
										}
										if(attrname.equals("Name")){
											attrname="name";
										}
										if (attrvalue != null
												&& !attrvalue.equals("") && !attrname.equalsIgnoreCase("FolderName")) {

											xmlwriter.writeAttribute(attrname,
													attrvalue);
										}
										if(attrname.equalsIgnoreCase("FolderName")){
											
											foldername=attrvalue.trim();
											if (foldername != null
													&& !foldername.equals("")) {
												attrvalue=foldername.replaceAll("\\s", "");
												MergeAttributeStr=attrvalue.toLowerCase();
											}
										}
										

										// remove space and give - in attribute
										// value
										else{
										
											if (attrvalue.length() > 4
													&& !attrvalue
															.equalsIgnoreCase("common") && !attrname.equalsIgnoreCase("Name")) {
	
												System.out.println("@@@attrvalue: "
														+ attrvalue);
												System.out.println("Node Display NAme->"+nodeName);
	
												attrvalue = attrvalue.trim()
														.replaceAll(" ", "-");
												attrvalue = attrvalue.trim()
														.replaceAll("\\.", "-");
												attrvalue = attrvalue.trim()
														.replaceAll(",", "-");
												attrvalue = attrvalue.substring(0,
														4);
	
											} else {
												attrvalue = attrvalue.trim()
														.replaceAll(" ", "-");
											}
	
											if (k == 0) {
												MergeAttributeStr = attrvalue
														.toLowerCase();
											} else {
												MergeAttributeStr = MergeAttributeStr
														+ "-"
														+ attrvalue.toLowerCase();
											}
										}
									}
									/**
									 * Description: If repeated node with same
									 * attribute with different value, then
									 * create the attribute folder other wise
									 * not to required create attribute folder.
									 * */

									/**
									 * nodeTypeIndi = 'P' (pi-doc) Description :
									 * Folder Name will be 'country name' Sub
									 * folder Name will be 'language'
									 */

									if (nodetypeindi.trim().equals("P")) {
										PathForAttrFolder = pathToCreate + "/"
												+ country;
										MergeAttributeStr = language;
										
										
									}

									/**
									 * nodeTypeIndi = 'D' (No Folder to be
									 * created) Description : Folder will be
									 * created from attribute values But not
									 * from 'vFolderName'
									 */
									else if (nodetypeindi.trim().equals("D")) {
										PathForAttrFolder = pathToCreate;
										
										System.out
												.println("Status Found() (D)");
										// No change in 'MergeAttributeStr'
									} else if (nodetypeindi.trim().equals("X")) {
										MergeAttributeStr = "";
										PathForAttrFolder = pathToCreate;
										System.out
										.println("Status Found() (X)");
									}

									else if (nodetypeindi.trim().equals("C")) {
										if (!isNodeHavingClones(
												nodeName.trim(), nodeId)) {
											MergeAttributeStr = "";
											
											System.out.println("Here//");
										}
										PathForAttrFolder = pathToCreate + "/"
												+ filepathelement;
									}
									/**
									 * nodeTypeIndi = 'N' (normal node)
									 * Description : Folder will be created from
									 * attribute values But not from
									 * 'vFolderName'
									 */
									else {
										PathForAttrFolder = pathToCreate + "/"
												+ filepathelement;
									}
									// System.out.println("PathForAttrFolder:"+PathForAttrFolder);
									createSubFolders(PathForAttrFolder,
											MergeAttributeStr);
									System.out.println("Merge="+MergeAttributeStr);
									
									parentNodes = docMgmtInt
											.getChildNodeByParent(nodeId
													.intValue(), wsId);
									getChildNode(parentNodes,
											relativePathToCreate, nodeId
													.intValue(), IdValue,
											stype, publishForm);
									xmlwriter.endEntity();
								} else {
									parentNodes = docMgmtInt
											.getChildNodeByParent(nodeId
													.intValue(), wsId);
									getChildNode(parentNodes,
											relativePathToCreate, nodeId
													.intValue(), IdValue,
											stype, publishForm);
									xmlwriter.endEntity();
								}
							} else {
								parentNodes = docMgmtInt.getChildNodeByParent(
										nodeId.intValue(), wsId);
								getChildNode(parentNodes, relativePathToCreate,
										nodeId.intValue(), IdValue, stype,
										publishForm);
							}
						} else {
							parentNodes = docMgmtInt.getChildNodeByParent(
									nodeId.intValue(), wsId);
							getChildNode(parentNodes, relativePathToCreate,
									nodeId.intValue(), IdValue, stype,
									publishForm);
						}
					} else {
						parentNodes = docMgmtInt.getChildNodeByParent(nodeId
								.intValue(), wsId);
						getChildNode(parentNodes, relativePathToCreate, nodeId
								.intValue(), IdValue, stype, publishForm);
					}

					/* Added by Ashmak Shah */
					/*
					 * STF Nodes After CRFs were copied into the crf folder to
					 * avoid this below line is added.
					 */
					relativePathToCreate = pathToCreate;
				}// if end of history vector

			} // for loop end
		}// main else end

	}

	private void createBaseFolder(String bfoldername,
			PublishAttrForm publishForm) {
		try {
			folderDtl = new Vector();

			folderDtl = docMgmtInt.getFolderByWorkSpaceId(wsId);

			if (folderDtl != null) {
				Object[] record = new Object[folderDtl.size()];
				record = (Object[]) folderDtl.elementAt(0);
				if (record != null) {
					// Source Folder Path
					sourceFolderName = record[0].toString();

					LastPublishedVersion = record[2].toString();

				}
			}

			// Change for New Submission Path
			createBaseFolder = new File(bfoldername + "/"
					+ publishForm.getSeqNumber());

			absolutePathToCreate = bfoldername + "/"
					+ publishForm.getSeqNumber();

			System.out.println("absolutePathToCreate: " + absolutePathToCreate);
			if (!createBaseFolder.exists()) // Check folder already present or
				// not
				createBaseFolder.mkdirs();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String copyFileforPublish(PublishAttrForm publishForm,
			String pathToCreate) throws Exception {

		String absolutePath = "";
		String absolutePathLink = "";
		newPath = "";
		absolutePath = pathToCreate;
		absolutePathLink = pathToCreate.replaceAll(absolutePathToCreate, "");

		Vector<DTOWorkSpaceNodeHistory> fileNameDtl;
		// In case of Manual Mode Publish
		if (projectPublishType == 'M') {
			ArrayList<DTOWorkSpaceNodeHistory> nodeHistory = docMgmtInt
					.getAllNodesLastHistory(wsId,
							new int[] { nodeId.intValue() });
			fileNameDtl = new Vector<DTOWorkSpaceNodeHistory>();
			if (nodeHistory.size() > 0)
				fileNameDtl.addElement(nodeHistory.get(0));
		} else {
			fileNameDtl = docMgmtInt.getFileNameForNodeForPublish(nodeId
					.intValue(), wsId, publishForm.getLabelNo());
		}

		if (fileNameDtl.size() > 0) {
			DTOWorkSpaceNodeHistory dtowsand = fileNameDtl.get(0);

			fileName = dtowsand.getFileName();
			folderStructure = dtowsand.getFolderName();

			folderStruct = new File(sourceFolderName.trim()
					+ folderStructure.trim() + "/" + fileName);

			File destParentDir = new File(absolutePath);
			destParentDir.mkdirs();
			fileManager.copyDirectory(folderStruct, new File(destParentDir,
					fileName));
			// addFiles(folderStruct,folderName);

			return (absolutePathLink.substring(1) + "/" + fileName);
		} else {
			return (absolutePathLink.substring(1));
		}
	}

	/*
	 * Add file at Publish Folder Path folderStruct = Source Folder File
	 * publishFolderStuct = Destination Folder File
	 */
	/*
	 * private void addFiles(File folderStruct,File publishFolderStuct){ try{
	 * 
	 * FileInputStream fin = new
	 * FileInputStream(folderStruct.getCanonicalFile());
	 * if(!publishFolderStuct.exists()) { publishFolderStuct.mkdirs(); }
	 * 
	 * FileOutputStream fout = new FileOutputStream(publishFolderStuct + "/" +
	 * fileName);
	 * 
	 * byte [] buf = new byte[1024 * 10]; int len;
	 * 
	 * while((len = fin.read(buf))> 0){ fout.write(buf,0,len); } fin.close();
	 * fout.close();
	 * 
	 * }catch(Exception e){ e.printStackTrace(); } }
	 */

	private void createSubFolders(String pathToCreate, String folderName) {

		File createsubfolder;
		createsubfolder = new File(pathToCreate + "/" + folderName);
		relativePathToCreate = pathToCreate + "/" + folderName;
		
	
		if (!createsubfolder.exists()) {
			createsubfolder.mkdirs();
		}
	}

	private void checkSumForindexFile() {
		try {
			String indexmd5 = md5.getMd5HashCode(absolutePathToCreate
					+ "/index.xml");
			BufferedWriter indexHashFileout = new BufferedWriter(
					new FileWriter(absolutePathToCreate + "/index-md5.txt"));
			indexHashFileout.write(indexmd5);
			indexHashFileout.close();
			indexHashFileout = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addutilFolder(String stype) {
		try {
			PropertyInfo propertyInfo = PropertyInfo.getPropInfo();

			File baseWorkDir = propertyInfo.getDir("BaseWorkFolder");
			File inDtdFile = new File(baseWorkDir.getAbsoluteFile()
					+ "/util_ch/util/dtd");

			File outDtdFile = new File(absolutePathToCreate + "/util/dtd");
			outDtdFile.mkdirs();

			fileManager.copyDirectory(inDtdFile, outDtdFile);

			/*
			 * String[] dtdfileList = indtdFile.list(); for(int i = 0 ; i<
			 * dtdfileList.length;i++){ FileInputStream fin = new
			 * FileInputStream(indtdFile+"/"+dtdfileList[i]); FileOutputStream
			 * fout = new FileOutputStream(outdtdFile+"/"+dtdfileList[i]); byte
			 * [] buf = new byte[1024 * 10]; int len;
			 * 
			 * while((len = fin.read(buf))> 0){ fout.write(buf,0,len); }
			 * fin.close(); fout.close(); }
			 */

			File inStyleFile = new File(baseWorkDir.getAbsoluteFile()
					+ "/util_ch/util/style");

			File outStyleFile = new File(absolutePathToCreate + "/util/style");
			outStyleFile.mkdirs();

			fileManager.copyDirectory(inStyleFile, outStyleFile);
			/*
			 * String[] outstyleList = instyleFile.list(); for(int i = 0 ; i<
			 * outstyleList.length;i++){ FileInputStream fin = new
			 * FileInputStream(instyleFile+"/"+outstyleList[i]);
			 * FileOutputStream fout = new
			 * FileOutputStream(outstyleFile+"/"+outstyleList[i]); byte [] buf =
			 * new byte[1024 * 10]; int len;
			 * 
			 * while((len = fin.read(buf))> 0){ fout.write(buf,0,len); }
			 * fin.close(); fout.close(); }
			 */

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void writeToXmlFile(String stype, PublishAttrForm publishForm)
			throws Exception {

		if (stype.equals("ch")) {
			String xmlDeclaration = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>";
			String docTypeDeclaration = "<!DOCTYPE ch:ch-backbone SYSTEM \"../../util/dtd/ch-regional.dtd\">";
			String xslStyleSheetDeclaration = "<?xml-stylesheet type=\"text/xsl\" href=\"../../util/style/ch-regional.xsl\"?>";
			String XmldtdVersionDeclaration = "<ch:ch-backbone xmlns:ch=\"http://www.swissmedic.ch\" xmlns:xlink=\"http://www.w3c.org/1999/xlink\" dtd-version=\"1.3\">";

			out.write(xmlDeclaration);
			out.write("\n" + docTypeDeclaration);
			out.write("\n" + xslStyleSheetDeclaration);
			out.write("\n" + XmldtdVersionDeclaration);
			out.write("\n");

			// Start of eu-envelope section
			out.write("\n" + "<ch-envelope>");
			out.write("\n" + "<envelope country=\"ch\">");
			publishForm.setRMSSelected('Y');
			// Envelope for RMS
			if (publishForm.getRMSSelected() == 'Y') {

				// Start of envelope
				String[] applicationNo=publishForm.getApplicationNumber().split(",");
				for(int i=0;i<applicationNo.length;i++){
				out.write("\n" +"<application-number>"
						+ applicationNo[i]
						+ "</application-number>");
				}
				out.write("\n");
				out.write("<submission-description>"
						+ publishForm.getSubmissionDescription()
						+ "</submission-description>");
				
				String[] inventedName=publishForm.getInventedName().split(",");
				for(int i=0;i<inventedName.length;i++){
					out.write("\n" +"<invented-name>"+inventedName[i]+"</invented-name>");
				}
				
		
				System.out.println("Ganelic Name="+publishForm.getGanelicForm());
				//galenicForm:Number:Language:galenicName - each pair separated by comma (,)
				
				String []gform=publishForm.getGanelicForm().split(",");
		
				for(int i=1;i<gform.length;i++){
					String []gFormSep=gform[i].split(":");
					out.write("\n");
					out.write("<galenic-form name=\"" 
							+gFormSep[0]+ "\">");
					out.write("\n\t");
						out.write("<swissmedic-number>"+gFormSep[1]+"</swissmedic-number>");
						out.write("\n\t");
						out.write("<galenic-name language=\""
								+gFormSep[2]+"\">"+gFormSep[3]+"</galenic-name>");
						out.write("\n");
					out.write("</galenic-form>");
				
				}
				
				out.write("\n");
				/*if(publishForm.getDmfHolder().trim().equals("")){
					publishForm.setDmfHolder("n/a");
					
				}
				if(publishForm.getPmfHolder().trim().equals("")){
					publishForm.setDmfHolder("n/a");
					
				}
				if(publishForm.getDmfNumber().trim().equals("")){
					publishForm.setDmfHolder("n/a");
					
				}
				if(publishForm.getPmfNumber().trim().equals("")){
					publishForm.setDmfHolder("n/a");
					
				}*/
				out.write("<dmf-number>"+publishForm.getDmfNumber()+"</dmf-number>");
				out.write("\n");
				out.write("<pmf-number>"+publishForm.getPmfNumber()+"</pmf-number>");
		
				
				String[] iin=publishForm.getInn().split(",");
				for(int i=0;i<iin.length;i++){
					out.write("\n" +"<inn>"+iin[i]+"</inn>");
				}
				out.write("\n");
				
				out.write("<applicant>" + publishForm.getApplicant()
						+ "</applicant>");
				
				out.write("\n");
				out.write("<dmf-holder>"+publishForm.getDmfHolder()+"</dmf-holder>");
				out.write("\n");
				out.write("<pmf-holder>"+publishForm.getPmfHolder()+"</pmf-holder>");
				out.write("\n");
				
				out.write("<agency>Swissmedic</agency>");
				
				String[] subtypes=publishForm.getSubmissionType().split(",");
				for(int i=0;i<subtypes.length;i++){
					out.write("\n" + "<application type=\""
							+ subtypes[i] + "\"/>");
				}
				
				out.write("\n");
				System.out.println("Envalope Generated");
				
				out.write("<paragraph-13-tpa>"+publishForm.getParagraph13()+"</paragraph-13-tpa>");
				out.write("\n");
				
				
				out.write("<ectd-sequence>" + publishForm.getSeqNumber()
						+ "</ectd-sequence>");
				if(publishForm.getRelatedSeqNumber().equals("")){
					publishForm.setRelatedSeqNumber("none");
				}
				if (publishForm.getRelatedSeqNumber() != null
						&& !publishForm.getRelatedSeqNumber().equals("")) {
					String[] relatedSeqs = publishForm.getRelatedSeqNumber()
							.split(",");
					for (int iSeq = 0; iSeq < relatedSeqs.length; iSeq++) {
						out.write("\n" + "<related-ectd-sequence>"
								+ relatedSeqs[iSeq].trim()
								+ "</related-ectd-sequence>");
					}
				}
			}
			out.write("\n" + "</envelope>");
			out.write("\n" + "</ch-envelope>");// End of envelopes
		}

		else if (stype.equals("chm2-m5")) {
			out.write("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>" + "\n");
			out
					.write("<!DOCTYPE ectd:ectd SYSTEM \"util/dtd/ich-ectd-3-2.dtd\">"
							+ "\n");
			out
					.write("<?xml-stylesheet type=\"text/xsl\" href=\"util/style/ectd-2-0.xsl\"?>"
							+ "\n");
			out
					.write("<ectd:ectd xmlns:ectd=\"http://www.ich.org/ectd\" xmlns:xlink=\"http://www.w3c.org/1999/xlink\" dtd-version=\"3.2\">"
							+ "\n");
			out
					.write("<m1-administrative-information-and-prescribing-information>");

			String chmd5 = md5.getMd5HashCode(absolutePathToCreate + "/m1/ch"
					+ "/ch-regional.xml");
			System.out
					.println("CheckSum ="
							+ "<leaf xml:lang=\"en\" checksum-type=\"md5\" modified-file=\"\" operation=\"new\" application-version=\"\" xlink:href=\"m1/ch/ch-regional.xml\" checksum=\""
							+ chmd5
							+ "\" ID=\"node-999\" xlink:type=\"simple\">");
			// out.write("<leaf xml:lang=\"en\" checksum-type=\"md5\" modified-file=\"\" operation=\"new\" application-version=\"\" xlink:href=\"m1/eu/eu-regional.xml\" checksum=\""+eumd5+"\" ID=\"node-999\" xlink:type=\"simple\">");
			out.write("\n");
			out
					.write("<leaf xml:lang=\"en\" checksum-type=\"md5\" operation=\"new\" application-version=\"\" xlink:href=\"m1/ch/ch-regional.xml\" checksum=\""
							+ chmd5
							+ "\" ID=\"node-999\" xlink:type=\"simple\">");
			out.write("\n");
			out.write("<title>");
			out.write("\n");
			out.write("CH Module 1");
			out.write("\n");
			out.write("</title>");
			out.write("\n");
			out.write("</leaf>");
			out.write("\n");
			out.write("</m1-administrative-information-and-prescribing-information>"
							+ "\n");
			out.write("\n");
		}
	}

	public void generateSTFFile(String wsId) {
		try {
			Vector<DTOWorkSpaceNodeDetail> getallfirstnode = docMgmtInt
					.getAllSTFFirstNodes(wsId);
			generateStudyDocument(wsId, getallfirstnode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String generateStudyIndetifier(int firstnodeId, String wsId) {
		int previoustagid = 0;
		int currenttagid = 0;
		String tagname = "";
		String attrName = "";
		String attrValue = "";
		String nodeContent = "";
		String previoustagname = "";
		String previoustagcontent = "";
		String studyidendata = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
		studyidendata += "<!DOCTYPE ectd:study SYSTEM \"";

		for (int icount = 0; icount < upCount; icount++) {
			studyidendata += "../";
		}

		studyidendata += "util/dtd/ich-stf-v2-2.dtd\">\n";
		studyidendata += "<?xml-stylesheet type=\"text/xsl\" href=\"";
		for (int icount = 0; icount < upCount; icount++) {

			studyidendata += "../";

		}
		studyidendata += "util/style/ich-stf-stylesheet-2-2.xsl\"?>\n";
		studyidendata += "<ectd:study xmlns:ectd=\"http://www.ich.org/ectd\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" xml:lang=\"en\" dtd-version=\"2.2\">\n";
		studyidendata += "<study-identifier>\n";

		Vector studyidentifierdata = docMgmtInt.getSTFIdentifierByNodeId(wsId,
				firstnodeId);
		// System.out.println(" FIRST NODE ID : " + firstnodeId + "  " + wsId);
		for (int i = 0; i < studyidentifierdata.size(); i++) {
			DTOSTFStudyIdentifierMst dto = (DTOSTFStudyIdentifierMst) studyidentifierdata
					.get(i);

			currenttagid = dto.getTagId();
			tagname = dto.getTagName();
			attrName = dto.getAttrName();
			attrValue = dto.getAttrValue();
			nodeContent = dto.getNodeContent();

			if (tagname.equalsIgnoreCase("study-id"))
				studyId = nodeContent;

			if (previoustagid > 0) {
				if (previoustagid == currenttagid) {
					// tag continue.
					studyidendata += " " + attrName + "=\"" + attrValue + "\"";
				} else {
					// previous tag close with nodecontent.
					studyidendata += ">" + previoustagcontent + "</"
							+ previoustagname + ">\n";
					// new tag start.// create a tag.
					if (attrName != "" && !attrName.equals(""))
						studyidendata += "<" + tagname + " " + attrName + "=\""
								+ attrValue + "\"";
					else
						studyidendata += "<" + tagname;
				}
			} else {
				// create tag.
				if (attrName != "" && !attrName.equals(""))
					studyidendata += "<" + tagname + " " + attrName + "=\""
							+ attrValue + "\"";
				else
					studyidendata += "<" + tagname;
			}
			previoustagname = tagname;
			previoustagcontent = nodeContent;
			previoustagid = currenttagid;
		}
		// close last tag.
		studyidendata += ">" + previoustagcontent + "</" + previoustagname
				+ ">\n";
		studyidendata += "</study-identifier>";

		return studyidendata;
	}

	public String getSTFFileLocation(int nodeId) {
		String filelocation = "";
		// System.out.println("STFXMLLocation Size:"+STFXMLLocation.size());

		for (int i = 0; i < STFXMLLocation.size(); i++) {
			DTOWorkSpaceNodeDetail dtostfxmllocation = (DTOWorkSpaceNodeDetail) STFXMLLocation
					.get(i);
			if (dtostfxmllocation.getNodeId() == nodeId) {
				filelocation = dtostfxmllocation.getBaseworkfolder();
				return filelocation;
			}
		}
		return filelocation;
	}

	public void generateStudyDocument(String wsId, Vector stffirstnodes) {
		try {
			String nodeName = "";
			int nodeId = 0;
			String nodecategory = "";
			String stfdata = "";
			int stfparentnodeid = 0;
			int nodeno = 0;

			for (int i = 0; i < stffirstnodes.size(); i++) {
				DTOWorkSpaceNodeDetail dto = (DTOWorkSpaceNodeDetail) stffirstnodes
						.get(i);

				stfparentnodeid = dto.getNodeId();

				int isLeaf = docMgmtInt.isLeafNodes(wsId, stfparentnodeid);
				String filelocation = getSTFFileLocation(stfparentnodeid);
				if (filelocation == null || filelocation.equals("")) {
					// This node is not going to publish as no STF docs were
					// changed
					continue;
				}

				upCount = getUpCount(filelocation);
				if (isLeaf == 0) {
					String studyidendata = "";
					stfdata = "<study-document>";
					BufferedWriter out = null;

					Vector<DTOWorkSpaceNodeDetail> getAllChildNodes = docMgmtInt
							.getAllChildSTFNodes(wsId, stfparentnodeid);

					for (int j = 0; j < getAllChildNodes.size(); j++) {
						DTOWorkSpaceNodeDetail stfNodeDtl = getAllChildNodes
								.get(j);
						nodeId = stfNodeDtl.getNodeId();
						nodeName = stfNodeDtl.getNodeName();
						nodecategory = stfNodeDtl.getStfNodeCategoryName();
						nodeno = stfNodeDtl.getNodeNo();

						if (nodeno == 1) {
							if (AllNodesofHistory.contains(nodeId)) {
								studyidendata = generateStudyIndetifier(nodeId,
										wsId);
								DTOWorkSpaceNodeHistory stfhistory = (DTOWorkSpaceNodeHistory) docMgmtInt
										.getLastNodeHistory(wsId, nodeId)
										.get(0);
								fileName = stfhistory.getFileName();
								out = new BufferedWriter(new FileWriter(
										filelocation + "/" + fileName));
								out.write(studyidendata);
							} else {// if STF XML is not in history do not
								// generate it.
								break;
							}
						} else {
							// if file attached at node or not and also the
							// 'operation' is not 'delete'
							if (AllNodesofHistory.contains(nodeId)
									&& !stfNodeDtl.getAttrValue()
											.equalsIgnoreCase("delete")) {
								if (stfNodeDtl.getNodeTypeIndi() == 'S') {// if
									// node
									// is
									// leaf
									// node
									// (old
									// code)
									stfdata += "<doc-content xlink:href=\"";
									for (int icount = 0; icount < upCount + 1; icount++) {
										stfdata += "../";
									}
									stfdata += currentSequenceNumber + "/"
											+ "index.xml" + "#node-" + nodeId
											+ "\">\n";
									stfdata += "<file-tag name=\"" + nodeName
											+ "\" info-type=\"" + nodecategory
											+ "\"/>\n";
									stfdata += "</doc-content>\n";
								} else if (stfNodeDtl.getNodeTypeIndi() == 'F') {// node
									// has
									// children
									// (else
									// new
									// code)
									/**
									 * This 'else if' block is Added By Ashmak
									 * Shah For adding nodes like CRFs into stf
									 * xml file
									 */
									Vector<DTOWorkSpaceNodeDetail> allMultipleTypeChildNodes = docMgmtInt
											.getAllChildSTFNodes(wsId, nodeId);

									for (int k = 0; k < allMultipleTypeChildNodes
											.size(); k++) {
										DTOWorkSpaceNodeDetail multipleTypeChildNode = allMultipleTypeChildNodes
												.get(k);

										// if file attached at node or not and
										// also the 'operation' is not 'delete'
										if (AllNodesofHistory
												.contains(multipleTypeChildNode
														.getNodeId())
												&& !multipleTypeChildNode
														.getAttrValue()
														.equalsIgnoreCase(
																"delete")) {
											stfdata += "<doc-content xlink:href=\"";
											for (int icount = 0; icount < upCount + 1; icount++) {
												stfdata += "../";
											}
											stfdata += currentSequenceNumber
													+ "/"
													+ "index.xml"
													+ "#node-"
													+ multipleTypeChildNode
															.getNodeId()
													+ "\">\n";

											/**
											 * Add property tag for nodes like
											 * CRFs <property
											 * name="site-identifier"
											 * info-type="us">JCD02</property>
											 */
											Vector siteIdVector = docMgmtInt
													.getSTFIdentifierByNodeId(
															wsId,
															multipleTypeChildNode
																	.getNodeId());
											DTOSTFStudyIdentifierMst property = (DTOSTFStudyIdentifierMst) siteIdVector
													.get(0);// first attribute
											// (name)

											stfdata += "<"
													+ property.getTagName()
													+ " ";// start property tag
											stfdata += property.getAttrName()
													+ "=\""
													+ property.getAttrValue()
													+ "\" ";
											property = (DTOSTFStudyIdentifierMst) siteIdVector
													.get(1);// next attrbute
											// (info-type)
											stfdata += property.getAttrName()
													+ "=\""
													+ property.getAttrValue()
													+ "\" >";
											stfdata += property
													.getNodeContent();// Site-Identifier
											// value
											stfdata += "</"
													+ property.getTagName()
													+ ">\n";// end property tag

											stfdata += "<file-tag name=\""
													+ multipleTypeChildNode
															.getNodeName()
													+ "\" info-type=\""
													+ multipleTypeChildNode
															.getStfNodeCategoryName()
													+ "\"/>\n";
											stfdata += "</doc-content>\n";
										}
									}
								}
							}
						}
						stfNodeDtl = null;
					}

					stfdata += "</study-document></ectd:study>\n";

					// if STF XML is not in history then 'out' will be null.
					if (out != null) {
						out.write(stfdata);
						out.close();
					}
					dto = null;
					getAllChildNodes = null;
				}
			}
		} catch (Exception e) {
			System.out.println("Exception e : - ");
			e.printStackTrace();
		}
	}

	private int getUpCount(String filelocation) {
		filelocation = filelocation
				.substring((absolutePathToCreate.length()) + 1);
		int count = 1;
		for (int j = 0; j < filelocation.length(); j++) {
			if ('\\' == filelocation.charAt(j) || '/' == filelocation.charAt(j)) {
				count++;
			}
		}
		return count;
	}

	public Vector getAllNodesofHistory() {
		return AllNodesofHistory;
	}

	public void setAllNodesofHistory(Vector allNodesofHistory) {
		AllNodesofHistory = allNodesofHistory;
	}

	public String getCurrentSequenceNumber() {
		return currentSequenceNumber;
	}

	public void setCurrentSequenceNumber(String currentSequenceNumber) {
		this.currentSequenceNumber = currentSequenceNumber;
	}

	public char getProjectPublishType() {
		return projectPublishType;
	}

	public void setProjectPublishType(char projectPublishType) {
		this.projectPublishType = projectPublishType;
	}

	public int[] getLeafIds() {
		return leafIds;
	}

	public void setLeafIds(int[] leafIds) {
		this.leafIds = leafIds;
	}

	public boolean isNodeHavingClones(String nodename, int nodeId) {
		Vector<DTOWorkSpaceNodeDetail> repeatNodeAndSiblingsDtl = docMgmtInt
				.getChildNodeByParent(docMgmtInt.getParentNodeId(wsId, nodeId),
						wsId);
		Vector<DTOWorkSpaceNodeDetail> originalNodeWithAllclones = new Vector<DTOWorkSpaceNodeDetail>();
		for (int i = 0; i < repeatNodeAndSiblingsDtl.size(); i++) {
			DTOWorkSpaceNodeDetail currentSibling = repeatNodeAndSiblingsDtl
					.get(i);
			// System.out.println(currentSibling.getNodeName());
			if (currentSibling.getNodeName().equalsIgnoreCase(nodename)) {
				originalNodeWithAllclones.addElement(repeatNodeAndSiblingsDtl
						.get(i));
				if (originalNodeWithAllclones.size() > 1) {
					return true;
				}
			}
		}
		return false;
	}
}// main class end
