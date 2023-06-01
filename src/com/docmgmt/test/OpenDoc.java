package com.docmgmt.test;
import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.ole.win32.OLE;
import org.eclipse.swt.ole.win32.OleClientSite;
import org.eclipse.swt.ole.win32.OleFrame;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;



public class OpenDoc {
    static OleClientSite clientSite;
    static OleFrame frame;
    static String fileName = "C:\\Users\\SSPL712\\Desktop\\New folder (2)\\Gxp-Assessment-Initial-Impact-Assessment.doc";


public static void main(final String[] args) {
    final Display display = new Display();
    final Shell shell = new Shell(display);

    shell.setSize(1200, 650);
    shell.setText(fileName);
    shell.setLayout(new FillLayout());

    try {
        frame = new OleFrame(shell, SWT.NONE);
        // esto abre un documento existente
        // clientSite = new OleClientSite(frame, SWT.NONE, new File("Doc1.doc"));
        // esto abre un documento en blanco
        clientSite = new OleClientSite(frame, SWT.NONE, "Word.Document");
        fileOpen();
        System.out.println(" I am in run method ");
    } catch (final SWTError e) {
        System.out.println("Unable to open activeX control");
        display.dispose();
        return;
    }
    shell.addListener(SWT.Close, new Listener()
    {
        public void handleEvent(Event event)
        {
            int style = SWT.APPLICATION_MODAL | SWT.YES | SWT.NO;
            MessageBox messageBox = new MessageBox(shell, style);
            messageBox.setText("Information");
            messageBox.setMessage("Save File?");
            event.doit = messageBox.open() == SWT.YES;
   
            if(event.doit){
            	System.out.println("true");
            	clientSite.save(new File(fileName), true);
            }
            else{
            	System.out.println("false");
            	display.dispose();
            }
        }
    });

    shell.open();

    while (!shell.isDisposed()) {
        if (!display.readAndDispatch()) {
            display.sleep();
        }
    }
   
}
static void fileOpen() {
    FileDialog dialog = new FileDialog(clientSite.getShell(), SWT.OPEN);
    dialog.setFilterExtensions(new String[] { "*.doc","*.docx" });
    
    if (fileName != null) {
        clientSite.dispose();
        clientSite = new OleClientSite(frame, SWT.NONE, "Word.Document", new File(fileName));
        clientSite.doVerb(OLE.OLEIVERB_INPLACEACTIVATE);
    }
}
}
