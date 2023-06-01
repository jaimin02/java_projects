package com.docmgmt.struts.actions.master.user;

import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.webinterface.services.CryptoLibrary;
import com.opensymphony.xwork2.ActionSupport;

public class PasswordEncryption extends ActionSupport {
	CryptoLibrary encrypt = new CryptoLibrary();
	private static final long serialVersionUID = 1L;
	private String inputpswd;
	private String submitBtn;
	private String outputpswd;
	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	
	
@Override
public String execute() throws Exception {

	if (submitBtn.equals("Encrypt"))
		return Encryption();
	else if (submitBtn.equals("Decrypt"))
		return Decryption();
	else if (submitBtn.equals("Encrypt All"))
		return EncryptionAll();
	else if (submitBtn.equals("Decrypt All"))
		return DecryptionAll();

	return SUCCESS;
}
			
				public String Encryption()
				{
					try {
						outputpswd = encrypt.encrypt(inputpswd);
					} catch (SecurityException e) {
						addActionError("Unable to Encrypt the string...");
					}
					return SUCCESS;
				}
				
				public String Decryption()
				{
					try {
						outputpswd = encrypt.decrypt(inputpswd);
					} catch (SecurityException e) {
						addActionError("Unable to Decrypt the string...");
					}
					return SUCCESS;
				}
				public String EncryptionAll()
				{
					if(docMgmtImpl.EncryptAll(1))//Encrypt
					{
						addActionMessage("Encrypt All Password Successfully..");
					}
					else{
						addActionError("Unable to Encrypt All User Passwords");
					}
					return SUCCESS;
				}
				
				public String DecryptionAll()
				{
					if(docMgmtImpl.EncryptAll(2))//Decrypt
					{
						addActionMessage("Decrypt All Password Successfully..");
					}
					else{
						addActionError("Unable to Decrypt All User Passwords");
					}
					return SUCCESS;
				}
				
				
	public String getOutputpswd() {
		return outputpswd;
	}
	public void setOutputpswd(String outputpswd) {
		this.outputpswd = outputpswd;
	}
	
	public String getSubmitBtn() {
		return submitBtn;
	}
	
	public void setSubmitBtn(String submitBtn) {
		this.submitBtn = submitBtn;
	}

	public String getInputpswd() {
		return inputpswd;
	}

	public void setInputpswd(String inputpswd) {
		this.inputpswd = inputpswd;
	}
}
