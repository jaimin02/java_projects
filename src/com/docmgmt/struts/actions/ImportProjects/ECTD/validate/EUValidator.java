package com.docmgmt.struts.actions.ImportProjects.ECTD.validate;

import java.util.ArrayList;

import org.w3c.dom.Document;

import com.docmgmt.struts.actions.ImportProjects.ECTD.EctdError;
import com.docmgmt.struts.actions.ImportProjects.ECTD.EctdErrorType;

public class EUValidator {

	public static ArrayList<EctdError> validateEURegional(Document euM1Doc){
		
		ArrayList<EctdError> ectdErrorList = new ArrayList<EctdError>();
		
		ArrayList<String> errList = EUNamingConventions.validateFileNames(euM1Doc);
		for (String errMsg : errList) {
			ectdErrorList.add(new EctdError(EctdErrorType.ECTD_WARNING,errMsg,euM1Doc.getBaseURI()));
		}
		ArrayList<String> absentEnvelopeCountryCodes = EnvelopeValidator.validate(euM1Doc);
		for (String CC : absentEnvelopeCountryCodes) {
			ectdErrorList.add(new EctdError(EctdErrorType.ECTD_WARNING,"Envelope not found for Country Code : "+CC,euM1Doc.getBaseURI()));
		}
		return ectdErrorList;
	}
}
