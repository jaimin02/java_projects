package com.docmgmt.struts.actions.util;

public class CommonUtilMethod {

	public static String replaceSpecialCharacters(String character){
		if(character.contains("!"))
			character = character.replaceAll("!", "%21");
		if(character.contains("*"))
			character = character.replaceAll("*", "%2A");
		if(character.contains("‘"))
			character = character.replaceAll("‘", "%27");
		if(character.contains("("))
			character = character.replaceAll("(", "%28");
		if(character.contains(")"))
			character = character.replaceAll(")", "%29");
		if(character.contains(";"))
			character = character.replaceAll(";", "%3B");
		if(character.contains(":"))
			character = character.replaceAll(":", "%3A");
		if(character.contains("@"))
			character = character.replaceAll("@", "%40");
		if(character.contains("&"))
			character = character.replaceAll("&", "%26");
		if(character.contains("="))
			character = character.replaceAll("=", "%3D");
		if(character.contains("+"))
			character = character.replaceAll("+", "%2B");
		if(character.contains("$"))
			character = character.replaceAll("$", "%24");
		if(character.contains(","))
			character = character.replaceAll(",", "%2C");
		if(character.contains("/"))
			character = character.replaceAll("/", "%2A");
		if(character.contains("?"))
			character = character.replaceAll("?", "%3F");
		if(character.contains("#"))
			character = character.replaceAll("#", "%23");
		if(character.contains("["))
			character = character.replaceAll("[", "%5B");
		if(character.contains("]"))
			character = character.replaceAll("]", "%5D");
		
		return character;
	}
	
}
