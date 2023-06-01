package com.docmgmt.server.webinterface.entities.Workspace;

/**
 * These values must be identical to the database values.
 * i.e. It must be found in database.
 * 
 */
public class ProjectType 
{
	//When a new project type is added, also add it in appropriate method below
	
	//only use char members here
	public static final char DMS_STANDARD = 'E';
	public static final char DMS_IMPORTED = 'I';
	public static final char ECTD_MANUAL = 'M';
	public static final char ECTD_STANDARD = 'P';
	public static final char DMS_DOC_CENTRIC = 'D';
	public static final char DMS_SKELETON = 'T'; 
	public static final char NEES_STANDARD = 'N'; 
	public static final char VNEES_STANDARD = 'V'; 
	public static final char ARCHIVE_STANDARD = 'A'; 
	/*
	 	E -> Standard DMS
		I -> Imported DMS Project 
		M -> ManualMOde eCTD
		P -> Standard eCTD
		D -> Document Centric
	*/
	
	public static final char[] getEctdValidCharacters()
	{
		char validChars[] = {ECTD_MANUAL,ECTD_STANDARD};
		return validChars;
	}
	
	public static final char[] getDMSValidCharacters()
	{
		char validChars[] = {DMS_STANDARD,DMS_IMPORTED,DMS_DOC_CENTRIC,DMS_SKELETON};
		return validChars;
	}

	public static final char[] getNeeSValidCharacters()
	{
		char validChars[] = {NEES_STANDARD};
		return validChars;
	}
	public static final char[] getvNeeSValidCharacters()
	{
		char validChars[] = {VNEES_STANDARD};
		return validChars;
	}
	public static final char[] getvArchiveValidCharacters()
	{
		char validChars[] = {ARCHIVE_STANDARD};
		return validChars;
	}
	
	public static String getApplicationType(char projectType)
	{
		char validChars[] = getEctdValidCharacters();
		boolean found = false;
		for (int indValidChar = 0;indValidChar < validChars.length;indValidChar++)
		{
			if (validChars[indValidChar] == projectType)
			{
				found = true;
				break;
			}
		}
		if (found)
			return "eCTD";
		validChars = getDMSValidCharacters();
		found = false;
		for (int indValidChar = 0;indValidChar < validChars.length;indValidChar++)
		{
			if (validChars[indValidChar] == projectType)
			{
				found = true;
				break;
			}
		}
		if (found)
			return "eCSV";

		
		validChars = getNeeSValidCharacters();
		found = false;
		for (int indValidChar = 0;indValidChar < validChars.length;indValidChar++)
		{
			if (validChars[indValidChar] == projectType)
			{
				found = true;
				break;
			}
		}
		if (found)
			return "NeeS";
		
		validChars = getvNeeSValidCharacters();
		found = false;
		for (int indValidChar = 0;indValidChar < validChars.length;indValidChar++)
		{
			if (validChars[indValidChar] == projectType)
			{
				found = true;
				break;
			}
		}
		if (found)
			return "vNeeS";
		
		validChars = getvArchiveValidCharacters();
		found = false;
		for (int indValidChar = 0;indValidChar < validChars.length;indValidChar++)
		{
			if (validChars[indValidChar] == projectType)
			{
				found = true;
				break;
			}
		}
		if (found)
			return "Archive";
		
		return "";
	}
}
