package com.docmgmt.server.webinterface.services.size;

import java.io.File;
import java.util.Vector;

import com.docmgmt.dto.DTOFolderSize;

public class GetFolderSize
{
	DTOFolderSize folderSize;
    Vector<Thread> fileThreads;

    public GetFolderSize()
    {
        folderSize=new DTOFolderSize();
        fileThreads=new Vector<Thread>();
    }

    void getFolderSize(File folderPath)
    {
        if (folderPath==null)
            return;
        if (folderPath.isDirectory())
        {
            //System.out.println(folderPath.getAbsolutePath());            
            for (int i=0;i<folderPath.listFiles().length;i++)
            {
                File f=folderPath.listFiles()[i];
                if (f.isDirectory())
                {
                    folderSize.setNoOfFolders(folderSize.getNoOfFolders()+1);
                    FileThread fileThread=new FileThread(f, folderSize);
                    fileThreads.add(fileThread);
                    fileThread.start();
                    getFolderSize(f);
                }
            }
        }
    }

    public void getFolderSize(String folderPath)
    {
    	File file=new File(folderPath);
        getFolderSize(file);
        FileThread fileThread=new FileThread(file, folderSize);
        fileThreads.add(fileThread);
        fileThread.start();
    }

    public DTOFolderSize getFinalOutput()
    {
        //System.out.println("no. of threads: " + fileThreads.size());
        for (int i=0;i<fileThreads.size();i++)
        {
            try
            {
                Thread thread = fileThreads.get(i);
                thread.join();
            }
            catch (InterruptedException ex)
            {
                ex.printStackTrace();
            }
        }
        folderSize.setSizeKBytes(folderSize.getSizeBytes()/1024.0);
        folderSize.setSizeMBytes(folderSize.getSizeKBytes()/1024.0);
        folderSize.setSizeGBytes(folderSize.getSizeMBytes()/1024.0);
        folderSize.setSizeKBytes(Math.round(folderSize.getSizeKBytes()*100.0)/100.0);
        folderSize.setSizeMBytes(Math.round(folderSize.getSizeMBytes()*100.0)/100.0);
        folderSize.setSizeGBytes(Math.round(folderSize.getSizeGBytes()*100.0)/100.0);
        return folderSize;
    }
}
