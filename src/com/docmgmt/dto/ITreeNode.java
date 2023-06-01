/**
 * 
 */
package com.docmgmt.dto;

import java.util.ArrayList;

/**
 * This interface represents a node of the tree
 * @author Nagesh
 *
 */
public interface ITreeNode 
{
	/*boolean isPublishFlag();
	int getNodeID();
	int getParentID();
	ArrayList getChildList();
	String getNodeLabel();
	void setNodeID(int nodeID);
	void setParentID(int parentNodeID);
	void setChildList(ArrayList<ITreeNode> childList);
	void setNodeLabel(String nodeLabel);
	void setPublishFlag(boolean publishFlag);
	void setStatusIndi(char statusIndi);*/
	boolean isPublishFlag();
	int getNodeID();
	int getParentID();
	char getStatusIndiForTree();
	String getWsId();
	ArrayList getChildList();
	String getNodeLabel();
	void setNodeID(int nodeID);
	void setParentID(int parentNodeID);
	void setChildList(ArrayList<ITreeNode> childList);
	void setNodeLabel(String nodeLabel);
	void setPublishFlag(boolean publishFlag);
	void setStatusIndi(char statusIndi);
	void setWsId(String WsId);
	void setStatusIndiForTree(char statusIndiForTree);
}
