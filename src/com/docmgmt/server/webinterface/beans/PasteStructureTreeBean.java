
package com.docmgmt.server.webinterface.beans;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.TreeMap;
import java.util.Vector;

import com.docmgmt.server.db.DocMgmtImpl;

public class PasteStructureTreeBean
{
	 private Object docMgmtLog;
	 private int userCode;
	 private Vector nodeInfoVector;
	 private String userType;
	 
 public PasteStructureTreeBean()
 {
     nodeInfoVector = new Vector();
 }

 DocMgmtImpl docMgmt = new DocMgmtImpl();

 public String getTemplateTreeHtml(String templateID, int userGroupCode, int userCode)
 {
     StringBuffer htmlSB = null;
     Vector templateTreeVector = getTreeNodes(templateID, userGroupCode, userCode);
     if(templateTreeVector.size() > 0)
     {
         htmlSB = new StringBuffer();
         TreeMap childNodeHS = new TreeMap();
         Hashtable nodeDtlHS = new Hashtable();
         Integer firstNode = null;
         for(int i = 0; i < templateTreeVector.size(); i++)
         {
             Object nodeRec[] = (Object[])templateTreeVector.get(i);
             Integer parentNodeId = (Integer)nodeRec[2];
             Integer currentNodeId = (Integer)nodeRec[0];
             if(i == 0)
                 firstNode = currentNodeId;
             if(childNodeHS.containsKey(parentNodeId))
             {
                 List childList = (List)childNodeHS.get(parentNodeId);
                 childList.add(currentNodeId);
                 if(!childNodeHS.containsKey(currentNodeId)){
                	 childNodeHS.put(currentNodeId, new ArrayList()); 
                 }
                 
             } else
             {
                 List childList = new ArrayList();
                 childList.add(currentNodeId);
                 childNodeHS.put(parentNodeId, childList);
                 if(!childNodeHS.containsKey(currentNodeId)){
                	 childNodeHS.put(currentNodeId, new ArrayList()); 
                 }
             }
             nodeDtlHS.put(currentNodeId, ((new Object[] {
                 nodeRec[1], nodeRec[2]
             })));
         }

         if(firstNode != null)
         {
             Object firstNodeObj[] = (Object[])nodeDtlHS.get(firstNode);
             htmlSB.append("    <DIV class=trigger onclick=\"showBranch1('Tbranch" + firstNode.intValue() + "','" + firstNode.intValue() + "');swapFolder1('Tfolder" + firstNode.intValue() + "')\">\n");
             htmlSB.append("<input type= \"checkbox\" name ='TCHK_" + firstNode.intValue() + "' value='TCHK_" + firstNode.intValue() + "'   style= \"border:0px\" onClick = \" return filledHiddenValueT('" + firstNode.intValue() + "');\">");
             htmlSB.append("        <IMG id='Tfolder" + firstNode.intValue() + "' src=\"images/closed.gif\" border=\"0\">" + ((String)firstNodeObj[0]).replaceAll(" ", "&nbsp;") + "\n");
             htmlSB.append("    </DIV>\n");
             htmlSB.append("    <SPAN class=\"branch\" id='Tbranch" + firstNode.intValue() + "'>\n");
             List childList = (List)childNodeHS.get(firstNode);
             for(int i = 0; i < childList.size(); i++)
             {
                 StringBuffer sb = getChildStructure((Integer)childList.get(i), childNodeHS, nodeDtlHS);
                 htmlSB.append(sb);
             }

             htmlSB.append("    </SPAN>\n");
         }
     }
     if(htmlSB != null)
         return htmlSB.toString();
     else
         return null;
 }

 private StringBuffer getChildStructure(Integer nodeId, TreeMap childNodeHS, Hashtable nodeDtlHS)
 {
     StringBuffer htmlStringBuffer = new StringBuffer();
     List childList = (List)childNodeHS.get(nodeId);
     Object nodeDtlObj[] = (Object[])nodeDtlHS.get(nodeId);
     String displayName = (String)nodeDtlObj[0];
     if(childList.size() > 0)
     {
         htmlStringBuffer.append("<SPAN class=\"trigger\" onclick=\"showBranch1('Tbranch" + nodeId.intValue() + "','" + nodeId.intValue() + "');swapFolder1('Tfolder" + nodeId.intValue() + "')\">\n");
         htmlStringBuffer.append("<input type= \"checkbox\" name = 'TCHK_" + nodeId.intValue() + "' value='TCHK_" + nodeId.intValue() + "' style= \"border:0px\" onClick = \" return filledHiddenValueT('" + nodeId.intValue() + "');\">");
         htmlStringBuffer.append("<IMG id='Tfolder" + nodeId.intValue() + "' src=\"images/closed.gif\">" + displayName +  "<BR>\n");
         htmlStringBuffer.append("</SPAN>\n");
         htmlStringBuffer.append("<SPAN class=\"branch\" id='Tbranch" + nodeId.intValue() + "'>\n");
         for(int i = 0; i < childList.size(); i++)
             htmlStringBuffer.append(getChildStructure((Integer)childList.get(i), childNodeHS, nodeDtlHS));

         htmlStringBuffer.append("</SPAN>\n");
     } else
     {
         htmlStringBuffer.append("<input type= \"checkbox\" name ='TCHK_" + nodeId.intValue() + "' value='TCHK_" + nodeId.intValue() + "' style= \"border:0px\" onClick = \" return filledHiddenValueT('" + nodeId.intValue() + "');\">");
         htmlStringBuffer.append("<IMG  src=\"images/empty.gif\">" + displayName + "<BR>\n");
     }
     return htmlStringBuffer;
 }

 public Vector getTreeNodes(String templateId, int userGroupCode, int userCode)
 {
 	try{

     nodeInfoVector = docMgmt.getTemplateForTreeDisplay(templateId);
     return nodeInfoVector;
 	}
    catch(Exception e)
	{
     e.printStackTrace();
	} 
     return nodeInfoVector;
 }


 public int getUserCode()
 {
     return userCode;
 }

 public void setUserCode(int i)
 {
     userCode = i;
 }

 public String getUserType()
 {
     return userType;
 }

 public void setUserType(String userType)
 {
     this.userType = userType;
 }

}