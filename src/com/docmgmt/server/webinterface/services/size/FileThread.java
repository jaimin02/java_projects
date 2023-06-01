package com.docmgmt.server.webinterface.services.size;

import java.io.File;

import com.docmgmt.dto.DTOFolderSize;

public class FileThread extends Thread
{
    File file;
    DTOFolderSize folderSize;

    FileThread(File file,DTOFolderSize folderSize)
    {
        this.file=file;
        this.folderSize=folderSize;
    }

    @Override
	public void run()
    {
        synchronized(folderSize)
        {
            if (file.isDirectory())
            {
                for (int i=0;i<file.listFiles().length;i++)
                {
                    File f=file.listFiles()[i];
                    if (f.isFile())// && !f.isHidden())
                    {
                        folderSize.setSizeBytes(folderSize.getSizeBytes()+f.length());
                        folderSize.setNoOfFiles(folderSize.getNoOfFiles()+1);
                        //System.out.println(f.getAbsolutePath());
                    }
                }
            }
        }
    }
}