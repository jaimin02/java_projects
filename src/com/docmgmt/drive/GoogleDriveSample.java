package com.docmgmt.drive;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.docmgmt.struts.actions.authentic.AuthenticSupport;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.Drive.Children;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.model.About;
import com.google.api.services.drive.model.ChildList;
import com.google.api.services.drive.model.ChildReference;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.ParentReference;

public class GoogleDriveSample extends AuthenticSupport {
	// Project ID: knowledgenet-test Project Number: 273703485680
	// https://www.example.com/oauth2callback

	private static final long serialVersionUID = 1L;

	public String url;
	
	/*
	 * 
	 * Client ID
	 * 273703485680-qvqljc7d35f9olbi1m8fj3luv5pcj8jq.apps.googleusercontent.com
	 * Email address
	 * 273703485680-qvqljc7d35f9olbi1m8fj3luv5pcj8jq@developer.gserviceaccount
	 * .com Client secret EYQWcD-DoaT69m1Jerzq5V2t Redirect URIs
	 * https://www.example.com/oauth2callback Javascript Origins
	 * https://www.example.com
	 */

	HttpTransport httpTransport = new NetHttpTransport();
	JsonFactory jsonFactory = new JacksonFactory();

	public String code;
	String accessToken;
	String refreshToken;

	String newCreatedFileId = "";

	String googleDriveTree = "";

	public String execute() throws IOException {

		String accsesstoken = (String) session.get("UserAccessToken");
		GoogleCredential credential;
		if (accsesstoken != null) {
			credential = new GoogleCredential.Builder().setTransport(
					new NetHttpTransport())
					.setJsonFactory(new JacksonFactory()).build();
			credential.setAccessToken(accsesstoken);

		} else {
			GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
					httpTransport, jsonFactory, DriveConstant.CLIENT_ID,
					DriveConstant.CLIENT_SECRET, Arrays
							.asList(DriveScopes.DRIVE)).setAccessType("online")
					.setApprovalPrompt("auto").build();
			GoogleTokenResponse response = flow.newTokenRequest(code)
					.setRedirectUri(DriveConstant.REDIRECT_URI).execute();
			credential = new GoogleCredential().setFromTokenResponse(response);
			session.put("UserAccessToken", credential.getAccessToken());
		}
		// Create a new authorized API client
		Drive service = new Drive.Builder(httpTransport, jsonFactory,
				credential).setApplicationName(DriveConstant.APPLICATION_NAME)
				.build();

		List<File> allFiles = retrieveAllFiles(service);

		displayFiles(allFiles, service);

		accessToken = credential.getAccessToken();
		refreshToken = credential.getRefreshToken();

	//	createBaseFolderForPublish(service,"0001","0001");
		
		createFoldersPath(service,new String[]{"0002","0001","Project-2"});
		
		return SUCCESS;

	}
		

	public String getGoogleDriveTree() {
		return googleDriveTree;
	}

	public void setGoogleDriveTree(String googleDriveTree) {
		this.googleDriveTree = googleDriveTree;
	}

	private void displayFiles(List<File> allFiles, Drive service) {
		// TODO Auto-generated method stub

		try {
			About about = service.about().get().execute();
			String rootFolder = about.getRootFolderId();
			sbdTreeSecond = new StringBuffer();
			printFilesInFolder(service, rootFolder);
			googleDriveTree = sbdTreeSecond.toString();
			System.out.println(sbdTreeSecond.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * for (int i = 0; i < allFiles.size(); i++) {
		 * 
		 * if (allFiles.get(i).getOriginalFilename() != null) {
		 * 
		 * System.out.println(allFiles.get(i).getOriginalFilename()); }
		 * 
		 * }
		 */

	}

	static String fullpath = "";
	static StringBuffer sbdTreeSecond;

	private static void printFilesInFolder(Drive service, String folderId)
			throws IOException {

		Children.List request = service.children().list(folderId).setQ(
				"trashed = false");

		sbdTreeSecond.append("<ul>");
		do {
			try {
				ChildList children = request.execute();

				for (ChildReference child : children.getItems()) {

					
					
					File file = service.files().get(child.getId()).execute();
					System.out.println("Title: " + file.getTitle());
					
					// System.out.println("Description: " +
					// file.getDescription());

					String link = "<li data=addClass:'pdf' data=href:'"
							+ file.getTitle() + "'\\> <a href='file:"
							+ file.getTitle() + "'\\>" + file.getTitle()
							+ "</a>";

					// String link = "<li><a href='"+file.getTitle()+"'>"+
					// file.getTitle()+"</a>";
					sbdTreeSecond.append(link);
					// System.out.println("MIME type: " + file.getMimeType());
					if (file.getMimeType().contains("folder")) {

						fullpath = fullpath + "/" + file.getTitle();

						System.out.println("Full->" + fullpath);
						printFilesInFolder(service, child.getId());

					}

				}
				request.setPageToken(children.getNextPageToken());
			} catch (IOException e) {
				System.out.println("An error occurred: " + e);
				request.setPageToken(null);
			}
		} while (request.getPageToken() != null
				&& request.getPageToken().length() > 0);

		sbdTreeSecond.append("</ul>");
	}

	public Credential getCredential() throws Exception {
		Credential credential = new GoogleCredential.Builder().setTransport(
				new NetHttpTransport()).setJsonFactory(new JacksonFactory())
				.build();
		credential.setAccessToken(accessToken);
		credential.setRefreshToken(refreshToken);

		return credential;
	}

	public void createBaseFolderForPublish(Drive service, 
			String wsId, String subId) {
		try {
			List<ParentReference> listParentReference = new ArrayList<ParentReference>();
			File file = null;
			file = getExistsFolder(service, wsId, (file == null) ? "root"
					: file.getId());
			if (file == null) {
				file = createFolder(service, wsId, listParentReference);
			}
			listParentReference.clear();
			listParentReference.add(new ParentReference().setId(file.getId()));
			
		} catch (Exception e) {

		}

	}

	public static void main(String[] args) throws IOException {
		HttpTransport httpTransport = new NetHttpTransport();
		JsonFactory jsonFactory = new JacksonFactory();

		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
				httpTransport, jsonFactory, DriveConstant.CLIENT_ID,
				DriveConstant.CLIENT_SECRET, Arrays.asList(DriveScopes.DRIVE))
				.setAccessType("online").setApprovalPrompt("auto").build();

		String url = flow.newAuthorizationUrl().setRedirectUri(
				DriveConstant.REDIRECT_URI).build();

		GoogleDriveSample sample = new GoogleDriveSample();
		Drive service;
		try {
			service = new Drive.Builder(httpTransport, jsonFactory, sample
					.getCredential()).setApplicationName(
					DriveConstant.APPLICATION_NAME).build();
			retrieveAllFiles(service);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out
				.println("Please open the following URL in your browser then type the authorization code:");
		System.out.println("  " + url);

	}

	public void reuseToken() {
		GoogleCredential credential1 = new GoogleCredential.Builder()
				.setJsonFactory(jsonFactory).setTransport(httpTransport)
				.setClientSecrets(DriveConstant.CLIENT_ID,
						DriveConstant.CLIENT_SECRET).build();
		credential1.setAccessToken(accessToken);
		credential1.setRefreshToken(refreshToken);
		Drive service = new Drive.Builder(httpTransport, jsonFactory,
				credential1).build();

	}

	public void moveFile(Drive service) {

		String s = service.getRootUrl();

		System.out.println("Root-" + s);

	}

	private static void deleteFile(Drive service, String fileId) {
		try {
			service.files().delete(fileId).execute();
		} catch (IOException e) {
			System.out.println("An error occurred: " + e);
		}
	}

	private static void printFile(Drive service, String fileId) {

		try {
			File file = service.files().get(fileId).execute();

			System.out.println("Download->" + file.getDownloadUrl());

			System.out.println("Title: " + file.getTitle());
			System.out.println("Description: " + file.getDescription());
			System.out.println("MIME type: " + file.getMimeType());
		} catch (IOException e) {
			System.out.println("An error occured: " + e);
		}
	}

	private static InputStream downloadFile(Drive service, File file) {
		if (file.getDownloadUrl() != null && file.getDownloadUrl().length() > 0) {
			try {
				HttpResponse resp = service.getRequestFactory()
						.buildGetRequest(new GenericUrl(file.getDownloadUrl()))
						.execute();
				return resp.getContent();
			} catch (IOException e) {
				// An error occurred.
				e.printStackTrace();
				return null;
			}
		} else {
			// The file doesn't have any content stored on Drive.
			return null;
		}
	}

	private static List<File> retrieveAllFiles(Drive service)
			throws IOException {
		List<File> result = new ArrayList<File>();
		Files.List request = service.files().list();

		do {
			try {
				FileList files = request.execute();

				result.addAll(files.getItems());
				request.setPageToken(files.getNextPageToken());
			} catch (IOException e) {
				System.out.println("An error occurred: " + e);
				request.setPageToken(null);
			}
		} while (request.getPageToken() != null
				&& request.getPageToken().length() > 0);

		return result;
	}

	public String getLoginUrl() {
		
		HttpTransport httpTransport = new NetHttpTransport();
		JsonFactory jsonFactory = new JacksonFactory();

		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
				httpTransport, jsonFactory, DriveConstant.CLIENT_ID,
				DriveConstant.CLIENT_SECRET, Arrays.asList(DriveScopes.DRIVE))
				.setAccessType("online").setApprovalPrompt("auto").build();

		url = flow.newAuthorizationUrl().setRedirectUri(DriveConstant.REDIRECT_URI).build();
		return "redirect";

	}

	private File getExistsFolder(Drive service, String title, String parentId)
			throws IOException {
		Drive.Files.List request;
		request = service.files().list();

		//'q' : 'trashed = false' 
		// Get folder and its sub folder from google drive.

		String query = "mimeType='application/vnd.google-apps.folder' AND trashed=false AND title='"
				+ title + "' AND '" + parentId + "' in parents";

		request = request.setQ(query);
		FileList files = request.execute();

		if (files.getItems().size() == 0) // if the size is zero, then the
			return null;
		else
			// since google drive allows to have multiple folders with the same
			// title (name)
			// we select the first file in the list to return
			return files.getItems().get(0);
	}

	private File createFolder(Drive service, String title,
			List<ParentReference> listParentReference) throws IOException {
		File body = new File();

		
		
		body.setTitle(title);
		body.setParents(listParentReference);

		body.setMimeType(DriveConstant.FOLDER_MIME_TYPE);
		File file = service.files().insert(body).execute();
		
		
		return file;

	}

	private String insertFile(Drive service, String parentId, String fileId)
			throws IOException {
		ChildReference newChild = new ChildReference();
		newChild.setId(fileId);
		ChildReference file1 = service.children().insert(parentId, newChild)
				.execute();

		return file1.getId();
	}

	private List<ParentReference> createFoldersPath(Drive service,
			String... titles) throws IOException {
		List<ParentReference> listParentReference = new ArrayList<ParentReference>();
		File file = null;
		for (int i = 0; i < titles.length; i++) {
			file = getExistsFolder(service, titles[i], (file == null) ? "root"
					: file.getId());
			if (file == null) {
				file = createFolder(service, titles[i], listParentReference);
				// newCreatedFileId
			}
			listParentReference.clear();
			listParentReference.add(new ParentReference().setId(file.getId()));
		}
		return listParentReference;
	}

	private static File copyFile(Drive service, String originFileId,
			String copyTitle) {
		File copiedFile = new File();
		copiedFile.setTitle(copyTitle);
		try {
			return service.files().copy(originFileId, copiedFile).execute();
		} catch (IOException e) {
			System.out.println("An error occurred: " + e);
		}
		return null;
	}

}
