package com.docmgmt.struts.actions.ectdviewer.xmlread;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import com.docmgmt.server.prop.PropertyInfo;
import com.docmgmt.struts.actions.ectdviewer.common.ZipManager;
import com.opensymphony.xwork2.ActionSupport;

public class FileManager extends ActionSupport {
	public ArrayList<String> sequenceDirectoryName;
	public String folderPath = "";
	public File uploadFile;
	public String uploadFileFileName;
	public Vector<HashMap<String, String>> allProjectDetailEU = new Vector<HashMap<String, String>>();
	public Vector<HashMap<String, String>> allProjectDetailUS = new Vector<HashMap<String, String>>();
	public String deleteFilePath = "";
	public String htmlContent = "";
	public String errorMsg = "";
	int totalFolder = 0;
	int totalFile = 0;
	long totalFolderSize = 0;
	public String location = "";
	public String replaceSequence = "";

	public String viewFolderProperty() {
		if (folderPath != null && !folderPath.equalsIgnoreCase("")) {
			totalFolderSize = getFolderSizeByFolderPath(folderPath);
		} else {
			return "Error";
		}
		Float size = (float) totalFolderSize / 1024;// KB
		size = size / 1024;// KB
		htmlContent = "<b><font>Size :-</b>" + String.format("%.3g%n", size)
				+ " MB</font><br></br>" + "<b><font>Folder :-</b>"
				+ totalFolder + "</font><br></br>" + "<b><font>File :-</b>"
				+ totalFile + "</font>";

		System.out.println(htmlContent);
		return SUCCESS;
	}

	public String takeAllProject() {

		try {
			String[] projectList;
			PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
			File ProjectPath = propertyInfo.getDir("UnZipProjectPath");
			File aDirectory = new File(ProjectPath.getAbsolutePath());

			projectList = aDirectory.list();

			File seqList;
			for (int i = 0; i < projectList.length; i++) {
				String projectPath = ProjectPath.getAbsolutePath() + "/"
						+ projectList[i];
				File projectSequence = new File(projectPath);
				
				if(!projectSequence.isDirectory()){
					continue;
				}

				String seqs[] = projectSequence.list();
				String regionName = "";

				List<String> wordList = Arrays.asList(seqs);
				Collections.sort(wordList);

				projectPath = projectPath.replace("\\", "/");
				seqList = new File(projectPath);
				if (seqList.list().length >= 1) {
					String allRegFile[] = seqList.list();

					for (int temp = 0; temp < allRegFile.length; temp++) {
						File selFile = new File(seqList.getAbsolutePath() + "/"
								+ allRegFile[temp] + "/m1");
						if (!selFile.exists())
							continue;

						String allm1sub[] = selFile.list();
						if (allm1sub.length > 0)
							regionName = allm1sub[0];
						break;
						// System.out.println("seqPath-"+selFile.getAbsolutePath());
					}
				}

				HashMap<String, String> singleProjectDetail = new HashMap<String, String>();
				singleProjectDetail.put("dossierName", projectList[i]);
				singleProjectDetail.put("dossierURL",
						"ectdviewer_ex.do?folderPath=" + projectPath);
				singleProjectDetail.put("availableSequence", ""
						+ seqList.list().length);
				singleProjectDetail.put("region", regionName);
				singleProjectDetail.put("viewDossierSizzeURL",
						"folderSizeAction_ex.do?folderPath=" + projectPath);
				singleProjectDetail.put("projectPath",
						"deleteDossier_ex.do?deleteFilePath=" + projectPath);

				if (regionName.equals("eu"))
					allProjectDetailEU.add(singleProjectDetail);
				else if (regionName.equals("us"))
					allProjectDetailUS.add(singleProjectDetail);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String deleteProjects() {
		try {
			File fDel = new File(deleteFilePath);
			PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
			File ProjectPath = propertyInfo.getDir("ImportProjectPath");

			String lastName = deleteFilePath.substring(deleteFilePath
					.lastIndexOf("/") + 1, deleteFilePath.length());
			System.out.println("zip File Deleted="
					+ deleteZipDossierFile(ProjectPath.getAbsolutePath() + "/"
							+ lastName + ".zip"));
			System.out.println("Dossier Folder Deleted=" + deleteDir(fDel));
		} catch (Exception e) {
			e.printStackTrace();
		}
		takeAllProject();
		return SUCCESS;
	}

	public boolean deleteZipDossierFile(String zipDossierFilePath) {
		File fDel;
		fDel = new File(zipDossierFilePath);
		return fDel.delete();
	}

	@Override
	public String execute() {
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		File baseImpDir = propertyInfo.getDir("ImportProjectPath");
		File unZipProjectPath = propertyInfo.getDir("UnZipProjectPath");
		System.out.println("uploadFileFileName=" + uploadFileFileName);
		System.out.println("baseImpDir=" + baseImpDir);
		File importFile = new File(baseImpDir, uploadFileFileName);
		FileManager fileManager = new FileManager();
		fileManager.copyDirectory(uploadFile, importFile);

		String destPath = unZipProjectPath.getAbsolutePath();
		uploadFileFileName = uploadFileFileName.substring(0, uploadFileFileName
				.lastIndexOf("."));

		boolean isUnzipped = ZipManager.unZip(importFile, destPath);
		deleteFilePath = destPath + "/" + uploadFileFileName;
		System.out.println(deleteFilePath);
		if (!validateDossier(destPath + "/" + uploadFileFileName, "index.xml")) {
			errorMsg = "Zip Folder Contant is not Valid";
			deleteProjects();
			return "Error";
		}
		if (baseImpDir == null) {
			errorMsg = "Base Directory Folder Not Found";
			deleteProjects();
			return "Error";
		}
		if (unZipProjectPath == null) {
			errorMsg = "UnZip Dossier Folder Not Found";
			deleteProjects();
			return "Error";
		}

		if (isUnzipped)
			System.out.println("Upload Succesfully");
		else
			System.out.println("Upload failed");

		folderPath = destPath + "/" + uploadFileFileName;
		sequenceDirectoryName = takeSequence(folderPath, "index.xml");
		return SUCCESS;
	}

	private static boolean validateDossier(String projectPath,
			String indexFilePath) {
		File aDirectory = new File(projectPath);
		String xmlFileName = indexFilePath;
		String[] filesInDir = aDirectory.list();
		for (int i = 0; i < filesInDir.length; i++) {
			File innerDirectory = new File(projectPath + "/" + filesInDir[i]);
			if (innerDirectory.isDirectory()) {
				File fileCheck = new File(projectPath + "/" + filesInDir[i]
						+ "/" + xmlFileName);
				if (!fileCheck.exists()) {
					return false;
				}
			}
		}
		return true;
	}

	public long getFolderSizeByFolderPath(String folderPath) {
		long folderSize = getFileSize(new File(folderPath));
		return folderSize;
	}

	public static ArrayList<String> takeSequence(String projectPath,
			String indexFilePath) {
		System.out.println("Geting Directory Info");

		File aDirectory = new File(projectPath);
		String xmlFileName = indexFilePath;
		ArrayList<String> directoryName = new ArrayList<String>();
		try {
			String[] filesInDir = aDirectory.list();
			for (int i = 0; i < filesInDir.length; i++) {
				File innerDirectory = new File(projectPath + "/"
						+ filesInDir[i]);
				if (innerDirectory.isDirectory()) {
					System.out.println("isDirectory");

					File fileCheck = new File(projectPath + "/" + filesInDir[i]
							+ "/" + xmlFileName);
					if (fileCheck.exists()) {
						System.out.println("File Exist");
						directoryName.add(filesInDir[i]);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Directory" + directoryName.toString());
		return directoryName;
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

	private long getFileSize(File folder) {
		totalFolder++;
		long foldersize = 0;
		File[] filelist = folder.listFiles();
		for (int i = 0; i < filelist.length; i++) {
			if (filelist[i].isDirectory()) {
				foldersize += getFileSize(filelist[i]);
			} else if (filelist[i].isFile()) {
				totalFile++;
			} else {
				System.out.println("Invallid!!!");
			}
			foldersize += filelist[i].length();
		}
		return foldersize;
	}

	public String uploadNextSequence() {

		htmlContent = "";

		if (uploadFile == null) {
			htmlContent = "Unable to upload File";
			return SUCCESS;
		}

		htmlContent = "Successfully Uploaded";
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		File baseImpDir = propertyInfo.getDir("ImportProjectPath");
		File unZipProjectPath = propertyInfo.getDir("UnZipProjectPath");
		String checkPath = unZipProjectPath + "/" + location + "/"
				+ uploadFileFileName.substring(0, 4);
	
		File checkFile = new File(checkPath);

		if (checkFile.exists() && replaceSequence.equals("no")) {
			htmlContent = "replace"; // Do you want to replace flag
			return SUCCESS;
		}
	File importFile = new File(baseImpDir, uploadFileFileName);
		FileManager fileManager = new FileManager();

		fileManager.copyDirectory(uploadFile, importFile);

		String destPath = unZipProjectPath.getAbsolutePath() + "/" + location
				+ "/";
		
		uploadFileFileName = uploadFileFileName.substring(0, uploadFileFileName
				.lastIndexOf("."));
		boolean isUnzipped = ZipManager.unZip(importFile, destPath);
		deleteFilePath = importFile.getAbsolutePath();
			File indexFile = new File(destPath+uploadFileFileName+"/index.xml");
		if (!indexFile.exists()) {
			htmlContent = "Index file not exist";
			deleteProjects();
			return SUCCESS;
		}
		if (baseImpDir == null) {
			htmlContent = "Base Directory Folder Not Found";
			deleteProjects();
			return SUCCESS;
		}
		if (unZipProjectPath == null) {
			htmlContent = "UnZip Dossier Folder Not Found";
			deleteProjects();
			return SUCCESS;
		}
		return SUCCESS;
	}

	public int getTotalFolder() {
		return totalFolder;
	}

	public int getTotalFile() {
		return totalFile;
	}
}
