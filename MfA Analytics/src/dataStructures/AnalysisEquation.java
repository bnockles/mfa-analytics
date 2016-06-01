package dataStructures;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

import java.awt.FontMetrics;
import java.awt.Stroke;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ui.GuiUtilities;
import ui.ViewerLabel;
import ui.VisibleComponent;
import weightEditor.WeightVersusTimeGrid;

public class AnalysisEquation extends VisibleComponent{

	public static final int EQUATION_WIDTH = 600;
	public static final int EQUATION_HEIGHT = 95;
	
	
	public static final double MAX_COEF = 1.0;
	public static final double MIN_COEF = -1.0;
	
	
	WeightVersusTimeGrid grid;
	public static final int START_TIME = 5*60 + 30;//five hours, thirty minutes after noon
	
	private double percentageLateCoef;//multiplied by number of times late
	private double absenceCoef;//multiplied by number of absences
	
	
	
	public AnalysisEquation(int x, int y, WeightVersusTimeGrid grid) {
		super(x, y, EQUATION_WIDTH, EQUATION_HEIGHT);
		InputStream is = AnalysisEquation.class.getResourceAsStream("/Baumans-Regular.ttf");
		Font font;
		try {
			Font aFont= Font.createFont(Font.TRUETYPE_FONT, is);
			font=aFont.deriveFont(34f);
			g.setFont(font);
		} catch (Exception e) {
		}
		
		this.grid = grid;
		foreGroundColor = Color.black;
		draw();
	}
	
	
	/**
	 * 
	 * @param time the time a person checked in
	 * @return a double that reflects weighted values from all selected vriteria
	 */
	public double getTimelinessValue(int time){
		//TODO: Add more complicated levels
		return grid.calculateWeight(time);
	}


	@Override
	public void draw() {
		g.setColor(backGroundColor);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(foreGroundColor);
		Font original = g.getFont();
		g.setFont(baseFont);
		String key = "t = Total Attendance";
		g.drawString("a = Absent %", 0, 20);
		g.drawString("l = Late %", 0, 45);
		g.drawString(key, 0, 70);
		
		FontMetrics fm1 = g.getFontMetrics();
		int keyWidth = fm1.stringWidth(key);
		int margin = keyWidth+35;

		g.setFont(original);
		String eq = "v = "+GuiUtilities.format(absenceCoef)+"a + "+GuiUtilities.format(percentageLateCoef)+"l + ";
		
		g.drawString(eq, margin, EQUATION_HEIGHT-3);
		FontMetrics fm2 = g.getFontMetrics();
		int eqWidth = fm2.stringWidth(eq);
		Stroke s = g.getStroke();
		Stroke thick = new BasicStroke(3);
		g.setStroke(thick);
		int sigmaHeight = EQUATION_HEIGHT-55;
		GuiUtilities.drawSigma(g,margin+eqWidth, 12, sigmaHeight);
		g.drawString("w r", margin+eqWidth+2*sigmaHeight/3+6, sigmaHeight+12);//sequence terms
		g.drawString("t", margin+eqWidth+45, EQUATION_HEIGHT);//divisor
		g.setFont(baseFont);
		g.drawString("n=1", margin+eqWidth, sigmaHeight+31);//n=1
		g.drawString("n   n", margin+eqWidth+ 2*sigmaHeight/3+26, sigmaHeight+16);//subscripts
		g.drawString("t", margin+eqWidth+sigmaHeight/3-3, 10);//t
		g.drawLine(margin+eqWidth, EQUATION_HEIGHT-22, margin+eqWidth+90, EQUATION_HEIGHT-22);//division bar
		g.setFont(original);
		g.setStroke(s);
	}
	


	public static int minutesPastStart(Date time){
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		int minutesPastNoon = cal.get(Calendar.HOUR)*60+cal.get(Calendar.MINUTE);
		return minutesPastNoon-START_TIME;
	}
	
	
	
	public String getTimelinessValueStatement(ArrayList<TimelinessRecord> timestamps, ViewerLabel label) {
		int records = timestamps.size();
		int absences = 0;
		int lateness= 0;
		double value = 0.0;
		for(TimelinessRecord tr : timestamps){
			if(tr.getStatus().equals(TimelinessRecord.ABSENT))absences ++;
			else if(tr.getStatus().equals(TimelinessRecord.EXCUSED))records --;//does not count excused absences in the average
			else if(tr.getTime() == null)records --;//if time was not taken, record is not included in analysis
			else {
				int lateValue = minutesPastStart(tr.getTime());
				if(lateValue > 0){
					lateness++;
				}
				value += grid.calculateWeight(lateValue);
			}
		}
		int absentPercentage = 0;
		int latePercentage  = 0;			
		if(records != 0){
			absentPercentage = (int)((double)absences/records*100.0);
			latePercentage  = (int)((double)lateness/records*100.0);
		}else{
			return "Value = "+value+", This person has no timestamp records.";
		}
		//factor in other stats
		value = value + absenceCoef*absentPercentage + percentageLateCoef*latePercentage;
		value = (int)(value*100)/100.0;
		
		
		label.setValue(value);
		label.setTotalAbsences(absences);
		label.setTotalLate(lateness);
		label.setTotalAttendance(records);
		return "Value = "+value+", Absent "+absentPercentage+"%, Late "+latePercentage+"%";
	}


	public double getPercentageLateCoef() {
		return percentageLateCoef;
	}


	public void setPercentageLateCoef(double percentageLateCoef) {
		this.percentageLateCoef = percentageLateCoef;
		setMarkedForUpdate(true);
	}



	public double getAbsenceCoef() {
		return absenceCoef;
	}


	public void setAbsenceCoef(double absenceCoef) {
		this.absenceCoef = absenceCoef;
		setMarkedForUpdate(true);
	}

	
}
