package com.docmgmt.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class UnZipFiles {
	List<String> fileList;
	private static final String INPUT_ZIP_FILE = "//90.0.0.15/docmgmtandpub/seqZip/0240/0000/0000.zip";
	private static final String OUTPUT_FOLDER = "//90.0.0.15/docmgmtandpub/seqZip/0240/0000";

	public static void main(String[] args) throws ZipException, IOException {
		unzip(new File(INPUT_ZIP_FILE), new File(OUTPUT_FOLDER));
		
	}

	public static void unzip(File file, File targetDir) throws ZipException,
			IOException {
		targetDir.mkdirs();
		ZipFile zipFile = new ZipFile(file);
		try {
			Enumeration<? extends ZipEntry> entries = zipFile.entries();
			while (entries.hasMoreElements()) {
				ZipEntry entry = entries.nextElement();
				File targetFile = new File(targetDir, entry.getName());
				if (entry.isDirectory()) {
					targetFile.mkdirs();
				} else {
					InputStream input = zipFile.getInputStream(entry);
					try {
						OutputStream output = new FileOutputStream(targetFile);
						try {
							copy(input, output);
						} finally {
							output.close();
						}
					} finally {
						input.close();
					}
				}
			}
		}finally {
			zipFile.close();
		}
	}
	private static void copy(InputStream input, OutputStream output)
			throws IOException {
		byte[] buffer = new byte[4096];
		int size;
		while ((size = input.read(buffer)) != -1)
			output.write(buffer, 0, size);
	}
}