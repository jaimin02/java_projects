/*
 * Created on Nov 29, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;


public class DTOAgencyMst implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	   
	String agencyName; 
	String agencyCode;
	String agencyFullName;
	String agencyPrintName;
	String agencyRegionCode;
	String agencyAddress1;
	String agencyAddress2;
	String agencyPostCode;
	String agencyTelephones;
	String agencyFax;
	String agencyEmailId;
	String agencyContactPerson1;
	String contactPersion1Email;
	Timestamp modifyOn;
	int modifyBy;
	char statusIndi;
	
	public String getAgencyName() {
		return agencyName;
	}
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	public String getAgencyCode() {
		return agencyCode;
	}
	public void setAgencyCode(String agencyCode) {
		this.agencyCode = agencyCode;
	}
	public String getAgencyFullName() {
		return agencyFullName;
	}
	public void setAgencyFullName(String agencyFullName) {
		this.agencyFullName = agencyFullName;
	}
	public String getAgencyPrintName() {
		return agencyPrintName;
	}
	public void setAgencyPrintName(String agencyPrintName) {
		this.agencyPrintName = agencyPrintName;
	}
	public String getAgencyRegionCode() {
		return agencyRegionCode;
	}
	public void setAgencyRegionCode(String agencyRegionCode) {
		this.agencyRegionCode = agencyRegionCode;
	}
	public String getAgencyAddress1() {
		return agencyAddress1;
	}
	public void setAgencyAddress1(String agencyAddress1) {
		this.agencyAddress1 = agencyAddress1;
	}
	public String getAgencyAddress2() {
		return agencyAddress2;
	}
	public void setAgencyAddress2(String agencyAddress2) {
		this.agencyAddress2 = agencyAddress2;
	}
	public String getAgencyPostCode() {
		return agencyPostCode;
	}
	public void setAgencyPostCode(String agencyPostCode) {
		this.agencyPostCode = agencyPostCode;
	}
	public String getAgencyTelephones() {
		return agencyTelephones;
	}
	public void setAgencyTelephones(String agencyTelephones) {
		this.agencyTelephones = agencyTelephones;
	}
	public String getAgencyFax() {
		return agencyFax;
	}
	public void setAgencyFax(String agencyFax) {
		this.agencyFax = agencyFax;
	}
	public String getAgencyEmailId() {
		return agencyEmailId;
	}
	public void setAgencyEmailId(String agencyEmailId) {
		this.agencyEmailId = agencyEmailId;
	}
	public String getAgencyContactPerson1() {
		return agencyContactPerson1;
	}
	public void setAgencyContactPerson1(String agencyContactPerson1) {
		this.agencyContactPerson1 = agencyContactPerson1;
	}
	public String getContactPersion1Email() {
		return contactPersion1Email;
	}
	public void setContactPersion1Email(String contactPersion1Email) {
		this.contactPersion1Email = contactPersion1Email;
	}
	public Timestamp getModifyOn() {
		return modifyOn;
	}
	public void setModifyOn(Timestamp modifyOn) {
		this.modifyOn = modifyOn;
	}
	public int getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(int modifyBy) {
		this.modifyBy = modifyBy;
	}
	public char getStatusIndi() {
		return statusIndi;
	}
	public void setStatusIndi(char statusIndi) {
		this.statusIndi = statusIndi;
	}
	
	
}
