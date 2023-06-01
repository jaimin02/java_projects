package com.docmgmt.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.itextpdf.text.DocumentException;

public class LinearizedPdf {

	/**
	 * @param args
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static void main(String[] args) throws DocumentException,
			IOException {

		String source = "//90.0.0.15/docmgmtandpub/resu1.pdf";
		String Dest = "//90.0.0.15/docmgmtandpub/resu111.pdf";

		if (new LinearizedPdf().isLinearized(source)) {

			System.out.println("Linearized");
		} else
			new LinearizedPdf().linearize(source, Dest);
	}

	public boolean isLinearized(String source) {

		String command = "D:\\vijay\\qpdflib\\bin\\qpdf --check " + source
				+ " ";

		boolean isLinearized = false;
		try {
			Runtime rt = Runtime.getRuntime();
			Process pCD = rt.exec(command);
			InputStream stderr = pCD.getErrorStream();
			BufferedReader input = new BufferedReader(new InputStreamReader(pCD
					.getInputStream()));

			InputStreamReader isr = new InputStreamReader(stderr);

			// BufferedReader br = new BufferedReader(isr);
			String line = null;

			// while ((line = br.readLine()) != null)
			// // wait until process complete
			// System.out.println(line);

			while ((line = input.readLine()) != null) {

				if (line.equals("File is linearized")) {
					isLinearized = true;
				}
				System.out.println(line);
			}
			int exitVal = pCD.waitFor();
			input.close();
			isr.close();
			stderr.close();
			System.gc();

			System.out.println("Process exitValue: " + exitVal);
		} catch (Exception e) {

			e.printStackTrace();
			// TODO: handle exception
		}

		return isLinearized;
	}

	public void linearize(String sourceFile, String destFile) {

		String command = "D:\\vijay\\qpdflib\\bin\\qpdf --linearize "
				+ sourceFile + " " + destFile + " ";
		try {
			Runtime rt = Runtime.getRuntime();
			Process pCD = rt.exec(command);
			InputStream stderr = pCD.getErrorStream();
			InputStreamReader isr = new InputStreamReader(stderr);

			BufferedReader br = new BufferedReader(isr);
			String line = null;
			System.out.println("<ERROR>");
			BufferedReader input = new BufferedReader(new InputStreamReader(pCD
					.getInputStream()));
			while ((line = input.readLine()) != null) {
				System.out.println(line);
			}

			// To handle Error
			// while ((line = br.readLine()) != null)
			// // wait until process complete
			// System.out.println(line);
			System.out.println("</ERROR>");
			int exitVal = pCD.waitFor();
			File sFile = new File(sourceFile);
			File dFile = new File(destFile);
			sFile.delete();
			stderr.close();

			
			isr.close();
			System.gc();
			File sFile1 = new File(sourceFile);
			dFile.renameTo(sFile1);

			System.out.println("Process exitValue: " + exitVal);
		} catch (Exception e) {

			e.printStackTrace();
			// TODO: handle exception
		}
	}

}
