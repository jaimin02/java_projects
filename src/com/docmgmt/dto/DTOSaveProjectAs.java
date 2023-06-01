/*
 * Created on Nov 29, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.docmgmt.dto;

import java.io.Serializable;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DTOSaveProjectAs implements Serializable{

	String sourcWsId;
	String destWsId;
	String selectedNodes[];
	int iModifyBy;
	
    
	/**
	 * @return Returns the destWsId.
	 */
	public String getDestWsId() {
		return destWsId;
	}
	/**
	 * @param destWsId The destWsId to set.
	 */
	public void setDestWsId(String destWsId) {
		this.destWsId = destWsId;
	}
	/**
	 * @return Returns the iModifyBy.
	 */
	public int getIModifyBy() {
		return iModifyBy;
	}
	/**
	 * @param modifyBy The iModifyBy to set.
	 */
	public void setIModifyBy(int modifyBy) {
		iModifyBy = modifyBy;
	}
	/**
	 * @return Returns the selectedNodes.
	 */
	public String[] getSelectedNodes() {
		return selectedNodes;
	}
	/**
	 * @param selectedNodes The selectedNodes to set.
	 */
	public void setSelectedNodes(String[] selectedNodes) {
		this.selectedNodes = selectedNodes;
	}
	/**
	 * @return Returns the sourcWsId.
	 */
	public String getSourcWsId() {
		return sourcWsId;
	}
	/**
	 * @param sourcWsId The sourcWsId to set.
	 */
	public void setSourcWsId(String sourcWsId) {
		this.sourcWsId = sourcWsId;
	}
}
