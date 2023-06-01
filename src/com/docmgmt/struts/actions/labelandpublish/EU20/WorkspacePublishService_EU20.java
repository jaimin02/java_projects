package com.docmgmt.struts.actions.labelandpublish.EU20;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import com.docmgmt.dto.DTOSTFStudyIdentifierMst;
import com.docmgmt.dto.DTOSubmissionInfoEU14SubDtl;
import com.docmgmt.dto.DTOSubmissionInfoEU20SubDtl;
import com.docmgmt.dto.DTOSubmissionInfoEUSubDtl;
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

public class WorkspacePublishService_EU20 {

	public Writer writer;
	public XmlWriter xmlwriter;

	public BufferedWriter out;
	public BufferedWriter out1;

	// Second buffered writer required to when with
	// eu-regional selected.

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
	public String sversion="";
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
	public String EUDtdVersion;
	// added by hardik shah

	Vector<Integer> AllNodesofHistory = new Vector<Integer>();

	DocMgmtImpl docMgmtInt = new DocMgmtImpl();

	HttpServletRequest request;

	/* Added By : Ashmak Shah */
	private String currentSequenceNumber;

	private char projectPublishType;
	private int[] leafIds;
	FileManager fileManager;

	public ArrayList<String> allNodesDtl;
	boolean isTrackingTableUploaded = false;

	public WorkspacePublishService_EU20() {
		PropertyInfo propInfo = PropertyInfo.getPropInfo();
		publishDestFolderName = propInfo.getValue("PublishFolder");
		fileManager = new FileManager();
		allNodesDtl = new ArrayList<String>();
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
			sversion=publishForm.getRegionalDtdVersion();
			EUDtdVersion=publishForm.getEUDTDVersion();
			currentSequenceNumber = publishForm.getSeqNumber();

			System.out.println("Current Seq=" + currentSequenceNumber);

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

			if (stype.equals("eu")) {
				iParentId = 1;
				File regionalXml = new File(absolutePathToCreate + "/m1/"
						+ stype + "/" + stype + "-regional.xml");
				regionalXml.getParentFile().mkdirs();
				out = new BufferedWriter(new FileWriter(regionalXml));

				writeToXmlFile(stype, publishForm);

				parentNodes = docMgmtInt.getChildNodeByParentForPublishForM1(
						iParentId, wsId);

				getChildNode(parentNodes, absolutePathToCreate, iParentId, 0,
						stype, publishForm);
				xmlwriter.close();
				out.write(writer.toString());

				out.write("</eu:eu-backbone>");

				out.flush();
				out.close();
				writer = null;
				xmlwriter = null;
				if (!isTrackingTableUploaded) {
					if (publishForm.getAddTT() == 'Y') {
						TrackingTableGenerator.addTrackingTable(regionalXml,
								wsId, publishForm.getSubmissionId(), "20");
					}
					if (publishForm.getAddTT() == 'N') {
						// TrackingTableGenerator.addTrackingTable(regionalXml,
						// wsId,
						// publishForm.getSubmissionId(), "20");
						TrackingTableGenerator.addLinkInEuRegional(regionalXml,
								wsId, publishForm.getSubmissionId(), "20");
					}

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
			// Delete util folder from m1/eu
			File utilPath = new File(absolutePathToCreate + "/m1/eu/util");
			if (utilPath.exists()) {
				FileManager.deleteDir(utilPath);
				System.out.println("File Exist>>>>>>");
			}

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
					if (pathToCreate.matches(".*/m1/eu/.*-cover.*")) {
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
								if (pathToCreate.contains("m1/eu")) {
									query.append("../../../");
									query.append("" + lastPubVersion + "");

									query.append("/m1/eu/eu-regional.xml#");
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
								}
								else if (nodetypeindi.trim().equals("G")) {
									String newPath = pathToCreate.substring(0,pathToCreate.lastIndexOf("/"));
									// System.out.print("File Path(new)="+newPath);
									attrValue = copyFileforPublish(publishForm,
											newPath);
								}
								else
									attrValue = copyFileforPublish(publishForm,
											pathToCreate);
								attrValue = attrValue.toLowerCase();
								stype = stype.toLowerCase();
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
				allNodesDtl.add(nodeDisplayName);
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
									xmlwriter.writeEntity(nodeName.trim());
								}

								allNodesDtl.add(nodeName);
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
										else{
											
										// remove space and give - in attribute
										// value
												if (attrvalue.length() > 4
														&& !attrvalue
																.equalsIgnoreCase("common")) {
		
													System.out.println("attrvalue: "
															+ attrvalue);
		
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
													
													System.out.println("MergeAttributeStr->"+MergeAttributeStr);
												}
										}

										

									}
									/*
									 * 
									 * Description: If repeated node with same
									 * attribute with different value, then
									 * create the attribute folder other wise
									 * not to required create attribute folder.
									 */

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
									}

									else if (nodetypeindi.trim().equals("C")) {
										if (!isNodeHavingClones(
												nodeName.trim(), nodeId)) {
											MergeAttributeStr = "";
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

			if (fileName.contains("common-tracking.pdf")) {
				isTrackingTableUploaded = true;
			}

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
			if(stype.equals("eu") && sversion.equals("20") ){
				File inDtdFile = new File(baseWorkDir.getAbsoluteFile()
						+ "/util_EU20/util/dtd");
	
				File outDtdFile = new File(absolutePathToCreate + "/util/dtd");
				outDtdFile.mkdirs();
	
				fileManager.copyDirectory(inDtdFile, outDtdFile);
				
				File inStyleFile = new File(baseWorkDir.getAbsoluteFile()
						+ "/util_EU20/util/style");

				File outStyleFile = new File(absolutePathToCreate + "/util/style");
				outStyleFile.mkdirs();

				fileManager.copyDirectory(inStyleFile, outStyleFile);
			}
			else if(stype.equals("eu") && sversion.equals("301") ){
				File inDtdFile = new File(baseWorkDir.getAbsoluteFile()
						+ "/util_eu301/util/dtd");
	
				File outDtdFile = new File(absolutePathToCreate + "/util/dtd");
				outDtdFile.mkdirs();
	
				fileManager.copyDirectory(inDtdFile, outDtdFile);
				
				File inStyleFile = new File(baseWorkDir.getAbsoluteFile()
						+ "/util_eu301/util/style");

				File outStyleFile = new File(absolutePathToCreate + "/util/style");
				outStyleFile.mkdirs();

				fileManager.copyDirectory(inStyleFile, outStyleFile);
			}
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

		if (stype.equals("eu")) {
			String XmldtdVersionDeclaration="";
			String xmlDeclaration = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
			String docTypeDeclaration = "<!DOCTYPE eu:eu-backbone SYSTEM \"../../util/dtd/eu-regional.dtd\">";
			String xslStyleSheetDeclaration = "<?xml-stylesheet type=\"text/xsl\" href=\"../../util/style/eu-regional.xsl\"?>";
			if(sversion.equals("20")){
				XmldtdVersionDeclaration = "<eu:eu-backbone xmlns:eu=\"http://europa.eu.int\" xmlns:xlink=\"http://www.w3c.org/1999/xlink\" dtd-version=\"2.0\">";
			}
			else{
				XmldtdVersionDeclaration = "<eu:eu-backbone xmlns:eu=\"http://europa.eu.int\" xmlns:xlink=\"http://www.w3c.org/1999/xlink\" dtd-version=\"3.0.1\">";
			}

			out.write(xmlDeclaration);
			out.write("\n" + docTypeDeclaration);
			out.write("\n" + xslStyleSheetDeclaration);
			out.write("\n" + XmldtdVersionDeclaration);
			out.write("\n");
			out.write("\n");
			// Start of eu-envelope section
			out.write("\n" + "<eu-envelope>");

			/**
			 * In case of NP & CP envelope details will be found as if it is an
			 * RMS in case of MRP & DCP
			 */
			ArrayList<DTOSubmissionInfoEUSubDtl> cmsDtl;
			if(EUDtdVersion.equals("13")){
			
				cmsDtl = docMgmtInt.getWorkspaceCMSSubmissionInfo(publishForm.getSubmissionId(),"13");
				if (cmsDtl.size() > 0) {
					publishForm.setSubmissionDescription(cmsDtl.get(0)
							.getSubmissionDescription());
				} else {
					// If no CMS Info found then get RMS Info.
					DTOSubmissionInfoEUSubDtl dtoRms = docMgmtInt
							.getWorkspaceRMSSubmissionInfo(publishForm
									.getSubmissionId());
					// select * from SubmissionInfoEUSubDtl where
					// vSubmissionInfoEUDtlId = '"+submissionId+"' AND
					// iWorkspaceCMSId = 0

					publishForm.setSubmissionDescription(dtoRms
							.getSubmissionDescription());
				}
			}
			else if(EUDtdVersion.equals("14")){
				cmsDtl = docMgmtInt.getWorkspaceCMSSubmissionInfo(publishForm.getSubmissionId(),"14");
				if (cmsDtl.size() > 0) {
					publishForm.setSubmissionDescription(cmsDtl.get(0)
							.getSubmissionDescription());
				} else {
					// If no CMS Info found then get RMS Info.
					DTOSubmissionInfoEU14SubDtl dtoRms = docMgmtInt
							.getWorkspaceRMSSubmissionInfoEU14(publishForm
									.getSubmissionId());
					publishForm.setSubmissionDescription(dtoRms
							.getSubmissionDescription());
				}
			}	
			else{
				cmsDtl = docMgmtInt.getWorkspaceCMSSubmissionInfo(publishForm.getSubmissionId(),"20");
				if (cmsDtl.size() > 0) {
					publishForm.setSubmissionDescription(cmsDtl.get(0)
							.getSubmissionDescription());
				} else {
					// If no CMS Info found then get RMS Info.
					DTOSubmissionInfoEU20SubDtl dtoRms = docMgmtInt
							.getWorkspaceRMSSubmissionInfoEU20(publishForm
									.getSubmissionId());
					publishForm.setSubmissionDescription(dtoRms
							.getSubmissionDescription());
				}
			}
			

			// Envelope for RMS
			if (publishForm.getRMSSelected() == 'Y') {
				
				if(sversion.equals("20")){

					// Start of envelope
	
					if (publishForm.getCountry().toLowerCase().equals("emea")) {
						publishForm.setCountry("ema");
					}
					out.write("\n" + "<envelope country=\""
							+ publishForm.getCountry().toLowerCase() + "\">");
	
					/*******************************************************/
					// Submission nos.
					// high level tracking no
					// submission type and mode
					out.write("\n" + "<submission ");
					out
							.write("type=\"" + publishForm.getSubmissionType_eu()
									+ "\"");
					if (publishForm.getSubmissionMode() != null
							&& !publishForm.getSubmissionMode().equals(""))
						out.write(" mode=\"" + publishForm.getSubmissionMode()
								+ "\"");
					out.write(">");
					if (publishForm.getHighLvlNo() != null
							&& !publishForm.getHighLvlNo().equals("")) {
						out.write("<number>" + publishForm.getHighLvlNo()
								+ "</number>");
					}
					out.write("\n" + "<tracking>");
					if (publishForm.getApplicationNumber() != null
							&& !publishForm.getApplicationNumber().equals("")) {
						String[] appNums = publishForm.getApplicationNumber()
								.split(",");
						for (int iNum = 0; iNum < appNums.length; iNum++) {
							// out.write("\n" +
							// "<number>"+appNums[iNum].replaceAll("-",
							// "/").trim()+"</number>");
							out.write("\n" + "<number>" + appNums[iNum].trim()
									+ "</number>");
						}
					} else {
						out.write("\n" + "<number></number>");
					}
					out.write("\n" + "</tracking></submission>");
					/*******************************************************/
	
					// Applicant
					out.write("\n" + "<applicant>" + publishForm.getApplicant()
							+ "</applicant>");
					// Agency
					out.write("\n" + "<agency code=" + "\""
							+ publishForm.getAgencyName() + "\"" + " />");
					// Procedure Type
					out.write("\n" + "<procedure type=\""
							+ publishForm.getProcedureType() + "\"/>");
					// Invented-name
					if (publishForm.getInventedName() != null
							&& !publishForm.getInventedName().equals("")) {
						String[] inventedName = publishForm.getInventedName().split(",");
						for (int iIn = 0; iIn < inventedName.length; iIn++) {
							out
									.write("\n" + "<invented-name>" + inventedName[iIn].trim()
											+ "</invented-name>");
						}
					}
					// INNs
					if (publishForm.getInn() != null
							&& !publishForm.getInn().equals("")) {
						String[] INNs = publishForm.getInn().split(",");
						for (int iInn = 0; iInn < INNs.length; iInn++) {
							out
									.write("\n" + "<inn>" + INNs[iInn].trim()
											+ "</inn>");
						}
					}
					// Sequence number
					out.write("\n" + "<sequence>" + publishForm.getSeqNumber()
							+ "</sequence>");
					// Related sequence numbers
					if (publishForm.getRelatedSeqNumber() != null
							&& !publishForm.getRelatedSeqNumber().equals("")) {
						String[] relatedSeqs = publishForm.getRelatedSeqNumber()
								.split(",");
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
				}else if(sversion.equals("301")){


					// Start of envelope
	
					if (publishForm.getCountry().toLowerCase().equals("emea")) {
						publishForm.setCountry("ema");
					}
					out.write("\n" + "<envelope country=\""
							+ publishForm.getCountry().toLowerCase() + "\">");
					
					out.write("\n" + "<identifier>"+publishForm.getUuid()+"</identifier>");
					/*******************************************************/
					// Submission nos.
					// high level tracking no
					// submission type and mode
					out.write("\n" + "<submission ");
					out
							.write("type=\"" + publishForm.getSubmissionType_eu()
									+ "\"");
					if (publishForm.getSubmissionMode() != null
							&& !publishForm.getSubmissionMode().equals(""))
						out.write(" mode=\"" + publishForm.getSubmissionMode()
								+ "\"");
					out.write(">");
					if (publishForm.getHighLvlNo() != null
							&& !publishForm.getHighLvlNo().equals("")) {
						out.write("<number>" + publishForm.getHighLvlNo()
								+ "</number>");
					}
					out.write("\n" + "<procedure-tracking>");
					if (publishForm.getApplicationNumber() != null
							&& !publishForm.getApplicationNumber().equals("")) {
						String[] appNums = publishForm.getApplicationNumber()
								.split(",");
						for (int iNum = 0; iNum < appNums.length; iNum++) {
							// out.write("\n" +
							// "<number>"+appNums[iNum].replaceAll("-",
							// "/").trim()+"</number>");
							out.write("\n" + "<number>" + appNums[iNum].trim()
									+ "</number>");
						}
					} else {
						out.write("\n" + "<number></number>");
					}
					out.write("\n" + "</procedure-tracking></submission>");
					/*******************************************************/
	
					// submission Unit Type
					
					out.write("\n" + "<submission-unit type=\""
							+ publishForm.getSubmissionUnitType() + "\"/>");
					// Applicant
					out.write("\n" + "<applicant>" + publishForm.getApplicant()
							+ "</applicant>");
					// Agency
					out.write("\n" + "<agency code=" + "\""
							+ publishForm.getAgencyName() + "\"" + " />");
					// Procedure Type
					out.write("\n" + "<procedure type=\""
							+ publishForm.getProcedureType() + "\"/>");
					// Invented-name
					
					if (publishForm.getInventedName() != null
							&& !publishForm.getInventedName().equals("")) {
						String[] inventedName = publishForm.getInventedName().split(",");
						for (int iIn = 0; iIn < inventedName.length; iIn++) {
							out
									.write("\n" + "<invented-name>" + inventedName[iIn].trim()
											+ "</invented-name>");
						}
					}
					// INNs
			
					if (publishForm.getInn() != null
							&& !publishForm.getInn().equals("")) {
						String[] INNs = publishForm.getInn().split(",");
						for (int iInn = 0; iInn < INNs.length; iInn++) {
							out
									.write("\n" + "<inn>" + INNs[iInn].trim()
											+ "</inn>");
						}
					}
					// Sequence number
					out.write("\n" + "<sequence>" + publishForm.getSeqNumber()
							+ "</sequence>");
					// Related sequence numbers
					if(publishForm.getSeqNumber().equalsIgnoreCase("0000")){
						out.write("\n" + "<related-sequence>0000</related-sequence>");
					}
					else if (publishForm.getRelatedSeqNumber() != null
							&& !publishForm.getRelatedSeqNumber().equals("")) {
						String[] relatedSeqs = publishForm.getRelatedSeqNumber()
								.split(",");
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
			}
			// Envelopes for selected CMSs
			if (publishForm.getProcedureType().equalsIgnoreCase(
					"mutual-recognition")
					|| publishForm.getProcedureType().equalsIgnoreCase(
							"decentralised")) {
				for (int i = 0; i < cmsDtl.size(); i++) {
					if(sversion.equals("20")){
						// Start of envelope
						out.write("\n" + "<envelope country=\""
								+ cmsDtl.get(i).getCountryCodeName().toLowerCase()
								+ "\">");
	
						/*******************************************************/
						// Submission nos.
						// high level tracking no
						// submission type and mode
						out.write("\n" + "<submission ");
						out.write("type=\"" + publishForm.getSubmissionType_eu()
								+ "\"");// 
						if (publishForm.getSubmissionMode() != null
								&& !publishForm.getSubmissionMode().equals(""))
							out.write(" mode=\"" + publishForm.getSubmissionMode()
									+ "\"");
						out.write(">");
						if (publishForm.getHighLvlNo() != null
								&& !publishForm.getHighLvlNo().equals("")) {
							out.write("<number>" + publishForm.getHighLvlNo()
									+ "</number>");
						}
						out.write("\n" + "<tracking>");
						// if(publishForm.getApplicationNumber() != null &&
						// !publishForm.getApplicationNumber().equals(""))
						if (cmsDtl.get(i).getPublishCMSTrackingNo() != null
								&& !cmsDtl.get(i).getPublishCMSTrackingNo().equals(
										"")) {
							String[] appNums = cmsDtl.get(i)
									.getPublishCMSTrackingNo().split(",");
							for (int iNum = 0; iNum < appNums.length; iNum++) {
								out.write("\n" + "<number>" + appNums[iNum].trim()
										+ "</number>");
							}
						}
						out.write("\n" + "</tracking></submission>");
						/*******************************************************/
	
						// Applicant
						out.write("\n" + "<applicant>" + publishForm.getApplicant()
								+ "</applicant>");
						// Agency
						out.write("\n" + "<agency code=" + "\""
								+ cmsDtl.get(i).getAgencyName() + "\"" + " />");
						// Procedure Type
						out.write("\n" + "<procedure type=\""
								+ publishForm.getProcedureType() + "\"/>");
						// Invented-name
						if (cmsDtl.get(i).getInventedName() == null
								|| cmsDtl.get(i).getInventedName().trim().equals("")) {
							out.write("\n" + "<invented-name>"
									+ publishForm.getInventedName()
									+ "</invented-name>");
						} else {
							out.write("\n" + "<invented-name>"
									+ cmsDtl.get(i).getInventedName()
									+ "</invented-name>");
						}
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
						out.write("\n" + "<sequence>" + publishForm.getSeqNumber()
								+ "</sequence>");
						// Related sequence numbers
						if (publishForm.getRelatedSeqNumber() != null
								&& !publishForm.getRelatedSeqNumber().equals("")) {
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
					}else if(sversion.equals("301")){
						

						// Start of envelope
						out.write("\n" + "<envelope country=\""
								+ cmsDtl.get(i).getCountryCodeName().toLowerCase()
								+ "\">");
	
						/*******************************************************/
						// Submission nos.
						// high level tracking no
						// submission type and mode
						out.write("\n" + "<identifier>"+publishForm.getUuid()+"</identifier>");
						out.write("\n" + "<submission ");
						out.write("type=\"" + publishForm.getSubmissionType_eu()
								+ "\"");// 
						if (publishForm.getSubmissionMode() != null
								&& !publishForm.getSubmissionMode().equals(""))
							out.write(" mode=\"" + publishForm.getSubmissionMode()
									+ "\"");
						out.write(">");
						if (publishForm.getHighLvlNo() != null
								&& !publishForm.getHighLvlNo().equals("")) {
							out.write("<number>" + publishForm.getHighLvlNo()
									+ "</number>");
						}
						out.write("\n" + "<procedure-tracking>");
						// if(publishForm.getApplicationNumber() != null &&
						// !publishForm.getApplicationNumber().equals(""))
						if (cmsDtl.get(i).getPublishCMSTrackingNo() != null
								&& !cmsDtl.get(i).getPublishCMSTrackingNo().equals(
										"")) {
							String[] appNums = cmsDtl.get(i)
									.getPublishCMSTrackingNo().split(",");
							for (int iNum = 0; iNum < appNums.length; iNum++) {
								out.write("\n" + "<number>" + appNums[iNum].trim()
										+ "</number>");
							}
						}
						out.write("\n" + "</procedure-tracking></submission>");
						/*******************************************************/
						// submission Unit Type
						
						out.write("\n" + "<submission-unit type=\""
								+ publishForm.getSubmissionUnitType() + "\"/>");
						// Applicant
						out.write("\n" + "<applicant>" + publishForm.getApplicant()
								+ "</applicant>");
						// Agency
						out.write("\n" + "<agency code=" + "\""
								+ cmsDtl.get(i).getAgencyName() + "\"" + " />");
						// Procedure Type
						out.write("\n" + "<procedure type=\""
								+ publishForm.getProcedureType() + "\"/>");
						// Invented-name
						if (cmsDtl.get(i).getInventedName() == null
								|| cmsDtl.get(i).getInventedName().trim().equals("")) {

							String[] RMSInventedName = publishForm.getInventedName().split(",");
							for (int iRIn = 0; iRIn < RMSInventedName.length; iRIn++) {
								out
										.write("\n" + "<invented-name>" + RMSInventedName[iRIn].trim()
												+ "</invented-name>");
							}
						
						} else {

							String[] CMSInventedName = cmsDtl.get(i).getInventedName().split(",");
							for (int iCIn = 0; iCIn < CMSInventedName.length; iCIn++) {
								out
										.write("\n" + "<invented-name>" + CMSInventedName[iCIn].trim()
												+ "</invented-name>");
							}
						}
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
						out.write("\n" + "<sequence>" + publishForm.getSeqNumber()
								+ "</sequence>");
						// Related sequence numbers
						
						if(publishForm.getSeqNumber().equalsIgnoreCase("0000")){
							out.write("\n" + "<related-sequence>0000</related-sequence>");
						}
						else if (publishForm.getRelatedSeqNumber() != null
								&& !publishForm.getRelatedSeqNumber().equals("")) {
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
				}
			}
			out.write("\n" + "</eu-envelope>");// End of envelopes
		}

		else if (stype.equals("eum2-m5")) {
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

			String eumd5 = md5.getMd5HashCode(absolutePathToCreate + "/m1/eu"
					+ "/eu-regional.xml");
			// System.out
			// .println("CheckSum ="
			// +
			// "<leaf xml:lang=\"en\" checksum-type=\"md5\" modified-file=\"\" operation=\"new\" application-version=\"\" xlink:href=\"m1/eu/eu-regional.xml\" checksum=\""
			// + eumd5
			// + "\" ID=\"node-999\" xlink:type=\"simple\">");
			// out.write("<leaf xml:lang=\"en\" checksum-type=\"md5\" modified-file=\"\" operation=\"new\" application-version=\"\" xlink:href=\"m1/eu/eu-regional.xml\" checksum=\""+eumd5+"\" ID=\"node-999\" xlink:type=\"simple\">");
			out
					.write("<leaf xml:lang=\"en\" checksum-type=\"md5\" operation=\"new\" application-version=\"\" xlink:href=\"m1/eu/eu-regional.xml\" checksum=\""
							+ eumd5
							+ "\" ID=\"node-999\" xlink:type=\"simple\">");
			out.write("<title>");
			out.write("EU Module 1");

			out.write("</title>");
			out.write("</leaf>");
			out
					.write("</m1-administrative-information-and-prescribing-information>"
							+ "\n");
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
