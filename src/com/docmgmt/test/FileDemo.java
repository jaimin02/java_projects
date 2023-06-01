package com.docmgmt.test;


public class FileDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//File DirLocation = new File("E:\\test");
		
		//FileManager.deleteDir(DirLocation);
		System.out.println(foo());
		
		/*
		if (DirLocation.isDirectory()) {
			String totalFile[] = DirLocation.list();

			ArrayList<String> extens = new ArrayList<String>();
			ArrayList<String> allreadyCounted = new ArrayList<String>();
			for (int i = 0; i < totalFile.length; i++) {
				extens.add(totalFile[i]
						.substring(totalFile[i].lastIndexOf(".")));
			}
			for (int i = 0; i < extens.size(); i++) {
				String exten = extens.get(i);
				if (allreadyCounted.contains(exten))
					continue;
				allreadyCounted.add(exten);
				int counter = 0;
				for (int j = i; j < extens.size(); j++) {
					if (exten.equalsIgnoreCase(extens.get(j)))
						counter++;
				}

				System.out.println("Total File With Extention " + exten + "==>"
						+ counter);
			}
		}
		*/

	}

	@SuppressWarnings("finally")
	static String foo() {
	    try {
	      return "try ...";
	    } catch (Exception e) {
	      return "catch ...";
	    } finally {
	      return "finally ..."; 
	    }
	  }
}
