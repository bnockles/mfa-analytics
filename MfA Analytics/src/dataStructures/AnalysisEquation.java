package dataStructures;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ui.ViewerLabel;
import ui.VisibleComponent;
import weightEditor.WeightVersusTimeGrid;

public class AnalysisEquation extends VisibleComponent{

	public static final int EQUATION_WIDTH = 600;
	public static final int EQUATION_HEIGHT = 80;
	
	
	public static final double MAX_COEF = 1.0;
	public static final double MIN_COEF = -1.0;
	
	
	WeightVersusTimeGrid grid;
	public static final int START_TIME = 5*60 + 30;//five hours, thirty minutes after noon
	
	private double percentageLateCoef;//multiplied by number of times late
	private double percentagePresenceCoef;//multiplied by percentage of presence
	private double onTimeCoef;//is multiplied by sum of all weighted late times
	private double lateCoef;//is multiplied by sum of all weighted on time times;
	private double absenceCoef;//multiplied by number of absences
	
	
	
	public AnalysisEquation(int x, int y, WeightVersusTimeGrid grid) {
		super(x, y, EQUATION_WIDTH, EQUATION_HEIGHT);
		
		
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
		g.setColor(foreGroundColor);
		g.drawString("value is based on graph alone", 3, EQUATION_HEIGHT-3);
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
		value = (int)(value*100)/100.0;
		
		
		label.setValue(value);
		label.setTotalAbsences(absences);
		label.setTotalLate(lateness);
		label.setTotalAttendance(records);
		return "Value = "+value+", Absent "+absentPercentage+"%, Late "+latePercentage+"%";
	}

	
}
