/*
 * Created on Nov 29, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Vector;


public class DTOAttributeMst implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	    String attrName;                                         
	    String attrForIndiId;                            
		String remark;                           
		int modifyBy;                              
		Timestamp modifyOn;           
		char statusIndi;                            
		int attrId;                                
		String attrValue;
		String attrMatrixValue;
		Vector attrMatrixValueVect;
		int attrValueId;
		String attrType;
		char userInput;
		String userTypeCode;
		String attrForIndiName;
		String userTypeName;
		String attrMatrixDisplayValue;
		Boolean savedUserAttr;
		
		 public Boolean getSavedUserAttr() {
			return savedUserAttr;
		}

		public void setSavedUserAttr(Boolean savedUserAttr) {
			this.savedUserAttr = savedUserAttr;
		}

		public String getAttrMatrixDisplayValue() {
			return attrMatrixDisplayValue;
		}

		public void setAttrMatrixDisplayValue(String attrMatrixDisplayValue) {
			this.attrMatrixDisplayValue = attrMatrixDisplayValue;
		}

		public DTOAttributeMst() {
			// TODO Auto-generated constructor stub
		}
		
	    public String getUserTypeName() {
			return userTypeName;
		}
		public void setUserTypeName(String userTypeName) {
			this.userTypeName = userTypeName;
		}
		
		public String getAttrForIndiName() {
			return attrForIndiName;
		}
		public void setAttrForIndiName(String attrForIndiName) {
			this.attrForIndiName = attrForIndiName;
		}
		/**
	     * @return Returns the attrForIndi.
	     */
	    
	    public String getAttrType() {
			return attrType;
		}
		public void setAttrType(String attrType) {
			this.attrType = attrType;
		}
		public char getUserInput() {
			return userInput;
		}
		public void setUserInput(char userInput) {
			this.userInput = userInput;
		}
		public String getUserTypeCode() {
			return userTypeCode;
		}
		public void setUserTypeCode(String userTypeCode) {
			this.userTypeCode = userTypeCode;
		}
		/**
	     * @return Returns the attrId.
	     */
	    public int getAttrId() {
	        return attrId;
	    }
	    /**
	     * @param attrId The attrId to set.
	     */
	    public void setAttrId(int attrId) {
	        this.attrId = attrId;
	    }
	    /**
	     * @return Returns the attrName.
	     */
	    public String getAttrName() {
	        return attrName;
	    }
	    /**
	     * @param attrName The attrName to set.
	     */
	    public void setAttrName(String attrName) {
	        this.attrName = attrName;
	    }
	    /**
	     * @return Returns the attrValue.
	     */
	    public String getAttrValue() {
	        return attrValue;
	    }
	    /**
	     * @param attrValue The attrValue to set.
	     */
	    public void setAttrValue(String attrValue) {
	        this.attrValue = attrValue;
	    }
	    /**
	     * @return Returns the modifyBy.
	     */
	    public int getModifyBy() {
	        return modifyBy;
	    }
	    /**
	     * @param modifyBy The modifyBy to set.
	     */
	    public void setModifyBy(int modifyBy) {
	        this.modifyBy = modifyBy;
	    } 
	    public Timestamp getModifyOn() {
			return modifyOn;
		}
		public void setModifyOn(Timestamp modifyOn) {
			this.modifyOn = modifyOn;
		}
		/**
	     * @return Returns the remark.
	     */
	    public String getRemark() {
	        return remark;
	    }
	    /**
	     * @param remark The remark to set.
	     */
	    public void setRemark(String remark) {
	        this.remark = remark;
	    }
	    /**
	     * @return Returns the statusIndi.
	     */
	    public char getStatusIndi() {
	        return statusIndi;
	    }
	    /**
	     * @param statusIndi The statusIndi to set.
	     */
	    public void setStatusIndi(char statusIndi) {
	        this.statusIndi = statusIndi;
	    }
	    
	    public String getAttrForIndiId() {
			return attrForIndiId;
		}
		public void setAttrForIndiId(String attrForIndiId) {
			this.attrForIndiId = attrForIndiId;
		}
		public String getAttrMatrixValue() {
	        return attrMatrixValue;
	    }
	    public void setAttrMatrixValue(String attrMatrixValue) {
	        this.attrMatrixValue = attrMatrixValue;
	    }
	    public int getAttrValueId() {
	        return attrValueId;
	    }
	    public void setAttrValueId(int attrValueId) {
	        this.attrValueId = attrValueId;
	    }

		public Vector getAttrMatrixValueVect() {
			return attrMatrixValueVect;
		}

		public void setAttrMatrixValueVect(Vector attrMatrixValueVect) {
			this.attrMatrixValueVect = attrMatrixValueVect;
		}


		
 
}
