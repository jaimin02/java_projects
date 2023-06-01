package com.docmgmt.struts.actions.labelandpublish;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.docmgmt.dto.DTOFileSize;
import com.docmgmt.dto.DTOWorkSpaceNodeAttrDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeAttrHistory;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.dto.DTOWorkSpaceNodeVersionHistory;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.KnetProperties;

public class FileManager {

	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl;

	public FileManager() {
		docMgmtImpl = new DocMgmtImpl();
	}

	public void copyPublishedFilesToWorkspace(String indexXMLFilePath,
			String destinationWorkspaceFolderPath, int userId,
			int defaultStageId) {

		File indexXMLFile = new File(indexXMLFilePath);
		File destinationWorkspaceFolder = new File(
				destinationWorkspaceFolderPath);
		List<String[]> allFilePathsToCopy = readIndexXMLAndGetFilePaths(
				indexXMLFile, destinationWorkspaceFolder, userId);

		for (int i = 0; i < allFilePathsToCopy.size(); i++) {
			String[] fileData = allFilePathsToCopy.get(i);

			String nodeId = fileData[0], fullFilePath = fileData[1];

			if (nodeId.equals("tt")) {
				continue;
			}

			File nodeFile = new File(fullFilePath);

			if (nodeFile.exists()) {

				int tranNo = createFolderStruc(destinationWorkspaceFolder
						.getAbsolutePath(), Integer.parseInt(nodeId), nodeFile,
						destinationWorkspaceFolder.getName());
				if (tranNo > 0) {

					// insertnodehistory
					String workspaceId = destinationWorkspaceFolder.getName();
					String folderPath = "/" + workspaceId + "/" + nodeId + "/"
							+ tranNo;
					System.out.println("Folder Path=" + folderPath);

					DTOWorkSpaceNodeHistory workSpaceNodeHistoryDTO = new DTOWorkSpaceNodeHistory();
					workSpaceNodeHistoryDTO.setWorkSpaceId(workspaceId);

					workSpaceNodeHistoryDTO.setNodeId(Integer.parseInt(nodeId));
					workSpaceNodeHistoryDTO.setTranNo(tranNo);
					workSpaceNodeHistoryDTO.setFileName(nodeFile.getName());
					workSpaceNodeHistoryDTO.setFileType("");
					workSpaceNodeHistoryDTO.setFolderName(folderPath);
					workSpaceNodeHistoryDTO.setRequiredFlag('Y');
					// System.out.println("FileName:-----"+nodeFile.getName());

					// stageId
					workSpaceNodeHistoryDTO.setStageId(defaultStageId);

					workSpaceNodeHistoryDTO.setRemark("");
					workSpaceNodeHistoryDTO.setModifyBy(userId);
					workSpaceNodeHistoryDTO.setStatusIndi('N');
					workSpaceNodeHistoryDTO.setDefaultFileFormat("");

					docMgmtImpl.insertNodeHistory(workSpaceNodeHistoryDTO);

					// insert WorkspaceNodeAttrHistory
					updateNodeAttrHistory(workspaceId,
							Integer.parseInt(nodeId), tranNo, userId);

					// insert WorkspaceNodeVersionHistory
					DTOWorkSpaceNodeVersionHistory objWSNodeVersionHistory = new DTOWorkSpaceNodeVersionHistory();
					objWSNodeVersionHistory.setWorkspaceId(workspaceId);
					objWSNodeVersionHistory.setNodeId(Integer.parseInt(nodeId));
					objWSNodeVersionHistory.setTranNo(tranNo);
					objWSNodeVersionHistory.setPublished('N');
					objWSNodeVersionHistory.setDownloaded('N');
					objWSNodeVersionHistory.setActivityId("");
					objWSNodeVersionHistory.setModifyBy(userId);
					objWSNodeVersionHistory.setExecutedBy(userId);
					objWSNodeVersionHistory.setExecutedOn(new Timestamp(
							new Date().getTime()));
					objWSNodeVersionHistory.setUserDefineVersionId("A-1");// if
					// no
					// file
					// is
					// uploaded
					// before
					// then
					// 'A-1'
					// otherwise
					// it
					// will
					// be
					// auto-incremented

					docMgmtImpl
							.insertWorkspaceNodeVersionHistory(objWSNodeVersionHistory);
					// System.out.println("File copied successfully... for node :-"+
					// nodeId);

				}

			}

		}
	}

	public List<String[]> readIndexXMLAndGetFilePaths(File indexXMLFile,
			File wsFolder, int userId) {
		List<String[]> fileList = new ArrayList<String[]>();

		List<String[]> allFilePaths = readXMLAndGetFilePaths(indexXMLFile,
				wsFolder, userId, fileList);

		return allFilePaths;
	}

	public List<String[]> readXMLAndGetFilePaths(File xmlfile, File wsFolder,
			int userId, List<String[]> fileList) {

		Document dom;
		// get an instance of factory

		System.out.println("File PAth(Get)=" + xmlfile.getName());

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			// get an instance of builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			dom = db.parse(xmlfile);
			Element docEle = dom.getDocumentElement();

			NodeList nl = docEle.getElementsByTagName("leaf");

			if (nl.getLength() != 0) {

				if (nl != null && nl.getLength() > 0) {

					for (int i = 0; i < nl.getLength(); i++) {

						Element el = (Element) nl.item(i);

						String filepath = el.getAttribute("xlink:href");

						String[] ID = el.getAttribute("ID").split("-");// ID=node-221;

						String nodeId = ID[1];

						if (filepath.endsWith(".xml")
								|| filepath.endsWith("tracking11111.pdf")) {
							if (!filepath.endsWith("tracking1111.pdf")) {

								fileList = readXMLAndGetFilePaths(new File(
										xmlfile.getParent() + File.separator
												+ filepath), wsFolder, userId,
										fileList);
							}
						} else {
							String fullFilePath = xmlfile.getParent()
									+ File.separator + filepath;
							String[] fileListData = { nodeId, fullFilePath };

							fileList.add(fileListData);

						}

					}

				}

			}

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (SAXException se) {
			se.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return fileList;
	}

	/*
	 * public void readXml(File xmlfile,File wsFolder, int userId) {
	 * 
	 * //System.out.println("readXml called.....");
	 * 
	 * //System.out.println(userId);
	 * 
	 * Document dom;
	 * 
	 * //get an instance of factory DocumentBuilderFactory dbf =
	 * DocumentBuilderFactory.newInstance(); try { //get an instance of builder
	 * DocumentBuilder db = dbf.newDocumentBuilder();
	 * 
	 * dom = db.parse(xmlfile); Element docEle = dom.getDocumentElement();
	 * 
	 * NodeList nl= docEle.getElementsByTagName("leaf");
	 * 
	 * if(nl.getLength() != 0) {
	 * 
	 * if(nl != null && nl.getLength() > 0) {
	 * 
	 * for(int i=0;i<nl.getLength();i++) {
	 * 
	 * Element el=(Element)nl.item(i);
	 * 
	 * String filepath = el.getAttribute("xlink:href");
	 * 
	 * String[] nodeid = el.getAttribute("ID").split("-");
	 * 
	 * if(filepath.endsWith(".xml")) {
	 * //if(filepath.equals("m1/us/us-regional.xml")) //{ readXml(new
	 * File(xmlfile.getParent()+File.separator+filepath),wsFolder,userId); //} }
	 * else {
	 * 
	 * //System.out.println("Copying file :- "+filepath+"  for node :- " +
	 * nodeid[1]);
	 * 
	 * File nodeFile = new File(xmlfile.getParent()+File.separator+filepath);
	 * 
	 * 
	 * if(nodeFile.exists()) {
	 * 
	 * int
	 * tranNo=createFolderStruc(wsFolder.getAbsolutePath(),Integer.parseInt(nodeid
	 * [1]),nodeFile,wsFolder.getName()); if(tranNo!=-1) {
	 * //allLeafNodesCounter++; //insertnodehistory
	 * 
	 * String folderPath = "/"+ wsFolder.getName() + "/" + nodeid[1] + "/" +
	 * tranNo;
	 * 
	 * DTOWorkSpaceNodeHistory workSpaceNodeHistoryDTO = new
	 * DTOWorkSpaceNodeHistory();
	 * workSpaceNodeHistoryDTO.setWorkSpaceId(wsFolder.getName());
	 * workSpaceNodeHistoryDTO.setNodeId(Integer.parseInt(nodeid[1]));
	 * workSpaceNodeHistoryDTO.setTranNo(tranNo);
	 * workSpaceNodeHistoryDTO.setFileName(nodeFile.getName());
	 * workSpaceNodeHistoryDTO.setFileType("");
	 * workSpaceNodeHistoryDTO.setFolderName(folderPath);
	 * workSpaceNodeHistoryDTO.setRequiredFlag('Y');
	 * //System.out.println("FileName:-----"+nodeFile.getName());
	 * 
	 * //stageId workSpaceNodeHistoryDTO.setStageId(10);
	 * 
	 * workSpaceNodeHistoryDTO.setRemark("");
	 * workSpaceNodeHistoryDTO.setModifyBy(userId);
	 * workSpaceNodeHistoryDTO.setStatusIndi('N');
	 * workSpaceNodeHistoryDTO.setDefaultFileFormat("");
	 * 
	 * docMgmtImpl.insertNodeHistory(workSpaceNodeHistoryDTO);
	 * 
	 * 
	 * 
	 * updateNodeAttrHistory(wsFolder.getName(), Integer.parseInt(nodeid[1]),
	 * tranNo, userId);
	 * //System.out.println("File copied successfully... for node :-"+
	 * nodeid[1]);
	 * 
	 * }
	 * 
	 * }
	 * 
	 * 
	 * }
	 * 
	 * }
	 * 
	 * 
	 * }
	 * 
	 * }
	 * 
	 * } catch(ParserConfigurationException pce) { pce.printStackTrace();
	 * }catch(SAXException se) { se.printStackTrace(); }catch(IOException ioe) {
	 * ioe.printStackTrace(); }
	 * 
	 * }
	 */

	public int createFolderStruc(String wsPath, int nodeId, File nodeFile,
			String workspaceid) {
		// System.out.println("createFolderStruc called.....");
		int tranNo = 0;

		try {

			// File nodeFolder = new File(wsPath + File.separator + nodeId);

			byte[] fileData = null;
			int fileSize = 0;
			if (nodeFile != null && nodeFile.exists()) {
				fileSize = new Long(nodeFile.length()).intValue();

				InputStream sourceInputStream = null;
				sourceInputStream = new FileInputStream(nodeFile);

				fileData = getBytesFromFile(nodeFile);

				tranNo = docMgmtImpl.getNewTranNo(workspaceid);
				if (tranNo > 0) {
					File tranFolder = new File(wsPath + File.separator + nodeId
							+ File.separator + tranNo);
					tranFolder.mkdirs();

					File destiFile = new File(wsPath + File.separator + nodeId
							+ File.separator + tranNo + File.separator
							+ nodeFile.getName());
					OutputStream bos = new FileOutputStream(destiFile);

					int temp = 0;

					while ((temp = sourceInputStream
							.read(fileData, 0, fileSize)) != -1) {
						bos.write(fileData, 0, temp);
					}

					bos.close();
				}
				sourceInputStream.close();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return tranNo;
	}

	public byte[] getBytesFromFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);

		// Get the size of the file
		long length = file.length();

		if (length > Integer.MAX_VALUE) {
			// File is too large
		}

		// Create the byte array to hold the data
		byte[] bytes = new byte[(int) length];

		// Read in the bytes
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length
				&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}

		// Ensure all the bytes have been read in
		if (offset < bytes.length) {
			throw new IOException("Could not completely read file "
					+ file.getName());
		}

		// Close the input stream and return bytes
		is.close();
		return bytes;
	}

	private void updateNodeAttrHistory(String wsId, int nodeId, int tranNo,
			int userCode) {

		Vector saveFileAttr = new Vector();

		Vector fileAttr = docMgmtImpl.getNodeAttrDetail(wsId, nodeId);
		for (int i = 0; i < fileAttr.size(); i++) {

			DTOWorkSpaceNodeAttrDetail workSpaceNodeAttrDetail = (DTOWorkSpaceNodeAttrDetail) fileAttr
					.get(i);

			DTOWorkSpaceNodeAttrHistory workSpaceNodeAttrHistoryDTO = new DTOWorkSpaceNodeAttrHistory();

			workSpaceNodeAttrHistoryDTO.setWorkSpaceId(wsId);
			workSpaceNodeAttrHistoryDTO.setNodeId(nodeId);
			workSpaceNodeAttrHistoryDTO.setTranNo(tranNo);
			workSpaceNodeAttrHistoryDTO.setAttrId(workSpaceNodeAttrDetail
					.getAttrId());
			workSpaceNodeAttrHistoryDTO.setAttrValue(workSpaceNodeAttrDetail
					.getAttrValue());
			workSpaceNodeAttrHistoryDTO.setRemark(workSpaceNodeAttrDetail
					.getRemark());
			workSpaceNodeAttrHistoryDTO.setStatusIndi('N');
			workSpaceNodeAttrHistoryDTO.setModifyBy(userCode);

			saveFileAttr.add(workSpaceNodeAttrHistoryDTO);
			workSpaceNodeAttrHistoryDTO = null;
		}

		docMgmtImpl.InsertUpdateNodeAttrHistory(saveFileAttr);

		saveFileAttr = null;

	}

	public void copyDirectory(File sourceLocation, File targetLocation) {
		try {
			if (sourceLocation.isDirectory()) {
				if (!targetLocation.exists()) {
					targetLocation.mkdir();
				}
				String[] children = sourceLocation.list();
				for (int i = 0; i < children.length; i++) {
					copyDirectory(new File(sourceLocation, children[i]),
							new File(targetLocation, children[i]));
				}
			} else {
				File parentDir = targetLocation.getParentFile();
				if (!parentDir.exists()) {
					parentDir.mkdirs();
				}

				InputStream in = new FileInputStream(sourceLocation);
				OutputStream out = new FileOutputStream(targetLocation);

				copyStream(in, out);

				in.close();
				out.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Below Function will delete whole the directory which is pass in the
	// argument
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
	}

	public static void copyStream(InputStream in, OutputStream out)
			throws IOException {
		// Copy the bits from instream to outstream
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
	}

	public static File convertDocument(File srcDoc, String outputFileExt) {
		// synchronized
		File outputFile = null;
		String srcFileExt = srcDoc.getAbsolutePath().substring(
				srcDoc.getAbsolutePath().lastIndexOf(".") + 1);

		if (!srcFileExt.equalsIgnoreCase(outputFileExt)) {

			// JODConverter
			try {

				KnetProperties knetProperties = KnetProperties.getPropInfo();
				String OpenOfficeConnString = knetProperties
						.getValue("OpenOfficeConnString");
				Runtime rt = Runtime.getRuntime();
				// Process pCD =
				// rt.exec("cd C:\\Program Files\\OpenOffice.org 3\\program") ;
				// Process pKill = rt.exec("taskkill.exe /F /IM soffice.exe") ;

				// Linux call
				// Process pSoffice =
				// rt.exec("/opt/openoffice.org3/program/soffice -invisible -accept \"uno:socket,host=localhost,port=8100;urp;StarOffice.ServiceManager\"")
				// ;
				// Windows call
				Process pSoffice = rt.exec(OpenOfficeConnString);
				System.out.println(OpenOfficeConnString);

				// Process pSoffice =
				// rt.exec("soffice -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\" -nofirststartwizard")
				// ;
				// Process pSoffice =
				// rt.exec("c:\\program files\\openoffice.org 3\\program\\soffice.exe -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\"")
				// ;
				// pSoffice =
				// rt.exec("c:\\program files\\openoffice.org 3\\program\\soffice.exe -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\"")
				// ;
				// Process pKill = rt.exec("taskkill.exe /F /IM soffice.exe") ;
				// InputStream in = pSoffice.getInputStream() ;
				// OutputStream out = pSoffice.getOutputStream ();
				//
				// ThreadConnection c=new ThreadConnection();
				// Thread t = new Thread(c);
				// t.run();

				File inputFile = srcDoc;
				
				String fName=srcDoc.getName();
				fName=fName.substring(0,fName.lastIndexOf("."));
				String destDoc=srcDoc.getParent()+"/"+fName+"_Main"+ "." + outputFileExt;
				/*String destDoc = srcDoc.getAbsolutePath().substring(0,
						srcDoc.getAbsolutePath().lastIndexOf("."))
						+ "." + outputFileExt;*/

				outputFile = new File(destDoc);

				// connect to an OpenOffice.org instance running on port 8100
				OpenOfficeConnection connection = new SocketOpenOfficeConnection(
						8100);

				// OpenOfficeConnection ss=new
				// PipeOpenOfficeConnection(PipeOpenOfficeConnection.DEFAULT_PIPE_NAME);

				connection.connect();

				// convert
				DocumentConverter converter = new OpenOfficeDocumentConverter(
						connection);

				converter.convert(inputFile, outputFile);

				// close the connection
				connection.disconnect();

				// pSoffice.destroy() ;

				// Process pKill1 = rt.exec("taskkill.exe /F /IM soffice.exe") ;

				// Process pKill2 = rt.exec("taskkill.exe /F /IM soffice.bin") ;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			outputFile = srcDoc;
		}

		return outputFile;
	}

	public static DTOFileSize getFileSize(String filePath) {
		DTOFileSize fileSize = new DTOFileSize();
		File file = new File(filePath);
		if (file != null && file.exists()) {
			fileSize.setSizeBytes(file.length());
			fileSize.setSizeKBytes(fileSize.getSizeBytes() / 1024.0);
			fileSize.setSizeMBytes(fileSize.getSizeKBytes() / 1024.0);
			fileSize.setSizeGBytes(fileSize.getSizeMBytes() / 1024.0);
			fileSize
					.setSizeKBytes(Math.round(fileSize.getSizeKBytes() * 100.0) / 100.0);
			fileSize
					.setSizeMBytes(Math.round(fileSize.getSizeMBytes() * 100.0) / 100.0);
			fileSize
					.setSizeGBytes(Math.round(fileSize.getSizeGBytes() * 100.0) / 100.0);
		}
		return fileSize;
	}

	public static boolean deleteFile(File file) {

		file.delete();

		return true;
	}

}

class ThreadConnection implements Runnable {

	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Inside Thread....");

	}

}
