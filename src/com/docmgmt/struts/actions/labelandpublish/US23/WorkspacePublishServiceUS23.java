package com.docmgmt.struts.actions.labelandpublish.US23;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.docmgmt.dto.DTOSTFStudyIdentifierMst;
import com.docmgmt.dto.DTOSubmissionInfoForManualMode;
import com.docmgmt.dto.DTOSubmittedWorkspaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeAttrHistory;
import com.docmgmt.dto.DTOWorkSpaceNodeAttribute;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.dto.DTOWorkspaceApplicationDetail;
import com.docmgmt.dto.PublishAttrForm;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.PropertyInfo;
import com.docmgmt.struts.actions.labelandpublish.FileManager;
import com.docmgmt.struts.resources.MD5;
import com.docmgmt.struts.resources.XmlWriter;
import com.opensymphony.xwork2.ActionContext;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class WorkspacePublishServiceUS23 {

	public Writer writer;
	public XmlWriter xmlwriter;

	public BufferedWriter out;
	public BufferedWriter out1; // Second buffered writer required to when with
	// eu-regional selected.

	public Vector childNodeForParent;
	public Vector childAttrForNode;
	public Vector parentNodes;
	public Vector childAttrId;
	public Vector attrDetail;
	public Vector attrNameForNode;
	public Vector folderDtl;
	public Vector folderPathDtl;
	public Vector fileNameDtl;
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

	
	private String currentSequenceNumber;

	private char projectPublishType;
	private int[] leafIds;

	public WorkspacePublishServiceUS23() {
		PropertyInfo propInfo = PropertyInfo.getPropInfo();
		publishDestFolderName = propInfo.getValue("PublishFolder");
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
				// Call for Proc_WorkSpaceNodeForRevisedSubmission_Doc
			} else if (projectPublishType == 'M') {
				// In case of Manual Mode getting all parents of the selected
				// nodes
				AllNodesofHistory = docMgmtInt.getWorkspaceTreeNodesForLeafs(
						workspaceId, leafIds);
				// Call for Proc_WorkSpaceTreeNodesForLeafs
			}

			// Copy Util folder
			addutilFolder(stype);

			if (stype.equals("us")) {
				iParentId = 1;
				File regionalXml = new File(absolutePathToCreate + "/m1/"
						+ stype + "/" + stype + "-regional.xml");
				regionalXml.getParentFile().mkdirs();
				out = new BufferedWriter(new FileWriter(regionalXml));

				// out = new BufferedWriter(new OutputStreamWriter(new
				// FileOutputStream(regionalXml),"UTF-8"));

				writeToXmlFile(stype, publishForm);
				
				

				parentNodes = docMgmtInt.getChildNodeByParentForPublishForM1(
						iParentId, wsId);
				
				
				// select
				// vWorkspaceId,iNodeId,vNodeName,vNodeDisplayName,vFolderName,cNodeTypeIndi,vRemark
				// from workspaceNodeDetail where iParentNodeId="+iParentId+"
				// and vWorkspaceId='"+wsId+"' and iNodeNo= 1 order by iNodeNo

				getChildNode(parentNodes, absolutePathToCreate, iParentId,
						stype, publishForm);
				xmlwriter.close();
				out.write(writer.toString());
				
			
				if (stype.equals("us"))
					out.write("</fda-regional:fda-regional>");
				out.flush();
				out.close();
				writer = null;
				xmlwriter = null;

				
				parseFile(regionalXml);
				
				
				writer = new java.io.StringWriter();
				xmlwriter = new XmlWriter(writer);
				out = new BufferedWriter(new FileWriter((absolutePathToCreate)
						+ "/index.xml"));

				writeToXmlFile(stype + "m2-m5", publishForm);

				parentNodes = docMgmtInt
						.getChildNodeByParentForPublishFromM2toM5(iParentId,
								wsId);
				// select
				// vWorkspaceId,iNodeId,vNodeName,vNodeDisplayName,vFolderName,cNodeTypeIndi,vRemark
				// from workspaceNodeDetail where iParentNodeId="+iParentId+"
				// and vWorkspaceId='"+wsId+"' and iNodeNo<> 1 order by iNodeNo

				for (int i = 0; i < parentNodes.size(); i++) {
					DTOWorkSpaceNodeDetail dto = (DTOWorkSpaceNodeDetail) parentNodes
							.get(i);
					nodeId = dto.getNodeId();
					nodeName = dto.getNodeName();
					nodeDisplayName = dto.getNodeDisplayName();
					dto = null;

				}

				getChildNode(parentNodes, absolutePathToCreate, iParentId,
						stype, publishForm);
				xmlwriter.close();
				out.write(writer.toString());
				out.write("</ectd:ectd>");
				out.flush();
				out.close();
			} else if (stype.equals("without")) {
				iParentId = 1;
				out = new BufferedWriter(new FileWriter((absolutePathToCreate)
						+ "/index-without-eu.xml"));
				stype = "without";
				writeToXmlFile(stype, publishForm);
				parentNodes = docMgmtInt.getChildNodeByParent(iParentId, wsId);
				// select
				// vWorkspaceId,iNodeId,iNodeNo,vnodename,vnodedisplayname,vfoldername,cnodetypeindi,vRemark,cRequiredFlag,cCloneFlag,dModifyOn,iModifyBy
				// from workspacenodedetail where vWorkspaceId = '"+wsId+"' and
				// iParentNodeId ="+iParentId order by iNodeNo

				getChildNode(parentNodes, absolutePathToCreate, iParentId,
						stype, publishForm);
				xmlwriter.close();
				out.write(writer.toString());
				out.write("</ectd:ectd>");
				out.flush();
				out.close();
			}
			checkSumForindexFile();
			// copyEuRegionalFile(stype);

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
			int parentId, String stype, PublishAttrForm publishForm)
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
					// Call for Proc_AttributesForNodeForPublish

					modifiedFile = docMgmtInt.getAttributeValueOfModifiedFile(
							wsId, nodeId.intValue());
					// select max(vlastpublishversion) as
					// vLastPublishVersion,indexid from
					// submittedworkspacenodedetail");
					// query.append(" where vWorkspaceId = '"+wsId+"' and iNodeId ="+nodeId+" group by indexid");

				}

				// hardcoding cover letter and fda form as new
				if (childAttrId.size() != 0) {
					String operationValue = "";

					if (pathToCreate.matches(".*/m1/us/.*cover.*")
							|| pathToCreate.matches(".*/m1/us/.*fda.*")) {
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
							// xmlwriter.writeAttribute("modified-file","");
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
								} else if (pathToCreate.contains("m1/us")) {
									query.append("../../../");
									query.append("" + lastPubVersion + "");

									query.append("/m1/us/us-regional.xml#");
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

									// System.out.println("Path to create NEW->"+pathToCreate);
									String newPath = pathToCreate.substring(0,
											pathToCreate.substring(
													0,
													pathToCreate
															.lastIndexOf("/"))
													.lastIndexOf("/"));
									// System.out.print("File Path(new)="+newPath);
									attrValue = copyFileforPublish(publishForm,
											newPath);

									FileManager
											.deleteDir(new File(pathToCreate));

									// Remove Directory when nodetypeindi="I"
									// otherwise empty directory will publish.
									// (m3-2-p-4-6-novel-excipients)

								} else
									attrValue = copyFileforPublish(publishForm,
											pathToCreate);
								attrValue = attrValue.toLowerCase();
								stype = stype.toLowerCase();

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

					// System.out.println("NodeId else:"+nodeId);
					int isLeaf = docMgmtInt
							.isLeafNodes(wsId, nodeId.intValue());
					// select count(*) as LeafNodes from workspacenodedetail
					// where vWorkspaceId='"+wsId+"' and iParentNodeId="+nodeId

					nodetypeindi = Character
							.toString(dtowsnd.getNodeTypeIndi());
					if (isLeaf == 0) {
						if (nodetypeindi.equalsIgnoreCase("T"))// study-report-1.pdf
						// to
						// study-report-1
						// for STF
						// Parent nodes
						{
							int intchar = filepathelement.indexOf(".");
							String finalstr;
							if (intchar != -1) {
								finalstr = filepathelement
										.substring(0, intchar);
							} else {
								finalstr = filepathelement;
							}
							createSubFolders(pathToCreate, finalstr);

						}else if(nodeName.equalsIgnoreCase("datasets") || nodeName.equalsIgnoreCase("data-tabulation-dataset")|| nodeName.equalsIgnoreCase("data-listing-dataset")|| nodeName.equalsIgnoreCase("analysis-dataset")
								|| nodeName.equalsIgnoreCase("data-tabulation-dataset-sdtm")|| nodeName.equalsIgnoreCase("data-tabulation-dataset-send") 
								|| nodeName.equalsIgnoreCase("analysis-dataset-adam") || nodeName.equalsIgnoreCase("analysis-data-definition") 
								|| nodeName.equalsIgnoreCase("data-tabulation-data-definition") || nodeName.equalsIgnoreCase("data-listing-data-definition")){
							
							String path=pathToCreate;
							String studyid=path.substring(path.lastIndexOf('/') + 1).trim();
							String datasetsPath=filepathelement.substring(filepathelement.indexOf('/') + 1).trim();
							filepathelement="datasets/"+studyid+"/"+datasetsPath; 
							String newPath=publishForm.getPublishDestinationPath()+"/"+publishForm.getSeqNumber();
							//pathToCreate=publishForm.getPublishDestinationPath()+"/"+publishForm.getSeqNumber()+"/m5";
							path=path.substring(0, newPath.length()+3);
							createSubFolders(path, filepathelement);
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
									"m1-administrative-information-and-prescribing-information")
							&& !nodeName.trim().equals(
									"m1-administrative-information")) {

						if (isLeaf == 0) {// This is a parent node
							if (!nodetypeindi.trim().equalsIgnoreCase("T")
									&& !nodetypeindi.trim().equals("F")) {
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

								/**
								 * For nodeTypeIndi = 'P'
								 */
								String country = "", language = "";

								String PathForAttrFolder = "";

								Vector nodeAttribute = docMgmtInt
										.getNodeAttributes(wsId, nodeId
												.intValue());
								// select * from workspaceNodeAttrDetail where
								// vWorkspaceId='"+wsId+"' and vAttrForIndiId =
								// '0002' and iNodeId="+nodeId

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
												&& !attrvalue.equals("")) {

											xmlwriter.writeAttribute(attrname,
													attrvalue);
										}

										// remove space and give - in attribute
										// value
										if (attrvalue.length() > 4
												&& !attrvalue
														.equalsIgnoreCase("common") && !attrname.equalsIgnoreCase("form-type")) {

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
										// No change in 'MergeAttributeStr'
									}
									/**
									 * nodeTypeIndi = 'N' (normal node)
									 * Description : Folder will be created from
									 * vFolderName
									 */
									else {

										System.out.println("Path to Create-"
												+ pathToCreate);
										PathForAttrFolder = pathToCreate + "/"
												+ filepathelement;
									}

									createSubFolders(PathForAttrFolder,
											MergeAttributeStr);
									parentNodes = docMgmtInt
											.getChildNodeByParent(nodeId
													.intValue(), wsId);
									// select
									// vWorkspaceId,iNodeId,iNodeNo,vnodename,vnodedisplayname,vfoldername,cnodetypeindi,vRemark,cRequiredFlag,cCloneFlag,dModifyOn,iModifyBy
									// from workspacenodedetail where
									// vWorkspaceId = '"+wsId+"' and
									// iParentNodeId ="+parentNodeId order by
									// iNodeNo
									// Here, inodeid passed as a parameter in
									// function will be parentnodeid

									getChildNode(parentNodes,
											relativePathToCreate, nodeId
													.intValue(), stype,
											publishForm);
									xmlwriter.endEntity();
								} else {
									parentNodes = docMgmtInt
											.getChildNodeByParent(nodeId
													.intValue(), wsId);
									// same query for workspacenodedetail as
									// above

									getChildNode(parentNodes,
											relativePathToCreate, nodeId
													.intValue(), stype,
											publishForm);
									xmlwriter.endEntity();
								}
							} else {
								parentNodes = docMgmtInt.getChildNodeByParent(
										nodeId.intValue(), wsId);
								// same query for workspacenodedetail as above

								getChildNode(parentNodes, relativePathToCreate,
										nodeId.intValue(), stype, publishForm);
							}
						} else {
							parentNodes = docMgmtInt.getChildNodeByParent(
									nodeId.intValue(), wsId);
							// same query for workspacenodedetail as above

							getChildNode(parentNodes, relativePathToCreate,
									nodeId.intValue(), stype, publishForm);
						}
					} else {
						parentNodes = docMgmtInt.getChildNodeByParent(nodeId
								.intValue(), wsId);
						// same query for workspacenodedetail as above
						getChildNode(parentNodes, relativePathToCreate, nodeId
								.intValue(), stype, publishForm);
					}

					/* Added by Ashmak Shah */
					/*
					 * STF Nodes After CRFs were copied into the crf folder to
					 * avoid this below line is added.
					 */
					// System.out.println("Relative--" + relativePathToCreate);

					relativePathToCreate = pathToCreate;
					// System.out.println("Relative--" + relativePathToCreate);
				}// if end of history vector

			}// for loop end
		}// main else end

	}

	private void createBaseFolder(String bfoldername,
			PublishAttrForm publishForm) {
		try {
			folderDtl = new Vector();

			folderDtl = docMgmtInt.getFolderByWorkSpaceId(wsId);
			// select Distinct
			// vBaseWorkFolder,vBasePublishFolder,vLastPublishedVersion from
			// View_CommonWorkspaceDetail
			// where vworkspaceid='"+wsId+"'

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

		File folderName;

		// In case of Manual Mode Publish
		if (projectPublishType == 'M') {
			ArrayList<DTOWorkSpaceNodeHistory> nodeHistory = docMgmtInt
					.getAllNodesLastHistory(wsId,
							new int[] { nodeId.intValue() });
			// select
			// vWorkspaceId,vWorkSpaceDesc,vBaseWorkFolder,iNodeId,iTranNo,vFileName,vFolderName,
			// iStageId,vStageDesc,iModifyBy,vUserName,dModifyOn,vNodeName,vNodeFolderName,vUserDefineVersionId
			// from View_NodesLatestHistory where vWorkspaceId = '"+wsId+"' and
			// nodeId in (nodeIDs[]) order by vWorkspaceId,iNodeId

			fileNameDtl = new Vector();
			if (nodeHistory.size() > 0)
				fileNameDtl.addElement(nodeHistory.get(0));
		} else {
			fileNameDtl = docMgmtInt.getFileNameForNodeForPublish(nodeId
					.intValue(), wsId, publishForm.getLabelNo());
			// Call for Proc_FileNameForPublish
		}

		if (fileNameDtl.size() > 0) {
			DTOWorkSpaceNodeHistory dtowsand = (DTOWorkSpaceNodeHistory) fileNameDtl
					.get(0);

			fileName = dtowsand.getFileName();
			folderStructure = dtowsand.getFolderName();

			folderStruct = new File(sourceFolderName.trim()
					+ folderStructure.trim() + "/" + fileName);

			folderName = new File(absolutePath);
			addFiles(folderStruct, folderName);

			return (absolutePathLink.substring(1) + "/" + fileName);
		} else {
			return (absolutePathLink.substring(1));
		}
	}

	/*
	 * Add file at Publish Folder Path folderStruct = Source Folder File
	 * publishFolderStuct = Destination Folder File
	 */
	private void addFiles(File folderStruct, File publishFolderStuct) {
		try {

			FileInputStream fin = new FileInputStream(folderStruct
					.getCanonicalFile());
			// System.out.println("publishFolderStuct:::"+publishDestFolderName.toString());
			if (!publishFolderStuct.exists()) {
				publishFolderStuct.mkdirs();
			}

			FileOutputStream fout = new FileOutputStream(publishFolderStuct
					+ "/" + fileName);

			byte[] buf = new byte[1024 * 10];
			int len;

			while ((len = fin.read(buf)) > 0) {
				fout.write(buf, 0, len);
			}
			fin.close();
			fout.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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
			File indtdFile = null;

			indtdFile = new File(baseWorkDir.getAbsoluteFile()
					+ "/util_us23/util/dtd");

			File outdtdFile = new File(absolutePathToCreate + "/util/dtd");
			outdtdFile.mkdirs();

			String[] dtdfileList = indtdFile.list();
			for (int i = 0; i < dtdfileList.length; i++) {
				FileInputStream fin = new FileInputStream(indtdFile + "/"
						+ dtdfileList[i]);
				FileOutputStream fout = new FileOutputStream(outdtdFile + "/"
						+ dtdfileList[i]);
				byte[] buf = new byte[1024 * 10];
				int len;

				while ((len = fin.read(buf)) > 0) {
					fout.write(buf, 0, len);
				}
				fin.close();
				fout.close();
			}

			File instyleFile = null;

			instyleFile = new File(baseWorkDir.getAbsoluteFile()
					+ "/util_us23/util/style");

			File outstyleFile = new File(absolutePathToCreate + "/util/style");
			outstyleFile.mkdirs();

			String[] outstyleList = instyleFile.list();
			for (int i = 0; i < outstyleList.length; i++) {
				FileInputStream fin = new FileInputStream(instyleFile + "/"
						+ outstyleList[i]);
				FileOutputStream fout = new FileOutputStream(outstyleFile + "/"
						+ outstyleList[i]);
				byte[] buf = new byte[1024 * 10];
				int len;

				while ((len = fin.read(buf)) > 0) {
					fout.write(buf, 0, len);
				}
				fin.close();
				fout.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void writeToXmlFile(String stype, PublishAttrForm publishForm)
			throws Exception {
		if (stype.equals("us")) {
			String xmlDeclaration = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>";
			String XmldtdVersionDeclaration = "<!DOCTYPE fda-regional:fda-regional SYSTEM \"http://www.accessdata.fda.gov/static/eCTD/us-regional-v3-3.dtd\">";
			String xslStyleSheetDeclaration = "<?xml-stylesheet type=\"text/xsl\" href=\"http://www.accessdata.fda.gov/static/eCTD/us-regional.xsl\"?>";
			String XmlBackboneDeclaration = "<fda-regional:fda-regional xmlns:fda-regional=\"http://www.ich.org/fda\" xml:lang=\"text\" dtd-version=\"3.3\" xmlns:xlink=\"http://www.w3c.org/1999/xlink\">";

			out.write(xmlDeclaration);
			out.write("\n" + XmldtdVersionDeclaration);
			out.write("\n" + xslStyleSheetDeclaration);
			out.write("\n" + XmlBackboneDeclaration);
			out.write("\n");

			out.write("\n" + "<admin>");

			out.write("\n" + "<applicant-info>");

			if (publishForm.getApplicationId() != null
					&& !publishForm.getApplicationId().trim().equals("")) {
				out.write("\n" + "<id>" + publishForm.getApplicationId()
						+ "</id>");
			}

			out.write("\n" + "<company-name>" + publishForm.getCompanyName()
					+ "</company-name>");

			out.write("\n" + "<submission-description>"
					+ publishForm.getSubmissionDescription()
					+ "</submission-description>");

			out.write("\n" + "<applicant-contacts>");

			for (int i = 0; i < publishForm.getApplicantContactList().length; i++) {

				String applicantContactDetail[] = publishForm
						.getApplicantContactList()[i].split("#");

				String applicantContactType = applicantContactDetail[0];
				String applicantContactname = applicantContactDetail[1];
				String telephones[] = applicantContactDetail[3].split(";");
				String emails[] = applicantContactDetail[2].split(";");

				out.write("\n" + "<applicant-contact>");

				out.write("\n"
						+ "<applicant-contact-name applicant-contact-type=\""
						+ applicantContactType + "\">" + applicantContactname
						+ "</applicant-contact-name>");

				out.write("\n" + "<telephones>");

				for (int teleNumber = 0; teleNumber < telephones.length; teleNumber++) {

					String tele[] = telephones[teleNumber].split(":");

					out.write("\n" + "<telephone telephone-number-type=\""
							+ tele[0] + "\">" + tele[1] + "</telephone>");
				}
				out.write("\n" + "</telephones>");

				out.write("\n" + "<emails>");
				for (int emailNo = 0; emailNo < emails.length; emailNo++) {
					out.write("\n" + "<email>" + emails[emailNo] + "</email>");
				}
				out.write("\n" + "</emails>");

				out.write("\n" + "</applicant-contact>");

			}
			out.write("\n" + "</applicant-contacts>");

			out.write("\n" + "</applicant-info>");

			out.write("\n" + "<application-set>");

			out.write("\n"
					+ "<application application-containing-files=\"true\">");
			out.write("\n" + "<application-information>");
			out.write("\n" + "<application-number application-type=\""
					+ publishForm.getApplicationType() + "\">"
					+ publishForm.getApplicationNumber()
					+ "</application-number>");

			if (publishForm.getCrossReferenceNumber() != null
					&& !publishForm.getCrossReferenceNumber().trim().equals("")) {
				
				out
						.write("\n"
								+ "<cross-reference-application-number application-type=\""
								+ publishForm.getCrossRefAppType() + "\">"
								+ publishForm.getCrossReferenceNumber()
								+ "</cross-reference-application-number>");

			}

			out.write("\n" + "</application-information>");

			out.write("\n" + "<submission-information>");
			if (publishForm.getSuppEffectiveDateType() != null
					&& !publishForm.getSuppEffectiveDateType().trim().equals("") && !publishForm.getSuppEffectiveDateType().equals("-1")) {

				out.write("\n" + "<submission-id submission-type=\""
						+ publishForm.getSubmissionType() + "\" supplement-effective-date-type=\"" +
							publishForm.getSuppEffectiveDateType()+"\">"
						+ publishForm.getSubmissionId() + "</submission-id>");
			} else {
				out.write("\n" + "<submission-id submission-type=\""
						+ publishForm.getSubmissionType() + "\">"
						+ publishForm.getSubmissionId() + "</submission-id>");
			}
			out.write("\n" + "<sequence-number submission-sub-type=\""
					+ publishForm.getSubmissionSubType() + "\">"
					+ publishForm.getSeqNumber() + "</sequence-number>");

			out.write("\n" + "</submission-information>");

			out.write("\n" + "</application>");

			// check here - is group submission if yes then write application
			// details

			Vector<DTOWorkspaceApplicationDetail> workspaceApplicationDetails = docMgmtInt
					.getWorkspaceApplicationDetail(publishForm.getWsId());

			for (int i = 0; i < workspaceApplicationDetails.size(); i++) {

				out
						.write("\n"
								+ "<application application-containing-files=\"false\">");
				out.write("\n" + "<application-information>");
				out.write("\n" + "<application-number application-type=\""
						+ publishForm.getApplicationType() + "\">"
						+ workspaceApplicationDetails.get(i).getApplicationNumber()
						+ "</application-number>");
				out.write("\n" + "</application-information>");

				out.write("\n" + "<submission-information>");
				out.write("\n" + "<submission-id submission-type=\""
						+ publishForm.getSubmissionType() + "\">"
						+ workspaceApplicationDetails.get(i).getSubmissionId()
						+ "</submission-id>");
				out.write("\n"
						+ "<sequence-number submission-sub-type=\""
						+ publishForm.getSubmissionSubType()
						+ "\">"
						+ workspaceApplicationDetails.get(i)
								.getSequenceNumber() + "</sequence-number>");

				out.write("\n" + "</submission-information>");

				out.write("\n" + "</application>");

			}

			out.write("\n" + "</application-set>");

			/*
			 * out.write("\n" + "<product-description>"); out.write("\n" +
			 * "<application-number>" + publishForm.getApplicationNumber() +
			 * "</application-number>"); out.write("\n" + "<prod-name type=\"" +
			 * publishForm.getProductType() + "\">" + publishForm.getProdName()
			 * + "</prod-name>"); out.write("\n" + "</product-description>");
			 * 
			 * out.write("\n" + "<application-information application-type=\"" +
			 * publishForm.getApplicationType() + "\">"); out.write("\n" +
			 * "<submission submission-type=\"" +
			 * publishForm.getSubmissionType_us() + "\">"); out.write("\n" +
			 * "<sequence-number>" + publishForm.getSeqNumber() +
			 * "</sequence-number>");
			 * 
			 * // Related sequences if (publishForm.getRelatedSeqNumber() !=
			 * null && !publishForm.getRelatedSeqNumber().equals("")) { String[]
			 * allRelatedSequences = publishForm
			 * .getRelatedSeqNumber().split(","); for (int i = 0; i <
			 * allRelatedSequences.length; i++) { out.write("\n" +
			 * "<related-sequence-number>" + allRelatedSequences[i] +
			 * "</related-sequence-number>"); } }
			 * 
			 * out.write("\n" + " </submission>"); out.write("\n" +
			 * "</application-information>");
			 */
			out.write("\n" + "</admin>");
			out.write("\n");
		} else if (stype.equals("without")) {
			out.write("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>" + "\n");
			out
					.write("<!DOCTYPE ectd:ectd SYSTEM \"util/dtd/ich-ectd-3-0.dtd\">"
							+ "\n");
			out
					.write("<?xml-stylesheet type=\"text/xsl\" href=\"util/style/ectd-2-0.xsl\"?>"
							+ "\n");
			out
					.write("<ectd:ectd xmlns:ectd=\"http://www.ich.org/ectd\" xmlns:xlink=\"http://www.w3c.org/1999/xlink\" dtd-version=\"3.00\">"
							+ "\n");
		} else if (stype.equals("eum2-m5") || stype.equals("usm2-m5")) {
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

			if (stype.equals("eum2-m5")) {
				String eumd5 = md5.getMd5HashCode(absolutePathToCreate
						+ "/m1/eu" + "/eu-regional.xml");
				// out.write("<leaf xml:lang=\"en\" checksum-type=\"md5\" modified-file=\"\" operation=\"new\" application-version=\"\" xlink:href=\"m1/eu/eu-regional.xml\" checksum=\""+eumd5+"\" ID=\"node-999\" xlink:type=\"simple\">");
				out
						.write("<leaf xml:lang=\"en\" checksum-type=\"md5\" operation=\"new\" application-version=\"\" xlink:href=\"m1/eu/eu-regional.xml\" checksum=\""
								+ eumd5
								+ "\" ID=\"node-9999\" xlink:type=\"simple\">");
			} else {
				String usmd5 = md5.getMd5HashCode(absolutePathToCreate
						+ "/m1/us" + "/us-regional.xml");
				// out.write("<leaf xml:lang=\"en\" checksum-type=\"md5\" modified-file=\"\" operation=\"new\" application-version=\"\" xlink:href=\"m1/us/us-regional.xml\" checksum=\""+usmd5+"\" ID=\"node-999\" xlink:type=\"simple\">");
				out
						.write("<leaf xml:lang=\"en\" checksum-type=\"md5\" operation=\"new\" application-version=\"\" xlink:href=\"m1/us/us-regional.xml\" checksum=\""
								+ usmd5
								+ "\" ID=\"node-9999\" xlink:type=\"simple\">");
			}
			out.write("<title>");
			if (stype.equals("eum2-m5"))
				out.write("EU Module 1");
			else if (stype.equals("usm2-m5"))
				out
						.write("m1-administrative-information-and-prescribing-information");

			out.write("</title>");
			out.write("</leaf>");
			out
					.write("</m1-administrative-information-and-prescribing-information>"
							+ "\n");
		}
	}

	/*
	 * //if the project is not of type publish public void workspaceHtml(String
	 * workspaceId,PublishAttrForm publishForm,String wsDesc){ try {
	 * 
	 * wsId = workspaceId; folderName = new StringBuffer(); childNodeForParent =
	 * new Vector(); childAttrForNode = new Vector(); parentNodes = new
	 * Vector(); childAttrId = new Vector();
	 * 
	 * 
	 * Update Publish Version in WorkSpaceMst
	 * 
	 * docMgmtInt.updatePublishedVersion(wsId);
	 * 
	 * // createBaseFolder();
	 * 
	 * out = new BufferedWriter(new FileWriter(absolutePathToCreate +
	 * "/index.html")); out.write("<HTML>"); out.write("<body>");
	 * out.write("<p>&nbsp;</p>"); out.write("<p>&nbsp;</p>");
	 * out.write("<table width=\"100%\" bgcolor=\"#C0C0C0\">");
	 * out.write("<tr>"); out.write("<td>");
	 * out.write("<p align=\"center\"><font face=\"Verdana\" size=\"3\"><b>");
	 * out.write("Index"); out.write("</b></font></p>"); out.write("</td>");
	 * out.write("</tr>");
	 * 
	 * out.write("<tr>"); out.write("<td>"); out.write("&nbsp;");
	 * out.write("</td>"); out.write("</tr>");
	 * 
	 * parentNodes = docMgmtInt.getChildNodeByParent(iParentId, wsId);
	 * getChildNodeForHtml
	 * (parentNodes,absolutePathToCreate,out,iParentId,publishForm);
	 * 
	 * out.write("</table>"); out.write("</body>"); out.write("</HTML>");
	 * out.close(); }catch(Exception e){ e.printStackTrace(); } }
	 * 
	 * public void getChildNodeForHtml(Vector childNodes,String
	 * pathToCreate,BufferedWriter out,int parentId,PublishAttrForm publishForm)
	 * throws Exception { int spaceForChild = 0; // Search In Space Vector For
	 * Parent for(int j=0;j<Space.size();j++) { Object []getDtl =
	 * (Object[])Space.get(j); Integer parent = (Integer)getDtl[0]; Integer
	 * space1 = (Integer)getDtl[1]; spaceForChild = space1.intValue() + 4; }
	 * 
	 * if(childNodes.size() == 0 ){
	 * 
	 * out.write("<tr>"); out.write("<td>"); for(int
	 * sCount=0;sCount<=spaceForChild;sCount++) { out.write("&nbsp;"); }
	 * out.write("<font face=\"Verdana\" size=\"2\"><b>");
	 * 
	 * attrValue = copyFileforPublish(publishForm,pathToCreate);
	 * 
	 * out.write("<a href=" + attrValue + ">"); out.write(nodeName);
	 * out.write("</a>"); out.write("</b></font>"); out.write("</td>");
	 * out.write("</tr>"); out.flush(); } else{ for(int i =
	 * 0;i<childNodes.size();i++){
	 * 
	 * childNodeForParent = (Vector)childNodes.elementAt(i); nodeId =
	 * (Integer)childNodeForParent.elementAt(1); nodeName =
	 * (String)childNodeForParent.elementAt(2); nodeDisplayName =
	 * (String)childNodeForParent.elementAt(3);
	 * 
	 * int isLeaf = docMgmtInt.isLeafNodes(wsId,nodeId.intValue());
	 * 
	 * if (isLeaf==0) {
	 * 
	 * out.write("<tr>"); out.write("<td>"); for(int
	 * sCount=0;sCount<=spaceForChild;sCount++) { out.write("&nbsp;"); }
	 * out.write("<font face=\"Verdana\" size=\"2\"><b>");
	 * out.write(nodeDisplayName); out.write("</b></font>"); out.write("</td>");
	 * out.write("</tr>");
	 * 
	 * createSubFolders(pathToCreate,(String)childNodeForParent.elementAt(4));
	 * //Add Node In Vector For Space Space.addElement(new Object[] {new
	 * Integer(parentId),new Integer(spaceForChild)});
	 * 
	 * } parentNodes = docMgmtInt.getChildNodeByParent(nodeId.intValue(), wsId);
	 * getChildNodeForHtml
	 * (parentNodes,relativePathToCreate,out,nodeId.intValue(),publishForm); } }
	 * }
	 */

	/*
	 * public String CreatePathForEuRegional(DTOWorkSpaceNodeDetail
	 * dtownd)throws Exception {
	 * 
	 * Vector getData = docMgmtInt.getParentNoAndNodeDisplay(dtownd);
	 * DTOWorkSpaceNodeDetail getPathData =
	 * (DTOWorkSpaceNodeDetail)getData.elementAt(0);
	 * 
	 * String path = getPathData.getFolderName();
	 * dtownd.setParentNodeId(getPathData.getNodeId());
	 * 
	 * getData = null;getPathData = null;
	 * 
	 * getData = docMgmtInt.getParentNoAndNodeDisplay(dtownd); getPathData =
	 * (DTOWorkSpaceNodeDetail)getData.elementAt(0);
	 * 
	 * path += "/" + getPathData.getFolderName(); return path; }
	 */

	public void copyEuRegionalFile(String stype) throws Exception {
		String sourceFile = absolutePathToCreate + "/" + stype
				+ "-regional.xml";
		String destFile = absolutePathToCreate + "/m1/" + stype + "/" + stype
				+ "-regional.xml";
		try {
			// if m1 has no attached files
			File usregdir = new File(absolutePathToCreate + "/m1/" + stype);
			if (!usregdir.exists())
				usregdir.mkdirs();

			File usreg = new File(destFile);
			if (!usreg.exists()) {
				usreg.createNewFile();
			}
			FileInputStream fin = new FileInputStream(sourceFile);
			FileOutputStream fout = new FileOutputStream(destFile);

			byte[] buf = new byte[1024 * 10];
			int len;

			while ((len = fin.read(buf)) > 0) {
				fout.write(buf, 0, len);
			}
			fin.close();
			fout.close();
			File deleteFile = new File(absolutePathToCreate + "/" + stype
					+ "-regional.xml");
			deleteFile.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void copyUSDTDFile(String stype) throws Exception {

		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		String dtdSourcePath = propertyInfo.getValue("DTDGenerated");

		try {

			if (stype.equals("us")) {
				String sourceFile = dtdSourcePath + "\\" + stype + "m1.dtd";
				String destFile = absolutePathToCreate
						+ "/util/dtd/us-regional-v2-01.dtd";

				FileInputStream fin = new FileInputStream(sourceFile);
				FileOutputStream fout = new FileOutputStream(destFile);

				byte[] buf = new byte[1024 * 10];
				int len = 0;
				while ((len = fin.read(buf)) > 0) {
					fout.write(buf, 0, len);
				}
				fin.close();
				fout.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void generateSTFFile(String wsId) {
		try {
			Vector getallfirstnode = new Vector();
			getallfirstnode = docMgmtInt.getAllSTFFirstNodes(wsId);
			// select Distinct
			// vWorkspaceId,iNodeId,iNodeNo,vNodeName,vNodeDisplayName,cNodeTypeIndi,iParentNodeId,vFolderName,iModifyBy
			// from View_CommonWorkspaceDetail where vWorkspaceId='"+wsId+"' and
			// cNodeTypeIndi='T'

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
		studyidendata += "util/style/ich-stf-stylesheet-2-3.xsl\"?>\n";
		studyidendata += "<ectd:study xmlns:ectd=\"http://www.ich.org/ectd\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" xml:lang=\"en\" dtd-version=\"2.2\">\n";
		studyidendata += "<study-identifier>\n";

		Vector studyidentifierdata = docMgmtInt.getSTFIdentifierByNodeId(wsId,
				firstnodeId);

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
				// creata tag.
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
				// select count(*) as LeafNodes from workspacenodedetail where
				// vWorkspaceId='"+wsId+"' and iParentNodeId="+stfparentnodeid+"

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
					// select Distinct
					// iNodeId,vWorkspaceId,iNodeNo,iParentNodeId,
					// vNodeName,vNodeDisplayName,vFolderName,vNodeCategory,cNodeTypeIndi,
					// (Case When cNodeTypeIndi = 'F' then '' else vAttrName
					// end) as vAttrName,
					// (Case When cNodeTypeIndi = 'F' then '' else vAttrValue
					// end) as vAttrValue
					// from View_CommonWorkspaceDetail where
					// vWorkspaceId='"+wsId+"' AND iParentNodeId =
					// stfparentnodeid
					// AND ((cNodeTypeIndi= 'S' AND (vattrname= 'operation' OR
					// vattrname='')) OR cNodeTypeIndi = 'F')
					// order by iNodeNo

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
								// select Distinct
								// HistoryTranNo,vFileName,vFolderName from
								// View_CommonWorkspaceDetail
								// vWorkspaceId='"+wsId+"' and iNodeId=nodeId

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
									// select Distinct
									// iNodeId,vWorkspaceId,iNodeNo,iParentNodeId,
									// vNodeName,vNodeDisplayName,vFolderName,vNodeCategory,cNodeTypeIndi,
									// (Case When cNodeTypeIndi = 'F' then ''
									// else vAttrName end) as vAttrName,
									// (Case When cNodeTypeIndi = 'F' then ''
									// else vAttrValue end) as vAttrValue
									// from View_CommonWorkspaceDetail where
									// vWorkspaceId='"+wsId+"' AND iParentNodeId
									// = nodeId
									// AND ((cNodeTypeIndi= 'S' AND (vattrname=
									// 'operation' OR vattrname='')) OR
									// cNodeTypeIndi = 'F')
									// order by iNodeNo

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
											// select * from
											// stfstudyIdentifiermst where
											// vWorkspaceId ='"+wsId+"' and
											// iNodeId="+iNodeId order by
											// iTagSequenceId

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
	private static void parseFile(File inputFile) {
		// TODO Auto-generated method stub

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);

			System.out.println("Root element :"
					+ doc.getDocumentElement().getNodeName());

			NodeList nList = doc.getElementsByTagName("m1-1-forms");

			NodeList parent = doc.getElementsByTagName("m1-regional");

			if (parent.item(0) != null && nList.item(0) != null) {

				NodeList subInfoNode = doc
						.getElementsByTagName("submission-information");
				

				NodeList nListChild = nList.item(0).getChildNodes();

				NodeList rootChild = parent.item(0).getChildNodes();

				ArrayList<Node> nodetoaddArray = new ArrayList<Node>();

				for (int nchild = 0; nchild < nListChild.getLength(); nchild++) {

					if (nListChild.item(nchild).getNodeType() == Node.ELEMENT_NODE) {
						NamedNodeMap attr = nListChild.item(nchild)
								.getAttributes();

						String form_type_code = attr.item(0).getNodeValue();
						if (form_type_code.equals("fdaft1")
								|| form_type_code.equals("fdaft2")) {
							Node nodetoAdd = nListChild.item(nchild).cloneNode(
									true);
							nodetoaddArray.add(nodetoAdd);
							nListChild.item(nchild).getParentNode()
									.removeChild(nListChild.item(nchild));
							nchild--;
						}
					}
				}

				int nodes = 0;
				for (int childList = 0; childList < nListChild.getLength(); childList++) {
					if (nListChild.item(childList).getNodeType() == Node.ELEMENT_NODE) {
						nodes = 1;
						break;
					}
				}
				if (nodes == 0) {
					nList.item(0).getParentNode().removeChild(nList.item(0));
				}

				nodes = 0;
				for (int root = 0; root < rootChild.getLength(); root++) {
					if (rootChild.item(root).getNodeType() == Node.ELEMENT_NODE) {
						nodes = 1;
						break;
					}
				}
				if (nodes == 0) {
					parent.item(0).getParentNode().removeChild(parent.item(0));
				}

				for (int appendNode = 0; appendNode < nodetoaddArray.size(); appendNode++) {

					subInfoNode.item(0).appendChild(
							nodetoaddArray.get(appendNode));
				}

				OutputFormat format = new OutputFormat(doc);
				format.setIndenting(true);

//				TransformerFactory transformerFactory = TransformerFactory
//						.newInstance();
//				Transformer transformer = transformerFactory.newTransformer();
//				DOMSource source = new DOMSource(doc);
//				StreamResult result = new StreamResult(inputFile
//						.getAbsolutePath());
//				
//				// "<!DOCTYPE fda-regional:fda-regional SYSTEM \"http://www.accessdata.fda.gov/static/eCTD/us-regional-v3-3.dtd\">";
//				transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "SYSTEM http://www.accessdata.fda.gov/static/eCTD/us-regional-v3-3.dtd");
//				//http://www.accessdata.fda.gov/static/eCTD/us-regional-v3-3.dtd 
//				transformer.transform(source, result);
//				
					
		

				XMLSerializer serializer = new XMLSerializer(new FileOutputStream(inputFile.getAbsolutePath()), format);
				serializer.serialize(doc);
				

			} else {
				System.out.println("NO Child Nodes");
			}

		} catch (Exception e) {
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
}// main class end
