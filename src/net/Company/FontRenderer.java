package net.Company;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class FontRenderer {

	String fontName = "Verdana";
	int fontType = Font.BOLD;
	int size = 8;
	UnicodeFont font;
	
	public FontRenderer(String fontName, int fontType, int fontSize)
	{
		this.fontName = fontName;
		this.fontType = fontType;
		this.size = fontSize;
		font = new UnicodeFont(new Font(fontName,fontType,size));
	}
	
	@SuppressWarnings("unchecked")
	public void init()
	{
		try{
			font.addAsciiGlyphs();
			font.getEffects().add(new ColorEffect());
			font.loadGlyphs();
		}
		catch(Exception e){e.printStackTrace();}
	}
	
	public int getFontHeight(String text)
	{
		return font.getHeight(text);
	}
	
	public int getFontWidth(String text)
	{
		return font.getWidth(text);
	}
	
	public void drawFont(int x, int y, String text, float r, float g, float b)
	{
		Color c = new Color(r,g,b);
		font.drawString(x,y,text,c);
	}
}
