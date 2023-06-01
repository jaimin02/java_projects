package Test;


import com.aspose.email.Attachment;
import com.aspose.email.AttachmentCollection;
import com.aspose.email.EmlLoadOptions;
import com.aspose.email.MailMessage;

public class OutlooktoPDF {

	public static void main(String[] args) {
		// The path to the resource directory.
		String dataDir = "C:\\Users\\SSPL416\\Desktop\\convert\\";

		//Initialize and Load an existing EML file
		MailMessage msg = MailMessage.load(dataDir + "eml", new EmlLoadOptions());

		//Initialize AttachmentCollection object with MailMessage Attachments
		AttachmentCollection attachments = msg.getAttachments();

		//Iterate over the AttachmentCollection
		for (int index = 0; index < attachments.size(); index++) {
			//Initialize Attachment object and Get the indexed Attachment reference
			Attachment attachment = (Attachment) attachments.get_Item(index);
			//Display Attachment Name
			System.out.println(attachment.getName());
			//Save Attachment to disk
			attachment.save(dataDir + "attachment_" + attachment.getName());
		}
	}

}
