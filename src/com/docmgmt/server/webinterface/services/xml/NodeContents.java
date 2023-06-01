package com.docmgmt.server.webinterface.services.xml;

import java.util.ArrayList;

import org.w3c.dom.Node;

public class NodeContents
	{
		String nodeName;
		String nodeContent;
		ArrayList<String> attributes;
		short nodeType;
						
		public NodeContents(String nodeName,String nodeContent,ArrayList<String> attributes,short nodeType) 
		{
			this.nodeName=nodeName;
			this.nodeContent=nodeContent;
			this.attributes=attributes;
			this.nodeType=nodeType;
		}		
		public NodeContents(String nodeName,String nodeContent,ArrayList<String> attributes) 
		{
			this(nodeName,nodeContent,attributes,Node.ELEMENT_NODE); 
		}
		public boolean equals(Node node)
		{			
			if (!node.getNodeName().equals(nodeName))
			{
				return false;
			}
			if (node.getNodeType()!=nodeType)
			{
				return false;
			}
			for (int i=0;attributes!=null && attributes.size()>0 && i<attributes.size();i++)
			{
				String attr[]=attributes.get(i).split("=");
				if (!XmlUtilities.getNodeAttribute(node,attr[0]).equals(attr[1]))
				{					
					return false;
				}
			}
			return true;
		}
	}