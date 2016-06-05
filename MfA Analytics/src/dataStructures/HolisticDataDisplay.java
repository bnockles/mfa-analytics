package dataStructures;

import java.awt.Color;
import java.io.InputStream;

import ui.VisibleComponent;

public class HolisticDataDisplay extends VisibleComponent{

	public static final int DATA_WIDTH = 600;
	public static final int DATA_HEIGHT = 95;
	
	
	
	
	
	public HolisticDataDisplay(int x, int y) {
		super(x, y, DATA_WIDTH, DATA_HEIGHT);
		InputStream is = AnalysisEquation.class.getResourceAsStream("/Baumans-Regular.ttf");
//		Font font;
//		try {
//			Font aFont= Font.createFont(Font.TRUETYPE_FONT, is);
//			font=aFont.deriveFont(34f);
//			g.setFont(font);
//		} catch (Exception e) {
//		}
		
		foreGroundColor = Color.black;
		draw();
	}
	


	@Override
	public void draw() {

//		Font original = g.getFont();
//		g.setFont(baseFont);
		g.drawString("Total Records:", 0, 70);
		g.drawString("Average Records:", 0, 20);
		g.drawString("Average Arrival:", 0, 20);
		g.drawString("Absenteeism:", 0, 45);
		

		int margin = 200;

	}
	
	
}
