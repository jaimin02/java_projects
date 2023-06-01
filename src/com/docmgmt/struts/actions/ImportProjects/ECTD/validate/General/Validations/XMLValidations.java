package com.docmgmt.struts.actions.ImportProjects.ECTD.validate.General.Validations;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.docmgmt.struts.actions.ImportProjects.ECTD.EctdError;
import com.docmgmt.struts.actions.ImportProjects.ECTD.EctdErrorType;
import com.docmgmt.struts.actions.ImportProjects.ECTD.validate.CommonXmlValidations;
import com.docmgmt.struts.actions.ImportProjects.ECTD.validate.CommonXmlValidations.HrefError;

class CheckPathTask implements ITask
{
	String prefix;
	CommonXmlValidations commonXmlValidations;
	File dossier;
	String ECTD_BACKBONE_XML;
	String regionDetails[];
	int REGION_NAME;
	int REGION_CHECKSUM;
	int REGION_DTDFILENAME;
	int REGION_XMLFILENAME;
	int REGION_XMLFILE_LOCATION;
	
	CheckPathTask(String prefix,CommonXmlValidations commonXmlValidations, File dossier, String ECTD_BACKBONE_XML, String[] regionDetails,
		int REGION_NAME, int REGION_CHECKSUM, int REGION_DTDFILENAME, int REGION_XMLFILENAME, int REGION_XMLFILE_LOCATION)
	{
		this.prefix = prefix;
		this.commonXmlValidations = commonXmlValidations;
		this.dossier = dossier;
		this.ECTD_BACKBONE_XML = ECTD_BACKBONE_XML;
		this.regionDetails = regionDetails;
		this.REGION_NAME = REGION_NAME;
		this.REGION_CHECKSUM = REGION_CHECKSUM;
		this.REGION_DTDFILENAME = REGION_DTDFILENAME;
		this.REGION_XMLFILENAME = REGION_XMLFILENAME;
		this.REGION_XMLFILE_LOCATION = REGION_XMLFILE_LOCATION;
	}
	
	public void performTask(ArrayList<EctdError> errorList, Object... argObject) 
	{
		if (argObject.length<=0)
		{
			return;
		}
		else
		{
			if (argObject[0]!=null && argObject[0] instanceof File)
			{
				File fileObj = (File)argObject[0];
				if ((regionDetails != null && fileObj.getName().equals(regionDetails[REGION_XMLFILENAME])) ||
						(regionDetails == null && fileObj.getName().equals(ECTD_BACKBONE_XML)))
				{
					File xmlFileObj = null;
					File[] fileList = null;
					if (regionDetails == null)
					{
						fileList = dossier.listFiles(new FileFilter() 
						{
							public boolean accept(File pathname) 
							{
								if (pathname.getName().equals(ECTD_BACKBONE_XML))
									return true;
								return false;
							}
						});
					}
					else
					{
						String dossierPath = dossier.getAbsolutePath() + 
							((dossier.getAbsolutePath().endsWith(File.pathSeparator))? "" : "/") + 
							regionDetails[REGION_XMLFILE_LOCATION] + "/" + regionDetails[REGION_XMLFILENAME];
						File file = new File(dossierPath);
						fileList = new File[1];
						fileList[0] = file;
					}
					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					DocumentBuilder builder = null;
					try 
					{
						builder = factory.newDocumentBuilder();
					} 
					catch (ParserConfigurationException e) 
					{
						e.printStackTrace();
						return;
					}
					Document xmlDocument = null;
					try 
					{
						xmlDocument = builder.parse(fileObj);
						if (fileList != null && fileList.length > 0 && fileList.length == 1)
							xmlFileObj = fileList[0];
						else
							return;
						if (xmlFileObj.exists())
						{
							NodeList leafList = xmlDocument.getElementsByTagName("leaf");
							for (int indLeafList = 0 ; indLeafList < leafList.getLength() ; indLeafList++)
							{
								Element leaf = (Element)leafList.item(indLeafList);
								String href = leaf.getAttribute("xlink:href");
								if (!commonXmlValidations.checkPath(href, xmlFileObj))
									errorList.add(new EctdError(EctdErrorType.ECTD_ERROR, prefix + "Path is invalid.", 
										fileObj.getName() + "(" + href + ")"));
							}
						}
					}
					catch (Exception e) 
					{
						e.printStackTrace();
					}
				}
			}
		}
	}
}

class CompareDTDsTask implements ITask
{
	String prefix;
	CommonXmlValidations commonXmlValidations;
	String DTDsFolder;
	String regionDetails[];
	String ECTD_BACKBONE_DTD;
	int REGION_NAME;
	int REGION_CHECKSUM;
	int REGION_DTDFILENAME;
	int REGION_STORED_DTDFILENAME;
	
	
	public CompareDTDsTask(String prefix, CommonXmlValidations commonXmlValidations, String DTDsFolder, String regionDetails[], String ECTD_BACKBONE_DTD,
		int REGION_NAME, int REGION_CHECKSUM, int REGION_DTDFILENAME, int REGION_STORED_DTDFILENAME) 
	{
		this.prefix = prefix;
		this.commonXmlValidations = commonXmlValidations;
		this.DTDsFolder = DTDsFolder;
		this.regionDetails = regionDetails;
		this.ECTD_BACKBONE_DTD = ECTD_BACKBONE_DTD;
		this.REGION_NAME = REGION_NAME;
		this.REGION_CHECKSUM = REGION_CHECKSUM;
		this.REGION_DTDFILENAME = REGION_DTDFILENAME;
		this.REGION_STORED_DTDFILENAME = REGION_STORED_DTDFILENAME;
	}
	
	public void performTask(ArrayList<EctdError> errorList, Object... argObject) 
	{
		if (argObject.length<=0)
		{
			return;
		}
		else
		{
			if (argObject[0]!=null && argObject[0] instanceof File)
			{
				File fileObj = (File)argObject[0];
				if ((regionDetails != null && fileObj.getName().equals(regionDetails[REGION_DTDFILENAME])) ||
						(regionDetails == null && fileObj.getName().equals(ECTD_BACKBONE_DTD)))
				{
					File DTDsFolderObj = new File(DTDsFolder);
					File[] fileList = DTDsFolderObj.listFiles(new FileFilter() 
					{
						public boolean accept(File pathname) 
						{
							return pathname.getName().equals((regionDetails == null)?ECTD_BACKBONE_DTD : regionDetails[REGION_STORED_DTDFILENAME]);
						}
					});
					if (fileList == null || fileList.length == 0 || fileList.length != 1)
						errorList.add(new EctdError(EctdErrorType.ECTD_ERROR, prefix + "Stored DTD is not found. Please contact KnowledgeNET Team.", 
								fileObj.getAbsolutePath()));
					else if (!commonXmlValidations.compare(fileObj, fileList[0]))
						errorList.add(new EctdError(EctdErrorType.ECTD_ERROR, prefix + "Delivered DTD is not same as Stored one.", 
								fileObj.getAbsolutePath()));
				}
			}
		}
	}
}

class ValidateAgainstStoredDtdTask implements ITask
{
	String prefix;
	CommonXmlValidations commonXmlValidations;
	String DTDsFolder;
	String regionDetails[];
	String ECTD_BACKBONE_DTD;
	String ECTD_BACKBONE_XML;
	int REGION_NAME;
	int REGION_CHECKSUM;
	int REGION_DTDFILENAME;
	int REGION_XMLFILENAME;
	int REGION_STORED_DTDFILENAME;
	
	
	public ValidateAgainstStoredDtdTask(String prefix, CommonXmlValidations commonXmlValidations, String DTDsFolder, String regionDetails[], 
		String ECTD_BACKBONE_DTD, int REGION_NAME, int REGION_CHECKSUM, int REGION_DTDFILENAME, int REGION_XMLFILENAME, int REGION_STORED_DTDFILENAME, 
		String ECTD_BACKBONE_XML) 
	{
		this.prefix = prefix;
		this.commonXmlValidations = commonXmlValidations;
		this.DTDsFolder = DTDsFolder;
		this.regionDetails = regionDetails;
		this.ECTD_BACKBONE_DTD = ECTD_BACKBONE_DTD;
		this.REGION_NAME = REGION_NAME;
		this.REGION_CHECKSUM = REGION_CHECKSUM;
		this.REGION_DTDFILENAME = REGION_DTDFILENAME;
		this.REGION_XMLFILENAME = REGION_XMLFILENAME;
		this.ECTD_BACKBONE_XML = ECTD_BACKBONE_XML;
		this.REGION_STORED_DTDFILENAME = REGION_STORED_DTDFILENAME;
	}
	
	class ValidateAgainstStoredDtdTaskErrorHandler implements ErrorHandler
	{
		ArrayList<EctdError> errorList;
		String prefix;
		String xmlPath;
		
		ValidateAgainstStoredDtdTaskErrorHandler (String prefix, ArrayList<EctdError> errorList, String xmlPath)
		{
			this.prefix = prefix;
			this.errorList = errorList;
			this.xmlPath = xmlPath;
		}
		
		public void error(SAXParseException exception) throws SAXException
		{
			errorList.add(new EctdError(EctdErrorType.ECTD_ERROR, prefix + "Validate Against Stored DTD : " + exception.getMessage(), xmlPath));
		}
		public void fatalError(SAXParseException exception) throws SAXException {}
		public void warning(SAXParseException exception) throws SAXException {}
	}
	
	public void performTask(ArrayList<EctdError> errorList, Object... argObject) 
	{
		if (argObject.length<=0)
		{
			return;
		}
		else
		{
			if (argObject[0]!=null && argObject[0] instanceof File)
			{
				File fileObj = (File)argObject[0];
				if ((regionDetails != null && fileObj.getName().equals(regionDetails[REGION_XMLFILENAME])) ||
						(regionDetails == null && fileObj.getName().equals(ECTD_BACKBONE_XML)))
				{
					File DTDsFolderObj = new File(DTDsFolder);
					File[] fileList = DTDsFolderObj.listFiles(new FileFilter() 
					{
						public boolean accept(File pathname) 
						{
							return pathname.getName().equals((regionDetails == null)?ECTD_BACKBONE_DTD : regionDetails[REGION_STORED_DTDFILENAME]);
						}
					});
					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					factory.setValidating(true);
					DocumentBuilder builder = null;
					try 
					{
						builder = factory.newDocumentBuilder();
					} 
					catch (ParserConfigurationException e) 
					{
						e.printStackTrace();
						return;
					}
					builder.setErrorHandler(new ValidateAgainstStoredDtdTaskErrorHandler (prefix, errorList, fileObj.getAbsolutePath()));
					Document xmlDocument = null;
					try 
					{
						xmlDocument = builder.parse(fileObj);
						if (fileList == null || fileList.length == 0 || fileList.length != 1)
							errorList.add(new EctdError(EctdErrorType.ECTD_ERROR, prefix + "Stored DTD is not found\nPlease contact KnowledgeNET Team.", 
									fileObj.getAbsolutePath()));
						else 
							commonXmlValidations.validateAgainstStoredDTD(xmlDocument, fileList[0].getAbsolutePath());
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
						return;
					} 
				}
			}
		}
	}
}

class CheckFilenameTask implements ITask
{
	String prefix;
	CommonXmlValidations commonXmlValidations;
	String ECTD_BACKBONE_XML;
	String regionDetails[];
	int REGION_NAME;
	int REGION_CHECKSUM;
	int REGION_DTDFILENAME;
	int REGION_XMLFILENAME;
	
	CheckFilenameTask (String prefix,CommonXmlValidations commonXmlValidations, String ECTD_BACKBONE_XML, String[] regionDetails,
		int REGION_NAME, int REGION_CHECKSUM, int REGION_DTDFILENAME, int REGION_XMLFILENAME)
	{
		this.prefix = prefix;
		this.commonXmlValidations = commonXmlValidations;
		this.ECTD_BACKBONE_XML = ECTD_BACKBONE_XML;
		this.regionDetails = regionDetails;
		this.REGION_NAME = REGION_NAME;
		this.REGION_CHECKSUM = REGION_CHECKSUM;
		this.REGION_DTDFILENAME = REGION_DTDFILENAME;
		this.REGION_XMLFILENAME = REGION_XMLFILENAME;
	}
	
	public void performTask(ArrayList<EctdError> errorList, Object... argObject) 
	{
		if (argObject.length<=0)
		{
			return;
		}
		else
		{
			if (argObject[0]!=null && argObject[0] instanceof File)
			{
				File fileObj = (File)argObject[0];
				if ((regionDetails != null && fileObj.getName().equals(regionDetails[REGION_XMLFILENAME])) ||
						(regionDetails == null && fileObj.getName().equals(ECTD_BACKBONE_XML)))
				{
					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					DocumentBuilder builder = null;
					try 
					{
						builder = factory.newDocumentBuilder();
					} 
					catch (ParserConfigurationException e) 
					{
						e.printStackTrace();
						return;
					}
					Document xmlDocument = null;
					try 
					{
						xmlDocument = builder.parse(fileObj);
						NodeList leafList = xmlDocument.getElementsByTagName("leaf");
						for (int indLeafList = 0 ; indLeafList < leafList.getLength() ; indLeafList++)
						{
							Element leaf = (Element)leafList.item(indLeafList);
							String href = leaf.getAttribute("xlink:href");
							if (!commonXmlValidations.checkFileName(href))
								errorList.add(new EctdError(EctdErrorType.ECTD_ERROR, prefix + "Invalid filename.", fileObj.getName() + "(" + href + ")"));
						}
					}
					catch (Exception e) 
					{
						System.out.println(e);
						e.printStackTrace();
					}
				}
			}
		}
	}
}

class CheckNodeExtensionsTask implements ITask
{
	String prefix;
	CommonXmlValidations commonXmlValidations;
	String regionDetails[];
	String ECTD_BACKBONE_XML;
	int REGION_NAME;
	int REGION_CHECKSUM;
	int REGION_DTDFILENAME;
	int REGION_XMLFILENAME;
	
	public CheckNodeExtensionsTask (String prefix, CommonXmlValidations commonXmlValidations, String regionDetails[], 
		int REGION_NAME, int REGION_CHECKSUM, int REGION_DTDFILENAME, int REGION_XMLFILENAME, String ECTD_BACKBONE_XML) 
	{
		this.prefix = prefix;
		this.commonXmlValidations = commonXmlValidations;
		this.regionDetails = regionDetails;
		this.REGION_NAME = REGION_NAME;
		this.REGION_CHECKSUM = REGION_CHECKSUM;
		this.REGION_DTDFILENAME = REGION_DTDFILENAME;
		this.REGION_XMLFILENAME = REGION_XMLFILENAME;
		this.ECTD_BACKBONE_XML = ECTD_BACKBONE_XML;
	}
	
	public void performTask(ArrayList<EctdError> errorList, Object... argObject) 
	{
		if (argObject.length<=0)
		{
			return;
		}
		else
		{
			if (argObject[0]!=null && argObject[0] instanceof File)
			{
				File fileObj = (File)argObject[0];
				if ((regionDetails != null && fileObj.getName().equals(regionDetails[REGION_XMLFILENAME])) ||
						(regionDetails == null && fileObj.getName().equals(ECTD_BACKBONE_XML)))
				{
					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					DocumentBuilder builder = null;
					try 
					{
						builder = factory.newDocumentBuilder();
					} 
					catch (ParserConfigurationException e) 
					{
						e.printStackTrace();
						return;
					}
					Document xmlDocument = null;
					try 
					{
						xmlDocument = builder.parse(fileObj);
						if (!commonXmlValidations.checkNodeExtension(xmlDocument))
							errorList.add(new EctdError(EctdErrorType.ECTD_ERROR, prefix + "XML File contains Node Extensions.", 
								fileObj.getAbsolutePath()));
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
						return;
					} 
				}
			}
		}
	}
}

class CheckChecksumTypeAttributeTask implements ITask
{
	String prefix;
	CommonXmlValidations commonXmlValidations;
	String regionDetails[];
	String ECTD_BACKBONE_XML;
	int REGION_NAME;
	int REGION_CHECKSUM;
	int REGION_DTDFILENAME;
	int REGION_XMLFILENAME;
	
	public CheckChecksumTypeAttributeTask (String prefix, CommonXmlValidations commonXmlValidations, String regionDetails[], 
		int REGION_NAME, int REGION_CHECKSUM, int REGION_DTDFILENAME, int REGION_XMLFILENAME, String ECTD_BACKBONE_XML) 
	{
		this.prefix = prefix;
		this.commonXmlValidations = commonXmlValidations;
		this.regionDetails = regionDetails;
		this.REGION_NAME = REGION_NAME;
		this.REGION_CHECKSUM = REGION_CHECKSUM;
		this.REGION_DTDFILENAME = REGION_DTDFILENAME;
		this.REGION_XMLFILENAME = REGION_XMLFILENAME;
		this.ECTD_BACKBONE_XML = ECTD_BACKBONE_XML;
	}
	
	public void performTask(ArrayList<EctdError> errorList, Object... argObject) 
	{
		if (argObject.length<=0)
		{
			return;
		}
		else
		{
			if (argObject[0]!=null && argObject[0] instanceof File)
			{
				File fileObj = (File)argObject[0];
				if ((regionDetails != null && fileObj.getName().equals(regionDetails[REGION_XMLFILENAME])) ||
						(regionDetails == null && fileObj.getName().equals(ECTD_BACKBONE_XML)))
				{
					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					DocumentBuilder builder = null;
					try 
					{
						builder = factory.newDocumentBuilder();
					} 
					catch (ParserConfigurationException e) 
					{
						e.printStackTrace();
						return;
					}
					Document xmlDocument = null;
					try 
					{
						xmlDocument = builder.parse(fileObj);
						if (!commonXmlValidations.checkChecksumTypeAttribute(xmlDocument))
							errorList.add(new EctdError(EctdErrorType.ECTD_ERROR, prefix + "XML File contains invalid (not MD5) checksum attribute.", 
								fileObj.getAbsolutePath()));
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
						return;
					} 
				}
			}
		}
	}
}

class CheckLeafsTask implements ITask
{
	String prefix;
	CommonXmlValidations commonXmlValidations;
	String regionDetails[];
	String ECTD_BACKBONE_XML;
	int REGION_NAME;
	int REGION_CHECKSUM;
	int REGION_DTDFILENAME;
	int REGION_XMLFILENAME;
	
	public CheckLeafsTask (String prefix, CommonXmlValidations commonXmlValidations, String regionDetails[], 
		int REGION_NAME, int REGION_CHECKSUM, int REGION_DTDFILENAME, int REGION_XMLFILENAME, String ECTD_BACKBONE_XML) 
	{
		this.prefix = prefix;
		this.commonXmlValidations = commonXmlValidations;
		this.regionDetails = regionDetails;
		this.REGION_NAME = REGION_NAME;
		this.REGION_CHECKSUM = REGION_CHECKSUM;
		this.REGION_DTDFILENAME = REGION_DTDFILENAME;
		this.REGION_XMLFILENAME = REGION_XMLFILENAME;
		this.ECTD_BACKBONE_XML = ECTD_BACKBONE_XML;
	}
	
	public void performTask(ArrayList<EctdError> errorList, Object... argObject) 
	{
		if (argObject.length<=0)
		{
			return;
		}
		else
		{
			if (argObject[0]!=null && argObject[0] instanceof File)
			{
				File fileObj = (File)argObject[0];
				if ((regionDetails != null && fileObj.getName().equals(regionDetails[REGION_XMLFILENAME])) ||
						(regionDetails == null && fileObj.getName().equals(ECTD_BACKBONE_XML)))
				{
					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					DocumentBuilder builder = null;
					try 
					{
						builder = factory.newDocumentBuilder();
					} 
					catch (ParserConfigurationException e) 
					{
						e.printStackTrace();
						return;
					}
					Document xmlDocument = null;
					try 
					{
						xmlDocument = builder.parse(fileObj);
						Vector<String> nodeNames = new Vector<String>();
						commonXmlValidations.checkLeafs(xmlDocument.getDocumentElement(),nodeNames);
						for (int indNodeName = 0 ; indNodeName < nodeNames.size() ; indNodeName++)
							errorList.add(new EctdError(EctdErrorType.ECTD_ERROR, prefix + nodeNames.get(indNodeName), fileObj.getAbsolutePath()));
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
						return;
					} 
				}
			}
		}
	}
}

class CheckHrefsTask implements ITask
{
	String prefix;
	CommonXmlValidations commonXmlValidations;
	String regionDetails[];
	String ECTD_BACKBONE_XML;
	int REGION_NAME;
	int REGION_CHECKSUM;
	int REGION_DTDFILENAME;
	int REGION_XMLFILENAME;
	File dossier;
	
	public CheckHrefsTask (String prefix, CommonXmlValidations commonXmlValidations, String regionDetails[], 
			int REGION_NAME, int REGION_CHECKSUM, int REGION_DTDFILENAME, int REGION_XMLFILENAME, String ECTD_BACKBONE_XML, File dossier) 
	{
		this.prefix = prefix;
		this.commonXmlValidations = commonXmlValidations;
		this.regionDetails = regionDetails;
		this.REGION_NAME = REGION_NAME;
		this.REGION_CHECKSUM = REGION_CHECKSUM;
		this.REGION_DTDFILENAME = REGION_DTDFILENAME;
		this.REGION_XMLFILENAME = REGION_XMLFILENAME;
		this.ECTD_BACKBONE_XML = ECTD_BACKBONE_XML;
		this.dossier = dossier;
	}
	
	class CheckHrefsTaskErrorHandler implements ErrorHandler
	{
		ArrayList<EctdError> errorList;
		String prefix;
		String xmlPath;
		
		CheckHrefsTaskErrorHandler (String prefix, ArrayList<EctdError> errorList, String xmlPath)
		{
			this.prefix = prefix;
			this.errorList = errorList;
			this.xmlPath = xmlPath;
		}
		
		public void error(SAXParseException exception) throws SAXException
		{
			errorList.add(new EctdError(EctdErrorType.ECTD_ERROR, prefix + exception.getMessage(), xmlPath));
		}
		public void fatalError(SAXParseException exception) throws SAXException {}
		public void warning(SAXParseException exception) throws SAXException {}
	}
	
	public void performTask(ArrayList<EctdError> errorList, Object... argObject) 
	{
		if (argObject.length<=0)
		{
			return;
		}
		else
		{
			if (argObject[0]!=null && argObject[0] instanceof File)
			{
				File fileObj = (File)argObject[0];
				if ((regionDetails != null && fileObj.getName().equals(regionDetails[REGION_XMLFILENAME])) ||
						(regionDetails == null && fileObj.getName().equals(ECTD_BACKBONE_XML)))
				{
					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					DocumentBuilder builder = null;
					try 
					{
						builder = factory.newDocumentBuilder();
					} 
					catch (ParserConfigurationException e) 
					{
						e.printStackTrace();
						return;
					}
					builder.setErrorHandler(new CheckHrefsTaskErrorHandler (prefix, errorList, fileObj.getAbsolutePath()));
					Document xmlDocument = null;
					try 
					{
						xmlDocument = builder.parse(fileObj);
						Vector<HrefError> hrefErrors = commonXmlValidations.checkHrefs(xmlDocument,dossier);
						for (int indHrefError = 0 ; indHrefError < hrefErrors.size() ; indHrefError ++)
						{
							HrefError error = hrefErrors.get(indHrefError);
							switch (error.getResult())
							{
								case HrefError.INTER_APPLICATION:
									errorList.add(new EctdError(EctdErrorType.ECTD_ERROR, prefix + "Inter Application Href found.", error.getLeafHref()));
									break;
									
								case HrefError.INTER_SEQUENCE:
									errorList.add(new EctdError(EctdErrorType.ECTD_ERROR, prefix + "Inter Sequence Href found.", error.getLeafHref()));
									break;
							}
						}
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
						return;
					} 
				}
			}
		}
	}
}

class CheckSubmissionDateTask implements ITask
{
	String prefix;
	CommonXmlValidations commonXmlValidations;
	String regionDetails[];
	int REGION_NAME;
	int REGION_CHECKSUM;
	int REGION_DTDFILENAME;
	int REGION_XMLFILENAME;
	
	public CheckSubmissionDateTask (String prefix, CommonXmlValidations commonXmlValidations, String regionDetails[], 
			int REGION_NAME, int REGION_CHECKSUM, int REGION_DTDFILENAME, int REGION_XMLFILENAME) 
	{
		this.prefix = prefix;
		this.commonXmlValidations = commonXmlValidations;
		this.regionDetails = regionDetails;
		this.REGION_NAME = REGION_NAME;
		this.REGION_CHECKSUM = REGION_CHECKSUM;
		this.REGION_DTDFILENAME = REGION_DTDFILENAME;
		this.REGION_XMLFILENAME = REGION_XMLFILENAME;
	}
	
	class CheckSubmissionDateTaskErrorHandler implements ErrorHandler
	{
		ArrayList<EctdError> errorList;
		String prefix;
		String xmlPath;
		
		CheckSubmissionDateTaskErrorHandler (String prefix, ArrayList<EctdError> errorList, String xmlPath)
		{
			this.prefix = prefix;
			this.errorList = errorList;
			this.xmlPath = xmlPath;
		}
		
		public void error(SAXParseException exception) throws SAXException
		{
			errorList.add(new EctdError(EctdErrorType.ECTD_ERROR, prefix + exception.getMessage(), xmlPath));
		}
		public void fatalError(SAXParseException exception) throws SAXException {}
		public void warning(SAXParseException exception) throws SAXException {}
	}
	
	public void performTask(ArrayList<EctdError> errorList, Object... argObject) 
	{
		if (argObject.length<=0)
		{
			return;
		}
		else
		{
			if (argObject[0]!=null && argObject[0] instanceof File)
			{
				File fileObj = (File)argObject[0];
				if (regionDetails != null && fileObj.getName().equals(regionDetails[REGION_XMLFILENAME]))
				{
					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					DocumentBuilder builder = null;
					try 
					{
						builder = factory.newDocumentBuilder();
					} 
					catch (ParserConfigurationException e) 
					{
						e.printStackTrace();
						return;
					}
					builder.setErrorHandler(new CheckSubmissionDateTaskErrorHandler (prefix, errorList, fileObj.getAbsolutePath()));
					Document xmlDocument = null;
					try 
					{
						xmlDocument = builder.parse(fileObj);
						if (!commonXmlValidations.checkSubmissionDate(xmlDocument))
							errorList.add(new EctdError(EctdErrorType.ECTD_ERROR, prefix + "Submission Date not valid.", fileObj.getAbsolutePath()));
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
						return;
					} 
				}
			}
		}
	}
}

class Check3011FormTask implements ITask
{
	String prefix;
	CommonXmlValidations commonXmlValidations;
	String regionDetails[];
	int REGION_NAME;
	int REGION_CHECKSUM;
	int REGION_DTDFILENAME;
	int REGION_XMLFILENAME;
	
	public Check3011FormTask (String prefix, CommonXmlValidations commonXmlValidations, String regionDetails[], 
			int REGION_NAME, int REGION_CHECKSUM, int REGION_DTDFILENAME, int REGION_XMLFILENAME) 
	{
		this.prefix = prefix;
		this.commonXmlValidations = commonXmlValidations;
		this.regionDetails = regionDetails;
		this.REGION_NAME = REGION_NAME;
		this.REGION_CHECKSUM = REGION_CHECKSUM;
		this.REGION_DTDFILENAME = REGION_DTDFILENAME;
		this.REGION_XMLFILENAME = REGION_XMLFILENAME;
	}
	
	class Check3011FormTaskErrorHandler implements ErrorHandler
	{
		ArrayList<EctdError> errorList;
		String prefix;
		String xmlPath;
		
		Check3011FormTaskErrorHandler (String prefix, ArrayList<EctdError> errorList, String xmlPath)
		{
			this.prefix = prefix;
			this.errorList = errorList;
			this.xmlPath = xmlPath;
		}
		
		public void error(SAXParseException exception) throws SAXException
		{
			errorList.add(new EctdError(EctdErrorType.ECTD_ERROR, prefix + exception.getMessage(), xmlPath));
		}
		public void fatalError(SAXParseException exception) throws SAXException {}
		public void warning(SAXParseException exception) throws SAXException {}
	}
	
	public void performTask(ArrayList<EctdError> errorList, Object... argObject) 
	{
		if (argObject.length<=0)
		{
			return;
		}
		else
		{
			if (argObject[0]!=null && argObject[0] instanceof File)
			{
				File fileObj = (File)argObject[0];
				if (regionDetails != null && fileObj.getName().equals(regionDetails[REGION_XMLFILENAME]))
				{
					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					DocumentBuilder builder = null;
					try 
					{
						builder = factory.newDocumentBuilder();
					} 
					catch (ParserConfigurationException e) 
					{
						e.printStackTrace();
						return;
					}
					builder.setErrorHandler(new Check3011FormTaskErrorHandler (prefix, errorList, fileObj.getAbsolutePath()));
					Document xmlDocument = null;
					try 
					{
						xmlDocument = builder.parse(fileObj);
						if (!commonXmlValidations.check3011Form(xmlDocument))
							errorList.add(new EctdError(EctdErrorType.ECTD_ERROR, prefix + "Invalid 3011 Form.", fileObj.getAbsolutePath()));
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
						return;
					} 
				}
			}
		}
	}
}

public class XMLValidations extends Validation
{
	CommonXmlValidations commonXmlValidations = new CommonXmlValidations();
	
	@Override
	public void validate (Node tagData, File dossier, ArrayList<EctdError> errorList) 
	{
		if (!init(tagData,dossier,errorList))
			return;
		
		ArrayList<ITask> xmlTaskList=new ArrayList<ITask>();
				
		ArrayList<Node> checkpathList = getChildNodes(tagData, "checkpath");
		for (int indcheckpathList = 0; 	checkpathList != null && indcheckpathList < checkpathList.size(); indcheckpathList++)
		{
			Node checkpathNode = checkpathList.get(indcheckpathList);
			String region = getNodeAttribute(checkpathNode, "type");
			CheckPathTask checkPathTask = null;
			if (region != null && !region.equals(""))
			{
				if (dossierRegionDetails != null && region.equals(dossierRegionDetails[REGION_NAME]))
				{
					String regionDetails[] = dossierRegionDetails;
					if (regionDetails == null || regionDetails.length == 0 || regionDetails.length != REGION_ARRAY_LENGTH)
						continue;
					checkPathTask = new CheckPathTask(prefix, commonXmlValidations, dossier, ECTD_BACKBONE_XML, regionDetails, REGION_NAME, REGION_CHECKSUM, 
							REGION_DTDFILENAME, REGION_XMLFILENAME, REGION_XMLFILE_LOCATION);
				}
			}
			else
				checkPathTask = new CheckPathTask(prefix, commonXmlValidations, dossier, ECTD_BACKBONE_XML, null, REGION_NAME, REGION_CHECKSUM, 
						REGION_DTDFILENAME, REGION_XMLFILENAME, REGION_XMLFILE_LOCATION);
			if (checkPathTask != null)
				xmlTaskList.add(checkPathTask);
		}
		
		ArrayList<Node> comparedtdList = getChildNodes(tagData, "compareDTDs");
		for (int indComparedtdList = 0;
			comparedtdList != null && indComparedtdList < comparedtdList.size();
				indComparedtdList++)
		{
			Node comparisonNode = comparedtdList.get(indComparedtdList);
			String region = getNodeAttribute(comparisonNode, "type");
			CompareDTDsTask compareDTDsTask = null;
			if (region != null && !region.equals(""))
			{
				if (dossierRegionDetails != null && region.equals(dossierRegionDetails[REGION_NAME]))
				{
					String regionDetails[] = dossierRegionDetails;
					if (regionDetails == null || regionDetails.length == 0 || regionDetails.length != REGION_ARRAY_LENGTH)
						continue;
					compareDTDsTask = new CompareDTDsTask(prefix, commonXmlValidations, DTDs_FOLDER, regionDetails, ECTD_BACKBONE_DTD, REGION_NAME, 
							REGION_CHECKSUM, REGION_DTDFILENAME, REGION_STORED_DTDFILENAME);
				}
			}
			else
				compareDTDsTask = new CompareDTDsTask(prefix, commonXmlValidations, DTDs_FOLDER, null, ECTD_BACKBONE_DTD, REGION_NAME, REGION_CHECKSUM, 
						REGION_DTDFILENAME, REGION_STORED_DTDFILENAME);
			if (compareDTDsTask != null)
				xmlTaskList.add(compareDTDsTask);
		}
		
		ArrayList<Node> validateAgainstDtdList = getChildNodes(tagData, "validateAgainstStoredDTD");
		for (int indvalidateAgainstDtdList = 0;
			validateAgainstDtdList != null && indvalidateAgainstDtdList < validateAgainstDtdList.size();
				indvalidateAgainstDtdList++)
		{
			Node validateNode = validateAgainstDtdList.get(indvalidateAgainstDtdList);
			String region = getNodeAttribute(validateNode, "type");
			ValidateAgainstStoredDtdTask validateAgainstDtdTask = null;
			if (region != null && !region.equals(""))
			{
				if (dossierRegionDetails != null && region.equals(dossierRegionDetails[REGION_NAME]))
				{
					String regionDetails[] = dossierRegionDetails;
					if (regionDetails == null || regionDetails.length == 0 || regionDetails.length != REGION_ARRAY_LENGTH)
						continue;
					validateAgainstDtdTask = new ValidateAgainstStoredDtdTask(prefix, commonXmlValidations, DTDs_FOLDER, regionDetails, ECTD_BACKBONE_DTD, 
						REGION_NAME, REGION_CHECKSUM, REGION_DTDFILENAME, REGION_XMLFILENAME, REGION_STORED_DTDFILENAME, ECTD_BACKBONE_XML);
				}
			}
			else
				validateAgainstDtdTask = new ValidateAgainstStoredDtdTask(prefix, commonXmlValidations, DTDs_FOLDER, null, ECTD_BACKBONE_DTD, 
					REGION_NAME, REGION_CHECKSUM, REGION_DTDFILENAME, REGION_XMLFILENAME, REGION_STORED_DTDFILENAME, ECTD_BACKBONE_XML);
			if (validateAgainstDtdTask != null)
				xmlTaskList.add(validateAgainstDtdTask);
		}
		
		ArrayList<Node> checkNodeExtensionsList = getChildNodes(tagData, "checkNodeExtensions");
		for (int indcheckNodeExtensionsList = 0;
			checkNodeExtensionsList != null && indcheckNodeExtensionsList < checkNodeExtensionsList.size();
				indcheckNodeExtensionsList++)
		{
			Node nodeExtNode = checkNodeExtensionsList.get(indcheckNodeExtensionsList);
			String region = getNodeAttribute(nodeExtNode, "type");
			CheckNodeExtensionsTask checkNodeExtensionsTask = null;
			if (region != null && !region.equals(""))
			{
				if (dossierRegionDetails != null && region.equals(dossierRegionDetails[REGION_NAME]))
				{
					String regionDetails[] = dossierRegionDetails;
					if (regionDetails == null || regionDetails.length == 0 || regionDetails.length != REGION_ARRAY_LENGTH)
						continue;
					checkNodeExtensionsTask = new CheckNodeExtensionsTask(prefix, commonXmlValidations, regionDetails, 
						REGION_NAME, REGION_CHECKSUM, REGION_DTDFILENAME, REGION_XMLFILENAME, ECTD_BACKBONE_XML);
				}
			}
			else
				checkNodeExtensionsTask = new CheckNodeExtensionsTask(prefix, commonXmlValidations, null,  
					REGION_NAME, REGION_CHECKSUM, REGION_DTDFILENAME, REGION_XMLFILENAME, ECTD_BACKBONE_XML);
			if (checkNodeExtensionsTask != null)
				xmlTaskList.add(checkNodeExtensionsTask);
		}
				
		ArrayList<Node> checkFileExtensionNodeList = getChildNodes(tagData, "checkFileExtension");
		for (int indcheckFileExtensionList = 0; 	checkFileExtensionNodeList != null && indcheckFileExtensionList < checkFileExtensionNodeList.size();
			indcheckFileExtensionList++)
		{
			Node checkFileExtensionNode = checkFileExtensionNodeList.get(indcheckFileExtensionList);
			String region = getNodeAttribute(checkFileExtensionNode, "type");
			CheckFilenameTask checkFilenameTask = null;
			if (region != null && !region.equals(""))
			{
				if (dossierRegionDetails != null && region.equals(dossierRegionDetails[REGION_NAME]))
				{
					String regionDetails[] = dossierRegionDetails;
					if (regionDetails == null || regionDetails.length == 0 || regionDetails.length != REGION_ARRAY_LENGTH)
						continue;
					checkFilenameTask = new CheckFilenameTask(prefix, commonXmlValidations, ECTD_BACKBONE_XML, regionDetails, REGION_NAME, REGION_CHECKSUM, 
							REGION_DTDFILENAME, REGION_XMLFILENAME);
				}
			}
			else
				checkFilenameTask = new CheckFilenameTask(prefix, commonXmlValidations, ECTD_BACKBONE_XML, null, REGION_NAME, REGION_CHECKSUM, 
						REGION_DTDFILENAME, REGION_XMLFILENAME);
			if (checkFilenameTask != null)
				xmlTaskList.add(checkFilenameTask);
		}
		
		ArrayList<Node> checkChecksumAttributeList = getChildNodes(tagData, "checkChecksumAttribute");
		for (int indcheckChecksumAttributeList = 0;
			checkChecksumAttributeList != null && indcheckChecksumAttributeList < checkChecksumAttributeList.size();
				indcheckChecksumAttributeList++)
		{
			Node checksumAttributeNode = checkChecksumAttributeList.get(indcheckChecksumAttributeList);
			String region = getNodeAttribute(checksumAttributeNode, "type");
			CheckChecksumTypeAttributeTask checksumTypeAttributeTask = null;
			if (region != null && !region.equals(""))
			{
				if (dossierRegionDetails != null && region.equals(dossierRegionDetails[REGION_NAME]))
				{
					String regionDetails[] = dossierRegionDetails;
					if (regionDetails == null || regionDetails.length == 0 || regionDetails.length != REGION_ARRAY_LENGTH)
						continue;
					checksumTypeAttributeTask = new CheckChecksumTypeAttributeTask(prefix, commonXmlValidations, regionDetails, 
						REGION_NAME, REGION_CHECKSUM, REGION_DTDFILENAME, REGION_XMLFILENAME, ECTD_BACKBONE_XML);
				}
			}
			else
				checksumTypeAttributeTask = new CheckChecksumTypeAttributeTask(prefix, commonXmlValidations, null,  
					REGION_NAME, REGION_CHECKSUM, REGION_DTDFILENAME, REGION_XMLFILENAME, ECTD_BACKBONE_XML);
			if (checksumTypeAttributeTask != null)
				xmlTaskList.add(checksumTypeAttributeTask);
		}
		
		ArrayList<Node> checkLeafPresenceList = getChildNodes(tagData, "checkLeafPresence");
		for (int indcheckLeafPresenceList = 0;
			checkLeafPresenceList != null && indcheckLeafPresenceList < checkLeafPresenceList.size();
				indcheckLeafPresenceList++)
		{
			Node checkLeafPresenceNode = checkLeafPresenceList.get(indcheckLeafPresenceList);
			String region = getNodeAttribute(checkLeafPresenceNode, "type");
			CheckLeafsTask checkLeafsTask = null;
			if (region != null && !region.equals(""))
			{
				if (dossierRegionDetails != null && region.equals(dossierRegionDetails[REGION_NAME]))
				{
					String regionDetails[] = dossierRegionDetails;
					if (regionDetails == null || regionDetails.length == 0 || regionDetails.length != REGION_ARRAY_LENGTH)
						continue;
					checkLeafsTask = new CheckLeafsTask(prefix, commonXmlValidations, regionDetails, 
						REGION_NAME, REGION_CHECKSUM, REGION_DTDFILENAME, REGION_XMLFILENAME, ECTD_BACKBONE_XML);
				}
			}
			else
				checkLeafsTask = new CheckLeafsTask(prefix, commonXmlValidations, null,  
					REGION_NAME, REGION_CHECKSUM, REGION_DTDFILENAME, REGION_XMLFILENAME, ECTD_BACKBONE_XML);
			if (checkLeafsTask != null)
				xmlTaskList.add(checkLeafsTask);
		}
		
		ArrayList<Node> onlyAllowIntraSequenceList = getChildNodes(tagData, "onlyAllowIntraSequence");
		for (int indonlyAllowIntraSequenceList = 0;
			onlyAllowIntraSequenceList != null && indonlyAllowIntraSequenceList < onlyAllowIntraSequenceList.size();
				indonlyAllowIntraSequenceList++)
		{
			Node onlyAllowIntraSeqNode = onlyAllowIntraSequenceList.get(indonlyAllowIntraSequenceList);
			String region = getNodeAttribute(onlyAllowIntraSeqNode, "type");
			CheckHrefsTask checkHrefsTask = null;
			if (region != null && !region.equals(""))
			{
				if (dossierRegionDetails != null && region.equals(dossierRegionDetails[REGION_NAME]))
				{
					String regionDetails[] = dossierRegionDetails;
					if (regionDetails == null || regionDetails.length == 0 || regionDetails.length != REGION_ARRAY_LENGTH)
						continue;
					checkHrefsTask = new CheckHrefsTask(prefix, commonXmlValidations, regionDetails, 
						REGION_NAME, REGION_CHECKSUM, REGION_DTDFILENAME, REGION_XMLFILENAME, ECTD_BACKBONE_XML, dossier);
				}
			}
			else
				checkHrefsTask = new CheckHrefsTask(prefix, commonXmlValidations, null,  
					REGION_NAME, REGION_CHECKSUM, REGION_DTDFILENAME, REGION_XMLFILENAME, ECTD_BACKBONE_XML, dossier);
			if (checkHrefsTask != null)
				xmlTaskList.add(checkHrefsTask);
		}
		
		Node checkSubmissionDateNode = getChildNode(tagData, "checkSubmissionDate");
		if (checkSubmissionDateNode != null)
		{
			String region = getNodeAttribute(checkSubmissionDateNode, "type");
			CheckSubmissionDateTask checkSubmissionDateTask = null;
			if (region != null && !region.equals(""))
			{
				if (dossierRegionDetails != null && region.equals(dossierRegionDetails[REGION_NAME]))
				{
					String regionDetails[] = dossierRegionDetails;
					if (!(regionDetails == null || regionDetails.length == 0 || regionDetails.length != REGION_ARRAY_LENGTH))
						checkSubmissionDateTask = new CheckSubmissionDateTask (prefix, commonXmlValidations, regionDetails, 
							REGION_NAME, REGION_CHECKSUM, REGION_DTDFILENAME, REGION_XMLFILENAME);
				}
			}
			if (checkSubmissionDateTask != null)
				xmlTaskList.add(checkSubmissionDateTask);
		}
		
		Node check3011FormNode = getChildNode(tagData, "check3011Form");
		if (check3011FormNode != null)
		{
			String region = getNodeAttribute(check3011FormNode, "type");
			Check3011FormTask check3011FormTask = null;
			if (region != null && !region.equals(""))
			{
				if (dossierRegionDetails != null && region.equals(dossierRegionDetails[REGION_NAME]))
				{
					String regionDetails[] = dossierRegionDetails;
					if (!(regionDetails == null || regionDetails.length == 0 || regionDetails.length != REGION_ARRAY_LENGTH))
						check3011FormTask = new Check3011FormTask (prefix, commonXmlValidations, regionDetails, 
							REGION_NAME, REGION_CHECKSUM, REGION_DTDFILENAME, REGION_XMLFILENAME);
				}
			}
			if (check3011FormTask != null)
				xmlTaskList.add(check3011FormTask);
		}
		
		findAll(dossier, xmlTaskList, null);
	}
}