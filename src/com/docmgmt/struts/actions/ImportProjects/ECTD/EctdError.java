package com.docmgmt.struts.actions.ImportProjects.ECTD;


public class EctdError{
	public short Type;
	public String Msg;
	public String XmlFileName;
	public EctdError(short type,String msg,String xmlFileName) {
		this.Type = type;
		this.Msg = msg;
		this.XmlFileName = xmlFileName;
	}
	@Override
	public String toString() {
		String toString = "";
		switch (this.Type) {
			case EctdErrorType.ECTD_ERROR:
				toString = "Error: ";
				break;
			case EctdErrorType.ECTD_WARNING:
				toString = "Warning: ";
				break;
			case EctdErrorType.ECTD_INFO:
				toString = "Info: ";
				break;
		}
		
		toString+=this.Msg+" File:"+this.XmlFileName;
		
		return toString;
	}
}

