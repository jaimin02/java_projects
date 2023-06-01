package com.docmgmt.server.webinterface.services.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author nagesh
 */
public class XmlUtilities
{
	
	public static String getNodeAttribute(Node node,String attribute)
    {
    	String retString=null;
    	try {
    		retString=node.getAttributes().getNamedItem(attribute).getTextContent();    		
    	}
    	catch(Exception e){}
    	return retString;    	
    }
    
	public static ArrayList<Node> getChildNodes(Node node)
    {
    	ArrayList<Node> nodeList=new ArrayList<Node>();
    	try
    	{
    		for (int i=0;i<node.getChildNodes().getLength();i++)
    			nodeList.add(node.getChildNodes().item(i));
    	}
    	catch(Exception e){}
    	return (nodeList);
    }
    
	public static ArrayList<Node> getChildNodes(Node node,String child)
    {
    	ArrayList<Node> nodeList=new ArrayList<Node>();
    	try
    	{
    		for (int i=0;i<node.getChildNodes().getLength();i++)
    			if (node.getChildNodes().item(i).getNodeName().equals(child))
    				nodeList.add(node.getChildNodes().item(i));
    	}
    	catch(Exception e){}
    	return (nodeList);
    }
    
	public static Node getChildNode(Node node,String child)
    {
    	Node retNode=null;
    	try
    	{
    		for (int i=0;i<node.getChildNodes().getLength();i++)
    			if (node.getChildNodes().item(i).getNodeName().equals(child))
    			{
    				retNode=node.getChildNodes().item(i);
    				break;
    			}
    	}
    	catch(Exception e){}
    	return (retNode);
    }
	
    public static boolean checkExists(Node node,String sequence)
    {
        String seq[]=sequence.split("/");
        boolean exists=true;
        for (int i=0;i<seq.length;i++)
        {
            NodeList childNodes=node.getChildNodes();
            Node newCurrNode=null;
            for (int j=0;j<childNodes.getLength();j++)
            {
                if (childNodes.item(j).getNodeName().equals(seq[i]))
                {
                    newCurrNode=childNodes.item(j);
                }
            }
            if (newCurrNode==null)
            {
                exists=false;
                break;
            }
            node=newCurrNode;
        }
        return exists;
    }

    public static boolean createIfNotExists(Document document,Node node,String sequence)
    {
        if (checkExists(node, sequence))
            return true;

        String seq[]=sequence.split("/");
        for (int i=0;i<seq.length;i++)
        {
            NodeList childNodes=node.getChildNodes();
            Node newCurrNode=null;
            for (int j=0;j<childNodes.getLength();j++)
            {
                if (childNodes.item(j).getNodeName().equals(seq[i]))
                {
                    newCurrNode=childNodes.item(j);
                }
            }
            if (newCurrNode==null)
            {
                Element newElement=document.createElement(seq[i]);
                node.appendChild(newElement);
                newCurrNode=newElement;
            }
            node=newCurrNode;
        }
        return checkExists(node, sequence);
    }
    
    public static Node createIfNotExists(Document document,Node node,ArrayList<NodeContents> sequence)
    {
    	Node checkNode = checkExists(node, sequence);
    	if(checkNode != null){
    		return checkNode; 
    	}
    	
    	Node bkNode=node;
        for (int i=0;i<sequence.size();i++)
        {
            NodeList childNodes=node.getChildNodes();
            Node newCurrNode=null;
            for (int j=0;j<childNodes.getLength();j++)
            {
            	if (sequence.get(i).equals(childNodes.item(j)))
                {
                    newCurrNode=childNodes.item(j);
                }
            }
            if (newCurrNode==null)
            {
            	Element newElement=document.createElement(sequence.get(i).nodeName);
                node.appendChild(newElement);
                newElement.setTextContent(sequence.get(i).nodeContent!=null?sequence.get(i).nodeContent:"");
                for (int j=0;sequence.get(i).attributes!=null && sequence.get(i).attributes.size()>0 && j<sequence.get(i).attributes.size();j++)
                {
                	String attr[]=sequence.get(i).attributes.get(j).split("=");
                	newElement.setAttribute(attr[0],attr[1]);                	
                }
                newCurrNode=newElement;
            }
            node=newCurrNode;
        }      
        
        return checkExists(bkNode, sequence);
            		
    }
    public static Node checkExists(Node node,ArrayList<NodeContents> sequence)
    {    
       for (int i=0;i<sequence.size();i++)
       {
           NodeList childNodes=node.getChildNodes();
           Node newCurrNode=null;
           for (int j=0;j<childNodes.getLength();j++)
           {
               if (sequence.get(i).equals(childNodes.item(j)))
               {
                   newCurrNode=childNodes.item(j);
                   break;
               }
           }
           if (newCurrNode==null)
           {
        	   node = null;
               break;
           }
           node=newCurrNode;
        }
        return node;
    }
    
    public static Node getPreviousSibling(Node xmlNode){
		Node previousSibling = xmlNode.getPreviousSibling();
		while (true) {
			//No previous sibling found
			if(previousSibling == null ){break;}
			//Only 'ELEMENT_NODE' type of nodes required.
			if(previousSibling.getNodeType() == Node.ELEMENT_NODE){
				break;
			}
			previousSibling = previousSibling.getPreviousSibling();
		} 
		return previousSibling;
	}
    public static Node getNextSibling(Node xmlNode){
		Node nextSibling = xmlNode.getNextSibling();
		while (true) {
			//No next sibling found
			if(nextSibling == null ){break;}
			//Only 'ELEMENT_NODE' type of nodes required.
			if(nextSibling.getNodeType() == Node.ELEMENT_NODE){
				break;
			}
			nextSibling = nextSibling.getNextSibling();
		}
		return nextSibling;
	}
    public static Node getFirstChild(Node xmlNode){
		Node firstChild = xmlNode.getFirstChild();
		while (true) {
			//No next sibling found
			if(firstChild == null ){break;}
			//Only 'ELEMENT_NODE' type of nodes required.
			if(firstChild.getNodeType() == Node.ELEMENT_NODE){
				break;
			}
			//Get Next Node
			firstChild = firstChild.getNextSibling();
		}
		return firstChild;
	}
    public static void main(String args[])
    {
        try
        {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            //Document document = db.parse("/home/index.xml");
            Document document = db.parse("/home/Tianeptine-Lupin-454/0000/index.xml");
            Node rootNode=document.getElementsByTagName("ectd:ectd").item(0);
            FileOutputStream fileOutputStream=new FileOutputStream("/home/tst.xls");
            PrintStream printStream=new PrintStream(fileOutputStream);
            System.setOut(printStream);
            System.out.println("Node ID\tTitle\tChecksum\tOperation\tPath\tPath Length\tFile Exists");
            check(rootNode,"/home/Tianeptine-Lupin-454/0000/");
            printStream.close();
 /*           
            //Document document = db.newDocument();
            Element book = document.createElement("book");            
            document.appendChild(book);
            for (int i = 1; i <= 2; i++) {
                Element chapter = document.createElement("chapter");
                Element title = document.createElement("title");
                title.appendChild(document.createTextNode("Chapter " + i));
                chapter.appendChild(title);
                chapter.appendChild(document.createElement("para"));
                book.appendChild(chapter);
            }

            System.out.println(checkExists(document,"book/chapter/title"));
            System.out.println(checkExists(document,"book/nagesh/chapter/title"));
            System.out.println(createIfNotExists(document,document,"book/chapter/title"));
            //System.out.println(createIfNotExists(document,document,"book/nagesh/chapter/title"));
            ArrayList<NodeContents> nc=new ArrayList<NodeContents>();
            NodeContents node=new NodeContents("book",null,null);
            nc.add(node);
            node=new NodeContents("nagesh","sspl",null);
            nc.add(node);
            node=new NodeContents("chapter","sspl123",null);
            nc.add(node);
            ArrayList<String> attr=new ArrayList<String>();
            attr.add("abc=def");
            attr.add("pqr=xyz");
            node=new NodeContents("title",null,attr);
            nc.add(node);            
            System.out.println(createIfNotExists(document,document,nc));
            System.out.println(checkExists(document,"book/nagesh/chapter/title"));
            
            TransformerFactory tf = TransformerFactory.newInstance();
            //Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(System.out);
            Properties outProperties = new Properties();
            outProperties.setProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            outProperties.setProperty(OutputKeys.INDENT, "yes");
           // transformer.setOutputProperties(outProperties);
           // transformer.transform(source, result);
*/
        }        
        catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
  
    private static void check(Node node,String path)
    {    	
    	if (node.getChildNodes().getLength()==0)
    		return;
    	ArrayList<Node> childList=getChildNodes(node);
    	for (int i=0;i<childList.size();i++)
    	{
    		Node childNode=childList.get(i);
    		String nodeName=childNode.getNodeName();
    		if (nodeName.equals("leaf"))
    		{
    			String hrefAttr=getNodeAttribute(childNode,"xlink:href");
    			if (!hrefAttr.contains("m3"))
    				continue;
    			String chksAttr=getNodeAttribute(childNode,"checksum");
    			String ndIdAttr=getNodeAttribute(childNode,"ID");
    			Node titleNode=getFirstChild(childNode);
    			String title=titleNode.getTextContent();
    			String operation=getNodeAttribute(childNode,"operation");
    			File file=new File(path + hrefAttr);
    			System.out.println(ndIdAttr + "\t" + title + "\t" + chksAttr + "\t" + operation + "\t" + hrefAttr + "\t" + hrefAttr.length() + "\t" + file.exists());
    		}
    		else
    			check(childList.get(i),path);
    	}
    }
    
}
