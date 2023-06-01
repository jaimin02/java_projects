package com.docmgmt.dto;

import java.util.Vector;

public class DTOMenuMst {
	
	public String opCode;
	public String opName ;
	public String opPath;
	public String oppCode;
	public String opActive;
	public int opSubMenuStatus;
	public Vector subMenu;
	public String opNameSpace;
	
	public String getOpNameSpace() {
		return opNameSpace;
	}
	public void setOpNameSpace(String opNameSpace) {
		this.opNameSpace = opNameSpace;
	}
	public String getOpCode() {
		return opCode;
	}
	public void setOpCode(String opCode) {
		this.opCode = opCode;
	}
	public String getOpName() {
		return opName;
	}
	public void setOpName(String opName) {
		this.opName = opName;
	}
	public String getOpPath() {
		return opPath;
	}
	public void setOpPath(String opPath) {
		this.opPath = opPath;
	}
	public String getOppCode() {
		return oppCode;
	}
	public void setOppCode(String oppCode) {
		this.oppCode = oppCode;
	}
	public String getOpActive() {
		return opActive;
	}
	public void setOpActive(String opActive) {
		this.opActive = opActive;
	}
	public int getOpSubMenuStatus() {
		return opSubMenuStatus;
	}
	public void setOpSubMenuStatus(int opSubMenuStatus) {
		this.opSubMenuStatus = opSubMenuStatus;
	}
	public Vector getSubMenu() {
		return subMenu;
	}
	public void setSubMenu(Vector subMenu) {
		this.subMenu = subMenu;
	}


}
