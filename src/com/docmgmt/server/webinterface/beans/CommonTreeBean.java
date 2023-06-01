package com.docmgmt.server.webinterface.beans;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.docmgmt.dto.ITreeNode;

/**
* This class represents the common tree bean which would return the
* tree structure of the dom root
*
* @author Nagesh
*/

public class CommonTreeBean 
{ 
	private List<?> nodeInfoVector;	
	ITreeNode root;

	public String retTreeHTML(Vector treeData)
	{
		nodeInfoVector=treeData;
		String htmlcode=null;
		//browse the vector of dtos
		//and generate the dto tree
		for (int i=0;i<nodeInfoVector.size();i++) //parent loop
		{
			//TreeNode is the interface of node
			ITreeNode parent=(ITreeNode)nodeInfoVector.get(i);			
			if (parent.getParentID()==0)
			{//set the parent node as the root of dom tree	
				root=parent;
			}

			for (int j=0;j<nodeInfoVector.size();j++) //child loop
			{
				ITreeNode child=(ITreeNode)nodeInfoVector.get(j);
				if (i!=j)
				{
					if (child.getParentID()==parent.getNodeID())
					{//found a child for the parent, add it to the parent child list
						ArrayList childlist= parent.getChildList();
						if (childlist==null)
							childlist=new ArrayList();
						childlist.add(child);
						
						
						parent.setChildList(childlist);
					}
				}
			}
		}
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder parser = factory.newDocumentBuilder();
			
			//create document
			Document doc = parser.newDocument();
			
			//main root of the tree
			Element domRoot=doc.createElement("ul");
			
			//append the root to tree
			doc.appendChild(domRoot);
			
			//generate the dom root, based on the dto tree
			generate(root,domRoot,doc);

			// create DOMSource for source tree
			Source xmlSource = new DOMSource(doc);

			// create StreamResult for transformation result
			ByteArrayOutputStream docop=new ByteArrayOutputStream();
			Result result = new StreamResult(docop);

			// create TransformerFactory
			TransformerFactory transformerFactory = TransformerFactory.newInstance();

			// create Transformer for transformation
			Transformer transformer = transformerFactory.newTransformer();

			transformer.setOutputProperty("indent","yes"); 
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");  

			// transform content
			transformer.transform(xmlSource, result);
			htmlcode=new String(docop.toByteArray());
			//System.out.println(htmlcode);
		}catch(Exception e){}
		return htmlcode;		
	}
	
	
	public Document retTreeNode(Vector treeData)
	{
		nodeInfoVector=treeData;
		String htmlcode=null;
		Document doc = null;
		
		//browse the vector of dtos
		//and generate the dto tree
		for (int i=0;i<nodeInfoVector.size();i++) //parent loop
		{
			//TreeNode is the interface of node
			ITreeNode parent=(ITreeNode)nodeInfoVector.get(i);			
			if (parent.getParentID()==0)
			{//set the parent node as the root of dom tree	
				root=parent;
			}

			for (int j=0;j<nodeInfoVector.size();j++) //child loop
			{
				ITreeNode child=(ITreeNode)nodeInfoVector.get(j);
				if (i!=j)
				{
					if (child.getParentID()==parent.getNodeID())
					{//found a child for the parent, add it to the parent child list
						ArrayList childlist= parent.getChildList();
						if (childlist==null)
							childlist=new ArrayList();
						childlist.add(child);
						
					
						parent.setChildList(childlist);
					}
				}
			}
		}
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder parser = factory.newDocumentBuilder();
			
			//create document
			doc = parser.newDocument();
			
			//main root of the tree
			Element domRoot=doc.createElement("ul");
			//CDATASection ne=doc.createCDATASection("nodedata");
			//ne.appendData("123");
			//domRoot.appendChild(ne);
						
			//append the root to tree
			doc.appendChild(domRoot);
			
			//generate the dom root, based on the dto tree
			generate(root,domRoot,doc);
		}catch(Exception e){}
		return doc;		
	}	
	
	private void generate(ITreeNode parentNodeDetail, Element domParent,Document doc) 
	{
		Element newElement=doc.createElement("li");
		
		Element label=doc.createElement("label");
		//Element label1=doc.createElement("button");
		boolean isParent=false;
		if (parentNodeDetail.getChildList()!=null && parentNodeDetail.getChildList().size()>0)
			isParent=true;
		newElement.appendChild(doc.createCDATASection(parentNodeDetail.toString()));
		
		label.setAttribute("title", takeNodeDisplayNameFromStr(parentNodeDetail.toString()));
	
	//	label.setAttribute("onclick", "uploadFileDrag(this.id)");

	
		label.setAttribute("id", new Integer(parentNodeDetail.getNodeID()).toString());
	//	label.setAttribute("class", "testdrop");
	
		label.setTextContent(parentNodeDetail.getNodeLabel());
		newElement.appendChild(label);
		
		
//		if(!isParent){
//			label1.setAttribute("title","");
//			label1.setAttribute("onmouseover", "getFileInfo(this.id)");
//			label1.setAttribute("id", "history"+new Integer(parentNodeDetail.getNodeID()).toString());
//			label1.setAttribute("class", "filehistory");
//			label1.setTextContent("I");
//			newElement.appendChild(label1);
//		}
		
		newElement.setAttribute("id",new Integer(parentNodeDetail.getNodeID()).toString());
		domParent.appendChild(newElement);
		if (isParent)
			newElement.setAttribute("class", "folder");
		//System.out.println(newElement.getAttribute("data"));

		if (parentNodeDetail.getChildList()!=null && parentNodeDetail.getChildList().size()>0)
		{
			Element newElement1=doc.createElement("ul");
			newElement.appendChild(newElement1);
			for (ITreeNode child:(ArrayList<ITreeNode>)parentNodeDetail.getChildList())            	
			{
				generate(child,newElement1,doc);//,selectedID);
			}
		}
	}

	private String takeNodeDisplayNameFromStr(String nodeDetailStr)
	{
		String nodeDisplayStr="";
		String[] nodeDetailList=nodeDetailStr.split(";");
		for(int i=0;i<nodeDetailList.length;i++)
		{
			String nodeAttValue=nodeDetailList[i];
			if(nodeAttValue.substring(0,nodeAttValue.indexOf(":")).trim().equalsIgnoreCase("nodeDisplayName"))
				return nodeAttValue.substring(nodeAttValue.indexOf(":")+1, nodeAttValue.length());
		}
		return nodeDisplayStr;
	}
	
	public static void main(String args[])
	{
		CommonTreeBean t=new CommonTreeBean();
		//t.retTreeHTML("0001",3,3);
	}
} 
