package Test;


import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.DispatchEvents;
import com.jacob.com.Variant;

/**
 * Submitted to the Jacob SourceForge web site as a sample 3/2005
 * <p>
 * This sample is BROKEN because it doesn't call quit!
 * 
 * @author Date Created Description Jason Twist 04 Mar 2005 Code opens a locally
 *         stored Word document and extracts the Built In properties and Custom
 *         properties from it. This code just gives an intro to JACOB and there
 *         are sections that could be enhanced
 */
public class MSExcelParse { 

	  public static void main(String[] args) {
	      MSExcelParse wordEventTest = new MSExcelParse();
	      wordEventTest.execute();
	  }

	  public void execute() {


	      String strDir = "C:\\Users\\SSPL712\\Desktop\\New folder (2)";
	      String strInputDoc = strDir + "Gxp-Assessment-Initial-Impact-Assessment.doc";

	      String pid = "Word.Application";

	      ActiveXComponent axc = new ActiveXComponent(pid);
	      axc.setProperty("Visible", new Variant(true));
	      Dispatch oDocuments = axc.getProperty("Documents").toDispatch();
	      Dispatch oDocument = Dispatch.call(oDocuments, "Open", strInputDoc).toDispatch();

	      WordEventHandler w = new WordEventHandler();
	      new DispatchEvents(oDocument, w);

	  }

	  public class WordEventHandler {
	      public void Close(Variant[] arguments) {
	          System.out.println("closed word document");
	      }
	  }
}