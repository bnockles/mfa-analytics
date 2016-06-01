package ui;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

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

	
	//rounds to two decimalPlaces
	public static String format(double d){
		d = (int)(d*100)/100.0;
		return ""+d;
	}
	public static void drawSigma(Graphics2D g, int i, int j, int h) {
		g.drawLine(i+h/3, j+h/2, i, j);//center to top right
		g.drawLine(i+h/3, j+h/2, i, j+h);//center to bottom left
		g.drawLine(i, j, i+2*h/3, j);//top
		g.drawLine(i, j+h, i+2*h/3, j+h);//bottom
		g.drawLine(i+2*h/3, j, i+2*h/3, j+h/5);//top right serif
		g.drawLine(i+2*h/3, j+h, i+2*h/3, j+h-h/5);//bottom right serif
	}

}
