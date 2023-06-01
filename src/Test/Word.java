package Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.ole.win32.OleClientSite;
import org.eclipse.swt.ole.win32.OleFrame;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

//import com.connect.DBConnectionHandler;

public class Word {
    static OleClientSite clientSite;
    static OleFrame frame;
    static File file;
    static Shell shell;
    static KeyListener keyListener;
    static Display display;

    public static void main(final String[] args) {
        display = new Display();
        shell = new Shell(display);

        shell.setSize(800, 600);
        shell.setText("Word Example");
        shell.setLayout(new FillLayout());

        try {
            frame = new OleFrame(shell, SWT.NONE);
            // esto abre un documento existente
            // clientSite = new OleClientSite(frame, SWT.NONE, new
            // File("Doc1.doc"));
            // esto abre un documento en blanco
            // clientSite = new OleClientSite(frame, SWT.NONE, "Word.Document");
            addFileMenu(frame);
            System.out.println(" I am in run method ");
        } catch (final SWTError e) {
            System.out.println("Unable to open activeX control");
            display.dispose();
            return;
        }

        keyListener = new KeyListener() {

            public void keyReleased(KeyEvent paramKeyEvent) {

            }

            public void keyPressed(KeyEvent paramKeyEvent) {
                // TODO Auto-generated method stub
                if (((paramKeyEvent.stateMask & SWT.CTRL) == SWT.CTRL)
                        && (paramKeyEvent.keyCode == 's')) {
                    JOptionPane.showMessageDialog(null,
                            "ctrl+s is pressed down initial ",
                            "Warning Message", JOptionPane.WARNING_MESSAGE);
                    if (file != null) {
                        clientSite.save(file, true);
                       // fileSave();
                        JOptionPane.showMessageDialog(null,
                                "ctrl+s is pressed down", "Warning Message",
                                JOptionPane.WARNING_MESSAGE);
                    } else
                        JOptionPane.showMessageDialog(null, "File is null",
                                "Warning Message", JOptionPane.WARNING_MESSAGE);
                }

            }
        };

        display.addFilter(SWT.KeyDown, new Listener() {

            public void handleEvent(Event e) {
                if (((e.stateMask & SWT.CTRL) == SWT.CTRL)
                        && (e.keyCode == 's')) {
                    System.out.println("From Display I am the Key down !!"
                            + e.keyCode);
                }
            }
        });

        final Color green = display.getSystemColor(SWT.COLOR_GREEN);
        final Color orig = shell.getBackground();

        shell.addKeyListener(new KeyListener() {
            public void keyReleased(KeyEvent e) {
                if (((e.stateMask & SWT.CTRL) == SWT.CTRL)
                        && (e.keyCode == 's')) {
                    shell.setBackground(orig);
                    System.out.println("Key up !!");
                }
            }

            public void keyPressed(KeyEvent e) {
                if (((e.stateMask & SWT.CTRL) == SWT.CTRL)
                        && (e.keyCode == 's')) {
                    shell.setBackground(green);
                    System.out.println("Key down !!");
                }
            }
        });

        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }

    static void addFileMenu(OleFrame frame) {
        final Shell shell = frame.getShell();
        Menu menuBar = shell.getMenuBar();
        if (menuBar == null) {
            menuBar = new Menu(shell, SWT.BAR);
            shell.setMenuBar(menuBar);
        }
        MenuItem fileMenu = new MenuItem(menuBar, SWT.CASCADE);
        fileMenu.setText("&File");
        Menu menuFile = new Menu(fileMenu);
        fileMenu.setMenu(menuFile);
        frame.setFileMenus(new MenuItem[] { fileMenu });
        
        MenuItem menuFileOpen = new MenuItem(menuFile, SWT.CASCADE);
        menuFileOpen.setText("Open File");
        menuFileOpen.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                fileOpen();
            }
        });
        MenuItem menuFileClose = new MenuItem(menuFile, SWT.CASCADE);
        menuFileClose.setText("Close File");
        menuFileClose.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                if (clientSite != null) {
                    clientSite.dispose();
                    file = null;
                }
            }
        });
    }

    static void fileOpen() {
        List<String> FileName = new ArrayList<String>();
       File file =  new File("C:\\Users\\SSPL712\\Desktop\\New folder (2)\\Gxp-Assessment-Initial-Impact-Assessment.doc");
       // try {
          /*  Connection conn = DBConnectionHandler.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery("select FILENAME from FILEDATA");
            while (rset.next())*/
                FileName.add((file.toString()));
            System.out.println("List Size "+FileName.size());
            if (clientSite != null)
                clientSite.dispose();
            shell.setVisible(false);
            /*if (file != null)
                file.delete();*/
            Word swtClass = new Word();
          /*  NewJFrame form = new NewJFrame(FileName, clientSite, shell, frame,
                    swtClass);
            form.setVisible(true);
            form.setSize(400, 400);*/

            /*} catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/

        /*
         * FileDialog dialog = new FileDialog(shell, SWT.OPEN);
         * dialog.setFilterExtensions(new String[] { "*.docx" }); String
         * fileName = dialog.open(); if (fileName != null) { if (clientSite !=
         * null) clientSite.dispose(); file = new File(fileName); clientSite =
         * new OleClientSite(frame, SWT.NONE, "Word.Document", file);
         * clientSite.addKeyListener(keyListener);
         * clientSite.doVerb(OLE.OLEIVERB_INPLACEACTIVATE); }
         */
    }


   /* static void fileSave() {
        try {
            FileInputStream fis = new FileInputStream(file);
            Connection conn = DBConnectionHandler.getConnection();

            PreparedStatement pstmt = conn
                    .prepareStatement("update FILEDATA  set FILEDATA = ?  where FILENAME = ?");
            pstmt.setBinaryStream(1, fis, (int) file.length());
            pstmt.setString(2, file.getName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }*/
}
