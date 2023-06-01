package com.docmgmt.server.webinterface.entities.Workspace;

import com.docmgmt.server.prop.PropertyInfo;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;

public class FontTypes {
	
	public static final String timesNewRoman = "TIMES_ROMAN";
	public static final String italic = "TIMES_ITALIC";
	public static final String timesBold = "TIMES_BOLD";
	public static final String verdana = "VERDANA";
	public static final PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
	String externalFontPath = propertyInfo.getValue("fontStyle");
	
	public Font registerExternalFont(String fontName,String ManualSignatureConfig) {
		// String alias=fontName.replace(".ttf", "");
		// alias=alias.replace(".TTF", "");
		if(ManualSignatureConfig.equalsIgnoreCase("Yes")){
			Font customFont = null;
			if (fontName.equalsIgnoreCase("Times New Roman")) {
				customFont = FontFactory.getFont(FontFactory.TIMES_ROMAN,7f, Font.BOLD, BaseColor.BLACK);
			} 
			else if (fontName.equalsIgnoreCase("Verdana")) {
				fontName= "verdana.ttf";
				FontFactory.register(externalFontPath + "/" + fontName,
						"custom_font");
				customFont = FontFactory.getFont("custom_font",7f, Font.BOLD, BaseColor.BLACK);
			}
			else if (fontName.equalsIgnoreCase("Calibri")) {
				fontName= "calibri.ttf";
				FontFactory.register(externalFontPath + "/" + fontName,
						"custom_font");
				customFont = FontFactory.getFont("custom_font",7f, Font.BOLD, BaseColor.BLACK);
			}else if (fontName.equalsIgnoreCase("Brush Script MT")) {
				fontName= "BRUSHSCI.TTF";
				FontFactory.register(externalFontPath + "/" + fontName,
						"custom_font");
				customFont = FontFactory.getFont("custom_font",7f, Font.BOLD, BaseColor.BLACK);
			}else if (fontName.equalsIgnoreCase("Times")) {
				fontName= "BRUSHSCI.TTF";
				FontFactory.register(externalFontPath + "/" + fontName,
						"custom_font");
				customFont = FontFactory.getFont("custom_font",7f, Font.BOLD, BaseColor.BLACK);
			}else if (fontName.equalsIgnoreCase("Times")) {
				FontFactory.register(externalFontPath + "/" + fontName,	"custom_font");
				customFont = FontFactory.getFont("custom_font",7f, Font.BOLD, BaseColor.BLACK);
			}
			return customFont;
		}
		else{
			Font customFont = null;
			if (fontName.equalsIgnoreCase("Times New Roman")) {
				customFont = FontFactory.getFont(FontFactory.TIMES_ROMAN);
			} 
			else if (fontName.equalsIgnoreCase("Verdana")) {
				fontName= "verdana.ttf";
				FontFactory.register(externalFontPath + "/" + fontName,
						"custom_font");
				customFont = FontFactory.getFont("custom_font");
			}
			else if (fontName.equalsIgnoreCase("Calibri")) {
				fontName= "calibri.ttf";
				FontFactory.register(externalFontPath + "/" + fontName,
						"custom_font");
				customFont = FontFactory.getFont("custom_font");
			}else if (fontName.equalsIgnoreCase("Brush Script MT")) {
				fontName= "BRUSHSCI.TTF";
				FontFactory.register(externalFontPath + "/" + fontName,
						"custom_font");
				customFont = FontFactory.getFont("custom_font");
			}else if (fontName.equalsIgnoreCase("Times")) {
				fontName= "BRUSHSCI.TTF";
				FontFactory.register(externalFontPath + "/" + fontName,
						"custom_font");
				customFont = FontFactory.getFont("custom_font");
			}else if (fontName.equalsIgnoreCase("Times")) {
				FontFactory.register(externalFontPath + "/" + fontName,	"custom_font");
				customFont = FontFactory.getFont("custom_font");
			}
			else if (fontName.equalsIgnoreCase("Ariel")) {
				fontName= "ARIEL.TTF";
				FontFactory.register(externalFontPath + "/" + fontName,	"custom_font");
				customFont = FontFactory.getFont("custom_font");
			}
			else if (fontName.equalsIgnoreCase("ArielUnderline")) {
				fontName= "ARIEL.TTF";
				FontFactory.register(externalFontPath + "/" + fontName,	"custom_font");
				customFont = FontFactory.getFont("custom_font",12, Font.UNDERLINE, BaseColor.BLACK);
			}
			else if (fontName.equalsIgnoreCase("ArielFooter")) {
				fontName= "ARIEL.TTF";
				FontFactory.register(externalFontPath + "/" + fontName,	"custom_font");
				customFont = FontFactory.getFont("custom_font",9, Font.NORMAL, BaseColor.BLACK);
			}
			
			return customFont;
		}
		
	}
	
	
	public static final String getTIMES_ROMAN()
	{
		String TIMES_ROMAN = "TIMES_ROMAN";
		return TIMES_ROMAN;
	}
	
	public static final String getTIMES_ITALIC()
	{
		String TIMES_ITALIC = "TIMES_ITALIC";
		return TIMES_ITALIC;
	}

	public static final String getTIMES_BOLD()
	{
		String TIMES_BOLD = "TIMES_BOLD";
		return TIMES_BOLD;
	}
	
	public static final String getVERDANA()
	{
		String VERDANA = "VERDANA";
		return VERDANA;
	}

}
