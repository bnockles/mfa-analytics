package dataStructures;

import java.awt.Color;
import java.io.InputStream;
import java.util.Arrays;

import ui.GuiUtilities;
import ui.VisibleComponent;

public class HolisticDataDisplay extends VisibleComponent{

	public static final int DATA_WIDTH = 600;
	public static final int DATA_HEIGHT = 135;


	private static double latenessCount;;
	private static int recordsCount;
	private static double absenceCount;
	private static double timeCount;
	private static double teachersCount;
	private static double pdsCount;


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
		resetCounts();
		foreGroundColor = Color.black;
		draw();
	}



	@Override
	public void draw() {
		System.out.println("Drawing holistic data");
		//		Font original = g.getFont();
		//		g.setFont(baseFont);
		g.setColor(backGroundColor);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(foreGroundColor);
		String describeTime = " minutes late"; 
		int minutes = (int)(timeCount/teachersCount);
		if(timeCount < 0){
			describeTime = " minutes early";
			minutes = -1 * minutes;
		}

		String[][] text = {{"Total Records:", ""+recordsCount},
				{"Average Records per teacher:", ""+GuiUtilities.formatTenths((double)recordsCount/teachersCount)},
				{"Average Records per PD:", ""+GuiUtilities.formatTenths((double)recordsCount/pdsCount)},
				{"Average Arrival Tme per PD:", ""+minutes + describeTime},
				{"People are absent, on average, ",GuiUtilities.formatTenths((double)absenceCount/recordsCount*100)+"% of the time"},
				{"People are late, on average, ",GuiUtilities.formatTenths((double)latenessCount/recordsCount*100)+"% of the time"}};
		int y = 0;
		for(String[] line : text){
			for(int i = 0; i < line.length; i ++){
				g.drawString(line[i], 250*i, 25*y);

			}
			y++;
		}


	}

	public static void resetCounts(){
		latenessCount = 0;
		recordsCount = 1;
		absenceCount = 0;
		timeCount = 0;
		teachersCount = 1;
		pdsCount = 1;
		
	}

	public static void tallyData(int addRecords, int addLateness, int addAbsence, int addMinutes){
		latenessCount += addLateness;
		recordsCount += addRecords;
		absenceCount += addAbsence;
		timeCount += addMinutes;
	}

	public static void addTotals(int teachers, int PDs){
		teachersCount = teachers;
		pdsCount = PDs;
	}


}
