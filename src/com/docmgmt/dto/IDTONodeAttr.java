package com.docmgmt.dto;

import java.io.Serializable;

public interface IDTONodeAttr extends Serializable{

	public String getAttrName();
	public int getAttrId();
	public String getAttrForIndiId();
	public String getAttrValue();
}
