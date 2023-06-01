package com.docmgmt.server.webinterface;

public class ClientRequestID {
	public static final int NONE           = -1;
	public static final int MAIN_PAGE     =  1;
    
    public static final int USER_HOME_PAGE =  2;
    public static final int WS_OPEN        =  4;
	public static final int WORKSPACE_NODE =  5;
	public static final int WORKSPACE_NODE_ATTR = 6;
	public static final int WORKSPACE_NODE_HISTORY = 7;
	public static final int HEADER         = 11;
	public static final int FOOTER         = 12;
     
	public static final int LOGOUT         = 13;
	public static final int WORKSPACE_FILE_UPLOAD = 14;
	public static final int WORKSPACE_FILE_DOWNLOAD = 15;
    public static final int SEARCH = 16;
    public static final int SEARCH_RESULT = 17;
    public static final int WORKSPACE_LOCK_NODE = 18;
    public static final int WORKSPACE_UNLOCK_NODE = 19; 
    public static final int OPEN_DOCUMENT = 20; 
    public static final int DOWNLOADZIP = 21;
    public static final int HIGHERLEVELSEARCH = 22;
    public static final int HIGHERLEVELSEARCH_RESULT = 23;
    public static final int ALL_PROJECTS = 24;
    public static final int ZIP_N_ATTACH_IN_MAIL = 25;
    public static final int WORKSPACE_FILE_LOCK = 26;
    public static final int HOTSEARCH = 27;
    public static final int UNLOCK_WORKSPACENODETREE = 28;
    public static final int SAVE_COMMENTS = 29;
    public static final int SHOWALL_COMMENTS = 30;
    public static final int CONFIRM_COMMENTS = 31;
    public static final int LOGOUT_NEWWINDOW = 32;
    public static final int LOGIN = 33;
    public static final int TITLE = 34;
    
//project admin	
    public static final int WORKSPACE_SUMMARY = 51;
    public static final int WORKSPACE_NODE_RIGHTS = 52;
    public static final int WSADMIN_OPEN        =  53;
    public static final int WORKSPACE_NODE_ADMIN = 54;
    
//Project Admin Reports View only
    public static final int LOGIN_STATUS = 101;
    public static final int LOCKED_FILE_STATUS = 102;
    public static final int SHOW_LOCKED_FILES = 103;
    public static final int SHOW_PROJECT_COMMENTS = 104;
    public static final int LOGIN_STATUS_PRINT = 105;
    public static final int UNLOCK_ALLLOCKED_FILES = 106;
    public static final int LOCKED_FILE_STATUS_PRINT = 107;
    public static final int SHOW_PROJECT_COMMENTS_PRINT = 108;
    
}
