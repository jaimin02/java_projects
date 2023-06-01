package com.docmgmt.struts.actions.ImportProjects.ECTD;

public class EctdErrorMsg{
	public static final short UNABLE_TO_GENERATE_CHKSUM = 1;
	public static final short CHKSUM_VAL_NOT_FOUND = 2;
	public static final short CHKSUM_VAL_NOT_MATCHED = 3;
	public static final short FILE_NOT_FOUND=4;
	public static final short TOO_SMALL_FILE=5;
	public static final short LEAF_WITHOUT_TITLE = 6;
	public static final short INVALID_MODIFIED_FILE_ENTRY = 7;
	
	
	public static String getErrMsgDesc(short ectdErrorMsg){
		String msgDescription = null;
		switch (ectdErrorMsg) {
		case EctdErrorMsg.UNABLE_TO_GENERATE_CHKSUM:
			msgDescription ="Unable to generate checksum.";
			break;
		case EctdErrorMsg.CHKSUM_VAL_NOT_FOUND:
			msgDescription ="Checksum value not found.";
		
		case EctdErrorMsg.CHKSUM_VAL_NOT_MATCHED:
			msgDescription ="Checksum value not matched.";
			break;
		case EctdErrorMsg.FILE_NOT_FOUND:
			msgDescription ="File not found.";
			break;
		case EctdErrorMsg.TOO_SMALL_FILE:
			msgDescription ="File with size less then 1kb found.";
			break;
		case EctdErrorMsg.LEAF_WITHOUT_TITLE:
			msgDescription ="Leaf Without Title Found.";
			break;
		case EctdErrorMsg.INVALID_MODIFIED_FILE_ENTRY:
			msgDescription = "Modified File entry doesn't point to a valid document.";
			break;
		default:
			msgDescription ="Undefined Error Found.";
			break;
		}
		return msgDescription;
	}
}
