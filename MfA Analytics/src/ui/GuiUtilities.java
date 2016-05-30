package ui;

import java.awt.FontMetrics;
import java.awt.Graphics;

public class GuiUtilities {

	public static void centerText(Graphics g, String text, int width, int y){
		FontMetrics fm = g.getFontMetrics();
		int tWidth = fm.stringWidth(text);
		g.drawString(text, (width - tWidth)/2, y);
	}
	/**
	 * 
	 * @param string
	 * @param desiredWidth
	 * @return a String with a width no wider than desiredWidth
	 */
	public static String shortenStringtoFit(Graphics g, String string, int desiredWidth){
		FontMetrics fm = g.getFontMetrics();
		String s = string;
		int i = string.length()-1;
		while(fm.stringWidth(s) > desiredWidth){
			s = string.substring(0, i)+"...";
			i--;
		}
		return s;
	}

}
