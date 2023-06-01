package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class DTOPdfPublishDtl implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String workspaceId;
	String productname;
	String version;
	
	String addFooter;
	String addHeader;
	
	String footer_pageNumberStyle;
	
	 String pageNumberStyle;
	 String pageNumberPosition;
	 String footerContentPosition;
	 String footer_pageNumberPosition;
	 
	 String headerTextPosition;
	 String header_logoPosition;
	 String logoPosition;
	 String nodeTitlePosition;
	 String headerTextColor;
	 
	 String addCoverPages;
	 String coverpage_productname = "";
	 String coverpage_submittedby = "";
	 String coverpage_submittedto = "";
	 String coverpage_fontsize;

	 String coverpage_fontsizeby;
	 String coverpage_fontsizeto;
	 String coverpage_fontstyle;
	 String coverpage_fontname;
	 String coverpage_subbyfontname;
	
	 String coverpage_subbyfontstyle;
	 String coverpage_subtofontstyle;
	 String coverpage_subtofontname;

	// TOC Settings

	 String tocSettings[];
	 String tocPageNumberStyle;
	 String tocFontSize;
	
	int modifyBy;
	Timestamp modifyOn;
	char statusIndi;
	

	

	public String getHeader_logoPosition() {
		return header_logoPosition;
	}

	public void setHeader_logoPosition(String headerLogoPosition) {
		header_logoPosition = headerLogoPosition;
	}

	public String getFooter_pageNumberPosition() {
		return footer_pageNumberPosition;
	}

	public void setFooter_pageNumberPosition(String footerPageNumberPosition) {
		footer_pageNumberPosition = footerPageNumberPosition;
	}

	public String getAddHeader() {
		return addHeader;
	}

	public void setAddHeader(String addHeader) {
		this.addHeader = addHeader;
	}

	public String getAddFooter() {
		return addFooter;
	}

	public void setAddFooter(String addFooter) {
		this.addFooter = addFooter;
	}

	public String getFooter_pageNumberStyle() {
		return footer_pageNumberStyle;
	}

	public void setFooter_pageNumberStyle(String footerPageNumberStyle) {
		footer_pageNumberStyle = footerPageNumberStyle;
	}

	public String getWorkspaceId() {
		return workspaceId;
	}

	public void setWorkspaceId(String workspaceId) {
		this.workspaceId = workspaceId;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public int getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(int modifyBy) {
		this.modifyBy = modifyBy;
	}

	public Timestamp getModifyOn() {
		return modifyOn;
	}

	public void setModifyOn(Timestamp modifyOn) {
		this.modifyOn = modifyOn;
	}

	public char getStatusIndi() {
		return statusIndi;
	}

	public void setStatusIndi(char statusIndi) {
		this.statusIndi = statusIndi;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPageNumberStyle() {
		return pageNumberStyle;
	}

	public void setPageNumberStyle(String pageNumberStyle) {
		this.pageNumberStyle = pageNumberStyle;
	}

	public String getPageNumberPosition() {
		return pageNumberPosition;
	}

	public void setPageNumberPosition(String pageNumberPosition) {
		this.pageNumberPosition = pageNumberPosition;
	}

	public String getFooterContentPosition() {
		return footerContentPosition;
	}

	public void setFooterContentPosition(String footerContentPosition) {
		this.footerContentPosition = footerContentPosition;
	}

	public String getHeaderTextPosition() {
		return headerTextPosition;
	}

	public void setHeaderTextPosition(String headerTextPosition) {
		this.headerTextPosition = headerTextPosition;
	}

	public String getLogoPosition() {
		return logoPosition;
	}

	public void setLogoPosition(String logoPosition) {
		this.logoPosition = logoPosition;
	}

	public String getNodeTitlePosition() {
		return nodeTitlePosition;
	}

	public void setNodeTitlePosition(String nodeTitlePosition) {
		this.nodeTitlePosition = nodeTitlePosition;
	}

	public String getHeaderTextColor() {
		return headerTextColor;
	}

	public void setHeaderTextColor(String headerTextColor) {
		this.headerTextColor = headerTextColor;
	}

	public String getAddCoverPages() {
		return addCoverPages;
	}

	public void setAddCoverPages(String addCoverPages) {
		this.addCoverPages = addCoverPages;
	}

	public String getCoverpage_productname() {
		return coverpage_productname;
	}

	public void setCoverpage_productname(String coverpageProductname) {
		coverpage_productname = coverpageProductname;
	}

	public String getCoverpage_submittedby() {
		return coverpage_submittedby;
	}

	public void setCoverpage_submittedby(String coverpageSubmittedby) {
		coverpage_submittedby = coverpageSubmittedby;
	}

	public String getCoverpage_submittedto() {
		return coverpage_submittedto;
	}

	public void setCoverpage_submittedto(String coverpageSubmittedto) {
		coverpage_submittedto = coverpageSubmittedto;
	}

	public String getCoverpage_fontsize() {
		return coverpage_fontsize;
	}

	public void setCoverpage_fontsize(String coverpageFontsize) {
		coverpage_fontsize = coverpageFontsize;
	}

	public String getCoverpage_fontsizeby() {
		return coverpage_fontsizeby;
	}

	public void setCoverpage_fontsizeby(String coverpageFontsizeby) {
		coverpage_fontsizeby = coverpageFontsizeby;
	}

	public String getCoverpage_fontsizeto() {
		return coverpage_fontsizeto;
	}

	public void setCoverpage_fontsizeto(String coverpageFontsizeto) {
		coverpage_fontsizeto = coverpageFontsizeto;
	}

	public String getCoverpage_fontstyle() {
		return coverpage_fontstyle;
	}

	public void setCoverpage_fontstyle(String coverpageFontstyle) {
		coverpage_fontstyle = coverpageFontstyle;
	}

	public String getCoverpage_fontname() {
		return coverpage_fontname;
	}

	public void setCoverpage_fontname(String coverpageFontname) {
		coverpage_fontname = coverpageFontname;
	}

	public String getCoverpage_subbyfontname() {
		return coverpage_subbyfontname;
	}

	public void setCoverpage_subbyfontname(String coverpageSubbyfontname) {
		coverpage_subbyfontname = coverpageSubbyfontname;
	}

	public String getCoverpage_subbyfontstyle() {
		return coverpage_subbyfontstyle;
	}

	public void setCoverpage_subbyfontstyle(String coverpageSubbyfontstyle) {
		coverpage_subbyfontstyle = coverpageSubbyfontstyle;
	}

	public String getCoverpage_subtofontstyle() {
		return coverpage_subtofontstyle;
	}

	public void setCoverpage_subtofontstyle(String coverpageSubtofontstyle) {
		coverpage_subtofontstyle = coverpageSubtofontstyle;
	}

	public String getCoverpage_subtofontname() {
		return coverpage_subtofontname;
	}

	public void setCoverpage_subtofontname(String coverpageSubtofontname) {
		coverpage_subtofontname = coverpageSubtofontname;
	}

	public String[] getTocSettings() {
		return tocSettings;
	}

	public void setTocSettings(String[] tocSettings) {
		this.tocSettings = tocSettings;
	}

	public String getTocPageNumberStyle() {
		return tocPageNumberStyle;
	}

	public void setTocPageNumberStyle(String tocPageNumberStyle) {
		this.tocPageNumberStyle = tocPageNumberStyle;
	}

	public String getTocFontSize() {
		return tocFontSize;
	}

	public void setTocFontSize(String tocFontSize) {
		this.tocFontSize = tocFontSize;
	}
	

	

}
