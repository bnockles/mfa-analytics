package ui;

import java.awt.Font;
import java.awt.GraphicsEnvironment;

import dataStructures.CsvLoader;

public class Main {

	public static void main(String[] args) {
		   GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	        Font[] fonts = ge.getAllFonts();

	        for (Font font : fonts) {
	            System.out.print(font.getFontName() + " : ");
	            System.out.println(font.getFamily());
	        }
		
		UI ui = new UI();
		CsvLoader loader = new CsvLoader(ui);//TODO this has been roughly done, currently Csv loader only loads. It has no interface
	}

}
