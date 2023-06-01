package com.docmgmt.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class ZipFiles {
	
	
	public static void main(String args[]) throws Exception
	{
		
	  File directoryToBeZipped = new File("//90.0.0.15/docmgmtandpub/PublishDestinationFolder/0805/0350/ZA-node-extension");
	  File zipFile = new File("//90.0.0.15/docmgmtandpub/PublishDestinationFolder/0805/0350/ZA-node-extension.zip");
	  net.lingala.zip4j.core.ZipFile zip = new net.lingala.zip4j.core.ZipFile(zipFile);

	  // Adding the list of files and directories to be zipped to a list
	  ArrayList<File> fileList = new ArrayList<>();
	  Arrays.stream(directoryToBeZipped.listFiles()).forEach((File file) -> {fileList.add(file);});

	  ZipParameters parameters = new ZipParameters();
	  parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
	  parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_FASTEST);
	  parameters.setEncryptFiles(false);

	  fileList.stream().forEach((File f) -> {
			try
			{
				if(f.isDirectory())
				{
					zip.addFolder(f, parameters);
				}
				else
				{
					zip.addFile(f, parameters);
				}
			}
			catch(ZipException zipExceptio)
			{
				//LOGGER.log(Level.SEVERE, "ZIP Exception while creating encrypted zips.", zipExceptio);
			}
		});

	}
	
	/*
	
	
	ZipFile zipFile = new ZipFile("//90.0.0.15/docmgmtandpub/PublishDestinationFolder/0805/0350.zip");
	ZipParameters parameters = new ZipParameters();

	zipFile.addFolder(new File("C:/Users/XXXXXX/Desktop/HELLO_Folder"), ZipParameters);
	
	File directoryToBeZipped = new File('c:/pathToDirectory');
	  File zipFile = new File('example.zip');
	  net.lingala.zip4j.core.ZipFile zip = new net.lingala.zip4j.core.ZipFile(zipFile);

	  // Adding the list of files and directories to be zipped to a list
	  ArrayList<File> fileList = new ArrayList<>();
	  Arrays.stream(directoryToBeZipped.listFiles()).forEach((File file) -> {fileList.add(file);});

	  ZipParameters parameters = new ZipParameters();
	  parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
	  parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_FASTEST);
	  parameters.setEncryptFiles(false);

	  fileList.stream().forEach((File f) -> {
			try
			{
				if(f.isDirectory())
				{
					zip.addFolder(f, parameters);
				}
				else
				{
					zip.addFile(f, parameters);
				}
			}
			catch(ZipException zipExceptio)
			{
				//LOGGER.log(Level.SEVERE, "ZIP Exception while creating encrypted zips.", zipExceptio);
			}
		});
*/}