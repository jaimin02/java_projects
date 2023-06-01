package com.docmgmt.server.webinterface.entities.Node;

public class NodeTypeIndi {
	
		public static final char NODE_EXTENSION = 'E';
		/*
		 NODE_EXTENSION = 'E'
		 Not in used. It was use for bifercate single leaf to multiple leaf.		   
		 */
		public static final char FOLDER_NODE = 'F';
		/*
		 	FOLDER_NODE = 'F'
		 	XMl file -> skip tag
		 	Create only folder.	  
		 */
		public static final char ECTD_EU_SPECIFIC_NODE = 'D';
		/*
		 ECTD_EU_SPECIFIC_NODE = 'D'
		 skip current nodename folder. but will create xml node.		
		*/
		public static final char ECTD_EU_PIDOC_NODE = 'P';
		/*
		  ECTD_EU_PIDOC_NODE = 'P'
		  it create specific attribute folder (country/language) and skip own folder. create node in xml. 
		 */
		public static final char ECTD_STF_PARENT_NODE = 'T';
		/*
		  ECTD_STF_PARENT_NODE = 'T'
		  create folder of vfoldername. in xml skip tag.
		 */
		public static final char ECTD_STF_NODE = 'S';
		/*
		 ECTD_STF_NODE = 'S'
		 create folder of current node. it is link all stf file in index.xml file
	    */
		public static final char NORMAL_NODE = 'N';
		/*
		  NORMAL_NODE = 'N'
		  it will create folder and tag in xml file.
		 */
		public static final char ECTD_EXCLUDED_NODE = 'X';
		/*
		  ECTD_EXCLUDED_NODE = 'X'
		  it will not create node name folder and attribute folder. it will create xml node.
		 */
		
		public static final char ECTD_PARENT_FOLDER_NODE = 'I';
		/*
		  ECTD_PARENT_FOLDER_NODE = 'I'
		  it will copy file in immediate parent folder.
		 */
	
		public static final char ECTD_SKIP_NODE = 'B';
		/*
		  ECTD_SKIP_NODE = 'B'
		  it will not create folder. and skip xml tag.
		 */
		
		public static final char ECTD_CHECKFORREPEATED_NODE = 'C';
		/*
		  ECTD_CHECKFORREPEATED_NODE = 'C'
		  it will check for multiple repeat node. if yes then create attribute folder.
		  in xml create xml tag.
		  Assumption : This is use for M3-2-a-1 and M3-2-a-2
		 */
		
		public static final char ECTD_SKIP_NODE_AND_FOLDER = 'G';
		/*
		 ECTD_SKIP_NODE_AND_FOLDER = 'G'
		  it will not create folder. and skip xml node tag.
		  it will copy file in immediate parent folder.
		 */
		
		public static final char DOC_Stack_Nodee= 'K';
		/*
		 DocStack_SKIP_NODE from deviation and auto mail = 'K'
		 it will publishing after uploading file on node.
		 No need to Review and approve for publish
		 */
}
